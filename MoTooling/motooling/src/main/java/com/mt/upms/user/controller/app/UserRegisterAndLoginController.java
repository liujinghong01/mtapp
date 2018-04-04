package com.mt.upms.user.controller.app;

import com.alibaba.fastjson.JSONObject;
import com.mt.cms.company.service.CompanyService;
import com.mt.common.bo.AccountValidatorUtil;
import com.mt.common.bo.IdType;
import com.mt.common.controller.BaseController;
import com.mt.common.core.shiro.token.manager.TokenManager;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.StringUtils;
import com.mt.common.weixin.WeixinUtil;
import com.mt.common.weixin.utils.SendTemplateMessage;
import com.mt.upms.common.model.SmsSend;
import com.mt.upms.common.model.User;
import com.mt.upms.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Fhk
 * @Description:
 * @create 2018/3/6 10:36
 * @Modified By:
 */
@Api(value = "用户注册/登录接口", description = "用户注册/登录接口")
@RestController
@Scope(value = "prototype")
public class UserRegisterAndLoginController extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    CompanyService companyService;

    /**
     * 判断验证码是否有效
     *
     * @param phoneNum
     * @return
     */
    private String getVerifyCode(String phoneNum) {
        SmsSend smsSend = userService.selectLoginSMSByPhone(phoneNum);
        if (smsSend == null) {
            return null;
        } else {
            Date now = new Date();
            Long t1 = (now.getTime() - smsSend.getCreatedAt().getTime()) / 1000;
            if (t1 > AccountValidatorUtil.VERIFY_CODE_TIMEOUT) {
                return null;
            }
        }
        return smsSend.getMessage().trim();
    }

    @ApiOperation(value = "U004 用户注册")
    @ApiImplicitParam(name = "entity", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"password\": \"\",\n" +
            "\"regist_type\": \"\",\n" +
            "\"user\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/user/register")
    public Object subRegister(@ApiParam(required = true, value = "用户注册信息") @RequestBody Map<String, Object> entity, ModelMap modelMap) {
        String clientType = entity.get("client_type") == null ? "" : entity.get("client_type").toString().trim();
        String registType = entity.get("regist_type") == null ? "" : entity.get("regist_type").toString().trim();
        String code = entity.get("code") == null ? "" : entity.get("code").toString().trim();
        String email = null, mobile = null,
                password = entity.get("password") == null ? "" : entity.get("password").toString().trim(),
                username = entity.get("user") == null ? "" : entity.get("user").toString().trim();
        User user;

        switch (registType) {
            case "email":
                email = username;
                if (!AccountValidatorUtil.isEmail(email)) {
                    modelMap.addAttribute("description", "Email 不正确！");
                    return setModelMap(modelMap, HttpCode.BAD_EMAIL);
                }
                user = userService.findUserByEmail(email);
                if (user != null) {
                    modelMap.addAttribute("description", "Email已经存在！");
                    return setModelMap(modelMap, HttpCode.BAD_EMAIL);
                }
                String verifyCode = getVerifyCode(username);
                if (verifyCode == null || !verifyCode.equals(code)) {
                    modelMap.addAttribute("description", "验证码不正确！");
                    return setModelMap(modelMap, HttpCode.BAD_EMAIL);
                }
                break;
            case "mobile":
                mobile = username;
                if (!AccountValidatorUtil.isMobile(mobile)) {
                    modelMap.addAttribute("description", "手机号 不正确！");
                    return setModelMap(modelMap, HttpCode.BAD_EMAIL);
                }
                user = userService.selectByMobile(mobile);
                if (user != null) {
                    modelMap.addAttribute("description", "手机号已经存在！");
                    return setModelMap(modelMap, HttpCode.BAD_EMAIL);
                }
                verifyCode = getVerifyCode(username);
                if (verifyCode == null || !verifyCode.equals(code)) {
                    modelMap.addAttribute("description", "验证码不正确！");
                    return setModelMap(modelMap, HttpCode.BAD_EMAIL);
                }
                break;
            default:
                modelMap.addAttribute("description", "注册类型 不正确！");
                return setModelMap(modelMap, HttpCode.BAD_EMAIL);
        }
        user = new User();
        Long uid = companyService.createNewId(IdType.USER);
        user.setUid(uid);
        user.setFullname(username);
        user.setUsername(username);
        user.setPassword(password);
        user.setMobile(mobile);
        user.setEmail(email);
        //主动注册
        user.setInviteStatus(Byte.parseByte("4"));
        try {
            userService.createRegister(user);
        } catch (Exception e) {
            modelMap.addAttribute("description", "保存信息 失败！");
            return setModelMap(modelMap, HttpCode.BAD_EMAIL);
        }
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("uid", uid);
        modelMap.addAttribute("description", "注册成功！");
        return setModelMap(modelMap, HttpCode.OK, userMap);
    }


    @ApiOperation(value = "U005-1  微信首次登录绑定原有帐户")
    @PostMapping("/api/user/wechat_first_login")
    private Object wechatRegister(@RequestBody Map<String, Object> param, HttpServletRequest request, ModelMap modelMap) {
        try {
            //通过unionid登錄
            String nickname = param.get("nickname") == null ? null : param.get("nickname").toString().trim();
            String sex = param.get("sex") == null ? "0" : param.get("sex").toString().trim();
            String language = param.get("language") == null ? null : param.get("language").toString().trim();
            String city = param.get("city") == null ? null : param.get("city").toString().trim();
            String province = param.get("province") == null ? null : param.get("province").toString().trim();
            String country = param.get("country") == null ? null : param.get("country").toString().trim();
            String headimgurl = param.get("headimgurl") == null ? null : param.get("headimgurl").toString().trim();
            String unionid = param.get("unionid") == null ? null : param.get("unionid").toString().trim();
            String openId = param.get("openid") == null ? null : param.get("openid").toString().trim();
            String pIsBind = param.get("is_bind") == null ? "" : param.get("is_bind").toString().trim();
            String pToken = param.get("token") == null ? "" : param.get("token").toString().trim();
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());
            if (unionid == null || unionid.isEmpty() || openId == null || openId.isEmpty()) {
                modelMap.addAttribute("description", "微信号无效，登录失败！");
                return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
            }
            User user;
            Map<String, Object> sessionMap = new HashMap<>();
            if (pIsBind == null || !"1".equals(pIsBind)) {
                // 不綁定原帳戶
                user = new User();
                pUid = companyService.createNewId(IdType.USER);
                String fileUrl;
                try {
                    fileUrl = userService.downloadPicture(headimgurl);
                } catch (Exception ex) {
                    logger.info(ex.getMessage());
                    fileUrl = "";
                }
                user.setUid(pUid);
                user.setOpenId(openId);
                user.setUsername(nickname);
                user.setFullname(nickname);
                user.setInviteStatus(Byte.parseByte("3"));
                user.setAvatar(fileUrl);
                user.setWechat(unionid);
                user.setGender(Byte.parseByte(sex));
                ////user.setPreferLanguage();
                user.setCity(city);
                user.setProvince(province);
                user.setCountry(country);
                userService.insertSelective(user);
                logger.info(JSONObject.toJSON(user));
                ////return setModelMap(modelMap, HttpCode.OK);
            } else {
                user = userService.selectByUid(pUid);
                user.setOpenId(openId);
                user.setWechat(unionid);
                userService.updateByPrimaryKeySelective(user);
            }
            //判断是否存在 open_id 如果存在 就发消息通知 微信用户登录情况
            if (!StringUtils.isEmpty(user.getWechat())) {
                SendTemplateMessage.send(user, WeixinUtil.getIp(request));
            }
            if (pIsBind == null || !"1".equals(pIsBind)) {
                if (user.getPassword() == null) {
                    user.setPassword("");
                }
                TokenManager.login(user, false);
                Session session = TokenManager.getSession();
                sessionMap.put("token", session.getId().toString());
                sessionMap.put("uid", user.getUid());
            } else {
                sessionMap.put("token", pToken);
                sessionMap.put("uid", user.getUid());
            }
            modelMap.addAttribute("description", "[" + user.getUsername() + "]登录成功.");
            return setModelMap(modelMap, HttpCode.OK, sessionMap);
        } catch (Exception ex) {
            modelMap.addAttribute("description", "用户登录 失败！");
            return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
        }
    }

    /**
     * 登录提交
     *
     * @param entity
     * @param request
     * @param modelMap
     * @return
     */
    @ApiOperation(value = "U005 用户登录")
    @ApiImplicitParam(name = "entity", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"identity_type\": \"\",\n" +
            "\"password\": \"\",\n" +
            "\"unionid\": \"\",\n" +
            "\"user\": \"\",\n" +
            "\"vericode\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/user/login")
    public Object login(@ApiParam(required = true, value = "登录帐号和密码") @RequestBody Map<String, Object> entity, HttpServletRequest request, ModelMap modelMap) {
        try {
            String unionid = null, mobile = null, email = null,
                    password = entity.get("password") == null ? "" : entity.get("password").toString().trim(),
                    vCode = entity.get("vericode") == null ? "" : entity.get("vericode").toString().trim(),
                    clientType = entity.get("client_type") == null ? "" : entity.get("client_type").toString().trim();
            User user = null;
            switch (entity.get("identity_type").toString().trim()) {
                case "wechat":
                    unionid = entity.get("unionid").toString().trim();
                    user = userService.selectByWechat(unionid);
                    if (user == null) {
                        //如果没有该union,要按unionid先注册该微信用户，然后登录
                        return setModelMap(modelMap, HttpCode.WECHAT_BIND_ORIG_ACCOUNT);
                    }
                    user = userService.selectByWechat(unionid);
                    break;
                case "mobile":
                    mobile = entity.get("user").toString().trim();
                    user = userService.selectByMobile(mobile);
                    if (user == null) {
                        modelMap.addAttribute("description", "帐号或密码不正确！");
                        return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
                    }
                    if (password.length() > 0) {
                        if (!user.getPassword().trim().equals(password)) {
                            modelMap.addAttribute("description", "帐号或密码不正确！");
                            return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
                        }
                    } else if (vCode.length() > 0) {
                        String userVerifyCode = userService.getCreatedVerifyCode(mobile, null).trim();
                        if (userVerifyCode.length() == 0) {
                            modelMap.addAttribute("description", "验证码已失效！");
                            return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
                        }
                        if (!userVerifyCode.equals(vCode)) {
                            modelMap.addAttribute("description", "验证码 不正确！");
                            return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
                        }
                    } else {
                        modelMap.addAttribute("description", "帐号或密码/验证码不正确！");
                        return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
                    }
                    break;
                case "email":
                    email = entity.get("user").toString().trim();
                    user = userService.findUserByEmail(email);
                    if (user == null) {
                        modelMap.addAttribute("description", "帐号或密码不正确！");
                        return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
                    }
                    if (!user.getPassword().trim().equals(password)) {
                        modelMap.addAttribute("description", "帐号或密码不正确！");
                        return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
                    }

                    break;
                default:
                    modelMap.addAttribute("description", "登录类型 不正确！");
                    return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
            }
            ////User token = UserManager.md5Pswd(user);
            ////user.setPassword(UserManager.md5Pswd(user.getPassword()));

            //判断是否存在 open_id 如果存在 就发消息通知 微信用户登录情况
//            if (!StringUtils.isEmpty(user.getWechat()) || user.getWechat() !=null) {
//                String openId = userService.selectOpenId(user.getWechat());
//                if (openId != null || !StringUtils.isEmpty(openId)){
//                    user.setOpenId(openId);
//                    SendTemplateMessage.send(user, WeixinUtil.getIp(request));
//                }
//            }
            user.setClientType(clientType);
            if (user.getPassword() == null) {
                user.setPassword("");
            }
            TokenManager.login(user, false);
            Session session = TokenManager.getSession();
            modelMap.addAttribute("description", "[" + user.getUsername() + "]登录成功.");
            Map<String, Object> sessionMap = new HashMap<>();
            sessionMap.put("token", session.getId().toString());
            sessionMap.put("uid", user.getUid());
            return setModelMap(modelMap, HttpCode.OK, sessionMap);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            modelMap.addAttribute("description", "用户登录 失败！");
            return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
        }
    }

    @ApiOperation(value = "已登录用户设置新密码")
    @PostMapping("/api/user/set-password")
    public Object setNewPassword(@RequestBody Map<String, Object> param, HttpServletRequest request, ModelMap modelMap) {
        try {

            String vCode = request.getHeader("x-access-token") == null ? "" : request.getHeader("x-access-token").toString().trim();
            String newPassword = param.get("new_password") == null ? "" : param.get("new_password").toString().trim();
            String mobile = param.get("mobile") == null ? "" : param.get("mobile").toString().trim();
            String email = param.get("email") == null ? "" : param.get("email").toString().trim();
            Long uid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.valueOf(param.get("uid").toString());
            if (vCode.isEmpty()) {
                modelMap.addAttribute("description", "验证码不能为空，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            if (newPassword.isEmpty()) {
                modelMap.addAttribute("description", "密码不能为空，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            if (mobile.isEmpty() && email.isEmpty()) {
                modelMap.addAttribute("description", "邮箱和手机号不能全部为空，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            userService.setUserNewPassword(uid, newPassword, vCode, mobile, email);
            modelMap.addAttribute("description", "设置新密码，成功！");
            return setModelMap(modelMap, HttpCode.OK);
        } catch (IllegalArgumentException ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", ex.getMessage() + "，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "已登录用户设置新密码，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    /*
    private Object wechatRegister(Map<String, Object>param, ModelMap modelMap ){
        //内部调用,通过unionid注册
        String nickname = param.get("nickname") == null ? null : param.get("nickname").toString().trim();
        String sex = param.get("sex") == null ? "0" : param.get("sex").toString().trim();
        String language = param.get("language") == null ? null : param.get("language").toString().trim();
        String city = param.get("city") == null ? null : param.get("city").toString().trim();
        String province = param.get("province") == null ? null : param.get("province").toString().trim();
        String country = param.get("country") == null ? null : param.get("country").toString().trim();
        String headimgurl = param.get("headimgurl") == null ? null : param.get("headimgurl").toString().trim();
        String unionid = param.get("unionid") == null ? null : param.get("unionid").toString().trim();
        String openId = param.get("openid") == null ? null : param.get("openid").toString().trim();
        User user = new User();
        Long uid = companyService.createNewId(IdType.USER);
        String fileUrl = userService.downloadPicture(headimgurl);

        user.setUid(uid);
        user.setOpenId(openId);
        user.setUsername(nickname);
        user.setFullname(nickname);
        user.setInviteStatus(Byte.parseByte("3"));
        user.setAvatar(fileUrl);
        user.setWechat(unionid);
        user.setGender(Byte.parseByte(sex));
        ////user.setPreferLanguage();
        user.setCity(city);
        user.setProvince(province);
        user.setCountry(country);
        userService.insertSelective(user);
        logger.info(JSONObject.toJSON(user));
        return setModelMap(modelMap, HttpCode.OK);
    }
    */

    /**
     * 登录提交
     *//*
    @ApiOperation(value="U005 用户登录")
    @ApiImplicitParam(name = "entity", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"identity_type\": \"\",\n" +
            "\"password\": \"\",\n" +
            "\"unionid\": \"\",\n" +
            "\"user\": \"\",\n" +
            "\"vericode\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/user/login")
    public Object login(@ApiParam(required = true, value = "登录帐号和密码") @RequestBody Map<String, Object> entity, HttpServletRequest request, ModelMap modelMap){
        try {
            String unionid = null, mobile = null, email = null,
                    password = entity.get("password") == null ? "" : entity.get("password").toString().trim(),
                    vCode = entity.get("vericode") == null ? "" : entity.get("vericode").toString().trim();
            User user = null;
            switch (entity.get("identity_type").toString().trim()) {
                case "wechat":
                    unionid = entity.get("unionid").toString().trim();
                    user = userService.selectByWechat(unionid);
                    if (user == null) {
                        //如果没有该union,要按unionid先注册该微信用户，然后登录
                        ModelMap dataMap = new ModelMap();
                        wechatRegister(entity, dataMap);
                        if (dataMap == null || dataMap.isEmpty() || !dataMap.get("reasonCode").toString().equals(HttpCode.OK.toString())) {
                            modelMap.addAttribute("description", "微信号 不正确！");
                            return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
                        }
                    }
                    user = userService.selectByWechat(unionid);
                    break;
                case "mobile":
                    mobile = entity.get("user").toString().trim();
                    user = userService.selectByMobile(mobile);
                    if (user == null) {
                        modelMap.addAttribute("description", "帐号或密码不正确！");
                        return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
                    }
                    if (password.length() > 0) {
                        if (!user.getPassword().trim().equals(password)) {
                            modelMap.addAttribute("description", "帐号或密码不正确！");
                            return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
                        }
                    } else if (vCode.length() > 0) {
                        String userVerifyCode = userService.getCreatedVerifyCode(mobile).trim();
                        if (userVerifyCode.length() == 0) {
                            modelMap.addAttribute("description", "验证码已失效！");
                            return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
                        }
                        if (!userVerifyCode.equals(vCode)) {
                            modelMap.addAttribute("description", "验证码 不正确！");
                            return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
                        }
                    } else {
                        modelMap.addAttribute("description", "帐号或密码/验证码不正确！");
                        return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
                    }
                    break;
                case "email":
                    email = entity.get("user").toString().trim();
                    user = userService.findUserByEmail(email);
                    if (user == null) {
                        modelMap.addAttribute("description", "帐号或密码不正确！");
                        return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
                    }
                    if (!user.getPassword().trim().equals(password)) {
                        modelMap.addAttribute("description", "帐号或密码不正确！");
                        return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
                    }

                    break;
                default:
                    modelMap.addAttribute("description", "登录类型 不正确！");
                    return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
            }
            ////User token = UserManager.md5Pswd(user);
            ////user.setPassword(UserManager.md5Pswd(user.getPassword()));

            //判断是否存在 open_id 如果存在 就发消息通知 微信用户登录情况
            if(!StringUtils.isEmpty( user.getWechat())){
                String openId = userService.selectOpenId(user.getWechat().toString());
                user.setOpenId(openId);
                SendTemplateMessage.send(user,WeixinUtil.getIp(request));
            }

            if(user.getPassword() == null) user.setPassword("");
            User token = TokenManager.login(user, false);
            Session session = TokenManager.getSession();
            modelMap.addAttribute("description", "[" + user.getUsername() + "]登录成功.");
            Map<String, Object> sessionMap = new HashMap<>();
            sessionMap.put("token", session.getId().toString());
            sessionMap.put("uid", user.getUid());
            return setModelMap(modelMap, HttpCode.OK, sessionMap);
        }catch (Exception ex) {
            modelMap.addAttribute("description", "用户登录 失败！");
            return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
        }
    }*/

//      /**
//     * 退出
//     * @return
//     */
//    @RequestMapping(value="/app/logout",method = RequestMethod.GET)
//    @ResponseBody
    public Map<String, Object> logout() {
        try {
            TokenManager.logout();
            resultMap.put("status", 200);
        } catch (Exception e) {
            resultMap.put("status", 500);
            logger.error("errorMessage:" + e.getMessage());
            //LoggerUtils.fmtError(getClass(), e, "退出出现错误，%s。", e.getMessage());
        }
        return resultMap;
    }
}

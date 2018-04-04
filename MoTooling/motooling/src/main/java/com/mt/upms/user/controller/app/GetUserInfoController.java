package com.mt.upms.user.controller.app;

import com.mt.common.bo.AccountValidatorUtil;
import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.upms.common.model.User;
import com.mt.upms.common.model.UserCompany;
import com.mt.upms.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Author: Fhk
* @Description:
* @create    2018/1/6 10:33
* @Modified By:
*/
@Api(value = "获取/修改用户信息接口", description = "获取/修改用户信息接口")
@RestController
@Scope(value="prototype")
public class GetUserInfoController extends BaseController {
    /**
     * 获取用户信息
     *
     */
    @Resource
    UserService userService;

    @ApiOperation(value = "U014 获取用户信息")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\"\n" +
            "\"uid\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/user/get-user-info")
    public Object getUserInfo(@ApiParam(required = true,value = "获取用户信息") @RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
           
            Long uid = Long.parseLong(param.get("uid") == null ? "0" : param.get("uid").toString().trim());
            User user = userService.selectByUid(uid);
            if (null == user) {
                modelMap.addAttribute("description", "用户不存在，失败！");
                return setModelMap(modelMap, HttpCode.BAD_UID);
            }
            List<UserCompany> listCompany = userService.getCompanyByUserId(uid);
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("address", user.getAddress());
            userMap.put("avatar", user.getAvatar());
            userMap.put("city", user.getCity());

            Object[] comArry = new Object[listCompany.size()];
            int li = 0;
            for (UserCompany com : listCompany) {
                Map<String, Object> userCompanyMap = new HashMap<>();
                userCompanyMap.put("company_id", com.getCompanyId());
                userCompanyMap.put("company_code", com.getCompanyCode());
                userCompanyMap.put("company_name", com.getCompanyName());
                userCompanyMap.put("role_code", com.getRoleCode());
                userCompanyMap.put("deps", userService.getDepIdAndNameByUid(com.getCompanyId(), uid));
                comArry[li++] = userCompanyMap;
            }
            userMap.put("company", comArry);
            userMap.put("country", user.getCountry());
            userMap.put("email", user.getEmail());
            userMap.put("email_validated", user.getEmailValidated());
            userMap.put("fullname", user.getFullname());
            userMap.put("gender", user.getGender());
            userMap.put("mobile", user.getMobile());
            userMap.put("postcode", user.getPostcode());
            userMap.put("province", user.getProvince());
            userMap.put("role_code", "");
            userMap.put("uid", user.getUid());
            userMap.put("username", user.getUsername());

            modelMap.addAttribute("description", "获取用户信息 成功！");
            return setModelMap(modelMap, HttpCode.OK, userMap);
        } catch (Exception ex) {
            modelMap.addAttribute("description", "获取用户信息 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "U012 已登录用户自行修改信息")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"address\": \"\",\n" +
            "\"city\": \"\",\n" +
            "\"country\": \"\",\n" +
            "\"email\": \"\",\n" +
            "\"username\": \"\",\n" +
            "\"fullname\": \"\",\n" +
            "\"gender\": \"\",\n" +
            "\"mobile\": \"\",\n" +
            "\"postcode\": \"\",\n" +
            "\"province\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/user/modify-user-info")
    public Object modifyUserInfo(@ApiParam(required = true, value = "修改用户信息") @RequestBody Map<String, Object>param, ModelMap modelMap) {
        Map<String, Object> dataMap = null;
        try {
            
            String uid = param.get("uid") == null ? "" : param.get("uid").toString().trim();
            dataMap = new HashMap<>();
            dataMap.put("uid", uid);
            User user = userService.selectByUid(Long.parseLong(uid));
            if (user == null) {
                modelMap.addAttribute("description", "该用户不存在，失败！");
                return setModelMap(modelMap, HttpCode.FAIL, dataMap);
            }
            user.setAddress(param.get("address") == null ? null : param.get("address").toString().trim());
            user.setCity(param.get("city") == null ? null : param.get("city").toString().trim());
            user.setCountry(param.get("country") == null ? null : param.get("country").toString().trim());
            String email = param.get("email") == null ? null : param.get("email").toString().trim();
            if(email != null && email.length() > 0 && !AccountValidatorUtil.isEmail(email)) {
                modelMap.addAttribute("description", "邮箱 不正确！");
                return setModelMap(modelMap, HttpCode.FAIL, dataMap);
            }
            user.setEmail(email);
            user.setFullname(param.get("fullname") == null ? null : param.get("fullname").toString().trim());
            user.setUsername(param.get("username") == null ? null : param.get("username").toString().trim());
            user.setGender(param.get("gender") == null ? null : Byte.parseByte(param.get("gender").toString()));
            String mobile = param.get("mobile") == null ? null : param.get("mobile").toString().trim();
            if(mobile != null && mobile.length() > 0 && !AccountValidatorUtil.isMobile(mobile)) {
                modelMap.addAttribute("description", "手机号码 不正确！");
                return setModelMap(modelMap, HttpCode.FAIL, dataMap);
            }
            user.setMobile(mobile);
            user.setPostcode(param.get("postcode") == null ? null : param.get("postcode").toString().trim());
            user.setProvince(param.get("province") == null ? null : param.get("province").toString().trim());
            user.setApiToken(param.get("token") == null ? null : param.get("token").toString().trim());
            //ruserService.updateByPrimaryKey(user);
            userService.updateByPrimaryKeySelective(user);
            //更新UserOnline信息...

            modelMap.addAttribute("description", "修改用户信息 成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);
        } catch (Exception ex) {
            modelMap.addAttribute("description", "用户修改信息 失败！");
            return setModelMap(modelMap, HttpCode.FAIL, dataMap);
        }
    }

    @ApiOperation(value = "U001 已登录用户修改密码")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"new_password\": \"\",\n" +
            "\"old_password\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/user/change-password")
    public Object modifyUserPassword(@ApiParam(required = true, value = "用户修改密码") @RequestBody Map<String, Object>param, ModelMap modelMap) {
        Map<String, Object> dataMap = null;
        try {
            
            String uid = param.get("uid") == null ? "" : param.get("uid").toString().trim();
            dataMap = new HashMap<>();
            dataMap.put("uid", uid);

            String newPassword = param.get("new_password") == null ? "" : param.get("new_password").toString().trim();
            String oldPassword = param.get("old_password") == null ? "" : param.get("old_password").toString().trim();

            User user = userService.selectByUid(Long.parseLong(uid));
            if(user == null) {
                modelMap.addAttribute("description", "用户不存在 失败！");
                return setModelMap(modelMap, HttpCode.FAIL, dataMap);
            }
            if(!user.getPassword().trim().equals(oldPassword)) {
                modelMap.addAttribute("description", "原密码不一致 失败！");
                return setModelMap(modelMap, HttpCode.FAIL, dataMap);
            }
            if(!AccountValidatorUtil.isPassword(newPassword)) {
                modelMap.addAttribute("description", "新密码 不正确！");
                return setModelMap(modelMap, HttpCode.FAIL, dataMap);
            }
            user.setPassword(newPassword);
            userService.updateByPrimaryKeySelective(user);

            //更新UserOnline信息...

            modelMap.addAttribute("description", "用户修改密码 成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);
        } catch (Exception ex) {
            modelMap.addAttribute("description", "用户修改密码 失败！");
            return setModelMap(modelMap, HttpCode.FAIL, dataMap);
        }
    }

    @ApiOperation(value = "U008 校验邮箱或手机号是已经存在接口")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"identity_type\": \"\",\n" +
            "\"user\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/user/check-is-exit")
    public Object checkEmailOrMobileExists(@ApiParam(required = true, value = "")@RequestBody Map<String, Object>param, ModelMap modelMap){
        try {
            String pIdentityType = param.get("identity_type") == null ? "" : param.get("identity_type").toString().trim();
            String pUser = param.get("user") == null ? "" : param.get("user").toString().trim();
            Boolean isExists = true;
            String retCode= "";
            switch (pIdentityType) {
                case "email":
                    if(userService.findUserByEmail(pUser) == null) { isExists = false; }
                    break;
                case "mobile":
                    if(userService.selectByMobile(pUser) == null) { isExists = false; }
                    break;
                default:
                    modelMap.addAttribute("description", "用户类型不正确，失败！");
                    return setModelMap(modelMap, HttpCode.FAIL);
            }
            if(isExists) {
                modelMap.addAttribute("description", "该用户已存在，成功！");
                return setModelMap(modelMap, HttpCode.USER_EXISTS);
            } else {
                modelMap.addAttribute("description", "该用户不存在，成功！");
                return setModelMap(modelMap, HttpCode.USER_NOT_EXISTS);
            }

        }catch (Exception ex) {
            modelMap.addAttribute("description", "校验邮箱或手机号，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "U007 校验验证码是否正确")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"code\": \"\",\n" +
            "\"mobile\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/user/check-code")
    public Object verifyCodeIsCorrect(@ApiParam(required = true, value = "校验验证码")@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            String pCode = param.get("code") == null ? "" : param.get("code").toString().trim();
            String pMobile = param.get("mobile") == null ? "" : param.get("mobile").toString().trim();
            String pEmaile = param.get("email") == null ? "" : param.get("email").toString().trim();
            Map<String, Object> dataMap = new HashMap<>();
            if (!pMobile.isEmpty()) {
                dataMap.put("mobile", pMobile);
            }
            if (!pEmaile.isEmpty()) {
                dataMap.put("email", pEmaile);
                if (!AccountValidatorUtil.isEmail(pEmaile)) {
                    modelMap.addAttribute("description", "邮箱参数不正确，失败！");
                    return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
                }
            }
            dataMap.put("code", pCode);
            if (pMobile.isEmpty() && pEmaile.isEmpty()) {
                modelMap.addAttribute("description", "手机或邮箱参数不正确，失败！");
                return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
            }
            if (pCode.length() < 1) {
                modelMap.addAttribute("description", "验证码不正确，失败！");
                return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
            }
            String verifyCode = userService.getCreatedVerifyCode(pMobile, pEmaile);
            if (verifyCode.equals(pCode)) {
                modelMap.addAttribute("description", "验证码正确，成功！");
                return setModelMap(modelMap, HttpCode.OK, dataMap);
            } else {
                modelMap.addAttribute("description", "验证码不正确（未生成/不正确/失效），失败！");
                return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
            }
        } catch (Exception ex) {
            modelMap.addAttribute("description", "校验验证码，失败！");
            return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
        }
    }

}


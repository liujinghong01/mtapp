package com.mt.upms.user.controller;

import com.mt.common.bo.AccountValidatorUtil;
import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.common.core.support.email.Email;
import com.mt.common.core.support.validator.LibphonenumberUsage;
import com.mt.common.qcloudsms.QcloudSms;
import com.mt.common.utils.EmailUtil;
import com.mt.upms.common.model.SmsSend;
import com.mt.upms.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lrd on 2017/9/12.
 */
@Api(value = "短信验证接口", description = "短信验证接口")
@RestController
@Scope(value="prototype")
public class SendMsgController extends BaseController{

    @Resource
    UserService userService;

    @ApiOperation(value = "U010 校验手机号的合法性")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"country_code\": \"\",\n" +
            "\"mobile\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/msg/valid")
    public Object valid(@ApiParam(required = true,value = "输入国家地区号和手机号")@RequestBody Map<String,Object> param, ModelMap modelMap){
        String countryCode = (String) param.get("country_code");
        String phoneNum = (String) param.get("mobile");
        Map<String, Object> data = new HashMap<>();
        if(phoneNum == null || phoneNum.trim().isEmpty()){
            data.put("result_code",0);
            //data.put("resultDesc", Resources.getMessage(""));
            data.put("result_desc","非法手机号");
            modelMap.addAttribute("description","返回失败！");
            return setModelMap(modelMap, HttpCode.FAIL, data);
        }
        Boolean isValid = LibphonenumberUsage.doValid(countryCode,phoneNum);
        if(isValid){
            data.put("result_code",1);
            //data.put("resultDesc", Resources.getMessage(""));
            data.put("result_desc","合法手机号");
            modelMap.addAttribute("description","返回成功！");
            return setModelMap(modelMap, HttpCode.OK, data);
        }else {
            data.put("result_code",0);
            //data.put("resultDesc", Resources.getMessage(""));
            data.put("result_desc","非法手机号");
            modelMap.addAttribute("description","返回失败！");
            return setModelMap(modelMap, HttpCode.FAIL, data);
        }
    }

    @ApiOperation(value = "U009 获取短信验证码")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"country_code\": \"\",\n" +
            "\"mobile\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/user/get-check-code")
    public Object getSMSVerifyCode(@ApiParam(required = true,value = "输入手机号")@RequestBody Map<String,Object> param, ModelMap modelMap){
        try {
            String phoneNum = param.get("mobile") == null ? "" : param.get("mobile").toString().trim();
            String countryCode = param.get("country_code") == null ? "" : param.get("country_code").toString().trim();
            if(!LibphonenumberUsage.doValid(countryCode,phoneNum)) {
                modelMap.addAttribute("description","手机号码 无效！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            String type = param.get("type") == null ? "" : param.get("type").toString().trim();
            if(!"0".equals(type) && !"1".equals(type)) {
                modelMap.addAttribute("description","验证码类型 无效！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            boolean isCreateCode = false;
            String random = Integer.toString(RandomUtils.nextInt(100000, 999999));
            SmsSend smsSend = new SmsSend();
            smsSend.setPhone(phoneNum);
            smsSend.setMessage(random);
            smsSend.setStatus((byte)1);
            smsSend.setType(Byte.valueOf(type));
            userService.createLoginSMS(smsSend);

            //向手机发送...
            ArrayList<String>sendContent = new ArrayList<>();
            sendContent.add(random + "");
            sendContent.add("0".equals(type) ? "注册" : "登录");
            sendContent.add(AccountValidatorUtil.VERIFY_CODE_TIMEOUT / 60 + "");
            StringBuilder err = new StringBuilder();
            if (QcloudSms.sendSms(countryCode, phoneNum, sendContent, err) != 0) {
                modelMap.addAttribute("description",err.toString() + " 失败！");
                return setModelMap(modelMap,HttpCode.FAIL);
            }
            Map<String, Object> data = new HashMap<>();
            ////data.put("resultDesc", Resources.getMessage(""));
            data.put("mobile", phoneNum);
            modelMap.addAttribute("description","获取验证码 成功！");
            return setModelMap(modelMap,HttpCode.OK,data);

        } catch (Exception ex) {
            modelMap.addAttribute("description","获取验证码 失败！");
            return setModelMap(modelMap,HttpCode.FAIL);
        }
    }
    @ApiOperation(value = "U0091 获取邮箱验证码")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"\temail\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/user/get-email-check-code")
    public Object getVerifyCode(@ApiParam(required = true,value = "输入")@RequestBody Map<String,Object> param, ModelMap modelMap){
        try {
            String phoneNum = param.get("email") == null ? "" : param.get("email").toString().trim();
            if (!AccountValidatorUtil.isEmail(phoneNum)) {
                modelMap.addAttribute("description","Email 无效！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            String type = param.get("type") == null ? "" : param.get("type").toString().trim();
            if(!"0".equals(type) && !"1".equals(type)) {
                modelMap.addAttribute("description","验证码类型 无效！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            String random = Integer.toString(RandomUtils.nextInt(100000, 999999));
            SmsSend smsSend = new SmsSend();
            smsSend.setPhone(phoneNum);
            smsSend.setMessage(random);
            smsSend.setStatus((byte)1);
            smsSend.setType(Byte.valueOf(type));
            ////userService.deleteLoginSMSByPhone(smsSend.getPhone().trim());
            userService.createLoginSMS(smsSend);

            // 向邮箱发送
            Email email = new Email();
            String emailBody = AccountValidatorUtil.SET_EMAIL_VERIFY_CODE_TEMPLET;
            emailBody = emailBody.replace("<<url1>>", random);
            emailBody = emailBody.replace("<<url2>>", AccountValidatorUtil.VERIFY_CODE_TIMEOUT / 60 + "");

            email.setBody(emailBody);
            email.setSendTo(phoneNum);
            email.setTopic("motooling邮箱注册验证码");
            if (!EmailUtil.sendEmail(email)) {
                modelMap.addAttribute("description","获取邮箱注册验证码 失败！");
                return setModelMap(modelMap,HttpCode.FAIL);
            }
            Map<String, Object> data = new HashMap<>();
            ////data.put("resultDesc", Resources.getMessage(""));
            data.put("email", phoneNum);
            modelMap.addAttribute("description","获取邮箱注册验证码 成功！");
            return setModelMap(modelMap,HttpCode.OK,data);

        } catch (Exception ex) {
            modelMap.addAttribute("description","获取邮箱注册验证码 失败！");
            return setModelMap(modelMap,HttpCode.FAIL);
        }
    }
}

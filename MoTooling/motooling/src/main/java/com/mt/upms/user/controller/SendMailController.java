package com.mt.upms.user.controller;


import com.mt.common.bo.AccountValidatorUtil;
import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.common.core.support.email.Email;
import com.mt.common.utils.EmailUtil;
import com.mt.upms.common.model.EmailSend;
import com.mt.upms.common.model.User;
import com.mt.upms.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 *  Added By FHK on 2017.9.15.
 */
@Api(value = "发送邮件接口", description = "发送邮件接口")
@RestController
@Scope(value="prototype")
public class SendMailController extends BaseController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "U006 发送设置密码验证邮件")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"email\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/user/send-email")
    public Object sendSetPasswordMail(@ApiParam(required = true, value = "发送密码验证邮件") @RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String pEmail = param.get("email") == null ? "" : param.get("email").toString().trim();
            if (!AccountValidatorUtil.isEmail(pEmail)) {
                modelMap.addAttribute("description", "邮箱地址不正确，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            User user = userService.findUserByEmail(pEmail);
            if (user == null) {
                modelMap.addAttribute("description", "邮箱地址不存在，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }

            EmailSend emailSend = new EmailSend();
            emailSend.setEmail(pEmail);
            emailSend.setUid(user.getUid());
            emailSend.setMessage("");

            Email email = new Email();
            email.setBody(userService.createSetPasswordEmail(emailSend));
            email.setSendTo(pEmail);
            email.setTopic("motooling重设密码邮件");

            if (EmailUtil.sendEmail(email)) {
                modelMap.addAttribute("description", "发送密码验证邮件，成功！");
                return setModelMap(modelMap, HttpCode.OK);
            } else {
                modelMap.addAttribute("description", "发送密码验证邮件，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
        } catch (Exception ex) {
            modelMap.addAttribute("description", "发送密码验证邮件，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "U002 通过邮箱找回密码")
    @ApiImplicitParam(name = "param", value = "[请直接在浏览器地址测试]", required = true, dataType = "Map")
    @GetMapping("/api/user/email-getpassword")
    public Object emailGetPassword(@ApiParam(required = true, value = "通过邮箱找回密码") @RequestParam Map<String, Object> param, ModelMap modelMap) {
        try {
            String pE = param.get("e") == null ? "" : param.get("e").toString().trim();
            String pT = param.get("t") == null ? "" : param.get("t").toString().trim();
            String pU = param.get("u") == null ? "" : param.get("u").toString().trim();
            Map<String, Object> retMap = userService.checkFindPasswordUrl(pU, pE, pT);

            if ((int)retMap.get("code") == 1) {
                modelMap.addAttribute("description", "找回密码url正确，成功！");
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("email", retMap.get("email"));

                //调用前端重设密码url...
                return setModelMap(modelMap, HttpCode.OK, dataMap);
            } else if (retMap.get("message").toString().length() > 1) {
                modelMap.addAttribute("description", retMap.get("message").toString() + "，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            } else {
                modelMap.addAttribute("description", "验证找回密码url，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }

        } catch (Exception ex) {
            modelMap.addAttribute("description", "验证找回密码url，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "U003 通过邮箱设置新密码")
    @ApiImplicitParam(name = "param", value = "[请直接在浏览器地址测试]", required = true, dataType = "Map")
    @GetMapping("/api/user/email-setpassword")
    public Object SetPassword(@ApiParam(required = true, value = "通过邮箱设置新密码") @RequestParam Map<String, Object> param, ModelMap modelMap) {
        String pE = param.get("e") == null ? "" : param.get("e").toString().trim();
        String pT = param.get("t") == null ? "" : param.get("t").toString().trim();
        String pU = param.get("u") == null ? "" : param.get("u").toString().trim();
        String newPassword = param.get("new_password") == null ? "" : param.get("new_password").toString().trim();

        if(!AccountValidatorUtil.isPassword(newPassword)) {
            modelMap.addAttribute("description", "新密码不正确，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }

        Map<String, Object>dataMap = new HashMap<>();
        Map<String, Object> retMap = userService.checkFindPasswordUrl(pU, pE, pT);
        if ((int)retMap.get("code") == 1) {
            User user = userService.selectByUid((Long)retMap.get("uid"));
            if(user != null) {
                user.setPassword(newPassword);
                userService.updateByPrimaryKeySelective(user);
                dataMap.put("uid", user.getUid());
                modelMap.addAttribute("description", "设置新密码，成功！");
                return setModelMap(modelMap, HttpCode.OK, dataMap);
            } else {
                modelMap.addAttribute("description", "设置新密码，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
        }
        else {
            if(retMap.get("message") != null) {
                modelMap.addAttribute("description", retMap.get("message").toString() + "，失败！");
            } else {
                modelMap.addAttribute("description", "设置新密码，失败！");
            }
            return setModelMap(modelMap, HttpCode.FAIL);
        }

    }
}

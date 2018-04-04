package com.mt.upms.user.controller;

import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.common.qcloudsms.QcloudSms;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;


/**
 *  Added By FHK on 2017.9.15.
 */
@Api(value = "发送短信", description = "发送短信")
@RestController
@Scope(value="prototype")
public class SendSmsController extends BaseController {


    @ApiOperation(value = "发送短信-test")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"nationCode\": \"\",\n" +
            "\"phoneNum\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/user/send-sms-test")
    public Object sendSmsTest(@ApiParam(required = true, value = "发送短信") @RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String nationCode = param.get("nationCode") == null ? "" : param.get("nationCode").toString().trim();
            String phoneNum = param.get("phoneNum") == null ? "" : param.get("phoneNum").toString().trim();
            //SmsSDKDemo.smsSendDemo();
            ArrayList<String> sendParam = new ArrayList();
            sendParam.add("123456");
            sendParam.add("注册");
            sendParam.add("1");
            StringBuilder err = new StringBuilder();
            if (QcloudSms.sendSms(nationCode, phoneNum, sendParam, err) != 0) {
                modelMap.addAttribute("description",  err.toString() + "，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            modelMap.addAttribute("description", "发送短信，成功！");
            return setModelMap(modelMap, HttpCode.OK);
        }catch (Exception ex) {
            modelMap.addAttribute("description", "发送短信，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

}

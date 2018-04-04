package com.mt.common.weixin.controller;

import com.mt.common.controller.BaseController;
import com.mt.common.core.config.Resources;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.StringUtils;
import com.mt.common.weixin.TokenThread;
import com.mt.common.weixin.WeixinUtil;
import io.swagger.annotations.*;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by lrd on 2017/9/4.
 */
@Api(value = "获取微信的签名",description = "获取微信签名接口")
@RestController
public class SignController extends BaseController {

    @ApiOperation(value = "获取微信签名")
    @ApiImplicitParams(@ApiImplicitParam(name = "header"))
    @PostMapping("weixin/signature")
    public Object getSignature(@ApiParam(required = true,value = "跳转的url")@RequestBody Map<String,String> param, ModelMap modelMap){
        long time = System.currentTimeMillis()/1000;
        String randomStr = UUID.randomUUID().toString();
        String type = param.get("type");
        String str = "jsapi_ticket="+ TokenThread.mt_wx_jsapi_ticket+"&noncestr="+randomStr+"&timestamp="+time+"&url="+param.get("url");
        if (StringUtils.isBlank(type)){
            if (type.equalsIgnoreCase("mt_wp")){
                str = "jsapi_ticket="+ TokenThread.mt_wp_jsapi_ticket+"&noncestr="+randomStr+"&timestamp="+time+"&url="+param.get("url");
            }
        }
        String signature = WeixinUtil.sha1Encrypt(str);
        String appid = Resources.WX_TYPE.getString(param.get("type")+"_appid");
        Map<String, Object> signMap = new HashMap<>();
        signMap.put("timestamp",time);
        signMap.put("noncestr",randomStr);
        signMap.put("signature",signature);
        signMap.put("appid",appid);
        modelMap.addAttribute("description","获取成功！");
        return setModelMap(modelMap, HttpCode.OK,signMap);
    }


}

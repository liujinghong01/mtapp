package com.mt.common.weixin.controller;

import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.upms.common.model.User;
import com.mt.upms.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by fhk on 2017.9.20
 *
 */

@Api(value = "微信端修改个人头像",description = "微信端修改个人头像")
@RestController
@Scope(value="prototype")
public class WechatModifyAvatarController extends BaseController {

    @Autowired
    UserService userService;



    @ApiOperation(value = "U013 微信端修改个人头像")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"server_id\": \"\"\n" +
            "\"token\": \"\"\n" +
            "\"uid\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/user/uploadImg")
    public Object wechatModifyAvatar(@ApiParam(required = true, value = "微信端修改个人头像")@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            String pClientType = param.get("client_type") == null ? "" : param.get("client_type").toString().trim();
            String pServerId = param.get("server_id") == null ? "" : param.get("server_id").toString().trim();
            Long pUid = param.get("uid") == null ? null : Long.parseLong(param.get("uid").toString());
            /////////////////////////////////////////////////
            String fileUrl = userService.wechatUploadImg(pServerId, pUid);
            if(fileUrl == null) {
                modelMap.addAttribute("description", "微信端修改个人头像，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            User user = userService.selectByUid(pUid);
            user.setAvatar(fileUrl);
            userService.updateByPrimaryKeySelective(user);

            ////////////////////////////////////////////////
            modelMap.addAttribute("description", "微信端修改个人头像，成功！");
            Map<String, Object>dataMap = new HashMap<>();
            dataMap.put("img_url", fileUrl);

            return setModelMap(modelMap, HttpCode.OK, dataMap);
        }catch (Exception ex) {
            modelMap.addAttribute("description", "微信端修改个人头像，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }


    }

    @ApiOperation(value = "跟新关注用户列表")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"server_id\": \"\"\n" +
            "\"token\": \"\"\n" +
            "\"uid\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/user/weixin/gzh")
    public Object addWeixiGzhList(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try{
            userService.addserWeixiGzh();
            return "成功";
        }   catch (Exception ex){
            logger.info(ex.getMessage());
        }
        return "失败";
    }


}

package com.mt.upms.user.controller.app;

import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.StringUtils;
import com.mt.upms.user.service.InstationMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api(value = "站内信", description = "站内信")
@RestController
@Scope(value="prototype")
public class InstationMessageController extends BaseController {

    @Autowired
    private InstationMessageService instationMessageService;

    @ApiOperation(value = "站内信（可群发）", notes = "")
    @ApiImplicitParam(name = "param", value = "{\"token\":\"\",\"company_id\":35,\"uid\":20}", required = true, dataType = "Map")
    @PostMapping("api/send/instation/message")
    public Object sendInstationMessage(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            
            Map<String, Object> map = new HashMap();
            map.put("company_id", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            Map<String, Object> query = (Map<String, Object>) param.get("query");
            map.put("query", query);
            if(query.get("content") == null && "".equals(query.get("content"))){
                modelMap.addAttribute("description", "内容不能为空！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            if(query.get("title") == null && "".equals(query.get("title"))){
                modelMap.addAttribute("description", "标题不能为空！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            if(query.get("to_company_id") != null && !"".equals(query.get("to_company_id"))){
            }else if(query.get("to_deparment_id") != null && !"".equals(query.get("to_deparment_id"))){
            }else if(query.get("recever_uid") != null){
            }else {
                modelMap.addAttribute("description", "请选择发送人！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            Map<String, Object>  returnMap = instationMessageService.addSendMessage(map);
            modelMap.addAttribute("description", "发送成功！");
            return setModelMap(modelMap, HttpCode.OK);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "发送失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "查看列表", notes = "查看列表")
    @ApiImplicitParam(name = "param", value = "{\"token\":\"\",\"company_id\":35,\"uid\":20}", required = true, dataType = "Map")
    @PostMapping("api/get/message/list")
    public Object addRole(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            
            Map<String, Object> map = new HashMap();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("message_type", param.get("message_type") == null ? null : param.get("message_type"));
            Map<String, Object> mapReturn = instationMessageService.selectMessage(map);
            modelMap.addAttribute("description", "查看列表成功！");
            return setModelMap(modelMap, HttpCode.OK, mapReturn);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查看列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

}
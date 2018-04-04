package com.mt.pm.production.controller;

import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.StringUtils;
import com.mt.pm.production.service.PmDeviceCheckAllocateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
* @Author: Wendy
* @Description:
* @create    2018/1/22 9:45
* @Modified By:
*/    
@RestController
@Api(value = "设备点检分配", description = "设备点检分配")
public class PmDeviceCheckAllocateController extends BaseController{
    @Autowired
    private PmDeviceCheckAllocateService  pmDeviceCheckAllocateService;


    @ApiOperation(value = "E693 查询设备点检分配表", notes = "")
    @ApiImplicitParam(name = "param", value = "{\"query\":" +
                    "{\"check_uid\":" +
                      "\"2\"}," +
                     "\"client_type\":" +
                     "\"\"" + ",\"uid" +
                     "\":19," +
                     " \"company_id\":35," +
                     " \"token\":" +
                     "\"5977ffb0-a93e-4a87-af44-904bb142f4d7\"}", required = true, dataType = "Map")
    @PostMapping("/api/pm/pg_device_check_allocate/list")
    @RequiresPermissions(value = "enterprise:pm:device:view")
    public Object selectDeviceCheckAllocate(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("query", StringUtils.isEmpty(param.get("query").toString()) == true ? null : param.get("query"));
            Map<String, Object> returnMap = pmDeviceCheckAllocateService.selectDeviceCheckAllocate(map);
            modelMap.addAttribute("description", "查询设备点检分配表成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询设备点检分配表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = " 783 查询设备点检分配详情表", notes = "")
    @ApiImplicitParam(name ="param",value =
            "{\"query\":" +
                    "{\"allocate_id\":" + "\"10\"}," +
            "\"client_type\":" +
            "\"\",\"uid\"" + ":19," +
            "\"company_id\":" + "35," +
            "\"token\"" + ":\"81aa7dbf-c0d1-49af-abb0-50e541dad545\"}",required = true,dataType = "Map")
    @PostMapping("/api/pm/pg_device_check_allocate/detail")
    @RequiresPermissions(value = "enterprise:pm:device:view")
    public  Object  selectDeviceCheckAllocateDetail(@RequestBody Map<String,Object> param,ModelMap  modelMap){
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("query", StringUtils.isEmpty(param.get("query").toString()) == true ? null : param.get("query"));
            Map<String, Object> returnMap = pmDeviceCheckAllocateService.selectDeviceCheckDetail(map);
            modelMap.addAttribute("description", "查询设备点检分配详情表成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询设备点检分配详情表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E702 设备点检分配", notes = "")
    @ApiImplicitParam(name = "param", value = "{\"company_id\":" + "\"35\"," +
            "\"uid\":" + "\"19\"," +
            "\"client_type\"" + ":\"\"," +
            "\"token\":" +
            "\"7bea0221-f007-4713-b2ac-be0a88e7056b\"," +
            "\"check_allocate_info\":" +
            "" + "{\"allocate_id\":" + "\"4\"," +
            "\"pg_id\":" +
            "\"659\"," +
            "\"check_uid_list\":" +
            "[{\"check_uid\":\"5\"" + "," +
            "\"check_uid_name\":\"大白\"}]," +
            "\"device_list\":" +
            "[{\"device_id\":" + "\"4\"," +
            "\"device_name" + "\":" +
            "\"cesneir测试内容\"}," +
            "{\"device_id\":\"5\"," +
            "\"device_name\":\"cesneir测试内容\"}," +
            "" + "{\"device_id\":\"6\"" +
            ",\"device_name\":\"cesneir测试内容\"}]}}", required = true, dataType = "Map")
    @PostMapping("/api/pm/pg_device/check_allocate_modify")
    @RequiresPermissions(value = "enterprise:pm:device:create,update")
    public Object deviceCheckAllocateManager(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("check_allocate_info", param.get("check_allocate_info") == null ? null : param.get("check_allocate_info"));
            map.put("company_id", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            Map<String, Object> returnMap = pmDeviceCheckAllocateService.addAndUpdateDeviceCheckAllocate(map);
            modelMap.addAttribute("description", "设备点检分配成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        }catch (IllegalArgumentException e){
            modelMap.addAttribute("description", e.getMessage());
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "设备点检分配失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E806 设备点检分配删除", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/pm/pg_device/check_allocate_delete")
    @RequiresPermissions(value = "enterprise:pm:device:delete")
    public Object updateCheckAllocate(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap<>();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("check_allocate_info", param.get("check_allocate_info") == null ? null : param.get("check_allocate_info"));
            Map<String,Object> returnMap = pmDeviceCheckAllocateService.updateCheckAllocateByAllocateId(map);
            modelMap.addAttribute("description", "设点检分配删除成功！");
            return setModelMap(modelMap, HttpCode.OK,returnMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "设备点检分配删除失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }
}
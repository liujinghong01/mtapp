package com.mt.pm.production.controller;

import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.StringUtils;
import com.mt.pm.production.service.PmPgDeviceService;
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
* @create    2018/1/9 15:37
* @Modified By:
*/
@RestController
@Api(value = "设备", description = "设备维护")
public class PmPgDeviceController extends BaseController {

    @Autowired
    private PmPgDeviceService pmPgDeviceService;


    @ApiOperation(value = "E694 查询设备列表", notes = "")
    @ApiImplicitParam(name = "param", value =
            "{\"curr_page\":1" +
            ",\"page_size\":20" +
            ",\"query\":" +
                    "{\"device_name\":\"cesneir测试内容\"" +
                    ",\"pg_id\":\"2\"}" +
            "," + "\"client_type\":2" +
            "," + "\"uid\":19" +
            ",\"company_id\":35" +
            ", \"token\": \"6187136b-550d-4604-bc59-b44ef6667c4d\"}", required = true, dataType = "Map")
    @PostMapping("/api/pm/pg_device/list")
    @RequiresPermissions(value = "enterprise:pm:device:view")
    public Object selectDeviceList(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            String uid = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("curr_page", param.get("curr_page") == null ? 1 : param.get("curr_page"));
            map.put("page_size", param.get("page_size") == null ? 10 : param.get("page_size"));
            map.put("query", StringUtils.isEmpty(param.get("query").toString()) == true ? null : param.get("query"));
            map.put("company_id", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            Map<String, Object> retrunMap = pmPgDeviceService.selectDeviceList(map);
            modelMap.addAttribute("description", "查询设备列表成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询设备列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }




    @ApiOperation(value = "B692 查询设备详情", notes = "")
    @ApiImplicitParam(name = "param", value =
            "{\"query\":" +
            "{\"device_id\":\"1\"}" +
            ",\"client_type\":" + "2" +
            ",\"uid\":19" +
            ",\"company_id\":35" +
            ",\"token\":\"28d1b32b-c189-47ea-81b7-9ff6f7a1c65b\"}", required = true, dataType = "Map")
    @PostMapping("/api/pm/pg_device/detail")
    @RequiresPermissions(value = "enterprise:pm:device:view")
    public Object selectDeviceDetail(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("query", StringUtils.isEmpty(param.get("query").toString()) == true ? null : param.get("query"));
            Map<String, Object> returnMap = pmPgDeviceService.selectDeviceDetail(map);
            modelMap.addAttribute("description", "查询设备详情成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询设备详情失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }

    }

    @ApiOperation(value = "E700 设备删除", notes = "")
    @ApiImplicitParam(name = "param", value =
            "{\"client_type\":\"\"" +
            ",\"device_id\":\"1\"" +
            ",\"token\":\"81a7128b-2de0-486a-b566-7ab5b951ae70\"" +
            ",\"uid\":\"1\"}", required = true, dataType = "Map")
    @PostMapping("/api/pm/pg_device/delete")
    @RequiresPermissions(value = "enterprise:pm:device:delete")
    public Object updateDeviceModify(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap<>();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("device_info", param.get("device_info") == null ? null : param.get("device_info"));
            Map<String,Object> returnMap = pmPgDeviceService.updateDevice(map);

            modelMap.addAttribute("description", "设备删除成功！");
            return setModelMap(modelMap, HttpCode.OK,returnMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "设备删除失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E701 设备新增修改", notes = "")
    @ApiImplicitParam(name = "param", value = "{\"client_type\":\"\"" +
            ",\"company_id\":\"35\"" +
            ",\"uid\":\"19\"" +
            "," + "\"token\":\"28d1b32b-c189-47ea-81b7-9ff6f7a1c65b\"" +
            ",\"device_info\":" +
            "{\"device_id\":\"1\"" +
            ",\"" + "check_uid\":\"1\"" +
            ",\"check_uid_name\":\"lll\"" +
            ",\"device_addr\":\"南山区\"" +
            ",\"device_brand\":"
            + "\"测试内容\"" +
            ",\"pg_id\":\"1\"" +
            ",\"device_model\":\"HA01\"" +
            ",\"device_name\":\"测试内容 \"" +
            ",\"device_no\"" + ":1" +
            ",\"device_pic\":12" +
            ",\"device_status\":0" +
            ",\"is_auto_pause\":\"1\"" +
            ",\"is_auto_prod\":\"1\"" +
            ",\"is_share\":" + "\"是\"" +
            ",\"labour_ratio\":\"5\"" +
            ",\"last_maintain_reason\":\"正常\"" +
            ",\"pg_name\":\"生产组\"" +
            ",\"prod_date\"" + ":\"2017-12-21\"" +
            ",\"work_life\":\"1\"}}", required = true, dataType = "Map")
    @PostMapping("/api/pm/pg_device/modify")
    @RequiresPermissions(value = "enterprise:pm:device:create,update")
    public Object addAndUpdateDevice(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap<>();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("device_info", param.get("device_info") == null ? null : param.get("device_info"));
            Map<String, Object> returnMap = pmPgDeviceService.addAndUpdateDevice(map);
            modelMap.addAttribute("description", "设备新增修改成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "设备新增修改失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }



    @ApiOperation(value = "E695 查询设备点检表", notes = "")
    @ApiImplicitParam(name = "param", value =
            "{\"query\":" +
                    "{\"device_id\":\"8\"}" +
            ",\"client_type\":\"\"" +
            ",\"uid\":19" +
            ",\"company_id\":35" +
            ",\"token\":\"5977ffb0-a93e-4a87-af44-904bb142f4d7\"}", required = true, dataType = "Map")
    @PostMapping("/api/pm/pg_device_check/list")
    @RequiresPermissions(value = "enterprise:pm:device:view")
    public Object selectDeviceCheckList(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("query", StringUtils.isEmpty(param.get("query").toString()) == true ? null : param.get("query"));
            Map<String, Object> returnMap = pmPgDeviceService.selectDeviceCheckList(map);
            modelMap.addAttribute("description", "查询设备点检表成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询设备点检表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E704 设备点检", notes = "")
    @ApiImplicitParam(name = "param", value =
            "{\"company_id\":\"35\"" +
            ",\"uid\":\"19\"" +
            ",\"client_type\":\"\"" +
            ",\"token\":\"c311a282-792d-4e38-b4c0-2179fdfb0e71\"" +
            ",\"device_check_info\":" +
            "{\"device_id\":\"2\"" +
            ",\"remark\":\"该设备在今天点检很成功\"}}", required = true, dataType = "Map")
    @PostMapping("/api/pm/pg_device/check")
    @RequiresPermissions(value = "enterprise:pm:device:update")
    public Object deviceCheckManager(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("device_check_info", StringUtils.isEmpty(param.get("device_check_info").toString()) == true ? null : param.get("device_check_info"));
            Map<String, Object> returnMap = pmPgDeviceService.updateDeviceCheck(map);
            modelMap.addAttribute("description", "设备点检功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "设备点检失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }
}
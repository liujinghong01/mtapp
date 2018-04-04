package com.mt.pm.production.controller;

import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.StringUtils;
import com.mt.pm.production.service.PmDeviceMaintainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

/**
* @Author: Wendy
* @Description:
* @create    2018/1/9 15:36
* @Modified By:
*/
@RestController
@Api(value="设备检修开始到检修完毕",description = "设备检修开始到检修完毕")
public class PmDeviceMaintainController extends BaseController{
    @Autowired
    private PmDeviceMaintainService  pmDeviceMaintainService;

    @ApiOperation(value = "E703 设备检修开始", notes = "")
    @ApiImplicitParam(name = "param", value =
            "{\"company_id\":\"35\"" +
            ",\"device_maintain_info\":" +
            "{\"device_id\":\"1\"}," +
            "" + "\"client_type\":"
            + "\"\",\"uid\":\"19\"," +
            "\"token\":\"6b590cde-669c-4344-aca7-b961cda97d4d\"}", required = true, dataType = "Map")
    @PostMapping("/api/pm/device_maintain/start")
    @RequiresPermissions(value = "enterprise:pm:device:update")
    public Object startDeviceMaintainBill(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("device_maintain_info", StringUtils.isEmpty(param.get("device_maintain_info").toString()) == true ? null : param.get("device_maintain_info"));
            Map<String, Object> returnMap = pmDeviceMaintainService.startDeviceMainTain(map);
            modelMap.addAttribute("description", "设备检修开始成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "设备检修开始失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E766 查询设备当前检修信息", notes = "")
    @ApiImplicitParam(name = "param", value =
            "{\"query\":" +
                    "{\"device_id\":\"2\"}," +
            "\"client_type\":\"\"," +
            "" + "\"uid\":\"19\"," +
            "\"token\":\"11de589e-c12f-4182-991b-d9c5df99509e\"}", required = true, dataType = "Map")
    @PostMapping("/api/pm/device_maintain_curr/query")
    @RequiresPermissions(value = "enterprise:pm:device:view")
    public Object selectDeviceMaintainCurr(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("query", StringUtils.isEmpty(param.get("query").toString()) == true ? null : param.get("query"));
            Map<String, Object> returnMap = pmDeviceMaintainService.selectDeviceMainTainCurr(map);
            modelMap.addAttribute("description", "查询设备当前检修信息成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        }catch (DateTimeParseException e){
            modelMap.addAttribute("description", e.getMessage());
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询设备当前检修信息失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E691 查询设备检修记录", notes = "")
    @ApiImplicitParam(name = "param", value =
            "{\"curr_page\":1" +
            ",\"page_size\":20" +
            ",\"query\":" + "{\"device_id\":\"1\"}" +
            ",\"client_type\":2" +
            ",\"uid\":19" +
            ",\"company_id\":35" +
            ",\"token\"" + ":\"04a62d93-0773-4e1c-9fdb-a0717b0d7765\"}", required = true, dataType = "Map")
    @PostMapping("/api/pm/pg_device/maintain_record")
    @RequiresPermissions(value = "enterprise:pm:device:view")
    public Object selectDeviceMainTainRecord(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("curr_page", param.get("curr_page") == null ? 1 : param.get("curr_page"));
            map.put("page_size", param.get("page_size") == null ? 10 : param.get("page_size"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("query", StringUtils.isEmpty(param.get("query").toString()) == true ? null : param.get("query"));
            Map<String, Object> returnMap = pmDeviceMaintainService.selectDeviceMaintainRecord(map);
            modelMap.addAttribute("description", "查询设备检修记录成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询设备检修记录失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }



    @ApiOperation(value = "E764 设备检修单保存 (检修完毕)", notes = "")
    @ApiImplicitParam(name = "param", value =
            "{\"company_id\":\"35\"" +
            ",\"client_type\":\"\"" +
            ",\"uid\":\"19\"" +
            ",\"token\":\"11de589e-c12f-4182-991b-d9c5df99509e\"" +
            ",\"device_maintain_info\":" +
            "{\"cycle\":\"1\"" +
            ",\"device_id\":\"2\"" +
            ",\"device_name\":\"设备\"" +
            ",\"end_date\":\"2017-12-27\"" +
            ",\"length\":\"2\"" +
            "," + "\"maintain_no\":\"001\"" +
            ",\"next_end_date\"" + ":\"2017-12-28\"" +
            "," + "\"next_start_date\":\"2017-12-29\"" + "" +
            ",\"reason\":\"as\",\"start_date\":\"2017-12-26\"}}", required = true, dataType = "Map")
    @PostMapping("/api/pm/device_maintain_bill/save")
    @RequiresPermissions(value = "enterprise:pm:device:create")
    public Object endDeviceMaintainBill(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("device_maintain_info", StringUtils.isEmpty(param.get("device_maintain_info").toString()) == true ? null : param.get("device_maintain_info"));
            Map<String, Object> returnMap = pmDeviceMaintainService.endDeviceMainTain(map);
            modelMap.addAttribute("description", "设备检修单保存成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "设备检修单保存失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }
}

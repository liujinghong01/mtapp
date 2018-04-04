package com.mt.pm.production.controller;

import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.ValidatorUtil;
import com.mt.pm.common.model.PmShift;
import com.mt.pm.common.pojo.PmShiftPojo;
import com.mt.pm.production.service.PmShiftService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

/**
 * @Author:Wujie
 * @Date: Create in 2018-01-12 11:28
 * @Description:
 * @Modified By:
 */
@RestController
@Api(value="班次",description = "班次")
public class PmShiftController extends BaseController {
    @Autowired
    private PmShiftService pmShiftService;

    @ApiOperation(value = "E500 查询班次列表", notes = "查询班次列表")
    @ApiImplicitParam(name = "param", required = true, dataType = "Map", value = "{\n" +
            "  \"client_type\": \"\",\n" +
            "  \"company_id\": \"1\",\n" +
            "  \"curr_page\": 1,\n" +
            "  \"page_size\": 5,\n" +
            "  \"uid\": \"97\",\n" +
            "  \"query\": {\n" +
            "    \"shift_name\": \"班次1\"\n" +
            "  },\n" +
            "  \"token\": \"53322389-48b4-4c9d-9e63-22748b637c3c\"\n" +
            "}")
    @PostMapping("/api/pm/shift/list")
    @RequiresPermissions(value = "enterprise:pm:set_time_calendar:view")
    public Object selectPmShiftList(@RequestBody PmShiftPojo pmShiftPojo, ModelMap modelMap) {
        try {

            Map<String,Object> returnMap = pmShiftService.selectPmShiftList(pmShiftPojo);
            modelMap.addAttribute("description", "查询班次列表成功！");
            return setModelMap(modelMap,HttpCode.OK,returnMap);
        }catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询班次列表失败！");
            return setModelMap(modelMap,HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E740 查询班次详情", notes = "查询班次详情")
    @ApiImplicitParam(name = "param", required = true, dataType = "Map", value = "{\n" +
            "  \"client_type\": \"\",\n" +
            "  \"uid\": \"97\",\n" +
            "  \"query\": {\n" +
            "    \"shift_id\": \"1\"\n" +
            "  },\n" +
            "  \"token\": \"e0bdbd10-e380-4932-8bb0-dd80abdd16bc\"\n" +
            "}")
    @PostMapping("/api/pm/shift/detail")
    @RequiresPermissions(value = "enterprise:pm:set_time_calendar:view")
    public Object selectPmShiftDetail(@RequestBody PmShiftPojo pmShiftPojo,ModelMap modelMap){
        try {
            Map<String, Object> returnMap = pmShiftService.selectPmShiftDetail(pmShiftPojo);
            modelMap.addAttribute("description", "查询班次详情成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询班次详情失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value="E503 班次删除" ,notes = "班次删除")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "  \"token\": \"53322389-48b4-4c9d-9e63-22748b637c3c\",\n" +
            "  \"shift_id\": \"1\",\n" +
            "  \"client_type\": \"\",\n" +
            "  \"uid\": \"1\"\n" +
            "}",required = true,dataType = "Map")
    @PostMapping("/api/pm/shift/delete")
    @RequiresPermissions(value = "enterprise:pm:set_time_calendar:delete")
    public Object deletePmShift(@RequestBody PmShiftPojo pmShiftPojo,ModelMap modelMap){
        try {
            pmShiftService.deletePmShift(pmShiftPojo);
            modelMap.addAttribute("description", "删除班次成功！");
            return setModelMap(modelMap, HttpCode.OK);
        } catch (Exception ex){
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "删除班次失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value="E504 班次新增修改",notes = "班次新增修改")
    @ApiImplicitParam(name = "param", value = " {\n" +
            "  \"company_id\": \"1\",\n" +
            "  \"uid\": \"97\",\n" +
            "  \"token\": \"fe7f0057-ef73-4e29-b981-7d620190ca40\",\n" +
            "  \"shift_info\": {\n" +
            "    \"day_mins\": \"10\",\n" +
            "    \"shift_color\": \"hei\",\n" +
            "    \"shift_id\": \"1\",\n" +
            "    \"shift_name\": \"班1次\",\n" +
            "    \"detail_list\": [\n" +
            "      {\n" +
            "        \"detail_id\": \"10\",\n" +
            "        \"end_time\": \"12:00\",\n" +
            "        \"is_over_day\": \"\",\n" +
            "        \"seq\": \"\",\n" +
            "        \"shift_mins\": \"50\",\n" +
            "        \"start_time\": \"8:00\"\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "} ",required = true,dataType = "Map")
    @PostMapping("/api/pm/shift/modify")
    @RequiresPermissions(value = "enterprise:pm:set_time_calendar:create,update")
    public Object addAndUpdatePmShift(@RequestBody PmShiftPojo pmShiftPojo, @Valid PmShift pmShift, BindingResult result, ModelMap modelMap){
        if (result.hasErrors()){
            Map<String,Object> map = ValidatorUtil.getErrors(result);
            throw new IllegalArgumentException(JSONObject.fromObject(map).toString());
        }
        try {
            Map<String,Object> returnMap = pmShiftService.addAndUpdatePmShift(pmShiftPojo);
            modelMap.addAttribute("description", "新增修改班次成功！");
            return setModelMap(modelMap, HttpCode.OK,returnMap);
        }catch (Exception ex){
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "新增修改班次失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }
}

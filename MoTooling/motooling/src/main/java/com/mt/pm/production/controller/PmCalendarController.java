package com.mt.pm.production.controller;

import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.pm.common.pojo.PmCalendarPojo;
import com.mt.pm.production.service.PmCalendarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author:Wujie
 * @Date: Create in 2018-01-12 11:28
 * @Description:
 * @Modified By:
 */
@RestController
@Api(value="工作日历",description = "工作日历")
public class PmCalendarController extends BaseController {

    @Autowired
    private PmCalendarService pmCalendarService;
    @ApiOperation(value = "E499 查询日历列表", notes = "查询日历列表")
    @ApiImplicitParam(name = "param", required = true, dataType = "Map", value = "{\n" +
            "  \"client_type\": \"\",\n" +
            "  \"company_id\": \"1\",\n" +
            "  \"curr_page\": 1,\n" +
            "  \"page_size\": 5,\n" +
            "  \"uid\": \"97\",\n" +
            "  \"query\": {\n" +
            "    \"calendar_name\": \"工作日历1\"\n" +
            "  },\n" +
            "  \"token\": \"6d0717c0-49b1-4dfc-861a-249e84585257\"\n" +
            "}")
    @PostMapping("/api/pm/calendar/list")
    @RequiresPermissions(value = "enterprise:pm:set_time_calendar:view")
    public Object selectPmCalendarList(@RequestBody PmCalendarPojo pmCalendarPojo, ModelMap modelMap) {
        try {
            Map<String, Object> returnMap = pmCalendarService.selectPmCalendarList(pmCalendarPojo);
            modelMap.addAttribute("description", "查询日历列表成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询日历列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E739 查询日历详情", notes = "查询日历详情")
    @ApiImplicitParam(name = "param", required = true, dataType = "Map", value = "{\n" +
            "  \"client_type\": \"\",\n" +
            "  \"uid\": \"137\",\n" +
            "  \"query\": {\n" +
            "    \"calendar_id\": \"1\",\n" +
            "    \"end_date\":\"2017-12-19\",\n" +
            "    \"start_date\":\"2017-12-17\"\n" +
            "  },\n" +
            "  \"token\": \"e9bbfe85-fbf2-48c5-9f89-65fa3a1493a5\"\n" +
            "}")
    @PostMapping("/api/pm/calendar/detail")
    @RequiresPermissions(value = "enterprise:pm:set_time_calendar:view")
    public Object selectPmCalendarDetail(@RequestBody PmCalendarPojo pmCalendarPojo, ModelMap modelMap) {
        try {
            Map<String, Object> returnMap = pmCalendarService.selectPmCalendarDetail(pmCalendarPojo);
            modelMap.addAttribute("description", "查询日历详情成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询日历详情失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E501 删除日历", notes = "删除日历")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "  \"token\": \"53322389-48b4-4c9d-9e63-22748b637c3c\",\n" +
            "  \"calendar_id\": \"1\",\n" +
            "  \"client_type\": \"\",\n" +
            "  \"uid\": \"1\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/calendar/delete")
    @RequiresPermissions(value = "enterprise:pm:set_time_calendar:delete")
    public Object deletePmCalendar(@RequestBody PmCalendarPojo pmCalendarPojo, ModelMap modelMap) {
        try {
            pmCalendarService.deletePmCalendar(pmCalendarPojo);
            modelMap.addAttribute("description", "删除日历成功！");
            return setModelMap(modelMap, HttpCode.OK);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "删除日历失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value="E502 新增修改日历",notes = "新增修改日历")
    @ApiImplicitParam(name = "param",value="{\n" +
            "  \"client_type\": \"\",\n" +
            "  \"company_id\": \"1\",\n" +
            "  \"uid\": \"97\",\n" +
            "  \"token\": \"53322389-48b4-4c9d-9e63-22748b637c3c\",\n" +
            "  \"calendar_info\": {\n" +
            "    \"calendar_id\": \"1\",\n" +
            "    \"calendar_name\": \"日历\",\n" +
            "    \"date_list\": [\n" +
            "      {\n" +
            "        \"shift_id\": \"5\",\n" +
            "        \"the_date\": \"\"\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}",required = true,dataType = "Map")
    @PostMapping("/api/pm/calendar/modify")
    @RequiresPermissions(value = "enterprise:pm:set_time_calendar:create,update")
    public Object addAndUpdatePmCalendar(@RequestBody PmCalendarPojo pmCalendarPojo,ModelMap modelMap){
        try {
            Map<String,Object> returnMap = pmCalendarService.addAndUpdatePmCalendar(pmCalendarPojo);
            modelMap.addAttribute("description", "新增修改日历成功！");
            return setModelMap(modelMap, HttpCode.OK,returnMap);
        } catch (IllegalArgumentException e){
            modelMap.addAttribute("description", e.getMessage());
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex){
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "新增修改日历失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

}
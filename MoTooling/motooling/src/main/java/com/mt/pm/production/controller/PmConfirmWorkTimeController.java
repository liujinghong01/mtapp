package com.mt.pm.production.controller;
/**
 * created by fhk on 2018.1.2
 *
 * 报价单参考及关联
 *
 */

import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.pm.production.service.PmConfirmWorkTimeService;
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
import java.util.List;
import java.util.Map;

/**
* @Author: Fhk
* @Description:
* @create    2018/1/10 13:28
* @Modified By:
*/
@RestController
@Api(value = "小组报工", description = "小组报工")
public class PmConfirmWorkTimeController extends BaseController {

    @Autowired
    private PmConfirmWorkTimeService pmConfirmWorkTimeService;


    @ApiOperation(value = "查询任务资源列表", notes = "查询任务资源列表")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\":\"\",\n" +
            "\"company_id\":\"\",\n" +
            "\"query\":{\n" +
            "\t\"pop_id\":\"\"\n" +
            "\t},\n" +
            "\"token\":\"\",\n" +
            "\"uid\":\"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/pop/resource_list")
    @RequiresPermissions(value = "enterprise:pm:task_manager:view")
    public Object queryResourceList(@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            
            Map<String, Object>map = (Map)param.get("query");
            if(map.get("pop_id") == null || map.get("pop_id").toString().isEmpty()) {
                modelMap.addAttribute("description", "加工工序 不能为空， 失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            Map retMap = pmConfirmWorkTimeService.queryAllResourceByPopId(Long.valueOf(map.get("pop_id").toString()));
            return setModelMap(modelMap, HttpCode.OK, retMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询任务资源列表 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "查询任务领取列表", notes = "查询任务领取列表")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\":\"\",\n" +
            "\"company_id\":\"\",\n" +
            "\"curr_page\":\"\",\n" +
            "\"page_szie\":\"\",\n" +
            "\"query\":{\n" +
            "\t\"pg_id\":\"\",\n" +
            "\t\"pop_status\":\"\"\n" +
            "\t},\n" +
            "\"token\":\"\",\n" +
            "\"uid\":\"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/pop/incept_list")
    @RequiresPermissions(value = "enterprise:pm:task_manager:view")
    public Object queryInceptList(@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            ////String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            ////Integer pCompanyId = param.get("company_id") == null || param.get("company_id").toString().isEmpty() ? null : Integer.parseInt(param.get("company_id").toString());
            Integer pCurrPage = param.get("curr_page") == null || param.get("curr_page").toString().isEmpty() ? null : Integer.parseInt(param.get("curr_page").toString());
            Integer pPageSize = param.get("page_size") == null || param.get("page_size").toString().isEmpty() ? null : Integer.parseInt(param.get("page_size").toString());
            ////if (pCurrPage == null || pCurrPage < 1) pCurrPage = 1;
            ////if (pPageSize == null || pPageSize < 1) pPageSize = 10;
            ////Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());
             
            Map<String, Object>map = (Map)param.get("query");
           /* if(map.get("pg_id") == null || map.get("pg_id").toString().isEmpty()) {
                modelMap.addAttribute("description", "生产小组 不能为空， 失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }*/
            /*map.put("curr_page", pCurrPage);
            map.put("page_size", pPageSize);
            map.put("page", (pCurrPage - 1)*pPageSize);*/
            map.put("uid", param.get("uid"));
            map.put("company_id", param.get("company_id"));

            StringBuilder totalCount = new StringBuilder();
            List<Map<String, Object>> pgMapList = pmConfirmWorkTimeService.selectTicketInceptList(map, totalCount);
            //Map<String,Object> pageMap = this.createPageMap(Long.parseLong(totalCount.toString()), pPageSize, pCurrPage);
            //pageMap.put("pg_list", pgMapList);
            Map<String, Object> retMap = new HashMap<>();
            retMap.put("pg_list", pgMapList);
            modelMap.addAttribute("description", "查询任务领取列表, 成功！");
            //return setModelMap(modelMap, HttpCode.OK, pageMap);
            return setModelMap(modelMap, HttpCode.OK, retMap);
        } catch (IllegalArgumentException ex) {
            modelMap.addAttribute("description", ex.getMessage() + ", 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询任务领取列表, 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "查询小组报工列表", notes = "查询小组报工列表")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\":\"\",\n" +
            "\"company_id\":\"\",\n" +
            "\"curr_page\":\"\",\n" +
            "\"page_szie\":\"\",\n" +
            "\"query\":{\n" +
            "\t\"pg_id\":\"\",\n" +
            "\t\"type\":\"\"\n" +
            "\t},\n" +
            "\"token\":\"\",\n" +
            "\"uid\":\"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/pop/confirm_list")
    @RequiresPermissions(value = "enterprise:pm:job_booking:view")
    public Object queryPgConfirmWorkTime(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            ////String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            ////Integer pCompanyId = param.get("company_id") == null || param.get("company_id").toString().isEmpty() ? null : Integer.parseInt(param.get("company_id").toString());
            Integer pCurrPage = param.get("curr_page") == null || param.get("curr_page").toString().isEmpty() ? null : Integer.parseInt(param.get("curr_page").toString());
            Integer pPageSize = param.get("page_size") == null || param.get("page_size").toString().isEmpty() ? null : Integer.parseInt(param.get("page_size").toString());
            ////if (pCurrPage == null || pCurrPage < 1) pCurrPage = 1;
            ////if (pPageSize == null || pPageSize < 1) pPageSize = 10;
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());
            
            Map<String, Object>map = (Map)param.get("query");
           /* if(map.get("pg_id") == null || map.get("pg_id").toString().isEmpty()) {
                modelMap.addAttribute("description", "生产小组 不能为空， 失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }*/
            String type = map.get("type") == null ? "" : map.get("type").toString().trim();
            if (!"1".equals(type) && !"2".equals(type) && !"3".equals(type) ) {
                modelMap.addAttribute("description", "类型 不正确， 失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            /*map.put("curr_page", pCurrPage);
            map.put("page_size", pPageSize);
            map.put("page", (pCurrPage - 1)*pPageSize);*/
            map.put("uid", pUid);
            map.put("company_id", param.get("company_id"));

            StringBuilder totalCount = new StringBuilder();
            Map<String, Object> popMap = (Map)pmConfirmWorkTimeService.selectPgConfirmList(map, totalCount);
            Map<String, Object> retMap = new HashMap<>();
            retMap.put("pg_list", popMap);
            /*Map<String,Object> pageMap = this.createPageMap(Long.parseLong(totalCount.toString()), pPageSize, pCurrPage);
            for (String key: popMap.keySet()) {
                pageMap.put(key, popMap.get(key) );
            }*/
            modelMap.addAttribute("description", "查询小组报工列表, 成功！");
            return setModelMap(modelMap, HttpCode.OK, retMap);
        }catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询小组报工列表, 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "查询设备或人员的加工中任务信息", notes = "查询设备或人员的加工中任务信息")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\":\"\",\n" +
            "\"company_id\":\"\",\n" +
            "\"query\":{\n" +
            "\t\"pg_id\":\"\",\n" +
            "\t\"resource_id\":\"\",\n" +
            "\t\"type\":\"\"\n" +
            "\t},\n" +
            "\"token\":\"\",\n" +
            "\"uid\":\"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/pop/proc_info")
    @RequiresPermissions(value = "enterprise:pm:job_booking:view")
    public Object queryPgProcInfo(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Integer pCompanyId = param.get("company_id") == null || param.get("company_id").toString().isEmpty() ? null : Integer.parseInt(param.get("company_id").toString());
            //Integer pCurrPage = param.get("curr_page") == null || param.get("curr_page").toString().isEmpty() ? null : Integer.parseInt(param.get("curr_page").toString());
            //Integer pPageSize = param.get("page_size") == null || param.get("page_size").toString().isEmpty() ? null : Integer.parseInt(param.get("page_size").toString());
            //if (pCurrPage == null || pCurrPage < 1) pCurrPage = 1;
            //if (pPageSize == null || pPageSize < 1) pPageSize = 10;
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());
            
            Map<String, Object>map = (Map)param.get("query");
            if(map.get("resource_id") == null || map.get("resource_id").toString().isEmpty()) {
                modelMap.addAttribute("description", "资源对象 不能为空， 失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            String type = map.get("type") == null ? "" : map.get("type").toString().trim();
            if (!"0".equals(type) && !"1".equals(type)) {
                modelMap.addAttribute("description", "类型 不正确， 失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            map.put("uid", pUid);
            List<Map<String, Object>> popMapList = pmConfirmWorkTimeService.selectProcInfoByResourceId(map);

            Map<String, Object> retMap = new HashMap<>();
            retMap.put("pop_list", popMapList);
            modelMap.addAttribute("description", "查询设备或人员的加工中任务信息, 成功！");
            return setModelMap(modelMap, HttpCode.OK, retMap);
        }catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询设备或人员的加工中任务信息, 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "查询人员或设备待加工任务列表", notes = "查询人员或设备待加工任务列表")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\":\"\",\n" +
            "\"company_id\":\"\",\n" +
            "\"query\":{\n" +
            "\t\"pg_id\":\"\",\n" +
            "\t\"pop_status\":\"\",\n" +
            "\t\"resource_id\":\"\",\n" +
            "\t\"type\":\"\"\n" +
            "\t},\n" +
            "\"token\":\"\",\n" +
            "\"uid\":\"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/pop/wait_proc_list")
    @RequiresPermissions(value = "enterprise:pm:job_booking:view")
    public Object queryWaitProcList(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Integer pCompanyId = param.get("company_id") == null || param.get("company_id").toString().isEmpty() ? null : Integer.parseInt(param.get("company_id").toString());
            //Integer pCurrPage = param.get("curr_page") == null || param.get("curr_page").toString().isEmpty() ? null : Integer.parseInt(param.get("curr_page").toString());
            //Integer pPageSize = param.get("page_size") == null || param.get("page_size").toString().isEmpty() ? null : Integer.parseInt(param.get("page_size").toString());
            //if (pCurrPage == null || pCurrPage < 1) pCurrPage = 1;
            //if (pPageSize == null || pPageSize < 1) pPageSize = 10;
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());
            
            Map<String, Object>map = (Map)param.get("query");
            if(map.get("resource_id") == null || map.get("resource_id").toString().isEmpty()) {
                modelMap.addAttribute("description", "资源对象 不能为空， 失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            String type = map.get("type") == null ? "" : map.get("type").toString().trim();
            if (!"0".equals(type) && !"1".equals(type)) {
                modelMap.addAttribute("description", "类型 不正确， 失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            map.put("uid", pUid);
            List<Map<String, Object>> popMap = pmConfirmWorkTimeService.selectOneResourceCanProcessPop(map);

            Map<String, Object> retMap = new HashMap<>();
            retMap.put("pop_list", popMap);
            retMap.put("resource_id", map.get("resource_id"));
            retMap.put("type", map.get("type"));
            modelMap.addAttribute("description", "查询人员或设备待加工任务列表, 成功！");
            return setModelMap(modelMap, HttpCode.OK, retMap);
        }catch (IllegalArgumentException ex) {
            modelMap.addAttribute("description", ex.getMessage() + ", 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询人员或设备待加工任务列表, 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "为加工任务指派资源", notes = "为加工任务指派资源")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\t\"client_type\":\"\",\n" +
            "\t\"company_id\":\"\",\n" +
            "\t\"resource_info\":{\n" +
            "\t\t\"device_list\":[\n" +
            "\t\t\"device_id\":\"\"\n" +
            "\t\t\t],\n" +
            "\t\t\"member_list\":[\n" +
            "\t\t\"member_id\":\"\"\n" +
            "\t\t\t],\n" +
            "\t\t\"pop_id\":\"\"\n" +
            "\t},\n" +
            "\t\"token\":\"\",\n" +
            "\t\"uid\":\"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/pop/allocate")
    @RequiresPermissions(value = "enterprise:pm:task_manager:create,update,delete,view")
    public Object popAllocatResource(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Integer pCompanyId = param.get("company_id") == null || param.get("company_id").toString().isEmpty() ? null : Integer.parseInt(param.get("company_id").toString());
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());
            
            Map<String, Object>map = (Map)param.get("resource_info");
            if(map == null || map.size() < 1) {
                modelMap.addAttribute("description", "资源对象 不能为空， 失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            map.put("uid", pUid);
            StringBuilder err = new StringBuilder();
            if (pmConfirmWorkTimeService.createAllocateResourceForPop(map, err) < 0) {
                modelMap.addAttribute("description", err.toString() + ", 失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }

            modelMap.addAttribute("description", "为加工任务指派资源, 成功！");
            return setModelMap(modelMap, HttpCode.OK);
        }catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "为加工任务指派资源, 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "任务领取及取消", notes = "任务领取及取消")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\t\"client_type\":\"\",\n" +
            "\t\"company_id\":\"\",\n" +
            "\t\"incept_info\":{\n" +
            "\t\t\"incept_flag\":\"\"\n" +
            "\t\t\"pop_id\":\"\"\n" +
            "\t},\n" +
            "\t\"token\":\"\",\n" +
            "\t\"uid\":\"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/pop/incept")
    @RequiresPermissions(value = "enterprise:pm:task_manager:create,update,delete,view")
    public Object popIncept(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Integer pCompanyId = param.get("company_id") == null || param.get("company_id").toString().isEmpty() ? null : Integer.parseInt(param.get("company_id").toString());
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());
            
            Map<String, Object>map = (Map)param.get("incept_info");
            if(map == null || map.size() < 1) {
                modelMap.addAttribute("description", "任务对象 不能为空， 失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            map.put("uid", pUid);
            map.put("companyId", pCompanyId);
            StringBuilder err = new StringBuilder();
            if (pmConfirmWorkTimeService.createPopIncept(map, err) < 0) {
                modelMap.addAttribute("description", err.toString() + ", 失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            modelMap.addAttribute("description", "任务领取及取消, 成功！");
            return setModelMap(modelMap, HttpCode.OK);
        } catch (IllegalArgumentException ex) {
            modelMap.addAttribute("description", ex.getMessage() + ", 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "任务领取及取消 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "更改加工中任务的加工人员", notes = "更改加工中任务的加工人员")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\t\"client_type\":\"\",\n" +
            "\t\"company_id\":\"\",\n" +
            "\t\"proc_member\":{\n" +
            "\t\t\"member_id\":\"\",\n" +
            "\t\t\"pop_id\":\"\",\n" +
            "\t\t\"type\":\"\"\n" +
            "\t},\n" +
            "\t\"token\":\"\",\n" +
            "\t\"uid\":\"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/pop/change_worker")
    @RequiresPermissions(value = "enterprise:pm:job_booking:create,update,delete,view")
    public Object popChangeWorker(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            //为 人工加工的且正处于加工中的任务 添加或移除 人员
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Integer pCompanyId = param.get("company_id") == null || param.get("company_id").toString().isEmpty() ? null : Integer.parseInt(param.get("company_id").toString());
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());
            
            Map<String, Object> map = (Map) param.get("proc_member");
            if (map == null || map.size() < 1) {
                modelMap.addAttribute("description", "任务对象 不能为空， 失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            map.put("uid", pUid);
            map.put("companyId", pCompanyId);
            StringBuilder err = new StringBuilder();
            if (pmConfirmWorkTimeService.modifyPopWorker(map, err) < 0) {
                modelMap.addAttribute("description", err.toString() + ", 失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            modelMap.addAttribute("description", "更改加工中任务的加工人员, 成功！");
            return setModelMap(modelMap, HttpCode.OK);
        } catch (IllegalArgumentException ex) {
            modelMap.addAttribute("description", ex.getMessage() + ", 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "更改加工中任务的加工人员 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "更改任务的加工状态（报工)", notes = "更改任务的加工状态（报工)")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\t\"client_type\":\"\",\n" +
            "\t\"company_id\":\"\",\n" +
            "\t\"pop_list\":{\n" +
            "\t\t\"confirm_qty\":\"\",\n" +
            "\t\t\"pop_id\":\"\",\n" +
            "\t\t\"real_other_time\":\"\",\n" +
            "\t\t\"set_status\":\"\"\n" +
            "\t},\n" +
            "\t\"token\":\"\",\n" +
            "\t\"uid\":\"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/pm/pop/change_status")
    @RequiresPermissions(value = "enterprise:pm:job_booking:create,update,delete,view")
    public Object popChangeStatus(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            //为 人工加工的且正处于加工中的任务 添加或移除 人员
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Integer pCompanyId = param.get("company_id") == null || param.get("company_id").toString().isEmpty() ? null : Integer.parseInt(param.get("company_id").toString());
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());
            
            Map<String, Object>map = (Map)param.get("pop_info");
            if(map == null || map.size() < 1) {
                modelMap.addAttribute("description", "任务对象 不能为空， 失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            map.put("uid", pUid);
            map.put("companyId", pCompanyId);
            StringBuilder err = new StringBuilder();
            if (pmConfirmWorkTimeService.updatePopStatus(map, err) < 0) {
                modelMap.addAttribute("description", err.toString() + ", 失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            modelMap.addAttribute("description", "更改任务的加工状态（报工), 成功！");
            return setModelMap(modelMap, HttpCode.OK);
        } catch (IllegalArgumentException ex) {
            modelMap.addAttribute("description", ex.getMessage() + ", 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "更改任务的加工状态（报工) 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }
}

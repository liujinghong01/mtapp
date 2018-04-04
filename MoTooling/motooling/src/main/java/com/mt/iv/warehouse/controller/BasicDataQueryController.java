package com.mt.iv.warehouse.controller;

import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.iv.warehouse.service.BasicDataQueryService;
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

@RestController
@Api(value = "仓库基础数据查询", description = "仓库基础数据查询")
public class BasicDataQueryController extends BaseController{

    @Autowired
    private BasicDataQueryService basicDataQueryService;


    @ApiOperation(value = "E346 查询仓库列表", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/iv/storehouse/list")
    @RequiresPermissions(value = "enterprise:iv:iv_base_data:view")
    public Object selectIvStorehouseList(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap<>();
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("curr_page", param.get("curr_page") == null ? 1: param.get("curr_page"));
            map.put("page_size", param.get("page_size") == null ? 10: param.get("page_size"));
            //1分页，0不分页
            map.put("is_paging", param.get("is_paging") == null ? 0 : param.get("is_paging"));
            Map<String, Object> retrunMap = basicDataQueryService.selectIvStorehouseList(map);
            modelMap.addAttribute("description", "查询仓库列表成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询仓库列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }



    @ApiOperation(value = "E347 查询仓库详细信息", notes = "")
    @ApiImplicitParam(name = "param", value = "{\"query\":{\"store_house_id\":\"1\"},\"token\":\"7b5d546f-620a-4f9b-acda-b1c5a9b29d93\",\"uid\":\"19\"}", required = true, dataType = "Map")
    @PostMapping("/api/iv/storehouse/detail")
    @RequiresPermissions(value = "enterprise:iv:iv_base_data:view")
    public Object selectIvStorehouseDetail(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            if(!param.containsKey("query")){
                modelMap.addAttribute("description", "缺少参数！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            Map<String, Object> query = (Map<String, Object>) param.get("query");
            if(query.get("store_house_id")==null ||query.get("store_house_id")==""){
                modelMap.addAttribute("description", "缺少参数！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("store_house_id", query.get("store_house_id"));
            Map<String, Object> retrunMap = basicDataQueryService.selectIvStorehouseDetail(map);
            modelMap.addAttribute("description", "查询仓库详细信息成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询仓库详细信息失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }



    @ApiOperation(value = "E359 查询入库类型列表", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/iv/in_store_type/list")
    @RequiresPermissions(value = "enterprise:iv:iv_base_data:view")
    public Object selectInStoreTypeList(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap<>();
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            Map<String, Object> retrunMap = basicDataQueryService.selectInStoreTypeList(map);
            modelMap.addAttribute("description", "查询入库类型列表成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询入库类型列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }



    @ApiOperation(value = "E362 查询出库类型列表", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/iv/out_store_type/list")
    @RequiresPermissions(value = "enterprise:iv:iv_base_data:view")
    public Object selectOutStoreTypeList(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap<>();
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            Map<String, Object> retrunMap = basicDataQueryService.selectOutStoreTypeList(map);
            modelMap.addAttribute("description", "查询出库类型列表成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询出库类型列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E361 查询物料类型列表", notes = "")
    @ApiImplicitParam(name = "param", value = "{\"token\":\"caad043d-19dd-4d88-8cc9-936487deaa04\",\"uid\":\"20\",\"curr_page\":1,\"page_size\":2,\"is_paging\":\"1\"}", required = true, dataType = "Map")
    @PostMapping("/api/iv/mat_type/list")
    @RequiresPermissions(value = "enterprise:iv:mat_info:view")
    public Object selectIvMatTypeList(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap<>();
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("curr_page", param.get("curr_page") == null ? 1: param.get("curr_page"));
            map.put("page_size", param.get("page_size") == null ? 10: param.get("page_size"));
            //1分页，0不分页
            map.put("is_paging", param.get("is_paging") == null ? 0 : param.get("is_paging"));
            Map<String, Object> retrunMap = basicDataQueryService.selectIvMatTypeList(map);
            modelMap.addAttribute("description", "查询物料类型列表成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询物料类型列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }





    @ApiOperation(value = "E436 获取库存物料管理类型", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/iv/store_direct_change/list")
    @RequiresPermissions(value = "enterprise:iv:mat_info:view")
    public Object selectIvStoreDirectChange(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap<>();
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            Map<String, Object> retrunMap = basicDataQueryService.selectIvStoreDirectChange(map);
            modelMap.addAttribute("description", "获取库存物料管理类型成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "获取库存物料管理类型失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }



    @ApiOperation(value = "E478 查询某部门及其子部门全部成员列表", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/company/getAllDeptMember")
    @RequiresPermissions(value = "enterprise:enterprise_manager:department:view")
    public Object selectCompanyGetAllDeptMenber(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("query", param.get("query") == null ? null : param.get("query"));
            Map<String, Object> retrunMap = basicDataQueryService.selectCompanyGetAllDeptMenber(map);
            modelMap.addAttribute("description", "查询某部门及其子部门全部成员列表成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询某部门及其子部门全部成员列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }




}

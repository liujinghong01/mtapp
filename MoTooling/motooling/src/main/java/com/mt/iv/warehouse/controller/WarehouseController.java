package com.mt.iv.warehouse.controller;

import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.iv.warehouse.service.WarehouseService;
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
@Api(value = "仓库基础数据维护", description = "仓库基础数据维护")
public class WarehouseController extends BaseController {

    @Autowired
    private WarehouseService warehouseService;

    @ApiOperation(value = "E352 仓库新增修改", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/iv/storehouse/modify")
    @RequiresPermissions(value = "enterprise:iv:iv_base_data:create,update")
    public Object storeHouseModify(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap<>();
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("storeHouseInfo", param.get("store_house_info") == null ? null : param.get("store_house_info"));
            Map<String, Object> retrunMap = warehouseService.addStoreHouse(map);
            modelMap.addAttribute("description", "仓库新增修改成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (IllegalArgumentException ex) {
            modelMap.addAttribute("description", ex.getMessage());
            return setModelMap(modelMap, HttpCode.FAIL);
        }catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "仓库新增修改失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }

    }


    @ApiOperation(value = "E351 仓库删除", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/iv/storehouse/delete")
    @RequiresPermissions(value = "enterprise:iv:iv_base_data:delete")
    public Object updateStoreHouseModify(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap<>();
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("store_house_id", param.get("store_house_id") == null ? null : param.get("store_house_id"));
            Integer integer = warehouseService.updateStoreHouseModify(map);
            if (integer > 0) {
                modelMap.addAttribute("description", "仓库删除成功！");
                return setModelMap(modelMap, HttpCode.OK);
            } else {
                modelMap.addAttribute("description", "参数错误！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "仓库删除失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E476 库位删除", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/iv/storeroom/delete")
    @RequiresPermissions(value = "enterprise:iv:iv_base_data:delete")
    public Object updateStoreRoom(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap<>();
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("store_room_id", param.get("store_room_id") == null ? null : param.get("store_room_id"));
            Integer integer = warehouseService.updateStoreRoom(map);
            if (integer > 0) {
                modelMap.addAttribute("description", "库位删除成功！");
                return setModelMap(modelMap, HttpCode.OK);
            } else {
                modelMap.addAttribute("description", "参数错误！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "库位删除失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }







    @ApiOperation(value = "E358 物料类型新增修改", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/iv/mat_type/modify")
    @RequiresPermissions(value = "enterprise:iv:mat_info:create,update")
    public Object addIvMatTypeModify(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap<>();
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("mat_type_info", param.get("mat_type_info") == null ? null : param.get("mat_type_info"));
            //company_name
            Map<String, Object> retrunMap = warehouseService.addIvMatTypeModify(map);
            modelMap.addAttribute("description", "物料类型新增修改成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "物料类型新增修改失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E357 物料类型删除", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/iv/mat_type/delete")
    @RequiresPermissions(value = "enterprise:iv:mat_info:delete")
    public Object deletIvMatTypeModify(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap<>();
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("mat_type_id", param.get("mat_type_id") == null ? null : param.get("mat_type_id"));
            Boolean b = warehouseService.deleteMatType(map);
            if(b){
                modelMap.addAttribute("description", "物料类型删除成功！");
                return setModelMap(modelMap, HttpCode.OK);
            }else {
                modelMap.addAttribute("description", "删除失败，未删除任何数据");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "物料类型删除失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E3.19新加  物料名称列表", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/bm_mat/all/mat_name")
    public Object selectBmMatAllMatName(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap<>();
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("curr_page", param.get("curr_page") == null ? 1 : param.get("curr_page"));
            map.put("page_size", param.get("page_size") == null ? 10 : param.get("page_size"));
            Map<String, Object> retrunMap = warehouseService.selectBmMatAllMatName(map);
            modelMap.addAttribute("description", "物料名称列表列表请求成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "物料名称列表列表请求失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E921 物料类型详情", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping(" /api/iv/mat/mat_type/modify")
    public Object selectBMatTypeModify(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap<>();
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            Map<String, Object> retrunMap = warehouseService.selectBMatTypeModify(map);
            modelMap.addAttribute("description", "查询物料类型详情成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询物料类型详情失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


}



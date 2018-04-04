package com.mt.iv.warehouse.controller;

import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.iv.warehouse.service.MaintenanceMaterialBaseDataService;
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
@Api(value = "物料基础数据维护", description = "物料基础数据维护")
public class MaintenanceMaterialBaseDataController extends BaseController{

    @Autowired
    private MaintenanceMaterialBaseDataService maintenanceMaterialBaseDataService;

    @ApiOperation(value = "E430 物料信息新增修改", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/iv/mat/modify")
    @RequiresPermissions(value = "enterprise:iv:mat_info:create,update")
    public Object addIvMatModify(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap<>();
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("mat_info", param.get("mat_info") == null ? null : param.get("mat_info"));
            Map<String, Object> retrunMap = maintenanceMaterialBaseDataService.addIvMatModify(map);
            modelMap.addAttribute("description", "物料信息新增修改成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (IllegalArgumentException e) {
            modelMap.addAttribute("description", e.getMessage());
            return setModelMap(modelMap, HttpCode.FAIL);
        }catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "物料信息新增修改失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }

    }

    @ApiOperation(value = "E431 查询物料信息详情", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/iv/select/mat/modify")
    @RequiresPermissions(value = "enterprise:iv:mat_info:view")
    public Object selectMatModify(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap<>();
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("mat_id", param.get("mat_id") == null ? null : param.get("mat_id"));
            Map<String, Object> retrunMap = maintenanceMaterialBaseDataService.selectMatModify(map);
            modelMap.addAttribute("description", "查询物料信息详情成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询物料信息详情失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }

    }





    @ApiOperation(value = "E432 库存物料数据筛选（按仓库分组统计）", notes = "")
    @ApiImplicitParam(name = "param", value = "{\"token\":\"0d8a7610-bf4a-48a1-9051-d609727c75b4\",\"company_id\":\"35\",\"query\":{\"mat_desc\":\"1\",\"mat_id\":\"\",\"mat_model\":\"\",\"mat_no\":\"\"}}", required = true, dataType = "Map")
    @PostMapping("/api/iv/store_house/search_mat")
    @RequiresPermissions(value = "enterprise:iv:mat_info:view")
    public Object selectSearchMat(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap<>();
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("query", param.get("query") == null ? null : param.get("query"));
            Map<String, Object> retrunMap = maintenanceMaterialBaseDataService.selectSearchMat(map);
            modelMap.addAttribute("description", "库存仓库物料数据筛选成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "库存仓库物料数据筛选失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }

    }


    @ApiOperation(value = "E456 库存物料数据筛选（按仓库库位分组统计）", notes = "")
    @ApiImplicitParam(name = "param", value = "{\"token\":\"0d8a7610-bf4a-48a1-9051-d609727c75b4\",\"company_id\":\"35\",\"query\":{\"mat_desc\":\"1\",\"mat_id\":\"\",\"mat_model\":\"\",\"mat_no\":\"\"}}", required = true, dataType = "Map")
    @PostMapping("/api/iv/store_room/search_mat")
    @RequiresPermissions(value = "enterprise:iv:mat_info:view")
    public Object selectRommSearchMat(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap<>();
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("query", param.get("query") == null ? null : param.get("query"));
            Map<String, Object> retrunMap = maintenanceMaterialBaseDataService.selectRommSearchMat(map);
            modelMap.addAttribute("description", "库存库位物料数据筛选成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        }catch (IllegalArgumentException ex) {
            modelMap.addAttribute("description", ex.getMessage());
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "库存库位物料数据筛选失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }

    }




    @ApiOperation(value = "E433 库存物料数据筛选（按全库存统计）", notes = "")
    @ApiImplicitParam(name = "param", value = "{\"token\":\"2d796123-91c4-4648-af79-98a60efe8395\",\"company_id\":\"35\",\"query\":{\"mat_desc\":\"1\",\"mat_id\":\"\",\"mat_model\":\"\",\"mat_no\":\"\"}}", required = true, dataType = "Map")
    @PostMapping("/api/iv/store/search_mat")
    @RequiresPermissions(value = "enterprise:iv:mat_info:view")
    public Object selectStoreSearchMat(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap<>();
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("is_paging", param.get("is_paging") == null ? null : param.get("is_paging"));
            map.put("curr_page", param.get("curr_page") == null ? 1 : param.get("curr_page"));
            map.put("page_size", param.get("page_size") == null ? 10 : param.get("page_size"));
            map.put("query", param.get("query") == null ? null : param.get("query"));
            Map<String, Object> retrunMap = maintenanceMaterialBaseDataService.selectStoreSearchMat(map);
            modelMap.addAttribute("description", "库存全库存物料数据筛选成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "库存全库存物料数据筛选失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }

    }


    @ApiOperation(value = "E914 基础物料数据筛选（按物料）",notes = "")
    @ApiImplicitParam(name = "param",value =
            "{\"token\":\"2d796123-91c4-4648-af79-98a60efe8395\"," +
            "\"company_id\":\"1\"" +
            ",\"query\":" +
                    "{\"mat_desc\":\"\"" +
                    ",\"mat_id\":\"\"" +
                    ",\"spec\":\"\"" +
                    ",\"mat_no\":\"WHA-6-12\"" +
                    ",\"mat_name\":\"\"}}",required =true,dataType = "map")
    @PostMapping("/api/iv/basic_mat/list")
    @RequiresPermissions(value = "enterprise:iv:mat_info:view")
    public  Object selectBasicMatList(@RequestBody Map<String,Object> param,ModelMap modelMap){
        try {

            Map<String, Object> map = new HashMap<>();
            map.put("company_id", param.get("company_id") == null ? null : param.get("company_id"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("is_paging", param.get("is_paging") == null ? null : param.get("is_paging"));
            map.put("curr_page", param.get("curr_page") == null ? 1 : param.get("curr_page"));
            map.put("page_size", param.get("page_size") == null ? 10 : param.get("page_size"));
            map.put("query", param.get("query") == null ? null : param.get("query"));
            Map<String, Object> retrunMap = maintenanceMaterialBaseDataService.selectBasicMatList(map);
            modelMap.addAttribute("description", "基础物料数据筛选成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "基础物料数据筛选失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


}

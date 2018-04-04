package com.mt.bm.bom.controller;

import com.mt.bm.bom.service.RawnatureService;
import com.mt.bm.common.model.Rawnature;
import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
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

@RestController
@Api(value = "物料材质表操作", description = "物料材质表操作")
public class RawnatureController extends BaseController {

    @Autowired
    private RawnatureService rawnatureMatService;



    public Object getRawnature(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            long rawnatureId=Integer.parseInt(param.get("rawnature_id").toString());
            Rawnature rawnature = rawnatureMatService.selectByPrimaryKey(rawnatureId);
            Map<String, Object>dataMap = new HashMap<>();
            dataMap.put("rawnature_info", rawnature);
            modelMap.addAttribute("description", "获取物料材质，成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);

        } catch (Exception ex){
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "获取物料材质，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E002 物料材质列表")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n"+
            "\"company_id\": \"\",\n"+
            "\"query\": {\n" +
            "    \"rawnature_type\":\"\"\n" +
            "    }\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/rawnature/list")
    @RequiresPermissions(value =  "enterprise:bm:mat_no_make:view")
    public Object getRawnatureList(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object>map = (Map)param.get("query");
            String cid=param.get("company_id").toString();
            map.put("companyId",cid);
            List<Rawnature> listRawnature;
            if(cid.equals(""))
            {
                listRawnature=null;
            }
            else {
                listRawnature = rawnatureMatService.selectRawNatureList(map);
            }
            Map<String, Object>dataMap = new HashMap<>();
            dataMap.put("rawnature_list", listRawnature);
            modelMap.addAttribute("description", "获取物料材质，成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);

        } catch (Exception ex){
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "获取物料材质，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E003 计划价格计算")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"company_id\": \"\",\n"+
            "\"uid\": \"\",\n" +
            "\"shape\": \"\",\n" +
            "\"length\": \"\",\n" +
            "\"width\": \"\",\n" +
            "\"height\": \"\",\n" +
            "\"rawnature_id\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/rawnature/price")
    @RequiresPermissions(value =  "enterprise:bm:mat_no_make:view")
    public Object getPlanPrice(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            long rawnatureId=Integer.parseInt(param.get("rawnature_id").toString());
            Rawnature listRawnature = rawnatureMatService.selectByPrimaryKey(rawnatureId);

            Map<String, Object>dataMap = new HashMap<>();
            dataMap.put("planPrice", 100);
            dataMap.put("weight", 100);
            modelMap.addAttribute("description", "计算价格，成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);

        } catch (Exception ex){
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "计算价格，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }
}


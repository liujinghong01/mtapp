package com.mt.bm.bom.controller;

import com.mt.bm.bom.service.MatService;
import com.mt.bm.common.model.Mat;
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
import java.util.Map;

@RestController
@Api(value = "物料表操作", description = "物料表操作")
public class MatController extends BaseController {

    @Autowired
    private MatService matService;

    @ApiOperation(value = "E004 物料基本信息添加")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/Mat/createMat")
    @RequiresPermissions(value =  "enterprise:bm:mat_no_make:create")
    public Object createMat(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Mat mat = new Mat();
            mat=getMatBean(param);
            matService.insert(mat);
            Map<String, Object>dataMap = new HashMap<>();
            dataMap.put("", "");
            modelMap.addAttribute("description", "物料创建，成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);
        } catch (Exception ex) {
            modelMap.addAttribute("description", "物料创建，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    private Mat getMatBean(Map<String, Object> param)
    {
        Mat mat = new Mat();
        //mat.setMatId(param.get("mat_id").toString().trim());
        return mat;
    }

    @ApiOperation(value = "E004 物料基本信息修改")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/Mat/modifMat")
    @RequiresPermissions(value =  "enterprise:bm:mat_no_make:update")
    public Object modifMat(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Mat mat = new Mat();
            mat=getMatBean(param);
            matService.updateByPrimaryKey(mat);
            Map<String, Object>dataMap = new HashMap<>();
            dataMap.put("", "");
            modelMap.addAttribute("description", "物料修改，成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);
        } catch (Exception ex) {
            modelMap.addAttribute("description", "物料修改，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E004 物料基本信息修改")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\",\n" +
            "\"mat_id\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/Mat/selectMat")
    @RequiresPermissions(value =  "enterprise:bm:mat_no_make:update")
    public Object selectMat(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            long matId= Integer.parseInt(param.get("mat_id").toString().trim()) ;
            Mat mat=matService.selectByPrimaryKey(matId);
            Map<String, Object>dataMap = new HashMap<>();
            dataMap.put("Mat", "mat");
            modelMap.addAttribute("description", "物料获取，成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);
        } catch (Exception ex) {
            modelMap.addAttribute("description", "物料获取，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }
}

package com.mt.bm.bom.controller;

import com.mt.bm.bom.service.MatTypeService;
import com.mt.bm.common.model.MatType;
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
@Api(value = "物料类型表操作", description = "物料类型表操作")
public class MatTypeController  extends BaseController {

    @Autowired
    private MatTypeService matTypeService;

    @ApiOperation(value = "E002 物料类型")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/MatType/getMatType")
    @RequiresPermissions(value =  "enterprise:bm:mat_no_make:view")
    public Object getMatTypeList(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            List<MatType> listRawnature;

            listRawnature = matTypeService.selectMatTypeAll();
            Map<String, Object>dataMap = new HashMap<>();
            dataMap.put("listRawnature", listRawnature);
            modelMap.addAttribute("description", "获取物料类型，成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);

        } catch (Exception ex){
            modelMap.addAttribute("description", "获取物料类型，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }
}

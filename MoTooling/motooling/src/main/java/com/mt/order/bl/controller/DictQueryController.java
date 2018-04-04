package com.mt.order.bl.controller;

import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.order.bl.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by fhk on 2017.10.28
 *
 * 字典数据查询 *
 */

@RestController
@Api(value = "字典数据查询", description = "字典数据查询")
public class DictQueryController extends BaseController{

    @Autowired
    private DictService dictService;


    protected Object getDictTypeList(Map<String, Object> param, ModelMap modelMap, String dictType, String dictTypeName) {
        try {
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Integer pCompanyId = param.get("company_id") == null || param.get("company_id").toString().isEmpty() ? null : Integer.parseInt(param.get("company_id").toString());
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());

            List<Map<String, Object>> dictionaryList = dictService.selectDictList(pCompanyId, dictType);
            Map<String, Object> retMap = new HashMap<>();
            retMap.put("list", dictionaryList);
            modelMap.addAttribute("description", dictTypeName + "，成功！");
            return setModelMap(modelMap, HttpCode.OK, retMap);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", dictTypeName + "，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "水口形式列表")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\":\"\",\n" +
            "\"company_id\":\"\",\n" +
            "\"token\":\"\",\n" +
            "\"uid\":\"\",\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/customerOrder/dict/waterTypeList")
    public Object salesmanList(@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            return this.getDictTypeList(param, modelMap,"WATER_TYPE", "水口形式列表");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", "水口形式列表，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "流道形式列表")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\":\"\",\n" +
            "\"company_id\":\"\",\n" +
            "\"token\":\"\",\n" +
            "\"uid\":\"\",\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/customerOrder/dict/runnerTypeList")
    public Object runnerTypeList(@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            return this.getDictTypeList(param, modelMap,"RUNNER_TYPE", "流道形式列表");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", "流道形式列表，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "货币种类列表")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\":\"\",\n" +
            "\"company_id\":\"\",\n" +
            "\"token\":\"\",\n" +
            "\"uid\":\"\",\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/customerOrder/dict/coinTypeList")
    public Object coinTypeList(@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            return this.getDictTypeList(param, modelMap,"COIN_TYPE", "货币种类列表");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", "货币种类列表，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "顶出方式 列表")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\":\"\",\n" +
            "\"company_id\":\"\",\n" +
            "\"token\":\"\",\n" +
            "\"uid\":\"\",\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/customerOrder/dict/outWayList")
    public Object outWayList(@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            return this.getDictTypeList(param, modelMap,"OUT_TYPE", "顶出方式 列表");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", "顶出方式 列表，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "报价单备注模板 列表")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\":\"\",\n" +
            "\"company_id\":\"\",\n" +
            "\"token\":\"\",\n" +
            "\"uid\":\"\",\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/quote/remarkTemplate")
    public Object remarkTemplateList(@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            return this.getDictTypeList(param, modelMap,"QUOTE_REMARK", "报价单备注模板列表");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", "报价单备注模板列表，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "查询发票种类列表")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/customerOrder/dict/invoiceTypeList")
    public Object queryInvoiceTypeList(@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Integer pCompanyId = param.get("company_id") == null || param.get("company_id").toString().isEmpty() ? null : Integer.parseInt(param.get("company_id").toString());
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());

            List<Map<String, Object>> mapList = dictService.selectInvoiceList(pCompanyId);
            Map<String, Object> retMap = new HashMap<>();
            retMap.put("list", mapList);
            modelMap.addAttribute("description", "查询发票种类列表，成功！");
            return setModelMap(modelMap, HttpCode.OK, retMap);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", "查询发票种类列表，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "查询工作状态列表")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/dict/workStateList")
    public Object queryWorkStateList(@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Integer pCompanyId = param.get("company_id") == null || param.get("company_id").toString().isEmpty() ? null : Integer.parseInt(param.get("company_id").toString());
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());

            List<Map<String, Object>> mapList = dictService.selectDictList(pCompanyId, "WORK_STATE");
            Map<String, Object> retMap = new HashMap<>();
            retMap.put("list", mapList);
            modelMap.addAttribute("description", "查询工作状态列表，成功！");
            return setModelMap(modelMap, HttpCode.OK, retMap);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", "查询工作状态列表，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

}

package com.mt.pc.purchase.controller;

import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.StringUtils;
import com.mt.pc.purchase.service.PurchaseDictionariesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Api(value = "采购字典", description = "采购字典")
public class PurchaseDictionariesController extends BaseController {

    @Autowired
    private PurchaseDictionariesService purchaseDictionariesService;

    @ApiOperation(value = "E265 供应商列表", notes = "")
    @ApiImplicitParam(name = "param", value = "{\"company_id\":\"35\",\"query\":{\"sup_name\":\"1\"},\"token\":\"44e75a62-4013-4493-8c5b-5a5b23e695f9\"}", required = true, dataType = "Map")
    @PostMapping("/api/pc/supplier/list")
    public Object selectRequirePlanList(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
           
            String uId = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("companyId", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id").toString().trim());
            map.put("query", StringUtils.isEmpty(param.get("query").toString()) == true ? null : param.get("query"));
            Map<String, Object> retrunMap = purchaseDictionariesService.selectSupplierList(map);
            modelMap.addAttribute("description", "查询供应商列表成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询供应商列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }



    @ApiOperation(value = "E266 发票种类列表", notes = "")
    @ApiImplicitParam(name = "param", value = "{\"company_id\":\"35\",\"token\":\"44e75a62-4013-4493-8c5b-5a5b23e695f9\"}", required = true, dataType = "Map")
    @PostMapping("/api/dict/invoice_type")
    public Object selectInvoiceType(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
           
            String uId = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("companyId", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id").toString().trim());
            Map<String, Object> retrunMap = purchaseDictionariesService.selectInvoiceType(map);
            modelMap.addAttribute("description", "查询发票种类列表成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询发票种类列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E280 物料规格型号列表", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/dict/mat_model")
    public Object selectMatModel(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
           
            String uId = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("companyId", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id").toString().trim());
            Map<String, Object> retrunMap = purchaseDictionariesService.selectMatModel(map);
            modelMap.addAttribute("description", "查询物料规格型号列表成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询物料规格型号列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E263 物料名称搜索", notes = "")
    @ApiImplicitParam(name = "param", value = "{\"company_id\":\"15\",\"token\":\"f7a5f723-b66a-493f-9fba-b89405c5894f\",\"query\":{\"mat_desc\":\"t\"}}", required = true, dataType = "Map")
    @PostMapping("/api/dict/search/matName")
    public Object selectMatRequreFrom(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
           
            String uId = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("companyId", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            map.put("query", StringUtils.isEmpty(param.get("query").toString()) == true ? null : param.get("query"));
            Map<String, Object> retrunMap = purchaseDictionariesService.selectMatName(map);
            modelMap.addAttribute("description", "物料名称搜索成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "物料名称搜索失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }




    @ApiOperation(value = "E272 根据供应商查询物料", notes = "")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"query\":[1,2,3],\n" +
            "\"company_id\":\"35\",\n" +
            "\"token\":\"8df06a14-7d22-43ba-a070-8afdf60a7d04\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/dict/query_mat_by_supplier")
    public Object queryMatBySupplier(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
           
            String uId = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("company_id", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            map.put("query", StringUtils.isEmpty(param.get("query").toString()) == true ? null : param.get("query"));
            Map<String, Object> retrunMap = purchaseDictionariesService.queryMatBySupplier(map);
            modelMap.addAttribute("description", "查询成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            modelMap.addAttribute("description", "查询失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E273 根据物料查询供应商", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/dict/query_supplier_by_mat")
    public Object querySupplierByMat(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
           
            String uId = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("companyId", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id").toString().trim());
            map.put("query", StringUtils.isEmpty(param.get("query").toString()) == true ? null : param.get("query"));
            Map<String, Object> retrunMap = purchaseDictionariesService.querySupplierByMat(map);
            modelMap.addAttribute("description", "查询成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (IllegalArgumentException e){
            logger.error(e.getMessage());
            modelMap.addAttribute("description", "查询失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }catch (Exception ex) {
            logger.error(ex.getMessage());
            modelMap.addAttribute("description", "查询失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }




    @ApiOperation(value = "E281 物料需求来源列表", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/dict/mat_require_from")
    public Object selectRequireFrom(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
           
            String uId = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("companyId", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id").toString().trim());
            map.put("query", StringUtils.isEmpty(param.get("query").toString()) == true ? null : param.get("query"));
            Map<String, Object> retrunMap = purchaseDictionariesService.selectRequireFrom(map);
            modelMap.addAttribute("description", "查询物料需求来源列表成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询物料需求来源列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }
}

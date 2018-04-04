package com.mt.order.bl.controller;


import com.mt.cms.company.service.CustomerService;
import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.StringUtils;
import com.mt.order.bl.service.CostItemListService;
import com.mt.order.bl.service.GetUnitNameService;
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
@Api(value = "核算单下拉框数据", description = "核算单下拉框数据")
public class CostItemListController extends BaseController{
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CostItemListService costItemListService;

    @Autowired
    private GetUnitNameService getUnitNameService;


    @ApiOperation(value = "E144 加工费项目单位列表", notes = "")
    @ApiImplicitParam(name = "param", value = "{ ", required = true, dataType = "Map")
    @PostMapping("api/customerOrder/dict/processCostItemUnitList")
    public Object processCostItemUnitList(@RequestBody Map<String, Object> param, ModelMap modelMap){
        try {
            
            String uId = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Integer companyId = param.get("company_id") == null ? null : Integer.valueOf(param.get("company_id").toString());
            Map<String, Object> retrunMap = costItemListService.processCostItemUnitList(companyId);
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询核算单列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E143 加工费项目列表", notes = "")
    @ApiImplicitParam(name = "param", value = "{ ", required = true, dataType = "Map")
    @PostMapping("api/customerOrder/dict/processCostItemList")
    public Object processCostItemList(@RequestBody Map<String, Object> param, ModelMap modelMap){
        try {
            
            String uId = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Integer companyId = param.get("company_id") == null ? null : Integer.valueOf(param.get("company_id").toString());
            Map<String, Object> retrunMap = costItemListService.processCostItemList(companyId);
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询加工费项目列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }



    @ApiOperation(value = "E150 客户列表", notes = "")
    @ApiImplicitParam(name = "param", value = "{ ", required = true, dataType = "Map")
    @PostMapping("api/customerOrder/dict/customerList")
    public Object customerList(@RequestBody Map<String, Object> param, ModelMap modelMap){
        try {
            
            Map<String, Object> map = new HashMap();
            map.put("companyId", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            map.put("cstType", StringUtils.isEmpty(param.get("cst_type").toString()) == true ? null : param.get("cst_type"));
            Map<String, Object> retrunMap = customerService.customerList(map);
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询客户列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E142 材料列表", notes = "")
    @ApiImplicitParam(name = "param", value = "{ ", required = true, dataType = "Map")
    @PostMapping("api/customerOrder/dict/rawNatureList")
    public Object rawNatureList(@RequestBody Map<String, Object> param, ModelMap modelMap){
        try {
            
            Map<String, Object> map = new HashMap();
            map.put("companyId", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            Map<String, Object> retrunMap = costItemListService.rawNatureList(map);
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询核材料列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }



    @ApiOperation(value = "E140 缺省子部件列表", notes = "")
    @ApiImplicitParam(name = "param", value = "{ ", required = true, dataType = "Map")
    @PostMapping("api/customerOrder/dict/defaultSubPartList")
    public Object defaultSubPartList(@RequestBody Map<String, Object> param, ModelMap modelMap){
        try {
            
            Map<String, Object> map = new HashMap();
            map.put("companyId", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            map.put("partName", StringUtils.isEmpty(param.get("part_name").toString()) == true ? null : param.get("part_name"));
            Map<String, Object> retrunMap = costItemListService.defaultSubPartList(map);
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            modelMap.addAttribute("description", "查询核材料列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E139 缺省部件列表", notes = "")
    @ApiImplicitParam(name = "param", value = "{ ", required = true, dataType = "Map")
    @PostMapping("api/customerOrder/dict/defaultPartList")
    public Object defaultPartList(@RequestBody Map<String, Object> param, ModelMap modelMap){
        try {
            
            Map<String, Object> retrunMap = costItemListService.defaultPartList();
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            modelMap.addAttribute("description", "查询核材料列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "", notes = "")
    @ApiImplicitParam(name = "param", value = "{\"param\":\"user\",\"pass\":\"user}")
    @PostMapping("/get/unit/name")
    public Object getUnitName(@RequestBody Map<String, String> param){
        String user = param.get("param");
        String pass = param.get("pass");
        if(user.equals("user")&& pass.equals("user")){
            getUnitNameService.saveRedis();
            return "0000";
        }
        return "错误";
    }




}

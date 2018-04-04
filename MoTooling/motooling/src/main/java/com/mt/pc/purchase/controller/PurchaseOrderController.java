package com.mt.pc.purchase.controller;

import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.StringUtils;
import com.mt.pc.purchase.service.PurchaseOrderService;
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
@Api(value = "采购订单", description = "采购订单")
public class PurchaseOrderController extends BaseController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;


    @ApiOperation(value = "E258 查询采购订单列表", notes = "")
    @ApiImplicitParam(name = "param", value = "{\"token\":\" \",\"company_id\":\"35\",\"uid\":\"20\",\"query\":{\"approve_type\":\"0\",\"approve_step\":[\"step10\"]}}", required = true, dataType = "Map")
    @PostMapping("/api/pc/purch_order/list")
    @RequiresPermissions(value = "enterprise:pc:purch_order:view")
    public Object selectPucchOrderList(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            String uId = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("curr_page", param.get("curr_page") == null ? 1: param.get("curr_page"));
            map.put("page_size", param.get("page_size") == null ? 10: param.get("page_size"));
            map.put("query", param.get("query") == null ? null: param.get("query"));
            map.put("companyId", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            Map<String, Object> retrunMap = purchaseOrderService.selectPucchOrderList(map);
            modelMap.addAttribute("description", "查询采购订单列表成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询采购订单列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E279 查询采购订单详情", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/pc/purch_order/detail")
    @RequiresPermissions(value = "enterprise:pc:purch_order:view")
    public Object selectPurchOrderDetail(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            String uId = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("query", StringUtils.isEmpty(param.get("query").toString()) == true ? null : param.get("query"));
            Map<String, Object> retrunMap = purchaseOrderService.selectPurchOrderDetail(map);
            modelMap.addAttribute("description", "查询采购订单详情成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询采购订单详情失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E259 删除采购单", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/pc/purch_order/delete")
    @RequiresPermissions(value = "enterprise:pc:purch_order:delete")
    public Object deletePurchOrder(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            String uId = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Integer purchId = param.get("purch_id") == null && param.get("purch_id") =="" ? null : Integer.valueOf(param.get("purch_id").toString());
            Boolean b = purchaseOrderService.deletePurchOrder(purchId);
            modelMap.addAttribute("description", "删除采购单成功！");
            return setModelMap(modelMap, HttpCode.OK);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "删除采购单失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E260 新增修改采购单", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/pc/purch_order/modify")
    @RequiresPermissions(value = "enterprise:pc:purch_order:create,update")
    public Object addPurchOrder(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap();
            map.put("purch_info", StringUtils.isEmpty(param.get("purch_info").toString()) == true ? null : param.get("purch_info"));
            map.put("company_id", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            map.put("uid", StringUtils.isEmpty(param.get("uid").toString()) == true ? null : param.get("uid"));
            Map<String, Object> retrunMap = purchaseOrderService.addPurchOreder(map);
            modelMap.addAttribute("description", "新增修改采购单成功！");
            return setModelMap(modelMap, HttpCode.OK,retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "新增修改采购单失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E261 发送采购单", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/pc/purch_order/send")
    @RequiresPermissions(value = "enterprise:pc:purch_order:view")
    public Object sendPurchOrder(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            Long purchId = param.get("purch_id") == null || param.get("purch_id").toString().isEmpty() ? null : Long.valueOf(param.get("purch_id").toString());
            Long uid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.valueOf(param.get("uid").toString());

            purchaseOrderService.sendPurchOrder(purchId, uid);
            modelMap.addAttribute("description", "发送采购单 成功！");
            return setModelMap(modelMap, HttpCode.OK);
        } catch (IllegalArgumentException ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", ex.getMessage() + " 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "发送采购单 失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

}

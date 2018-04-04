package com.mt.pc.purchase.controller;

import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.StringUtils;
import com.mt.pc.purchase.service.DeliveryNoteService;
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
@Api(value = "送货单", description = "送货单")
public class DeliveryNoteController extends BaseController {


    @Autowired
    private DeliveryNoteService deliveryNoteService;

    @ApiOperation(value = "E413 查询销售定单列表（客户采购定单）", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("api/pc/salesOrder/list")
    @RequiresPermissions(value = "enterprise:pc:deliver_order:view")
    public Object salesOrderList(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            String uId = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("curr_page", param.get("curr_page") == null ? 1 : param.get("curr_page"));
            map.put("page_size", param.get("page_size") == null ? 10 : param.get("page_size"));
            map.put("companyId", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id").toString().trim());
            map.put("query", StringUtils.isEmpty(param.get("query").toString()) == true ? null : param.get("query"));
            Map<String, Object> retrunMap = deliveryNoteService.salesOrderList(map);
            modelMap.addAttribute("description", "查询销售定单列表成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询销售定单列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E412 生成送货单", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("api/salesOrder/createDeliveryOrder")
    @RequiresPermissions(value = "enterprise:pc:deliver_order:create")
    public Object createDeliveryOrder(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Map<String, Object> map = new HashMap();
            map.put("company_id", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            map.put("uid", StringUtils.isEmpty(param.get("uid").toString()) == true ? null : param.get("uid"));
            map.put("deliveryInfo", StringUtils.isEmpty(param.get("delivery_info").toString()) == true ? null : param.get("delivery_info"));
            Map<String, Object> retrunMap = deliveryNoteService.createDeliveryOrder(map);
            modelMap.addAttribute("description", "生成送货单成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "生成送货单失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

}

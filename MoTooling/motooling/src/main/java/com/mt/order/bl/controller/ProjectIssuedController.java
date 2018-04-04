package com.mt.order.bl.controller;

import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.StringUtils;
import com.mt.order.bl.service.ProjectNotHwService;
import com.mt.order.bl.service.ProjectService;
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
@Api(value = "客户订单下达", description = "客户订单下达")
public class ProjectIssuedController extends BaseController{

    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectNotHwService projectNotHwService;

    @ApiOperation(value = "E192 查询客户定单下达列表", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/cst_order/order_startup_list")
    @RequiresPermissions(value = "enterprise:sm:order_operate:view")
    public Object orderStartupList(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            
            Map<String, Object> map = new HashMap();
            map.put("company_id", StringUtils.isEmpty(param.get("company_id")+"") == true ? null : param.get("company_id"));
            map.put("curr_page", param.get("curr_page") == null ? 1 : param.get("curr_page"));
            map.put("page_size", param.get("page_size") == null ? 10 : param.get("page_size"));
            map.put("query", param.get("query") == null ? null : param.get("query"));
            Map<String, Object> retrunMap = projectService.orderStartupList(map);
            modelMap.addAttribute("description", "查询客户定单下达列表成功！");
            return setModelMap(modelMap, HttpCode.OK,retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询客户定单下达列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E196 设置客户定单产品开始", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/cst_order/order_restartup")
    @RequiresPermissions(value = "enterprise:sm:order_operate:update")
    public Object orderStartup(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            
            Integer orderLineId = param.get("order_line_id") == null ? null : Integer.valueOf(param.get("order_line_id").toString());
            boolean b = projectNotHwService.updateOrderStartup(orderLineId);
            modelMap.addAttribute("description", "设置客户定单产品开始成功！");
            return setModelMap(modelMap, HttpCode.OK);
            }catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "设置客户定单产品开始失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E194 设置客户定单产品反完结", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/cst_order/order_not_end")
    @RequiresPermissions(value = "enterprise:sm:order_operate:update")
    public Object orderNotEnd(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            
            Integer orderLineId = param.get("order_line_id") == null ? null : Integer.valueOf(param.get("order_line_id").toString());
            boolean b = projectNotHwService.updateOrderNotEnd(orderLineId);
            if(b){
                modelMap.addAttribute("description", "设置客户定单产品反完结成功！");
                return setModelMap(modelMap, HttpCode.OK);
            }else {
                modelMap.addAttribute("description", "参数错误");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
        }catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "设置客户定单产品反完结失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E196 设置客户定单产品暂停或者完结", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/cst_order/order_startup_stop")
    @RequiresPermissions(value = "enterprise:sm:order_operate:update")
    public Object orderStartupStop(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            
            boolean b = projectNotHwService.updateOrderStartupStop(param);
            modelMap.addAttribute("description", "设置客户定单产品暂停或者完结成功！");
            return setModelMap(modelMap, HttpCode.OK);
        }catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "设置客户定单产品暂停或者完结失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }



    @ApiOperation(value = "E197 参考历史定单生成新定单", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("api/customerOrder/order/associate")
    @RequiresPermissions(value = "enterprise:sm:order_operate:create")
    public Object selectProject(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            
            Map<String, Object> map = new HashMap();
            map.put("company_id", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            // 历史定单
            map.put("project_id", param.get("project_id") == null ? null : param.get("project_id"));
            // 当前定单
            map.put("current_project_id", param.get("current_project_id") == null ? null : param.get("current_project_id"));
            Map<String, Object> mapReturn = projectService.addSelectProject(map);
            modelMap.addAttribute("description", "参考历史定单生成新定单成功！");
            return setModelMap(modelMap, HttpCode.OK, mapReturn);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "参考历史定单生成新定单失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }
}

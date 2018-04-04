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
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "客户订单", description = "客户订单")
public class ProjectController extends BaseController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectNotHwService projectNotHwService;


    @ApiOperation(value = "E134 查询客户定单列表", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/cst_order/list")
    @RequiresPermissions(value = "enterprise:sm:cst_order:view")
    public Object selectOrderList(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            
            String uId = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Map<String, Object> retrunMap = projectService.selectProjectList(param);
            modelMap.addAttribute("description", "查询客户定单列表成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询客户定单列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E137 删除客户定单", notes = "")
    @ApiImplicitParam(name = "param", value = "{", required = true, dataType = "Map")
    @PostMapping("/api/cst_order/delete")
    @RequiresPermissions(value = "enterprise:sm:cst_order:delete")
    public Object deleteProjectNo(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            
            String uId = param.get("uid") == null ? null : param.get("uid").toString().trim();
            boolean b = projectService.deleteProjectNo(param);
            if (b) {
                modelMap.addAttribute("description", "删除客户定单成功！");
                return setModelMap(modelMap, HttpCode.OK);
            } else {
                modelMap.addAttribute("description", "失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "删除客户定单失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E135 关联报价单生成客户定单", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/cst_order/auto_from_quote")
    @RequiresPermissions(value = "enterprise:sm:cst_order:create")
    public Object CstOrderAuto(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            
           Map<String,Object> map = new HashMap<>();
           map.put("quote_id",param.get("quote_id") == null ? null : Integer.valueOf(param.get("quote_id").toString()));
           map.put("project_id",param.get("project_id") == null ? null : Integer.valueOf(param.get("project_id").toString()));
           map.put("uid",param.get("uid") == null ? null : Integer.valueOf(param.get("uid").toString()));
           map.put("compnany_id",param.get("compnany_id") == null ? null : Integer.valueOf(param.get("compnany_id").toString()));
            Map<String, Object> retrunMap = projectService.addCstOrderAuto(map);
            modelMap.addAttribute("description", "关联报价单生成客户定单成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "关联报价单生成客户定单失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E136 客户定单头信息新增修改", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/cst_order/modify")
    @RequiresPermissions(value = "enterprise:sm:cst_order:create,update")
    public Object updateAndAddModify(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            
            Map<String, Object> map = null;
            if (param.containsKey("cst_order_head")) {
                map = (Map<String, Object>) param.get("cst_order_head");
            }
            if (StringUtils.isEmpty((String) map.get("cst_name"))) {
                modelMap.addAttribute("description", "请填写，客户名称！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            if (StringUtils.isEmpty((String) map.get("order_accept_date"))) {
                modelMap.addAttribute("description", "请填写，定单接收日期！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            if (StringUtils.isEmpty((map.get("coin").toString()))) {
                modelMap.addAttribute("description", "请填写，币种！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            if (StringUtils.isEmpty((map.get("salesman")).toString())) {
                modelMap.addAttribute("description", "请填写，业务员！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            Map<String, Object> returnMap = projectService.updateAndAddModify(param);
            modelMap.addAttribute("description", "客户定单头信息新增修改成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "客户定单头信息新增修改失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E164 查询定单行信息列表", notes = "")
    @ApiImplicitParam(name = "param", value = "{", required = true, dataType = "Map")
    @PostMapping("/api/cst_order/query_not_hw_line_list")
    @RequiresPermissions(value = "enterprise:sm:cst_order:view")
    public Object queryNotHwLineList(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            
            Map<String, Object> retrunMap = projectNotHwService.queryNotHwLineList(param);
            modelMap.addAttribute("description", "查询定单行信息列表成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询定单行信息列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E174 查询定单行信息明细", notes = "")
    @ApiImplicitParam(name = "param", value = "{", required = true, dataType = "Map")
    @PostMapping("/api/cst_order/query_not_hw_line_detail")
    @RequiresPermissions(value = "enterprise:sm:cst_order:view")
    public Object queryNotHwLineDetail(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            

            Integer order_line_id = param.get("order_line_id") == null ? null : Integer.valueOf(param.get("order_line_id").toString());
            Map<String, Object> retrunMap = projectNotHwService.queryNotHwLineDetail(order_line_id);
            modelMap.addAttribute("description", "查询定单行信息明细成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询定单行信息明细失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E179 客户定单行信息的新增修改", notes = "")
    @ApiImplicitParam(name = "param", value = "{", required = true, dataType = "Map")
    @PostMapping("/api/cst_order/modifyLine")
    @RequiresPermissions(value = "enterprise:sm:cst_order:create,update")
    public Object modifyLine(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            
            Map<String, Object> cstMap = (Map<String, Object>) param.get("cst_order_line");
      /*    todo  先注释 是否需要验证？
            if (StringUtils.isEmpty((String) cstMap.get("cst_mold_no"))) {
                modelMap.addAttribute("description", "请填写，客户模号！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }*/
            if (StringUtils.isEmpty((String) cstMap.get("first_try_date"))) {
                modelMap.addAttribute("description", "请填写，首次试模日期！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            if (StringUtils.isEmpty((String) cstMap.get("start_date"))) {
                modelMap.addAttribute("description", "请填写，正式开模日期！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            if (!cstMap.containsKey("prod_list")) {
                modelMap.addAttribute("description", "请填写，制品信息！");
                return setModelMap(modelMap, HttpCode.FAIL);
            } else {
                List<Map<String, Object>> listMap = (List<Map<String, Object>>) cstMap.get("prod_list");
                if (listMap.size() < 1) {
                    modelMap.addAttribute("description", "请填写，制品信息！");
                    return setModelMap(modelMap, HttpCode.FAIL);
                }
            }
            Map<String, Object> map = new HashMap<>();
            Map<String, Object> retrunMap = projectNotHwService.addAndUpdatemodifyLine(param);
            map.put("project_id", retrunMap.get("project_id"));
            retrunMap.remove("project_id");
            map.put("cst_order_line", retrunMap);
            modelMap.addAttribute("description", "客户定单行信息的新增修改成功！");
            return setModelMap(modelMap, HttpCode.OK, map);
        }catch (IllegalArgumentException ex){
            modelMap.addAttribute("description", ex.getMessage());
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "客户定单行信息的新增修改失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E183 删除客户定单制品信息", notes = "")
    @ApiImplicitParam(name = "param", value = "{", required = true, dataType = "Map")
    @PostMapping("/api/cst_line/deleteProd")
    @RequiresPermissions(value = "enterprise:sm:cst_order:delete")
    public Object deleteProd(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            
            String uId = param.get("uid") == null ? null : param.get("uid").toString().trim();
            boolean b = projectNotHwService.deleteProd(param);
            if (b) {
                modelMap.addAttribute("description", "删除客户定单制品信息成功！");
                return setModelMap(modelMap, HttpCode.OK);
            } else {
                modelMap.addAttribute("description", "参数错误失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "删除客户定单制品信息失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E182 删除客户定单行信息", notes = "")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/cst_order/deleteLine")
    @RequiresPermissions(value = "enterprise:sm:cst_order:delete")
    public Object deleteLine(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            
            String uId = param.get("uid") == null ? null : param.get("uid").toString().trim();
            boolean b = projectNotHwService.deleteLine(param);
            if (b) {
                modelMap.addAttribute("description", "删除客户定单行信息成功！");
                return setModelMap(modelMap, HttpCode.OK);
            } else {
                modelMap.addAttribute("description", "参数错误失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "删除客户定单行信息失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E176 查询客户定单头信息", notes = "")
    @ApiImplicitParam(name = "param", value = "{", required = true, dataType = "Map")
    @PostMapping("/api/cst_order/query_head")
    @RequiresPermissions(value = "enterprise:sm:cst_order:view")
    public Object queryHead(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            
            Integer projectId = param.get("project_id") == null ? null : Integer.valueOf(param.get("project_id").toString());
            Map<String, Object> retrunMap = projectService.queryHead(projectId);
            modelMap.addAttribute("description", "查询客户定单头信息成功！");
            return setModelMap(modelMap, HttpCode.OK, retrunMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询客户定单头信息失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }
}

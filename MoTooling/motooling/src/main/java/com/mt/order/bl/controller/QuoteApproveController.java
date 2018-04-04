package com.mt.order.bl.controller;

import com.mt.common.bo.BeanToMapUtil;
import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.common.customenum.RoleEnum;
import com.mt.order.bl.service.ApproveService;
import com.mt.order.bl.service.QuoteService;
import com.mt.order.common.model.ApproveRecordHis;
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

/**
* @Author: Fhk
* @Description: 报价单审批
* @create    2017.10.24
* @Modified By:
*/
@RestController
@Api(value = "审批管理", description = "审批管理")
public class QuoteApproveController extends BaseController {

    @Autowired
    private QuoteService quoteService;

    @Autowired
    private ApproveService approveService;


    @ApiOperation(value = "提交审批")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"approve_flag\":\"\",\n" +
            "\"approve_man_id\":\"\",\n" +
            "\"approve_man_name\":\"\",\n" +
            "\"approve_sugg\":\"\",\n" +
            "\"bill_id\":\"\",\n" +
            "\"bill_type\":\"\",\n" +
            "\"client_type\":\"\",\n" +
            "\"token\":\"\",\n" +
            "\"uid\":\"\"\n" +
            "}\n", required = true, dataType = "Map")
    @PostMapping("/api/workflow/process")
    ////@RequiresRoles(value={"enterprise_admin","department_leader"},logical= Logical.OR)
    public Object approve(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Long pBillId = param.get("bill_id") == null || param.get("bill_id").toString().isEmpty() ? null : Long.parseLong(param.get("bill_id").toString());
            String pBillType = param.get("bill_type") == null ? null : param.get("bill_type").toString().trim();
            String pApproveStep = param.get("approve_step") == null ? null : param.get("approve_step").toString().trim();
            String pApproveFlag = param.get("approve_flag") == null ? null : param.get("approve_flag").toString().trim();
            String pApproveSugg = param.get("approve_sugg") == null ? null : param.get("approve_sugg").toString().trim();
            //Long pApproveManId = param.get("approve_man_id") == null || param.get("approve_man_id").toString().isEmpty() ? null : Long.parseLong(param.get("approve_man_id").toString());
            String pApproveManName = param.get("approve_man_name") == null ? null : param.get("approve_man_name").toString().trim();
            //审批人就是
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());
            Integer pCompanyId = param.get("company_id") == null || param.get("company_id").toString().isEmpty() ? null : Integer.valueOf(param.get("company_id").toString());

            //如果每种单据的审批角色是不一样的，则要把这段加入approveService.createApprove中去，根据不同单据区分不同角色
            if (!"step10".equals(pApproveStep) && !"step11".equals(pApproveStep) ) {
                if (!approveService.isHasPermission(pCompanyId, pUid, RoleEnum.DEPARTMENT_LEADER) && !approveService.isHasPermission(pCompanyId, pUid, RoleEnum.ENTERPRISE_ADMIN)) {
                    modelMap.addAttribute("description", "权限不足，如需要操作、请您联系管理人员,失败！");
                    return setModelMap(modelMap, HttpCode.FAIL);
                }
            }
            StringBuilder err = new StringBuilder();
            if (pApproveManName == null || pApproveManName.isEmpty()) { pApproveManName = approveService.getWorkNameByUid(pCompanyId, pUid); }
            Map<String, Object> retMap = approveService.createApprove(pBillId, pBillType, pApproveStep, pApproveFlag, pApproveSugg, pUid, pApproveManName, err);
            if (retMap == null) {
                modelMap.addAttribute("description", err.toString());
                return setModelMap(modelMap, HttpCode.FAIL);
            } else {
                err = new StringBuilder();
                if (!"step10".equals(pApproveStep) && !"step11".equals(pApproveStep) && "step30".equals(retMap.get("approve_step"))) {
                    retMap = approveService.createApprove(pBillId, pBillType, retMap.get("approve_step").toString(), pApproveFlag, pApproveSugg, pUid, pApproveManName, err);
                    if (retMap == null) {
                        modelMap.addAttribute("description", err.toString());
                        return setModelMap(modelMap, HttpCode.FAIL);
                    }
                }
            }
            modelMap.addAttribute("description", "提交审批,成功！");
            return setModelMap(modelMap, HttpCode.OK, retMap);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", "提交审批,失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "查看审批意见")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"bill_id\":\"\",\n" +
            "\"bill_type\":\"\",\n" +
            "\"client_type\":\"\",\n" +
            "\"token\":\"\",\n" +
            "\"uid\":\"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/workflow/show_opinion")
    public Object queryApprove(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Long pBillId = param.get("bill_id") == null || param.get("bill_id").toString().isEmpty() ? null : Long.parseLong(param.get("bill_id").toString());
            String pBillType = param.get("bill_type") == null ? null : param.get("bill_type").toString().trim();
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());

            List<ApproveRecordHis> approveRecordHisList = approveService.selectApproveRecordsByApproveId(pBillId, pBillType);
            List<Map<String, Object>> mapList = this.camelToUnderline(BeanToMapUtil.convertBeanList(approveRecordHisList));
            String approveStatus = "";
            if (mapList != null) {
                for (Map<String, Object> map : mapList) {
                    approveStatus = "";
                    if ("step20".equals(map.get("approve_step"))) {
                        approveStatus += "初审";
                    }
                    if ("step30".equals(map.get("approve_step"))) {
                        if ("5".equals(pBillType)) {
                            approveStatus += "单价审批";
                        } else if ("6".equals(pBillType)) {
                            approveStatus += "一级审批";
                        } else if ("7".equals(pBillType)) {
                            approveStatus += "二级审批";
                        } else {
                            approveStatus += "复审";
                        }
                    }
                    if ("1".equals(map.get("approve_flag"))) {
                        approveStatus += "通过";
                    }
                    if ("2".equals(map.get("approve_flag"))) {
                        approveStatus += "不通过";
                    }
                    map.put("approve_status", approveStatus);
                }
            }
            Map<String, Object> retMap = new HashMap<>();
            retMap.put("process_list", mapList);
            modelMap.addAttribute("description", "查看审批意见，成功！");
            return setModelMap(modelMap, HttpCode.OK, retMap);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", "查看审批意见，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "设置客户定单产品下达")
    @ApiImplicitParam(name = "param", value = "{\"client_type\":\"\",\n" +
            "\"order_startup\":{\n" +
            "    \"count\":\"\",\n" +
            "    \"cst_mold_no\":\"\",\n" +
            "    \"design_cost\":\"\",\n" +
            "    \"design_ratio\":\"\",\n" +
            "    \"is_batch_process\":\"\",\n" +
            "    \"is_crt_bom\":\"\",\n" +
            "    \"order_line_id\":\"\",\n" +
            "    \"process_cost\":\"\",\n" +
            "    \"process_ratio\":\"\",\n" +
            "    \"stuff_cost\":\"\",\n" +
            "    \"stuff_ratio\":\"\",\n" +
            "    \"sup_type\":\"\",\n" +
            "    \"up_not_tax\":\"\" },\n" +
            "\"token\":\"\",\n" +
            "\"uid\":\"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/cst_order/order_startup")
    @RequiresPermissions(value = "enterprise:sm:order_operate:create,update")
    public Object orderStartup(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();

            Map<String, Object> orderStartupMap = param.get("order_startup") == null ? null : (Map<String, Object>) param.get("order_startup");

            String supType = orderStartupMap.get("sup_type") == null || orderStartupMap.get("sup_type").toString().trim().isEmpty() ? null : orderStartupMap.get("sup_type").toString().trim();
            boolean supTypeIsNotValid = supType == null || !"1".equals(supType) && !"2".equals(supType);
            if (supTypeIsNotValid) {
                modelMap.addAttribute("description", "供货类型：输入数据无效，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            if ("1".equals(supType) && !"1".equals(orderStartupMap.get("is_crt_bom"))) {
                modelMap.addAttribute("description", "自制一定要生成BOM，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            String decNum = orderStartupMap.get("count") == null || orderStartupMap.get("count").toString().trim().isEmpty() ? null : orderStartupMap.get("count").toString();
            if (decNum == null || Integer.parseInt(decNum) < 1) {
                modelMap.addAttribute("description", "数量：输入数据无效，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            decNum = orderStartupMap.get("design_cost") == null || orderStartupMap.get("design_cost").toString().trim().isEmpty() ? null : orderStartupMap.get("design_cost").toString();
            if (decNum == null) {
                /*modelMap.addAttribute("description", "设计费：输入数据无效，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);*/
                orderStartupMap.put("design_cost", 0);
            }
            decNum = orderStartupMap.get("design_ratio") == null || orderStartupMap.get("design_ratio").toString().trim().isEmpty() ? null : orderStartupMap.get("design_ratio").toString();
            if (decNum == null) {
               /* modelMap.addAttribute("description", "设计费率：输入数据无效，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);*/
                orderStartupMap.put("design_ratio", 0);
            }
            decNum = orderStartupMap.get("process_cost") == null || orderStartupMap.get("process_cost").toString().trim().isEmpty() ? null : orderStartupMap.get("process_cost").toString();
            if (decNum == null) {
                /*modelMap.addAttribute("description", "加工费：输入数据无效，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);*/
                orderStartupMap.put("process_cost", 0);
            }
            decNum = orderStartupMap.get("process_ratio") == null || orderStartupMap.get("process_ratio").toString().trim().isEmpty() ? null : orderStartupMap.get("process_ratio").toString();
            if ("".equals(decNum)) {
                /*modelMap.addAttribute("description", "加工费率：输入数据无效，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);*/
                orderStartupMap.put("process_ratio", 0);
            }
            decNum = orderStartupMap.get("stuff_cost") == null || orderStartupMap.get("stuff_cost").toString().trim().isEmpty() ? null : orderStartupMap.get("stuff_cost").toString();
            if (decNum == null) {
                /*modelMap.addAttribute("description", "材料费：输入数据无效，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);*/
                orderStartupMap.put("stuff_cost", 0);
            }
            decNum = orderStartupMap.get("stuff_ratio") == null || orderStartupMap.get("stuff_ratio").toString().trim().isEmpty() ? null : orderStartupMap.get("stuff_ratio").toString();
            if (decNum == null) {
                /*modelMap.addAttribute("description", "材料费率：输入数据无效，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);*/
                orderStartupMap.put("stuff_ratio", 0);
            }
            orderStartupMap.put("uid", param.get("uid"));
            List<Map<String, Object>> moldNoList = quoteService.createOrderStartup(orderStartupMap);
            if (moldNoList == null) {
                modelMap.addAttribute("description", "该产品已经下达，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            Map<String, Object> retMap = new HashMap<>();
            retMap.put("mold_no_list", moldNoList);
            retMap.put("order_line_id", orderStartupMap.get("order_line_id"));
            modelMap.addAttribute("description", "设置客户定单产品下达，成功！");
            return setModelMap(modelMap, HttpCode.OK, retMap);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", "设置客户定单产品下达，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


}

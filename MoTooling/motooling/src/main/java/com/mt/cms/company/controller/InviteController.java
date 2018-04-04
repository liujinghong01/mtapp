package com.mt.cms.company.controller;

/**
* @Author: Fhk
* @Description:
* @create    2017/10/24 17:44
* @Modified By:
*/

import com.mt.cms.common.model.SupplierWare;
import com.mt.cms.company.service.CompanyService;
import com.mt.cms.company.service.CustomerService;
import com.mt.cms.company.service.SupplierService;
import com.mt.common.bo.BeanToMapUtil;
import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.upms.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(value = "邀请接口", description = "邀请接口")
public class InviteController  extends BaseController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private CustomerService customerService;

    @ApiOperation(value = "E018 邀请公司内部人员")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"clientType\": \"\",\n" +
            "\"departmentId\": \"\",\n" +
            "\"departmentName\": \"\",\n" +
            "\"gender\": \"\",\n" +
            "\"inviteeUserName\": \"\",\n" +
            "\"inviterCompanyId\": \"\",\n" +
            "\"inviterCompanyName\": \"\",\n" +
            "\"inviterUserId\": \"\",\n" +
            "\"inviterUsername\": \"\",\n" +
            "\"phoneNum\": \"\",\n" +
            "\"roleId\": \"\",\n" +
            "\"roleName\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/invite/member")
    public Object inviteMember(@ApiParam(required = true, value = "邀请公司内部人员") @RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            Long uid = param.get("uid") == null ? null : Long.parseLong(param.get("uid").toString());
            //判断用户是否已经登录...
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("uid", uid);
            StringBuilder err = new StringBuilder();
            if (companyService.createInvite10(param, err)) {
                modelMap.addAttribute("description", "邀请公司内部人员，成功！");
                return setModelMap(modelMap, HttpCode.OK, dataMap);
            } else {
                modelMap.addAttribute("description", err.toString());
                return setModelMap(modelMap, HttpCode.FAIL, dataMap);
            }
        }catch (Exception ex) {
            modelMap.addAttribute("description", "邀请公司内部人员，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E019 邀请公司外部人员")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"clientType\": \"\",\n" +
            "\"gender\": \"\",\n" +
            "\"inviteeUserName\": \"\",\n" +
            "\"inviterCompanyId\": \"\",\n" +
            "\"inviterCompanyName\": \"\",\n" +
            "\"inviterUserId\": \"\",\n" +
            "\"inviterUsername\": \"\",\n" +
            "\"phoneNum\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/invite/friend")
    public Object inviteFriend(@ApiParam(required = true, value = "邀请公司外部人员") @RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            Long uid = param.get("uid") == null ? null : Long.parseLong(param.get("uid").toString());

            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("uid", uid);

            StringBuilder err = new StringBuilder();
            if (companyService.createInvite11(param, err)) {
                modelMap.addAttribute("description", "邀请公司外部人员，成功！");
                return setModelMap(modelMap, HttpCode.OK, dataMap);
            } else {
                modelMap.addAttribute("description", err.toString());
                return setModelMap(modelMap, HttpCode.FAIL, dataMap);
            }
        }catch (Exception ex) {
            modelMap.addAttribute("description", "邀请公司外部人员，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E020 邀请公司客户")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"address\": \"\",\n" +
            "\"clientType\": \"\",\n" +
            "\"contractPerson\": \"\",\n" +
            "\"contractPhoneNum\": \"\",\n" +
            "\"customerCompanyId\": \"\",\n" +
            "\"customerCompanyName\": \"\",\n" +
            "\"inviterCompanyId\": \"\",\n" +
            "\"inviterCompanyName\": \"\",\n" +
            "\"inviterUserId\": \"\",\n" +
            "\"inviterUsername\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/invite/cst")
    public Object invite(@ApiParam(required = true, value = "邀请公司客户") @RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            Long uid = param.get("uid") == null ? null : Long.parseLong(param.get("uid").toString());

            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("uid", uid);
            StringBuilder err = new StringBuilder();
            if (companyService.createInvite20(param, err)) {
                modelMap.addAttribute("description", "邀请公司客户，成功！");
                return setModelMap(modelMap, HttpCode.OK, dataMap);
            } else {
                modelMap.addAttribute("description", err.toString());
                return setModelMap(modelMap, HttpCode.FAIL, dataMap);
            }
        }catch (Exception ex) {
            modelMap.addAttribute("description", "邀请公司客户，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E017 邀请公司供应商")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "  \"address\": \"\",\n" +
            "  \"clientType\": \"\",\n" +
            "  \"contractPerson\": \"\",\n" +
            "  \"contractPhoneNum\": \"\",\n" +
            "  \"inviterCompanyId\": \"\",\n" +
            "  \"inviterCompanyName\": \"\",\n" +
            "  \"inviterUserId\": \"\",\n" +
            "  \"inviterUsername\": \"\",\n" +
            "  \"invoiceType\": \"\",\n" +
            "  \"invoiceTypeName\": \"\",\n" +
            "  \"supplierCompanyId\": \"\",\n" +
            "  \"supplierCompanyName\": \"\",\n" +
            "  \"supplyType\": \"\",\n" +
            "  \"supplyTypeName\": \"\",\n" +
            "  \"taxRate\": \"\",\n" +
            "  \"token\": \"\",\n" +
            "  \"uid\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/invite/sup")
    public Object inviteSup(@ApiParam(required = true, value = "邀请公司供应商") @RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            Long uid = param.get("uid") == null ? null : Long.parseLong(param.get("uid").toString());
            //判断用户是否已经登录...

            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("uid", uid);
            StringBuilder err = new StringBuilder();
            if (companyService.createInvite30(param, err)) {
                modelMap.addAttribute("description", "邀请公司供应商，成功！");
                return setModelMap(modelMap, HttpCode.OK, dataMap);
            }
            else {
                modelMap.addAttribute("description", err.toString());
                return setModelMap(modelMap, HttpCode.FAIL, dataMap);
            }
        }catch (Exception ex) {
            modelMap.addAttribute("description", "邀请公司供应商，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E018 查询供应商供货类型")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/sup/supplier_ware_list")
    @RequiresPermissions(value = "enterprise:supplier:become_supplier:view")
    public Object supplierWareList(@ApiParam(required = true, value = "查询供应商供货类型") @RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            Long uid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());
            Integer companyId = param.get("company_id") == null || param.get("company_id").toString().isEmpty() ? null : Integer.valueOf(param.get("company_id").toString());
            Integer supCompanyId = param.get("sup_company_id") == null || param.get("sup_company_id").toString().isEmpty() ? null : Integer.valueOf(param.get("sup_company_id").toString());

            Map<String, Object> dataMap = new HashMap<>();
            List<SupplierWare> supplierWareList = companyService.selectBySupCompanyId(companyId, supCompanyId);
            List<Map<String, Object>> retList = BeanToMapUtil.convertBeanList(supplierWareList);
            dataMap.put("mat_type_list", retList);
            modelMap.addAttribute("description", "查询供应商供货类型，成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);
        } catch (IllegalArgumentException ex) {
            modelMap.addAttribute("description", ex.getMessage() + "，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }catch (Exception ex) {
            modelMap.addAttribute("description", "查询供应商供货类型，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }
    @ApiOperation(value = "E019 修改供应商供货类型")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/sup/supplier_ware_modify")
    @RequiresPermissions(value = "enterprise:supplier:become_supplier:update,view")
    public Object supplierWareModify(@ApiParam(required = true, value = "修改供应商供货类型") @RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            Long uid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());
            Integer companyId = param.get("company_id") == null || param.get("company_id").toString().isEmpty() ? null : Integer.valueOf(param.get("company_id").toString());
            Integer supCompanyId = param.get("sup_company_id") == null || param.get("sup_company_id").toString().isEmpty() ? null : Integer.valueOf(param.get("sup_company_id").toString());
            List<Map<String, Object>> mapList = (List<Map<String, Object>>)param.get("company_id");

            List<SupplierWare> supplierWareList = (List<SupplierWare>)BeanToMapUtil.convertMapList(SupplierWare.class, mapList);
            companyService.updateSupplierWare(supplierWareList);
            modelMap.addAttribute("description", "修改供应商供货类型，成功！");
            return setModelMap(modelMap, HttpCode.OK);
        } catch (IllegalArgumentException ex) {
            modelMap.addAttribute("description", ex.getMessage() + "，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }catch (Exception ex) {
            modelMap.addAttribute("description", "修改供应商供货类型，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

}
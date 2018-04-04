package com.mt.cms.company.controller;

import com.mt.cms.common.model.Customer;
import com.mt.cms.common.model.Supplier;
import com.mt.cms.company.service.CustomerService;
import com.mt.cms.company.service.SupplierService;
import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "客户供应商操作", description = "客户供应商操作")
@RestController
public class CustomerOrSupplierController extends BaseController {


    @Autowired
    private SupplierService supplierService;

    @Autowired
    private CustomerService customerService;

    @ApiOperation(value = "E013 获取客户或供应商信息列表")
    @ApiImplicitParam(name = "param", value = "{ \"client_type\":\"\", \"token\":\"必填\", \"cst_sup_type\":1或2, \"company_id\":\"必须\", \"uid\":\"必须\",\"curr_page\":1,\"page_size\":2 }", required = true, dataType = "Map")
    @PostMapping("api/cst-or-sup/list")
    @RequiresPermissions(value = {"enterprise:customer:customer_info:view","enterprise:supplier:supplier_info:view"},logical= Logical.OR)
    public Object getCustomerOrSupplier(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            String client_type = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            String uid = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Integer cst_sup_type = param.get("cst_sup_type") == null ? null : Integer.parseInt(param.get("cst_sup_type").toString());
            Map<String, Object> map = new HashMap();
            map.put("curr_page",param.get("curr_page") == null ? 1 : Integer.valueOf(param.get("curr_page").toString()));
            map.put("page_size",param.get("page_size") == null ? 10 :Integer.valueOf(param.get("page_size").toString()));
            map.put("companyId", param.get("company_id") == null ? null : Long.parseLong(param.get("company_id").toString()));
            //cst_sup_type   1：是获取客户，2：获取供应商
            if (cst_sup_type == 1) {
                Map<String, Object> customerMap = customerService.selectCustomerId(map);
                modelMap.addAttribute("description", "获取客户信息列表，成功！");
                return setModelMap(modelMap, HttpCode.OK, customerMap);
            } else if (cst_sup_type == 2) {
                Map<String, Object> supplierMap = supplierService.selectsupplierId(map);
                modelMap.addAttribute("description", "获取供应商信息列表，成功！");
                return setModelMap(modelMap, HttpCode.OK, supplierMap);
            } else {
                modelMap.addAttribute("description", "cst_sup_type参数错误！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
        } catch (Exception ex) {
            modelMap.addAttribute("description", "获取信息列表，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E014 获取客户资料详情")
    @ApiImplicitParam(name = "param", value = "{ \"client_type\":\"\", \"token\":\"必填\",\"cst_company_id\":\"必填\", \"uid\":\"必填\" }", required = true, dataType = "Map")
    @PostMapping("api/cst-info/view")
    @RequiresPermissions(value = "enterprise:customer:customer_info:view")
    public Object getCustomer(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            Map<String, Object> map = new HashMap();
            map.put("cstCompanyId", param.get("cst_company_id") ==null || param.get("cst_company_id")=="" ? null : param.get("cst_company_id"));
            map.put("uid", param.get("uid") !=null || param.get("uid")!="" ? null : param.get("uid"));
            map.put("companyId", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id").toString().trim());
            Map<String, Object> returnMap = customerService.getCustome(map);
            modelMap.addAttribute("description", "获取客户资料详情，成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);
        } catch (Exception ex) {
            modelMap.addAttribute("description", "参数错误或客户不存在！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E012 获取供应商资料详情")
    @ApiImplicitParam(name = "param", value = "{ \"client_type\":\"\", \"token\":\"必填\",\"sup_company_id\":\"必填\", \"uid\":\"必填\" }", required = true, dataType = "Map")
    @PostMapping("api/sup-info/view")
    @RequiresPermissions(value = "enterprise:supplier:supplier_info:view")
    public Object geSupplier(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String client_type = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String, Object> map = new HashMap();
            map.put("supCompanyId", param.get("sup_company_id") ==null || param.get("sup_company_id")=="" ? null : param.get("sup_company_id"));
            map.put("uid", param.get("uid") !=null || param.get("uid")!="" ? null : param.get("uid"));
            map.put("companyId", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id").toString().trim());
            Map<String, Object> returnMap = supplierService.getSupplier(map);
            modelMap.addAttribute("description", "获取供应商资料详情，成功！");
            return setModelMap(modelMap, HttpCode.OK, returnMap);

        } catch (Exception ex) {
            modelMap.addAttribute("description", "参数错误或供应商不存在！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "E010 修改客户资料", notes = "根据客户 cst_company_id 来进行修改")
    @ApiImplicitParam(name = "param", value = "{\"cst_company_id\":\"\",\"client_type\":\"\",\"company_id\":\"\"," +
            "\"contact_man\":\"\",\"credit_class\":\"\",\"credit_class_code\":\"\",\"cst_company_address\":\"\",\"cst_company_name\":\"\"," +
            "\"cst_type\":\"\",\"cst_type_code\":\"\",\"email\":\"\",\"grade\":\"\",\"grade_code\":\"\",\"important\":\"\",\"important_code\":\"\"," +
            "\"phone\":\"\",\"product\":\"\",\"token\":\"\",\"uid\":\"\"}  自行添加需要修改的参数", required = true, dataType = "Map")
    @PostMapping("api/cst-info/update")
    @RequiresPermissions(value = "enterprise:customer:customer_info:update")
    public Object updateCustomer(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            String uid = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Customer customer = new Customer();
            customer.setCompanyId(param.get("company_id") == null ? null : Long.parseLong(param.get("company_id").toString()));
            customer.setCstCompanyId(param.get("cst_company_id") == null ? null : Long.parseLong(param.get("cst_company_id").toString()));
            customer.setContactMan(param.get("contact_man") == null ? null : param.get("contact_man").toString());
            customer.setCreditClass(param.get("credit_class") == null ? null : param.get("credit_class").toString());
            customer.setCreditClass(param.get("credit_class_code") == null ? null : param.get("credit_class_code").toString());
            customer.setCstCompanyAddress(param.get("cst_company_address") == null ? null : param.get("cst_company_address").toString());
            customer.setCstCompanyName(param.get("cst_company_name") == null ? null : param.get("cst_company_name").toString());
            customer.setCstType(param.get("cst_type") == null? null : Integer.parseInt(param.get("cst_type").toString()));
            customer.setCstTypeCode(param.get("cst_type_code") == null ? null : Integer.parseInt(param.get("cst_type_code").toString()));
            customer.setEmail(param.get("email") == null ? null : param.get("email").toString());
            customer.setGrade(param.get("grade") == null ? null : Integer.valueOf(param.get("grade").toString()));
            customer.setGradeCode(param.get("grade_code") == null ? null : Integer.valueOf(param.get("grade_code").toString()));
            customer.setImportant(param.get("important") == null? null : Integer.parseInt(param.get("important").toString()));
            customer.setImportantCode(param.get("important_code") == null ? null : Integer.parseInt(param.get("important_code").toString()));
            customer.setPhone(param.get("phone") == null ? null : param.get("phone").toString());
            customer.setProduct(param.get("product") == null ? null : param.get("product").toString());
            int i = customerService.updateCustomer(customer);
            if (i == 1) {
                Map<String, Object> map = customerService.selectCustomer(customer.getCompanyId(), customer.getCstCompanyId());
                modelMap.addAttribute("description", "修改客户资料，成功！");
                return setModelMap(modelMap, HttpCode.OK,map);
            } else {
                modelMap.addAttribute("description", "修改客户资料，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "请填正确填写资料！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E009 修改供应商资料详情", notes = "根据供应商 sup_company_id 来进行修改")
    @ApiImplicitParam(name = "param", value = "{\"token\":\"\",\"sup_company_id\":\"\",\"client_type\":\"\",\"uid\":\"\",\"company_id\":\"\"," +
            "\"contact_man\":\"\",\"credit_class\":\"\",\"email\":\"\",\"is_tax\":\"\",\"phone\":\"\",\"quality_level\":\"\",\"sup_company_address\":\"\"," +
            "\"quality_level_code\":\"\",\"sup_company_name\":\"\",\"supply_type\":\"\",\"supply_type_code\":\"\",\"tax_type\":\"\"," +
            "\"tax_ratio\":\"\",\"tax_type_code\":\"\"}自行添加需要修改的参数", required = true, dataType = "Map")
    @PostMapping("api/sup-info/update")
    @RequiresPermissions(value = "enterprise:supplier:supplier_info:update")
    public Object updateSupplier(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            String uid = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Supplier supplier = new Supplier();
            supplier.setCompanyId(param.get("company_id") == null ? null : Long.parseLong(param.get("company_id").toString()));
            supplier.setContactMan(param.get("contact_man") == null ? null : param.get("contact_man").toString());
            supplier.setCreditClass(param.get("credit_class") == null ? null : param.get("credit_class").toString());
            supplier.setEmail(param.get("email") == null ? null : param.get("email").toString());
            supplier.setIsTax(param.get("is_tax") == null ? true : Boolean.valueOf(param.get("is_tax").toString()));
            supplier.setPhone(param.get("phone") == null ? null : param.get("phone").toString());
            supplier.setQualityLevel(param.get("quality_level") == null ? null : Integer.parseInt(param.get("quality_level").toString()));
            supplier.setSupCompanyAddress(param.get("sup_company_address") == null ? null : param.get("sup_company_address").toString());
            supplier.setSupCompanyId(param.get("sup_company_id") == null ? null : Long.parseLong(param.get("sup_company_id").toString()));
            supplier.setSupCompanyName(param.get("sup_company_name") == null ? null : param.get("sup_company_name").toString());
            supplier.setSupplyType(param.get("supply_type") == null ? null : param.get("supply_type").toString().trim());
            supplier.setTaxRatio(param.get("tax_ratio") == null ? null : new BigDecimal(param.get("tax_ratio").toString()));
            supplier.setTaxType(param.get("tax_type") == null ? null : param.get("tax_type").toString());
            supplier.setTaxTypeCode(param.get("tax_type_code") == null ? null : Integer.valueOf(param.get("tax_type_code").toString()));
            int i = supplierService.updateSupplier(supplier);
            if (i == 1) {
                logger.info(supplier.getSupCompanyId());
                Map<String, Object> map = supplierService.selectSupplier(supplier.getSupCompanyId());
                modelMap.addAttribute("description", "修改供应商资料详情，成功！");
                return setModelMap(modelMap, HttpCode.OK, map);
            } else {
                modelMap.addAttribute("description", "修改供应商资料详情，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
        } catch (Exception ex) {
            modelMap.addAttribute("description", "请填写必填的资料，失败！");
            logger.info(ex.getMessage());
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E011 删除企业的客户或供应商")
    @ApiImplicitParam(name = "param", value = "{ \"client_type\":\"\", \"token\":\"必填\", \"cst_sup_id\":[1,2], \"cst_sup_typ\":1或2, \"company_id\":\"\", \"uid\":\"\" }", required = true, dataType = "Map")
    @PostMapping("/api/cst_sup/delete")
    @RequiresPermissions(value = {"enterprise:customer:customer_info:delete","enterprise:supplier:supplier_info:delete"},logical= Logical.OR)
    public Object deleteCustomerOrSupplier(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            String client_type = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Integer uCompanyId = param.get("company_id") == null ? null : Integer.parseInt(param.get("company_id").toString());
            String uid = param.get("uid") == null ? null : param.get("uid").toString().trim();
            Integer cst_sup_typ = param.get("cst_sup_typ") == null ? null : Integer.parseInt(param.get("cst_sup_typ").toString());
            List<Integer> cst_sup_id = param.get("cst_sup_id") == null ? null : (List<Integer>) param.get("cst_sup_id");

            //cst_sup_type   1：是获取客户，2：获取供应商
            if (cst_sup_typ == 1) {
                int i = customerService.deleteCustomer(cst_sup_id);
                if (i >= 1) {
                    modelMap.addAttribute("description", "客户删除,成功！");
                    return setModelMap(modelMap, HttpCode.OK);
                } else {
                    modelMap.addAttribute("description", "客户不存在！");
                    return setModelMap(modelMap, HttpCode.FAIL);
                }
            } else if (cst_sup_typ == 2) {
                int i = supplierService.deleteSupplier(cst_sup_id);
                if (i >= 1) {
                    modelMap.addAttribute("description", "供应商删除,成功！");
                    return setModelMap(modelMap, HttpCode.OK);
                } else {
                    modelMap.addAttribute("description", "供应商不存在！");
                    return setModelMap(modelMap, HttpCode.FAIL);
                }
            } else {
                modelMap.addAttribute("description", "cst_sup_type错误，删除失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "删除，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }
}

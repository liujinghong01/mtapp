package com.mt.cms.company.controller;


import com.mt.cms.company.service.CompanyService;
import com.mt.cms.company.service.CustomerService;
import com.mt.cms.company.service.SupplierService;
import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.upms.user.service.UserService;
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

import java.util.HashMap;
import java.util.Map;

@RestController
@Api(value = "搜索功能", description = "搜索功能")
public class SearchController extends BaseController {
    @ApiOperation(value = "E015 搜索企业或平台用户信息", notes = "[client_type = '1'浏览器 '2' 移动app  '3' 微信网页 '4'微信小程序 '5' 微信公众号] " +
            "[cst_sup_type  number  1：是获取客户，2：获取供应商 ]  [com_user_name  按公司名字模糊搜索  string  必须]  [token  用户标识  string  必须] [uid  用户uid  number  必须]")
    @ApiImplicitParam(name = "param", value = "{\"client_type\":\"2\",\"search_type\":\"1或者2\",\"com_user_name\":\"\",\" uid\":\"123\",\"token\":\"\"}", required = true, dataType = "Map")
    @PostMapping("/api/com-user/search")
    public Object getEnterpriseInformation(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
          
            Integer uId = param.get("uid") == null ? null:Integer.valueOf(param.get("uid").toString().trim());
            String client_type = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            String comUserName = param.get("com_user_name") == null ? null : param.get("com_user_name").toString().trim();
            Integer searchType = param.get("search_type") == null ? null : Integer.valueOf(param.get("search_type").toString());
            Integer companyId = param.get("company_id") == null ? null : Integer.valueOf(param.get("company_id").toString().trim());
            if(companyId==null){
                modelMap.addAttribute("description", "缺少公司ID！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            if(searchType==1){
                Map<String, Object> map= companyService.vagueSelectCompanyAndUser(comUserName,searchType,companyId,uId);
                modelMap.addAttribute("description", "获取企业信息，成功！");
                return setModelMap(modelMap, HttpCode.OK,map);
            }else if(searchType==2){
                Map<String, Object> map= companyService.vagueSelectCompanyAndUser(comUserName,searchType,companyId,uId);
                modelMap.addAttribute("description", "获取平台用户信息，成功！");
                return setModelMap(modelMap, HttpCode.OK,map);
            }else {
                modelMap.addAttribute("description", "searchType参数，错误！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "获取企业或平台用户信息，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @Autowired
    private CompanyService companyService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "E016 搜索企业自己客户或供应商", notes = "[client_type = '1'浏览器 '2' 移动app  '3' 微信网页 '4'微信小程序 '5' 微信公众号] [company_id  公司id  string  必须] " +
            "[cst_sup_type  number  1：是获取客户，2：获取供应商 必须]  [cst_sup_type  搜索企业自己客户或供应商名字  string  必须] [company_id  公司ID 必须]  [token  用户标识  string  必须] [uid  用户uid  number  必须]")
    @ApiImplicitParam(name = "param", value = "{\"client_type\": \"\",\"company_id\": \"\",  \"cst_sup_name\": \"\", \"uid\": \"\"," +
            "\"cst_sup_type\":\"\",\"token\": \"\",\"curr_page\":1,\"page_size\":2 }", required = true, dataType = "Map")
    @PostMapping("/api/cst-sup/search")
    @RequiresPermissions(value = {"enterprise:customer:customer_info:view","enterprise:supplier:supplier_info:view"},logical= Logical.OR)
    public Object getSupCstInformation(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            String uid = param.get("uid") == null ? null : param.get("uid").toString().trim();
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Long companyId = param.get("company_id") == null ? null : Long.parseLong(param.get("company_id").toString().trim());
            Integer cstSupType = param.get("cst_sup_type") == null ? null : Integer.parseInt(param.get("cst_sup_type").toString());
            Map<String,Object> map=new HashMap<>();
            map.put("cst_company_name",param.get("cst_sup_name") == null ? null : param.get("cst_sup_name").toString().trim());
            map.put("companyId",param.get("company_id") == null ? null : Long.parseLong(param.get("company_id").toString().trim()));
            map.put("page_size",param.get("page_size") == null ? 10 : Integer.valueOf(param.get("page_size").toString()));
            map.put("curr_page",param.get("curr_page") == null ? 1 : Integer.valueOf(param.get("curr_page").toString()));
            if (cstSupType == 1) {
                Map<String,Object> mapOut = customerService.searchCstName(map);
                modelMap.addAttribute("description", "获取客户信息，成功！");
                return setModelMap(modelMap, HttpCode.OK, mapOut);
            } else if (cstSupType == 2) {
                Map<String,Object> mapOut  = supplierService.searchSupName(map);
                modelMap.addAttribute("description", "获取供应商信息，成功！");
                return setModelMap(modelMap, HttpCode.OK, mapOut);
            } else {
                modelMap.addAttribute("description", "获取企业信息，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
        } catch (Exception ex) {
            modelMap.addAttribute("description", "获取企业信息，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

}

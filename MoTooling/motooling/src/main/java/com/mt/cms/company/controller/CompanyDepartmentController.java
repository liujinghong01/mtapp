package com.mt.cms.company.controller;

import com.mt.cms.company.service.CompanyDepartmentService;
import com.mt.cms.company.service.CompanyService;
import com.mt.cms.company.service.DepartmentService;
import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
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
@Api(value = "公司或部门操作", description = "公司或部门操作")
public class CompanyDepartmentController extends BaseController {

    @Autowired
    private CompanyDepartmentService companyDepartmentService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CompanyService companyService;

    /*
    @ApiOperation(value = "E006 公司或部门内批量删除成员", notes = "[client_type = '1'浏览器 '2' 移动app  '3' 微信网页 '4'微信小程序 '5' 微信公众号]" +
            "[token  用户标识  string  必须] [uid  用户uid  number  必须]  [company_id  公司id 必填]  [dep_id 部门ID  必填] [member_ids[] 成员id数组 ]")
    @ApiImplicitParam(name = "param", value = "{\"client_type\":\"\",\"uid\":\"\",\"company_id\":\"\",\"token\": \"\",\"dep_id\":\"\", \"member_ids\": [7,8]}", required = true, dataType = "Map")
    @PostMapping("/api/dep-user/delete")
    public Object batchDelete(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            String cToken = param.get("token") == null ? null : param.get("token").toString().trim();
            if (!this.isLoginUser(cToken)) {
                modelMap.addAttribute("description", "该用户未登录，失败！");
                return setModelMap(modelMap, HttpCode.UNAUTHORIZED);
            }
            Integer uid = param.get("uid") == null ? null : Integer.valueOf(param.get("uid").toString());
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            String companyId = param.get("company_id") == null ? null : param.get("company_id").toString().trim();
            String depId = param.get("dep_id") == null ? null : param.get("dep_id").toString().trim();
            List memberIds = param.get("member_ids") == null ? null : (List) param.get("member_ids");
            boolean remove = memberIds.remove(uid);
            Boolean b = companyDepartmentService.deleteBatch(companyId, depId, memberIds);
            if (b) {
                modelMap.addAttribute("description", "删除成员，成功！");
                return setModelMap(modelMap, HttpCode.OK);
            } else {
                modelMap.addAttribute("description", "成员不存在该部门！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            modelMap.addAttribute("description", "删除成员，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }*/


    @ApiOperation(value = "E007 公司部门删除", notes = "[client_type = '1'浏览器 '2' 移动app  '3' 微信网页 '4'微信小程序 '5' 微信公众号]" +
            "[token  用户标识  string  必须] [uid  用户uid  number  必须]  [company_id  公司id 必填]  [dep_id 部门ID  必填] ")
    @ApiImplicitParam(name = "param", value = "{\"client_type\":\"\",\"uid\":\"\",\"token\":\"\",\"dep_ids\":[22,23],\"company_id\":\"35\"}", required = true, dataType = "Map")
    @PostMapping("/api/dep/delete")
    @RequiresPermissions(value = "enterprise:enterprise_manager:department:delete")
    public Object deleteDep(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            String uid = param.get("uid") == null ? null : param.get("uid").toString().trim();
            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Map<String,Object> map = new HashMap();
            map.put("depIds",param.get("dep_id") == null ? null : (List)param.get("dep_id"));
            map.put("companyId",param.get("company_id") == null ? null : param.get("company_id"));
            ////Boolean b = departmentService.deleteDepId(map);
            StringBuilder err = new StringBuilder();
            int ret =  companyService.deleteDepartment(Integer.valueOf(map.get("dep_id").toString()), Long.valueOf(map.get("uid").toString()), err, false);
            if (ret >= 0) {
                modelMap.addAttribute("description", "部门删除，成功！");
                return setModelMap(modelMap, HttpCode.OK);
            } else {
                modelMap.addAttribute("description", err.toString() + "，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "该部门不存在，删除失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }

    }


    /*@ApiOperation(value = "E008 批量用户部门变更", notes = "[client_type = '1'浏览器 '2' 移动app  '3' 微信网页 '4'微信小程序 '5' 微信公众号]" +
            "[token  用户标识  string  必须] [uid  用户uid  number  必须]  [company_id  公司id 必填]  [dep_id 部门ID  必填] ")
    @ApiImplicitParam(name = "param", value = "{\"client_type\":\"\",\"uid\":\"\",\"token\":\"\",\"dep_ids\":[22,23],\"member_ids\":[7,9],\"company_id\":\"35\" ,\"member_dep_id\":\"\"}", required = true, dataType = "Map")
    @PostMapping("/api/user-dep/update")
    public Object updateDep(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            String clientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            String uid = param.get("uid") == null ? null : param.get("uid").toString().trim();
            String companyId = param.get("company_id") == null ? null : param.get("company_id").toString().trim();
            Integer userDepId = param.get("member_dep_id") == ""||param.get("member_dep_id") == null ? null : Integer.valueOf(param.get("member_dep_id").toString());
            List depIds = param.get("dep_ids") == null ? null : (List)param.get("dep_ids");
            List memberIds = param.get("member_ids") == null ? null : (List)param.get("member_ids");
            Boolean b = companyDepartmentService.updateBatch(companyId,depIds,memberIds,userDepId);
            if(b){
               modelMap.addAttribute("description", "用户部门变更，成功！");
               return setModelMap(modelMap, HttpCode.OK);
            }else {
               modelMap.addAttribute("description", "公司ID或用户ID错误！");
               return setModelMap(modelMap, HttpCode.FAIL);
            }
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "某用户存在转移部门！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }*/

}

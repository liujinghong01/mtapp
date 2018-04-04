package com.mt.cms.company.controller;


/**
* @Author: Fhk
* @Description:
* @create    2017/9/10 17:37
* @Modified By:
*/

import com.mt.cms.common.model.Company;
import com.mt.cms.common.model.Department;
import com.mt.cms.common.model.OrigUserCompany;
import com.mt.cms.company.service.CompanyService;
import com.mt.common.bo.BeanToMapUtil;
import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.StringUtils;
import com.mt.upms.common.model.User;
import com.mt.upms.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mt.common.core.support.HttpCode.NOT_LOGIN;

@Api(value = "公司注册/创建接口", description = "公司注册/创建接口")
@RestController
@Scope(value="prototype")
public class CompanyRegisterController extends BaseController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "E001 公司注册/创建")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "  \"address\": \"\",\n" +
            "  \"ceo\": \"\",\n" +
            "  \"city\": \"\",\n" +
            "  \"client_type\": \"\",\n" +
            "  \"company_code\": \"\",\n" +
            "  \"company_id\": \"\",\n" +
            "  \"company_name\": \"\",\n" +
            "  \"company_type\": \"\",\n" +
            "  \"country\": \"\",\n" +
            "  \"credibility\": \"\",\n" +
            "  \"data\": \"\",\n" +
            "  \"email\": \"\",\n" +
            "  \"fax\": \"\",\n" +
            "  \"industry\": \"\",\n" +
            "  \"license\": \"\",\n" +
            "  \"phone\": \"\",\n" +
            "  \"postcode\": \"\",\n" +
            "  \"province\": \"\",\n" +
            "  \"token\": \"\",\n" +
            "  \"uid\": \"\",\n" +
            "  \"website\": \"\",\n" +
            "  \"wechat_pub_acct\": \"\",\n" +
            "  \"weibo\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/company/company-profile")
    public Object subRegister(@ApiParam(required = true, value = "公司注册信息") @RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Company company = new Company();
            company.setAddress(param.get("address") == null ? null : param.get("address").toString().trim());
            company.setCeo(param.get("ceo") == null ? null : param.get("ceo").toString().trim());
            company.setCity(param.get("city") == null ? null : param.get("city").toString().trim());
            company.setCompanyCode(param.get("company_code") == null ? null : param.get("company_code").toString().trim());
            Integer companyId = param.get("company_id") == null || param.get("company_id").toString().isEmpty() ? null : Integer.parseInt(param.get("company_id").toString());
            company.setCompanyId(companyId);
            String companyName = param.get("company_name") == null ? null : param.get("company_name").toString().trim();
            company.setCompanyName(companyName);
            company.setCompanyFullName(companyName);
            company.setCompanyType(param.get("company_type") == null ? null : Short.parseShort(param.get("company_type").toString()));
            company.setCountry(param.get("country") == null ? null : param.get("country").toString().trim());
            company.setCredibility(param.get("credibility") == null ? null : param.get("credibility").toString().trim());
            company.setEmail(param.get("email") == null ? null : param.get("email").toString().trim());
            company.setFax(param.get("fax") == null ? null : param.get("fax").toString().trim());
            company.setIndustry(param.get("industry") == null ? null : Short.parseShort(param.get("industry").toString()));
            company.setLicence(param.get("license") == null ? null : param.get("license").toString().trim());
            company.setPhone(param.get("phone") == null ? null : param.get("phone").toString().trim());
            company.setPostcode(param.get("postcode") == null ? null : param.get("postcode").toString().trim());
            company.setProvince(param.get("province") == null ? null : param.get("province").toString().trim());
            ////company.(param.get("token") == null ? null : param.get("token").toString().trim());
            company.setWebsite(param.get("website") == null ? null : param.get("website").toString().trim());
            company.setWechatPubAcct(param.get("wechat_pub_acct") == null ? null : param.get("wechat_pub_acct").toString().trim());
            company.setWeibo(param.get("weibo") == null ? null : param.get("weibo").toString().trim());
            company.setBizDes(param.get("biz_des") == null ? null : param.get("biz_des").toString().trim());
            company.setPlatCompanyType((byte) 1);
            Long uid = param.get("uid") == null ||  param.get("uid").toString().trim().length() < 1 ? null : Long.parseLong(param.get("uid").toString());

            User user = userService.selectByUid(uid);
            if(user == null) {
                modelMap.addAttribute("description", "无效的用户Id，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            companyId = companyService.createCompanyRegister(company, uid);
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("company_id", companyId);
            dataMap.put("company_name", companyName);
            modelMap.addAttribute("description", "公司注册 成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);
        } catch (Exception ex) {
            modelMap.addAttribute("description", "公司注册，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E002 查询公司信息")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"company_id\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/company/get-company-profile")
    public Object getCompanyInfo(@ApiParam(required = true, value = "查询公司信息") @RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            Integer companyId = param.get("company_id") == null ? null : Integer.parseInt(param.get("company_id").toString());
            String uid = param.get("uid") == null ? "" : param.get("uid").toString().trim();

            Map<String, Object> company = BeanToMapUtil.convertBean(companyService.selectByCompanyId(companyId));
            if (company == null || company.isEmpty()) {
                modelMap.addAttribute("description", "公司不存在，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            Map<String, Object> mapReturn = new HashMap<>();
            for (String m : company.keySet()) {
                mapReturn.put(StringUtils.camelToUnderline(m),company.get(m));
            }
            modelMap.addAttribute("description", "查询公司信息，成功！");
            return setModelMap(modelMap, HttpCode.OK, mapReturn);
        } catch (Exception ex) {
            modelMap.addAttribute("description", "查询公司信息，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    public Object getUserOfDept(@ApiParam(required = true, value = "") @RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            //判断用户是否已经登录...
            String clientType = param.get("client_type") == null ? "" : param.get("client_type").toString().trim();
            String pToken = param.get("token") == null ? "" : param.get("token").toString().trim();
            if (!this.isLoginUser(pToken)) {
                modelMap.addAttribute("description", "该用户未登录，失败！");
                return setModelMap(modelMap, NOT_LOGIN);
            }
            Integer pCompanyId = param.get("company_id") == null ? null : Integer.parseInt(param.get("company_id").toString());
            Integer pDepId = param.get("dep_id") == null ? null : Integer.parseInt(param.get("dep_id").toString());
            String uid = param.get("uid") == null ? "" : param.get("uid").toString().trim();

            List<User>  listUser = userService.selectByDepId(pCompanyId, pDepId);
            modelMap.addAttribute("description", "查询部门成员，成功！");
            return setModelMap(modelMap, HttpCode.OK, listUser);

        } catch (Exception ex) {
            modelMap.addAttribute("description", "查询部门成员，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E006 获取公司成员基本信息")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"company_id\": \"\",\n" +
            "\"member_id\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/com/member-info")
    public Object getCompanyMemberInfo(@ApiParam(required = true, value = "")@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {

            String pClientType = param.get("client_type") == null ? "" : param.get("client_type").toString().trim();
            Integer pCompanyId = param.get("company_id") == null ? null : Integer.parseInt(param.get("company_id").toString());
            Integer pDepId = param.get("dep_id") == null ? null : Integer.parseInt(param.get("dep_id").toString());
            Long pMemberId = param.get("member_id") == null ? null : Long.parseLong(param.get("member_id").toString());
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());
            ////OrigUserCompany origUserCompany = companyService.selectUserCompanyByUid(pCompanyId, pMemberId);
            OrigUserCompany origUserCompany = companyService.selectUserCompanyByDepIdAndUid(pCompanyId, pDepId, pMemberId);
            if(origUserCompany == null) {
                modelMap.addAttribute("description", "该公司成员不存在，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            Department department = null;
            if(origUserCompany.getDepId() != null) {
                department = companyService.selectByDepId(origUserCompany.getDepId());
            }
            ////User user = userService.selectByUid(pMemberId.longValue());
            Map<String, Object>dataMap = new HashMap<>();
            dataMap.put("dep_id", department == null ? null : department.getDepId());
            dataMap.put("dep_name", department == null ? null : department.getName());
            dataMap.put("dep_position", origUserCompany.getPositionName());
            ////dataMap.put("member_name", user.getFullname());
            dataMap.put("work_phone", origUserCompany.getWorkPhone());
            dataMap.put("work_email", origUserCompany.getWorkEmail());
            dataMap.put("work_state", origUserCompany.getWorkState());
            dataMap.put("work_name", origUserCompany.getWorkName());
            dataMap.put("member_id", pMemberId);

            modelMap.addAttribute("description", "获取公司成员基本信息，成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);

        }catch (Exception ex) {
            modelMap.addAttribute("description", "获取公司成员基本信息，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E005 添加部门接口")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"comment\": \"\",\n" +
            "\"company_id\": \"\",\n" +
            "\"dep_name\": \"\",\n" +
            "\"manager\": \"\",\n" +
            "\"name_en\": \"\",\n" +
            "\"upper_id\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/company/create-department")
    @RequiresPermissions(value = "enterprise:enterprise_manager:department:create,update,delete,view")
    public Object addDept(@ApiParam(required = true, value = "添加部门") @RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {

            StringBuilder err = new StringBuilder();
            Map dataMap = companyService.addDept(param, err, false);
            if (dataMap == null) {
                modelMap.addAttribute("description", err.toString() + "，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            } else {
                modelMap.addAttribute("description", "添加部门，成功！");
                return setModelMap(modelMap, HttpCode.OK, dataMap);
            }
        } catch (IllegalArgumentException ex) {
            modelMap.addAttribute("description", ex.getMessage() + "，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "添加部门，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "E004 获取部门及所在部门人员")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"company_id\": \"\",\n" +
            "\"dep_id\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/company/getDepartmentMember")
    public Object getMemberOfDepth(@ApiParam(required = true, value = "") @RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? "" : param.get("client_type").toString().trim();
            Integer pCompanyId = param.get("company_id") == null ? null : Integer.parseInt(param.get("company_id").toString());
            String pDeptId = param.get("dep_id") == null ? "" : param.get("dep_id").toString().trim();
            Long pUid = param.get("uid") == null ? null : Long.parseLong(param.get("uid").toString());

            if(companyService.selectUserCompanyByUid(pCompanyId, pUid) == null) {
                modelMap.addAttribute("description", "用户不属于该公司，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            if (pDeptId.length() < 1 || "all".equals(pDeptId.toLowerCase())) {
                pDeptId = "0";
            }
            List<Map<String, Object>> memberInfoList = userService.getDeptMemeberByDepId(pCompanyId, Integer.parseInt(pDeptId));
            List<Department> departmentList = companyService.selectByUpperDepId(pCompanyId, Integer.parseInt(pDeptId));
            Integer item = 0;
            String allUpperIds;
            Object[] depts = new Object[departmentList.size()];
            for(Department department: departmentList) {
                Map<String, Object> dept = new HashMap<>();
                dept.put("dep_id", department.getDepId());
                dept.put("dep_name", department.getName());
                dept.put("dep_code", department.getDepCode());
                dept.put("is_default", department.getIsDefault());
                allUpperIds = department.getAllUpperIds();
                allUpperIds = allUpperIds == null ? "" : allUpperIds.trim();
                String[] upperIds = allUpperIds.split("\\|");
                dept.put("level", upperIds[0].length() > 0 ? upperIds.length + 1 : upperIds.length);
                Integer memberNum = companyService.getAllUserCountByDepId(pCompanyId, department.getDepId());
                dept.put("member_num", memberNum);
                depts[item++] = dept;
            }
            Map<String, Object>dataMap = new HashMap<>();
            dataMap.put("deps", depts);
            dataMap.put("memberInfos", memberInfoList);

            modelMap.addAttribute("description", "获取部门及所在部门人员，成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);

        } catch (Exception ex){
            modelMap.addAttribute("description", "获取部门及所在部门人员，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation("修改公司成员基本信息")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\":\"\",\n" +
            "\"company_id\":\"\",\n" +
            "\"dep_id\":\"\",\n" +
            "\"dep_name\":\"\",\n" +
            "\"dep_position\":\"\",\n" +
            "\"dep_position_code\":\"\",\n" +
            "\"member_id\":\"\",\n" +
            "\"member_phone\":\"\",\n" +
            "\"work_email\":\"\",\n" +
            "\"work_state\":\"\",\n" +
            "\"token\": \"\"\n" +
            "\"uid\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/company/modify-memberinfo")
    @RequiresPermissions(value = "enterprise:enterprise_manager:employee:create,update,delete,view")
    public Object modifyMemberInfo(@ApiParam(required = true, value = "") @RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? "" : param.get("client_type").toString().trim();
            Integer pCompanyId = param.get("company_id") == null ? null : Integer.parseInt(param.get("company_id").toString());
            Integer pDepId = param.get("dep_id") == null || param.get("dep_id").toString().isEmpty() ? null : Integer.parseInt(param.get("dep_id").toString());
            String pDepName = param.get("dep_name") == null ? null : param.get("dep_name").toString().trim();
            Integer pDepPosition = param.get("dep_position_code") == null || param.get("dep_position_code").toString().isEmpty() ? null : Integer.parseInt(param.get("dep_position_code").toString());
            String pDepPositionName = param.get("dep_position") == null ? null : param.get("dep_position").toString().trim();
            Long pMemberId = param.get("member_id") == null || param.get("member_id").toString().isEmpty() ? null : Long.parseLong(param.get("member_id").toString());
            String pWorkPhone = param.get("work_phone") == null ? null : param.get("work_phone").toString().trim();
            String pWorkState = param.get("work_state") == null ? null : param.get("work_state").toString().trim();
            String pWorkEmail = param.get("work_email") == null ? null : param.get("work_email").toString().trim();
            String pWorkName = param.get("work_name") == null ? null : param.get("work_name").toString().trim();
            Long pUid = param.get("uid") == null ? null : Long.parseLong(param.get("uid").toString());

            if(companyService.selectUserCompanyByUid(pCompanyId, pUid) == null) {
                modelMap.addAttribute("description", "用户不属于该公司，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            if (pDepId == null || pDepId.equals(0)) {
                modelMap.addAttribute("description", "部门不存在，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            Department department = companyService.selectByDepId(pDepId);
            if (department == null) {
                modelMap.addAttribute("description", "部门不存在，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            if (!department.getCompanyId().equals(pCompanyId)) {
                modelMap.addAttribute("description", "部门不属于公司，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            OrigUserCompany origUserCompany = companyService.selectUserCompanyByDepIdAndUid(pCompanyId, pDepId, pMemberId);
            if (origUserCompany == null) {
                origUserCompany = companyService.selectUserCompanyByUid(pCompanyId, pMemberId);
                if (origUserCompany == null) {
                    modelMap.addAttribute("description", "成员不存在，失败！");
                    return setModelMap(modelMap, HttpCode.FAIL);
                }
            }
            origUserCompany.setDepId(pDepId == null || pDepId.equals(0) ? null : pDepId);
            origUserCompany.setDepId(pDepId);
            origUserCompany.setPositionCode(pDepPosition);
            origUserCompany.setPositionName(pDepPositionName);
            origUserCompany.setWorkPhone(pWorkPhone);
            origUserCompany.setWorkEmail(pWorkEmail);
            origUserCompany.setWorkState(pWorkState);
            origUserCompany.setWorkName(pWorkName);

            StringBuilder err = new StringBuilder();
            if (companyService.updateUserCompany(origUserCompany, err, false) < 0) {
                modelMap.addAttribute("description", err.toString() + "，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            ////origUserCompany = companyService.selectUserCompanyByUid(pCompanyId, pMemberId);
            department = companyService.selectByDepId(origUserCompany.getDepId());
            ////User user = userService.selectByUid(pMemberId);
            Map<String, Object>dataMap = new HashMap<>();
            dataMap.put("dep_id", origUserCompany.getDepId());
            dataMap.put("dep_name", department == null ? null : department.getName());
            dataMap.put("dep_position", origUserCompany.getPositionName());
            ////dataMap.put("member_name", user == null ? null : user.getUsername());
            dataMap.put("work_phone", origUserCompany.getWorkPhone());
            dataMap.put("work_email", origUserCompany.getWorkEmail());
            dataMap.put("work_state", origUserCompany.getWorkState());
            dataMap.put("work_name", origUserCompany.getWorkName());
            modelMap.addAttribute("description", "修改公司内部成员信息，成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);

        } catch (IllegalArgumentException ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", ex.getMessage() + "，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        } catch (Exception ex){
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "修改公司内部成员信息，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation("查询公司所有部门及其子部门")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\": \"\",\n" +
            "\"company_id\": \"\",\n" +
            "\"dep_id\": \"\",\n" +
            "\"token\": \"\",\n" +
            "\"uid\": \"\"\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/company/get-sub-deps")
    public Object getSubDeps(@ApiParam(required = true, value = "查询公司所有部门及其所有子部门") @RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? "" : param.get("client_type").toString().trim();
            Integer pCompanyId = param.get("company_id") == null ? null : Integer.parseInt(param.get("company_id").toString());
            Integer pDepId = param.get("dep_id") == null || param.get("dep_id").toString().isEmpty() ? null : Integer.parseInt(param.get("dep_id").toString());
            Long pUid = param.get("uid") == null ? null : Long.parseLong(param.get("uid").toString()); String pDepName = param.get("dep_name") == null ? null : param.get("dep_name").toString().trim();

            if(companyService.selectUserCompanyByUid(pCompanyId, pUid) == null) {
                modelMap.addAttribute("description", "用户不属于该公司，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            if (pDepId != null) {
                Department department = companyService.selectByDepId(pDepId);
                if (department == null) {
                    modelMap.addAttribute("description", "部门不存在，失败！");
                    return setModelMap(modelMap, HttpCode.FAIL);
                }
                if (!department.getCompanyId().equals(pCompanyId)) {
                    modelMap.addAttribute("description", "部门不属于公司，失败！");
                    return setModelMap(modelMap, HttpCode.FAIL);
                }
            }
            ArrayList<Map<String, Object>> allSubDeps = companyService.getAllSubDeps(pCompanyId, pDepId);

            Map<String, Object>dataMap = new HashMap<>();
            dataMap.put("deps", allSubDeps);
            modelMap.addAttribute("description", "查询公司所有部门及其所有子部门，成功！");
            return setModelMap(modelMap, HttpCode.OK, dataMap);

        } catch (Exception ex){
            modelMap.addAttribute("description", "查询公司所有部门及其所有子部门，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "修改部门信息")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/company/modify-deptinfo")
    @RequiresPermissions(value = "enterprise:enterprise_manager:department:update,view")
    public Object modifyDep(@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? "" : param.get("client_type").toString().trim();
            Integer pCompanyId = param.get("company_id") == null ? null : Integer.parseInt(param.get("company_id").toString());
            Map<String, Object>defInfoMap = (Map)param.get("dep_info");
            Long pUid = param.get("uid") == null ? null : Long.parseLong(param.get("uid").toString());
            ////String pDepName = defInfoMap.get("dep_name") == null ? null : defInfoMap.get("dep_name").toString().trim();

            defInfoMap.put("company_id", pCompanyId);
            defInfoMap.put("uid", pUid);

            StringBuilder err = new StringBuilder();
            if (companyService.updateDepartment(defInfoMap, err, false) < 0) {
                modelMap.addAttribute("description", err.toString() + "，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            //Map depMap = this.camelToUnderline(BeanToMapUtil.convertBean(department));
            //depMap.put("dep_name", depMap.get("name"));
            Department department = companyService.selectByDepId(Integer.valueOf(defInfoMap.get("dep_id").toString()));
            defInfoMap.put("is_default", department.getIsDefault());
            String allUpperIds = department.getAllUpperIds();
            allUpperIds = allUpperIds == null ? "" : allUpperIds.trim();
            String[] upperIds = allUpperIds.split("\\|");
            defInfoMap.put("level", upperIds[0].length() > 0 ? upperIds.length + 1 : upperIds.length);
            Map<String, Object>retMap = new HashMap<>();
            retMap.put("dep_info", defInfoMap);
            modelMap.addAttribute("description", "修改部门信息，成功！");
            return setModelMap(modelMap, HttpCode.OK, retMap);

        } catch (IllegalArgumentException ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", ex.getMessage() + "，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
        catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "修改部门信息，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "获取部门基本信息")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/company/department")
    public Object getDep(@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? "" : param.get("client_type").toString().trim();
            Integer pCompanyId = param.get("company_id") == null ? null : Integer.parseInt(param.get("company_id").toString());
            Integer pDepId = param.get("dep_id") == null || param.get("dep_id").toString().isEmpty() ? null : Integer.parseInt(param.get("dep_id").toString());
            Long pUid = param.get("uid") == null ? null : Long.parseLong(param.get("uid").toString());
           
            Department department = companyService.selectByDepId(pDepId);
            Map<String, Object> depInfo = this.camelToUnderline(BeanToMapUtil.convertBean(department));
            depInfo.put("dep_name", depInfo.get("name") == null ? null : depInfo.get("name").toString());
            depInfo.put("dep_admin_id", depInfo.get("manager") == null ? null : depInfo.get("manager").toString());
            depInfo.put("dep_admin_name", depInfo.get("manager_name") == null ? null : depInfo.get("manager_name").toString());
            depInfo.put("parent_dep_id", depInfo.get("upper_id") == null ? null : depInfo.get("upper_id").toString());
            depInfo.put("parent_dep_name", depInfo.get("upper") == null ? null : depInfo.get("upper").toString());
            depInfo.put("dep_code", depInfo.get("dep_code") == null ? null : depInfo.get("dep_code").toString());
            depInfo.put("dep_id", depInfo.get("dep_id") == null ? null : depInfo.get("dep_id"));
            depInfo.put("is_default", depInfo.get("is_default") == null ? null : depInfo.get("is_default").toString());
            String allUpperIds = department.getAllUpperIds();
            allUpperIds = allUpperIds == null ? "" : allUpperIds.trim();
            String[] upperIds = allUpperIds.split("\\|");
            depInfo.put("level", upperIds[0].length() > 0 ? upperIds.length + 1 : upperIds.length);
            Map<String, Object> retMap = new HashMap<>();
            retMap.put("dep_info", depInfo);
            modelMap.addAttribute("description", "获取部门基本信息，成功！");
            return setModelMap(modelMap, HttpCode.OK, retMap);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", "获取部门基本信息，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "业务员列表")
    @ApiImplicitParam(name = "param", value = "{\n" +
            "\"client_type\":\"\",\n" +
            "\"company_id\":\"\",\n" +
            "\"token\":\"\",\n" +
            "\"uid\":\"\",\n" +
            "}", required = true, dataType = "Map")
    @PostMapping("/api/customerOrder/dict/salesmanList")
    @RequiresPermissions(value = "enterprise:customer:customer_info:view")
    public Object salesmanList(@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Integer pCompanyId = param.get("company_id") == null || param.get("company_id").toString().isEmpty() ? null : Integer.parseInt(param.get("company_id").toString());
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());

            List<Map<String, Object>> mapList = companyService.selectSalesmanListByCompanyId(pCompanyId);
            Map<String, Object> retMap = new HashMap<>();
            retMap.put("list", mapList);
            modelMap.addAttribute("description", "业务员列表，成功！");
            return setModelMap(modelMap, HttpCode.OK, retMap);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", "业务员列表，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "查询客户公司")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/customerOrder/dict/cstCompanyList")
    @RequiresPermissions(value = "enterprise:customer:customer_info:view")
    public Object cstCompanyList(@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Integer pCompanyId = param.get("company_id") == null || param.get("company_id").toString().isEmpty() ? null : Integer.parseInt(param.get("company_id").toString());
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());
            String pCstName = param.get("cst_name") == null ? "" : param.get("cst_name").toString().trim();

            List<Map<String, Object>> mapList = companyService.selectCstCompanyByCstName(pCompanyId, pCstName);
            Map<String, Object> retMap = new HashMap<>();
            retMap.put("cst_list", mapList);
            modelMap.addAttribute("description", "查询客户公司，成功！");
            return setModelMap(modelMap, HttpCode.OK, retMap);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", "查询客户公司，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }
    @ApiOperation(value = "查询客户公司联系人列表")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/customerOrder/dict/cstCompanyContactor")
    public Object cstCompanyContactorList(@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Integer pCompanyId = param.get("company_id") == null || param.get("company_id").toString().isEmpty() ? null : Integer.parseInt(param.get("company_id").toString());
            Integer pCstCompanyId = param.get("cst_company_id") == null || param.get("cst_company_id").toString().isEmpty() ? null : Integer.parseInt(param.get("cst_company_id").toString());
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());
            String pType = param.get("type") == null ? "" : param.get("type").toString().trim();
            //0默认联系人，1其它联系人，2全部联系人
            if (!"0".equals(pType) && !"1".equals(pType) && !"2".equals(pType)) {
                modelMap.addAttribute("description", "类型 不正确，失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            List<Map<String, Object>> mapList = companyService.selectCstContactorByCustomerId(pCompanyId, pCstCompanyId, pType);
            Map<String, Object> retMap = new HashMap<>();
            retMap.put("contactor_list", mapList);
            modelMap.addAttribute("description", "查询客户公司联系人列表，成功！");
            return setModelMap(modelMap, HttpCode.OK, retMap);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelMap.addAttribute("description", "查询客户公司联系人列表，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "查询某部门包括其所有子部门的全部在职成员")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("/api/company/get-all-members")
    @RequiresPermissions(value = "enterprise:enterprise_manager:department:view")
    public Object getAllMembersOfDept(@RequestBody Map<String, Object>param, ModelMap modelMap) {
        try {
            //String pClientType = param.get("client_type") == null ? null : param.get("client_type").toString().trim();
            Integer pCompanyId = param.get("company_id") == null || param.get("company_id").toString().isEmpty() ? null : Integer.parseInt(param.get("company_id").toString());
            Integer pDepId = param.get("dep_id") == null || param.get("dep_id").toString().isEmpty() ? null : Integer.parseInt(param.get("dep_id").toString());
            String pDepCode = param.get("dep_code") == null ? null : param.get("dep_code").toString().trim();
            Long pUid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.parseLong(param.get("uid").toString());
            //String pCstName = param.get("cst_name") == null ? "" : param.get("cst_name").toString().trim();

            List<Map<String, Object>> mapList = companyService.selectAllAndSubUserCompanyByDepId(pCompanyId, pDepId, pDepCode);
            Map<String, Object> retMap = new HashMap<>();
            retMap.put("memberInfos", mapList);
            modelMap.addAttribute("description", "查询某部门包括其所有子部门的全部在职成员，成功！");
            return setModelMap(modelMap, HttpCode.OK, retMap);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询某部门包括其所有子部门的全部在职成员，失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


}


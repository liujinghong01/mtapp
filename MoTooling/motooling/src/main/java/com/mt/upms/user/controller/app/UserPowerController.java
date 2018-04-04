package com.mt.upms.user.controller.app;

import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.common.utils.StringUtils;
import com.mt.upms.user.service.UserPowerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api(value = "用户权限", description = "用户权限")
@RestController
@Scope(value = "prototype")
public class UserPowerController extends BaseController {
    @Autowired
    private UserPowerService userPowerService;

    @ApiOperation(value = "001 登陆后选择公司获取用户该公司所有的权限列表", notes = "")
    @ApiImplicitParam(name = "param", value = "{\"token\":\"\",\"company_id\":35,\"uid\":20}", required = true, dataType = "Map")
    @PostMapping("api/get_user_permission")
    public Object addOrder(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            
            Map<String, Object> map = new HashMap(16);
            map.put("company_id", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("company_name", param.get("company_name") == null ? null : param.get("company_name"));
            Map<String, Object> mapReturn = userPowerService.selectPermissionis(map);
            modelMap.addAttribute("description", "获取权限列表成功！");
            return setModelMap(modelMap, HttpCode.OK, mapReturn);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "获取权限列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "Q001  查询可分配权限模块名称列表", notes = "")
    @ApiImplicitParam(name = "param", value = "{\"token\":\"9f403678-0408-4526-873c-3ec64841f1cf\",\"company_id\":35,\"uid\":20,\"module_id\":4}", required = true, dataType = "Map")
    @PostMapping("api/get/permission_module/list")
//    @RequiresPermissions("enterprise:enterprise_manager:role_permission:view")
    public Object moduleList(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            
            Map<String, Object> map = new HashMap(16);
            map.put("company_id", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("moudle_id", param.get("moudle_id") == null ? null : param.get("moudle_id"));
            Map<String, Object> mapReturn = userPowerService.selectModuleList(map);
            modelMap.addAttribute("description", "获取可分配权限模块名称列表成功！");
            return setModelMap(modelMap, HttpCode.OK, mapReturn);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "获取可分配权限模块名称列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "Q002  权限修改分配", notes = "")
    @ApiImplicitParam(name = "param", value = "{\"token\":\"\",\"company_id\":35,\"uid\":20}", required = true, dataType = "Map")
    @PostMapping("api/distribution/permission")
//    @RequiresPermissions("enterprise:enterprise_manager:role_permission:update")
    public Object distributionPermission(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            
            Map<String, Object> map = new HashMap(16);
            map.put("company_id", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("query", param.get("query") == null ? null : param.get("query"));
            Map<String, Object> mapReturn = userPowerService.distributionPermission(map);
            modelMap.addAttribute("description", "权限修改分配成功！");
            return setModelMap(modelMap, HttpCode.OK, mapReturn);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "权限修改分配失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "分配角色", notes = "")
    @ApiImplicitParam(name = "param", value = "{\"token\":\"\",\"company_id\":35,\"uid\":20}", required = true, dataType = "Map")
    @PostMapping("api/add/role")
//    @RequiresPermissions("enterprise:enterprise_manager:role_permission:create")
    public Object addRole(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            
            Map<String, Object> map = new HashMap(16);
            map.put("company_id", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("query", param.get("query") == null ? null : param.get("query"));
            Map<String, Object> mapReturn = userPowerService.addRole(map);
            modelMap.addAttribute("description", "分配角色成功！");
            return setModelMap(modelMap, HttpCode.OK, mapReturn);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "分配角色失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "修改删除角色权限", notes = "修改删除角色权限")
    @ApiImplicitParam(name = "param", value = "{\"token\":\"\",\"uid\":137,\"company_id\":\"110\",\"query\":{\"dept_id\":\"1\",\"resource_id\":\"50\",\"role_id\":\"4\"}}", required = true, dataType = "Map")
    @PostMapping("api/delect/permission")
//    @RequiresPermissions("enterprise:enterprise_manager:role_permission:delete")
    public Object deletePermission(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            
            if (param.get("company_id") == null || param.get("company_id") == "") {
                modelMap.addAttribute("description", "缺少公司ID");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            Map<String, Object> query = (Map<String, Object>) param.get("query");
            if (query.get("dep_id") == null || query.get("dep_id") == "") {
                modelMap.addAttribute("description", "缺少部门ID");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            if (query.get("to_uid") == null || query.get("to_uid") == "") {
                modelMap.addAttribute("description", "缺少修改人员ID");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            if (query.get("role_code") == null || query.get("role_code") == "") {
                modelMap.addAttribute("description", "缺少角色CODE");
                return setModelMap(modelMap, HttpCode.FAIL);
            }

            query.put("company_id", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            query.put("uid", param.get("uid") == null ? null : param.get("uid"));
            boolean b = userPowerService.deletePermission(query);
            if (!b) {
                modelMap.addAttribute("description", "参数错误，删除失败！");
                return setModelMap(modelMap, HttpCode.FAIL);
            }
            modelMap.addAttribute("description", "删除角色权限成功！");
            return setModelMap(modelMap, HttpCode.OK);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "删除角色权限失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "删除自定义角色", notes = "删除自定义角色")
    @ApiImplicitParam(name = "param", value = "{\"token\":\"\",\"company_id\":35,\"uid\":20}", required = true, dataType = "Map")
    @PostMapping("api/delect/custom_role")
//    @RequiresPermissions("enterprise:enterprise_manager:role_permission:create")
    public Object delectCustomRole(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            
            boolean b = userPowerService.delectCustomRole(param);
            modelMap.addAttribute("description", "删除自定义角色成功！");
            return setModelMap(modelMap, HttpCode.OK);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "删除自定义角色失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "授权权限给角色", notes = "自定义角色？")
    @ApiImplicitParam(name = "param", value = "{\"token\":\"\",\"company_id\":35,\"uid\":20}", required = true, dataType = "Map")
    @PostMapping("api/role/add/permission")
//    @RequiresPermissions("enterprise:enterprise_manager:role_permission:create")
    public Object roleAddPermission(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            
            Map<String, Object> map = new HashMap(16);
            map.put("company_id", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id"));
            map.put("uid", param.get("uid") == null ? null : param.get("uid"));
            map.put("query", param.get("query") == null ? null : param.get("query"));
            Map<String, Object> mapReturn = userPowerService.addRoleAddPermission(map);
            modelMap.addAttribute("description", "授权权限给角色成功！");
            return setModelMap(modelMap, HttpCode.OK, mapReturn);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "授权权限给角色失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "查询角色列表", notes = "小于自己的角色")
    @ApiImplicitParam(name = "param", value = "{\"token\":\"\",\"role_code\":\"group_admin\"}", required = true, dataType = "Map")
    @PostMapping("api/role/get_role_list")
    public Object roleList(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            
            Map<String, Object> mapReturn =  userPowerService.getRoleList(param);
            modelMap.addAttribute("description", "查询角色列表成功！");
            return setModelMap(modelMap, HttpCode.OK, mapReturn);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            modelMap.addAttribute("description", "查询角色列表失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }

    @ApiOperation(value = "自定义角色", notes = "自定义角色")
    @ApiImplicitParam(name = "param", value = "{\"token\":\"\",\"role_code\":\"group_admin\"}", required = true, dataType = "Map")
    @PostMapping("api/add/custom_roles")
    public Object customRoles(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            
            Map<String, Object> mapReturn = userPowerService.addCustomRoles(param);
            modelMap.addAttribute("description", "自定义角色成功！");
            return setModelMap(modelMap, HttpCode.OK, mapReturn);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            modelMap.addAttribute("description", "自定义角色失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "给部门分配资源", notes = "给部门分配资源")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("api/add_update/dep_resource")
    public Object addDepResource(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            
            Map<String, Object> mapReturn = userPowerService.addDepResource(param);
            modelMap.addAttribute("description", "给部门分配资源成功！");
            return setModelMap(modelMap, HttpCode.OK, mapReturn);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            modelMap.addAttribute("description", "给部门分配资源失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


    @ApiOperation(value = "获取所有资源角色", notes = "所有资源角色")
    @ApiImplicitParam(name = "param", value = "", required = true, dataType = "Map")
    @PostMapping("api/role_resource/selectList")
    public Object selectRoleResource(@RequestBody Map<String, Object> param, ModelMap modelMap) {
        try {
            
            Map<String, Object> mapReturn = userPowerService.selectRoleResource(param);
            modelMap.addAttribute("description", "获取所有资源角色成功！");
            return setModelMap(modelMap, HttpCode.OK, mapReturn);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            modelMap.addAttribute("description", "获取所有资源角色失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }
    }


}

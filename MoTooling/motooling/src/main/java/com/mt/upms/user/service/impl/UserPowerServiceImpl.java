package com.mt.upms.user.service.impl;

import com.mt.cms.common.dao.CompanyMapper;
import com.mt.common.customenum.RoleEnum;
import com.mt.common.utils.DateUtil;
import com.mt.common.utils.RedisUtil;
import com.mt.common.utils.StringUtils;
import com.mt.upms.common.dao.PermissionMapper;
import com.mt.upms.common.dao.RoleMapper;
import com.mt.upms.common.dao.UserRoleMapper;
import com.mt.upms.common.model.ChildModuleBeans;
import com.mt.upms.common.model.PermissionBeans;
import com.mt.upms.common.model.User;
import com.mt.upms.user.service.UserPowerService;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;

@Service
public class UserPowerServiceImpl implements UserPowerService {
    private static Logger logger = LoggerFactory.getLogger(UserPowerServiceImpl.class);
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private CompanyMapper companyMapper;

    /**
     * 授权权限给角色
     *
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> addRoleAddPermission(Map<String, Object> map) {
        Map<String, Object> query = (Map<String, Object>) map.get("query");
        List<Integer> permissionIds = (List<Integer>) query.get("permission_ids");
        if (permissionIds.contains(1)) {
            query.put("permission_ids", "1");
        } else if (permissionIds.size() > 0) {
            String s = permissionIds.toString();
            query.put("permission_ids", s.substring(1, s.length() - 1));
        } else {
            query.put("permission_ids", "4");
        }
        query.put("company_id", map.get("company_id"));
        query.put("created_at", DateUtil.getDateTime());
        roleMapper.addRoleResource(query);

        return null;
    }

    /**
     * 添加自定义角色
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> addCustomRoles(Map<String, Object> param) {
        Map<String, Object> query = (Map<String, Object>) param.get("query");
        Map<String, Object> roleData = new HashMap<>(16);
        List<Map<String, Object>> resources = (List<Map<String, Object>>) query.get("resources");
        Iterator<Map<String, Object>> iterator = resources.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> next = iterator.next();
            String permission_ids = next.get("permission_ids").toString();
            next.put("permission_ids", permission_ids.substring(1, permission_ids.length() - 1));
        }
        roleData.put("created_at", DateUtil.getDateTime());
        //生成8位随机岁。 role_code
        roleData.put("role", "custom_" + StringUtils.generateShortUuid());
        roleData.put("company_id", param.get("company_id"));
        roleData.put("company_name", param.get("company_name"));
        roleData.put("role", "custom_roles");
        roleData.put("description", "自定义权限");
        roleData.put("name", query.get("custom_name"));
        roleMapper.addCustomRole(roleData);

        query.put("role_id", roleData.get("id"));
        query.put("company_id", param.get("company_id"));
        query.put("company_name", param.get("company_name"));
        query.put("created_at", DateUtil.getDateTime());
        permissionMapper.addRoleResource(query);
        return roleData;
    }

    /**
     * 给部门分配资源
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> addDepResource(Map<String, Object> param) {
        permissionMapper.addDepResource(param);
        return null;
    }


    /**
     * 添加角色
     *
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> addRole(Map<String, Object> map) {
        Map<String, Object> query = (Map<String, Object>) map.get("query");
        query.put("company_id", map.get("company_id"));
        query.put("created_at", DateUtil.getDateTime());
        Map<String, Object> returnMap = new HashMap<>();
        try {
            Integer integer = userRoleMapper.insertIgnoreRoleResource(query);
            if (integer < 1) {
                throw new SQLException("SQL错误");
            } else {
                returnMap.put("role_id", map.get("role_id"));
                returnMap.put("to_uid", map.get("to_uid"));
                return returnMap;
            }
        } catch (Exception ex) {
            ex.getMessage();
            returnMap.put("description", "授权权限给角色失败");
            return returnMap;
        }
    }

    /**
     * 修改 删除角色
     *
     * @param map
     * @return
     */
    @Override
    public boolean deletePermission(Map<String, Object> map) {

        int roleId = roleMapper.getRoleId(map);
        map.put("role_id", roleId);
        map.put("updated_at", DateUtil.getCurrentDateString());
        boolean whether = userRoleMapper.updateRoleId(map);
        return whether;
    }


    /**
     * 权限分配
     *
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> distributionPermission(Map<String, Object> map) {
        Map<String, Object> query = (Map<String, Object>) map.get("query");

        List<Map<String, Object>> list = (List<Map<String, Object>>) query.get("resource_permission_list");
        query.put("company_id", map.get("company_id"));
        query.put("uid", map.get("uid"));
        query.put("created_at", DateUtil.getDateTime());

        for (Map<String, Object> param : list) {
            List<Integer> permissionIds = (List<Integer>) param.get("permission_ids");
            if (permissionIds.contains(1)) {
                query.put("permission_ids", "1");
            } else if (permissionIds.size() > 0) {
                String s = permissionIds.toString();
                query.put("permission_ids", s.substring(1, s.length() - 1));
            } else {
                query.put("permission_ids", "4");
            }
            query.put("resource_id", param.get("resource_id"));
            roleMapper.addRoleResource(query);
        }
        userRoleMapper.insertIgnoreRoleResource(query);

        return null;
    }


    /**
     * 查询可分配的模块权限列表
     */
    @Override
    public Map<String, Object> selectModuleList(Map<String, Object> map) {
        ChildModuleBeans childModuleBeans = recursiveTreeList(Integer.valueOf(map.get("moudle_id").toString()));
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("childModule", childModuleBeans);
        return returnMap;
    }


    /**
     * 获取所有资源 角色  只有企业admin 有权限进行操作
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> selectRoleResource(Map<String, Object> param) {
        int companyId = Integer.valueOf(param.get("company_id").toString());
        // 查寻是否有新的角色 如果没有 直接去缓存中的数据
        List<Map<String, Object>> roleList = roleMapper.selectNewRole(companyId);
        Map<String, Object> returnMap = new HashMap<>(16);
        if (roleList.size() <= 0) {
            // 从缓存中取默认值
            String defaultRole = RedisUtil.getString("default_role");
            String defaultResource = RedisUtil.getString("default_resource");
            if (defaultRole != null && defaultResource != null) {
                returnMap.put("resource", defaultRole);
                returnMap.put("reoleResource", defaultResource);
                return returnMap;
            } else {
                throw new IllegalArgumentException("没有原始参数");
            }
        } else {


        }
        return param;
    }

    /**
     * 删除自定义角色
     *
     * @param param
     * @return
     */
    @Override
    public boolean delectCustomRole(Map<String, Object> param) {
        return roleMapper.delectCustomRole(param);
    }

    /**
     * 角色列表
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> getRoleList(Map<String, Object> param) {
        Object roleCode = param.get("role_code") == null ? null : param.get("role_code");
        RoleEnum roleEnum = RoleEnum.valueOf(roleCode.toString().toUpperCase());
        List<Map<String, Object>> enumList = roleEnum.getRoleMap(roleEnum.getRoldCode());
        List<Map<String, Object>> roleList = roleMapper.selectNewRole(Integer.valueOf(param.get("company_id").toString()));
        if (roleList.size() > 0) {
            for (Map<String, Object> roleMap : roleList) {
                enumList.add(roleMap);
            }
        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("roles", enumList);
        return returnMap;
    }


    /**
     * 登陆后查询用户的所有权限
     */
    @Override
    public Map<String, Object> selectPermissionis(Map<String, Object> map) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        PrincipalCollection principals = subject.getPrincipals();
        user.setUid(Long.valueOf(map.get("uid").toString()));
        user.setCompanyId(Long.valueOf(map.get("company_id").toString()));
        if(map.get("company_name") != null && map.get("company_name") != ""){
            user.setCompanyName(map.get("company_name").toString());
        }else {
            user.setCompanyName(companyMapper.selectByCompanyId(Integer.valueOf(map.get("company_id").toString())).getCompanyName());
        }

        String realmName = principals.getRealmNames().iterator().next();
        PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
        subject.runAs(newPrincipalCollection);
        // 根据用户ID 和公司 查出 用户 在公司的 角色 和权限
        List<Map<String, Object>> companyRolePermissionList = permissionMapper.findPermissionByUserId(user);
        List<Map<String, Object>> departmentResourceList = permissionMapper.findDepartmentResource(user);
        List<Map<String, Object>> roleByUserId = roleMapper.RoleByUserId(user);
        if (roleByUserId.size() > 0) {
            for (Map<String, Object> roleList : roleByUserId) {
                if (!RoleEnum.ENTERPRISE_ADMIN.getCode().equals(roleList.get("code").toString())) {
                    companyRolePermissionList.retainAll(departmentResourceList);
                }
            }
        }
        Map<String, Object> returnMap = new HashMap<>(16);
        Map<String, Object> jsonMap = new HashMap<>(16);
        // 如果是企业admin 直接返回，不进行后续操作
        for (Map<String, Object> roleUserid : roleByUserId) {
            if ("enterprise_admin".equals(roleUserid.get("code").toString())) {
                returnMap.put("role", roleByUserId);
                Set<String> permissionsSet = new HashSet<>();
                permissionsSet.add("enterprise:*");
                jsonMap.put("permissions", permissionsSet);
                jsonMap.put("company_id", map.get("company_id"));
                jsonMap.put("role", roleByUserId);
                RedisUtil.setMapString("user_role_permission", "role_permissions_uid_" + map.get("uid") + "_company_id_" + map.get("company_id"), JSONObject.fromObject(jsonMap).toString());
                return returnMap;
            }
        }

        Set<String> permSet = new HashSet<>(16);
        Set<String> permissionsSet = new HashSet<>(10);
        JSONObject json = null;
        List<ChildModuleBeans> cbList = new ArrayList<>(10);
        Set<Integer> parentSet = new HashSet<>(16);
        for (Map<String, Object> companyRolePermission : companyRolePermissionList) {
            parentSet.add(Integer.valueOf(companyRolePermission.get("parent_id").toString()));
            StringBuilder sb = new StringBuilder(companyRolePermission.get("identity").toString());
            ChildModuleBeans childModuleBeans = new ChildModuleBeans();
            // 拼接获取父code
            isParentId(companyRolePermission, sb);
            if (companyRolePermission != null) {
                //	 1.所有权限 admin  2.新增 create  3.修改 update  4.删除 delete 5 查看 view  6.审核 audit    可以写死？
                if ("1".equals(companyRolePermission.get("permission_ids").toString())) {
                    // 添加 具体权限
                    PermissionBeans permissionBeans = addPermissions(companyRolePermission);
                    permSet.add(sb.toString());
                    childModuleBeans.setPermission(permissionBeans);
                    childModuleBeans.setName(companyRolePermission.get("name").toString());
                    childModuleBeans.setIdentity(companyRolePermission.get("identity").toString());
                    childModuleBeans.setId(Integer.valueOf(companyRolePermission.get("role_id").toString()));
                    childModuleBeans.setParentId(Integer.valueOf(companyRolePermission.get("parent_id").toString()));
                    cbList.add(childModuleBeans);
                } else {
                    // 获取权限
                    PermissionBeans permissionBeans = addPermissions(companyRolePermission);
                    //等到 权限树
                    childModuleBeans = recursiveTree(Integer.valueOf(companyRolePermission.get("resource_id").toString()), permissionBeans);
                    childModuleBeans.setPermission(permissionBeans);
                    getSetIdentity(companyRolePermission, sb, permissionsSet);
                    permissionsSet = addPermissions(companyRolePermission, permissionsSet);
                    cbList.add(childModuleBeans);
                    permSet.addAll(permissionsSet);
                    permissionsSet.clear();
                }
            }
        }

        List<ChildModuleBeans> treeList = new ArrayList<>(10);
        for (Integer paretId : parentSet) {
            ChildModuleBeans moduleBeans = permissionMapper.getreeNode(paretId);
            treeList.add(moduleBeans);
        }
        ChildModuleBeans returnModule = new ChildModuleBeans();
        returnModule.setId(4);
        returnModule.setIdentity("enterprise");
        returnModule.setName("企业资源");
        treeList.add(returnModule);
        treeList.addAll(cbList);

        jsonMap.put("permissions", permSet);
        jsonMap.put("company_id", map.get("company_id"));
        jsonMap.put("role", roleByUserId);
        json = JSONObject.fromObject(jsonMap);
        RedisUtil.setMapString("user_role_permission", "role_permissions_uid_" + map.get("uid") + "_company_id_" + map.get("company_id"), json.toString());
        returnMap.put("role", roleByUserId);
        returnMap.put("permission", treeList);
        return returnMap;
    }


    /**
     * 添加 具体权限
     *
     * @param companyRolePermission
     * @return
     */
    private PermissionBeans addPermissions(Map<String, Object> companyRolePermission) {
        PermissionBeans permissionBeans = new PermissionBeans();
        String[] permissionIds = companyRolePermission.get("permission_ids").toString().split(",");
        for (String s : permissionIds) {
            //2.新增 create  3.修改 update  4.删除 delete 5 查看 view  6.审核 audit
            if ("2".equals(s)) {
                permissionBeans.setAdd(1);
            } else if ("3".equals(s)) {
                permissionBeans.setUpdate(1);
            } else if ("4".equals(s)) {
                permissionBeans.setDelete(1);
            } else if ("5".equals(s)) {
                permissionBeans.setView(1);
            } else if ("6".equals(s)) {
                permissionBeans.setApprove(1);
            } else if ("1".equals(s)) {
                permissionBeans.setAdd(1);
                permissionBeans.setUpdate(1);
                permissionBeans.setDelete(1);
                permissionBeans.setView(1);
                permissionBeans.setApprove(1);
            }
        }
        return permissionBeans;
    }


    private String getPermissionString(Map<String, Object> param) {
        String[] permissionIds = param.get("permission_ids").toString().split(",");
        StringBuilder sb = new StringBuilder("00000");
        for (String s : permissionIds) {
            if ("2".equals(s)) {
                sb.replace(0, 1, "1");
            } else if ("3".equals(s)) {
                sb.replace(1, 2, "1");
            } else if ("4".equals(s)) {
                sb.replace(2, 3, "1");
            } else if ("5".equals(s)) {
                sb.replace(3, 4, "1");
            } else if ("6".equals(s)) {
                sb.replace(4, 5, "1");
            } else if ("1".equals(s)) {
                return "11111";
            }
        }
        return sb.toString();
    }

    /**
     * 递归返回 树结构对象
     *
     * @param id
     * @return
     */
    public ChildModuleBeans recursiveTree(Integer id, PermissionBeans permissionBeans) {
        ChildModuleBeans childs = permissionMapper.getreeNode(id);
        List<ChildModuleBeans> childTreeNodes = permissionMapper.queryTreeNode(id);
        // 遍历子节点
        for (ChildModuleBeans child : childTreeNodes) {
            ChildModuleBeans n = recursiveTree(child.getId(), permissionBeans); //递归
            n.setPermission(permissionBeans);
            childs.getChildModule().add(n);
        }
        return childs;
    }


    public ChildModuleBeans recursiveTreeList(Integer id) {
        ChildModuleBeans childs = permissionMapper.getreeNode(id);
        List<ChildModuleBeans> childTreeNodes = permissionMapper.queryTreeNode(id);
        Iterator<ChildModuleBeans> iterator = childTreeNodes.iterator();
        while (iterator.hasNext()) {
            ChildModuleBeans next = iterator.next();
            ChildModuleBeans n = recursiveTreeList(next.getId()); //递归
            childs.getChildModule().add(n);
        }

        return childs;
    }


    /**
     * 得到父级identity
     *
     * @param companyRolePermission
     * @param sb
     */
    private void isParentId(Map<String, Object> companyRolePermission, StringBuilder sb) {
        if (companyRolePermission.get("parent_ids") != null && companyRolePermission.get("parent_ids") != "") {
            String[] parentIds = companyRolePermission.get("parent_ids").toString().split("/");
            // 根据 parent_ids 查出 父级以上 identity
            List<Map<String, Object>> identityList = permissionMapper.selectParentIdsIdentity(parentIds);
            for (Map<String, Object> map : identityList) {
                sb.insert(0, map.get("identity") + ":");
            }
        }
    }

    /**
     * 递归 添加权限
     *
     * @param param
     * @param sbParam
     * @param set
     */
    private void getSetIdentity(Map<String, Object> param, StringBuilder sbParam, Set<String> set) {
        if (param != null) {
            if (param.get("resource_id") != null && param.get("resource_id") != "") {
                List<Map<String, Object>> childrenList = permissionMapper.getChildren(Long.valueOf(param.get("resource_id").toString()));
                if (childrenList.size() > 0) {
                    for (Map<String, Object> childer : childrenList) {
                        StringBuilder sb = new StringBuilder(sbParam);
                        sb.append(":").append(childer.get("identity"));
                        getSetIdentity(childer, sb, set);
                        set.add(sb.toString());
                    }
                } else {
                    set.add(sbParam.toString());
                }
            }
        }
    }

    /**
     * 权限具体功能添加
     *
     * @param companyRolePermission
     * @param set
     * @return
     */
    private Set<String> addPermissions(Map<String, Object> companyRolePermission, Set<String> set) {
        Set<String> returnSet = new HashSet<>(10);
        StringBuilder sb = new StringBuilder(":");
        String[] permissionIds = companyRolePermission.get("permission_ids").toString().split(",");
        for (String s : permissionIds) {
            // 2.新增 create  3.修改 update  4.删除 delete 5 查看 view  6.审核 audit
            if ("2".equals(s)) {
                sb.append("create,");
            } else if ("3".equals(s)) {
                sb.append("update,");
            } else if ("4".equals(s)) {
                sb.append("delete,");
            } else if ("5".equals(s)) {
                sb.append("view,");
            } else if ("6".equals(s)) {
                sb.append("audit,");
            } else if ("1".equals(s)) {
                sb.append("*,");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        for (String addSet : set) {
            returnSet.add(addSet + sb.toString());
        }
        return returnSet;
    }
}

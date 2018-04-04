package com.mt.upms.user.service;

import java.util.Map;

public interface UserPowerService {
    // 登陆后查询用户的所有权限
    Map<String,Object> selectPermissionis(Map<String, Object> map);
    // 查询可分配的模块权限列表
    Map<String,Object> selectModuleList(Map<String, Object> map);
    // 修改权限
    Map<String,Object> distributionPermission(Map<String, Object> map);
    // 添加角色
    Map<String,Object> addRole(Map<String, Object> map);
    // 删除角色权限
    boolean deletePermission(Map<String, Object> map);
    // 授权权限给角色
    Map<String,Object> addRoleAddPermission(Map<String, Object> map);
    // 添加自定义角色
    Map<String,Object> addCustomRoles(Map<String, Object> param);
    // 给部门分配资源
    Map<String,Object> addDepResource(Map<String, Object> param);
    // 获取所有角色，资源
    Map<String,Object> selectRoleResource(Map<String, Object> param);
    // 删除自定义角色
    boolean delectCustomRole(Map<String, Object> param);
    // 角色列表
    Map<String,Object> getRoleList(Map<String, Object> param);
}

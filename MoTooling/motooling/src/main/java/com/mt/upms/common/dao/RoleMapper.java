package com.mt.upms.common.dao;

import com.mt.upms.common.model.Role;
import com.mt.upms.common.model.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RoleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    Role selectByPrimaryKey(Long id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);



    int insertSelective(Role record);

    int updateByPrimaryKeySelective(Role record);

    Set<String> findRoleByUserId(User user);

    List<Role> findNowAllPermission(Map<String, Object> map);

    void initData();

    List<Map<String,Object>> RoleByUserId(User user);
    //新增角色资源
    Integer addRoleResource(Map<String, Object> map);

    Integer updatePermission(Map<String, Object> map);
    // 根据 company_id 和 role 的code 查出 role_id
    Long selectRoleId(Map<String,Object> map);
    //添加自定义角色
    Integer addCustomRole(Map<String, Object> roleData);
    // 得到公司所有的角色
    List<Map<String,Object>> selectRoleCode(int companyId);
    // 查寻是否有新的角色
    List<Map<String,Object>> selectNewRole(int companyId);
    // 获取对应公司普通员工的ID
    int getEmployee(Integer companyId);
    // 获取对应code 角色id
    int getRoleId(Map<String, Object> map);
    // 删除自定义角色
    boolean delectCustomRole(Map<String, Object> param);
}
package com.mt.upms.common.dao;

import com.mt.upms.common.model.ChildModuleBeans;
import com.mt.upms.common.model.Permission;
import com.mt.upms.common.model.User;
import com.mt.upms.permission.bo.UPermissionBo;

import java.util.List;
import java.util.Map;

public interface PermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Long id);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);


    int insertSelective(Permission record);
    int updateByPrimaryKeySelective(Permission record);
    List<UPermissionBo> selectPermissionById(Long id);
    //根据用户ID  公司ID 获取公司对应的权限集合
    List<Map<String,Object>> findPermissionByUserId(User user);


    List<Map<String,Object>> findDepartmentResource(User user);

    Map<String,Object> selectParentIdentity(Long parentId);
    //查询父级所有identiey
    List<Map<String,Object>> selectParentIdsIdentity(String[] parentIds);
    //查询 子目录
    List<Map<String,Object>> getChildren(Long id);

    List<ChildModuleBeans> queryTreeNode(Integer id);

    ChildModuleBeans getreeNode(Integer id);
    //给自定义角色  添加资源
    int addRoleResource(Map<String, Object> query);
    //给 新增的部门（非缺省部门）添加资源
    int addDepResource(Map<String, Object> param);
}
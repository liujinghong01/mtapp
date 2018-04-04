package com.mt.upms.common.dao;

import com.mt.upms.common.model.RolePermission;

import java.util.List;
import java.util.Map;

public interface RolePermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RolePermission record);

    RolePermission selectByPrimaryKey(Long id);

    List<RolePermission> selectAll();

    int updateByPrimaryKey(RolePermission record);


    int insertSelective(RolePermission record);

    List<RolePermission> findRolePermissionByPid(Long id);

    List<RolePermission> findRolePermissionByRid(Long id);

    List<RolePermission> find(RolePermission entity);

    int deleteByPid(Long id);

    int deleteByRid(Integer id);

    int delete(RolePermission entity);

    int deleteByRids(Map<String, Object> resultMap);



}
package com.mt.upms.permission.service;


import com.mt.common.core.mybatis.page.Pagination;
import com.mt.upms.common.model.Permission;
import com.mt.upms.permission.bo.UPermissionBo;

import java.util.List;
import java.util.Map;

public interface PermissionService {

	int deleteByPrimaryKey(Long id);

	Permission insert(Permission record);

    Permission insertSelective(Permission record);

    Permission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

	Map<String, Object> deletePermissionById(String ids);

	Pagination<Permission> findPage(Map<String, Object> resultMap, Integer pageNo,
									 Integer pageSize);
	List<UPermissionBo> selectPermissionById(Long id);

	Map<String, Object> addPermission2Role(Integer roleId, String ids);

	Map<String, Object> deleteByRids(String roleIds);
}

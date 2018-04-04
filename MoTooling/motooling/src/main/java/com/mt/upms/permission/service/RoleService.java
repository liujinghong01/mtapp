package com.mt.upms.permission.service;



import com.mt.common.core.mybatis.page.Pagination;
import com.mt.upms.common.model.Role;
import com.mt.upms.common.model.User;
import com.mt.upms.permission.bo.RolePermissionAllocationBo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RoleService {

	int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

	Pagination<Role> findPage(Map<String, Object> resultMap, Integer pageNo,
							   Integer pageSize);

	Map<String, Object> deleteRoleById(String ids);

	Pagination<RolePermissionAllocationBo> findRoleAndPermissionPage(
            Map<String, Object> resultMap, Integer pageNo, Integer pageSize);
	//根据用户ID查询角色（role），放入到Authorization里。
	Set<String> findRoleByUserId(User user);

	List<Role> findNowAllPermission();
	//初始化数据
	void initData();
}

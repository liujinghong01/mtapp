package com.mt.upms.common.dao;

import com.mt.upms.common.model.UserRole;

import java.util.List;
import java.util.Map;

public interface UserRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);


    /**
     *  Added by fhk on 2.17.9.19
      */

    int deleteByUserId(Long id);

    int deleteRoleByUserIds(Map<String, Object> resultMap);

    List<Long> findUserIdByRoleId(Integer id);

    List<UserRole> selectByCompanyIdAndUid(Map<String, Object>dataMap);

    //Alnwick       新增角色 资源表 。 根据索引 insert ignore  没有就新增，有就不做操作
    int insertIgnoreRoleResource(Map<String, Object> roleResourceMap);
    // 如果人员修改了部门，角色变更为普通员工
    int updateDepId(Map<String, Object> param);
    // 修改角色
    boolean updateRoleId(Map<String, Object> map);

    UserRole selectByCompanyIdAndUidAndRoleId(Map map);

    UserRole selectByCompanyIdAndDepIdAndUid(Map map);
    // 查出当前角色code
    String getRoleCode(Map<String, Object> userRoleMap);

    List<UserRole> selectByCompanyIdAndDepIdAndRoleCode(Map map);
    //人员是否在该部门存在相同角色user_role
    int selectRoleExistence(UserRole userRole);

    //是否具有某个角色
    Integer isHasRole(Map map);
}
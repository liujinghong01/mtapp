package com.mt.cms.common.dao;

import com.mt.cms.common.model.Department;

import java.util.List;
import java.util.Map;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Integer depId);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Integer depId);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);


    // Added by fhk on 2017.9.15
    Department selectByDepName(Map<String, Object> dataMap);

    Integer getMaxDepId();

    int insertDepartment(Department record);

    //返回本部门的直接下级部门
    List<Department> selectByUpperDepId(Map<String, Object> dataMap);

    //得到某部门有所有人员，包括其下下级部门人员
    Integer getAllUserCountByDepId(Map<String, Object>  dataMap);

    int deleteBatch(Map<String, Object> map);

    Integer  updateDepartment(Map<String,Object> map);

    Integer  addDepartment(Map<String,Object> map);

    Department selectByDepCode(Map<String,Object> map);

    List<Department> selectValidUserCompanyByDepIdAndUid(Map map);

    Department selectByDepCodeIdAndDepCode(Map map);
}
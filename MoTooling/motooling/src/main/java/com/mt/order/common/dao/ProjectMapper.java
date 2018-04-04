package com.mt.order.common.dao;

import com.mt.order.common.model.Project;

import java.util.List;
import java.util.Map;

public interface ProjectMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Project record);

    int insertSelective(Project record);

    Project selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);

    List<Map<String,Object>> selectProjectList(Map<String, Object> param);

    Integer selectProjectTotalCount(Map<String, Object> param);

    boolean deleteProjectNo(Map<String, Object> param);

    int addModify(Map<String, Object> map);

    int updateModify(Map<String, Object> map);

    Map<String,Object> queryHead(Integer projectId);
    //查询单号
    String selectProjectNo(Map<String, Object> map);
}
package com.mt.order.common.dao;

import com.mt.order.common.model.ProjectNotHwProd;

import java.util.List;
import java.util.Map;

public interface ProjectNotHwProdMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProjectNotHwProd record);

    int insertSelective(ProjectNotHwProd record);

    ProjectNotHwProd selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProjectNotHwProd record);

    int updateByPrimaryKey(ProjectNotHwProd record);

    List<Map<String,Object>> selectProdList(Integer param);

    int addProd(Map<String, Object> obj);

    int updateProd(Map<String, Object> obj);
    //批量删除 制品信息
    boolean updateStauts(List<Integer> list);
}
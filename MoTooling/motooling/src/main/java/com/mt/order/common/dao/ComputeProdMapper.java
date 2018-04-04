package com.mt.order.common.dao;

import com.mt.order.common.model.ComputeProd;

import java.util.List;
import java.util.Map;

public interface ComputeProdMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ComputeProd record);

    int insertSelective(ComputeProd record);

    ComputeProd selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ComputeProd record);

    int updateByPrimaryKey(ComputeProd record);

    void insertList(Map<String, Object> map);

    List<Map<String,Object>> selectProd(Integer compute_id);

    Boolean deleteComputePart(Map<String, Object> map);

    List<ComputeProd> selectComputeProdsByComputeId(Long computeId);

    int updateProdList(Map<String, Object> objMap);
}
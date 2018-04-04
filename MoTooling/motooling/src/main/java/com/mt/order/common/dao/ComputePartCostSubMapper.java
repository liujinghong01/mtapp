package com.mt.order.common.dao;

import com.mt.order.common.model.ComputePartCostSub;

import java.util.List;
import java.util.Map;

public interface ComputePartCostSubMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ComputePartCostSub record);

    int insertSelective(ComputePartCostSub record);

    ComputePartCostSub selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ComputePartCostSub record);

    int updateByPrimaryKey(ComputePartCostSub record);

    Integer addPartCostSub(Map<String, Object> subMap);

    List<Map<String,Object>> selectPartCostSub(Integer id);

    Boolean deletePartSub(Map<String, Object> map);

    int updatePartCostSub(Map<String, Object> subMap);

}
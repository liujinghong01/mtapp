package com.mt.order.common.dao;

import com.mt.order.common.model.ComputeCost;

import java.util.Map;

public interface ComputeCostMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ComputeCost record);

    int insertSelective(ComputeCost record);

    ComputeCost selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ComputeCost record);

    int updateByPrimaryKey(ComputeCost record);

    int addComputeCost(Map<String, Object> map);

    int updateComputeCost(Map<String, Object> map);

    void updateStuffCost(Map<String, Object> map);

    //统计材料费
    Double selectSumStuffCost(Integer compute_id);

    ComputeCost selectCostDetail(Integer computeId);
}
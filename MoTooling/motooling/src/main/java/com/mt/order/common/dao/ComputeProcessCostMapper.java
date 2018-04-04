package com.mt.order.common.dao;

import com.mt.order.common.model.ComputeProcessCost;

import java.util.HashMap;
import java.util.List;

public interface ComputeProcessCostMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ComputeProcessCost record);

    int insertSelective(ComputeProcessCost record);

    ComputeProcessCost selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ComputeProcessCost record);

    int updateByPrimaryKey(ComputeProcessCost record);

    List<ComputeProcessCost> selectDetail(Long computeId);

    int addProcessCost(HashMap<String, Object> hashMap);

    int updateProcessCost(HashMap<String, Object> hashMap);

    Double selectSumProcessCost(Integer compute_id);

    int delProcess(List<Integer> list);
}
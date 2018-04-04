package com.mt.order.bl.service;

import java.util.Map;

public interface ComputePartCostService {
    //新增零部件明细及其子部件
    Map<String,Object> addAndUpdateComputePartCost(Map<String, Object> map) throws IllegalAccessException;

    //查询零部件明细及其子部件
    Map<String,Object> selectDetailPart(Map<String, Object> map);

    Map<String,Object> selectGetPartList(Integer compute_id);

    Boolean deletePartSub(Map<String, Object> map);

    Boolean deletePart(Map<String, Object> map);

    int deleteEditionPart(Long computeId);
}

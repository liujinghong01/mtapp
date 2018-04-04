package com.mt.order.bl.service;

import java.util.Map;

public interface ComputeProcessCostService {
    Boolean addAndUpdate(Map<String, Object> map);

    Map<String,Object> selectDetail(Long computeId);

    Boolean delProcess(Map<String, Object> map);
}

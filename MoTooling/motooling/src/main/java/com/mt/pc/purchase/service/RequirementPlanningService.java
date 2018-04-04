package com.mt.pc.purchase.service;

import java.util.Map;

public interface RequirementPlanningService {
    Map<String,Object> selectRequirePlanList(Map<String, Object> map);

    Map<String,Object> selectRequirePlanDeatil(Map<String, Object> map);

   boolean deleteRequire(Integer requireId);


}

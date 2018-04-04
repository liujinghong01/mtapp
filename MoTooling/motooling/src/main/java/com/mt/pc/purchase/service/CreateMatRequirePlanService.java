package com.mt.pc.purchase.service;

import com.mt.pc.common.model.PcRequirePlan;

public interface CreateMatRequirePlanService {

    boolean createMatRequirePlanfromRequest(Long requestId, Long uid);

    boolean createMatRequirePlanfromMold(Integer companyId, String moldNo, Long uid);

    boolean createMatRequirePlanfromBom(PcRequirePlan pcRequirePlan);
}

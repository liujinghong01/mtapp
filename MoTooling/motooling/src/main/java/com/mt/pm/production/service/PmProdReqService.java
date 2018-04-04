package com.mt.pm.production.service;


import com.mt.pm.common.model.PmProdReq;

import java.util.Map;

public interface PmProdReqService {
    //查询模具物料需求
    Map<String,Object>   selectMouldMatterDemand(Map<String,Object> map);

    boolean createPmProdReqfromBom(PmProdReq pmProdReq);



    Map<String, Object> selectProdReqByBomConditions(Map<String, Object> map);
    Map<String, Object> selectProdReqByMatConditions(Map<String, Object> map);

    Map<String, Object> selectProdReqByNode(Map<String, Object> map);

    PmProdReq selectProdReqByPrimaryKey(Long reqId);
}

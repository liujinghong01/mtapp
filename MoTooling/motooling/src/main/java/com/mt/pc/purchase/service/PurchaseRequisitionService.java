package com.mt.pc.purchase.service;

import java.util.List;
import java.util.Map;

public interface PurchaseRequisitionService {
    Map<String,Object> addAndupdateModefy(Map<String, Object> map);

    Boolean deleteRequest(Long requestId);

    Boolean deleteRequestLine(List<Long> lineIds);
    //查询请购单及 请购单子表
    Map<String,Object> queryRequest(Long requestId);
    //请购单列表
    Map<String,Object> queryRequestList(Map<String, Object> map);
    //参考请购单
    Map<String,Object> addRequestRefurHis(Map<String, Object> map);
    //新增修改请购单行明细信息
    Map<String,Object> addAndUpdateModifyLine(Map<String, Object> map);
}

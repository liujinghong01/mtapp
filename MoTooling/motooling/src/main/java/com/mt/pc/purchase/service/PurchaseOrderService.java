package com.mt.pc.purchase.service;

import java.util.Map;

public interface PurchaseOrderService {
    Map<String,Object> selectPucchOrderList(Map<String, Object> map);

    Map<String,Object> selectPurchOrderDetail(Map<String, Object> map);
    //删除采购单
    Boolean deletePurchOrder(Integer purchId);
    //新增修改采购单
    Map<String,Object> addPurchOreder(Map<String, Object> map);

    int sendPurchOrder(Long purchOrderId, Long uid);
}

package com.mt.pc.purchase.service;

import java.util.Map;

public interface DeliveryNoteService {
    //查询销售定单列表
    Map<String,Object> salesOrderList(Map<String, Object> map);
    //生成送货单
    Map<String,Object> createDeliveryOrder(Map<String, Object> map);
}

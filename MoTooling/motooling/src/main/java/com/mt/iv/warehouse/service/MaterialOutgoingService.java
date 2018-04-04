package com.mt.iv.warehouse.service;

import java.util.Map;

public interface MaterialOutgoingService {
    // 查询出库初始数据
    Map<String,Object> selectIvQueryInitData(Map<String, Object> map);
    // 根据单号出库
    Map<String,Object> addIvBillInStore(Map<String, Object> map);
}

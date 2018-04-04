package com.mt.iv.warehouse.service;

import java.util.Map;

public interface MaterialStorageService {
    // 模糊查询单号（用于出入库）列表
    Map<String,Object> selectBillSearch(Map<String, Object> map);
    // 查询入库初始数据
    Map<String,Object> selectIvQueryInitData(Map<String, Object> map);
    // 查询单号所对应的出入库凭证
    Map<String,Object> selectVoucher(Map<String, Object> map);
    // 根据单号入库
    Map<String,Object> addIvBillInStore(Map<String, Object> map);
}

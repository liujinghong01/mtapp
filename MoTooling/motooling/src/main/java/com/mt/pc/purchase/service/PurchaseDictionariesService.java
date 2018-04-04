package com.mt.pc.purchase.service;

import java.util.Map;

public interface PurchaseDictionariesService {

    Map<String,Object> selectSupplierList(Map<String, Object> map);

    Map<String,Object> selectInvoiceType(Map<String, Object> map);

    Map<String,Object> selectMatModel(Map<String, Object> map);

    Map<String,Object> selectMatRequreFrom(Map<String, Object> map);
    //物料名称搜索
    Map<String,Object> selectMatName(Map<String, Object> map);
    // 根据供应商查询物料
    Map<String,Object> queryMatBySupplier(Map<String, Object> map);
    //根据物料查询供应商
    Map<String,Object> querySupplierByMat(Map<String, Object> map);
    //物料需求来源列表
    Map<String,Object> selectRequireFrom(Map<String, Object> map);
}

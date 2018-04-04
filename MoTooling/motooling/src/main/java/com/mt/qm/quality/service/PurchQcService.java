package com.mt.qm.quality.service;

import java.util.Map;

/**
* @Author: Wendy
* @Description:
* @create    2018/1/12 9:32
* @Modified By:
*/    
public interface PurchQcService {
    //查询来料质检列表
    Map<String,Object>   selectPurchQcList(Map<String,Object>  map);

    //删除来料质检单
    Integer     updatePurchQcBill(Map<String,Object> map);

    //新增修改来料质检
    Map<String,Object>   addAndUpdatePurchQcBill(Map<String,Object>  map);

    //查询送货单质检详情（根据送货单）
    Map<String,Object>   selectDeliveryBillPurDetail(Map<String,Object>  map);

    //根据送货单号筛选生成质检
    Map<String,Object>   addDeliveryNoPurBill(Map<String,Object>  map);

    //查询来料质检详情
    Map<String,Object>  selectPurchQcDetail(Map<String,Object>  map);

    //查询待质检来料详情
    Map<String,Object>  selectStayCheckPurchDetail(Map<String,Object>  map);

}

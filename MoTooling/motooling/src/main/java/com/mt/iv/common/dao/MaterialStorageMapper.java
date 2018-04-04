package com.mt.iv.common.dao;

import java.util.List;
import java.util.Map;

public interface MaterialStorageMapper {
    // 模糊查询单号（用于出入库）列表
    List<Map<String, Object>> selectBillSearch(Map<String, Object> map);
    //查询采购单 入库( MP)
    List<Map<String,Object>> selectQueryDataMP(Map<String, Object> map);
    //(PZ)
    List<Map<String,Object>> selectQueryDataPZ(Map<String, Object> map);
    //根据送货单号查出采购单号
    List<Map<String,Object>> selectPurchNo(String bill_no);
    //根据 部门 查询对应的出入库凭证
    List<Map<String,Object>> getDepIdVoucherNoList(Map<String, Object> map);
    //根据 单号（模号  内部定单号）  查询对应的出入库凭证
    List<Map<String,Object>> getBillNoVoucherNoList(Map<String, Object> map);

    List<Map<String,Object>> getBillNoVoucherList(Map<String, Object> map);
    //添加部门退货 入库
    int addDepIdIvStore(Map<String, Object> inStoreInfo);
    //voucher_dept 部门退货
    int addvoucherDept(Map<String, Object> inStoreInfo);
    //修改 总库存表，库存，库位表 数量.金额
    int updateQuantityTotalProce(Map<String, Object> inStoreDetailList);
    //采购收货入库
    int addVoucherPurch(Map<String, Object> inStoreDetailList);
    //根据送货单号 查询 采购单号
    String selectPcDeliveryPurchNo(String bill_no);
    //根据凭证单号查询移动类型
    String selectTransType(String bill_no);
    //分页总条数
    Integer selectBillSearchTotalCount(Map<String, Object> map);
    //修改采购单已收数量。
    int updatePurchQuantity(Map<String, Object> inStoreDetailList);
}

package com.mt.qm.common.dao;

import com.mt.qm.common.model.QmPurchQcSub;

import java.util.List;
import java.util.Map;
/**
* @Author: Wendy
* @Description:
* @create    2018/1/23 15:33
* @Modified By:
*/    
public interface QmPurchQcSubMapper {
    int deleteByPrimaryKey(Long id);

    int insert(QmPurchQcSub record);

    int insertSelective(QmPurchQcSub record);

    QmPurchQcSub selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(QmPurchQcSub record);

    int updateByPrimaryKey(QmPurchQcSub record);

    // 删除来料质检物料
    Integer   updateIncomingMatCheckInfo(Long  purchQcId);

    // 新增来料质检单详表
    Integer   addIncomingMatCheckDetail(Map<String,Object>   map);

    // 修改来料质检单详表
    Integer   updateIncomingMatCheckDetail(Map<String,Object>   map);

    // 查询送货单质检详情
    Map<String,Object>  selectDeliveryBillCheckDetail(Map<String,Object>  map);

    // 根据采购Id取采购明细取数量
    Map<String,Object>   selectPurchQuantityById(Map<String,Object>  map);

    // 根据采购Id取来料质检明细取质检数量
    List<Map<String,Object>>   selectIncomingCheckQtyById(Map<String,Object>  map);

    // 通过送货单号筛选待质检采购明细
    List<Map<String,Object>>   selectDeliveryPurDetail(Map<String,Object>  map);

    // 查询来料质检详情
    List<Map<String,Object>>   selectIncomingMatCheckDetail(Map<String,Object>  map);

    // 查询待质检来料详情
    List<Map<String,Object>>   selectStayCheckPurchDetail(Map<String,Object>  map);

    //质检审批通过后修改采购单子表
    int updatePcPurchOrderSubByQcInfo(Long purchQcId);
}
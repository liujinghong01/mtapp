package com.mt.iv.common.dao;

import java.util.List;
import java.util.Map;

public interface MaterialOutgoingMapper {
    //凭证号（退货出库）
    List<Map<String,Object>> selectQueryDataPZ(Map<String, Object> map);
    //领料单（部门发货出库）
    List<Map<String,Object>> selectQueryDataDA(Map<String, Object> map);
    //添加出货凭证表
    int addOutDepIdIvStore(Map<String, Object> outStoreDetailList);
    //内部定单号（销售出库）
    List<Map<String,Object>> selectQueryDataCO(Map<String, Object> map);
    //领料单（部门发货出库）  可有单号。也可以没有单号
    int addOutVoucherDept(Map<String, Object> outStoreDetailMap);
    //根据领料单号查寻出ID
    Integer selectApplyId(String bill_no);
    //模号（模具发货出库） 模具领料单
    Integer addOutVoucherMold(Map<String, Object> outStoreDetailMap);
    //根据物料编码 查询物料类型
    Map<String,Object> selectBomType(String mat_no);
    //内部定单号（销售出库）
    Integer addoutVocherSell(Map<String, Object> outStoreDetailMap);
    //修改 领料单 已领料数量
    Integer updateApplyMatBill(Map<String, Object> outStoreDetailMap);
}

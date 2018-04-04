package com.mt.iv.common.dao;

import com.mt.iv.common.model.Voucher;

import java.util.List;
import java.util.Map;

public interface VoucherMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Voucher record);

    int insertSelective(Voucher record);

    Voucher selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Voucher record);

    int updateByPrimaryKey(Voucher record);

    // 新增凭证表
    Integer   addVoucher(Map<String,Object>  map);

    //查询出库凭证总条数
    Integer selectOutVoucherCount(Map<String,Object> map);

    //查询入库凭证总条数
    Integer selectInVoucherCount(Map<String,Object> map);

    //查询调拨单据总条数
    Integer selectVoucherSwitchCount(Map<String,Object> map);

    //查询盘点单据总条数
    Integer selectVoucherCheckCount(Map<String,Object> map);

    //查询出库凭证物料数量
    Map<String,Object> selectOutVoucherMatSum(Map<String,Object> map);

    //查询入库凭证物料数量
    Map<String,Object> selectInVoucherMatSum(Map<String,Object> map);

    //查询盘点单据物料数量
    Map<String,Object> selectVoucherCheckMatSum(Map<String,Object> map);

    //查询调拨单据物料数量
    Map<String,Object> selectVoucherSwitchMatSum(Map<String,Object> map);

    //查询出库凭证列表
    List<Map<String,Object>> selectOutVoucherList(Map<String,Object> map);

    //查询入库凭证列表
    List<Map<String,Object>> selectInVoucherList(Map<String,Object> map);

    //查询盘点单据列表
    List<Map<String,Object>> selectVoucherCheckList(Map<String,Object> map);

    //查询调拨单据列表
    List<Map<String,Object>> selectVoucherSwitchList(Map<String,Object> map);

    //查询出库凭证详情
    Map<String,Object> selectInVoucherDetail(Map<String,Object> map);

    //查询凭证管理详情
    Map<String,Object> selectVoucherList(Map<String,Object> map);

    //查询凭证管理详情
    List<Map<String,Object>> selectVoucherDetail(Map<String,Object> map);

    //根据凭证号查询物料数量
    Map<String,Object> selectMatSumByVoucher(String voucher);

    //查询凭证单中某物料信息
    Map<String,Object> selectVoucherListByDetailId(Map<String,Object> map);

    //查询凭证单中某物料信息
    Map<String,Object> selectVoucherDetailByDetailId(Map<String,Object> map);

    //根据操作人id查询操作人名字
    Map<String,Object> selectWorkNameById(Map<String,Object> map);

    //根据凭证号查询移动类型
    Map<String,Object> selectTransTypeByVoucher(Map<String,Object> map);

    //根据凭证id查询收讫质检
    Map<String,Object> selectVoucherPurch(Map<String,Object> map);

    Map<String,Object> selectVoucherPurchByVoucherNo(Map<String,Object> map);
}
package com.mt.iv.common.dao;

import com.mt.iv.common.model.IvStoreCheckBill;

import java.util.List;
import java.util.Map;
/**
* @Author: Wendy
* @Description:
* @create    2018/1/23 15:29
* @Modified By:
*/    
public interface IvStoreCheckBillMapper {

    int deleteByPrimaryKey(Long id);

    int insert(IvStoreCheckBill record);

    int insertSelective(IvStoreCheckBill record);

    IvStoreCheckBill selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IvStoreCheckBill record);

    int updateByPrimaryKey(IvStoreCheckBill record);

    // 新增盘点单主表
    Integer   addStoreCheckBill(Map<String,Object> checkInfo);

    // 查询总条数
    Integer totalCount(Map<String, Object> map);

    // 查询盘点单待盘已盘物料总条数
    Integer storeChecktotalCount(Map<String, Object> map);

    // 查看仓库进行盘点（盘盈盘亏）
    Map<String,Object>  selectHouseStoreCheck(Long  checkBillId);

    // 根据Id盘点单
    Map<String,Object>  selectStoreCheckBillById(Long id);

    // 查询盘点单列表
    List<Map<String,Object>> selectStoreCheckBillList(Map<String, Object> map);

    // 查询盘点单列表
    List<Map<String,Object>> selectStoreCheckMatTypeList(Map<String, Object> map);

    // 查询盘点单待盘已盘物料
    List<Map<String,Object>>  selectStoreCheckBillDetail(Map<String,Object> map);

    // 查询所有盘点物料详情
    List<Map<String,Object>>  selectStoreCheckAllDetail(Map<String,Object>  map);
}
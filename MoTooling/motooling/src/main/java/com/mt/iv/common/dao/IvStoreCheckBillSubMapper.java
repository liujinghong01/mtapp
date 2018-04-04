package com.mt.iv.common.dao;

import com.mt.iv.common.model.IvStoreCheckBillSub;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @Author: Wendy
* @Description:
* @create    2018/1/23 15:29
* @Modified By:
*/    
public interface IvStoreCheckBillSubMapper {

    int deleteByPrimaryKey(Long id);

    int insert(IvStoreCheckBillSub record);

    int insertSelective(IvStoreCheckBillSub record);

    IvStoreCheckBillSub selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IvStoreCheckBillSub record);

    int updateByPrimaryKey(IvStoreCheckBillSub record);

    // 新增盘点单从表
    Integer   addStoreCheckBillSubDetail(Map<String,Object> map);

    //新增库存盘点单--物料类型
    Integer   addStoreCheckBillMatType(Map<String,Object>  map);

    // 修改盘点单从表
    Integer   updateStoreCheckBillSubDetail(Map<String,Object> map);

    // 修改盘点单从表
    Integer   updateStoreCheckMatIdDetail(Map<String,Object> map);

    // 查询盘点单详情子表
    List<Map<String,Object>> selectStoreCheckBillSubDetail(Long  id);

    // 盘点单提交审批时，对于未盘点的物料设置为无须再盘状态(先查出所有为未盘的物料)
    List<Map<String,Object>>  selectNoStoreCheckMat(@Param("check_bill_id")Long  checkBillId);
}
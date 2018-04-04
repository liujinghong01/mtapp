package com.mt.iv.common.dao;

import com.mt.iv.common.model.IvApplyMatBillSub;

import java.util.List;
import java.util.Map;

/**
* @Author: Wendy
* @Description:
* @create    2018/1/23 15:28
* @Modified By:
*/    
public interface IvApplyMatBillSubMapper {

    int deleteByPrimaryKey(Long id);

    int insert(IvApplyMatBillSub record);

    int insertSelective(IvApplyMatBillSub record);

    IvApplyMatBillSub selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IvApplyMatBillSub record);

    int updateByPrimaryKey(IvApplyMatBillSub record);

    // 新增领料子表
    Integer   addApplyMatBillSub(Map<String, Object> objectMap);

    // 修改领料子表
    Integer   updateApplyMatBillSub(Map<String, Object> obejectMap);

    // 查询领料单详情子表
    List<Map<String,Object>>  selectApplyMatBillSubDetail(Long  id);

}
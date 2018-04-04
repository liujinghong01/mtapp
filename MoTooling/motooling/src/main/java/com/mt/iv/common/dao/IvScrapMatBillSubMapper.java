package com.mt.iv.common.dao;

import com.mt.iv.common.model.IvScrapMatBillSub;

import java.util.List;
import java.util.Map;

/**
* @Author: Wendy
* @Description:
* @create    2018/1/23 15:29
* @Modified By:
*/    
public interface IvScrapMatBillSubMapper {

    int deleteByPrimaryKey(Long id);

    int insert(IvScrapMatBillSub record);

    int insertSelective(IvScrapMatBillSub record);

    IvScrapMatBillSub selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IvScrapMatBillSub record);

    int updateByPrimaryKey(IvScrapMatBillSub record);

    // 新增报废单详情表
    Integer   addScrapMatBillSub(Map<String,Object> objectMap);

    // 修改报废单详情表
    Integer   updateScrapMatBillSub(Map<String,Object> objectMap);

    // 查询报废单详情从表
    List<Map<String,Object>> selectScrapMatBillSubDetail(Long  id);
}
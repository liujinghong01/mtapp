package com.mt.iv.common.dao;

import com.mt.iv.common.model.IvScrapMatBill;

import java.util.List;
import java.util.Map;

/**
* @Author: Wendy
* @Description:
* @create    2018/1/23 15:28
* @Modified By:
*/    
public interface IvScrapMatBillMapper {

    int deleteByPrimaryKey(Long id);

    int insert(IvScrapMatBill record);

    int insertSelective(IvScrapMatBill record);

    IvScrapMatBill selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IvScrapMatBill record);

    int updateByPrimaryKey(IvScrapMatBill record);

    // 查询报废单列表
    List<Map<String,Object>>  selectScrapMatBillList(Map<String,Object>  map);

    // 查询总条数
    Integer  totalCount(Map<String,Object>  map);

    // 新增报废单主表
    Integer   addScrapMatBill(Map<String,Object>  scrapMatInfo);

    // 修改报废单主表
    Integer  updateScrapMatBill(Map<String,Object>  scrapMatInfo);

    // 查询报废单详情主表
    Map<String,Object>   selectScrapMatBillDetail(Integer id);

    // 根据Id查询报废单
    Map<String,Object>  selectScrapMatBillById(Long  scrap_id);

}
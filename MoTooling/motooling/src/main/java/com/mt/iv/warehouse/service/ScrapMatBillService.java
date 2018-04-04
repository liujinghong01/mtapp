package com.mt.iv.warehouse.service;


import java.util.Map;
/**
* @Author: Wendy
* @Description:
* @create    2018/1/23 15:30
* @Modified By:
*/    
public interface ScrapMatBillService {
      // 查询报废单列表
      Map<String,Object> selectScrapMatBillList(Map<String,Object>  map);

      // 查询报废单详情
      Map<String,Object> selectScrapMatBillDetail(Map<String,Object>  map);

      // 报废单新增修改
      Map<String,Object> addAndUpdateScrapMatBill(Map<String,Object> map);

      // 执行报废
      Map<String,Object> executeScrap(Map<String,Object> map);


}

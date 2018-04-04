package com.mt.iv.warehouse.service;

import java.util.Map;
/**
* @Author: Wendy
* @Description:
* @create    2018/1/23 15:30
* @Modified By:
*/    
public interface ApplyMatBillService {
       // 领料单删除
       Integer updateApplyBill(Map<String, Object> map);

       // 新增修改领料单
       Map<String,Object>        addAndUpdateApplyMatBill(Map<String, Object> map);

       // 查询领料单列表
       Map<String,Object>        selectApplyMatBillList(Map<String, Object> map);

       // 查询领料单详情
       Map<String,Object>        selectApplyMatBillDetail(Map<String,Object>  map);
}

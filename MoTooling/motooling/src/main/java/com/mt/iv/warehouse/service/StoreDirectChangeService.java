package com.mt.iv.warehouse.service;


import java.util.Map;
/**
* @Author: Wendy
* @Description:
* @create    2018/1/23 15:30
* @Modified By:
*/    
public interface StoreDirectChangeService {
    // 库存物料管理(直接入库、直接出库、调拨)提交
    Map<String,Object>  storeDirectChangeManager(Map<String,Object>  map);
}

package com.mt.order.bl.service;


import java.util.Map;

public interface CostItemListService {

    Map<String,Object> processCostItemList(Integer companyId);

    Map<String,Object> processCostItemUnitList(Integer companyId);
    //下拉框材料列表
    Map<String,Object> rawNatureList(Map<String, Object> map);
    //缺省部件列表
    Map<String,Object> defaultPartList();
    //缺省子部件列表
    Map<String,Object> defaultSubPartList(Map<String, Object> map);
}

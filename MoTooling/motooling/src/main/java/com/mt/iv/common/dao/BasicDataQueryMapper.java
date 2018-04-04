package com.mt.iv.common.dao;

import java.util.List;
import java.util.Map;

public interface BasicDataQueryMapper {

    // 查询入库类型列表
    List<Map<String,Object>> selectInStoreTypeList();
    // 查询出库类型列表
    List<Map<String,Object>> selectOutStoreTypeList();
    // 查询物料类型列表
    List<Map<String,Object>> selectIvMatTypeList(Map<String,Object> map);

    Integer IvMatTypeListTotalCount(Map<String,Object> map);
    // 获取库存物料管理类型
    List<Map<String,Object>> selectIvStoreDirectChange();
    // 根据缺省部门编码查出部门ID
    List<Map<String,Object>> selectDepId(Map<String, Object> map);
    // 遍历查询部门成员
    List<Map<String,Object>> seslectDepIdUser(List<Long> depIds);
}

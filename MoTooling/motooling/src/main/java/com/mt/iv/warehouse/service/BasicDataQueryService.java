package com.mt.iv.warehouse.service;

import java.util.Map;

public interface BasicDataQueryService {
    // 查询物料类型列表
    Map<String,Object> selectIvStorehouseList(Map<String, Object> map);
    // 查询仓库详细信息(含库位)
    Map<String,Object> selectIvStorehouseDetail(Map<String, Object> map);
    // 查询入库类型列表
    Map<String,Object> selectInStoreTypeList(Map<String, Object> map);
    // 查询物料类型列表
    Map<String,Object> selectIvMatTypeList(Map<String, Object> map);
    // 查询出库类型列表
    Map<String,Object> selectOutStoreTypeList(Map<String, Object> map);
    // 获取库存物料管理类型
    Map<String,Object> selectIvStoreDirectChange(Map<String, Object> map);
    // 查询某部门及其子部门全部成员列表
    Map<String,Object> selectCompanyGetAllDeptMenber(Map<String, Object> map);
}

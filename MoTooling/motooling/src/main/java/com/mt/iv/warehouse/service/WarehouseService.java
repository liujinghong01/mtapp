package com.mt.iv.warehouse.service;

import java.util.Map;

public interface WarehouseService {
    //仓库新增修改
    Map<String,Object> addStoreHouse(Map<String, Object> map) throws IllegalArgumentException;
    //仓库删除
    Integer updateStoreHouseModify(Map<String, Object> map);
    //新增修改物料类型 根据 数据库表：bm_mat_type
    Map<String,Object> addIvMatTypeModify(Map<String, Object> map);
    //物料类型删除
    Boolean deleteMatType(Map<String, Object> map);
    //库位删除
    Integer updateStoreRoom(Map<String, Object> map);
    //3.19 新增 查看所有物料名称
    Map<String,Object> selectBmMatAllMatName(Map<String, Object> map) throws Exception;
    //查询物料类型详情
    Map<String,Object> selectBMatTypeModify(Map<String, Object> map);
}

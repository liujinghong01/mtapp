package com.mt.iv.warehouse.service;

import java.util.Map;

public interface MaintenanceMaterialBaseDataService {
    Map<String,Object> addIvMatModify(Map<String, Object> map);

    Map<String,Object> selectSearchMat(Map<String, Object> map);

    Map<String,Object> selectRommSearchMat(Map<String, Object> map);

    Map<String,Object> selectStoreSearchMat(Map<String, Object> map);
    // 查询物料信息详情
    Map<String,Object> selectMatModify(Map<String, Object> map);

    Map<String,Object> selectBasicMatList(Map<String,Object> map);
}

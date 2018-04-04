package com.mt.iv.common.dao;

import java.util.Map;

public interface BmMatMapper {
    // 新增物料信息
    Long addIvMatModify(Map<String, Object> matInfo);
    // 修改物料信息
    Integer updateIvMatModify(Map<String, Object> matInfo);
    //查询物料信息
    Map<String,Object>    selectMatInfo(Map<String,Object>  map);
    // 查询物料信息详情
    Map<String,Object> selectMatModify(Map<String, Object> map);
}

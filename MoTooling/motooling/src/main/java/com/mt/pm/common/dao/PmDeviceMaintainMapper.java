package com.mt.pm.common.dao;

import com.mt.pm.common.model.PmDeviceMaintain;

import java.util.Map;

public interface PmDeviceMaintainMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PmDeviceMaintain record);

    int insertSelective(PmDeviceMaintain record);

    PmDeviceMaintain selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmDeviceMaintain record);

    int updateByPrimaryKey(PmDeviceMaintain record);

    // 开始检修（是否检修完毕为未完毕）
    Integer   addDeviceMaintain(Map<String,Object> map);

    // 查询设备当前检修信息
    Map<String,Object> selectDeviceMaintainCurr(Map<String,Object>  map);

}
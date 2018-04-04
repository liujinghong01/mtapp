package com.mt.pm.common.dao;

import com.mt.pm.common.model.PmDeviceCheck;

import java.util.Map;

public interface PmDeviceCheckMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PmDeviceCheck record);

    int insertSelective(PmDeviceCheck record);

    PmDeviceCheck selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmDeviceCheck record);

    int updateByPrimaryKey(PmDeviceCheck record);

    // 设备点检（如果没有点检过直接修改点检状态）
    Integer   updateDeviceCheck(Map<String,Object> map);

    // 设备点检（如果为已经点检过的新增一条记录）
    Integer   addDeviceCheck(Map<String,Object> map);

    // 根据设备ID查出最新生成的一条点检记录
    Map<String,Object>   selectDeviceCheckMaxId(Map<String,Object> map);
}
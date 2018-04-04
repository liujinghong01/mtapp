package com.mt.pm.production.service;

import java.util.Map;

/**
* @Author: Wendy
* @Description:
* @create    2018/1/23 15:41
* @Modified By:
*/    
public interface PmDeviceCheckAllocateService {
    // 查询设备点检分配表
    Map<String,Object>    selectDeviceCheckAllocate(Map<String, Object> map);

    // 查询设备点检分配详情表
    Map<String,Object>    selectDeviceCheckDetail(Map<String,Object>  map);

    // 设备点检分配新增修改
    Map<String,Object>    addAndUpdateDeviceCheckAllocate(Map<String,Object>  map);

    // 根据分配ID删除原有的分配
    Map<String,Object>    updateCheckAllocateByAllocateId(Map<String,Object>  map);
}

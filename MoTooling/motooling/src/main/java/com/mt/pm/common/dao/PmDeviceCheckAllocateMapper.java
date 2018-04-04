package com.mt.pm.common.dao;

import com.mt.pm.common.model.PmDeviceCheckAllocate;

import java.util.List;
import java.util.Map;

public interface PmDeviceCheckAllocateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PmDeviceCheckAllocate record);

    int insertSelective(PmDeviceCheckAllocate record);

    PmDeviceCheckAllocate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmDeviceCheckAllocate record);

    int updateByPrimaryKey(PmDeviceCheckAllocate record);

    // 删除原来的分配信息
    Integer    updateCheckAllocateByAllocateId(Long  allocateId);

    // 多对多的关系新增点检分配
    Integer    addDeviceCheckAllocate(Map<String,Object>  map);

    // 获取分配ID最大值
    Integer   selectMaxAllocateId();

    // 查询设备分配表
    List<Map<String,Object>>   selectDeviceCheckAllocate(Map<String,Object>  map);

    // 查看设备点检分配表详情(判断生成小组的pg_id是否一致)
    List<Map<String,Object>>   selectDeviceCheckDetail(Map<String,Object>  map);

    // 查询设备点检分配表
    List<Map<String,Object>>  selectDeviceCheckAllocateInfo(Map<String, Object> map);

    // 根据设备ID分配
    List<Map<String,Object>>  selectAllocateIdCheckById(Long  allocateId);

}
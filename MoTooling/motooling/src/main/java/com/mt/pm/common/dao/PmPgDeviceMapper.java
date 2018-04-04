package com.mt.pm.common.dao;

import com.mt.pm.common.model.PmPgDevice;

import java.util.List;
import java.util.Map;

public interface PmPgDeviceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PmPgDevice record);

    int insertSelective(PmPgDevice record);

    PmPgDevice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmPgDevice record);

    int updateByPrimaryKey(PmPgDevice record);

    // 设备删除
    Integer   updateDevice(Long deviceId);

    // 查询设备列表总条数
    Integer  totalCount(Map<String, Object> map);

    // 设备新增
    Integer  addDeviceInfo(Map<String, Object> map);

    // 设备修改
    Integer  updateDeviceInfo(Map<String,Object> map);

    // 查询设备检修记录总条数
    Integer   selectDeviceMaintainRecordCount(Map<String, Object> map);

    //  查询生产设备详情
    List<Map<String,Object>>   selectProductionDeviceDetail(Long pgId);

    // 查询设备列表
    List<Map<String,Object>>   selectDeviceList(Map<String, Object> map);

    // 查询设备点检表
    List<Map<String,Object>>  selectDeviceCheckList(Map<String, Object> map);

    // 查询设备检修记录
    List<Map<String,Object>>   selectDeviceMaintainRecord(Map<String, Object> map);

    // 查询设备详情
    Map<String,Object>   selectDeviceDetail(Map<String, Object> map);

    // 设备响应信息
    Map<String,Object>  selectDeviceInfo(Long  deviceId);

    List<PmPgDevice> selectValidByPgIdAndDeviceId(Map map);

}
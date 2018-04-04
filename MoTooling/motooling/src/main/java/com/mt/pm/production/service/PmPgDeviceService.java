package com.mt.pm.production.service;


import java.util.Map;

/**
* @Author: Wendy
* @Description:
* @create    2018/1/9 15:26
* @Modified By:
*/
public interface PmPgDeviceService {
    // 查询设备的列表
    Map<String,Object>  selectDeviceList(Map<String, Object> map);

    // 查询设备的详情
    Map<String,Object>  selectDeviceDetail(Map<String, Object> map);

    // 查询设备点检表
    Map<String,Object>  selectDeviceCheckList(Map<String, Object> map);

    // 设备删除
    Map<String,Object>   updateDevice(Map<String, Object> map);

    // 设备新增修改
    Map<String,Object>   addAndUpdateDevice(Map<String,Object> map);

    // 设备点检
    Map<String,Object>   updateDeviceCheck(Map<String,Object>  map);

}

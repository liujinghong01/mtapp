package com.mt.pm.production.service;


import java.util.Map;

/**
* @Author: Wendy
* @Description:
* @create    2018/1/9 15:26
* @Modified By:
*/
public interface PmDeviceMaintainService {
    // 设备检修开始
    Map<String,Object>   startDeviceMainTain(Map<String,Object>  map);

    // 查询当前设备的检修信息
    Map<String,Object>   selectDeviceMainTainCurr(Map<String,Object>  map);

    // 设备检修单保存（检修完毕）
    Map<String,Object>   endDeviceMainTain(Map<String,Object> map);

    // 查询设备检修记录
    Map<String,Object>  selectDeviceMaintainRecord(Map<String, Object> map);
}

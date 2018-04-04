package com.mt.pm.production.service.impl;

import com.mt.common.utils.DateUtil;
import com.mt.common.utils.StringUtils;
import com.mt.pm.common.dao.PmDeviceCheckAllocateMapper;
import com.mt.pm.common.dao.PmDeviceCheckMapper;
import com.mt.pm.common.dao.PmPgDeviceMapper;
import com.mt.pm.production.service.PmPgDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Author: Wendy
* @Description:
* @create    2018/1/24 15:02
* @Modified By:
*/    
@Service
public class PmPgDeviceServiceImpl implements PmPgDeviceService {
    @Autowired
    private PmPgDeviceMapper pmPgDeviceMapper;

    @Autowired
    private PmDeviceCheckMapper pmDeviceCheckMapper;

    @Autowired
    private PmDeviceCheckAllocateMapper pmDeviceCheckAllocateMapper;


    /**
     * 查看设备列表
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectDeviceList(Map<String, Object> map) {
        if (map.containsKey("query")) {
            Map<String, Object> query = (Map<String, Object>) map.get("query");
            map.put("device_name", query.get("device_name") + "");
            map.put("pg_id", query.get("pg_id") + "");
        }
        int totalCount = pmPgDeviceMapper.totalCount(map);
        map.put("page", ((Integer) map.get("curr_page") - 1) * (Integer) map.get("page_size"));
        List<Map<String, Object>>   deviceList = pmPgDeviceMapper.selectDeviceList(map);
        if(deviceList.size()<=0){
            return StringUtils.pageList(map,"device_list",0);
        }
        Map<String, Object> page = StringUtils.page(map, deviceList, "device_list", totalCount);
        return page;
    }


    /**
     * 查看设备的详情
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectDeviceDetail(Map<String, Object> map) {
        Map<String, Object> query = (Map<String, Object>) map.get("query");
        map.put("device_id", query.get("device_id").toString());
        Map<String, Object> deviceParam = pmPgDeviceMapper.selectDeviceDetail(map);
        List<Map<String, Object>> checkAllocateList = pmDeviceCheckAllocateMapper.selectDeviceCheckAllocate(map);
        deviceParam.put("check_allocate_list", checkAllocateList);
        Map<String, Object> returnMap = new HashMap<String,Object>(16);
        returnMap.put("device_info", deviceParam);
        return returnMap;
    }


    /**
     * 查询设备点检表
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectDeviceCheckList(Map<String, Object> map) {
        if (map.containsKey("query")) {
            Map<String, Object> query = (Map<String, Object>) map.get("query");
            map.put("device_id", query.get("device_id")+"");
        }
        List<Map<String, Object>> deviceCheckList = pmPgDeviceMapper.selectDeviceCheckList(map);
        if(deviceCheckList.size()<=0){
            return StringUtils.pageList(map,"device_check_list",0);
        }
        Map<String, Object> returnMap = new HashMap<String,Object>();
        returnMap.put("device_check_list", deviceCheckList);
        return returnMap;
    }


    /**
     * 设备删除
     * @param map
     * @return
     */
    @Override
    public Map<String,Object> updateDevice(Map<String, Object> map) {
        Map<String,Object>  deviceInfo=(Map<String,Object>)map.get("device_info");
        pmPgDeviceMapper.updateDevice(Long.valueOf(deviceInfo.get("device_id").toString()));
        return deviceInfo;
    }


    /**
     * 设备新增修改
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> addAndUpdateDevice(Map<String, Object> map) {
        Map<String, Object> deviceInfo = (Map<String, Object>) map.get("device_info");
        deviceInfo.put("company_id", map.get("company_id"));
        Boolean falg = false;
        Long id = null;

        if (deviceInfo.containsKey("device_id")) {
            falg = deviceInfo.get("device_id") == null || deviceInfo.get("device_id") == "" ? false : true;
        }

        if (falg) {
            deviceInfo.put("updated_at", DateUtil.getDateTime());
            pmPgDeviceMapper.updateDeviceInfo(deviceInfo);
        } else {
            deviceInfo.put("created_at", DateUtil.getDateTime());
            pmPgDeviceMapper.addDeviceInfo(deviceInfo);
            id = Long.valueOf(deviceInfo.get("id").toString());
        }

        Map<String, Object> returnMap = new HashMap<String,Object>();
        if (falg) {
            returnMap = pmPgDeviceMapper.selectDeviceInfo(Long.valueOf(deviceInfo.get("device_id").toString()));
        } else {
            returnMap = pmPgDeviceMapper.selectDeviceInfo(id);
        }
        return returnMap;
    }


    /**
     * 设备点检
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> updateDeviceCheck(Map<String, Object> map) {
        Map<String, Object> deviceCheckInfo = (Map<String, Object>) map.get("device_check_info");
        deviceCheckInfo.put("company_id", map.get("company_id"));
        deviceCheckInfo.put("real_handler_id", map.get("uid"));
        // 根据设备ID查询最新的记录
        Map<String, Object> deviceCheckMap = pmDeviceCheckMapper.selectDeviceCheckMaxId(deviceCheckInfo);

        // 拿到最新的记录判断是否点检过的
        Integer isChecked = Integer.valueOf(deviceCheckMap.get("is_checked").toString());

        // 如果是1是点检完毕的再次点检就需要新增一条记录
        if (isChecked == 1 && isChecked != null) {
            deviceCheckInfo.put("created_at", DateUtil.getDateTime());
            deviceCheckInfo.put("real_check_date", DateUtil.getDateTime());
            pmDeviceCheckMapper.addDeviceCheck(deviceCheckInfo);

        }
        // 如果是0未点检，这时候需要修改点检状态
        if (isChecked == 0 && isChecked !=null) {
            deviceCheckInfo.put("updated_at", DateUtil.getDateTime());
            deviceCheckInfo.put("real_check_date", DateUtil.getDateTime());
            deviceCheckInfo.put("id", deviceCheckMap.get("id"));
            pmDeviceCheckMapper.updateDeviceCheck(deviceCheckInfo);
        }
        return deviceCheckInfo;
    }
}

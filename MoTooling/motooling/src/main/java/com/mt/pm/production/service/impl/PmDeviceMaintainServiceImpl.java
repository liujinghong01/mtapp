package com.mt.pm.production.service.impl;

import com.mt.common.utils.DateUtil;
import com.mt.common.utils.StringUtils;
import com.mt.pm.common.dao.PmDeviceMaintainMapper;
import com.mt.pm.common.dao.PmPgDeviceMapper;
import com.mt.pm.production.service.PmDeviceMaintainService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @Author: Wendy
* @Description:
* @create    2018/1/24 15:07
* @Modified By:
*/    
@Service
public class PmDeviceMaintainServiceImpl implements PmDeviceMaintainService{
    @Autowired
    private PmPgDeviceMapper pmPgDeviceMapper;

    @Autowired
    private PmDeviceMaintainMapper  pmDeviceMaintainMapper;

    /**
     * 设备开始检修   设备开始检修拿到device_id修改设备表的状态改到检修中
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> startDeviceMainTain(Map<String, Object> map) {
        Map<String,Object>  deviceMaintainInfo=(Map<String,Object>)map.get("device_maintain_info");

        deviceMaintainInfo.put("company_id",map.get("company_id"));
        deviceMaintainInfo.put("device_id",deviceMaintainInfo.get("device_id"));
        deviceMaintainInfo.put("device_status","2");

        // 本次检修结束时间  清空从新开始
        deviceMaintainInfo.put("last_end_time",null);
        deviceMaintainInfo.put("last_maintain_reason",null);
        //本次检修开始时间
        deviceMaintainInfo.put("last_start_time",DateUtil.getDateTime());

        pmPgDeviceMapper.updateDeviceInfo(deviceMaintainInfo);
        return map;
    }


    /**
     * 查询当前设备的检修的信息{把设备检修信息完善}
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectDeviceMainTainCurr(Map<String, Object> map) {
        if(map.containsKey("query")){
            Map<String,Object>  query=(Map<String,Object>)map.get("query");
            map.put("device_id",query.get("device_id")==null||query.get("device_id")==""?null:query.get("device_id"));
        }
        Map<String,Object> deviceMainTainParam=
                pmDeviceMaintainMapper.selectDeviceMaintainCurr(map);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String lastStartTime = deviceMainTainParam.get("start_date").toString();
        Calendar calendar = Calendar.getInstance();
        //Date.getTime() 获得毫秒型日期
        long nowDate = calendar.getTime().getTime();
        try {
            long specialDate = sdf.parse(lastStartTime).getTime();
            // 点检开始检修时间
            deviceMainTainParam.put("start_date", lastStartTime);
            // 计算间隔多少天，则除以毫秒到天的转换公式
            long betweenDate = (nowDate - specialDate) / (1000 * 60 * 60 * 24);
            // 点检开始的时间
            deviceMainTainParam.put("length", betweenDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 检修周期
        Integer cycle = Integer.valueOf(deviceMainTainParam.get("cycle").toString());
        deviceMainTainParam.put("cycle",cycle);

        // 点检开始检修时间
        deviceMainTainParam.put("end_date", DateUtil.getDateTime());
        // 设备ID
        deviceMainTainParam.put("device_id",deviceMainTainParam.get("device_id"));
        // 设备名称
        deviceMainTainParam.put("device_name",deviceMainTainParam.get("device_name"));
        try {
            // 下次开始检修时间
            deviceMainTainParam.put("next_start_date", sdf.format(DateUtil.dateAddYears(sdf.parse(lastStartTime), cycle)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 下次结束检修时间
        deviceMainTainParam.put("next_end_date", sdf.format(DateUtil.dateAddYears(new Date(), cycle)));

        Map<String,Object>   returnMap=new HashedMap();
        returnMap.put("device_maintain_info",deviceMainTainParam);
        return returnMap;
    }


    /**
     * 设备检修完毕（保存）{点击保存（真正的检修完毕）设备表更改状态，设备检修表新增一条检修记录}
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> endDeviceMainTain(Map<String, Object> map) {
        Map<String,Object>   deviceMaintainInfo=(Map<String,Object>)map.get("device_maintain_info");
        deviceMaintainInfo.put("company_id",map.get("company_id"));
        // 正常待加工
        deviceMaintainInfo.put("device_status","0");
        // 本次检修结束时间  最后保存得结束时间
        deviceMaintainInfo.put("last_end_time",DateUtil.getDateTime());
        deviceMaintainInfo.put("device_id",deviceMaintainInfo.get("device_id"));
        pmPgDeviceMapper.updateDeviceInfo(deviceMaintainInfo);

        deviceMaintainInfo.put("created_at",DateUtil.getDateTime());
        pmDeviceMaintainMapper.addDeviceMaintain(deviceMaintainInfo);
        return deviceMaintainInfo;
    }


    /**
     * 查询设备检修记录
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectDeviceMaintainRecord(Map<String, Object> map) {
        if (map.containsKey("query")) {
            Map<String, Object> query = (Map<String, Object>) map.get("query");
            map.put("device_id", query.get("device_id") == null || query.get("device_id") == "" ? null : query.get("device_id"));
        }
        int totalCount = pmPgDeviceMapper.selectDeviceMaintainRecordCount(map);
        map.put("page", ((Integer) map.get("curr_page") - 1) * (Integer) map.get("page_size"));

        List<Map<String, Object>> checkDetailList = pmPgDeviceMapper.selectDeviceMaintainRecord(map);
        if(checkDetailList.size()<=0){
            return StringUtils.pageList(map,"check_detail_list",0);
        }
        Map<String, Object> page = StringUtils.page(map, checkDetailList, "check_detail_list", totalCount);
        return page;
    }
}
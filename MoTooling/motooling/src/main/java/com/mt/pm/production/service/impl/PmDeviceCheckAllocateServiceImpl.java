package com.mt.pm.production.service.impl;

import com.mt.common.utils.DateUtil;
import com.mt.pm.common.dao.PmDeviceCheckAllocateMapper;
import com.mt.pm.production.service.PmDeviceCheckAllocateService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
* @Author: Wendy
* @Description:
* @create    2018/1/9 15:30
* @Modified By:
*/
@Service
public class PmDeviceCheckAllocateServiceImpl implements PmDeviceCheckAllocateService {

    @Autowired
    private PmDeviceCheckAllocateMapper pmDeviceCheckAllocateMapper;


    /**
     * 查询设备点检分配表
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectDeviceCheckAllocate(Map<String, Object> map) {
        if (map.containsKey("query")) {
            Map<String, Object> query = (Map<String, Object>) map.get("query");
            map.put("check_uid", query.get("check_uid") + "");
            map.put("pg_id", query.get("pg_id") == null || query.get("pg_id") == "" ? null : query.get("pg_id"));
        }
        Map<String, Object> returnMap = new HashedMap();
        List<Map<String, Object>> deviceCheckAllocateList =pmDeviceCheckAllocateMapper.selectDeviceCheckAllocateInfo(map);

        List checkUidRepeatList=null;
        List deviceRepeatList=null;
        Set<Integer> set = new HashSet();
        List  returnList  = new ArrayList<>();
        for (Map<String, Object> param: deviceCheckAllocateList) {
            set.add(Integer.valueOf(param.get("allocate_id").toString()));
        }
        for (Integer i: set) {
            List  arrayList  = new ArrayList<>();
            List  checkList  = new ArrayList<>();
            Map<String, Object> paramMap = new HashMap<>(16);
            for (Map<String, Object> param: deviceCheckAllocateList) {
                if(Objects.equals(i, Integer.valueOf(param.get("allocate_id").toString()))){
                    Map<String, Object> deviceMap = new HashMap<>(16);
                    deviceMap.put("device_id", param.get("device_id"));
                    deviceMap.put("device_name", param.get("device_name"));

                    Map<String, Object> checkUidMap = new HashMap<>(16);
                    checkUidMap.put("check_uid", param.get("check_uid"));
                    checkUidMap.put("check_uid_name", param.get("check_uid_name"));
                    checkList.add(checkUidMap);
                    arrayList.add(deviceMap);
                    checkUidRepeatList = new ArrayList(new HashSet(checkList));
                }
                deviceRepeatList = new ArrayList(new HashSet(arrayList));
            }
            paramMap.put("allocate_id",i);
            paramMap.put("device_list",deviceRepeatList);
            paramMap.put("check_uid_list",checkUidRepeatList);
            returnList.add(paramMap);
        }
        returnMap.put("check_allocate_info",returnList);
        return  returnMap;
    }


    /**
     * 查询设备点检分配详情表
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectDeviceCheckDetail(Map<String, Object> map) {
        if (map.containsKey("query")) {
            Map<String, Object> query = (Map<String, Object>) map.get("query");
            map.put("allocate_id", query.get("allocate_id") == null || query.get("allocate_id") == "" ? null : query.get("allocate_id"));
        }
        List<Map<String, Object>> deviceCheckAllocateList = pmDeviceCheckAllocateMapper.selectDeviceCheckDetail(map);

        Set<Integer>   set=new HashSet<>();
        for(Map<String,Object>  deviceCheckAllcateParam:deviceCheckAllocateList){
            set.add(Integer.valueOf(deviceCheckAllcateParam.get("allocate_id").toString()));
        }
        Map<String,Object>  paramMap=null;
        // 设备点检人
        List checkUidList=null;
        // 设备
        List deviceList=null;
        // set去重
        List newList=null;
        // set去重
        List newLists=null;
        for(Integer  i:set){
            checkUidList=new ArrayList<>();
            deviceList=new ArrayList<>();
            paramMap=new  HashMap<>();
            for(Map<String,Object> deviceCheckAllcateParam:deviceCheckAllocateList){
                if(Objects.equals(i,Integer.valueOf(deviceCheckAllcateParam.get("allocate_id").toString()))){
                    Map<String,Object>  deviceparam=new HashedMap();
                    deviceparam.put("check_uid_name",deviceCheckAllcateParam.get("check_uid_name").toString());
                    deviceparam.put("check_uid",deviceCheckAllcateParam.get("check_uid").toString());
                    checkUidList.add(deviceparam);

                    newList = new ArrayList(new HashSet(checkUidList));

                    Iterator iterator = deviceCheckAllcateParam.keySet().iterator();
                    // 移除取出的值
                    while (iterator.hasNext()) {
                        String str=(String)iterator.next();
                        if("allocate_id".equals(str)){
                            iterator.remove();
                        }
                        if("check_uid".equals(str)){
                            iterator.remove();
                        }
                        if("check_uid_name".equals(str)){
                            iterator.remove();
                        }
                    }
                    deviceList.add(deviceCheckAllcateParam);
                }
                newLists = new ArrayList(new HashSet(deviceList));
            }
            paramMap.put("allocate_id",i);
            paramMap.put("check_uid_list",newList);
            paramMap.put("device_list", newLists);
        }
        return paramMap;
    }


    /**
     * @Name: 设备点检分配
     * @Description: 一台设备可以多个人点检, 一个人也可以点检多台设备
     * @Description: 保存时，要先把原来的分配删除，再把新的保存进去,删除以设备为主
     * @create 2018/1/8 14:52
     */
    @Override
    public Map<String, Object> addAndUpdateDeviceCheckAllocate(Map<String, Object> map) {
        Map<String, Object> checkAllocateInfo = (Map<String, Object>) map.get("check_allocate_info");
        checkAllocateInfo.put("company_id", map.get("company_id"));
        // 设备
        List<Map<String, Object>> deviceList = (List<Map<String, Object>>) checkAllocateInfo.get("device_list");

        Boolean flg = false;
        // 为空新增，否则修改
        if (checkAllocateInfo.containsKey("allocate_id")) {
            flg = checkAllocateInfo.get("allocate_id") == null || checkAllocateInfo.get("allocate_id") == "" ? false : true;
        }

        if (flg) {
            // 判断生产小组pg_id是否一致
            Integer   pgId=Integer.valueOf(checkAllocateInfo.get("pg_id").toString());
            checkAllocateInfo.put("allocate_id", checkAllocateInfo.get("allocate_id"));
            List<Map<String, Object>> deviceCheckAllocateList=pmDeviceCheckAllocateMapper.selectDeviceCheckDetail(checkAllocateInfo);
            for (Map<String, Object> deviceCheckAllocateParam : deviceCheckAllocateList) {
                Integer pgIds = Integer.valueOf(deviceCheckAllocateParam.get("pg_id").toString());
                if(pgId.equals(pgIds)==false){
                    throw  new IllegalArgumentException("生产小组不一致，请重新输入！");
                }else{
                    break;
                }
            }

            // 根据分配ID删除再新增
            List<Map<String, Object>> allocateList = pmDeviceCheckAllocateMapper.selectAllocateIdCheckById(Long.valueOf(checkAllocateInfo.get("allocate_id").toString()));
            // 删除原来分配的
            if (allocateList.size() > 0) {
                pmDeviceCheckAllocateMapper.updateCheckAllocateByAllocateId(Long.valueOf(checkAllocateInfo.get("allocate_id").toString()));
            }

            for (Map<String, Object> deviceMap : deviceList) {
                List<Map<String, Object>> checkUIdList = (List<Map<String, Object>>) checkAllocateInfo.get("check_uid_list");
                for (Map<String, Object> check : checkUIdList) {
                    check.put("device_id", deviceMap.get("device_id"));
                    check.put("created_at", DateUtil.getDateTime());
                    check.put("handler_id", map.get("uid"));
                    check.put("allocate_id", checkAllocateInfo.get("allocate_id"));
                    pmDeviceCheckAllocateMapper.addDeviceCheckAllocate(check);
                }
                checkAllocateInfo.put("check_uid_list", checkUIdList);
            }
        } else {
            // 生成最大的分配ID新增
            Integer  pgId=Integer.valueOf(checkAllocateInfo.get("pg_id").toString());
            Integer  allocateId=pmDeviceCheckAllocateMapper.selectMaxAllocateId();

            for (Map<String, Object> deviceMap : deviceList) {
                List<Map<String, Object>> checkUIdList = (List<Map<String, Object>>) checkAllocateInfo.get("check_uid_list");
                for (Map<String, Object> check : checkUIdList) {
                    check.put("device_id", deviceMap.get("device_id"));
                    check.put("created_at", DateUtil.getDateTime());
                    check.put("handler_id", map.get("uid"));

                    check.put("allocate_id", allocateId);
                    pmDeviceCheckAllocateMapper.addDeviceCheckAllocate(check);
                }
                checkAllocateInfo.put("check_uid_list", checkUIdList);
            }

            checkAllocateInfo.put("allocate_id",allocateId);
            List<Map<String, Object>> deviceCheckAllocateList=pmDeviceCheckAllocateMapper.selectDeviceCheckDetail(checkAllocateInfo);
            for (Map<String, Object> deviceCheckAllocateParam : deviceCheckAllocateList) {
                Integer pgIds = Integer.valueOf(deviceCheckAllocateParam.get("pg_id").toString());
                if(pgId.equals(pgIds)==false){
                    throw  new IllegalArgumentException("生产小组不一致，请重新输入！");
                }else{
                    break;
                }
            }
        }
        checkAllocateInfo.put("device_list", deviceList);
        return checkAllocateInfo;
    }


    /**
    * @Author: Wendy
    * @Description:设备点检分配删除
    * @create    2018/1/10 9:58
    * @Modified By:
    */
    @Override
    public Map<String, Object> updateCheckAllocateByAllocateId(Map<String, Object> map) {
        Map<String,Object>   checkAllocateInfo=(Map<String,Object>)map.get("check_allocate_info");
        pmDeviceCheckAllocateMapper.updateCheckAllocateByAllocateId(Long.valueOf(checkAllocateInfo.get("allocate_id").toString()));
        return checkAllocateInfo;
    }
}

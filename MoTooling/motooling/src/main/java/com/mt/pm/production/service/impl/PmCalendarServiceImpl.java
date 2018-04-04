package com.mt.pm.production.service.impl;

import com.mt.common.utils.StringUtils;
import com.mt.pc.purchase.service.CreateBillNoService;
import com.mt.pm.common.dao.PmCalendarMapper;
import com.mt.pm.common.dao.PmCalendarSubMapper;
import com.mt.pm.common.dao.PmShiftMapper;
import com.mt.pm.common.dao.PmShiftSubMapper;
import com.mt.pm.common.model.PmCalendar;
import com.mt.pm.common.model.PmCalendarSub;
import com.mt.pm.common.pojo.PmCalendarPojo;
import com.mt.pm.production.service.PmCalendarService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author:Wujie
 * @Date: Create in 2018-01-12 11:29
 * @Description:
 * @Modified By:
 */
@Service
@SuppressWarnings("unchecked")
public class PmCalendarServiceImpl implements PmCalendarService{

    @Autowired
    private PmCalendarMapper pmCalendarMapper;

    @Autowired
    private PmCalendarSubMapper pmCalendarSubMapper;

    @Autowired
    private CreateBillNoService createBillNoService;

    @Autowired
    private PmShiftSubMapper pmShiftSubMapper;

    @Autowired
    private PmShiftMapper pmShiftMapper;

    @Override
    public Map<String, Object> selectPmCalendarList(PmCalendarPojo pmCalendarPojo) {
        pmCalendarPojo.setPage((pmCalendarPojo.getCurrPage() - 1) * pmCalendarPojo.getPageSize());
        List<Map<String,Object>> calendarList = pmCalendarMapper.selectPmCalendarList(pmCalendarPojo);
        //将pmCalendarPojo转换成Map 并转换成下划线格式
        Map<String,Object> map = StringUtils.camelToUnderline(JSONObject.fromObject(pmCalendarPojo));
        Map<String,Object> page;
        if (calendarList.size() <= 0) {
            return StringUtils.pageList(map, "calendar_list", 0);
        }
        Integer totalCount = pmCalendarMapper.selectTotalCount(pmCalendarPojo);
        page = StringUtils.page(map, calendarList, "calendar_list", totalCount);
        return page;
    }

    @Override
    public Map<String, Object> selectPmCalendarDetail(PmCalendarPojo pmCalendarPojo) {
        Map<String,Object> query = pmCalendarPojo.getQuery();
        List<Map<String,Object>> detailList = new ArrayList<>();
        String theDate = null;
        Integer shiftId = null;
        //查询日历详情
        Map<String,Object> calendarInfo = pmCalendarMapper.selectPmCalendarDetail(query);
        //查询日期
        List<Map<String,Object>> theDateList = pmCalendarSubMapper.selectTheDateByCalendarId(query);
            for (Map<String,Object>  mapList : theDateList) {
                Map<String,Object> detailMap = new HashMap<>();
                theDate = mapList.get("the_date") == null || mapList.get("the_date") == "" ? null : mapList.get("the_date").toString().trim();
                detailMap.put("the_date",theDate);
                //根据日期查询班次id
                 List<Map<String,Object>> shiftIdList = pmCalendarSubMapper.selectShiftIdByTheDate(theDate);
                  for (Map<String,Object> shiftIdMap:shiftIdList) {
                    shiftId = Integer.valueOf(shiftIdMap.get("shift_id") == null || shiftIdMap.get("shift_id") == "" ? null : shiftIdMap.get("shift_id").toString().trim());
                    //根据班次id查询班次列表
                    Map<String,Object>  shiftInfoMap = pmShiftMapper.selectPmShiftListByShiftId(shiftId);
                    detailMap.put("shift_info",shiftInfoMap);
                }
                 detailList.add(detailMap);
             }
        calendarInfo.put("detail_list",detailList);
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("calendar_info",calendarInfo);
                   return returnMap;
    }

    @Override
    public Map<String,Object> addAndUpdatePmCalendar(PmCalendarPojo pmCalendarPojo) {
        PmCalendar pmCalendar = pmCalendarPojo.getCalendarInfo();
        List<PmCalendarSub> dateList = pmCalendarPojo.getCalendarInfo().getDateList();
        Boolean flg = false;
        Long id = null;
        Boolean partFlg = pmCalendar.getCalendarId() == null || pmCalendar.getCalendarId().equals("") ? false : true;
        if(partFlg){
            //修改日历
            pmCalendar.setUpdatedAt(new Date());
            pmCalendarMapper.updateByPrimaryKeySelective(pmCalendar);
        }else {
            //新增日历
            pmCalendar.setCompanyId(Integer.valueOf(pmCalendarPojo.getCompanyId()));
            pmCalendarMapper.addPmCalendar(pmCalendar);
            id = Long.valueOf(pmCalendar.getCalendarId());
        }

        for (PmCalendarSub pmCalendarSub : dateList){
            pmCalendarSub.setCalendarId(pmCalendar.getCalendarId() == null || pmCalendar.getCalendarId().equals("") ? id : pmCalendar.getCalendarId());
            flg = pmCalendarSub.getShiftId() == null || pmCalendarSub.getShiftId().equals("") ? false : true;
            if (flg){
                //根据班次id和日历id查询日期
                List<Map<String,Object>> theDateList = pmCalendarSubMapper.selectTheDateByShiftId(pmCalendarSub);
                //没有日期就新增
                if(theDateList.size() <=0){
                    pmCalendarSubMapper.insertSelective(pmCalendarSub);
                }else {
                    Boolean isTheDate = false;
                    for (Map<String,Object> theDateMap : theDateList){
                        if(pmCalendarSub.getTheDate().equals(theDateMap.get("the_date"))){
                            isTheDate=true;
                        }
                    }
                    if(isTheDate){
                        //如果当前日期存在就修改日历子表
                        pmCalendarSub.setUpdatedAt(new Date());
                        pmCalendarSubMapper.updateByPrimaryKeySelective(pmCalendarSub);
                    }else {
                        //如果当前日期不存在就新增日历子表
                        pmCalendarSubMapper.addPmCalendarSub(pmCalendarSub);
                    }
                }
            }else {
                if(partFlg){
                    pmCalendarSub.setCalendarId(pmCalendar.getCalendarId());
                }else {
                    pmCalendarSub.setCalendarId(id);
                }
                //根据日期和日历id获取班次id
                List<Map<String,Object>> shiftIdList = pmCalendarSubMapper.selectShiftIdByTheDateAndCalendarId(pmCalendarSub);
                if (shiftIdList.size() <= 0){
                    throw new IllegalArgumentException ("当前日期无班次");
                }else {
                    for (Map<String,Object> shiftIdMap : shiftIdList){
                        shiftIdMap.put("the_date",pmCalendarSub.getTheDate());
                        shiftIdMap.put("updated_at",new Date());
                        //根据班次id删除日历子表
                        pmCalendarSubMapper.deletePmCalendarSubByShiftId(shiftIdMap);
                    }
                }
            }
        }
        Map<String,Object> calendarMap;
        Long calendarId;
        if(partFlg){
            calendarId = pmCalendar.getCalendarId();
        }else {
            calendarId = id;
        }
        calendarMap = pmCalendarMapper.selectPmCalendarById(calendarId);
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("calendar_info",calendarMap);
        return returnMap;
    }

    @Override
    public Boolean deletePmCalendar(PmCalendarPojo pmCalendarPojo){
        pmCalendarPojo.setUpdateAt(new Date());
        Boolean bool = pmCalendarMapper.deletePmCalendar(pmCalendarPojo);
        if(bool){
            bool = pmCalendarSubMapper.deletePmCalendarSub(pmCalendarPojo);
            return bool;
        }
        return bool;
    }
}

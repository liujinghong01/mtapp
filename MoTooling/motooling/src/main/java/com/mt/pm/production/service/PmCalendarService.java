package com.mt.pm.production.service;

import com.mt.pm.common.pojo.PmCalendarPojo;

import java.util.Map;

/**
 * @Author:Wujie
 * @Date: Create in 2018-01-12 11:28
 * @Description:
 * @Modified By:
 */
public interface PmCalendarService {

    /**
     * 查询日历列表
     * @param pmCalendarPojo
     * @return
     */
    Map<String,Object> selectPmCalendarList(PmCalendarPojo pmCalendarPojo);

    /**
     * 查询日历列表详情
     * @param pmCalendarPojo
     * @return
     */
    Map<String,Object> selectPmCalendarDetail(PmCalendarPojo pmCalendarPojo);

    /**
     * 新增修改日历
     * @param map
     * @return
     */
    Map<String,Object> addAndUpdatePmCalendar(PmCalendarPojo pmCalendarPojo);

    /**
     * 删除日历
     * @param pmCalendarPojo
     * @return
     */
    Boolean deletePmCalendar(PmCalendarPojo pmCalendarPojo);
}

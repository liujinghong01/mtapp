package com.mt.pm.common.dao;

import com.mt.pm.common.model.PmCalendar;
import com.mt.pm.common.pojo.PmCalendarPojo;

import java.util.List;
import java.util.Map;

/**
 * @Author:Wujie
 * @Date: Create in 2018-01-12 11:28
 * @Description:
 * @Modified By:
 */
public interface PmCalendarMapper {

    int deleteByPrimaryKey(Long id);

    int insert(PmCalendar record);

    int insertSelective(PmCalendar record);

    PmCalendar selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmCalendar record);

    int updateByPrimaryKey(PmCalendar record);
    /**
     * 查询列表总条数
     * @param pmCalendarPojo
     * @return
     */
    Integer selectTotalCount(PmCalendarPojo pmCalendarPojo);

    /**
     * 查询日历列表
     * @param pmCalendarPojo
     * @return
     */
    List<Map<String,Object>> selectPmCalendarList(PmCalendarPojo pmCalendarPojo);

    /**
     * 查询日历详情
     * @param map
     * @return
     */
    Map<String,Object> selectPmCalendarDetail(Map map);

    /**
     * 添加日历列表
     * @param pmCalendar
     * @return
     */
    Integer addPmCalendar(PmCalendar pmCalendar);

    /**
     * 删除日历列表
     * @param pmCalendarPojo
     * @return
     */
    Boolean deletePmCalendar(PmCalendarPojo pmCalendarPojo);

    /**
     * 根据日历id查询日历id和日历名字
     * @param calendarId
     * @return
     */
    Map<String,Object> selectPmCalendarById(Long calendarId);

    /**
     * 返回日历id和日历名字
     * @param pmCalendarPojo
     * @return
     */
    Map<String,Object> selectPmCalendarDetailById(PmCalendarPojo pmCalendarPojo);
}
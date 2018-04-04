package com.mt.pm.common.dao;

import com.mt.pm.common.model.PmCalendarSub;
import com.mt.pm.common.pojo.PmCalendarPojo;

import java.util.List;
import java.util.Map;

/**
 * @Author:Wujie
 * @Date: Create in 2018-01-12 11:28
 * @Description:
 * @Modified By:
 */
public interface PmCalendarSubMapper {

    int deleteByPrimaryKey(Long id);

    int insert(PmCalendarSub record);

    int insertSelective(PmCalendarSub record);

    PmCalendarSub selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmCalendarSub record);

    int updateByPrimaryKey(PmCalendarSub record);
    /**
     * 删除日历子表
     * @param pmCalendarPojo
     * @return
     */
    Boolean deletePmCalendarSub(PmCalendarPojo pmCalendarPojo);

    /**
     * 添加日历子表
     * @param pmCalendarSub
     * @return
     */
    Integer addPmCalendarSub(PmCalendarSub pmCalendarSub);

    /**
     * 根据日期查询班次id
     * @param theDate
     * @return
     */
    List<Map<String,Object>> selectShiftIdByTheDate(String theDate);

    /**
     * 根据日期和日历id查询班次id
     * @param pmCalendarSub
     * @return
     */
    List<Map<String,Object>> selectShiftIdByTheDateAndCalendarId(PmCalendarSub pmCalendarSub);

    /**
     * 根据日历id查询日期
     * @param map
     * @return
     */
    List< Map<String,Object>> selectTheDateByCalendarId(Map map);

    /**
     * 根据班次Id删除日历子表
     * @param map
     * @return
     */
    Boolean deletePmCalendarSubByShiftId(Map<String,Object> map);

    /**
     * 根据班次日历id查询日期
     * @param pmCalendarSub
     * @return
     */
    List<Map<String,Object>> selectTheDateByShiftId(PmCalendarSub pmCalendarSub);
}
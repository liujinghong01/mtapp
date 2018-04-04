package com.mt.pm.common.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mt.common.base.BasePojo;
import com.mt.pm.common.model.PmCalendar;

import java.util.Date;
import java.util.Map;

/**
 * @Author:Wujie
 * @Date: Create in 2018-03-14 14:09
 * @Description:
 * @Modified By:
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class PmCalendarPojo extends BasePojo {

    //日历id
    private Integer calendarId;
    //修改时间
    private Date updateAt;

    private PmCalendar calendarInfo;

    private Map<String,Object> query;

    public PmCalendar getCalendarInfo() {
        return calendarInfo;
    }

    public void setCalendarInfo(PmCalendar calendarInfo) {
        this.calendarInfo = calendarInfo;
    }

    public Map<String, Object> getQuery() {
        return query;
    }

    public void setQuery(Map<String, Object> query) {
        this.query = query;
    }

    public Integer getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(Integer calendarId) {
        this.calendarId = calendarId;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}

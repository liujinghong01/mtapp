package com.mt.pm.common.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mt.common.base.BasePojo;
import com.mt.pm.common.model.PmShift;

import java.util.Date;

/**
 * @Author:Wujie
 * @Date: Create in 2018-03-14 16:49
 * @Description:
 * @Modified By:
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class PmShiftPojo extends BasePojo{

    private Long shiftId;

    private Date updatedAt;

    private PmShift shiftInfo;

    private PmShift query;



    public Long getShiftId() {
        return shiftId;
    }

    public void setShiftId(Long shiftId) {
        this.shiftId = shiftId;
    }

    public PmShift getQuery() {
        return query;
    }

    public Date getUpdateAt() {
        return updatedAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updatedAt = updateAt;
    }

    public void setQuery(PmShift query) {
        this.query = query;
    }

    public PmShift getShiftInfo() {
        return shiftInfo;
    }

    public void setShiftInfo(PmShift shiftInfo) {
        this.shiftInfo = shiftInfo;
    }

}

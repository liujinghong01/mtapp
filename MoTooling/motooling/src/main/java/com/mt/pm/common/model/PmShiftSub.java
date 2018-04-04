package com.mt.pm.common.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown=true)
public class PmShiftSub {

    @JSONField(name="detail_id")
    private Long id;

    private Long shiftId;

    private Integer seq;

    private String startTime;

    private String endTime;

    private Integer shiftMins;

    private String isOverDay;

    private Date updatedAt;

    private Date createdAt;

    private String status;

    private String isMt;

    public Long getDetailId() {
        return id;
    }

    public void setDetailId(Long id) {
        this.id = id;
    }

    public Long getShiftId() {
        return shiftId;
    }

    public void setShiftId(Long shiftId) {
        this.shiftId = shiftId;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public Integer getShiftMins() {
        return shiftMins;
    }

    public void setShiftMins(Integer shiftMins) {
        this.shiftMins = shiftMins;
    }

    public String getIsOverDay() {
        return isOverDay;
    }

    public void setIsOverDay(String isOverDay) {
        this.isOverDay = isOverDay == null ? null : isOverDay.trim();
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getIsMt() {
        return isMt;
    }

    public void setIsMt(String isMt) {
        this.isMt = isMt == null ? null : isMt.trim();
    }
}
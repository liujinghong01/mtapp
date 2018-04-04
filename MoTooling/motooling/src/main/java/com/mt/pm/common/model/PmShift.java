package com.mt.pm.common.model;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class PmShift {

    @JSONField(name="shift_id")
    private Long id;

    private Integer companyId;

    private String companyName;

    private String shiftName;

    private Integer dayMins;

    private String shiftColor;

    private String isTransProcSeqShift;

    private Long handlerId;

    private Date createdAt;

    private Date updatedAt;

    private String status;

    private String shiftType;

    private String isMt;

    private List<PmShiftSub> pmShiftSubList;

    private List<PmShiftSub> detailList;

    public List<PmShiftSub> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<PmShiftSub> detailList) {
        this.detailList = detailList;
    }

    public Long getShiftId() {
        return id;
    }

    public void setShiftId(Long id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName == null ? null : shiftName.trim();
    }

    public Integer getDayMins() {
        return dayMins;
    }

    public void setDayMins(Integer dayMins) {
        this.dayMins = dayMins;
    }

    public String getShiftColor() {
        return shiftColor;
    }

    public void setShiftColor(String shiftColor) {
        this.shiftColor = shiftColor == null ? null : shiftColor.trim();
    }

    public String getIsTransProcSeqShift() {
        return isTransProcSeqShift;
    }

    public void setIsTransProcSeqShift(String isTransProcSeqShift) {
        this.isTransProcSeqShift = isTransProcSeqShift == null ? null : isTransProcSeqShift.trim();
    }

    public Long getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(Long handlerId) {
        this.handlerId = handlerId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getShiftType() {
        return shiftType;
    }

    public void setShiftType(String shiftType) {
        this.shiftType = shiftType == null ? null : shiftType.trim();
    }

    public String getIsMt() {
        return isMt;
    }

    public void setIsMt(String isMt) {
        this.isMt = isMt == null ? null : isMt.trim();
    }

    public List<PmShiftSub> getPmShiftSubList() {
        return pmShiftSubList;
    }

    public void setPmShiftSubList(List<PmShiftSub> pmShiftSubList) {
        this.pmShiftSubList = pmShiftSubList;
    }
}
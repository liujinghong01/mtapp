package com.mt.pm.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class PmPg {
    private Long id;

    private Long pgId;

    private String pgName;

    private String pgDesc;

    private String pgType;

    private String stdUnit;

    private String assignToDevice;

    private Integer calendarId;

    private String calendarName;

    private BigDecimal labourRatio;

    private String startTime;

    private String endTime;

    private BigDecimal restLength;

    private BigDecimal capaRatio;

    private BigDecimal overloadRatio;

    private BigDecimal transLength;

    private BigDecimal queueLength;

    private String availStatus;

    private Long workshopId;

    private String pgPic;

    private BigDecimal dayHours;

    private BigDecimal sort;

    private String remark;

    private Long handlerId;

    private Date createdAt;

    private Date updatedAt;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPgId() {
        return pgId;
    }

    public void setPgId(Long pgId) {
        this.pgId = pgId;
    }

    public String getPgName() {
        return pgName;
    }

    public void setPgName(String pgName) {
        this.pgName = pgName == null ? null : pgName.trim();
    }

    public String getPgDesc() {
        return pgDesc;
    }

    public void setPgDesc(String pgDesc) {
        this.pgDesc = pgDesc == null ? null : pgDesc.trim();
    }

    public String getPgType() {
        return pgType;
    }

    public void setPgType(String pgType) {
        this.pgType = pgType == null ? null : pgType.trim();
    }

    public String getStdUnit() {
        return stdUnit;
    }

    public void setStdUnit(String stdUnit) {
        this.stdUnit = stdUnit == null ? null : stdUnit.trim();
    }

    public String getAssignToDevice() {
        return assignToDevice;
    }

    public void setAssignToDevice(String assignToDevice) {
        this.assignToDevice = assignToDevice == null ? null : assignToDevice.trim();
    }

    public Integer getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(Integer calendarId) {
        this.calendarId = calendarId;
    }

    public String getCalendarName() {
        return calendarName;
    }

    public void setCalendarName(String calendarName) {
        this.calendarName = calendarName == null ? null : calendarName.trim();
    }

    public BigDecimal getLabourRatio() {
        return labourRatio;
    }

    public void setLabourRatio(BigDecimal labourRatio) {
        this.labourRatio = labourRatio;
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

    public BigDecimal getRestLength() {
        return restLength;
    }

    public void setRestLength(BigDecimal restLength) {
        this.restLength = restLength;
    }

    public BigDecimal getCapaRatio() {
        return capaRatio;
    }

    public void setCapaRatio(BigDecimal capaRatio) {
        this.capaRatio = capaRatio;
    }

    public BigDecimal getOverloadRatio() {
        return overloadRatio;
    }

    public void setOverloadRatio(BigDecimal overloadRatio) {
        this.overloadRatio = overloadRatio;
    }

    public BigDecimal getTransLength() {
        return transLength;
    }

    public void setTransLength(BigDecimal transLength) {
        this.transLength = transLength;
    }

    public BigDecimal getQueueLength() {
        return queueLength;
    }

    public void setQueueLength(BigDecimal queueLength) {
        this.queueLength = queueLength;
    }

    public String getAvailStatus() {
        return availStatus;
    }

    public void setAvailStatus(String availStatus) {
        this.availStatus = availStatus == null ? null : availStatus.trim();
    }

    public Long getWorkshopId() {
        return workshopId;
    }

    public void setWorkshopId(Long workshopId) {
        this.workshopId = workshopId;
    }

    public String getPgPic() {
        return pgPic;
    }

    public void setPgPic(String pgPic) {
        this.pgPic = pgPic == null ? null : pgPic.trim();
    }

    public BigDecimal getDayHours() {
        return dayHours;
    }

    public void setDayHours(BigDecimal dayHours) {
        this.dayHours = dayHours;
    }

    public BigDecimal getSort() {
        return sort;
    }

    public void setSort(BigDecimal sort) {
        this.sort = sort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
}
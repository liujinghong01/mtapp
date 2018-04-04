package com.mt.pm.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class PmDeviceMaintain {
    private Long id;

    private Long deviceId;

    private String deviceName;

    private String maintainNo;

    private Date prevEndDate;

    private Date startDate;

    private Date endDate;

    private String reason;

    private BigDecimal cycle;

    private BigDecimal length;

    private Date nextStartDate;

    private Date nextEndDate;

    private String isMaintained;

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

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public String getMaintainNo() {
        return maintainNo;
    }

    public void setMaintainNo(String maintainNo) {
        this.maintainNo = maintainNo == null ? null : maintainNo.trim();
    }

    public Date getPrevEndDate() {
        return prevEndDate;
    }

    public void setPrevEndDate(Date prevEndDate) {
        this.prevEndDate = prevEndDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public BigDecimal getCycle() {
        return cycle;
    }

    public void setCycle(BigDecimal cycle) {
        this.cycle = cycle;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public Date getNextStartDate() {
        return nextStartDate;
    }

    public void setNextStartDate(Date nextStartDate) {
        this.nextStartDate = nextStartDate;
    }

    public Date getNextEndDate() {
        return nextEndDate;
    }

    public void setNextEndDate(Date nextEndDate) {
        this.nextEndDate = nextEndDate;
    }

    public String getIsMaintained() {
        return isMaintained;
    }

    public void setIsMaintained(String isMaintained) {
        this.isMaintained = isMaintained == null ? null : isMaintained.trim();
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
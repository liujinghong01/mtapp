package com.mt.pm.common.model;

import java.util.Date;

public class PmDeviceCheck {
    private Long id;

    private Long deviceId;

    private Date planCheckDate;

    private Date realCheckDate;

    private Long planHandlerId;

    private Long realHandlerId;

    private String remark;

    private String isChecked;

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

    public Date getPlanCheckDate() {
        return planCheckDate;
    }

    public void setPlanCheckDate(Date planCheckDate) {
        this.planCheckDate = planCheckDate;
    }

    public Date getRealCheckDate() {
        return realCheckDate;
    }

    public void setRealCheckDate(Date realCheckDate) {
        this.realCheckDate = realCheckDate;
    }

    public Long getPlanHandlerId() {
        return planHandlerId;
    }

    public void setPlanHandlerId(Long planHandlerId) {
        this.planHandlerId = planHandlerId;
    }

    public Long getRealHandlerId() {
        return realHandlerId;
    }

    public void setRealHandlerId(Long realHandlerId) {
        this.realHandlerId = realHandlerId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked == null ? null : isChecked.trim();
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
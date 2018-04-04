package com.mt.pm.common.model;

import java.util.Date;

public class PmDeviceCheckAllocate {
    private Long id;

    private Long deviceId;

    private Long checkUid;

    private String checkUidName;

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

    public Long getCheckUid() {
        return checkUid;
    }

    public void setCheckUid(Long checkUid) {
        this.checkUid = checkUid;
    }

    public String getCheckUidName() {
        return checkUidName;
    }

    public void setCheckUidName(String checkUidName) {
        this.checkUidName = checkUidName == null ? null : checkUidName.trim();
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
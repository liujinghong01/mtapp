package com.mt.pm.common.model;

import java.util.Date;

public class PmTicketAllocate {
    private Long id;

    private Long inceptId;

    private Long memberId;

    private Long deviceId;

    private String allocateFlag;

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

    public Long getInceptId() {
        return inceptId;
    }

    public void setInceptId(Long inceptId) {
        this.inceptId = inceptId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getAllocateFlag() {
        return allocateFlag;
    }

    public void setAllocateFlag(String allocateFlag) {
        this.allocateFlag = allocateFlag == null ? null : allocateFlag.trim();
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
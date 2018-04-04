package com.mt.iv.common.model;

import java.util.Date;

public class IvStoreRoomConf {
    private Long id;

    private String storeRoomName;

    private String storeRoomDesc;

    private Long storeHouseId;

    private String storeHouseName;

    private Boolean isDefaultRoom;

    private Long lastHandlerId;

    private Date lastHandleTime;

    private Date createdAt;

    private Date updatedAt;

    private Byte status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStoreRoomName() {
        return storeRoomName;
    }

    public void setStoreRoomName(String storeRoomName) {
        this.storeRoomName = storeRoomName == null ? null : storeRoomName.trim();
    }

    public String getStoreRoomDesc() {
        return storeRoomDesc;
    }

    public void setStoreRoomDesc(String storeRoomDesc) {
        this.storeRoomDesc = storeRoomDesc == null ? null : storeRoomDesc.trim();
    }

    public Long getStoreHouseId() {
        return storeHouseId;
    }

    public void setStoreHouseId(Long storeHouseId) {
        this.storeHouseId = storeHouseId;
    }

    public String getStoreHouseName() {
        return storeHouseName;
    }

    public void setStoreHouseName(String storeHouseName) {
        this.storeHouseName = storeHouseName == null ? null : storeHouseName.trim();
    }

    public Boolean getIsDefaultRoom() {
        return isDefaultRoom;
    }

    public void setIsDefaultRoom(Boolean isDefaultRoom) {
        this.isDefaultRoom = isDefaultRoom;
    }

    public Long getLastHandlerId() {
        return lastHandlerId;
    }

    public void setLastHandlerId(Long lastHandlerId) {
        this.lastHandlerId = lastHandlerId;
    }

    public Date getLastHandleTime() {
        return lastHandleTime;
    }

    public void setLastHandleTime(Date lastHandleTime) {
        this.lastHandleTime = lastHandleTime;
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
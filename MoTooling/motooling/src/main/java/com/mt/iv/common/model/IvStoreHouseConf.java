package com.mt.iv.common.model;

import java.util.Date;

public class IvStoreHouseConf {
    private Long id;

    private String storeHouseNo;

    private String storeHouseName;

    private String storeHouseAddr;

    private Integer companyId;

    private String companyName;

    private Long lastHandlerId;

    private Date lastHandleTime;

    private Byte storeType;

    private Date createdAt;

    private Date updatedAt;

    private Byte status;

    private Long managerId;

    private String managerName;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStoreHouseNo() {
        return storeHouseNo;
    }

    public void setStoreHouseNo(String storeHouseNo) {
        this.storeHouseNo = storeHouseNo == null ? null : storeHouseNo.trim();
    }

    public String getStoreHouseName() {
        return storeHouseName;
    }

    public void setStoreHouseName(String storeHouseName) {
        this.storeHouseName = storeHouseName == null ? null : storeHouseName.trim();
    }

    public String getStoreHouseAddr() {
        return storeHouseAddr;
    }

    public void setStoreHouseAddr(String storeHouseAddr) {
        this.storeHouseAddr = storeHouseAddr == null ? null : storeHouseAddr.trim();
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

    public Byte getStoreType() {
        return storeType;
    }

    public void setStoreType(Byte storeType) {
        this.storeType = storeType;
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

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName == null ? null : managerName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}
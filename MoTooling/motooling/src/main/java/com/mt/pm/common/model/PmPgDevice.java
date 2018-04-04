package com.mt.pm.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class PmPgDevice {
    private Long id;

    private String deviceNo;

    private String deviceName;

    private Long pgId;

    private String deviceModel;

    private String deviceBrand;

    private String deviceAddr;

    private Date prodDate;

    private BigDecimal workLife;

    private String isShare;

    private BigDecimal labourRatio;

    private String devicePic;

    private Byte isAutoPause;

    private String isAutoProd;

    private String deviceStatus;

    private Date lastStartTime;

    private Date lastEndTime;

    private String lastMaintainReason;

    private Integer cycle;

    private String accessory;

    private Long managerId;

    private String managerName;

    private Long lastPopId;

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

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo == null ? null : deviceNo.trim();
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public Long getPgId() {
        return pgId;
    }

    public void setPgId(Long pgId) {
        this.pgId = pgId;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel == null ? null : deviceModel.trim();
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand == null ? null : deviceBrand.trim();
    }

    public String getDeviceAddr() {
        return deviceAddr;
    }

    public void setDeviceAddr(String deviceAddr) {
        this.deviceAddr = deviceAddr == null ? null : deviceAddr.trim();
    }

    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public BigDecimal getWorkLife() {
        return workLife;
    }

    public void setWorkLife(BigDecimal workLife) {
        this.workLife = workLife;
    }

    public String getIsShare() {
        return isShare;
    }

    public void setIsShare(String isShare) {
        this.isShare = isShare == null ? null : isShare.trim();
    }

    public BigDecimal getLabourRatio() {
        return labourRatio;
    }

    public void setLabourRatio(BigDecimal labourRatio) {
        this.labourRatio = labourRatio;
    }

    public String getDevicePic() {
        return devicePic;
    }

    public void setDevicePic(String devicePic) {
        this.devicePic = devicePic == null ? null : devicePic.trim();
    }

    public Byte getIsAutoPause() {
        return isAutoPause;
    }

    public void setIsAutoPause(Byte isAutoPause) {
        this.isAutoPause = isAutoPause;
    }

    public String getIsAutoProd() {
        return isAutoProd;
    }

    public void setIsAutoProd(String isAutoProd) {
        this.isAutoProd = isAutoProd == null ? null : isAutoProd.trim();
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus == null ? null : deviceStatus.trim();
    }

    public Date getLastStartTime() {
        return lastStartTime;
    }

    public void setLastStartTime(Date lastStartTime) {
        this.lastStartTime = lastStartTime;
    }

    public Date getLastEndTime() {
        return lastEndTime;
    }

    public void setLastEndTime(Date lastEndTime) {
        this.lastEndTime = lastEndTime;
    }

    public String getLastMaintainReason() {
        return lastMaintainReason;
    }

    public void setLastMaintainReason(String lastMaintainReason) {
        this.lastMaintainReason = lastMaintainReason == null ? null : lastMaintainReason.trim();
    }

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public String getAccessory() {
        return accessory;
    }

    public void setAccessory(String accessory) {
        this.accessory = accessory == null ? null : accessory.trim();
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

    public Long getLastPopId() {
        return lastPopId;
    }

    public void setLastPopId(Long lastPopId) {
        this.lastPopId = lastPopId;
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
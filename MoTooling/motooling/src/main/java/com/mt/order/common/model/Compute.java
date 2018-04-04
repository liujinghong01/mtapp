package com.mt.order.common.model;

import java.util.Date;

public class Compute {
    private Long id;

    private String computeNo;

    private Integer companyId;

    private String companyName;

    private Integer depId;

    private String depName;

    private Long handlerId;

    private String handlerName;

    private Date computeDate;

    private Date validDate;

    private String cstMoldNo;

    private String material;

    private String cavityMaterial;

    private String prodInfo;

    private String prodRemark;

    private Byte type;

    private String typeName;

    private String deliveryPlace;

    private String approveStep;

    private String approveSugg;

    private Byte status;

    private Boolean isActive;

    private Date createdAt;

    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComputeNo() {
        return computeNo;
    }

    public void setComputeNo(String computeNo) {
        this.computeNo = computeNo == null ? null : computeNo.trim();
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

    public Integer getDepId() {
        return depId;
    }

    public void setDepId(Integer depId) {
        this.depId = depId;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName == null ? null : depName.trim();
    }

    public Long getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(Long handlerId) {
        this.handlerId = handlerId;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName == null ? null : handlerName.trim();
    }

    public Date getComputeDate() {
        return computeDate;
    }

    public void setComputeDate(Date computeDate) {
        this.computeDate = computeDate;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public String getCstMoldNo() {
        return cstMoldNo;
    }

    public void setCstMoldNo(String cstMoldNo) {
        this.cstMoldNo = cstMoldNo == null ? null : cstMoldNo.trim();
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material == null ? null : material.trim();
    }

    public String getCavityMaterial() {
        return cavityMaterial;
    }

    public void setCavityMaterial(String cavityMaterial) {
        this.cavityMaterial = cavityMaterial == null ? null : cavityMaterial.trim();
    }

    public String getProdInfo() {
        return prodInfo;
    }

    public void setProdInfo(String prodInfo) {
        this.prodInfo = prodInfo == null ? null : prodInfo.trim();
    }

    public String getProdRemark() {
        return prodRemark;
    }

    public void setProdRemark(String prodRemark) {
        this.prodRemark = prodRemark == null ? null : prodRemark.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public String getDeliveryPlace() {
        return deliveryPlace;
    }

    public void setDeliveryPlace(String deliveryPlace) {
        this.deliveryPlace = deliveryPlace == null ? null : deliveryPlace.trim();
    }

    public String getApproveStep() {
        return approveStep;
    }

    public void setApproveStep(String approveStep) {
        this.approveStep = approveStep == null ? null : approveStep.trim();
    }

    public String getApproveSugg() {
        return approveSugg;
    }

    public void setApproveSugg(String approveSugg) {
        this.approveSugg = approveSugg == null ? null : approveSugg.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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
}
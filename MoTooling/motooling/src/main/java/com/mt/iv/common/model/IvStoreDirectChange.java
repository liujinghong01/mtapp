package com.mt.iv.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class IvStoreDirectChange {
    private Long id;

    private Integer companyId;

    private String companyName;

    private String changeNo;

    private String changeType;

    private Long outStoreHouseId;

    private String outStoreHouseName;

    private Long inStoreHouseId;

    private String inStoreHouseName;

    private String creatorId;

    private Date createTime;

    private BigDecimal totalQty;

    private BigDecimal totalPrice;

    private BigDecimal totalWeight;

    private String remark;

    private Date createdAt;

    private Date updatedAt;

    private Byte status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getChangeNo() {
        return changeNo;
    }

    public void setChangeNo(String changeNo) {
        this.changeNo = changeNo == null ? null : changeNo.trim();
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType == null ? null : changeType.trim();
    }

    public Long getOutStoreHouseId() {
        return outStoreHouseId;
    }

    public void setOutStoreHouseId(Long outStoreHouseId) {
        this.outStoreHouseId = outStoreHouseId;
    }

    public String getOutStoreHouseName() {
        return outStoreHouseName;
    }

    public void setOutStoreHouseName(String outStoreHouseName) {
        this.outStoreHouseName = outStoreHouseName == null ? null : outStoreHouseName.trim();
    }

    public Long getInStoreHouseId() {
        return inStoreHouseId;
    }

    public void setInStoreHouseId(Long inStoreHouseId) {
        this.inStoreHouseId = inStoreHouseId;
    }

    public String getInStoreHouseName() {
        return inStoreHouseName;
    }

    public void setInStoreHouseName(String inStoreHouseName) {
        this.inStoreHouseName = inStoreHouseName == null ? null : inStoreHouseName.trim();
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId == null ? null : creatorId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(BigDecimal totalQty) {
        this.totalQty = totalQty;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(BigDecimal totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
package com.mt.iv.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class IvStoreDirectChangeSub {
    private Long id;

    private Long changeId;

    private Byte dealFlag;

    private Long matId;

    private String matNo;

    private String matDesc;

    private Long matTypeId;

    private String matTypeName;

    private String matModel;

    private String unit;

    private BigDecimal quantity;

    private BigDecimal weight;

    private Long inStoreRoomId;

    private String inStoreRoomName;

    private Long outStoreRoomId;

    private String outStoreRoomName;

    private Date createdAt;

    private Date updatedAt;

    private Byte status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChangeId() {
        return changeId;
    }

    public void setChangeId(Long changeId) {
        this.changeId = changeId;
    }

    public Byte getDealFlag() {
        return dealFlag;
    }

    public void setDealFlag(Byte dealFlag) {
        this.dealFlag = dealFlag;
    }

    public Long getMatId() {
        return matId;
    }

    public void setMatId(Long matId) {
        this.matId = matId;
    }

    public String getMatNo() {
        return matNo;
    }

    public void setMatNo(String matNo) {
        this.matNo = matNo == null ? null : matNo.trim();
    }

    public String getMatDesc() {
        return matDesc;
    }

    public void setMatDesc(String matDesc) {
        this.matDesc = matDesc == null ? null : matDesc.trim();
    }

    public Long getMatTypeId() {
        return matTypeId;
    }

    public void setMatTypeId(Long matTypeId) {
        this.matTypeId = matTypeId;
    }

    public String getMatTypeName() {
        return matTypeName;
    }

    public void setMatTypeName(String matTypeName) {
        this.matTypeName = matTypeName == null ? null : matTypeName.trim();
    }

    public String getMatModel() {
        return matModel;
    }

    public void setMatModel(String matModel) {
        this.matModel = matModel == null ? null : matModel.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Long getInStoreRoomId() {
        return inStoreRoomId;
    }

    public void setInStoreRoomId(Long inStoreRoomId) {
        this.inStoreRoomId = inStoreRoomId;
    }

    public String getInStoreRoomName() {
        return inStoreRoomName;
    }

    public void setInStoreRoomName(String inStoreRoomName) {
        this.inStoreRoomName = inStoreRoomName == null ? null : inStoreRoomName.trim();
    }

    public Long getOutStoreRoomId() {
        return outStoreRoomId;
    }

    public void setOutStoreRoomId(Long outStoreRoomId) {
        this.outStoreRoomId = outStoreRoomId;
    }

    public String getOutStoreRoomName() {
        return outStoreRoomName;
    }

    public void setOutStoreRoomName(String outStoreRoomName) {
        this.outStoreRoomName = outStoreRoomName == null ? null : outStoreRoomName.trim();
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
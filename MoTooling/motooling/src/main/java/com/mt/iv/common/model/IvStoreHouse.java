package com.mt.iv.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class IvStoreHouse {
    private Long id;

    private Long storeHouseId;

    private Long matId;

    private String matNo;

    private String matDesc;

    private Long matTypeId;

    private String matTypeName;

    private String matModel;

    private String unit;

    private BigDecimal stockQty;

    private BigDecimal totalPrice;

    private Long lastStoreRoomId;

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

    public Long getStoreHouseId() {
        return storeHouseId;
    }

    public void setStoreHouseId(Long storeHouseId) {
        this.storeHouseId = storeHouseId;
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

    public BigDecimal getStockQty() {
        return stockQty;
    }

    public void setStockQty(BigDecimal stockQty) {
        this.stockQty = stockQty;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getLastStoreRoomId() {
        return lastStoreRoomId;
    }

    public void setLastStoreRoomId(Long lastStoreRoomId) {
        this.lastStoreRoomId = lastStoreRoomId;
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
package com.mt.pc.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class PcPurchOrderSub {
    private Long id;

    private Long purchId;

    private String matId;

    private String matNo;

    private String matDesc;

    private Long matTypeId;

    private String matTypeName;

    private String matModel;

    private BigDecimal up;

    private String unit;

    private String superPriceReason;

    private BigDecimal quantity;

    private BigDecimal weight;

    private BigDecimal processUp;

    private BigDecimal processCost;

    private BigDecimal totalPrice;

    private Byte orderFlag;

    private BigDecimal receivedQty;

    private Long requireId;

    private Long inquirySubId;

    private Date createdAt;

    private Date updatedAt;

    private Byte status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPurchId() {
        return purchId;
    }

    public void setPurchId(Long purchId) {
        this.purchId = purchId;
    }

    public String getMatId() {
        return matId;
    }

    public void setMatId(String matId) {
        this.matId = matId == null ? null : matId.trim();
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

    public BigDecimal getUp() {
        return up;
    }

    public void setUp(BigDecimal up) {
        this.up = up;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getSuperPriceReason() {
        return superPriceReason;
    }

    public void setSuperPriceReason(String superPriceReason) {
        this.superPriceReason = superPriceReason == null ? null : superPriceReason.trim();
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

    public BigDecimal getProcessUp() {
        return processUp;
    }

    public void setProcessUp(BigDecimal processUp) {
        this.processUp = processUp;
    }

    public BigDecimal getProcessCost() {
        return processCost;
    }

    public void setProcessCost(BigDecimal processCost) {
        this.processCost = processCost;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Byte getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(Byte orderFlag) {
        this.orderFlag = orderFlag;
    }

    public BigDecimal getReceivedQty() {
        return receivedQty;
    }

    public void setReceivedQty(BigDecimal receivedQty) {
        this.receivedQty = receivedQty;
    }

    public Long getRequireId() {
        return requireId;
    }

    public void setRequireId(Long requireId) {
        this.requireId = requireId;
    }

    public Long getInquirySubId() {
        return inquirySubId;
    }

    public void setInquirySubId(Long inquirySubId) {
        this.inquirySubId = inquirySubId;
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
package com.mt.qm.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class QmPurchQcSub {
    private Long id;

    private Long purchQcId;

    private Long purchId;

    private String purchNo;

    private Long purchSubId;

    private Long matId;

    private String matNo;

    private String matDesc;

    private Long matTypeId;

    private String matTypeName;

    private String matModel;

    private BigDecimal qcQty;

    private BigDecimal qualifiedQty;

    private BigDecimal noQualifiedQty;

    private BigDecimal specialQty;

    private String unusualReason;

    private Integer step;

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

    public Long getPurchQcId() {
        return purchQcId;
    }

    public void setPurchQcId(Long purchQcId) {
        this.purchQcId = purchQcId;
    }

    public Long getPurchId() {
        return purchId;
    }

    public void setPurchId(Long purchId) {
        this.purchId = purchId;
    }

    public String getPurchNo() {
        return purchNo;
    }

    public void setPurchNo(String purchNo) {
        this.purchNo = purchNo == null ? null : purchNo.trim();
    }

    public Long getPurchSubId() {
        return purchSubId;
    }

    public void setPurchSubId(Long purchSubId) {
        this.purchSubId = purchSubId;
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

    public BigDecimal getQcQty() {
        return qcQty;
    }

    public void setQcQty(BigDecimal qcQty) {
        this.qcQty = qcQty;
    }

    public BigDecimal getQualifiedQty() {
        return qualifiedQty;
    }

    public void setQualifiedQty(BigDecimal qualifiedQty) {
        this.qualifiedQty = qualifiedQty;
    }

    public BigDecimal getNoQualifiedQty() {
        return noQualifiedQty;
    }

    public void setNoQualifiedQty(BigDecimal noQualifiedQty) {
        this.noQualifiedQty = noQualifiedQty;
    }

    public BigDecimal getSpecialQty() {
        return specialQty;
    }

    public void setSpecialQty(BigDecimal specialQty) {
        this.specialQty = specialQty;
    }

    public String getUnusualReason() {
        return unusualReason;
    }

    public void setUnusualReason(String unusualReason) {
        this.unusualReason = unusualReason == null ? null : unusualReason.trim();
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
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
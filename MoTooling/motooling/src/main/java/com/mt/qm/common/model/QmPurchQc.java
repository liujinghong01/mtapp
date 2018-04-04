package com.mt.qm.common.model;

import java.util.Date;

public class QmPurchQc {
    private Long id;

    private Integer companyId;

    private String companyName;

    private String purchType;

    private String supName;

    private Integer supCompanyId;

    private String purchQcNo;

    private Date qcDate;

    private String inspector;

    private String approveStep;

    private String approveSugg;

    private Long creatorId;

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

    public String getPurchType() {
        return purchType;
    }

    public void setPurchType(String purchType) {
        this.purchType = purchType == null ? null : purchType.trim();
    }

    public String getSupName() {
        return supName;
    }

    public void setSupName(String supName) {
        this.supName = supName == null ? null : supName.trim();
    }

    public Integer getSupCompanyId() {
        return supCompanyId;
    }

    public void setSupCompanyId(Integer supCompanyId) {
        this.supCompanyId = supCompanyId;
    }

    public String getPurchQcNo() {
        return purchQcNo;
    }

    public void setPurchQcNo(String purchQcNo) {
        this.purchQcNo = purchQcNo == null ? null : purchQcNo.trim();
    }

    public Date getQcDate() {
        return qcDate;
    }

    public void setQcDate(Date qcDate) {
        this.qcDate = qcDate;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector == null ? null : inspector.trim();
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

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
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
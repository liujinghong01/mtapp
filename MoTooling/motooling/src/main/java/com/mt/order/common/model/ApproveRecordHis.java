package com.mt.order.common.model;

import java.util.Date;

public class ApproveRecordHis {
    private Long id;

    private Byte approveType;

    private Long approveId;

    private String approveStep;

    private Date approveStartDate;

    private Byte approveFlag;

    private String approveSugg;

    private Long approveManId;

    private String approveManName;

    private Date approveEndDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getApproveType() {
        return approveType;
    }

    public void setApproveType(Byte approveType) {
        this.approveType = approveType;
    }

    public Long getApproveId() {
        return approveId;
    }

    public void setApproveId(Long approveId) {
        this.approveId = approveId;
    }

    public String getApproveStep() {
        return approveStep;
    }

    public void setApproveStep(String approveStep) {
        this.approveStep = approveStep == null ? null : approveStep.trim();
    }

    public Date getApproveStartDate() {
        return approveStartDate;
    }

    public void setApproveStartDate(Date approveStartDate) {
        this.approveStartDate = approveStartDate;
    }

    public Byte getApproveFlag() {
        return approveFlag;
    }

    public void setApproveFlag(Byte approveFlag) {
        this.approveFlag = approveFlag;
    }

    public String getApproveSugg() {
        return approveSugg;
    }

    public void setApproveSugg(String approveSugg) {
        this.approveSugg = approveSugg == null ? null : approveSugg.trim();
    }

    public Long getApproveManId() {
        return approveManId;
    }

    public void setApproveManId(Long approveManId) {
        this.approveManId = approveManId;
    }

    public String getApproveManName() {
        return approveManName;
    }

    public void setApproveManName(String approveManName) {
        this.approveManName = approveManName == null ? null : approveManName.trim();
    }

    public Date getApproveEndDate() {
        return approveEndDate;
    }

    public void setApproveEndDate(Date approveEndDate) {
        this.approveEndDate = approveEndDate;
    }
}
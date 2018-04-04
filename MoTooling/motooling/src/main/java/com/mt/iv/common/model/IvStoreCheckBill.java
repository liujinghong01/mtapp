package com.mt.iv.common.model;

import java.util.Date;

public class IvStoreCheckBill {

    private Long id;

    private String checkBillNo;

    private Integer companyId;

    private String companyName;

    private Integer storeHouseId;

    private String storeHouseName;

    private Long matTypeId;

    private String matTypeName;

    private String checkBillType;

    private Integer baseCheckBillId;

    private Integer checkYear;

    private String voucherNoFrom;

    private Long voucherIdFrom;

    private String voucherNoTo;

    private Long voucherIdTo;


    private String liquidateFlag;


    private Long operatorId;

    private Long creatorId;


    private Long handlerId;


    private String approveStep;


    private String approveSugg;

    private Date createdAt;

    private Date updatedAt;

    private Byte status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getCheckBillNo() {
        return checkBillNo;
    }

    public void setCheckBillNo(String checkBillNo) {
        this.checkBillNo = checkBillNo == null ? null : checkBillNo.trim();
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


    public Integer getStoreHouseId() {
        return storeHouseId;
    }


    public void setStoreHouseId(Integer storeHouseId) {
        this.storeHouseId = storeHouseId;
    }


    public String getStoreHouseName() {
        return storeHouseName;
    }


    public void setStoreHouseName(String storeHouseName) {
        this.storeHouseName = storeHouseName == null ? null : storeHouseName.trim();
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


    public String getCheckBillType() {
        return checkBillType;
    }

    public void setCheckBillType(String checkBillType) {
        this.checkBillType = checkBillType == null ? null : checkBillType.trim();
    }

    public Integer getBaseCheckBillId() {
        return baseCheckBillId;
    }

    public void setBaseCheckBillId(Integer baseCheckBillId) {
        this.baseCheckBillId = baseCheckBillId;
    }


    public Integer getCheckYear() {
        return checkYear;
    }

    public void setCheckYear(Integer checkYear) {
        this.checkYear = checkYear;
    }


    public String getVoucherNoFrom() {
        return voucherNoFrom;
    }


    public void setVoucherNoFrom(String voucherNoFrom) {
        this.voucherNoFrom = voucherNoFrom == null ? null : voucherNoFrom.trim();
    }


    public Long getVoucherIdFrom() {
        return voucherIdFrom;
    }


    public void setVoucherIdFrom(Long voucherIdFrom) {
        this.voucherIdFrom = voucherIdFrom;
    }


    public String getVoucherNoTo() {
        return voucherNoTo;
    }


    public void setVoucherNoTo(String voucherNoTo) {
        this.voucherNoTo = voucherNoTo == null ? null : voucherNoTo.trim();
    }


    public Long getVoucherIdTo() {
        return voucherIdTo;
    }


    public void setVoucherIdTo(Long voucherIdTo) {
        this.voucherIdTo = voucherIdTo;
    }


    public String getLiquidateFlag() {
        return liquidateFlag;
    }


    public void setLiquidateFlag(String liquidateFlag) {
        this.liquidateFlag = liquidateFlag == null ? null : liquidateFlag.trim();
    }


    public Long getOperatorId() {
        return operatorId;
    }


    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
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
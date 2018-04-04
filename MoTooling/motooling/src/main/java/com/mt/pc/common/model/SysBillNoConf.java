package com.mt.pc.common.model;

import java.util.Date;

public class SysBillNoConf {
    private Long id;

    private Integer companyId;

    private String companyName;

    private String billTypeCode;

    private String billTypeName;

    private String newestBillNo;

    private Long newestBillId;

    private String newestBillNum;

    private String numCycle;

    private Integer numLength;

    private Long handlerId;

    private Date createdAt;

    private Date updatedAt;

    private String remark;

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

    public String getBillTypeCode() {
        return billTypeCode;
    }

    public void setBillTypeCode(String billTypeCode) {
        this.billTypeCode = billTypeCode == null ? null : billTypeCode.trim();
    }

    public String getBillTypeName() {
        return billTypeName;
    }

    public void setBillTypeName(String billTypeName) {
        this.billTypeName = billTypeName == null ? null : billTypeName.trim();
    }

    public String getNewestBillNo() {
        return newestBillNo;
    }

    public void setNewestBillNo(String newestBillNo) {
        this.newestBillNo = newestBillNo == null ? null : newestBillNo.trim();
    }

    public Long getNewestBillId() {
        return newestBillId;
    }

    public void setNewestBillId(Long newestBillId) {
        this.newestBillId = newestBillId;
    }

    public String getNewestBillNum() {
        return newestBillNum;
    }

    public void setNewestBillNum(String newestBillNum) {
        this.newestBillNum = newestBillNum == null ? null : newestBillNum.trim();
    }

    public String getNumCycle() {
        return numCycle;
    }

    public void setNumCycle(String numCycle) {
        this.numCycle = numCycle == null ? null : numCycle.trim();
    }

    public Integer getNumLength() {
        return numLength;
    }

    public void setNumLength(Integer numLength) {
        this.numLength = numLength;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}
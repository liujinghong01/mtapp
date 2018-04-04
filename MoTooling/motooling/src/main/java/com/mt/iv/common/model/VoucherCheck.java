package com.mt.iv.common.model;

import java.util.Date;

public class VoucherCheck {
    private Long id;

    private Integer companyId;

    private String companyName;

    private Long voucherId;

    private Integer checkBillId;

    private Long checkBillSubId;

    private Long checkBillSubSpecId;

    private Long handlerId;

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

    public Long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    public Integer getCheckBillId() {
        return checkBillId;
    }

    public void setCheckBillId(Integer checkBillId) {
        this.checkBillId = checkBillId;
    }

    public Long getCheckBillSubId() {
        return checkBillSubId;
    }

    public void setCheckBillSubId(Long checkBillSubId) {
        this.checkBillSubId = checkBillSubId;
    }

    public Long getCheckBillSubSpecId() {
        return checkBillSubSpecId;
    }

    public void setCheckBillSubSpecId(Long checkBillSubSpecId) {
        this.checkBillSubSpecId = checkBillSubSpecId;
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
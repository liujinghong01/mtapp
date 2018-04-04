package com.mt.cms.common.model;

import java.util.Date;

public class CustomerType {
    private Integer cstType;

    private String cstTypeName;

    private String cstTypeNameEn;

    private Boolean isActive;

    private Date createdAt;

    private Date updatedAt;

    private Byte status;

    public Integer getCstType() {
        return cstType;
    }

    public void setCstType(Integer cstType) {
        this.cstType = cstType;
    }

    public String getCstTypeName() {
        return cstTypeName;
    }

    public void setCstTypeName(String cstTypeName) {
        this.cstTypeName = cstTypeName == null ? null : cstTypeName.trim();
    }

    public String getCstTypeNameEn() {
        return cstTypeNameEn;
    }

    public void setCstTypeNameEn(String cstTypeNameEn) {
        this.cstTypeNameEn = cstTypeNameEn == null ? null : cstTypeNameEn.trim();
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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
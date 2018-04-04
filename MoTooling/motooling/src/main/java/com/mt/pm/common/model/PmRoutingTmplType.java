package com.mt.pm.common.model;

import java.util.Date;

public class PmRoutingTmplType {
    private Long id;

    private String tmplTypeName;

    private Long lastHandlerId;

    private Date createdAt;

    private Date updatedAt;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTmplTypeName() {
        return tmplTypeName;
    }

    public void setTmplTypeName(String tmplTypeName) {
        this.tmplTypeName = tmplTypeName == null ? null : tmplTypeName.trim();
    }

    public Long getLastHandlerId() {
        return lastHandlerId;
    }

    public void setLastHandlerId(Long lastHandlerId) {
        this.lastHandlerId = lastHandlerId;
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


    private String companyId;

    private String companyName;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }
}
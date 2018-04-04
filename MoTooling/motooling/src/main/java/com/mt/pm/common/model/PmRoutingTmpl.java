package com.mt.pm.common.model;

import java.util.Date;
import java.util.List;

public class PmRoutingTmpl {
    private Long id;

    private Long tmplTypeId;

    private String tmplTypeName;

    private String tmplName;

    private String tmplDesc;

    private String remark;

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

    public Long getTmplTypeId() {
        return tmplTypeId;
    }

    public void setTmplTypeId(Long tmplTypeId) {
        this.tmplTypeId = tmplTypeId;
    }

    public String getTmplTypeName() {
        return tmplTypeName;
    }

    public void setTmplTypeName(String tmplTypeName) {
        this.tmplTypeName = tmplTypeName;
    }

    public String getTmplName() {
        return tmplName;
    }

    public void setTmplName(String tmplName) {
        this.tmplName = tmplName == null ? null : tmplName.trim();
    }

    public String getTmplDesc() {
        return tmplDesc;
    }

    public void setTmplDesc(String tmplDesc) {
        this.tmplDesc = tmplDesc == null ? null : tmplDesc.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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


    private List<PmRoutingTmplSub> subList;
    public List<PmRoutingTmplSub> getSubList(){ return subList; }

    public  void  setSubList(List<PmRoutingTmplSub> subList){ this.subList =subList == null ? null : subList; }

}
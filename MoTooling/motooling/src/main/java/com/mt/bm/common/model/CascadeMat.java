package com.mt.bm.common.model;

import java.util.Date;

public class CascadeMat {
    private Integer id;
    private String prodType;
    private String matType;
    private String matCascadeName;
    private String matCascadeNo;
    private String remark;
    private String handlerId;
    private Date handledTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProdType() {
        return prodType;
    }

    public void setProdType(String prodType) {
        this.prodType = prodType == null ? null : prodType.trim();
    }
    public String getMatType() {
        return matType;
    }

    public void setMatType(String matType) {
        this.matType = matType == null ? null : matType.trim();
    }
    public String getMatCascadeName() {
        return matCascadeName;
    }

    public void setMatCascadeName(String matCascadeName) {
        this.matCascadeName = matCascadeName == null ? null : matCascadeName.trim();
    }
    public String getMatCascadeNo() {
        return matCascadeNo;
    }

    public void setMatCascadeNo(String matCascadeNo) {
        this.matCascadeNo = matCascadeNo == null ? null : matCascadeNo.trim();
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
    public String getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(String handlerId) {
        this.handlerId = handlerId == null ? null : handlerId.trim();
    }

    public Date getHandledTime() {
        return handledTime;
    }

    public void setHandledTime(Date handledTime) {
        this.handledTime = handledTime;
    }
}

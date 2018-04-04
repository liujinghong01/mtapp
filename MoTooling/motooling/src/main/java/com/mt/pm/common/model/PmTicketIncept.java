package com.mt.pm.common.model;

import java.util.Date;

public class PmTicketIncept {
    private Long id;

    private Long poId;

    private Long popId;

    private String confirmNo;

    private Date inceptTime;

    private Long inceptUid;

    private String inceptUidName;

    private String inceptRemark;

    private Date cancelTime;

    private Long cancelUid;

    private String cancelUidName;

    private String cancelRemark;

    private String inceptFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPoId() {
        return poId;
    }

    public void setPoId(Long poId) {
        this.poId = poId;
    }

    public Long getPopId() {
        return popId;
    }

    public void setPopId(Long popId) {
        this.popId = popId;
    }

    public String getConfirmNo() {
        return confirmNo;
    }

    public void setConfirmNo(String confirmNo) {
        this.confirmNo = confirmNo == null ? null : confirmNo.trim();
    }

    public Date getInceptTime() {
        return inceptTime;
    }

    public void setInceptTime(Date inceptTime) {
        this.inceptTime = inceptTime;
    }

    public Long getInceptUid() {
        return inceptUid;
    }

    public void setInceptUid(Long inceptUid) {
        this.inceptUid = inceptUid;
    }

    public String getInceptUidName() {
        return inceptUidName;
    }

    public void setInceptUidName(String inceptUidName) {
        this.inceptUidName = inceptUidName == null ? null : inceptUidName.trim();
    }

    public String getInceptRemark() {
        return inceptRemark;
    }

    public void setInceptRemark(String inceptRemark) {
        this.inceptRemark = inceptRemark == null ? null : inceptRemark.trim();
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Long getCancelUid() {
        return cancelUid;
    }

    public void setCancelUid(Long cancelUid) {
        this.cancelUid = cancelUid;
    }

    public String getCancelUidName() {
        return cancelUidName;
    }

    public void setCancelUidName(String cancelUidName) {
        this.cancelUidName = cancelUidName == null ? null : cancelUidName.trim();
    }

    public String getCancelRemark() {
        return cancelRemark;
    }

    public void setCancelRemark(String cancelRemark) {
        this.cancelRemark = cancelRemark == null ? null : cancelRemark.trim();
    }

    public String getInceptFlag() {
        return inceptFlag;
    }

    public void setInceptFlag(String inceptFlag) {
        this.inceptFlag = inceptFlag == null ? null : inceptFlag.trim();
    }
}
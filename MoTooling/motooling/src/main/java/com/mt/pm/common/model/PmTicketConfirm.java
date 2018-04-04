package com.mt.pm.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class PmTicketConfirm {
    private Long id;

    private Long batchProcId;

    private Long poId;

    private Long popId;

    private String confirmNo;

    private Integer confirmSeq;

    private String confirmType;

    private String confirmFlag;

    private Integer confirmQty;

    private Long deviceId;

    private Long memberId;

    private String isLeading;

    private BigDecimal realMachineTime;

    private BigDecimal realLaborTime;

    private BigDecimal realOtherTime;

    private Date realStartTime;

    private Date realFinishTime;

    private BigDecimal procMins;

    private BigDecimal realCost;

    private BigDecimal otherWorkPrice;

    private BigDecimal machineWorkPrice;

    private BigDecimal laborWorkPrice;

    private String remark;

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

    public Long getBatchProcId() {
        return batchProcId;
    }

    public void setBatchProcId(Long batchProcId) {
        this.batchProcId = batchProcId;
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

    public Integer getConfirmSeq() {
        return confirmSeq;
    }

    public void setConfirmSeq(Integer confirmSeq) {
        this.confirmSeq = confirmSeq;
    }

    public String getConfirmType() {
        return confirmType;
    }

    public void setConfirmType(String confirmType) {
        this.confirmType = confirmType == null ? null : confirmType.trim();
    }

    public String getConfirmFlag() {
        return confirmFlag;
    }

    public void setConfirmFlag(String confirmFlag) {
        this.confirmFlag = confirmFlag == null ? null : confirmFlag.trim();
    }

    public Integer getConfirmQty() {
        return confirmQty;
    }

    public void setConfirmQty(Integer confirmQty) {
        this.confirmQty = confirmQty;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getIsLeading() {
        return isLeading;
    }

    public void setIsLeading(String isLeading) {
        this.isLeading = isLeading == null ? null : isLeading.trim();
    }

    public BigDecimal getRealMachineTime() {
        return realMachineTime;
    }

    public void setRealMachineTime(BigDecimal realMachineTime) {
        this.realMachineTime = realMachineTime;
    }

    public BigDecimal getRealLaborTime() {
        return realLaborTime;
    }

    public void setRealLaborTime(BigDecimal realLaborTime) {
        this.realLaborTime = realLaborTime;
    }

    public BigDecimal getRealOtherTime() {
        return realOtherTime;
    }

    public void setRealOtherTime(BigDecimal realOtherTime) {
        this.realOtherTime = realOtherTime;
    }

    public Date getRealStartTime() {
        return realStartTime;
    }

    public void setRealStartTime(Date realStartTime) {
        this.realStartTime = realStartTime;
    }

    public Date getRealFinishTime() {
        return realFinishTime;
    }

    public void setRealFinishTime(Date realFinishTime) {
        this.realFinishTime = realFinishTime;
    }

    public BigDecimal getProcMins() {
        return procMins;
    }

    public void setProcMins(BigDecimal procMins) {
        this.procMins = procMins;
    }

    public BigDecimal getRealCost() {
        return realCost;
    }

    public void setRealCost(BigDecimal realCost) {
        this.realCost = realCost;
    }

    public BigDecimal getOtherWorkPrice() {
        return otherWorkPrice;
    }

    public void setOtherWorkPrice(BigDecimal otherWorkPrice) {
        this.otherWorkPrice = otherWorkPrice;
    }

    public BigDecimal getMachineWorkPrice() {
        return machineWorkPrice;
    }

    public void setMachineWorkPrice(BigDecimal machineWorkPrice) {
        this.machineWorkPrice = machineWorkPrice;
    }

    public BigDecimal getLaborWorkPrice() {
        return laborWorkPrice;
    }

    public void setLaborWorkPrice(BigDecimal laborWorkPrice) {
        this.laborWorkPrice = laborWorkPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
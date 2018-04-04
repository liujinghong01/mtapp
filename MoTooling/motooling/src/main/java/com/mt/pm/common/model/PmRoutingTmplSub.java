package com.mt.pm.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class PmRoutingTmplSub {
    private Long id;

    private Long tmplId;

    private Integer procSeq;

    private Long procId;

    private String procName;

    private Byte procType;

    private String procDesc;

    private Long pgId;

    private Integer deviceId;

    private BigDecimal machineTime;

    private BigDecimal laborTime;

    private BigDecimal otherTime;

    private Byte timeUnit;

    private String assUnit;

    private BigDecimal assCalcQty;

    private BigDecimal assUp;

    private BigDecimal assTime;

    private String remark;

    private String isNeedQc;

    private String isAutoAssign;

    private String isAutoAccept;

    private String isStartWarn;

    private String isEndWarn;

    private String isCanAss;

    private Integer taskPartQty;

    private Date createdAt;

    private Date updatedAt;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTmplId() {
        return tmplId;
    }

    public void setTmplId(Long tmplId) {
        this.tmplId = tmplId;
    }

    public Integer getProcSeq() {
        return procSeq;
    }

    public void setProcSeq(Integer procSeq) {
        this.procSeq = procSeq;
    }

    public Long getProcId() {
        return procId;
    }

    public void setProcId(Long procId) {
        this.procId = procId;
    }

    public String getProcName() {
        return procName;
    }

    public void setProcName(String procName) {
        this.procName = procName == null ? null : procName.trim();
    }

    public Byte getProcType() {
        return procType;
    }

    public void setProcType(Byte procType) {
        this.procType = procType;
    }

    public String getProcDesc() {
        return procDesc;
    }

    public void setProcDesc(String procDesc) {
        this.procDesc = procDesc == null ? null : procDesc.trim();
    }

    public Long getPgId() {
        return pgId;
    }

    public void setPgId(Long pgId) {
        this.pgId = pgId;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public BigDecimal getMachineTime() {
        return machineTime;
    }

    public void setMachineTime(BigDecimal machineTime) {
        this.machineTime = machineTime;
    }

    public BigDecimal getLaborTime() {
        return laborTime;
    }

    public void setLaborTime(BigDecimal laborTime) {
        this.laborTime = laborTime;
    }

    public BigDecimal getOtherTime() {
        return otherTime;
    }

    public void setOtherTime(BigDecimal otherTime) {
        this.otherTime = otherTime;
    }

    public Byte getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(Byte timeUnit) {
        this.timeUnit = timeUnit;
    }

    public String getAssUnit() {
        return assUnit;
    }

    public void setAssUnit(String assUnit) {
        this.assUnit = assUnit == null ? null : assUnit.trim();
    }

    public BigDecimal getAssCalcQty() {
        return assCalcQty;
    }

    public void setAssCalcQty(BigDecimal assCalcQty) {
        this.assCalcQty = assCalcQty;
    }

    public BigDecimal getAssUp() {
        return assUp;
    }

    public void setAssUp(BigDecimal assUp) {
        this.assUp = assUp;
    }

    public BigDecimal getAssTime() {
        return assTime;
    }

    public void setAssTime(BigDecimal assTime) {
        this.assTime = assTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getIsNeedQc() {
        return isNeedQc;
    }

    public void setIsNeedQc(String isNeedQc) {
        this.isNeedQc = isNeedQc == null ? null : isNeedQc.trim();
    }

    public String getIsAutoAssign() {
        return isAutoAssign;
    }

    public void setIsAutoAssign(String isAutoAssign) {
        this.isAutoAssign = isAutoAssign == null ? null : isAutoAssign.trim();
    }

    public String getIsAutoAccept() {
        return isAutoAccept;
    }

    public void setIsAutoAccept(String isAutoAccept) {
        this.isAutoAccept = isAutoAccept == null ? null : isAutoAccept.trim();
    }

    public String getIsStartWarn() {
        return isStartWarn;
    }

    public void setIsStartWarn(String isStartWarn) {
        this.isStartWarn = isStartWarn == null ? null : isStartWarn.trim();
    }

    public String getIsEndWarn() {
        return isEndWarn;
    }

    public void setIsEndWarn(String isEndWarn) {
        this.isEndWarn = isEndWarn == null ? null : isEndWarn.trim();
    }

    public String getIsCanAss() {
        return isCanAss;
    }

    public void setIsCanAss(String isCanAss) {
        this.isCanAss = isCanAss == null ? null : isCanAss.trim();
    }

    public Integer getTaskPartQty() {
        return taskPartQty;
    }

    public void setTaskPartQty(Integer taskPartQty) {
        this.taskPartQty = taskPartQty;
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


    private BigDecimal procQty;

    private BigDecimal assPrice;

    private String picPath;


    public BigDecimal getProcQty() {
        return procQty;
    }

    public void setProcQty(BigDecimal procQty) {
        this.procQty = procQty;
    }

    public BigDecimal getAssPrice() {
        return assPrice;
    }

    public void setAssPrice(BigDecimal assPrice) {
        this.assPrice = assPrice;
    }


    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath == null ? null : picPath.trim();
    }
}
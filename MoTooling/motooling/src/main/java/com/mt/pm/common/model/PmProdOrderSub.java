package com.mt.pm.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class PmProdOrderSub {
    private Long id;

    private String confirmNo;

    private Long poId;

    private Long procId;

    private String procName;

    private Integer procSeq;

    private String procDesc;

    private Byte procType;

    private Integer procQty;

    private Integer doneQty;

    private String isInTerminal;

    private Long pgId;

    private Long deviceId;

    private String pgName;

    private String pgType;

    private String deviceName;

    private BigDecimal machineTime;

    private BigDecimal laborTime;

    private BigDecimal otherTime;

    private BigDecimal realMachineTime;

    private BigDecimal realLaborTime;

    private BigDecimal realOtherTime;

    private Byte timeUnit;

    private Integer taskPartQty;

    private String assUnit;

    private BigDecimal assCalcQty;

    private Integer assQty;

    private BigDecimal assTime;

    private BigDecimal assUp;

    private BigDecimal assTp;

    private String assReason;

    private String assApproveStep;

    private String assApproveSugg;

    private Byte assReceivedFlag;

    private String remark;

    private Date planStartTime;

    private Date planEndTime;

    private BigDecimal planWorkTime;

    private BigDecimal planRegionLoad;

    private Date realStartTime;

    private Date realEndTime;

    private Date lastStartTime;

    private Date lastEndTime;

    private String isNeedQc;

    private Byte qcFlag;

    private Integer qcedQty;

    private String isAutoAssign;

    private String isAutoAccept;

    private String isStartWarn;

    private String isEndWarn;

    private String isRework;

    private String isRepair;

    private String popStatus;

    private Byte workType;

    private Long workerId;

    private String isCanAss;

    private String isVoteAss;

    private Date createdAt;

    private Date updatedAt;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfirmNo() {
        return confirmNo;
    }

    public void setConfirmNo(String confirmNo) {
        this.confirmNo = confirmNo == null ? null : confirmNo.trim();
    }

    public Long getPoId() {
        return poId;
    }

    public void setPoId(Long poId) {
        this.poId = poId;
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

    public Integer getProcSeq() {
        return procSeq;
    }

    public void setProcSeq(Integer procSeq) {
        this.procSeq = procSeq;
    }

    public String getProcDesc() {
        return procDesc;
    }

    public void setProcDesc(String procDesc) {
        this.procDesc = procDesc == null ? null : procDesc.trim();
    }

    public Byte getProcType() {
        return procType;
    }

    public void setProcType(Byte procType) {
        this.procType = procType;
    }

    public Integer getProcQty() {
        return procQty;
    }

    public void setProcQty(Integer procQty) {
        this.procQty = procQty;
    }

    public Integer getDoneQty() {
        return doneQty;
    }

    public void setDoneQty(Integer doneQty) {
        this.doneQty = doneQty;
    }

    public String getIsInTerminal() {
        return isInTerminal;
    }

    public void setIsInTerminal(String isInTerminal) {
        this.isInTerminal = isInTerminal == null ? null : isInTerminal.trim();
    }

    public Long getPgId() {
        return pgId;
    }

    public void setPgId(Long pgId) {
        this.pgId = pgId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
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

    public Byte getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(Byte timeUnit) {
        this.timeUnit = timeUnit;
    }

    public Integer getTaskPartQty() {
        return taskPartQty;
    }

    public void setTaskPartQty(Integer taskPartQty) {
        this.taskPartQty = taskPartQty;
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

    public Integer getAssQty() {
        return assQty;
    }

    public void setAssQty(Integer assQty) {
        this.assQty = assQty;
    }

    public BigDecimal getAssTime() {
        return assTime;
    }

    public void setAssTime(BigDecimal assTime) {
        this.assTime = assTime;
    }

    public BigDecimal getAssUp() {
        return assUp;
    }

    public void setAssUp(BigDecimal assUp) {
        this.assUp = assUp;
    }

    public BigDecimal getAssTp() {
        return assTp;
    }

    public void setAssTp(BigDecimal assTp) {
        this.assTp = assTp;
    }

    public String getAssReason() {
        return assReason;
    }

    public void setAssReason(String assReason) {
        this.assReason = assReason == null ? null : assReason.trim();
    }

    public String getAssApproveStep() {
        return assApproveStep;
    }

    public void setAssApproveStep(String assApproveStep) {
        this.assApproveStep = assApproveStep == null ? null : assApproveStep.trim();
    }

    public String getAssApproveSugg() {
        return assApproveSugg;
    }

    public void setAssApproveSugg(String assApproveSugg) {
        this.assApproveSugg = assApproveSugg == null ? null : assApproveSugg.trim();
    }

    public Byte getAssReceivedFlag() {
        return assReceivedFlag;
    }

    public void setAssReceivedFlag(Byte assReceivedFlag) {
        this.assReceivedFlag = assReceivedFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }

    public Date getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(Date planEndTime) {
        this.planEndTime = planEndTime;
    }

    public BigDecimal getPlanWorkTime() {
        return planWorkTime;
    }

    public void setPlanWorkTime(BigDecimal planWorkTime) {
        this.planWorkTime = planWorkTime;
    }

    public BigDecimal getPlanRegionLoad() {
        return planRegionLoad;
    }

    public void setPlanRegionLoad(BigDecimal planRegionLoad) {
        this.planRegionLoad = planRegionLoad;
    }

    public Date getRealStartTime() {
        return realStartTime;
    }

    public void setRealStartTime(Date realStartTime) {
        this.realStartTime = realStartTime;
    }

    public Date getRealEndTime() {
        return realEndTime;
    }

    public void setRealEndTime(Date realEndTime) {
        this.realEndTime = realEndTime;
    }

    public Date getLastStartTime() {
        return lastStartTime;
    }

    public void setLastStartTime(Date lastStartTime) {
        this.lastStartTime = lastStartTime;
    }

    public Date getLastEndTime() {
        return lastEndTime;
    }

    public void setLastEndTime(Date lastEndTime) {
        this.lastEndTime = lastEndTime;
    }

    public String getIsNeedQc() {
        return isNeedQc;
    }

    public void setIsNeedQc(String isNeedQc) {
        this.isNeedQc = isNeedQc == null ? null : isNeedQc.trim();
    }

    public Byte getQcFlag() {
        return qcFlag;
    }

    public void setQcFlag(Byte qcFlag) {
        this.qcFlag = qcFlag;
    }

    public Integer getQcedQty() {
        return qcedQty;
    }

    public void setQcedQty(Integer qcedQty) {
        this.qcedQty = qcedQty;
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

    public String getIsRework() {
        return isRework;
    }

    public void setIsRework(String isRework) {
        this.isRework = isRework == null ? null : isRework.trim();
    }

    public String getIsRepair() {
        return isRepair;
    }

    public void setIsRepair(String isRepair) {
        this.isRepair = isRepair == null ? null : isRepair.trim();
    }

    public String getPopStatus() {
        return popStatus;
    }

    public void setPopStatus(String popStatus) {
        this.popStatus = popStatus == null ? null : popStatus.trim();
    }

    public Byte getWorkType() {
        return workType;
    }

    public void setWorkType(Byte workType) {
        this.workType = workType;
    }

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }

    public String getIsCanAss() {
        return isCanAss;
    }

    public void setIsCanAss(String isCanAss) {
        this.isCanAss = isCanAss == null ? null : isCanAss.trim();
    }

    public String getIsVoteAss() {
        return isVoteAss;
    }

    public void setIsVoteAss(String isVoteAss) {
        this.isVoteAss = isVoteAss == null ? null : isVoteAss.trim();
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




    public String getPgName() {
        return pgName;
    }

    public void setPgName(String pgName) {
        this.pgName = pgName == null ? null : pgName.trim();
    }


    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }



    public String getPgType() {
        return pgType;
    }

    public void setPgType(String pgType) {
        this.pgType = pgType == null ? null : pgType.trim();
    }
}
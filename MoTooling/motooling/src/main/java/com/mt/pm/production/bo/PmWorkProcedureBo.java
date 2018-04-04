package com.mt.pm.production.bo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;

/**
 * @Author: Randy
 * @Date: Create in 2018-01-08 13:19
 * @Description: 工序对象
 * @Modified By:
 */
public class PmWorkProcedureBo {

    //todo 工艺路线中工序列表id
    private Long id;
    /**工艺路线头信息id*/
    private Long poId;
    /**部门名称*/
    private String deptName;
    /**设备名称*/
    private String deviceName;
    /**工序序号*/
    private Integer procSeq;
    /**工序id*/
    private Long procId;
    /**工序名称*/
    private String procName;
    /**工序类型*/
    private Integer procType;
    /**生产数量*/
    private Integer procQty;
    /**计划开始时间*/
    private LocalDateTime planStartTime;
    /**计划结束时间*/
    private LocalDateTime planEndTime;
    /**工序工作部门id*/
    private Long pgId;
    /**工序部门名称*/
    private String pgName;
    /**工序报工部门类型*/
    private Long pgType;
    /**公司id*/
    private Long compId;
    /**公司名称*/
    private Long compName;
    /**机器工时*/
    private BigDecimal machineWorkHours;
    /**人工工时*/
    private BigDecimal laborWorkHours;
    /**辅助工时*/
    private BigDecimal auxiliaryHours;
    /**外协工期*/
    private BigDecimal outsourcingHours;
    /**工序状态*/
    private Integer popStatus;
    /**区间负荷*/
    private BigDecimal planRegionLoad;
    /**任务工时*/
    private BigDecimal taskHours;

    public PmWorkProcedureBo() {
    }

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

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
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
        this.procName = procName;
    }

    public Integer getProcType() {
        return procType;
    }

    public void setProcType(Integer procType) {
        this.procType = procType;
    }

    public Integer getProcQty() {
        return procQty;
    }

    public void setProcQty(Integer procQty) {
        this.procQty = procQty;
    }

    public LocalDateTime getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(LocalDateTime planStartTime) {
        this.planStartTime = planStartTime;
    }

    public LocalDateTime getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(LocalDateTime planEndTime) {
        this.planEndTime = planEndTime;
    }

    public Long getPgId() {
        return pgId;
    }

    public void setPgId(Long pgId) {
        this.pgId = pgId;
    }

    public String getPgName() {
        return pgName;
    }

    public void setPgName(String pgName) {
        this.pgName = pgName;
    }

    public Long getPgType() {
        return pgType;
    }

    public void setPgType(Long pgType) {
        this.pgType = pgType;
    }

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public Long getCompName() {
        return compName;
    }

    public void setCompName(Long compName) {
        this.compName = compName;
    }

    public BigDecimal getMachineWorkHours() {
        return machineWorkHours;
    }

    public void setMachineWorkHours(BigDecimal machineWorkHours) {
        this.machineWorkHours = machineWorkHours;
    }

    public BigDecimal getLaborWorkHours() {
        return laborWorkHours;
    }

    public void setLaborWorkHours(BigDecimal laborWorkHours) {
        this.laborWorkHours = laborWorkHours;
    }

    public BigDecimal getAuxiliaryHours() {
        return auxiliaryHours;
    }

    public void setAuxiliaryHours(BigDecimal auxiliaryHours) {
        this.auxiliaryHours = auxiliaryHours;
    }

    public BigDecimal getOutsourcingHours() {
        return outsourcingHours;
    }

    public void setOutsourcingHours(BigDecimal outsourcingHours) {
        this.outsourcingHours = outsourcingHours;
    }

    public Integer getPopStatus() {
        return popStatus;
    }

    public void setPopStatus(Integer popStatus) {
        this.popStatus = popStatus;
    }

    public BigDecimal getPlanRegionLoad() {
        return planRegionLoad;
    }

    public void setPlanRegionLoad(BigDecimal planRegionLoad) {
        this.planRegionLoad = planRegionLoad;
    }

    public BigDecimal getTaskHours() {
        return taskHours;
    }

    public void setTaskHours(BigDecimal taskHours) {
        this.taskHours = taskHours;
    }

    public PmWorkProcedureBo(Long id, Long poId, String deptName, String deviceName, Integer procSeq, Long procId, String procName, Integer procType, Integer procQty, LocalDateTime planStartTime, LocalDateTime planEndTime, Long pgId, String pgName, Long pgType, Long compId, Long compName, BigDecimal machineWorkHours, BigDecimal laborWorkHours, BigDecimal auxiliaryHours, BigDecimal outsourcingHours, Integer popStatus, BigDecimal planRegionLoad, BigDecimal taskHours) {
        this.id = id;
        this.poId = poId;
        this.deptName = deptName;
        this.deviceName = deviceName;
        this.procSeq = procSeq;
        this.procId = procId;
        this.procName = procName;
        this.procType = procType;
        this.procQty = procQty;
        this.planStartTime = planStartTime;
        this.planEndTime = planEndTime;
        this.pgId = pgId;
        this.pgName = pgName;
        this.pgType = pgType;
        this.compId = compId;
        this.compName = compName;
        this.machineWorkHours = machineWorkHours;
        this.laborWorkHours = laborWorkHours;
        this.auxiliaryHours = auxiliaryHours;
        this.outsourcingHours = outsourcingHours;
        this.popStatus = popStatus;
        this.planRegionLoad = planRegionLoad;
        this.taskHours = taskHours;
    }

    @Override
    public String toString() {
        return "PmWorkProcedureBo{" +
                "id=" + id +
                ", poId=" + poId +
                ", deptName='" + deptName + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", procSeq=" + procSeq +
                ", procId=" + procId +
                ", procName='" + procName + '\'' +
                ", procType=" + procType +
                ", procQty=" + procQty +
                ", planStartTime=" + planStartTime +
                ", planEndTime=" + planEndTime +
                ", pgId=" + pgId +
                ", pgName='" + pgName + '\'' +
                ", pgType=" + pgType +
                ", compId=" + compId +
                ", compName=" + compName +
                ", machineWorkHours=" + machineWorkHours +
                ", laborWorkHours=" + laborWorkHours +
                ", auxiliaryHours=" + auxiliaryHours +
                ", outsourcingHours=" + outsourcingHours +
                ", popStatus=" + popStatus +
                ", planRegionLoad=" + planRegionLoad +
                ", taskHours=" + taskHours +
                '}';
    }

    public static Comparator procSeqComparator = new Comparator() {
        @Override
        public int compare(Object o1, Object o2) {
            return (((PmWorkProcedureBo)o1).getProcSeq() < ((PmWorkProcedureBo)o2).getProcSeq() ? -1 :
                    (((PmWorkProcedureBo)o1).getProcSeq() .equals (((PmWorkProcedureBo)o2).getProcSeq())? 0:1));
        }
    };
}

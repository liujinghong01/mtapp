package com.mt.order.common.model;

import java.math.BigDecimal;

public class ComputeProcessCost {
    private Long id;

    private Long computeId;

    private String cstMoldNo;

    private String processName;

    private BigDecimal processHours;

    private String processUnit;

    private BigDecimal costRatio;

    private BigDecimal processCost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getComputeId() {
        return computeId;
    }

    public void setComputeId(Long computeId) {
        this.computeId = computeId;
    }

    public String getCstMoldNo() {
        return cstMoldNo;
    }

    public void setCstMoldNo(String cstMoldNo) {
        this.cstMoldNo = cstMoldNo == null ? null : cstMoldNo.trim();
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName == null ? null : processName.trim();
    }

    public BigDecimal getProcessHours() {
        return processHours;
    }

    public void setProcessHours(BigDecimal processHours) {
        this.processHours = processHours;
    }

    public String getProcessUnit() {
        return processUnit;
    }

    public void setProcessUnit(String processUnit) {
        this.processUnit = processUnit == null ? null : processUnit.trim();
    }

    public BigDecimal getCostRatio() {
        return costRatio;
    }

    public void setCostRatio(BigDecimal costRatio) {
        this.costRatio = costRatio;
    }

    public BigDecimal getProcessCost() {
        return processCost;
    }

    public void setProcessCost(BigDecimal processCost) {
        this.processCost = processCost;
    }
}
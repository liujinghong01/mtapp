package com.mt.bm.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class MatLog {
    private Long matLogId;

    private Long matId;

    private Integer version;

    private String matNo;

    private String matName;

    private Long matTypeId;

    private String matTypeName;

    private Long rawnatureId;

    private String rawnatureName;

    private String matDesc;

    private BigDecimal minStockQty;

    private BigDecimal batchQty;

    private String procureStyle;

    private String isQc;

    private String unitNo;

    private BigDecimal weight;

    private String shape;

    private BigDecimal length;

    private BigDecimal width;

    private BigDecimal height;

    private String processesType;

    private BigDecimal planPrice;

    private String picPath;

    private String binNo;

    private String isUnused;

    private String uptId;

    private Date uptTime;

    private String cfmId;

    private Date cfmTime;

    private String handlerId;

    private Date handledTime;

    public Long getMatLogId() {
        return matLogId;
    }

    public void setMatLogId(Long matLogId) {
        this.matLogId = matLogId;
    }

    public Long getMatId() {
        return matId;
    }

    public void setMatId(Long matId) {
        this.matId = matId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getMatNo() {
        return matNo;
    }

    public void setMatNo(String matNo) {
        this.matNo = matNo == null ? null : matNo.trim();
    }

    public String getMatName() {
        return matName;
    }

    public void setMatName(String matName) {
        this.matName = matName == null ? null : matName.trim();
    }

    public Long getMatTypeId() {
        return matTypeId;
    }

    public void setMatTypeId(Long matTypeId) {
        this.matTypeId = matTypeId;
    }

    public String getMatTypeName() {
        return matTypeName;
    }

    public void setMatTypeName(String matTypeName) {
        this.matTypeName = matTypeName == null ? null : matTypeName.trim();
    }

    public Long getRawnatureId() {
        return rawnatureId;
    }

    public void setRawnatureId(Long rawnatureId) {
        this.rawnatureId = rawnatureId;
    }

    public String getRawnatureName() {
        return rawnatureName;
    }

    public void setRawnatureName(String rawnatureName) {
        this.rawnatureName = rawnatureName == null ? null : rawnatureName.trim();
    }

    public String getMatDesc() {
        return matDesc;
    }

    public void setMatDesc(String matDesc) {
        this.matDesc = matDesc == null ? null : matDesc.trim();
    }

    public BigDecimal getMinStockQty() {
        return minStockQty;
    }

    public void setMinStockQty(BigDecimal minStockQty) {
        this.minStockQty = minStockQty;
    }

    public BigDecimal getBatchQty() {
        return batchQty;
    }

    public void setBatchQty(BigDecimal batchQty) {
        this.batchQty = batchQty;
    }

    public String getProcureStyle() {
        return procureStyle;
    }

    public void setProcureStyle(String procureStyle) {
        this.procureStyle = procureStyle == null ? null : procureStyle.trim();
    }

    public String getIsQc() {
        return isQc;
    }

    public void setIsQc(String isQc) {
        this.isQc = isQc == null ? null : isQc.trim();
    }

    public String getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo == null ? null : unitNo.trim();
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape == null ? null : shape.trim();
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public String getProcessesType() {
        return processesType;
    }

    public void setProcessesType(String processesType) {
        this.processesType = processesType == null ? null : processesType.trim();
    }

    public BigDecimal getPlanPrice() {
        return planPrice;
    }

    public void setPlanPrice(BigDecimal planPrice) {
        this.planPrice = planPrice;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath == null ? null : picPath.trim();
    }

    public String getBinNo() {
        return binNo;
    }

    public void setBinNo(String binNo) {
        this.binNo = binNo == null ? null : binNo.trim();
    }

    public String getIsUnused() {
        return isUnused;
    }

    public void setIsUnused(String isUnused) {
        this.isUnused = isUnused == null ? null : isUnused.trim();
    }

    public String getUptId() {
        return uptId;
    }

    public void setUptId(String uptId) {
        this.uptId = uptId == null ? null : uptId.trim();
    }

    public Date getUptTime() {
        return uptTime;
    }

    public void setUptTime(Date uptTime) {
        this.uptTime = uptTime;
    }

    public String getCfmId() {
        return cfmId;
    }

    public void setCfmId(String cfmId) {
        this.cfmId = cfmId == null ? null : cfmId.trim();
    }

    public Date getCfmTime() {
        return cfmTime;
    }

    public void setCfmTime(Date cfmTime) {
        this.cfmTime = cfmTime;
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
package com.mt.order.common.model;

import java.math.BigDecimal;

public class ComputePartCost {
    private Long id;

    private Long computeId;

    private String cstMoldNo;

    private Boolean isDetailCalc;

    private String partName;

    private String partType;

    private BigDecimal partWeight;

    private String partSize;

    private String partHardness;

    private String partSupplier;

    private BigDecimal partCost;

    private Boolean isInCost;

    private Integer partCount;

    private BigDecimal rebate;

    private BigDecimal rebateRatio;

    private Boolean quench;

    private Boolean nitrogen;

    private Boolean isSpoilage;

    private BigDecimal spoilage;

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

    public Boolean getIsDetailCalc() {
        return isDetailCalc;
    }

    public void setIsDetailCalc(Boolean isDetailCalc) {
        this.isDetailCalc = isDetailCalc;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName == null ? null : partName.trim();
    }

    public String getPartType() {
        return partType;
    }

    public void setPartType(String partType) {
        this.partType = partType == null ? null : partType.trim();
    }

    public BigDecimal getPartWeight() {
        return partWeight;
    }

    public void setPartWeight(BigDecimal partWeight) {
        this.partWeight = partWeight;
    }

    public String getPartSize() {
        return partSize;
    }

    public void setPartSize(String partSize) {
        this.partSize = partSize == null ? null : partSize.trim();
    }

    public String getPartHardness() {
        return partHardness;
    }

    public void setPartHardness(String partHardness) {
        this.partHardness = partHardness == null ? null : partHardness.trim();
    }

    public String getPartSupplier() {
        return partSupplier;
    }

    public void setPartSupplier(String partSupplier) {
        this.partSupplier = partSupplier == null ? null : partSupplier.trim();
    }

    public BigDecimal getPartCost() {
        return partCost;
    }

    public void setPartCost(BigDecimal partCost) {
        this.partCost = partCost;
    }

    public Boolean getIsInCost() {
        return isInCost;
    }

    public void setIsInCost(Boolean isInCost) {
        this.isInCost = isInCost;
    }

    public Integer getPartCount() {
        return partCount;
    }

    public void setPartCount(Integer partCount) {
        this.partCount = partCount;
    }

    public BigDecimal getRebate() {
        return rebate;
    }

    public void setRebate(BigDecimal rebate) {
        this.rebate = rebate;
    }

    public BigDecimal getRebateRatio() {
        return rebateRatio;
    }

    public void setRebateRatio(BigDecimal rebateRatio) {
        this.rebateRatio = rebateRatio;
    }

    public Boolean getQuench() {
        return quench;
    }

    public void setQuench(Boolean quench) {
        this.quench = quench;
    }

    public Boolean getNitrogen() {
        return nitrogen;
    }

    public void setNitrogen(Boolean nitrogen) {
        this.nitrogen = nitrogen;
    }

    public Boolean getIsSpoilage() {
        return isSpoilage;
    }

    public void setIsSpoilage(Boolean isSpoilage) {
        this.isSpoilage = isSpoilage;
    }

    public BigDecimal getSpoilage() {
        return spoilage;
    }

    public void setSpoilage(BigDecimal spoilage) {
        this.spoilage = spoilage;
    }
}
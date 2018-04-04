package com.mt.order.common.model;

import java.math.BigDecimal;

public class ComputeCost {
    private Long id;

    private Long computeId;

    private String cstMoldNo;

    private BigDecimal stuffCost;

    private BigDecimal stuffRatio;

    private String processCostTemplate;

    private BigDecimal processCost;

    private BigDecimal processRatio;

    private BigDecimal processDifficultRatio;

    private BigDecimal designCost;

    private BigDecimal designRatio;

    private BigDecimal designHour;

    private BigDecimal designHourRatio;

    private BigDecimal tryMoldCost;

    private BigDecimal tryMoldRatio;

    private Integer tryMoldTimes;

    private BigDecimal tryMoldTimesRatio;

    private BigDecimal postBoxCost;

    private BigDecimal postBoxRatio;

    private Boolean isDeliveryCost;

    private BigDecimal deliveryCost;

    private BigDecimal deliveryCostLocal;

    private BigDecimal deliveryRatio;

    private BigDecimal machineWeight;

    private BigDecimal manageCost;

    private String manageRatio;

    private BigDecimal moldCost;

    private BigDecimal moldCostLocal;

    private BigDecimal moldUp;

    private BigDecimal moldUpLocal;

    private String profitRatio;

    private BigDecimal profitCost;

    private BigDecimal profitCostLocal;

    private BigDecimal fixingTon;

    private BigDecimal taxRatio;

    private BigDecimal taxCost;

    private BigDecimal taxCostLocal;

    private BigDecimal taxRealRatio;

    private BigDecimal taxReal;

    private BigDecimal taxVatRatio;

    private BigDecimal taxVat;

    private Byte intMode;

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

    public BigDecimal getStuffCost() {
        return stuffCost;
    }

    public void setStuffCost(BigDecimal stuffCost) {
        this.stuffCost = stuffCost;
    }

    public BigDecimal getStuffRatio() {
        return stuffRatio;
    }

    public void setStuffRatio(BigDecimal stuffRatio) {
        this.stuffRatio = stuffRatio;
    }

    public String getProcessCostTemplate() {
        return processCostTemplate;
    }

    public void setProcessCostTemplate(String processCostTemplate) {
        this.processCostTemplate = processCostTemplate == null ? null : processCostTemplate.trim();
    }

    public BigDecimal getProcessCost() {
        return processCost;
    }

    public void setProcessCost(BigDecimal processCost) {
        this.processCost = processCost;
    }

    public BigDecimal getProcessRatio() {
        return processRatio;
    }

    public void setProcessRatio(BigDecimal processRatio) {
        this.processRatio = processRatio;
    }

    public BigDecimal getProcessDifficultRatio() {
        return processDifficultRatio;
    }

    public void setProcessDifficultRatio(BigDecimal processDifficultRatio) {
        this.processDifficultRatio = processDifficultRatio;
    }

    public BigDecimal getDesignCost() {
        return designCost;
    }

    public void setDesignCost(BigDecimal designCost) {
        this.designCost = designCost;
    }

    public BigDecimal getDesignRatio() {
        return designRatio;
    }

    public void setDesignRatio(BigDecimal designRatio) {
        this.designRatio = designRatio;
    }

    public BigDecimal getDesignHour() {
        return designHour;
    }

    public void setDesignHour(BigDecimal designHour) {
        this.designHour = designHour;
    }

    public BigDecimal getDesignHourRatio() {
        return designHourRatio;
    }

    public void setDesignHourRatio(BigDecimal designHourRatio) {
        this.designHourRatio = designHourRatio;
    }

    public BigDecimal getTryMoldCost() {
        return tryMoldCost;
    }

    public void setTryMoldCost(BigDecimal tryMoldCost) {
        this.tryMoldCost = tryMoldCost;
    }

    public BigDecimal getTryMoldRatio() {
        return tryMoldRatio;
    }

    public void setTryMoldRatio(BigDecimal tryMoldRatio) {
        this.tryMoldRatio = tryMoldRatio;
    }

    public Integer getTryMoldTimes() {
        return tryMoldTimes;
    }

    public void setTryMoldTimes(Integer tryMoldTimes) {
        this.tryMoldTimes = tryMoldTimes;
    }

    public BigDecimal getTryMoldTimesRatio() {
        return tryMoldTimesRatio;
    }

    public void setTryMoldTimesRatio(BigDecimal tryMoldTimesRatio) {
        this.tryMoldTimesRatio = tryMoldTimesRatio;
    }

    public BigDecimal getPostBoxCost() {
        return postBoxCost;
    }

    public void setPostBoxCost(BigDecimal postBoxCost) {
        this.postBoxCost = postBoxCost;
    }

    public BigDecimal getPostBoxRatio() {
        return postBoxRatio;
    }

    public void setPostBoxRatio(BigDecimal postBoxRatio) {
        this.postBoxRatio = postBoxRatio;
    }

    public Boolean getIsDeliveryCost() {
        return isDeliveryCost;
    }

    public void setIsDeliveryCost(Boolean isDeliveryCost) {
        this.isDeliveryCost = isDeliveryCost;
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public BigDecimal getDeliveryCostLocal() {
        return deliveryCostLocal;
    }

    public void setDeliveryCostLocal(BigDecimal deliveryCostLocal) {
        this.deliveryCostLocal = deliveryCostLocal;
    }

    public BigDecimal getDeliveryRatio() {
        return deliveryRatio;
    }

    public void setDeliveryRatio(BigDecimal deliveryRatio) {
        this.deliveryRatio = deliveryRatio;
    }

    public BigDecimal getMachineWeight() {
        return machineWeight;
    }

    public void setMachineWeight(BigDecimal machineWeight) {
        this.machineWeight = machineWeight;
    }

    public BigDecimal getManageCost() {
        return manageCost;
    }

    public void setManageCost(BigDecimal manageCost) {
        this.manageCost = manageCost;
    }

    public String getManageRatio() {
        return manageRatio;
    }

    public void setManageRatio(String manageRatio) {
        this.manageRatio = manageRatio;
    }

    public BigDecimal getMoldCost() {
        return moldCost;
    }

    public void setMoldCost(BigDecimal moldCost) {
        this.moldCost = moldCost;
    }

    public BigDecimal getMoldCostLocal() {
        return moldCostLocal;
    }

    public void setMoldCostLocal(BigDecimal moldCostLocal) {
        this.moldCostLocal = moldCostLocal;
    }

    public BigDecimal getMoldUp() {
        return moldUp;
    }

    public void setMoldUp(BigDecimal moldUp) {
        this.moldUp = moldUp;
    }

    public BigDecimal getMoldUpLocal() {
        return moldUpLocal;
    }

    public void setMoldUpLocal(BigDecimal moldUpLocal) {
        this.moldUpLocal = moldUpLocal;
    }

    public String getProfitRatio() {
        return profitRatio;
    }

    public void setProfitRatio(String profitRatio) {
        this.profitRatio = profitRatio;
    }

    public BigDecimal getProfitCost() {
        return profitCost;
    }

    public void setProfitCost(BigDecimal profitCost) {
        this.profitCost = profitCost;
    }

    public BigDecimal getProfitCostLocal() {
        return profitCostLocal;
    }

    public void setProfitCostLocal(BigDecimal profitCostLocal) {
        this.profitCostLocal = profitCostLocal;
    }

    public BigDecimal getFixingTon() {
        return fixingTon;
    }

    public void setFixingTon(BigDecimal fixingTon) {
        this.fixingTon = fixingTon;
    }

    public BigDecimal getTaxRatio() {
        return taxRatio;
    }

    public void setTaxRatio(BigDecimal taxRatio) {
        this.taxRatio = taxRatio;
    }

    public BigDecimal getTaxCost() {
        return taxCost;
    }

    public void setTaxCost(BigDecimal taxCost) {
        this.taxCost = taxCost;
    }

    public BigDecimal getTaxCostLocal() {
        return taxCostLocal;
    }

    public void setTaxCostLocal(BigDecimal taxCostLocal) {
        this.taxCostLocal = taxCostLocal;
    }

    public BigDecimal getTaxRealRatio() {
        return taxRealRatio;
    }

    public void setTaxRealRatio(BigDecimal taxRealRatio) {
        this.taxRealRatio = taxRealRatio;
    }

    public BigDecimal getTaxReal() {
        return taxReal;
    }

    public void setTaxReal(BigDecimal taxReal) {
        this.taxReal = taxReal;
    }

    public BigDecimal getTaxVatRatio() {
        return taxVatRatio;
    }

    public void setTaxVatRatio(BigDecimal taxVatRatio) {
        this.taxVatRatio = taxVatRatio;
    }

    public BigDecimal getTaxVat() {
        return taxVat;
    }

    public void setTaxVat(BigDecimal taxVat) {
        this.taxVat = taxVat;
    }

    public Byte getIntMode() {
        return intMode;
    }

    public void setIntMode(Byte intMode) {
        this.intMode = intMode;
    }
}
package com.mt.order.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class Mold {
    private Long id;

    private Integer companyId;

    private String companyName;

    private Byte moldType;

    private Long projectHwId;

    private Long projectNotHwId;

    private String moldNo;

    private Date firstTryDate;

    private Date deliveryDate;

    private Date downTime;

    private String projectMgr;

    private String businessMgr;

    private String moldPrice;

    private BigDecimal designCost;

    private BigDecimal stuffCost;

    private BigDecimal processCost;

    private Integer paymentTimes;

    private BigDecimal realStuffCost;

    private BigDecimal realAssCost;

    private BigDecimal realProduceCost;

    private BigDecimal realProcessCost;

    private BigDecimal realTotalCost;

    private String stuffRatio;

    private String designRatio;

    private String processRatio;

    private String startupStep;

    private String isPaymentFinish;

    private BigDecimal firstMoney;

    private Date firstTime;

    private String firstInvoiceNo;

    private BigDecimal secondMoney;

    private Date secondTime;

    private String secondInvoiceNo;

    private BigDecimal thirdMoney;

    private Date thirdTime;

    private String thirdInvoiceNo;

    private BigDecimal fourthMoney;

    private Date fourthTime;

    private String fourthInvoiceNo;

    private BigDecimal restMoney;

    private String paymentRemark;

    private BigDecimal payedMoney;

    private String supType;

    private Boolean isCrtPlan;

    private Boolean isCrtBom;

    private String producePrincipal;

    private Date startMoldDate;

    private Integer batchCount;

    private Byte emergency;

    private String produceMgr;

    private String designMgr;

    private Date orderFinishDate;

    private Date fsampleTime;

    private String makeUnit;

    private String acceptUnit;

    private Date pauseStartDate;

    private Date pauseEndDate;

    private BigDecimal invoiceMoney;

    private Boolean isInvoiceFinish;

    private Byte status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public Byte getMoldType() {
        return moldType;
    }

    public void setMoldType(Byte moldType) {
        this.moldType = moldType;
    }

    public Long getProjectHwId() {
        return projectHwId;
    }

    public void setProjectHwId(Long projectHwId) {
        this.projectHwId = projectHwId;
    }

    public Long getProjectNotHwId() {
        return projectNotHwId;
    }

    public void setProjectNotHwId(Long projectNotHwId) {
        this.projectNotHwId = projectNotHwId;
    }

    public String getMoldNo() {
        return moldNo;
    }

    public void setMoldNo(String moldNo) {
        this.moldNo = moldNo == null ? null : moldNo.trim();
    }

    public Date getFirstTryDate() {
        return firstTryDate;
    }

    public void setFirstTryDate(Date firstTryDate) {
        this.firstTryDate = firstTryDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getDownTime() {
        return downTime;
    }

    public void setDownTime(Date downTime) {
        this.downTime = downTime;
    }

    public String getProjectMgr() {
        return projectMgr;
    }

    public void setProjectMgr(String projectMgr) {
        this.projectMgr = projectMgr == null ? null : projectMgr.trim();
    }

    public String getBusinessMgr() {
        return businessMgr;
    }

    public void setBusinessMgr(String businessMgr) {
        this.businessMgr = businessMgr == null ? null : businessMgr.trim();
    }

    public String getMoldPrice() {
        return moldPrice;
    }

    public void setMoldPrice(String moldPrice) {
        this.moldPrice = moldPrice == null ? null : moldPrice.trim();
    }

    public BigDecimal getDesignCost() {
        return designCost;
    }

    public void setDesignCost(BigDecimal designCost) {
        this.designCost = designCost;
    }

    public BigDecimal getStuffCost() {
        return stuffCost;
    }

    public void setStuffCost(BigDecimal stuffCost) {
        this.stuffCost = stuffCost;
    }

    public BigDecimal getProcessCost() {
        return processCost;
    }

    public void setProcessCost(BigDecimal processCost) {
        this.processCost = processCost;
    }

    public Integer getPaymentTimes() {
        return paymentTimes;
    }

    public void setPaymentTimes(Integer paymentTimes) {
        this.paymentTimes = paymentTimes;
    }

    public BigDecimal getRealStuffCost() {
        return realStuffCost;
    }

    public void setRealStuffCost(BigDecimal realStuffCost) {
        this.realStuffCost = realStuffCost;
    }

    public BigDecimal getRealAssCost() {
        return realAssCost;
    }

    public void setRealAssCost(BigDecimal realAssCost) {
        this.realAssCost = realAssCost;
    }

    public BigDecimal getRealProduceCost() {
        return realProduceCost;
    }

    public void setRealProduceCost(BigDecimal realProduceCost) {
        this.realProduceCost = realProduceCost;
    }

    public BigDecimal getRealProcessCost() {
        return realProcessCost;
    }

    public void setRealProcessCost(BigDecimal realProcessCost) {
        this.realProcessCost = realProcessCost;
    }

    public BigDecimal getRealTotalCost() {
        return realTotalCost;
    }

    public void setRealTotalCost(BigDecimal realTotalCost) {
        this.realTotalCost = realTotalCost;
    }

    public String getStuffRatio() {
        return stuffRatio;
    }

    public void setStuffRatio(String stuffRatio) {
        this.stuffRatio = stuffRatio == null ? null : stuffRatio.trim();
    }

    public String getDesignRatio() {
        return designRatio;
    }

    public void setDesignRatio(String designRatio) {
        this.designRatio = designRatio == null ? null : designRatio.trim();
    }

    public String getProcessRatio() {
        return processRatio;
    }

    public void setProcessRatio(String processRatio) {
        this.processRatio = processRatio == null ? null : processRatio.trim();
    }

    public String getStartupStep() {
        return startupStep;
    }

    public void setStartupStep(String startupStep) {
        this.startupStep = startupStep == null ? null : startupStep.trim();
    }

    public String getIsPaymentFinish() {
        return isPaymentFinish;
    }

    public void setIsPaymentFinish(String isPaymentFinish) {
        this.isPaymentFinish = isPaymentFinish == null ? null : isPaymentFinish.trim();
    }

    public BigDecimal getFirstMoney() {
        return firstMoney;
    }

    public void setFirstMoney(BigDecimal firstMoney) {
        this.firstMoney = firstMoney;
    }

    public Date getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(Date firstTime) {
        this.firstTime = firstTime;
    }

    public String getFirstInvoiceNo() {
        return firstInvoiceNo;
    }

    public void setFirstInvoiceNo(String firstInvoiceNo) {
        this.firstInvoiceNo = firstInvoiceNo == null ? null : firstInvoiceNo.trim();
    }

    public BigDecimal getSecondMoney() {
        return secondMoney;
    }

    public void setSecondMoney(BigDecimal secondMoney) {
        this.secondMoney = secondMoney;
    }

    public Date getSecondTime() {
        return secondTime;
    }

    public void setSecondTime(Date secondTime) {
        this.secondTime = secondTime;
    }

    public String getSecondInvoiceNo() {
        return secondInvoiceNo;
    }

    public void setSecondInvoiceNo(String secondInvoiceNo) {
        this.secondInvoiceNo = secondInvoiceNo == null ? null : secondInvoiceNo.trim();
    }

    public BigDecimal getThirdMoney() {
        return thirdMoney;
    }

    public void setThirdMoney(BigDecimal thirdMoney) {
        this.thirdMoney = thirdMoney;
    }

    public Date getThirdTime() {
        return thirdTime;
    }

    public void setThirdTime(Date thirdTime) {
        this.thirdTime = thirdTime;
    }

    public String getThirdInvoiceNo() {
        return thirdInvoiceNo;
    }

    public void setThirdInvoiceNo(String thirdInvoiceNo) {
        this.thirdInvoiceNo = thirdInvoiceNo == null ? null : thirdInvoiceNo.trim();
    }

    public BigDecimal getFourthMoney() {
        return fourthMoney;
    }

    public void setFourthMoney(BigDecimal fourthMoney) {
        this.fourthMoney = fourthMoney;
    }

    public Date getFourthTime() {
        return fourthTime;
    }

    public void setFourthTime(Date fourthTime) {
        this.fourthTime = fourthTime;
    }

    public String getFourthInvoiceNo() {
        return fourthInvoiceNo;
    }

    public void setFourthInvoiceNo(String fourthInvoiceNo) {
        this.fourthInvoiceNo = fourthInvoiceNo == null ? null : fourthInvoiceNo.trim();
    }

    public BigDecimal getRestMoney() {
        return restMoney;
    }

    public void setRestMoney(BigDecimal restMoney) {
        this.restMoney = restMoney;
    }

    public String getPaymentRemark() {
        return paymentRemark;
    }

    public void setPaymentRemark(String paymentRemark) {
        this.paymentRemark = paymentRemark == null ? null : paymentRemark.trim();
    }

    public BigDecimal getPayedMoney() {
        return payedMoney;
    }

    public void setPayedMoney(BigDecimal payedMoney) {
        this.payedMoney = payedMoney;
    }

    public String getSupType() {
        return supType;
    }

    public void setSupType(String supType) {
        this.supType = supType == null ? null : supType.trim();
    }

    public Boolean getIsCrtPlan() {
        return isCrtPlan;
    }

    public void setIsCrtPlan(Boolean isCrtPlan) {
        this.isCrtPlan = isCrtPlan;
    }

    public Boolean getIsCrtBom() {
        return isCrtBom;
    }

    public void setIsCrtBom(Boolean isCrtBom) {
        this.isCrtBom = isCrtBom;
    }

    public String getProducePrincipal() {
        return producePrincipal;
    }

    public void setProducePrincipal(String producePrincipal) {
        this.producePrincipal = producePrincipal == null ? null : producePrincipal.trim();
    }

    public Date getStartMoldDate() {
        return startMoldDate;
    }

    public void setStartMoldDate(Date startMoldDate) {
        this.startMoldDate = startMoldDate;
    }

    public Integer getBatchCount() {
        return batchCount;
    }

    public void setBatchCount(Integer batchCount) {
        this.batchCount = batchCount;
    }

    public Byte getEmergency() {
        return emergency;
    }

    public void setEmergency(Byte emergency) {
        this.emergency = emergency;
    }

    public String getProduceMgr() {
        return produceMgr;
    }

    public void setProduceMgr(String produceMgr) {
        this.produceMgr = produceMgr == null ? null : produceMgr.trim();
    }

    public String getDesignMgr() {
        return designMgr;
    }

    public void setDesignMgr(String designMgr) {
        this.designMgr = designMgr == null ? null : designMgr.trim();
    }

    public Date getOrderFinishDate() {
        return orderFinishDate;
    }

    public void setOrderFinishDate(Date orderFinishDate) {
        this.orderFinishDate = orderFinishDate;
    }

    public Date getFsampleTime() {
        return fsampleTime;
    }

    public void setFsampleTime(Date fsampleTime) {
        this.fsampleTime = fsampleTime;
    }

    public String getMakeUnit() {
        return makeUnit;
    }

    public void setMakeUnit(String makeUnit) {
        this.makeUnit = makeUnit == null ? null : makeUnit.trim();
    }

    public String getAcceptUnit() {
        return acceptUnit;
    }

    public void setAcceptUnit(String acceptUnit) {
        this.acceptUnit = acceptUnit == null ? null : acceptUnit.trim();
    }

    public Date getPauseStartDate() {
        return pauseStartDate;
    }

    public void setPauseStartDate(Date pauseStartDate) {
        this.pauseStartDate = pauseStartDate;
    }

    public Date getPauseEndDate() {
        return pauseEndDate;
    }

    public void setPauseEndDate(Date pauseEndDate) {
        this.pauseEndDate = pauseEndDate;
    }

    public BigDecimal getInvoiceMoney() {
        return invoiceMoney;
    }

    public void setInvoiceMoney(BigDecimal invoiceMoney) {
        this.invoiceMoney = invoiceMoney;
    }

    public Boolean getIsInvoiceFinish() {
        return isInvoiceFinish;
    }

    public void setIsInvoiceFinish(Boolean isInvoiceFinish) {
        this.isInvoiceFinish = isInvoiceFinish;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
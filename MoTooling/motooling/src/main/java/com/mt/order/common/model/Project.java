package com.mt.order.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class Project {
    private Long id;

    private String projectNo;

    private String cstOrderNo;

    private Integer companyId;

    private String companyName;

    private Integer depId;

    private String depName;

    private Long handlerId;

    private Long cstCompanyId;

    private String cstName;

    private Date orderAcceptDate;

    private Date realTime;

    private String coin;

    private String coinName;

    private BigDecimal orderMoney;

    private String coinLocal;

    private String coinLocalName;

    private BigDecimal orderMoneyLocal;

    private String invoiceType;

    private String invoiceTypeName;

    private String invoiceTo;

    private String isIncludeTax;

    private BigDecimal taxRatio;

    private String prodName;

    private String deliveryType;

    private String deliveryPlace;

    private Long salesman;

    private String salesmanName;

    private String approveStep;

    private String approveSugg;

    private Byte status;

    private Boolean isActive;

    private Date createdAt;

    private Date updatedAt;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo == null ? null : projectNo.trim();
    }

    public String getCstOrderNo() {
        return cstOrderNo;
    }

    public void setCstOrderNo(String cstOrderNo) {
        this.cstOrderNo = cstOrderNo == null ? null : cstOrderNo.trim();
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

    public Integer getDepId() {
        return depId;
    }

    public void setDepId(Integer depId) {
        this.depId = depId;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName == null ? null : depName.trim();
    }

    public Long getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(Long handlerId) {
        this.handlerId = handlerId;
    }

    public Long getCstCompanyId() {
        return cstCompanyId;
    }

    public void setCstCompanyId(Long cstCompanyId) {
        this.cstCompanyId = cstCompanyId;
    }

    public String getCstName() {
        return cstName;
    }

    public void setCstName(String cstName) {
        this.cstName = cstName == null ? null : cstName.trim();
    }

    public Date getOrderAcceptDate() {
        return orderAcceptDate;
    }

    public void setOrderAcceptDate(Date orderAcceptDate) {
        this.orderAcceptDate = orderAcceptDate;
    }

    public Date getRealTime() {
        return realTime;
    }

    public void setRealTime(Date realTime) {
        this.realTime = realTime;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin == null ? null : coin.trim();
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName == null ? null : coinName.trim();
    }

    public BigDecimal getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(BigDecimal orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getCoinLocal() {
        return coinLocal;
    }

    public void setCoinLocal(String coinLocal) {
        this.coinLocal = coinLocal == null ? null : coinLocal.trim();
    }

    public String getCoinLocalName() {
        return coinLocalName;
    }

    public void setCoinLocalName(String coinLocalName) {
        this.coinLocalName = coinLocalName == null ? null : coinLocalName.trim();
    }

    public BigDecimal getOrderMoneyLocal() {
        return orderMoneyLocal;
    }

    public void setOrderMoneyLocal(BigDecimal orderMoneyLocal) {
        this.orderMoneyLocal = orderMoneyLocal;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType == null ? null : invoiceType.trim();
    }

    public String getInvoiceTypeName() {
        return invoiceTypeName;
    }

    public void setInvoiceTypeName(String invoiceTypeName) {
        this.invoiceTypeName = invoiceTypeName == null ? null : invoiceTypeName.trim();
    }

    public String getInvoiceTo() {
        return invoiceTo;
    }

    public void setInvoiceTo(String invoiceTo) {
        this.invoiceTo = invoiceTo == null ? null : invoiceTo.trim();
    }

    public String getIsIncludeTax() {
        return isIncludeTax;
    }

    public void setIsIncludeTax(String isIncludeTax) {
        this.isIncludeTax = isIncludeTax == null ? null : isIncludeTax.trim();
    }

    public BigDecimal getTaxRatio() {
        return taxRatio;
    }

    public void setTaxRatio(BigDecimal taxRatio) {
        this.taxRatio = taxRatio;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName == null ? null : prodName.trim();
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType == null ? null : deliveryType.trim();
    }

    public String getDeliveryPlace() {
        return deliveryPlace;
    }

    public void setDeliveryPlace(String deliveryPlace) {
        this.deliveryPlace = deliveryPlace == null ? null : deliveryPlace.trim();
    }

    public Long getSalesman() {
        return salesman;
    }

    public void setSalesman(Long salesman) {
        this.salesman = salesman;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName == null ? null : salesmanName.trim();
    }

    public String getApproveStep() {
        return approveStep;
    }

    public void setApproveStep(String approveStep) {
        this.approveStep = approveStep == null ? null : approveStep.trim();
    }

    public String getApproveSugg() {
        return approveSugg;
    }

    public void setApproveSugg(String approveSugg) {
        this.approveSugg = approveSugg == null ? null : approveSugg.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}

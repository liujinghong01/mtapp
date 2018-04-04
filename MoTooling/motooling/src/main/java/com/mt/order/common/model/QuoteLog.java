package com.mt.order.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class QuoteLog {
    private Long id;

    private Integer version;

    private Integer companyId;

    private String companyName;

    private Integer depId;

    private String depName;

    private String quoteNo;

    private Integer cstCompanyId;

    private String cstName;

    private String phone;

    private String fax;

    private String email;

    private String connectMan;

    private Long salesman;

    private String salesmanName;

    private Long handler;

    private String handlerName;

    private Date quoteDate;

    private String availDays;

    private String prodName;

    private BigDecimal quotePrice;

    private BigDecimal quotePriceLocal;

    private String coin;

    private String coinLocal;

    private String deliveryPlace;

    private String approveStep;

    private String approveSugg;

    private Byte status;

    private Boolean isActive;

    private Date createdAt;

    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    public String getQuoteNo() {
        return quoteNo;
    }

    public void setQuoteNo(String quoteNo) {
        this.quoteNo = quoteNo == null ? null : quoteNo.trim();
    }

    public Integer getCstCompanyId() {
        return cstCompanyId;
    }

    public void setCstCompanyId(Integer cstCompanyId) {
        this.cstCompanyId = cstCompanyId;
    }

    public String getCstName() {
        return cstName;
    }

    public void setCstName(String cstName) {
        this.cstName = cstName == null ? null : cstName.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getConnectMan() {
        return connectMan;
    }

    public void setConnectMan(String connectMan) {
        this.connectMan = connectMan == null ? null : connectMan.trim();
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

    public Long getHandler() {
        return handler;
    }

    public void setHandler(Long handler) {
        this.handler = handler;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName == null ? null : handlerName.trim();
    }

    public Date getQuoteDate() {
        return quoteDate;
    }

    public void setQuoteDate(Date quoteDate) {
        this.quoteDate = quoteDate;
    }

    public String getAvailDays() {
        return availDays;
    }

    public void setAvailDays(String availDays) {
        this.availDays = availDays == null ? null : availDays.trim();
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName == null ? null : prodName.trim();
    }

    public BigDecimal getQuotePrice() {
        return quotePrice;
    }

    public void setQuotePrice(BigDecimal quotePrice) {
        this.quotePrice = quotePrice;
    }

    public BigDecimal getQuotePriceLocal() {
        return quotePriceLocal;
    }

    public void setQuotePriceLocal(BigDecimal quotePriceLocal) {
        this.quotePriceLocal = quotePriceLocal;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin == null ? null : coin.trim();
    }

    public String getCoinLocal() {
        return coinLocal;
    }

    public void setCoinLocal(String coinLocal) {
        this.coinLocal = coinLocal == null ? null : coinLocal.trim();
    }

    public String getDeliveryPlace() {
        return deliveryPlace;
    }

    public void setDeliveryPlace(String deliveryPlace) {
        this.deliveryPlace = deliveryPlace == null ? null : deliveryPlace.trim();
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
}
package com.mt.pc.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class PcPurchOrder {
    private Long id;

    private String purchNo;

    private Integer companyId;

    private String companyName;

    private Long supCompanyId;

    private String supName;

    private String approveGrade1Step;

    private String approveGrade1Sugg;

    private String approveGrade2Step;

    private String approveGrade2Sugg;

    private String approvePriceStep;

    private String approvePriceSugg;

    private Byte orderFlag;

    private Date orderDate;

    private Date sendDate;

    private Byte createType;

    private Long supLinkman;

    private String supLinkmanName;

    private String supLinkmanPhone;

    private String supLinkmanEmail;

    private Long purchman;

    private String purchmanName;

    private String purchmanPhone;

    private String purchmanEmail;

    private Date deliveryDate;

    private String deliveryPlace;

    private String coin;

    private Byte invoiceType;

    private BigDecimal taxRatio;

    private BigDecimal rebate;

    private BigDecimal totalQty;

    private BigDecimal totalWeight;

    private BigDecimal totalProcessCost;

    private BigDecimal totalPrice;

    private Long creatorId;

    private Long handlerId;

    private Date createdAt;

    private Date updatedAt;

    private Byte status;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPurchNo() {
        return purchNo;
    }

    public void setPurchNo(String purchNo) {
        this.purchNo = purchNo == null ? null : purchNo.trim();
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

    public Long getSupCompanyId() {
        return supCompanyId;
    }

    public void setSupCompanyId(Long supCompanyId) {
        this.supCompanyId = supCompanyId;
    }

    public String getSupName() {
        return supName;
    }

    public void setSupName(String supName) {
        this.supName = supName == null ? null : supName.trim();
    }

    public String getApproveGrade1Step() {
        return approveGrade1Step;
    }

    public void setApproveGrade1Step(String approveGrade1Step) {
        this.approveGrade1Step = approveGrade1Step == null ? null : approveGrade1Step.trim();
    }

    public String getApproveGrade1Sugg() {
        return approveGrade1Sugg;
    }

    public void setApproveGrade1Sugg(String approveGrade1Sugg) {
        this.approveGrade1Sugg = approveGrade1Sugg == null ? null : approveGrade1Sugg.trim();
    }

    public String getApproveGrade2Step() {
        return approveGrade2Step;
    }

    public void setApproveGrade2Step(String approveGrade2Step) {
        this.approveGrade2Step = approveGrade2Step == null ? null : approveGrade2Step.trim();
    }

    public String getApproveGrade2Sugg() {
        return approveGrade2Sugg;
    }

    public void setApproveGrade2Sugg(String approveGrade2Sugg) {
        this.approveGrade2Sugg = approveGrade2Sugg == null ? null : approveGrade2Sugg.trim();
    }

    public String getApprovePriceStep() {
        return approvePriceStep;
    }

    public void setApprovePriceStep(String approvePriceStep) {
        this.approvePriceStep = approvePriceStep == null ? null : approvePriceStep.trim();
    }

    public String getApprovePriceSugg() {
        return approvePriceSugg;
    }

    public void setApprovePriceSugg(String approvePriceSugg) {
        this.approvePriceSugg = approvePriceSugg == null ? null : approvePriceSugg.trim();
    }

    public Byte getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(Byte orderFlag) {
        this.orderFlag = orderFlag;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Byte getCreateType() {
        return createType;
    }

    public void setCreateType(Byte createType) {
        this.createType = createType;
    }

    public Long getSupLinkman() {
        return supLinkman;
    }

    public void setSupLinkman(Long supLinkman) {
        this.supLinkman = supLinkman;
    }

    public String getSupLinkmanName() {
        return supLinkmanName;
    }

    public void setSupLinkmanName(String supLinkmanName) {
        this.supLinkmanName = supLinkmanName == null ? null : supLinkmanName.trim();
    }

    public String getSupLinkmanPhone() {
        return supLinkmanPhone;
    }

    public void setSupLinkmanPhone(String supLinkmanPhone) {
        this.supLinkmanPhone = supLinkmanPhone == null ? null : supLinkmanPhone.trim();
    }

    public String getSupLinkmanEmail() {
        return supLinkmanEmail;
    }

    public void setSupLinkmanEmail(String supLinkmanEmail) {
        this.supLinkmanEmail = supLinkmanEmail == null ? null : supLinkmanEmail.trim();
    }

    public Long getPurchman() {
        return purchman;
    }

    public void setPurchman(Long purchman) {
        this.purchman = purchman;
    }

    public String getPurchmanName() {
        return purchmanName;
    }

    public void setPurchmanName(String purchmanName) {
        this.purchmanName = purchmanName == null ? null : purchmanName.trim();
    }

    public String getPurchmanPhone() {
        return purchmanPhone;
    }

    public void setPurchmanPhone(String purchmanPhone) {
        this.purchmanPhone = purchmanPhone == null ? null : purchmanPhone.trim();
    }

    public String getPurchmanEmail() {
        return purchmanEmail;
    }

    public void setPurchmanEmail(String purchmanEmail) {
        this.purchmanEmail = purchmanEmail == null ? null : purchmanEmail.trim();
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryPlace() {
        return deliveryPlace;
    }

    public void setDeliveryPlace(String deliveryPlace) {
        this.deliveryPlace = deliveryPlace == null ? null : deliveryPlace.trim();
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin == null ? null : coin.trim();
    }

    public Byte getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Byte invoiceType) {
        this.invoiceType = invoiceType;
    }

    public BigDecimal getTaxRatio() {
        return taxRatio;
    }

    public void setTaxRatio(BigDecimal taxRatio) {
        this.taxRatio = taxRatio;
    }

    public BigDecimal getRebate() {
        return rebate;
    }

    public void setRebate(BigDecimal rebate) {
        this.rebate = rebate;
    }

    public BigDecimal getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(BigDecimal totalQty) {
        this.totalQty = totalQty;
    }

    public BigDecimal getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(BigDecimal totalWeight) {
        this.totalWeight = totalWeight;
    }

    public BigDecimal getTotalProcessCost() {
        return totalProcessCost;
    }

    public void setTotalProcessCost(BigDecimal totalProcessCost) {
        this.totalProcessCost = totalProcessCost;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(Long handlerId) {
        this.handlerId = handlerId;
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}
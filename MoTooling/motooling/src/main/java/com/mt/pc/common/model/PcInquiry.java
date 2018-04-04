package com.mt.pc.common.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PcInquiry {
    private Long id;

    private String inquiryNo;

    private Byte dealFlag;

    private Integer companyId;

    private String companyName;

    private Long purchMan;

    private String purchManName;

    private String purchManPhone;

    private String purchManEmail;

    private Date inquiryDate;

    private Date quoteDate;

    private Date quoteEndDate;

    private Date deliveryDate;

    private String deliveryPlace;

    private String coin;

    private Byte invoiceType;

    private BigDecimal taxRatio;

    private Long creatorId;

    private Long handlerId;

    private Date createdAt;

    private Date updatedAt;

    private BigDecimal totalPrice;

    private Byte status;

    private String remark;

    private List<PcInquirySub> details=new ArrayList<PcInquirySub>();

    public List<PcInquirySub> getDetails() {
        return details;
    }
    public void setDetails(List<PcInquirySub> details) {
        this.details = details;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInquiryNo() {
        return inquiryNo;
    }

    public void setInquiryNo(String inquiryNo) {
        this.inquiryNo = inquiryNo == null ? null : inquiryNo.trim();
    }

    public Byte getDealFlag() {
        return dealFlag;
    }

    public void setDealFlag(Byte dealFlag) {
        this.dealFlag = dealFlag;
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

    public Long getPurchMan() {
        return purchMan;
    }

    public void setPurchMan(Long purchMan) {
        this.purchMan = purchMan;
    }

    public String getPurchManName() {
        return purchManName;
    }

    public void setPurchManName(String purchManName) {
        this.purchManName = purchManName == null ? null : purchManName.trim();
    }

    public String getPurchManPhone() {
        return purchManPhone;
    }

    public void setPurchManPhone(String purchManPhone) {
        this.purchManPhone = purchManPhone == null ? null : purchManPhone.trim();
    }

    public String getPurchManEmail() {
        return purchManEmail;
    }

    public void setPurchManEmail(String purchManEmail) {
        this.purchManEmail = purchManEmail == null ? null : purchManEmail.trim();
    }

    public Date getInquiryDate() {
        return inquiryDate;
    }

    public void setInquiryDate(Date inquiryDate) {
        this.inquiryDate = inquiryDate;
    }

    public Date getQuoteDate() {
        return quoteDate;
    }

    public void setQuoteDate(Date quoteDate) {
        this.quoteDate = quoteDate;
    }

    public Date getQuoteEndDate() {
        return quoteEndDate;
    }

    public void setQuoteEndDate(Date quoteEndDate) {
        this.quoteEndDate = quoteEndDate;
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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
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
package com.mt.cms.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class Supplier {
    private Long id;

    private Long supCompanyId;

    private Long companyId;

    private String contactMan;

    private String phone;

    private String email;

    private String wechat;

    private String weibo;

    private String supplyType;

    private String taxType;

    private Boolean isTax;

    private BigDecimal taxRatio;

    private String currency;

    private String creditClass;

    private String creditMark;

    private BigDecimal creditSum;

    private Integer creditTime;

    private Integer treatTimes;

    private String checkoutType;

    private Integer qualityLevel;

    private Boolean isActive;

    private Date createdAt;

    private Date updatedAt;

    private Byte status;

    private String supCompanyName;

    private String supCompanyAddress;

    private Integer supplyTypeCode;

    private Integer taxTypeCode;

    private Integer qualityLevelCode;

    private Byte inviteStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSupCompanyId() {
        return supCompanyId;
    }

    public void setSupCompanyId(Long supCompanyId) {
        this.supCompanyId = supCompanyId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getContactMan() {
        return contactMan;
    }

    public void setContactMan(String contactMan) {
        this.contactMan = contactMan == null ? null : contactMan.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat == null ? null : wechat.trim();
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo == null ? null : weibo.trim();
    }

    public String getSupplyType() {
        return supplyType;
    }

    public void setSupplyType(String supplyType) {
        this.supplyType = supplyType == null ? null : supplyType.trim();
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType == null ? null : taxType.trim();
    }

    public Boolean getIsTax() {
        return isTax;
    }

    public void setIsTax(Boolean isTax) {
        this.isTax = isTax;
    }

    public BigDecimal getTaxRatio() {
        return taxRatio;
    }

    public void setTaxRatio(BigDecimal taxRatio) {
        this.taxRatio = taxRatio;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public String getCreditClass() {
        return creditClass;
    }

    public void setCreditClass(String creditClass) {
        this.creditClass = creditClass == null ? null : creditClass.trim();
    }

    public String getCreditMark() {
        return creditMark;
    }

    public void setCreditMark(String creditMark) {
        this.creditMark = creditMark == null ? null : creditMark.trim();
    }

    public BigDecimal getCreditSum() {
        return creditSum;
    }

    public void setCreditSum(BigDecimal creditSum) {
        this.creditSum = creditSum;
    }

    public Integer getCreditTime() {
        return creditTime;
    }

    public void setCreditTime(Integer creditTime) {
        this.creditTime = creditTime;
    }

    public Integer getTreatTimes() {
        return treatTimes;
    }

    public void setTreatTimes(Integer treatTimes) {
        this.treatTimes = treatTimes;
    }

    public String getCheckoutType() {
        return checkoutType;
    }

    public void setCheckoutType(String checkoutType) {
        this.checkoutType = checkoutType == null ? null : checkoutType.trim();
    }

    public Integer getQualityLevel() {
        return qualityLevel;
    }

    public void setQualityLevel(Integer qualityLevel) {
        this.qualityLevel = qualityLevel;
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getSupCompanyName() {
        return supCompanyName;
    }

    public void setSupCompanyName(String supCompanyName) {
        this.supCompanyName = supCompanyName == null ? null : supCompanyName.trim();
    }

    public String getSupCompanyAddress() {
        return supCompanyAddress;
    }

    public void setSupCompanyAddress(String supCompanyAddress) {
        this.supCompanyAddress = supCompanyAddress == null ? null : supCompanyAddress.trim();
    }

    public Integer getSupplyTypeCode() {
        return supplyTypeCode;
    }

    public void setSupplyTypeCode(Integer supplyTypeCode) {
        this.supplyTypeCode = supplyTypeCode;
    }

    public Integer getTaxTypeCode() {
        return taxTypeCode;
    }

    public void setTaxTypeCode(Integer taxTypeCode) {
        this.taxTypeCode = taxTypeCode;
    }

    public Integer getQualityLevelCode() {
        return qualityLevelCode;
    }

    public void setQualityLevelCode(Integer qualityLevelCode) {
        this.qualityLevelCode = qualityLevelCode;
    }

    public Byte getInviteStatus() {
        return inviteStatus;
    }

    public void setInviteStatus(Byte inviteStatus) {
        this.inviteStatus = inviteStatus;
    }
}
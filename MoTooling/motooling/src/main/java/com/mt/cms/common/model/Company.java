package com.mt.cms.common.model;

import java.util.Date;

public class Company {
    private Long id;

    private Integer companyId;

    private String companyName;

    private String companyFullName;

    private String companyCode;

    private String regNo;

    private String bizDes;

    private String bizSize;

    private String legalPerson;

    private String licence;

    private String ceo;

    private String phone;

    private String ceoId;

    private String ceoWechat;

    private String email;

    private String fax;

    private String website;

    private String wechatPubAcct;

    private Short industry;

    private String weibo;

    private Short companyType;

    private String country;

    private String province;

    private String city;

    private String address;

    private String postcode;

    private String credibility;

    private Boolean stickness;

    private Integer qualityLevel;

    private Byte platCompanyType;

    private Boolean isActive;

    private Date createdAt;

    private Date updatedAt;

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

    public String getCompanyFullName() {
        return companyFullName;
    }

    public void setCompanyFullName(String companyFullName) {
        this.companyFullName = companyFullName == null ? null : companyFullName.trim();
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode == null ? null : companyCode.trim();
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo == null ? null : regNo.trim();
    }

    public String getBizDes() {
        return bizDes;
    }

    public void setBizDes(String bizDes) {
        this.bizDes = bizDes == null ? null : bizDes.trim();
    }

    public String getBizSize() {
        return bizSize;
    }

    public void setBizSize(String bizSize) {
        this.bizSize = bizSize == null ? null : bizSize.trim();
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson == null ? null : legalPerson.trim();
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence == null ? null : licence.trim();
    }

    public String getCeo() {
        return ceo;
    }

    public void setCeo(String ceo) {
        this.ceo = ceo == null ? null : ceo.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getCeoId() {
        return ceoId;
    }

    public void setCeoId(String ceoId) {
        this.ceoId = ceoId == null ? null : ceoId.trim();
    }

    public String getCeoWechat() {
        return ceoWechat;
    }

    public void setCeoWechat(String ceoWechat) {
        this.ceoWechat = ceoWechat == null ? null : ceoWechat.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website == null ? null : website.trim();
    }

    public String getWechatPubAcct() {
        return wechatPubAcct;
    }

    public void setWechatPubAcct(String wechatPubAcct) {
        this.wechatPubAcct = wechatPubAcct == null ? null : wechatPubAcct.trim();
    }

    public Short getIndustry() {
        return industry;
    }

    public void setIndustry(Short industry) {
        this.industry = industry;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo == null ? null : weibo.trim();
    }

    public Short getCompanyType() {
        return companyType;
    }

    public void setCompanyType(Short companyType) {
        this.companyType = companyType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    public String getCredibility() {
        return credibility;
    }

    public void setCredibility(String credibility) {
        this.credibility = credibility == null ? null : credibility.trim();
    }

    public Boolean getStickness() {
        return stickness;
    }

    public void setStickness(Boolean stickness) {
        this.stickness = stickness;
    }

    public Integer getQualityLevel() {
        return qualityLevel;
    }

    public void setQualityLevel(Integer qualityLevel) {
        this.qualityLevel = qualityLevel;
    }

    public Byte getPlatCompanyType() {
        return platCompanyType;
    }

    public void setPlatCompanyType(Byte platCompanyType) {
        this.platCompanyType = platCompanyType;
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
}
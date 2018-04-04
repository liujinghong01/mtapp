package com.mt.cms.common.model;

import java.util.Date;

public class Customer {
    private Long id;

    private Long cstCompanyId;

    private Long companyId;

    private String contactMan;

    private String phone;

    private String wechat;

    private String email;

    private String weibo;

    private Integer cstType;

    private String product;

    private String taxNo;

    private Boolean isActive;

    private Date createdAt;

    private Date updatedAt;

    private Byte status;

    private String cstCompanyName;

    private String cstCompanyAddress;

    private Integer cstTypeCode;

    private Integer grade;

    private Integer gradeCode;

    private String creditClass;

    private String creditClassCode;

    private Integer important;

    private Integer importantCode;

    private Byte inviteStatus;

    private String coin;

    private String coinName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCstCompanyId() {
        return cstCompanyId;
    }

    public void setCstCompanyId(Long cstCompanyId) {
        this.cstCompanyId = cstCompanyId;
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

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat == null ? null : wechat.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo == null ? null : weibo.trim();
    }

    public Integer getCstType() {
        return cstType;
    }

    public void setCstType(Integer cstType) {
        this.cstType = cstType;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product == null ? null : product.trim();
    }

    public String getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo == null ? null : taxNo.trim();
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

    public String getCstCompanyName() {
        return cstCompanyName;
    }

    public void setCstCompanyName(String cstCompanyName) {
        this.cstCompanyName = cstCompanyName == null ? null : cstCompanyName.trim();
    }

    public String getCstCompanyAddress() {
        return cstCompanyAddress;
    }

    public void setCstCompanyAddress(String cstCompanyAddress) {
        this.cstCompanyAddress = cstCompanyAddress == null ? null : cstCompanyAddress.trim();
    }

    public Integer getCstTypeCode() {
        return cstTypeCode;
    }

    public void setCstTypeCode(Integer cstTypeCode) {
        this.cstTypeCode = cstTypeCode;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(Integer gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getCreditClass() {
        return creditClass;
    }

    public void setCreditClass(String creditClass) {
        this.creditClass = creditClass == null ? null : creditClass.trim();
    }

    public String getCreditClassCode() {
        return creditClassCode;
    }

    public void setCreditClassCode(String creditClassCode) {
        this.creditClassCode = creditClassCode == null ? null : creditClassCode.trim();
    }

    public Integer getImportant() {
        return important;
    }

    public void setImportant(Integer important) {
        this.important = important;
    }

    public Integer getImportantCode() {
        return importantCode;
    }

    public void setImportantCode(Integer importantCode) {
        this.importantCode = importantCode;
    }

    public Byte getInviteStatus() {
        return inviteStatus;
    }

    public void setInviteStatus(Byte inviteStatus) {
        this.inviteStatus = inviteStatus;
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
}
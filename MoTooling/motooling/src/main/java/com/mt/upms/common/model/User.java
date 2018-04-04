package com.mt.upms.common.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    //0:禁止登录
    public static final Long _0 = new Long(0);
    //1:有效
    public static final Long _1 = new Long(1);


    private String provider;// 用户类型

    private String toKen;// 用户认证

    private String clientType;

    ////////////////////////////////////////////////

    private Long id;

    private Long uid;

    private String usercode;

    private String fullname;

    private String username;

    private String password;

    private String mobile;

    private Boolean mobileValidated;

    private String email;

    private Boolean emailValidated;

    private Byte bandedEmail;

    private String wechat;

    private String openId;

    private Boolean wechatValidated;

    private String avatar;

    private String qq;

    private String weibo;

    private Byte gender;

    private Date birthdate;

    private String address;

    private String country;

    private String province;

    private String city;

    private Short countryId;

    private Short provinceId;

    private String postcode;

    private Long referee;

    private String initial;

    private Integer attachedCompany;

    private Byte stickness;

    private String groupUser;

    private Integer preferLanguage;

    private String ipAddress;

    private String apiToken;

    private Date lastLoginTime;

    private Byte status;

    private Boolean isActive;

    private Date createdAt;

    private Date updatedAt;

    private Byte inviteStatus;

    private Long companyId;

    private String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode == null ? null : usercode.trim();
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname == null ? null : fullname.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Boolean getMobileValidated() {
        return mobileValidated;
    }

    public void setMobileValidated(Boolean mobileValidated) {
        this.mobileValidated = mobileValidated;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Boolean getEmailValidated() {
        return emailValidated;
    }

    public void setEmailValidated(Boolean emailValidated) {
        this.emailValidated = emailValidated;
    }

    public Byte getBandedEmail() {
        return bandedEmail;
    }

    public void setBandedEmail(Byte bandedEmail) {
        this.bandedEmail = bandedEmail;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat == null ? null : wechat.trim();
    }


    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Boolean getWechatValidated() {
        return wechatValidated;
    }

    public void setWechatValidated(Boolean wechatValidated) {
        this.wechatValidated = wechatValidated;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo == null ? null : weibo.trim();
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
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

    public Short getCountryId() {
        return countryId;
    }

    public void setCountryId(Short countryId) {
        this.countryId = countryId;
    }

    public Short getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Short provinceId) {
        this.provinceId = provinceId;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    public Long getReferee() {
        return referee;
    }

    public void setReferee(Long referee) {
        this.referee = referee;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial == null ? null : initial.trim();
    }

    public Integer getAttachedCompany() {
        return attachedCompany;
    }

    public void setAttachedCompany(Integer attachedCompany) {
        this.attachedCompany = attachedCompany;
    }

    public Byte getStickness() {
        return stickness;
    }

    public void setStickness(Byte stickness) {
        this.stickness = stickness;
    }

    public String getGroupUser() {
        return groupUser;
    }

    public void setGroupUser(String groupUser) {
        this.groupUser = groupUser == null ? null : groupUser.trim();
    }

    public Integer getPreferLanguage() {
        return preferLanguage;
    }

    public void setPreferLanguage(Integer preferLanguage) {
        this.preferLanguage = preferLanguage;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress == null ? null : ipAddress.trim();
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken == null ? null : apiToken.trim();
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
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

    public Byte getInviteStatus() {
        return inviteStatus;
    }

    public void setInviteStatus(Byte inviteStatus) {
        this.inviteStatus = inviteStatus;
    }

    ///////////////////////////////////////////


    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        provider = provider;
    }

    public String getToKen() {
        return toKen;
    }

    public void setToKen(String toKen) {
        this.toKen = toKen;
    }


    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public User() {
    }

    public User(User user) {
        this.id = user.getId();
        this.uid = user.getUid();
        this.usercode = user.getUsercode();
        this.fullname = user.getFullname();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.mobile = user.getMobile();
        this.mobileValidated = user.getMobileValidated();
        this.email = user.getEmail();
        this.emailValidated = user.getEmailValidated();
        this.bandedEmail = user.getBandedEmail();
        this.wechat = user.getWechat();
        this.wechatValidated = user.getWechatValidated();
        this.avatar = user.getAvatar();
        this.qq = user.getQq();
        this.weibo = user.getWeibo();
        this.gender = user.getGender();
        this.birthdate = user.getBirthdate();
        this.address = user.getAddress();
        this.country = user.getCountry();
        this.province = user.getProvince();
        this.city = user.getCity();
        this.countryId = user.getCountryId();
        this.provinceId = user.getProvinceId();
        this.postcode = user.getPostcode();
        this.referee = user.getReferee();
        this.initial = user.getInitial();
        this.status = user.getStatus();
        this.lastLoginTime = user.getLastLoginTime();
        this.isActive = user.getIsActive();
        this.stickness = user.getStickness();
        this.groupUser = user.getGroupUser();
        this.preferLanguage = user.getPreferLanguage();
        this.ipAddress = user.getIpAddress();
        this.apiToken = user.getApiToken();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }

}
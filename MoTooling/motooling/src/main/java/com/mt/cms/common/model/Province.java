package com.mt.cms.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class Province {
    private Integer provinceId;

    private Integer countryId;

    private String name;

    private String digitCode;

    private String englishCode;

    private Integer regionId;

    private String nameEn;

    private String taxName;

    private BigDecimal taxRate1;

    private BigDecimal taxRate2;

    private Boolean isActive;

    private Date createdAt;

    private Date updatedAt;

    private Byte status;

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDigitCode() {
        return digitCode;
    }

    public void setDigitCode(String digitCode) {
        this.digitCode = digitCode == null ? null : digitCode.trim();
    }

    public String getEnglishCode() {
        return englishCode;
    }

    public void setEnglishCode(String englishCode) {
        this.englishCode = englishCode == null ? null : englishCode.trim();
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn == null ? null : nameEn.trim();
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName == null ? null : taxName.trim();
    }

    public BigDecimal getTaxRate1() {
        return taxRate1;
    }

    public void setTaxRate1(BigDecimal taxRate1) {
        this.taxRate1 = taxRate1;
    }

    public BigDecimal getTaxRate2() {
        return taxRate2;
    }

    public void setTaxRate2(BigDecimal taxRate2) {
        this.taxRate2 = taxRate2;
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
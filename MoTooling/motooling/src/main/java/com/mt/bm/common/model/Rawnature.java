package com.mt.bm.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class Rawnature {
    private Long id;

    private String companyId;

    private String companyName;

    private String rawnatureType;

    private String rawnatureName;

    private BigDecimal density;

    private String densityUnit;

    private BigDecimal procurePrice;

    private String firstProvider;

    private String isUnused;

    private String handlerId;

    private Date handledTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getRawnatureType() {
        return rawnatureType;
    }

    public void setRawnatureType(String rawnatureType) {
        this.rawnatureType = rawnatureType == null ? null : rawnatureType.trim();
    }

    public String getRawnatureName() {
        return rawnatureName;
    }

    public void setRawnatureName(String rawnatureName) {
        this.rawnatureName = rawnatureName == null ? null : rawnatureName.trim();
    }

    public BigDecimal getDensity() {
        return density;
    }

    public void setDensity(BigDecimal density) {
        this.density = density;
    }

    public String getDensityUnit() {
        return densityUnit;
    }

    public void setDensityUnit(String densityUnit) {
        this.densityUnit = densityUnit == null ? null : densityUnit.trim();
    }

    public BigDecimal getProcurePrice() {
        return procurePrice;
    }

    public void setProcurePrice(BigDecimal procurePrice) {
        this.procurePrice = procurePrice;
    }

    public String getFirstProvider() {
        return firstProvider;
    }

    public void setFirstProvider(String firstProvider) {
        this.firstProvider = firstProvider == null ? null : firstProvider.trim();
    }

    public String getIsUnused() {
        return isUnused;
    }

    public void setIsUnused(String isUnused) {
        this.isUnused = isUnused == null ? null : isUnused.trim();
    }

    public String getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(String handlerId) {
        this.handlerId = handlerId == null ? null : handlerId.trim();
    }

    public Date getHandledTime() {
        return handledTime;
    }

    public void setHandledTime(Date handledTime) {
        this.handledTime = handledTime;
    }
}
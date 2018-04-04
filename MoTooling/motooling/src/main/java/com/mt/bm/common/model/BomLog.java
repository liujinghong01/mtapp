package com.mt.bm.common.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class BomLog {
    private Long bomLogId;

    private Long bomId;

    private String companyId;

    private String companyName;

    private String moldNo;

    private String bomType;

    private String crtId;

    private Date crtTime;

    private String uptId;

    private Date uptTime;

    private String uptReason;

    private String appId;

    private Date appTime;

    private String appFlag;

    private String appRemark;

    private Long edition;

    private Long maxNodepos;

    private BigDecimal planStuffCost;

    private String handlerId;

    private Date handledTime;

    public Long getBomLogId() {
        return bomLogId;
    }

    public void setBomLogId(Long bomLogId) {
        this.bomLogId = bomLogId;
    }

    public Long getBomId() {
        return bomId;
    }

    public void setBomId(Long bomId) {
        this.bomId = bomId;
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

    public String getMoldNo() {
        return moldNo;
    }

    public void setMoldNo(String moldNo) {
        this.moldNo = moldNo == null ? null : moldNo.trim();
    }

    public String getBomType() {
        return bomType;
    }

    public void setBomType(String bomType) {
        this.bomType = bomType == null ? null : bomType.trim();
    }

    public String getCrtId() {
        return crtId;
    }

    public void setCrtId(String crtId) {
        this.crtId = crtId == null ? null : crtId.trim();
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public String getUptId() {
        return uptId;
    }

    public void setUptId(String uptId) {
        this.uptId = uptId == null ? null : uptId.trim();
    }

    public Date getUptTime() {
        return uptTime;
    }

    public void setUptTime(Date uptTime) {
        this.uptTime = uptTime;
    }

    public String getUptReason() {
        return uptReason;
    }

    public void setUptReason(String uptReason) {
        this.uptReason = uptReason == null ? null : uptReason.trim();
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public Date getAppTime() {
        return appTime;
    }

    public void setAppTime(Date appTime) {
        this.appTime = appTime;
    }

    public String getAppFlag() {
        return appFlag;
    }

    public void setAppFlag(String appFlag) {
        this.appFlag = appFlag == null ? null : appFlag.trim();
    }

    public String getAppRemark() {
        return appRemark;
    }

    public void setAppRemark(String appRemark) {
        this.appRemark = appRemark == null ? null : appRemark.trim();
    }

    public Long getEdition() {
        return edition;
    }

    public void setEdition(Long edition) {
        this.edition = edition;
    }

    public Long getMaxNodepos() {
        return maxNodepos;
    }

    public void setMaxNodepos(Long maxNodepos) {
        this.maxNodepos = maxNodepos;
    }

    public BigDecimal getPlanStuffCost() {
        return planStuffCost;
    }

    public void setPlanStuffCost(BigDecimal planStuffCost) {
        this.planStuffCost = planStuffCost;
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


    private List<BomNode> bomNodeList;
    public List<BomNode> getBomNodeList(){return bomNodeList;}

    public  void setBomNodeList(List<BomNode> bomNodeList){this.bomNodeList=bomNodeList;}
}
package com.mt.bm.common.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Bom {
    private Long bomId;

    private String companyId;

    private String companyName;

    private String moldNo;

    private String bomType;

    private String appId;

    private Date appTime;

    private String appFlag;

    private String appRemark;

    private Integer edition;

    private Integer maxNodepos;

    private BigDecimal planStuffCost;

    private  String isShow;

    private String handlerId;

    private Date handledTime;

    private List<BomNode> bomNodeList;

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

    public Integer getEdition() {
        return edition;
    }

    public void setEdition(Integer edition) {
        this.edition = edition;
    }

    public Integer getMaxNodepos() {
        return maxNodepos;
    }

    public void setMaxNodepos(Integer maxNodepos) {
        this.maxNodepos = maxNodepos;
    }

    public BigDecimal getPlanStuffCost() {
        return planStuffCost;
    }

    public void setPlanStuffCost(BigDecimal planStuffCost) {
        this.planStuffCost = planStuffCost;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow == null ? null : isShow.trim();
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


    public List<BomNode> getBomNodeList(){return bomNodeList;}

    public  void setBomNodeList(List<BomNode> bomNodeList){this.bomNodeList=bomNodeList;}


    private Date firstTryDate;

    public Date getFirstTryDate() {
        return firstTryDate;
    }

    public void setFirstTryDate(Date firstTryDate) {
        this.firstTryDate = firstTryDate;
    }
}
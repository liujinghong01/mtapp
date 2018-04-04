package com.mt.pm.common.model;

import java.util.Date;

public class PmProdReq {
    private Long id;

    private Integer companyId;

    private String companyName;

    private String reqType;

    private String moldNo;

    private String bomType;

    private String matId;

    private Integer version;

    private String matNo;

    private String matName;

    private Long matTypeId;

    private String matTypeName;

    private String matModel;

    private String unit;

    private String unitName;

    private Integer nodePos;

    private Integer reqQty;

    private Integer undealQty;

    private Date deliveryDate;

    private String isFinished;

    private String pri;

    private String isCore;

    private Date planDate;

    private String reqState;

    private Date subMatRcvDate;

    private Long handlerId;

    private Date createdAt;

    private Date updatedAt;

    private String status;

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

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType == null ? null : reqType.trim();
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

    public String getMatId() {
        return matId;
    }

    public void setMatId(String matId) {
        this.matId = matId == null ? null : matId.trim();
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getMatNo() {
        return matNo;
    }

    public void setMatNo(String matNo) {
        this.matNo = matNo == null ? null : matNo.trim();
    }

    public String getMatName() {
        return matName;
    }

    public void setMatName(String matName) {
        this.matName = matName == null ? null : matName.trim();
    }

    public Long getMatTypeId() {
        return matTypeId;
    }

    public void setMatTypeId(Long matTypeId) {
        this.matTypeId = matTypeId;
    }

    public String getMatTypeName() {
        return matTypeName;
    }

    public void setMatTypeName(String matTypeName) {
        this.matTypeName = matTypeName == null ? null : matTypeName.trim();
    }

    public String getMatModel() {
        return matModel;
    }

    public void setMatModel(String matModel) {
        this.matModel = matModel == null ? null : matModel.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    public Integer getNodePos() {
        return nodePos;
    }

    public void setNodePos(Integer nodePos) {
        this.nodePos = nodePos;
    }

    public Integer getReqQty() {
        return reqQty;
    }

    public void setReqQty(Integer reqQty) {
        this.reqQty = reqQty;
    }

    public Integer getUndealQty() {
        return undealQty;
    }

    public void setUndealQty(Integer undealQty) {
        this.undealQty = undealQty;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(String isFinished) {
        this.isFinished = isFinished == null ? null : isFinished.trim();
    }

    public String getPri() {
        return pri;
    }

    public void setPri(String pri) {
        this.pri = pri == null ? null : pri.trim();
    }

    public String getIsCore() {
        return isCore;
    }

    public void setIsCore(String isCore) {
        this.isCore = isCore == null ? null : isCore.trim();
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public String getReqState() {
        return reqState;
    }

    public void setReqState(String reqState) {
        this.reqState = reqState == null ? null : reqState.trim();
    }

    public Date getSubMatRcvDate() {
        return subMatRcvDate;
    }

    public void setSubMatRcvDate(Date subMatRcvDate) {
        this.subMatRcvDate = subMatRcvDate;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }


    private Long rawnatureId;

    private String rawnatureName;

    public Long getRawnatureId() {
        return rawnatureId;
    }

    public void setRawnatureId(Long rawnatureId) {
        this.rawnatureId = rawnatureId;
    }

    public String getRawnatureName() {
        return rawnatureName;
    }

    public void setRawnatureName(String rawnatureName) {
        this.rawnatureName = rawnatureName == null ? null : rawnatureName.trim();
    }



    private Date firstTryDate;

    public Date getFirstTryDate() {
        return firstTryDate;
    }

    public void setFirstTryDate(Date firstTryDate) {
        this.firstTryDate = firstTryDate;
    }


    private Long bomId;

    public Long getBomId() {
        return bomId;
    }

    public void setBomId(Long bomId) {
        this.bomId = bomId;
    }
}
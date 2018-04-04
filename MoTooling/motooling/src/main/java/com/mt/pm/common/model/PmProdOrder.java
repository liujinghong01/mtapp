package com.mt.pm.common.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class PmProdOrder {
    private Long id;

    private String poNo;

    private Long companyId;

    private String companyName;

    private String moldNo;

    private String bomType;

    private Long nodePos;

    private Long reqId;

    private String matId;

    private Integer version;

    private String matNo;

    private String matName;

    private Long matTypeId;

    private String matTypeName;

    private String matModel;

    private String unit;

    private String unitName;

    private Date firstTryDate;

    private String relatePic;

    private Long rawnatureId;

    private String rawnatureName;

    private Integer bomQty;

    private String projectPic;

    private Integer prodQty;

    private Integer confirmQty;

    private Integer scrapQty;

    private BigDecimal receivedQty;

    private String poStatus;

    private String remark;

    private Byte scheduleMode;

    private Date planStartTime;

    private Date planEndTime;

    private Date realStartTime;

    private Date realEndTime;

    private String pri;

    private Long creatorId;

    private Long handlerId;

    private String isScheduled;

    private Date createdAt;

    private Date updatedAt;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo == null ? null : poNo.trim();
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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

    public Long getNodePos() {
        return nodePos;
    }

    public void setNodePos(Long nodePos) {
        this.nodePos = nodePos;
    }

    public Long getReqId() {
        return reqId;
    }

    public void setReqId(Long reqId) {
        this.reqId = reqId;
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

    public Date getFirstTryDate() {
        return firstTryDate;
    }

    public void setFirstTryDate(Date firstTryDate) {
        this.firstTryDate = firstTryDate;
    }

    public String getRelatePic() {
        return relatePic;
    }

    public void setRelatePic(String relatePic) {
        this.relatePic = relatePic == null ? null : relatePic.trim();
    }

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

    public Integer getBomQty() {
        return bomQty;
    }

    public void setBomQty(Integer bomQty) {
        this.bomQty = bomQty;
    }

    public String getProjectPic() {
        return projectPic;
    }

    public void setProjectPic(String projectPic) {
        this.projectPic = projectPic == null ? null : projectPic.trim();
    }

    public Integer getProdQty() {
        return prodQty;
    }

    public void setProdQty(Integer prodQty) {
        this.prodQty = prodQty;
    }

    public Integer getConfirmQty() {
        return confirmQty;
    }

    public void setConfirmQty(Integer confirmQty) {
        this.confirmQty = confirmQty;
    }

    public Integer getScrapQty() {
        return scrapQty;
    }

    public void setScrapQty(Integer scrapQty) {
        this.scrapQty = scrapQty;
    }

    public BigDecimal getReceivedQty() {
        return receivedQty;
    }

    public void setReceivedQty(BigDecimal receivedQty) {
        this.receivedQty = receivedQty;
    }

    public String getPoStatus() {
        return poStatus;
    }

    public void setPoStatus(String poStatus) {
        this.poStatus = poStatus == null ? null : poStatus.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Byte getScheduleMode() {
        return scheduleMode;
    }

    public void setScheduleMode(Byte scheduleMode) {
        this.scheduleMode = scheduleMode;
    }

    public Date getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }

    public Date getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(Date planEndTime) {
        this.planEndTime = planEndTime;
    }

    public Date getRealStartTime() {
        return realStartTime;
    }

    public void setRealStartTime(Date realStartTime) {
        this.realStartTime = realStartTime;
    }

    public Date getRealEndTime() {
        return realEndTime;
    }

    public void setRealEndTime(Date realEndTime) {
        this.realEndTime = realEndTime;
    }

    public String getPri() {
        return pri;
    }

    public void setPri(String pri) {
        this.pri = pri == null ? null : pri.trim();
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

    public String getIsScheduled() {
        return isScheduled;
    }

    public void setIsScheduled(String isScheduled) {
        this.isScheduled = isScheduled == null ? null : isScheduled.trim();
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



    private BigDecimal urgencyLevel;

    public BigDecimal getUrgencyLevel() {
        return urgencyLevel;
    }

    public void setUrgencyLevel(BigDecimal urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    private List<PmProdOrderSub> prodDetailList;
    public List<PmProdOrderSub> getProdDetailList(){ return prodDetailList; }

    public  void  setProdDetailList(List<PmProdOrderSub> prodDetailList){ this.prodDetailList =prodDetailList == null ? null : prodDetailList; }



    private Long bomId;

    public Long getBomId() {
        return bomId;
    }

    public void setBomId(Long bomId) {
        this.bomId = bomId;
    }
}
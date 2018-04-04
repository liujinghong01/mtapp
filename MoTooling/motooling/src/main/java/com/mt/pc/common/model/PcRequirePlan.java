package com.mt.pc.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class PcRequirePlan {
    private Long id;

    private Integer companyId;

    private String companyName;

    private Byte createType;

    private String createTypeName;

    private Long requestId;

    private Long requestSubId;

    private Long bomId;

    private Long nodePos;

    private Long projectHwId;

    private Long projectNotHwId;

    private String coin;

    private String coinName;

    private Byte dealFlag;

    private Long matId;

    private String matNo;

    private String matName;

    private String matDesc;

    private Long matTypeId;

    private String matTypeName;

    private String matModel;

    private BigDecimal planPrice;

    private String unit;

    private BigDecimal planQty;

    private BigDecimal planUp;

    private BigDecimal planWeight;

    private BigDecimal totalPrice;

    private Date deliveryDate;

    private BigDecimal buyQty;

    private Byte supplyType;

    private String supplyTypeName;

    private Long handlerId;

    private Date createdAt;

    private Date updatedAt;

    private Byte status;

    private String remark;

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

    public Byte getCreateType() {
        return createType;
    }

    public void setCreateType(Byte createType) {
        this.createType = createType;
    }

    public String getCreateTypeName() {
        return createTypeName;
    }

    public void setCreateTypeName(String createTypeName) {
        this.createTypeName = createTypeName == null ? null : createTypeName.trim();
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getRequestSubId() {
        return requestSubId;
    }

    public void setRequestSubId(Long requestSubId) {
        this.requestSubId = requestSubId;
    }

    public Long getBomId() {
        return bomId;
    }

    public void setBomId(Long bomId) {
        this.bomId = bomId;
    }

    public Long getNodePos() {
        return nodePos;
    }

    public void setNodePos(Long nodePos) {
        this.nodePos = nodePos;
    }

    public Long getProjectHwId() {
        return projectHwId;
    }

    public void setProjectHwId(Long projectHwId) {
        this.projectHwId = projectHwId;
    }

    public Long getProjectNotHwId() {
        return projectNotHwId;
    }

    public void setProjectNotHwId(Long projectNotHwId) {
        this.projectNotHwId = projectNotHwId;
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

    public Byte getDealFlag() {
        return dealFlag;
    }

    public void setDealFlag(Byte dealFlag) {
        this.dealFlag = dealFlag;
    }

    public Long getMatId() {
        return matId;
    }

    public void setMatId(Long matId) {
        this.matId = matId;
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

    public String getMatDesc() {
        return matDesc;
    }

    public void setMatDesc(String matDesc) {
        this.matDesc = matDesc == null ? null : matDesc.trim();
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

    public BigDecimal getPlanPrice() {
        return planPrice;
    }

    public void setPlanPrice(BigDecimal planPrice) {
        this.planPrice = planPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public BigDecimal getPlanQty() {
        return planQty;
    }

    public void setPlanQty(BigDecimal planQty) {
        this.planQty = planQty;
    }

    public BigDecimal getPlanUp() {
        return planUp;
    }

    public void setPlanUp(BigDecimal planUp) {
        this.planUp = planUp;
    }

    public BigDecimal getPlanWeight() {
        return planWeight;
    }

    public void setPlanWeight(BigDecimal planWeight) {
        this.planWeight = planWeight;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public BigDecimal getBuyQty() {
        return buyQty;
    }

    public void setBuyQty(BigDecimal buyQty) {
        this.buyQty = buyQty;
    }

    public Byte getSupplyType() {
        return supplyType;
    }

    public void setSupplyType(Byte supplyType) {
        this.supplyType = supplyType;
    }

    public String getSupplyTypeName() {
        return supplyTypeName;
    }

    public void setSupplyTypeName(String supplyTypeName) {
        this.supplyTypeName = supplyTypeName == null ? null : supplyTypeName.trim();
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}
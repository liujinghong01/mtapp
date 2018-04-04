package com.mt.iv.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class Voucher {
    private Long id;

    private Integer companyId;

    private String companyName;

    private Long parentVoucherId;

    private String isFullyWithdrew;

    private String voucherNo;

    private String transDirection;

    private String transId;

    private String transType;

    private Long matId;

    private String matNo;

    private String matDesc;

    private Long matTypeId;

    private String matTypeName;

    private String matModel;

    private Integer storeHouseId;

    private Integer storeRoomId;

    private Date transDate;

    private Date chalkupDate;

    private BigDecimal voucherQty;

    private BigDecimal voucherPrice;

    private Integer voucherPieceCount;

    private BigDecimal voucherUp;

    private Long rawnatureId;

    private String rawnatureName;

    private String unit;

    private String indentNo;

    private String voucherHead;

    private Long handlerId;

    private Date handleTime;

    private BigDecimal voucherWeight;

    private Long pgId;

    private String remark;

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

    public Long getParentVoucherId() {
        return parentVoucherId;
    }

    public void setParentVoucherId(Long parentVoucherId) {
        this.parentVoucherId = parentVoucherId;
    }

    public String getIsFullyWithdrew() {
        return isFullyWithdrew;
    }

    public void setIsFullyWithdrew(String isFullyWithdrew) {
        this.isFullyWithdrew = isFullyWithdrew == null ? null : isFullyWithdrew.trim();
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo == null ? null : voucherNo.trim();
    }

    public String getTransDirection() {
        return transDirection;
    }

    public void setTransDirection(String transDirection) {
        this.transDirection = transDirection == null ? null : transDirection.trim();
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId == null ? null : transId.trim();
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
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

    public Integer getStoreHouseId() {
        return storeHouseId;
    }

    public void setStoreHouseId(Integer storeHouseId) {
        this.storeHouseId = storeHouseId;
    }

    public Integer getStoreRoomId() {
        return storeRoomId;
    }

    public void setStoreRoomId(Integer storeRoomId) {
        this.storeRoomId = storeRoomId;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public Date getChalkupDate() {
        return chalkupDate;
    }

    public void setChalkupDate(Date chalkupDate) {
        this.chalkupDate = chalkupDate;
    }

    public BigDecimal getVoucherQty() {
        return voucherQty;
    }

    public void setVoucherQty(BigDecimal voucherQty) {
        this.voucherQty = voucherQty;
    }

    public BigDecimal getVoucherPrice() {
        return voucherPrice;
    }

    public void setVoucherPrice(BigDecimal voucherPrice) {
        this.voucherPrice = voucherPrice;
    }

    public Integer getVoucherPieceCount() {
        return voucherPieceCount;
    }

    public void setVoucherPieceCount(Integer voucherPieceCount) {
        this.voucherPieceCount = voucherPieceCount;
    }

    public BigDecimal getVoucherUp() {
        return voucherUp;
    }

    public void setVoucherUp(BigDecimal voucherUp) {
        this.voucherUp = voucherUp;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getIndentNo() {
        return indentNo;
    }

    public void setIndentNo(String indentNo) {
        this.indentNo = indentNo == null ? null : indentNo.trim();
    }

    public String getVoucherHead() {
        return voucherHead;
    }

    public void setVoucherHead(String voucherHead) {
        this.voucherHead = voucherHead == null ? null : voucherHead.trim();
    }

    public Long getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(Long handlerId) {
        this.handlerId = handlerId;
    }

    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }

    public BigDecimal getVoucherWeight() {
        return voucherWeight;
    }

    public void setVoucherWeight(BigDecimal voucherWeight) {
        this.voucherWeight = voucherWeight;
    }

    public Long getPgId() {
        return pgId;
    }

    public void setPgId(Long pgId) {
        this.pgId = pgId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
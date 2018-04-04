package com.mt.iv.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class IvStoreCheckBillSub {

    private Long id;

    private Integer checkBillId;

    private Integer storeRoomId;


    private String storeRoomName;


    private Long matId;


    private String matNo;

    private String matDesc;


    private Long matTypeId;

    private String matTypeName;


    private String matModel;


    private String unit;


    private BigDecimal lastQty;


    private BigDecimal lastPrice;


    private BigDecimal occurInQty;


    private BigDecimal occurInPrice;


    private BigDecimal occurOutQty;

    private BigDecimal occurOutPrice;


    private BigDecimal occurSpoilQty;


    private BigDecimal occurSpoilPrice;


    private BigDecimal checkBillQty;


    private BigDecimal checkBillPrice;

    private BigDecimal checkStoreQty;


    private BigDecimal checkStorePrice;


    private BigDecimal checkRealQty;


    private BigDecimal checkRealPrice;


    private BigDecimal checkDifferRatio;


    private BigDecimal checkProfitLossQty;


    private BigDecimal checkProfitLossPrice;


    private String checkProfitLossReason;


    private Boolean isWithVoucher;


    private Boolean isDealedWithVoucher;


    private Boolean isManualChanged;

    private String liquidateFlag;

    private Date createdAt;


    private Date updatedAt;


    private Byte status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Integer getCheckBillId() {
        return checkBillId;
    }

    public void setCheckBillId(Integer checkBillId) {
        this.checkBillId = checkBillId;
    }


    public Integer getStoreRoomId() {
        return storeRoomId;
    }


    public void setStoreRoomId(Integer storeRoomId) {
        this.storeRoomId = storeRoomId;
    }


    public String getStoreRoomName() {
        return storeRoomName;
    }

    public void setStoreRoomName(String storeRoomName) {
        this.storeRoomName = storeRoomName == null ? null : storeRoomName.trim();
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


    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }


    public BigDecimal getLastQty() {
        return lastQty;
    }


    public void setLastQty(BigDecimal lastQty) {
        this.lastQty = lastQty;
    }


    public BigDecimal getLastPrice() {
        return lastPrice;
    }


    public void setLastPrice(BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
    }


    public BigDecimal getOccurInQty() {
        return occurInQty;
    }


    public void setOccurInQty(BigDecimal occurInQty) {
        this.occurInQty = occurInQty;
    }


    public BigDecimal getOccurInPrice() {
        return occurInPrice;
    }


    public void setOccurInPrice(BigDecimal occurInPrice) {
        this.occurInPrice = occurInPrice;
    }


    public BigDecimal getOccurOutQty() {
        return occurOutQty;
    }


    public void setOccurOutQty(BigDecimal occurOutQty) {
        this.occurOutQty = occurOutQty;
    }


    public BigDecimal getOccurOutPrice() {
        return occurOutPrice;
    }


    public void setOccurOutPrice(BigDecimal occurOutPrice) {
        this.occurOutPrice = occurOutPrice;
    }


    public BigDecimal getOccurSpoilQty() {
        return occurSpoilQty;
    }


    public void setOccurSpoilQty(BigDecimal occurSpoilQty) {
        this.occurSpoilQty = occurSpoilQty;
    }

    public BigDecimal getOccurSpoilPrice() {
        return occurSpoilPrice;
    }


    public void setOccurSpoilPrice(BigDecimal occurSpoilPrice) {
        this.occurSpoilPrice = occurSpoilPrice;
    }


    public BigDecimal getCheckBillQty() {
        return checkBillQty;
    }


    public void setCheckBillQty(BigDecimal checkBillQty) {
        this.checkBillQty = checkBillQty;
    }


    public BigDecimal getCheckBillPrice() {
        return checkBillPrice;
    }


    public void setCheckBillPrice(BigDecimal checkBillPrice) {
        this.checkBillPrice = checkBillPrice;
    }


    public BigDecimal getCheckStoreQty() {
        return checkStoreQty;
    }


    public void setCheckStoreQty(BigDecimal checkStoreQty) {
        this.checkStoreQty = checkStoreQty;
    }


    public BigDecimal getCheckStorePrice() {
        return checkStorePrice;
    }


    public void setCheckStorePrice(BigDecimal checkStorePrice) {
        this.checkStorePrice = checkStorePrice;
    }

    public BigDecimal getCheckRealQty() {
        return checkRealQty;
    }


    public void setCheckRealQty(BigDecimal checkRealQty) {
        this.checkRealQty = checkRealQty;
    }


    public BigDecimal getCheckRealPrice() {
        return checkRealPrice;
    }


    public void setCheckRealPrice(BigDecimal checkRealPrice) {
        this.checkRealPrice = checkRealPrice;
    }


    public BigDecimal getCheckDifferRatio() {
        return checkDifferRatio;
    }


    public void setCheckDifferRatio(BigDecimal checkDifferRatio) {
        this.checkDifferRatio = checkDifferRatio;
    }


    public BigDecimal getCheckProfitLossQty() {
        return checkProfitLossQty;
    }


    public void setCheckProfitLossQty(BigDecimal checkProfitLossQty) {
        this.checkProfitLossQty = checkProfitLossQty;
    }


    public BigDecimal getCheckProfitLossPrice() {
        return checkProfitLossPrice;
    }

    public void setCheckProfitLossPrice(BigDecimal checkProfitLossPrice) {
        this.checkProfitLossPrice = checkProfitLossPrice;
    }

    public String getCheckProfitLossReason() {
        return checkProfitLossReason;
    }


    public void setCheckProfitLossReason(String checkProfitLossReason) {
        this.checkProfitLossReason = checkProfitLossReason == null ? null : checkProfitLossReason.trim();
    }


    public Boolean getIsWithVoucher() {
        return isWithVoucher;
    }


    public void setIsWithVoucher(Boolean isWithVoucher) {
        this.isWithVoucher = isWithVoucher;
    }

    public Boolean getIsDealedWithVoucher() {
        return isDealedWithVoucher;
    }


    public void setIsDealedWithVoucher(Boolean isDealedWithVoucher) {
        this.isDealedWithVoucher = isDealedWithVoucher;
    }


    public Boolean getIsManualChanged() {
        return isManualChanged;
    }


    public void setIsManualChanged(Boolean isManualChanged) {
        this.isManualChanged = isManualChanged;
    }


    public String getLiquidateFlag() {
        return liquidateFlag;
    }


    public void setLiquidateFlag(String liquidateFlag) {
        this.liquidateFlag = liquidateFlag == null ? null : liquidateFlag.trim();
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
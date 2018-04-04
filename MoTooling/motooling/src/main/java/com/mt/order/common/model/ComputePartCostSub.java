package com.mt.order.common.model;

import java.math.BigDecimal;

public class ComputePartCostSub {
    private Long id;

    private Long computePartCostId;

    private String subPartName;

    private Integer rawNatureId;

    private String rawNature;

    private String stuffShape;

    private String stuffPiece;

    private String stuffLength;

    private String stuffWidth;

    private String stuffHeight;

    private String stuffDiameter;

    private String stuffDensity;

    private String stuffDensityUnit;

    private String stuffUp;

    private String stuffPrice;

    private Boolean isCalcWeight;

    private BigDecimal stuffWeight;

    private Boolean isQuench;

    private Boolean isNitrogen;

    private Boolean isNotStandardCalc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getComputePartCostId() {
        return computePartCostId;
    }

    public void setComputePartCostId(Long computePartCostId) {
        this.computePartCostId = computePartCostId;
    }

    public String getSubPartName() {
        return subPartName;
    }

    public void setSubPartName(String subPartName) {
        this.subPartName = subPartName == null ? null : subPartName.trim();
    }

    public Integer getRawNatureId() {
        return rawNatureId;
    }

    public void setRawNatureId(Integer rawNatureId) {
        this.rawNatureId = rawNatureId;
    }

    public String getRawNature() {
        return rawNature;
    }

    public void setRawNature(String rawNature) {
        this.rawNature = rawNature == null ? null : rawNature.trim();
    }

    public String getStuffShape() {
        return stuffShape;
    }

    public void setStuffShape(String stuffShape) {
        this.stuffShape = stuffShape == null ? null : stuffShape.trim();
    }

    public String getStuffPiece() {
        return stuffPiece;
    }

    public void setStuffPiece(String stuffPiece) {
        this.stuffPiece = stuffPiece == null ? null : stuffPiece.trim();
    }

    public String getStuffLength() {
        return stuffLength;
    }

    public void setStuffLength(String stuffLength) {
        this.stuffLength = stuffLength == null ? null : stuffLength.trim();
    }

    public String getStuffWidth() {
        return stuffWidth;
    }

    public void setStuffWidth(String stuffWidth) {
        this.stuffWidth = stuffWidth == null ? null : stuffWidth.trim();
    }

    public String getStuffHeight() {
        return stuffHeight;
    }

    public void setStuffHeight(String stuffHeight) {
        this.stuffHeight = stuffHeight == null ? null : stuffHeight.trim();
    }

    public String getStuffDiameter() {
        return stuffDiameter;
    }

    public void setStuffDiameter(String stuffDiameter) {
        this.stuffDiameter = stuffDiameter == null ? null : stuffDiameter.trim();
    }

    public String getStuffDensity() {
        return stuffDensity;
    }

    public void setStuffDensity(String stuffDensity) {
        this.stuffDensity = stuffDensity == null ? null : stuffDensity.trim();
    }

    public String getStuffDensityUnit() {
        return stuffDensityUnit;
    }

    public void setStuffDensityUnit(String stuffDensityUnit) {
        this.stuffDensityUnit = stuffDensityUnit == null ? null : stuffDensityUnit.trim();
    }

    public String getStuffUp() {
        return stuffUp;
    }

    public void setStuffUp(String stuffUp) {
        this.stuffUp = stuffUp == null ? null : stuffUp.trim();
    }

    public String getStuffPrice() {
        return stuffPrice;
    }

    public void setStuffPrice(String stuffPrice) {
        this.stuffPrice = stuffPrice == null ? null : stuffPrice.trim();
    }

    public Boolean getIsCalcWeight() {
        return isCalcWeight;
    }

    public void setIsCalcWeight(Boolean isCalcWeight) {
        this.isCalcWeight = isCalcWeight;
    }

    public BigDecimal getStuffWeight() {
        return stuffWeight;
    }

    public void setStuffWeight(BigDecimal stuffWeight) {
        this.stuffWeight = stuffWeight;
    }

    public Boolean getIsQuench() {
        return isQuench;
    }

    public void setIsQuench(Boolean isQuench) {
        this.isQuench = isQuench;
    }

    public Boolean getIsNitrogen() {
        return isNitrogen;
    }

    public void setIsNitrogen(Boolean isNitrogen) {
        this.isNitrogen = isNitrogen;
    }

    public Boolean getIsNotStandardCalc() {
        return isNotStandardCalc;
    }

    public void setIsNotStandardCalc(Boolean isNotStandardCalc) {
        this.isNotStandardCalc = isNotStandardCalc;
    }
}
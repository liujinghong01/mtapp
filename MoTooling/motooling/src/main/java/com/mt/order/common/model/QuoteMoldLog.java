package com.mt.order.common.model;

import java.math.BigDecimal;

public class QuoteMoldLog {
    private Long id;

    private Integer version;

    private Long quoteId;

    private Long computeId;

    private String computeNo;

    private String quoteNo;

    private String cstMoldNo;

    private String material;

    private Byte type;

    private String typeName;

    private String typeSub;

    private String waterType;

    private String runner;

    private String outWay;

    private String surfaceReq;

    private BigDecimal taskTime;

    private BigDecimal length;

    private BigDecimal width;

    private BigDecimal height;

    private String sizeUnit;

    private String moldMat;

    private Integer count;

    private BigDecimal weight;

    private BigDecimal upLocal;

    private BigDecimal up;

    private BigDecimal priceLocal;

    private BigDecimal price;

    private BigDecimal rebate;

    private BigDecimal rebatePrice;

    private BigDecimal upTax;

    private BigDecimal upLocalTax;

    private BigDecimal priceTax;

    private BigDecimal priceLocalTax;

    private BigDecimal rebatePriceTax;

    private Byte intMode;

    private String priceTaxInt;

    private String priceLocalTaxInt;

    private String rebatePriceTaxInt;

    private String isApproved;

    private Byte status;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(Long quoteId) {
        this.quoteId = quoteId;
    }

    public Long getComputeId() {
        return computeId;
    }

    public void setComputeId(Long computeId) {
        this.computeId = computeId;
    }

    public String getComputeNo() {
        return computeNo;
    }

    public void setComputeNo(String computeNo) {
        this.computeNo = computeNo == null ? null : computeNo.trim();
    }

    public String getQuoteNo() {
        return quoteNo;
    }

    public void setQuoteNo(String quoteNo) {
        this.quoteNo = quoteNo == null ? null : quoteNo.trim();
    }

    public String getCstMoldNo() {
        return cstMoldNo;
    }

    public void setCstMoldNo(String cstMoldNo) {
        this.cstMoldNo = cstMoldNo == null ? null : cstMoldNo.trim();
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material == null ? null : material.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public String getTypeSub() {
        return typeSub;
    }

    public void setTypeSub(String typeSub) {
        this.typeSub = typeSub == null ? null : typeSub.trim();
    }

    public String getWaterType() {
        return waterType;
    }

    public void setWaterType(String waterType) {
        this.waterType = waterType == null ? null : waterType.trim();
    }

    public String getRunner() {
        return runner;
    }

    public void setRunner(String runner) {
        this.runner = runner == null ? null : runner.trim();
    }

    public String getOutWay() {
        return outWay;
    }

    public void setOutWay(String outWay) {
        this.outWay = outWay == null ? null : outWay.trim();
    }

    public String getSurfaceReq() {
        return surfaceReq;
    }

    public void setSurfaceReq(String surfaceReq) {
        this.surfaceReq = surfaceReq == null ? null : surfaceReq.trim();
    }

    public BigDecimal getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(BigDecimal taskTime) {
        this.taskTime = taskTime;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public String getSizeUnit() {
        return sizeUnit;
    }

    public void setSizeUnit(String sizeUnit) {
        this.sizeUnit = sizeUnit == null ? null : sizeUnit.trim();
    }

    public String getMoldMat() {
        return moldMat;
    }

    public void setMoldMat(String moldMat) {
        this.moldMat = moldMat == null ? null : moldMat.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getUpLocal() {
        return upLocal;
    }

    public void setUpLocal(BigDecimal upLocal) {
        this.upLocal = upLocal;
    }

    public BigDecimal getUp() {
        return up;
    }

    public void setUp(BigDecimal up) {
        this.up = up;
    }

    public BigDecimal getPriceLocal() {
        return priceLocal;
    }

    public void setPriceLocal(BigDecimal priceLocal) {
        this.priceLocal = priceLocal;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getRebate() {
        return rebate;
    }

    public void setRebate(BigDecimal rebate) {
        this.rebate = rebate;
    }

    public BigDecimal getRebatePrice() {
        return rebatePrice;
    }

    public void setRebatePrice(BigDecimal rebatePrice) {
        this.rebatePrice = rebatePrice;
    }

    public BigDecimal getUpTax() {
        return upTax;
    }

    public void setUpTax(BigDecimal upTax) {
        this.upTax = upTax;
    }

    public BigDecimal getUpLocalTax() {
        return upLocalTax;
    }

    public void setUpLocalTax(BigDecimal upLocalTax) {
        this.upLocalTax = upLocalTax;
    }

    public BigDecimal getPriceTax() {
        return priceTax;
    }

    public void setPriceTax(BigDecimal priceTax) {
        this.priceTax = priceTax;
    }

    public BigDecimal getPriceLocalTax() {
        return priceLocalTax;
    }

    public void setPriceLocalTax(BigDecimal priceLocalTax) {
        this.priceLocalTax = priceLocalTax;
    }

    public BigDecimal getRebatePriceTax() {
        return rebatePriceTax;
    }

    public void setRebatePriceTax(BigDecimal rebatePriceTax) {
        this.rebatePriceTax = rebatePriceTax;
    }

    public Byte getIntMode() {
        return intMode;
    }

    public void setIntMode(Byte intMode) {
        this.intMode = intMode;
    }

    public String getPriceTaxInt() {
        return priceTaxInt;
    }

    public void setPriceTaxInt(String priceTaxInt) {
        this.priceTaxInt = priceTaxInt == null ? null : priceTaxInt.trim();
    }

    public String getPriceLocalTaxInt() {
        return priceLocalTaxInt;
    }

    public void setPriceLocalTaxInt(String priceLocalTaxInt) {
        this.priceLocalTaxInt = priceLocalTaxInt == null ? null : priceLocalTaxInt.trim();
    }

    public String getRebatePriceTaxInt() {
        return rebatePriceTaxInt;
    }

    public void setRebatePriceTaxInt(String rebatePriceTaxInt) {
        this.rebatePriceTaxInt = rebatePriceTaxInt == null ? null : rebatePriceTaxInt.trim();
    }

    public String getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(String isApproved) {
        this.isApproved = isApproved == null ? null : isApproved.trim();
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
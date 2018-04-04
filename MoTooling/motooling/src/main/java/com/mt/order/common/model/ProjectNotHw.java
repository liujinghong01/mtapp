package com.mt.order.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class ProjectNotHw {
    private Long id;

    private BigDecimal up;

    private Long projectId;

    private String projectNo;

    private String cstMoldNo;

    private String material;

    private String cstProdName;

    private Byte type;

    private String typeName;

    private String cavityMaterial;

    private Date startDate;

    private Date firstTryDate;

    private Date deliveryDate;

    private String unit;

    private Integer count;

    private BigDecimal upNotTax;

    private BigDecimal priceNotTax;

    private BigDecimal price;

    private String remark;

    private String quoteNo;

    private Long quoteId;

    private Byte status;

    private String startupStep;

    private String reason;

    private Byte isBatchProcess;

    private String supType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getUp() {
        return up;
    }

    public void setUp(BigDecimal up) {
        this.up = up;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo == null ? null : projectNo.trim();
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

    public String getCstProdName() {
        return cstProdName;
    }

    public void setCstProdName(String cstProdName) {
        this.cstProdName = cstProdName == null ? null : cstProdName.trim();
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

    public String getCavityMaterial() {
        return cavityMaterial;
    }

    public void setCavityMaterial(String cavityMaterial) {
        this.cavityMaterial = cavityMaterial == null ? null : cavityMaterial.trim();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFirstTryDate() {
        return firstTryDate;
    }

    public void setFirstTryDate(Date firstTryDate) {
        this.firstTryDate = firstTryDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getUpNotTax() {
        return upNotTax;
    }

    public void setUpNotTax(BigDecimal upNotTax) {
        this.upNotTax = upNotTax;
    }

    public BigDecimal getPriceNotTax() {
        return priceNotTax;
    }

    public void setPriceNotTax(BigDecimal priceNotTax) {
        this.priceNotTax = priceNotTax;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getQuoteNo() {
        return quoteNo;
    }

    public void setQuoteNo(String quoteNo) {
        this.quoteNo = quoteNo == null ? null : quoteNo.trim();
    }

    public Long getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(Long quoteId) {
        this.quoteId = quoteId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getStartupStep() {
        return startupStep;
    }

    public void setStartupStep(String startupStep) {
        this.startupStep = startupStep == null ? null : startupStep.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public Byte getIsBatchProcess() {
        return isBatchProcess;
    }

    public void setIsBatchProcess(Byte isBatchProcess) {
        this.isBatchProcess = isBatchProcess;
    }

    public String getSupType() {
        return supType;
    }

    public void setSupType(String supType) {
        this.supType = supType == null ? null : supType.trim();
    }
}
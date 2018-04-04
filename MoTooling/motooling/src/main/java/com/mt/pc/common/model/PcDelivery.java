package com.mt.pc.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class PcDelivery {
    private Long id;

    private String deliveryNo;

    private Long purchId;

    private String purchNo;

    private Integer companyId;

    private String companyName;

    private Integer cstCompanyId;

    private String cstName;

    private Long deliveryMan;

    private String deliveryManName;

    private String deliveryManPhone;

    private String deliveryManEmail;

    private BigDecimal totalQty;

    private BigDecimal totalPrice;

    private Long creatorId;

    private Long handlerId;

    private Date createdAt;

    private Date updatedAt;

    private Byte status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo == null ? null : deliveryNo.trim();
    }

    public Long getPurchId() {
        return purchId;
    }

    public void setPurchId(Long purchId) {
        this.purchId = purchId;
    }

    public String getPurchNo() {
        return purchNo;
    }

    public void setPurchNo(String purchNo) {
        this.purchNo = purchNo == null ? null : purchNo.trim();
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

    public Integer getCstCompanyId() {
        return cstCompanyId;
    }

    public void setCstCompanyId(Integer cstCompanyId) {
        this.cstCompanyId = cstCompanyId;
    }

    public String getCstName() {
        return cstName;
    }

    public void setCstName(String cstName) {
        this.cstName = cstName == null ? null : cstName.trim();
    }

    public Long getDeliveryMan() {
        return deliveryMan;
    }

    public void setDeliveryMan(Long deliveryMan) {
        this.deliveryMan = deliveryMan;
    }

    public String getDeliveryManName() {
        return deliveryManName;
    }

    public void setDeliveryManName(String deliveryManName) {
        this.deliveryManName = deliveryManName == null ? null : deliveryManName.trim();
    }

    public String getDeliveryManPhone() {
        return deliveryManPhone;
    }

    public void setDeliveryManPhone(String deliveryManPhone) {
        this.deliveryManPhone = deliveryManPhone == null ? null : deliveryManPhone.trim();
    }

    public String getDeliveryManEmail() {
        return deliveryManEmail;
    }

    public void setDeliveryManEmail(String deliveryManEmail) {
        this.deliveryManEmail = deliveryManEmail == null ? null : deliveryManEmail.trim();
    }

    public BigDecimal getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(BigDecimal totalQty) {
        this.totalQty = totalQty;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
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
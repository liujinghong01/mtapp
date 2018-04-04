package com.mt.qm.common.model;

import java.util.Date;

public class QmProcQcSub {
    private Long id;

    private Integer popQcId;

    private Long poId;

    private String poNo;

    private Long popId;

    private String confirmNo;

    private Integer confirmSeq;

    private String qualityLoss;

    private Integer qcQty;

    private Integer qualifiedQty;

    private Integer noQualifiedQty;

    private Integer specialQty;

    private Integer scrapQty;

    private String isSubmitFault;

    private String unusualReason;

    private String unusualPic;

    private Integer step;

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

    public Integer getPopQcId() {
        return popQcId;
    }

    public void setPopQcId(Integer popQcId) {
        this.popQcId = popQcId;
    }

    public Long getPoId() {
        return poId;
    }

    public void setPoId(Long poId) {
        this.poId = poId;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo == null ? null : poNo.trim();
    }

    public Long getPopId() {
        return popId;
    }

    public void setPopId(Long popId) {
        this.popId = popId;
    }

    public String getConfirmNo() {
        return confirmNo;
    }

    public void setConfirmNo(String confirmNo) {
        this.confirmNo = confirmNo == null ? null : confirmNo.trim();
    }

    public Integer getConfirmSeq() {
        return confirmSeq;
    }

    public void setConfirmSeq(Integer confirmSeq) {
        this.confirmSeq = confirmSeq;
    }

    public String getQualityLoss() {
        return qualityLoss;
    }

    public void setQualityLoss(String qualityLoss) {
        this.qualityLoss = qualityLoss == null ? null : qualityLoss.trim();
    }

    public Integer getQcQty() {
        return qcQty;
    }

    public void setQcQty(Integer qcQty) {
        this.qcQty = qcQty;
    }

    public Integer getQualifiedQty() {
        return qualifiedQty;
    }

    public void setQualifiedQty(Integer qualifiedQty) {
        this.qualifiedQty = qualifiedQty;
    }

    public Integer getNoQualifiedQty() {
        return noQualifiedQty;
    }

    public void setNoQualifiedQty(Integer noQualifiedQty) {
        this.noQualifiedQty = noQualifiedQty;
    }

    public Integer getSpecialQty() {
        return specialQty;
    }

    public void setSpecialQty(Integer specialQty) {
        this.specialQty = specialQty;
    }

    public Integer getScrapQty() {
        return scrapQty;
    }

    public void setScrapQty(Integer scrapQty) {
        this.scrapQty = scrapQty;
    }

    public String getIsSubmitFault() {
        return isSubmitFault;
    }

    public void setIsSubmitFault(String isSubmitFault) {
        this.isSubmitFault = isSubmitFault == null ? null : isSubmitFault.trim();
    }

    public String getUnusualReason() {
        return unusualReason;
    }

    public void setUnusualReason(String unusualReason) {
        this.unusualReason = unusualReason == null ? null : unusualReason.trim();
    }

    public String getUnusualPic() {
        return unusualPic;
    }

    public void setUnusualPic(String unusualPic) {
        this.unusualPic = unusualPic == null ? null : unusualPic.trim();
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
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
}
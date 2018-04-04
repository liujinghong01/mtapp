package com.mt.bm.common.model;

import java.util.Date;

public class MtMatType {
    private Long id;

    private String matType;

    private String typeName;

    private String typeDesc;

    private String matNoPrefix;

    private Byte procureStyle;

    private String unit;

    private String binNo;

    private String handlerId;

    private Date handledTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatType() {
        return matType;
    }

    public void setMatType(String matType) {
        this.matType = matType == null ? null : matType.trim();
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc == null ? null : typeDesc.trim();
    }

    public String getMatNoPrefix() {
        return matNoPrefix;
    }

    public void setMatNoPrefix(String matNoPrefix) {
        this.matNoPrefix = matNoPrefix == null ? null : matNoPrefix.trim();
    }

    public Byte getProcureStyle() {
        return procureStyle;
    }

    public void setProcureStyle(Byte procureStyle) {
        this.procureStyle = procureStyle;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getBinNo() {
        return binNo;
    }

    public void setBinNo(String binNo) {
        this.binNo = binNo == null ? null : binNo.trim();
    }

    public String getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(String handlerId) {
        this.handlerId = handlerId == null ? null : handlerId.trim();
    }

    public Date getHandledTime() {
        return handledTime;
    }

    public void setHandledTime(Date handledTime) {
        this.handledTime = handledTime;
    }
}
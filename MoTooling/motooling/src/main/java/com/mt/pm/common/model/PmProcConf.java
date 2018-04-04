package com.mt.pm.common.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class PmProcConf {
    private Long id;

    private String procNo;

    private String procName;

    private String procDesc;

    private String assUnit;

    private BigDecimal assCalcQty;

    private BigDecimal assTime;

    private BigDecimal assUp;

    private Byte procType;

    private String isCanAss;

    private BigDecimal otherTime;

    private Integer assSupId;

    private BigDecimal sort;

    private Date createdAt;

    private Date updatedAt;

    private String status;

    private List<PmProcPg> pgList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcNo() {
        return procNo;
    }

    public void setProcNo(String procNo) {
        this.procNo = procNo == null ? null : procNo.trim();
    }

    public String getProcName() {
        return procName;
    }

    public void setProcName(String procName) {
        this.procName = procName == null ? null : procName.trim();
    }

    public String getProcDesc() {
        return procDesc;
    }

    public void setProcDesc(String procDesc) {
        this.procDesc = procDesc == null ? null : procDesc.trim();
    }

    public String getAssUnit() {
        return assUnit;
    }

    public void setAssUnit(String assUnit) {
        this.assUnit = assUnit == null ? null : assUnit.trim();
    }

    public BigDecimal getAssCalcQty() {
        return assCalcQty;
    }

    public void setAssCalcQty(BigDecimal assCalcQty) {
        this.assCalcQty = assCalcQty;
    }

    public BigDecimal getAssTime() {
        return assTime;
    }

    public void setAssTime(BigDecimal assTime) {
        this.assTime = assTime;
    }

    public BigDecimal getAssUp() {
        return assUp;
    }

    public void setAssUp(BigDecimal assUp) {
        this.assUp = assUp;
    }

    public Byte getProcType() {
        return procType;
    }

    public void setProcType(Byte procType) {
        this.procType = procType;
    }

    public String getIsCanAss() {
        return isCanAss;
    }

    public void setIsCanAss(String isCanAss) {
        this.isCanAss = isCanAss == null ? null : isCanAss.trim();
    }

    public BigDecimal getOtherTime() {
        return otherTime;
    }

    public void setOtherTime(BigDecimal otherTime) {
        this.otherTime = otherTime;
    }

    public Integer getAssSupId() {
        return assSupId;
    }

    public void setAssSupId(Integer assSupId) {
        this.assSupId = assSupId;
    }

    public BigDecimal getSort() {
        return sort;
    }

    public void setSort(BigDecimal sort) {
        this.sort = sort;
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

    public  List<PmProcPg> getPgList(){ return pgList; }

    public  void  setPgList(List<PmProcPg> pgList){ this.pgList =pgList == null ? null : pgList; }


    private String companyId;

    private String companyName;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }
}
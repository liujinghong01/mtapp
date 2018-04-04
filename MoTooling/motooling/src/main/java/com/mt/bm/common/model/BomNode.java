package com.mt.bm.common.model;

import java.math.BigDecimal;

public class BomNode {
    private Long nodeId;

    private Long bomId;

    private String isUnused;

    private String path;

    private String parentPath;

    private Integer nodePos;

    private Integer depth;

    private Long matId;

    private String matNo;

    private String unitNo;

    private BigDecimal totalQty;

    private String isPurchase;

    private String remark;

    private Integer sortOrder;


    private Integer processesRCount;

    private Integer processesZCount;

    private Integer processesFCount;

    private String isPatentBuild;

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public Long getBomId() {
        return bomId;
    }

    public void setBomId(Long bomId) {
        this.bomId = bomId;
    }

    public String getIsUnused() {
        return isUnused;
    }

    public void setIsUnused(String isUnused) {
        this.isUnused = isUnused == null ? null : isUnused.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath == null ? null : parentPath.trim();
    }

    public Integer getNodePos() {
        return nodePos;
    }

    public void setNodePos(Integer nodePos) {
        this.nodePos = nodePos;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Long getMatId() {
        return matId;
    }

    public void setMatId(Long matId) {
        this.matId = matId == null ? null : matId;
    }

    public String getMatNo() {
        return matNo;
    }

    public void setMatNo(String matNo) {
        this.matNo = matNo == null ? null : matNo.trim();
    }

    public String getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo == null ? null : unitNo.trim();
    }

    public BigDecimal getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(BigDecimal totalQty) {
        this.totalQty = totalQty;
    }

    public String getIsPurchase() {
        return isPurchase;
    }

    public void setIsPurchase(String isPurchase) {
        this.isPurchase = isPurchase == null ? null : isPurchase.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    private Mat mat;
    public void setMat(Mat mat) {
        this.mat = mat;
    }

    public Mat getMat() {
        return mat;
    }

    private String matType;
    public String getMatType() {
        return matType;
    }

    public void setMatType(String matType) {
        this.matType = matType == null ? null : matType.trim();
    }


    public Integer getProcessesRCount() {
        return processesRCount;
    }

    public void setProcessesRCount(Integer processesRCount) {
        this.processesRCount = processesRCount;
    }


    public Integer getProcessesZCount() {
        return processesZCount;
    }

    public void setProcessesZCount(Integer processesZCount) {
        this.processesZCount = processesZCount;
    }


    public Integer getProcessesFCount() {
        return processesFCount;
    }

    public void setProcessesFCount(Integer processesFCount) {
        this.processesFCount = processesFCount;
    }


    public String getIsPatentBuild() {
        return isPatentBuild;
    }

    public void setIsPatentBuild(String isPatentBuild) {
        this.isPatentBuild = isPatentBuild == null ? null : isPatentBuild.trim();
    }



}
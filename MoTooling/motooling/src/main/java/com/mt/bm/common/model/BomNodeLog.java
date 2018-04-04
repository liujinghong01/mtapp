package com.mt.bm.common.model;

import java.math.BigDecimal;

public class BomNodeLog {
    private Long bomLogId;

    private Long bomId;

    private Long nodeId;

    private String isUnused;

    private String path;

    private String parentPath;

    private Long nodePos;

    private Integer depth;

    private String matId;

    private String matNo;

    private String unitNo;

    private BigDecimal totalQty;

    private String ispurchase;

    private String remark;

    private Integer sortOrder;

    public Long getBomLogId() {
        return bomLogId;
    }

    public void setBomLogId(Long bomLogId) {
        this.bomLogId = bomLogId;
    }

    public Long getBomId() {
        return bomId;
    }

    public void setBomId(Long bomId) {
        this.bomId = bomId;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
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

    public Long getNodePos() {
        return nodePos;
    }

    public void setNodePos(Long nodePos) {
        this.nodePos = nodePos;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public String getMatId() {
        return matId;
    }

    public void setMatId(String matId) {
        this.matId = matId == null ? null : matId.trim();
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

    public String getIspurchase() {
        return ispurchase;
    }

    public void setIspurchase(String ispurchase) {
        this.ispurchase = ispurchase == null ? null : ispurchase.trim();
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
}
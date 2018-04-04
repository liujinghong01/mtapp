package com.mt.upms.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Permission implements Serializable{
    private static final long serialVersionUID = 1L;

    private Long id;

    private Integer permId;

    private String permName;

    private String permDescription;

    private BigDecimal sortValue;

    private Boolean isActive;

    private Date createdAt;

    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPermId() {
        return permId;
    }

    public void setPermId(Integer permId) {
        this.permId = permId;
    }

    public String getPermName() {
        return permName;
    }

    public void setPermName(String permName) {
        this.permName = permName == null ? null : permName.trim();
    }

    public String getPermDescription() {
        return permDescription;
    }

    public void setPermDescription(String permDescription) {
        this.permDescription = permDescription == null ? null : permDescription.trim();
    }

    public BigDecimal getSortValue() {
        return sortValue;
    }

    public void setSortValue(BigDecimal sortValue) {
        this.sortValue = sortValue;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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
}
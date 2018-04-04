package com.mt.upms.common.model;

import java.io.Serializable;
import java.util.Date;

public class RolePermission implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;

    private Integer roleId;

    private Integer permId;

    private Boolean isActive;

    private Date createdAt;

    private Date updatedAt;

    public RolePermission(){}

    public RolePermission(Integer roleId, Integer permId) {
        this.roleId = roleId;
        this.permId = permId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPermId() {
        return permId;
    }

    public void setPermId(Integer permId) {
        this.permId = permId;
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
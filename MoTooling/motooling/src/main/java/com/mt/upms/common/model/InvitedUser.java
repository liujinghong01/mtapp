package com.mt.upms.common.model;

import java.util.Date;

public class InvitedUser {
    private Integer id;

    private Long hostUid;

    private Integer hostCompany;

    private Long guestUid;

    private Integer guestCompany;

    private String typeCode;

    private Date createdAt;

    private Date updatedAt;

    private Byte inviteStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getHostUid() {
        return hostUid;
    }

    public void setHostUid(Long hostUid) {
        this.hostUid = hostUid;
    }

    public Integer getHostCompany() {
        return hostCompany;
    }

    public void setHostCompany(Integer hostCompany) {
        this.hostCompany = hostCompany;
    }

    public Long getGuestUid() {
        return guestUid;
    }

    public void setGuestUid(Long guestUid) {
        this.guestUid = guestUid;
    }

    public Integer getGuestCompany() {
        return guestCompany;
    }

    public void setGuestCompany(Integer guestCompany) {
        this.guestCompany = guestCompany;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode == null ? null : typeCode.trim();
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

    public Byte getInviteStatus() {
        return inviteStatus;
    }

    public void setInviteStatus(Byte inviteStatus) {
        this.inviteStatus = inviteStatus;
    }

}
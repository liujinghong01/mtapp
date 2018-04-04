package com.mt.upms.common.model;

import java.util.Date;

public class MyFriend {
    private Long id;

    private Long myUid;

    private String myName;

    private Long friUid;

    private String friName;

    private Boolean isActive;

    private Date createdAt;

    private Date updatedAt;

    private String friAvatar;

    private String friPhone;

    private String friComName;

    private String friEmail;

    private String relation;

    private Integer friComId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMyUid() {
        return myUid;
    }

    public void setMyUid(Long myUid) {
        this.myUid = myUid;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName == null ? null : myName.trim();
    }

    public Long getFriUid() {
        return friUid;
    }

    public void setFriUid(Long friUid) {
        this.friUid = friUid;
    }

    public String getFriName() {
        return friName;
    }

    public void setFriName(String friName) {
        this.friName = friName == null ? null : friName.trim();
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

    public String getFriAvatar() {
        return friAvatar;
    }

    public void setFriAvatar(String friAvatar) {
        this.friAvatar = friAvatar == null ? null : friAvatar.trim();
    }

    public String getFriPhone() {
        return friPhone;
    }

    public void setFriPhone(String friPhone) {
        this.friPhone = friPhone == null ? null : friPhone.trim();
    }

    public String getFriComName() {
        return friComName;
    }

    public void setFriComName(String friComName) {
        this.friComName = friComName == null ? null : friComName.trim();
    }

    public String getFriEmail() {
        return friEmail;
    }

    public void setFriEmail(String friEmail) {
        this.friEmail = friEmail == null ? null : friEmail.trim();
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation == null ? null : relation.trim();
    }

    public Integer getFriComId() {
        return friComId;
    }

    public void setFriComId(Integer friComId) {
        this.friComId = friComId;
    }
}
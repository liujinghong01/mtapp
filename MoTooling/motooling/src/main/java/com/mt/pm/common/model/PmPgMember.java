package com.mt.pm.common.model;

import java.util.Date;

public class PmPgMember {
    private Long id;

    private Long pgId;

    private Long memberId;

    private String memberName;

    private String isLeader;

    private Integer pri;

    private String isSchedule;

    private Integer skillLevelId;

    private String skillLevelName;

    private Date furloughStartDate;

    private Date furloughEndDate;

    private String isShowEffic;

    private String memberStatus;

    private Long lastPopId;

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

    public Long getPgId() {
        return pgId;
    }

    public void setPgId(Long pgId) {
        this.pgId = pgId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName == null ? null : memberName.trim();
    }

    public String getIsLeader() {
        return isLeader;
    }

    public void setIsLeader(String isLeader) {
        this.isLeader = isLeader == null ? null : isLeader.trim();
    }

    public Integer getPri() {
        return pri;
    }

    public void setPri(Integer pri) {
        this.pri = pri;
    }

    public String getIsSchedule() {
        return isSchedule;
    }

    public void setIsSchedule(String isSchedule) {
        this.isSchedule = isSchedule == null ? null : isSchedule.trim();
    }

    public Integer getSkillLevelId() {
        return skillLevelId;
    }

    public void setSkillLevelId(Integer skillLevelId) {
        this.skillLevelId = skillLevelId;
    }

    public String getSkillLevelName() {
        return skillLevelName;
    }

    public void setSkillLevelName(String skillLevelName) {
        this.skillLevelName = skillLevelName == null ? null : skillLevelName.trim();
    }

    public Date getFurloughStartDate() {
        return furloughStartDate;
    }

    public void setFurloughStartDate(Date furloughStartDate) {
        this.furloughStartDate = furloughStartDate;
    }

    public Date getFurloughEndDate() {
        return furloughEndDate;
    }

    public void setFurloughEndDate(Date furloughEndDate) {
        this.furloughEndDate = furloughEndDate;
    }

    public String getIsShowEffic() {
        return isShowEffic;
    }

    public void setIsShowEffic(String isShowEffic) {
        this.isShowEffic = isShowEffic == null ? null : isShowEffic.trim();
    }

    public String getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(String memberStatus) {
        this.memberStatus = memberStatus == null ? null : memberStatus.trim();
    }

    public Long getLastPopId() {
        return lastPopId;
    }

    public void setLastPopId(Long lastPopId) {
        this.lastPopId = lastPopId;
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
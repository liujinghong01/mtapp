package com.mt.upms.common.model;

public class SysUseMessage {
    private Integer id;

    private Integer sendUid;

    private Integer receverUid;

    private Integer messageId;

    private String removeUid;

    private Integer companyId;

    private Integer deparmentId;

    private Byte sendStatus;

    private Byte receveStatus;

    private Byte readStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSendUid() {
        return sendUid;
    }

    public void setSendUid(Integer sendUid) {
        this.sendUid = sendUid;
    }

    public Integer getReceverUid() {
        return receverUid;
    }

    public void setReceverUid(Integer receverUid) {
        this.receverUid = receverUid;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getRemoveUid() {
        return removeUid;
    }

    public void setRemoveUid(String removeUid) {
        this.removeUid = removeUid == null ? null : removeUid.trim();
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getDeparmentId() {
        return deparmentId;
    }

    public void setDeparmentId(Integer deparmentId) {
        this.deparmentId = deparmentId;
    }

    public Byte getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Byte sendStatus) {
        this.sendStatus = sendStatus;
    }

    public Byte getReceveStatus() {
        return receveStatus;
    }

    public void setReceveStatus(Byte receveStatus) {
        this.receveStatus = receveStatus;
    }

    public Byte getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Byte readStatus) {
        this.readStatus = readStatus;
    }
}
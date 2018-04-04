package com.mt.order.common.model;

public class Dictionary {
    private Long id;

    private String dictType;

    private String dictTypeSub;

    private String dictCode;

    private String dictName;

    private String dictNameEn;

    private String description;

    private Float sort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType == null ? null : dictType.trim();
    }

    public String getDictTypeSub() {
        return dictTypeSub;
    }

    public void setDictTypeSub(String dictTypeSub) {
        this.dictTypeSub = dictTypeSub == null ? null : dictTypeSub.trim();
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode == null ? null : dictCode.trim();
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName == null ? null : dictName.trim();
    }

    public String getDictNameEn() {
        return dictNameEn;
    }

    public void setDictNameEn(String dictNameEn) {
        this.dictNameEn = dictNameEn == null ? null : dictNameEn.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Float getSort() {
        return sort;
    }

    public void setSort(Float sort) {
        this.sort = sort;
    }
}
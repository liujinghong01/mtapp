package com.mt.order.common.model;

public class DictProcessItem {
    private Long id;

    private String itemCode;

    private String itemName;

    private String itemNameEn;

    private String defaultUnit;

    private String defaultRatio;

    private Float sort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode == null ? null : itemCode.trim();
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    public String getItemNameEn() {
        return itemNameEn;
    }

    public void setItemNameEn(String itemNameEn) {
        this.itemNameEn = itemNameEn == null ? null : itemNameEn.trim();
    }

    public String getDefaultUnit() {
        return defaultUnit;
    }

    public void setDefaultUnit(String defaultUnit) {
        this.defaultUnit = defaultUnit == null ? null : defaultUnit.trim();
    }

    public String getDefaultRatio() {
        return defaultRatio;
    }

    public void setDefaultRatio(String defaultRatio) {
        this.defaultRatio = defaultRatio == null ? null : defaultRatio.trim();
    }

    public Float getSort() {
        return sort;
    }

    public void setSort(Float sort) {
        this.sort = sort;
    }
}
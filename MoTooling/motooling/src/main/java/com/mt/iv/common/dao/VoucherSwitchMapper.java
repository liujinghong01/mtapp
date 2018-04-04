package com.mt.iv.common.dao;

import com.mt.iv.common.model.VoucherSwitch;

import java.util.Map;

public interface VoucherSwitchMapper {
    int deleteByPrimaryKey(Long id);

    int insert(VoucherSwitch record);

    int insertSelective(VoucherSwitch record);

    VoucherSwitch selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VoucherSwitch record);

    int updateByPrimaryKey(VoucherSwitch record);

    // 新增分类凭证表（物料转仓）
    Integer  addVoucherSwitch(Map<String,Object>  map);
}
package com.mt.iv.common.dao;

import com.mt.iv.common.model.VoucherDirect;

import java.util.Map;

public interface VoucherDirectMapper {
    int deleteByPrimaryKey(Long id);

    int insert(VoucherDirect record);

    int insertSelective(VoucherDirect record);

    VoucherDirect selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VoucherDirect record);

    int updateByPrimaryKey(VoucherDirect record);

    // 直接出入库记录仓库的变动
    Integer   addVoucherDirect(Map<String, Object> map);
}
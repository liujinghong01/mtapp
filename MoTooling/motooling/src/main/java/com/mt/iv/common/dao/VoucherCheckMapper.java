package com.mt.iv.common.dao;

import com.mt.iv.common.model.VoucherCheck;

import java.util.List;
import java.util.Map;

public interface VoucherCheckMapper {
    int deleteByPrimaryKey(Long id);

    int insert(VoucherCheck record);

    int insertSelective(VoucherCheck record);

    VoucherCheck selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VoucherCheck record);

    int updateByPrimaryKey(VoucherCheck record);

    // 新增分类凭证表（盘点出入库）
    Integer   addVoucherCheck(Map<String,Object> map);

    List<Map<String,Object>> selectVoucherCheckList(Map<String,Object> map);

    Integer totalCount(Map<String,Object> map);
}
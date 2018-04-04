package com.mt.iv.common.dao;

import com.mt.iv.common.model.VoucherScrap;

import java.util.Map;
/**
* @Author: Wendy
* @Description:
* @create    2018/1/23 15:30
* @Modified By:
*/    
public interface VoucherScrapMapper {

    int deleteByPrimaryKey(Long id);

    int insert(VoucherScrap record);

    int insertSelective(VoucherScrap record);

    VoucherScrap selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VoucherScrap record);

    int updateByPrimaryKey(VoucherScrap record);

    // 新增分类凭证表
    Integer  addClassifyVoucher(Map<String,Object> map);
}
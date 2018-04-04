package com.mt.cms.common.dao;

import com.mt.cms.common.model.SupplierWare;

import java.util.List;
import java.util.Map;

public interface SupplierWareMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierWare record);

    int insertSelective(SupplierWare record);

    SupplierWare selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierWare record);

    int updateByPrimaryKey(SupplierWare record);

    List<SupplierWare> selectBySupCompanyId(Map map);
}
package com.mt.cms.common.dao;

import com.mt.cms.common.model.CompanyType;

public interface CompanyTypeMapper {

    int deleteByPrimaryKey(Short typeId);

    int insert(CompanyType record);

    int insertSelective(CompanyType record);

    CompanyType selectByPrimaryKey(Short typeId);

    int updateByPrimaryKeySelective(CompanyType record);

    int updateByPrimaryKey(CompanyType record);
}
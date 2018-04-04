package com.mt.cms.common.dao;

import com.mt.cms.common.model.CompanyIndustry;

public interface CompanyIndustryMapper {

    int deleteByPrimaryKey(Integer industryId);

    int insert(CompanyIndustry record);

    int insertSelective(CompanyIndustry record);

    CompanyIndustry selectByPrimaryKey(Integer industryId);

    int updateByPrimaryKeySelective(CompanyIndustry record);

    int updateByPrimaryKey(CompanyIndustry record);
}
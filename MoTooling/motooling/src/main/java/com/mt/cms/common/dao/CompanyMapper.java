package com.mt.cms.common.dao;

import com.mt.cms.common.model.Company;
import com.mt.upms.common.model.UserCompany;

import java.util.List;
import java.util.Map;

public interface CompanyMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Company record);

    int insertSelective(Company record);

    Company selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Company record);

    int updateByPrimaryKey(Company record);

    //-------added by fhk-----------
    List<UserCompany> selectByUserId(Long uid);

    Company selectByCompanyId(Integer companyId);

    Integer getMaxCompanyId();

    List<Map<String,Object>> searchCompany(String companyName);

    List<Map<String, Object>> vagueSelectCompany(String companyName);

    List<Map<String, Object>> createDefaultDepartment(Integer companyId);

    String createCompanyInitData(Map map);

    String selectCompanyNme(Integer companyId);

    //查询某部门包括其所有子部门的全部在职成员
    List<Map<String, Object>>  selectAllAndSubUserCompanyByDepId(Map map);
}
package com.mt.cms.company.service;

import com.mt.cms.common.model.*;
import com.mt.common.bo.IdType;
import com.mt.common.customenum.RoleEnum;
import com.mt.pm.common.model.PmPgDevice;
import com.mt.pm.common.model.PmPgMember;
import com.mt.upms.common.model.UserRole;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Added by fhk on 2017.9.14
 */
public interface CompanyService {
    PmPgMember selectByPgIdAndMemberId(Long pgId, Long memberId);

    int insertUserCompany(OrigUserCompany origUserCompany, StringBuilder err, boolean isPgCall);

    //部门与生产小组信息及其人员信息要进行同步更新
    int updateUserCompany(OrigUserCompany origUserCompany, StringBuilder err, boolean isPgCall);

    //部门与生产小组信息及其人员信息要进行同步更新
    int deleteUserCompany( Integer companyId, Integer depId, Long memberId, Long handlerId, StringBuilder err, boolean isPgCall);

     Map<String, Object> addDept(Map param, StringBuilder err, boolean isPgCall);

    int insertDepartment(Department department, StringBuilder err, boolean isPgCall);

    //部门与生产小组信息及其人员信息要进行同步更新
    int updateDepartment(Map defInfoMap, StringBuilder err, boolean isPgCall);

    //部门与生产小组信息及其人员信息要进行同步更新
    int deleteDepartment(Integer depId, Long handlerId, StringBuilder err, boolean isPgCall);

    List<OrigUserCompany> selectValidUserCompanyByDepIdAndUid(Integer companyId, Integer depId, Long uid);

    List<PmPgDevice> selectValidByPgIdAndDeviceId(Long pgId, Long deviceId);

    List<Department> selectValidByUpperDepId(Integer companyId, Integer depId);

    int updatePgMemberIsLeader(Long pgId, Long memberId, String isLeader);

    List<UserRole> selectByCompanyIdAndDepIdAndRoleCode(Integer companyId, Integer depId, RoleEnum roleEnum);

    UserRole selectByCompanyIdAndDepIdAndUid(Integer companyId, Integer depId, Long uid);

    Long getRoleId(Integer companyId, RoleEnum roleEnum);

    OrigUserCompany selectUserCompanyByUid(Integer companyId, Long uid);

    OrigUserCompany selectUserCompanyByDepIdAndUid(Integer companyId, Integer depId, Long uid);

    List<OrigUserCompany> selectUserCompanyListByDepIdAndUid(Integer companyId, Integer depId, Long uid);

    Integer registerCompany(Company company);

    Integer createCompanyRegister(Company company, Long uid);

    Company selectByCompanyId(Integer companyId);

    Department selectByDepName(Integer companyId, String depName);

    List<Department> selectByUpperDepId(Integer companyId, Integer depId);

    Integer getAllUserCountByDepId(Integer companyId, Integer deptId);

    int getAllUpperDeptIds(Integer companyId, Integer depId, List<Integer> depIdMap);

    Map<String, Object> getAllUpperDeptIds(Integer companyId, Integer depId);

    Map<String,Object> vagueSelectCompanyAndUser(String comUserName, Integer searchType, Integer companyId, Integer uId);

    Long createNewId(IdType idType);

    int updateByPrimaryKeySelective(Company company);

    Department selectByDepId(Integer depId);

    Customer selectByCompanyIdAndCustomerId(Integer companyId, Integer customerId);

    Supplier selectByCompanyIdAndSupplierId(Integer companyId, Integer supplierId);

    List<Map<String, Object>> createDefaultDepartment(Integer companyId);

    String createCompanyInitData(Integer companyId, Long uid);

    ArrayList<Map<String, Object>> getAllSubDeps(Integer companyId, Integer depId);

    //int setDepAndSubsAllUpperIds(Integer companyId, Integer depId);

    //部门与生产小组信息及其人员信息要进行同步更新
    //int updateDepartment(Department department, StringBuilder err, boolean isPgCall);

    List<Map<String, Object>> selectSalesmanListByCompanyId(Integer companyId);

    List<Map<String, Object>> selectCstCompanyByCstName(Integer companyId, String cstName);

    Boolean createInvite10(Map<String, Object> param, StringBuilder err);

    Boolean createInvite11(Map<String, Object> param, StringBuilder err);

    Boolean createInvite20(Map<String, Object> param, StringBuilder err);

    Boolean createInvite30(Map<String, Object> param, StringBuilder err);

    Department selectByDepCode(Integer companyId, String depCode);

    Department selectProductionDept(Integer companyId);

    UserRole selectByCompanyIdAndUidAndRoleId(Integer companyId, Long uid, Long roleId);

    List<Map<String, Object>> selectAllAndSubUserCompanyByDepId(Integer companyId, Integer depId, String depCode);

    List<Map<String, Object>> selectCstContactorByCustomerId(Integer companyId, Integer cstCompanyId, String type);

    List<SupplierWare> selectBySupCompanyId(Integer companyId, Integer supCompanyId);

    int updateSupplierWare(List<SupplierWare> supplierWareList);

}

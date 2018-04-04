package com.mt.cms.common.dao;

import com.mt.cms.common.model.OrigUserCompany;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface OrigUserCompanyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrigUserCompany record);

    int insertSelective(OrigUserCompany record);

    OrigUserCompany selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrigUserCompany record);

    int updateByPrimaryKey(OrigUserCompany record);


    ///////////////// 添加
    Boolean batchDelete(HashMap<String, Object> map);

    Boolean updateDepId(Map<String, Object> map);

    int batchUpdate(Map<String, Object> map);

    int updateDepids(Map<String, Object> map);

    int insertUser(Map<String, Object> map);

    boolean isRoleCodeNull(Map<String, Object> map);

    OrigUserCompany selectUser(Map<String, Object> mapUser);

    int deleteUser(Map<String, Object> mapUser);

    List<Map<String,Object>> selectIds();

    Boolean batchDeleteNull(HashMap<String, Object> map);

    OrigUserCompany selectUserCompanyByUid(Map<String, Object> paramMap);

    OrigUserCompany selectUserCompanyByDepIdAndUid(Map<String, Object> paramMap);

    List<OrigUserCompany> selectUserCompanyListByDepIdAndUid(Map<String, Object> paramMap);

    //新增
    Integer addUserCompany(OrigUserCompany record);

    //修改
    Integer updateUserCompany(OrigUserCompany record);

    List<OrigUserCompany> selectValidUserCompanyByDepIdAndUid(Map map);
}
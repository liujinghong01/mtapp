package com.mt.cms.common.dao;

import com.mt.cms.common.model.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);

    ////////////////////////////////////////
    List<Map<String,Object>> selectCustomerId(Map<String, Object> map);

    Map<String,Object> getCustome(Map<String, Object> map);

    int updateCustomer(Customer customer);

    int deleteCustomer( List<Integer> list);

    List<Map<String,Object>> seachCstName(Map<String, Object> map);

    int totalCount(Map<String, Object> map);

    Integer getMaxCustomerId();

    Map<String,Object> selectCustomer(Map map);

    Customer getCustomes(Long cstCompanyId);

    Customer selectByCompanyIdAndCustomerId(Map map);

    List<Long> seletComapyId(int compayid);

    int totalCountIsActive(Map<String, Object> map);

    List<Map<String,Object>> customerList(Map<String, Object> map);

    List<Map<String, Object>> selectCstCompanyByCstName(Map<String, Object> map);

    List<Map<String, Object>> selectContactorByCustomerId(Integer customerId);
}
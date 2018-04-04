package com.mt.cms.company.service;

import com.mt.cms.common.model.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerService {

    Map<String,Object> selectCustomerId(Map<String, Object> map);

    Map<String, Object> getCustome( Map<String, Object> map);

    Customer getCustomes(Long cstCompanyId);

    int updateCustomer(Customer customer);


    int deleteCustomer( List<Integer> cst_sup_id);


    Map<String,Object> searchCstName(Map<String,Object> map);


    int insertCustomer(Customer customer);


    Map<String,Object> selectCustomer(Long companyId, Long cstCompanyId);

    //获取客户下拉列表
    Map<String,Object> customerList(Map<String, Object> map);
}

package com.mt.cms.common.dao;

import com.mt.cms.common.model.CustomerType;

public interface CustomerTypeMapper {

    int deleteByPrimaryKey(Integer cstType);

    int insert(CustomerType record);

    int insertSelective(CustomerType record);

    CustomerType selectByPrimaryKey(Integer cstType);

    int updateByPrimaryKeySelective(CustomerType record);

    int updateByPrimaryKey(CustomerType record);
}
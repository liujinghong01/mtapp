package com.mt.order.common.dao;

import com.mt.order.common.model.QuoteLog;
import org.apache.ibatis.annotations.Param;

public interface QuoteLogMapper {
    int deleteByPrimaryKey(@Param("id") Long id, @Param("version") Integer version);

    int insert(QuoteLog record);

    int insertSelective(QuoteLog record);

    QuoteLog selectByPrimaryKey(@Param("id") Long id, @Param("version") Integer version);

    int updateByPrimaryKeySelective(QuoteLog record);

    int updateByPrimaryKey(QuoteLog record);
}
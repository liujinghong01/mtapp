package com.mt.order.common.dao;

import com.mt.order.common.model.QuotePaymentTermLog;
import org.apache.ibatis.annotations.Param;

public interface QuotePaymentTermLogMapper {
    int deleteByPrimaryKey(@Param("id") Long id, @Param("version") Integer version);

    int insert(QuotePaymentTermLog record);

    int insertSelective(QuotePaymentTermLog record);

    QuotePaymentTermLog selectByPrimaryKey(@Param("id") Long id, @Param("version") Integer version);

    int updateByPrimaryKeySelective(QuotePaymentTermLog record);

    int updateByPrimaryKey(QuotePaymentTermLog record);
}
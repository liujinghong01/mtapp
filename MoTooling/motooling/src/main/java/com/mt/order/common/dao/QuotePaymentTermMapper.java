package com.mt.order.common.dao;

import com.mt.order.common.model.QuotePaymentTerm;

import java.util.List;

public interface QuotePaymentTermMapper {
    int deleteByPrimaryKey(Long id);

    int insert(QuotePaymentTerm record);

    int insertSelective(QuotePaymentTerm record);

    QuotePaymentTerm selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(QuotePaymentTerm record);

    int updateByPrimaryKey(QuotePaymentTerm record);

    //以下为自定义
    List<QuotePaymentTerm> selectByQuoteId(Long quoteId);

    List<QuotePaymentTerm> selectByQuoteIdAndVer(Long quoteId, Integer version);

    Long getMaxPayId();

    int deleteQuotePaymentsByQuoteId(Long quoteId);

}
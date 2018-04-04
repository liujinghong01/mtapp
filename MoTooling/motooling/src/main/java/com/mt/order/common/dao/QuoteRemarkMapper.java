package com.mt.order.common.dao;

import com.mt.order.common.model.QuoteRemark;

import java.util.List;

public interface QuoteRemarkMapper {
    int deleteByPrimaryKey(Long id);

    int insert(QuoteRemark record);

    int insertSelective(QuoteRemark record);

    QuoteRemark selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(QuoteRemark record);

    int updateByPrimaryKey(QuoteRemark record);


    //以下为处定义
    Long getMaxRemarkId();

    List<QuoteRemark> selectQuoteRemarksByQuoteId(Long quoteId);

    List<QuoteRemark> selectQuoteRemarksByQuoteIdAndVer(Long quoteId, Integer version);

    int deleteQuoteRemarksByQuoteId(Long quoteId);
}
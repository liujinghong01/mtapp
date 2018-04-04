package com.mt.order.common.dao;

import com.mt.order.common.model.QuoteMold;

import java.util.List;
import java.util.Map;

public interface QuoteMoldMapper {
    int deleteByPrimaryKey(Long id);

    int insert(QuoteMold record);

    int insertSelective(QuoteMold record);

    QuoteMold selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(QuoteMold record);

    int updateByPrimaryKey(QuoteMold record);

    //以下为自定义
    Long getMaxQuoteLineId();

    List<QuoteMold> selectByQuoteId(Long quoteId);

    List<QuoteMold> selectByQuoteIdAndVer(Long quoteId, Integer version);

    List<QuoteMold> selectQuoteMoldsByComputeId(Map map);

    QuoteMold selectByPrimaryKeyAndVer(Long quoteLineId, Integer version);

    List<QuoteMold> selectCurrentAndPrevVersionQuoteMolds(Map map);
}
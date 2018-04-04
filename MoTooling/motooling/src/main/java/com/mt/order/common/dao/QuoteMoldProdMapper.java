package com.mt.order.common.dao;

import com.mt.order.common.model.QuoteMoldProd;

import java.util.List;
import java.util.Map;

public interface QuoteMoldProdMapper {
    int deleteByPrimaryKey(Long id);

    int insert(QuoteMoldProd record);

    int insertSelective(QuoteMoldProd record);

    QuoteMoldProd selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(QuoteMoldProd record);

    int updateByPrimaryKey(QuoteMoldProd record);

    //以下是自定义
    Long getMaxProdId();

    List<QuoteMoldProd> selectByQuoteLineId(Long quoteMoldId);

    List<QuoteMoldProd> selectByQuoteLineIdAndVer(Long quoteMoldId, Integer version);

    List<QuoteMoldProd> selectCurrentAndPrevVersionQuoteMoldProds(Map map);

    int deleteProdsByLineId(Long quoteMoldId);
}
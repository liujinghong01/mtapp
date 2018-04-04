package com.mt.order.common.dao;

import com.mt.order.common.model.Quote;

import java.util.List;
import java.util.Map;

public interface QuoteMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Quote record);

    int insertSelective(Quote record);

    Quote selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Quote record);

    int updateByPrimaryKey(Quote record);

    //以下为自定义
    Map getMaxQuoteIdAndNo();

    List<Map<String, Object>> selectByConditions(Map queryMap);

    Long selectByConditionsCount(Map queryMap);

    /*int deleteTest();

    int deleteTest1();*/

    //Alnwick 添加  查询 报价单行信息
    List<Map<String, Object>> getDetails(Long quote_id);
    // 查询 报价单制品信息
    List<Map<String,Object>> selectQuoteMoldProd(Long quote_id);

    //产生报价单日志版本
    String addQuoteLog(Long quoteId);

    //删除报价单当前未审批结束版本，恢复报价单上一个版本
    String revertQuoteLog(Long quoteId);

    Quote selectByPrimaryKeyAndVer(Long quoteId, Integer version);

    List<Map<String, Object>> selectHisQuotes(Long quoteId);

    int updateQuoteHeadPrice(Long quoteId);
}
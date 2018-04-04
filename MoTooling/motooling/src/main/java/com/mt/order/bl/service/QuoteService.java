package com.mt.order.bl.service;

import com.mt.order.common.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface QuoteService {

    Map<String, Object> createQuoteNo(Integer companyId) ;

    Map<String, Object> createQuoteNo(Long hisQuoteId);

    ArrayList<String> createMoldNos(Long orderLineId, int moldNosCount);

    int modifyQuoteHeadInfo(Quote quote);

    int modifyQuoteLineInfo(QuoteMold quoteMold);

    int updateQuoteHeadPrice(Long quoteId);

    Long modifyQuoteLineInfo(QuoteMold quoteMold, List<QuoteMoldProd> quoteMoldProdList);

    Long createQuoteLineId();

    Long createQuoteLineProdId();

    int modifyQuoteMoldProdInfo(QuoteMoldProd quoteMoldProd);

    List<Map<String, Object>> modifyQuoteMoldProdInfo(Map param) throws Exception;

    int modifyQuotePayment(QuotePaymentTerm quotePaymentTerm);

    List<Map<String, Object>> modifyQuotePayment(Map param) throws Exception;

    int modifyQuoteRemark(QuoteRemark quoteRemark);

    List<Map<String, Object>> modifyQuoteRemark(Map param) throws Exception;

    List<QuotePaymentTerm> selectPaymentsByQuoteId(Long quoteId);

    List<QuotePaymentTerm>  selectPaymentsByQuoteId(Long quoteId, Integer version);

    List<QuoteMold> selectQuoteMoldsByQuoteId(Long quoteId);

    List<QuoteMold> selectQuoteMoldsByQuoteId(Long quoteId, Integer version);

    QuoteMold selectQuoteMoldByQuoteLineId(Long quoteLineId);

    QuoteMold selectQuoteMoldByQuoteLineId(Long quoteLineId, Integer version);

    List<QuoteMoldProd> selectQuoteMoldProdsByQuoteLineId(Long quoteLineId);

    List<QuoteMoldProd> selectQuoteMoldProdsByQuoteLineId(Long quoteLineId, Integer version);

    QuoteMoldProd selectQuoteMoldProdByProdId(Long prodId);

    List<Map<String, Object>> selectQuotes(Map queryMap, StringBuilder totalCount);

    List<Map<String, Object>> selectHisQuotes(Long quoteId);

    int deleteQuote(Long quoteId);

    int deleteQuoteLine(Long quoteLineId);

    int deleteQuoteLineProd(Long prodId);

    int deleteProdsByLineId(Long quoteLineId);

    int deleteQuotePaymentsByQuoteId(Long quoteId);

    int deleteQuoteRemarksByQuoteId(Long quoteId);

    Quote selectQuoteByQuoteId(Long quoteId);

    Quote selectQuoteByQuoteId(Long quoteId, Integer version);

    int insertQuote(Quote quote);

    int insertQuoteLine(QuoteMold quoteMold);

    int insertQuoteLineProd(QuoteMoldProd quoteMoldProd);

    boolean createQuoteRelateCompute(Long quoteId, Long quoteLineId, Long computeId, Map mapQuoteMoldAndProds, StringBuilder err);

    boolean createQuoteRelateCompute(Long quoteId, ArrayList<Long> computeIds, Map mapQuoteMoldAndProds, StringBuilder err);

    List<QuoteMold> selectQuoteMoldsByComputeId(Long quoteId, Long computeId);

    QuotePaymentTerm selectQuotePaymentByPayId(Long payId);

    QuoteRemark selectQuoteRemarkByRemarkId(Long remarkId);

    List<QuoteRemark> selectQuoteRemarksByQuoteId(Long quoteId);

    List<QuoteRemark> selectQuoteRemarksByQuoteId(Long quoteId, Integer version);

    int deleteQuotePaymentByPayId(Long payId);

    void deleteQuotePaymentByPayId(ArrayList<Long> payIds);

    int deleteQuoteRemarkByRemarkId(Long remarkId);

    void deleteQuoteRemarkByRemarkId(ArrayList<Long> remarkIds);

    List<Map<String, Object>> getMoldNosByOrderLineId(Long orderLineId);

    List<Map<String, Object>> createOrderStartup(Map<String, Object> orderStartupMap);

    List<QuoteMold> createReferHisQuote(Long newQuoteId, String newQuoteNo, Long quoteIdHis);

    List<QuoteMold> createReferHisQuote(Long quoteId, ArrayList<Long> hisQuoteIds);

    List<Map<String, Object>> quoteVersionCompare(QuoteMold curQuoteMold, QuoteMold hisQuoteMold);

    List<QuoteMold> selectCurrentAndPrevVersionQuoteMolds(Long quoteId, Integer hisVersion);

    List<Map<String, Object>> getQuoteVersionCompare(Long quoteId);

    List<Map<String, Object>> quoteVersionCompare(QuoteMoldProd curQuoteMoldProd, QuoteMoldProd hisQuoteMoldProd);

    List<QuoteMoldProd> selectCurrentAndPrevVersionQuoteMoldProds(Long quoteLineId, Integer hisVersion);

    Boolean OrderOutPurchRequireIsDealed(Long projectNotHwId);

    int deleteOrderOutPurchRequire(Long projectNotHwId);
}

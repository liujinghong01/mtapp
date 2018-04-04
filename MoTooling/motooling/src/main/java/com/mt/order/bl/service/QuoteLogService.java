package com.mt.order.bl.service;

import java.util.List;
import java.util.Map;

public interface QuoteLogService {
    //生成新的日志, 返回当前版本号
    Integer createNewQuoteLog(Long quoteId, StringBuilder approveStep);

    //恢复上次日志, 返回当前版本号
    Integer createRevertQuoteLog(Long quoteId);

    //当V2版本及其之后版本审批时，浏览页面中应比对之前版本，返回变化部分
    List<Map<String, Object>> quoteVersionCompare(Long quoteId);

}

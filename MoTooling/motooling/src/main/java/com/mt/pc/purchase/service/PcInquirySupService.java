package com.mt.pc.purchase.service;

import java.util.Map;


public interface PcInquirySupService {
        // 查询供应商询价反馈单列表
        Map<String,Object>  selectFeedBackList(Map<String, Object> map);

        // 查询供应商询价反馈单详情
        Map<String, Object>   selectFeedBackDetail(Map<String, Object> InquiryId);

        // 反馈单提交（供应商对询价价格反馈单进行修改提交）
        Map<String, Object>  updateInquirySup(Map<String, Object> map);
}

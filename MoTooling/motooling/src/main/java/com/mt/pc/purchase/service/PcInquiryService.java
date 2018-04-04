package com.mt.pc.purchase.service;

import java.util.Map;

/**
* @Author: Wendy
* @Description:
* @create    2018/1/24 14:30
* @Modified By:
*/
public interface PcInquiryService {

     // 查询处理询价单列表
     Map<String,Object>     selectInquiryList(Map<String, Object> map);

     // 查询询价单详情
     Map<String, Object>    selectInquiryDetail(Map<String, Object> map);

     // 查询询价反馈单详情
     Map<String, Object>   selectInquiryFeedBackDetail(Map<String, Object> map);
}

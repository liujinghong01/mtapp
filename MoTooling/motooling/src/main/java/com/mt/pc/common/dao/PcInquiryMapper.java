package com.mt.pc.common.dao;

import com.mt.pc.common.model.PcInquiry;

import java.util.List;
import java.util.Map;

public interface PcInquiryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PcInquiry record);

    int insertSelective(PcInquiry record);

    PcInquiry selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PcInquiry record);

    int updateByPrimaryKey(PcInquiry record);


    // 查询待处理询价单列表
    List<Map<String,Object>> selectInquiryList(Map<String, Object> map);

    // 查询总条数
    Integer totalCount(Map<String, Object> map);

    // 查询询价单详情主表
    Map<String,Object>   selectInquiry(Map<String,Object>  map);

    // 查询询价单详情主表
    Map<String,Object>   selectInquiryFeedBack(Map<String,Object>  map);

    // 新增 询价单主表
    Long addInquiry(Map<String, Object> InquiryInfo);

    // 修改 询价单主表
    Long updateInquiry(Map<String, Object> InquiryInfo);

    //统计公司发了几个供应商
    Map<String, Object>  totalSupCount(Map<String,Object>  map);

    //统计供应商的提交反馈情况
    Map<String, Object>  totalDealFlagCount(Map<String,Object>  map);

    Map<String, Object> selectInquiryId(Long  id);
}
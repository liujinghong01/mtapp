package com.mt.pc.common.dao;

import com.mt.pc.common.model.PcInquirySup;

import java.util.List;
import java.util.Map;

public interface PcInquirySupMapper {

    int deleteByPrimaryKey(Long id);

    int insert(PcInquirySup record);

    int insertSelective(PcInquirySup record);

    int updateByPrimaryKeySelective(PcInquirySup record);

    int updateByPrimaryKey(PcInquirySup record);


    // 查询供应商反馈列表{供应商列表信息}
    List<Map<String,Object>> selectFeedBackList(Map<String, Object> map);

    // 查询总条数
    Integer totalCount(Map<String, Object> map);

    // 查询供应商反馈详情主表{供应商主表信息}
    Map<String,Object>  selectInquirySup(Map<String,Object>  map);

    // 查询询价单详情{供应商详情信息}
    List<Map<String,Object>>  selectSupInfo(Integer id);

    // 查询供应商询价反馈单详情(根据inquiry_id查询出所有供应商信息，并且按总金额升序排列)
    List<Map<String,Object>>   selectSupInquiryFeedBack(Map<String, Object> map);

    // 新增 询价单供应商反馈（主表）
    Long addInquirySup(Map<String, Object> InquiryInfo);

    // 修改 询价单供应商反馈（主表）
    Long updateInquirySup(Map<String, Object> InquiryFeedback);

}
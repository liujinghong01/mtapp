package com.mt.pc.common.dao;

import com.mt.pc.common.model.PcInquirySub;

import java.util.List;
import java.util.Map;

public interface PcInquirySubMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PcInquirySub record);

    int insertSelective(PcInquirySub record);

    PcInquirySub selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PcInquirySub record);

    int updateByPrimaryKey(PcInquirySub record);


    // 查询询价单详情详情表(查看新创建的询价单)
    List<Map<String, Object>> selectInquirySub(Integer id);

    // 查询询价单详情详情表(计算总和)
    Map<String, Object> selectMatSumPrice(Map<String, Object> map);

    // 新增 询价详情表
    Integer addInquiryDetail(Map<String, Object> objectMap);
}
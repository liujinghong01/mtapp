package com.mt.pc.common.dao;

import com.mt.pc.common.model.PcInquirySupSub;

import java.util.List;
import java.util.Map;

public interface PcInquirySupSubMapper {

    int deleteByPrimaryKey(Long id);

    int insert(PcInquirySupSub record);


    int insertSelective(PcInquirySupSub record);


    PcInquirySupSub selectByPrimaryKey(Long id);


    int updateByPrimaryKeySelective(PcInquirySupSub record);


    int updateByPrimaryKey(PcInquirySupSub record);


    /**
     *  查询询价单反馈单详情（子表）
     *  作为公司查看供应商反馈情况
     *  作为供应商查看公司创建的询价单--进行提交反馈
     */

    List<Map<String,Object>> selectInquirySupSub(Map<String,Object>  map);

    /**
     * 统计BOM需求物料领料来源的总数量
     * @param map
     * @return
     */

    Map<String,Object> selectMatTotalQty(Map<String,Object>  map);

    // 新增 询价供应反馈详情表（子表）
    Integer addInquirySupDetail(Map<String, Object> objectMap);

    // 修改 询价供应反馈详情表 （子表）
    Integer updateInquirySupDetail(Map<String, Object> objectMap);
}
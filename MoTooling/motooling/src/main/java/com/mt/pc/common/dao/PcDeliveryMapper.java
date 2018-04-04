package com.mt.pc.common.dao;

import com.mt.pc.common.model.PcDelivery;

import java.util.Map;

public interface PcDeliveryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PcDelivery record);

    int insertSelective(PcDelivery record);

    PcDelivery selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PcDelivery record);

    int updateByPrimaryKey(PcDelivery record);
    //生成送货单
    Integer addDelivery(Map<String, Object> map);

    Map<String,Object> getDleveryInfo(Long purchId);
}
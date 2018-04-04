package com.mt.pc.common.dao;

import com.mt.pc.common.model.PcDeliverySub;

import java.util.List;
import java.util.Map;

public interface PcDeliverySubMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PcDeliverySub record);

    int insertSelective(PcDeliverySub record);

    PcDeliverySub selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PcDeliverySub record);

    int updateByPrimaryKey(PcDeliverySub record);
    //送货单子表
    int addDeliverySub(Map<String, Object> objectMap);

    List<Map<String,Object>> getDleverySubInfo(Long purchId);
}
package com.mt.pc.common.dao;

import com.mt.pc.common.model.PcPurchOrderSub;

import java.util.List;
import java.util.Map;

public interface PcPurchOrderSubMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PcPurchOrderSub record);

    int insertSelective(PcPurchOrderSub record);

    PcPurchOrderSub selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PcPurchOrderSub record);

    int updateByPrimaryKey(PcPurchOrderSub record);

    List<Map<String, Object>> selectPurchOrderSubDetail(Integer purchId);

    Boolean updatePurchOrderSub(Integer purchId);
    //ADD 采购单子表
    Integer addPurchOrderSub(Map<String, Object> objectMap);
    //update 采购单子表
    Integer updateOrderSub(Map<String, Object> objectMap);
    //统计total_price
    Long getTotalPrice(Long purchId);

    //Update 采购已建数量冲减
    Integer updateOrderSubQty(Map<String, Object> objectMap);
}
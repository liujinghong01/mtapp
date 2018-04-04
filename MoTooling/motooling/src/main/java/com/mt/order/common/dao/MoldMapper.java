package com.mt.order.common.dao;

import com.mt.order.common.model.Mold;

import java.util.List;
import java.util.Map;

public interface MoldMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Mold record);

    int insertSelective(Mold record);

    Mold selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Mold record);

    int updateByPrimaryKey(Mold record);


    //以下为自定义

    List<Map<String, Object>> getMoldNosByOrderLineId(Long orderLineId);

    int orderStartupLineInfo(Map orderStartupMap);

    int orderStartupMold(Map orderStartupMap);

    String getMaxMoldNo();

    Map<String, Object> getStartupStepByOrderLineId(Long orderLineId);

    //设置客户定单产品开始
    int updateOrderStartup(Integer orderLineId);
    //设置客户定单产品反完结
    int updateOrderNotEnd(Integer orderLineId);

    int updateOrderStartupStop(Map<String, Object> param);

    int createBom(Map<String, Object> createBomMap);

    //根据Mold信息获得公司Id和Name
    Map<String, Object> getCompanyOfMold(Map moldMap);

    //查询模具表获取交货日期（模具领料需要）
    Map<String,Object>   selectMoldGetDeliveryDate(Map<String,Object>  map);
}
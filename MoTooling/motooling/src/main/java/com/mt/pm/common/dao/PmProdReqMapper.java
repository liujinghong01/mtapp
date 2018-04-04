package com.mt.pm.common.dao;

import com.mt.pm.common.model.PmProdReq;

import java.util.List;
import java.util.Map;

public interface PmProdReqMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PmProdReq record);

    int insertSelective(PmProdReq record);

    PmProdReq selectByPrimaryKey(Long id);

    PmProdReq selectProdReqByPrimaryKey(Long id);


    //查询是否曾经生成过生产需求，只取最近一条记录
    PmProdReq selectReqBycondition(Map<String,Object>  map);

    int updateByPrimaryKeySelective(PmProdReq record);

    int updateByPrimaryKey(PmProdReq record);

    //查询模具物料需求表
    List<Map<String,Object>>   selectMouldMatterDemand(Map<String,Object>  map);

    //查询当前总库存数量
    Map<String,Object>  selectTotalStockQty(Map<String,Object>  map);

    //未创建生产定单列表--按物料
    List<Map<String, Object>> selectMatReqBycondition(Map queryMap);

    Integer selectMatReqByconditionCount(Map queryMap);


    //未创建生产定单列表--按Bom
    List<Map<String, Object>> selectBomReqBycondition(Map queryMap);

    Integer selectBomReqByconditionCount(Map queryMap);

    //未创建生产定单需求列表成品（主）--按Bom-Node
    List<Map<String, Object>> selectNodeReqByNodeId0(Map queryMap);

    //未创建生产定单需求列表（主）--按Bom-Node
    List<Map<String, Object>> selectNodeReqByNodeId(Map queryMap);
    //未创建生产定单需求列表（电极Bom）--按Bom-Node
    List<Map<String, Object>> selectENodeReqByNodeId(Map queryMap);

    //未创建生产定单需求信息--按Bom-Node
    List<Map<String, Object>> selectNodeReqInfo(Map queryMap);
}
package com.mt.pm.production.service;

import com.mt.pm.common.model.PmProdOrder;
import com.mt.pm.common.model.PmProdOrderSub;

import java.util.List;
import java.util.Map;

public interface PmProdOrderService {


    int deleteProdOrderByPrimaryKey(Long id);

    PmProdOrder selectPmProdOrderByPrimaryKey(Long id);

    Long createOrUpdateProdOrder(PmProdOrder record, List<PmProdOrderSub> records);

    Map<String, Object> selectProdOrderByConditions(Map<String, Object> map);


    int deleteProdOrderSubByPrimaryKey(Long id);

    int insert(PmProdOrderSub record);

    PmProdOrderSub selectPmProdOrderSubByPrimaryKey(Long id);

    int updateProdOrderSubByPrimaryKeySelective(PmProdOrderSub record);

    List<PmProdOrderSub> selectProdOrderSubs(Long prodOrderId);

    Map<String, Object> selectProdOrderSubByConditions(Map<String, Object> map);

    PmProdOrder schedule(PmProdOrder record, List<PmProdOrderSub> records);

    boolean generatedProdOrder(PmProdOrder record, List<PmProdOrderSub> records);

    //根据生产定单对应的BOM节点，获取该节点的下级节点的生产定单信息
    List<Map<String, Object>> selectProdOrderByPBomNode(Long id);
    //获取下级节点
    List<Map<String, Object>> selectBomSNodes(Long id);
    //获取上级节点
    Map<String, Object> selectBomPNode(Long id);

}

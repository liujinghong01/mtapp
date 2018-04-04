package com.mt.pc.common.dao;

import com.mt.pc.common.model.PcPurchOrder;

import java.util.List;
import java.util.Map;

public interface PcPurchOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PcPurchOrder record);

    int insertSelective(PcPurchOrder record);

    PcPurchOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PcPurchOrder record);

    int updateByPrimaryKey(PcPurchOrder record);
    //查询采购订单列表
    List<Map<String,Object>> selectPucchOrderList(Map<String, Object> map);

    Integer totalCount(Map<String, Object> map);
    //查询采购订单详情
    Map<String,Object> selectPurchOrderDetail(Integer purchId);
    //删除采购单
    Boolean updatePurchOrder(Integer purchId);
    //新增 采购订单
    Long addPurchOreder(Map<String, Object> purchInfo);
    //修改 采购订单
    Long updatePurch0reder(Map<String, Object> purchInfo);

    int updatePurchOrderTotalPrice(Map<String, Object> totalPrice);

    Map<String,Object> selectPurch(Long id);
    //查询销售定单列表
    List<Map<String,Object>> salesOrderList(Map<String, Object> map);

    Integer totalCountOrderList(Map<String, Object> map);

    //采购单是否需要价格审批
    Integer isNeedPriceApp(Long billId);

    //采购单是否需要一级审批
    Integer isNeedGrade1App(Long billId);

    //采购单是否需要二级审批
    Integer isNeedGrade2App(Long billId);

}
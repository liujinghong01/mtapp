package com.mt.pc.purchase.service.impl;

import com.mt.common.utils.DateUtil;
import com.mt.common.utils.StringUtils;
import com.mt.pc.common.dao.PcPurchOrderMapper;
import com.mt.pc.common.dao.PcPurchOrderSubMapper;
import com.mt.pc.common.dao.PcRequirePlanMapper;
import com.mt.pc.common.model.PcPurchOrder;
import com.mt.pc.purchase.service.CreateBillNoService;
import com.mt.pc.purchase.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private PcPurchOrderMapper pcPurchOrderMapper;
    @Autowired
    private PcPurchOrderSubMapper pcPurchOrderSubMapper;
    @Autowired
    private CreateBillNoService createBillNoService;
    @Autowired
    private PcRequirePlanMapper pcRequirePlanMapper;

    /**
     * 查询采购订单列表
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectPucchOrderList(Map<String, Object> map) {
        map.put("page", ((Integer) map.get("curr_page") - 1) * (Integer) map.get("page_size"));
        List<Map<String, Object>> pucchOrderList= pcPurchOrderMapper.selectPucchOrderList(map);
        if(pucchOrderList.size()<=0){
            return StringUtils.pageList(map,"purch_list",0);
        }
        Integer totalCount = pcPurchOrderMapper.totalCount(map);
        Map<String, Object> page = StringUtils.page(map, pucchOrderList, "purch_list", totalCount);
        return page;
    }

    /**
     * 查询采购订单详情
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectPurchOrderDetail(Map<String, Object> map) {
        Map<String, Object> query = (Map<String, Object>) map.get("query");
        Integer purchId = Integer.valueOf(query.get("purch_id").toString());
        Map<String, Object> param= pcPurchOrderMapper.selectPurchOrderDetail(purchId);
        List<Map<String, Object>> paramSub = pcPurchOrderSubMapper.selectPurchOrderSubDetail(purchId);
        param.put("purch_detail_list",paramSub);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("purch_info",param);
        return returnMap;
    }

    /**
     * 删除采购单
     * @param purchId
     * @return
     */
    @Override
    public Boolean deletePurchOrder(Integer purchId) {
      Boolean b  = pcPurchOrderMapper.updatePurchOrder(purchId);
        if(b){
               pcPurchOrderSubMapper.updatePurchOrderSub(purchId);
            }
        return b;
    }

    /**
     * 新增修改采购单
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> addPurchOreder(Map<String, Object> map) {
        Map<String, Object> purchInfo = (Map<String, Object>) map.get("purch_info");
        purchInfo.put("company_id",map.get("company_id"));
        List<Map<String, Object>> purchDetailList = (List<Map<String, Object>>) purchInfo.get("purch_detail_list");
        purchInfo.remove("purch_detail_list");
        Boolean partFlg = false;
        Boolean flg = false;
        Long id = null;
        if (purchInfo.containsKey("purch_id")) {
            partFlg = purchInfo.get("purch_id") == null || purchInfo.get("purch_id") == "" ? false : true;
        }
        if(partFlg){
            //修改 采购单
            purchInfo.put("updated_at", DateUtil.getDateTime());
            Long i = pcPurchOrderMapper.updatePurch0reder(purchInfo);

        }else {
            //新增
            purchInfo.put("purch_no",createBillNoService.createBillNo(Integer.valueOf(map.get("company_id").toString()),"BA",Long.valueOf(map.get("uid").toString())).get("bill_no"));
            purchInfo.put("created_at", DateUtil.getDateTime());
            Long i = pcPurchOrderMapper.addPurchOreder(purchInfo);
            id =   Long.valueOf(purchInfo.get("id").toString());
        }

        for (Map<String, Object> objectMap:purchDetailList) {
            objectMap.put("purch_id", purchInfo.get("purch_id") == null || purchInfo.get("purch_id") == "" ? id : purchInfo.get("purch_id"));
                if(objectMap.containsKey("detail_id")){
                    flg = purchInfo.get("detail_id") == null || purchInfo.get("detail_id") == "" ? false : true;
                }
                if(flg){
                    //修改 采购单子表
                    objectMap.put("updated_at", DateUtil.getDateTime());
                    int i =  pcPurchOrderSubMapper.updateOrderSub(objectMap);

                }else {
                    //新增
                    objectMap.put("created_at", DateUtil.getDateTime());
                    pcPurchOrderSubMapper.addPurchOrderSub(objectMap);
                    objectMap.get("id");
                }
                if(objectMap.get("require_id") != null && objectMap.get("require_id") != ""){
                    Map<String, Object> quantityMap = new HashMap<>(2);
                    quantityMap.put("buy_qty",objectMap.get("quantity"));
                    quantityMap.put("require_id",objectMap.get("require_id"));
                    pcRequirePlanMapper.updateRequirePlanQty(quantityMap);
                }


            }
        Long totalPrice =null;
        Map<String,Object> totalMap = new HashMap<>();
        if(partFlg){
            totalPrice=  pcPurchOrderSubMapper.getTotalPrice(Long.valueOf(purchInfo.get("purch_id").toString()));
            totalMap.put("totalPrice",totalPrice);
            totalMap.put("id",purchInfo.get("purch_id"));

        }else {
            totalPrice =   pcPurchOrderSubMapper.getTotalPrice(id);
            totalMap.put("totalPrice",totalPrice);
            totalMap.put("id",id);
        }
        pcPurchOrderMapper.updatePurchOrderTotalPrice(totalMap);
       Map<String ,Object> returnMap = pcPurchOrderMapper.selectPurch(Long.valueOf(totalMap.get("id").toString()));
       return returnMap;
    }

    @Override
    public int sendPurchOrder(Long purchOrderId, Long uid) {
        PcPurchOrder pcPurchOrder = pcPurchOrderMapper.selectByPrimaryKey(purchOrderId);
        if (!"step0".equals(pcPurchOrder.getApproveGrade2Step()) && !"step00".equals(pcPurchOrder.getApproveGrade2Step())) {
            throw new IllegalArgumentException("该采购单还没有审批完毕");
        }
        if (pcPurchOrder.getOrderFlag() != 0) {
            throw new IllegalArgumentException("该采购单不是待发送状态");
        }
        pcPurchOrder.setOrderFlag(Byte.valueOf("1"));
        pcPurchOrder.setSendDate(new Date());
        pcPurchOrder.setHandlerId(uid);
        pcPurchOrder.setUpdatedAt(new Date());
        return  pcPurchOrderMapper.updateByPrimaryKeySelective(pcPurchOrder);
    }
}

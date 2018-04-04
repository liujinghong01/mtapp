package com.mt.pc.purchase.service.impl;

import com.mt.common.utils.DateUtil;
import com.mt.common.utils.StringUtils;
import com.mt.pc.common.dao.PcDeliveryMapper;
import com.mt.pc.common.dao.PcDeliverySubMapper;
import com.mt.pc.common.dao.PcPurchOrderMapper;
import com.mt.pc.purchase.service.CreateBillNoService;
import com.mt.pc.purchase.service.DeliveryNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DeliveryNoteServiceImpl implements DeliveryNoteService {

    @Autowired
    private PcPurchOrderMapper pcPurchOrderMapper;
    @Autowired
    private PcDeliveryMapper pcDeliveryMapper;
    @Autowired
    private PcDeliverySubMapper pcDeliverySubMapper;
    @Autowired
    private CreateBillNoService createBillNoService;

    /**
     * 查询销售定单列表
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> salesOrderList(Map<String, Object> map) {
        map.put("page", ((Integer) map.get("curr_page") - 1) * (Integer) map.get("page_size"));
        List<Map<String,Object>> orderList =  pcPurchOrderMapper.salesOrderList(map);
        Integer totalCount = pcPurchOrderMapper.totalCountOrderList(map);
        if(orderList.size()<=0){
            return StringUtils.pageList(map,"sales_order_list",0);
        }
        Map<String, Object> page = StringUtils.page(map, orderList, "sales_order_list", totalCount);
        return page;
    }

    /**
     * 生成送货单
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> createDeliveryOrder(Map<String, Object> map) {
        List<Map<String, Object>> detailList= (List<Map<String, Object>>) map.get("delivery_detail_list");
        map.remove("delivery_detail_list");
        map.put("created_at", DateUtil.getDateTime());
        map.put("delivery_no",createBillNoService.createBillNo(Integer.valueOf(map.get("company_id").toString()),"BA",Long.valueOf(map.get("uid").toString())).get("bill_no"));
        Integer id = pcDeliveryMapper.addDelivery(map);
        for (Map<String, Object> objectMap:detailList) {
            objectMap.put("delivery_id",id);
            objectMap.put("created_at",DateUtil.getDateTime());
            pcDeliverySubMapper.addDeliverySub(objectMap);
        }
        Long purchId =  Long.valueOf(map.get("purch_id").toString());
        Map<String, Object> deliveryInfo =pcDeliveryMapper.getDleveryInfo(purchId);
        List<Map<String, Object>> subList=pcDeliverySubMapper.getDleverySubInfo(purchId);
        deliveryInfo.put("delivery_detail_list",subList);
        return deliveryInfo;
    }
}

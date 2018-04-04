package com.mt.pc.purchase.service.impl;


import com.mt.common.utils.DateUtil;
import com.mt.pc.common.dao.*;
import com.mt.pc.purchase.service.CreateBillNoService;
import com.mt.pc.purchase.service.PcInquirySubService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
* @Author: Wendy
* @Description:
* @create    2018/1/24 14:58
* @Modified By:
*/
@Service
public class PcInquirySubServiceImpl implements PcInquirySubService{
    private final static Logger log = Logger.getLogger(PcInquirySubServiceImpl.class);
    @Autowired
    private PcInquiryMapper  pcInquiryMapper;

    @Autowired
    private PcInquirySupMapper pcInquirySupMapper;

    @Autowired
    private PcInquirySubMapper  pcInquirySubMapper;

    @Autowired
    private CreateBillNoService createBillNoService;

    @Autowired
    private PcRequirePlanMapper pcRequirePlanMapper;

    @Autowired
    private PcInquirySupSubMapper pcInquirySupSubMapper;


    /**
     * 新增询价单发送到供应商
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> addInquirySub(Map<String, Object> map) {
        Map<String, Object> inquiryInfo  = (Map<String, Object>) map.get("inquiry_info");
        inquiryInfo.put("company_id",map.get("company_id"));
        List<Map<String, Object>>  matList = (List<Map<String, Object>>)inquiryInfo.get("mat_list");
        inquiryInfo.remove("mat_list");
        List<Map<String, Object>>  supList = (List<Map<String, Object>>)inquiryInfo.get("sup_list");
        inquiryInfo.remove("sup_list");
        String inquiryNo = createBillNoService.createBillNo(Integer.valueOf(map.get("company_id").toString()), "MI", Long.valueOf(map.get("uid").toString())).get("bill_no").toString();

        Double  totalPrice=null;
        Long inquiryId = null;
        Long inquirySupId = null;
        Long inquirySubId = null;

        // 新增询价主表
        inquiryInfo .put("created_at", DateUtil.getDateTime());
        inquiryInfo .put("inquiry_no", inquiryNo);
        inquiryInfo .put("inquiry_date", DateUtil.getDateTime());
        inquiryInfo.put("company_name",map.get("company_name"));
        Long i = pcInquiryMapper.addInquiry(inquiryInfo);
        inquiryId =   Long.valueOf(inquiryInfo.get("id").toString());
        for (Map<String, Object> objectMap:matList) {
            objectMap.put("inquiry_id", inquiryInfo.get("id") == null || inquiryInfo.get("id") == "" ? inquiryId : inquiryInfo.get("id"));

            // 新增询价详情表
            objectMap.put("created_at", DateUtil.getDateTime());
            Integer isOk= pcInquirySubMapper.addInquiryDetail(objectMap);
            inquirySubId=Long.valueOf(objectMap.get("id").toString());

            // 生成询价单成功
            if(isOk>0) {
                Long requireId = Long.valueOf(objectMap.get("require_id").toString());
                Map<String, Object> qtyParam = pcRequirePlanMapper.selectRequirePlanDeatil(requireId);
                // 最初的数量
                Double planQty = Double.valueOf(qtyParam.get("plan_qty").toString());
                // 需要询价的数量
                Double quantity = Double.valueOf(objectMap.get("quantity").toString());
                // 已处理的数量
                Double buyQty = planQty - quantity;
                objectMap.put("buy_qty", buyQty);
                objectMap.put("require_id", requireId);
                pcRequirePlanMapper.updateRequirePlanQty(objectMap);

                // 数量小于等于0就把该物料从列表移除
                if (buyQty <= 0) {
                    pcRequirePlanMapper.updateRequirePlan(objectMap);
                }

                //计算总金额
                objectMap.put("inquiry_id",inquiryInfo.get("id"));
                Map<String,Object> inquirySupSubParam=pcInquirySubMapper.selectMatSumPrice(objectMap);
                totalPrice=Double.valueOf(inquirySupSubParam.get("total_price").toString());
                inquirySupSubParam.put("total_price",totalPrice);
                inquirySupSubParam.put("id",inquiryId);
                pcInquiryMapper.updateInquiry(inquirySupSubParam);
            }
        }

        for (Map<String, Object> objectMap:supList) {
            objectMap.put("id", inquiryInfo.get("id") == null || inquiryInfo.get("id") == "" ? inquirySupId : inquiryInfo.get("id"));
            // 新增反馈主表
            objectMap.put("created_at", DateUtil.getDateTime());
            objectMap.put("inquiry_id",inquiryId);
            objectMap.put("company_id",inquiryInfo.get("company_id"));
            objectMap.put("total_price",totalPrice);
            inquiryInfo.putAll(objectMap);
            Long isOk=pcInquirySupMapper.addInquirySup(inquiryInfo);
            inquirySupId=Long.valueOf(inquiryInfo.get("id").toString());
                for (Map<String, Object> matMap:matList) {
                    // 新增反馈单详情
                    matMap.put("created_at", DateUtil.getDateTime());
                    matMap.put("inquiry_detail_id",inquirySubId);
                    matMap.put("id",inquirySupId);
                    pcInquirySupSubMapper.addInquirySupDetail(matMap);
                }
        }
        Map<String ,Object> returnMap =pcInquiryMapper.selectInquiryId(inquiryId);
        return returnMap;
    }
}
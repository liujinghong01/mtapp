package com.mt.pc.purchase.service.impl;


import com.mt.common.utils.DateUtil;
import com.mt.common.utils.StringUtils;
import com.mt.pc.common.dao.PcInquirySupMapper;
import com.mt.pc.common.dao.PcInquirySupSubMapper;
import com.mt.pc.purchase.service.PcInquirySupService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Author: Wendy
* @Description:
* @create    2018/1/24 16:42
* @Modified By:
*/    
@Service
public class PcInquirySupServiceImpl  implements PcInquirySupService{
    private final static Logger log = Logger.getLogger(PcInquirySupServiceImpl.class);

    @Autowired
    private PcInquirySupMapper  pcInquirySupMapper;

    @Autowired
    private PcInquirySupSubMapper  pcInquirySupSubMapper;


    /**
     * 查询供应商询价反馈单列表
     * info:登录公司ID查看自己需要提交反馈的数据列表
     * info:sup_company_id==company_id
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectFeedBackList(Map<String, Object> map) {
        if(map.containsKey("query")) {
            Map<String, Object> query = (Map<String, Object>) map.get("query");
            StringBuilder sb = new StringBuilder();
            map.put("deal_flag", query.get("deal_flag")==null||query.get("deal_flag")==""?null:query.get("deal_flag"));
            map.put("mat_desc", query.get("mat_desc")==null||query.get("mat_desc")==""?null:query.get("mat_desc"));
        }
        map.put("page",((Integer)map.get("curr_page")-1)*(Integer) map.get("page_size"));
        List<Map<String, Object>> inquiryList = pcInquirySupMapper.selectFeedBackList(map);
        if(inquiryList.size()<=0){
            return StringUtils.pageList(map,"inquiry_list",0);
        }
        int totalCount  = pcInquirySupMapper.totalCount(map);
        Map<String, Object> page = StringUtils.page(map, inquiryList,"inquiry_list", totalCount);
        return page;
    }


    /**
     * 查询供应商询价反馈单详情
     * info:登录公司ID查看自己需要提交反馈的数据详情
     * info:sup_company_id==company_id
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectFeedBackDetail(Map<String, Object> map) {
        map.put("company_id", map.get("company_id")==null||map.get("company_id")==""?null:map.get("company_id"));
        if(map.containsKey("query")) {
            Map<String, Object> query = (Map<String, Object>) map.get("query");
            map.put("inquiry_id", query.get("inquiry_id")==null||query.get("inquiry_id")==""?null:query.get("inquiry_id"));
        }
        Map<String, Object> paramMap = pcInquirySupMapper.selectInquirySup(map);
        paramMap.put("inquiry_sup_id", paramMap.get("feedback_id").toString());
        List<Map<String, Object>> paramSub = pcInquirySupSubMapper.selectInquirySupSub(paramMap);
        paramMap.put("mat_list", paramSub);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("inquiry_feedback", paramMap);
        return returnMap;
    }


    /**
     * 反馈单提交（供应商对反馈单进行修改提交）
     * 对价格回馈给公司
     * @param map
     * @return
     */
    @Override
    public Map<String, Object>  updateInquirySup(Map<String, Object> map) {
        Map<String, Object> inquiryFeedback  = (Map<String, Object>) map.get("inquiry_feedback");
        List<Map<String, Object>>  matList = (List<Map<String, Object>>)inquiryFeedback.get("mat_list");
        inquiryFeedback.remove("mat_list");

        Double  totalPrice=null;
        Long id = null;
        if (inquiryFeedback.get("feedback_id")!=null) {
            inquiryFeedback.put("sup_company_id",map.get("company_id"));
            inquiryFeedback.put("feedback_id",inquiryFeedback.get("feedback_id"));
            inquiryFeedback.put("updated_at", DateUtil.getDateTime());
            inquiryFeedback.put("deal_flag", "2");
            pcInquirySupMapper.updateInquirySup(inquiryFeedback);
        }

        for (Map<String, Object> objectMap : matList) {
            objectMap.put("feedback_id", inquiryFeedback.get("feedback_id") == null || inquiryFeedback.get("feedback_id") == "" ? id : inquiryFeedback.get("feedback_id"));
            if (objectMap.get("feedback_detail_id")!=null) {
                objectMap.put("feedback_detail_id",objectMap.get("feedback_detail_id"));
                objectMap.put("updated_at", DateUtil.getDateTime());
                int i = pcInquirySupSubMapper.updateInquirySupDetail(objectMap);
                if(i>0){
                    // 计算总金额
                    objectMap.put("inquiry_sup_id",inquiryFeedback.get("feedback_id"));
                    Map<String,Object> inquirySupSubParam=pcInquirySupSubMapper.selectMatTotalQty(objectMap);
                    totalPrice=Double.valueOf(inquirySupSubParam.get("total_price").toString());
                    inquiryFeedback.put("total_price",totalPrice);
                    pcInquirySupMapper.updateInquirySup(inquiryFeedback);
                }
            }
        }
        return inquiryFeedback;
    }
}

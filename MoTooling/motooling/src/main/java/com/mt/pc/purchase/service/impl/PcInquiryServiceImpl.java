package com.mt.pc.purchase.service.impl;

import com.mt.common.utils.StringUtils;
import com.mt.pc.common.dao.PcInquiryMapper;
import com.mt.pc.common.dao.PcInquirySubMapper;
import com.mt.pc.common.dao.PcInquirySupMapper;
import com.mt.pc.common.dao.PcInquirySupSubMapper;
import com.mt.pc.purchase.service.PcInquiryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Author: Wendy
* @Description:
* @create    2018/1/24 14:55
* @Modified By:
*/    
@Service
public class PcInquiryServiceImpl implements PcInquiryService{
    private final static Logger log = Logger.getLogger(PcInquiryServiceImpl.class);

    @Autowired
    private PcInquiryMapper  pcInquiryMapper;

    @Autowired
    private PcInquirySubMapper  pcInquirySubMapper;

    @Autowired
    private PcInquirySupMapper  pcInquirySupMapper;

    @Autowired
    private PcInquirySupSubMapper pcInquirySupSubMapper;


    /**
     * 查询待处理询价单列表
     * info:查看公司新创建的询价单列表
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectInquiryList(Map<String, Object> map) {
        int totalCount  =pcInquiryMapper.totalCount(map);
        map.put("page", ((Integer) map.get("curr_page") - 1) * (Integer) map.get("page_size"));

        List<Map<String, Object>>  inquiryList = pcInquiryMapper.selectInquiryList(map);
        if(inquiryList.size()<=0){
            return StringUtils.pageList(map,"inquiry_list",0);
        }

        for(Map<String,Object>  inquiryParam:inquiryList) {
            inquiryParam.put("inquiry_id", inquiryParam.get("inquiry_id").toString());
            inquiryParam.put("company_id", map.get("company_id").toString());

            // 统计反馈状态
            Map<String, Object> dealFlagParam = pcInquiryMapper.totalDealFlagCount(inquiryParam);
            // 统计发送的供应商总数
            Map<String, Object> supParam = pcInquiryMapper.totalSupCount(inquiryParam);
            Integer  dealCount=Integer.valueOf(dealFlagParam.get("deal_flag").toString());
            Integer  supCount =Integer.valueOf(supParam.get("inquiry_id").toString());
                // 1.部分反馈  2.全部反馈  0.未反馈
                if (dealCount.intValue() < supCount.intValue()) {
                    inquiryParam.put("deal_flag", "1");
                } else  if (dealCount.intValue() == supCount.intValue()) {
                    inquiryParam.put("deal_flag", "2");
                }
                if(dealCount==0){
                    inquiryParam.put("deal_flag", "0");
                }
            }
        Map<String, Object> page = StringUtils.page(map, inquiryList,"inquiry_list", totalCount);
        return page;
    }


    /**
     * 查询询价单详情(查看公司创建成功的询价单)
     * info:查看公司新创建的询价单详情
     * @param map
     * @return
     */
    @Override
    public Map<String,Object> selectInquiryDetail(Map<String,Object> map) {
        Integer inquiryId =null;
        if(map.containsKey("query")) {
            Map<String, Object> query = (Map<String, Object>) map.get("query");
            map.put("inquiry_id", query.get("inquiry_id") == null || query.get("inquiry_id") == "" ? null : query.get("inquiry_id"));
            inquiryId=Integer.valueOf(query.get("inquiry_id").toString());
        }
        Map<String,Object> param=pcInquiryMapper.selectInquiry(map);
        List<Map<String,Object>> inquirySubList=pcInquirySubMapper.selectInquirySub(inquiryId);
        param.put("mat_list",inquirySubList);

        List<Map<String,Object>> listSupList=pcInquirySupMapper.selectSupInfo(inquiryId);
        param.put("sup_list",listSupList);

        Map<String,Object>  returnMap=new HashMap<String,Object>();
        returnMap.put("inquiry_info",param);
        return returnMap;
    }


    /**
     * 查询询价反馈单详情{根据公司查看供应商反馈的情况}
     * 查看供应商的数据反馈情况(公司发送到供应商——查看供应商反馈状态)
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> selectInquiryFeedBackDetail(Map<String, Object> param) {
        if (param.get("company_id") ==null || param.get("company_id")==""){
            throw new NullPointerException ("请写公司ID");
        }

        if(param.containsKey("query")) {
            Map<String, Object> query = (Map<String, Object>) param.get("query");
            param.put("inquiry_id", query.get("inquiry_id") == null || query.get("inquiry_id") == "" ? null : query.get("inquiry_id"));
            param.put("feedback_id", query.get("feedback_id") + "");
            param.put("company_id", param.get("company_id"));
        }

        Map<String, Object> inquiryParam = pcInquiryMapper.selectInquiryFeedBack(param);
        if(inquiryParam==null){
            throw   new  NullPointerException("查不到此询价单信息！");
        }
        inquiryParam.put("id", inquiryParam.get("inquiry_id"));
        inquiryParam.put("company_id", param.get("company_id"));
        inquiryParam.put("feedback_id", param.get("feedback_id"));

        List<Map<String,Object>> inquirySupList=pcInquirySupMapper.selectSupInquiryFeedBack(inquiryParam);
        for(Map<String,Object> mapList:inquirySupList){
            mapList.put("inquiry_sup_id",mapList.get("feedback_id").toString());
            List<Map<String,Object>> inquirySupSubList=pcInquirySupSubMapper.selectInquirySupSub(mapList);
            mapList.put("mat_list",inquirySupSubList);
        }
        Map<String,Object>  returnMap=new HashMap<String,Object>();
        returnMap.put("inquiry_feedback_list",inquirySupList);
        return  returnMap;
    }
}
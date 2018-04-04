package com.mt.pm.production.service.impl;


import com.mt.bm.common.dao.BomNodeMapper;
import com.mt.bm.common.model.BomNode;
import com.mt.common.utils.StringUtils;
import com.mt.iv.common.dao.BmMatMapper;
import com.mt.iv.common.dao.IvApplyMatBillMapper;
import com.mt.order.common.dao.MoldMapper;
import com.mt.pm.common.dao.PmProdReqMapper;
import com.mt.pm.common.model.PmProdReq;
import com.mt.pm.production.service.PmProdReqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PmProdReqServiceImpl implements PmProdReqService{
    @Autowired
    private PmProdReqMapper  pmProdReqMapper;
    @Autowired
    private BomNodeMapper nodeMapper;
    @Autowired
    private BmMatMapper bmMatMapper;
    @Autowired
    private MoldMapper moldMapper;
    @Autowired
    private IvApplyMatBillMapper ivApplyMatBillMapper;


    @Override
    public Map<String, Object> selectMouldMatterDemand(Map<String, Object> map) {
        Map<String,Object>  query=(Map<String,Object>)map.get("query");
        if (query.get("apply_type") ==null || query.get("apply_type")==""){
            throw new NullPointerException ("请选择领料类型");
        }
        String  applyType=query.get("apply_type")==null||query.get("apply_type")==""?null:query.get("apply_type").toString();
        List<Map<String,Object>>  reqList=new ArrayList<Map<String,Object>>();
        switch (applyType){
            case "1":
                // 需求领料（——BOM子表的物料）
                reqList=pmProdReqMapper.selectMouldMatterDemand(map);
                for(Map<String,Object>  reqParam:reqList) {
                    reqParam.put("company_id", map.get("company_id"));

                    // undeal_qty
                    Map<String,Object>   sumQtyParam=ivApplyMatBillMapper.selectMatInfoByQty(reqParam);
                    if(sumQtyParam.get("applyNum")==null){
                        reqParam.put("undeal_qty", reqParam.get("total_qty"));
                    }else{
                        Double totalQty = Double.valueOf(sumQtyParam.get("total_qty").toString());
                        Double sumApplyQty = Double.valueOf(sumQtyParam.get("applyNum").toString());
                        reqParam.put("undeal_qty",totalQty-sumApplyQty);
                    }

                    // total_stock_qty
                    Map<String, Object> totalQty = pmProdReqMapper.selectTotalStockQty(reqParam);
                    if (totalQty == null) {
                        reqParam.put("total_stock_qty", null);
                    } else {
                        Double totalStockQty = Double.valueOf(totalQty.get("total_stock_qty").toString());
                        reqParam.put("total_stock_qty", totalStockQty);
                    }

                    // delivery_date
                    Map<String,Object>   dateParam=moldMapper.selectMoldGetDeliveryDate(reqParam);
                    if(dateParam==null){
                        reqParam.put("delivery_date",null);
                    }else {
                        reqParam.put("delivery_date", dateParam.get("delivery_date"));
                    }

                    // mat
                    Map<String,Object>  matParam=bmMatMapper.selectMatInfo(reqParam);
                    if(matParam==null){
                        reqParam.put("mat_id",null);
                    }

                    Iterator iterator = reqParam.keySet().iterator();
                    // 移除取出的值
                    while (iterator.hasNext()) {
                        String str=(String)iterator.next();
                        if("company_id".equals(str)){
                            iterator.remove();
                        }
                        if("mold_no".equals(str)){
                            iterator.remove();
                        }
                    }
                    reqParam.putAll(matParam);
                }
                break;
            case "2":
                // 超领 （——物料基础数据）
                break ;
            default:
                break;
        }

        Map<String,Object>  returnMap=new HashMap<String,Object>();
        returnMap.put("req_list",reqList);
        return returnMap;
    }

    @Override
    public boolean createPmProdReqfromBom(PmProdReq pmProdReq) {
        pmProdReq.setCreatedAt(new Date());
        pmProdReq.setUpdatedAt(new Date());
        pmProdReq.setReqType("1");
        pmProdReq.setBomType("1");
        pmProdReq.setStatus("1");
        return pmProdReqMapper.insert(pmProdReq)>0;
    }

    @Override
    public  Map<String, Object> selectProdReqByBomConditions(Map<String, Object> map){
        Map<String, Object> query = (Map<String, Object>) map.get("query");
        map.put("page", ((Integer) map.get("curr_page") - 1) * (Integer) map.get("page_size"));
        Integer totalCount = pmProdReqMapper.selectBomReqByconditionCount(query);
        List<Map<String, Object>> list = pmProdReqMapper.selectBomReqBycondition(query);
        if(list.size()<=0){
            return StringUtils.pageList(map,"prod_info",0);
        }
        Map<String, Object> page = StringUtils.page(map, list, "prod_info", totalCount);
        return page;
    }

    @Override
    public  Map<String, Object> selectProdReqByMatConditions(Map<String, Object> map){
        Map<String, Object> query = (Map<String, Object>) map.get("query");
        map.put("page", ((Integer) map.get("curr_page") - 1) * (Integer) map.get("page_size"));
        Integer totalCount = pmProdReqMapper.selectMatReqByconditionCount(query);
        List<Map<String, Object>> list = pmProdReqMapper.selectMatReqBycondition(query);
        if(list.size()<=0){
            return StringUtils.pageList(map,"prod_info",0);
        }
        Map<String, Object> page = StringUtils.page(map, list, "prod_info", totalCount);
        return page;
    }

    @Override
    public  Map<String, Object> selectProdReqByNode(Map<String, Object> map){
        Map<String, Object> dataMap = new HashMap<String, Object>();
        if(map.get("bom_type").toString().equals("电极"))
        {
            BomNode bomNode=nodeMapper.selectByPrimaryKey(Long.parseLong(map.get("node_id").toString()));
            if(bomNode.getPath().equals("0")||bomNode.getParentPath().equals(""))
            {
                dataMap.put("bom_node_list", pmProdReqMapper.selectENodeReqByNodeId(map));
            }
            else
            {
                dataMap.put("bom_node_list", pmProdReqMapper.selectNodeReqByNodeId(map));
            }
        }
        else {
            BomNode bomNode=nodeMapper.selectByPrimaryKey(Long.parseLong(map.get("node_id").toString()));
            if(bomNode.getPath().equals("0")&& "成品".equals(map.get("bom_type").toString()))
            {
                dataMap.put("bom_node_list", pmProdReqMapper.selectNodeReqByNodeId0(map));
            }
            else {
                map.put("bom_type","");
                dataMap.put("bom_node_list", pmProdReqMapper.selectNodeReqByNodeId(map));
            }
        }
        return dataMap;
    }

    @Override
    public  PmProdReq selectProdReqByPrimaryKey(Long reqId){
        return pmProdReqMapper.selectByPrimaryKey(reqId);
    }
}

package com.mt.qm.quality.service.impl;

import com.mt.common.utils.DateUtil;
import com.mt.common.utils.StringUtils;
import com.mt.pc.common.dao.PcPurchOrderSubMapper;
import com.mt.pc.purchase.service.CreateBillNoService;
import com.mt.qm.common.dao.QmPurchQcMapper;
import com.mt.qm.common.dao.QmPurchQcSubMapper;
import com.mt.qm.quality.service.PurchQcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Author: Wendy
* @Description:
* @create    2018/1/12 9:32
* @Modified By:
*/
@Service
public class PurchQcServiceImpl implements PurchQcService{
    @Autowired
    private QmPurchQcMapper qmPurchQcMapper;

    @Autowired
    private QmPurchQcSubMapper qmPurchQcSubMapper;

    @Autowired
    private CreateBillNoService createBillNoService;

    @Autowired
    private PcPurchOrderSubMapper  pcPurchOrderSubMapper;


    /**
     * 查询来料质检列表
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectPurchQcList(Map<String, Object> map) {
        Map<String,Object>  query=(Map<String,Object>)map.get("query");
        if(map.containsKey("query")){
            if(query.get("approve_step")!=null && query.get("approve_step")!=""){
                map.put("approve_step",query.get("approve_step"));
            }
        }
        int   totalCount=qmPurchQcMapper.totalCount(map);
        map.put("page", ((Integer) map.get("curr_page") - 1) * (Integer) map.get("page_size"));

        List<Map<String,Object>> applyMatList=qmPurchQcMapper.selectIncomingMatCheckList(map);
        if(applyMatList.size()<=0){
            return StringUtils.pageList(map,"qc_info",0);
        }
        Map<String,Object>  page= StringUtils.page(map, applyMatList,"qc_info", totalCount);
        return page;
    }


    /**
     * 删除来料质检单
     * @param map
     * @return
     */
    @Override
    public Integer updatePurchQcBill(Map<String, Object> map) {
        Integer i;
        map.put("id",map.get("purch_qc_id"));
        List<Map<String,Object>>   approveStepList=qmPurchQcMapper.selectApproveStepList(map);
        if(approveStepList.size()>0) {
             i=qmPurchQcMapper.updateIncomingMatCheck(Long.valueOf(map.get("purch_qc_id").toString().trim()));
            if (i > 0) {
                // 移除未审批单、已建单数量冲减
                map.put("purch_qc_id", map.get("purch_qc_id").toString());
                List<Map<String,Object>> qtyList = qmPurchQcSubMapper.selectIncomingCheckQtyById(map);
                for(Map<String,Object>  qtyParam:qtyList) {
                    // 删除来料质检物料
                    qmPurchQcSubMapper.updateIncomingMatCheckInfo(Long.valueOf(qtyParam.get("purch_qc_id").toString()));
                    qtyParam.put("purch_sub_id", qtyParam.get("purch_sub_id"));
                    Map<String, Object> qtyBillParam = qmPurchQcSubMapper.selectPurchQuantityById(qtyParam);
                    Double qcBillQty = Double.valueOf(qtyBillParam.get("qc_bill_qty").toString());
                    if(qtyParam.get("qc_qty")!=null) {
                        Double qcQty = Double.valueOf(qtyParam.get("qc_qty").toString());
                        qtyBillParam.put("qc_bill_qty", qcBillQty - qcQty);
                        qtyBillParam.put("detail_id", qtyParam.get("purch_sub_id"));
                        pcPurchOrderSubMapper.updateOrderSubQty(qtyBillParam);
                    }
                }
            }
        }else{
            throw   new  NullPointerException("此质检单不能删除,已经通过审批！");
        }
        return i;
    }

    /**
     * 新增修改来料质检
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> addAndUpdatePurchQcBill(Map<String, Object> map) {
        return this.saveQmPurchQcManager(map,false);
    }

    /**
     * 查询送货质检详情
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectDeliveryBillPurDetail(Map<String, Object> map) {
        Map<String,Object>  query=(Map<String,Object>)map.get("query");
        if(map.containsKey("query")){
            map.put("delivery_no",query.get("delivery_no")==null||query.get("delivery_no")==""?null:query.get("delivery_no"));
        }
        Map<String,Object>    qmPurchParam=qmPurchQcSubMapper.selectDeliveryBillCheckDetail(map);
        List<Map<String,Object>>   stayQualityList=qmPurchQcSubMapper.selectDeliveryPurDetail(map);
        for(Map<String,Object>  stayQualityParam:stayQualityList){
            Double   quantity=Double.valueOf(stayQualityParam.get("quantity").toString());
            Double   qcBillQty=Double.valueOf(stayQualityParam.get("qc_bill_qty").toString());
            stayQualityParam.put("no_qc_qty",quantity-qcBillQty);
        }
        qmPurchParam.put("delivery_qc_detail_list",stayQualityList);

        Map<String,Object>  returnMap=new HashMap<String,Object>();
        returnMap.put("delivery_qc_info",qmPurchParam);
        return returnMap;
    }


    /**
     * 根据送货单号生成质检单
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> addDeliveryNoPurBill(Map<String, Object> map) {
        return this.saveQmPurchQcManager(map,true);
    }

    /**
     * 查询来料质检详情
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectPurchQcDetail(Map<String, Object> map) {
        Map<String,Object>  query=(Map<String,Object>)map.get("query");
        if(map.containsKey("query")){
            map.put("purch_qc_no",query.get("purch_qc_no")==null||query.get("purch_qc_no")==""?null:query.get("purch_qc_no"));
        }
        Map<String,Object>  purchQcInfo=qmPurchQcMapper.selectIncomingMatCheckMainInfo(map);
        List<Map<String,Object>>   purchQcList=qmPurchQcSubMapper.selectIncomingMatCheckDetail(map);
        purchQcInfo.put("purch_qc_detail_list",purchQcList);
        Map<String,Object>  returnMap=new HashMap<String,Object>();
        returnMap.put("purch_qc_info",purchQcInfo);
        return returnMap;
    }

    /**
     * 查询来料待质检详情
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectStayCheckPurchDetail(Map<String, Object> map) {
        Map<String, Object> query = (Map<String, Object>) map.get("query");
        if(map.containsKey("query")){
            map.put("purch_no",query.get("purch_no")==null||query.get("purch_no")==""?null:query.get("purch_no"));
            map.put("sup_name",query.get("sup_name")+"");
        }
        if (query.get("purch_type") ==null || query.get("purch_type")==""){
            throw new NullPointerException ("请选择采购类型");
        }
        String  purchType=query.get("purch_type")==null||query.get("purch_type")==""?null:query.get("purch_type").toString();
        List<Map<String, Object>> stayCheckPurchList=qmPurchQcMapper.selectStayCheckPurchInfo(map);
        switch (purchType) {
            case "1":
                for (Map<String, Object> stayCheckPurchParam : stayCheckPurchList) {
                    List<Map<String, Object>> purchQcList = qmPurchQcSubMapper.selectStayCheckPurchDetail(map);
                    stayCheckPurchParam.put("purch_type", "1");
                    stayCheckPurchParam.put("purch_detail_list", purchQcList);
                }
                break;
            case "2":
                for (Map<String, Object> stayCheckPurchParam : stayCheckPurchList) {
                    List<Map<String, Object>> purchQcList = qmPurchQcSubMapper.selectStayCheckPurchDetail(map);
                    stayCheckPurchParam.put("purch_type", null);
                    stayCheckPurchParam.put("purch_detail_list", purchQcList);
                }
                break;
            default:
                break;
        }
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("purch_list", stayCheckPurchList);
        return returnMap;
    }


    private  Map<String,Object>  saveQmPurchQcManager(Map<String,Object>  map,boolean isPgCall) {
        Map<String, Object> returnMap=null;
        if(!isPgCall) {
            Map<String, Object> purchQcInfo = (Map<String, Object>) map.get("purch_qc_info");
            purchQcInfo.put("company_id", map.get("company_id"));
            purchQcInfo.put("company_name", map.get("company_name"));
            purchQcInfo.put("handler_id", map.get("uid"));
            List<Map<String, Object>> purchQcDetailList = (List<Map<String, Object>>)purchQcInfo.get("purch_qc_detail_list");

            Long id = null;
            Boolean  flg=false;
            if (purchQcInfo.containsKey("purch_qc_id")) {
                flg = purchQcInfo.get("purch_qc_id") == null || purchQcInfo.get("purch_qc_id") == "" ? false : true;
            }

            if (flg) {
                // 修改来料质检主表  删除再新增
                this.deletePurchQcBill(purchQcInfo);
                purchQcInfo.put("updated_at", DateUtil.getDateTime());
                qmPurchQcMapper.addIncomingMatCheckMainInfo(purchQcInfo);
                id = Long.valueOf(purchQcInfo.get("id").toString());
            } else {
                // 新增来料质检主表
                Map<String,Object> noMap = createBillNoService.createBillNo(Integer.valueOf(purchQcInfo.get("company_id").toString()),"PQ");
                purchQcInfo.put("purch_qc_no",noMap.get("bill_no").toString());
                purchQcInfo.put("created_at", DateUtil.getDateTime());
                purchQcInfo.put("qc_date", DateUtil.getDateTime());
                qmPurchQcMapper.addIncomingMatCheckMainInfo(purchQcInfo);
                id = Long.valueOf(purchQcInfo.get("id").toString());
            }

            // 待质检的采购明细
            for (Map<String, Object> purchDetailParam : purchQcDetailList) {
                Boolean partFalg = false;
                purchDetailParam.put("purch_qc_id",id);

                // 质检明细id
                if (purchDetailParam.containsKey("purch_qc_detail_id")) {
                    partFalg = purchDetailParam.get("purch_qc_detail_id") == null || purchDetailParam.get("purch_qc_detail_id") == "" ? false : true;
                }
                if (partFalg) {
                    //update——删除再新增、将采购质检的已建单数量冲减
                    purchQcInfo.put("purch_qc_id", purchQcInfo.get("purch_qc_id").toString());
                    List<Map<String,Object>> qtyList = qmPurchQcSubMapper.selectIncomingCheckQtyById(purchQcInfo);
                    for(Map<String,Object>  qtyParam:qtyList) {
                        qtyParam.put("purch_sub_id", qtyParam.get("purch_sub_id"));
                        Map<String, Object> qtyBillParam = qmPurchQcSubMapper.selectPurchQuantityById(qtyParam);
                        Double qcBillQty = Double.valueOf(qtyBillParam.get("qc_bill_qty").toString());
                        if(qtyParam.get("qc_qty")!=null){
                            Double qcQty = Double.valueOf(qtyParam.get("qc_qty").toString());
                            qtyBillParam.put("detail_id", qtyParam.get("purch_sub_id"));
                            qtyBillParam.put("qc_bill_qty", qcBillQty - qcQty);
                            pcPurchOrderSubMapper.updateOrderSubQty(qtyBillParam);
                        }
                    }
                    qmPurchQcSubMapper.deleteByPrimaryKey(Long.valueOf(purchQcInfo.get("purch_qc_id").toString()));
                    purchDetailParam.put("handler_id", map.get("uid"));
                    this.savePurchInfoManager(purchDetailParam,purchQcInfo);
                } else {
                    //save
                    this.savePurchInfoManager(purchDetailParam,purchQcInfo);
                }
            }
            returnMap = qmPurchQcMapper.selectIncomingMatCheckById(id);
        }
        if(isPgCall) {
            Map<String, Object> deliveryQcInfo = (Map<String, Object>) map.get("delivery_qc_info");
            deliveryQcInfo.put("company_id", map.get("company_id"));
            deliveryQcInfo.put("company_name", map.get("company_name"));
            deliveryQcInfo.put("handler_id", map.get("uid"));
            List<Map<String, Object>> deliveryQcDetailList = (List<Map<String, Object>>)deliveryQcInfo.get("delivery_qc_detail_list");

            Long  id=null;
            Map<String,Object> noMap = createBillNoService.createBillNo(Integer.valueOf(deliveryQcInfo.get("company_id").toString()),"PQ");
            deliveryQcInfo.put("purch_qc_no",noMap.get("bill_no").toString());
            deliveryQcInfo.put("created_at", DateUtil.getDateTime());
            deliveryQcInfo.put("qc_date", DateUtil.getDateTime());
            qmPurchQcMapper.addIncomingMatCheckMainInfo(deliveryQcInfo);
            id = Long.valueOf(deliveryQcInfo.get("id").toString());

            for (Map<String, Object> deliveryQcDetailParam : deliveryQcDetailList) {
                //save
                deliveryQcDetailParam.put("purch_qc_id", id);
                this.savePurchInfoManager(deliveryQcDetailParam,deliveryQcInfo);
            }
            returnMap = qmPurchQcMapper.selectIncomingMatCheckById(id);
        }
        return returnMap;
    }

    private  void   savePurchInfoManager(Map<String,Object>  purchDetailParam,Map<String,Object> purchQcInfo){
        // 实时查询
        purchDetailParam.put("purch_sub_id",purchDetailParam.get("purch_sub_id"));
        Map<String,Object>   qtyParam=qmPurchQcSubMapper.selectPurchQuantityById(purchDetailParam);
        Double quantity = Double.valueOf(qtyParam.get("quantity").toString());
        Double qcBillQty = Double.valueOf(qtyParam.get("qc_bill_qty").toString());

        // 本次质检数量
        Double qcQty = Double.valueOf(purchDetailParam.get("qc_qty").toString());
        Double  noQcQty= quantity-qcBillQty;
        if (noQcQty >=qcQty) {
            purchDetailParam.put("updated_at", DateUtil.getDateTime());
            purchDetailParam.put("purch_qc_id", purchDetailParam.get("purch_qc_id"));
            purchDetailParam.put("purch_id", purchQcInfo.get("purch_id"));
            purchDetailParam.put("purch_no", purchQcInfo.get("purch_no"));
            purchDetailParam.put("handler_id", purchQcInfo.get("handler_id"));

            // 新增质检子表数据（根据筛选的采购单号）
            qmPurchQcSubMapper.addIncomingMatCheckDetail(purchDetailParam);
            if (qcBillQty != null){
                purchDetailParam.put("qc_bill_qty", qcBillQty + qcQty);
                purchDetailParam.put("detail_id", purchDetailParam.get("purch_sub_id"));
                pcPurchOrderSubMapper.updateOrderSub(purchDetailParam);
            }
        } else {
            throw new ArithmeticException("采购数量不足！");
        }
    }
    private void deletePurchQcBill(Map<String, Object> map) {
        qmPurchQcMapper.deleteByPrimaryKey(Long.valueOf(map.get("purch_qc_id").toString().trim()));
    }
}
package com.mt.iv.warehouse.service.impl;

import com.mt.common.utils.DateUtil;
import com.mt.common.utils.StringUtils;
import com.mt.iv.common.dao.IvApplyMatBillMapper;
import com.mt.iv.common.dao.IvApplyMatBillSubMapper;
import com.mt.iv.common.dao.IvStoreMapper;
import com.mt.iv.warehouse.service.ApplyMatBillService;
import com.mt.pc.purchase.service.CreateBillNoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Author: Wendy
* @Description:
* @create    2018/1/24 14:39
* @Modified By:
*/    
@Service
public class ApplyMatBillServiceImpl implements ApplyMatBillService{
    @Autowired
    private IvStoreMapper ivStoreMapper;

    @Autowired
    private CreateBillNoService createBillNoService;

    @Autowired
    private IvApplyMatBillMapper  ivApplyMatBillMapper;

    @Autowired
    private IvApplyMatBillSubMapper  ivApplyMatBillSubMapper;


    /**
     * 新增修改领料单
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> addAndUpdateApplyMatBill(Map<String, Object> map) {
        Map<String,Object>  applyMatInfo=(Map<String,Object>)map.get("apply_mat_info");
        applyMatInfo.put("company_id",map.get("company_id"));
        applyMatInfo.put("company_name",map.get("company_name"));
        List<Map<String,Object>>  applyDetailList=(List<Map<String,Object>>)applyMatInfo.get("apply_detail_list");
        applyDetailList.remove("apply_detail_list");

        Boolean flg = false;
        Long id = null;

        if(applyMatInfo.containsKey("apply_id")){
            flg = applyMatInfo.get("apply_id") == null || applyMatInfo.get("apply_id") == "" ? false : true;
        }
        if(flg){
            // 修改人ID
            applyMatInfo.put("handler_id", map.get("uid"));
            applyMatInfo.put("updated_at", DateUtil.getDateTime());
            ivApplyMatBillMapper.updateApplyMatBill(applyMatInfo);
        }else{
            // 新增领料主单
            applyMatInfo.put("handler_id",map.get("uid"));
            applyMatInfo.put("creator_id",map.get("uid"));
            Map<String,Object>   createdNameMap=ivApplyMatBillMapper.selectUserCompanyByUid(applyMatInfo);
            // 创建人ID
            applyMatInfo.put("created_at", DateUtil.getDateTime());
            applyMatInfo.put("creator_name", createdNameMap.get("work_name"));
            // 领料
            if("0".equals(applyMatInfo.get("aplly_type"))){
                applyMatInfo.put("apply_no",createBillNoService.createBillNo(Integer.valueOf(map.get("company_id").toString()), "DA", Long.valueOf(map.get("uid").toString())).get("bill_no").toString());
                ivApplyMatBillMapper.addApplyMatBill(applyMatInfo);
                id=Long.valueOf(applyMatInfo.get("id").toString());
            }
            // 模具
            if("1".equals(applyMatInfo.get("aplly_type"))) {
                applyMatInfo.put("apply_no", createBillNoService.createBillNo(Integer.valueOf(map.get("company_id").toString()), "MA", Long.valueOf(map.get("uid").toString())).get("bill_no").toString());
                ivApplyMatBillMapper.addApplyMatBill(applyMatInfo);
                id = Long.valueOf(applyMatInfo.get("id").toString());
            }
            // 超领
            if("2".equals(applyMatInfo.get("aplly_type"))) {
                applyMatInfo.put("apply_no", createBillNoService.createBillNo(Integer.valueOf(map.get("company_id").toString()), "MA", Long.valueOf(map.get("uid").toString())).get("bill_no").toString());
                ivApplyMatBillMapper.addApplyMatBill(applyMatInfo);
                id = Long.valueOf(applyMatInfo.get("id").toString());
            }
        }

        for(Map<String,Object> objectMap:applyDetailList){
            Boolean partFlg = false;
            objectMap.put("apply_id",applyMatInfo.get("apply_id")==null||applyMatInfo.get("apply_id")==""?id:applyMatInfo.get("apply_id"));
            if(objectMap.containsKey("detail_id")){
                partFlg=objectMap.get("detail_id")==null||objectMap.get("detail_id")==""?false:true;
            }
            if(partFlg){
                if("0".equals(applyMatInfo.get("aplly_type"))) {
                // 修改领料详情单（有Id进入修改）
                objectMap.put("update_at",DateUtil.getDateTime());
                int i=ivApplyMatBillSubMapper.updateApplyMatBillSub(objectMap);
                }

                if("1".equals(applyMatInfo.get("aplly_type"))) {
                    // 实时查询
                    objectMap.put("node_id", objectMap.get("node_id"));
                    Map<String, Object> qtyParam = ivApplyMatBillMapper.selectMatInfoByQty(objectMap);
                    // BOM来源物料总数量
                    Double totalQty = Double.valueOf(qtyParam.get("total_qty").toString());
                    // BOM来源物料的领料数量记录
                    Double sumApplyQty = Double.valueOf(qtyParam.get("applyNum").toString());
                    // 本次申请数量
                    Double applyQty = Double.valueOf(objectMap.get("quantity").toString());
                    Double num = sumApplyQty + applyQty;

                    if (totalQty - num > 0) {
                        objectMap.put("update_at", DateUtil.getDateTime());
                        ivApplyMatBillSubMapper.updateApplyMatBillSub(objectMap);
                    } else {
                        throw new ArithmeticException("总数量不足,不能进行领料！");
                    }
                }
                if("2".equals(applyMatInfo.get("aplly_type"))) {
                    // 修改领料详情单（有Id进入修改）
                    objectMap.put("update_at",DateUtil.getDateTime());
                    int i=ivApplyMatBillSubMapper.updateApplyMatBillSub(objectMap);
                }
            }else{
                // 新增领料详情单,根据总库存量做参考领料,出库再判断领料数量与实时总库存量
                if("0".equals(applyMatInfo.get("aplly_type"))) {
                    objectMap.put("created_at", DateUtil.getDateTime());
                    int i = ivApplyMatBillSubMapper.addApplyMatBillSub(objectMap);
                    objectMap.get("id");
                }

                if("1".equals(applyMatInfo.get("aplly_type"))) {
                    objectMap.put("node_id", objectMap.get("node_id"));
                    Map<String, Object> qtyParam = ivApplyMatBillMapper.selectMatInfoByQty(objectMap);
                    // 如果等于空就新建模具物料需求
                    // BOM来源物料总数量
                    Double totalQty = Double.valueOf(qtyParam.get("total_qty").toString());
                    // 本次申请数量
                    Double applyQty = Double.valueOf(objectMap.get("quantity").toString());

                    // 没建模具物料,新创建限制不能大于总数
                    if(qtyParam.get("applyNum")==null) {
                        if (applyQty < totalQty){
                            objectMap.put("created_at", DateUtil.getDateTime());
                            ivApplyMatBillSubMapper.addApplyMatBillSub(objectMap);
                            objectMap.get("id");
                        }else{
                            throw new ArithmeticException("总数量不足,不能进行领料！");
                        }
                    }else {
                        // BOM来源物料的领料数量记录
                        Double sumApplyQty = Double.valueOf(qtyParam.get("applyNum").toString());
                        Double num = totalQty - sumApplyQty;
                        if (num > 0) {
                            objectMap.put("created_at", DateUtil.getDateTime());
                            ivApplyMatBillSubMapper.addApplyMatBillSub(objectMap);
                            objectMap.get("id");
                        } else {
                            throw new ArithmeticException("总数量不足,不能进行领料！");
                        }
                    }
                }
                if("2".equals(applyMatInfo.get("aplly_type"))) {
                    objectMap.put("created_at", DateUtil.getDateTime());
                    int i = ivApplyMatBillSubMapper.addApplyMatBillSub(objectMap);
                    objectMap.get("id");
                }
            }
        }

        Map<String, Object> returnMap = new HashMap<String,Object>();
        if (flg) {
            returnMap = ivApplyMatBillMapper.selectApplyMatBillById(Long.valueOf(applyMatInfo.get("apply_id").toString().trim()));
        } else {
            returnMap = ivApplyMatBillMapper.selectApplyMatBillById(id);
        }
        return returnMap;
    }

    /**
     * 查询领料单列表
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectApplyMatBillList(Map<String, Object> map) {
        if(map.containsKey("query")) {
            Map<String, Object> query = (Map<String, Object>) map.get("query");
            boolean  flg=query.get("approve_step")!=null&&query.get("approve_step")!=""&&query.get("aplly_type")!=null&&query.get("aplly_type")!="";
            if(flg){
                map.put("approve_step",query.get("approve_step"));
                map.put("aplly_type",query.get("aplly_type"));
            }
        }
        int totalCount  = ivApplyMatBillMapper.totalCount(map);
        map.put("page", ((Integer) map.get("curr_page") - 1) * (Integer) map.get("page_size"));
        List<Map<String, Object>> applyMatList =ivApplyMatBillMapper.selectApplyMatBillList(map);
        if(applyMatList.size()<=0){
            return StringUtils.pageList(map,"apply_mat_list",0);
        }
        Map<String, Object> page = StringUtils.page(map, applyMatList,"apply_mat_list", totalCount);
        return page;
    }


    /**
     * 查询领料单详情
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectApplyMatBillDetail(Map<String, Object> map) {
        Map<String, Object> query = (Map<String, Object>) map.get("query");
        Integer applyId = Integer.valueOf(query.get("apply_id").toString());
        Map<String, Object> approveParam = ivApplyMatBillMapper.selectApplyMatBill(applyId);
        List<Map<String, Object>> approveList = ivApplyMatBillSubMapper.selectApplyMatBillSubDetail(Long.valueOf(applyId));
        for(Map<String,Object>  stockParam:approveList){
            Map<String,Object>  companyParam=ivApplyMatBillMapper.selectCompanyId(applyId);
            stockParam.put("company_id",companyParam.get("company_id"));
            stockParam.put("mat_id",stockParam.get("mat_id"));
            Map<String,Object>  storeParam=ivStoreMapper.selectStoreMatDesc(stockParam);
            if(storeParam==null){
                stockParam.put("curr_stock_qty",null);
            }else {
                stockParam.put("curr_stock_qty", storeParam.get("stock_qty"));
            }
            // undeal_qty
            Map<String,Object>   QtyParam=ivApplyMatBillMapper.selectMatInfoByQty(stockParam);
            if(QtyParam==null){
                stockParam.put("undeal_qty",null);
            }else{
                Double totalQty = Double.valueOf(QtyParam.get("total_qty").toString());
                Double sumApplyQty = Double.valueOf(QtyParam.get("applyNum").toString());
                stockParam.put("undeal_qty",totalQty-sumApplyQty);
            }
        }
        approveParam.put("apply_detail_list", approveList);
        Map<String, Object> returnMap = new HashMap<String,Object>();
        returnMap.put("apply_mat_info", approveParam);
        return returnMap;

    }

    /**
     * 领料单删除
     * @param map
     * @return
     */
    @Override
    public Integer updateApplyBill(Map<String, Object> map) {
        Integer  i;
        List<Map<String,Object>>  approveList=ivApplyMatBillMapper.selectApproveStepList(Long.valueOf(map.get("apply_id").toString()));
        if(approveList.size()>0){
            i=ivApplyMatBillMapper.updateApplyBill(Long.valueOf(map.get("apply_id").toString()));
        }else{
            throw   new  NullPointerException("此领料单不能删除！");
        }
        return i;
    }
}
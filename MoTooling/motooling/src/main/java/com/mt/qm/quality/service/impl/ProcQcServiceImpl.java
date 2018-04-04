package com.mt.qm.quality.service.impl;

import com.mt.common.utils.DateUtil;
import com.mt.common.utils.StringUtils;
import com.mt.pc.purchase.service.CreateBillNoService;
import com.mt.pm.common.model.PmPgMember;
import com.mt.pm.production.service.PmConfirmWorkTimeService;
import com.mt.qm.common.dao.QmProcQcMapper;
import com.mt.qm.common.dao.QmProcQcSubMapper;
import com.mt.qm.quality.service.ProcQcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:Wujie
 * @Date: Create in 2018-01-12 10:05
 * @Description:
 * @Modified By:
 */

@Service
@SuppressWarnings("unchecked")
public class ProcQcServiceImpl implements ProcQcService {

    @Autowired
    private QmProcQcMapper qmProcQcMapper;

    @Autowired
    private QmProcQcSubMapper qmProcQcSubMapper;

    @Autowired
    private PmConfirmWorkTimeService pmConfirmWorkTimeService;

    @Autowired
    private CreateBillNoService createBillNoService;

    @Override
    public Map<String, Object> selectProcQcList(Map<String, Object> map) {
        map.put("page",((Integer) map.get("curr_page") - 1) * (Integer) map.get("page_size"));
        Map<String,Object> query = (Map<String,Object>) map.get("query");
        map.put("approveStepArray",query.get("approveStepArray"));
        List<Map<String,Object>> procQcList = qmProcQcMapper.selectProcQcList(map);
        if (procQcList.size() <= 0){
            return StringUtils.pageList(map, "proc_qc_list", 0);
        }
        Integer totalCount = qmProcQcMapper.selectTotalCount(map);
        Map<String,Object> page;
        page = StringUtils.page(map,procQcList,"proc_qc_list",totalCount);
        return page;
    }

    @Override
    public Map<String, Object> selectProcQcDetail(Map<String, Object> map) {
        Map<String,Object> queryMap = (Map<String,Object>) map.get("query");
        List<Map<String,Object>> procList = new ArrayList<>();
        Integer popQcId = Integer.valueOf(queryMap.get("pop_qc_id").toString().trim());
        Map<String,Object> procQcMap = qmProcQcMapper.selectProcQcDetail(popQcId);
        procQcMap.put("inspector",procQcMap.get("creator_name") == null || procQcMap.get("creator_name") == "" ? null : procQcMap.get("creator_name").toString().trim());
        List<Map<String,Object>> procQcInfo = qmProcQcMapper.selectProcQcAndProdOrderDetail(popQcId);
        for (Map<String,Object> procMap : procQcInfo) {
            Long pgId = Long.valueOf(procMap.get("pg_id").toString().trim());
            Long workerId = Long.valueOf(procMap.get("worker_id").toString().trim());
            PmPgMember pmPgMember = pmConfirmWorkTimeService.selectByPgIdAndMemberId(pgId,workerId);
            procMap.put("worker_name",pmPgMember.getMemberName());
            procList.add(procMap);
        }
        procQcMap.put("prod_order_list",procList);
        Map<String,Object> returnMap = new HashMap<>(16);
        returnMap.put("proc_qc_info",procQcMap);
        return returnMap;
    }

    @Override
    public Map<String, Object> selectNoProcQcList(Map<String, Object> map) {
        Map<String,Object> query = (Map<String,Object>) map.get("query");
        List<Map<String,Object>> prodOrderList = qmProcQcMapper.selectProcQcPqiList(query);
        List<Map<String,Object>> procList = new ArrayList<>();
        for (Map<String,Object> procMap : prodOrderList) {
            Long pgId = Long.valueOf(procMap.get("pg_id").toString().trim());
            Long workerId = Long.valueOf(procMap.get("worker_id").toString().trim());
            PmPgMember pmPgMember = pmConfirmWorkTimeService.selectByPgIdAndMemberId(pgId,workerId);
            procMap.put("worker_name",pmPgMember.getMemberName());
            Long qcBillQty = Long.valueOf(procMap.get("qc_bill_qty").toString().trim());
            Long doneQty = Long.valueOf(procMap.get("done_qty").toString().trim());
            procMap.put("pc_wait_qty",doneQty - qcBillQty);
            procMap.put("no_qualified_qty","");
            procMap.put("qc_qty","");
            procMap.put("qualified_qty","");
            procMap.put("quality_loss","");
            procMap.put("scrap_qty","");
            procMap.put("special_qty","");
            procMap.put("unusual_pic","");
            procMap.put("unusual_reason","");
            procList.add(procMap);
        }
        Map<String,Object> procQcInfo = new HashMap<>();
        procQcInfo.put("prod_order_info",procList);
        Map<String,Object> returnMap = new HashMap<>(16);
        returnMap.put("prod_qc_info",procQcInfo);
        return returnMap;
    }

    @Override
    public Map<String, Object> addAndUpdateProcQc(Map<String, Object> map) {
        Map<String,Object> procQcInfo = (Map<String,Object>) map.get("proc_qc_info");
        procQcInfo.put("company_id",map.get("company_id").toString().trim());
        procQcInfo.put("uid",map.get("uid").toString().trim());
        List<Map<String,Object>> prodOrderList = (List<Map<String,Object>>) procQcInfo.get("prod_order_list");
        //根据工序id获取定单工序已完成数量
        Boolean partFlg = false;
        Boolean flg = false;
        Integer id = null;
        if(procQcInfo.containsKey("pop_qc_id")){
            partFlg = procQcInfo.get("pop_qc_id") == null || procQcInfo.get("pop_qc_id") == "" ? false :true;
        }
        if(partFlg){
            //修改工序质检单
            procQcInfo.put("updated_at", DateUtil.getDateTime());
            procQcInfo.put("creator_id",map.get("uid") == null || map.get("uid") == "" ? null : map.get("uid").toString().trim());
            procQcInfo.put("creator_name",procQcInfo.get("inspector") == null || procQcInfo.get("inspector") == "" ? null : procQcInfo.get("inspector").toString().trim());
            qmProcQcMapper.updateProcQc(procQcInfo);
        }else {
            //生成工序质检单号
            Map<String,Object> noMap = createBillNoService.createBillNo(Integer.valueOf(procQcInfo.get("company_id").toString()),"PQ");
            procQcInfo.put("pop_qc_no",noMap.get("bill_no").toString());
            procQcInfo.put("creator_id",map.get("uid") == null || map.get("uid") == "" ? null : map.get("uid").toString().trim());
            procQcInfo.put("creator_name",procQcInfo.get("inspector") == null || procQcInfo.get("inspector") == "" ? null : procQcInfo.get("inspector").toString().trim());
            qmProcQcMapper.addProcQc(procQcInfo);
            id = Integer.valueOf(procQcInfo.get("id").toString());
        }

        for (Map<String,Object> objectMap : prodOrderList){
            Integer qcQty = Integer.valueOf(objectMap.get("qc_qty").toString().trim());
            Integer popId = Integer.valueOf(objectMap.get("pop_id").toString().trim());
            objectMap.put("pop_qc_id",procQcInfo.get("pop_qc_id") == null || procQcInfo.get("pop_qc_id") == "" ? id : procQcInfo.get("pop_qc_id"));
            if (objectMap.containsKey("pop_qc_sub_id")){
                flg = objectMap.get("pop_qc_sub_id") == null || objectMap.get("pop_qc_sub_id") == "" ? false : true;
            }
            if(flg){
                //根据工序质检单子表id查询出质检数量
                Integer popQcSubId = Integer.valueOf(objectMap.get("pop_qc_sub_id").toString().trim());
                Map<String,Object> doneQtyMap = qmProcQcMapper.selectProcQtyByPopQcSubId(popQcSubId);
                Integer oldQcQty = Integer.valueOf(doneQtyMap.get("qc_qty").toString().trim());
                Integer doneQty = Integer.valueOf(doneQtyMap.get("done_qty").toString().trim());
                Integer qcBillQty = Integer.valueOf(doneQtyMap.get("qc_bill_qty").toString().trim());
                if(doneQty - oldQcQty - qcBillQty >= qcQty){
                    objectMap.put("updated_at",DateUtil.getDateTime());
                    qmProcQcSubMapper.updateProcQcSub(objectMap);
                    //修改工序质检单成功后修改已建质检单数量
                    objectMap.put("qc_bill_qty",qcBillQty - oldQcQty + qcQty);
                    qmProcQcMapper.updateQcBillQty(objectMap);
                }else {
                    throw new IllegalArgumentException ("待质检数量不足");
                }
            }else {
                //新增工序质检单子表
                Map<String,Object> doneQtyMap = qmProcQcMapper.selectProcQtyByPopId(popId);
                Integer doneQty = Integer.valueOf(doneQtyMap.get("done_qty").toString().trim());
                Integer qcBillQty = Integer.valueOf(doneQtyMap.get("qc_bill_qty").toString().trim());
                if(doneQty - qcBillQty >= qcQty){
                    objectMap.put("created_at",DateUtil.getDateTime());
                    qmProcQcSubMapper.addProcQcSub(objectMap);
                    //新增工序质检单成功后修改已建质检单数量
                    objectMap.put("qc_bill_qty",qcBillQty + qcQty);
                    qmProcQcMapper.updateQcBillQty(objectMap);
                }else {
                    throw new IllegalArgumentException ("待质检数量不足");
                }
            }
        }
        Map<String,Object> procQcMap;
        Integer popQcId;
        if(partFlg){
            popQcId = Integer.valueOf(procQcInfo.get("pop_qc_id").toString());
        }else {
            popQcId = id;
        }
        procQcMap = qmProcQcMapper.selectPopQcIdAndPopQcNo(popQcId);
        Map<String,Object> returnMap = new HashMap<>(16);
        returnMap.put("proc_qc_info",procQcMap);
        return returnMap;
    }

    @Override
    public Boolean deleteProcQc(Map<String,Object> map){
        Map<String,Object> popQcMap = new HashMap<>(16);
        popQcMap.put("pop_qc_id",map.get("pop_qc_id"));
        popQcMap.put("update_at",DateUtil.getDateTime());
        Boolean bool = qmProcQcMapper.deleteProcQc(popQcMap);
        if(bool){
            bool = qmProcQcSubMapper.deleteProcQcSub(popQcMap);
            //删除工序质检单成功后修改已建质检单数量
            Integer popQcId = Integer.valueOf(popQcMap.get("pop_qc_id").toString());
            Integer qcQty = qmProcQcSubMapper.selectQcQtyByPopQcId(popQcId);
            Integer qcBillQty = qmProcQcSubMapper.selectQcBillQtyByPopQcId(popQcId);
            Map<String,Object> qcBillQtyMap = new HashMap<>(16);
            qcBillQtyMap.put("pop_id",popQcId);
            qcBillQtyMap.put("qc_bill_qty",qcBillQty - qcQty);
            qmProcQcMapper.updateQcBillQty(qcBillQtyMap);
            return bool;
        }
        return bool;
    }
}

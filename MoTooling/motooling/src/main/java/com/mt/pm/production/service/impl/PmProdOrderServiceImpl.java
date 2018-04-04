package com.mt.pm.production.service.impl;

import com.mt.bm.common.dao.BomNodeMapper;
import com.mt.common.utils.StringUtils;
import com.mt.pc.purchase.service.CreateBillNoService;
import com.mt.pm.common.dao.PmProdOrderMapper;
import com.mt.pm.common.dao.PmProdOrderSubMapper;
import com.mt.pm.common.dao.PmProdReqMapper;
import com.mt.pm.common.model.PmProdOrder;
import com.mt.pm.common.model.PmProdOrderSub;
import com.mt.pm.common.model.PmProdReq;
import com.mt.pm.production.service.PmProdOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PmProdOrderServiceImpl implements PmProdOrderService{

    @Autowired
    private PmProdOrderMapper pmProdOrderMapper;
    @Autowired
    private PmProdOrderSubMapper pmProdOrderSubMapper;
    @Autowired
    private PmProdReqMapper pmProdReqMapper;
    @Autowired
    private CreateBillNoService createBillNoService;
    @Autowired
    private BomNodeMapper bomNodeMapper;

    @Override
    public int deleteProdOrderByPrimaryKey(Long id) {
        PmProdOrder record=pmProdOrderMapper.selectProdOrderByPrimaryKey(id);
        //判断是否可以删除

        //逻辑删除
        Map<String, Object> subDelete=new HashMap<>();
        subDelete.put("poId",id);
        subDelete.put("updatedAt",new Date());
        pmProdOrderSubMapper.deleteProdOrderSub(subDelete);
        PmProdOrder recordDelete=new PmProdOrder();
        recordDelete.setId(id);
        recordDelete.setStatus("0");
        recordDelete.setUpdatedAt(new Date());
        pmProdOrderMapper.updateByPrimaryKeySelective(recordDelete);
        //增加生产需求
        PmProdReq req = pmProdReqMapper.selectByPrimaryKey(record.getReqId());
        req.setUndealQty(req.getUndealQty()+record.getProdQty());
        req.setUpdatedAt(new Date());
        pmProdReqMapper.updateByPrimaryKeySelective(req);
        return 0;
    }

    @Override
    public Long createOrUpdateProdOrder(PmProdOrder record, List<PmProdOrderSub> records) {
        if (record.getId()==null){
            //生成生产定单
            record.setCreatedAt(new Date());
            record.setUpdatedAt(new Date());
            record.setStatus("1");
            pmProdOrderMapper.insert(record);
            PmProdOrderSub sub;
            for(int i=0;i<records.size();i++){
                sub=records.get(i);
                sub.setId(null);
                sub.setPoId(record.getId());
                sub.setCreatedAt(new Date());
                sub.setUpdatedAt(new Date());
                sub.setStatus("1");
                pmProdOrderSubMapper.insert(sub);
            }
            //扣减生产需求
            PmProdReq req = pmProdReqMapper.selectByPrimaryKey(record.getReqId());
            req.setUndealQty(req.getUndealQty()-record.getProdQty());
            req.setUpdatedAt(new Date());
            pmProdReqMapper.updateByPrimaryKeySelective(req);
            //生成生产需求日志？
        }else {
            //修改生产定单
            PmProdOrder oldInfo = pmProdOrderMapper.selectByPrimaryKey(record.getId());
            List<PmProdOrderSub> subList = pmProdOrderSubMapper.selectByProdOrderId(record.getId());
            record.setUpdatedAt(new Date());
            pmProdOrderMapper.updateByPrimaryKeySelective(record);
            PmProdOrderSub sub;
            String subId = "";
            for (int i = 0; i < subList.size(); i++) {
                subId += subList.get(i).getId().toString() + ",";
            }
            for (int i = 0; i < records.size(); i++) {
                sub = records.get(i);
                if (sub.getId() == null) {
                    sub.setPoId(record.getId());
                    sub.setCreatedAt(new Date());
                    sub.setUpdatedAt(new Date());
                    sub.setStatus("1");
                    pmProdOrderSubMapper.insert(sub);
                } else {
                    subId = subId.replace(sub.getId() + ",", "");
                    sub.setUpdatedAt(new Date());
                    pmProdOrderSubMapper.updateByPrimaryKeySelective(sub);
                }
            }
            //逻辑删除页面删除的工序
            if (subId!=null&&!subId.equals("")){
                Map<String, Object> subDelete=new HashMap<>();
                subDelete.put("poId",record.getId());
                subDelete.put("procId",subId.substring(0, subId.length()-1));
                subDelete.put("updatedAt",new Date());
                pmProdOrderSubMapper.deleteProdOrderSub(subDelete);
            }


            //扣减或增加生产需求
            if(!record.getProdQty().equals(oldInfo.getProdQty())) {
                PmProdReq req = pmProdReqMapper.selectByPrimaryKey(record.getReqId());
                req.setId(record.getReqId());
                req.setUndealQty(req.getUndealQty()-(record.getProdQty() - oldInfo.getProdQty()));
                req.setUpdatedAt(new Date());
                pmProdReqMapper.updateByPrimaryKeySelective(req);
                //生成生产需求日志？
            }
        }
        return record.getId();
    }

    @Override
    public PmProdOrder selectPmProdOrderByPrimaryKey(Long id) {
        PmProdOrder pmProdOrder = pmProdOrderMapper.selectProdOrderByPrimaryKey(id);
        return pmProdOrder;
    }

    @Override
    public Map<String, Object> selectProdOrderByConditions(Map<String, Object> map) {
        map.put("page", ((Integer) map.get("curr_page") - 1) * (Integer) map.get("page_size"));
        Integer totalCount = pmProdOrderMapper.selectProdOrderByConditionCount(map);
        List<Map<String, Object>> list = pmProdOrderMapper.selectProdOrderByCondition(map);
        if(list.size()<=0){
            return StringUtils.pageList(map,"prod_info",0);
        }
        Map<String, Object> page = StringUtils.page(map, list, "prod_info", totalCount);
        return page;
    }

    @Override
    public int deleteProdOrderSubByPrimaryKey(Long id) {
        return pmProdOrderSubMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(PmProdOrderSub record) {
        return pmProdOrderSubMapper.insert(record);
    }

    @Override
    public PmProdOrderSub selectPmProdOrderSubByPrimaryKey(Long id) {
        return pmProdOrderSubMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateProdOrderSubByPrimaryKeySelective(PmProdOrderSub record) {
        return pmProdOrderSubMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<PmProdOrderSub> selectProdOrderSubs(Long prodOrderId) {
        return pmProdOrderSubMapper.selectByProdOrderId(prodOrderId);
    }

    @Override
    public Map<String, Object> selectProdOrderSubByConditions(Map<String, Object> map) {
        return null;
    }

    @Override
    public PmProdOrder schedule(PmProdOrder record, List<PmProdOrderSub> records) {
        return null;
    }

    @Override
    public boolean generatedProdOrder(PmProdOrder record, List<PmProdOrderSub> records) {
        PmProdOrder oldPO=pmProdOrderMapper.selectProdOrderByPrimaryKey(record.getId());
        if("".equals(record.getPoNo())) {
            if (oldPO.getPoNo() == null || oldPO.getPoNo().equals("")) {
                //获取生产定单流水号
                String pono = createBillNoService.createBillNo(Integer.parseInt(record.getCompanyId().toString()), "PO", Long.valueOf(0)).get("bill_no").toString();
                record.setPoNo(pono);
            }
        }
        //修改生产定单
        record.setPoStatus("2");
        record.setUpdatedAt(new Date());
        pmProdOrderMapper.updateByPrimaryKeySelective(record);
        PmProdOrderSub sub;
        for (int i = 0; i < records.size(); i++) {
            sub = records.get(i);
            sub.setPopStatus("2");
            sub.setUpdatedAt(new Date());
            pmProdOrderSubMapper.updateByPrimaryKeySelective(sub);
        }
        return true;
    }

    public List<Map<String, Object>> selectProdOrderByPBomNode(Long id){
        Map<String, Object> map=setCondition(id);
        if(null==map) {
            return null;
        }
        return pmProdOrderMapper.selectProdOrderByPBomNode(map);
    }

    @Override
    public List<Map<String, Object>> selectBomSNodes(Long id){
        Map<String, Object> map=setCondition(id);
        if(null==map) {
            return null;
        }
        return bomNodeMapper.selectSBomNodes(map);
    }
    @Override
    public Map<String, Object> selectBomPNode(Long id){
        Map<String, Object> map=setCondition(id);
        if(null==map) {
            return null;
        }
        return bomNodeMapper.getBomPNode(map);
    }

    private Map<String,Object> setCondition(Long id)
    {
        PmProdOrder pmProdOrder=pmProdOrderMapper.selectByPrimaryKey(id);
        Map<String, Object> map = new HashMap<>();
        map.put("bom_id",pmProdOrder.getBomId().toString());
        map.put("mat_id",pmProdOrder.getMatId().toString());
        map.put("node_pos",pmProdOrder.getNodePos().toString());
        map = bomNodeMapper.getBomNodeByPos(map);
        return map;
    }
}

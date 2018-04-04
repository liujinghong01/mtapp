package com.mt.pc.purchase.service.impl;

import com.mt.common.utils.DateUtil;
import com.mt.common.utils.StringUtils;
import com.mt.pc.common.dao.PcRequestMapper;
import com.mt.pc.common.dao.PcRequestSubMapper;
import com.mt.pc.purchase.service.CreateBillNoService;
import com.mt.pc.purchase.service.PurchaseRequisitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PurchaseRequisitionServiceImpl implements PurchaseRequisitionService {
    @Autowired
    private PcRequestMapper pcRequestMapper;

    @Autowired
    private PcRequestSubMapper pcRequestSubMapper;

    @Autowired
    private CreateBillNoService createBillNoService;
    /**
     * 新增修改请购单
     *
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> addAndupdateModefy(Map<String, Object> map) {
        Map<String, Object> headInfo = (Map<String, Object>) map.get("head_info");
        headInfo.put("companyId",map.get("companyId"));
        headInfo.put("companyName",map.get("companyName"));
        List<Map<String, Object>> lineList = (List<Map<String, Object>>) headInfo.get("line_list");
        headInfo.remove("line_list");
        Boolean flg = false;
        Boolean partFlg = false;
        Long id = null;
        if (headInfo.containsKey("request_id")) {
            flg = headInfo.get("request_id") == null || headInfo.get("request_id") == "" ? false : true;
        }
       Map<String,Object> coinMap =  pcRequestMapper.getCoin(Integer.valueOf(map.get("companyId").toString()));
        headInfo.put("coin",coinMap.get("coin"));
        headInfo.put("coin_name",coinMap.get("coin_name"));
        headInfo.put("handler_id",map.get("uid"));
        if (flg) {
            //修改 请购单
            headInfo.put("updated_at", DateUtil.getDateTime());
            pcRequestMapper.updeteRequestModify(headInfo);
        } else {
            //新增
            headInfo.put("created_at", DateUtil.getDateTime());
            headInfo.put("request_no", createBillNoService.createBillNo(Integer.valueOf(map.get("companyId").toString()),"PR",Long.valueOf(map.get("uid").toString())).get("bill_no"));
            pcRequestMapper.addRequestModify(headInfo);
            id = Long.valueOf(headInfo.get("id").toString());
        }
        for (Map<String, Object> objectMap : lineList) {
            objectMap.put("request_id", headInfo.get("request_id") == null || headInfo.get("request_id") == "" ? id : headInfo.get("request_id"));
            if (objectMap.containsKey("line_id")) {
                partFlg = objectMap.get("line_id") == null || objectMap.get("line_id") == "" ? false : true;
            }
            if (partFlg) {
                //修改 请购单子表
                objectMap.put("updated_at", DateUtil.getDateTime());
                Long i = pcRequestSubMapper.updeteRequestSubModify(objectMap);
            } else {
                //新增
                objectMap.put("created_at", DateUtil.getDateTime());
                Long i = pcRequestSubMapper.addRequestSsubModify(objectMap);
                objectMap.get("id");
            }
        }
        Map<String, Object> returnMap = new HashMap<>();
        if (flg) {
            returnMap = pcRequestMapper.selectIdAndNo(Long.valueOf(headInfo.get("request_id").toString()));
        } else {
            returnMap = pcRequestMapper.selectIdAndNo(id);
        }
        return returnMap;
    }

    /**
     * 删除请购单
     * @param requestId
     * @return
     */
    @Override
    public Boolean deleteRequest(Long requestId) {
        Boolean b =pcRequestMapper.deleteRequest(requestId);
        if(b){
           pcRequestSubMapper.deleteRequest(requestId);
        }
        return b;
    }

    @Override
    public Boolean deleteRequestLine(List<Long> lineIds) {
        return pcRequestSubMapper.deleteRequestLine(lineIds);
    }

    /**
     * 查询请购单
     */
    @Override
    public Map<String, Object> queryRequest(Long requestId) {
        Map<String, Object> map =  pcRequestMapper.queryRequest(requestId);
        List<Map<String, Object>> mapList = pcRequestSubMapper.queryRequestSub(requestId);
        //查询当前存库量
        for(Map<String,Object>  matMap:mapList){
            matMap.put("mat_id",matMap.get("mat_id"));
            Map<String,Object>  matIdMap=pcRequestSubMapper.selectCurrStockQty(matMap);
            if(matIdMap==null){
                matMap.put("curr_stock_qty",0);
            }else {
                matMap.put("curr_stock_qty", matIdMap.get("curr_stock_qty"));
            }
        }
        map.put("line_list",mapList);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("head_info",map);
        return returnMap;
    }

    /**
     * 查询请购单列表
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> queryRequestList(Map<String, Object> map) {
        if(map.containsKey("query")){
            Map<String, Object> param = (Map<String, Object>) map.get("query");
          if(param.get("approve_step")!=null && param.get("approve_step")!=""){
              map.put("approve_step",param.get("approve_step"));
            }
        }
        map.put("page", ((Integer) map.get("curr_page") - 1) * (Integer) map.get("page_size"));
        List<Map<String, Object>> requesList= pcRequestMapper.selectRequesList(map);
        if(requesList.size()<=0){
            return StringUtils.pageList(map,"request_list",0);
        }
        Integer totalCount = pcRequestMapper.totalCount(map);
        Map<String, Object> page = StringUtils.page(map, requesList, "request_list", totalCount);
        return page;
    }

    /**
     * 参考请购单
     * @return
     */
    @Override
    public Map<String, Object> addRequestRefurHis(Map<String, Object> param) {
        Map<String, Object> map = queryRequest(Long.valueOf(param.get("requestIdHis").toString()));
        Map<String, Object> headInfo  = (Map<String, Object>) map.get("head_info");
        headInfo.put("companyName",param.get("companyName"));
        headInfo.remove("request_id");
        headInfo.put("approve_step","step10");
        headInfo.put("approve_sugg","");
        headInfo.put("handler_id",param.get("uid"));
        List<Map<String, Object>> list = (List<Map<String, Object>>) headInfo.get("line_list");
        for (Map<String, Object> objectMap:list) {
            objectMap.remove("line_id");
        }
        map.put("companyId",param.get("companyId"));
        map.put("uid",param.get("uid"));
        return addAndupdateModefy(map);
    }


    /**
     * 新增修改请购单行明细信息
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> addAndUpdateModifyLine(Map<String, Object> map) {
        List<Map<String, Object>> paramList = (List<Map<String, Object>>) map.get("line_list");
        Boolean partFlg = false;
        List<Integer> intLis = new ArrayList<>();
        for (Map<String, Object> objectMap:paramList){
            objectMap.put("request_id",map.get("request_id"));
            if (objectMap.containsKey("line_id")) {
                partFlg = objectMap.get("line_id") == null || objectMap.get("line_id") == "" ? false : true;
            }
            if (partFlg) {
                //修改 请购单子表
                objectMap.put("updated_at", DateUtil.getDateTime());
                Long i = pcRequestSubMapper.updeteRequestSubModify(objectMap);
                intLis.add((Integer) objectMap.get("line_id"));
            } else {
                //新增
                objectMap.put("created_at", DateUtil.getDateTime());
                Long i = pcRequestSubMapper.addRequestSsubModify(objectMap);
                intLis.add((Integer) objectMap.get("id"));
            }
        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("line_list",intLis);
        return returnMap;
    }
}

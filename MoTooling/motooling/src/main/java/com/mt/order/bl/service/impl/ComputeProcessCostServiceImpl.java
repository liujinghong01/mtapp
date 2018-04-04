package com.mt.order.bl.service.impl;

import com.mt.common.utils.StringUtils;
import com.mt.order.bl.service.ComputeProcessCostService;
import com.mt.order.common.dao.ComputeCostMapper;
import com.mt.order.common.dao.ComputeProcessCostMapper;
import com.mt.order.common.model.ComputeProcessCost;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ComputeProcessCostServiceImpl implements ComputeProcessCostService {
    private final static Logger log = Logger.getLogger(ComputeProcessCostServiceImpl.class);
    @Autowired
    private ComputeProcessCostMapper computeProcessCostMapper;
    @Autowired
    private ComputeCostMapper computeCostMapper;

    /**
     * E122 新增/修改加工费明细
     * @param map
     * @return
     */
    @Override
    public Boolean addAndUpdate(Map<String, Object> map) {
        List<Map<String,Object>> list = (List<Map<String, Object>>) map.get("process_list");
        int i =0;
        for (Map<String,Object> mapList:list) {
            HashMap<String, Object> hashMap = new HashMap<>();
            for (Object o: mapList.entrySet()) {
                String substring = o.toString().substring(0, o.toString().indexOf("="));
                hashMap.put(substring,mapList.get(substring));
            }
            hashMap.put("compute_id",map.get("compute_id"));
            if(StringUtils.isEmpty(hashMap.get("id").toString())){
             i= computeProcessCostMapper.addProcessCost(hashMap);
            }else{
               i= computeProcessCostMapper.updateProcessCost(hashMap);
            }
        }
        if(i>0){
            Double d = computeProcessCostMapper.selectSumProcessCost(Integer.valueOf(map.get("compute_id").toString()));
            if(d!=null&&d>=0) {
                map.put("process_cost", d);
                computeCostMapper.updateStuffCost(map);
            }
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Map<String, Object> selectDetail(Long computeId) {
        List<ComputeProcessCost> entityList = computeProcessCostMapper.selectDetail(computeId);
        List<Map<String, Object>> mapList = new ArrayList<>();
        if(entityList.size()>0) {
            Iterator<ComputeProcessCost> iterator = entityList.iterator();
            while (iterator.hasNext()) {
                mapList.add(JSONObject.fromObject(iterator.next()));
            }
            mapList = StringUtils.camelToUnderline(mapList);
        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("process_list",mapList);
        return returnMap;
    }

    @Override
    public Boolean delProcess(Map<String, Object> map) {
        List<Integer> list = (List<Integer>) map.get("processItemIds");
        int i = computeProcessCostMapper.delProcess(list);
        if (i > 0) {
            Double d = computeProcessCostMapper.selectSumProcessCost(Integer.valueOf(map.get("compute_id").toString()));
            if(d != null) {
                map.put("process_cost", d);
                computeCostMapper.updateStuffCost(map);
            }
            return true;
        } else {
            return false;

        }

    }
}

package com.mt.order.bl.service.impl;

import com.mt.order.bl.service.ComputePartCostService;
import com.mt.order.common.dao.ComputeCostMapper;
import com.mt.order.common.dao.ComputePartCostMapper;
import com.mt.order.common.dao.ComputePartCostSubMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ComputePartCostServiceImpl implements ComputePartCostService {
    private final static Logger log = Logger.getLogger(ComputeServiceImpl.class);
    @Autowired
    private ComputePartCostMapper computePartCostMapper;
    @Autowired
    private ComputePartCostSubMapper computePartCostSubMapper;
    @Autowired
    private ComputeCostMapper computeCostMapper;

    /**
     * E118 新增/修改 零部件
     *
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> addAndUpdateComputePartCost(Map<String, Object> map)  throws IllegalAccessException{
        List<Map<String, Object>> partList = (List<Map<String, Object>>) map.get("part_list");
        List<Integer> returnList = new ArrayList();
        for (Map<String, Object> objMap : partList) {
            List<Map<String, Object>> subPartList = null;
            if (objMap.containsKey("sub_part_list")) {
                subPartList = (List<Map<String, Object>>) objMap.get("sub_part_list");
                objMap.remove("sub_part_list");
            } else {
                subPartList = null;
            }
            //判断part_id 是否为空  为空增加， 不为空修改
            Boolean partFlg = false;
            if (objMap.containsKey("part_id")) {
                partFlg = objMap.get("part_id") == null || objMap.get("part_id") == "" ? false : true;
            }
            if(0<computePartCostMapper.selectPartNameRepea(objMap.get("part_name"),objMap.get("part_id"),map.get("compute_id"))){
                throw  new IllegalArgumentException("零部件名称重复");
            }

            Map<String, Object> subMap = new HashMap<>();
            Map<String, Object> partMap = new HashMap<>();
            for (Object o : objMap.keySet()) {
                partMap.put(o.toString(), objMap.get(o));
            }
            partMap.put("compute_id", map.get("compute_id"));
            if (partFlg) {
                //修改
                computePartCostMapper.updatePartCost(partMap);
                returnList.add(Integer.valueOf(partMap.get("part_id").toString()));
                subMap.put("compute_part_cost_id", objMap.get("part_id"));
            } else {
                //新增
                int i = computePartCostMapper.addPartCost(partMap);
                Object id = partMap.get("id");
                System.out.println(id);
                subMap.put("compute_part_cost_id", id);
                returnList.add(Integer.valueOf(id.toString()));
            }
            if (subPartList != null) {
                for (Map<String, Object> sub : subPartList) {
                    for (Object oSub : sub.keySet()) {
                        subMap.put(oSub.toString(), sub.get(oSub));
                    }
                    Boolean bo = false;
                    if (sub.containsKey("sub_part_id")) {
                        bo = sub.get("sub_part_id") == null || sub.get("sub_part_id") == "" ? false : true;
                    }
                    if (bo) {
                        computePartCostSubMapper.updatePartCostSub(subMap);
                    } else {
                        computePartCostSubMapper.addPartCostSub(subMap);
                    }
                }
            }
        }
        Boolean isComputeId = false;
        if (map.containsKey("compute_id")) {
            isComputeId = map.get("compute_id") == null || map.get("compute_id") == "" ? false : true;
        }
        if (isComputeId) {
            Double d = computeCostMapper.selectSumStuffCost(Integer.valueOf(map.get("compute_id").toString()));
            if (d != null) {
                map.put("stuff_cost", d);
                computeCostMapper.updateStuffCost(map);
            }
        } else {
            if (returnList.size() > 0) {
                Integer integer = returnList.get(0);
                Integer computeId = computePartCostMapper.selectComputeId(integer);
                Double d = computeCostMapper.selectSumStuffCost(computeId);
                map.put("stuff_cost", d);
                map.put("compute_id", computeId);
                computeCostMapper.updateStuffCost(map);
            }
        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("part_list", returnList);
        return returnMap;
    }

    /**
     * 获取核算单零部件明细
     * 根据 compute_id  和零部件的 id  获取数据 及 子表
     *
     * @return
     */
    @Override
    public Map<String, Object> selectDetailPart(Map<String, Object> param) {
        Map<String, Object> map = computePartCostMapper.selectPartCost(param);
        List<Map<String, Object>> list = computePartCostSubMapper.selectPartCostSub(Integer.valueOf(param.get("part_id").toString()));
        map.put("sub_part_list", list);
        return map;
    }

    @Override
    public Map<String, Object> selectGetPartList(Integer compute_id) {
        List<Map<String, Object>> list = computePartCostMapper.selectGetPartList(compute_id);
        Map<String, Object> map = new HashMap<>();
        map.put("part_list", list);
        return map;
    }

    /**
     * 删除零部件子表   修改 status=0  不进行 物理删除
     *
     * @param map
     * @return
     */
    @Override
    public Boolean deletePartSub(Map<String, Object> map) {
        return computePartCostSubMapper.deletePartSub(map);
    }

    /**
     * 删除零部件表   修改 status=0  不进行 物理删除
     *
     * @param map
     * @return
     */
    @Override
    public Boolean deletePart(Map<String, Object> map) {
        Boolean b = computePartCostMapper.deletePart(map);
        Double d = computeCostMapper.selectSumStuffCost(Integer.valueOf(map.get("compute_id").toString()));
        if (d != null && d >= 0) {
            map.put("process_cost", d);
            computeCostMapper.updateStuffCost(map);
        }
        return b;
    }

    /**
     * 逻辑删除 所有 computeId 的零部件信息 带上时间   以时间区分版本
     *
     * @param computeId
     * @return
     */
    @Override
    public int deleteEditionPart(Long computeId) {
        return computePartCostMapper.updateEditionPart(computeId);
    }
}

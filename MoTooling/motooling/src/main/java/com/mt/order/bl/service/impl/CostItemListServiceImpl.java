package com.mt.order.bl.service.impl;

import com.mt.bm.common.dao.RawnatureMapper;
import com.mt.order.bl.service.CostItemListService;
import com.mt.order.common.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CostItemListServiceImpl implements CostItemListService {

    @Autowired
    private DictProcessItemMapper dictProcessItemMapper;

    @Autowired
    private DictProcessItemUnitMapper dictProcessItemUnitMapper;

    @Autowired
    private ComputePartCostSubMapper computePartCostSubMapper;

    @Autowired
    private ComputePartCostMapper computePartCostMapper;

    @Autowired
    private RawnatureMapper rawnatureMapper;

    @Autowired
    private  DictionaryMapper dictionaryMapper;

    /**
     * 加工费项目列表
     * @param companyId
     * @return
     */
    @Override
    public Map<String, Object> processCostItemList(Integer companyId) {
       List<Map<String, Object>> listMap =  dictProcessItemMapper.processCostItemList(companyId);
        Map<String, Object> map = new HashMap<>();
        map.put("process_item_list",listMap);
        return map;
    }

    /**
     *加工费项目单位列表
     * @param companyId
     * @return
     */
    @Override
    public Map<String, Object> processCostItemUnitList(Integer companyId) {
        List<Map<String, Object>> listMap  =dictProcessItemUnitMapper.processCostItemUnitList(companyId);
        Map<String, Object> map = new HashMap<>();
        map.put("unit_list",listMap);
        return map;
    }

    /**
     * 下拉框   材料列表
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> rawNatureList(Map<String, Object> map) {
        List<Map<String, Object>> listMap = rawnatureMapper.selectRawNatureList(map);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("raw_nature_list",listMap);
        return returnMap;
    }

    //缺省部件列表  dictionary  part
    @Override
    public Map<String, Object> defaultPartList() {
        List<Map<String, Object>> listMap =dictionaryMapper.defaultPartList();
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("default_part_list",listMap);
        return returnMap;
    }
    //缺省子部件列表  PART_61  模胚     startup_step   mold
    @Override
    public Map<String, Object> defaultSubPartList(Map<String, Object> map) {
        List<Map<String, Object>> listMap =   dictionaryMapper.defaultSubPartList(map);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("default_sub_list",listMap);
        return returnMap;
    }
}

package com.mt.pc.purchase.service.impl;

import com.mt.common.utils.StringUtils;
import com.mt.order.bl.service.impl.ComputeServiceImpl;
import com.mt.pc.common.dao.PcRequirePlanMapper;
import com.mt.pc.purchase.service.RequirementPlanningService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RequirementPlanningServiceImpl implements RequirementPlanningService {
    private final static Logger log = Logger.getLogger(ComputeServiceImpl.class);

    @Autowired
    private PcRequirePlanMapper pcRequirePlanMapper;
    @Override
    public Map<String, Object> selectRequirePlanList(Map<String, Object> map) {
            if(map.containsKey("query")) {
                Map<String, Object> query = (Map<String, Object>) map.get("query");
                StringBuilder sb = new StringBuilder();
                List<Integer> typeList = (List<Integer>) query.get("create_type");
                if (typeList!=null) {
                    for (Integer s : typeList) {
                        sb.append(s).append("|");
                    }
                    if (sb.length() > 0) {
                        map.put("create_type", sb.toString().substring(0, sb.length() - 1));
                    }
                }
                map.put("mat_desc", query.get("mat_desc")==null||query.get("mat_desc")==""?null:query.get("mat_desc"));
            }
        map.put("page", ((Integer) map.get("curr_page") - 1) * (Integer) map.get("page_size"));
        List< Map<String, Object>>  list= pcRequirePlanMapper.selectRequirePlanList(map);
        if(list.size()<=0){
            return StringUtils.pageList(map,"require_list",0);
        }
        Integer totalCount = pcRequirePlanMapper.totalCount(map);
        Map<String, Object> page = StringUtils.page(map, list, "require_list", totalCount);
        return page;
    }

    @Override
    public Map<String, Object> selectRequirePlanDeatil(Map<String, Object> map) {
        Map<String, Object> query= (Map<String, Object>) map.get("query");
        Long requireId = Long.valueOf(query.get("require_id").toString());
        Map<String, Object>  param  = pcRequirePlanMapper.selectRequirePlanDeatil(requireId);
        Map<String, Object> returnMap =new HashMap<>();
        returnMap.put("require_info",param);
        return returnMap;
    }

    @Override
    public boolean deleteRequire(Integer requireId) {
       boolean b =  pcRequirePlanMapper.deleteRequire(requireId);
        return b;
    }
}

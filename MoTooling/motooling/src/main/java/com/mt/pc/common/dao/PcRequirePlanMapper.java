package com.mt.pc.common.dao;

import com.mt.pc.common.model.PcRequirePlan;

import java.util.List;
import java.util.Map;

public interface PcRequirePlanMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PcRequirePlan record);

    int insertSelective(PcRequirePlan record);

    PcRequirePlan selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PcRequirePlan record);

    int updateByPrimaryKey(PcRequirePlan record);

    List<Map<String,Object>> selectRequirePlanList(Map<String, Object> map);

    Integer totalCount(Map<String, Object> map);

    boolean deleteRequire(Integer requireId);

    Map<String,Object> selectRequirePlanDeatil(Long requireId);

    String createMatRequirePlanfromRequest(Map map);

    String createMatRequirePlanfromMold(Map map);

    Map<String, Object> getDefaultCoin(Integer companyId);

    Integer   updateRequirePlanQty(Map<String,Object>  map);

    Integer   updateRequirePlan(Map<String,Object>  map);

    List<PcRequirePlan> selectByNotHwIdAndCreateType(Map map);
}
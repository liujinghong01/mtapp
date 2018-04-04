package com.mt.bm.common.dao;

import com.mt.bm.common.model.Bom;

import java.util.List;
import java.util.Map;

public interface BomMapper {
    int deleteByPrimaryKey(Long bomId);

    int insert(Bom record);

    int insertSelective(Bom record);

    Bom selectByPrimaryKey(Long bomId);

    int updateByPrimaryKeySelective(Bom record);

    int updateByPrimaryKey(Bom record);

    Integer selectBomByConditionsCount(Map queryMap);

    List<Map<String, Object>> selectBomByConditions(Map queryMap);

    Long selectBomIdByMoldNo(Map queryMap);

    List<Map<String, Object>> selectMatByBomId(Long bomId);
}
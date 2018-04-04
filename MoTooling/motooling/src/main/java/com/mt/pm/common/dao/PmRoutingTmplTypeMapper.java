package com.mt.pm.common.dao;

import com.mt.pm.common.model.PmRoutingTmplType;

import java.util.List;
import java.util.Map;

public interface PmRoutingTmplTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PmRoutingTmplType record);

    int insertSelective(PmRoutingTmplType record);

    PmRoutingTmplType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmRoutingTmplType record);

    int updateByPrimaryKey(PmRoutingTmplType record);


    List<Map<String, Object>> selectRoutingTmplTypeBycondition(Map queryMap);

    Integer selectRoutingTmplTypeByconditionCount(Map queryMap);


    List<Map<String, Object>> selectRoutingTmplTypeList(Map queryMap);
}
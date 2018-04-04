package com.mt.pm.common.dao;

import com.mt.pm.common.model.PmRoutingTmpl;

import java.util.List;
import java.util.Map;

public interface PmRoutingTmplMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PmRoutingTmpl record);

    int insertSelective(PmRoutingTmpl record);

    PmRoutingTmpl selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmRoutingTmpl record);

    int updateByPrimaryKey(PmRoutingTmpl record);

    List<Map<String, Object>> selectRoutingTmplBycondition(Map queryMap);

    Integer selectRoutingTmplByconditionCount(Map queryMap);
}
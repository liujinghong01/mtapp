package com.mt.pm.common.dao;

import com.mt.pm.common.model.PmProcConf;

import java.util.List;
import java.util.Map;

public interface PmProcConfMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PmProcConf record);

    int insertSelective(PmProcConf record);

    PmProcConf selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmProcConf record);

    int updateByPrimaryKey(PmProcConf record);

    List<Map<String, Object>> selectProcessBycondition(Map queryMap);

    Integer selectProcessByconditionCount(Map queryMap);
}
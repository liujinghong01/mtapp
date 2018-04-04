package com.mt.pm.common.dao;

import com.mt.pm.common.model.PmProcPg;

import java.util.List;
import java.util.Map;

public interface PmProcPgMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PmProcPg record);

    int insertSelective(PmProcPg record);

    PmProcPg selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmProcPg record);

    int updateByPrimaryKey(PmProcPg record);

    List<Map<String, Object>> selectByProcId(Long procId);

    int deleteByProcId(Long procId);
}
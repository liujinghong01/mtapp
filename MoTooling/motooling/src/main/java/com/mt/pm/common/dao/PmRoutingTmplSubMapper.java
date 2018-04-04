package com.mt.pm.common.dao;

import com.mt.pm.common.model.PmRoutingTmplSub;

import java.util.List;
import java.util.Map;

public interface PmRoutingTmplSubMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PmRoutingTmplSub record);

    int insertSelective(PmRoutingTmplSub record);

    PmRoutingTmplSub selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmRoutingTmplSub record);

    int updateByPrimaryKey(PmRoutingTmplSub record);

    int deleteByTmplId(Long id);

    List<Map<String, Object>> selectByTmplId(Long tmplId);
}
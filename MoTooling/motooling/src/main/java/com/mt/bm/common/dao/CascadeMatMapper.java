package com.mt.bm.common.dao;

import com.mt.bm.common.model.CascadeMat;

import java.util.List;
import java.util.Map;


public interface CascadeMatMapper {
    int insert(CascadeMat record);

    CascadeMat selectByPrimaryKey(Integer id);

    List<CascadeMat> selectByMatType(Map queryMap);

    List<String> selectCascadeMatTypeList(Map queryMap);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(CascadeMat cascadeMat);
}

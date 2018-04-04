package com.mt.bm.bom.service;

import com.mt.bm.common.model.CascadeMat;

import java.util.List;
import java.util.Map;

public interface CascadeMatService {

    int insertCascadeMat(CascadeMat cascadeMat);

    List<CascadeMat> selectByMatType(Map queryMap);

    int deleteCascadeMat( Integer id);

    CascadeMat selectById( Integer id);

    int updateCascadeMat( CascadeMat cascadeMat);

    List<String> selectCascadeMatTypeList(Map queryMap);
}

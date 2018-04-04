package com.mt.bm.bom.service;

import com.mt.bm.common.model.Rawnature;

import java.util.List;
import java.util.Map;

public interface RawnatureService {
    int deleteByPrimaryKey(Long id);

    int insert(Rawnature record);

    int insertSelective(Rawnature record);

    Rawnature selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Rawnature record);

    int updateByPrimaryKey(Rawnature record);


    List<Rawnature> selectRawNatureList(Map map);
}

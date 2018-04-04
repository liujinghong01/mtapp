package com.mt.bm.common.dao;

import com.mt.bm.common.model.MtMat;

public interface MtMatMapper {
    int deleteByPrimaryKey(Long matId);

    int insert(MtMat record);

    int insertSelective(MtMat record);

    MtMat selectByPrimaryKey(Long matId);

    int updateByPrimaryKeySelective(MtMat record);

    int updateByPrimaryKey(MtMat record);
}
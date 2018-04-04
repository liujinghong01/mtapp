package com.mt.bm.common.dao;

import com.mt.bm.common.model.BomLog;

public interface BomLogMapper {
    int deleteByPrimaryKey(Long bomLogId);

    int insert(BomLog record);

    int insertSelective(BomLog record);

    BomLog selectByPrimaryKey(Long bomLogId);

    int updateByPrimaryKeySelective(BomLog record);

    int updateByPrimaryKey(BomLog record);
}
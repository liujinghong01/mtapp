package com.mt.bm.common.dao;

import com.mt.bm.common.model.MatLog;

public interface MatLogMapper {
    int deleteByPrimaryKey(Long matLogId);

    int insert(MatLog record);

    int insertSelective(MatLog record);

    MatLog selectByPrimaryKey(Long matLogId);

    int updateByPrimaryKeySelective(MatLog record);

    int updateByPrimaryKey(MatLog record);
}
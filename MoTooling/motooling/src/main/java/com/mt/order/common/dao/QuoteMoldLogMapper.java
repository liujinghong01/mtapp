package com.mt.order.common.dao;

import com.mt.order.common.model.QuoteMoldLog;
import org.apache.ibatis.annotations.Param;

public interface QuoteMoldLogMapper {
    int deleteByPrimaryKey(@Param("id") Long id, @Param("version") Integer version);

    int insert(QuoteMoldLog record);

    int insertSelective(QuoteMoldLog record);

    QuoteMoldLog selectByPrimaryKey(@Param("id") Long id, @Param("version") Integer version);

    int updateByPrimaryKeySelective(QuoteMoldLog record);

    int updateByPrimaryKey(QuoteMoldLog record);
}
package com.mt.order.common.dao;

import com.mt.order.common.model.QuoteMoldProdLog;
import org.apache.ibatis.annotations.Param;

public interface QuoteMoldProdLogMapper {
    int deleteByPrimaryKey(@Param("id") Long id, @Param("version") Integer version);

    int insert(QuoteMoldProdLog record);

    int insertSelective(QuoteMoldProdLog record);

    QuoteMoldProdLog selectByPrimaryKey(@Param("id") Long id, @Param("version") Integer version);

    int updateByPrimaryKeySelective(QuoteMoldProdLog record);

    int updateByPrimaryKey(QuoteMoldProdLog record);
}
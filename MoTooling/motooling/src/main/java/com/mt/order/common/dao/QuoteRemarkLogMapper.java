package com.mt.order.common.dao;

import com.mt.order.common.model.QuoteRemarkLog;
import org.apache.ibatis.annotations.Param;

public interface QuoteRemarkLogMapper {
    int deleteByPrimaryKey(@Param("id") Long id, @Param("version") Integer version);

    int insert(QuoteRemarkLog record);

    int insertSelective(QuoteRemarkLog record);

    QuoteRemarkLog selectByPrimaryKey(@Param("id") Long id, @Param("version") Integer version);

    int updateByPrimaryKeySelective(QuoteRemarkLog record);

    int updateByPrimaryKey(QuoteRemarkLog record);
}
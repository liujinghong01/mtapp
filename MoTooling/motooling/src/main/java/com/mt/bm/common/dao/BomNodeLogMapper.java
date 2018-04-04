package com.mt.bm.common.dao;

import com.mt.bm.common.model.BomNodeLog;

public interface BomNodeLogMapper {
    int insert(BomNodeLog record);

    int insertSelective(BomNodeLog record);
}
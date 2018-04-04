package com.mt.bm.bom.service;

import com.mt.bm.common.model.MatType;

import java.util.List;

public interface MatTypeService {
    int deleteByPrimaryKey(Long id);

    int insert(MatType record);

    int insertSelective(MatType record);

    MatType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MatType record);

    int updateByPrimaryKey(MatType record);

    List<MatType> selectMatTypeAll();
}

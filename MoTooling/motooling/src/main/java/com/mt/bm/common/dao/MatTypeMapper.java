package com.mt.bm.common.dao;

import com.mt.bm.common.model.MatType;

import java.util.List;
import java.util.Map;


public interface MatTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MatType record);

    int insertSelective(MatType record);

    MatType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MatType record);

    int updateByPrimaryKey(MatType record);

    List<MatType> selectMatTypeAll();

    // Alnwick 添加

    // 物料类型新增
    Integer addIvMatTypeModify(Map<String, Object> matTypeInfo);
    // 物料类型修改
    Integer updateIvMatTypeModify(Map<String, Object> matTypeInfo);
    // 删除物料类型
    Boolean delectMatType(Long mat_type_id);
    // selectBMatTypeModify
    MatType selectBMatTypeModify(Map<String, Object> map);
}
package com.mt.bm.common.dao;

import com.mt.bm.common.model.MtMatType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MtMatTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MtMatType record);

    int insertSelective(MtMatType record);

    MtMatType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MtMatType record);

    int updateByPrimaryKey(MtMatType record);

    List<Map<String,Object>> selectMtMat(Map<String,Object> map);

    List<Map<String,Object>> selectMatIds(List<Integer> list);
}
package com.mt.bm.common.dao;

import com.mt.bm.common.model.Rawnature;

import java.util.List;
import java.util.Map;

public interface RawnatureMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Rawnature record);

    int insertSelective(Rawnature record);

    Rawnature selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Rawnature record);

    int updateByPrimaryKey(Rawnature record);

    List<Rawnature> selectByNodeId(Long nodeId);

    /*
        Alnwick 添加
        下拉框数据， 材料列表
     */
    List<Map<String,Object>> selectRawNatureList(Map<String, Object> map);

}
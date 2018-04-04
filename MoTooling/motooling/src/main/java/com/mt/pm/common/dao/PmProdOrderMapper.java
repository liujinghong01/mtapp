package com.mt.pm.common.dao;

import com.mt.pm.common.model.PmProdOrder;

import java.util.List;
import java.util.Map;

public interface PmProdOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PmProdOrder record);

    int insertSelective(PmProdOrder record);

    PmProdOrder selectByPrimaryKey(Long id);

    PmProdOrder selectProdOrderByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmProdOrder record);

    int updateByPrimaryKey(PmProdOrder record);

    List<Map<String, Object>> selectProdOrderByCondition(Map queryMap);

    Integer selectProdOrderByConditionCount(Map queryMap);

    List<Map<String,Object>> selectProdOrderByPBomNode(Map map);

    /**
     * @Author: Randy
     * @Date:2018/2/6 14:10
     * @params: 
     * @return：
     * @Description：
     */
    int batchUpdate(List<Map<String, Object>> mapList);

}
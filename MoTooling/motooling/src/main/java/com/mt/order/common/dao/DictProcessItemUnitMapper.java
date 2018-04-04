package com.mt.order.common.dao;

import com.mt.order.common.model.DictProcessItemUnit;

import java.util.List;
import java.util.Map;

public interface DictProcessItemUnitMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DictProcessItemUnit record);

    int insertSelective(DictProcessItemUnit record);

    DictProcessItemUnit selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DictProcessItemUnit record);

    int updateByPrimaryKey(DictProcessItemUnit record);
    //加工费项目单位列表
    List<Map<String,Object>> processCostItemUnitList(Integer companyId);
}
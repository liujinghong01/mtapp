package com.mt.order.common.dao;

import com.mt.order.common.model.DictProcessItem;

import java.util.List;
import java.util.Map;

public interface DictProcessItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DictProcessItem record);

    int insertSelective(DictProcessItem record);

    DictProcessItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DictProcessItem record);

    int updateByPrimaryKey(DictProcessItem record);

    //加工费项目列表
    List<Map<String,Object>> processCostItemList(Integer companyId);



}
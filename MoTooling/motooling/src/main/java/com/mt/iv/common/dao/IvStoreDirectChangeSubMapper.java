package com.mt.iv.common.dao;

import com.mt.iv.common.model.IvStoreDirectChangeSub;

import java.util.List;
import java.util.Map;
/**
* @Author: Wendy
* @Description:
* @create    2018/1/23 15:29
* @Modified By:
*/    
public interface IvStoreDirectChangeSubMapper {
    int deleteByPrimaryKey(Long id);

    int insert(IvStoreDirectChangeSub record);

    int insertSelective(IvStoreDirectChangeSub record);

    IvStoreDirectChangeSub selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IvStoreDirectChangeSub record);

    int updateByPrimaryKey(IvStoreDirectChangeSub record);

    // 执行直接变更（查子表）,查询in/out_store_house_id是否有值
    List<Map<String,Object>>  selectStoreDirectChangeSubDetail(Integer  id);

    // 查询in/out_store_house_id为空，新增变更子表
    Integer   addStoreDirectChangeSub(Map<String,Object>  map);
}
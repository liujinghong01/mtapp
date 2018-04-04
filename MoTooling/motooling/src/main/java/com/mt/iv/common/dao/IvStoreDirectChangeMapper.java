package com.mt.iv.common.dao;

import com.mt.iv.common.model.IvStoreDirectChange;

import java.util.Map;
/**
* @Author: Wendy
* @Description:
* @create    2018/1/23 15:29
* @Modified By:
*/    
public interface IvStoreDirectChangeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(IvStoreDirectChange record);

    int insertSelective(IvStoreDirectChange record);

    IvStoreDirectChange selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IvStoreDirectChange record);

    int updateByPrimaryKey(IvStoreDirectChange record);

    // 查询in/out_store_house_id为空，新增变更主表
    Integer   addStoreDirectChange(Map<String,Object>  map);

    // 根据ID查找变更信息
    Map<String,Object>  selectStoreDirectChange(Long  id);
}
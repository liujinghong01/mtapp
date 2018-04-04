package com.mt.iv.common.dao;

import com.mt.iv.common.model.IvStoreHouse;

import java.util.List;
import java.util.Map;

public interface IvStoreHouseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(IvStoreHouse record);

    int insertSelective(IvStoreHouse record);

    IvStoreHouse selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IvStoreHouse record);

    int updateByPrimaryKey(IvStoreHouse record);
    // 库存物料数据筛选（按仓库分组统计）
    List<Map<String,Object>> selectSearchMat(Map<String, Object> map);

    // 根据物料名称查出仓库的总数量和金额
    Map<String,Object>  selectStoreHouseMatDesc(Map<String,Object> map);

    // 执行报废将仓库的数量和金额改变
    Integer    updateStoreHouseMatDesc(Map<String,Object>  map);
    // 新增库存地点表
    int addStoreHouse(Map<String, Object> param);

    // 根据仓库id查询仓库名字
    List<Map<String,Object>> selectStoreHouseNameById(Long storeHouseId);
}
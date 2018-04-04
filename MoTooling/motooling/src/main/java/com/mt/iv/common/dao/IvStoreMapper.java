package com.mt.iv.common.dao;

import com.mt.iv.common.model.IvStore;

import java.util.List;
import java.util.Map;

public interface IvStoreMapper {
    int deleteByPrimaryKey(Long id);

    int insert(IvStore record);

    int insertSelective(IvStore record);

    IvStore selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IvStore record);

    int updateByPrimaryKey(IvStore record);
    // 库存物料数据筛选（按全库存统计）
    List<Map<String,Object>> selectStoreSearchMat(Map<String, Object> map);

    Integer totalCountStore(Map<String, Object> map);

    // 根据物料名称查出总库存的数量和金额
    Map<String,Object>   selectStoreMatDesc(Map<String,Object> map);

    // 报废执行根据总库存修改数量和金额
    Integer    updateStoreMatDesc(Map<String,Object>  map);

    // 如果总库存不存在 就添加
    Integer addStore(Map<String, Object> param);

    // 根据物料id查询库存数量
    Map<String,Object> selectStockQtyByMatId(Long matId);

    // 根据物料id查询库存金额
    Map<String,Object> selectTotalPriceByMatId(Long matId);

    //基础物料数据筛选（按物料）
    List<Map<String,Object>>   selectBasicMatList(Map<String,Object>  map);

    Integer totalCountMat(Map<String,Object>  map);

}
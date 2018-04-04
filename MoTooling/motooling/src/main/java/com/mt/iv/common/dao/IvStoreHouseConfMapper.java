package com.mt.iv.common.dao;

import com.mt.iv.common.model.IvStoreHouseConf;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IvStoreHouseConfMapper {
    int deleteByPrimaryKey(Long id);

    int insert(IvStoreHouseConf record);

    int insertSelective(IvStoreHouseConf record);

    IvStoreHouseConf selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IvStoreHouseConf record);

    int updateByPrimaryKey(IvStoreHouseConf record);
    // 仓库新增
    Integer addStoreHouse(Map<String, Object> map);
    // 仓库修改
    Integer updateStoreHouse(Map<String, Object> storeHouseInfo);
    // 查询仓库列表
    List<Map<String,Object>> selectIvStorehouseList(Map<String, Object> map);
    // 总条数
    Integer totalCount(Map<String, Object> map);
    // 查询仓库详细信息(含库位)
    Map<String,Object> selectDetail(Map<String, Object> map);
    // 仓库删除
    Integer updateStoreHouseStatus(Long storeHouseId);
    // 仓库名是否存在
    int selectStoreHouseName(@Param("company_id") Object company_id,@Param("store_house_name")  Object store_house_name,@Param("store_house_id") Object store_house_id);
}
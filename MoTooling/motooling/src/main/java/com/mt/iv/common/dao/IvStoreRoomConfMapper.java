package com.mt.iv.common.dao;

import com.mt.iv.common.model.IvStoreRoomConf;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IvStoreRoomConfMapper {
    int deleteByPrimaryKey(Long id);

    int insert(IvStoreRoomConf record);

    int insertSelective(IvStoreRoomConf record);

    IvStoreRoomConf selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IvStoreRoomConf record);

    int updateByPrimaryKey(IvStoreRoomConf record);
    // 新增库位配置表
    Integer addStoreRoom(Map<String, Object> obMap);
    // 修改库位配置表
    Integer updateStoreRoom(Map<String, Object> obMap);
    // 查询仓库详细信息(含库位
    List<Map<String,Object>> selectRoomDetail(Map<String, Object> map);

    Map<String,Object> selectRoomConf(Map<String, Object> map);
    // 查询是否 同仓库  库位名重复
    int selectStoreRoomName(@Param("store_house_id") Object store_house_id,@Param("store_room_name")  Object store_room_name,@Param("store_room_id")  Object store_room_id);
}
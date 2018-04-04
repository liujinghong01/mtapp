package com.mt.iv.common.dao;

import com.mt.iv.common.model.IvStoreRoom;

import java.util.List;
import java.util.Map;

public interface IvStoreRoomMapper {
    int deleteByPrimaryKey(Long id);

    int insert(IvStoreRoom record);

    int insertSelective(IvStoreRoom record);

    IvStoreRoom selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IvStoreRoom record);

    int updateByPrimaryKey(IvStoreRoom record);
    //库存物料数据筛选（按仓库库位分组统计）
    List<Map<String,Object>> selectRommSearchMat(Map<String, Object> map);
    //库位删除
    Integer updateStoreRoom(Map<String, Object> map);

    //根据物料名称查出库位的总数量和金额
    Map<String,Object>  selectStoreRoomMatDesc(Map<String,Object> map);

    //执行报废将库位的数量和金额修改
    Integer   updateStoreRoomMatDesc(Map<String,Object>  map);
    //添加库存表
    Integer addStoreRoom(Map<String, Object> param);

    //根据仓库和物料类型去库位库存中查到符合条件的所有物料
    List<Map<String,Object>>  selectStoreRoomMatDetail(Map<String,Object> map);

    // 盘点单生成（修改盘点锁定标志）
    Integer   updateStoreCheckFlag(Map<String,Object>  map);

    //根据库位id查询库位名字
    List<Map<String,Object>> selectStoreRoomNameById(Long storeRoomId);
}
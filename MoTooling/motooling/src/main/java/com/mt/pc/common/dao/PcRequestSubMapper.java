package com.mt.pc.common.dao;

import com.mt.pc.common.model.PcRequestSub;

import java.util.List;
import java.util.Map;

public interface PcRequestSubMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PcRequestSub record);

    int insertSelective(PcRequestSub record);

    PcRequestSub selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PcRequestSub record);

    int updateByPrimaryKey(PcRequestSub record);
    //修改 请购单子表
    Long updeteRequestSubModify(Map<String, Object> objectMap);
    //新增 请购单子表
    Long addRequestSsubModify(Map<String, Object> objectMap);
    //删除 请购单子表中requestId的数据
    Boolean deleteRequest(Long requestId);
    //批量删除 请购单行信息
    Boolean deleteRequestLine(List<Long> list);
    //查询请购单子表
    List<Map<String,Object>> queryRequestSub(Long requestId);
    //查询当前库存量
    Map<String,Object>   selectCurrStockQty(Map<String,Object>  objectMap);

}
package com.mt.pc.common.dao;

import com.mt.pc.common.model.PcRequest;

import java.util.List;
import java.util.Map;

public interface PcRequestMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PcRequest record);

    int insertSelective(PcRequest record);

    PcRequest selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PcRequest record);

    int updateByPrimaryKey(PcRequest record);
    //修改请购单
    Long updeteRequestModify(Map<String, Object> headInfo);
    //新增请购单
    Long addRequestModify(Map<String, Object> headInfo);

    Map<String, Object>  selectIdAndNo(Long request_id);
    //删除请购单
    Boolean deleteRequest(Long requestId);
    //查询请购单
    Map<String,Object> queryRequest(Long requestId);
    //查询 请购单列表
    List<Map<String,Object>> selectRequesList(Map<String, Object> map);

    Integer totalCount(Map<String, Object> map);

    Map<String,Object> getCoin(Integer companyId);
}
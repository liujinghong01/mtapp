package com.mt.bm.common.dao;

import com.mt.bm.common.model.BomNode;

import java.util.List;
import java.util.Map;

public interface BomNodeMapper {
    int deleteByPrimaryKey(Long nodeId);

    int insert(BomNode record);

    int insertSelective(BomNode record);

    BomNode selectByPrimaryKey(Long nodeId);

    List<BomNode> selectByBomId(Long bomId);

    int updateByPrimaryKeySelective(BomNode record);

    int updateByPrimaryKey(BomNode record);

    List<BomNode> selectBomNodes(Map map);

    String getNewBomNodePath(Long nodeId);

    String getNewBomNodeBrotherPath(Long nodeId);

    BomNode getSonDefaultBomNode(Long bomId);

    List<BomNode> selectEBomNodes(Map map);

    BomNode selectFirstEBomNode(Long matId);

    int deleteByPNodeId(Map map);

    int deleteMatByPNodeId(Map map);

    Map<String,Object> getBomNodeByPos(Map map);

    Map<String,Object> getBomPNode(Map map);

    List<Map<String,Object>> selectSBomNodes(Map map);
}
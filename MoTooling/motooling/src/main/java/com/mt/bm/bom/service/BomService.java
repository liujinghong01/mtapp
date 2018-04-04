package com.mt.bm.bom.service;

import com.mt.bm.common.model.Bom;
import com.mt.bm.common.model.BomNode;
import com.mt.bm.common.model.Mat;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BomService {
    int deleteByPrimaryKey(Long bomId);

    int insert(Bom record);

    int insertSelective(Bom record);

    Bom selectByPrimaryKey(Long bomId);

    int updateByPrimaryKeySelective(Bom record);

    int updateByPrimaryKey(Bom record);

    int selectBomByConditionsCount(Map queryMap);

    Map<String, Object> selectBomByConditions(Map<String, Object> map);
    //返回分页类
//   Pagination selectBomByConditions(Map<String, Object> map);

    BomNode selectBomNode(Long nodeId);

    boolean deleteBomNode(Long nodeId);

    List<BomNode> selectBomNodes(Map queryMap);

    int insertBomNode(BomNode bomNode);

    int updateBomNode(BomNode bomNode);

    int updateByPrimaryKeySelective(BomNode bomNode);

    String getNewBomNodePath(Long nodeId,String type);

    BomNode getSonDefaultBomNode(Long bomId);

    boolean createBom(String is_crt_bom, String mold_no,String uid,String companyId,Integer bomQty,Date first_try_date);

    List<BomNode> selectEBomNodes(Map map);

    BomNode selectFirstEBomNode(Long matId);

    boolean appBom(Bom bomNode);

    boolean appBomRequire(Long bomId);

    Long selectBomIdByMoldNo(Map queryMap);

    List<Map<String, Object>> selectMatByBomId(Long bomId);

    boolean createOrUpdateBomNode(Long pNodeId,BomNode bomNode,Mat mat);

    int deleteByPNodeId(Map map);

    int deleteMatByPNodeId(Map map);
}

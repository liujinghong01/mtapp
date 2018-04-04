package com.mt.qm.common.dao;

import com.mt.qm.common.model.QmPurchQc;

import java.util.List;
import java.util.Map;
/**
* @Author: Wendy
* @Description:
* @create    2018/1/23 15:33
* @Modified By:
*/    
public interface QmPurchQcMapper {
    int deleteByPrimaryKey(Long id);

    int insert(QmPurchQc record);

    int insertSelective(QmPurchQc record);

    QmPurchQc selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(QmPurchQc record);

    int updateByPrimaryKey(QmPurchQc record);

    // 查询总条数
    Integer     totalCount(Map<String,Object>  map);

    // 删除来料质检单
    Integer   updateIncomingMatCheck(Long  purchQcId);

    // 新增来料质检单主表
    Integer   addIncomingMatCheckMainInfo(Map<String,Object>   map);

    // 修改来料质检单主表
    Integer   updateIncomingMatCheckMainInfo(Map<String,Object>   map);

    // 根据质检单ID查看质检信息
    Map<String,Object>   selectIncomingMatCheckById(Long  id);

    // 查询来料质检主表
    Map<String,Object>   selectIncomingMatCheckMainInfo(Map<String,Object>  map);

    // 查询待质检来料主表
    List<Map<String,Object>>    selectStayCheckPurchInfo(Map<String,Object>  map);

    // 查询来料质检列表
    List<Map<String,Object>>   selectIncomingMatCheckList(Map<String,Object> map);

    // 查询未审批以及退回的
    List<Map<String,Object>>   selectApproveStepList(Map<String,Object>  map);
}
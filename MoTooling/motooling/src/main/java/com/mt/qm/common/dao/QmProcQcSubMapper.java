package com.mt.qm.common.dao;

import com.mt.qm.common.model.QmProcQcSub;

import java.util.Map;

/**
 * @Author:Wujie
 * @Date: Create in 2018-01-12 11:28
 * @Description:
 * @Modified By:
 */
public interface QmProcQcSubMapper {

    int deleteByPrimaryKey(Long id);

    int insert(QmProcQcSub record);

    int insertSelective(QmProcQcSub record);

    QmProcQcSub selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(QmProcQcSub record);

    int updateByPrimaryKey(QmProcQcSub record);

    /**
     * 新增工序质检单子表
     * @param map
     * @return
     */
    Integer addProcQcSub(Map<String,Object> map);

    /**
     * 修改工序质检单子表
     * @param map
     * @return
     */
    Integer updateProcQcSub(Map<String,Object> map);

    /**
     * 删除工序质检单子表
     * @param map
     * @return
     */
    Boolean deleteProcQcSub(Map<String,Object> map);

    /**
     * 根据工序质检单子表id查询质检数量
     * @param map
     * @return
     */
    Map<String,Object> selectQcQtyById(Map<String,Object> map);

    /**
     * 根据工序质检单id查询质检数量
     * @param popQcId
     * @return
     */
    Integer selectQcQtyByPopQcId(Integer popQcId);

    /**
     * 根据工序质检单id查询已建质检单数量
     * @param popQcId
     * @return
     */
    Integer selectQcBillQtyByPopQcId(Integer popQcId);

    //质检审批通过后修改生产定单子表
    int updatePmProdOrderSubByQcInfo(Long popQcId);
}
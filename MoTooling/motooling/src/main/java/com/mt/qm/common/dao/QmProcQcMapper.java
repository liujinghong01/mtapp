package com.mt.qm.common.dao;

import com.mt.qm.common.model.QmProcQc;

import java.util.List;
import java.util.Map;

/**
 * @Author:Wujie
 * @Date: Create in 2018-01-12 11:28
 * @Description:
 * @Modified By:
 */
public interface QmProcQcMapper {

    int deleteByPrimaryKey(Long id);

    int insert(QmProcQc record);

    int insertSelective(QmProcQc record);

    QmProcQc selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(QmProcQc record);

    int updateByPrimaryKey(QmProcQc record);

    /**
     * 查询工序质检单列表总条数
     * @param map
     * @return
     */
    Integer selectTotalCount(Map<String,Object> map);

    /**
     * 查询工序质检单列表
     * @param map
     * @return
     */
    List<Map<String,Object>> selectProcQcList(Map<String,Object> map);

    /**
     * 查询工序质检单详情
     * @param popQcId
     * @return
     */
    Map<String,Object> selectProcQcDetail(Integer popQcId);

    /**
     * 查询工序质检单详情
     * @param popQcId
     * @return
     */
    List<Map<String,Object>> selectProcQcAndProdOrderDetail (Integer popQcId);

    /**
     * 查询待质检的工序信息
     * @param map
     * @return
     */
    List<Map<String,Object>> selectProcQcPqiList(Map<String,Object> map);

    /**
     * 新增工序质检单
     * @param map
     * @return
     */
    Integer addProcQc(Map<String,Object> map);

    /**
     * 修改工序质检单
     * @param map
     * @return
     */
    Integer updateProcQc(Map<String,Object> map);

    /**
     * 删除工序质检单
     * @param map
     * @return
     */
    Boolean deleteProcQc(Map<String,Object> map);

    /**
     * 新增修改成功之后返回质检单id和质检单号
     * @param popQcId
     * @return
     */
    Map<String,Object> selectPopQcIdAndPopQcNo(Integer popQcId);

    /**
     * 根据订单工序id查询订单工序已完成数量和已建质检单数量
     * @param popId
     * @return
     */
    Map<String,Object> selectProcQtyByPopId(Integer popId);

    /**
     * 根据质检单子表id查询质检数量,订单工序已完成数量,已建质检单数量
     * @param popQcSubId
     * @return
     */
    Map<String,Object> selectProcQtyByPopQcSubId(Integer popQcSubId);

    /**
     * 新增工序质检单成功后修改已建质检单数量
     * @param map
     * @return
     */
    Integer updateQcBillQty(Map<String,Object> map);
}
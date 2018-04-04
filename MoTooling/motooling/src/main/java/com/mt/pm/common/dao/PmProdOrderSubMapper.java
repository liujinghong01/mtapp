package com.mt.pm.common.dao;

import com.mt.pm.common.model.PmProdOrderSub;
import com.mt.pm.production.bo.PmWorkProcedureBo;

import java.util.List;
import java.util.Map;

public interface PmProdOrderSubMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PmProdOrderSub record);

    int insertSelective(PmProdOrderSub record);

    PmProdOrderSub selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmProdOrderSub record);

    int updateByPrimaryKey(PmProdOrderSub record);

    List<PmProdOrderSub> selectByProdOrderId(long prodOrderId);

    int deleteProdOrderSub(Map queryMap);

    int deleteByProdId(Long id);

    /**
     * @Author: Randy
     * @Date:2018/1/11 16:31
     * @params:
     * @return：
     * @Description： 根据物料号查询工序列表集合
     */
    List<PmWorkProcedureBo> selectByPoId(String poId);

    /**
     * @Author: Randy
     * @Date:2018/2/1 9:47
     * @params:
     * @return：
     * @Description： 根据公司部门和时间段信息获取有效工序列表集合
     */
    List<PmWorkProcedureBo> selectByPgInfo(Map queryMap);



}
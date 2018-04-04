package com.mt.order.common.dao;

import com.mt.order.common.model.Compute;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ComputeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Compute record);

    int insertSelective(Compute record);

    Compute selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Compute record);

    int updateByPrimaryKey(Compute record);

    List<Map<String,Object>> vagueSelect(Map<String, Object> hashMap);

    Integer totalCount(Map<String, Object> map);

    Integer addCustomerOrder(Map<String, Object> map);

    Map<String,Object> getComputeAndCost(Integer compute_id);

    Boolean updateStatus(Long computeId);

    int updateCustomerOrder(Map<String, Object> map);
    // 是否存在重名的 制品信息
    int selectProdInfoRepea(@Param("prod_info") String prod_info, @Param("companyId")  int companyId, @Param("computeId") Object computeId);
}
package com.mt.order.common.dao;

import com.mt.order.common.model.ComputePartCost;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ComputePartCostMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ComputePartCost record);

    int insertSelective(ComputePartCost record);

    ComputePartCost selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ComputePartCost record);

    int updateByPrimaryKey(ComputePartCost record);

    Integer addPartCost(Map<String, Object> hashMap);

    Map<String,Object> selectPartCost(Map<String, Object> map);

    List<Map<String,Object>> selectGetPartList(Integer compute_id);

    Boolean deletePart(Map<String, Object> map);

    int updatePartCost(Map<String, Object> partMap);

    Integer selectComputeId(Integer integer);

    int updateEditionPart(Long computeId);
    //零部件名称 是否重复
    int selectPartNameRepea(@Param("part_name") Object partName, @Param("part_id") Object part_id,@Param("compute_id") Object compute_id);
}
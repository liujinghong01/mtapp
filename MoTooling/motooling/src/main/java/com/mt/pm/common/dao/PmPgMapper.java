package com.mt.pm.common.dao;

import com.mt.pm.common.model.PmPg;

import java.util.List;
import java.util.Map;

public interface PmPgMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PmPg record);

    int insertSelective(PmPg record);

    PmPg selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmPg record);

    int updateByPrimaryKey(PmPg record);

    // 查询生产小组列表
    List<Map<String,Object>> selectProductionGroup(Map<String, Object> map);

    // 查询生产小组列表总条数
    Integer  totalCount(Map<String, Object> map);

    // 查询生产小组详情主表
    Map<String,Object>  selectProductionGroupDetail(Map<String, Object> map);

    // 生产小组的响应参数
    Map<String,Object>  selectProductionGroupInfo(Long  pgId);

    // 生产小组的新增
    Integer  addProductionGroup(Map<String, Object> map);

    // 生产小组的修改
    Integer  updateProductionGroupInfo(Map<String,Object> map);

     /**
     * 根据 PgId 得到该条记录
     * @param pgId
     * @return
     */
    PmPg selectByPgId(Long pgId);

    List<PmPg> selectByMemberId(Map map);
}
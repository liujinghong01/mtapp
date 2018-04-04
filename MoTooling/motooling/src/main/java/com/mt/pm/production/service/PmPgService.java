package com.mt.pm.production.service;


import java.util.Map;

/**
* @Author: Wendy
* @Description:
* @create    2018/1/9 15:26
* @Modified By:
*/
public interface PmPgService {
    // 查询生产小组的列表
    Map<String,Object>  selectProductionGroupList(Map<String, Object> map);

    // 查询生产小组的详情
    Map<String,Object>  selectProductionGroupDetail(Map<String, Object> map);

    // 人员删除
    Map<String,Object> updateMember(Map<String, Object> map);

    // 生产小组删除
    Map<String,Object> updateProductionGroup(Map<String, Object> map);

    // 人员新增修改
    Map<String,Object> addAndUpdateMemberInfo(Map<String,Object> map);

    // 生产小组新增修改
    Map<String,Object> addAndUpdateProductionGroup(Map<String,Object> map);


}


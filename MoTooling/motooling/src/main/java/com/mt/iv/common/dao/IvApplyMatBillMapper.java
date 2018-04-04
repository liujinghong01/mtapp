package com.mt.iv.common.dao;

import com.mt.iv.common.model.IvApplyMatBill;

import java.util.List;
import java.util.Map;

/**
* @Author: Wendy
* @Description:
* @create    2018/1/23 15:29
* @Modified By:
*/    
public interface IvApplyMatBillMapper {

    int deleteByPrimaryKey(Long id);

    int insert(IvApplyMatBill record);

    int insertSelective(IvApplyMatBill record);

    IvApplyMatBill selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IvApplyMatBill record);

    int updateByPrimaryKey(IvApplyMatBill record);

    //根据UID取创建人名称
    Map<String,Object>   selectUserCompanyByUid(Map<String,Object>  map);

    // 新增领料主单
    Integer   addApplyMatBill(Map<String, Object> applyMatInfo);

    // 修改领料主单
    Integer   updateApplyMatBill(Map<String, Object> applyMatInfo);

    // 查询带调节总条数
    Integer totalCount(Map<String, Object> map);

    // 领料单删除
    Integer updateApplyBill(Long applyId);

    // 根据领料ID查找信息
    Map<String,Object>  selectApplyMatBillById(Long id);

    // 查询领料详情主表
    Map<String,Object>  selectApplyMatBill(Integer id);

    // 查询带条件数组领料单列表
    List<Map<String,Object>>  selectApplyMatBillList(Map<String, Object> map);

    //判断该领料单信息是否为已审批-审核-审批结束-已领料的状态
    List<Map<String,Object>>  selectApproveStepList(Long  applyId);

    //模具领料——查询BOM物料子表总数量、查询领料子表已领数量
    Map<String,Object>  selectMatInfoByQty(Map<String,Object>  map);

    Map<String,Object>  selectCompanyId(Integer id);


}
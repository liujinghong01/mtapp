package com.mt.pm.common.dao;

import com.mt.pm.common.model.PmShiftSub;
import com.mt.pm.common.pojo.PmShiftPojo;
import com.mt.pm.production.bo.PmShiftSubBo;

import java.util.List;
import java.util.Map;

/**
 * @Author:Wujie
 * @Date: Create in 2018-01-12 11:28
 * @Description:
 * @Modified By:
 */
public interface PmShiftSubMapper {

    int deleteByPrimaryKey(Long id);

    int insert(PmShiftSub record);

    int insertSelective(PmShiftSub record);

    PmShiftSub selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmShiftSub record);

    int updateByPrimaryKey(PmShiftSub record);

    /**
     * 添加
     * @param pmShiftSub
     * @return
     */
    Integer addPmShiftSub(PmShiftSub pmShiftSub);

    /**
     * 修改
     * @param objectMap
     * @return
     */
    Integer updatePmShiftSub(Map<String,Object> objectMap);

    /**
     * 删除
     * @param pmShiftPojo
     * @return
     */
    Boolean deletePmShiftSub(PmShiftPojo pmShiftPojo);

    /**
     * 查询班次子表详情
     * @param pmShiftPojo
     * @return
     */
    List<Map<String,Object>> selectPmShiftSubDetail(PmShiftPojo pmShiftPojo);

    /**
     * 查询公司默认班次
     * @Author: Randy
     * @Date:2018/1/11 15:07
     * @param pgId
     * @params:
     * @return：
     * @Description： 查询公司默认班次
     */
    List<PmShiftSubBo> selectDefaultShiftSubListByPgid(Long pgId);

    /**
     * 查询公司某部门某天班次段列表
     * @Author: Randy
     * @Date:2018/1/9 15:39
     * @param map
     * @params:
     * @return：
     * @Description： 查询公司某部门某天班次段列表
     */
    List<PmShiftSubBo> selectPmShiftSubListByDate(Map<String,Object> map);


}
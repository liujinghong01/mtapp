package com.mt.pm.common.dao;

import com.mt.pm.common.model.PmShift;
import com.mt.pm.common.pojo.PmShiftPojo;

import java.util.List;
import java.util.Map;

/**
 * @Author:Wujie
 * @Date: Create in 2018-01-12 11:28
 * @Description:
 * @Modified By:
 */
public interface PmShiftMapper {

    int deleteByPrimaryKey(Long id);

    int insert(PmShift record);

    int insertSelective(PmShift record);

    PmShift selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmShift record);

    int updateByPrimaryKey(PmShift record);

    /**
     * 添加班次
     * @param pmShift
     * @return
     */
    int addPmShift(PmShift pmShift);

    /**
     * 查询班次列表总条数
     * @param pmShiftPojo
     * @return
     */
    int totalCountList(PmShiftPojo pmShiftPojo);

    /**
     * 查询班次列表
     * @param pmShiftPojo
     * @return
     */
    List<Map<String,Object>> selectPmShiftList(PmShiftPojo pmShiftPojo);

    /**
     * 查询班次详情
     * @param pmShiftPojo
     * @return
     */
    Map<String,Object> selectPmShiftDetail(PmShiftPojo pmShiftPojo);

    /**
     * 删除班次
     * @param pmShiftPojo
     * @return
     */
    Boolean deletePmShift(PmShiftPojo pmShiftPojo);

    /**
     * 新增修改返回数据
     * @param shiftId
     * @return
     */
    Map<String,Object> selectPmShiftById(Long shiftId);

    /**
     * 根据班次id查询班次列表
     * @param shiftId
     * @return
     */
    Map<String,Object> selectPmShiftListByShiftId(Integer shiftId);
}
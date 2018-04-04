package com.mt.pm.production.service;


import com.mt.pm.common.pojo.PmShiftPojo;

import java.util.Map;

/**
 * @Author:Wujie
 * @Date: Create in 2018-01-12 11:28
 * @Description:
 * @Modified By:
 */
public interface PmShiftService {

    /**
     * 查询班次列表
     * @param pmShiftPojo
     * @return
     */
    Map<String,Object> selectPmShiftList(PmShiftPojo pmShiftPojo);

    /**
     * 查询班次详情
     * @param pmShiftPojo
     * @return
     */
    Map<String,Object> selectPmShiftDetail(PmShiftPojo pmShiftPojo);

    /**
     * 新增修改班次
     * @param pmShiftPojo
     * @return
     */
    Map<String,Object> addAndUpdatePmShift(PmShiftPojo pmShiftPojo);

    /**
     * 删除班次
     * @param pmShiftPojo
     * @return
     */
    Boolean deletePmShift(PmShiftPojo pmShiftPojo);
}


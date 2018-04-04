package com.mt.qm.quality.service;

import java.util.Map;

/**
 * @Author:Wujie
 * @Date: Create in 2018-01-12 9:59
 * @Description:
 * @Modified By:
 */
public interface ProcQcService {

    /**
     * 查询工序质检单列表
     * @param map
     * @return
     */
    Map<String,Object> selectProcQcList(Map<String,Object> map);

    /**
     * 查询工序质检单详情
     * @param map
     * @return
     */
    Map<String,Object> selectProcQcDetail(Map<String,Object> map);

    /**
     * 查询待质检的工序信息
     * @param map
     * @return
     */
    Map<String,Object> selectNoProcQcList(Map<String,Object> map);

    /**
     * 新增或者修改工序质检单
     * @param map
     * @return
     */
    Map<String,Object> addAndUpdateProcQc(Map<String,Object> map);

    /**
     * 删除工序质检单
     * @param map
     * @return
     */
    Boolean deleteProcQc(Map<String,Object> map);
}

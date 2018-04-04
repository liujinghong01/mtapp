package com.mt.pm.production.service;

import com.mt.pm.production.bo.PmWorkProcedureBo;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @Author: Randy
 * @Date: Create in 2018-01-08 15:45
 * @Description: 手工排产服务
 * @Modified By:
 */
public interface PmSchedulingService {


    /**
     * @Author: Randy
     * @Date:2018/1/24 13:14
     * @params:
     * @return：
     * @Description： 查询未报工工序列表
     */
    List<PmWorkProcedureBo> sortPmWorkProcedures(String poId, int sortType);

    /**
     *
     * @param
     * @param scheduringTime
     * @param sortType 排序类型，1、顺排  2、倒排
     * @return
     * @description 输入物料编码和排序起始时间，进行排产并返回排产后的工序集合。
     */
    Map<String, Object> produceScheduling(List<PmWorkProcedureBo> pmWorkProcedureBos, LocalDateTime scheduringTime, int sortType, String companyId) throws ParseException;

    /**
     * @Author: Randy
     * @Date:2018/1/24 10:30
     * @params: poId
     * @return： 紧迫度数值
     * @Description： 根据未报工工序计算当前零件的紧迫度数值
     */
    double urgencyCalculate(List<PmWorkProcedureBo> pmWorkProcedureBos, LocalDate tryDate ,String companId);

    /**
     * @Author: Randy
     * @Date:2018/1/31 13:53
     * @params:
     * @return：
     * @Description： 根据生产定单id计算本层零件紧迫度，同时update下层零件紧迫度数值
     */
    void updateUrgencyCalculate(String poId);


}

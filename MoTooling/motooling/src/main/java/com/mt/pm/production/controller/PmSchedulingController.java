package com.mt.pm.production.controller;

import com.mt.common.controller.BaseController;
import com.mt.common.core.support.HttpCode;
import com.mt.pc.purchase.service.CreateBillNoService;
import com.mt.pm.production.bo.PmWorkProcedureBo;
import com.mt.pm.production.service.PmSchedulingService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Randy
 * @Date: Create in 2018-01-12 9:16
 * @Description:
 * @Modified By:
 */
@RestController
@Api(value = "手工排产",description = "手工排产")
public class PmSchedulingController extends BaseController {

    @Autowired
    private PmSchedulingService pmSchedulingService;
    @Autowired
    private CreateBillNoService createBillNoService;

    @PostMapping("/api/pm/schedule/start")
    @RequiresPermissions(value = "enterprise:pm:routings:update")
    public Object produceScheduling(@RequestBody Map<String, Object> param, ModelMap modelMap){
        try {

            String companyId = new String((param.get("company_id") == null ? null : param.get("company_id")).toString().trim());
            String poId = (param.get("po_id") == null ? null : param.get("po_id")).toString().trim();
            String startTime = param.get("start_time") == null ? null : param.get("start_time").toString();
            String endTime = param.get("end_time") == null ? null : param.get("end_time").toString();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            int sortType = new Integer(param.get("schedule_mode") == null ? null : param.get("schedule_mode").toString()).intValue();
            String firstTryDate = param.get("first_try_date") == null ? null : param.get("first_try_date").toString();
            Map<String, Object> resultMap = new HashMap();
            //获取有用工序列表
            List<PmWorkProcedureBo> pmWorkProcedureBos = pmSchedulingService.sortPmWorkProcedures(poId,sortType);
            //根据工序列表排产
            if (startTime != null && sortType == 0){
                LocalDateTime startDateTime = LocalDateTime.parse(startTime, dateTimeFormatter);
                resultMap = pmSchedulingService.produceScheduling(pmWorkProcedureBos,startDateTime,0, companyId);
            }
            if (endTime != null && sortType == 1){
                LocalDateTime endDateTime = LocalDateTime.parse(endTime, dateTimeFormatter);
                resultMap = pmSchedulingService.produceScheduling(pmWorkProcedureBos,endDateTime,1, companyId);
            }
            //根据工序列表计算紧迫度
            LocalDate tryDate = LocalDate.parse(firstTryDate,dayFormatter);
            double urgencyLevel = pmSchedulingService.urgencyCalculate(pmWorkProcedureBos, tryDate, companyId);
            //todo 需要对入参进行校验。
            Map<String, Object> map = new HashMap();
            map.put("po_id",poId);
            map.put("po_no",createBillNoService.createBillNo(new Integer(companyId),"PO"));
            map.put("proc_list",resultMap.get("pmWorkProcedureBoList"));
            map.put("schedule_mode",sortType);
            map.put("planStartTime",resultMap.get("planStartTime"));
            map.put("planEndTime",resultMap.get("planEndTime"));
            map.put("urgencyLevel",urgencyLevel);
            modelMap.addAttribute("reasonCode", "111111");
            modelMap.addAttribute("description", "手工排产成功！");
            return setModelMap(modelMap, HttpCode.OK, map);
        } catch (Exception ex) {
            ex.printStackTrace();
            //logger.info(ex.getMessage());
            modelMap.addAttribute("reasonCode", "000000");
            modelMap.addAttribute("description", "手工排产失败！");
            return setModelMap(modelMap, HttpCode.FAIL);
        }

    }

}
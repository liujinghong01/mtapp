package com.mt.pm.production.service.impl;

import com.mt.cms.company.service.CompanyService;
import com.mt.pm.common.dao.*;
import com.mt.pm.common.model.PmPg;
import com.mt.pm.common.model.PmProdOrder;
import com.mt.pm.production.bo.PmShiftSubBo;
import com.mt.pm.production.bo.PmWorkProcedureBo;
import com.mt.pm.production.service.PmProdOrderService;
import com.mt.pm.production.service.PmSchedulingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.mt.pm.production.bo.PmWorkProcedureBo.procSeqComparator;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * @Author: Randy
 * @Date: Create in 2018-01-08 20:30
 * @Description: 工艺路线顺排倒排实现类
 * @Modified By:
 */
@Service
public class PmSchedulingServiceImpl implements PmSchedulingService {

    /**设置默认当天班次中找到值*/
    private boolean findInShift = false;
    /**设置默认在班次第一段中找到*/
    private int shiftNo = 1;
    /**当前工序开始日期时间*/
    private LocalDateTime pmWorkProcedureStartDateTime;
    /**当前工序结束日期时间*/
    private LocalDateTime pmWorkProcedureEndDateTime;
    /**上一个工序的开始日期时间*/
    private LocalDateTime previousStartDateTime;
    /**上一个工序的结束日期时间*/
    private LocalDateTime previousEndDateTime;

    private static String workGroupType = "0";

    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    @Autowired
    private PmShiftSubMapper pmShiftSubMapper;
    @Autowired
    private PmPgMapper pmPgMapper;
    @Autowired
    private PmProdOrderSubMapper pmProdOrderSubMapper;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private PmPgDeviceMapper pmPgDeviceMapper;
    @Autowired
    private PmProdOrderService pmProdOrderService;
    @Autowired
    private PmProdOrderMapper pmProdOrderMapper;


    private static final Logger logger = LoggerFactory.getLogger(PmSchedulingServiceImpl.class);


    @Override
    public Map<String, Object> produceScheduling(List<PmWorkProcedureBo> pmWorkProcedureBos, LocalDateTime scheduringTime, int sortType, String companyId) throws ParseException {
        //1、根据物料编号查询工艺路线，同时过滤无效的工序并根据输入的排序类型进行排序。,0 顺排、 1 倒排
        if (scheduringTime == null){
            scheduringTime = LocalDateTime.now();
        }
        previousStartDateTime = scheduringTime;
        previousEndDateTime = scheduringTime;
        logger.debug("上一个工序的开始时间="+previousStartDateTime+"；上一个工序的结束时间="+previousEndDateTime);
        logger.debug("pmworkprocedureBos="+pmWorkProcedureBos.toString());
        for (PmWorkProcedureBo pmWorkProcedureBo : pmWorkProcedureBos) {
            //判断工序类型：1、生产 2、外协
            //外协
            if (pmWorkProcedureBo.getProcType()==2){
                if (sortType == 0){
                    pmWorkProcedureBo.setPlanStartTime(previousEndDateTime);
                    pmWorkProcedureEndDateTime = previousEndDateTime.plusSeconds(pmWorkProcedureBo.getOutsourcingHours().multiply(new BigDecimal(3600)).longValue());
                    pmWorkProcedureBo.setPlanEndTime(pmWorkProcedureEndDateTime);
                    previousEndDateTime = pmWorkProcedureEndDateTime;
                }else{
                    pmWorkProcedureBo.setPlanEndTime(previousStartDateTime);
                    pmWorkProcedureStartDateTime = previousStartDateTime.minusSeconds(pmWorkProcedureBo.getOutsourcingHours().multiply(new BigDecimal(3600)).longValue());
                    pmWorkProcedureBo.setPlanStartTime(pmWorkProcedureStartDateTime);
                    previousStartDateTime = pmWorkProcedureStartDateTime;

                }
                pmWorkProcedureBo.setPlanRegionLoad(new BigDecimal("0"));
            }else{
                //根据部门id获取公司默认班次
                List<PmShiftSubBo> defaultComShift = pmShiftSubMapper.selectDefaultShiftSubListByPgid(pmWorkProcedureBo.getPgId());
                Map queryConditionMap = new HashMap();
                //顺排
                if (sortType == 0){
                    pmWorkProcedureStartDateTime = previousEndDateTime;
                    //查询一次获取输入日期后10天的日历班次
                    LocalDate pmWorkProcedureStartTimeMonthLater = pmWorkProcedureStartDateTime.toLocalDate().plusDays(10);
                    queryConditionMap.put("pmWorkProcedureStartTime",pmWorkProcedureStartDateTime.toLocalDate());
                    queryConditionMap.put("pmWorkProcedureStartTimeMonthLater",pmWorkProcedureStartTimeMonthLater);
                    queryConditionMap.put("sortType","asc");
                }else{                 //倒排
                    pmWorkProcedureEndDateTime = previousStartDateTime;
                    LocalDate pmWorkProcedureStartTimeMonthBefore = pmWorkProcedureEndDateTime.toLocalDate().minusDays(10);
                    queryConditionMap.put("pmWorkProcedureStartTime",pmWorkProcedureStartTimeMonthBefore);
                    queryConditionMap.put("pmWorkProcedureStartTimeMonthLater",pmWorkProcedureEndDateTime.toLocalDate());
                    queryConditionMap.put("sortType","desc");
                }
                queryConditionMap.put("pgId",pmWorkProcedureBo.getPgId());
                List<PmShiftSubBo> pmShiftSubBos = pmShiftSubMapper.selectPmShiftSubListByDate(queryConditionMap);
                //获取有效日期的班次
                List<PmShiftSubBo> effectShift =  getEffectiveShiftByDay(pmShiftSubBos,defaultComShift,sortType);
                //对有效班次进行排序,0 顺排、 1 倒排
                effectShift = sortShift(effectShift,sortType);
                logger.debug("有效班次段"+effectShift.toString());
                //获取当前工序的开始时间
                for (int i = 0; i < effectShift.size(); i++) {
                    PmShiftSubBo pmShiftSubBo = effectShift.get(i);
                    LocalTime shiftStartTime = LocalTime.parse(pmShiftSubBo.getStartTime(),timeFormatter);
                    LocalTime shiftEndTime = LocalTime.parse(pmShiftSubBo.getEndTime(),timeFormatter);
                    if (sortType == 0){
                        LocalTime procedureStartTime = LocalTime.parse(pmWorkProcedureStartDateTime.format(timeFormatter),timeFormatter);
                        if (procedureStartTime.isBefore(shiftStartTime)||procedureStartTime.equals(shiftStartTime)){
                            pmWorkProcedureStartDateTime = LocalDateTime.of(pmWorkProcedureStartDateTime.toLocalDate(),shiftStartTime);
                            findInShift = true;
                            shiftNo = i;
                            break;
                        }else if (shiftStartTime.isBefore(procedureStartTime) && procedureStartTime.isBefore(shiftEndTime)){
                            findInShift = true;
                            shiftNo = i;
                            effectShift.get(i).setStartTime(pmWorkProcedureStartDateTime.format(timeFormatter));
                            break;
                        }
                    } else {
                        LocalTime procedureEndTime = LocalTime.parse(pmWorkProcedureEndDateTime.format(timeFormatter),timeFormatter);
                        if (shiftEndTime.isBefore(procedureEndTime) || shiftEndTime.equals(procedureEndTime)){
                            pmWorkProcedureEndDateTime = LocalDateTime.of(pmWorkProcedureEndDateTime.toLocalDate(),shiftEndTime);
                            findInShift = true;
                            shiftNo = i;
                            break;
                        } else if (shiftStartTime.isBefore(procedureEndTime) && procedureEndTime.isBefore(shiftEndTime)){
                            findInShift = true;
                            shiftNo = i;
                            effectShift.get(i).setEndTime(pmWorkProcedureEndDateTime.format(timeFormatter));
                            break;
                        }
                    }
                }
                //如果在当天班次没有找到
                if (!findInShift) {
                    if (sortType == 0) {
                        pmWorkProcedureStartDateTime = pmWorkProcedureStartDateTime.plusDays(1);
                    } else {
                        pmWorkProcedureEndDateTime = pmWorkProcedureEndDateTime.minusDays(1);
                    }
                    effectShift =  getEffectiveShiftByDay(pmShiftSubBos,defaultComShift,sortType);
                    //对有效班次进行排序,0 顺排、 1 倒排
                    effectShift = sortShift(effectShift,sortType);
                    if (sortType == 0) {
                        pmWorkProcedureStartDateTime = LocalDateTime.of(pmWorkProcedureStartDateTime.toLocalDate(),LocalTime.parse(effectShift.get(0).getStartTime(),timeFormatter));
                    } else {
                        pmWorkProcedureEndDateTime = LocalDateTime.of(pmWorkProcedureEndDateTime.toLocalDate(),LocalTime.parse(effectShift.get(0).getEndTime(),timeFormatter));
                    }
                    shiftNo = 0;
                }
                //保存当前工序的开日期时间
                if (sortType == 0) {
                    pmWorkProcedureBo.setPlanStartTime(pmWorkProcedureStartDateTime);
                    pmWorkProcedureEndDateTime = pmWorkProcedureStartDateTime;
                } else {
                    pmWorkProcedureBo.setPlanEndTime(pmWorkProcedureEndDateTime);
                    pmWorkProcedureStartDateTime = pmWorkProcedureEndDateTime;
                }
                //当前工序的总时长
                long totalTime;
                //班次内的剩余时长
                long remainderTime = 0;
                //查询当前工序的工作部门属性是设备还是人工？
                PmPg pmPg = pmPgMapper.selectByPgId(pmWorkProcedureBo.getPgId());
                //未完工数量
                int count = pmWorkProcedureBo.getProcQty() != null?pmWorkProcedureBo.getProcQty():1;
                BigDecimal auxiliaryHours = pmWorkProcedureBo.getAuxiliaryHours();
                if (pmPg!=null&&null!=pmPg.getPgType()&&("0").equals(pmPg.getPgType())){
                    BigDecimal machineWorkHours = pmWorkProcedureBo.getMachineWorkHours();
                    //计算最小单位分钟
                    totalTime = (machineWorkHours.multiply(new BigDecimal(count)).add(auxiliaryHours)).multiply(new BigDecimal("60")).longValue();
                    pmWorkProcedureBo.setTaskHours(machineWorkHours.multiply(new BigDecimal(count)));
                } else {
                    BigDecimal laborWorkHours = pmWorkProcedureBo.getLaborWorkHours();
                    totalTime = (laborWorkHours.multiply(new BigDecimal(count)).add(auxiliaryHours)).multiply(new BigDecimal("60")).longValue();
                    pmWorkProcedureBo.setTaskHours(laborWorkHours.multiply(new BigDecimal(count)));
                }
                /*计算工序结束时间
                如果当天班次的有效时间大于工序总时长，结束时间就在当天班次内，否则对其日期加一，查询第二天班次再进行比较。*/
                //todo：（randy 2018-01-18 [2018-01-28]） 如果分解数大于1，还需要总时长/分解数，取整。
                totalTime = getPmWorkProcedureEndTime(effectShift, totalTime, sortType);
                if (totalTime > 0) {
                    while (totalTime > 0) {
                        if (sortType == 0) {
                            pmWorkProcedureEndDateTime = pmWorkProcedureEndDateTime.plusDays(1);
                        } else {
                            pmWorkProcedureStartDateTime = pmWorkProcedureStartDateTime.minusDays(1);
                        }
                        effectShift = getEffectiveShiftByDay(pmShiftSubBos, defaultComShift, sortType);
                        effectShift = sortShift(effectShift, sortType);
                        long effectShiftDayTime = 0;
                        for (PmShiftSubBo pmShiftSubBo : effectShift) {
                            effectShiftDayTime = effectShiftDayTime + pmShiftSubBo.getShiftMins();
                        }
                        totalTime = totalTime - effectShiftDayTime;
                        if (totalTime >= 0){
                            remainderTime = totalTime;
                        }
                        shiftNo = 1;
                    }
                    if (remainderTime >= 0) {
                        getPmWorkProcedureEndTime(effectShift, remainderTime, sortType);
                    }
                }
                if (sortType == 0) {
                    pmWorkProcedureBo.setPlanEndTime(pmWorkProcedureEndDateTime);
                } else {
                    pmWorkProcedureBo.setPlanStartTime(pmWorkProcedureStartDateTime);
                }

                BigDecimal regionWeight = getRegionWeight(companyId, pmWorkProcedureBo.getPgId(), pmWorkProcedureBo.getPgType().toString(), defaultComShift,pmWorkProcedureBo.getPlanStartTime(),pmWorkProcedureBo.getPlanEndTime());
                pmWorkProcedureBo.setPlanRegionLoad(regionWeight);
            }
            previousStartDateTime = pmWorkProcedureStartDateTime;
            previousEndDateTime = pmWorkProcedureEndDateTime;
            logger.debug(pmWorkProcedureBo.getId()+pmWorkProcedureBo.getProcName()+"本生产工序计划开始="+pmWorkProcedureBo.getPlanStartTime()+"----计划结束时间="+pmWorkProcedureBo.getPlanEndTime());

        }
        Map<String, Object> reslutMap = new HashMap<>();
        reslutMap.put("pmWorkProcedureBoList",pmWorkProcedureBos);
        if (sortType == 0) {
            reslutMap.put("planStartTime", scheduringTime);
            reslutMap.put("planEndTime", pmWorkProcedureEndDateTime );
        } else {
            reslutMap.put("planStartTime", pmWorkProcedureStartDateTime);
            reslutMap.put("planEndTime", scheduringTime);
        }
        return reslutMap;
    }

    /**
     * @Author: Randy
     * @Date:2018/1/31 8:43
     * @params:
     * @return：
     * @Description： 计算本零件的紧迫度
     */
    @Override
    public double urgencyCalculate(List<PmWorkProcedureBo> pmWorkProcedureBos, LocalDate tryDate ,String companId) {
        long period = LocalDate.now().until(tryDate,DAYS);
        BigDecimal totalTaskPeriod =new BigDecimal("0");
        for (PmWorkProcedureBo pmWorkProcedureBo:pmWorkProcedureBos) {
            if (pmWorkProcedureBo.getProcType()==2){
                totalTaskPeriod = totalTaskPeriod.add(pmWorkProcedureBo.getOutsourcingHours());
            } else {
                totalTaskPeriod = totalTaskPeriod.add(pmWorkProcedureBo.getTaskHours());
            }
        }
        //保留3位小数
        BigDecimal urgency = totalTaskPeriod.divide(new BigDecimal(period).multiply(new BigDecimal(24)),3,BigDecimal.ROUND_HALF_UP);
        //本层零件的紧迫度 = 上层零件的紧迫度 + urgency （传入poid 获取上层零件BOM信息，然后根据返回数据获取上层零件的生产定单紧迫度，多个生产定单取最大值）
        //1.根据poid 查询获取上层零件的节点信息
        Map<String, Object> upNodeInfo = pmProdOrderService.selectBomPNode(pmWorkProcedureBos.get(0).getPoId());
        if (upNodeInfo != null) {
            upNodeInfo.put("compan_id",companId);
            //2.根据bom_id、mat_id、company_id获取生产定单信息，排序生产定单的紧迫度数值，选择最大的进行计算
            List<Map<String ,Object>> resultMapList = pmProdOrderMapper.selectProdOrderByCondition(upNodeInfo);
            Collections.sort(resultMapList, (o1, o2) -> {
                BigDecimal urgency1 = new BigDecimal(o1.get("urgency_level").toString());
                BigDecimal urgency2 = new BigDecimal(o2.get("urgency_level").toString());
                return urgency1.compareTo(urgency2);
            });
            urgency = urgency.add(new BigDecimal(resultMapList.get(resultMapList.size()-1).get("urgency_level").toString()));
        }
        logger.debug("本工序紧迫度=" + urgency.doubleValue());
        return urgency.doubleValue();
    }

    /**
     * @Author: Randy
     * @Date:2018/2/6 13:14
     * @params:
     * @return：
     * @Description： 修改下层零件的紧迫度
     */
    @Override
    public void updateUrgencyCalculate(String poId){
        List<PmWorkProcedureBo> pmWorkProcedureBos = sortPmWorkProcedures(poId, 0);
        // 需要在保存的时候去处理----下层零件的紧迫度也需要跟新，计算公式： 下层零件的紧迫度 = 本层零件修改后的紧迫度 + 下层零件原来的紧迫度（下层所有零件紧迫度都会变化）
        //1.根据poid 获取本次零件的紧迫度， 查询获取下层零件的节点信息
        PmProdOrder pmProdOrder = pmProdOrderMapper.selectByPrimaryKey(Long.getLong(poId));
        BigDecimal thisLevelUrgency = pmProdOrder.getUrgencyLevel();
        BigDecimal subLevelUrgency;
        // 2.根据bom_id、mat_id、company_id获取生产定单信息
        List<Map<String, Object>> nextLevelUrgencyList = pmProdOrderService.selectProdOrderByPBomNode(pmProdOrder.getBomId());
        // 3.查询下层零件的生产定单信息，进行紧迫度数据更新
        for (Map<String, Object> subLevel: nextLevelUrgencyList) {
            subLevelUrgency = new BigDecimal(subLevel.get("urgency_level").toString());
            subLevel.put("urgency_level", subLevelUrgency.add(thisLevelUrgency));
        }
        pmProdOrderMapper.batchUpdate(nextLevelUrgencyList);
    }


    /**
     * 根据物料查询和排序类型查询工序集
     * @param poId
     * @param sortType
     * @return
     */
    @Override
    public List<PmWorkProcedureBo> sortPmWorkProcedures(String poId,int sortType){
        List<PmWorkProcedureBo> pmWorkProcedureBos = pmProdOrderSubMapper.selectByPoId(poId);
        Collections.sort(pmWorkProcedureBos,procSeqComparator);
        if (sortType == 1){
            Collections.reverse(pmWorkProcedureBos);
        }
        return pmWorkProcedureBos;
    }

    /**
     * 重新对班次段进行排序，如果有跨天时间段，还要拆分跨天时间段。
     * @param effectShift
     * @param sortType
     * @return
     */
    private List<PmShiftSubBo> sortShift(List<PmShiftSubBo> effectShift,int sortType) {
        Iterator<PmShiftSubBo> iterator = effectShift.iterator();
        while (iterator.hasNext()) {
            PmShiftSubBo pmShiftSubBo = iterator.next();
            LocalTime shiftStartTime = LocalTime.parse(pmShiftSubBo.getStartTime(),timeFormatter);
            LocalTime shiftEndTime = LocalTime.parse(pmShiftSubBo.getEndTime(),timeFormatter);
            if (shiftStartTime.isAfter(shiftEndTime)) {
                pmShiftSubBo.setEndTime("24:00:00");
                PmShiftSubBo addShiftSubBo = new PmShiftSubBo();
                addShiftSubBo.setIsOverDay("1");
                addShiftSubBo.setStartTime("00:00:00");
                addShiftSubBo.setEndTime(pmShiftSubBo.getEndTime());
                effectShift.add(addShiftSubBo);
                break;
            }
        }
        Collections.sort(effectShift, Comparator.comparing(PmShiftSubBo::getStartTime));
        if (sortType==1) {
            Collections.reverse(effectShift);
        }
        return effectShift;
    }

    /**
     * 通过日期得到有效工作班次
     * @param pmShiftSubBos
     * @param defaultComShift
     * @return
     */
    private List<PmShiftSubBo> getEffectiveShiftByDay(List<PmShiftSubBo> pmShiftSubBos,List<PmShiftSubBo> defaultComShift,int sortType){
        List<PmShiftSubBo> effectShift = new ArrayList<>();
        if (pmShiftSubBos == null||pmShiftSubBos.size() == 0){
            return defaultComShift;
        }
        for (PmShiftSubBo pmShiftSubBo : pmShiftSubBos) {
            if (sortType == 0){
                //判断开始有效时间段在那一天日期
                if (pmShiftSubBo.getTheDate().toLocalDate().equals(pmWorkProcedureStartDateTime.toLocalDate())){
                    if (pmShiftSubBo.getShiftMins()>0){
                        effectShift.add(pmShiftSubBo);
                    } else {
                        pmWorkProcedureStartDateTime = pmWorkProcedureStartDateTime.plusDays(1);
                    }
                }else{//没有找到当前日期班次，查看默认班次，如果当前日期是周六和周日是休假，如果不是启用默认班次
                    if (effectShift.size()>0){
                        break;
                    } else {
                        DayOfWeek dayOfWeek = pmWorkProcedureStartDateTime.getDayOfWeek();
                        if (dayOfWeek.getValue()==6||dayOfWeek.getValue()==7){
                            pmWorkProcedureStartDateTime = pmWorkProcedureStartDateTime.plusDays(1);
                        } else {
                            effectShift = defaultComShift;
                        }
                    }
                }
            }else{
                //判断开始有效时间段在那一天日期
                if (pmShiftSubBo.getTheDate().toLocalDate().equals(pmWorkProcedureEndDateTime.toLocalDate())){
                    if (pmShiftSubBo.getShiftMins()>0){
                        effectShift.add(pmShiftSubBo);
                    } else {
                        pmWorkProcedureEndDateTime = pmWorkProcedureEndDateTime.minusDays(1);
                    }
                }else{//没有找到当前日期班次，查看默认班次，如果当前日期是周六和周日是休假，如果不是启用默认班次
                    if (effectShift.size()>0){
                        break;
                    } else {
                        DayOfWeek dayOfWeek = pmWorkProcedureEndDateTime.getDayOfWeek();
                        if (dayOfWeek.getValue()==6||dayOfWeek.getValue()==7){
                            pmWorkProcedureEndDateTime = pmWorkProcedureEndDateTime.minusDays(1);
                        } else {
                            effectShift = defaultComShift;
                        }
                    }
                }
            }
        }
        logger.info("获取的有效班次："+effectShift.toString());
        return effectShift;
    }

    /**
     * 计算工序是否可以在当天班次中完成。
     * @param effectShift 有效班次
     * @param totalTime 工序总工时
     * @return 返回还剩工时数
     * @throws ParseException
     */
    private Long getPmWorkProcedureEndTime(List<PmShiftSubBo> effectShift,long totalTime,int sortType) {
        for (int i = shiftNo; i < effectShift.size() ; i++) {
            PmShiftSubBo pmShiftSubBo = effectShift.get(i);
            LocalTime startTime = LocalTime.parse(pmShiftSubBo.getStartTime(),timeFormatter);
            LocalTime endTime = LocalTime.parse(pmShiftSubBo.getEndTime(),timeFormatter);
            long period = startTime.until(endTime,MINUTES);
            if (totalTime <= period) {
                if (sortType == 0) {
                    pmWorkProcedureEndDateTime =  LocalDateTime.of(pmWorkProcedureEndDateTime.toLocalDate(),startTime.plusMinutes(totalTime));
                } else {
                    pmWorkProcedureStartDateTime =  LocalDateTime.of(pmWorkProcedureStartDateTime.toLocalDate(),endTime.minusMinutes(totalTime));
                }
                totalTime = totalTime - period;
                break;
            }
            totalTime = totalTime - period;
        }
        return totalTime;
    }

    /**
     * @Author: Randy
     * @Date:2018/1/31 15:55
     * @params: pgId 部门id
     * @return：
     * @Description： 获取时间段区间内部门的负荷
     */
    private BigDecimal getRegionWeight(String companyId, long pgId, String pgType, List<PmShiftSubBo> defaultComShift,LocalDateTime compareStartTime, LocalDateTime compareEndTime){
        Map queryConditionMap = new HashMap();
        queryConditionMap.put("pgId", pgId);
        queryConditionMap.put("companyId", companyId);
        queryConditionMap.put("startTime", compareStartTime);
        queryConditionMap.put("endTime", compareEndTime);
        //查询这个时间段内部门需要加工的工序列表集
        List<PmWorkProcedureBo> pmWorkProcedureBos = pmProdOrderSubMapper.selectByPgInfo(queryConditionMap);
        queryConditionMap.put("pmWorkProcedureStartTime",compareStartTime.toLocalDate());
        queryConditionMap.put("pmWorkProcedureStartTimeMonthLater",compareEndTime.toLocalDate());
        //查询这个时间段部门的班次
        List<PmShiftSubBo> pmShiftSubBos = pmShiftSubMapper.selectPmShiftSubListByDate(queryConditionMap);
        LocalDateTime startDateTime;
        LocalDateTime endDateTime;
        BigDecimal regionWeight;
        //工时有效时长统计，单位为分钟
        long allWorkProcedureTime = 0;
        if (pmWorkProcedureBos != null && pmWorkProcedureBos.size() > 0){
            for (PmWorkProcedureBo pmWorkProcedureBo:pmWorkProcedureBos) {
                startDateTime = pmWorkProcedureBo.getPlanStartTime();
                endDateTime = pmWorkProcedureBo.getPlanEndTime();
                //判断时间区间范围后，进行有效时间的计算
                if (startDateTime.isBefore(compareStartTime) && endDateTime.isAfter(compareStartTime)){
                    allWorkProcedureTime += getEffectPeriod(pmShiftSubBos, defaultComShift, compareStartTime, endDateTime).get("workTimeTotal").longValue();
                    continue;
                }
                if (startDateTime.isAfter(compareStartTime) && startDateTime.isBefore(compareEndTime) && endDateTime.isAfter(compareEndTime)){
                    allWorkProcedureTime += getEffectPeriod(pmShiftSubBos, defaultComShift, startDateTime, compareEndTime).get("workTimeTotal").longValue();
                    continue;
                }
                if (startDateTime.isAfter(compareStartTime) && startDateTime.isBefore(compareEndTime) && endDateTime.isBefore(compareEndTime)){
                    allWorkProcedureTime += getEffectPeriod(pmShiftSubBos, defaultComShift, startDateTime, endDateTime).get("workTimeTotal").longValue();
                    continue;
                }
                if (startDateTime.isBefore(compareStartTime) && endDateTime.isAfter(compareEndTime)) {
                    allWorkProcedureTime += pmWorkProcedureBo.getTaskHours().multiply(new BigDecimal(60)).longValue();
                    continue;
                }
                if (startDateTime.isEqual(compareStartTime) && endDateTime.isEqual(compareEndTime)){
                    allWorkProcedureTime += pmWorkProcedureBo.getTaskHours().multiply(new BigDecimal(60)).longValue();
                    continue;
                }

            }
        }
        //计算区间负荷值
        /**
         * 得到总工时时长，判断当前部门是人工还是机器类型部门，1、如果是人工 { 负荷 = allWorkProcedureTime / （单人每日工时*部门人数） }；
         * 2、如果是机器 { 负荷 = allWorkProcedureTime / （日历时长*设备数量）--- 设备数量不包括期间在维修和已报废的}
         */
        if (null!=pgType&&workGroupType.equals(pgType)){
            //设备日历时长
            long deviceTimeLong = getEffectPeriod(pmShiftSubBos, defaultComShift, compareStartTime, compareEndTime).get("deviceTimeTotal").longValue();
            queryConditionMap.put("pg_id", pgId);
            int totalCount = pmPgDeviceMapper.totalCount(queryConditionMap);
            regionWeight = new BigDecimal(allWorkProcedureTime).divide(new BigDecimal(totalCount).multiply(new BigDecimal(deviceTimeLong)));
        } else {
            //部门人数
            Integer personCount = companyService.getAllUserCountByDepId(new Integer(companyId), new Integer((int) pgId));
            //获取单人每日工时
            BigDecimal dayHours = pmShiftSubBos.get(0).getDayHours();
            regionWeight = new BigDecimal(allWorkProcedureTime).divide(dayHours.multiply(new BigDecimal(personCount)));
        }
        return regionWeight;
    }

    /**
     * @Author: Randy
     * @Date:2018/2/3 10:43
     * @params: pmShiftSubBos是有效班次信息，defaultComShift是默认班次信息
     * @return： 某个工序在期间内的工时时长
     * @Description：
     */
    private Map<String ,Long> getEffectPeriod(List<PmShiftSubBo> pmShiftSubBos, List<PmShiftSubBo> defaultComShift, LocalDateTime compareStartTime, LocalDateTime compareEndTime){
        /**
         * 首先确定compareStartTime 到 compareEndTime的班次信息
         */
        Map<String, Long> map = new HashMap<>();
        long days = compareStartTime.until(compareEndTime,DAYS);
        LocalDate tempDate = compareStartTime.toLocalDate();
        List<PmShiftSubBo> shiftBoList = new ArrayList<>();
        LocalTime st;
        LocalTime et;
        long workTimeTotal = 0;
        long deviceTimeTotal = 0;
        if (days > 0){
            do {
                for (PmShiftSubBo pmShiftSubBo : pmShiftSubBos) {
                    if (pmShiftSubBo.getTheDate().toLocalDate().isEqual(tempDate)){
                        shiftBoList.add(pmShiftSubBo);
                    }
                }
                if (shiftBoList.size() == 0){
                    DayOfWeek dayOfWeek = tempDate.getDayOfWeek();
                    if (dayOfWeek.getValue()==6||dayOfWeek.getValue()==7){
                        tempDate = tempDate.plusDays(1);
                        continue;
                    }
                    shiftBoList.addAll(defaultComShift);
                }
                for (PmShiftSubBo pmShiftSubBo: sortShift(shiftBoList,0)) {
                    st = LocalTime.parse(pmShiftSubBo.getStartTime(), timeFormatter);
                    et = LocalTime.parse(pmShiftSubBo.getEndTime(), timeFormatter);
                    //计算开始日期当天的需要的工作时长
                    if (pmShiftSubBo.getTheDate().toLocalDate().isEqual(compareStartTime.toLocalDate())){
                        if (compareStartTime.toLocalTime().until(st, MINUTES) >= 0){
                            workTimeTotal += pmShiftSubBo.getShiftMins();
                        }else if (compareStartTime.toLocalTime().isAfter(st) && compareStartTime.toLocalTime().isBefore(et)){
                            workTimeTotal += compareStartTime.until(et, MINUTES);
                        }
                        //计算结束日期当天的需要的工作时长
                    } else if (pmShiftSubBo.getTheDate().toLocalDate().isEqual(compareEndTime.toLocalDate())) {
                        if (et.until(compareEndTime.toLocalTime(), MINUTES) >= 0){
                            workTimeTotal += pmShiftSubBo.getShiftMins();
                        }else if (compareEndTime.toLocalTime().isAfter(st) && compareEndTime.toLocalTime().isBefore(et)){
                            workTimeTotal += st.until(compareEndTime, MINUTES);
                        }
                    } else {
                        workTimeTotal += pmShiftSubBo.getShiftMins();
                    }
                    deviceTimeTotal += pmShiftSubBo.getShiftMins();
                }
                tempDate = tempDate.plusDays(1);
            } while (!tempDate.isEqual(compareEndTime.toLocalDate()));
        } else { //开始时间和结束时间是在同一天，只需要计算当天班次，不考虑当天是否休假（如果休假就不会有工时时段）
            for (PmShiftSubBo pmShiftSubBo : pmShiftSubBos) {
                if (pmShiftSubBo.getTheDate().toLocalDate().isEqual(compareStartTime.toLocalDate())){
                    shiftBoList.add(pmShiftSubBo);
                }
            }
            if (shiftBoList.size() == 0){
                shiftBoList = defaultComShift;
            }
            for (PmShiftSubBo pmShiftSubBo: sortShift(shiftBoList,0)) {
                //查出来的首个班次日期是否和开始统计日期相同
                st = LocalTime.parse(pmShiftSubBo.getStartTime(), timeFormatter);
                et = LocalTime.parse(pmShiftSubBo.getEndTime(), timeFormatter);
                //找与日期相符的统计对应的班次时长
                if (compareStartTime.toLocalTime().until(st, MINUTES) >= 0){
                    if (compareEndTime.until(et, MINUTES) >= 0){
                        workTimeTotal += st.until(compareEndTime.toLocalTime(), MINUTES);
                        break;
                    }else{
                        workTimeTotal += pmShiftSubBo.getShiftMins();
                    }
                }else if (compareStartTime.toLocalTime().isAfter(st) && st.until(compareEndTime.toLocalTime(),MINUTES) >= 0){
                    if (compareEndTime.until(et, MINUTES) >= 0){
                        workTimeTotal += compareStartTime.until(compareEndTime.toLocalTime(), MINUTES);
                        break;
                    }else{
                        workTimeTotal += compareStartTime.until(et, MINUTES);
                    }
                }
                deviceTimeTotal += pmShiftSubBo.getShiftMins();
            }
        }
        map.put("deviceTimeTotal", deviceTimeTotal);
        map.put("workTimeTotal", workTimeTotal);
        return map;
    }

}

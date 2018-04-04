package com.mt.pm.production.service.impl;
/**
 * created by fhk on 2018.1.2
 *
 * 报价单参考及关联
 *
 */

import com.mt.cms.common.model.OrigUserCompany;
import com.mt.cms.company.service.CompanyService;
import com.mt.pc.purchase.service.CreateBillNoService;
import com.mt.pm.common.dao.*;
import com.mt.pm.common.model.*;
import com.mt.pm.production.service.PmConfirmWorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
* @Author: Fhk
* @Description:
* @create    2018/1/10 13:11
* @Modified By:
*/
@Service
public class PmConfirmWorkTimeServiceImpl implements PmConfirmWorkTimeService {

    @Autowired
    private PmTicketAllocateMapper pmTicketAllocateMapper;

    @Autowired
    private PmPgDeviceMapper pmPgDeviceMapper;

    @Autowired
    private PmPgMemberMapper pmPgMemberMapper;

    @Autowired
    private PmProdOrderMapper pmProdOrderMapper;

    @Autowired
    private PmProdOrderSubMapper pmProdOrderSubMapper;

    @Autowired
    private PmPgMapper pmPgMapper;

    @Autowired
    private PmTicketConfirmMapper pmTicketConfirmMapper;

    @Autowired
    private PmTicketInceptMapper pmTicketInceptMapper;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CreateBillNoService createBillNoService;

    /**
     * 根据人员Id获得其所在的生产小组，注意一个人员可以有多个生产小组
     * @param companyId
     * @param memberId
     * @return
     */
    @Override
    public Long[] getPgIdsByMemberId(Integer companyId, Long memberId) {
        //为设备指定负责人，设备与负责人为同一生产小组
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", companyId);
        map.put("memberId", memberId);
        List<PmPg> pmPgList = pmPgMapper.selectByMemberId(map);
        if (pmPgList == null || pmPgList.size() <1) { return null; }
        Long[] pgIdArray = new Long[pmPgList.size()];
        int index = 0;
        for(PmPg pmPg: pmPgList) {
            pgIdArray[index++] = pmPg.getPgId();
        }
        return pgIdArray;
    }

    @Override
    public Long[] getPgIdsByDeviceId(Integer companyId, Long memberId) {
        //为设备指定负责人，设备与负责人为同一生产小组
        return  null;
    }
    /**
     * 根据工序Id查询 为该任务指派的资源（人或者设备）
     * 资源结果集中包括已分配的，也包含未分配的，以是否分配的标志来标识
     * @param popId
     * @return
     */
    @Override
    public Map<String, Object>queryAllResourceByPopId(Long popId) {
        PmProdOrderSub pmProdOrderSub = pmProdOrderSubMapper.selectByPrimaryKey(popId);
        PmPg pmPg = pmPgMapper.selectByPgId(pmProdOrderSub.getPgId());
        List<Map<String, Object>> retMapList;
        //类型：0机器,1人工
        if ("0".equals(pmPg.getPgType())) {
            retMapList = pmTicketAllocateMapper.selectAllDeviceByPopId(popId);
        } else {
            retMapList = pmTicketAllocateMapper.selectAllMemberByPopId(popId);
        }
        Map map = new HashMap<>();
        map.put("device_list", retMapList);
        map.put(" pg_type", pmPg.getPgType());
        map.put("pop_id", popId);
        return map;
    }

    /**
     * 查询某生产小组的待领取任务，以及已领未加工任务
     * @param map
     * @param totalCount
     * @return
     */
    @Override
    public List<Map<String, Object>> selectTicketInceptList(Map map, StringBuilder totalCount) {
        Long[] pgIdArray = this.getPgIdsByMemberId(Integer.valueOf(map.get("company_id").toString()), Long.valueOf(map.get("uid").toString()));
        if (pgIdArray == null || pgIdArray.length < 1) {
            throw  new IllegalArgumentException("该用户没有所在的生产小组");
        }
        map.put("pgIdArray", pgIdArray);
        //分页
        ////totalCount.append(pmTicketConfirmMapper.getTotalCountByConditions(map) + "");
        //return pmTicketConfirmMapper.selectByConditions(map);
        List<Map<String, Object>> inceptList = pmTicketConfirmMapper.selectByConditions(map);
        //Map<String, Object> allocatedResMap = this.queryAllResourceByPopId(Long.valueOf(map.get("pop_id").toString()));
        ArrayList pgList = new ArrayList();
        ArrayList popList = new ArrayList();
        Map<String, Object> onePgMap;
        Long pgId = 0L;
        Map<String, Object> prevItemPop = null;
        for (Map<String, Object> itemPop: inceptList) {
            if (!pgId.equals(0L) && !pgId.equals(itemPop.get("pg_id"))) {
                onePgMap = new HashMap<>();
                onePgMap.put("pg_id", prevItemPop.get("pg_id"));
                onePgMap.put("pg_name", prevItemPop.get("pg_name"));
                onePgMap.put("pg_type", prevItemPop.get("pg_type"));
                onePgMap.put("pop_list", popList);
                pgList.add(onePgMap);

                popList = new ArrayList();
            }
            /*if (itemPop.get("pg_id") == null) {
                continue;
                //throw new IllegalArgumentException("工序不存在生产小组");
            }*/
            prevItemPop = itemPop;
            pgId = Long.valueOf(itemPop.get("pg_id").toString());
            popList.add(itemPop);
            //////////////////////////////////////////////////////
            Long popId = Long.valueOf(itemPop.get("pop_id").toString());
            ////PmProdOrderSub pmProdOrderSub = pmProdOrderSubMapper.selectByPrimaryKey(popId);
            PmPg pmPg = pmPgMapper.selectByPgId(pgId);
            itemPop.put("pg_type", pmPg.getPgType());
            List<Map<String, Object>> allocatedList = pmTicketAllocateMapper.selectAllocatedResByPopId(popId);
            ArrayList<Map<String, Object>> arrAllocatedRes = new ArrayList<>();
            for (Map<String, Object> itemAllocated : allocatedList) {
                Map<String, Object> itemRes = new HashMap<>();
                if ("0".equals(pmPg.getPgType())) {
                    itemRes.put("resource_id", itemAllocated.get("device_id"));
                    itemRes.put("resource_name", itemAllocated.get("device_name"));
                } else {
                    itemRes.put("resource_id", itemAllocated.get("member_id"));
                    itemRes.put("resource_name", itemAllocated.get("member_name"));
                }
                arrAllocatedRes.add(itemRes);
            }
            itemPop.put("allocated_list", arrAllocatedRes);
        }
        ////////////////////
        if (inceptList.size() > 0) {
            onePgMap = new HashMap<>();
            onePgMap.put("pg_id", prevItemPop.get("pg_id"));
            onePgMap.put("pg_name", prevItemPop.get("pg_name"));
            onePgMap.put("pg_type", prevItemPop.get("pg_type"));
            onePgMap.put("pop_list", popList);
            pgList.add(onePgMap);
        }

        return pgList;
    }

    /**
     * 查询小组的报工列表：包括任务列表，资源列表，完工统计
     * @param param
     * @param totalCount
     * @return
     */
    @Override
    public Object selectPgConfirmList(Map param, StringBuilder totalCount) {
        Long[] pgIdArray = this.getPgIdsByMemberId(Integer.valueOf(param.get("company_id").toString()), Long.valueOf(param.get("uid").toString()));
        if (pgIdArray == null || pgIdArray.length < 1) {
            throw  new IllegalArgumentException("该用户没有所在的生产小组");
        }
        //param.put("pgIdArray", pgIdArray);
        String type = param.get("type") == null ? "" : param.get("type").toString().trim();
        Long uid = param.get("uid") == null || param.get("uid").toString().isEmpty() ? null : Long.valueOf(param.get("uid").toString());

        ArrayList retPgList = new ArrayList();
        Map<String, Object> onePgMap;
        Map<String, Object> resourceMap;
        byte[] popStatusArray = new byte[]{3, 5};
        param.put("popStatusArray", popStatusArray);
        for(Long pgId: pgIdArray) {
            param.put("pg_id", pgId);
            PmPg pmPg = pmPgMapper.selectByPgId(Long.valueOf(param.get("pg_id").toString()));
            onePgMap = new HashMap<>();
            resourceMap = new HashMap<>();
            switch (type) {
                case "1":
                    //指派给我的或我的设备的,未加工或者部分完工的任务
                    //以及部门未指派且未加工和部分完工的任务
                    //totalCount.append(pmTicketConfirmMapper.getMyCanProcessPopCount(param) + "");

                    onePgMap.put("pop_list", pmTicketConfirmMapper.selectMyCanProcessPop(param));
                    break;
                case "2":
                    if ("0".equals(pmPg.getPgType())) {
                        //资源列表显示所有本小组设备，可以看到每个设备的加工状态,并标注我负责的设备
                        //totalCount.append(pmTicketConfirmMapper.getMyPgDeviceCount(param) + "");
                        resourceMap.put("device_list", pmTicketConfirmMapper.getMyPgDevice(param));
                    } else {
                        //资源列表显示所有本小组成员及其加工状态，并标注是否为当前用户
                        //totalCount.append(pmTicketConfirmMapper.getMyPgMemberCount(param) + "");
                        //这里的分页有问题，可能将同一组加式人员割裂
                        List<Map<String, Object>> mapList = pmTicketConfirmMapper.getMyPgMember(param);
                        Long batchProcId = null;
                        List<Map<String, Object>> memberLis = new ArrayList<>();
                        Map<String, Object> subMap;

                        ArrayList<Object> mapArr = null;
                        for (Map map : mapList) {
                            if (batchProcId != null && batchProcId.equals(map.get("batch_proc_id"))) {
                                mapArr.add(map);
                            } else if (mapArr != null) {
                                subMap = new HashMap<>();
                                subMap.put("member_sub_list", mapArr);
                                //subMap.put("pop_id", popId);
                                memberLis.add(subMap);
                                mapArr = new ArrayList<>();
                                mapArr.add(map);
                            } else {
                                mapArr = new ArrayList<>();
                                mapArr.add(map);
                            }
                            batchProcId = map.get("batch_proc_id") == null ? null : Long.valueOf(map.get("batch_proc_id").toString());
                        }
                        if (mapArr != null) {
                            subMap = new HashMap<>();
                            subMap.put("member_sub_list", mapArr);
                            //subMap.put("pop_id", popId);
                            memberLis.add(subMap);
                        }
                        resourceMap.put("member_list", memberLis);
                    }
                    onePgMap.put("resource_info", resourceMap);
                    break;
                case "3":
                    onePgMap.put("finished_list", null);
                    break;

                default:
                    return null;
            }
            onePgMap.put("pg_id", pgId);
            onePgMap.put("pg_name", pmPg.getPgName());
            onePgMap.put("pg_type", pmPg.getPgType());
            PmPgMember pmPgMember = this.selectByPgIdAndMemberId(pgId, uid);
            if (pmPgMember != null) {
                onePgMap.put("uid_status", pmPgMember.getMemberStatus());
            }
            retPgList.add(onePgMap);
        }
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("pg_list", retPgList);
        retMap.put("type", type);
        retMap.put("uid", uid);
        return retMap;
    }

    @Override
    public List<Map<String, Object>> selectProcInfoByResourceId(Map map) {
        List<Map<String, Object>> processingMapList = pmTicketConfirmMapper.selectProcInfoByResourceId(map);
        ArrayList workerList;
        Map<String, Object> iteMap;
        Long memberId;
        Long pgId = Long.valueOf(map.get("pg_id").toString());
        for (Map<String, Object> processingMap: processingMapList) {
            workerList = new ArrayList();
            List<PmTicketConfirm> pmTicketConfirmList = this.selectByPopId(Long.valueOf(processingMap.get("pop_id").toString()), "0", null, null);
            for(PmTicketConfirm pmTicketConfirm: pmTicketConfirmList) {
                memberId = pmTicketConfirm.getMemberId();
                PmPgMember pmPgMember = this.selectByPgIdAndMemberId(pgId, memberId);
                if (pmPgMember != null) {
                    iteMap = new HashMap<>();
                    iteMap.put("member_id", memberId);
                    iteMap.put("member_name", pmPgMember.getMemberName());
                    workerList.add(iteMap);
                }
            }
            processingMap.put("member_list", workerList);
        }
        return processingMapList;
    }

    @Override
    public List<Map<String, Object>> selectOneResourceCanProcessPop(Map map) {
        byte[] popStatusArray = new byte[]{3, 5};
        map.put("popStatusArray", popStatusArray);
        return pmTicketConfirmMapper.selectOneResourceCanProcessPop(map);
    }

    /**
     * 为加工任务分配人员或者机器
     * @param map
     * @return
     * 0 成功
     * -1 加工工序不存在，-2 加工工序状态无效（必须为已领未加工）
     * -3 加工工序所在生产小组不存在，-4 指定的机器不存在或不在本生产小组
     * -5 指定的人员不存或不在本生产小组
     */
    @Override
    public int createAllocateResourceForPop(Map map, StringBuilder err) {
        Long popId = map.get("pop_id") == null || map.get("pop_id").toString().isEmpty() ? null : Long.valueOf(map.get("pop_id").toString());

        PmProdOrderSub pmProdOrderSub = pmProdOrderSubMapper.selectByPrimaryKey(popId);
        if (pmProdOrderSub == null) {
            err.append("加工工序不存在");
            return -1;
        }
        //3已领未加工
        if (!"3".equals(pmProdOrderSub.getPopStatus())){
            err.append("加工工序状态无效（必须为已领未加工）");
            return -2;
        }

        PmPg pmPg = pmPgMapper.selectByPgId(pmProdOrderSub.getPgId());
        if (pmPg == null){
            err.append("加工工序所在生产小组不存在");
            return  -3;
        }
        ArrayList<Long> resourceArrList = new ArrayList<>();

        if ("0".equals(pmPg.getPgType())) {
            //机器
            List<Map<String, Object>> devMapList =(List<Map<String, Object>>)map.get("device_list");
            if (devMapList != null || devMapList.size() > 0) {
                for (Map itemMap : devMapList) {
                    Long resId = itemMap.get("device_id") == null || itemMap.get("device_id").toString().isEmpty() ? null : Long.valueOf(itemMap.get("device_id").toString());
                    PmPgDevice pmPgDevice = pmPgDeviceMapper.selectByPrimaryKey(resId);
                    if (pmPgDevice == null || pmPgDevice.getPgId() == null || !pmPgDevice.getPgId().equals(pmProdOrderSub.getPgId())) {
                        err.append("指定的机器不存在或不在本生产小组");
                        return -4;
                    }
                    resourceArrList.add(resId);
                }
            }
        } else {
            //人工
            List<Map<String, Object>> memberMapList =(List<Map<String, Object>>)map.get("member_list");
            if (memberMapList != null || memberMapList.size() > 0) {
                for (Map itemMap : memberMapList) {
                    Long resId = itemMap.get("member_id") == null || itemMap.get("member_id").toString().isEmpty() ? null : Long.valueOf(itemMap.get("member_id").toString());
                    PmPgMember pmPgMember = this.selectByPgIdAndMemberId(pmProdOrderSub.getPgId(), resId);
                    if (pmPgMember == null || pmPgMember.getPgId() == null || !pmPgMember.getPgId().equals(pmProdOrderSub.getPgId())) {
                        err.append("指定的人员不存或不在本生产小组");
                        return -5;
                    }
                    resourceArrList.add(resId);
                }
            }
        }
        PmTicketIncept pmTicketIncept = pmTicketInceptMapper.selectByPopId(popId);
        if (pmTicketIncept == null) {
            err.append("该加工任务还没有领取");
            return -6;
        }
        //删除原来的分配
        pmTicketAllocateMapper.setDeleteStatusByPopId(popId);
        for(Long item: resourceArrList) {
            //添加新的分配
            PmTicketAllocate pmTicketAllocate = new PmTicketAllocate();
            if ("0".equals(pmPg.getPgType())) {
                pmTicketAllocate.setInceptId(pmTicketIncept.getId());
                pmTicketAllocate.setDeviceId(item);
                pmTicketAllocate.setAllocateFlag("1");
                pmTicketAllocate.setHandlerId(Long.valueOf(map.get("uid").toString()));
            } else {
                pmTicketAllocate.setInceptId(pmTicketIncept.getId());
                pmTicketAllocate.setMemberId(item);
                pmTicketAllocate.setAllocateFlag("1");
                pmTicketAllocate.setHandlerId(Long.valueOf(map.get("uid").toString()));
            }
            pmTicketAllocateMapper.insertSelective(pmTicketAllocate);
        }
        return 0;
    }

    /**
     * 加工任务领取
     * @param map
     * @param err
     * @return
     *  0 成功
     * -1 失败
     */
    @Override
    public int createPopIncept(Map map, StringBuilder err) {
        ArrayList popIds = (ArrayList)map.get("pop_ids");
        String inceptFlag = map.get("incept_flag") == null ? "" : map.get("incept_flag").toString();
        if (!"1".equals(inceptFlag) && !"2".equals(inceptFlag) ) {
            err.append("无效的领取标志");
            return  -1;
        }
        if (popIds == null || popIds.size() < 1) {
            err.append("没有指定加工工序");
            return  -1;
        }
        PmTicketIncept pmTicketIncept;
        for (Object popId: popIds) {
            PmProdOrderSub pmProdOrderSub = pmProdOrderSubMapper.selectByPrimaryKey(Long.valueOf(popId + ""));
            if (pmProdOrderSub == null) {
                throw new IllegalArgumentException("加工工序不存在");
            }
            if ("1".equals(inceptFlag)) {
                //2已排产待领取、3已领未加工
                if (!"2".equals(pmProdOrderSub.getPopStatus())) {
                    throw new IllegalArgumentException("该工序不是待取领状态");
                }
                //目前任务领取没有生成确认号，视后续所用增加
                pmTicketIncept = new PmTicketIncept();
                Map<String, Object> noMap = createBillNoService.createBillNo(Integer.valueOf(map.get("companyId").toString()), "IC");
                if (!"1".equals(noMap.get("flag").toString())) {
                    throw new IllegalArgumentException("生成任务领取确认号出错");
                }
                pmTicketIncept.setConfirmNo(noMap.get("bill_no").toString());
                pmTicketIncept.setPoId(pmProdOrderSub.getPoId());
                pmTicketIncept.setPopId(Long.valueOf(popId + ""));
                pmTicketIncept.setInceptTime(new Date());
                pmTicketIncept.setInceptUid(Long.valueOf(map.get("uid").toString()));
                OrigUserCompany origUserCompany = companyService.selectUserCompanyByUid(Integer.valueOf(map.get("companyId").toString()), Long.valueOf(map.get("uid").toString()));
                if (origUserCompany != null) { pmTicketIncept.setInceptUidName(origUserCompany.getWorkName()); }
                pmTicketIncept.setInceptFlag("1");
                pmTicketInceptMapper.insertSelective(pmTicketIncept);
                pmProdOrderSub.setPopStatus(3 + "");
                pmProdOrderSubMapper.updateByPrimaryKeySelective(pmProdOrderSub);
            } else {
                //取消领取
                if (!"3".equals(pmProdOrderSub.getPopStatus())) {
                    throw new IllegalArgumentException("该工序不是已领取状态");
                }
                //目前任务领取没有生成确认号，视后续所用增加
                pmTicketIncept = pmTicketInceptMapper.selectByPopId(Long.valueOf(popId + ""));
                if (pmProdOrderSub != null) {
                    pmTicketIncept.setInceptFlag("2");
                    pmTicketInceptMapper.insertSelective(pmTicketIncept);
                }
                pmProdOrderSub.setPopStatus(2 + "");
                pmProdOrderSubMapper.updateByPrimaryKeySelective(pmProdOrderSub);
            }
        }
        return 0;
    }

    @Override
    public PmPgMember selectByPgIdAndMemberId(Long pgId, Long memberId) {
        Map<String, Object> map = new HashMap<>();
        map.put("pgId", pgId);
        map.put("memberId", memberId);
        return pmPgMemberMapper.selectByPgIdAndMemberId(map);
    }
    /**
     * 为 人工加工的且正处于加工中的任务 添加或移除 人员
     * @param map
     * @param err
     * @return
     */
    @Override
    public int modifyPopWorker(Map map, StringBuilder err) {
        Long pgId = map.get("pg_id") == null || map.get("pg_id").toString().isEmpty() ? null : Long.valueOf(map.get("pg_id").toString());
        Long procedMemberId = map.get("member_id") == null || map.get("member_id").toString().isEmpty() ? null : Long.valueOf(map.get("member_id").toString());
        Long memberId = map.get("uid") == null || map.get("uid").toString().isEmpty() ? null : Long.valueOf(map.get("uid").toString());
        //String type = map.get("type") == null ? "" : map.get("type").toString();

        PmPgMember pmPgMember = this.selectByPgIdAndMemberId(pgId, procedMemberId);
        if (pmPgMember == null || "1".equals(pmPgMember.getMemberStatus())) {
            err.append("人员不是加工中状态");
            return -1;
        }
        pmPgMember = this.selectByPgIdAndMemberId(pgId, memberId);
        if (pmPgMember == null || "0".equals(pmPgMember.getMemberStatus())) {
            err.append("人员不是待加工状态");
            return -1;
        }
        List<PmTicketConfirm> pmTicketConfirmList = this.selectByPopId(null, "0", null, procedMemberId);
        if (pmTicketConfirmList == null || pmTicketConfirmList.size() < 1) {
            err.append("没有加工中任务");
            return -1;
        }

        Long popId;
        PmTicketConfirm pmTicketConfirm;
        for (PmTicketConfirm itemPmTicketConfirm: pmTicketConfirmList) {
            popId = itemPmTicketConfirm.getPopId();
            PmProdOrderSub pmProdOrderSub = pmProdOrderSubMapper.selectByPrimaryKey(popId);
            if (!"4".equals(pmProdOrderSub.getPopStatus())) {
                throw new IllegalArgumentException("该工序不是加工中工序");
            }
            PmPg pmPg = pmPgMapper.selectByPgId(pmProdOrderSub.getPgId());
            if (pmPg == null || "1".equals(pmPg.getPgType())) {
                throw new IllegalArgumentException("该工序所在生产小组不是人工类型小组");
            }
            //if ("1".equals(type)) {
                //添加
                /*pmPgMember = this.selectByPgIdAndMemberId(pmProdOrderSub.getPgId(), memberId);
                if (pmPgMember == null || !"0".equals(pmPgMember.getMemberStatus())) {
                    err.append("人员无效或者不能同时加工多个工序");
                    return -1;
                }*/
                pmTicketConfirm = new PmTicketConfirm();
                pmTicketConfirm.setBatchProcId(itemPmTicketConfirm.getBatchProcId());
                pmTicketConfirm.setPoId(pmProdOrderSub.getPoId());
                pmTicketConfirm.setPopId(popId);
                pmTicketConfirm.setConfirmType("0");
                pmTicketConfirm.setConfirmFlag("1");
                pmTicketConfirm.setMemberId(memberId);
                pmTicketConfirm.setRealStartTime(new Date());
                pmTicketConfirm.setHandlerId(Long.valueOf(map.get("uid").toString()));
                pmTicketConfirmMapper.insertSelective(pmTicketConfirm);
                pmPgMember.setMemberStatus("1");
                //pmPgMember.setLastPopId(popId);
                pmPgMember.setHandlerId(memberId);
                pmPgMember.setUpdatedAt(new Date());
                pmPgMemberMapper.updateByPrimaryKeySelective(pmPgMember);

           // }
            /*else {
                //移除
                *//*pmPgMember = pmPgMemberMapper.selectByPrimaryKey(memberId);
                if (pmPgMember == null || !"1".equals(pmPgMember.getMemberStatus())) {
                    err.append("人员无效或者并未处于加工中");
                    return -1;
                }*//*
                *//*if (!popId.equals(pmPgMember.getLastPopId())) {
                    err.append("该人员当前并未加工该工序");
                    return -1;
                }*//*

                Map<String, Object> popIdMap = new HashMap<>();
                popIdMap.put("popId", popId);
                popIdMap.put("memberId", memberId);
                pmTicketConfirm = pmTicketConfirmMapper.selectByPopIdAndMemberId(popIdMap);
                pmTicketConfirm.setConfirmFlag("2");
                pmTicketConfirm.setUpdatedAt(new Date());
                pmTicketConfirm.setHandlerId(Long.valueOf(map.get("uid").toString()));
                pmTicketConfirmMapper.updateByPrimaryKeySelective(pmTicketConfirm);

                pmPgMember.setMemberStatus("0");
                pmPgMemberMapper.updateByPrimaryKeySelective(pmPgMember);
            }*/
        }
        return  0;
    }

    /**
     * 加工工序设置报工
     * @param map
     * @param err
     * @return
     */
    @Override
    public int updatePopStatus(Map map, StringBuilder err) {
        //Long popId = map.get("pop_ids") == null || map.get("pop_ids").toString().isEmpty() ? null : Long.valueOf(map.get("pop_ids").toString());
        ArrayList<Object> popIds = (ArrayList)map.get("pop_ids");
        Long memberId = map.get("uid") == null || map.get("uid").toString().isEmpty() ? null : Long.valueOf(map.get("uid").toString());
        Long deviceId = map.get("device_id") == null || map.get("device_id").toString().isEmpty() ? null : Long.valueOf(map.get("device_id").toString());
        Long pgId = map.get("pg_id") == null || map.get("pg_id").toString().isEmpty() ? null : Long.valueOf(map.get("pg_id").toString());
        String setStatus = map.get("set_status") == null ? "" : map.get("set_status").toString();

        if (popIds == null || popIds.size() < 1) {
            err.append("加工任务不存在");
            return  -1;
        }
        if (pgId == null ) {
            err.append("生产小组不存在");
            return  -1;
        }
        map.put("pg_id", pgId);
        if (deviceId == null) {
            map.put("type", "1");
            map.put("resource_id", memberId);
        } else {
            map.put("type", "0");
            map.put("resource_id", deviceId);
        }
        Long popId, batchProcId = null;
        if ("1".equals(setStatus)) {
            List<Map<String, Object>> popMapList = this.selectProcInfoByResourceId(map);
            if (popMapList != null && popMapList.size() > 0) {
                err.append("该资源已经存在开始加工的任务");
                return -1;
            }
            batchProcId = pmTicketConfirmMapper.getMaxBatchProcId();
            if (batchProcId == null) { batchProcId = 1L; }
            else { batchProcId ++; }
        } else {
            batchProcId = pmTicketConfirmMapper.getProcessingBatchProcIdByPopId(Long.valueOf(popIds.get(0).toString()));
            List<PmTicketConfirm> processingPmTicketConfirmList = pmTicketConfirmMapper.selectProcessingByBatchProcId(batchProcId);
            if (popIds.size() != processingPmTicketConfirmList.size()) {
                err.append("同时开始的一批加工任务，必须同时报工");
                return -1;
            }
            boolean isExists = false;
            for (PmTicketConfirm itemConfirm: processingPmTicketConfirmList) {
                isExists = false;
                for (Object itemPopId: popIds) {
                    popId = Long.valueOf(itemPopId.toString());
                    if (popId.equals(itemConfirm.getPopId())) { isExists = true; break; }
                }
                if (!isExists) {
                    err.append("同时开始的一批加工任务，必须同时报工");
                    return -1;
                }
            }
        }
        for (Object itemPopId: popIds) {
            popId = Long.valueOf(itemPopId.toString());
            PmProdOrderSub pmProdOrderSub = pmProdOrderSubMapper.selectByPrimaryKey(popId);
            if (pmProdOrderSub == null) {
                throw new IllegalArgumentException("加工任务不存在");
            }
            PmPg pmPg = pmPgMapper.selectByPgId(pmProdOrderSub.getPgId());
            if (pmPg == null) {
                throw new IllegalArgumentException("所在的生产小组不存在");
            }
            if (!pgId.equals(pmPg.getPgId())) {
                throw new IllegalArgumentException("无效的生产小组");
            }
            //0制订中、1排产预览、2已排产待领取、3已领未加工、4加工中、5部分加工完毕、6全部加工完毕
            //1开始加工，2取消加工，3部分完成，4全部完成
            if ("6".equals(pmProdOrderSub.getPopStatus())) {
                throw new IllegalArgumentException("该加工任务已经全部完工");
            }
            if (!"1".equals(setStatus) && !"2".equals(setStatus) && !"3".equals(setStatus) && !"4".equals(setStatus)) {
                throw new IllegalArgumentException("无效的状态变更");
            }
            if ("1".equals(setStatus) && !"3".equals(pmProdOrderSub.getPopStatus()) && !"5".equals(pmProdOrderSub.getPopStatus())) {
                throw new IllegalArgumentException("设置开始加工必须是“已接收”或“部分完成”的任务");
            }
            if ("2".equals(setStatus) && !"4".equals(pmProdOrderSub.getPopStatus())) {
                throw new IllegalArgumentException("设置取消加工必须是“加工中”的任务");
            }
            if ("3".equals(setStatus) && !"4".equals(pmProdOrderSub.getPopStatus())) {
                throw new IllegalArgumentException("设置部分完成必须是“加工中”的任务");
            }
            if ("4".equals(setStatus) && !"4".equals(pmProdOrderSub.getPopStatus())) {
                throw new IllegalArgumentException("设置全部完成必须是“加工中”的任务");
            }
        /*
        PmTicketIncept pmTicketIncept = pmTicketInceptMapper.selectByPopId(popId);
        if (pmTicketIncept == null || !"1".equals(pmTicketIncept.getInceptFlag())) {
            err.append("加工任务没有被领取");
            return  -1;
        }*/
            PmProdOrder pmProdOrder = pmProdOrderMapper.selectByPrimaryKey(pmProdOrderSub.getPoId());
            PmTicketConfirm pmTicketConfirm;
            PmPgMember pmPgMember;
            PmPgDevice pmPgDevice = null;
            byte[] popStatusArray;
            List<Map<String, Object>> oneResCanStartProcPopList;
            boolean isExists = false;

           /* if ("0".equals(pmPg.getPgType())) {
                map.put("resource_id", deviceId);
            } else {
                map.put("resource_id", memberId);
            }
            map.put("type", pmPg.getPgType());
            map.put("pg_id", pmProdOrderSub.getPgId());*/

            //1开始加工，2取消加工，3部分完成，4全部完成
            //定单工序状态（0制订中、1排产预览、2已排产待领取、3已领未加工、4加工中、5部分加工完毕、6全部加工完毕）
            switch (setStatus) {
                case "1":
                    //1开始加工
                /*
                if ("4"pmProdOrderSub.getPopStatus() != null && pmProdOrderSub.getPopStatus().equals("4")) {
                    err.append("该任务已在加工中");
                    return -1;
                }*/
                    /*popStatusArray = new byte[]{3, 5};
                    map.put("popStatusArray", popStatusArray);
                    map.put("popStatusArray", popStatusArray);
                    oneResCanStartProcPopList = pmTicketConfirmMapper.selectOneResourceCanProcessPop(map);
                    for (Map item : oneResCanStartProcPopList) {
                        if (item.get("pop_id") == popId) {
                            isExists = true;
                            break;
                        }
                    }
                    if (!isExists) {
                        throw new IllegalArgumentException("该任务无法设置开始加工");
                    }*/
                    if ("0".equals(pmPg.getPgType())) {
                        pmPgDevice = pmPgDeviceMapper.selectByPrimaryKey(deviceId);
                        //pmPgDevice.setLastPopId(popId);
                        pmPgDevice.setDeviceStatus("1");
                        pmPgDevice.setHandlerId(Long.valueOf(map.get("uid").toString()));
                        pmPgDevice.setUpdatedAt(new Date());
                        pmPgDeviceMapper.updateByPrimaryKeySelective(pmPgDevice);
                    } else {
                        pmPgMember =  this.selectByPgIdAndMemberId(pmPg.getPgId(), memberId);
                        pmPgMember.setMemberStatus("1");
                        //pmPgMember.setLastPopId(popId);
                        pmPgMember.setHandlerId(Long.valueOf(map.get("uid").toString()));
                        pmPgMember.setUpdatedAt(new Date());
                        pmPgMemberMapper.updateByPrimaryKeySelective(pmPgMember);
                    }
                    pmTicketConfirm = new PmTicketConfirm();
                    pmTicketConfirm.setPoId(pmProdOrderSub.getPoId());
                    pmTicketConfirm.setPopId(popId);
                    pmTicketConfirm.setConfirmType("0");
                    pmTicketConfirm.setConfirmFlag("1");
                    if ("0".equals(pmPg.getPgType())) {
                        pmTicketConfirm.setDeviceId(deviceId);
                        pmTicketConfirm.setMemberId(memberId);
                        if (memberId.equals(pmPgDevice.getManagerId())) {
                            pmTicketConfirm.setIsLeading("1");
                        } else {
                            pmTicketConfirm.setIsLeading("0");
                        }
                    } else {
                        pmTicketConfirm.setMemberId(memberId);
                    }
                    pmTicketConfirm.setConfirmQty(this.getProdQtyByPopId(popId));
                    pmTicketConfirm.setRealStartTime(new Date());
                    pmTicketConfirm.setHandlerId(Long.valueOf(map.get("uid").toString()));
                    pmTicketConfirm.setBatchProcId(batchProcId);
                    pmTicketConfirmMapper.insertSelective(pmTicketConfirm);

                    if ("3".equals(pmProdOrderSub.getPopStatus())) {
                        pmProdOrderSub.setRealStartTime(new Date());
                    }
                    pmProdOrderSub.setPopStatus(4 + "");
                    pmProdOrderSub.setUpdatedAt(new Date());
                    pmProdOrderSubMapper.updateByPrimaryKeySelective(pmProdOrderSub);
                    String calcPoStatus = this.calcProdStatus(pmProdOrderSub.getPoId());
                    if (calcPoStatus != null && !calcPoStatus.equals(pmProdOrder.getPoStatus())) {
                        pmProdOrder.setPoStatus(calcPoStatus);
                        pmProdOrder.setHandlerId(Long.valueOf(map.get("uid").toString()));
                        pmProdOrder.setUpdatedAt(new Date());
                        pmProdOrderMapper.updateByPrimaryKey(pmProdOrder);
                    }

                    break;
                case "2":
                    //2取消加工
                /*if (!"4".equals(pmProdOrderSub.getPopStatus())) {
                    err.append("该任务未处于加工中");
                    return -1;
                }*/
                   /* popStatusArray = new byte[]{4};
                    map.put("popStatusArray", popStatusArray);
                    map.put("popStatusArray", popStatusArray);
                    oneResCanStartProcPopList = pmTicketConfirmMapper.selectOneResourceCanProcessPop(map);
                    for (Map item : oneResCanStartProcPopList) {
                        if (item.get("pop_id") == popId) {
                            isExists = true;
                            break;
                        }
                    }
                    if (!isExists) {
                        throw new IllegalArgumentException("");
                        err.append("该任务无法设置取消加工");
                        return -1;
                    }*/
                    //加工中
                    List<PmTicketConfirm> pmTicketConfirmList = this.selectByPopId(popId, "0", deviceId, memberId);
                    for (PmTicketConfirm item : pmTicketConfirmList) {
                        item.setConfirmFlag("2");
                        item.setHandlerId(Long.valueOf(map.get("uid").toString()));
                        item.setUpdatedAt(new Date());
                        pmTicketConfirmMapper.updateByPrimaryKeySelective(item);
                    }
                    if ("0".equals(pmPg.getPgType())) {
                        pmPgDevice = pmPgDeviceMapper.selectByPrimaryKey(deviceId);
                        //pmPgDevice.setLastPopId(popId);
                        pmPgDevice.setDeviceStatus("0");
                        pmPgDevice.setHandlerId(Long.valueOf(map.get("uid").toString()));
                        pmPgDevice.setUpdatedAt(new Date());
                        pmPgDeviceMapper.updateByPrimaryKeySelective(pmPgDevice);
                    } else {
                        pmPgMember = this.selectByPgIdAndMemberId(pmPg.getPgId(), memberId);
                        pmPgMember.setMemberStatus("0");
                        //pmPgMember.setLastPopId(popId);
                        pmPgMember.setHandlerId(Long.valueOf(map.get("uid").toString()));
                        pmPgMember.setUpdatedAt(new Date());
                        pmPgMemberMapper.updateByPrimaryKeySelective(pmPgMember);
                    }
                    pmTicketConfirmList = this.selectByPopId(popId, "1", null, null);
                    if (pmTicketConfirmList != null && pmTicketConfirmList.size() > 0) {
                        //部分完工
                        pmProdOrderSub.setPopStatus(5 + "");
                        pmProdOrder.setPoStatus(5 + "");
                    } else {
                        //已领未加工
                        pmProdOrderSub.setPopStatus(3 + "");
                        pmProdOrder.setPoStatus(3 + "");
                    }
                    pmProdOrderSub.setUpdatedAt(new Date());
                    pmProdOrderSubMapper.updateByPrimaryKeySelective(pmProdOrderSub);

                    calcPoStatus = this.calcProdStatus(pmProdOrderSub.getPoId());
                    if (calcPoStatus != null && !calcPoStatus.equals(pmProdOrder.getPoStatus())) {
                        pmProdOrder.setPoStatus(calcPoStatus);
                        pmProdOrder.setHandlerId(Long.valueOf(map.get("uid").toString()));
                        pmProdOrder.setUpdatedAt(new Date());
                        pmProdOrderMapper.updateByPrimaryKey(pmProdOrder);
                    }

                    break;
                case "3":
                    //3部分完成
                /*if (!"4".equals(pmProdOrderSub.getPopStatus())) {
                    err.append("该任务未处于加工中");
                    return -1;
                }*/
                    /*popStatusArray = new byte[]{4};
                    map.put("popStatusArray", popStatusArray);
                    map.put("popStatusArray", popStatusArray);
                    oneResCanStartProcPopList = pmTicketConfirmMapper.selectOneResourceCanProcessPop(map);
                    for (Map item : oneResCanStartProcPopList) {
                        if (item.get("pop_id") == popId) {
                            isExists = true;
                            break;
                        }
                    }
                    if (!isExists) {
                        throw new IllegalArgumentException("");
                        err.append("该任务无法设置部分完工");
                        return -1;
                    }*/
                    //加工中
                    /*Integer prodQty = this.getProdQtyByPopId(popId);
                    Integer doneQty = pmProdOrderSub.getDoneQty();
                    Integer confirmQty = Integer.valueOf(map.get("confirm_qty").toString());
                    if (confirmQty == 0) {
                        throw new IllegalArgumentException("");
                        err.append("确认数量不能为零");
                        return -1;
                    }
                    if (doneQty + confirmQty == prodQty) {
                        throw new IllegalArgumentException("");
                        err.append("加工数量已全部完成，请选择全部完工");
                        return -1;
                    }*/
                    pmTicketConfirmList = this.selectByPopId(popId, "0", deviceId, memberId);
                    if (pmTicketConfirmList == null || pmTicketConfirmList.size() < 1) {
                        throw new IllegalArgumentException("报工数据出错");
                    }
                    for (PmTicketConfirm itemTicketConfirm : pmTicketConfirmList) {
                        itemTicketConfirm.setConfirmType("1");
                        itemTicketConfirm.setConfirmFlag("1");
                        //itemTicketConfirm.setConfirmQty(confirmQty);
                        itemTicketConfirm.setHandlerId(Long.valueOf(map.get("uid").toString()));
                        itemTicketConfirm.setUpdatedAt(new Date());
                        pmTicketConfirmMapper.updateByPrimaryKeySelective(itemTicketConfirm);
                    }

                    if ("0".equals(pmPg.getPgType())) {
                        pmPgDevice = pmPgDeviceMapper.selectByPrimaryKey(deviceId);
                        //pmPgDevice.setLastPopId(popId);
                        pmPgDevice.setDeviceStatus("0");
                        pmPgDevice.setHandlerId(Long.valueOf(map.get("uid").toString()));
                        pmPgDevice.setUpdatedAt(new Date());
                        pmPgDeviceMapper.updateByPrimaryKeySelective(pmPgDevice);
                    } else {
                        pmPgMember = this.selectByPgIdAndMemberId(pmPg.getPgId(), memberId);
                        pmPgMember.setMemberStatus("0");
                        //pmPgMember.setLastPopId(popId);
                        pmPgMember.setHandlerId(Long.valueOf(map.get("uid").toString()));
                        pmPgMember.setUpdatedAt(new Date());
                        pmPgMemberMapper.updateByPrimaryKeySelective(pmPgMember);
                    }
                    //部分完工
                    pmProdOrderSub.setPopStatus(5 + "");
                    //pmProdOrderSub.getDoneQty() == null ? confirmQty : pmProdOrderSub.getDoneQty() + confirmQty);
                    pmProdOrderSub.setDoneQty(this.getProdQtyByPopId(popId));
                    pmProdOrderSub.setUpdatedAt(new Date());
                    pmProdOrderSubMapper.updateByPrimaryKeySelective(pmProdOrderSub);

                    calcPoStatus = this.calcProdStatus(pmProdOrderSub.getPoId());
                    Integer calPoConfirmQty = this.calcProdConfirmQty(pmProdOrderSub.getPoId());
                    if (calcPoStatus != null && !calcPoStatus.equals(pmProdOrder.getPoStatus())
                            || calPoConfirmQty != null && !calPoConfirmQty.equals(pmProdOrder.getConfirmQty())) {
                        pmProdOrder.setConfirmQty(calPoConfirmQty);
                        pmProdOrder.setPoStatus(calcPoStatus);
                        pmProdOrder.setHandlerId(Long.valueOf(map.get("uid").toString()));
                        pmProdOrder.setUpdatedAt(new Date());
                        pmProdOrderMapper.updateByPrimaryKey(pmProdOrder);
                    }

                    break;
                case "4":
                    //4全部完成
                /*if (!"4".equals(pmProdOrderSub.getPopStatus())) {
                    err.append("该任务未处于加工中");
                    return -1;
                }*/
                    /*popStatusArray = new byte[]{4};
                    map.put("popStatusArray", popStatusArray);
                    map.put("popStatusArray", popStatusArray);
                    oneResCanStartProcPopList = pmTicketConfirmMapper.selectOneResourceCanProcessPop(map);
                    for (Map item : oneResCanStartProcPopList) {
                        if (item.get("pop_id") == popId) {
                            isExists = true;
                            break;
                        }
                    }
                    if (!isExists) {
                        throw new IllegalArgumentException("");
                        err.append("该任务无法设置全部完工");
                        return -1;
                    }*/
                    //加工中
                    /*prodQty = this.getProdQtyByPopId(popId);
                    doneQty = pmProdOrderSub.getDoneQty();
                    confirmQty = Integer.valueOf(map.get("confirm_qty").toString());
                    if (confirmQty == 0) {
                        throw new IllegalArgumentException("");
                        err.append("确认数量不能为零");
                        return -1;
                    }
                    if (doneQty + confirmQty != prodQty) {
                        throw new IllegalArgumentException("");
                        err.append("加工数量没有全部完成，不能选择全部完工");
                        return -1;
                    }*/
                    pmTicketConfirmList = this.selectByPopId(popId, "0", deviceId, memberId);
                    if (pmTicketConfirmList == null || pmTicketConfirmList.size() > 1) {
                        throw new IllegalArgumentException("报工数据出错");
                    }
                    for (PmTicketConfirm itemTicketConfirm : pmTicketConfirmList) {
                        itemTicketConfirm.setConfirmType("2");
                        itemTicketConfirm.setConfirmFlag("1");
                        //itemTicketConfirm.setConfirmQty(confirmQty);
                        itemTicketConfirm.setHandlerId(Long.valueOf(map.get("uid").toString()));
                        itemTicketConfirm.setUpdatedAt(new Date());
                        pmTicketConfirmMapper.updateByPrimaryKeySelective(itemTicketConfirm);
                    }

                    if ("0".equals(pmPg.getPgType())) {
                        pmPgDevice = pmPgDeviceMapper.selectByPrimaryKey(deviceId);
                        //pmPgDevice.setLastPopId(popId);
                        pmPgDevice.setDeviceStatus("0");
                        pmPgDevice.setHandlerId(Long.valueOf(map.get("uid").toString()));
                        pmPgDevice.setUpdatedAt(new Date());
                        pmPgDeviceMapper.updateByPrimaryKeySelective(pmPgDevice);
                    } else {
                        pmPgMember = this.selectByPgIdAndMemberId(pmPg.getPgId(), memberId);
                        pmPgMember.setMemberStatus("0");
                        //pmPgMember.setLastPopId(popId);
                        pmPgMember.setHandlerId(Long.valueOf(map.get("uid").toString()));
                        pmPgMember.setUpdatedAt(new Date());
                        pmPgMemberMapper.updateByPrimaryKeySelective(pmPgMember);
                    }
                    //全部完工
                    pmProdOrderSub.setPopStatus(6 + "");
                    //pmProdOrderSub.getDoneQty() == null ? confirmQty : pmProdOrderSub.getDoneQty() + confirmQty);
                    pmProdOrderSub.setDoneQty(this.getProdQtyByPopId(popId));
                    pmProdOrderSub.setUpdatedAt(new Date());
                    pmProdOrderSub.setRealEndTime(new Date());
                    pmProdOrderSubMapper.updateByPrimaryKeySelective(pmProdOrderSub);

                    calcPoStatus = this.calcProdStatus(pmProdOrderSub.getPoId());
                    calPoConfirmQty = this.calcProdConfirmQty(pmProdOrderSub.getPoId());
                    if (calcPoStatus != null && !calcPoStatus.equals(pmProdOrder.getPoStatus())
                            || calPoConfirmQty != null && !calPoConfirmQty.equals(pmProdOrder.getConfirmQty())) {
                        pmProdOrder.setConfirmQty(calPoConfirmQty);
                        pmProdOrder.setPoStatus(calcPoStatus);
                        pmProdOrder.setHandlerId(Long.valueOf(map.get("uid").toString()));
                        pmProdOrder.setUpdatedAt(new Date());
                        pmProdOrderMapper.updateByPrimaryKey(pmProdOrder);
                    }

                    break;
                default:
                    throw new IllegalArgumentException("设置状态不正确");
            }
        }
        return  1;
    }

    /**
     * 根据条件获得报工记录
     * @param popId
     * @param confirmType
     * @param deviceId
     * @param memberId
     * @return
     */
    public  List<PmTicketConfirm> selectByPopId(Long popId, String confirmType, Long deviceId, Long memberId) {
        Map map = new HashMap<>();
        map.put("popId", popId);
        map.put("confirmType", confirmType);
        map.put("deviceId", deviceId);
        map.put("memberId", memberId);
        return pmTicketConfirmMapper.selectByPopId(map);
    }

    /**
     * 获取加工工序的原始加工产量
     * @param popId
     * @return
     */
    public Integer getProdQtyByPopId(Long popId) {
        PmProdOrderSub pmProdOrderSub = pmProdOrderSubMapper.selectByPrimaryKey(popId);
        if (pmProdOrderSub == null) { return 0; }
        if (pmProdOrderSub.getProcQty() == null || pmProdOrderSub.getProcQty() <= 0) {
            PmProdOrder pmProdOrder = pmProdOrderMapper.selectByPrimaryKey(pmProdOrderSub.getPoId());
            if (pmProdOrder == null) { return 0; }
            return pmProdOrder.getProdQty() == null ? 0 : pmProdOrder.getProdQty();
        } else {
            return pmProdOrderSub.getProcQty();
        }
    }

    /**
     * 根据定单所有工序计算定单的已确认产量
     * @param poId
     * @return
     */
    public Integer  calcProdConfirmQty(Long poId) {
        Integer confirmQty = 0;
        List<PmProdOrderSub> pmProdOrderSubMapList = pmProdOrderSubMapper.selectByProdOrderId(poId);
        for (PmProdOrderSub item: pmProdOrderSubMapList) {
            if (item.getDoneQty() != null && Integer.valueOf(item.getDoneQty().toString()) < confirmQty) {
                confirmQty = Integer.valueOf(item.getDoneQty().toString());
            }
        }
        return confirmQty;
    }

    /**
     * 根据定单所有工序计算定单的状态
     * @param poId
     * @return
     */
    public String  calcProdStatus(Long poId) {
        //分别对应0-6的状态,计算这些状态出来的个数
        Integer[] arrPopStatusCount = new Integer[]{0 , 0, 0, 0, 0, 0, 0};
        List<PmProdOrderSub> pmProdOrderSubMapList = pmProdOrderSubMapper.selectByProdOrderId(poId);
        Integer confirmQty = calcProdConfirmQty(poId);
        PmProdOrder pmProdOrder = pmProdOrderMapper.selectByPrimaryKey(poId);
        for (PmProdOrderSub item: pmProdOrderSubMapList) {
            if (item.getPopStatus() == null) { arrPopStatusCount[0]++; }
            else if ("0".equals(item.getPopStatus() + "")) { arrPopStatusCount[0]++; }
            else if ("1".equals(item.getPopStatus() + "")) { arrPopStatusCount[1]++; }
            else if ("2".equals(item.getPopStatus() + "")) { arrPopStatusCount[2]++; }
            else if ("3".equals(item.getPopStatus() + "")) { arrPopStatusCount[3]++; }
            else if ("4".equals(item.getPopStatus() + "")) { arrPopStatusCount[4]++; }
            else if ("5".equals(item.getPopStatus() + "")) { arrPopStatusCount[5]++; }
            else if ("6".equals(item.getPopStatus() + "")) { arrPopStatusCount[6]++; }
            else {
                //返回null将不更改原状态
                return pmProdOrder.getPoStatus();
            }
        }
        if (arrPopStatusCount[6] == pmProdOrderSubMapList.size()) { return "6"; }
        if (arrPopStatusCount[5] + arrPopStatusCount[6] == pmProdOrderSubMapList.size()) { return "5"; }
        if (arrPopStatusCount[4] + arrPopStatusCount[5] + arrPopStatusCount[6] == pmProdOrderSubMapList.size()) {
            if (confirmQty > 0) { return "5"; }
            else { return "4"; }
        }
        if (arrPopStatusCount[3] + arrPopStatusCount[4] + arrPopStatusCount[5] + arrPopStatusCount[6] == pmProdOrderSubMapList.size()) {
            if (arrPopStatusCount[3] == pmProdOrderSubMapList.size()) { return "3"; }
            else { return "4"; }
        }
        if (arrPopStatusCount[2] + arrPopStatusCount[3] + arrPopStatusCount[4] + arrPopStatusCount[5] + arrPopStatusCount[6] == pmProdOrderSubMapList.size()) {
            if (arrPopStatusCount[2] == pmProdOrderSubMapList.size()) { return "2"; }
            else if (arrPopStatusCount[2] + arrPopStatusCount[3] == pmProdOrderSubMapList.size()) { return "3"; }
            else { return "4"; }
        }
        return pmProdOrder.getPoStatus();
    }

    /**
     * 该定单的所有工序是否已经全部完工
     * @param poId
     * @return
     */
    public boolean isAllPopFinished(Long poId) {
        List<PmProdOrderSub> pmProdOrderSubMapList = pmProdOrderSubMapper.selectByProdOrderId(poId);
        for (PmProdOrderSub item: pmProdOrderSubMapList) {
            if (item.getPopStatus() == null || !"6".equals(item.getPopStatus() + "")) { return false; }
        }
        return true;
    }

}

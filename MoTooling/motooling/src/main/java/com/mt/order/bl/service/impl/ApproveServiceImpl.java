package com.mt.order.bl.service.impl;

/**
 * created by fhk on 2017.11.29
 *
 * 业务审批服务类
 *
 */

import com.mt.bm.bom.service.BomService;
import com.mt.bm.common.dao.BomMapper;
import com.mt.bm.common.model.Bom;
import com.mt.cms.common.dao.OrigUserCompanyMapper;
import com.mt.cms.common.model.OrigUserCompany;
import com.mt.common.customenum.RoleEnum;
import com.mt.iv.common.dao.IvApplyMatBillMapper;
import com.mt.iv.common.dao.IvScrapMatBillMapper;
import com.mt.iv.common.dao.IvStoreCheckBillMapper;
import com.mt.iv.common.model.IvApplyMatBill;
import com.mt.iv.common.model.IvScrapMatBill;
import com.mt.iv.common.model.IvStoreCheckBill;
import com.mt.iv.warehouse.service.StoreCheckBillService;
import com.mt.order.bl.service.ApproveService;
import com.mt.order.common.dao.*;
import com.mt.order.common.model.*;
import com.mt.pc.common.dao.PcPurchOrderMapper;
import com.mt.pc.common.dao.PcRequestMapper;
import com.mt.pc.common.model.PcPurchOrder;
import com.mt.pc.common.model.PcRequest;
import com.mt.pc.purchase.service.CreateMatRequirePlanService;
import com.mt.qm.common.dao.QmProcQcMapper;
import com.mt.qm.common.dao.QmProcQcSubMapper;
import com.mt.qm.common.dao.QmPurchQcMapper;
import com.mt.qm.common.dao.QmPurchQcSubMapper;
import com.mt.qm.common.model.QmProcQc;
import com.mt.qm.common.model.QmPurchQc;
import com.mt.upms.common.dao.RoleMapper;
import com.mt.upms.common.dao.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApproveServiceImpl implements ApproveService {

    @Autowired
    private ApproveRecordHisMapper approveRecordHisMapper;

    @Autowired
    private PcRequestMapper pcRequestMapper;

    @Autowired
    private PcPurchOrderMapper pcPurchOrderMapper;

    @Autowired
    private ComputeMapper computeMapper;

    @Autowired
    private QuoteMapper quoteMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private CreateMatRequirePlanService createMatRequirePlanService;

    @Autowired
    private IvApplyMatBillMapper ivApplyMatBillMapper;

    @Autowired
    private IvStoreCheckBillMapper ivStoreCheckBillMapper;

    @Autowired
    private IvScrapMatBillMapper ivScrapMatBillMapper;

    @Autowired
    private StoreCheckBillService storeCheckBillService;

    @Autowired
    private BomMapper bomMapper;

    @Autowired
    private BomService bomService;

    @Autowired
    private QmPurchQcMapper qmPurchQcMapper;

    @Autowired
    private QmPurchQcSubMapper qmPurchQcSubMapper;

    @Autowired
    private QmProcQcMapper qmProcQcMapper;

    @Autowired
    private QmProcQcSubMapper qmProcQcSubMapper;

    @Autowired
    private  QuoteMoldMapper quoteMoldMapper;

    @Autowired
    private OrigUserCompanyMapper origUserCompanyMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 业务审批流程
     * @param currSetp
     * @param approveFlag
     * @param newStep
     * @return
     */
    private boolean approveFlow(String currSetp, String approveFlag, StringBuilder newStep) {
        switch (currSetp) {
            //step10制订中, step11退回制订中
            case "step10":
            case "step11":
                if ("1".equals(approveFlag)) {
                    newStep.append("step20");
                } else {
                    return false;
                }
                break;

            //复核中
            case "step20":
                if ("1".equals(approveFlag)) {
                    newStep.append("step30");
                } else if ("2".equals(approveFlag)) {
                    newStep.append("step11");
                } else {
                    return false;
                }
                break;

            //审批中
            case "step30":
                if ("1".equals(approveFlag)) {
                    //step0审批结束,step00无须审批
                    newStep.append("step0");
                } else if ("2".equals(approveFlag)) {
                    newStep.append("step11");
                } else {
                    return false;
                }
                break;
            default:
                return false;
        }
        return true;
    }


    /**
     * 获取处于某环节的待审业务
     * @param billId
     * @param billType
     * @param approveStep
     * @return
     */
    @Override
    public ApproveRecordHis selectWaitApproveRecord(Long billId, String billType, String approveStep) {
        Map<String, Object> map = new HashMap<>();
        map.put("approveId", billId);
        map.put("approveType", Byte.parseByte(billType));
        map.put("approveStep", approveStep);
        return approveRecordHisMapper.selectWaitApproveRecord(map);
    }
    @Override
    public Long getRoleId(Integer companyId, RoleEnum roleEnum) {
        Map<String, Object> map = new HashMap<>();
        map.put("company_id", companyId);
        //公司内部邀请角色总是普通员工
        map.put("role_code", roleEnum.getCode());
        //根据 company_id 和 role 的code 查出 role_id
        return roleMapper.selectRoleId(map);
    }
    /**
     * 审批时生成审批记录
     * @param billId
     * @param billType
     * @param currApproveStep：原来所处审批环节
     * @param approveFlag：审批标志，同意或不同意
     * @param approveSugg：审批意见
     * @param newApproveStep：下一步审批环节
     * @param approveManId：审批人Id
     * @param approveManName：审批人名称
     */
    private void createApproveRecord(Long billId, String billType, String currApproveStep, String approveFlag,
                                String approveSugg, StringBuilder newApproveStep, Long approveManId, String approveManName) {
        ApproveRecordHis waitApproveRecord;
        //审批意见每次要更新
        if (approveSugg == null) { approveSugg = "";  }

        waitApproveRecord = this.selectWaitApproveRecord(billId, billType, currApproveStep);
        if (waitApproveRecord == null) {
            waitApproveRecord = new ApproveRecordHis();
            if ("step11".equals(newApproveStep.toString())) { waitApproveRecord.setApproveSugg(approveSugg); }
            waitApproveRecord.setApproveType(Byte.parseByte(billType + ""));
            waitApproveRecord.setApproveId(billId);
            waitApproveRecord.setApproveStep(newApproveStep.toString());
            waitApproveRecord.setApproveStartDate(new Date());
            if (!"step10".equals(currApproveStep) && !"step11".equals(currApproveStep)) {
                waitApproveRecord.setApproveFlag(Byte.valueOf(approveFlag));
                waitApproveRecord.setApproveManId(approveManId);
                waitApproveRecord.setApproveManName(approveManName);
                waitApproveRecord.setApproveEndDate(new Date());
                approveRecordHisMapper.insertSelective(waitApproveRecord);

                if (!"step0".equals(newApproveStep.toString()) && !"step00".equals(newApproveStep.toString())  && !"step11".equals(newApproveStep.toString())) {
                    waitApproveRecord = new ApproveRecordHis();
                    //if ("step11".equals(newApproveStep.toString())) { waitApproveRecord.setApproveSugg(approveSugg); }
                    waitApproveRecord.setApproveType(Byte.parseByte(billType + ""));
                    waitApproveRecord.setApproveId(billId);
                    waitApproveRecord.setApproveStep(newApproveStep.toString());
                    waitApproveRecord.setApproveStartDate(new Date());
                    approveRecordHisMapper.insertSelective(waitApproveRecord);
                }
            } else {
                approveRecordHisMapper.insertSelective(waitApproveRecord);
            }
        } else {
            waitApproveRecord.setApproveFlag(Byte.parseByte(approveFlag));
            waitApproveRecord.setApproveSugg(approveSugg);
            waitApproveRecord.setApproveManId(approveManId);
            waitApproveRecord.setApproveManName(approveManName);
            waitApproveRecord.setApproveEndDate(new Date());
            approveRecordHisMapper.updateByPrimaryKeySelective(waitApproveRecord);
            if (!"step0".equals(newApproveStep.toString()) && !"step00".equals(newApproveStep.toString())  && !"step11".equals(newApproveStep.toString())) {
                waitApproveRecord = new ApproveRecordHis();
                //if ("step11".equals(newApproveStep.toString())) { waitApproveRecord.setApproveSugg(approveSugg); }
                waitApproveRecord.setApproveType(Byte.parseByte(billType + ""));
                waitApproveRecord.setApproveId(billId);
                waitApproveRecord.setApproveStep(newApproveStep.toString());
                waitApproveRecord.setApproveStartDate(new Date());
                approveRecordHisMapper.insertSelective(waitApproveRecord);
            }
        }
    }

    @Override
    public String getWorkNameByUid(Integer companyId, Long uid) {
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", companyId);
        map.put("uid", uid);
        OrigUserCompany origUserCompany = origUserCompanyMapper.selectUserCompanyByUid(map);
        return origUserCompany.getWorkName();
    }

    @Override
    public boolean isHasPermission(Integer companyId, Long uid, RoleEnum roleEnum ) {
        /*this.getRoleId(companyId, RoleEnum.ENTERPRISE_ADMIN);
        this.getRoleId(companyId, RoleEnum.DEPARTMENT_LEADER);*/
        Long roleId = this.getRoleId(companyId, roleEnum);
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", companyId);
        map.put("uid", uid);
        map.put("roleId", roleId);
        if (userRoleMapper.isHasRole(map) > 0 ) {
            return  true;
        } else {
            return  false;
        }
    }
    /**
     * 业务审批
     * @param billId
     * @param billType
     * 	1核算单，2报价单，3客户定单, 4请购单，5采购订单单价审批，6采购订单一级审批，7采购订单二级审批, 8领料单 , 9仓库盘点单 ,10报废单,
     * 	11来料质检单, 12外协质检单, 13工序质检单, 20 Bom审批
     * @param approveStep
     * @param approveFlag
     * @param approveSugg
     * @param approveManId
     * @param approveManName
     * @param err
     * @return
     */
    @Override
    public Map<String, Object> createApprove(Long billId, String billType, String approveStep, String approveFlag, String approveSugg, Long approveManId, String approveManName, StringBuilder err) {
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("bill_id", billId);
        retMap.put("bill_type", billType);
        String currSetp = "";
        StringBuilder newStep = new StringBuilder();
        ApproveRecordHis waitApproveRecord;
        //审批意见每次要更新
        if (approveSugg == null) { approveSugg = ""; }
        if ("2".equals(approveFlag) && approveSugg.isEmpty()) {
            err.append("不同意时必须填写审批意见！");
            return null;
        }
        switch (billType)  {
            //核算单
            case "1":
                Compute compute = computeMapper.selectByPrimaryKey(billId);
                if (compute == null) {
                    err.append("不存在该项业务数据，失败！");
                    return null;
                }
                currSetp = compute.getApproveStep();
                currSetp = currSetp == null ? "" : currSetp.trim();
                if (!currSetp.equals(approveStep)) {
                    err.append("前后端数据不一致，失败！");
                    return null;
                }
                if(!this.approveFlow(currSetp, approveFlag, newStep)) {
                    err.append("审批数据不规范，失败！");
                    return null;
                }
                retMap.put("approve_step", newStep.toString());

                this.createApproveRecord(billId, billType, currSetp, approveFlag, approveSugg, newStep, approveManId, approveManName);
                compute.setApproveSugg(approveSugg);
                compute.setApproveStep(newStep.toString());
                computeMapper.updateByPrimaryKeySelective(compute);
                break;

            //报价单
            case "2":
                Quote quote = quoteMapper.selectByPrimaryKey(billId);
                if (quote == null) {
                    err.append("不存在该项业务数据，失败！");
                    return null;
                }
                List<QuoteMold> quoteMoldList = quoteMoldMapper.selectByQuoteId(billId);
                if (quoteMoldList == null || quoteMoldList.size() < 1) {
                    err.append("该报价单没有产品信息，失败！");
                    return null;
                }
                currSetp = quote.getApproveStep();
                currSetp = currSetp == null ? "" : currSetp.trim();
                if (!currSetp.equals(approveStep)) {
                    err.append("前后端数据不一致，失败！");
                    return null;
                }
                if(!this.approveFlow(currSetp, approveFlag, newStep)) {
                    err.append("审批数据不规范，失败！");
                    return null;
                }
                retMap.put("approve_step", newStep.toString());

                this.createApproveRecord(billId, billType, currSetp, approveFlag, approveSugg, newStep, approveManId, approveManName);
                quote.setApproveSugg(approveSugg);
                quote.setApproveStep(newStep.toString());
                if ("step0".equals(newStep.toString())) {
                    for (QuoteMold quoteMold: quoteMoldList) {
                        //设置该产品已审批状态
                        quoteMold.setIsApproved("1");
                        quoteMoldMapper.updateByPrimaryKeySelective(quoteMold);
                    }
                }
                quoteMapper.updateByPrimaryKeySelective(quote);

                break;

            //客户定单
            case "3":
                Project project = projectMapper.selectByPrimaryKey(billId);
                if (project == null) {
                    err.append("不存在该项业务数据，失败！");
                    return null;
                }
                currSetp = project.getApproveStep();
                currSetp = currSetp == null ? "" : currSetp.trim();
                if (!currSetp.equals(approveStep)) {
                    err.append("前后端数据不一致，失败！");
                    return null;
                }
                if(!this.approveFlow(currSetp, approveFlag, newStep)) {
                    err.append("审批数据不规范，失败！");
                    return null;
                }
                retMap.put("approve_step", newStep.toString());

                this.createApproveRecord(billId, billType, currSetp, approveFlag, approveSugg, newStep, approveManId, approveManName);
                project.setApproveSugg(approveSugg);
                project.setApproveStep(newStep.toString());
                projectMapper.updateByPrimaryKeySelective(project);
                break;

            //请购单
            case "4":
                PcRequest pcRequest = pcRequestMapper.selectByPrimaryKey(billId);
                if (pcRequest == null) {
                    err.append("不存在该项业务数据，失败！");
                    return null;
                }
                currSetp = pcRequest.getApproveStep();
                currSetp = currSetp == null ? "" : currSetp.trim();
                if (!currSetp.equals(approveStep)) {
                    err.append("前后端数据不一致，失败！");
                    return null;
                }
                if(!this.approveFlow(currSetp, approveFlag, newStep)) {
                    err.append("审批数据不规范，失败！");
                    return null;
                }
                retMap.put("approve_step", newStep.toString());

                this.createApproveRecord(billId, billType, currSetp, approveFlag, approveSugg, newStep, approveManId, approveManName);
                pcRequest.setApproveSugg(approveSugg);
                pcRequest.setApproveStep(newStep.toString());
                pcRequestMapper.updateByPrimaryKeySelective(pcRequest);
                if ("step0".equals(newStep.toString())) {
                    createMatRequirePlanService.createMatRequirePlanfromRequest(billId, approveManId);
                }
                break;

            //采购订单单价审批
            case "5":
                PcPurchOrder pcPurchOrder = pcPurchOrderMapper.selectByPrimaryKey(billId);
                if (pcPurchOrder == null) {
                    err.append("不存在该项业务数据，失败！");
                    return null;
                }
                currSetp = pcPurchOrder.getApprovePriceStep();
                currSetp = currSetp == null ? "" : currSetp.trim();
                if (!currSetp.equals(approveStep)) {
                    err.append("前后端数据不一致，失败！");
                    return null;
                }
                //先判断是否需要价格审批
                int isPriceApp = pcPurchOrderMapper.isNeedPriceApp(billId);
                if (isPriceApp == 0) {
                    //无需审批，提交下一级
                    newStep.append("step00");
                    pcPurchOrder.setApprovePriceStep(newStep.toString());
                    pcPurchOrderMapper.updateByPrimaryKeySelective(pcPurchOrder);
                    retMap.put("approve_step", newStep.toString());
                    return createApprove(billId, "6", pcPurchOrder.getApproveGrade1Step(), "1",
                            pcPurchOrder.getApproveGrade1Sugg(), approveManId, approveManName, err);
                }

                if(!this.approveFlow(currSetp, approveFlag, newStep)) {
                    err.append("审批数据不规范，失败！");
                    return null;
                }
                //5采购订单单价审批，6采购订单一级审批，7采购订单二级审批: 不需要复核step20
                if ("step20".equals(newStep.toString())) {
                    newStep.delete(0, newStep.length());
                    newStep.append("step30");
                }
                retMap.put("approve_step", newStep.toString());

                this.createApproveRecord(billId, billType, currSetp, approveFlag, approveSugg, newStep, approveManId, approveManName);
                pcPurchOrder.setApprovePriceSugg(approveSugg);
                pcPurchOrder.setApprovePriceStep(newStep.toString());
                pcPurchOrderMapper.updateByPrimaryKeySelective(pcPurchOrder);
                if ("step0".equals(newStep.toString())) {
                    createApprove(billId, "6", pcPurchOrder.getApproveGrade1Step(), "1",
                            pcPurchOrder.getApproveGrade1Sugg(), approveManId, approveManName, err);
                }
                break;

            //采购订单一级审批
            case "6":
                pcPurchOrder = pcPurchOrderMapper.selectByPrimaryKey(billId);
                if (pcPurchOrder == null) {
                    err.append("不存在该项业务数据，失败！");
                    return null;
                }
                currSetp = pcPurchOrder.getApproveGrade1Step();
                currSetp = currSetp == null ? "" : currSetp.trim();
                if (!currSetp.equals(approveStep)) {
                    err.append("前后端数据不一致，失败！");
                    return null;
                }
                int isGrade1App = pcPurchOrderMapper.isNeedGrade1App(billId);
                if (isGrade1App == 0) {
                    //不需要审批
                    newStep.append("step00");
                    //无需审批，提交下一级
                    pcPurchOrder.setApproveGrade1Step(newStep.toString());
                    pcPurchOrderMapper.updateByPrimaryKeySelective(pcPurchOrder);
                    retMap.put("approve_step", newStep.toString());
                    return createApprove(billId, "7", pcPurchOrder.getApproveGrade2Step(), "1",
                            pcPurchOrder.getApproveGrade2Sugg(), approveManId, approveManName, err);
                }

                if(!this.approveFlow(currSetp, approveFlag, newStep)) {
                    err.append("审批数据不规范，失败！");
                    return null;
                }
                //5采购订单单价审批，6采购订单一级审批，7采购订单二级审批: 不需要复核step20
                if ("step20".equals(newStep.toString())) {
                    newStep.delete(0, newStep.length());
                    newStep.append("step30");
                }
                retMap.put("approve_step", newStep.toString());

                this.createApproveRecord(billId, billType, currSetp, approveFlag, approveSugg, newStep, approveManId, approveManName);
                pcPurchOrder.setApproveGrade1Sugg(approveSugg);
                pcPurchOrder.setApproveGrade1Step(newStep.toString());
                if ("step11".equals(newStep.toString())) {
                    pcPurchOrder.setApprovePriceSugg(approveSugg);
                    pcPurchOrder.setApprovePriceStep(newStep.toString());
                }
                pcPurchOrderMapper.updateByPrimaryKeySelective(pcPurchOrder);
                if ("step0".equals(newStep.toString())) {
                    createApprove(billId, "7", pcPurchOrder.getApproveGrade2Step(), "1",
                            pcPurchOrder.getApproveGrade2Sugg(), approveManId, approveManName, err);
                }
                break;

            //采购订单二级审批
            case "7":
                pcPurchOrder = pcPurchOrderMapper.selectByPrimaryKey(billId);
                if (pcPurchOrder == null) {
                    err.append("不存在该项业务数据，失败！");
                    return null;
                }
                currSetp = pcPurchOrder.getApproveGrade2Step();
                currSetp = currSetp == null ? "" : currSetp.trim();
                if (!currSetp.equals(approveStep)) {
                    err.append("前后端数据不一致，失败！");
                    return null;
                }
                isGrade1App = pcPurchOrderMapper.isNeedGrade2App(billId);
                if (isGrade1App == 0) {
                    //不需要审批
                    newStep.append("step00");
                    //无需审批，提交下一级
                    pcPurchOrder.setApproveGrade2Step(newStep.toString());
                    pcPurchOrderMapper.updateByPrimaryKeySelective(pcPurchOrder);
                    retMap.put("approve_step", newStep.toString());
                    return retMap;
                }
                if(!this.approveFlow(currSetp, approveFlag, newStep)) {
                    err.append("审批数据不规范，失败！");
                    return null;
                }
                //5采购订单单价审批，6采购订单一级审批，7采购订单二级审批: 不需要复核step20
                if ("step20".equals(newStep.toString())) {
                    newStep.delete(0, newStep.length());
                    newStep.append("step30");
                }
                retMap.put("approve_step", newStep.toString());

                this.createApproveRecord(billId, billType, currSetp, approveFlag, approveSugg, newStep, approveManId, approveManName);
                pcPurchOrder.setApproveGrade2Sugg(approveSugg);
                pcPurchOrder.setApproveGrade2Step(newStep.toString());
                if ("step11".equals(newStep.toString())) {
                    pcPurchOrder.setApproveGrade1Sugg(approveSugg);
                    pcPurchOrder.setApproveGrade1Step(newStep.toString());
                    pcPurchOrder.setApprovePriceSugg(approveSugg);
                    pcPurchOrder.setApprovePriceStep(newStep.toString());
                }
                pcPurchOrderMapper.updateByPrimaryKeySelective(pcPurchOrder);
                break;

            //8领料单
            case "8":
                IvApplyMatBill ivApplyMatBill = ivApplyMatBillMapper.selectByPrimaryKey(billId);
                if (ivApplyMatBill == null) {
                    err.append("不存在该项业务数据，失败！");
                    return null;
                }
                currSetp = ivApplyMatBill.getApproveStep();
                 currSetp = currSetp == null ? "" : currSetp.trim();
                if (!currSetp.equals(approveStep)) {
                    err.append("前后端数据不一致，失败！");
                    return null;
                }
                if(!this.approveFlow(currSetp, approveFlag, newStep)) {
                    err.append("审批数据不规范，失败！");
                    return null;
                }
                retMap.put("approve_step", newStep.toString());

                this.createApproveRecord(billId, billType, currSetp, approveFlag, approveSugg, newStep, approveManId, approveManName);
                ivApplyMatBill.setApproveSugg(approveSugg);
                ivApplyMatBill.setApproveStep(newStep.toString());
                ivApplyMatBillMapper.updateByPrimaryKeySelective(ivApplyMatBill);
                break;

            //9仓库盘点单
            case "9":
                IvStoreCheckBill ivStoreCheckBill = ivStoreCheckBillMapper.selectByPrimaryKey(billId);
                if (ivStoreCheckBill == null) {
                    err.append("不存在该项业务数据，失败！");
                    return null;
                }
                currSetp = ivStoreCheckBill.getApproveStep();
                currSetp = currSetp == null ? "" : currSetp.trim();
                if (!currSetp.equals(approveStep)) {
                    err.append("前后端数据不一致，失败！");
                    return null;
                }
                if(!this.approveFlow(currSetp, approveFlag, newStep)) {
                    err.append("审批数据不规范，失败！");
                    return null;
                }
                retMap.put("approve_step", newStep.toString());

                this.createApproveRecord(billId, billType, currSetp, approveFlag, approveSugg, newStep, approveManId, approveManName);
                ivStoreCheckBill.setApproveSugg(approveSugg);
                ivStoreCheckBill.setApproveStep(newStep.toString());
                ivStoreCheckBillMapper.updateByPrimaryKeySelective(ivStoreCheckBill);
                if ("step0".equals(newStep.toString())) {
                    //盘点单审批结束要生成盘点出入库凭证并以实盘数修改库存
                    storeCheckBillService.createStoreCheckVoucher(billId, approveManId);
                } else {
                    storeCheckBillService.updateStoreCheckStatus(billId, approveManId);
                }
                break;

            //,10报废单
            case "10":
                IvScrapMatBill ivScrapMatBill = ivScrapMatBillMapper.selectByPrimaryKey(billId);
                if (ivScrapMatBill == null) {
                    err.append("不存在该项业务数据，失败！");
                    return null;
                }
                currSetp = ivScrapMatBill.getApproveStep();
                currSetp = currSetp == null ? "" : currSetp.trim();
                if (!currSetp.equals(approveStep)) {
                    err.append("前后端数据不一致，失败！");
                    return null;
                }
                if(!this.approveFlow(currSetp, approveFlag, newStep)) {
                    err.append("审批数据不规范，失败！");
                    return null;
                }
                retMap.put("approve_step", newStep.toString());

                this.createApproveRecord(billId, billType, currSetp, approveFlag, approveSugg, newStep, approveManId, approveManName);
                ivScrapMatBill.setApproveSugg(approveSugg);
                ivScrapMatBill.setApproveStep(newStep.toString());
                ivScrapMatBillMapper.updateByPrimaryKeySelective(ivScrapMatBill);
                break;

            // 11来料质检单
            case "11":
                QmPurchQc qmPurchQc = qmPurchQcMapper.selectByPrimaryKey(billId);
                if (qmPurchQc == null) {
                    err.append("不存在该项业务数据，失败！");
                    return null;
                }
                currSetp = qmPurchQc.getApproveStep();
                currSetp = currSetp == null ? "" : currSetp.trim();
                if (!currSetp.equals(approveStep)) {
                    err.append("前后端数据不一致，失败！");
                    return null;
                }
                if(!this.approveFlow(currSetp, approveFlag, newStep)) {
                    err.append("审批数据不规范，失败！");
                    return null;
                }
                retMap.put("approve_step", newStep.toString());

                this.createApproveRecord(billId, billType, currSetp, approveFlag, approveSugg, newStep, approveManId, approveManName);
                qmPurchQc.setApproveSugg(approveSugg);
                qmPurchQc.setApproveStep(newStep.toString());
                qmPurchQcMapper.updateByPrimaryKeySelective(qmPurchQc);
                if ("step0".equals(newStep.toString())) {
                    qmPurchQcSubMapper.updatePcPurchOrderSubByQcInfo(billId);
                }
                break;

            // 12外协质检单
            case "12":
                err.append("不正确的业务类型，失败！");
                return null;

            // 13工序质检单
            case "13":
                QmProcQc qmProcQc = qmProcQcMapper.selectByPrimaryKey(billId);
                if (qmProcQc == null) {
                    err.append("不存在该项业务数据，失败！");
                    return null;
                }
                currSetp = qmProcQc.getApproveStep();
                currSetp = currSetp == null ? "" : currSetp.trim();
                if (!currSetp.equals(approveStep)) {
                    err.append("前后端数据不一致，失败！");
                    return null;
                }
                if(!this.approveFlow(currSetp, approveFlag, newStep)) {
                    err.append("审批数据不规范，失败！");
                    return null;
                }
                retMap.put("approve_step", newStep.toString());

                this.createApproveRecord(billId, billType, currSetp, approveFlag, approveSugg, newStep, approveManId, approveManName);
                qmProcQc.setApproveSugg(approveSugg);
                qmProcQc.setApproveStep(newStep.toString());
                qmProcQcMapper.updateByPrimaryKeySelective(qmProcQc);
                if ("step0".equals(newStep.toString())) {
                    qmProcQcSubMapper.updatePmProdOrderSubByQcInfo(billId);
                }
                break;

            //Bom审批
            case "20":
                Bom bom =  bomMapper.selectByPrimaryKey(billId);
                if (bom == null) {
                    err.append("不存在该项业务数据，失败！");
                    return null;
                }
                currSetp = bom.getAppFlag();
                currSetp = currSetp == null ? "" : currSetp.trim();
                if (!currSetp.equals(approveStep)) {
                    err.append("前后端数据不一致，失败！");
                    return null;
                }
                if(!this.approveFlow(currSetp, approveFlag, newStep)) {
                    err.append("审批数据不规范，失败！");
                    return null;
                }
                if ("step20".equals(newStep.toString())) {
                    newStep.delete(0, newStep.length());
                    newStep.append("step30");
                }
                retMap.put("approve_step", newStep.toString());

                this.createApproveRecord(billId, billType, currSetp, approveFlag, approveSugg, newStep, approveManId, approveManName);
                bom.setAppId(approveManId == null ? null : approveManId.toString());
                bom.setAppTime(new Date());
                bom.setAppRemark(approveSugg);
                bom.setAppFlag(newStep.toString());
                bomMapper.updateByPrimaryKeySelective(bom);
                if ("step0".equals(newStep.toString())) {
                    bomService.appBomRequire(billId);
                }
                break;

            default:
                err.append("不正确的业务类型，失败！");
                return null;
        }
        return retMap;
    }

    /**
     * 获取业务数据的审批记录
     * @param billId
     * @param billType
     * @return
     */
    @Override
    public List<ApproveRecordHis> selectApproveRecordsByApproveId(Long billId, String billType) {
        Map<String, Object> map = new HashMap<>();
        map.put("approveId", billId);
        map.put("approveType", Byte.parseByte(billType + ""));
        return approveRecordHisMapper.selectApproveRecordsByApproveId(map);
    }
}

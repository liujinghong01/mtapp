package com.mt.pc.purchase.service.impl;

/**
 * created by fhk on 2017.11.30
 * 产生物料需求计划服务类
 */


import com.mt.pc.common.dao.PcRequirePlanMapper;
import com.mt.pc.common.model.PcRequirePlan;
import com.mt.pc.purchase.service.CreateMatRequirePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class CreateMatRequirePlanServiceImpl implements CreateMatRequirePlanService{


    @Autowired
    private PcRequirePlanMapper pcRequirePlanMapper;


    /**
     * 请购单审批结束后，从请购单生成物料需求计划
     * @param requestId
     * @return
     */
    @Override
    public boolean createMatRequirePlanfromRequest(Long requestId, Long uid) {
        Map<String, Object> map = new HashMap<>();
        map.put("requestId", requestId);
        map.put("uid", uid);
        String retStr = pcRequirePlanMapper.createMatRequirePlanfromRequest(map);
        if (retStr != null && "1".equals(retStr.substring(0,1))) {
            return true;
        } else {
            return true;
        }
    }

    /**
     * 客户订单下达后， 先生成该模号的成品物料，然后如果模具是外购，还需要生成该模具的物料需求计划
     * @param companyId
     * @param moldNo
     * @return
     */
    @Override
    public boolean createMatRequirePlanfromMold(Integer companyId, String moldNo, Long uid)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", companyId);
        map.put("moldNo", moldNo);
        map.put("uid", uid);
        String retStr = pcRequirePlanMapper.createMatRequirePlanfromMold(map);
        if (retStr != null && retStr.substring(0,1).equals("1")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * BOM审批后生成物料需求计划调用
     * @param pcRequirePlan
     * @return
     */
    @Override
    public boolean createMatRequirePlanfromBom(PcRequirePlan pcRequirePlan) {
        //  `company_id` int(12) DEFAULT NULL COMMENT '公司id',
        //  `company_name` varchar(256) DEFAULT NULL COMMENT '公司名称',
        //  `create_type` tinyint(4) DEFAULT NULL COMMENT '创建类型： 1 Bom; 2 模具外购; 3 请购单;4 仓库报警; 5 库存下料转釆购；6质量事故单',
        //  `create_type_name` varchar(64) DEFAULT NULL COMMENT '创建类型名称',
        //  `bom_id` bigint(20) DEFAULT NULL COMMENT 'BomId',
        //  `node_pos` bigint(20) DEFAULT NULL COMMENT '物料在BOM中唯一位置',
        //  `mat_no` varchar(200) DEFAULT NULL COMMENT '物料编码',
        //  `mat_desc` varchar(200) DEFAULT NULL COMMENT '物料名称',
        //  `mat_type_id` bigint(20) DEFAULT NULL,
        //  `mat_type_name` varchar(32) DEFAULT NULL COMMENT '物料类型名称',
        //  `mat_model` varchar(128) DEFAULT NULL COMMENT '规格型号',
        //  `plan_price` decimal(20,5) DEFAULT NULL COMMENT '物料计划单价',
        //  `unit` varchar(20) DEFAULT NULL COMMENT '单位',
        //  `plan_qty` decimal(20,5) DEFAULT NULL COMMENT '申请数量',
        //  `plan_up` decimal(20,5) DEFAULT NULL COMMENT '申请价格',
        //  `plan_weight` decimal(20,5) DEFAULT NULL COMMENT '申请重量',
        //  `total_price` decimal(20,5) DEFAULT NULL COMMENT '申请总金额',
        // 以上数据为传入的

        Map<String, Object> map = pcRequirePlanMapper.getDefaultCoin(pcRequirePlan.getCompanyId());

        pcRequirePlan.setCoin(map == null || map.get("dict_code") == null ? null : map.get("dict_code").toString());
        pcRequirePlan.setCoinName(map == null || map.get("dict_name") == null ? null : map.get("dict_name").toString());
        pcRequirePlan.setCreateType(Byte.parseByte(1 + ""));
        pcRequirePlan.setCreateTypeName("Bom");

        //今天+1
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天

        pcRequirePlan.setDeliveryDate(c.getTime());
        pcRequirePlan.setSupplyType(Byte.parseByte("0"));
        pcRequirePlan.setSupplyTypeName("直接采购");

        pcRequirePlanMapper.insertSelective(pcRequirePlan);
        return true;
    }
}

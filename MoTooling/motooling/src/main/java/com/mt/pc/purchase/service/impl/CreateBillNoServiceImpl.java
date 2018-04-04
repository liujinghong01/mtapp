package com.mt.pc.purchase.service.impl;

/**
 * created by fhk on 2017.12.1
 * 各种单号生成服务类
 */

import com.mt.common.bo.AccountValidatorUtil;
import com.mt.pc.common.dao.SysBillNoConfMapper;
import com.mt.pc.purchase.service.CreateBillNoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CreateBillNoServiceImpl implements CreateBillNoService {

    @Autowired
    private SysBillNoConfMapper sysBillNoConfMapper;


    @Override
    public Map<String, Object>createBillNo(Integer companyId, String billTypeCode) {
        return this.createBillNo(companyId, billTypeCode, null);
    }
    /**
     * 生成各种类型的单号
     * @param companyId
     * @param billTypeCode：
            报价核算单号 BA
            报价单号 QT
            客户定单号 CO
            请购单号 PR
            物料询价单号 MI
            外协询价单号 AI
            物料采购定单号 MP
            外协采购定单号 AP
            部门领料单 DA
            模具领料单 MA
            下料单 MS
            盘点单 MC
            报废单 SC
            生产定单 PO
            送货单DM
            询价反馈单 IR
            客户采购单 CP
            模号 MO
            凭证单 PZ
            基础工序 GX
            工序领取确认号 IC
            工序质检单号 PQ
            来料质检单号 MQ

     * @param uid
     * @return：
         *  flag     --   String: "1" 成功，"0" 失败;
         *  desc     --   String: 失败原因；
         *  bill_no  --   String: 单号；
         *  bill_id  --   Long:   单Id (凭证没有Id)
 */
    @Override
    public Map<String, Object>createBillNo(Integer companyId, String billTypeCode, Long uid) {
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", companyId);
        map.put("billTypeCode", billTypeCode);
        map.put("uid", uid);
        String ret;
        synchronized(AccountValidatorUtil.CREATE_BILL_NO_SYNCHROMIZED) {
            ret = sysBillNoConfMapper.createBillNo(map);
        }
        Map<String, Object>  retMap = new HashMap<>();
        if (ret == null) {
            retMap.put("flag", "0");
            retMap.put("desc", "发生异常！");
        } else {
            String[] retArray = ret.split("\\|");
            if (retArray == null || (retArray.length != 3 && retArray.length != 2)) {
                retMap.put("flag", "0");
                retMap.put("desc", "发生异常！");
            } else if(retArray.length == 2) {
                retMap.put("flag", retArray[0]);
                if (!retArray[0].equals("1"))
                    retMap.put("desc", retArray[1]);
                else
                    retMap.put("bill_no", retArray[1]);
            } else {
                retMap.put("flag", retArray[0]);
                retMap.put("bill_no", retArray[1]);
                retMap.put("bill_id", retArray[2] == null || retArray[2].isEmpty() ? null : Long.parseLong(retArray[2]));
            }
        }
        return retMap;
    }


}

package com.mt.iv.warehouse.service.impl;


import com.mt.common.utils.DateUtil;
import com.mt.iv.common.dao.MaterialOutgoingMapper;
import com.mt.iv.common.dao.MaterialStorageMapper;
import com.mt.iv.warehouse.service.InventoryGoodsJudgmentService;
import com.mt.iv.warehouse.service.MaterialOutgoingService;
import com.mt.pc.purchase.service.CreateBillNoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MaterialOutgoingServiceImpl implements MaterialOutgoingService {

    private static Logger logger = Logger.getLogger(MaterialOutgoingServiceImpl.class);

    @Autowired
    private MaterialOutgoingMapper materialOutgoingMapper;

    @Autowired
    private CreateBillNoService createBillNoService;

    @Autowired
    private MaterialStorageMapper materialStorageMapper;

    @Autowired
    private InventoryGoodsJudgmentService inventoryGoodsJudgmentService;

    /**
     * 查询出库初始数据
     * 凭证号（退货出库），内部定单号（销售出库），领料单（部门发货出库），模号（模具发货出库）；
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectIvQueryInitData(Map<String, Object> map) {
           String bill_no = map.get("bill_no").toString().substring(0, 2);
        List<Map<String, Object>> list = new ArrayList<>();
        switch (bill_no) {
            //凭证号（退货出库）
            case "PZ":
                list= materialOutgoingMapper.selectQueryDataPZ(map);
                break;
            //内部定单号（销售出库）
            case "CO":
                list= materialOutgoingMapper.selectQueryDataCO(map);
                break;
            //领料单（部门发货出库）
            case "DA":
            //模号（模具发货出库） 模具领料单
            case "MA":
                list= materialOutgoingMapper.selectQueryDataDA(map);
                break;
        }
        Map<String, Object> param = new HashMap<>();
        param.put("bill_no", map.get("bill_no"));
        list.remove(null);
        param.put("out_store_detail_list", list);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("out_store_info", param);
        return returnMap;
    }


    /**
     *  根据单号出库
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> addIvBillInStore(Map<String, Object> map) {
        Map<String, Object> outStoreInfo = (Map<String, Object>) map.get("out_store_info");
        List< Map<String, Object>> list = (List<Map<String, Object>>) outStoreInfo.get("out_store_detail_list");

        Object voucherNo = createBillNoService.createBillNo(Integer.valueOf(map.get("company_id").toString()), "PZ", Long.valueOf(map.get("uid").toString())).get("bill_no");
//        String voucherNo ="PZ"+ StringUtils.getRandom(8);
        for ( Map<String, Object> outStoreDetailMap : list) {
            if (outStoreDetailMap.get("quantity") == null || outStoreDetailMap.get("quantity")==""){
                throw new IllegalArgumentException ("请填数量");
            }
            if (outStoreDetailMap.get("total_price") ==null || outStoreDetailMap.get("total_price")==""){
                throw new IllegalArgumentException ("请填金额");
            }
        outStoreDetailMap.put("quantity",-Double.valueOf(outStoreDetailMap.get("quantity").toString()));
        outStoreDetailMap.put("total_price",-Double.valueOf(outStoreDetailMap.get("total_price").toString()));
        if(outStoreDetailMap.get("weight")!=null && outStoreDetailMap.get("weight")!= "" ) {
            outStoreDetailMap.put("weight", -Double.valueOf(outStoreDetailMap.get("weight").toString()));
        }
        outStoreDetailMap.put("company_id",map.get("company_id"));
        outStoreDetailMap.put("uid",map.get("uid"));
        outStoreDetailMap.put("voucher_no", voucherNo);
        outStoreDetailMap.put("bill_no",outStoreInfo.get("bill_no"));
        outStoreDetailMap.put("dep_name",outStoreInfo.get("dep_name"));
        outStoreDetailMap.put("dep_id",outStoreInfo.get("dep_id"));
        outStoreDetailMap.put("handle_time",DateUtil.getDateTime());
            try {
                inventoryGoodsJudgmentService.inventoryGoodsJudgment(outStoreDetailMap);
            } catch (IllegalAccessException e) {
                logger.error("仓库变动异常        :" + e.getMessage());
            }
        Long id= null;
        Long  voucherId=null;
        String billNo = null;
        if(outStoreInfo.get("bill_no")!=null && outStoreInfo.get("bill_no") !=""){
            billNo  = outStoreInfo.get("bill_no").toString().substring(0, 2);
        }
        if(outStoreInfo.get("dep_id") != null && outStoreInfo.get("dep_id")!= "") {
            //`trans_type` '移动类型'  平台字典数据写死
            outStoreDetailMap.put("trans_type","DA_OUT");
            materialOutgoingMapper.addOutDepIdIvStore(outStoreDetailMap);
            id= Long.valueOf(outStoreDetailMap.get("id").toString());
            voucherId = id;
            outStoreDetailMap.put("voucher_id",voucherId);
            //领料单（部门发货出库）  可有单号。也可以没有单号  没有单号 情况
           materialOutgoingMapper.addOutVoucherDept(outStoreDetailMap);
        }
            switch (billNo) {
                //凭证号（退货出库）
                case "PZ":
                    String transType = materialStorageMapper.selectTransType(outStoreDetailMap.get("bill_no").toString());
                    switch (transType){
                        case "MA_OUT":
                            voucherId = addOutVoucherMold(outStoreDetailMap);
                            break;
                        case "DA_OUT":
                            voucherId = addOutVoucherDept(outStoreDetailMap);
                            break;
                        case "CO_OUT":
                            voucherId = addoutVocherSell(outStoreDetailMap);
                            break;
                        default:
                            throw new IllegalArgumentException ("数据类型异常");
                    }
                    break;
                //内部定单号（销售出库）CO_OUT
                case "CO":
                    voucherId = addoutVocherSell(outStoreDetailMap);
                    break;
                //领料单（部门发货出库）
                case "DA":
                    voucherId = addOutVoucherDept(outStoreDetailMap);
                    break;
                //模号（模具发货出库） 模具领料单
                case "MA":
                    voucherId = addOutVoucherMold(outStoreDetailMap);
                    break;
                default:
                    throw new IllegalArgumentException ("此单号类型不能出库");
            }
        }
        Map<String,Object> returnMap = new HashMap<>();
        Map<String,Object> voucherNoMap = new HashMap<>();
        voucherNoMap.put("voucher_no",voucherNo);
        returnMap.put("voucher_info",voucherNoMap);
        return returnMap;
    }



    private Long addOutVoucherMold(Map<String, Object> outStoreDetailMap) {
        Long id;
        Long voucherId;
        outStoreDetailMap.put("trans_type","MA_OUT");
        materialOutgoingMapper.addOutDepIdIvStore(outStoreDetailMap);
        id= Long.valueOf(outStoreDetailMap.get("id").toString());
        voucherId = id;
        outStoreDetailMap.put("voucher_id",voucherId);
        outStoreDetailMap.put("apply_id", materialOutgoingMapper.selectApplyId(outStoreDetailMap.get("bill_no").toString()));//根据领料单单号 查领料单ID
        Map<String,Object> bomMap = materialOutgoingMapper.selectBomType(outStoreDetailMap.get("mat_no").toString()); //根据物料编码 查询物料类型
        if(bomMap!=null) {
            outStoreDetailMap.put("bom_type", bomMap.get("bom_type"));
        }
        materialOutgoingMapper.addOutVoucherMold(outStoreDetailMap);
        outStoreDetailMap.put("quantity",-Double.valueOf(outStoreDetailMap.get("quantity").toString()));
        outStoreDetailMap.put("total_price",-Double.valueOf(outStoreDetailMap.get("total_price").toString()));
        if(outStoreDetailMap.get("weight")!=null && outStoreDetailMap.get("weight")!= "" ) {
            outStoreDetailMap.put("weight", -Double.valueOf(outStoreDetailMap.get("weight").toString()));
        }
        materialOutgoingMapper.updateApplyMatBill(outStoreDetailMap);
        return voucherId;
    }

    private Long addOutVoucherDept(Map<String, Object> outStoreDetailMap) {
        Long id;
        Long voucherId;
        outStoreDetailMap.put("trans_type","DA_OUT");
        materialOutgoingMapper.addOutDepIdIvStore(outStoreDetailMap);
        id= Long.valueOf(outStoreDetailMap.get("id").toString());
        voucherId = id;
        outStoreDetailMap.put("voucher_id",voucherId);
        outStoreDetailMap.put("apply_id",materialOutgoingMapper.selectApplyId(outStoreDetailMap.get("bill_no").toString()));//根据领料单号查寻出ID

        materialOutgoingMapper.addOutVoucherDept(outStoreDetailMap);
        return voucherId;
    }

    private Long addoutVocherSell(Map<String, Object> outStoreDetailMap) {
        Long id;
        Long voucherId;
        outStoreDetailMap.put("trans_type","CO_OUT");
        materialOutgoingMapper.addOutDepIdIvStore(outStoreDetailMap);
        id= Long.valueOf(outStoreDetailMap.get("id").toString());
        voucherId = id;
        outStoreDetailMap.put("voucher_id",voucherId);
        materialOutgoingMapper.addoutVocherSell(outStoreDetailMap);
        return voucherId;
    }
}

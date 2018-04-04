package com.mt.iv.warehouse.service.impl;

import com.mt.common.utils.DateUtil;
import com.mt.common.utils.StringUtils;
import com.mt.iv.common.dao.MaterialStorageMapper;
import com.mt.iv.warehouse.service.InventoryGoodsJudgmentService;
import com.mt.iv.warehouse.service.MaterialStorageService;
import com.mt.pc.purchase.service.CreateBillNoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MaterialStorageServiceImpl implements MaterialStorageService {
    private static Logger logger = Logger.getLogger(MaterialOutgoingServiceImpl.class);
    @Autowired
    private MaterialStorageMapper materialStorageMapper;
    @Autowired
    private CreateBillNoService createBillNoService;
    @Autowired
    private InventoryGoodsJudgmentService inventoryGoodsJudgmentService;


    /**
     * 模糊查询单号（用于出入库）列表
     *
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectBillSearch(Map<String, Object> map) {
        Map<String, Object> param = (Map<String, Object>) map.get("query");
        String typeName = "单号";
        switch (Integer.valueOf(param.get("type").toString())) {
            case 1:
                typeName = "模号";
                map.put("bill_no", "mold_no");
                map.put("table", "mold");
                break;
            case 2:
                typeName = "生产单号";
                map.put("bill_no", "");
                map.put("table", "");
                break;
            case 3:
                typeName = "采购单号";
                map.put("bill_no", "purch_no");
                map.put("table", "pc_purch_order");
                break;
            case 4:
                typeName = "送货单号";
                map.put("bill_no", "delivery_no");
                map.put("table", "pc_delivery");
                break;
            case 5:
                typeName = "内部定单号";
                map.put("bill_no", "project_no");
                map.put("table", "project");
                break;
            case 6:
                typeName = "领料单号";
                map.put("bill_no", "apply_no");
                map.put("table", "iv_apply_mat_bill");
                break;
            case 7:
                typeName = "入库凭证号";
                map.put("bill_no", "voucher_no");
                map.put("table", "voucher");
                map.put("trans_direction", "1");
                break;
            case 8:
                typeName = "出库凭证号";
                map.put("bill_no", "voucher_no");
                map.put("table", "voucher");
                map.put("trans_direction", "0");
                break;
        }
        map.put("page", ((Integer) map.get("curr_page") - 1) * (Integer) map.get("page_size"));
        List<Map<String, Object>> list = materialStorageMapper.selectBillSearch(map);
        if(list.size()<=0){
            return StringUtils.pageList(map,"list",0);
        }
        Integer totalCount = materialStorageMapper.selectBillSearchTotalCount(map);
        Map<String, Object> returnMap = new HashMap<>();
        if (list == null && list.size() < 1) {
                return StringUtils.pageList(map,"list",0);
        }
        for (Map<String, Object> objectMap : list) {
            objectMap.put("type", typeName);
        }
        Map<String, Object> page = StringUtils.page(map, list, "bill_no_list", totalCount);
        return page;
    }


    /**
     * 查询入库初始数据
     * 凭证号（退货入库），采购单号和送货单号（采购入库），生产单号（生产收货入库）
     * 凭证单 PZ        物料采购定单号 MP    送货单DM        生产定单PO
     */
    @Override
    public Map<String, Object> selectIvQueryInitData(Map<String, Object> map) {
        String bill_no = map.get("bill_no").toString().substring(0, 2);
        List<Map<String, Object>> list = new ArrayList<>();
        switch (bill_no) {
            case "PZ":
                list = materialStorageMapper.selectQueryDataPZ(map);
                break;
            case "DM":
                list=materialStorageMapper.selectPurchNo(map.get("bill_no").toString());
                break;
            case "MP":
                list = materialStorageMapper.selectQueryDataMP(map);
                break;
            case "PO":
                break;
        }
        list.remove(null);
        Map<String, Object> param = new HashMap<>();
        param.put("bill_no", map.get("bill_no"));
        param.put("in_store_detail_list", list);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("in_store_info", param);
        return returnMap;
    }


    /**
     * 查询单号所对应的出入库凭证
     *
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectVoucher(Map<String, Object> map) {
        Map<String, Object> query = (Map<String, Object>) map.get("query");
        List<Map<String, Object>> list = new ArrayList<>();
        if (query.get("bill_no") != null && query.get("bill_no") != "") {
            String typeName = query.get("bill_no").toString().substring(0, 2);
            ;
            switch (typeName) {
                //模号
                case "MO":
                    map.put("bill", "mold_no");
                    map.put("table", "voucher_mold");
                    break;
                //内部订单
                case "CO":
                    map.put("bill", "project_no");
                    map.put("table", "voucher_sell");
                    break;
            }
            list = materialStorageMapper.getBillNoVoucherList(map);
        } else {
            list = materialStorageMapper.getDepIdVoucherNoList(map);
        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("voucher_no_list", list);
        return returnMap;
    }


    /**
     * 根据单号入库
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> addIvBillInStore(Map<String, Object> map) {
        Map<String, Object> inStoreInfo = (Map<String, Object>) map.get("in_store_info");
        List< Map<String, Object>> list = (List<Map<String, Object>>) inStoreInfo.get("in_store_detail_list");
        Object voucherNo = createBillNoService.createBillNo(Integer.valueOf(map.get("company_id").toString()), "PZ", Long.valueOf(map.get("uid").toString())).get("bill_no");
        for ( Map<String, Object> inStoreDetailMap : list) {
            inStoreDetailMap.put("company_id", map.get("company_id"));
            inStoreDetailMap.put("uid", map.get("uid"));
            inStoreDetailMap.put("voucher_no", voucherNo);
            inStoreDetailMap.put("bill_no", inStoreInfo.get("bill_no"));
            inStoreDetailMap.put("dep_name", inStoreInfo.get("dep_name"));
            inStoreDetailMap.put("dep_id", inStoreInfo.get("dep_id"));
            String date = DateUtil.dateToString(new Date(), DateUtil.DATETIME_PATTERN);
            inStoreDetailMap.put("handle_time", date);
            try {
                inventoryGoodsJudgmentService.inventoryGoodsJudgment(inStoreDetailMap);
            } catch (IllegalAccessException e) {
                logger.error("仓库变动异常        :" + e.getMessage());
            }
            Long voucherId = null;
            Long id = null;
            if (inStoreInfo.get("dep_id") != null && inStoreInfo.get("dep_id") != "") {
                voucherId = addvoucherDept(inStoreDetailMap);
                id = Long.valueOf(inStoreInfo.get("id").toString());
            } else {
                String bill_no = inStoreInfo.get("bill_no").toString().substring(0, 2);
                switch (bill_no) {
                    //凭证号（退货入库）
                    case "PZ":
                        String transType = materialStorageMapper.selectTransType(inStoreInfo.get("bill_no").toString());   //根据凭证单号查询移动类型
                        switch (transType) {
                            //采购收货入库
                            case "MP_IN":
                                voucherId = addVoucherPurch(inStoreDetailMap);
                                break;
                            //生产收货入库
                            case "PO_IN":
                                break;
                            //部门退货入库
                            case "DA_IN":
                                voucherId = addvoucherDept(inStoreDetailMap);
                                break;
                        }
                        break;
                    //送货单号
                    case "DM":
                        inStoreDetailMap.put("DM","DM");
                        voucherId = addVoucherPurch(inStoreDetailMap);
                        break;
                    //采购单号
                    case "MP":
                        voucherId = addVoucherPurch(inStoreDetailMap);
                        break;
                    //生产单号
                    case "PO":
                        inStoreDetailMap.put("trans_type", "PO_IN");
                        break;
                    default:
                        throw new IllegalArgumentException("此单号类型不能入库");
                }
            }
        }
        Map<String,Object> returnMap = new HashMap<>();
        Map<String,Object> voucherNoMap = new HashMap<>();
        voucherNoMap.put("voucher_no",voucherNo);
        returnMap.put("voucher_info",voucherNoMap);
        return returnMap;
    }



    private Long addvoucherDept(Map<String, Object> inStoreDetailMap) {
        Long id;
        Long voucherId;
        inStoreDetailMap.put("trans_type","DA_IN");
        materialStorageMapper.addDepIdIvStore(inStoreDetailMap);
        id = Long.valueOf(inStoreDetailMap.get("id").toString());
        voucherId  = id;
        inStoreDetailMap.put("voucher_id",voucherId);
        //部门ID 不为空。 部门退货入库
        materialStorageMapper.addvoucherDept(inStoreDetailMap);
        return voucherId;
    }

    private Long addVoucherPurch(Map<String, Object> inStoreDetailMap) {
        Long id;
        Long voucherId;
        inStoreDetailMap.put("trans_type","MP_IN");
        materialStorageMapper.addDepIdIvStore(inStoreDetailMap);
        id = Long.valueOf(inStoreDetailMap.get("id").toString());
        voucherId  = id;
        inStoreDetailMap.put("voucher_id",voucherId);
        inStoreDetailMap.put("is_stock_in_finished",1);
        materialStorageMapper.updatePurchQuantity(inStoreDetailMap);
        materialStorageMapper.addVoucherPurch(inStoreDetailMap);
        return voucherId;
    }


}

package com.mt.iv.warehouse.service.impl;

import com.mt.common.utils.DateUtil;
import com.mt.iv.common.dao.*;
import com.mt.iv.warehouse.service.InventoryGoodsJudgmentService;
import com.mt.iv.warehouse.service.StoreDirectChangeService;
import com.mt.pc.purchase.service.CreateBillNoService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
* @Author: Wendy
* @Description:
* @create    2018/1/24 14:54
* @Modified By:
*/    
@Service
public class StoreDirectChangeServiceImpl implements StoreDirectChangeService{
    private static Logger logger = Logger.getLogger(StoreDirectChangeServiceImpl.class);
    @Autowired
    private VoucherMapper        voucherMapper;

    @Autowired
    private VoucherDirectMapper  voucherDirectMapper;

    @Autowired
    private VoucherSwitchMapper  voucherSwitchMapper;

    @Autowired
    private CreateBillNoService  createBillNoService;

    @Autowired
    private IvStoreDirectChangeMapper  ivStoreDirectChangeMapper;

    @Autowired
    private IvStoreDirectChangeSubMapper  ivStoreDirectChangeSubMapper;

      @Autowired
    private InventoryGoodsJudgmentService inventoryGoodsJudgmentService;

    /**
     * 库存物料管理(直接入库、直接出库、调拨)提交
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> storeDirectChangeManager(Map<String, Object> map) {
        Map<String,Object>  changeInfo=(Map<String,Object>)map.get("change_info");
        changeInfo.put("company_id",map.get("company_id"));
        changeInfo.put("company_name",map.get("company_name"));
        List<Map<String,Object>>  changeDetailList=(List<Map<String,Object>>)changeInfo.get("change_detail_list");

        String voucherNo = createBillNoService.createBillNo(Integer.valueOf(map.get("company_id").toString()), "PZ", Long.valueOf(map.get("uid").toString())).get("bill_no").toString();

        Long  id=null;
        for(Map<String,Object>  inOutStoreDetailMap:changeDetailList) {
             if (inOutStoreDetailMap.get("quantity") == null || inOutStoreDetailMap.get("quantity")==""){
                throw new IllegalArgumentException ("请填数量");
            }
            if (changeInfo.get("total_price") ==null || changeInfo.get("total_price")==""){
                throw new IllegalArgumentException ("请填金额");
            }

            boolean  flag=changeInfo.get("out_store_house_id") != null && changeInfo.get("out_store_house_id") != "" && changeInfo.get("in_store_house_id") != null && changeInfo.get("in_store_house_id") != "";
            if (flag) {
                // 调拨
                this.allocationOutOfStorage(map);

            }else if (changeInfo.get("out_store_house_id") != null && changeInfo.get("out_store_house_id") != "") {
                inOutStoreDetailMap.put("store_room_id", inOutStoreDetailMap.get("out_store_room_id").toString());
                inOutStoreDetailMap.put("store_house_id", changeInfo.get("out_store_house_id").toString());
                inOutStoreDetailMap.put("mat_id", inOutStoreDetailMap.get("mat_id").toString());
                inOutStoreDetailMap.put("quantity", -Double.valueOf(inOutStoreDetailMap.get("quantity").toString()));
                inOutStoreDetailMap.put("total_price", -Double.valueOf(changeInfo.get("total_price").toString())+"");
                inOutStoreDetailMap.put("company_id", map.get("company_id"));
                inOutStoreDetailMap.put("uid", map.get("uid"));

                try {
                    inventoryGoodsJudgmentService.inventoryGoodsJudgment(inOutStoreDetailMap);
                } catch (IllegalAccessException e) {
                    logger.error("仓库变动异常        :" + e.getMessage());
                }

                // 新增库存变更主表
                changeInfo.put("created_at", DateUtil.getDateTime());
                ivStoreDirectChangeMapper.addStoreDirectChange(changeInfo);
                id = Long.valueOf(changeInfo.get("id").toString().trim());

                // 新增凭证表和分类凭证表
                inOutStoreDetailMap.put("voucher_no", voucherNo);
                inOutStoreDetailMap.put("trans_type", "DR_OUT");
                inOutStoreDetailMap.put("change_id", changeInfo.get("change_id"));
                inOutStoreDetailMap.put("change_no", changeInfo.get("change_no"));
                inOutStoreDetailMap.put("handler_id", map.get("uid"));
                inOutStoreDetailMap.put("remark", changeInfo.get("remark"));
                inOutStoreDetailMap.put("trans_direction","0");
                voucherMapper.addVoucher(inOutStoreDetailMap);
                voucherDirectMapper.addVoucherDirect(inOutStoreDetailMap);
                inOutStoreDetailMap.get("id");

                // 新增库存变更子表
                inOutStoreDetailMap.put("created_at", DateUtil.getDateTime());
                inOutStoreDetailMap.put("change_type", changeInfo.get("change_type"));
                ivStoreDirectChangeSubMapper.addStoreDirectChangeSub(inOutStoreDetailMap);
            } else if (changeInfo.get("in_store_house_id") != null  && changeInfo.get("in_store_house_id") != "") {
                inOutStoreDetailMap.put("store_room_id", inOutStoreDetailMap.get("in_store_room_id").toString());
                inOutStoreDetailMap.put("store_house_id", changeInfo.get("in_store_house_id").toString());
                inOutStoreDetailMap.put("mat_id", inOutStoreDetailMap.get("mat_id").toString());
                inOutStoreDetailMap.put("quantity", Double.valueOf(inOutStoreDetailMap.get("quantity").toString()));
                inOutStoreDetailMap.put("total_price",Double.valueOf(changeInfo.get("total_price").toString()));
                inOutStoreDetailMap.put("company_id", map.get("company_id"));
                inOutStoreDetailMap.put("uid", map.get("uid"));

                // InStore
                try {
                    inventoryGoodsJudgmentService.inventoryGoodsJudgment(inOutStoreDetailMap);
                } catch (IllegalAccessException e) {
                    logger.error("仓库变动异常        :" + e.getMessage());
                }
                // 新增库存变更主表
                changeInfo.put("created_at", DateUtil.getDateTime());
                ivStoreDirectChangeMapper.addStoreDirectChange(changeInfo);
                id = Long.valueOf(changeInfo.get("id").toString().trim());

                // 新增凭证表和分类凭证表
                inOutStoreDetailMap.put("voucher_no", voucherNo);
                inOutStoreDetailMap.put("trans_type", "DR_IN");
                inOutStoreDetailMap.put("change_id", changeInfo.get("change_id"));
                inOutStoreDetailMap.put("change_no", changeInfo.get("change_no"));
                inOutStoreDetailMap.put("handler_id", map.get("uid"));
                inOutStoreDetailMap.put("remark", changeInfo.get("remark"));
                inOutStoreDetailMap.put("company_name", map.get("company_name"));
                inOutStoreDetailMap.put("trans_direction","1");
                voucherMapper.addVoucher(inOutStoreDetailMap);
                voucherDirectMapper.addVoucherDirect(inOutStoreDetailMap);
                inOutStoreDetailMap.get("id");

                // 新增库存变更子表
                inOutStoreDetailMap.put("created_at", DateUtil.getDateTime());
                inOutStoreDetailMap.put("change_type", changeInfo.get("change_type"));
                ivStoreDirectChangeSubMapper.addStoreDirectChangeSub(inOutStoreDetailMap);
            }
        }

        Map<String,Object>  changeInfoParam=ivStoreDirectChangeMapper.selectStoreDirectChange(Long.valueOf(changeInfo.get("id").toString()));
        changeInfoParam.put("voucher_no",voucherNo);
        Map<String,Object>  returnMap=new HashedMap();
        returnMap.put("change_info",changeInfoParam);
        return returnMap;
    }



    // 调拨（OutInStore出入库）
    private void allocationOutOfStorage(Map<String, Object> map) {
        Map<String, Object> changeInfo = (Map<String, Object>) map.get("change_info");
        changeInfo.put("company_id", map.get("company_id"));
        changeInfo.put("company_name", map.get("company_name"));
        List<Map<String, Object>> changeDetailList = (List<Map<String, Object>>) changeInfo.get("change_detail_list");
        changeInfo.remove("change_detail_list");


        for (Map<String, Object> inOutStoreDetailMap : changeDetailList) {
            if (changeInfo.get("out_store_house_id") != null && changeInfo.get("out_store_house_id") != "") {
                inOutStoreDetailMap.put("store_room_id", inOutStoreDetailMap.get("out_store_room_id").toString());
                inOutStoreDetailMap.put("store_house_id", changeInfo.get("out_store_house_id").toString());
                inOutStoreDetailMap.put("mat_id", inOutStoreDetailMap.get("mat_id").toString());
                inOutStoreDetailMap.put("quantity", -Double.valueOf(inOutStoreDetailMap.get("quantity").toString()));
                inOutStoreDetailMap.put("total_price", -Double.valueOf(changeInfo.get("total_price").toString())+"");
                inOutStoreDetailMap.put("company_id", map.get("company_id"));
                inOutStoreDetailMap.put("uid", map.get("uid"));

                try {
                    inventoryGoodsJudgmentService.inventoryGoodsJudgment(inOutStoreDetailMap);
                } catch (IllegalAccessException e) {
                    logger.error("仓库变动异常        :" + e.getMessage());
                }

                // 新增库存变更主表
                changeInfo.put("created_at", DateUtil.getDateTime());
                ivStoreDirectChangeMapper.addStoreDirectChange(changeInfo);
                Long id = Long.valueOf(changeInfo.get("id").toString().trim());


                // 新增凭证表和分类凭证表
                inOutStoreDetailMap.put("voucher_no", createBillNoService.createBillNo(Integer.valueOf(map.get("company_id").toString()), "PZ", Long.valueOf(map.get("uid").toString())).get("bill_no").toString());
                inOutStoreDetailMap.put("trans_type", "AT_OUT");
                inOutStoreDetailMap.put("change_id", changeInfo.get("change_id"));
                inOutStoreDetailMap.put("change_no", changeInfo.get("change_no"));
                inOutStoreDetailMap.put("handler_id", map.get("uid"));
                inOutStoreDetailMap.put("remark", changeInfo.get("remark"));
                inOutStoreDetailMap.put("company_name", map.get("company_name"));
                inOutStoreDetailMap.put("trans_direction","0");
                voucherMapper.addVoucher(inOutStoreDetailMap);
                voucherSwitchMapper.addVoucherSwitch(inOutStoreDetailMap);
                inOutStoreDetailMap.get("id");

                // 新增库存变更子表
                inOutStoreDetailMap.put("created_at", DateUtil.getDateTime());
                inOutStoreDetailMap.put("change_type", changeInfo.get("change_type"));
                ivStoreDirectChangeSubMapper.addStoreDirectChangeSub(inOutStoreDetailMap);
            }

            if (changeInfo.get("in_store_house_id") != null && changeInfo.get("in_store_house_id")!="") {
                inOutStoreDetailMap.put("store_room_id", inOutStoreDetailMap.get("in_store_room_id").toString());
                inOutStoreDetailMap.put("store_house_id", changeInfo.get("in_store_house_id").toString());
                inOutStoreDetailMap.put("mat_id", inOutStoreDetailMap.get("mat_id").toString());
                inOutStoreDetailMap.put("quantity", Double.valueOf(inOutStoreDetailMap.get("quantity").toString()));
                inOutStoreDetailMap.put("total_price", Double.valueOf(changeInfo.get("total_price").toString()));
                inOutStoreDetailMap.put("company_id", map.get("company_id"));
                inOutStoreDetailMap.put("uid", map.get("uid"));

                // InStore
                try {
                    inventoryGoodsJudgmentService.inventoryGoodsJudgment(inOutStoreDetailMap);
                } catch (IllegalAccessException e) {
                    logger.error("仓库变动异常        :" + e.getMessage());
                }
                // 新增库存变更主表
                changeInfo.put("created_at", DateUtil.getDateTime());
                ivStoreDirectChangeMapper.addStoreDirectChange(changeInfo);
                Long id = Long.valueOf(changeInfo.get("id").toString().trim());

                // 新增凭证表和分类凭证表
                inOutStoreDetailMap.put("voucher_no", createBillNoService.createBillNo(Integer.valueOf(map.get("company_id").toString()), "PZ", Long.valueOf(map.get("uid").toString())).get("bill_no").toString());
                inOutStoreDetailMap.put("trans_type", "AT_IN");
                inOutStoreDetailMap.put("change_id", changeInfo.get("change_id"));
                inOutStoreDetailMap.put("change_no", changeInfo.get("change_no"));
                inOutStoreDetailMap.put("handler_id", map.get("uid"));
                inOutStoreDetailMap.put("remark", changeInfo.get("remark"));
                inOutStoreDetailMap.put("company_name", map.get("company_name"));
                inOutStoreDetailMap.put("trans_direction","1");
                voucherMapper.addVoucher(inOutStoreDetailMap);
                voucherSwitchMapper.addVoucherSwitch(inOutStoreDetailMap);
                inOutStoreDetailMap.get("id");

                // 新增库存变更子表
                inOutStoreDetailMap.put("created_at", DateUtil.getDateTime());
                inOutStoreDetailMap.put("change_type", changeInfo.get("change_type"));
                ivStoreDirectChangeSubMapper.addStoreDirectChangeSub(inOutStoreDetailMap);
            }
        }
    }
}
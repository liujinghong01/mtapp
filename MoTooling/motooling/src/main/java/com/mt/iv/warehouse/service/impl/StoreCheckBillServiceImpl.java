package com.mt.iv.warehouse.service.impl;

import com.mt.common.utils.DateUtil;
import com.mt.common.utils.StringUtils;
import com.mt.iv.common.dao.*;
import com.mt.iv.warehouse.service.InventoryGoodsJudgmentService;
import com.mt.iv.warehouse.service.StoreCheckBillService;
import com.mt.pc.purchase.service.CreateBillNoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Author: Wendy
* @Description:
* @create    2018/1/24 14:52
* @Modified By:
*/    
@Service
public class StoreCheckBillServiceImpl implements StoreCheckBillService{
    private static Logger logger = Logger.getLogger(StoreCheckBillServiceImpl.class);

    @Autowired
    private VoucherMapper voucherMapper;

    @Autowired
    private IvStoreRoomMapper ivStoreRoomMapper;

    @Autowired
    private CreateBillNoService createBillNoService;

    @Autowired
    private VoucherCheckMapper   voucherCheckMapper;

    @Autowired
    private IvStoreCheckBillMapper  ivStoreCheckBillMapper;

    @Autowired
    private IvStoreCheckBillSubMapper ivStoreCheckBillSubMapper;

    @Autowired
    private InventoryGoodsJudgmentService inventoryGoodsJudgmentService;


    /**
     * 盘点单生成
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> addStoreCheckBill(Map<String, Object> map) {
        Map<String,Object>  checkInfo=(Map<String,Object>)map.get("check_info");
        checkInfo.put("company_id",map.get("company_id"));
        checkInfo.put("company_name",map.get("company_name"));
        List<Map<String,Object>>  matTypeList=(List<Map<String,Object>>)checkInfo.get("mat_type_list");
        checkInfo.remove("mat_type_list");

        if(checkInfo.get("store_house_id")==null || checkInfo.get("store_house_id")==""){
                throw   new  NullPointerException("请选择仓库");
        }
        if(checkInfo.get("operator_id")==null || checkInfo.get("operator_id")==""){
            throw   new  NullPointerException("请选择经办人");
        }

        Long  id=null;
        checkInfo.put("creat_at", DateUtil.getDateTime());
        checkInfo.put("handler_id",map.get("uid"));
        checkInfo.put("creator_id",map.get("uid"));
        checkInfo.put("liquidate_flag","2");
        checkInfo.put("check_bill_no",createBillNoService.createBillNo(Integer.valueOf(map.get("company_id").toString()), "MC", Long.valueOf(map.get("uid").toString())).get("bill_no").toString());
        ivStoreCheckBillMapper.addStoreCheckBill(checkInfo);
        id=Long.valueOf(checkInfo.get("id").toString());

        if(matTypeList.size()==0){
            throw   new  NullPointerException("请选择物料类型");
        }

        for(Map<String,Object>  objectMap:matTypeList){
            boolean  flag=objectMap.get("mat_type_id")==null || objectMap.get("mat_type_id")==""||objectMap.get("mat_type_name")==null || objectMap.get("mat_type_name")=="";
            if(flag){
                throw   new  NullPointerException("请选择物料类型");
            }

            objectMap.put("id", checkInfo.get("id") == null || checkInfo.get("id") == "" ? id : checkInfo.get("id"));
            checkInfo.put("mat_type_id",objectMap.get("mat_type_id"));

            //生成物料类型
            objectMap.put("check_bill_id",checkInfo.get("id"));
            ivStoreCheckBillSubMapper.addStoreCheckBillMatType(objectMap);

            // 根据仓库和物料类型去库位库存中查到符合条件的所有物料(保存到详情表生成盘点单)
            List<Map<String,Object>>  storeRoomList=ivStoreRoomMapper.selectStoreRoomMatDetail(checkInfo);

            for(Map<String,Object>  storeRoomParam:storeRoomList) {
                objectMap.put("created_at", DateUtil.getDateTime());
                objectMap.put("id", checkInfo.get("id"));
                objectMap.putAll(storeRoomParam);
                ivStoreCheckBillSubMapper.addStoreCheckBillSubDetail(objectMap);

                // 物料锁定
                ivStoreRoomMapper.updateStoreCheckFlag(objectMap);
            }
        }

        Map<String,Object>  storeCheckById=ivStoreCheckBillMapper.selectStoreCheckBillById(id);
        Map<String,Object>  returnMap=new HashMap<String,Object>();
        returnMap.put("check_info",storeCheckById);
        return returnMap;
    }


    /**
    * @Author: Wendy
    * @Description:盘点单盘点
    * @create    2018/1/8 15:30
    * @Modified By:
    */
    @Override
    public Map<String, Object> updateStoreCheckBill(Map<String, Object> map) {
        Map<String, Object> checkInfo = (Map<String, Object>) map.get("check_info");
        checkInfo.put("company_id", map.get("company_id"));
        checkInfo.put("mat_id",checkInfo.get("mat_id"));
        checkInfo.put("id",checkInfo.get("check_bill_id").toString().trim());
        checkInfo.put("check_bill_id",checkInfo.get("check_bill_id").toString().trim());
        checkInfo.put("updated_at", DateUtil.getDateTime());
        Integer  i=ivStoreCheckBillSubMapper.updateStoreCheckMatIdDetail(checkInfo);
        this.judgLiquidateFlag(checkInfo);
        return checkInfo;
    }



   /**
   * @Author: Wendy
   * @Description: 查询盘点单列表
   * @create    2018/1/8 15:30
   * @Modified By:
   */
   @Override
    public Map<String, Object> selectStoreCheckBillList(Map<String, Object> map) {
        if(map.containsKey("query")) {
            Map<String, Object> query = (Map<String, Object>) map.get("query");
            boolean  flg=query.get("approve_step")!=null&&query.get("approve_step")!=""&&query.get("liquidate_flag")!=null&&query.get("liquidate_flag")!="";
            if(flg){
                map.put("approve_step",query.get("approve_step"));
                map.put("liquidate_flag",query.get("liquidate_flag"));
            }
        }
        int totalCount  = ivStoreCheckBillMapper.totalCount(map);
        map.put("page", ((Integer) map.get("curr_page") - 1) * (Integer) map.get("page_size"));

        List<Map<String, Object>> storeCheckList =ivStoreCheckBillMapper.selectStoreCheckBillList(map);
        if(storeCheckList.size()<=0){
           return StringUtils.pageList(map,"check_bill_list",0);
        }
        for(Map<String,Object> paramlist:storeCheckList) {
            paramlist.put("check_bill_id", Integer.valueOf(paramlist.get("check_bill_id").toString().trim()));
            List<Map<String, Object>> matTypelist = ivStoreCheckBillMapper.selectStoreCheckMatTypeList(paramlist);
            paramlist.put("mat_type_list", matTypelist);
        }
        Map<String, Object> page= StringUtils.page(map, storeCheckList, "check_bill_list", totalCount);
        return page;
    }



    /**
    * @Author: Wendy
    * @Description:查询盘点单待盘和已盘的物料
    * @create    2018/1/8 15:30
    * @Modified By:
    */
    @Override
    public Map<String, Object> selectStoreCheckBillDetail(Map<String, Object> map) {
        if(map.containsKey("query")) {
            Map<String, Object> query = (Map<String, Object>) map.get("query");
            StringBuilder sb = new StringBuilder();
            List<Integer> typeList = (List<Integer>) query.get("mat_type_ids");
            if (typeList!=null && typeList.size()>0) {
                for (Integer s : typeList) {
                    sb.append(s).append("|");
                }
                if (sb.length() > 0) {
                    map.put("mat_type_ids", sb.toString().substring(0, sb.length() - 1));
                }
            }
        }
        int totalCount  = ivStoreCheckBillMapper.storeChecktotalCount(map);
        map.put("page", ((Integer) map.get("curr_page") - 1) * (Integer) map.get("page_size"));

        List<Map<String, Object>> checkDetailList =ivStoreCheckBillMapper.selectStoreCheckBillDetail(map);
        if(checkDetailList.size()<=0){
            return StringUtils.pageList(map,"scrap_mat_list",0);
        }
        Map<String, Object> page= StringUtils.page(map, checkDetailList, "check_detail_list", totalCount);
        return page;
    }


    /**
     * 盘点单审批结束要生成盘点出入库凭证并以实盘数修改库存
     * @param checkBillId
     * @param approveManId
     * @return
     */
    @Override
    public boolean createStoreCheckVoucher(Long checkBillId, Long approveManId) {
        //出入库凭证
        Map<String,Object>   checkInOutStoreParam=ivStoreCheckBillMapper.selectHouseStoreCheck(checkBillId);
        checkInOutStoreParam.put("check_bill_id",checkInOutStoreParam.get("id").toString());
        List<Map<String,Object>>  checkInOutStoreList=ivStoreCheckBillMapper.selectStoreCheckAllDetail(checkInOutStoreParam);
        for(Map<String,Object>  checkInOutParam:checkInOutStoreList){
            // 帐面数量
            Double  checkBillQty=Double.valueOf(checkInOutParam.get("check_bill_qty").toString().trim());
            // 帐面金额
            Double  checkBillPrice=Double.valueOf(checkInOutParam.get("check_bill_price").toString().trim());
            // 实盘金额
            Double  checkRealPrice=Double.valueOf(checkInOutParam.get("check_real_price").toString().trim());
            // 实盘数量
            Double  checkRealQty=Double.valueOf(checkInOutParam.get("check_real_qty").toString().trim());
            // 盘盈盘亏数量
            checkInOutParam.put("check_profit_loss_qty",checkRealQty-checkBillQty);
            // 盘盈盘亏金额
            checkInOutParam.put("check_profit_loss_price",checkRealPrice-checkBillPrice);

            Double  nowRealQty=checkRealQty-checkBillQty;
            if(nowRealQty>0) {
                // 盘盈入库数量增加
                checkInOutParam.put("store_room_id", checkInOutParam.get("store_room_id").toString());
                checkInOutParam.put("store_house_id", checkInOutStoreParam.get("store_house_id").toString());
                checkInOutParam.put("mat_id", checkInOutParam.get("mat_id").toString());
                checkInOutParam.put("quantity", Double.valueOf(checkInOutParam.get("check_profit_loss_qty").toString()));
                checkInOutParam.put("total_price", Double.valueOf(checkInOutParam.get("check_profit_loss_price").toString()));
                checkInOutParam.put("company_id", checkInOutStoreParam.get("company_id"));

                //InStore
                try {
                    inventoryGoodsJudgmentService.inventoryGoodsJudgment(checkInOutParam);
                } catch (IllegalAccessException e) {
                    logger.error("仓库变动异常        :" + e.getMessage());
                }

                // 新增凭证表和分类凭证表
                checkInOutParam.put("trans_type", "MC_IN");
                checkInOutParam.put("trans_direction", "1");
                checkInOutParam.put("unit",checkInOutStoreParam.get("unit"));
                checkInOutParam.put("company_id",checkInOutStoreParam.get("company_id"));
                checkInOutParam.put("company_name",checkInOutStoreParam.get("company_name"));
                voucherMapper.addVoucher(checkInOutParam);
                voucherCheckMapper.addVoucherCheck(checkInOutParam);
                checkInOutParam.get("id");
            }
            if(nowRealQty<0){
                // 盘盈出库数量减少
                checkInOutParam.put("store_room_id", checkInOutParam.get("store_room_id").toString());
                checkInOutParam.put("store_house_id", checkInOutStoreParam.get("store_house_id").toString());
                checkInOutParam.put("mat_id", checkInOutParam.get("mat_id").toString());
                checkInOutParam.put("quantity", Double.valueOf(checkInOutParam.get("check_profit_loss_qty").toString()));
                checkInOutParam.put("total_price",-Double.valueOf(checkInOutParam.get("check_profit_loss_price").toString()));
                checkInOutParam.put("company_id", checkInOutStoreParam.get("company_id"));

                // OutStore
                try {
                    inventoryGoodsJudgmentService.inventoryGoodsJudgment(checkInOutParam);
                } catch (IllegalAccessException e) {
                    logger.error("仓库变动异常        :" + e.getMessage());
                }

                // 新增凭证表和分类凭证表
                checkInOutParam.put("trans_type", "MC_OUT");
                checkInOutParam.put("trans_direction", "0");
                checkInOutParam.put("unit",checkInOutStoreParam.get("unit"));
                checkInOutParam.put("company_id", checkInOutStoreParam.get("company_id"));
                checkInOutParam.put("company_name", checkInOutStoreParam.get("company_name"));
                voucherMapper.addVoucher(checkInOutParam);
                voucherCheckMapper.addVoucherCheck(checkInOutParam);
                checkInOutParam.get("id");
            }
        }
        return true;
    }


    /**
     * 盘点单提交审批时，对于未盘点的物料设置为无须再盘状态，并置主表状态为 1（全部结清完毕）, 子表原为2（待结清）的现改为0（不需要结清）
     * @param checkBillId
     * @param uid
     * @return
     */
    @Override
    public boolean updateStoreCheckStatus(Long checkBillId,Long uid) {
        // 提交审批
        // 先查出盘点"未盘"的物料
        List<Map<String,Object>>  noStorecheckList=ivStoreCheckBillSubMapper.selectNoStoreCheckMat(checkBillId);
        for(Map<String,Object>   storecheckParam:noStorecheckList){
            // 子表原为2（待结清）的现改为0（不需要结清）
            storecheckParam.put("liquidate_flag","0");
            // 并置主表状态为 1（全部结清完毕）
            storecheckParam.put("liquidate_flags","1");
            ivStoreCheckBillSubMapper.updateStoreCheckBillSubDetail(storecheckParam);
        }
        return true;
    }

    /**
     * 当结清标识为全部结清
     * @param checkInfo
     */
    private    void     judgLiquidateFlag(Map<String,Object> checkInfo){
        Integer  liquidateFlag=Integer.valueOf(checkInfo.get("liquidate_flag").toString().trim());
        if(liquidateFlag==1&&liquidateFlag!=null){
            checkInfo.put("check_bill_id",checkInfo.get("id").toString().trim());
            checkInfo.put("mat_id",checkInfo.get("mat_id").toString().trim());
            List<Map<String, Object>> list =ivStoreCheckBillMapper.selectStoreCheckAllDetail(checkInfo);
            for(Map<String,Object>  storeCheckParam:list){
                // 帐面数量
                Double  checkBillQty=Double.valueOf(storeCheckParam.get("check_bill_qty").toString().trim());
                // 帐面金额
                Double  checkBillPrice=Double.valueOf(storeCheckParam.get("check_bill_price").toString().trim());
                // 实盘金额
                Double  checkRealPrice=Double.valueOf(storeCheckParam.get("check_real_price").toString().trim());
                // 实盘数量
                Double  checkRealQty=Double.valueOf(storeCheckParam.get("check_real_qty").toString().trim());
                // 差异率
                checkInfo.put("check_differ_ratio",checkRealQty-checkBillQty/checkRealQty);
                // 盘盈盘亏数量
                checkInfo.put("check_profit_loss_qty",checkRealQty-checkBillQty);
                // 盘盈盘亏金额
                checkInfo.put("check_profit_loss_price",checkRealPrice-checkBillPrice);
                ivStoreCheckBillSubMapper.updateStoreCheckMatIdDetail(checkInfo);
            }
        }
    }
}

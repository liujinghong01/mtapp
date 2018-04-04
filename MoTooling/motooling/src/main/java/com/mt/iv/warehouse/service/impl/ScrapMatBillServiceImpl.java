package com.mt.iv.warehouse.service.impl;


import com.mt.common.utils.DateUtil;
import com.mt.common.utils.StringUtils;
import com.mt.iv.common.dao.*;
import com.mt.iv.warehouse.service.ScrapMatBillService;
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
* @create    2018/1/24 15:00
* @Modified By:
*/    
@Service
public class ScrapMatBillServiceImpl implements ScrapMatBillService{
    private static Logger logger = Logger.getLogger(ScrapMatBillServiceImpl.class);
    @Autowired
    private VoucherMapper   voucherMapper;

    @Autowired
    private IvStoreMapper    ivStoreMapper;

    @Autowired
    private VoucherScrapMapper voucherscrapMapper;

    @Autowired
    private IvStoreRoomMapper ivStoreRoomMapper;

    @Autowired
    private IvStoreHouseMapper ivStoreHouseMapper;

    @Autowired
    private CreateBillNoService createBillNoService;

    @Autowired
    private IvScrapMatBillMapper  ivScrapMatBillMapper;

    @Autowired
    private IvScrapMatBillSubMapper  ivScrapMatBillSubMapper;

    /**
     * 查询报废单列表
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectScrapMatBillList(Map<String, Object> map) {
        if(map.containsKey("query")){
            Map<String,Object>  query=(Map<String,Object>)map.get("query");
            map.put("approve_step",query.get("approve_step")==null || query.get("approve_step")==""?null:query.get("approve_step"));
        }
        int  totalCount=ivScrapMatBillMapper.totalCount(map);
        map.put("page", ((Integer) map.get("curr_page") - 1) * (Integer) map.get("page_size"));
        List<Map<String, Object>> scrapMatList =ivScrapMatBillMapper.selectScrapMatBillList(map);
        if(scrapMatList.size()<=0){
            return StringUtils.pageList(map,"scrap_mat_list",0);
        }
        Map<String, Object> page = StringUtils.page(map, scrapMatList,"scrap_mat_list", totalCount);
        return page;
    }

    /**
     * 查询报废单详情
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectScrapMatBillDetail(Map<String, Object> map) {
        Map<String, Object> query = (Map<String, Object>) map.get("query");
        Integer scrapId = Integer.valueOf(query.get("scrap_id").toString());
        Map<String, Object> param = ivScrapMatBillMapper.selectScrapMatBillDetail(scrapId);
        List<Map<String, Object>> paramSub = ivScrapMatBillSubMapper.selectScrapMatBillSubDetail(Long.valueOf(param.get("scrap_id").toString().trim()));
        param.put("scrap_detail_list", paramSub);
        Map<String, Object> returnMap = new HashMap<String,Object>(16);
        returnMap.put("scrap_mat_info", param);
        return returnMap;
    }

    /**
     * 新增修改报废单
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> addAndUpdateScrapMatBill(Map<String, Object> map) {
        Map<String,Object>  scrapMatInfo=(Map<String,Object>)map.get("scrap_mat_info");
        scrapMatInfo.put("company_id",map.get("company_id"));
        List<Map<String,Object>>  scrapDetailList=(List<Map<String,Object>>)scrapMatInfo.get("scrap_detail_list");
        scrapMatInfo.remove("scrap_detail_list");

        // 判断主表新增OR修改
        Boolean  flg=false;
        Long  id=null;

        if(scrapMatInfo.containsKey("scrap_id")){
            flg=scrapMatInfo.get("scrap_id")==null || scrapMatInfo.get("scrap_id") =="" ? false :true;
        }
        if(flg){
            // 有ID进入修改
            scrapMatInfo.put("updated_at", DateUtil.getDateTime());
            ivScrapMatBillMapper.updateScrapMatBill(scrapMatInfo);
        }else {
            // 反之
            scrapMatInfo.put("created_at", DateUtil.getDateTime());
            scrapMatInfo.put("scrap_no",createBillNoService.createBillNo(Integer.valueOf(map.get("company_id").toString()), "SC", Long.valueOf(map.get("uid").toString())).get("bill_no").toString());
            ivScrapMatBillMapper.addScrapMatBill(scrapMatInfo);
            id = Long.valueOf(scrapMatInfo.get("id").toString().trim());
        }

        for(Map<String,Object>  objectMap:scrapDetailList){
            Boolean  partFlg=false;
            // 把报废主单scrap_id存入详情表
            objectMap.put("scrap_id",scrapMatInfo.get("scrap_id")==null || scrapMatInfo.get("scrap_id")=="" ? id:scrapMatInfo.get("scrap_id"));
            // 判断detail_id是否有值  TrueOrFalse
            if(objectMap.containsKey("detail_id")){
                partFlg=objectMap.get("detail_id")==null || objectMap.get("detail_id") =="" ?false:true;
            }
            if(partFlg){
                // 有ID进入修改
                objectMap.put("updated_at",DateUtil.getDateTime());
                ivScrapMatBillSubMapper.updateScrapMatBillSub(objectMap);
            }else{
                // 反之
                objectMap.put("created_at",DateUtil.getDateTime());
                ivScrapMatBillSubMapper.addScrapMatBillSub(objectMap);
                objectMap.get("id");
            }
        }

        Map<String, Object> returnMap = new HashMap<String,Object>(16);
        if (flg) {
            returnMap = ivScrapMatBillMapper.selectScrapMatBillById(Long.valueOf(scrapMatInfo.get("scrap_id").toString().trim()));
        } else {
            returnMap = ivScrapMatBillMapper.selectScrapMatBillById(id);
        }
        return returnMap;
    }


    /**
     * 执行报废
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> executeScrap(Map<String, Object> map) {
        Integer scrapId = Integer.valueOf(map.get("scrap_id").toString());
        Map<String, Object> param = ivScrapMatBillMapper.selectScrapMatBillDetail(scrapId);
        List<Map<String, Object>> paramSub = ivScrapMatBillSubMapper.selectScrapMatBillSubDetail(Long.valueOf(scrapId));

        Long  id=null;

        // 根据报废主表的scrapId查询出从表的详情物料记录新增到凭证表和分类凭证表
        // 凭证表作为仓库变动的记录
        for(Map<String,Object>  paramSubList:paramSub){
            paramSubList.put("company_id", map.get("company_id") == null || map.get("company_id") == "" ? id : map.get("company_id"));
            paramSubList.put("created_at",DateUtil.getDateTime());
            paramSubList.put("voucher_no",createBillNoService.createBillNo(Integer.valueOf(map.get("company_id").toString()), "PZ", Long.valueOf(map.get("uid").toString())).get("bill_no").toString());
            paramSubList.put("uid",map.get("uid"));
            paramSubList.put("trans_type","SC_OUT");
            paramSubList.put("trans_direction", "0");
            voucherMapper.addVoucher(paramSubList);
            voucherscrapMapper.addClassifyVoucher(paramSubList);
        }


        // 取出报废的数量，用仓库总数量-报废数量=现有的数量
        for (Map<String, Object> paramSubRoomList : paramSub) {
            // 取报废物料的数量,库位,仓库,物料Id
            Double quantity = Double.valueOf(paramSubRoomList.get("quantity").toString());
            paramSubRoomList.put("store_room_id",paramSubRoomList.get("store_room_id").toString());
            paramSubRoomList.put("store_house_id",paramSubRoomList.get("store_house_id").toString());
            paramSubRoomList.put("mat_id",Integer.valueOf(paramSubRoomList.get("mat_id").toString()));

            // 修改库位的数量和金额
            // 根据指定的库位和物料ID找到相对应的物料进行修改数量和金额
            Map<String, Object> roomMatDesc = ivStoreRoomMapper.selectStoreRoomMatDesc(paramSubRoomList);
            Double  stockQty=Double.valueOf(roomMatDesc.get("stock_qty").toString().trim());

            // 报废数量大于库存数量
            if(quantity>stockQty){
                throw new IllegalArgumentException ("库存不足,不能执行报废！");
            }else {
                Double roomStockQty = Double.valueOf(roomMatDesc.get("stock_qty").toString().trim());
                Double roomTotalPrice = Double.valueOf(roomMatDesc.get("total_price").toString().trim());
                roomMatDesc.put("stock_qty", roomStockQty - quantity);
                roomMatDesc.put("total_price", (roomStockQty - quantity) * roomTotalPrice / roomStockQty);
                Integer  i=ivStoreRoomMapper.updateStoreRoomMatDesc(roomMatDesc);
                if(i>1){
                    this.updateScrapMatBillManager(paramSubRoomList,map);
                    paramSubRoomList.put("stock_qty",roomMatDesc.get("stock_qty"));
                    ivScrapMatBillSubMapper.updateScrapMatBillSub(paramSubRoomList);
                }
            }
        }
        for (Map<String, Object> paramSubHouseList : paramSub) {
            // 取报废物料的数量,仓库,物料Id
            Double quantity = Double.valueOf(paramSubHouseList.get("quantity").toString());
            paramSubHouseList.put("store_house_id",paramSubHouseList.get("store_house_id").toString());
            paramSubHouseList.put("mat_id",Integer.valueOf(paramSubHouseList.get("mat_id").toString()));

            // 修改仓库的数量和金额
            // 根据指定的仓库和物料ID找到相对应的物料进行修改数量和金额
            Map<String, Object> houseMatDesc = ivStoreHouseMapper.selectStoreHouseMatDesc(paramSubHouseList);
            Double  stockQty=Double.valueOf(houseMatDesc.get("stock_qty").toString().trim());

            // 报废数量大于库存数量
            if(quantity>stockQty){
                throw new IllegalArgumentException ("库存不足,不能执行报废！");
            }else {
                Double houseStockQty = Double.valueOf(houseMatDesc.get("stock_qty").toString().trim());
                Double houseTotalPrice = Double.valueOf(houseMatDesc.get("total_price").toString().trim());
                houseMatDesc.put("stock_qty", houseStockQty - quantity);
                houseMatDesc.put("total_price", (houseStockQty - quantity) * houseTotalPrice / houseStockQty);
                Integer i=ivStoreHouseMapper.updateStoreHouseMatDesc(houseMatDesc);
                if(i>1){
                    this.updateScrapMatBillManager(paramSubHouseList,map);
                }
            }
        }

        for (Map<String, Object> paramSubStoreList : paramSub) {
            // 取报废物料的数量,物料Id
            Double quantity = Double.valueOf(paramSubStoreList.get("quantity").toString());
            paramSubStoreList.put("mat_id",Integer.valueOf(paramSubStoreList.get("mat_id").toString()));

            // 修改总仓库的数量和金额
            // 根据指定物料ID找到相对应的物料进行修改数量和金额
            Map<String, Object> storeMatDesc = ivStoreMapper.selectStoreMatDesc(paramSubStoreList);
            Double  stockQty=Double.valueOf(storeMatDesc.get("stock_qty").toString().trim());

            // 报废数量大于库存数量
            if(quantity>stockQty){
                throw new IllegalArgumentException ("库存不足,不能执行报废！");
            }else {
                Double storeStockQty = Double.valueOf(storeMatDesc.get("stock_qty").toString().trim());
                Double storeTotalPrice = Double.valueOf(storeMatDesc.get("total_price").toString().trim());
                storeMatDesc.put("stock_qty", storeStockQty - quantity);
                storeMatDesc.put("total_price", (storeStockQty - quantity) * storeTotalPrice / storeStockQty);
                Integer i=ivStoreMapper.updateStoreMatDesc(storeMatDesc);

                if(i>1){
                    this.updateScrapMatBillManager(paramSubStoreList,map);
                }
            }
        }
        Map<String,Object>  scrapInfo=ivScrapMatBillMapper.selectScrapMatBillById(Long.valueOf(param.get("scrap_id").toString().trim()));
        scrapInfo.put("voucher_no",createBillNoService.createBillNo(Integer.valueOf(map.get("company_id").toString()), "PZ", Long.valueOf(map.get("uid").toString())).get("bill_no").toString()            );
        Map<String,Object>  returnMap=new HashMap<String,Object>();
        returnMap.put("scrap_info",scrapInfo);
        return returnMap;
    }

    private  void  updateScrapMatBillManager(Map<String,Object> paramSubStoreList,Map<String,Object>  map){
        paramSubStoreList.put("is_scraped","1");
        paramSubStoreList.put("scrap_id",Integer.valueOf(map.get("scrap_id").toString()));
        ivScrapMatBillMapper.updateScrapMatBill(paramSubStoreList);
    }
}
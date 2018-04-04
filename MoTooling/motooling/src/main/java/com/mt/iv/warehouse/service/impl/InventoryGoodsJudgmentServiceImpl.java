package com.mt.iv.warehouse.service.impl;

import com.mt.common.utils.DateUtil;
import com.mt.iv.common.dao.*;
import com.mt.iv.warehouse.service.InventoryGoodsJudgmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class InventoryGoodsJudgmentServiceImpl implements InventoryGoodsJudgmentService {

    @Autowired
    private IvStoreRoomConfMapper ivStoreRoomConfMapper;

    @Autowired
    private IvStoreRoomMapper ivStoreRoomMapper;

    @Autowired
    private IvStoreHouseConfMapper ivStoreHouseConfMapper;

    @Autowired
    private IvStoreHouseMapper ivStoreHouseMapper;

    @Autowired
    private IvStoreMapper ivStoreMapper;

    @Autowired
    private MaterialStorageMapper materialStorageMapper;

    @Override
    public void inventoryGoodsJudgment(Map<String, Object> param) throws  IllegalArgumentException  {
        if (param.get("quantity") == null || param.get("quantity")==""){
            throw new IllegalArgumentException ("请填数量");
        }
        if (param.get("total_price") ==null || param.get("total_price")==""){
            throw new IllegalArgumentException ("请填金额");
        }
        if (param.get("store_house_id")==null || param.get("store_house_id")=="" ){
            throw new IllegalArgumentException ("请填写仓库ID");
        }
        if (param.get("store_room_id")==null || param.get("store_room_id")=="" ){
            throw new IllegalArgumentException ("请填写库位ID");
        }
        if (param.get("company_id")==null || param.get("company_id")=="" ){
            throw new IllegalArgumentException ("请填公司ID");
        }
         if (param.get("mat_id")==null || param.get("mat_id")=="" ){
            throw new IllegalArgumentException ("物料Id");
        }
        param.put("created_at", DateUtil.getDateTime());
        Map<String, Object> quantityPricHouseMap =  ivStoreHouseMapper.selectStoreHouseMatDesc(param);
        Map<String, Object> quantityPricRoomeMap = ivStoreRoomMapper.selectStoreRoomMatDesc(param);
        Map<String, Object> quantityPricStroeMap= ivStoreMapper.selectStoreMatDesc(param);
        if(ivStoreHouseConfMapper.selectDetail(param) ==null){
             //判断 如果查询时仓库为空  就新增
            ivStoreHouseConfMapper.addStoreHouse(param);
            param.put("store_house_id",param.get("id"));
        }
        if(quantityPricHouseMap==null){
            //为空 新增库存地点
            ivStoreHouseMapper.addStoreHouse(param);
        }
        if(ivStoreRoomConfMapper.selectRoomConf(param)==null){
            //如果库位地点 不存在 就添加
            ivStoreRoomConfMapper.addStoreRoom(param);
            param.put("store_room_id",param.get("id"));
        }
        if (quantityPricRoomeMap==null){
            //如果库位 不存在 就添加
            ivStoreRoomMapper.addStoreRoom(param);
        }
        if(quantityPricStroeMap== null){
            //如果总库存不存在 就添加
            ivStoreMapper.addStore(param);
        }

        //为ture 表示 出库 。 flase 入库
        boolean quantityIsNum = isNumeric(param.get("quantity").toString());
        if(quantityIsNum){
            //出库
            Double quantity =Double.valueOf(param.get("quantity").toString());
            Double totalPrice =Double.valueOf(param.get("total_price").toString());
            if(quantityPricHouseMap==null) {
                quantityPricHouseMap = ivStoreHouseMapper.selectStoreHouseMatDesc(param);
            }
            if(quantityPricRoomeMap==null) {
                quantityPricRoomeMap = ivStoreRoomMapper.selectStoreRoomMatDesc(param);
            }
            if(quantityPricStroeMap==null) {
                quantityPricStroeMap= ivStoreMapper.selectStoreMatDesc(param);
            }
            //stock_qty  total_price
            Double stockQtyHouse = Double.valueOf(quantityPricHouseMap.get("stock_qty").toString());
            Double stockQtyRoom = Double.valueOf(quantityPricRoomeMap.get("stock_qty").toString());
            Double stockQtyStroe = Double.valueOf(quantityPricStroeMap.get("stock_qty").toString());

            Double totalPriceHouse = Double.valueOf(quantityPricHouseMap.get("total_price").toString());
            Double totalPriceRoom = Double.valueOf(quantityPricRoomeMap.get("total_price").toString());
            Double totalPriceStroe = Double.valueOf(quantityPricStroeMap.get("total_price").toString());

            if(quantity+stockQtyHouse<0 || quantity+stockQtyRoom<0 || quantity+stockQtyStroe<0  ){
                throw new IllegalArgumentException ("库存不足");
            }else if(totalPrice+totalPriceHouse<0 || totalPrice+totalPriceRoom<0 || totalPrice+totalPriceStroe<0  ) {
                throw new IllegalArgumentException ("金额不足");
            }else {
                materialStorageMapper.updateQuantityTotalProce(param);
            }
        }else {
            //入库
               materialStorageMapper.updateQuantityTotalProce(param);
        }
    }




    /**
     * 匹配是负数  不为数字 抛出异常
     * @return
     * @author yutao
     */
    public static boolean isNumeric(String str) {
        // 正则表达式匹配负数
        Pattern pattern = Pattern.compile("-[0-9]+\\.?[0-9]*");
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            throw  new IllegalArgumentException("请传数字");//异常 说明包含非数字。
        }
        Matcher isNum = pattern.matcher(bigStr); // matcher是全匹配
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

}

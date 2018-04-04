package com.mt.iv.warehouse.service.impl;

import com.mt.common.utils.StringUtils;
import com.mt.iv.common.dao.IvStoreHouseMapper;
import com.mt.iv.common.dao.IvStoreMapper;
import com.mt.iv.common.dao.IvStoreRoomMapper;
import com.mt.iv.common.dao.VoucherMapper;
import com.mt.iv.warehouse.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:Wujie
 * @Date: Create in 2018-01-12 11:28
 * @Description:
 * @Modified By:
 */
@Service
@SuppressWarnings("unchecked")
public class VoucherServiceImpl implements VoucherService{

    @Autowired
    private VoucherMapper voucherMapper;

    @Autowired
    private IvStoreMapper ivStoreMapper;

    @Autowired
    private IvStoreHouseMapper ivStoreHouseMapper;

    @Autowired
    private IvStoreRoomMapper ivStoreRoomMapper;

    @Override
    public Map<String, Object> selectVoucherList(Map<String, Object> map) {
        map.put("page", ((Integer) map.get("curr_page") - 1) * (Integer) map.get("page_size"));
        Map<String,Object> queryMap = (Map<String,Object>) map.get("query");
        Integer voucherType = Integer.valueOf(queryMap.get("voucher_type").toString());
        List<Map<String,Object>> voucherList;
        Map<String,Object> page;
        Map<String,Object> matSumMap;
        Double matSum;
        Integer totalCount;
        switch (voucherType){
            case 1:
                voucherList = voucherMapper.selectInVoucherList(map);
                matSumMap = voucherMapper.selectInVoucherMatSum(map);
                totalCount = voucherMapper.selectInVoucherCount(map);
                break;
            case 2:
                voucherList = voucherMapper.selectOutVoucherList(map);
                matSumMap = voucherMapper.selectOutVoucherMatSum(map);
                totalCount = voucherMapper.selectOutVoucherCount(map);
                break;
            case 3:
                voucherList = voucherMapper.selectVoucherCheckList(map);
                matSumMap = voucherMapper.selectVoucherCheckMatSum(map);
                totalCount = voucherMapper.selectVoucherCheckCount(map);
                break;
            case 4:
                voucherList = voucherMapper.selectVoucherSwitchList(map);
                matSumMap = voucherMapper.selectVoucherSwitchMatSum(map);
                totalCount = voucherMapper.selectVoucherSwitchCount(map);
                break;
            default:
                throw new IllegalArgumentException ("请选择正确的凭证类型");
        }
        matSum = Double.valueOf(matSumMap.get("mat_sum").toString());
        for (Map<String,Object> voucherMap : voucherList){
            voucherMap.put("mat_sum",matSum);
        }
        if(voucherList.size() <= 0){
            return StringUtils.pageList(map, "voucher_list", 0);
        }
        page = StringUtils.page(map,voucherList,"voucher_list",totalCount);
        return page;
    }

    @Override
    public Map<String, Object> selectVoucherDetail(Map<String, Object> map) {
        Map<String,Object> query = (Map<String,Object>) map.get("query");
        Map<String,Object> voucherInfo = voucherMapper.selectVoucherList(query);
        voucherInfo.put("company_id",map.get("company_id"));
        //根据凭证号获取物料数量
        String voucher = voucherInfo.get("voucher").toString();
        Map<String,Object> voucherMap = voucherMapper.selectMatSumByVoucher(voucher);
        Double matSum = Double.valueOf(voucherMap.get("mat_sum").toString());
        voucherInfo.put("mat_sum",matSum);
        //根据操作人id获取操作人名字
        Map<String,Object> handlerNameMap = voucherMapper.selectWorkNameById(voucherInfo);
        if(handlerNameMap == null){
            voucherInfo.put("handler_name","");
        }else {
            String handlerName = handlerNameMap.get("work_name").toString();
            voucherInfo.put("handler_name",handlerName);
        }
        List<Map<String,Object>> detailList = voucherMapper.selectVoucherDetail(query);
        for (Map<String,Object> detailMap : detailList){
            //根据物料id获取库存数量
            Long matId = Long.valueOf(detailMap.get("mat_id").toString());
            Map<String,Object> stockQtyMap = ivStoreMapper.selectStockQtyByMatId(matId);
            if(stockQtyMap.size() <= 0 ){
                detailMap.put("stock_qty","");
            }else {
                Double stockQty = Double.valueOf(stockQtyMap.get("stock_qty").toString());
                detailMap.put("stock_qty",stockQty);
            }
            //根据凭证号查询物料类型
            Map<String,Object> transTypeMap = voucherMapper.selectTransTypeByVoucher(detailMap);
            String transType = transTypeMap.get("trans_type").toString();
            //判断移动类型是否为采购入库
            String isStock = null;
            String isCheck = null;
            if(transType.equals("MP_IN")){
                Map<String,Object> voucherPurchMap = voucherMapper.selectVoucherPurch(detailMap);
                isStock = voucherPurchMap.get("is_stock_in_finished").toString();
                isCheck = voucherPurchMap.get("is_check").toString();
            }
            detailMap.put("is_stock_in_finished",isStock);
            detailMap.put("is_check",isCheck);
            //根据物料id获取库存金额
            Map<String,Object> totalPriceMap = ivStoreMapper.selectTotalPriceByMatId(matId);
            if(totalPriceMap == null){
                detailMap.put("total_price","");
            }else {
                Double totalPrice = Double.valueOf(totalPriceMap.get("total_price").toString());
                detailMap.put("total_price",totalPrice);
            }
            //根据库存地点id获取所在仓库名字
            Long storeHouseId = Long.valueOf(detailMap.get("store_house_id").toString());
            List<Map<String,Object>> storeHouseNameList = ivStoreHouseMapper.selectStoreHouseNameById(storeHouseId);
            List<String> storeNameList = new ArrayList<>();
            for (Map<String,Object> storeHouseNameMap : storeHouseNameList){
                storeNameList.add(storeHouseNameMap.get("store_house_name").toString());
            }
            detailMap.put("store_house_name",storeNameList);
            //根据库位id获取所在库位名字
            Long storeRoomId = Long.valueOf(detailMap.get("store_room_id").toString());
            List<Map<String,Object>> storeRoomNameList = ivStoreRoomMapper.selectStoreRoomNameById(storeRoomId);
            List<String> storeRoomList = new ArrayList<>();
            for (Map<String,Object> storeRoomNameMap : storeRoomNameList){
                storeRoomList.add(storeRoomNameMap.get("store_room_name").toString());
            }
            detailMap.put("store_room_name",storeRoomList);
        }
        voucherInfo.put("detailList",detailList);
        Map<String,Object> returnMap = new HashMap<>(16);
        returnMap.put("voucher_info",voucherInfo);
        return returnMap;
    }

    @Override
    public Map<String, Object> selectVoucherOneDetail(Map<String, Object> map) {
        Map<String,Object> query = (Map<String,Object>) map.get("query");
        Map<String,Object> voucherInfo = voucherMapper.selectVoucherListByDetailId(query);
        //根据凭证号获取物料数量
        String voucher = voucherInfo.get("voucher").toString();
        Map<String,Object> voucherMap = voucherMapper.selectMatSumByVoucher(voucher);
        Double matSum = Double.valueOf(voucherMap.get("mat_sum").toString());
        voucherInfo.put("mat_sum",matSum);

        Map<String,Object> detailInfo = voucherMapper.selectVoucherDetailByDetailId(query);
        //根据凭证号查询物料类型
        Map<String,Object> transTypeMap = voucherMapper.selectTransTypeByVoucher(query);
        String transType = transTypeMap.get("trans_type").toString();
        //判断移动类型是否为采购入库
        String isStock = null;
        String isCheck = null;
        if(transType.equals("MP_IN")){
            Map<String,Object> voucherPurchMap = voucherMapper.selectVoucherPurch(query);
            isStock = voucherPurchMap.get("is_stock_in_finished").toString();
            isCheck = voucherPurchMap.get("is_check").toString();
        }
        detailInfo.put("is_stock_in_finished",isStock);
        detailInfo.put("is_check",isCheck);

        //根据物料id获取库存数量
        Long matId = Long.valueOf(detailInfo.get("mat_id").toString());
        Map<String,Object> stockQtyMap = ivStoreMapper.selectStockQtyByMatId(matId);
        if(stockQtyMap == null){
            detailInfo.put("stock_qty","");
        }else {
            Double stockQty = Double.valueOf(stockQtyMap.get("stock_qty").toString());
            detailInfo.put("stock_qty",stockQty);
        }
        //根据物料id获取库存金额
        Map<String,Object> totalPriceMap = ivStoreMapper.selectTotalPriceByMatId(matId);
        if(totalPriceMap == null){
            detailInfo.put("total_price","");
        }else {
            Double totalPrice = Double.valueOf(totalPriceMap.get("total_price").toString());
            detailInfo.put("total_price",totalPrice);
        }
        //根据库存地点id获取所在仓库名字
        Long storeHouseId = Long.valueOf(detailInfo.get("store_house_id").toString());
        List<Map<String,Object>> storeHouseNameList = ivStoreHouseMapper.selectStoreHouseNameById(storeHouseId);
        List<String> storeNameList = new ArrayList<>();
        for (Map<String,Object> storeHouseNameMap : storeHouseNameList){
            storeNameList.add(storeHouseNameMap.get("store_house_name").toString());
        }
        detailInfo.put("store_house_name",storeNameList);
        //根据库位id获取所在库位名字
        Long storeRoomId = Long.valueOf(detailInfo.get("store_room_id").toString());
        List<Map<String,Object>> storeRoomNameList = ivStoreRoomMapper.selectStoreRoomNameById(storeRoomId);
        List<String> storeRoomList = new ArrayList<>();
        for (Map<String,Object> storeRoomNameMap : storeRoomNameList){
            storeRoomList.add(storeRoomNameMap.get("store_room_name").toString());
        }
        detailInfo.put("store_room_name",storeRoomList);
        voucherInfo.put("detail_info",detailInfo);
        Map<String,Object> returnMap = new HashMap<>(16);
        returnMap.put("voucher_info",voucherInfo);
        return returnMap;
    }
}

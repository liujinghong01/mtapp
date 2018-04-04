package com.mt.iv.warehouse.service;

import java.util.Map;

public interface InventoryGoodsJudgmentService {
    /**
     * 判断出入库
     * 数量:    quantity
     * 金额:    total_price
     * 重量:    weight
     * 仓库：   store_house_id
     * 库位：   store_room_id
     * 公司ID： company_id
     * 物料ID： mat_id
     * 通用接口
     * @return
     */
     void inventoryGoodsJudgment(Map<String,Object> param) throws IllegalAccessException;
}

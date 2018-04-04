package com.mt.iv.warehouse.service;

import java.util.Map;

/**
* @Author: Wendy
* @Description:
* @create    2018/1/23 15:30
* @Modified By:
*/    
public interface StoreCheckBillService {
      // 盘点单生成
      Map<String,Object>     addStoreCheckBill(Map<String,Object>  map);

      // 盘点单盘点
      Map<String,Object>     updateStoreCheckBill(Map<String,Object>  map);

      // 查询盘点单列表
      Map<String,Object>     selectStoreCheckBillList(Map<String, Object> map);

      // 查询盘点单待盘和已盘的物料
      Map<String,Object>     selectStoreCheckBillDetail(Map<String,Object>  map);

      // 盘点单审批结束要生成盘点出入库凭证并以实盘数修改库存
      boolean createStoreCheckVoucher(Long checkBillId, Long approveManId);

      // 盘点单提交审批时，对于未盘点的物料设置为无须再盘状态
      boolean updateStoreCheckStatus(Long checkBillId,Long  uid);
}
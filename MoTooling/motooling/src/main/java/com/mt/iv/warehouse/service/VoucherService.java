package com.mt.iv.warehouse.service;

import java.util.Map;

public interface VoucherService {

    //查询凭证管理列表
    Map<String,Object> selectVoucherList(Map<String,Object> map);

    //查询凭证管理详情
    Map<String,Object> selectVoucherDetail(Map<String,Object> map);

    //查询凭证单中某物料信息
    Map<String,Object> selectVoucherOneDetail(Map<String,Object> map);
}

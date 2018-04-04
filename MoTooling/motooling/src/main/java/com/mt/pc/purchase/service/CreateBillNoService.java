package com.mt.pc.purchase.service;

import java.util.Map;

public interface CreateBillNoService {

    Map<String, Object> createBillNo(Integer companyId, String billTypeCode);
    Map<String, Object> createBillNo(Integer companyId, String billTypeCode, Long uid);


}
package com.mt.order.bl.service;

public interface GetUnitNameService {
    String  getUnitName(String code);

    void saveRedis();
}

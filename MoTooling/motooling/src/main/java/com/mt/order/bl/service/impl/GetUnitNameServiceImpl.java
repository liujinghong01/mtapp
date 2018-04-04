package com.mt.order.bl.service.impl;

import com.mt.cms.common.dao.GetUnitNameMapper;
import com.mt.common.utils.RedisUtil;
import com.mt.order.bl.service.GetUnitNameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GetUnitNameServiceImpl implements GetUnitNameService {
    private static final Logger log = LoggerFactory.getLogger(GetUnitNameServiceImpl.class);

    @Autowired
    private GetUnitNameMapper getUnitNameMapper;

    @Override
    public String getUnitName(String code) {
        String string = RedisUtil.getMapString("UnitName",code);
        if (string != null) {
            return string;
        } else {
            Map<String, String> unitName = getUnitNameMapper.getUnitName(code);
            if (unitName != null) {
                RedisUtil.setMapString("UnitName", unitName.get("unit_code"),unitName.get("unit_name"));
                log.info("{}  {}",unitName.get("unit_name"),unitName.get("unit_code"));
                return unitName.get("unit_name");
            }
        }
        return null;
    }

    @Override
    public void saveRedis() {
        RedisUtil.delString("UnitName");
        List<Map<String, String>> list = getUnitNameMapper.selectCodeAndName();
        for (Map<String, String> map : list) {
                RedisUtil.setMapString("UnitName", map.get("unit_code"), map.get("unit_name"));
        }

    }
}

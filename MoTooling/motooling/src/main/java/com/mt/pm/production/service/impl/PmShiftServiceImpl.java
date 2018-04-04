package com.mt.pm.production.service.impl;

import com.mt.common.utils.StringUtils;
import com.mt.pc.purchase.service.CreateBillNoService;
import com.mt.pm.common.dao.PmShiftMapper;
import com.mt.pm.common.dao.PmShiftSubMapper;
import com.mt.pm.common.model.PmShift;
import com.mt.pm.common.model.PmShiftSub;
import com.mt.pm.common.pojo.PmShiftPojo;
import com.mt.pm.production.service.PmShiftService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
public class PmShiftServiceImpl implements PmShiftService{

    @Autowired
    private PmShiftMapper pmShiftMapper;

    @Autowired
    private PmShiftSubMapper pmShiftSubMapper;

    @Autowired
    private CreateBillNoService createBillNoService;

    @Override
    public Map<String, Object> selectPmShiftList(PmShiftPojo pmShiftPojo) {
        pmShiftPojo.setPage((pmShiftPojo.getCurrPage() - 1) * pmShiftPojo.getPageSize());
        List<Map<String,Object>> shiftList = pmShiftMapper.selectPmShiftList(pmShiftPojo);
        Map<String,Object> map = StringUtils.camelToUnderline(JSONObject.fromObject(pmShiftPojo));
        Map<String,Object> page;
        if (shiftList.size() <= 0) {
            return StringUtils.pageList(map, "shift_list", 0);
        }
        Integer totalCount = pmShiftMapper.totalCountList(pmShiftPojo);
        page = StringUtils.page(map, shiftList, "shift_list", totalCount);
        return page;
    }

    @Override
    public Map<String,Object> selectPmShiftDetail(PmShiftPojo pmShiftPojo){
        Map<String,Object> shiftInfo = pmShiftMapper.selectPmShiftDetail(pmShiftPojo);
        List<Map<String,Object>> detailList = pmShiftSubMapper.selectPmShiftSubDetail(pmShiftPojo);
        shiftInfo.put("detail_list",detailList);
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("shift_info",shiftInfo);
        return returnMap;
    }

    @Override
    public Map<String,Object> addAndUpdatePmShift(PmShiftPojo pmShiftPojo){
        PmShift pmShift = pmShiftPojo.getShiftInfo();
        List<PmShiftSub> detailList = pmShiftPojo.getShiftInfo().getDetailList();
        Long id = null;
        Boolean partFlg = pmShiftPojo.getShiftInfo().getShiftId() == null || pmShiftPojo.getShiftInfo
                ().getShiftId().equals("") ? false : true ;
        if(partFlg){
            //修改班次
            pmShift.setUpdatedAt(new Date());
            pmShiftMapper.updateByPrimaryKeySelective(pmShift);
            //修改班次成功之后删除班次子表数据
            pmShiftPojo.setShiftId(pmShift.getShiftId());
            pmShiftPojo.setUpdateAt(new Date());
            pmShiftSubMapper.deletePmShiftSub(pmShiftPojo);
        }else {
            //新增班次
            pmShift.setCompanyId(pmShiftPojo.getCompanyId());
            pmShiftMapper.addPmShift(pmShift);
            id = pmShift.getShiftId();
        }

        for (PmShiftSub pmShiftSub : detailList){
            pmShiftSub.setShiftId(pmShiftPojo.getShiftInfo().getShiftId() == null || pmShiftPojo.getShiftInfo().getShiftId().equals("") ? id : pmShiftPojo.getShiftInfo().getShiftId());
            pmShiftSubMapper.insertSelective(pmShiftSub);
        }
        Map<String,Object> shiftMap;
        if (partFlg){
            shiftMap = pmShiftMapper.selectPmShiftById(pmShiftPojo.getShiftInfo().getShiftId());
        }else {
            shiftMap = pmShiftMapper.selectPmShiftById(id);
        }
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("shift_info",shiftMap);

        return returnMap;
    }

    @Override
    public Boolean deletePmShift(PmShiftPojo pmShiftPojo) {
        pmShiftPojo.setUpdateAt(new Date());
        Boolean bool = pmShiftMapper.deletePmShift(pmShiftPojo);
        if(bool){
            pmShiftPojo.setUpdateAt(new Date());
            bool = pmShiftSubMapper.deletePmShiftSub(pmShiftPojo);
            return bool;
        }
        return bool;
    }

}

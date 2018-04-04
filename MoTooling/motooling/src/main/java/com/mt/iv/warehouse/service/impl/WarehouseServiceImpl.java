package com.mt.iv.warehouse.service.impl;

import com.mt.bm.common.dao.MatMapper;
import com.mt.bm.common.dao.MatTypeMapper;
import com.mt.bm.common.model.Mat;
import com.mt.bm.common.model.MatType;
import com.mt.cms.common.dao.CompanyMapper;
import com.mt.common.utils.DateUtil;
import com.mt.common.utils.StringUtils;
import com.mt.iv.common.dao.IvStoreHouseConfMapper;
import com.mt.iv.common.dao.IvStoreRoomConfMapper;
import com.mt.iv.common.dao.IvStoreRoomMapper;
import com.mt.iv.warehouse.service.WarehouseService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class WarehouseServiceImpl implements WarehouseService {
    @Autowired
    private IvStoreHouseConfMapper ivStoreHouseConfMapper;

    @Autowired
    private IvStoreRoomConfMapper ivStoreRoomConfMapper;

    @Autowired
    private MatTypeMapper matTypeMapper;

    @Autowired
    private MatMapper matMapper;

    @Autowired
    private IvStoreRoomMapper ivStoreRoomMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public Map<String, Object> addStoreHouse(Map<String, Object> map) throws IllegalArgumentException {
        Map<String, Object> storeHouseInfo = (Map<String, Object>) map.get("storeHouseInfo");
        List<Map<String, Object>> storeRoomList = (List<Map<String, Object>>) storeHouseInfo.get("store_room_list");
        map.remove("store_room_list");
        storeHouseInfo.put("uid", map.get("uid"));
        storeHouseInfo.put("company_id", map.get("company_id"));
        Boolean flg = false;
        Boolean partFlg = false;
        Long id = null;
        if (storeHouseInfo.containsKey("store_house_id")) {
            flg = storeHouseInfo.get("store_house_id") == null || storeHouseInfo.get("store_house_id") == "" ? false : true;
        }
        if (0 < ivStoreHouseConfMapper.selectStoreHouseName(map.get("company_id"), storeHouseInfo.get("store_house_name"), storeHouseInfo.get("store_house_id"))) {
            throw new IllegalArgumentException("仓库名重复");
        }
        if (flg) {
            //修改
            storeHouseInfo.put("updated_at", DateUtil.getDateTime());
            ivStoreHouseConfMapper.updateStoreHouse(storeHouseInfo);
        } else {
            //新增
            storeHouseInfo.put("created_at", DateUtil.getDateTime());
            ivStoreHouseConfMapper.addStoreHouse(storeHouseInfo);
            id = Long.valueOf(storeHouseInfo.get("id").toString());
        }
        Map<String, Object> returnMap = new HashMap<>();
        if (flg) {
            returnMap.put("store_house_id", storeHouseInfo.get("store_house_id"));
        } else {
            returnMap.put("store_house_id", id);
        }
        if (0 == storeRoomList.size()) {
            Map<String, Object> storeRoomMap = new HashMap<>(6);
            storeRoomMap.put("store_house_id", returnMap.get("store_house_id"));
            storeRoomMap.put("store_house_name", storeHouseInfo.get("store_house_name"));
            storeRoomMap.put("is_default_room", "1");
            storeRoomMap.put("store_room_name", storeHouseInfo.get("store_house_name"));
            storeRoomMap.put("created_at", DateUtil.getDateTime());
            storeRoomMap.put("last_handler_id", map.get("uid"));
            ivStoreRoomConfMapper.addStoreRoom(storeRoomMap);
        } else {
            for (Map<String, Object> obMap : storeRoomList) {
                partFlg = false;
                if (obMap.containsKey("store_room_id")) {
                    partFlg = obMap.get("store_room_id") == null || obMap.get("store_room_id") == "" ? false : true;
                }
                if (obMap.get("store_room_name") == null || obMap.get("store_room_name") == "") {
                    throw new IllegalArgumentException("库位名不能为空");
                }
                if (flg) {
                    obMap.put("store_house_id", storeHouseInfo.get("store_house_id"));
                } else {
                    obMap.put("store_house_id", id);
                }
                obMap.put("updated_at", DateUtil.getDateTime());
                if (partFlg) {
                    if (0 < ivStoreRoomConfMapper.selectStoreRoomName(returnMap.get("store_house_id"), obMap.get("store_room_name"), obMap.get("store_room_id"))) {
                        throw new IllegalArgumentException("库位名重复");
                    }
                    storeHouseInfo.put("updated_at", DateUtil.getDateTime());
                    ivStoreRoomConfMapper.updateStoreRoom(obMap);
                } else {
                    if (0 < ivStoreRoomConfMapper.selectStoreRoomName(returnMap.get("store_house_id"), obMap.get("store_room_name"), null)) {
                        throw new IllegalArgumentException("库位名重复");
                    }
                    obMap.put("last_handler_id", storeHouseInfo.get("uid"));
                    obMap.put("created_at", DateUtil.getDateTime());
                    obMap.put("store_house_name", storeHouseInfo.get("storeHouseInfo"));
                    ivStoreRoomConfMapper.addStoreRoom(obMap);
                }
            }
        }
        return returnMap;
    }

    /**
     * 删除仓库。
     * 传 status = 0
     *
     * @param map
     * @return
     */
    @Override
    public Integer updateStoreHouseModify(Map<String, Object> map) {
        return ivStoreHouseConfMapper.updateStoreHouseStatus(Long.valueOf(map.get("store_house_id").toString()));
    }


    /**
     * 物料类型新增修改
     * 数据库表：bm_mat_type
     *
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> addIvMatTypeModify(Map<String, Object> map) {
        Map<String, Object> matTypeInfo = (Map<String, Object>) map.get("mat_type_info");
        matTypeInfo.put("company_id", map.get("company_id"));
        matTypeInfo.put("uid", map.get("uid"));
        Boolean flg = false;
        Long id = null;
        if (matTypeInfo.containsKey("mat_type_id")) {
            flg = matTypeInfo.get("mat_type_id") == null || matTypeInfo.get("mat_type_id") == "" ? false : true;
        }
        if (flg) {
            //修改
            matTypeInfo.put("handled_time", DateUtil.getDateTime());
            matTypeMapper.updateIvMatTypeModify(matTypeInfo);
        } else {
            //新增
            matTypeInfo.put("company_name", companyMapper.selectCompanyNme(Integer.valueOf(map.get("company_id").toString())));
            matTypeInfo.put("handled_time", DateUtil.getDateTime());
            matTypeMapper.addIvMatTypeModify(matTypeInfo);
            id = Long.valueOf(matTypeInfo.get("id").toString());
        }

        Map<String, Object> returnMap = new HashMap<>();
        if (flg) {
            returnMap.put("mat_type_id", matTypeInfo.get("mat_type_id"));
            return returnMap;
        } else {
            returnMap.put("mat_type_id", id);
            return returnMap;
        }
    }

    /**
     * 物料类型删除
     *
     * @param map
     * @return
     */
    @Override
    public Boolean deleteMatType(Map<String, Object> map) {
        return matTypeMapper.delectMatType(Long.valueOf(map.get("mat_type_id").toString()));
    }


    /**
     * 库位删除
     *
     * @param map
     * @return
     */
    @Override
    public Integer updateStoreRoom(Map<String, Object> map) {
        return ivStoreRoomMapper.updateStoreRoom(map);
    }

    @Override
    public Map<String, Object> selectBmMatAllMatName(Map<String, Object> map) throws Exception {
        Integer totalCount = matMapper.selectBmMatAllMatNameTotalCount(map);
        map.put("page", ((Integer) map.get("curr_page") - 1) * (Integer) map.get("page_size"));
        List<Mat> mat = matMapper.selectBmMatAllMatName(map);
        List<Map<String, Object>> list = StringUtils.convertListBean2ListMap(mat, Mat.class);
        if (list.size() <= 0) {
            return StringUtils.pageList(map, "list", 0);
        }
        return StringUtils.page(map, list, "list", totalCount);
    }

    /**
     * 查询物料类型详情
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectBMatTypeModify(Map<String, Object> map) {
        MatType matType = matTypeMapper.selectBMatTypeModify(map);
        return  StringUtils.camelToUnderline(JSONObject.fromObject(matType));
    }

}

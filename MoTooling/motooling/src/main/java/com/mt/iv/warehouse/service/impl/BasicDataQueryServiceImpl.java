package com.mt.iv.warehouse.service.impl;

import com.mt.common.utils.StringUtils;
import com.mt.iv.common.dao.BasicDataQueryMapper;
import com.mt.iv.common.dao.IvStoreHouseConfMapper;
import com.mt.iv.common.dao.IvStoreRoomConfMapper;
import com.mt.iv.warehouse.service.BasicDataQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BasicDataQueryServiceImpl implements BasicDataQueryService {

    @Autowired
    private IvStoreHouseConfMapper ivStoreHouseConfMapper;

    @Autowired
    private IvStoreRoomConfMapper ivStoreRoomConfMapper;

    @Autowired
    private BasicDataQueryMapper basicDataQueryMapper;

    /**
     * 查询仓库列表
     *
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectIvStorehouseList(Map<String, Object> map) {
        map.put("page", ((Integer) map.get("curr_page") - 1) * (Integer) map.get("page_size"));
        List<Map<String, Object>> pucchOrderList = ivStoreHouseConfMapper.selectIvStorehouseList(map);
        Map<String, Object> page = new HashMap<>();
        if ("1".equals(map.get("is_paging"))) {
            if (pucchOrderList.size() <= 0) {
                return StringUtils.pageList(map, "storehouse_list", 0);
            }
            Integer totalCount = ivStoreHouseConfMapper.totalCount(map);
            page = StringUtils.page(map, pucchOrderList, "storehouse_list", totalCount);
            return page;
        }else {
            page.put("storehouse_list", pucchOrderList);
            return page;
        }

    }

    /**
     * 查询仓库详细信息(含库位)
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectIvStorehouseDetail(Map<String, Object> map) {
        Map<String, Object> store_house_info = ivStoreHouseConfMapper.selectDetail(map);
        List<Map<String, Object>> roomList =  ivStoreRoomConfMapper.selectRoomDetail(map);
        store_house_info.put("store_room_list",roomList);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("store_house_info",store_house_info);
        return returnMap;
    }


    /**
     * 查询入库类型列表
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectInStoreTypeList(Map<String, Object> map) {
        List<Map<String, Object>> list =  basicDataQueryMapper.selectInStoreTypeList();
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("list",list);
        return returnMap;
    }

    /**
     * 查询出库类型列表
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectOutStoreTypeList(Map<String, Object> map) {
        List<Map<String, Object>> list =  basicDataQueryMapper.selectOutStoreTypeList();
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("list",list);
        return returnMap;
    }


    /**
     *获取库存物料管理类型
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectIvStoreDirectChange(Map<String, Object> map) {
        List<Map<String, Object>> list =  basicDataQueryMapper.selectIvStoreDirectChange();
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("list",list);
        return returnMap;
    }


    /**
     * 查询某部门及其子部门全部成员列表
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectCompanyGetAllDeptMenber(Map<String, Object> map) {
        Map<String, Object>  query = (Map<String, Object>) map.get("query");
        map.remove("query");
        map.put("dep_code",query.get("dep_code"));
        map.put("dep_id",query.get("dep_id"));
        List<Map<String,Object>> depList =null;
        List<Long> depIds = new ArrayList<>();
        Long depId = null;
        if(query.get("dep_code")!=null && query.get("dep_code")!="" ){
            depList =  basicDataQueryMapper.selectDepId(map);
            if(depList.size()==1){
                depId=Long.valueOf(depList.get(0).get("dep_id").toString());
                depList =  basicDataQueryMapper.selectDepId(depList.get(0));
            }
        }else {
            depId = Long.valueOf(query.get("dep_id").toString());
            depList =  basicDataQueryMapper.selectDepId(map);
        }

        if(depList!=null){
            for (Map<String,Object> objectMap:depList) {
                if(objectMap.get("all_upper_ids")!=null && objectMap.get("all_upper_ids")!="" ){
                    String allUpperIds = objectMap.get("all_upper_ids").toString();
                    String[] split = allUpperIds.split("|");
                    for (String sp: split) {
                        if(sp.equals(depId)){
                            depIds.add(Long.valueOf(sp));
                            break;
                        }
                    }
                }

            }
        }
        depIds.add(depId);
        List<Map<String,Object>> list = basicDataQueryMapper.seslectDepIdUser(depIds);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("member_list",list);
        return returnMap;
    }


    /**
     * 查询物料类型列表
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectIvMatTypeList(Map<String, Object> map) {
        map.put("page", ((Integer) map.get("curr_page") - 1) * (Integer) map.get("page_size"));
        List<Map<String, Object>> ivMatTypeList =  basicDataQueryMapper.selectIvMatTypeList(map);
        Map<String, Object> page = new HashMap<>();
        if ("1".equals(map.get("is_paging").toString())) {
            if (ivMatTypeList.size() <= 0) {
                return StringUtils.pageList(map, "mat_type_list", 0);
            }
            Integer totalCount = basicDataQueryMapper.IvMatTypeListTotalCount(map);
            page = StringUtils.page(map, ivMatTypeList, "mat_type_list", totalCount);
            return page;
        }else {
            page.put("mat_type_list", ivMatTypeList);
            return page;
        }
    }




}

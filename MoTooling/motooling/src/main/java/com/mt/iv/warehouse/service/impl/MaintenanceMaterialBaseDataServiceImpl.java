package com.mt.iv.warehouse.service.impl;

import com.mt.cms.common.dao.CompanyMapper;
import com.mt.common.core.shiro.token.manager.TokenManager;
import com.mt.common.utils.DateUtil;
import com.mt.common.utils.StringUtils;
import com.mt.iv.common.dao.BmMatMapper;
import com.mt.iv.common.dao.IvStoreHouseMapper;
import com.mt.iv.common.dao.IvStoreMapper;
import com.mt.iv.common.dao.IvStoreRoomMapper;
import com.mt.iv.warehouse.service.MaintenanceMaterialBaseDataService;
import com.mt.upms.common.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MaintenanceMaterialBaseDataServiceImpl implements MaintenanceMaterialBaseDataService {

    @Autowired
    private BmMatMapper bmMatMapper;

    @Autowired
    private IvStoreHouseMapper ivStoreHouseMapper;

    @Autowired
    private IvStoreRoomMapper ivStoreRoomMapper;

    @Autowired
    private IvStoreMapper ivStoreMapper;

    @Autowired
    private CompanyMapper companyMapper;

    /**
     * 物料信息新增修改
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> addIvMatModify(Map<String, Object> map) {
        Map<String, Object> matInfo = (Map<String, Object>) map.get("mat_info");
        matInfo.put("company_id",map.get("company_id"));
        User user = TokenManager.getToken();
        if(map.get("company_id").equals(user.getCompanyId())){
            matInfo.put("company_name",user.getCompanyName());
        }else {
            matInfo.put("company_name",companyMapper.selectByCompanyId(Integer.valueOf(map.get("company_id").toString())).getCompanyName());
        }

        Boolean flg = false;
        Integer  id = null;
        if (matInfo.containsKey("mat_id")) {
            flg = matInfo.get("mat_id") == null || matInfo.get("mat_id") == "" ? false : true;
        }
        if(flg){
            //修改
            matInfo.put("handled_time", DateUtil.getDateTime());
            bmMatMapper.updateIvMatModify(matInfo);
        }else {
            //新增
            if(matInfo.get("mat_no")==null || matInfo.get("mat_no")==""){
                throw new IllegalArgumentException("请填写物料编码");
            }
            matInfo.put("handled_time", DateUtil.getDateTime());
            Long i = bmMatMapper.addIvMatModify(matInfo);
            id = Integer.valueOf(matInfo.get("id").toString());
        }

        Map<String, Object> returnMap = new HashMap<>();
        if(flg){
            returnMap.put("mat_id",map.get("mat_id"));
            returnMap.put("mat_no",matInfo.get("mat_no"));
            return returnMap;
        }else {
            returnMap.put("mat_id",id);
            returnMap.put("mat_no",matInfo.get("mat_no"));
            return returnMap;
        }
    }

    /**
     * 库存物料数据筛选（按仓库分组统计）
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectSearchMat(Map<String, Object> map) {
      List<Map<String, Object>> list =  ivStoreHouseMapper.selectSearchMat(map);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("store_mat_list",list);
        return returnMap;
    }


    /**
     * 库存物料数据筛选（按仓库库位分组统计）
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectRommSearchMat(Map<String, Object> map) {
        List<Map<String, Object>> list =  ivStoreRoomMapper.selectRommSearchMat(map);
        if(0==list.size()){
            throw  new IllegalArgumentException("不存在物料，请添加");
        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("store_mat_list",list);
        return returnMap;
    }


    /**
     * 库存物料数据筛选（按全库存统计）
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectStoreSearchMat(Map<String, Object> map) {
        Map<String, Object> query = (Map<String, Object>) map.get("query");
        map.putAll(query);
        map.put("page", ((Integer) map.get("curr_page") - 1) * (Integer) map.get("page_size"));
        List<Map<String, Object>> storeList = ivStoreMapper.selectStoreSearchMat(map);
        Map<String, Object> page = new HashMap<>();
        if ("1".equals(map.get("is_paging").toString())) {
            if (storeList.size() <= 0) {
                return StringUtils.pageList(map, "store_mat_list", 0);
            }
            Integer totalCount = ivStoreMapper.totalCountStore(map);
            page = StringUtils.page(map, storeList, "store_mat_list", totalCount);
            return page;
        }else {
            page.put("store_mat_list", storeList);
            return page;
        }
    }

    /**
     * 查询物料信息详情
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectMatModify(Map<String, Object> map) {
        return bmMatMapper.selectMatModify(map);
    }

    /**
     * 基础物料数据筛选（按物料）
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectBasicMatList(Map<String, Object> map) {
        map.put("page",((Integer)map.get("curr_page")-1*(Integer)map.get("page_size")));
        List<Map<String,Object>>   matList=ivStoreMapper.selectBasicMatList(map);
        Map<String,Object>  page=new  HashMap<>();
        if("1".equals(map.get("is_paging"))){
            if(matList.size() <=0){
                return  StringUtils.pageList(map,"matList",0);
            }
            Integer   totalCount=ivStoreMapper.totalCountMat(map);
            page=StringUtils.page(map,matList,"matList",totalCount);
            return  page;
        }else{
            page.put("matList",matList);
            return page;
        }
    }
}

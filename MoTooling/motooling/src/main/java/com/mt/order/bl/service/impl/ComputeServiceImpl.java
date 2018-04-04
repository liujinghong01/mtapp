package com.mt.order.bl.service.impl;

import com.mt.common.core.shiro.token.manager.TokenManager;
import com.mt.common.utils.DateUtil;
import com.mt.common.utils.StringUtils;
import com.mt.order.bl.service.ComputePartCostService;
import com.mt.order.bl.service.ComputeService;
import com.mt.order.common.dao.ComputeCostMapper;
import com.mt.order.common.dao.ComputeMapper;
import com.mt.order.common.dao.ComputePartCostMapper;
import com.mt.order.common.model.ComputeCost;
import com.mt.pc.purchase.service.CreateBillNoService;
import com.mt.upms.common.model.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ComputeServiceImpl implements ComputeService {
    private final static Logger log = Logger.getLogger(ComputeServiceImpl.class);
    @Autowired
    private ComputeMapper computeMapper;
    @Autowired
    private ComputeCostMapper computeCostMapper;
    @Autowired
    private ComputePartCostMapper computePartCostMapper;
    @Autowired
    private CreateBillNoService createBillNoService;
    @Autowired
    private ComputePartCostService computePartCostService;



    /**
     * 获取核算单列表
     */
    @Override
    public Map<String, Object> selectOrderList(Map<String, Object> map) {
        JSONObject jsonObject = JSONObject.fromObject(map.get("query"));
        if (jsonObject.containsKey("compute_no")) {
            map.put("compute_no", jsonObject.get("compute_no")+"");
            jsonObject.remove("compute_no");
                }
        if(jsonObject.containsKey("cst_prod_name")){
            map.put("cst_prod_name", jsonObject.get("cst_prod_name")+"");
            jsonObject.remove("cst_prod_name");
        }
        if(jsonObject.containsKey("compute_id")){
            map.put("compute_id", jsonObject.get("compute_id")+"");
            jsonObject.remove("compute_id");
        }

            for (Object obj : jsonObject.keySet()) {
                JSONArray array = jsonObject.getJSONArray(obj.toString());
                StringBuffer param = new StringBuffer();
                for (Object objList : array) {
                    param.append(objList).append("|");
                }
                if (param.length() > 0) {
                    map.put(obj.toString(), param.toString().substring(0, param.length() - 1));
                }
            }
        map.put("page", ((Integer) map.get("curr_page") - 1) * (Integer) map.get("page_size"));
        List<Map<String, Object>> list = computeMapper.vagueSelect(map);
        if(list.size()<=0){
            return StringUtils.pageList(map,"list",0);
            }
        Integer totalCount = computeMapper.totalCount(map);
        Map<String, Object> page = StringUtils.page(map, list, "list", totalCount);
        return page;
    }


    /**
     * 新增核算单
     * JSON 解析出数据进行添加
     */
    @Override
    @Transactional
    public Map<String, Object> addCustomerOrder(Map<String, Object> map) throws IllegalAccessException{
        //判断compute_id  存在为修改。 不存在为添加
        Boolean flg =false;
        if(map.containsKey("compute_id")){
            flg=  map.get("compute_id")==null||map.get("compute_id")==""?false:true;
        }
        Map<String, Object> computeInfo = (Map<String, Object>) map.get("compute_info");

        if(0<computeMapper.selectProdInfoRepea(computeInfo.get("prod_info").toString(),Integer.valueOf(map.get("company_id").toString()),map.get("compute_id"))){
            throw  new IllegalArgumentException("制品信息重复");
        }

        if (computeInfo.containsKey("type")) {
            Integer yepe = Integer.valueOf(computeInfo.get("type").toString());
            if (yepe == 1) {
                computeInfo.put("type_name", "冲压模");
            } else if (yepe == 2) {
                computeInfo.put("type_name", "压铸模");
            } else {
                computeInfo.put("type_name", "注塑模");
            }
        }
        if (!computeInfo.isEmpty()) {
            for (Object obj : computeInfo.keySet()) {
                map.put((String) obj, computeInfo.get(obj));
            }
        }
        // 创建核算单表时间
        String date = DateUtil.dateToString(new Date(), DateUtil.DATETIME_PATTERN);
//        QuickResponse quickResponse = new QuickResponse();
//        quickResponse.setTypeName(BillTypeEnum.BA.toString());
        int id = 0;
        if (flg) {
            //修改核算单  stuff_cost
            map.put("updated_at", date);
            computeMapper.updateCustomerOrder(map);
            Double d =  computeCostMapper.selectSumStuffCost( Integer.valueOf(map.get("compute_id").toString()));
//            quickResponse.setId(Integer.valueOf(map.get("compute_id").toString()));
            if(d!=null&&d>=0) {
                map.put("stuff_cost", d);
            }
            computeCostMapper.updateComputeCost(map);
        } else {
            //新增 放回 添加数据的ID MAP 参数返回。  get("id") 获取到 compute表 自增的 ID
            map.put("created_at", date);
            String computeNo = createBillNoService.createBillNo(Integer.valueOf(map.get("company_id").toString()), "BA", Long.valueOf(map.get("uid").toString())).get("bill_no").toString();
            map.put("compute_no", computeNo);
            User user = TokenManager.getToken();
            map.put("handler_name", user.getFullname());
            computeMapper.addCustomerOrder(map);
            id = Integer.parseInt(map.get("id").toString());
            map.put("compute_id", id);
//            quickResponse.setId(id);
//            quickResponse.setBillNo(computeNo);
            computeCostMapper.addComputeCost(map);
        }
//        quickResponse.setDate(DateUtil.dateToStringWithTime());
//        String url = QiniuUtil.uploadFile(quickResponse.createQRCode(quickResponse), QiniuUtil.BUCKET_NAME_BILL, map.get("company_id").toString(), BillTypeEnum.BA.toString());
//        quickResponse.setUrl(url);
        Map<String, Object> objectMap = new HashMap<>();
        Map<String, Object> computeMap = selectOrderMoldDatail(Integer.valueOf(map.get("compute_id").toString()));
        computeMap.put("compute_date",DateUtil.getCurrentDateString());
        objectMap.put("compute_info", computeMap);
//        objectMap.put("url", url);
        return objectMap;
    }

    /**
     * 根据 compute_id 查询出 核算单 及子表 的数据
     */
    @Override
    public Map<String, Object> selectOrderMoldDatail(Integer compute_id) {
        Map<String, Object> map = computeMapper.getComputeAndCost(compute_id);
        return map;
    }

    /**
     * 删除核算单  修改status  = 0   不进行 物理删除
     *
     * @param computeId
     * @return
     */
    @Override
    public Boolean deleteOrder(Long computeId) {
        return computeMapper.updateStatus(computeId);
    }


    /**
     * 参考核算单
     * @return
     */
    @Override
    public Map<String, Object> selectassciate(Map<String, Object> param) throws IllegalAccessException {
        Integer computeId = Integer.valueOf(param.get("compute_id").toString());
        ComputeCost computeCost = computeCostMapper.selectCostDetail(computeId);
        computeCost.setComputeId(computeId.longValue());
        Map<String, Object> objectMap = computePartCostService.selectGetPartList(computeId);
        List< Map<String, Object>> list = (List<Map<String, Object>>) objectMap.get("part_list");
        List arrayList = new ArrayList();
        for (Map<String, Object> partMap: list) {
            Map<String, Object> detailPart = computePartCostService.selectDetailPart(partMap);
            detailPart.remove("part_id");
            for (Map<String, Object> subMap: (List<Map<String, Object>>)detailPart.get("sub_part_list")) {
                subMap.remove("sub_part_id");
            }
            arrayList.add(detailPart);
        }
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, Object> computeCostMap = StringUtils.camelToUnderline(JSONObject.fromObject(computeCost));
        returnMap.put("part_list",arrayList);
        Long currentComputeId = Long.valueOf(param.get("current_compute_id").toString());
        computeCost.setComputeId(currentComputeId);
        computeCostMapper.updateByPrimaryKeySelective(computeCost);
        computePartCostMapper.updateEditionPart(currentComputeId);
        returnMap.put("compute_id",currentComputeId);
        computePartCostService.addAndUpdateComputePartCost(returnMap);
        returnMap.put("compute_info",computeCostMap);
        return returnMap;
    }

}

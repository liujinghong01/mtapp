package com.mt.order.bl.service.impl;

import com.mt.common.utils.StringUtils;
import com.mt.order.bl.service.ProjectNotHwService;
import com.mt.order.common.dao.MoldMapper;
import com.mt.order.common.dao.ProjectNotHwMapper;
import com.mt.order.common.dao.ProjectNotHwProdMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectNotHwServiceImpl implements ProjectNotHwService {

    @Autowired
    private ProjectNotHwMapper projectNotHwMapper;
    @Autowired
    private ProjectNotHwProdMapper projectNotHwProdMapper;
    @Autowired
    private MoldMapper moldMapper;

    /**
     * 查询定单行信息列表
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> queryNotHwLineList(Map<String, Object> param) {
        List<Map<String, Object>> map = projectNotHwMapper.queryNotHwLineListMap(param);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("order_line_list",map);
        return returnMap;
    }

//    public Map<String, Object> queryNotHwLineList(Integer projectId) {
//        List<Map<String, Object>> map = projectNotHwMapper.queryNotHwLineList(projectId);
//        Map<String, Object> returnMap = new HashMap<>();
//        returnMap.put("order_line_list",map);
//        return returnMap;
//    }

    /**
     * 查询客户定单行信息明细
     * @return
     */
    @Override
    public Map<String, Object> queryNotHwLineDetail(Integer order_line_id) {
        Map<String, Object> map = projectNotHwMapper.queryNotHwLineDetail(order_line_id);
       List<Map<String, Object>> listMap= projectNotHwProdMapper.selectProdList(order_line_id);
        map.put("prod_list",listMap);
        return map;
    }


    /**
     * 客户定单行信息的新增修改
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> addAndUpdatemodifyLine(Map<String, Object> param) {
        Map<String, Object> map=null;
        List<Map<String, Object>> mapList =null;
        if(!param.containsKey("prod_list")){
            map = (Map<String, Object>) param.get("cst_order_line");
            map.put("project_id",param.get("project_id"));
            if(param.get("project_no") != null && param.get("project_no")!=""){
                map.put("project_no",param.get("project_no"));
            }
            if(param.containsKey("order_line_id")){
                map.put("order_line_id",param.get("order_line_id"));
            }
            mapList = (List<Map<String, Object>>) map.get("prod_list");
            map.remove("prod_list");
        }else{
            mapList = (List<Map<String, Object>>) param.get("prod_list");
            param.remove("prod_list");
            map=param;
        }
        //判断part_id 是否为空  为空增加， 不为空修改
        Boolean partFlg =false;
        int id = 0;
        if(map.containsKey("order_line_id")){
            partFlg=  map.get("order_line_id")==null||map.get("order_line_id")==""?false:true;
        }
        if(map.get("price")!=null && map.get("price")!="") {
            if (Double.valueOf(map.get("price").toString()) > 0) {
                if (Double.valueOf(map.get("count").toString()) < 1) {
                   throw  new IllegalArgumentException("请填写数量");
                }
            }
        }
        if(partFlg){
            //修改
           int i =  projectNotHwMapper.updateNotHw(map);
           id= Integer.valueOf(map.get("order_line_id").toString());
        }else {
            //新增
          int i =   projectNotHwMapper.insterNotHw(map);
            id = Integer.parseInt(map.get("id").toString());
        }
        Boolean flg =false;
        if(mapList!=null&&mapList.size()!=0){
            for (Map<String, Object> obj:mapList) {
                if(obj.containsKey("prod_id")){
                    flg=  obj.get("prod_id")==null||obj.get("prod_id")==""?false:true;
                }else {
                    flg=false;
                }
                if(flg){
                    //修改
                     projectNotHwProdMapper.updateProd(obj);
                }else {
                    //新增
                        if(id>0){
                           obj.put("project_not_hw_id",id);
                        }else {
                            obj.put("project_not_hw_id",map.get("quote_line_id"));
                        }
                      projectNotHwProdMapper.addProd(obj);
                }
            }
        }
        return queryNotHwLineDetail(id);
    }

    /**
     *    删除制品信息
     * @param param
     * @return
     */
    @Transactional
    @Override
    public boolean deleteProd(Map<String, Object> param) {
        List<Integer> list  = (List<Integer>) param.get("prod_id");
        return  projectNotHwProdMapper.updateStauts(list);
    }

    /**
     *  删除行信息
     * @param param
     * @return
     */
    @Override
    public boolean deleteLine(Map<String, Object> param) {
       Integer orderLineId = Integer.valueOf(param.get("order_line_id").toString());
        return projectNotHwMapper.deleteLine(orderLineId);
    }

    /**
     * 设置客户定单产品开始
     * @param orderLineId
     * @return
     */
    @Override
    public boolean updateOrderStartup(Integer orderLineId) {
        moldMapper.updateOrderStartup(orderLineId);
        return projectNotHwMapper.orderStartup(orderLineId);
    }

    /**
     * 设置客户定单产品反完结
     * @return
     */
    @Override
    public boolean updateOrderNotEnd(Integer orderLineId) {
        moldMapper.updateOrderNotEnd(orderLineId);
        return projectNotHwMapper.orderNotEnd(orderLineId);
    }

    /**
     * 设置客户定单产品暂停或者完结
     * @return
     */
    @Override
    public boolean updateOrderStartupStop(Map<String, Object> param) {
        Integer flag = Integer.valueOf(param.get("flag").toString());
       if( StringUtils.isEmpty(param.get("order_line_id").toString())){
           return false;
       }
        if(flag==1){
            //暂停
            param.put("startup_step","step300");
            moldMapper.updateOrderStartupStop(param);
            return projectNotHwMapper.orderStartupStop(param);
        }else if(flag==2){
            //完结
            param.put("startup_step","step00");
            moldMapper.updateOrderStartupStop(param);
            return projectNotHwMapper.orderStartupStop(param);
        }else {
            return false;
        }
    }
}

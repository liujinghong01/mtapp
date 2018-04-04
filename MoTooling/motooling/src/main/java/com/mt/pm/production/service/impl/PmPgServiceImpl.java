package com.mt.pm.production.service.impl;

import com.mt.cms.common.model.OrigUserCompany;
import com.mt.cms.company.service.CompanyService;
import com.mt.common.utils.DateUtil;
import com.mt.common.utils.StringUtils;
import com.mt.pm.common.dao.PmPgDeviceMapper;
import com.mt.pm.common.dao.PmPgMapper;
import com.mt.pm.common.dao.PmPgMemberMapper;
import com.mt.pm.production.service.PmPgService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
* @Author: Wendy
* @Description:
* @create    2018/1/24 15:07
* @Modified By:
*/    
@Service
public class PmPgServiceImpl implements PmPgService {
    private static Logger logger = Logger.getLogger(PmPgServiceImpl.class);

    @Autowired
    private PmPgMapper pmPgMapper;

    @Autowired
    private PmPgDeviceMapper pmPgDeviceMapper;

    @Autowired
    private PmPgMemberMapper pmPgMemberMapper;

    @Autowired
    private CompanyService companyService;


    /**
     * 查询生产小组的列表
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectProductionGroupList(Map<String, Object> map) {
        if (map.containsKey("query")) {
            Map<String, Object> query = (Map<String, Object>) map.get("query");
            map.put("all_upper_ids", query.get("dep_id") + "");
        }
        int totalCount = pmPgMapper.totalCount(map);
        map.put("page", ((Integer) map.get("curr_page") - 1) * (Integer) map.get("page_size"));
        List<Map<String, Object>>  pgList = pmPgMapper.selectProductionGroup(map);
        if(pgList.size()<=0){
            return StringUtils.pageList(map,"pg_list",0);
        }
        Map<String, Object> page = StringUtils.page(map, pgList, "pg_list", totalCount);
        return page;
    }


    /**
     * 查询生产小组的详情
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectProductionGroupDetail(Map<String, Object> map) {
        Map<String, Object> query = (Map<String, Object>) map.get("query");
        Long pgId = Long.valueOf(query.get("pg_id").toString());
        query.put("pg_id", query.get("pg_id").toString());
        Map<String, Object> productionGroupParam = pmPgMapper.selectProductionGroupDetail(query);
        List<Map<String, Object>> deviceList = pmPgDeviceMapper.selectProductionDeviceDetail(pgId);
        productionGroupParam.put("device_list", deviceList);

        List<Map<String, Object>> memeberList = pmPgMemberMapper.selectProductionGroupInfo(pgId);
        productionGroupParam.put("memeber_list", memeberList);

        Map<String, Object> returnMap = new HashMap<String, Object>(16);
        returnMap.put("pg_info", productionGroupParam);
        return returnMap;
    }


    /**
     * 人员删除
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> updateMember(Map<String, Object> map) {
        StringBuilder   str=new StringBuilder();
        Map<String, Object> memberInfo = (Map<String, Object>) map.get("member_info");
        Long   handlerId=Long.valueOf(map.get("uid").toString());
        Long   memeberId = Long.valueOf(memberInfo.get("member_id").toString().trim());
        Integer  pgId=Integer.valueOf(memberInfo.get("pg_id").toString());
        Integer  companyId=Integer.valueOf(map.get("company_id").toString());

        Integer  i=companyService.deleteUserCompany(companyId,pgId,memeberId,handlerId,str,false);
        if(i<0){
            throw   new  NoClassDefFoundError(str.toString());
        }
        return memberInfo;
    }


    /**
     * 生产小组删除
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> updateProductionGroup(Map<String, Object> map) {
        StringBuilder   str=new StringBuilder();

        Map<String, Object> productionGroupParam = (Map<String, Object>) map.get("pg_info");
        Integer   pgId=Integer.valueOf(productionGroupParam.get("pg_id").toString());
        Long      handlerId=Long.valueOf(map.get("uid").toString());

        Integer  i=companyService.deleteDepartment(pgId,handlerId,str,false);
        if(i<0){
            throw   new  NoClassDefFoundError(str.toString());
        }
        return productionGroupParam;
    }

    /**
     * 人员新增修改
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> addAndUpdateMemberInfo(Map<String, Object> map) {
        Map<String, Object>    memberParam = (Map<String, Object>) map.get("member_info");
        Integer   depId=Integer.valueOf(memberParam.get("pg_id").toString());
        Long      uId=Long.valueOf(memberParam.get("member_id").toString());
        Integer   companyId=Integer.valueOf(map.get("company_id").toString());
        StringBuilder  info=new StringBuilder();

        memberParam.put("pg_id",memberParam.get("pg_id"));
        memberParam.put("company_id",companyId);
        memberParam.put("uid",uId);
        Map<String,Object>   param=pmPgMemberMapper.selectMemberInfo(memberParam);

        if(param!=null){
            OrigUserCompany  or=new OrigUserCompany();
            or.setId(Long.valueOf(param.get("id").toString()));
            or.setUid(uId);
            or.setDepId(depId);
            or.setCompanyId(companyId);
            or.setWorkName(memberParam.get("work_name").toString());
            or.setWorkEmail(memberParam.get("work_email").toString());
            or.setWorkState(memberParam.get("work_state").toString());
            or.setWorkPhone(memberParam.get("work_phone").toString());
            or.setPositionName(memberParam.get("position_name").toString());
            if(memberParam.get("position_code")!=null) {
                Integer   positionCode=Integer.valueOf(memberParam.get("position_code").toString());
                or.setPositionCode(positionCode);
            }
            Integer i=companyService.updateUserCompany(or,info,false);
            if(i<0){
                throw   new  NoClassDefFoundError(info.toString());
            }
            if(i>0){
                memberParam.put("member_id",or.getUid());
                this.updateMemberManager(memberParam);
            }
        }
        if(param==null) {
            OrigUserCompany or = new OrigUserCompany();

            or.setUid(uId);
            or.setDepId(depId);
            or.setCompanyId(companyId);
            or.setWorkName(memberParam.get("work_name").toString());
            or.setWorkEmail(memberParam.get("work_email").toString());
            or.setWorkState(memberParam.get("work_state").toString());
            or.setWorkPhone(memberParam.get("work_phone").toString());
            or.setPositionName(memberParam.get("position_name").toString());
            if(memberParam.get("position_code")!=null) {
                Integer   positionCode=Integer.valueOf(memberParam.get("position_code").toString());
                or.setPositionCode(positionCode);
            }
            Integer i = companyService.insertUserCompany(or, info, false);
            if (i < 0) {
                throw new NoClassDefFoundError(info.toString());
            }
            if (i > 0) {
                memberParam.put("member_id",or.getUid());
                this.updateMemberManager(memberParam);
            }
        }

        Map<String, Object> returnMap = new HashMap<String,Object>();
        memberParam.put("member_id",memberParam.get("member_id").toString());
        if(param!=null){
            returnMap.put("member_info", pmPgMemberMapper.selectMemberSaveInfo(memberParam));
        } else {
            returnMap.put("member_info", pmPgMemberMapper.selectMemberSaveInfo(memberParam));
        }
        return returnMap;
    }

    private  void    updateMemberManager(Map<String,Object>  memberInfo){
        memberInfo.put("furlough_end_date",memberInfo.get("furlough_end_date"));
        memberInfo.put("furlough_start_date",memberInfo.get("furlough_start_date"));
        pmPgMemberMapper.updateMemberInfo(memberInfo);
    }

    /**
     * 生产小组新增修改
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> addAndUpdateProductionGroup(Map<String, Object> map) {
        Map<String, Object> pgInfo = (Map<String, Object>) map.get("pg_info");
        pgInfo.put("company_id", map.get("company_id"));

        StringBuilder   info=new StringBuilder();
        Boolean falg = false;
        Long id = null;
        if (pgInfo.containsKey("pg_id")) {
            falg = pgInfo.get("pg_id") == null || pgInfo.get("pg_id") == "" ? false : true;
        }
        if (falg) {
            Map<String, Object> groupParam = pmPgMapper.selectProductionGroupInfo(Long.valueOf(pgInfo.get("pg_id").toString().trim()));
            pgInfo.put("updated_at", DateUtil.getDateTime());
            // 上级部门ID
            pgInfo.put("parent_dep_id", pgInfo.get("parent_dep_id"));
            // 上级部门名称
            pgInfo.put("parent_dep_name", pgInfo.get("parent_dep_name"));
            // 部门名称
            pgInfo.put("dep_name", pgInfo.get("pg_name"));
            // 部门主管ID
            pgInfo.put("dep_admin_id", pgInfo.get("dep_admin_id"));
            pgInfo.put("dep_admin_name", pgInfo.get("dep_admin"));
            // 生产小组编号
            pgInfo.put("pg_id", pgInfo.get("pg_id"));
            // 修改生产小组
            pmPgMapper.updateProductionGroupInfo(pgInfo);
            // 部门ID
            pgInfo.put("dep_id", groupParam.get("pg_id"));


            Integer  i=companyService.updateDepartment(pgInfo, info, true);
            if(i<0){
                throw   new  NullPointerException(info.toString());
            }

        } else {
            // 创建部门生产小组
            pgInfo.put("created_at", DateUtil.getDateTime());
            // 上级部门ID
            pgInfo.put("upper_id", pgInfo.get("parent_dep_id"));
            // 上级部门名称
            pgInfo.put("upper", pgInfo.get("parent_dep_name"));
            // 部门名称
            pgInfo.put("dep_name", pgInfo.get("pg_name"));
            // 部门主管ID
            pgInfo.put("manager", pgInfo.get("dep_admin_id"));
            // 部门主管ID
            pgInfo.put("manager_name", pgInfo.get("dep_admin"));
            // 新增部门
            Map<String, Object> addDeptParam = companyService.addDept(pgInfo, null, true);
            if(addDeptParam==null){
                throw   new  NullPointerException(info.toString());
            }
            id = Long.valueOf(addDeptParam.get("dep_id").toString());
            // 生产部的子部门ID
            pgInfo.put("pg_id", id);
            // 新增生产小组
            pmPgMapper.addProductionGroup(pgInfo);
            id = Long.valueOf(pgInfo.get("id").toString());
        }

        Map<String, Object> returnMap = new HashMap<String,Object>();
        if (falg) {
            returnMap = pmPgMapper.selectProductionGroupInfo(Long.valueOf(pgInfo.get("pg_id").toString().trim()));
        } else {
            returnMap = pmPgMapper.selectProductionGroupInfo(id);
        }
        return returnMap;
    }
}
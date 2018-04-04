package com.mt.pm.production.service.impl;

import com.mt.cms.common.dao.CompanyMapper;
import com.mt.cms.common.model.Company;
import com.mt.common.utils.StringUtils;
import com.mt.pc.purchase.service.CreateBillNoService;
import com.mt.pm.common.dao.*;
import com.mt.pm.common.model.*;
import com.mt.pm.production.service.PmProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PmProcessServiceImpl  implements PmProcessService {
    @Autowired
    private PmProcConfMapper pmProcConfMapper;
    @Autowired
    private PmRoutingTmplMapper pmRoutingTmplMapper;
    @Autowired
    private PmRoutingTmplSubMapper pmRoutingTmplSubMapper;
    @Autowired
    private PmRoutingTmplTypeMapper pmRoutingTmplTypeMapper;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private PmProcPgMapper pmProcPgMapp;
    @Autowired
    private CreateBillNoService createBillNoService;


    @Override
    public boolean deleteProcessByPrimaryKey(Long id) {
        pmProcConfMapper.deleteByPrimaryKey(id);
        pmProcPgMapp.deleteByProcId(id);
        return true;
    }

    @Override
    public boolean createOrUpdateProcess(PmProcConf record,List<PmProcPg> records) {
        if (record.getId()==null) {
            Company company = companyMapper.selectByCompanyId(Integer.parseInt(record.getCompanyId()));
            record.setCompanyName(company.getCompanyName());
            String  proc_no = createBillNoService.createBillNo(company.getCompanyId(), "GX", Long.valueOf(0)).get("bill_no").toString();
            record.setProcNo(proc_no);
            record.setCreatedAt(new Date());
            record.setUpdatedAt(new Date());
            record.setStatus("1");
            pmProcConfMapper.insert(record);
            PmProcPg bean;
            for (int i = 0; i < records.size(); i++) {
                bean = records.get(i);
                bean.setProcId(record.getId());
                bean.setCreatedAt(new Date());
                bean.setUpdatedAt(new Date());
                bean.setStatus("1");
                pmProcPgMapp.insert(bean);
            }
        }
        else
        {
            record.setUpdatedAt(new Date());
            pmProcConfMapper.updateByPrimaryKeySelective(record);
            pmProcPgMapp.deleteByProcId(record.getId());
            PmProcPg bean;
            for (int i = 0; i < records.size(); i++) {
                bean = records.get(i);
                bean.setProcId(record.getId());
                bean.setCreatedAt(new Date());
                bean.setUpdatedAt(new Date());
                bean.setStatus("1");
                pmProcPgMapp.insert(bean);
            }
        }
        return true;
    }

    @Override
    public PmProcConf selectProcessByPrimaryKey(Long id) {
        return pmProcConfMapper.selectByPrimaryKey(id);
    }


    @Override
    public Map<String, Object> selectProcessByConditions(Map<String, Object> map) {
        Map<String, Object> query = (Map<String, Object>) map.get("query");
        map.put("page", ((Integer) map.get("curr_page") - 1) * (Integer) map.get("page_size"));
        Integer totalCount = pmProcConfMapper.selectProcessByconditionCount(query);
        List<Map<String, Object>> list = pmProcConfMapper.selectProcessBycondition(query);
        if(list.size()<=0){
            return StringUtils.pageList(map,"proc_info",0);
        }
        Map<String, Object> page = StringUtils.page(map, list, "proc_info", totalCount);
        return page;
    }

    @Override
    public boolean deleteRoutingTmplTypeByPrimaryKey(Long id) {
        Map<String, Object> query = new HashMap<>();
        query.put("tmpl_type_id",id);
        if(pmRoutingTmplMapper.selectRoutingTmplByconditionCount(query)>0)
        {
            return false;
        }
        else
        {
            pmRoutingTmplTypeMapper.deleteByPrimaryKey(id);
            return true;
        }
    }

    @Override
    public boolean createOrUpdatePmRoutingTmplType(PmRoutingTmplType record) {
        if(record.getId()==null)
        {
            Company company = companyMapper.selectByCompanyId(Integer.parseInt(record.getCompanyId()));
            record.setCompanyName(company.getCompanyName());
            record.setCreatedAt(new Date());
            record.setUpdatedAt(new Date());
            record.setStatus("1");
            pmRoutingTmplTypeMapper.insert(record);
        }
        else {
            record.setUpdatedAt(new Date());
            pmRoutingTmplTypeMapper.updateByPrimaryKeySelective(record);
        }
        return true;
    }

    @Override
    public PmProcConf selectRoutingTmplTypeByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public Map<String, Object> selectRoutingTmplTypeByConditions(Map<String, Object> map) {
        Map<String, Object> query = (Map<String, Object>) map.get("query");
        map.put("page", ((Integer) map.get("curr_page") - 1) * (Integer) map.get("page_size"));
        Integer totalCount = pmRoutingTmplTypeMapper.selectRoutingTmplTypeByconditionCount(query);
        List<Map<String, Object>> list = pmRoutingTmplTypeMapper.selectRoutingTmplTypeBycondition(query);
        if(list.size()<=0){
            return StringUtils.pageList(map,"routing_tmpl_type_info",0);
        }
        Map<String, Object> page = StringUtils.page(map, list, "routing_tmpl_type_info", totalCount);
        return page;
    }

    @Override
    public List<Map<String, Object>> selectRoutingTmplTypeList(Map queryMap)
    {
        return pmRoutingTmplTypeMapper.selectRoutingTmplTypeList(queryMap);
    }

    @Override
    public boolean deleteRoutingTmplByPrimaryKey(Long id) {
        pmRoutingTmplSubMapper.deleteByTmplId(id);
        pmRoutingTmplMapper.deleteByPrimaryKey(id);
        return true;
    }

    @Override
    public boolean createOrUpdatePmRoutingTmpl(PmRoutingTmpl record,List<PmRoutingTmplSub> records) {
        if(record.getId()==null)
        {
            Company company = companyMapper.selectByCompanyId(Integer.parseInt(record.getCompanyId()));
            record.setCompanyName(company.getCompanyName());
            record.setCreatedAt(new Date());
            record.setUpdatedAt(new Date());
            record.setStatus("1");
            pmRoutingTmplMapper.insert(record);
            if (records!=null)
            {
                PmRoutingTmplSub sub;
                for(int i=0;i<records.size();i++){
                    sub = records.get(i);
                    sub.setTmplId(record.getId());
                    sub.setCreatedAt(new Date());
                    sub.setUpdatedAt(new Date());
                    sub.setStatus("1");
                    pmRoutingTmplSubMapper.insert(sub);
                }
            }
        }
        else{
            record.setUpdatedAt(new Date());
            pmRoutingTmplMapper.updateByPrimaryKeySelective(record);
            String subId = "";
            List<Map<String, Object>> listR = pmRoutingTmplSubMapper.selectByTmplId(record.getId());
            for (int i = 0; i < listR.size(); i++) {
                subId += listR.get(i).get("id") + ",";
            }
            if (records!=null)
            {
                PmRoutingTmplSub sub;
                for(int i=0;i<records.size();i++){
                    sub = records.get(i);
                    sub.setTmplId(record.getId());
                    if (sub.getId()==null) {
                        sub.setTmplId(record.getId());
                        sub.setCreatedAt(new Date());
                        sub.setUpdatedAt(new Date());
                        sub.setStatus("1");
                        pmRoutingTmplSubMapper.insert(sub);
                    }
                    else {
                        subId = subId.replace(sub.getId() + ",", "");
                        sub.setUpdatedAt(new Date());
                        pmRoutingTmplSubMapper.updateByPrimaryKeySelective(sub);
                    }
                }
                if (!subId.equals("")){
                    String [] list =subId.split(",");
                    for (int i=0;i<list.length;i++){
                        pmRoutingTmplSubMapper.deleteByPrimaryKey(Long.parseLong(list[i]));
                    }
                }
            }
        }
        return true;
    }

    @Override
    public PmRoutingTmpl selectRoutingTmplByPrimaryKey(Long id) {
        return pmRoutingTmplMapper.selectByPrimaryKey(id);
    }


    @Override
    public Map<String, Object> selectRoutingTmplByConditions(Map<String, Object> map) {
        Map<String, Object> query = (Map<String, Object>) map.get("query");
        map.put("page", ((Integer) map.get("curr_page") - 1) * (Integer) map.get("page_size"));
        Integer totalCount = pmRoutingTmplMapper.selectRoutingTmplByconditionCount(query);
        List<Map<String, Object>> list = pmRoutingTmplMapper.selectRoutingTmplBycondition(query);
        if(list.size()<=0){
            return StringUtils.pageList(map,"routing_tmpl_info",0);
        }
        Map<String, Object> page = StringUtils.page(map, list, "routing_tmpl_info", totalCount);
        return page;
    }


}

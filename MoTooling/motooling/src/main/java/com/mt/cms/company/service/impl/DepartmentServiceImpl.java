package com.mt.cms.company.service.impl;

import com.mt.cms.common.dao.DepartmentMapper;
import com.mt.cms.common.dao.OrigUserCompanyMapper;
import com.mt.cms.company.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private OrigUserCompanyMapper origUserCompanyMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 先修改 要删除的公司部门  全部人员 DepId 为null  再 把公司 的部门给删除
     */
    @Override
    @Transactional
    public Boolean deleteDepId(Map<String, Object> map) {
        int i = departmentMapper.deleteBatch(map);
        if (i >0) {
            origUserCompanyMapper.updateDepId(map);
        List<Map<String,Object>> list = origUserCompanyMapper.selectIds();
            if(list != null){
                for (Map<String,Object> o : list) {
                    origUserCompanyMapper.deleteByPrimaryKey(Integer.valueOf(o.get("id").toString()));
                }
            }
            return true;
            }
        return false;
    }
}

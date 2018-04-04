package com.mt.cms.company.service.impl;

import com.mt.cms.common.dao.OrigUserCompanyMapper;
import com.mt.cms.common.model.OrigUserCompany;
import com.mt.cms.company.service.CompanyDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CompanyDepartmentServiceImpl implements CompanyDepartmentService {

    @Autowired
    private OrigUserCompanyMapper origUserCompanyMapper;

    @Override
    @Transactional
    public Boolean deleteBatch(String companyId, String depId, List memberIds) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("companyId", companyId);
        map.put("depId", depId);
        map.put("memberIds", memberIds);
        if(depId.equals(null)||depId.equals("")){
            return origUserCompanyMapper.batchDeleteNull(map);
        }else {
            return origUserCompanyMapper.batchDelete(map);
        }
    }


    @Override
    public Boolean updateBatch(String companyId, List depIds, List memberIds, Integer userDepId) {
        if (depIds.size() == 1) {
            Map<String, Object> map = new HashMap();
            map.put("companyId", companyId);
            map.put("depId", depIds.get(0));
            map.put("memberIds", memberIds);
            map.put("userDepId", userDepId);
            int i = origUserCompanyMapper.updateDepids(map);
            if (i > 0) {
                return true;
            }
        } else if (depIds.size() > 1) {
            Map<String, Object> mapUser = new HashMap();
            mapUser.put("companyId", companyId);
            mapUser.put("userDepId", userDepId);
            for (Object m : memberIds) {
                mapUser.put("uId", m);
                int num = 1;
                for (Object depId : depIds) {
                    mapUser.put("depId", depId);
                    OrigUserCompany origUserCompany = origUserCompanyMapper.selectUser(mapUser);
                    if(num++==1){
                        origUserCompanyMapper.deleteUser(mapUser);
                    }
                    origUserCompany.setDepId(Integer.valueOf(depId.toString()));
                    origUserCompany.setId(null);
                    origUserCompany.setRoleCode(null);
                    origUserCompanyMapper.insertSelective(origUserCompany);
                }
            }
            return true;
        }
        return false;
    }
}

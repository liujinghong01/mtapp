package com.mt.cms.company.service;

import java.util.List;

public interface CompanyDepartmentService {
    Boolean deleteBatch(String companyId, String depId, List<Integer> memberIds);

    Boolean updateBatch(String companyId, List depIds, List memberIds, Integer userDepId);
}

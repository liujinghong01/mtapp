package com.mt.bm.bom.service.impl;

import com.mt.bm.bom.service.CascadeMatService;
import com.mt.bm.common.dao.CascadeMatMapper;
import com.mt.bm.common.model.CascadeMat;
import com.mt.common.core.mybatis.BaseMybatisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CascadeMatServiceImpl extends BaseMybatisDao<CascadeMatMapper> implements CascadeMatService {

    @Autowired
    private CascadeMatMapper cascadeMatMapper;

    @Override
    public int insertCascadeMat(CascadeMat cascadeMat) {
        return cascadeMatMapper.insert(cascadeMat);
    }

    @Override
    public List<CascadeMat> selectByMatType(Map queryMap) {
        return  cascadeMatMapper.selectByMatType(queryMap);
    }

    @Override
    public int deleteCascadeMat(Integer id) {
        return cascadeMatMapper.deleteByPrimaryKey(id);
    }

    @Override
    public CascadeMat selectById(Integer id) {
        return cascadeMatMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateCascadeMat(CascadeMat cascadeMat) {
        return cascadeMatMapper.updateByPrimaryKey(cascadeMat);
    }

    @Override
    public List<String> selectCascadeMatTypeList(Map queryMap) {
        return cascadeMatMapper.selectCascadeMatTypeList(queryMap);
    }
}

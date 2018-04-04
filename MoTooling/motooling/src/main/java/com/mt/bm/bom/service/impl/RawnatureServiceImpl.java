package com.mt.bm.bom.service.impl;

import com.mt.bm.bom.service.RawnatureService;
import com.mt.bm.common.dao.RawnatureMapper;
import com.mt.bm.common.model.Rawnature;
import com.mt.common.core.mybatis.BaseMybatisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RawnatureServiceImpl extends BaseMybatisDao<RawnatureMapper> implements RawnatureService {

    @Autowired
    private RawnatureMapper rawnatureMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return rawnatureMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Rawnature record) {
        return rawnatureMapper.insert(record);
    }

    @Override
    public int insertSelective(Rawnature record) {
        return 0;
    }

    @Override
    public Rawnature selectByPrimaryKey(Long id) {
        return rawnatureMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Rawnature record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Rawnature record) {
        return rawnatureMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Rawnature> selectRawNatureList(Map map)
    {
        return  rawnatureMapper.selectRawNatureList(map);
    }
}

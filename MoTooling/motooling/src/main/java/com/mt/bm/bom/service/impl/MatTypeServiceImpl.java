package com.mt.bm.bom.service.impl;

import com.mt.bm.bom.service.MatTypeService;
import com.mt.bm.common.dao.MatTypeMapper;
import com.mt.bm.common.model.MatType;
import com.mt.common.core.mybatis.BaseMybatisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatTypeServiceImpl extends BaseMybatisDao<MatTypeMapper> implements MatTypeService {

    @Autowired
    private MatTypeMapper matTypeMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return matTypeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(MatType record) {
        return matTypeMapper.insert(record);
    }

    @Override
    public int insertSelective(MatType record) {
        return 0;
    }

    @Override
    public MatType selectByPrimaryKey(Long id) {
        return matTypeMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(MatType record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(MatType record) {
        return matTypeMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<MatType> selectMatTypeAll() {
        return matTypeMapper.selectMatTypeAll();
    }
}

package com.mt.bm.bom.service.impl;


import com.mt.bm.bom.service.MatService;
import com.mt.bm.common.dao.MatMapper;
import com.mt.bm.common.model.Mat;
import com.mt.common.core.mybatis.BaseMybatisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MatServiceImpl extends BaseMybatisDao<MatMapper> implements MatService {

    @Autowired
    private MatMapper matMapper;

    @Override
    public int deleteByPrimaryKey(Long matId) {
        return matMapper.deleteByPrimaryKey(matId);
    }

    @Override
    public int insert(Mat record) {
        return matMapper.insert(record);
    }

    @Override
    public int insertSelective(Mat record) {
        return 0;
    }

    @Override
    public Mat selectByPrimaryKey(Long matId) {
        return matMapper.selectByPrimaryKey(matId);
    }

    @Override
    public int updateByPrimaryKeySelective(Mat record) {
        return matMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Mat record) {
        return matMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<String> selectMatNameList(Map<String, Object> map) {
        return matMapper.selectMatNameList(map);
    }

    @Override
    public List<Map<String, Object>> selectMatNameSpecList(Map<String, Object> map) {
        return matMapper.selectMatNameSpecList(map);
    }
}

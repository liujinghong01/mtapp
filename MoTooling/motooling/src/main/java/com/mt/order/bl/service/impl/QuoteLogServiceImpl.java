package com.mt.order.bl.service.impl;

import com.mt.order.bl.service.QuoteLogService;
import com.mt.order.common.dao.QuoteMapper;
import com.mt.order.common.model.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
* @Author: Fhk
* @Description:
* @create    2018/3/12 18:14
* @Modified By:
*/
@Service
public class QuoteLogServiceImpl implements QuoteLogService {

    @Autowired
    private QuoteMapper quoteMapper;


    //生成新的日志, 返回当前版本号.在新增修改之前调用
    @Override
    public Integer createNewQuoteLog(Long quoteId, StringBuilder approveStep) {
        Quote quote = quoteMapper.selectByPrimaryKey(quoteId);
        if (quote == null) {
            if (approveStep != null ) { approveStep.append("step10"); }
            return 1;
        }
        if (!"step0".equals(quote.getApproveStep()) && !"step00".equals(quote.getApproveStep())) {
            if (approveStep != null ) { approveStep.append("step10"); }
            return quote.getVersion();
        } else {
            String ret = quoteMapper.addQuoteLog(quoteId);
            if ("1".equals(ret)) {
                if (approveStep != null ) { approveStep.append("step10"); }
                return quote.getVersion() == null ? 1 : quote.getVersion() + 1;
            } else {
                throw new IllegalArgumentException(ret.substring(2));
            }
        }
    }


    //删除报价单当前未审批结束版本，恢复报价单上一个版本。返回当前版本号
    @Override
    public Integer createRevertQuoteLog(Long quoteId) {
        Integer version;
        Quote quote = quoteMapper.selectByPrimaryKey(quoteId);
        if (quote == null) {
            throw new IllegalArgumentException("不存在该报价单");
        }
        String ret = quoteMapper.revertQuoteLog(quoteId);
        if ("1".equals(ret)) {
            version = quote.getVersion() - 1;
            return version == 0 ? 1 : version;
        } else {
            throw new IllegalArgumentException(ret.substring(2));
        }
    }

    //当V2版本及其之后版本审批时，浏览页面中应比对之前版本，返回变化部分
    @Override
    public List<Map<String, Object>> quoteVersionCompare(Long quoteId) {
        //目前暂未实现版本比较
        return null;
    }
}

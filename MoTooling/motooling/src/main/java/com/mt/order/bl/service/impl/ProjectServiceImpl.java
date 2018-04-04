package com.mt.order.bl.service.impl;

import com.mt.common.utils.DateUtil;
import com.mt.common.utils.StringUtils;
import com.mt.order.bl.service.ProjectNotHwService;
import com.mt.order.bl.service.ProjectService;
import com.mt.order.common.dao.ProjectMapper;
import com.mt.order.common.dao.ProjectNotHwMapper;
import com.mt.order.common.dao.QuoteMapper;
import com.mt.pc.purchase.service.CreateBillNoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProjectServiceImpl implements ProjectService {
    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private ProjectNotHwMapper projectNotHwMapper;

    @Autowired
    ProjectNotHwService projectNotHwService;

    @Autowired
    private CreateBillNoService createBillNoService;

    @Autowired
    private QuoteMapper quoteMapper;

    /**
     * 查询客户订单列表
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> selectProjectList(Map<String, Object> param) {
        Map<String, Object> map = (Map<String, Object>) param.get("query");
        map.put("curr_page", param.get("curr_page") == null ? 1 : param.get("curr_page"));
        map.put("page_size", param.get("page_size") == null ? 10 : param.get("page_size"));
        map.put("companyId", StringUtils.isEmpty(param.get("company_id").toString()) == true ? null : param.get("company_id").toString().trim());
        StringBuffer sb = new StringBuffer();
        if (map.containsKey("approve_step")) {
            List<String> listMap = (List<String>) map.get("approve_step");
            if (listMap != null) {
                for (String s : listMap) {
                    sb.append(s).append("|");
                }
                if (sb.length() > 0) {
                    map.put("approve_step", sb.toString().substring(0, sb.length() - 1));
                } else {
                    map.remove("approve_step");
                }
                sb.setLength(0);
            }
        }
        if (map.containsKey("cst_company_id")) {
            List<Integer> listMap = (List<Integer>) map.get("cst_company_id");
            if (listMap != null) {
                for (Integer s : listMap) {
                    sb.append(s).append("|");
                }
                if (sb.length() > 0) {
                    map.put("cst_company_id", sb.toString().substring(0, sb.length() - 1));
                } else {
                    map.remove("cst_company_id");
                }
            }
        }
        map.put("page", ((Integer) map.get("curr_page") - 1) * (Integer) map.get("page_size"));
        List<Map<String, Object>> list = projectMapper.selectProjectList(map);
        if (list.size() <= 0) {
            return StringUtils.pageList(map, "cst_order_list", 0);
        }
        Integer totalCount = projectMapper.selectProjectTotalCount(map);
        Map<String, Object> page = StringUtils.page(map, list, "cst_order_list", totalCount);
        return page;
    }

    @Override
    public boolean deleteProjectNo(Map<String, Object> param) {
        return projectMapper.deleteProjectNo(param);
    }

    /**
     * 新增 修改  客户定单头信息
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> updateAndAddModify(Map<String, Object> param) {
        Map<String, Object> map = null;
        if (param.containsKey("cst_order_head")) {
            map = (Map<String, Object>) param.get("cst_order_head");
        } else {
            map = param;
        }
        map.put("company_id", param.get("company_id"));
        Boolean b = false;
        Boolean flg = false;
        if (map.containsKey("project_id")) {
            flg = map.get("project_id") == null || map.get("project_id") == "" ? false : true;
        }
        Integer id = 0;
        String date = DateUtil.dateToString(new Date(), DateUtil.DATETIME_PATTERN);
        if (flg) {
            map.put("updated_at", date);
            b = projectMapper.updateModify(map) > 0 ? true : false;
            id = Integer.valueOf(map.get("project_id").toString());
        } else {
            map.put("created_at", date);
            map.put("project_no", createBillNoService.createBillNo(Integer.valueOf(param.get("company_id").toString()), "CO", Long.valueOf(param.get("uid").toString())).get("bill_no"));
            b = projectMapper.addModify(map) > 0 ? true : false;
            id = Integer.valueOf(map.get("id").toString());
        }

        return queryHead(id);
    }

    /**
     * 关联报价单生成客户订单
     *
     * @return
     */
    @Override
    public Map<String, Object> addCstOrderAuto(Map<String, Object> map) {
        List<Map<String, Object>> quoteList = quoteMapper.getDetails(Long.valueOf(map.get("quote_id").toString()));
        String projectNo = projectMapper.selectProjectNo(map);

        for (Map<String, Object> objectMap : quoteList) {
            List<Map<String, Object>> list = quoteMapper.selectQuoteMoldProd(Long.valueOf(objectMap.get("id").toString()));
            objectMap.put("prod_list", list);
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("project_id", map.get("project_id"));
            paramMap.put("project_no", projectNo);
            paramMap.put("cst_order_line", objectMap);
            projectNotHwService.addAndUpdatemodifyLine(paramMap);
        }
        Map<String, Object> objectMap = new HashMap<>(2);
        objectMap.put("project_id", map.get("project_id"));
        objectMap.put("project_no", projectNo);
        return objectMap;
    }

    /**
     * 查询 订单头信息
     *
     * @param projectId
     * @return
     */
    @Override
    public Map<String, Object> queryHead(Integer projectId) {
        Map<String, Object> map = projectMapper.queryHead(projectId);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("project_head", map);
        return returnMap;
    }

    /**
     * 查询客户定单下达列表
     *
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> orderStartupList(Map<String, Object> map) {
        map.put("page", ((Integer) map.get("curr_page") - 1) * (Integer) map.get("page_size"));
        Map<String, Object> query = (Map<String, Object>) map.get("query");
        map.putAll(query);
        List<Map<String, Object>> listMap = projectNotHwMapper.orderStartupList(map);
        if (listMap.size() <= 0) {
            return StringUtils.pageList(map, "cst_order_list", 0);
        }
        Integer totalCount = projectNotHwMapper.totalCount(map);
        Map<String, Object> page = StringUtils.page(map, listMap, "cst_order_list", totalCount);
        return page;
    }

    /**
     * 参考客户订单历史
     *
     * @return
     */
    @Override
    public Map<String, Object> addSelectProject(Map<String, Object> param) {
        // 历史定单号
        Integer project_id = Integer.valueOf(param.get("project_id").toString());
        // 当前定单号
        Integer current_project_id = Integer.valueOf(param.get("current_project_id").toString());
        // 查询出历史定单 行信息列表
        List<Map<String, Object>> queryNotHwLineList = projectNotHwMapper.queryNotHwLineListMap(param);
        Iterator<Map<String, Object>> iterator = queryNotHwLineList.iterator();
        List<Integer> intList = new ArrayList<Integer>(10);
        Map<String, Object> next = null;
        while (iterator.hasNext()) {
            next = iterator.next();
            intList.add(Integer.valueOf(Integer.valueOf(next.get("order_line_id").toString())));
        }
        Iterator<Integer> intListIterator = intList.iterator();
        List<Map<String, Object>> notHwLineDetailList = new ArrayList<>(10);
        while (intListIterator.hasNext()) {
            Map<String, Object> notHwLineDetail = projectNotHwService.queryNotHwLineDetail(intListIterator.next());
            notHwLineDetail.put("project_id", current_project_id);
            notHwLineDetail.remove("order_line_id");
            List<Map<String, Object>> prod_list = (List<Map<String, Object>>) notHwLineDetail.get("prod_list");
            for (Map<String, Object> prodMap : prod_list) {
                prodMap.remove("prod_id");
            }
            notHwLineDetailList.add(notHwLineDetail);
        }
        Iterator<Map<String, Object>> notHwLineDetailListIterator = notHwLineDetailList.iterator();
        while (notHwLineDetailListIterator.hasNext()) {
            projectNotHwService.addAndUpdatemodifyLine(notHwLineDetailListIterator.next());
        }

        Map<String, Object> returnMap = new HashMap<>(1);
        returnMap.put("current_project_id", current_project_id);
        return returnMap;
    }
}

/**
 * Created by zxf on 2017/10/26.
 *
 * 核算单页面接口列表
 */

import fetch from '@/utils/fetch';


//客户定单列表
export function list(data) {
  return fetch({
    url: 'api/cst_order/list',
    method: 'post',
    data
  });
}

//客户定单头信息新增/修改
export function save(data) {
  return fetch({
    url: 'api/cst_order/modify',
    method: 'post',
    data
  });
}

// 删除客户定单
//  /api/cst_order/delete

export function del(data) {
  return fetch({
    url: 'api/cst_order/delete',
    method: 'post',
    data
  });
}




//查询客户定单头信息
export function headDetail(data) {
  return fetch({
    url: '/api/cst_order/query_head',
    method: 'post',
    data
  });
}


//客户定单行信息修改
// /api/cst_order/modifyLine

export function modifyLine(data) {
  return fetch({
    url: 'api/cst_order/modifyLine',
    method: 'post',
    data
  });
}


//查询客户定单行信息列表
// api/cst_order/query_not_hw_line_list

export function queryLineList(data) {
  return fetch({
    url: 'api/cst_order/query_not_hw_line_list',
    method: 'post',
    data
  });
}

//查询客户定单行信息明细
// api/cst_order/query_not_hw_line_detail

export function queryLineDetail(data) {
  return fetch({
    url: 'api/cst_order/query_not_hw_line_detail',
    method: 'post',
    data
  });
}



//参考历史定单生成定单
// api/customerOrder/order/associate


export function referHis(data) {
  return fetch({
    url: 'api/customerOrder/order/associate',
    method: 'post',
    data
  });
}

//关联报价单生成客户定单行信息
// api/cst_order/auto_from_quote


export function associateQuote(data) {
  return fetch({
    url: 'api/cst_order/auto_from_quote',
    method: 'post',
    data
  });
}


// 客户定单行信息删除
///api/cst_order/deleteLine
export function deleteLine(data) {
  return fetch({
    url: 'api/cst_order/deleteLine',
    method: 'post',
    data
  });
}
export function UnitList(data) {
  return fetch({
    url:'/api/customerOrder/dict/processCostItemUnitList',
    method:'post',
    data
  })
}
export function rawNatureList(data) {
  return fetch({
    url: 'api/customerOrder/dict/rawNatureList',
    method: 'post',
    data
  });
}

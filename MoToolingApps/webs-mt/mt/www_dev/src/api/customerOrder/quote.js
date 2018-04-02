/**
 * Created by zxf on 2017/10/26.
 *
 * 核算单页面接口列表
 */

import fetch from '@/utils/fetch';

//历史报价单列表
export function list(data) {
  return fetch({
    url: '/api/quote/list',
    method: 'post',
    data
  });
}//历史核算单列表
export function computeList(data) {
  return fetch({
    url: 'api/customerOrder/compute/list',
    method: 'post',
    data
  });
}
//历史核算单详情
export function computeDetail(data) {
  return fetch({
    url: 'api/customerOrder/compute/mold/detail',
    method: 'post',
    data
  });
}
//参考历史报价单 api/customerOrder/quote/referHis
export function referHis(data) {
  return fetch({
    url: 'api/customerOrder/quote/referHis',
    method: 'post',
    data
  });
}
//参考历史核算单 api/customerOrder/quote/relateCompute
export function relateCompute(data) {
  return fetch({
    url: 'api/customerOrder/quote/relateCompute',
    method: 'post',
    data
  });
}

//报价单新增/修改

export function modify(data) {
  return fetch({
    url: '/api/quote/modify',
    method: 'post',
    data
  });
}

//报价单删除

export function quoteDelete(data) {
  return fetch({
    url: '/api/quote/delete',
    method: 'post',
    data
  });
}


//获取头信息
export function headDetail(data) {
  return fetch({
    url: 'api/quote/headDetail',
    method: 'post',
    data
  });
}

//  /api/quote_line/list   行信息列表

export function quoteLines(data) {
  return fetch({
    url: 'api/quote_line/list',
    method: 'post',
    data
  });
}


//  行信息详情
//   /api/quote_line/getInfo
export function quoteLineDetail(data) {
  return fetch({
    url: 'api/quote_line/getInfo',
    method: 'post',
    data
  });
}


//修改行信息
// /api/quote_line/modify

export function quoteLineModify(data) {
  return fetch({
    url: 'api/quote_line/modify',
    method: 'post',
    data
  });
}


// 删除行信息
// api/quote_line/delete

export function quoteLineDel(data) {
  return fetch({
    url: 'api/quote_line/delete',
    method: 'post',
    data
  });
}

// 查询付款信息列表
// /api/paylist/list
export function payList(data) {
  return fetch({
    url: 'api/paylist/list',
    method: 'post',
    data
  });
}

//修改付款信息
// /api/quote_pay/modify
export function payListModify(data) {
  return fetch({
    url: 'api/quote_pay/modify',
    method: 'post',
    data
  });
}

// 查询备注信息说明
///api/quote/queryRemark
export function remarkList(data) {
  return fetch({
    url: 'api/quote/queryRemark',
    method: 'post',
    data
  });
}

// 备注信息修改
////api/quote_remark/modify
export function remarkListModify(data) {
  return fetch({
    url: 'api/quote_remark/modify',
    method: 'post',
    data
  });
}

// 备注模板
///api/quote/remarkTemplate
export function remarkTemplate(data) {
  return fetch({
    url: 'api/quote/remarkTemplate',
    method: 'post',
    data
  });
}

//客户定单列表
export function orderList(data) {
  return fetch({
    url: 'api/cst_order/list',
    method: 'post',
    data
  });
}
//客户订单参考历史报价单  /api/cst_order/auto_from_quote
export function fromQuote(data) {
  return fetch({
    url: '/api/cst_order/auto_from_quote',
    method: 'post',
    data
  });
}
//客户订单参考历史客户定单  api/customerOrder/order/associate
export function orderAssociate(data) {
  return fetch({
    url: 'api/customerOrder/order/associate',
    method: 'post',
    data
  });
}





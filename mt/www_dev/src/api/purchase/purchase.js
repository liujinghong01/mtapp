/**
 * Created by zxf on 2017/11/22.
 */
import fetch from '@/utils/fetch'
//采购单相关



//查询采购定单列表
export function purchaseList(data) {
  return fetch({
    url: '/api/pc/purch_order/list',
    method: 'post',
    data
  });
}

//查询采购定单详情
export function purchaseDetail(data) {
  return fetch({
    url: '/api/pc/purch_order/detail',
    method: 'post',
    data
  });
}

//采购定单删除
export function purchaseDel(data) {
  return fetch({
    url: '/api/pc/purch_order/delete',
    method: 'post',
    data
  });
}

//采购定单新增/修改
export function purchaseModify(data) {
  return fetch({
    url: '/api/pc/purch_order/modify',
    method: 'post',
    data
  });
}


//采购定单提交审批
export function purchaseSubmitApprove(data) {
  return fetch({
    url: '/api/pc/purch_order/submit_approve',
    method: 'post',
    data
  });
}


//查询公司详情
export function getCompany(data){
  return  fetch({
    url:'api/company/get-company-profile',
    method:'post',
    data
  });
}


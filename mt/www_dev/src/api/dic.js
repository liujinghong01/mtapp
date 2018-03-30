/**
 * Created by zxf on 2017/10/30.
 */


// 客户订单用到的 下拉选择框的字典
import fetch from '@/utils/fetch';


//业务员列表
//  api/customerOrder/dict/salesmanList
export function salesmanList(data) {
  return fetch({
    url: 'api/customerOrder/dict/salesmanList',
    method: 'post',
    data:data
  });
}

// api/customerOrder/dict/rawNatureList

//材料列表

export function rawNatureList(data) {
  return fetch({
    url: 'api/customerOrder/dict/rawNatureList',
    method: 'post',
    data
  });
}

//加工费项目列表
// api/customerOrder/dict/processCostItemList

export function processCostItemList(data) {
  return fetch({
    url: 'api/customerOrder/dict/processCostItemList',
    method: 'post',
    data
  });
}

//加工费项目 单位列表
// api/customerOrder/dict/processCostItemUnitList

export function processCostItemUnitList(data) {
  return fetch({
    url: 'api/customerOrder/dict/processCostItemUnitList',
    method: 'post',
    data
  });
}

//客户列表
//api/customerOrder/dict/customerList

export function customerList(data) {
  return fetch({
    url: 'api/customerOrder/dict/customerList',
    method: 'post',
    data
  });
}

//缺省子部件列表
//api/customerOrder/dict/defaultSubPartList

export function defaultSubPartList(data) {
  return fetch({
    url: 'api/customerOrder/dict/defaultSubPartList',
    method: 'post',
    data
  });
}

//缺省部件列表
// api/customerOrder/dict/defaultPartList

export function defaultPartList(data) {
  return fetch({
    url: 'api/customerOrder/dict/defaultPartList',
    method: 'post',
    data
  });
}

//币种列表
// api/customerOrder/dict/coinTypeList

export function coinTypeList(data) {
  return fetch({
    url: 'api/customerOrder/dict/coinTypeList',
    method: 'post',
    data
  });
}

//顶出方式
//api/customerOrder/dict/outWayList

export function outWayList(data) {
  return fetch({
    url: 'api/customerOrder/dict/outWayList',
    method: 'post',
    data
  });
}


//水口形式
//api/customerOrder/dict/waterTypeList

export function waterTypeList(data) {
  return fetch({
    url: 'api/customerOrder/dict/waterTypeList',
    method: 'post',
    data
  });
}

//流道形式
// api/customerOrder/dict/runnerTypeList

export function runnerTypeList(data) {
  return fetch({
    url: 'api/customerOrder/dict/runnerTypeList',
    method: 'post',
    data
  });
}


// 客户列表  包含联系人信息
// /api/customerOrder/dict/cstCompanyList

export function cstCompanyList(data) {
  return fetch({
    url: 'api/customerOrder/dict/cstCompanyList',
    method: 'post',
    data
  });
}

export function invoiceType(data) {
  return fetch({
    url: '/api/dict/invoice_type',
    method: 'post',
    data
  });
}

export function querySup(data) {
  return fetch({
    url: '/api/pc/supplier/list',
    method: 'post',
    data
  });
}



// 根据物料查询供应商
export function querySupByMat(data) {
  return fetch({
    url: '/api/dict/query_supplier_by_mat',
    method: 'post',
    data
  });
}

//  根据供应商查物料
export function queryMatBySupplier(data) {
  return fetch({
    url: '/api/dict/query_mat_by_supplier',
    method: 'post',
    data
  });
}


//  模糊查询物料
export function queryMatByName(data) {
  return fetch({
    url: '/api/dict/search/matName',
    method: 'post',
    data
  });
}

//查询某部门及其子部门下所有成员列表
///

export function getAllDeptMember(data) {
  return fetch({
    url: 'api/company/getAllDeptMember',
    method: 'post',
    data
  });
}

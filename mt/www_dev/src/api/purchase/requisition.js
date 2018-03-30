/**
 * Created by zxf on 2017/11/21.
 */

import fetch from '@/utils/fetch'
//请购单相关

//请购单列表
export function requisitionList(data) {
  return fetch({
    url: '/api/pc/query/requestList',
    method: 'post',
    data
  });
}


// 请购单详情
export function requisitionDetail(data) {
  return fetch({
    url: '/api/pc/query/request',
    method: 'post',
    data
  });
}

//请购单删除
export function requisitionDel(data) {
  return fetch({
    url: '/api/pc/request/delete',
    method: 'post',
    data
  });
}

// 请购单行信息删除
export function requisitionLineDel(data) {
  return fetch({
    url: '/api/pc/request/deleteLine',
    method: 'post',
    data
  });
}
 //


// 参考历史请购单
export function requisitionRefer(data) {
  return fetch({
    url: '/api/pc/request/referHis',
    method: 'post',
    data
  });
}

// 新增修改请购单
export function requisitionModify(data) {
  return fetch({
    url: '/api/pc/request/modify',
    method: 'post',
    data
  });
}

// 新增修改请购单头信息
export function requisitionHeadModify(data) {
  return fetch({
    url: '/api/pc/request/modifyHead',
    method: 'post',
    data
  });
}

// 新增修改请购单行信息
export function requisitionLineModify(data) {
  return fetch({
    url: '/api/pc/request/modifyLine',
    method: 'post',
    data
  });
}


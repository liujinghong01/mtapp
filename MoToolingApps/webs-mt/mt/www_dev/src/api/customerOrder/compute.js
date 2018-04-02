/**
 * Created by zxf on 2017/10/26.
 *
 * 核算单页面接口列表
 */

import fetch from '@/utils/fetch';

export function list(data) {
  return fetch({
    url: 'api/customerOrder/compute/list',
    method: 'post',
    data
  });
}

export function save(data) {
  return fetch({
    url: 'api/customerOrder/compute/add',
    method: 'post',
    data
  });
}

export function detail(data) {
  return fetch({
    url: 'api/customerOrder/compute/mold/detail',
    method: 'post',
    data
  });
}


export function del(data) {
  return fetch({
    url: 'api/customerOrder/compute/delete',
    method: 'post',
    data
  });
}

//零部件修改/新增
// api/customerOrder/compute/part/modify

export function partModify(data) {
  return fetch({
    url: 'api/customerOrder/compute/part/modify',
    method: 'post',
    data
  });
}

//提交审批   /api/workflow/process

export function submitApprove(data) {
  return fetch({
    url: '/api/workflow/process',
    method: 'post',
    data
  });
}

// api/customerOrder/compute/getPartList

export function getPartList(data) {
  return fetch({
    url: 'api/customerOrder/compute/getPartList',
    method: 'post',
    data
  });
}


//api/customerOrder/compute/part/delete


export function partDel(data) {
  return fetch({
    url: 'api/customerOrder/compute/part/delete',
    method: 'post',
    data
  });
}



export function subPartDel(data) {
  return fetch({
    url: 'api/customerOrder/compute/part/sub/delete',
    method: 'post',
    data
  });
}

//api/customerOrder/compute/prd/delete

export function prdDel(data) {
  return fetch({
    url: 'api/customerOrder/compute/prd/delete',
    method: 'post',
    data
  });
}


//api/customerOrder/compute/process
export function addProcess(data) {
  return fetch({
    url: 'api/customerOrder/compute/process',
    method: 'post',
    data
  });
}

// api/customerOrder/compute/detail/process-detail

export function processList(data) {
  return fetch({
    url: 'api/customerOrder/compute/detail/process-detail',
    method: 'post',
    data
  });
}

// 参考历史核算单
// api/customerOrder/compute/associate


export function associate(data) {
  return fetch({
    url: 'api/customerOrder/compute/associate',
    method: 'post',
    data
  });
}



// 查询核算单主要零部件明细及其子部件
// api/customerOrder/accounting/detail/part

export function partDetail(data) {
  return fetch({
    url: 'api/customerOrder/accounting/detail/part',
    method: 'post',
    data
  });
}


// api/customerOrder/compute/processItemDel

export function processItemDel(data) {
  return fetch({
    url: 'api/customerOrder/compute/processItemDel',
    method: 'post',
    data
  });
}

/**
 * Created by zxf on 2017/11/30.
 */

import fetch from '@/utils/fetch'

//领料单列表
export function billList(data) {
  return fetch({
    url:'/api/iv/apply_mat_bill/list',
    method:'post',
    data
  })
}

//查看领料单详情
export function billDetail(data) {
  return fetch({
    url:'/api/iv/apply_mat_bill/detail',
    method:'post',
    data
  })
}

export function billModify(data) {
  return fetch({
    url:'/api/iv/apply_mat_bill/modify',
    method:'post',
    data
  })
}
//  审批 /api/workflow/process
export function approve(data) {
  return fetch({
    url:'/api/workflow/process',
    method:'post',
    data
  })
}

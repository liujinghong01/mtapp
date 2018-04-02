/**
 * Created by zxf on 2017/12/5.
 */
import fetch from '@/utils/fetch'

//查询盘点单列表
export function inventoryList(data) {
  return fetch({
    url:'/api/iv/store_check_bill/list',
    method:'post',
    data
  })
}

//查询盘点单详情
export function inventoryDetail(data) {
  return fetch({
    url:'/api/iv/store_check_bill/detail',
    method:'post',
    data
  })
}

//新增盘点单
export function inventoryAdd(data) {
  return fetch({
    url:'/api/iv/store_check_bill/add',
    method:'post',
    data
  })
}

//盘点单个物料
export function inventoryMat(data) {
  return fetch({
    url:'/api/iv/store_check_bill/modify',
    method:'post',
    data
  })
}




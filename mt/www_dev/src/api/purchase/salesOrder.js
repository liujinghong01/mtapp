/**
 * Created by zxf on 2017/11/28.
 */

import fetch from '@/utils/fetch'

export function orderList(data) {
  return fetch({
    url:'api/pc/salesOrder/list',
    method:'post',
    data
  })
}

export function orderDetail(data) {
  return fetch({
    url:'api/pc/purch_order/detail',
    method:'post',
    data
  })
}



export function createOrder(data) {
  return fetch({
    url:'api/salesOrder/createDeliveryOrder',
    method:'post',
    data
  })
}

import fetch from '@/utils/fetch'

//模糊查询单号（用于出入库）列表
export function billSearch(data) {
  return fetch({
    url:'/api/iv/bill/search',
    method:'post',
    data
  })
}
//查询入库基础数据  /api/iv/bill/query_in_store_init_data
export function initDatain(data) {
  return fetch({
    url:'/api/iv/bill/query_in_store_init_data',
    method:'post',
    data
  })
}
//提交数据入库 /api/iv/bill/in_store
export function submitStorage(data) {
  return fetch({
    url:'/api/iv/bill/in_store',
    method:'post',
    data
  })
}

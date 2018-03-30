import fetch from '@/utils/fetch'

//模糊查询单号（用于出入库）列表
export function billSearch(data) {
  return fetch({
    url:'/api/iv/bill/search',
    method:'post',
    data
  })
}
//查询出库基础数据   /api/iv/bill/query_out_store_init_data
export function initDataOut(data) {
  return fetch({
    url:'/api/iv/bill/query_out_store_init_data',
    method:'post',
    data
  })
}
//提交出库数据 /api/iv/bill/out_store  /api/iv/store_house/search_mat
export function submitStorageOut(data) {
  return fetch({
    url:'/api/iv/bill/out_store',
    method:'post',
    data
  })
}

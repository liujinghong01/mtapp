import fetch from '@/utils/fetch'

//模糊查询物料列表 /api/iv/store_room/search_mat
export function searchMat(data) {
  return fetch({
    url:'/api/iv/store_room/search_mat',
    method:'post',
    data
  })
}
//新建报废单  /api/iv/scrap_mat_bill/modify
export function scrapSubmit(data) {
  return fetch({
    url:'/api/iv/scrap_mat_bill/modify',
    method:'post',
    data
  })
}
//查询报废单列表 /api/iv/scrap_mat_bill/list
export function scrapList(data) {
  return fetch({
    url:'/api/iv/scrap_mat_bill/list',
    method:'post',
    data
  })
}
// 查询报废单详情 /api/iv/scrap_mat_bill/detail
export function getScrapDetail(data) {
  return fetch({
    url:'/api/iv/scrap_mat_bill/detail',
    method:'post',
    data
  })
}
//报废单执行报废 /api/iv/scrap_mat_bill/scrap
export function scrap(data) {
  return fetch({
    url:'/api/iv/scrap_mat_bill/scrap',
    method:'post',
    data
  })
}

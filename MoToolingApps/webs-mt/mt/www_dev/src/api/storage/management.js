import fetch from '@/utils/fetch'

//查询仓库列表 /api/iv/storehouse/list
export function storehouseList(data) {
  return fetch({
    url:'/api/iv/storehouse/list',
    method:'post',
    data
  })
}
//查询库位列表  /api/iv/storehouse/detail
export function storeRoomList(data) {
  return fetch({
    url:'/api/iv/storehouse/detail',
    method:'post',
    data
  })
}
// 查询库存物料
export function queryMats(data) {
  return fetch({
    url:'/api/iv/store_room/search_mat',
    method:'post',
    data
  })
}
//仓库物料管理提交 /api/iv/store_mat/direct_change
export function toragesManage(data) {
  return fetch({
    url:'/api/iv/store_mat/direct_change',
    method:'post',
    data
  })
}

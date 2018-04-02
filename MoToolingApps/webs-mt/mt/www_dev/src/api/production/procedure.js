import fetch from '@/utils/fetch'

//查询单位列表 api/customerOrder/dict/processCostItemUnitList
export function processCostItemUnitList(data) {
  return fetch({
    url:'api/customerOrder/dict/processCostItemUnitList',
    method:'post',
    data
  })
}
//工序新增修改 /api/pm/proc/modify
export function processModify(data) {
  return fetch({
    url:'/api/pm/proc/modify',
    method:'post',
    data
  })
}
//查询工序列表 /api/pm/process/list
export function processList(data) {
  return fetch({
    url:'/api/pm/proc/list',
    method:'post',
    data
  })
}
//删除工序 /api/pm/proc/delete
export function processDelete(data) {
  return fetch({
    url:'/api/pm/proc/delete',
    method:'post',
    data
  })
}
//工序详情  /api/pm/process/detail
export function processDetail(data) {
  return fetch({
    url:'/api/pm/proc/detail',
    method:'post',
    data
  })
}
//查询生产小组详情 /api/pm/pg/detail
export function pgDetail(data) {
  return fetch({
    url:'/api/pm/pg/detail',
    method:'post',
    data
  })
}





import fetch from '@/utils/fetch'
//未创建工艺路线列表(按BOM)   /api/pm/prod_req_bom/list
export function prod_req_bom_list(data) {
  return fetch({
    url:'/api/pm/prod_req_bom/list',
    method:'post',
    data
  })
}

//未创建工艺路线列表(按物料)  /api/pm/prod_req_mat/list
export function prod_req_mat_list(data) {
  return fetch({
    url:'/api/pm/prod_req_mat/list',
    method:'post',
    data
  })
}
//未创建工艺路线列表(按BOM层级nodeid) /api/pm/prod_req_bom_node/list
export function req_bom_node_list(data) {
  return fetch({
    url:'/api/pm/prod_req_bom_node/list',
    method:'post',
    data
  })
}
//未创建工艺路线需求  /api/pm/prod_req/info
export function prod_req_info(data) {
  return fetch({
    url:'/api/pm/prod_req/info',
    method:'post',
    data
  })
}
//查询工艺模板列表 /api/pm/routing_tmpl/list
export function tmplList(data) {
  return fetch({
    url:'/api/pm/routing_tmpl/list',
    method:'post',
    data
  })
}
////查询工艺模板详情  /api/pm/routing_tmpl/detail
export function tmplDetail(data) {
  return fetch({
    url:'/api/pm/routing_tmpl/detail',
    method:'post',
    data
  })
}
//工艺路线(生产定单)列表 /api/pm/prod_order/list
export function prodOrderList(data) {
  return fetch({
    url:'/api/pm/prod_order/list',
    method:'post',
    data
  })
}
//生产定单新增修改  /api/pm/prod_order/modify
export function prodOrderModify(data) {
  return fetch({
    url:'/api/pm/prod_order/modify',
    method:'post',
    data
  })
}
// 查询生产订单详情   /api/pm/prod_order/detail
export function prodOrderDetail(data) {
  return fetch({
    url:'/api/pm/prod_order/detail',
    method:'post',
    data
  })
}
//生产订单删除  /api/pm/prod_order/delete
export function prodOrderDelete(data) {
  return fetch({
    url:'/api/pm/prod_order/delete',
    method:'post',
    data
  })
}

//排产预览 /api/pm/schedule/start
export function scheduleStart(data) {
  return fetch({
    url:'/api/pm/schedule/start',
    method:'post',
    data
  })
}
//开始排产 /api/pm/po/generated
export function generated(data) {
  return fetch({
    url:'/api/pm/po/generated',
    method:'post',
    data
  })
}







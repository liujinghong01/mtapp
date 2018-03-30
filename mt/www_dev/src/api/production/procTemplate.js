import fetch from '@/utils/fetch'

//查询工序模板类型列表 /api/pm/routing_tmpl_type/list
export function tmplTypeList(data) {
  return fetch({
    url:'/api/pm/routing_tmpl_type/list',
    method:'post',
    data
  })
}
//模板类型删除   /api/pm/routing_tmpl_type/delete
export function tmplTypeDelete(data) {
  return fetch({
    url:'/api/pm/routing_tmpl_type/delete',
    method:'post',
    data
  })
}
//新增工序模板类型 /api/pm/routing_tmpl_type/edit
export function tmplTypeModify(data) {
  return fetch({
    url:'/api/pm/routing_tmpl_type/edit',
    method:'post',
    data
  })
}
//查询工序列表  /api/pm/proc/list
export function procList(data) {
  return fetch({
    url:'/api/pm/proc/list',
    method:'post',
    data
  })
}
//查询工艺模板详情 /api/pm/routing_tmpl/modify
export function tmplModify(data) {
  return fetch({
    url:'/api/pm/routing_tmpl/modify',
    method:'post',
    data
  })
}
//查询工艺模板详情 /api/pm/routing_tmpl/list
export function tmplList(data) {
  return fetch({
    url:'/api/pm/routing_tmpl/list',
    method:'post',
    data
  })
}
//工艺模板删除 /api/pm/routing_tmpl/delete
export function tmplDelete(data) {
  return fetch({
    url:'/api/pm/routing_tmpl/delete',
    method:'post',
    data
  })
}
//查询工艺模板详情 /api/pm/routing_tmpl/detail
export function tmplDetail(data) {
  return fetch({
    url:'/api/pm/routing_tmpl/detail',
    method:'post',
    data
  })
}






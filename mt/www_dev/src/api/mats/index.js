/**
 * Created by zxf on 2017/11/27.
 */

//物料相关接口
import fetch from '@/utils/fetch'
//bom列表
export function bomList(data) {
  return fetch({
    url: '/api/bom/list',
    method: 'post',
    data
  })
}
// 某bom下的一级节点列表
///api/bom_node/list
export function bomNodeList(data) {
  return fetch({
    url: 'api/bom_node/list',
    method: 'post',
    data
  })
}
//新建或修改Bom节点
// /api/bom_node/update
export function updateNodes(data) {
  return fetch({
    url: '/api/bom_node/update',
    method: 'post',
    data
  })
}
//物料类别列表
//  /api/cascade_type/list
export function cascadeType(data) {
  return fetch({
    url: '/api/cascade_type/list',
    method: 'post',
    data
  })
}
//物料名称列表
// /api/mat_name/list
export function mateName(data) {
  return fetch({
    url: '/api/mat_name/list',
    method: 'post',
    data
  })
}
//物料名称级联规格查询
//  /api/mat_name_spec/list
export function matNameSpec(data) {
  return fetch({
    url: '/api/mat_name_spec/list',
    method: 'post',
    data
  })
}
//物料类型级联名称查询
// /api/cascade_mat/list
export function cascadeMat(data) {
  return fetch({
    url: '/api/cascade_mat/list',
    method: 'post',
    data
  })
}
//材质列表
//  /api/rawnature/list
export function rawnatureList(data) {
  return fetch({
    url: '/api/rawnature/list',
    method: 'post',
    data
  })
}
//计划单价计算
// /api/rawnature/price
export function rawnaturePrice(data) {
  return fetch({
    url: '/api/rawnature/price',
    method: 'post',
    data
  })
}
//查询BOM节点详细信息
//  /api/bom_node/info
export function bomNodeInfo(data) {
  return fetch({
    url: '/api/bom_node/info',
    method: 'post',
    data
  })
}
//删除Bom节点
//  /api/bom_node/delete
export function bomNodeDelete(data) {
  return fetch({
    url: '/api/bom_node/delete',
    method: 'post',
    data
  })
}
//提交审批
//  /api/bom/app
export function commitBom(data) {
  return fetch({
    url: '/api/bom/app',
    method: 'post',
    data
  })
}




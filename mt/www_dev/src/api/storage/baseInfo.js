/**
 * Created by zxf on 2017/11/30.
 */

import fetch from '@/utils/fetch'


// 仓库新增修改
export function storageNew(data) {
  return fetch({
    url:'/api/iv/storehouse/modify',
    method:'post',
    data
  })
}

/// 查询仓库列表
export function storageList(data) {
  return fetch({
    url:'/api/iv/storehouse/list',
    method:'post',
    data
  })
}

// 查询仓库详情
export function storageDetail(data) {
  return fetch({
    url:'/api/iv/storehouse/detail',
    method:'post',
    data
  })
}

//删除仓库
export function storageDel(data) {
  return fetch({
    url:'/api/iv/storehouse/delete',
    method:'post',
    data
  })
}

//删除库位
export function storageRoomDel(data) {
  return fetch({
    url:'/api/iv/storeroom/delete',
    method:'post',
    data
  })
}


//查询仓库物料类型列表
export function storageMats(data) {
  return fetch({
    url:'/api/iv/mat_type/list',
    method:'post',
    data
  })
}


//查询仓库物料类型删除
export function storageMatDel(data) {
  return fetch({
    url:'/api/iv/mat_type/delete',
    method:'post',
    data
  })
}

//新增修改物料类型
export function storageMatModify(data) {
  return fetch({
    url:'/api/iv/mat_type/modify',
    method:'post',
    data
  })
}

// 查询库存物料 /api/iv/store_room/search_mat
export function queryMats(data) {
  return fetch({
    url:'/api/iv/store_room/search_mat',
    method:'post',
    data
  })
}
//   /api/iv/store/search_mat
export function storeSearchMat(data) {
  return fetch({
    url:'/api/iv/store/search_mat',
    method:'post',
    data
  })
}
//查询模具物料需求表 /api/pm/mold_mat_req/list
export function moldMatReq(data) {
  return fetch({
    url:'/api/pm/mold_mat_req/list',
    method:'post',
    data
  })
}
//查询物料列表 /api/bm_mat/all/mat_name
export function matNameList(data) {
  return fetch({
    url:'/api/bm_mat/all/mat_name',
    method:'post',
    data
  })
}
//获取仓管部所有员工  /api/bm_mat/all/mat_name
export function Allname(data) {
  return fetch({
    url:'/api/company/getAllDeptMember',
    method:'post',
    data
  })
}
//获取单位列表  api/customerOrder/dict/processCostItemUnitList
export function UnitList(data) {
  return fetch({
    url:'/api/customerOrder/dict/processCostItemUnitList',
    method:'post',
    data
  })
}
//获取材料列表 api/customerOrder/dict/rawNatureList
export function rawNatureList(data) {
  return fetch({
    url:'api/customerOrder/dict/rawNatureList',
    method:'post',
    data
  })
}
//新增修改物料基础数据 /api/iv/mat/modify
export function matModify(data) {
  return fetch({
    url:'/api/iv/mat/modify',
    method:'post',
    data
  })
}
//查询物料详情 /api/iv/select/mat/modify
export function getMatDetaile(data) {
  return fetch({
    url:'/api/iv/select/mat/modify',
    method:'post',
    data
  })
}
// 查询仓库列表 /api/iv/storehouse/list
export function storList(data) {
  return fetch({
    url:'/api/iv/storehouse/list',
    method:'post',
    data
  })
}
//查询枯萎列表  /api/iv/storehouse/detail
export function houseList(data) {
  return fetch({
    url:'/api/iv/storehouse/detail',
    method:'post',
    data
  })
}

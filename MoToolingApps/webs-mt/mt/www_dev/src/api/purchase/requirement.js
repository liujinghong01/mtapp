/**
 * Created by zxf on 2017/11/21.
 */

import fetch from '@/utils/fetch'
//物料需求相关接口

//需求列表
export function requirementList(data) {
  return fetch({
    url: '/api/pc/mat_require_plan/list',
    method: 'post',
    data
  });
}


///api/pc/mat_require_plan/detail
export function requirementDetail(data) {
  return fetch({
    url: 'api/pc/mat_require_plan/detail',
    method: 'post',
    data
  });
}

/// api/pc/mat_require_plan/delete
export function requirementDel(data) {
  return fetch({
    url: '/api/pc/mat_require_plan/delete',
    method: 'post',
    data
  });
}
//根据物料查询供应商 /api/dict/query_supplier_by_mat
export function querySupplier(data) {
  return fetch({
    url: '/api/dict/query_supplier_by_mat',
    method: 'post',
    data
  });
}


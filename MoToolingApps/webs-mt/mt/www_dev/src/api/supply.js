import fetch from '@/utils/fetch';

export function cstOrSupList(data) { //获取客户或供应商列表
  return fetch({
    url: '/api/cst-or-sup/list',
    method: 'post',
    data
  });
}

export function cstInfoView(data) { //获取客户资料详情
  return fetch({
    url: '/api/cst-info/view',
    method: 'post',
    data: data
  });
}

export function supInfoView(data) { //获取供应商资料详情
  return fetch({
    url: '/api/sup-info/view',
    method: 'post',
    data: data
  });
}

export function cstInfoUpdate(data) { //修改客户资料
  return fetch({
    url: '/api/cst-info/update',
    method: 'post',
    data: data
  });
}

export function supInfoUpdate(data) { //修改供应商资料
  return fetch({
    url: '/api/sup-info/update',
    method: 'post',
    data: data
  });
}

export function cstSupDelete(data) { //删除企业的客户或供应商
  return fetch({
    url: '/api/cst_sup/delete',
    method: 'post',
    data: data
  });
}

export function cstSupSearch(data) { //搜索企业的客户或供应商
  return fetch({
    url: '/api/cst-sup/search',
    method: 'post',
    data: data
  });
}

export function comUserSearch(data) { //搜索企业或平台用户信息
  return fetch({
    url: '/api/com-user/search',
    method: 'post',
    data: data
  });
}

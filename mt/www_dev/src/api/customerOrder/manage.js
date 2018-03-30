/**
 * Created by zxf on 2017/10/26.
 *
 * 核算单页面接口列表
 */

import fetch from '@/utils/fetch';


//客户定单列表
// /api/cst_order/order_startup_list
export function list(data) {
  return fetch({
    url: 'api/cst_order/order_startup_list',
    method: 'post',
    data
  });
}

//定单下达
// /api/cst_order/order_startup
export function startup(data) {
  return fetch({
    url: 'api/cst_order/order_startup',
    method: 'post',
    data
  });
}



//定单反完结
//  api/cst_order/order_not_end

export function restart(data) {
  return fetch({
    url: 'api/cst_order/order_not_end',
    method: 'post',
    data
  });
}




// 定单重新开始
export function start(data) {
  return fetch({
    url: 'api/cst_order/order_restartup',
    method: 'post',
    data
  });
}


//客户定单 操作 暂停/完结
// /api/cst_order/order_startup_stop

export function manage(data) {
  return fetch({
    url: 'api/cst_order/order_startup_stop',
    method: 'post',
    data
  });
}



import fetch from '@/utils/fetch'
//查询凭证管理列表  /api/iv/voucher/list
export function voucherList(data) {
  return fetch({
    url:'/api/iv/voucher/list',
    method:'post',
    data
  })
}
// /api/iv/voucher/detail
export function voucherDetail(data) {
  return fetch({
    url:'/api/iv/voucher/detail',
    method:'post',
    data
  })
}

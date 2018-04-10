/**
 * Created by zxf on 2017/11/22.
 */


import fetch from '@/utils/fetch'
//询价单相关


//供应商询价单反馈单列表
export function inquiryFeedbackList(data) {
  return fetch({
    url: '/api/pc/sup_inquiry_feedback/list',
    method: 'post',
    data
  });
}


//供应商询价单反馈单详情
export function supInquiryFeedbackDetail(data) {
  return fetch({
    url: '/api/pc/sup_inquiry_feedback/detail',
    method: 'post',
    data
  });
}


//待处理反馈单列表
export function inquiryList(data) {
  return fetch({
    url: '/api/pc/inquiry_deal/list',
    method: 'post',
    data
  });
}


//查询询价反馈单详情
export function inquiryFeedbackDetail(data) {
  return fetch({
    url: '/api/pc/inquiry_feedback/detail',
    method: 'post',
    data
  });
}


//询价单详情
export function inquiryDetail(data) {
  return fetch({
    url: '/api/pc/inquiry/detail',
    method: 'post',
    data
  });
}


//发送/创建询价单
export function sendInquiry(data) {
  return fetch({
    url: '/api/pc/inquiry/send',
    method: 'post',
    data
  });
}

// 提交询价单反馈
export function submitFeddback(data) {
  return fetch({
    url: '/api/pc/sup_inquiry_feedback/submit',
    method: 'post',
    data
  });
}
//查询公司详情 api/company/get-company-profile
export function getCompany(data) {
  return fetch({
    url: 'api/company/get-company-profile',
    method: 'post',
    data
  });
}


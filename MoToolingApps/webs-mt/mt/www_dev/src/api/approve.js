/**
 * Created by zxf on 2017/12/7.
 */
import fetch from '@/utils/fetch';

export function submitApprove(data) {
  return fetch({
    url: '/api/workflow/process',
    method: 'post',
    data
  });
}
//approveSuggests 审批意见
export function approveSuggests(data) {
  return fetch({
    url: '/api/workflow/show_opinion',
    method: 'post',
    data
  });
}

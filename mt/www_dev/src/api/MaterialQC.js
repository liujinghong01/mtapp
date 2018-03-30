/**
 * Created by zxf on 2017/12/7.
 */
import fetch from '@/utils/fetch';

//来料质检列表 /api/qm/purch_qc/list
export function purch_qc_list(data) {
  return fetch({
    url: '/api/qm/purch_qc/list',
    method: 'post',
    data
  });
}
// 查询待质检来料详情  /api/qm/purch_qc_stay/detail
export function purch_qc_stay(data) {
  return fetch({
    url: '/api/qm/purch_qc_stay/detail',
    method: 'post',
    data
  });
}
//供应商列表 /api/pc/supplier/list
export function supplierList(data) {
  return fetch({
    url: '/api/pc/supplier/list',
    method: 'post',
    data
  });
}
//新增修改来料质检单 /api/qm/purch_qc_bill/modify
export function mat_qc_modify(data) {
  return fetch({
    url: '/api/qm/purch_qc_bill/modify',
    method: 'post',
    data
  });
}
//查询来料质检详情  /api/qm/purch_qc/detail
export function purch_qc_detaile(data) {
  return fetch({
    url: '/api/qm/purch_qc/detail',
    method: 'post',
    data
  });
}
//删除来料质检单 /api/qm/purch_qc_bill/delete
export function purch_qc_delete(data) {
  return fetch({
    url: '/api/qm/purch_qc_bill/delete',
    method: 'post',
    data
  });
}
//提交审批、退回  /api/workflow/process
export function purch_qc_process(data) {
  return fetch({
    url: '/api/workflow/process',
    method: 'post',
    data
  });
}
//工序质检列表 /api/qm/proc_qc/list
export function procedure_qc_list(data) {
  return fetch({
    url: '/api/qm/proc_qc/list',
    method: 'post',
    data
  });
}
//查询待质检的工序信息 /api/qm/proc_qc_pqi/list
export function procedure_new_list(data) {
  return fetch({
    url: '/api/qm/proc_qc_pqi/list',
    method: 'post',
    data
  });
}
//查询工序列表 /api/pm/proc/list
export function procList(data) {
  return fetch({
    url: '/api/pm/proc/list',
    method: 'post',
    data
  });
}
//查看审批意见  /api/workflow/show_opinion
export function workflowOpinion(data) {
  return fetch({
    url: '/api/workflow/show_opinion',
    method: 'post',
    data
  });
}
//新增修改工序质检单 /api/qm/proc_qc/modify
export function proc_qc_modify(data) {
  return fetch({
    url: '/api/qm/proc_qc/modify',
    method: 'post',
    data
  });
}
//查询工序质检详情  /api/qm/proc_qc/detail
export function proc_qc_detail(data) {
  return fetch({
    url: '/api/qm/proc_qc/detail',
    method: 'post',
    data
  });
}



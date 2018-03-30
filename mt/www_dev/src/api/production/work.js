import fetch from '@/utils/fetch'
//查询任务资源列表  /api/pm/pop/resource_list
export function resourceList(data) {
  return fetch({
    url:'/api/pm/pop/resource_list',
    method:'post',
    data
  })
}
//查询任务领取列表   /api/pm/pop/incept_list
export function inceptList(data) {
  return fetch({
    url:'/api/pm/pop/incept_list',
    method:'post',
    data
  })
}
//任务领取，取消 /api/pm/pop/incept
export function incept(data) {
  return fetch({
    url:'/api/pm/pop/incept',
    method:'post',
    data
  })
}

//查询小组报工列表  /api/pm/pop/confirm_list
export function confirmList(data) {
  return fetch({
    url:'/api/pm/pop/confirm_list',
    method:'post',
    data
  })
}

//查询设备或人员的待加工、加工中任务列表  /api/pm/pop/wait_proc_list
export function waitProcList(data) {
  return fetch({
    url:'/api/pm/pop/wait_proc_list',
    method:'post',
    data
  })
}
//查询生产小组详情  /api/pm/pg/detail
export function pgDetail(data) {
  return fetch({
    url:'/api/pm/pg/detail',
    method:'post',
    data
  })
}
//为加工任务指派资源  /api/pm/pop/allocate
export function popAllocate(data) {
  return fetch({
    url:'/api/pm/pop/allocate',
    method:'post',
    data
  })
}
//查询设备或人员的加工中任务信息  /api/pm/pop/proc_info
export function popProcInfo(data) {
  return fetch({
    url:'/api/pm/pop/proc_info',
    method:'post',
    data
  })
}
//更改任务的加工状态（报工)   /api/pm/pop/change_status
export function popChangeStateus(data) {
  return fetch({
    url:'/api/pm/pop/change_status',
    method:'post',
    data
  })
}






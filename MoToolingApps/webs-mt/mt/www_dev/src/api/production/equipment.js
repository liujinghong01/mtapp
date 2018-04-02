import fetch from '@/utils/fetch'

//查询生产小组详情 /api/pm/pg/detail
export function pgDetail(data) {
  return fetch({
    url:'/api/pm/pg/detail',
    method:'post',
    data
  })
}
//查询生产小组列表 /api/pm/pg/list
export function pgList(data) {
  return fetch({
    url:'/api/pm/pg/list',
    method:'post',
    data
  })
}
//查询设备列表 /api/pm/pg_device/list
export function deviceList(data) {
  return fetch({
    url:'/api/pm/pg_device/list',
    method:'post',
    data
  })
}
//查询设备当前检修信息  /api/pm/device_maintain_curr/query
export function deviceMaintainInfo(data) {
  return fetch({
    url:'/api/pm/device_maintain_curr/query',
    method:'post',
    data
  })
}

//查询设备检修记录  /api/pm/pg_device/maintain_record
export function maintenanceRecord(data) {
  return fetch({
    url:'/api/pm/pg_device/maintain_record',
    method:'post',
    data
  })
}
//查询设备点检分配表  /api/pm/pg_device_check_allocate/list
export function deviceCheckAllocate(data) {
  return fetch({
    url:'/api/pm/pg_device_check_allocate/list',
    method:'post',
    data
  })
}
//设备点检分配删除  /api/pm/pg_device/check_allocate_delete
export function checkAllocateDelete(data) {
  return fetch({
    url:'/api/pm/pg_device/check_allocate_delete',
    method:'post',
    data
  })
}
//查询设备点检   /api/pm/pg_device_check/list
export function deviceCheck(data) {
  return fetch({
    url:'/api/pm/pg_device_check/list',
    method:'post',
    data
  })
}
//查询设备详情  /api/pm/pg_device/detail
export function deviceDetail(data) {
  return fetch({
    url:'/api/pm/pg_device/detail',
    method:'post',
    data
  })
}


//生产小组删除  /api/pm/pg/delete
export function pgDelete(data) {
  return fetch({
    url:'/api/pm/pg/delete',
    method:'post',
    data
  })
}
//生产小组新增修改   /api/pm/pg/modify
export function pgModify(data) {
  return fetch({
    url:'/api/pm/pg/modify',
    method:'post',
    data
  })
}
//设备删除    /api/pm/pg_device/delete
export function pgDeviceDelete(data) {
  return fetch({
    url:'/api/pm/pg_device/delete',
    method:'post',
    data
  })
}
//设备新增修改    /api/pm/pg_device/modify
export function pgDeviceModify(data) {
  return fetch({
    url:'/api/pm/pg_device/modify',
    method:'post',
    data
  })
}
//设备检修单    /api/pm/device_maintain_bill/save
export function deviceMaintainBill(data) {
  return fetch({
    url:'/api/pm/device_maintain_bill/save',
    method:'post',
    data
  })
}
//设备检修开始     /api/pm/device_maintain/start
export function deviceMaintainStart(data) {
  return fetch({
    url:'/api/pm/device_maintain/start',
    method:'post',
    data
  })
}
//设备点检    /api/pm/pg_device/check
export function pgDeviceCheck(data) {
  return fetch({
    url:'/api/pm/pg_device/check',
    method:'post',
    data
  })
}
//设备点检分配   /api/pm/pg_device/check_allocate_modify
export function pgDeviceCheckAllocate(data) {
  return fetch({
    url:'/api/pm/pg_device/check_allocate_modify',
    method:'post',
    data
  })
}
//设备点检分配详情  /api/pm/pg_device_check_allocate/detail
export function pgDeviceCheckAllocateDetail(data) {
  return fetch({
    url:'/api/pm/pg_device_check_allocate/detail',
    method:'post',
    data
  })
}
// 查询某部门及其子部门全部成员列表 /api/company/getAllDeptMember
export function getAllDeptMember(data) {
  return fetch({
    url:'/api/company/getAllDeptMember',
    method:'post',
    data
  })
}
//生产小组成员删除  /api/pm/pg_member/delete
export function pgMemberDelete(data) {
  return fetch({
    url:'/api/pm/pg_member/delete',
    method:'post',
    data
  })
}
//生产小组成员新增，修改 /api/pm/pg_member/modify
export function pgMemberModify(data) {
  return fetch({
    url:'/api/pm/pg_member/modify',
    method:'post',
    data
  })
}
//查询某部门包括其所有子部门的全部在职成员 api/company/get-all-members
export function getAllMembers(data) {
  return fetch({
    url:'api/company/get-all-members',
    method:'post',
    data
  })
}




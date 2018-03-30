import fetch from '@/utils/fetch'

//查询班次列表 /api/pm/shift/list
export function shiftList(data) {
  return fetch({
    url:'/api/pm/shift/list',
    method:'post',
    data
  })
}
//查询日历列表  /api/pm/calendar/list
export function calendarList(data) {
  return fetch({
    url:'/api/pm/calendar/list',
    method:'post',
    data
  })
}
//日历删除  /api/pm/calendar/delete
export function calendarDelete(data) {
  return fetch({
    url:'/api/pm/calendar/delete',
    method:'post',
    data
  })
}
//日历新增修改  /api/pm/calendar/modify
export function calendarModify(data) {
  return fetch({
    url:'/api/pm/calendar/modify',
    method:'post',
    data
  })
}
//班次删除  /api/pm/shift/delete
export function shiftDelete(data) {
  return fetch({
    url:'/api/pm/shift/delete',
    method:'post',
    data
  })
}
//班次新增修改   /api/pm/shift/modify
export function shiftModify(data) {
  return fetch({
    url:'/api/pm/shift/modify',
    method:'post',
    data
  })
}
//班次详情  /api/pm/shift/detail
export function shiftDetail(data) {
  return fetch({
    url:'/api/pm/shift/detail',
    method:'post',
    data
  })
}
//查询日历详情 /api/pm/calendar/detail
export function calendarDetail(data) {
  return fetch({
    url:'/api/pm/calendar/detail',
    method:'post',
    data
  })
}


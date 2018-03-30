/**
 * Created by zxf on 2017/8/29.
 */
import fetch from '@/utils/fetch';






export function getPermission(data) {
  return fetch({
    url: '/api/get_user_permission',
    method: 'post',
    data
  });
}

export function companyProfile(data) {
  return fetch({
    url: '/api/company/company-profile',
    method: 'post',
    data
  });
}


//获取直接部门或子部门以及所在部门的人员
export function getDeptInfo(data) {
  return fetch({
    url: '/api/company/getDepartmentMember',
    method: 'post',
    data
  });
}

//获取公司基本信息
export function getCompanyProfile(data) {
  return fetch({
    url: 'api/company/get-company-profile',
    method: 'post',
    data
  });
}

//创建新部门
export function createDepartment(data) {
  return fetch({
    url: 'api/company/create-department',
    method: 'post',
    data
  });
}

//获取公司成员信息
export function getMemberInfo(data) {
  return fetch({
    url: 'api/com/member-info',
    method:'post',
    data
  })
}

//修改公司成员信息
export function updateMemberInfo(data) {
  return fetch({
    url: 'api/company/modify-memberinfo',
    method:'post',
    data
  })
}

//删除部门成员
export function deleteDepMembers(data) {
  return fetch({
    url: 'api/dep-user/delete',
    method:'post',
    data
  })
}

//获取部门及其子部门列表
export function getSubDeps(data) {
  return fetch({
    url: 'api/company/get-sub-deps',
    method:'post',
    data
  })
}

//批量移动员工部门
export function batchMoveMember(data) {
  return fetch({
    url: 'api/user-dep/update',
    method:'post',
    data
  })
}


//获取部门详情
export function getDeptDetail(data) {
  return fetch({
    url: 'api/company/department',
    method:'post',
    data
  })
}

//修改部门信息
export function updateDeptInfo(data) {
  return fetch({
    url: 'api/company/modify-deptinfo',
    method:'post',
    data
  })
}
// 查询某部门及其子部门全部成员列表  /api/company/getAllDeptMember
export function getAllDeptMember(data) {
  return fetch({
    url: '/api/company/get-all-members',
    method:'post',
    data
  })
}







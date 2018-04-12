import fetch from '@/utils/fetch';

export function login(data) {
  return fetch({
    url: '/api/user/login',
    method: 'post',
    data
  });
}

export function getSMSCode(data) {
  return fetch({
    url: '/api/user/get-check-code',
    method: 'post',
    data
  });
}

export function getEmailCode(data) {
  return fetch({
    url: '/api/user/get-email-check-code',
    method: 'post',
    data
  });
}


export function checkUserIsExist(type,user) {
  return fetch({
    url: '/api/user/check-is-exit',
    method: 'post',
    data: {
      type,
      user
    }
  });
}

export function regist(data) {
  return fetch({
    url: '/api/user/register',
    method: 'post',
    data
  });
}

export function getInfo(token,uid) {
  return fetch({
    url: '/api/user/get-user-info',
    method: 'post',
    data: {token,uid}
  });
}

export function logout() {
  return fetch({
    url: '/api/user/logout',
    method: 'post'
  });
}

export function checkIsEexit(data) {
  return fetch({
    url: '/api/user/check-is-exit',
    method: 'post',
    data
  });
}

export function sendPWDEmail(data) {
  return fetch({
    url: '/api/user/send-email',
    method: 'post',
    data
  });
}

export function modifyUserInfo(data) {
  return fetch({
    url: 'api/user/modify-user-info',
    method: 'post',
    data
  });
}


//api/user/wechat_first_login 第一次微信登录

export function bindWX(data) {
  return fetch({
    url: 'api/user/wechat_first_login',
    method: 'post',
    data
  });
}

//生成头像图片
//api/user/api/profile_picture
export function profilePicture(data) {
  return fetch({
    url: '/api/user/api/profile_picture',
    method: 'post',
    data
  });
}

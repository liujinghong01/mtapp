/**
 * Created by zxf on 2017/8/16.
 */

import axios from 'axios';
const WX = {};


WX.isInstalled = function (success,err) {
  return window.Wechat.isInstalled(function (install) {
    return install
  },function (error) {
    return false;
  })
}

WX.getauth = function () {
  return new Promise((resolve,reject) => {
    "use strict";
    if (!this.isInstalled){
      reject({error:'请先安装微信'})
    }
    let scope = 'snsapi_userinfo',state = '_'+(+new Date());
    window.Wechat.auth(scope,state,function (response) {
      axios.get('https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx48c8ae06e1aa4b5f&secret=02fdef3930bda35daa022e5a3bea6168&code='+response.code+'&grant_type=authorization_code').then(res=>{
        console.log(JSON.stringify(response))
        axios.get('https://api.weixin.qq.com/sns/userinfo?access_token='+res.data.access_token+'&openid='+res.data.openid).then(resData=>{
          resData.data.client_type = 3;
          resData.data.identity_type = 'wechat';
          console.log(JSON.stringify(resData.data))
          axios.post(process.env.BASE_API+'/api/user/login',resData.data).then(responseData=>{
            if (responseData.data.reasonCode === '50001') {
              //表示该微信第一次登录，需要提示用户绑定原有账号
              $router.forward({path:'/bindWX',query:{wechat:resData.data}})
            }else {
              console.log(JSON.stringify(responseData))
              resolve(responseData.data.data)
            }
          }).catch(error=>{
            reject({error:'登录Motooling失败'})
            // return {error:'登录失败'}
          })
        }).catch(error=>{
          // return {error:'获取用户信息失败'}
          reject({error:'获取微信用户信息失败'})
        })
      }).catch(error=>{
        // return {error:'获取token失败'}
        reject({error:'获取微信token失败'})
      })
    },function (error) {
      // return {error:error}
      reject({error:'error'})
    })
  })
}

WX.shareLink = function (obj,success,fail) {
  window.Wechat.share({
    message: {
      title: obj.title,
      description: obj.description,
      thumb: "../assets/login_images/logo.png",
      media: {
        type: window.Wechat.Type.WEBPAGE,
        webpageUrl: obj.url
      }
    },
    scene: Wechat.Scene.SESSION   // share to Timeline
  }, function (msg) {
    success("Success");
  }, function (reason) {
    fail(reason)
  });
}


export default WX;

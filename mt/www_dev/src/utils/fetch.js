import axios from 'axios';
import store from '../store';


// 创建axios实例 基于promise实现的ajax库

const service = axios.create({
  baseURL: process.env.BASE_API, // api的base_url
  timeout: 5000                  // 请求超时时间
});

// if(store.getters.chosenCompany){
//   service.baseURL = store.getters.baseURL
// }else{
//   service.baseURL = process.env.BASE_API
// }

// request拦截器
service.interceptors.request.use(config => {
  // $loading.show('')
  if(config.params){ //get
    config.params.client_type = 2;
  }else{ //post
    config.data.client_type = 2;
  }
  config.headers['content-Type'] = 'application/json'
  if (store.getters.token) {
    //登录成功之后的请示都带token 以及 uid
    config.headers['x-access-token'] =  store.getters.token;
    if(config.params){
      config.params.token = store.getters.token;
      config.params.uid = store.getters.uid;
    }else{
      config.data.token = store.getters.token;
      config.data.uid = store.getters.uid;
    }
  }
  if(store.getters.chosenCompany){
    let company = store.getters.chosenCompany
    if(config.params){
      config.params.company_id = company.company_id
      config.params.company_name = company.company_name
    }else{
      config.data.company_name = company.company_name
      config.data.company_id = company.company_id
    }
  }
  console.log(config);
  // $loading.show()
  return config;
}, error => {
  // Do something with request error
  console.log(error); // for debug
  Promise.reject(error);
})

// respone拦截器
service.interceptors.response.use(
  response => {

    // 请求本身 404 500 request
    // 业务响应 对/错
    // $loading.hide()
  /**
  * code为非00000是抛错 可结合自己业务进行修改
  */
  // $loading.hide()
    console.log(JSON.stringify(response))
    const res = response.data;
    if (res.reasonCode !== '00000' && res.reasonCode!=='10020') {   //10020是校验手机号或者邮箱是否存在的的返回值
      // 50008:非法的token; 50012:其他客户端登录了;  50014:Token 过期了;
      if (res.reasonCode === '50000' || res.reasonCode === '50012' || res.reasonCode === '50014') {
        $toast.show('您的登录信息已过期').then(
          store.dispatch('FedLogOut').then(() => {
            location.reload();// 为了重新实例化vue-router对象 避免bug
          })
        )
      }
      if (res.httpCode === '403') {
        // $toast.show('权限不足，请联系管理员',2000)
        res.description = '权限不足，请联系管理员'
      }
      return Promise.reject(res);
    } else {
      return res.data;
    }
  },
  error => {
    $loading.hide()
    console.log('err' + error);// for debug
    return Promise.reject({description:'网络异常，请稍后重试'});
  }
)

export default service;

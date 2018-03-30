import { login, logout, getInfo } from '@/api/login';

import { getPermission } from '@/api/company'


const user = {
  state: {
    token: $storage.get('token'),   //当前登录用户的token
    uid: $storage.get('uid'),       //当前登录用户的uid
    userinfo:JSON.parse($storage.get('userinfo')),//{"result":"success","reasonCode":0,"uid":"10","userRole":null,"description":"登录成功","token":"5n0kzc3xrlculmt93n2qein1fa1xojqy","timestamp":"2017-08-21 03:56:18"}
    chosenCompany:$storage.get('chosenCompany')
  },
  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token;
      $storage.set('token',token)
    },
    SET_UID:(state, uid) =>{
      "use strict";
      state.uid = uid;
      $storage.set('uid',uid);
    },
    SET_NAME: (state, name) => {
      state.name = name;
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar;
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles;
    },
    SET_USERINFO:(state, userinfo) => {
      "use strict";
      console.log('SET_USERINFO');
      $storage.set('userinfo',JSON.stringify(userinfo));
      state.userinfo = userinfo;
    },
    SET_COMPANY:(state,company) => {
      //当前用户所选择的公司
      $storage.set('chosenCompany',company)
      state.chosenCompany = company;

    }
  },

  actions: {

    setUserinfo({ commit},obj){
      commit('SET_USERINFO',obj);
    },
    setTokenAndUid({ commit},obj){
      commit('SET_TOKEN', obj.token);
      commit('SET_UID', obj.uid);
    },

    //保存公司信息
    setCompany({ commit },company){
      commit('SET_COMPANY',company);

    },

    // 登录
    Login({ commit }, userInfo) {
      return new Promise((resolve, reject) => {
        login(userInfo).then(response => {
          commit('SET_TOKEN', response.token)
          commit('SET_UID',response.uid)
          resolve()
        }).catch(error => {
          reject(error);
        });
      });
    },
    //
    // //获取用户权限信息
    // GetUserPermission( { commit, state} ) {
    //   return new Promise( (resolve, reject) => {
    //     getInfo(state.token,state.uid).then(response => {
    //       commit('SET_USERINFO',response);
    //       resolve(response)
    //     }).catch(error => {
    //       reject(error)
    //     })
    //   })
    // },

    // 获取用户信息
    GetInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        getInfo(state.token,state.uid).then(response => {
          commit('SET_USERINFO',response);
          resolve(response);
        }).catch(error => {
          reject(error);
        });
      });
    },

    // 登出
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          commit('SET_TOKEN', '');
          commit('SET_ROLES', []);
          removeToken();
          resolve();
        }).catch(error => {
          reject(error);
        });
      });
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '');
        commit('SET_UID', '');
        commit('SET_USERINFO', '');
        $storage.remove('userinfo')
        $storage.remove('token');
        $storage.remove('uid');
        resolve();
      });
    }
  }
};

export default user;

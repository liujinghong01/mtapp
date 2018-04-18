import Vue from 'vue';
import VueRouter from 'vue-router';

Vue.use(VueRouter);


import Vonic from 'vonic';
Vue.use(Vonic)

import routes from './router';
import store from './store';

import { en , zh } from '../static/lang/local'

import sess from '@/utils/sess'

import './assets/iconfont/iconfont.css'


import '@/styles/index.scss';

import vuexI18n from 'vuex-i18n'

import echarts from 'echarts'
Vue.prototype.$echarts = echarts

Vue.use(vuexI18n.plugin,store);
// Vue.use(iconSvg)

if(navigator){
  let lan = navigator.language.toLowerCase()
  switch (lan){
    case 'zh-cn': Vue.i18n.add('zh',zh()); Vue.i18n.set('zh');break;
    case 'en-us': Vue.i18n.add('en',en());Vue.i18n.set('en'); break;
    default: Vue.i18n.add('zh',zh());Vue.i18n.set('zh');
  }
}

const beforeEach = function (to,from,next) {

  //解决安卓返回键以及浏览器的后退键 行为与左上角返回按扭不一致的问题
  const toRoute = to.path
  const fromRoute = from.path
  const scrollTop = Vonic.app.pageContentScrollTop()
  let h = sess.get(toRoute)
  if (h && h.history) {
    Vonic.app.nextDirection('back')
    h.history = false
    sess.remove(toRoute)
    store.commit('POP')
  } else {
    sess.set(fromRoute || '/', {
      history: true,
      scrollTop: scrollTop
    })
    store.commit('PUSH',to.name)
    Vonic.app.nextDirection('forward')
  }
  /*1、每次路由跳转之前，要先本次跳转是否需要登录
   2、看是否已登录
   3、登录则根据当前用户的权限判断是否可以访问当前页面
   */
  console.log('to'+ to.path)
  if ($storage.get('token')) {
    if (to.path === '/') {
      next({ path: '/home' });
    } else {
      if (!store.getters.userinfo) {
        store.dispatch('GetInfo').then(res => {
          const company = res.company;
          console.log('company'+company)
          if(company&&company.length>0){
            //跳转到选择公司页面
            if(company.length===1){
              that.$store.dispatch('setCompany',company[0])
              $router.forward('/home');
            }else{
              sess.set('companys',company);
              $router.forward('/chooseCompany');
            }
          }else{
            console.log('company is null')
            this.$store.dispatch('setCompany','');
            $router.forward('/home');
          }
          next({...to})
        })
      } else {
        next();
      }
      // next();
    }
  } else {
    if (to.meta.withoutLogin) {
      next()
    } else {
      next('/hello');
    }
  }
}
const afterEach = (toRoute, fromRoute) => {

  const to = toRoute.path
  const from = fromRoute.path
  // [Custom Business] Never use history scrollTop when '/' => '/home'
  if (from == '/' && to == '/home') return

  const h = sess.get(to)
  if (h && h.scrollTop) {
    Vue.nextTick(() => {
      Vonic.app.pageContentScrollTop(h.scrollTop)
    })
  }
}

Vue.mixin({
  beforeRouteLeave(to,from,next){
    //注册全局混合方法，由于所有页面都进行了缓存，页面返回时将当前页面销毁
    const toRoute = to.path
    const fromRoute = from.path
    const h = sess.get(toRoute);

    if(h&&h.history){
      console.log('distory'+toRoute)
     this.$destroy()
    }
    next();
  }
})

Vonic.app.setConfig('beforeEach',beforeEach);
Vonic.app.setConfig('afterEach',afterEach);

Vue.use(Vonic.app, {
  routes,
  store,
  pageTransition: 'ios',
  // pushMethod: '',
})

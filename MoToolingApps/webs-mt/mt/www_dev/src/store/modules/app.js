
import storage from '@/utils/storage'
const app = {
  state: {
    activeIndex:0,
    hideNavbar:true,
    baseURL:storage.get('baseurl'),
    cacheRoutes:[]
  },
  mutations: {
    PUSH:(state,to)=>{
      state.cacheRoutes.push(to)
    },
    POP: (state,from) =>{
      state.cacheRoutes.pop()
    },
    CHANGE_ACTIVE:(state,index)=>{
      state.activeIndex = index
    },
    HIDE_NAVBAR:(state,show)=>{
      state.hideNavbar = show
    },
    CHANGE_BASEURL:(state,url)=>{
      state.baseURL = url
      storage.set('baseurl',url)
    }
  },
  actions: {
    changIndex: ({ commit},index) => {
      commit('CHANGE_ACTIVE',index)
    },
    hideNavbar: ({ commit},show) => {
      commit('HIDE_NAVBAR',show)
    },
    changeBasurl: ({ commit},url) => {
      commit('CHANGE_BASEURL',url)
    },
  }
};

export default app;

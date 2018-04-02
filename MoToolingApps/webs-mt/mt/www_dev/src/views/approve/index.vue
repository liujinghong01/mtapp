<template>
  <keep-alive :include="routers">
    <router-view>
    </router-view>
  </keep-alive>
</template>
<script>
  import sess from '@/utils/sess'
  export default{
    data(){
      return{
        transitionName:'slide-left'
      }
    },
    computed:{
      routers(){
        return this.$store.state.app.cacheRoutes
      }
    },
    watch:{
      '$route':function (to,from) {
        if(sess.get(to.path)){
          this.transitionName = 'slide-left'
        }else{
          this.transitionName = 'slide-right'
        }
      }
    }
  }
</script>

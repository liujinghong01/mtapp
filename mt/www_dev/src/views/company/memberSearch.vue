<template>
  <div class="mw-page page company" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>员工信息</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content">
          <div class="search">
            <search v-model="keywords" placeholder="输入关键字" :on-search="onSearch" :on-cancel="onCancel"></search>
          </div>
        </div>
    </div>
</template>
<script>
  import { mapGetters } from 'vuex'
  export default {
    name:'memberSearch',
    data(){
      return {
        keywords:'',
      }
    },
    computed:mapGetters([
      'userinfo',
      'hideNavbar'
    ]),
    methods: {
      onSearch(){
        console.log('on search')
      },
      onCancel(){
        console.log('on cancel')
        history.back()
      }
    },
  }
</script>
<style scoped type="text/css" lang="scss">
  .search{
    height: 60px;
    background-color: #416eb6;
  }

</style>

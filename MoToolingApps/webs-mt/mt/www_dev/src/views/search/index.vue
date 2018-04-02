<template>
  <div class="page" v-nav="{hideNavbar:true}">
    <div class="page-content" style="padding-top: 0">
      <div class="search">
        <search v-model="keywords" placeholder="输入关键字" :on-search="onSearch" :on-cancel="onCancel"></search>
      </div>
    </div>
  </div>
</template>
<script>
  import { mapGetters } from 'vuex'
  export default {

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
    watch:{
        'keywords':function (news) {
          console.log(this.keywords)
        }
    }
  }
</script>
<style scoped type="text/css" lang="scss">
  .search{
    height: 60px;
    background-color: #416eb6;
  }

</style>

<template>
    <div class="page has-navbar"
         v-nav="{
          title:'供应商搜索',
          showMenuButton:true,
          menuButtonText:'<i style='+'color:white'+' class=ion-plus-round></i>'}">
        <div class="page-content">
          <search
            v-model="searchVal"
            placeholder="搜索"
            :on-search="onSearch"
            :on-cancel="onCancel"
            cancelText="取消"
            style="background-color: white !important;"></search>
          <div class="logo">
            <img src="../../../../static/images/supply/supply_search.png"/>
          </div>
          <div class="tips">
            未找到您搜索的供应商资料，可以填写公司名进行邀请
          </div>
          <div class="input-company">
            <von-input
              type="text"
              v-model="companyName"
              placeholder="请输入公司名称">
            </von-input>
          </div>
          <md-button
            class="md-button button button-positive button-block"
            style="width: 80%; margin: 0 auto;">
            确认
          </md-button>
        </div>
    </div>
</template>
<script>
  export default {
    data(){
      return {
        searchVal:'',
        companyName:''
      }
    },
    methods:{
      onSearch(){
        console.log('搜索回调');
      },
      onCancel(){
        console.log('搜索取消回调');
      }
    }
  }
</script>
<style scoped lang="scss">
  .logo{
    margin: 50px auto 0 auto;
    text-align: center;
    width: 80%;
  }
  .logo img{
    width: 50%;
  }
  .tips{
    margin-top: 15px;
    margin-bottom: 40px;
    font-size: 10px;
    color: #ababab;
    text-align: center;
  }
  .input-company{
    margin: 0 auto 42px auto;
    width: 80%;
    text-align: center;
  }
</style>

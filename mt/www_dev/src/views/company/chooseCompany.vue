<template>
  <div class="mw-page page company" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>选择公司</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content">
      <div style="height: 30%"></div>
      <div class="app-content">
        <p>您注册有多个公司，请选择</p>
        <md-button class="button button-calm button-block "  v-for="item in companys" @click.native="chosen(item)">{{item.company_name}}</md-button>
      </div>
    </div>
  </div>
</template>
<script>
  import { companyProfile,getPermission } from '@/api/company'
  import sess from '@/utils/sess'
  export default{
      name:'chooseCompany',
    data(){
      return{
        companys:JSON.parse(sess.get('companys'))
      }
    },
    computed:{
    },
    methods:{
      //选择公司
     chosen(item){
       this.$store.dispatch('setCompany',item).then(res=>{
           getPermission({}).then().catch()
       });
       this.$router.forward('/home');
     }
    }
  }
</script>

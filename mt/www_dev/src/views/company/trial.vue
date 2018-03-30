<template>
  <div class="mw-page page" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>申请试用</span>
      <i></i>
    </div>
    <div class="mw-page-content">
      <div>
        <div class="logo" style="height:30%;text-align: center;padding-top: 8%;">
          <img src="static/images/login/logo.png" width="50%">
        </div>
        <div style="width: 80%;margin: 20% auto">
          <mw-input placeholder="请输入公司名称" v-model="company_name"></mw-input>
          <mw-input placeholder="请输入负责人的手机号" v-model="phone" style="margin-top: 8px"></mw-input>
          <md-button class="button  button-block" style="background-color: #416eb6;color: white" @click.native="tryNow">立即试用</md-button>
        </div>
        </div>
    </div>
  </div>
</template>
<script>
  import { companyProfile } from '@/api/company'
  import { validatePhone } from '@/utils/validate'
  import {getPermission} from '@/api/company'
  import MwInput from "../../components/mw-input";
    export default{
      components: {MwInput},
      name:'trialVersion',
      data(){
        return{
          company_name:'',
          phone:''
        }
      },
      methods:{
        tryNow(){
          const that = this;
          if(!that.company_name.trim()||that.company_name.length===0){
            $toast.show('请输入公司名称')
            return
          }
          if(!that.phone.trim()||that.phone.length===0){
            $toast.show('手机号不能为空')
            return
          }
          if (!validatePhone(this.phone)){
            $toast.show('请输入正确的手机号')
            return
          }
          const requestData = {
            company_name:that.company_name,
            phone:that.phone
          }
          companyProfile(requestData).then(res=>{
            console.log(res)
            this.$store.dispatch('setCompany',res).then(res=>{
              getPermission({}).then().catch()
            })
            history.go(-1)
          }).catch(error=>{
              console.log(error)
            $toast.show(error.description);return
          })
        }
      }
    }
</script>

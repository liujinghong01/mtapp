<template>
  <div class="mw-page page crm" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>修改{{setTitle}}</span>
      <i class="right" @click="menuClick">保存</i>
    </div>
    <div class="mw-page-content">

          <div v-if="param_edit.type === 'gender'">
            <von-radio :options="genderOptions" v-model="param_edit.val"></von-radio>
          </div>
          <div v-else>
            <mw-input :placeholder=setTitle v-model="param_edit.val" :autofocus="true"></mw-input>
          </div>
          <!--<div v-else-if="param_edit.type === 'mobile'">-->
            <!--<mw-input placeholder="请输入手机号" v-model="param_edit.val" :autofocus="true"></mw-input>-->
          <!--</div>-->
          <!--<div v-else-if="param_edit.type === 'email'">-->
            <!--<mw-input placeholder="请输入邮箱" v-model="param_edit.val" :autofocus="true"></mw-input>-->
          <!--</div>-->
          <!--<div v-else-if="type === 'zone'">-->
            <!--<von-input type="text" v-model="tempUserinfo.zone" :placeholder="tempUserinfo.zone?tempUserinfo.zone:'选择地区'" floatingLabel="true" label="地区"></von-input>-->
          <!--</div>-->
    </div>
  </div>
</template>
<script>
  import {mapGetters} from 'vuex'
  import { modifyUserInfo } from '@/api/login'
  import { validatePhone } from '@/utils/validate'
  import store from '@/store'
  import _ from 'lodash'
  import MwInput from "../../../components/mw-input";
  export default{
    components: {MwInput},
    name:'modifyUserinfo',
    data(){
      return {
//        type:this.$route.params.type,
//        param:this.$route.params.param,
        param:{},
        param_edit:{},
        tempUserinfo:{},
        tempUserinfo_edit:{},
        genderOptions:['未知',"男", "女"],
        title: ''
      }
    },
    created(){
      this.param = this.$route.query
      this.param_edit = JSON.parse(JSON.stringify(this.param))

//      if(!this.$route.params.param||this.$route.params.param.indexOf('null')>-1){
//        this.tempUserinfo[this.type] = '';
//      }else{
//
//        this.tempUserinfo[this.type] = this.type==='gender'?parseInt(this.$route.params.param):this.$route.params.param
//      }
    },
    computed:{
      setTitle(){
        let title = '';
        switch (this.param.type){
          case 'gender':{
            title = '性别' ;break
          }
          case 'username': title = '昵称';break;
          case 'mobile': title = '手机号';break;
          case 'email': title = '邮箱';break;
          case 'zone': title = '选择地区';break;
          case 'wechat': title = '微信';break;
          default: title = '昵称'
        }
        this.title = title
        return title
      },
//      ...mapGetters(['userinfo'])
    },
    methods:{
      menuClick(){
        const that = this;
        console.log('menuClick');
        if (this.param_edit.type !== 'gender') {
          if(!this.param_edit.val){
            $toast.show(this.title+'不能为空')
            return
          }
          if(this.param.type === 'mobile' && !validatePhone(parseInt(this.param_edit.val))){
            $toast.show(this.title+'不正确')
            return
          }
        }
        if (_.isEqual(this.param,this.param_edit)) {
          history.go(-1)
          return
        }

        let reqData = {}
        switch (this.param.type){
          case 'gender':{
            reqData.gender = this.param_edit.val
            break
          }
          case 'username':{
            reqData.username = this.param_edit.val
            break
          }
          case 'mobile':{
            reqData.mobile = this.param_edit.val
            break
          }
          case 'email':{
            reqData.email = this.param_edit.val
            break
          }
        }
        modifyUserInfo(reqData).then(res=>{
          this.$store.dispatch('GetInfo');
          history.back()
        }).catch(error=>{
          $toast.show(error.description)
        })
      }
    }
  }
</script>

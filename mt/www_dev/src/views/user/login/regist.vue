<template>
  <div class="mw-page page" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>{{$t('regist')}}</span>
      <i></i>
    </div>
    <div class="mw-page-content">
      <div>
        <div class="logo" style="height:30%;text-align: center;padding-top: 8%;">
          <img src="static/images/login/logo.png" width="50%">
        </div>
          <div style="width: 70%;margin: 0 auto" v-if="regist_type==='email'">
            <mw-input style="margin-top: 8px" class="register-login" inputType="email" v-model="email" :placeholder="$t('enter-email')" ref="von_input"></mw-input>
            <mw-input style="margin-top: 8px" class="register-login" inputType="password" placeholder="6-16位数字/字母/符号(_*@%&)" v-model="password" ></mw-input>
            <div style="background-color: white;display:flex;margin-top: 8px;">
              <mw-input style="flex: 1 1 auto" inputType="tel" class="checkCode register-login" :placeholder="$t('enter-sms-code')" v-model="checkCode"></mw-input>
              <div v-if="!isSentEmailCode"  class=" getCode" @click="validEmail()">{{$t('get-sms-code')}}</div>
              <div v-else="isSentEmailCode" class=" getCode" style="color: #c8c8c8">{{$t('get-sms-code-again')}}({{downCountEmail}})</div>
            </div>
          </div>
          <div style="display: inline;" v-else >
            <div class="width zoneCode" @click="changeZoneCode">
              <span >{{$t('region')}}</span>
              <span class="code">{{zone.zh}} ({{zone.code}})</span>
            </div>
            <mw-input style="margin-top: 8px" class="width register-login" inputType="tel" v-model="username" :placeholder="$t('enter-phone')" ref="von_input"></mw-input>
          </div>
          <div class="width" v-if="regist_type === 'phone_with_code'"
               style="display:flex;margin-top: 8px;background-color: white;border-radius: 5px;">
            <mw-input inputType="tel" class="checkCode register-login" :placeholder="$t('enter-sms-code')" v-model="checkCode" style="flex: 1 1 auto"></mw-input>
            <div v-if="!isSentCode"  class=" getCode" @click="checkPhoneIsExist">{{$t('get-sms-code')}}</div>
            <div v-else="isSentCode" class=" getCode" style="color: #c8c8c8">{{$t('get-sms-code-again')}}({{downCount}})</div>
          </div>
        <div class="width">
          <p class="regist_type" v-if="regist_type==='email'" @click="changeType('phone_with_code')">{{$t('regist-via-phone')}}</p>
          <p class="regist_type" v-else @click="changeType('email')">{{$t('regist-via-email')}}</p>
          <p class="regist_type right" @click="$router.forward('/login')">{{$t('login')}}</p>
          <md-button class="button button-positive button-block" style="background-color: #4161b6;" @click.native="checkUser">{{$t('regist')}}</md-button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import WX from '@/utils/WX'
  import sess from '@/utils/sess'
  import { regist } from '@/api/login'
  import { validateEmail,validatePWD,validIsNumber,validatePhone } from '@/utils/validate'
  import { getSMSCode,getEmailCode,checkIsEexit } from '@/api/login'
  import axios from 'axios'
  import MwInput from "../../../components/mw-input";

  //13129559455
  export default {
    name:'regist',
    components: {MwInput},
    data(){
      return {
        username:'',
        regist_type:'phone_with_code',  // 'email'  'phone_with_pwd'
        checkCode:'',
        password:'',
        pwd_repeat:'',
        email:'',
        interval:0 ,  //计时器
        isSentCode:false,
        isSentEmailCode:false,
        downCount:60,
        downCountEmail:60,
        zone:{
          zh:'中国',
          code:'+86',
          en:'China'
        }
      }
    },
    created(){
      if(sess.get('zoneCode')){
        this.zone = sess.get('zoneCode')
      }
    },
    destory(){
       if (this.interval) {
         clearInterval(this.interval)
       }
    },
    methods: {
      changeZoneCode(){
        console.log('zoneCode')
        $router.forward('/zoneCode')
      },
      changeType(type){
        this.regist_type = type;
        this.login_type = type
        this.username = ''
        this.email = ''
        this.password = ''
        this.checkCode = ''
        clearInterval(this.interval);
        this.downCount = 60
        this.isSentCode = false;
      },
      checkPhoneIsExist(){
        if (!this.username || this.username === '' || this.username.trim().length === 0) {
          $toast.show('手机号不能为空，请检查', 1000);
          return;
        }
        if (!this.validPhone()) {
          $toast.show('手机号格式有误，请检查')
          return;
        }
        const reqData = {
          identity_type:'mobile',
          user:this.username
        }
        checkIsEexit(reqData).then(res=>{
          $toast.show(this.username+'已经存在');
        }).catch(error=>{
          this.validPhone('sendCode')
        })
      },


      checkUser(){
        const that = this;
        if (this.login_type === 'email') {
          if (!that.email || that.email === '' || that.email.trim().length === 0) {
            $toast.show((that.login_type === 'email' ? '邮箱' : '手机号') + '不能为空，请检查', 1000);
            return;
          }
        } else {
          if (!that.username || that.username === '' || that.username.trim().length === 0) {
            $toast.show((that.login_type === 'email' ? '邮箱' : '手机号') + '不能为空，请检查', 1000);
            return;
          }
        }

        if(that.regist_type === 'phone_with_code'&&!validatePhone(that.username)){
          $toast.show('手机号码错误，请检查', 1000);
          return;
        }

        if(that.regist_type === 'email'&&!validateEmail(that.email)){
          $toast.show('邮箱格式有误，请检查', 1000);
          return;
        }

        if(that.regist_type === 'email' && (!that.password || that.password.trim().length === 0 || !validatePWD(that.password))){
          if(that.password.length < 6){
            $toast.show('密码长度至少6位')
            return;
          }else if(that.password.length > 20){
            $toast.show('密码长度最长20位')
            return;
          }else{
            $toast.show('密码格式有误，请检查', 1000);
            return;
          }
        }
        if(!that.checkCode || that.checkCode.trim().length===0){
          $toast.show('验证码不能为空，请检查', 1000);
          return;
        }
        if(!validIsNumber(this.checkCode)){
          $toast.show('验证码格式有误，请检查', 1000);
          return;
        }
        if(that.regist_type === 'email'){
          that.submitRegist();
        }else{
          this.validPhone('regist')
        }
      },
      sendCode(type){  //发送验证码
        const that = this;
        if(type === 'mobile'){
          const requestData = {
            country_code:that.zone.code.substr(1),
            mobile:that.username,
            type:0
          };
          getSMSCode(requestData).then( res=>{
            that.isSentCode = true;
            $toast.show('验证码已发送')
            this.interval = setInterval(function () {
              if(that.downCount>0){
                that.downCount -- ;
              }else{
                clearInterval(this.interval);
                that.downCount = 60
                that.isSentCode = false;
              }
            },1000)
          }).catch(error =>{
            $toast.show('网络繁忙，请稍后重试')
          })

        }else if(type === 'email'){
          let req_data = {
            email:this.email,
            type:0
          }
          getEmailCode(req_data)
            .then(res => {
              that.isSentEmailCode = true;
              $toast.show('验证码已发送至您的邮箱')
              this.interval = setInterval(function () {
                if(that.downCountEmail>0){
                  that.downCountEmail -- ;
                }else{
                  clearInterval(this.interval);
                  that.downCountEmail = 60
                  that.isSentEmailCode = false;
                }
              },1000)
            })
            .catch(err => {
              $toast.show('网络繁忙，请稍后重试')
            })
        }
      },

      submitRegist(){
        //TODO validate input
        const that = this;
        let reqData = {
          client_type:2,
          user:that.regist_type==='email'?this.email:this.username,
          password:that.password,
          regist_type:that.regist_type==='email'?'email':'mobile',
          code:that.checkCode
        }
        regist(reqData).then(response =>{
          console.log('regist success');
          let reqDataLogin = {
            user:that.regist_type==='email'?this.email:that.username,
            password:this.password,
            identity_type:that.regist_type==='email'?'email':'mobile',
            vericode:that.checkCode
          }
          console.log(reqDataLogin)
          that.$store.dispatch('Login',reqDataLogin).then(res =>{
            $toast.show('恭喜您，注册成功')
            that.$store.dispatch('GetInfo').then(res =>{
              console.log(res);
              const company = res.company;
              console.log('company'+company)
              if(company&&company.length>0){
                //跳转到选择公司页面
                if(company.length===1){
                  that.$store.dispatch('setCompany',company[0]);
                  $router.forward('/home');
                }else{
                  sess.set('companys',company);
                  $router.forward('/chooseCompany');
                }
              }else{
                console.log('company is null')
                that.$store.dispatch('setCompany','');
                $router.forward('/home');
              }

            }).catch(error=>{
              $toast.show('网络异常，请稍后重试!', 1000);
            })

          }).catch(error =>{
            console.log('login error');
          })
        }).catch(error =>{
          console.log('regist failure');

          $toast.show(error.description, 3000);
        })
      },
      validPhone(action){
        const that = this
        if(!that.username || that.username ===''){
          $toast.show('手机号不能为空，请检查', 1000);
          return;
        }
        //TODO 检查手机号是否正确   这里应该封装成一个方法
        let isOk = validatePhone(that.username)
        if (!isOk) {
          $toast.show('手机号不正确，请检查', 1000);
        }
        if(action==='regist'){
          if(isOk) {
            this.submitRegist()
          }
        }else if(action==='sendCode'){
          if(isOk){
            that.sendCode('mobile')
          }
        }else{
          return isOk
        }

//        axios.post('http://www.motooling.com:8080/motooling/api/msg/valid',{country_code:that.zone.code.substr(1),mobile:that.username}).then(res=>{
//          if(res.data.data.result_code === 1){
//            if(action==='regist'){
//              that.submitRegist()
//            }else if(action==='sendCode') {
//              that.sendCode()
//            }else {
//              return true
//            }
//          }else{
//            $toast.show('无效的手机号')
//            return false;
//          }
//        }).catch(error=>{
//          $toast.show('无效的手机号')
//          console.log('error'+error);
//          return false
//        })
      },
      validEmail(){
        if(!this.email || this.email ===''){
          $toast.show((this.regist_type === 'email'?'邮箱':'手机号')+'不能为空，请检查', 1000);
          return;
        }
        //TODO 检查邮箱是否正确
        let isOk = validateEmail(this.email)
        if(isOk){
          this.sendCode('email')
        }else{
          $toast.show('邮箱格式错误!',1000)
          return;
        }
      }
    },
  }
</script>

<style type='stylesheet/scss' lang='scss' scoped>
  .width{
    width:70%;
    margin: 0 auto;
    text-align: center;
  }
  .checkCode{
    width: 50%;
    padding: 0;
    float: left;
  }
  .regist_type{
    color: #416eb6;
    text-align: left;
    padding: 10px 2px;
    display: inline;
    float: left;
  }
  .right{
    text-align: right;
    float: right;
  }
  .getCode{
    width: 50%;
    float: right;
    background-color:transparent;
    color: #416eb6;
    line-height: 50px;
    height: 50px;
    box-shadow: none;
    font-size: 14px;
    text-align: center;
  }
  .zoneCode{
    height: 58px;line-height: 49px;text-align: left;
    span{
      padding-left: 15px;
      color: #9c9c9c;
    }
    .code{
      color: black;
    }
  }
</style>

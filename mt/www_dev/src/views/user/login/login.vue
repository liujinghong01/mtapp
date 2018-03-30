<template>
  <div class="mw-page page" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>{{$t('login')}}</span>
      <i></i>
    </div>
    <div class="mw-page-content">
      <div>
        <div class="logo" style="height:30%;text-align: center;padding-top: 8%;">
          <img src="static/images/login/logo.png" width="50%">
        </div>
        <!--非邮箱登录-->
        <div style="width: 70%;margin: 0 auto" v-if="login_type!=='email'">
          <div style="display: inline;">
            <div class="zoneCode" @click="changeZoneCode">
              <span>{{$t('region')}}</span>
              <span class="code">{{zone.zh}} ({{zone.code}})</span>
            </div>
          </div>
          <mw-input :placeholder="$t('enter-phone')" inputType="tel" v-model="username"></mw-input>
          <div style="margin-top: 5px">
            <mw-input :placeholder="$t('enter-sms-code')" inputType="tel" v-model="checkCode"
                      style="width: 50%;float:left"></mw-input>
            <div v-if="!isSentCode" class="getCode" @click="checkPhoneIsExist">{{$t('get-sms-code')}}</div>
            <div v-else="isSentCode" class="getCode" style="color: #bbbbbb">
              {{$t('get-sms-code-again')}}({{downCount}})
            </div>
          </div>
        </div>
        <!--邮箱登录-->
        <div style="width: 70%;margin: 0 auto" v-else>
          <div style="height: 49px"></div>
          <mw-input :placeholder="$t('enter-email')" inputType="email" v-model="email"></mw-input>
          <div style="margin-top: 5px">
            <mw-input :placeholder="$t('enter-password')" inputType="password" v-model="password"></mw-input>
          </div>
        </div>
        <div class="width">
          <p class="login_type" v-if="login_type==='email'" @click="changeType('phone_with_code')">{{$t('login-via-phone')}}</p>
          <p class="login_type" v-else @click="changeType('email')">{{$t('login-via-email')}}</p>
          <p class="login_type right" @click="$router.forward('/regist')">注册</p>
          <md-button class="button button-positive button-block" style="background-color: #4161b6;" @click.native="checkUser">{{$t('login')}}</md-button>
          <p style="margin-top: 15%;color: #416eb6" v-show="login_type==='email'" @click="findPassword"> 忘记密码?</p>
        </div>
      </div>
    </div>
  </div>
</template>
<script>

  import sess from '@/utils/sess'
  import {login, getSMSCode,checkIsEexit} from '@/api/login'
  import { getPermission } from '@/api/company'
  import {validateEmail, validIsNumber, validatePhone, validatePWD} from '@/utils/validate'
  import  axios  from 'axios'
  import MwInput from "../../../components/mw-input";

  export default {
    name: 'login',
    components: {MwInput},
    data(){
      return {
        username: '',
        password: '',
        email: '',
        phone: '',
        login_type: 'phone_with_code',
        checkCode: '',
        isSentCode: false,
        downCount: 60,   //倒计时
        interval: 0,   //计时器
        zone: {
          zh: '中国',
          code: '+86',
          en: 'China'
        }
      }
    },
    created(){
      if (sess.get('zoneCode')) {
        this.zone = sess.get('zoneCode')
      }
    },
    destory() {
      clearInterval(this.interval)
    },
    methods: {
      sendCode(){  //发送验证码
        const that = this;
        this.checkCode = '';
        const requestData = {
          country_code: that.zone.code.substr(1),
          mobile: this.username,
          type: 1
        };
        getSMSCode(requestData).then(res => {
          that.isSentCode = true;
          $toast.show('验证码已发送')
          this.interval = setInterval(function () {
            if (that.downCount > 0) {
              that.downCount--;
            } else {
              clearInterval(this.interval);
              that.downCount = 60
              that.isSentCode = false;
            }
          }, 1000)
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      findPassword(){ //找回密码
        var that = this;
        var btns = new Object();
//        btns[that.$t('find-pwd-via-mobile')] = () => {
//          $router.forward('/findPWD/mobile')
//        }
        btns[that.$t('find-pwd-via-email')] = () => {
          $router.forward('/findPWD/email')
        }
        $actionSheet.show({
          buttons: btns,
          title: that.$t('find-pwd'),
          cancelText: that.$t('cancel')
        })
      },
      selectCompany(company){
        console.log(company)
      },
      changeZoneCode(){
        $router.forward('/zoneCode')
      },
      checkPhoneIsExist(){
        console.log('checkPhoneIsExist')
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
          this.validPhone('sendCode')
        }).catch(error=>{
          $toast.show(this.username+'尚未注册');
        })
      },
      changeType(type){
        this.login_type = type
        this.username = ''
        this.email = ''
        this.password = ''
        this.checkCode = ''
        clearInterval(this.interval);
        this.downCount = 60
        this.isSentCode = false;
      },
      checkUser(){
        //TODO  验证手机号
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
        if (that.login_type === 'phone_with_code' && !this.validPhone()) {
          $toast.show('手机号有误，请检查')
          return;
        }
        if (that.login_type === 'phone_with_pwd' && (!that.password || that.password.trim().length === 0 || !validatePWD(that.password))) {
          $toast.show('密码有误，请检查', 1000);
          return;
        }
        if (that.login_type === 'phone_with_code' && that.checkCode.trim().length === 0) {
          $toast.show('验证码不能为空', 1000);
          return;
        }
        if (that.login_type === 'phone_with_code' && (!validIsNumber(this.checkCode))) {
          $toast.show('验证码有误，请检查', 1000);
          return;
        }
        if (that.login_type === 'email') {
          if (!validateEmail(that.email)) {
            $toast.show('邮箱格式有误，请检查', 1000);
            return;
          } else if (!that.password || that.password.trim() === '' || that.password.trim().length === 0 || !validatePWD(that.password)) {
            $toast.show('密码有误，请检查', 1000);
            return;
          } else {
            that.submitLogin();
          }
        } else {
          this.validPhone('login')
        }
      },
      submitLogin(){
        const that = this;
        let requestData = {
          user: that.login_type === 'email' ? that.email : that.username,
          identity_type: that.login_type === 'email' ? 'email' : 'mobile',
        };
        if (that.login_type === 'phone_with_code') {
          //如果是验证码登录
          requestData.vericode = that.checkCode
        } else {
          //如果是密码登录
          requestData.password = that.password
        }
        that.$store.dispatch('Login', requestData).then(response => {
          that.$store.dispatch('GetInfo').then(res => {
            console.log(res);
            const company = res.company;
            console.log('company' + company)
            if (company && company.length > 0) {
              //跳转到选择公司页面
              if (company.length === 1) {
                that.$store.dispatch('setCompany', company[0]).then(res=>{
                  getPermission({}).then().catch()
                })
                $router.forward('/home');
              } else {
                sess.set('companys', company)
                $router.forward('/chooseCompany');
              }
            } else {
              console.log('company is null')
              that.$store.dispatch('setCompany', '').then(res=>{
                getPermission({}).then().catch()
              });
              $router.forward('/home');
            }
          }).catch(error => {
            $toast.show('网络异常，请稍后重试!', 2000);
          })
        }).catch(error => {
          $toast.show(error.description, 2000);
        })
      },
      validPhone(action){
        const that = this
        if (!that.username || that.username === '') {
          $toast.show((that.login_type === 'email' ? '邮箱' : '手机号') + '不能为空，请检查', 1000);
          return;
        }
        //TODO 检查手机号是否正确   这里应该封装成一个方法
        let isOk = validatePhone(that.username)
        if (!isOk) {
          $toast.show('手机号不正确，请检查', 1000)
          return isOk
        }
        if (action === 'login') {
          if (isOk) {
            this.submitLogin()
          }
        } else if (action === 'sendCode') {
          if (isOk) {
            that.sendCode()
          }
        } else {
          return isOk
        }
        //18988754370   2,4,5,7,8,9,10,11,12
//        axios.post('http://www.motooling.com:8080/motooling/api/msg/valid',{country_code:that.zone.code.substr(1),mobile:that.username}).then(res=>{
//          if(res.data.data.result_code === 1){
//            if(action==='login'){
//              that.submitLogin();
//            }else if(action==='sendCode') {
//              that.sendCode()
//            }else {
//                return true
//            }
//
//          }else{
//            $toast.show('无效的手机号')
//            return false;
//          }
//        }).catch(error=>{
//          $toast.show('无效的手机号')
//          console.log('error'+error);
//          return false
//        })
      }
    }
  }
</script>

<style type='stylesheet/scss' lang='scss' scoped>
  .width {
    width: 70%;
    margin: 0 auto;
    text-align: center;
  }

  .checkCode {
    width: 50%;
    padding: 0;
    float: left;
  }

  .login_type {
    color: #416eb6;
    text-align: left;
    padding: 10px 2px;
    display: inline;
    float: left;
  }

  .right {
    text-align: right;
    float: right;
  }

  .getCode {
    width: 50%;
    float: right;
    background-color: white;
    color: #416eb6;
    line-height: 50px;
    height: 50px;
    text-align: center;
    box-shadow: none;
    font-size: 14px;
  }

  .zoneCode {
    height: 49px;
    line-height: 49px;
    text-align: left;
    span {
      padding-left: 15px;
      color: #9c9c9c;
    }
    .code {
      color: black;
    }
  }
</style>

<template>
    <div class="page has-navbar" v-nav="{title: title, showBackButton: true}">
      <div class="page-content text-center" style="height: 100%">

        <div >
          <div style="height:60%;width: 60%;z-index: 100;margin: 0 auto">
            <img src="static/images/login/logo.png" alt="" width="100%">
          </div>
          <div style="display: inline;" v-if="type==='email'">
            <div style="height: 49px"></div>
            <von-input style="margin-top: 8px" class="width" type="email" v-model="username" :placeholder="$t('enter-email')" ref="von_input"></von-input>
          </div>
          <div style="display: inline;" v-else >
            <div class="width zoneCode" @click="changeZoneCode">
              <span >{{$t('region')}}</span>
              <span class="code">{{zone.zh}} ({{zone.code}})</span>
            </div>
            <von-input style="margin-top: 8px" class="width" type="tel" v-model="username" :placeholder="$t('enter-phone')" ref="von_input"></von-input>
          </div>
        </div>
        <div class="width">
          <md-button class="button button-positive button-block" style="background-color: #4161b6;" @click.native="checkExit">{{$t('next')}}</md-button>
        </div>
      </div>
    </div>
</template>
<script>
  import {checkIsEexit,sendPWDEmail} from '@/api/login'

    export default{
      name:'findPWD',
      data(){
        return {
         type:'mobile',
          username:'',
          title:this.$t('find-pwd-via-mobile'),
          zone:{
            zh:'中国',
            code:'+86',
            en:'China'
          }
        }
      },
      created(){
        if(this.$route.path.indexOf('email')>-1){
          this.type = 'email';
          this.title = this.$t('find-pwd-via-email')
        }
      },
      methods:{
        changeZoneCode(){
          $router.forward('/zoneCode')
        },
        checkExit(){
          const that = this;
          const requestData = {
            'identity_type':that.type,
            'user':that.username
          }
          checkIsEexit(requestData).then(res=>{
            if(that.type==='email'){
              sendPWDEmail({'email':that.username}).then(res=>{
                $toast.show('验证邮件已发送至您的邮箱'+that.username+',请按提示进行密码重置操作',2000).then(res=>{
                  history.go(-1)
                })
              }).catch(error=>{
                $toast.show('网络异常，请稍后重试')
              })
            }else{
              sendPWDEmail({'m':that.username}).then(res=>{
                $toast.show('验证邮件已发送至您的邮箱'+that.username+',请按提示进行密码重置操作')
              }).catch(error=>{
                $toast.show('网络异常，请稍后重试')
              })
            }
          }).catch(error=>{
            $toast.show(that.username+'尚未注册');
          })
        }

      }
    }
</script>
<style scoped lang="scss">
  .width{
    width:70%;
    margin: 0 auto;
    text-align: center;
  }
  .zoneCode{
    height: 49px;line-height: 49px;text-align: left;
    span{
      padding-left: 15px;
      color: #9c9c9c;
    }
    .code{
      color: black;
    }
  }
</style>

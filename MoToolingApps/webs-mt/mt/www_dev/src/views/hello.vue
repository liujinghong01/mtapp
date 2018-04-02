<template>
    <div class="page" v-nav="{showBackButton:false,hideNavbar:true}">
        <div class="page-content" style="height: 100%" >
          <div style="position: absolute;top: 0;height: 100%;overflow: hidden">
            <img src="static/images/login/hello-bg.jpeg" width="100%">
          </div>
          <div style="position: absolute;top:0;width: 100%">
            <div style="height:50%;width: 50%;z-index: 100;margin: 15% auto">
              <img src="static/images/login/hello-logo.png" alt="" width="100%">
            </div>
            <div class="bts">
              <md-button class="button button-assertive button-block" style="background-color: #4161b6;" @click.native="Login">{{$t('login')}}</md-button>
              <md-button class="button button-balanced button-block" style="color: #4161b6;background-color: white;margin-top: 30px;opacity: .8" @click.native="Regist">{{$t('regist')}}</md-button>
            </div>
            <div class="third-party">
              <p>第三方登录</p>
              <ul>
                <li @click="loginByWX"><img src="../../static/images/login/wechat.png" height="100%"></li>
              </ul>
            </div>
          </div>

        </div>
    </div>
</template>
<script>
  import WX from '@/utils/WX';
  import { platform } from '@/utils'
  import sess from '@/utils/sess'
  import {getPermission} from '@/api/company'
    export default{

      methods:{
        Login(){
          $router.forward('/login')
        },
        Regist(){
          $router.forward('/regist')
        },
        loginByWX() {   //微信登录
          let that = this;
          WX.getauth().then(response => {
            console.log(JSON.stringify(response));
            that.$store.dispatch('setTokenAndUid', {token: response.token, uid: response.uid})
            that.$store.dispatch('GetInfo').then(res => {
              const company = res.company;
              console.log('company'+company)
              if(company&&company.length>0){

                //跳转到选择公司页面
                if(company.length===1){
                  that.$store.dispatch('setCompany',company[0]).then(res=>{
                    getPermission({}).then().catch()
                  });
                  $router.forward('/home');
                }else{
                  sess.set('companys',company);
                  $router.forward('/chooseCompany')
                }
              }else{
                console.log('company is null')
                that.$store.dispatch('setCompany','').then(res=>{
                  getPermission({}).then().catch()
                });
                $router.forward('/home');
              }
            }, rej => {
              $toast.show(rej.description,'2000');
            }).catch(error => {
              console.log(error)
            })
          }).catch(error => {
            console.log('userinfo error' + JSON.stringify(error))
          })
        },
        handleBackButton(e){
          console.log('handleBackButton'+this.$route.path)
          if(this.$route.path=== '/hello'){
            navigator.app.exitApp();
          }

        }
      },
      mounted(){
        const that = this;
        console.log('addEventListener backbutton');
        if(platform()==='Android'){
          document.addEventListener("backbutton",that.handleBackButton,false)
        }

      },
      beforeDestroy() {
        const that = this;
        if(platform()==='Android'){
          console.log('removeEventListener backbutton')
          document.removeEventListener("backbutton",that.handleBackButton,false)
        }

      }
    }
</script>
<style scoped lang="scss">
  .bts{
    width: 70%;
    margin: 0 auto;
  }
  .third-party{
    color: white;
    width: 80%;
    text-align: center;
    margin: 0 auto;
    margin-top: 10%;
    ul{
      width: 100%;
      li{
        display: inline-block;
        width: 50px;
        height: 40px;
        margin-left: 5px;
        padding-top: 10px;
        border-top: 1px solid white;
      }
    }
  }
</style>

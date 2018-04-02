<template>
  <div class="mw-page page approve" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left"></i>
      <span>我的</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content">
          <!--用户个人中心-->
          <div>
            <div class="user-info" @click="$router.forward('/userinfo')">
              <div class="avatar">
                <img :src="userinfo.avatar" width="100%">
              </div>
              <div class="content">
                <p class="name">{{userinfo.username}}</p>
                <p class="company">{{!chosenCompany?'未关联公司':chosenCompany.company_name}}</p>
              </div>
            </div>
            <div class="item-divider-ios"></div>
            <item class="item-icon-left" @click.native="scan">
              {{$t('scan')}}
              <i class="icon ion-qr-scanner"></i>
            </item>
            <item class="item-icon-left item-icon-right">
              {{$t('card')}}
              <i class="icon ion-card"></i>
              <i class="icon ion-ios-arrow-right" style="color: #DDD;"></i>
            </item>
            <div class="item-divider-ios"></div>
            <!--<item class="item-icon-left item-icon-right" @click.native="$router.forward('/settings')">-->
              <!--{{$t('settings')}}-->
              <!--<i class="icon ion-gear-a"></i>-->
              <!--<i class="icon ion-ios-arrow-right" style="color: #DDD;"></i>-->
            <!--</item>-->
          </div>
        </div>
    </div>
</template>
<script>
    import Scanner from '@/utils/Scanner'
  import { mapGetters } from 'vuex'
  export default {

    name:'my',
    computed:mapGetters([
        'userinfo',
        'hideNavbar',
        'chosenCompany'
    ]),
    data(){
      return{

      }
    },
    methods:{
      scan(){
        Scanner.scanbar(function () {

        },function () {

        })
      }
    },
    mounted(){
      console.log(this.$router)
    }
  }
</script>
<style scoped lang="scss">
  .user-info{
    width: 100%;
    height: 160px;
    position: relative;
    background: url("../../assets/img/userinfo/userinfo.png");
    background-size: cover;
    .avatar{
      width: 70px;
      height: 70px;
      /*margin: 20px auto 10px auto;*/
      padding-top: 10px;
      margin: 0 auto;
      img{
        border-radius: 40px;
      }
    }
    .content{
      position: relative;
      width:80%;
      margin: 20px auto 0 auto;
      color: #ffffff;
      height: 60px;
      p{
        /*margin-top: 3px;*/
        text-align: center;
      }
      .company{
        margin-top: -10px;
        opacity: .6;
      }
      .name{
        font-size: 16px;
      }
    }
    .qrcode{
      position: relative;
      float: right;
      width: 20px;
      height: 20px;
      background-color: blue;
      margin: 30px 15px;
    }
    .pl-login{
      position: relative;
      float: right;
      width: 60px;
      height: 20px;
      margin: 30px 0;
    }
  }
</style>

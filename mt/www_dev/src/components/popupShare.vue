<template>
  <div><!-- popup -->
    <div class="popup-mask" @click="hideShare"></div>
    <div class="popup-box">
      <div class="popup-content-list">
        <div class="item-list">
          <img src="../../static/images/company/invite-wechat.png">
          <div>项目圈</div>
        </div>
        <div class="item-list">
          <img src="../../static/images/company/invite-sms.png">
          <div>人脉</div>
        </div>
        <div class="item-list">
          <img src="../../static/images/company/invite-email.png">
          <div>朋友圈</div>
        </div>
        <div class="item-list">
          <img src="../../static/images/company/invite-email.png">
          <div>微信</div>
        </div>
      </div>
      <div class="popup-btn-close" @click="hideShare">取消</div>
    </div>
  </div>
</template>
<script>
  import { mapGetters } from 'vuex'
  import WX from '@/utils/WX'
  import Contact from '@/utils/Contact'
    export default{
      name:'popupShare',
      data(){
        return {
          // showPopup:false,
        }
      },
      props:{},
      computed:mapGetters([
        'hideNavbar',
        'chosenCompany',
        'userinfo'
      ]),
      methods:{
        hideShare(){
          this.$emit('hideShare')
        },
        shareWeixin(){
          const that = this;
          let tmpStr = that.createUrl().url;
          const obj = {
            url:tmpStr.substring(tmpStr.indexOf('http')),
            description:that.createUrl().desc
          }
          WX.shareLink(obj,function (res) {
            console.log(res)
          },function (error) {
          })
        },

        createUrl(){
          const that = this;
          let obj = {
              url : '',
              desc:'',
          }
          return obj;
        }
      }
    }
</script>
<style scoped lang="scss">
  .popup-mask{
    background-color: rgba(0, 0, 0, .5);
    z-index: 998;
    width: 100%;
    height: 100%;
    position: fixed;
    top: 0;
  }
  .popup-box{
    width: 100%;
    position: fixed;
    z-index: 999;
    bottom:0;
    animation: popupAnime .5s;
  }
  .popup-top-tips{
    width: 60%;
    margin: 10px auto;
    color:#C9CBCD;
    text-align: center;
  }
  .popup-content-list{
    background-color: #ffffff;
    margin: 1rem auto;
    display: flex;
    margin-bottom: 65px;
    width: 90%;
    border-radius: 10px;
  }
  .popup-content-list .item-list{
    padding-top:20px;
    padding-bottom:20px;
    text-align: center;
    width: 25%;
    height: 40%;
  }

  .popup-content-list .item-list img{
    width: 50%;
  }

  .popup-btn-close{
    width: 90%;
    height: 50px;
    background-color: white;
    line-height: 50px;
    text-align: center;
    position: absolute;
    left: 5%;
    bottom: 0;
    border-radius: 8px;
    color: #5495FF;
  }

  @keyframes popupAnime
  {
    from {bottom:-15%;}
    to {bottom:0;}
  }
</style>

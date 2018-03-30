<template>
  <div><!-- popup -->
    <div class="popup-mask" @click="hideInvite"></div>
    <div class="popup-box">
      <span class="popup-top-tips" v-if="type==1">
        邀请供应商加入MoTooling
      </span>
      <span class="popup-top-tips" v-if="type==4">
        邀请客户加入MoTooling
      </span>
      <span class="popup-top-tips" v-if="type==3">
        邀请朋友加入MoTooling
      </span>
      <div v-if="type==2">
        <span class="popup-top-tips" v-if="dep_id">
        邀请同事加入<b>{{dep_name}}</b><br>
          <!--加入申请需要管理员<b style="color: black">XX</b>审批-->
        </span>
        <span class="popup-top-tips" v-else>
          邀请同事加入<b>{{chosenCompany?chosenCompany.company_name:''}}</b><br>
          <!--加入申请需要管理员<b>XX</b>审批-->
        </span>
      </div>

      <div class="popup-content-list">
        <!--<div class="item-list">-->
        <!--<img src="../../../../static/images/company/invite-mapp.png">-->
        <!--<span>MT内部邀请</span>-->
        <!--</div>-->
        <div class="item-list" @click="inviteViaWX">
          <img src="../../static/images/company/invite-wechat.png">
          <span>微信邀请</span>
        </div>
        <div class="item-list" @click="sendSms">
          <img src="../../static/images/company/invite-sms.png">
          <span>短信邀请</span>
        </div>
        <div class="item-list" @click="inviteViaEmail">
          <img src="../../static/images/company/invite-email.png">
          <span>邮箱邀请</span>
        </div>
      </div>
      <div class="popup-btn-close" @click="hideInvite">取消</div>
    </div>
  </div>
</template>
<script>
  import { mapGetters } from 'vuex'
  import WX from '@/utils/WX'
  import Contact from '@/utils/Contact'
    export default{
      name:'invite',
      data(){
        return {
          showPopup:false,
        }
      },
      props:{
        dep_id:'',
        dep_name:'',
        type:0,
        data:''
      },
      computed:mapGetters([
        'hideNavbar',
        'chosenCompany',
        'userinfo'
      ]),
      mounted(){

      },
      methods:{
        hideInvite(){
          this.$emit('hideInvite')
        },
        inviteViaWX(){
          const that = this;
          let tmpStr = that.createUrl().url;
          const obj = {
            url:tmpStr.substring(tmpStr.indexOf('http')),
            title:'邀请',
            description:that.createUrl().desc
          }
          WX.shareLink(obj,function (res) {
            console.log(res)
          },function (error) {
          })
        },
        inviteViaEmail(){
          const that = this;
          cordova.plugins.email.open({
            body:that.createUrl().url
          });
        },
        sendSms: function() {
          const that = this;
         // console.log("number=" + number + ", message= " + that.createUrl());
          //CONFIGURATION
          const options = {
            replaceLineBreaks: false, // true to replace \n by a new line, false by default
            android: {
              intent: 'INTENT'  // send SMS with the native android SMS messaging
              //intent: '' // send SMS without open any other app
            }
          };
          const success = function () { console.log('Message sent successfully'); };
          const error = function (e) { console.log('Message Failed:' + e); };
          sms.send('', that.createUrl().url, options, success, error);
        },
        createUrl(){
          const that = this;
          let obj = {
              url : '',
              desc:'',
          }
          switch (that.type){
            case '2': //公司内部邀请
              obj.url +='您的同事邀请您加入公司'+that.chosenCompany.company_name+'   http://weixin.motooling.com/#/regist?inviterCompanyId='+that.chosenCompany.company_id+'&' +
                'inviterCompanyName='+that.chosenCompany.company_name+'&' +
                'inviterUsername='+ that.userinfo.username+'&inviterUserId='+that.userinfo.uid+'&inviteType='+that.type;
              if(that.dep_id){
                obj.url+='&departmentId='+that.dep_id+'&departmentName='+that.dep_name;
              };
              obj.desc+='您的同事'+that.userinfo.username+'邀请您加入公司'+that.chosenCompany.company_name
              break;
            case '1'://供应商邀请
              obj.url+='您的合作伙伴'+that.chosenCompany.company_name+'邀请您成为其供应商   http://weixin.motooling.com/#/regist?inviterCompanyId='+that.chosenCompany.company_id+'&' +
                'inviterCompanyName='+that.chosenCompany.company_name+'&' +
                'inviterUsername='+ that.userinfo.username+'&inviterUserId='+that.userinfo.uid+'&inviteType='+that.type;
              obj.desc+='您的合作伙伴'+that.chosenCompany.company_name+'邀请您成为其供应商'
              break;
            case '4':
             obj.url+='您的合作伙伴'+that.chosenCompany.company_name+'邀请您成为其客户   http://weixin.motooling.com/#/regist?inviterCompanyId='+that.chosenCompany.company_id+'&' +
                'inviterCompanyName='+that.chosenCompany.company_name+'&' +
                'inviterUsername='+ that.userinfo.username+'&inviterUserId='+that.userinfo.uid+'&inviteType='+that.type;
              obj.desc+='您的合作伙伴'+that.chosenCompany.company_name+'邀请您成为其客户'
              break;
            case '3':
              obj.url+='您的好友'+that.userinfo.username+'邀请您注册MoTooling   http://weixin.motooling.com/#/regist?inviterCompanyId='+that.chosenCompany.company_id+'&' +
                'inviterCompanyName='+that.chosenCompany.company_name+'&' +
                'inviterUsername='+ that.userinfo.username+'&inviterUserId='+that.userinfo.uid+'&inviteType='+that.type;
              obj.desc+='您的好友'+that.chosenCompany.company_name+'邀请您注册MoTooling '
              break;
          }
          return obj;
        }
      }
    }
</script>
<style scoped lang="scss">
  .popup-mask{
    background-color: rgba(0, 0, 0, .4);
    z-index: 998;
    width: 100%;
    height: 100%;
    position: fixed;
    top: 0;
  }
  .popup-box{
    height: 40%;
    width: 100%;
    background-color: #F3F6FB;
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
    margin: 1rem auto;
    display: flex;
  }
  .popup-content-list .item-list{
    text-align: center;
    width: 25%;
    height: 40%;
  }
  .popup-content-list .item-list img{
    width: 80%;
  }
  .popup-content-list .item-list span{
    color: #C9CBCD;
  }
  .popup-btn-close{
    width: 100%;
    height: 40px;
    background-color: white;
    line-height: 40px;
    text-align: center;
    position: absolute;
    bottom: 0;
  }
  .popup-box span{
    display: block;
  }
  @keyframes popupAnime
  {
    from {bottom:-15%;}
    to {bottom:0;}
  }
</style>

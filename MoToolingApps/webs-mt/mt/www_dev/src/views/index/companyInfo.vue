<template>
  <div class="mw-page page" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left"></i>
      <span>{{info.company_name}}</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content">
      <list>
        <item class="item-icon-right">
          <img src="../../../static/images/company/logo_company.png" class="item-img">
          <span class="item-text item-text-top">{{info.company_name}}</span><br>
          <span class="item-text item-text-bottom">认证图片</span>
        </item>
      </list>
      <list>
        <item class="item-icon-right">
          企业全称
          <span class="item-note">{{info.company_name}}</span>
        </item>
        <item class="item-icon-right">
          所在行业
          <span class="item-note"></span>
        </item>
        <item class="item-icon-right">
          办公地址
          <span class="item-note">{{info.address}}</span>
        </item>
        <item class="item-icon-right">
          负责人
          <span class="item-note">{{info.ceo}}</span>
        </item>
      </list>
      <div class="div-bottom">
        <div class="btn-invite btn-inviteSupply" @click="showInvite(1)">邀请成为供应商</div>
        <div class="btn-invite btn-inviteCustom" @click="showInvite(4)">邀请成为客户</div>
      </div>
      <div v-show="showPopup">
        <invite v-on:hideInvite="showPopup=false" type="inviteType"></invite>
      </div>
    </div>
  </div>
</template>
<script>
  import { getCompanyProfile } from '@/api/company';
  import { mapGetters } from 'vuex'
  import invite from '@/components/invite'
  export default {
      name:'companyInfo',
    data(){
      return {
        info:{},
        showPopup:false,
        inviteType:1
      }
    },
    components:{
      invite
    },
    computed:mapGetters([
      'token',
      'uid'
    ]),
    mounted(){
      let data = {};
      data.client_type = 2;
      data.company_id = this.$route.query.id;
      data.token = this.token;
      data.uid = this.uid;

      getCompanyProfile(data)
        .then(res => {
          this.info = res;
          console.log(res);
        })
        .catch(err => {
          console.log(err);
        })
    },
    methods:{
      showInvite(i){
        this.showPopup = true;
        this.inviteType = i;
      }
    }
  }
</script>
<style scoped lang="scss">
  .item-img{
    width: 50px;
    height: 50px;
    float:left;
  }
  .item-text{
    line-height: 25px;
    margin-left: 8px
  }
  .item-text-top{
    font-size: 16px;
  }
  .item-text-bottom{
    font-size: 12px;
    color: #416eb6;
  }
  .div-bottom{
    position: fixed;
    bottom: 0;
    height: 60px;
    width: 100%;
    display: flex;
    background: white;
  }
  .div-bottom .btn-invite{
    width: 49.4%;
    line-height: 60px;
    text-align: center;
    color: #416eb6;
  }
  .div-bottom .btn-inviteSupply{
    border-right: 1px solid;
  }
</style>

<template>
  <div class="mw-page page crm" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>新增客户</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content has-tab" v-if="chosenCompany">
      <div style="width: 100%;background-color: white;height: 50px">
        <mw-search placeholder="搜索客户"  v-model="searchVal" :search="onSearch" cancelText="取消" :cancelAction="onCancel"></mw-search>
      </div>
      <div style="flex: 1" v-if="!isShowSearchResult">
        <div class="content">
          <br>
          <item>
            企业全称
            <div class="item-note">{{chosenCompany.Name}}</div>
          </item>
          <item>
            企业法人
            <div class="item-note">{{chosenCompany.OperName}}</div>
          </item>
          <item>
            纳税识别号
            <div class="item-note">{{chosenCompany.CreditCode}}</div>
          </item>
          <item>
            企业状态
            <div class="item-note">{{chosenCompany.Status}}</div>
          </item>
        </div>
      </div>
      <div style="flex: 1" v-else="isShowSearchResult">
        <list v-if="searchList.length">
          <item class="item-icon-right" v-for="item in searchList" @click.native="toInfo(item)">
            <img src="../../../../static/images/company/logo_company.png" class="item-img">
            <span class="item-text item-text-top">{{item.Name}}</span><br>
            <span class="item-text item-text-bottom">法人:{{item.OperName}}</span>
          </item>
        </list>
        <list v-else-if="!searchList.length">
          <!--<h1>这是搜索结果</h1>-->
          <div class="logo">
            <img src="../../../../static/images/supply/supply_search.png"/>
          </div>
          <div class="tips">
            未找到您搜索的客户资料.
          </div>
        </list>
      </div>
    </div>

    <invite v-on:hideInvite="showPopup=false" type="4"  v-show="showPopup"> </invite>
    <div class="mw-page-content" v-if="!chosenCompany">
      <div style="height: 30%;width: 80%;margin: 30px auto">
        <img src="../../../../static/images/company/default.png" width="100%">
      </div>
      <div style="width: 80%;margin: 0 auto">
        <div class="text-center">
          <p>您还未关联企业，您可以自己申请试用企业版，或者将您的公司注册为企业用户。</p>
          <md-button class="button button-calm block" @click.native="$router.forward('/company/trial')">试用企业版</md-button>
          <!--<br>-->
          <!--<md-button class="button button-assertive block">购买企业版</md-button>-->
        </div>
      </div>
    </div>
    <div class="mw-page-footer">
      <div class="btns">
        <div class="btn" @click="showPopup = true">发送邀请</div>
      </div>
    </div>
  </div>
</template>
<script>
  import { mapGetters } from 'vuex'
  import axios from 'axios'
  import { cstOrSupList,cstSupSearch,cstSupDelete } from '@/api/supply'
  import invite from '@/components/invite'
  import MwSearch from "../../../components/mw-search"
  import SwipeItem from "../../../components/swipeItem";
  export default {
    name:'cstList',
    data(){
      return {
        searchVal:'',
        showPopup:false,
        cstInfos:[],
        isShowSearchResult:false,
        searchCancelText:'搜索',
        searchList:[],
        pageModel:{
          curr_page:0,
          page_size:10,
          total_page:0
        },
        chosenCompany:{}  //选择的公司
      }
    },
    computed:mapGetters([
      'token',
      'uid',
      'chosenCompany'
    ]),
    components:{
      SwipeItem,
      MwSearch,
      invite
    },
    methods:{
      getData(){
        axios.get('http://i.yjapi.com/ECIV4/Search?key=df39830ea37040d9a24e0570c45ef4a4&keyword='+this.searchVal)
          .then( res => {
            console.log(res.data.Result)
            if (res.data.Result.length){
              this.isShowSearchResult = true
               this.searchList = res.data.Result
            }
          }).catch( err => {
          this.isShowSearchResult = false
            $toast.show('查询结果为空',2000)
        })
      },
      onSearch(){
        this.getData()
      },
      onCancel(){
        this.searchVal = ''
        this.isShowSearchResult = false
        this.searchList.length = 0
      },
      toInfo(item){
        this.isShowSearchResult = false
        this.chosenCompany = item
      }
    }
  }
</script>
<style scoped lang="scss">
  .item-img{
    width: 30px;
    height: 30px;
    float:left;
  }
  .item-text{
    line-height: 10px;
    margin-left: 8px
  }
  .item-text-top{
    font-size: 14px;
  }
  .item-text-bottom{
    font-size: 12px;
    color: #416eb6;
  }
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
  .logo{
    margin: 50px auto 0 auto;
    text-align: center;
    width: 80%;
  }
  .logo img{
    width: 50%;
  }
  .tips{
    margin-top: 15px;
    margin-bottom: 40px;
    font-size: 10px;
    color: #ababab;
    text-align: center;
  }
</style>

<template>
  <div class="mw-page page crm" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>我的供应商</span>
      <i class="right" @click="showPopup=true" v-if="chosenCompany">新增</i>
      <i class="right" v-else></i>
    </div>
    <div class="mw-page-content has-tab" v-if="chosenCompany">
      <div style="width: 100%;background-color: white;">
        <mw-search placeholder="查询供应商信息"  v-model="searchVal" :search="onSearch" cancelText="取消" :cancelAction="onCancel"></mw-search>
      </div>
      <div style="flex: 1;position: relative" v-if="!isShowSearchResult">
        <div class="content">
          <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh">
            <swipe-item :obj="item"  v-for="item in cstInfos"  v-on:ItemClick="toInfo" swipeItemText="删除" v-on:SwipeItemClick="delItem">
              <img src="../../../../static/images/company/logo_company.png" class="item-img">
              <span class="item-text item-text-top">{{item.sup_company_name}}</span><br>
              <span class="item-text item-text-bottom"
                    :style="{color: item.grade < 3 ? 'red' : 'green'}">
              信誉度: {{item.credit_class}}
            </span>
              <span class="item-text item-text-bottom">星级:
              <i class="ion-ios-star" v-for="i in item.grade"></i>
            </span>
            </swipe-item>
            <div v-if="pageModel.curr_page >= pageModel.total_page" slot="infinite" class="text-center"></div>
          </scroll>
        </div>
      </div>
      <div style="flex: 1" v-else="isShowSearchResult">
        <list v-if="searchList.length">
          <item class="item-icon-right" v-for="item in searchList" @click.native="toInfo(item)">
            <img src="../../../../static/images/company/logo_company.png" class="item-img">
            <span class="item-text item-text-top">{{item.sup_company_name}}</span><br>
            <span class="item-text item-text-bottom">地址:{{item.sup_company_address}}</span>
          </item>
        </list>
        <list v-else-if="!searchList.length">
          <!--<h1>这是搜索结果</h1>-->
          <div class="logo">
            <img src="../../../../static/images/supply/supply_search.png"/>
          </div>
          <div class="tips">
            未找到您搜索的供应商资料.
          </div>
        </list>
      </div>
    </div>
    <invite v-on:hideInvite="showPopup=false" type="1"  v-show="showPopup"> </invite>
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
  </div>
</template>
<script>
  import { mapGetters } from 'vuex'
  import { cstOrSupList,cstSupSearch,cstSupDelete } from '@/api/supply';
  import invite from '@/components/invite'
  import MwSearch from "../../../components/mw-search";
  import SwipeItem from "../../../components/swipeItem";

  export default {
    name:'supplyList',
    data(){
      return {
        searchVal:'',
        showPopup:false,
        cstInfos:[],
        isShowSearchResult:false,
        searchList:[],
        pageModel:{
          curr_page:0,
          page_size:10,
          total_page:0
        }
      }
    },
    computed:mapGetters([
      'token',
      'uid',
      'chosenCompany'
    ]),
    components:{
      MwSearch,
      invite,
      SwipeItem
    },
    created(){
      this.getData()
    },
    /*watch:{
      searchVal(){
        this.onSearch();
      }
    },*/
    methods:{
      getData(refresh){
        const reqData = {
          cst_sup_type:2,
          curr_page:refresh?1:1+this.pageModel.curr_page,
          page_size:10
        }
        cstOrSupList(reqData)
          .then(res => {
            if (refresh) {
              this.cstInfos.length = 0
            }
            this.cstInfos = this.cstInfos.concat(res.cst_info)
            this.pageModel.curr_page = res.curr_page ? res.curr_page : 0
            this.pageModel.total_page = res.curr_page ? res.curr_page : 0
          })
          .catch(err => {
            $toast.show(err.description)
          })
      },
      onSearch(){
        if(!this.chosenCompany){
          $toast.show('您还没有公司,请先创建或加入公司.');
        }else{
          let data = {};
          data.client_type = 2;
          data.cst_sup_name = this.searchVal;
          data.cst_sup_type = 2;
          data.curr_page = 1;
          data.page_size = 10;
          cstSupSearch(data).then(res => {
            this.isShowSearchResult = true;
            this.searchList = res.cst_sup;
          })
          .catch(err => {
            $toast.show('搜索出错了!');
            console.log(err);
          })
        }
      },
      onCancel(){
        this.searchVal = '';
        this.isShowSearchResult = false;
      },
      //上拉加载 分页
      onInfinite(done){
        const that = this;
        if(that.pageModel.total_page<=that.pageModel.curr_page){
          done()
          return
        }
        setTimeout(function () {
          if(that.pageModel.total_page<2){
            done()
          }else{
            that.getData();
            done()
          }
        },500)
      },
      //下拉刷新
      onRefresh(done){
        const that = this
        setTimeout(function () {
          that.pageModel.curr_page = 0
          that.getData(true)
          done()
        },500)
      },
      delItem(item){

        $dialog.confirm({
          theme:'ios',
          okText:'确定',
          cancelText:'取消',
          title:'确定要删除吗?'
        }).then(res=>{
          if (res) {
            const reqData ={
              cst_sup_typ:2,
              cst_sup_id:item.cst_company_id
            }
            cstSupDelete(reqData).then(res => {
              $toast.show('删除成功!', 1000)
              this.$router.go(-1);
            }).catch(err => {
              console.log(err);
              $toast.show(err.description)
            })
          }
        })
      },
      menuClick(){
        this.$router.forward('/supplyList-2')
      },
      toInfo(item){
        console.log(item)
        this.$router.push({path:'/crm/supplyInfo',query:{id:item.sup_company_id}});
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

  .search-right-btn{
    display: block;
    color: #416eb6;
    margin-left: 10.6px;
    font-size: 17px;
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

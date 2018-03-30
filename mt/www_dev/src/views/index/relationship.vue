<template>
  <div class="mw-page page approve" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left"></i>
      <span>人脉</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content">
      <div style="width: 100%;background-color: white;">
        <mw-search placeholder="查询供应商/客户/同事"  v-model="searchVal" :search="onSearch" cancelText="取消" :cancelAction="onCancel"></mw-search>
      </div>
          <div v-if="!isShowSearchResult||!searchVal">
            <item class="item-icon-right"
                  @click.native=" $router.forward('/company/index')">
              <img src="../../../static/images/company/logo_company.png" class="item-img">
              <span style="line-height: 30px;margin-left: 15px">我的公司</span>
              <span class="item-note" style="margin-top: 8px">{{chosenCompany ? chosenCompany.companyName : ''}}</span>
              <i class="icon ion-ios-arrow-right" style="color: #DDD;height: 63px"></i>
            </item>
            <item class="item-icon-right" @click.native="$router.forward('crm/cstList')">
              <img src="../../../static/images/company/logo_customer.png" class="item-img">
              <span style="line-height: 30px;margin-left: 15px">我的客户</span>
              <i class="icon ion-ios-arrow-right" style="color: #DDD;height: 63px"></i>
            </item>
            <item class="item-icon-right" @click.native="$router.forward('/crm/supplyList')">
              <img src="../../../static/images/company/logo_supplyer.png" class="item-img">
              <span style="line-height: 30px;margin-left: 15px">我的供应商</span>
              <i class="icon ion-ios-arrow-right" style="color: #DDD;height: 63px"></i>
            </item>
            <div class="item-divider-ios" style="padding-top: 10px" v-if="friends.length>0">我的朋友</div>
            <item class="item-icon-right item-contact" v-if="friends.length>1" v-for="item in friends">
              <img :src="item.icon?item.icon:'../../../static/images/company/contact2.png'" class="item-img">
              <span style="line-height: 30px;margin-left: 15px">{{item.name}}</span>
            </item>
          </div>
          <div v-else-if="isShowSearchResult&&searchVal">
            <button-bar
              theme="positive"
              :tab-items="tabItems"
              :tab-index="tabIndex"
              :on-tab-click="onTabClick" style="padding: 0 10px;">
            </button-bar>
            <list class="list-ios" v-show="isShowSearchResult" style="margin-bottom: 50px;">
              <div class="logo" v-if="searchList.length <= 0">
                <img src="../../../static/images/supply/supply_search.png"/>
              </div>
              <div class="tips" v-if="!searchList.length">
                未找到您搜索的{{searchReqData.search_type == 1 ? tabItems[0] : tabItems[1]}}资料.
              </div>
              <item class="item-icon-right"
                    v-for="item in searchList"
                    @click.native="toInfo(searchReqData.search_type == 1 ? item.company_id : item.user_id, item.relation_type)">
                <img src="../../../static/images/company/logo_company.png" class="item-img">
                <span style="line-height: 30px;margin-left: 15px">{{searchReqData.search_type == 1 ? item.company_name : item.user_name}}</span>
                <!--<span class="item-note" v-if="item.relation_type == 0">无关系</span>-->
                <span class="item-note" v-if="item.relation_type == 1">供应商</span>
                <span class="item-note" v-if="item.relation_type == 2">客户</span>
              </item>
            </list>
          </div>
          <!--<cascade :fields="fields" :data="cascadeData" :value="values" :on-change="onChange"></cascade>-->
        </div>
    </div>
</template>
<script>
  import { mapGetters } from 'vuex'
  import { comUserSearch } from '@/api/supply'
  import axios from 'axios'
  import MwInput from "../../components/mw-input";
  import MwSearch from "../../components/mw-search";
  export default {
    components: {
      MwSearch,
      MwInput},
    name:'relationship',
    data(){
      return{
        friends:[],
        searchVal:'',
        searchCancelText:'搜索',
        isShowSearchResult:false,
        searchList:{},
        searchReqData:{'company_id':'29'},
        tabIndex:0,
        tabItems:['公司','用户'],
        fields:['国家','省份','城市'],
        values:[],
        cascadeData:[]
      }
    },
    computed:mapGetters([
      'chosenCompany',
    ]),

    created(){
      let that = this;
      if (!that.chosenCompany) {
        that.$store.dispatch('GetInfo').then(res => {
          const company = res.company;
          if (company && company.length > 0) {
            //跳转到选择公司页面
            if (company.length === 1) {
              that.$store.dispatch('setCompany', company[0]);
              $router.forward('/home')
            } else {
              sess.set('companys', company);
              $router.forward('/chooseCompany')
            }
          } else {
            console.log('company is null')
            that.$store.dispatch('setCompany', '')
          }
        }).catch(error => {
          $toast.show('网络异常，请稍后重试!', 1000);
        })
      }
    },
    methods:{
      onTabClick(index) {
        console.log(index);
        this.tabIndex = index;
        if(index == 0){
          this.searchReqData.search_type = 1;//1:company 2:user
          comUserSearch(this.searchReqData)
            .then(res => {
              console.log(res);
              if(this.searchReqData.search_type == 1){
                this.searchList = res.company_info;
              }else if(this.searchReqData.search_type == 2){
                this.searchList = res.user_info;
              }else{
                $toast.show('搜索出错.', 1000);
              }
            })
            .catch(err => {
              console.log(err);
            })
        }else{
          this.searchReqData.search_type = 2;//1:company 2:user
          comUserSearch(this.searchReqData)
            .then(res => {
              console.log(res);
              if(this.searchReqData.search_type == 1){
                this.searchList = res.company_info;
              }else if(this.searchReqData.search_type == 2){
                this.searchList = res.user_info;
              }else{
                $toast.show('搜索出错.', 1000);
              }
            })
            .catch(err => {
              console.log(err);
            })
        }
      },
      onSearch(){
        this.tabIndex = 0;
        this.isShowSearchResult = true;
        this.searchReqData.client_type = 2;
        this.searchReqData.com_user_name = this.searchVal;
        this.searchReqData.search_type = 1;//1:company 2:user
        comUserSearch(this.searchReqData)
          .then(res => {
            console.log(res);
            if(this.searchReqData.search_type == 1){
              this.searchList = res.company_info;
            }else if(this.searchReqData.search_type == 2){
              this.searchList = res.user_info;
            }else{
              $toast.show('搜索出错.', 1000);
            }
          })
          .catch(err => {
            console.log(err);
          })

        console.log('搜索回调');
      },
      onCancel(){
        console.log('搜索取消回调');
        this.searchVal = '';
        this.isShowSearchResult = false;
      },
      toInfo(id,type){
        switch (type){
          case 0:
            if (this.searchReqData.search_type===1){
              this.$router.push({path:'/company/companyInfo',query:{id:id}});
            }else {
              this.$router.push('/company/memberInfo/'+id);
            }
            break;
          case 1:
            this.$router.push({path:'/crm/supplyInfo',query:{id:id}});
            break;
          case 2:
            this.$router.push({path:'/crm/customInfo',query:{id:id}});
            break;
          default:

            break;
        }
      },
      onChange(value){
        this.values = value;
        console.log(1211);
        console.log(this.values);
      }
    }
  }
</script>
<style scoped lang="scss">
  .search{
    .search{
      width: 80%;
      margin: 0 auto;
      padding-top: 13px;
      position: relative;
      height: 100%;
      input{
        height: 30px;
        border-radius: 5px;
        width: 100%;
        opacity: .1;
      }
      .inputMask{
        position: absolute;
        top: 0;
        height: 30px;
        border-radius: 5px;
        width: 100%;
        text-align: center;
        color: white;
      }
    }
  }
  .item-img{
    width: 30px;height: 30px;float:left;
  }
  .item-contact{
    padding: 10px 0;
    .arrow{
      color: #DDD;height: 50px
    }

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

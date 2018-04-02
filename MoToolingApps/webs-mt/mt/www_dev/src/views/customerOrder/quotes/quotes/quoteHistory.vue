<template>
  <div class="mw-page page" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>历史报价单列表</span>
      <i class="right" @click="showFilter = true">筛选</i>
    </div>
    <div class="mw-page-content has-tab">
      <div style="position:relative;height: 100%;">
        <scroll :on-infinite="onInfinite" style="top:0">
          <div v-for="item in historys" class="history">
            <mw-radio :active="item.chosen" :obj="item" :iClick="chooseItem" :aClick="arrowClick" :chosenObj="chosenItem">
              <p>报价单编号:{{item.quote_no}}</p>
              <p>客户:{{item.cst_name}}</p>
            </mw-radio>
          </div>
          <div v-if="pageModel.curr_page >= pageModel.total_page" slot="infinite" class="text-center">没有更多数据</div>
        </scroll>
      </div>
    </div>
    <div class="mask" v-show="showFilter" @click="showFilter=false"></div>
    <div :class="showFilter?'filter active':'filter'">
      <div class="item item-divider" v-show="showFilter">
        筛选
      </div>
      <list class="list-ios" v-show="showFilter" style="position: relative">
        <von-input type="text" v-model="query.quote_no" placeholder="输入报价单编号"></von-input>
        <von-input type="text" v-model="query.cst_prod_name" placeholder="产品名称"></von-input>
        <von-input type="text" v-model="searchCompanyName" placeholder="输入客户名称"></von-input>
        <ul class="searchCsts" v-show="cstList.length>0&&showCstList">
          <li @click="chooseCst(item)" v-for="item in cstList" :key="item.cst_company_id">{{item.cst_company_name}}
          </li>
          <li @click="showCstList=false" style="text-align: center">收起
          </li>
        </ul>
        <cst-item label="业务员" v-model="searchSales" :hasArrow="true" :arrowClick="chooseSalesman"></cst-item>
        <ul class="types" style="padding-top: 5px">
          <li class="type active"  v-for="item in chosenSalesman">{{item.username}}</li>
        </ul>
      </list>
      <p style="margin: 8px 15px;font-size: 12px" v-show="chosenCst.length>0">已选公司</p>
      <ul class="companys" v-show="chosenCst.length>0">
        <li v-for="item in chosenCst" :key="item.cst_company_id">{{item.cst_company_name}}</li>
      </ul>
      <div class="btns" v-show="showFilter">
        <div class="btn reset" @click="reset">重置</div>
        <div class="btn" @click="submit">确定</div>
      </div>
    </div>
    <div class="mw-page-footer">
      <div class="btns" >
        <div class="btn" @click="saveData">
          确定参考
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import sess from '@/utils/sess'
  import {list,referHis} from '@/api/customerOrder/quote'
  import mwRadio from '@/components/mwRadio'
  import { associateQuote } from '@/api/customerOrder/order'
  import CstItem from "../../../../components/cstItem";
  export default{
    name:'quoteHistory',
    components:{
      CstItem,
      mwRadio
    },
    data(){
      return {
        query:{
          quote_no:'',
          cst_prod_name:'',
          salesman:[]
        },
        searchCompanyName:'',
        pageModel:{
          curr_page:0,
          total_page:1,
          page_size:10
        },
        action:'',
        chosenItem:{},
        historys:[],
        searchSales:'',
        chosenHistorys:[],
        cstList:[],
        chosenCst:[],
        showCstList:false,
        showFilter:false,
        chosenSalesman:[]
      }
    },
    mounted(){
      this.action = this.$route.params.action
      if(this.action === 'associate'){
        this.project_id = this.$route.params.project_id
      }
      this.queryHistory()
    },

    methods: {
      onInfinite(done) {
        const that = this;
        if(that.pageModel.total_page <= that.pageModel.curr_page){
          done()
          return
        }
        setTimeout(function () {
          if(that.pageModel.total_page<2){
            done()
          }else{
            that.queryHistory();
            done()
          }
        },600)
      },
      queryHistory(){
        const reqData = {
          query:this.query,
          curr_page:1+this.pageModel.curr_page,
          page_size:this.pageModel.page_size
        }
        list(reqData).then(res=>{
          this.historys = this.historys.concat(res.quote_list)
          this.pageModel.curr_page = res.curr_page
          this.pageModel.total_page = res.total_page
        }).catch(error=>{
          $toast.show(error.description)
        })

      },
      chooseItem(item){
        if(this.chosenItem.quote_id===item.quote_id){
          return
        }
        if(this.chosenItem.chosen){
          this.chosenItem.chosen = false
        }
        this.chosenItem = item
      },
      arrowClick(item){
        this.$router.forward('/customerOrder/quote/detail/check/'+item.quote_id)
      },
      saveData(){
        if(this.action === 'associate'){
          const reqData = {
            project_id:this.project_id,
            quote_id:this.chosenItem.quote_id
          }
          associateQuote(reqData).then(res=>{
            console.log(res)
            history.go(-1)
          }).catch(error=>{
              $toast.show(error.description)
          })
        }else{
          const reqData = {
            quote_id_his:this.chosenItem.quote_id
          }
          referHis(reqData).then(res=>{
            console.log(res)
            this.$router.forward('/customerOrder/quote/detail/edit/'+res.quote_id)
          }).catch(error=>{
            $toast.show(error.description)
          })
        }

      },

      //筛选相关
      onMenuClick(){
        this.showFilter = true
      },
      reset(){

      },
      submit(){

      },
      chooseSalesman(){

      }
    }
  }
</script>

<style lang="scss" scoped>

  .history{
    padding-left: -20px;
    p{
      transform: scale(0.9,0.9);
      margin-left: -20px;
    }
  }

  .mask{
    position: absolute;
    width: 100%;
    height: 100%;
    top:0;
    left: 0;
    z-index: 14;
    background-color: #2e2e2d;
    opacity: .4;
  }
  .filter{
    width: 0;
  }
  .filter.active{
    width: 80%;
    height: 100%;
    background-color: #F3F6FB;
    position: absolute;
    top:0;
    right: 0;
    z-index: 15;
    transition:width .5s;
    -moz-transition:width .2s; /* Firefox 4 */
    -webkit-transition:width .2s; /* Safari and Chrome */
    -o-transition:width .2s; /* Opera */
    .btns{
      display: flex;
      position: absolute;
      bottom: 0;
      width: 100%;
      height: 40px;

      .btn{
        flex: 1;
        text-align: center;
        line-height: 40px;
        background-color: #416eb6 ;
        color: white;
        &.reset{
          background-color:#FFFFFF ;
          color:  #416eb6;
        }
      }
    }
  }

</style>

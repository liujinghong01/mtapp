<template>
  <div class="mw-page page" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>历史客户定单</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content">
        <div style="position:relative;">

          <von-input type="text" v-model="quote_no" placeholder="请输入报价编号单号" label="报价单编号"></von-input>
          <von-input type="text" v-model="currCustomer.cst_company_name" placeholder="请输入客户名称" label="客户名称"></von-input>
          <ul class="searchList" v-if="searchList.length>0">
            <li v-for="item in searchList" :key="item.cst_company_id" @click="setCustomer(item)">{{item.cst_company_name}}</li>
          </ul>

          <ul class="types" style="padding-top: 5px">
            <li class="type active"  v-for="item in chosenCompanys">{{item.cst_company_name}}</li>
          </ul>

          <von-input type="text" v-model="prd_name" placeholder="请输入产品名称" label="产品名称"></von-input>
          <cst-item label="业务员" v-model="salesman" :hasArrow="true" :arrowClick="chooseSalesman"></cst-item>
          <ul class="types" style="padding-top: 5px">
            <li class="type active"  v-for="item in chosenSalesman">{{item.username}}</li>
          </ul>
        </div>
      </div>
    <div class="btns">
      <div class="btn" @click="ok">确定</div>
    </div>
  </div>
</template>
<script>
  import {mapGetters} from 'vuex'
  import sess from '@/utils/sess'
  import _ from 'lodash'
  import { cstSupSearch } from '@/api/supply'
  import CstItem from "../../../components/cstItem";
  export default{
    name:'orderAssociate',
    components: {CstItem},
    data(){
      return {
        quote_no:'',
        prd_name:'',
        salesman:'',
        chosenSalesman:[],
        chosenSalesmanIds:[],

        searchList:[],
        currCustomer:{
          cst_company_name:''
        },

        compute_history:[],
        chosenCompanys:[],
        chosenCompanysIds:[],

      }
    },
    computed:{
      ...mapGetters(['chosenCompany']),

    },
    activated(){
      const salesman = sess.get('salesman')
      if(salesman&&salesman!=''){
        this.chosenSalesman.length=0
        this.chosenSalesman = JSON.parse(salesman)
        sess.remove('salesman')
      }
    },
    methods:{
      setCustomer(item){
        this.searchList.length=0;
        for(let i in this.chosenCompanys){
          if(this.chosenCompanys[i].cst_company_name===item.cst_company_name){
            return
          }
        }
        this.chosenCompanys.push(item)
        this.getHistory()
      },
      chooseType(item){
        if(item.chosen){
          this.$set(item,'chosen',false)
          return
        }
        if(this.chosenType.name){
          this.chosenType.chosen = false
        }
        this.chosenType = item;
        this.$set(item,'chosen',true)
        this.getHistory();
      },
      matchCustomer:_.debounce(
        function(){
          let data = {};
          data.cst_sup_name = this.currCustomer.cst_company_name;
          data.cst_sup_type = 1;
          data.curr_page = 1;
          data.page_size = 10;

          cstSupSearch(data)
            .then(res => {
              console.log(res);
              this.searchList = res.cst_sup;
            })
            .catch(err => {
              $toast.show('搜索出错了!');
              console.log(err);
            })
        },800
      ),
      chooseSalesman(){
        this.$router.forward('/customerOrder/quote/changeSalesman/multi')
      },
      getHistory(){
        let requestData={
          curr_page:1,
          page_size:15,
          query:{
            cst_company_id:this.chosenCompanysIds,
            prod_name:'',
            quote_no:'',
            approve_step:[],
            salesman:this.chosenSalesmanIds
          },
        }

      },
      ok(){
        let quoteQuery={
          cst_company_id:this.chosenCompanysIds,
          approve_step:[],
          salesman:this.chosenSalesmanIds,
          start_date:'',    //报价开始日期
          end_date:'',      //报价日期
          prod_name:'',     //产品名称
          project_no:'',      //报价单编号

        }

        this.$router.forward('/customerOrder/order/orderHistory/'+JSON.stringify(quoteQuery))
      },
    },
    watch:{
      'currCustomer.cst_company_name':function () {
        if(this.currCustomer.cst_company_name.trim().length===0){
          this.currCustomer.cst_company_id=''
          this.searchList.length=0;
          return
        }
        if(this.currCustomer.cst_company_id){
          return;
        }
        this.matchCustomer();
      },
      'chosenSalesman':function () {
        const that = this;
        that.chosenSalesmanIds.length=0
        that.chosenSalesman.forEach(item=>{
          that.chosenSalesmanIds.push(item.uid)
        })
      },
      'chosenCompany':function () {
        const that = this;
        that.chosenCompanys.length=0
        that.chosenCompanys.forEach(item=>{
          that.chosenCompanysIds.push(item.uid)
        })
      }
    }
  }
</script>
<style scoped lang="scss">
  .searchList{
    background-color: whitesmoke;
    width: 100%;
    height: auto;
    position: absolute;
    z-index: 10;
    padding-left: 10px;
    li{
      padding: 8px;
      height: 40px;
      transition: height .2s;
    }
  }
  .prod-type{
    width: 100%;
    height: auto;
    margin-top: 10px;
    background-color: white;
    .title{
      padding:10px 15px;
      width: 100%;
      margin: 0;
    }
  }

  .types {
    width: 90%;
    margin: 0 auto;
    position: relative;
    padding-bottom: 5px;
    li {
      padding: 5px 20px;
      text-align: center;
      border-radius: 15px;
      background-color: #FFFFFF;
      transform: scale(0.8, 0.8);
      -webkit-transform: scale(0.8, 0.8); /* Chrome, Safari, Opera */
      -moz-transform: scale(0.8, 0.8);
      border: thin solid #ababab;
      color: #ababab;
      width: 30%;
      text-overflow: ellipsis;
      white-space: nowrap;
      overflow: hidden;
      display: inline-block;
      &.active {
        color: #416eb6;
        border: thin solid #416eb6;
      }
    }
  }


  .compute-history{
    width: 100%;
    margin-top: 10px;
    background-color: white;
    position: relative;
    .title{
      padding:10px 15px;
      width: 100%;
      margin: 0;
    }
    .history {
      width: 100%;
      position: absolute;
      top: 40px;
      height: auto;
      margin-bottom: 50px;
      span{
        width: 45%;
        text-overflow: ellipsis;
        white-space: nowrap;
        float: left;
        padding: 5px;
        overflow: hidden;

      }
    }
  }
</style>


<template>
  <div class="mw-page page" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>客户定单参考列表</span>
      <i class="right "></i>
    </div>
    <div class="mw-page-content has-tab" >
      <div style="height: 48px">
        <tabs  :tab-items="tabs" :tab-index="tabIndex" :on-tab-click="onTabClick"></tabs>
      </div>
      <div style="flex: 1;position: relative">
        <div class="content"  v-show="tabIndex===0">
        <scroll class="page-content"
                :on-infinite="onInfinite" style="-webkit-overflow-scrolling: touch">
          <div v-for="(item, index) in items[tabIndex].list" :key="index"  >
            <item  @click.native="referToInfo(item)">
              <p >产品名称:{{item.prod_name}}</p>
              <p>报价单号:{{item.quote_no}}</p>
              <p>客户:{{item.cst_name}}</p>
              <p>报价 日期:{{item.quote_date}}</p>
              <span class="referTo" @click="referTo(item)">参考</span>
            </item>
          </div>
          <div v-if="pageModels[tabIndex].curr_page >= pageModels[tabIndex].total_page" slot="infinite" class="text-center">没有更多数据</div>
        </scroll>


      </div>
        <div class="content"  v-show="tabIndex===1">
          <scroll class="page-content" :on-infinite="onInfinite" style="-webkit-overflow-scrolling: touch">
            <div v-for="(item, index) in items[tabIndex].list" :key="index"  >
              <item  @click.native="referToInfo(item)">
                <!--<p >类型:{{item.type_name}}</p>-->
                <p>编号:{{item.project_no}}</p>
                <p>制品人:{{item.cst_name}}</p>
                <p>制单日期:{{item.order_accept_date}}</p>
                <span class="referTo" @click="referTo(item)">参考</span>
              </item>
            </div>
            <div v-if="pageModels[tabIndex].curr_page >= pageModels[tabIndex].total_page" slot="infinite" class="text-center">没有更多数据</div>
          </scroll>


        </div>

        </div>

      </div>
    <div class="referToInfo" v-show="isShow" @click="isShow=false">
      <div class="infoBox" v-if="tabIndex===1">
        <p>编号：{{ compute.compute_no }} </p>
        <p>制品信息：{{ compute.prod_info }} </p>
        <p>成本：{{ compute.mold_cost }}元</p>
        <p>利润：{{ compute.profit_cost }}元 </p>
        <p>模具单价：{{ compute.mold_up }}元</p>
      </div>
      <div class="quotes" v-else>
        <p>客户：{{ quote.cst_name }} </p>
        <p>产品名称：{{ quote.prod_name }}</p>
      </div>
    </div>


  </div>
</template>
<script>
  import { mapGetters } from 'vuex'
  import { cstSupSearch } from '@/api/supply'
  import swipeItem from '@/components/swipeItem'
  import _ from 'lodash'
  import { list,orderList,headDetail,quoteLines,computeDetail,fromQuote ,orderAssociate } from '@/api/customerOrder/quote'
  import sess from '@/utils/sess'
  export default{
    name:'orderReferTo',
    components:{
      swipeItem
    },
    data(){
      return{
        tabIndex:0,
        quote_id:'',
        tabs:['历史报价单','历史客户定单'],
        pageModels:[
          {curr_page:0,page_size:10,total_page:0 },
          {curr_page:0,page_size:10,total_page:0 }
        ],
        query:{       //查询条件
          compute_no:"",    //核算单编号
          approve_step:[], //审批步骤
          cst_company_id:[], //选择的公司id数组
          cst_prod_name:'',   //产品名称
          type:[]             //核算单类型
        },
        items:[
          {list:{}},
          {list:{}}
        ],
        quote:"",
        quoteLines:'',
        compute:{},
        isShow:false,
        project_id:''

      }
    },

    mounted() {
      this.query.approve_step.push('step0')
      this.setList(true)
      this.setQuotesList(true)
      this.project_id=this.$route.query.project_id
    },
    activated(){


    },
    computed:{
      ...mapGetters(['chosenCompany']),
    },
    methods:{

      onTabClick(index){
        if(index===this.tabIndex){
          return
        }
        this.tabIndex=index
      },
      onMenuClick(){
        this.$router.forward('/customerOrder/compute/detail/new/null')
      },
      onInfinite(done) {
        const that = this;
        if(that.pageModels[this.tabIndex].total_page<=that.pageModels[this.tabIndex].curr_page){
          done()
          return
        }
        setTimeout(function () {
          if(that.pageModels[that.tabIndex].total_page<2){
            done()
          }else{
            if(that.tabIndex===1){

              that.setList();
            }else{
              that.setQuotesList()
            }
            done()
          }
        },400)
      },
      setQuotesList(refresh){
        const that = this;
        const requestData={
          company_id:this.chosenCompany.company_id,
          curr_page:refresh?1:++that.pageModels[that.tabIndex].curr_page,
          page_size:that.pageModels[that.tabIndex].page_size,
          query:this.query
        }
        list(requestData).then(res=>{
          if (res.quote_list.length>0) {
            if(!refresh) {
              that.items[0].list = that.items[that.tabIndex].list.concat(res.quote_list);
            }else {
              that.items[0].list = res.quote_list
            }
            that.pageModels[0].total_page = res.total_page
            that.pageModels[0].curr_page = res.curr_page
            that.pageModels[0].total_count = res.total_count
          }
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      setList(refresh){
        const that = this;
        const requestData={
          curr_page:refresh?1:++that.pageModels[1].curr_page,
          page_size:that.pageModels[1].page_size,
          query:this.query
        }
        orderList(requestData).then(res=>{
          if (res.cst_order_list.length>0) {
            if(!refresh){
              alert(1)
              that.items[1].list= that.items[1].list.concat(res.cst_order_list);
            }else{

              that.items[1].list = res.cst_order_list;
            }
            that.pageModels[1].total_page = res.total_page;
            that.pageModels[1].curr_page = res.curr_page;
            that.pageModels[1].total_count = res.total_count;
          }

        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      referTo(item){
        event.cancelBubble = true;
        if(this.tabIndex===0){
          const reqData = {
            project_id	:this.project_id,
            quote_id:item.quote_id
          }
          fromQuote(reqData).then(res => {
            $toast.show('参考成功',600)
            $router.go(-1)
          }).catch(error => {
            $toast.show(error.description)
          })

        }else{
          const reqData = {
            project_id:item.project_id,//	核算单Id	array<number>
            current_project_id:this.project_id
          }
          orderAssociate(reqData).then(res => {
            $toast.show('参考成功',600)
            $router.go(-1)
          }).catch(error => {
            $toast.show(error.description)
          })
        }

      },
      referToInfo(item){
        this.isShow=true
        if(this.tabIndex===1){
          const requestData = {
            compute_id: item.compute_id
          }
          // computeDetail(requestData).then(res => {
          //   this.compute = res
          //   $toast.hide()
          //
          // }).catch(error => {
          //   this.isShow=false
          //   $toast.show(error.description,600)
          // })
        }else{
          this.quote_id=item.quote_id
          this.getData()
        }

      },
      getData(){//报价单头信息
        const that = this;
        const requestData = {
          quote_id:that.quote_id
        }
        headDetail(requestData).then(res=>{
          that.quote = res
          that.getQuoteLines()
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      getQuoteLines(){//报价单行信息
        const reqData={
          quote_id:this.quote_id
        }
        quoteLines(reqData).then(res=>{
          this.quoteLines = res.quote_line_list;
        }).catch(error=>{
          $toast.show(error.description)
        })
      },

    },
    watch:{

    }
  }
</script>
<style lang="scss" scoped>
  .content {
    position: relative;
    height: 100%;
  }
  p{
    line-height: 16px;padding:1px 0;margin: 0;
    font-size: 12px;
    text-overflow:ellipsis;
  }
.referTo{
  background: #416eb6;
  position: absolute;
  width: 50px;
  text-align: center;
  height: 20px;
  right: 10px;
  top: 50%;
  margin-top: -10px;
  line-height: 20px;
  border-radius: 3px;
  color: #fff;
}
  .referToInfo{
    width: 100%;height: 100%;
    position: absolute;
    left: 0;
    bottom: 0;
    z-index: 50;
    background: rgba(16,8,6,.3);
    &>.quotes{
      position: absolute;
      width: 260px;
      height: 383px;
      left: 50%;
      top: 50%;
      margin-left: -130px;
      margin-top: -200px;
      background: #fff;
      border-radius:6px ;
      overflow-x: hidden;
      overflow-y: scroll;
      padding: 8px ;
      box-sizing: border-box;
      &>p{
        line-height: 20px;
        border-bottom: 1px solid #ddd;
        margin: 0;
        font-size: 12px;
        padding:  5px 0;
        &:last-of-type{
          border-bottom: 0px;
        }
      }
    }
    &>.infoBox{
      width: 200px;
      height: 170px;
      background: #ffffff;
      position: absolute;
      left: 50%;
      top: 50%;
      margin-left: -100px;
      margin-top: -90px;
      border-radius:6px ;
      padding: 8px 5px;

      &>p{
        line-height: 20px;
        border-bottom: 1px solid #ddd;
        margin: 0;
        font-size: 12px;
        padding:  5px;
        &:last-of-type{
          border-bottom: 0px;
        }
      }
    }
  }
</style>

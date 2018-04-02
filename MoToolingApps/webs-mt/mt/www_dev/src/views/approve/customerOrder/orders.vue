<template>
  <div class="mw-page page"   v-nav="{hideNavbar:true}">

    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>待审批客户定单列表</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content has-tab">
      <!--<div style="height: 60px">-->
        <!--<tabs :tab-items="tabs" :tab-index="tabIndex" :on-tab-click="onTabClick"></tabs>-->
      <!--</div>-->
      <!--<div style="flex: 1;">-->
        <div :class="['content',tabIndex===0 ? 'active' : '']">
          <scroll class="page-content"
                  :on-infinite="onInfinite" :on-refresh="onRefresh">
            <div v-for="(item, index) in items[0].list" :key="index"  >
              <item @click.native="onItemClick(item)">
                <span>业务员:{{item.salesman_name}}</span>  <span>订单编号:{{item.project_no}}</span>
                <span>客户:{{item.cst_name}}</span>  <span>订单日期:{{item.order_accept_date}}</span>
              </item>
            </div>
            <div v-if="pageModels[0].curr_page >= pageModels[0].total_page" slot="infinite" class="text-center">没有更多数据</div>
          </scroll>
        </div>

      </div>
    <!--</div>-->
  </div>
</template>
<script>
  import { mapGetters } from 'vuex'
  import { cstSupSearch } from '@/api/supply'
  import swipeItem from '@/components/swipeItem'
  import _ from 'lodash'
  import { list} from '@/api/customerOrder/order'
  export default{
    name:'approveOrders',
    components:{
      swipeItem
    },
    data(){
      return{
//        tabs: [   //tabs 标签
//          "待审批", "我审批的"
//        ],
        tabIndex:0,
        pageModels:[
          {curr_page:0,page_size:10},
          {curr_page:0,page_size:10},
        ],
        query:{       //查询条件
          start_date:'',    //报价开始日期
          end_date:'',      //报价日期
          prod_name:'',     //产品名称
          quote_no:'',      //报价单编号
          approve_step:[], //审批步骤
          cst_company_id:[], //选择的公司id数组
          salesman:[],        //业务员
          type:[]             //报价单类型
        },
        items:[ //核算单列表,每个list代表某一状态的list
          { list:[] },
          { list:[] },
        ],
        showFilter:false,   //是否显示筛选框
      }
    },

    mounted() {
      this.query.approve_step.push('step20')
      this.query.approve_step.push('step30')
    },
    activated(){
      this.setList(true)
    },
    computed:{
      ...mapGetters(['chosenCompany']),
      chosenTypes(){
        return this.types.filter(item=>{
          return item.chosen
        })
      },
    },
    methods: {
      onInfinite(done) {
        const that = this;
        if (that.pageModels[that.tabIndex].total_page <= that.pageModels[that.tabIndex].curr_page) {
          done()
          return
        }
        setTimeout(function () {
          if (that.pageModels[that.tabIndex].total_page < 2) {
            done()
          } else {
            that.setList();
            done()
          }
        }, 400)
      },
      onRefresh(done){
        const that = this
        setTimeout(function () {
          that.setList(true);
          done()
        }, 400)
      },

      setList(refresh){
        const that = this;
        const requestData = {
          company_id: this.chosenCompany.company_id,
          curr_page: refresh?1:that.pageModels[that.tabIndex].curr_page+1,
          page_size: that.pageModels[that.tabIndex].page_size,
          query: this.query
        }
        list(requestData).then(res => {
          console.log(res)
          if (!refresh) {
            that.items[that.tabIndex].list = that.items[that.tabIndex].list.concat(res.cst_order_list);
          }else {
            that.items[that.tabIndex].list = res.cst_order_list;
          }
          that.pageModels[that.tabIndex].total_page = res.total_page;
          that.pageModels[that.tabIndex].curr_page = res.curr_page;
          that.pageModels[that.tabIndex].total_count = res.total_count;

        }).catch(error => {
          $toast.show(error.description)
        })
      },

      onItemClick(item){
        this.$router.forward({
          path:'/customerOrder/order/previewDetail',
          query:{
            project_id:item.project_id,
            btnShow:'approve'
          }
        })
      },
    }
  }
</script>
<style lang="scss" scoped>
  .content{
    position: relative;height: 100%;width: 100%;
    display: none;
  }
  .content.active{
    display: block;
  }
  .item{
    span{
      width: 50%;
      float: left;
      text-overflow: ellipsis;
      white-space: nowrap;
      padding: 5px;
      overflow: hidden;
    }
  }


</style>

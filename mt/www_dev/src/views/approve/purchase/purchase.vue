<template>
  <div class="mw-page page"   v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>待审批采购单</span>
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
                采购单号: {{item.purch_no}}
                <!--<span>项目模号:{{item.mold_no}}</span>-->
                <!--<span>产品名称:{{item.prod_name}}</span>  <span>产品类型:{{item.prod_type}}</span>-->
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
  import { purchaseList } from '@/api/purchase/purchase'
  export default{
    name:'approvePurchases',
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
          app_flag:[], //审批步骤
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
      this.query.app_flag = 'step30'
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
        const requestData={
          curr_page:refresh?1:that.pageModels[that.tabIndex].curr_page+1,
          page_size:that.pageModels[that.tabIndex].page_size,
          query:this.query
        }
        purchaseList(requestData).then(res=>{
          console.log(res)
          if (!refresh) {
            that.items[that.tabIndex].list = that.items[that.tabIndex].list.concat(res.purch_list)
          }else {
            that.items[that.tabIndex].list = res.purch_list
          }
          that.pageModels[that.tabIndex].total_page = res.total_page
          that.pageModels[that.tabIndex].curr_page = res.curr_page
          that.pageModels[that.tabIndex].total_count = res.total_count
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      onItemClick(item){
        this.$router.forward('/purchase/purchase/detail/approve/'+item.purch_id)
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

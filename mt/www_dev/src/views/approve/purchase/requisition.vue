<template>
  <div class="mw-page page"   v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>待审批请购单</span>
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
                <p><span style="width: 100%">请购单号: {{item.request_no}}</span></p>
                <p><span>请购日期: {{item.request_date}}</span></p>

                <span>请购部门: {{item.dep_name}}</span>  <span>请购人: {{item.request_man_name}}</span>
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
  import { requisitionList } from '@/api/purchase/requisition'
  export default{
    name:'approveRequisitions',
    data(){
      return{
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
        items:[ //每个list代表某一状态的list
          { list:[] },
          { list:[] },
        ],
        showFilter:false,   //是否显示筛选框
      }
    },

    mounted() {
      this.query.approve_step = ['step20','step30']
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
        requisitionList(requestData).then(res=>{
          if (!refresh) {
            that.items[that.tabIndex].list = that.items[that.tabIndex].list.concat(res.request_list)
          }else {
            that.items[that.tabIndex].list = res.request_list
          }
          that.pageModels[that.tabIndex].total_page = res.total_page
          that.pageModels[that.tabIndex].curr_page = res.curr_page
          that.pageModels[that.tabIndex].total_count = res.total_count
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      onItemClick(item){
        this.$router.forward('/purchase/requisition/detail/approve/'+item.request_id)
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

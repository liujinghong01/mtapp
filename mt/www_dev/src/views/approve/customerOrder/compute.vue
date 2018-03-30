<template>
  <div class="mw-page page" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>待审批核算单列表</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content has-tab">
      <!--<div style="height: 60px">-->
        <!--<tabs :tab-items="tabs" :tab-index="tabIndex" :on-tab-click="onTabClick"></tabs>-->
      <!--</div>-->
      <!--<div style="flex: 1">-->
        <div :class="['content',tabIndex===0 ? 'active' : '']">
          <scroll class="page-content"
                  :on-infinite="onInfinite" style="-webkit-overflow-scrolling: touch" :on-refresh="onRefresh">
            <div v-for="(item, index) in items[0].list" :key="index"  >
              <swipe-item swipeItemText="删除" @click.native="onItemClick(item,'approve')" :canSwipe="false"  :obj="item">
                <p>制品:{{item.prod_info}}</p>
                <p>编号:{{item.compute_no}}</p>
                <p>审批状态:<span v-if="item.approve_step==='step20'">初审</span> <span v-else>复审</span></p>
                <p>类型:{{item.type_name}}</p>
                <p>核算日期:{{item.created_at}}</p>
              </swipe-item>
            </div>
            <div v-if="pageModels[0].curr_page >= pageModels[0].total_page" slot="infinite" class="text-center">没有更多数据</div>
          </scroll>
        </div>
        <!--<div :class="['content',tabIndex===1 ? 'active' : '']">-->
          <!--<scroll class="page-content"-->
                  <!--:on-infinite="onInfinite">-->
            <!--<div v-for="(item, index) in items[1].list" :key="index"  >-->
              <!--<swipe-item :canSwipe="false" @click.native="onItemClick(item)" :obj="item">-->
                <!--<span>类型:{{item.type_name}}</span>  <span>编号:{{item.cst_mold_id}}</span>-->
                <!--<span>客户:{{item.cst_company_name}}</span>  <span>核算日期:{{item.created_at}}</span>-->
              <!--</swipe-item>-->
            <!--</div>-->

            <!--<div v-if="pageModels[1].curr_page >= pageModels[1].total_page" slot="infinite" class="text-center">没有更多数据</div>-->
          <!--</scroll>-->
        <!--</div>-->
      </div>
    <!--</div>-->
  </div>
</template>
<script>
  import { mapGetters } from 'vuex'
  import { cstSupSearch } from '@/api/supply'
  import swipeItem from '@/components/swipeItem'
  import _ from 'lodash'
  import {list,del} from '@/api/customerOrder/compute'
  import sess from '@/utils/sess'
  export default{
    name:'approveComputes',
    components:{
      swipeItem
    },
    data(){
      return{
//        tabs: [   //tabs 标签
//          "审批中", "已审批"
//        ],
        tabIndex:0,
        pageModels:[
          {curr_page:0,page_size:10},
          {curr_page:0,page_size:10},
        ],
        query:{       //查询条件
          compute_no:"",    //核算单编号
          approve_step:[], //审批步骤
          cst_company_id:[], //选择的公司id数组
          cst_prod_name:'',   //产品名称
          type:[]             //核算单类型
        },
        items:[ //核算单列表,每个list代表某一状态的list
          { list:[] },
          { list:[] },
        ],
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
    },
    methods:{
      onMenuClick(){
        this.$router.forward('/customerOrder/compute/new')
      },
      onTabClick(index){  //切换审批状态
        console.log('onTabClick');
        const that = this;

        if(index===this.tabIndex){
          return
        }
        switch (index) {
          case 0:{
            that.query.approve_step.length = 0
            that.query.approve_step.push('step20')
            that.query.approve_step.push('step30')
            break
          }
          case 1:{
            that.query.approve_step.length = 0
            that.query.approve_step.push('step0')
            break
          }
        }
        that.tabIndex = index;
        if(this.items[that.tabIndex].list.length===0){
          this.setList()
        }
      },
      onInfinite(done) {
        const that = this;
        if(that.pageModels[that.tabIndex].total_page<=that.pageModels[that.tabIndex].curr_page){
          done()
          return
        }
        setTimeout(function () {
          if(that.pageModels[that.tabIndex].total_page<2){
            done()
          }else{
            that.setList();
            done()
          }
        },400)
      },
      onRefresh(done){
        const that = this;
        setTimeout(function () {
          that.setList(true);
          done()
        }, 400)
      },
      setList(refresh){
        const that = this;
        const requestData={
          curr_page:refresh?1:++that.pageModels[that.tabIndex].curr_page,
          page_size:that.pageModels[that.tabIndex].page_size,
          query:this.query
        }
        list(requestData).then(res=>{
          console.log(res)
          if(!refresh){
            that.items[that.tabIndex].list = that.items[that.tabIndex].list.concat(res.list);
          }else{
            that.items[that.tabIndex].list = res.list;
          }

          that.pageModels[that.tabIndex].total_page = res.total_page;
          that.pageModels[that.tabIndex].curr_page = res.curr_page;
          that.pageModels[that.tabIndex].total_count = res.total_count;
        }).catch(error=>{
          $toast.show(error.description)
        })
      },

      onItemClick(item,isSubmit){
        // this.$router.forward('/customerOrder/compute/detail/approve/'+item.compute_id)
        this.$router.forward({
          path:'/customerOrder/computeDetailPreview',
          query:{
            compute_id:item.compute_id,
            isSubmit:isSubmit,

          }
        })
      },

    },
    watch:{
      'searchCompanyName':function () {
        if(this.searchCompanyName.trim().length===0){
          this.searchCompanyName=''
          this.cstList.length=0;
          return
        }
        this.searchCompany()
      }
    }
  }
</script>
<style lang="scss" scoped>

  .content{
    height: 100%;
    display: none;
  }
  .item{
    p{
      padding: 2px 0;
      span{
        color: #416eb6;
      }
    }

  }
  .active{
    display: block;
  }

</style>

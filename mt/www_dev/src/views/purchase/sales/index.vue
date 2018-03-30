<template>
  <div class="mw-page page purchase" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>销售定单列表</span>
      <i class="right" @click="onMenuClick">筛选</i>
    </div>
    <div class="mw-page-content" style="display: flex;flex-direction: column">
      <div>
        <div class="mw-tab">
          <ul class="tab-content">
            <li :class="{'active':tabIndex===0}" @click="onTabClick(0)">未完结</li>
            <li :class="{'active':tabIndex===1}" @click="onTabClick(1)">已完结</li>
          </ul>
        </div>
      </div>
      <div class="divider"></div>
      <div style="flex: 1;">
        <div class="content"  v-show="tabIndex===0">
          <scroll class="page-content" :on-infinite="onInfinite"  style="-webkit-overflow-scrolling: touch">
            <div v-for="(item, index) in inquiryList" :key="index">
              <item class="item-icon-right"  @click.native="onItemClick(item)">
                编号 : {{item.inquiry_no}}
                <span class="item-note normal" v-show="item.deal_flag>0" >查看反馈</span>
                <i class="icon ion-ios-arrow-right" style="color: #DDD;"></i>
              </item>
            </div>
            <div v-if="pageModels[0].curr_page >= pageModels[0].total_page" slot="infinite" class="text-center">没有更多数据</div>
          </scroll>
        </div>
        <div class="content"  v-show="tabIndex===1">
          <scroll class="page-content" :on-infinite="onInfinite"  style="-webkit-overflow-scrolling: touch">
            <div v-for="(item, index) in inquiryList" :key="index">
              <item class="item-icon-right"  @click.native="onItemClick(item)">
                编号 : {{item.inquiry_no}}
                <span class="item-note normal" v-show="item.deal_flag>0" >查看反馈</span>
                <i class="icon ion-ios-arrow-right" style="color: #DDD;"></i>
              </item>
            </div>
            <div v-if="pageModels[1].curr_page >= pageModels[1].total_page" slot="infinite" class="text-center">没有更多数据</div>
          </scroll>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import CstItem from "../../../components/cstItem";
  import { orderList,orderDetal} from '@/api/purchase/salesOrder'
  export default{
    components: {
      CstItem,
    },
    name:'salesList',
    data(){
      return {
        inquiryList:[
          {inquiry_id:'x01111',inquiry_no:'00000001',deal_flag:0},
          {inquiry_id:'x01111',inquiry_no:'00000003',deal_flag:0},
          {inquiry_id:'x01111',inquiry_no:'00000004',deal_flag:1},
          {inquiry_id:'x01111',inquiry_no:'00000005',deal_flag:2},
          {inquiry_id:'x01111',inquiry_no:'00000006',deal_flag:1},
          {inquiry_id:'x01111',inquiry_no:'00000007',deal_flag:2},
          {inquiry_id:'x01111',inquiry_no:'00000008',deal_flag:0},
          {inquiry_id:'x01111',inquiry_no:'00000008',deal_flag:0},
          {inquiry_id:'x01111',inquiry_no:'00000008',deal_flag:0},
          {inquiry_id:'x01111',inquiry_no:'00000008',deal_flag:0},
          {inquiry_id:'x01111',inquiry_no:'00000008',deal_flag:0},
          {inquiry_id:'x01111',inquiry_no:'00000008',deal_flag:0},
          {inquiry_id:'x01111',inquiry_no:'00000009',deal_flag:1},
          {inquiry_id:'x01111',inquiry_no:'000000010',deal_flag:0}
        ],
        list:[
          {salesList:[]},
          {salesList:[]}
        ],
        pageModels:[
          {
            curr_page:0,
            total_page:0,
            page_size:10
          },
          {
            curr_page:0,
            total_page:0,
            page_size:10
          },
        ],
        query:{

        },
        tabIndex:0
      }
    },
    methods: {
      onMenuClick(){

      },
      onInfinite(){

      },
      onTabClick(index){
        this.tabIndex = index
        if(this.list[index].salesList.length===0){

        }
      },
      onItemClick(item){
        if(item.deal_flag>0){
          this.$router.forward('/purchase/inquiry/feedback/check/'+item.inquiry_id)
        }else {
          this.$router.forward('/purchase/inquiry/detail/check/'+item.inquiry_id)
        }
      },

      getData(){
        const reqData = {
          query:this.query
        }
        orderList(reqData).then(res=>{
          if(res.sales_order_list.length>0){
            this.pageModels[this.tabIndex].curr_page = res.curr_page
            this.pageModels[this.tabIndex].page_size = res.page_size

          }else{

          }
        }).catch(error=>{
          $toast.show(error.description)
        })
      }

    }
  }
</script>

<style lang="scss" scoped>

</style>

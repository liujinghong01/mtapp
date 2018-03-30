<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>凭证列表</span>
      <i class="right" ></i>
    </div>
    <div class="mw-page-content has-tab">
      <div style="height: 60px">
        <tabs :tab-items="tabs" :tab-index="tabIndex" :on-tab-click="onTabClick"></tabs>
      </div>
      <div style="flex: 1">
        <div class="content" v-show="tabIndex===0">
          <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
            <item  v-for="item in  list[0].voucherList" @click.native="goDetail(item.voucher_no)">
                <h3 class="vi-color" style="font-size: 16px">{{item.voucher_no}}</h3>
                <div >类型 ：<span >{{ item.trans_type }}</span></div>
                <div >种类 ：<span >{{ item.mat_count }}</span></div>
                <div >物料数量 ：<span >{{ item.mat_sum }}</span></div>
                <div >凭证日期 ：<span >{{ item.trans_date }}</span></div>
                <i style="position: absolute;top: 50%;right: 0;width: 30px;height: 40px;font-size: 40px;margin-top: -20px;color: #ababab"
                   class="icon ion-ios-arrow-right"></i>
            </item>
            <div v-if="pageModels[0].curr_page >= pageModels[0].total_page" slot="infinite" class="text-center">没有更多数据</div>
          </scroll>
        </div>

        <div class="content" v-show="tabIndex===1">
          <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
            <item  v-for="item in  list[1].voucherList" @click.native="goDetail(item.voucher_no)" >
              <h3 class="vi-color" style="font-size: 16px">{{item.voucher_no}}</h3>
              <div >类型 ：<span >{{ item.trans_type }}</span></div>
              <div >种类 ：<span >{{ item.mat_count }}</span></div>
              <div >物料数量 ：<span >{{ item.mat_sum }}</span></div>
              <div >凭证日期 ：<span >{{ item.trans_date }}</span></div>
              <i style="position: absolute;top: 50%;right: 0;width: 30px;height: 40px;font-size: 40px;margin-top: -20px;color: #ababab"
                 class="icon ion-ios-arrow-right"></i>
            </item>
            <div v-if="pageModels[1].curr_page >= pageModels[1].total_page" slot="infinite" class="text-center">没有更多数据</div>
          </scroll>
        </div>

        <div class="content" v-show="tabIndex===2">
          <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
            <item  v-for="item in  list[2].voucherList" @click.native="goDetail(item.voucher_no)">
              <h3 class="vi-color" style="font-size: 16px">{{item.voucher_no}}</h3>
              <div >类型 ：<span >{{ item.trans_type }}</span></div>
              <div >种类 ：<span >{{ item.mat_count }}</span></div>
              <div >物料数量 ：<span >{{ item.mat_sum }}</span></div>
              <div >凭证日期 ：<span >{{ item.trans_date }}</span></div>
              <i style="position: absolute;top: 50%;right: 0;width: 30px;height: 40px;font-size: 40px;margin-top: -20px;color: #ababab"
                 class="icon ion-ios-arrow-right"></i>
            </item>
            <div v-if="pageModels[2].curr_page >= pageModels[2].total_page" slot="infinite" class="text-center">没有更多数据</div>
          </scroll>
        </div>

        <div class="content" v-show="tabIndex===3">
          <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
            <item  v-for="item in  list[3].voucherList" @click.native="goDetail(item.voucher_no)">
              <h3 class="vi-color" style="font-size: 16px">{{item.voucher_no}}</h3>
              <div >类型 ：<span >{{ item.trans_type }}</span></div>
              <div >种类 ：<span >{{ item.mat_count }}</span></div>
              <div >物料数量 ：<span >{{ item.mat_sum }}</span></div>
              <div >凭证日期 ：<span >{{ item.trans_date }}</span></div>
              <i style="position: absolute;top: 50%;right: 0;width: 30px;height: 40px;font-size: 40px;margin-top: -20px;color: #ababab"
                 class="icon ion-ios-arrow-right"></i>
            </item>
            <div v-if="pageModels[3].curr_page >= pageModels[3].total_page" slot="infinite" class="text-center">没有更多数据</div>
          </scroll>
        </div>
      </div>
    </div>
    <!--<div class="mw-page-footer">-->
      <!--<div class="btns">-->
        <!--<div class="btn" @click="$router.forward('/storage/scrap/detail/new/0')">-->
          <!--新建物料报废单-->
        <!--</div>-->
      <!--</div>-->
    <!--</div>-->
  </div>
</template>

<script>
  import { voucherList } from '@/api/storage/voucher'
  import SwipeItem from "../../../components/swipeItem"
  export default{
    name:'voucherList',
    components: {SwipeItem},
    data(){
      return {
        tabs:['入库单据','出库单据','盘点单据','调拨单据'],
        tabIndex:0,
        voucher_type:1,
        pageModels:[
          {curr_page:0,page_size:10,total_page:0},
          {curr_page:0,page_size:10,total_page:0},
          {curr_page:0,page_size:10,total_page:0},
          {curr_page:0,page_size:10,total_page:0}
        ],
        list:[
          {voucherList:[]},
          {voucherList:[]},
          {voucherList:[]},
          {voucherList:[]},
        ]
      }
    },
    computed:{
    },
    activated(){
    },
    methods: {
      onTabClick(index) {
        if(index===this.tabIndex) {
          return
        }
        this.tabIndex = index
        this.voucher_type=this.tabIndex+1
        if(this.list[index].length===undefined){
          this.getVoucherList('refresh')
        }
      },
      //上拉加载 分页
      onInfinite(done){
        let _this=this
        if(this.pageModels[this.tabIndex].curr_page>this.pageModels[this.tabIndex].total_page){
          done()
          return
        }
        setTimeout(function () {
          console.log(_this.pageModels)
          if(_this.pageModels[_this.tabIndex].total_page<2){
            done()
          }else{
            _this.getVoucherList(_this.tabIndex+1)
          }
          done()
        },500)
      },
      //下拉刷新
      onRefresh(done){
        let _this=this
        setTimeout(function () {
          _this.getVoucherList('refresh')
          done()
        },500)
      },
      getVoucherList(type){
        if(type==='refresh'){
          this.list[this.tabIndex].voucherList=[]
          this.pageModels[this.tabIndex].curr_page=0
        }
        const requestData={
          curr_page:this.pageModels[this.tabIndex].curr_page+1,
          page_size:this.pageModels[this.tabIndex].page_size,
          query:{
            voucher_type:this.voucher_type,
            voucher_on:''
          }
        }

        voucherList(requestData).then(res => {
          if(res.voucher_list.length>0){
            this.list[this.tabIndex].voucherList=this.list[this.tabIndex].voucherList.concat( res.voucher_list )
            this.pageModels[this.tabIndex].curr_page=res.curr_page
            this.pageModels[this.tabIndex].total_page=res.total_page
          }else{
            this.pageModels[this.tabIndex].total_page=0
          }
        }).catch(error => {
          $toast.show(error.description)
          // this.pageModels[this.tabIndex].total_page=''
        })
      },
      goDetail(voucher_no){
        this.$router.forward('/storage/voucher/Detail/'+voucher_no)
      }
    },
    mounted(){
      this.getVoucherList(1)
    }
  }
</script>

<style lang="scss" scoped>
.item>div{
  line-height: 18px;
  color: #ababab;
  span{color: #444}
}
</style>

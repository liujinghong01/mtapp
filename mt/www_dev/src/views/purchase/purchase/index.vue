<template>
  <div class="mw-page page purchase" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>采购单列表</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content has-tab">
      <div style="height: 60px">
        <tabs :tab-items="tabs" :tab-index="tabIndex" :on-tab-click="onTabClick"></tabs>
      </div>
      <div style="flex: 1;position:relative;">
        <div class="content" v-show="tabIndex===0">
          <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
            <div v-for="item in list[tabIndex].request_list" >
              <swipe-item class="req-item" v-on:ItemClick="onItemClick(item)" :obj="item" swipeItemText="删除" v-on:SwipeItemClick="delItem(item)">
                <div :class="['swipe-content',item.swipe?'active':'']">
                  <div class="req-item-title">采购单号 ：<span class="vi-color">{{item.purch_no}}</span><i class="icon ion-ios-arrow-right"></i></div>
                </div>
              </swipe-item>
            </div>
            <div v-if="pageModels[0].curr_page >= pageModels[0].total_page" slot="infinite" class="text-center">没有更多数据</div>
          </scroll>
        </div>
        <div class="content" v-show="tabIndex===1">
          <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
            <div v-for="item in list[tabIndex].request_list">
              <swipe-item class="req-item" v-on:ItemClick="onItemClick(item)" :obj="item" swipeItemText="删除" v-on:SwipeItemClick="delItem(item)">
                <div :class="['swipe-content',item.swipe?'active':'']">
                  <div class="req-item-title">采购单号 ：<span class="vi-color">{{item.purch_no}}</span><i class="icon ion-ios-arrow-right"></i></div>
                </div>
              </swipe-item>
            </div>
            <div v-if="pageModels[1].curr_page >= pageModels[1].total_page" slot="infinite" class="text-center">没有更多数据</div>
          </scroll>
        </div>
        <div class="content" v-show="tabIndex===2">
          <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
            <div v-for="item in list[tabIndex].request_list">
              <swipe-item
                @click.native="onItemClick(item)"
                :canSwipe="false"
                class="req-item"
                :obj="item"
                swipeItemText="删除"
                v-on:SwipeItemClick="delItem(item)">
                <div :class="['swipe-content',item.swipe?'active':'']">
                  <div class="req-item-title">采购单号 ：<span class="vi-color">{{item.purch_no}}</span><i class="icon ion-ios-arrow-right"></i></div>
                </div>
              </swipe-item>
            </div>
            <div v-if="pageModels[2].curr_page >= pageModels[2].total_page" slot="infinite" class="text-center">没有更多数据</div>
          </scroll>
        </div>
        <div class="content" v-show="tabIndex===3">
          <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
            <div v-for="item in list[tabIndex].request_list">
              <swipe-item class="req-item" v-on:ItemClick="onItemClick(item)" :obj="item" swipeItemText="删除" v-on:SwipeItemClick="delItem(item)">
                <div :class="['swipe-content',item.swipe?'active':'']">
                  <div class="req-item-title">采购单号 ：<span class="vi-color">{{item.purch_no}}</span><i class="icon ion-ios-arrow-right"></i></div>
                </div>
              </swipe-item>
            </div>
            <div v-if="pageModels[3].curr_page >= pageModels[3].total_page" slot="infinite" class="text-center">没有更多数据</div>
          </scroll>
        </div>
        <div class="content" v-show="tabIndex===4">
          <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
            <div v-for="item in list[4].request_list">
              <swipe-item class="req-item" v-on:ItemClick="onItemClick(item)" :obj="item" swipeItemText="删除" v-on:SwipeItemClick="delItem(item)">
                <div :class="['swipe-content',item.swipe?'active':'']">
                  <div class="req-item-title">采购单号 ：<span class="vi-color">{{item.purch_no}}</span><i class="icon ion-ios-arrow-right"></i></div>
                </div>
              </swipe-item>
            </div>
            <div v-if="pageModels[3].curr_page >= pageModels[3].total_page" slot="infinite" class="text-center">没有更多数据</div>
          </scroll>
        </div>
      </div>
    </div>
    <!--<div class="mw-page-footer">-->
      <!--<div class="btns">-->
        <!--<div class="btn" @click="$router.forward('/purchase/purchase/detail/new/null')">-->
          <!--新增-->
        <!--</div>-->
      <!--</div>-->
    <!--</div>-->
  </div>
</template>

<script>
  import SwipeItem from "../../../components/swipeItem";
  import { purchaseList,purchaseDel } from '@/api/purchase/purchase'
  export default{
    name:'purchaseList',
    components: {SwipeItem},
    data(){
      return {
        tabIndex:0,
        tabs:[
          '单价审批','一级审批','二级审批','退回','已审批'
        ],
        pageModels:[
          {curr_page:0,page_size:10,total_page:0},
          {curr_page:0,page_size:10,total_page:0},
          {curr_page:0,page_size:10,total_page:0},
          {curr_page:0,page_size:10,total_page:0},
          {curr_page:0,page_size:10,total_page:0},
        ],
        query:{
          approve_step: [],
          approve_type: ''
        },
        list:[
          {request_list:[]},
          {request_list:[]},
          {request_list:[]},
          {request_list:[]},
          {request_list:[]},
        ],
      }
    },
    created(){
      this.query.approve_type  = 0
      this.query.approve_step = ['step30']
      this.getData()
    },
    methods: {
      onTabClick(index){
        const that = this
        switch (index) {
          case 0:{
            that.query.approve_type = 0
            that.query.approve_step = ['step30']
            break
          }
          case 1:{
            that.query.approve_type = 1
            that.query.approve_step = ['step30']
            break
          }
          case 2:{
            that.query.approve_type = 2
            that.query.approve_step = ['step30']
            break
          }
          case 3:{
            that.query.approve_type = 0
            that.query.approve_step = ['step11']
            break
          }
          case 4:{
            that.query.approve_type = ''
            that.query.approve_step = ['step0','step00']
            break
          }
        }
        that.tabIndex = index;
        if(this.list[that.tabIndex].request_list.length===0){
//          this.getData()
        }
        this.getData(true)
      },
      onInfinite(done){
        const that = this;
        if(that.pageModels[that.tabIndex].total_page<=that.pageModels[that.tabIndex].curr_page){
          done()
          return
        }
        setTimeout(function () {
          if(that.pageModels[that.tabIndex].total_page<2){
            done()
          }else{
            that.getData();
            done()
          }
        },400)
      },
      onRefresh(done){
        const that = this
        setTimeout(function () {
          that.getData(true)
          done()
        },500)
      },
      onItemClick(item){
        this.$router.forward('/purchase/purchase/detail/check/'+item.purch_id)
      },
      getData(refresh){
        const that = this;
        const requestData={
          curr_page:refresh?1:that.pageModels[that.tabIndex].curr_page+1,
          page_size:that.pageModels[that.tabIndex].page_size,
          query:this.query
        }
        purchaseList(requestData).then(res=>{
          if (!refresh) {
            that.list[that.tabIndex].request_list = that.list[that.tabIndex].request_list.concat(res.purch_list)
          }else {
            that.list[that.tabIndex].request_list = res.purch_list
          }
          that.pageModels[that.tabIndex].total_page = res.total_page;
          that.pageModels[that.tabIndex].curr_page = res.curr_page;
          that.pageModels[that.tabIndex].total_count = res.total_count;
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      delItem(item){
        $dialog.confirm({
          // 设置为ios样式
          theme: 'ios',
          // 标题
          title: '确定要删除吗？',
          // 取消按钮文本
          cancelText: '取消',
          // 确定按钮文本
          okText: '确定'
        }).then(res=>{
          if(res){
            purchaseDel({
              purch_id:item.purch_id
            }).then(res=>{
              $toast.show('删除成功')
              this.list[this.tabIndex].request_list = this.list[this.tabIndex].request_list.filter(o=>{
                return o.purch_id !== item.purch_id
              })
            }).catch(error=>{
              $toast.show(error.description)
            })

          }
        })
      }
    }
  }
</script>

<style lang="scss" scoped>
</style>

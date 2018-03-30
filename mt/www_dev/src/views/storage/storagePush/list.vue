<template>
    <div class="mw-page page storage" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>入库单列表</span>
            <i class="right"></i>
        </div>
        <div class="mw-page-content has-tab">
          <div style="height: 60px">
            <tabs :tab-items="tabs" :tab-index="tabIndex" :on-tab-click="onTabClick"></tabs>
          </div>
          <div style="flex: 1">
            <div class="content" v-show="tabIndex===0">
              <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
                  <swipe-item  v-for="item in  list[0].inventoryList" class="req-item" v-on:ItemClick="onItemClick(item)" :obj="item" swipeItemText="删除" v-on:SwipeItemClick="delItem(item)">
                    <div class="req-item-title">盘点单号 ：<span class="vi-color">{{item.check_bill_no}}</span><i class="icon ion-ios-arrow-right"></i></div>
                    <div class="req-item-content">
                      <p>盘点仓库 : {{item.store_house_name}}</p>
                    </div>
                    <div class="req-item-content">
                      <p>盘点物料类型 : {{item.mat_type_name}}</p>
                    </div>
                  </swipe-item>
                <div v-if="pageModels[0].curr_page >= pageModels[0].total_page" slot="infinite" class="text-center">没有更多数据</div>
              </scroll>
            </div>
            <div class="content" v-show="tabIndex===1">
              <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
                <swipe-item v-for="item in list[1].inventoryList"  class="req-item" v-on:ItemClick="onItemClick(item)" :obj="item" swipeItemText="删除" v-on:SwipeItemClick="delItem(item)">
                  <div class="req-item-title">盘点单号 ：<span class="vi-color">{{item.check_bill_no}}</span><i class="icon ion-ios-arrow-right"></i></div>
                  <div class="req-item-content">
                    <span>盘点仓库 : {{item.store_house_name}}</span>
                    <span>盘点物料类型 : {{item.mat_type_name}}</span>
                  </div>
                </swipe-item>
                <div v-if="pageModels[1].curr_page >= pageModels[1].total_page" slot="infinite" class="text-center">没有更多数据</div>
              </scroll>
            </div>
          </div>
        </div>
        <div class="mw-page-footer">
          <div class="btns">
            <div class="btn" @click="$router.forward('/storage/storagePush/new')">
                新建入库单
            </div>
          </div>
        </div>
    </div>
</template>

<script>
  import { inventoryList } from '@/api/storage/inventory'
  import SwipeItem from "../../../components/swipeItem";
    export default{
      name:'storagePushList',
      components: {SwipeItem},
      data(){
        return {
          tabs:['未提交','已提交'],
          tabIndex:0,
          approve_step:'step10',
          pageModels:[
            {curr_page:0,page_size:10,total_page:0},
            {curr_page:0,page_size:10,total_page:0},
          ],
          list:[
            {inventoryList:[]},
            {inventoryList:[]},
          ]
        }
      },
      computed:{

      },
      activated(){
        this.getData()
      },
      methods: {

        onTabClick(index) {
          if(index===this.tabIndex) {
            return
          }
          this.tabIndex = index
        },

        //上拉加载 分页
        onInfinite(done){
          const that = this;
          if(that.pageModels[this.tabIndex].total_page<=that.pageModels[this.tabIndex].curr_page){
            done()
            return
          }
          setTimeout(function () {
            if(that.pageModels[this.tabIndex].total_page<2){
              done()
            }else{
              that.getData();
              done()
            }
          },500)
        },
        //下拉刷新
        onRefresh(done){
          const that = this
          setTimeout(function () {
            that.pageModels[that.tabIndex].curr_page = 0
            that.getData(true)
            done()
          },500)
        },
        //获取数据
        getData(refresh){
          console.log('获取数据')

        },
        onItemClick(item){

        }
      }
    }
</script>

<style lang="scss" scoped>

</style>

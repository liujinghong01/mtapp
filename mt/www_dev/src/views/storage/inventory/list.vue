<template>
    <div class="mw-page page storage" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>盘点单列表</span>
            <i class="right"></i>
        </div>
        <div class="mw-page-content has-tab">
          <div style="height: 60px">
            <tabs :tab-items="tabs" :tab-index="tabIndex" :on-tab-click="onTabClick"></tabs>
          </div>
          <div style="flex: 1">
            <div class="content" v-show="tabIndex===0">
              <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
                  <swipe-item  v-for="item in  list[0].inventoryList" class="req-item" @click.native="onItemClick(item)" :obj="item" swipeItemText="删除" v-on:SwipeItemClick="delItem(item)" :canSwipe=false>
                    <div class="req-item-title">盘点单号 ：<span class="vi-color">{{item.check_bill_no}}</span><i class="icon ion-ios-arrow-right"></i></div>
                    <div class="req-item-content">
                      <span>盘点仓库 : {{item.store_house_name}}</span>
                    </div>
                    <div class="req-item-content">
                      <span>盘点物料类型 :
                        <span v-for="mat in item.mat_type_list">{{mat.mat_type_name}} </span>
                      </span>
                    </div>
                  </swipe-item>
                <div v-if="pageModels[0].curr_page >= pageModels[0].total_page" slot="infinite" class="text-center">没有更多数据</div>
              </scroll>
            </div>
            <div class="content" v-show="tabIndex===1">
              <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
                <swipe-item v-for="item in list[1].inventoryList"  class="req-item" @click.native="onItemClick(item)" :obj="item" swipeItemText="删除" v-on:SwipeItemClick="delItem(item)" :canSwipe=false>
                  <div class="req-item-title">盘点单号 ：<span class="vi-color">{{item.check_bill_no}}</span><i class="icon ion-ios-arrow-right"></i></div>
                  <div class="req-item-content">
                    <span>盘点仓库 : {{item.store_house_name}}</span>
                  </div>
                  <div class="req-item-content">
                    <span>盘点物料类型 :
                      <span v-for="mat in item.mat_type_list">{{mat.mat_type_name}} </span>
                    </span>
                  </div>
                </swipe-item>
                <div v-if="pageModels[1].curr_page >= pageModels[1].total_page" slot="infinite" class="text-center">没有更多数据</div>
              </scroll>
            </div>
          </div>
        </div>
        <div class="mw-page-footer">
          <div class="btns">
            <div class="btn" @click="$router.forward('/storage/inventory/detail/new')">
                新增盘点
            </div>
          </div>
        </div>
    </div>
</template>

<script>
  import { inventoryList } from '@/api/storage/inventory'
  import SwipeItem from "../../../components/swipeItem";
    export default{
        name:'inventoryList',
      components: {SwipeItem},
      data(){
        return {
          tabs:['待盘','已盘'],
          tabIndex:0,
          liquidate_flag:'step10',
          pageModels:[
            {curr_page:0,page_size:10,total_page:0},
            {curr_page:0,page_size:10,total_page:0},
          ],
          list:[
            {inventoryList:[

            ]},
            {inventoryList:[]},
          ],
          query:{
            approve_step:[],
            liquidate_flag:['2']
          }
        }
      },
      computed:{

      },
      activated(){
        this.getData(true)
      },
      methods: {
        onTabClick(index) {
          if(index===this.tabIndex) {
            return
          }
          this.tabIndex = index
          if (index === 0) {
            this.query.liquidate_flag = ['2']
          } else {
            this.query.liquidate_flag = ['1']
          }
          if (this.list[index].inventoryList.length === 0) {
            this.getData(true)
          }
        },
        //将列表中盘点的物料类型名称转换成数组
        filterMats(item){
          let mats = ''
          item.mat_type_list.filter(o=>{
            mats += o.mat_type_name
          })
          return mats
        },
        //上拉加载 分页
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
          const reqData = {
            curr_page:refresh?1:1+this.pageModels[this.tabIndex].curr_page,
            page_size:this.pageModels[this.tabIndex].page_size,
            query:this.query
          }
          inventoryList(reqData).then(res=>{
            if(res.check_bill_list.length>0){
              if(refresh){
                this.list[this.tabIndex].inventoryList.length = 0
              }
              this.pageModels[this.tabIndex].curr_page = res.curr_page
              this.pageModels[this.tabIndex].total_page = res.total_page
              this.list[this.tabIndex].inventoryList = this.list[this.tabIndex].inventoryList.concat(res.check_bill_list)
            }
          }).catch(error=>{
            $toast.show(error.description)
          })
        },
        onItemClick(item){
          this.$router.forward({path:'/storage/inventory/detail',query:{inventory:JSON.stringify(item)}})
        }
      }
    }
</script>

<style lang="scss" scoped>

</style>

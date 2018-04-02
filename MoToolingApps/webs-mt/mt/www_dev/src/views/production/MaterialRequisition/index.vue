<template>
    <div class="mw-page page storage" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>领料单列表</span>
            <i class="right"></i>
        </div>
        <div class="mw-page-content has-tab">
          <div style="height: 60px">
            <tabs :tab-items="tabs" :tab-index="tabIndex" :on-tab-click="onTabClick"></tabs>
          </div>
          <div style="flex: 1">
            <div class="content" v-show="tabIndex===0">
              <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
                <div v-for="item in  list[0].billList" >
                  <swipe-item class="req-item" v-on:ItemClick="onItemClick(item)" :obj="item" swipeItemText="删除" v-on:SwipeItemClick="delItem(item)">
                    <div class="req-item-title">领料单号 ：<span class="vi-color">{{item.apply_no}}</span><i class="icon ion-ios-arrow-right"></i></div>
                    <div class="req-item-content">
                      <span>部门 : {{item.dep_name}}</span>
                      <span>日期 : {{item.request_date}}</span>
                    </div>
                  </swipe-item>
                </div>
                <div v-if="pageModels[0].curr_page >= pageModels[0].total_page" slot="infinite" class="text-center">没有更多数据</div>
              </scroll>
            </div>
            <div class="content" v-show="tabIndex===1">
              <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
                <div v-for="item in list[1].billList" >
                  <swipe-item class="req-item" v-on:ItemClick="onItemClick(item)" :obj="item" swipeItemText="删除" v-on:SwipeItemClick="delItem(item)">
                    <div class="req-item-title">领料单号 ：<span class="vi-color">{{item.apply_no}}</span><i class="icon ion-ios-arrow-right"></i></div>
                    <div class="req-item-content">
                      <span>部门 : {{item.dep_name}}</span>
                      <span>日期 : {{item.request_date}}</span>
                    </div>
                  </swipe-item>
                </div>
                <div v-if="pageModels[1].curr_page >= pageModels[1].total_page" slot="infinite" class="text-center">没有更多数据</div>
              </scroll>
            </div>
            <div class="content" v-show="tabIndex===2">
              <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
                <div v-for="item in  list[2].billList" >
                  <swipe-item
                    @click.native="onItemClick(item)"
                    :canSwipe="false"
                    class="req-item"
                    v-on:ItemClick="onItemClick(item)"
                    :obj="item"
                    swipeItemText="删除"
                    v-on:SwipeItemClick="delItem(item)">
                    <div class="req-item-title">领料单号 ：<span class="vi-color">{{item.apply_no}}</span><i class="icon ion-ios-arrow-right"></i></div>
                    <div class="req-item-content">
                      <span>部门 : {{item.dep_name}}</span>
                      <span>日期 : {{item.request_date}}</span>
                    </div>
                  </swipe-item>
                </div>
                <div v-if="pageModels[2].curr_page >= pageModels[2].total_page" slot="infinite" class="text-center">没有更多数据</div>
              </scroll>
            </div>
            <div class="content" v-show="tabIndex===3">
              <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
                <div v-for="item in list[3].billList" >
                  <swipe-item class="req-item" v-on:ItemClick="onItemClick(item)" :obj="item" swipeItemText="删除" v-on:SwipeItemClick="delItem(item)">
                    <div class="req-item-title">领料单号 ：<span class="vi-color">{{item.apply_no}}</span><i class="icon ion-ios-arrow-right"></i></div>
                    <div class="req-item-content">
                      <span>部门 : {{item.dep_name}}</span>
                      <span>日期 : {{item.request_date}}</span>
                    </div>
                  </swipe-item>
                </div>
                <div v-if="pageModels[3].curr_page >= pageModels[3].total_page" slot="infinite" class="text-center">没有更多数据</div>
              </scroll>
            </div>
          </div>
        </div>
      <div class="mw-page-footer">
        <div class="btns">
          <div class="btn" @click="$router.forward({
            path:'MaterialRequisition/detaile',
            query:{
              action:'new'
            }
          })">
            新增
          </div>
        </div>
      </div>
    </div>
</template>

<script>
  import CstItem from "../../../components/cstItem";
  import {billList} from '@/api/storage/applyMatBill'
  import SwipeItem from "../../../components/swipeItem";
  export default{
      name:'MaterialRequisition',
    components: {
      SwipeItem,
      CstItem},
    data(){
      return {
        tabs:['制定中','审批中','已审批','退回'],
        tabIndex:0,
        pageModels:[
          {curr_page:0, page_size:10, total_page:0},
          {curr_page:0, page_size:10, total_page:0},
          {curr_page:0, page_size:10, total_page:0},
          {curr_page:0, page_size:10, total_page:0}
        ],
        query:{
          aplly_type:[1,2],
          approve_step:[]
        },
        list:[
          {billList:[]},
          {billList:[]},
          {billList:[]},
          {billList:[]},
        ]
      }
    },
    mounted(){
      this.query.approve_step.push('step10')
    },
    methods: {
      onTabClick(index){
        console.log(index)
        if(index===4){
            return
        }
        this.tabIndex = index
        switch (index) {
          case 0:{
            this.query.approve_step.length = 0
            this.query.approve_step.push('step10')
            break
          }
          case 1:{
            this.query.approve_step.length = 0
            this.query.approve_step.push('step20')
            this.query.approve_step.push('step30')
            break
          }
          case 2:{
            this.query.approve_step.length = 0
            this.query.approve_step.push('step0')
            break
          }
          case 3:{
            this.query.approve_step.length = 0
            this.query.approve_step.push('step11')
            break
          }
        }
        if (this.list[this.tabIndex].billList.length===0) {
          this.getData(true)
        }
      },
      //上拉加载 分页
      onInfinite(done){
        const that = this;
        if(that.pageModels[this.tabIndex].total_page<=that.pageModels[this.tabIndex].curr_page){
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
      //查看详情
      onItemClick(item){
        this.$router.forward({
            path:'MaterialRequisition/detaile',
            query:{
              action:'check',
              apply_id:item.apply_id
            }
        })
      },
      //删除
      delItem(item){

      },
      //获取数据
      getData(refresh){
        const reqData = {
          curr_page:refresh?1:this.pageModels[this.tabIndex].curr_page+1,
          page_size:this.pageModels[this.tabIndex].page_size,
          query:this.query
        }
        billList(reqData).then(res=>{
          if(res.apply_mat_list.length>0){
            if(refresh){
              this.list[this.tabIndex].billList.length = 0
            }
            this.pageModels[this.tabIndex].curr_page = res.curr_page
            this.pageModels[this.tabIndex].total_page = res.total_page
            this.list[this.tabIndex].billList = this.list[this.tabIndex].billList.concat(res.apply_mat_list)
          }
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
    },
    activated(){
      console.log('activated')
      this.getData(true)
    },
  }
</script>

<style lang="scss" scoped>

</style>

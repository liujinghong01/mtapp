<template>
    <div class="mw-page page purchase" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>请购单列表</span>
            <i class="right"></i>
        </div>
        <div class="mw-page-content has-tab">
          <div style="height: 60px">
            <tabs :tab-items="tabs" :tab-index="tabIndex" :on-tab-click="onTabClick"></tabs>
          </div>
          <div style="flex: 1;position:relative;">
            <div class="content" v-show="tabIndex===0">
              <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
                <div v-for="item in list[0].request_list" >
                  <swipe-item class="req-item"
                              v-on:ItemClick="onItemClick(item)"
                              :obj="item"
                              swipeItemText="删除"
                              v-on:SwipeItemClick="delItem(item)">
                    <div class="req-item-title">请购单号 ：<span class="vi-color">{{item.request_no}}</span><i class="icon ion-ios-arrow-right"></i></div>
                    <div class="req-item-content">
                      <span>部门 : {{item.dep_name}}</span>
                      <span>日期 : {{item.request_date}}</span>
                    </div>
                  </swipe-item>
                </div>
                <div v-if="pageModel[0].curr_page >= pageModel[0].total_page" slot="infinite" class="text-center">没有更多数据</div>
              </scroll>
            </div>
            <div class="content" v-show="tabIndex===1">
              <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
                <div v-for="item in list[1].request_list">
                  <swipe-item class="req-item"
                              v-on:ItemClick="onItemClick(item)"
                              :obj="item"
                              swipeItemText="删除"
                              :canSwipe="false"
                              v-on:SwipeItemClick="delItem(item)">
                    <div class="req-item-title">请购单号 ：<span class="vi-color">{{item.request_no}}</span><i class="icon ion-ios-arrow-right"></i></div>
                    <div class="req-item-content">
                      <span>部门 : {{item.dep_name}}</span>
                      <span>日期 : {{item.request_date}}</span>
                    </div>
                  </swipe-item>
                </div>
                <div v-if="pageModel[1].curr_page >= pageModel[1].total_page" slot="infinite" class="text-center">没有更多数据</div>
              </scroll>
            </div>
            <div class="content" v-show="tabIndex===2">
              <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
                <div v-for="item in list[2].request_list">
                  <swipe-item class="req-item"
                              v-on:ItemClick="onItemClick(item)"
                              :obj="item"
                              swipeItemText="删除"
                              :canSwipe="false"
                              v-on:SwipeItemClick="delItem(item)">
                    <div class="req-item-title">请购单号 ：<span class="vi-color">{{item.request_no}}</span><i class="icon ion-ios-arrow-right"></i></div>
                    <div class="req-item-content">
                      <span>部门 : {{item.dep_name}}</span>
                      <span>日期 : {{item.request_date}}</span>
                    </div>
                  </swipe-item>
                </div>
                <div v-if="pageModel[2].curr_page >= pageModel[2].total_page" slot="infinite" class="text-center">没有更多数据</div>
              </scroll>
            </div>
            <div class="content" v-show="tabIndex===3">
              <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
                <div v-for="item in list[3].request_list">
                  <swipe-item
                    :canSwipe="false"
                    class="req-item"
                    v-on:ItemClick="onItemClick(item)"
                    :obj="item"
                    swipeItemText="删除"
                    v-on:SwipeItemClick="delItem(item)">
                    <div class="req-item-title">请购单号 ：<span class="vi-color">{{item.request_no}}</span><i class="icon ion-ios-arrow-right"></i></div>
                    <div class="req-item-content">
                      <span>部门 : {{item.dep_name}}</span>
                      <span>日期 : {{item.request_date}}</span>
                    </div>
                  </swipe-item>
                </div>
                <div v-if="pageModel[3].curr_page >= pageModel[3].total_page" slot="infinite" class="text-center">没有更多数据</div>
              </scroll>
            </div>
          </div>
        </div>
        <div class="mw-page-footer" v-show="tabIndex===0">
            <div class="btns">
                <div class="btn" @click="$router.forward('/purchase/requisition/detail/new/null')">
                    新增
                </div>
              <div class="divider"></div>
                <div class="btn" @click="$router.forward('/purchase/requisition/history')">
                    参考
                </div>
            </div>
        </div>
    </div>
</template>

<script>
  import SwipeItem from "../../../components/swipeItem";
  import { requisitionList,requisitionDel } from '@/api/purchase/requisition'
  import sess from '@/utils/sess'
    export default{
      name:'requisitionList',
      components: {SwipeItem},
      data(){
          return {
            tabIndex:0,
            tabs:[
              '制定中',  '审批中','已审批','退回'
            ],
            pageModel:[
              {curr_page:0,page_size:10,total_page:0},
              {curr_page:0,page_size:10,total_page:0},
              {curr_page:0,page_size:10,total_page:0},
              {curr_page:0,page_size:10,total_page:0}
            ],
            query:{
              approve_step:[]
            },
            list:[
              {request_list:[]},
              {request_list:[]},
              {request_list:[]},
              {request_list:[]}
            ]
          }
        },
      mounted(){
        this.query.approve_step.push('step10')
      },
      activated(){
        this.getData(true)
      },
        methods: {
          onTabClick(index){
          if (index === this.tabIndex) {
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
                this.query.approve_step.push('step00')
                break
              }
              case 3:{
                this.query.approve_step.length = 0
                this.query.approve_step.push('step11')
                break
              }
            }
            this.getData(true)
          },
          onInfinite(done){
            const that = this;
            if(that.pageModel[that.tabIndex].total_page<=that.pageModel[that.tabIndex].curr_page){
              done()
              return
            }
            setTimeout(function () {
              if(that.pageModel[that.tabIndex].total_page<2){
                done()
              }else{
                that.getData();
                done()
              }
            },500)
          },
          onRefresh(done){
            const that = this
            setTimeout(function () {
              that.getData(true)
              done()
            },500)
          },
          onItemClick(item){
            this.$router.forward('/purchase/requisition/detail/check/'+item.request_id)
          },
          getData(refresh){

            const reqData = {
              curr_page:refresh?1:++this.pageModel[this.tabIndex].curr_page,
              page_size:this.pageModel[this.tabIndex].page_size,
              query:this.query
            }
            requisitionList(reqData).then(res=>{
              if(refresh){
                this.list[this.tabIndex].request_list = []
              }
              this.pageModel[this.tabIndex].curr_page = res.curr_page
              this.pageModel[this.tabIndex].total_page = res.total_page
              this.list[this.tabIndex].request_list = this.list[this.tabIndex].request_list.concat(res.request_list)
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
                requisitionDel({
                  request_id:item.request_id
                }).then(res=>{
                  this.list[this.tabIndex].request_list = this.list[this.tabIndex].request_list.filter(o=>{
                    return o.request_id !== item.request_id
                  })
                }).catch(error=>{

                })

              }
            })

          }
        }
    }
</script>

<style lang="scss" scoped>

</style>

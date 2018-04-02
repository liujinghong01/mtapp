<template>
    <div class="mw-page page storage" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>工序质检</span>
            <i class="right"></i>
        </div>
        <div class="mw-page-content has-tab">
          <div style="height: 60px">
            <tabs :tab-items="tabs" :tab-index="tabIndex" :on-tab-click="onTabClick"></tabs>
          </div>
          <div style="flex: 1">
            <div class="content" v-show="tabIndex===0">
              <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
                <div v-for="(item,index) in  list[0].billList" >
                  <swipe-item class="req-item" v-on:ItemClick="onItemClick(item)" :obj="item" swipeItemText="删除" v-on:SwipeItemClick="delItem(item,index)">
                    <div class="req-item-title">质检单号 ：<span class="vi-color">{{item.pop_qc_no}}</span>
                    </div>
                    <div class="row">
                      <span>工序 : {{item.proc_name}}</span>
                    </div>
                    <div  class="row">
                      <span>加工部门 : {{item.pg_name}}</span>
                    </div>
                    <div  class="row">
                      <span>质检员 : {{item.inspector}}</span>
                    </div>
                    <div  class="row">
                      <span>质检日期 : {{item.qc_date}}</span>
                    </div>
                  </swipe-item>
                </div>
                <div v-if="pageModels[0].curr_page >= pageModels[0].total_page" slot="infinite" class="text-center">没有更多数据</div>
              </scroll>
            </div>
            <div class="content" v-show="tabIndex===1">
              <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
                <div v-for="item in list[1].billList" >
                  <swipe-item class="req-item" v-on:ItemClick="onItemClick(item)" :obj="item" swipeItemText="删除" v-on:SwipeItemClick="delItem(item,index)">
                    <div class="req-item-title">质检单号 ：<span class="vi-color">{{item.pop_qc_no}}</span>
                    </div>
                    <div class="row">
                      <span>工序 : {{item.proc_name}}</span>
                    </div>
                    <div  class="row">
                      <span>加工部门 : {{item.pg_name}}</span>
                    </div>
                    <div  class="row">
                      <span>质检员 : {{item.inspector}}</span>
                    </div>
                    <div  class="row">
                      <span>质检日期 : {{item.qc_date}}</span>
                    </div>
                  </swipe-item>
                </div>
                <div v-if="pageModels[1].curr_page >= pageModels[1].total_page" slot="infinite" class="text-center">没有更多数据</div>
              </scroll>
            </div>
            <div class="content" v-show="tabIndex===2">
              <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
                <div v-for="item in  list[2].billList" >
                  <swipe-item class="req-item" v-on:ItemClick="onItemClick(item)" :obj="item" swipeItemText="删除" v-on:SwipeItemClick="delItem(item,index)">
                    <div class="req-item-title">质检单号 ：<span class="vi-color">{{item.pop_qc_no}}</span>
                    </div>
                    <div class="row">
                      <span>工序 : {{item.proc_name}}</span>
                    </div>
                    <div  class="row">
                      <span>加工部门 : {{item.pg_name}}</span>
                    </div>
                    <div  class="row">
                      <span>质检员 : {{item.inspector}}</span>
                    </div>
                    <div  class="row">
                      <span>质检日期 : {{item.qc_date}}</span>
                    </div>
                  </swipe-item>
                </div>
                <div v-if="pageModels[2].curr_page >= pageModels[2].total_page" slot="infinite" class="text-center">没有更多数据</div>
              </scroll>
            </div>
            <div class="content" v-show="tabIndex===3">
              <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
                <div v-for="item in list[3].billList" >
                  <swipe-item class="req-item" v-on:ItemClick="onItemClick(item)" :obj="item" swipeItemText="删除" v-on:SwipeItemClick="delItem(item,index)">
                    <div class="req-item-title">质检单号 ：<span class="vi-color">{{item.pop_qc_no}}</span>
                    </div>
                    <div class="row">
                      <span>工序 : {{item.proc_name}}</span>
                    </div>
                    <div  class="row">
                      <span>加工部门 : {{item.pg_name}}</span>
                    </div>
                    <div  class="row">
                      <span>质检员 : {{item.inspector}}</span>
                    </div>
                    <div  class="row">
                      <span>质检日期 : {{item.qc_date}}</span>
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
            path:'newProcedureQC',
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
  import { procedure_qc_list,purch_qc_delete } from '@/api/MaterialQC'
  import SwipeItem from "../../../components/swipeItem";
  export default{
      name:'ProcedureQC',
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
          approveStepArray:[]
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
      this.query.approveStepArray=['step10']
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
            this.query.approveStepArray=['step10']
            break
          }
          case 1:{
            this.query.approveStepArray=['step20','step30']
            break
          }
          case 2:{
            this.query.approveStepArray=['step0']
            break
          }
          case 3:{
            this.query.approveStepArray=['step11']
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
          path:'ProcedureQCdetaile',
          query:{
            action:'check',
            pop_qc_id:item.pop_qc_id
          }
        })
      },
      //删除
      delItem(item,index){
        const reqData = {
          purch_qc_id:item.purch_qc_id
        }
        $dialog.confirm({
          theme: 'ios',
          title: '确定要删除吗？',
          cancelText: '取消',
          okText: '确定'
        }).then(res=>{
          if(res){
            purch_qc_delete(reqData).then(res=>{
              this.list[this.tabIndex].billList.splice(index,1)
              $toast.show('删除成功',600)
            }).catch(error=>{
              $toast.show(error.description)
            })
          }
        })

      },
      //获取数据
      getData(refresh){
        const reqData = {
          curr_page:refresh?1:this.pageModels[this.tabIndex].curr_page+1,
          page_size:this.pageModels[this.tabIndex].page_size,
          query:this.query
        }
        procedure_qc_list(reqData).then(res=>{
          if(res.proc_qc_list.length>0){
            if(refresh){
              this.list[this.tabIndex].billList.length = 0
            }
            this.pageModels[this.tabIndex].curr_page = res.curr_page
            this.pageModels[this.tabIndex].total_page = res.total_page
            console.log(res.qc_info)

            this.list[this.tabIndex].billList = this.list[this.tabIndex].billList.concat(res.proc_qc_list)
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
.row{
  line-height: 20px;
  padding: 0;
  margin: 0;
}
</style>

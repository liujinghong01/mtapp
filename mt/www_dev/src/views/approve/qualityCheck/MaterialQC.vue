<template>
    <div class="mw-page page storage" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>待审批来料质检单</span>
            <i class="right"></i>
        </div>
        <div class="mw-page-content has-tab">
          <div style="flex: 1">
            <div class="content">
              <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
                <div v-for="item in list" >
                  <swipe-item class="req-item" v-on:ItemClick="onItemClick(item)" :obj="item" swipeItemText="删除" v-on:SwipeItemClick="delItem(item,index)">
                    <div class="req-item-title">质检单号 ：<span class="vi-color">{{item.purch_qc_no}}</span>
                      <!--<i class="icon ion-ios-arrow-right"></i>-->
                    </div>
                    <div class="row">
                      <span>类型 : {{item.dep_name}}</span>
                    </div>
                    <div  class="row">
                      <span>供应商 : {{item.sup_name}}</span>
                    </div>
                    <div  class="row">
                      <span>质检员 : {{item.inspector}}</span>
                    </div>
                    <div  class="row">
                      <span>质检日期 : {{item.qc_date}}</span>
                    </div>
                  </swipe-item>
                </div>
                <div v-if="pageModels.curr_page >= pageModels.total_page" slot="infinite" class="text-center">没有更多数据</div>
              </scroll>
            </div>
          </div>
        </div>

    </div>
</template>

<script>
  import CstItem from "../../../components/cstItem";
  import { purch_qc_list,purch_qc_delete } from '@/api/MaterialQC'
  import SwipeItem from "../../../components/swipeItem";
  export default{
      name:'approveMaterialQC',
    components: {
      SwipeItem,
      CstItem},
    data(){
      return {
        pageModels:{
          curr_page:0,
          page_size:10,
          total_page:0
        },
        query:{
          approve_step:['step20','step30']
        },
        list:[]
      }
    },
    mounted(){
    },
    methods: {

      //上拉加载 分页
      onInfinite(done){
        const that = this;
        if(that.pageModels.total_page<=that.pageModels.curr_page){
          done()
          return
        }
        setTimeout(function () {
          if(that.pageModels.total_page<2){
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
          that.pageModels.curr_page = 0
          that.getData(true)
          done()
        },500)
      },
      //查看详情
      onItemClick(item){
        this.$router.forward({
          path:'/qualityCheck/qcDetaile',
          query:{
            action:'check',
            purch_qc_no:item.purch_qc_no,
            step:'step'
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
              this.list.splice(index,1)
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
          curr_page:refresh?1:this.pageModels.curr_page+1,
          page_size:this.pageModels.page_size,
          query:this.query
        }
        purch_qc_list(reqData).then(res=>{
          if(res.qc_info.length>0){
            if(refresh){
              this.list.length = 0
            }
            this.pageModels.curr_page = res.curr_page
            this.pageModels.total_page = res.total_page
            console.log(res.qc_info)

            this.list = this.list.concat(res.qc_info)
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

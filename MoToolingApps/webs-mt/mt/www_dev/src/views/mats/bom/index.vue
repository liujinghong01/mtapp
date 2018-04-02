<template>
  <div class="mw-page page purchase" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
        <span>BOM列表</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content has-tab">
      <div style="height: 60px">
        <tabs :tab-items="tabs" :tab-index="tabIndex" :on-tab-click="onTabClick"></tabs>
      </div>
      <div style="flex: 1;position:relative;">
        <div class="content" v-show="tabIndex===0">
          <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
            <div v-for="item in list[tabIndex].bom_list" >
              <swipe-item
                @click.native="onItemClick(item)"
                :canSwipe="false"
                class="req-item"
                :obj="item"
                swipeItemText="删除"
                v-on:SwipeItemClick="delItem(item)">
                <div :class="['swipe-content',item.swipe?'active':'']">
                  <div class="req-item-title">客户名称 ：<span class="vi-color">{{item.cst_name}}</span><i class="icon ion-ios-arrow-right"></i></div>
                  <div class="req-item-title">项目模号 ：<span class="vi-color">{{item.mold_no}}</span></div>
                  <div class="req-item-content">
                    <span>产品名称 : {{item.cst_prod_name}}</span>
                    <span>产品类型 : {{prodTypeName(item.prod_type)}}</span>
                  </div>
                </div>
              </swipe-item>
            </div>
            <div v-if="pageModels[0].curr_page >= pageModels[0].total_page" slot="infinite" class="text-center">没有更多数据</div>
          </scroll>
        </div>
        <div class="content" v-show="tabIndex===1">
          <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
            <div v-for="item in list[tabIndex].bom_list">
              <swipe-item
                @click.native="onItemClick(item)"
                :canSwipe="false"
                class="req-item"
                :obj="item"
                swipeItemText="删除"
                v-on:SwipeItemClick="delItem(item)">
                <div :class="['swipe-content',item.swipe?'active':'']">
                  <div class="req-item-title">客户名称 ：<span class="vi-color">{{item.cst_name}}</span><i class="icon ion-ios-arrow-right"></i></div>
                  <div class="req-item-title">项目模号 ：<span class="vi-color">{{item.mold_no}}</span></div>
                  <div class="req-item-content">
                    <span>产品名称 : {{item.cst_prod_name}}</span>
                    <span>产品类型 : {{prodTypeName(item.prod_type)}}</span>
                  </div>
                </div>
              </swipe-item>
            </div>
            <div v-if="pageModels[1].curr_page >= pageModels[1].total_page" slot="infinite" class="text-center">没有更多数据</div>
          </scroll>
        </div>
        <div class="content" v-show="tabIndex===2">
          <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
            <div v-for="item in list[tabIndex].bom_list">
              <swipe-item
                @click.native="onItemClick(item)"
                :canSwipe="false"
                class="req-item"
                :obj="item"
                swipeItemText="删除"
                v-on:SwipeItemClick="delItem(item)">
                <div :class="['swipe-content',item.swipe?'active':'']">
                  <div class="req-item-title">客户名称 ：<span class="vi-color">{{item.cst_name}}</span><i class="icon ion-ios-arrow-right"></i></div>
                  <div class="req-item-title">项目模号 ：<span class="vi-color">{{item.mold_no}}</span></div>
                  <div class="req-item-content">
                    <span>产品名称 : {{item.cst_prod_name}}</span>
                    <span>产品类型 : {{prodTypeName(item.prod_type)}}</span>
                  </div>
                </div>
              </swipe-item>
            </div>
            <div v-if="pageModels[2].curr_page >= pageModels[2].total_page" slot="infinite" class="text-center">没有更多数据</div>
          </scroll>
        </div>
        <div class="content" v-show="tabIndex===3">
          <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
            <div v-for="item in list[tabIndex].bom_list">
              <swipe-item
                @click.native="onItemClick(item)"
                :canSwipe="false"
                class="req-item"
                :obj="item"
                swipeItemText="删除"
                v-on:SwipeItemClick="delItem(item)">
                <div :class="['swipe-content',item.swipe?'active':'']">
                  <div class="req-item-title">客户名称 ：<span class="vi-color">{{item.cst_name}}</span><i class="icon ion-ios-arrow-right"></i></div>
                  <div class="req-item-title">项目模号 ：<span class="vi-color">{{item.mold_no}}</span></div>
                  <div class="req-item-content">
                    <span>产品名称 : {{item.cst_prod_name}}</span>
                    <span>产品类型 : {{prodTypeName(item.prod_type)}}</span>
                  </div>
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
  import { bomList } from '@/api/mats'
  export default{
    name:'bomList',
    components: {SwipeItem},
    data(){
      return {
        tabIndex:0,
        tabs:[
          '编制中','审批中','已审批','退回'
//          ,'筛选'
        ],
        pageModels:[
          {curr_page:0,page_size:10,total_page:0},
          {curr_page:0,page_size:10,total_page:0},
          {curr_page:0,page_size:10,total_page:0},
          {curr_page:0,page_size:10,total_page:0},
        ],
        query:{
          app_flag:'step10',
          cst_name:'',
          prod_name:''
        },
        list:[
          {bom_list:[]},
          {bom_list:[]},
          {bom_list:[]},
          {bom_list:[]}
        ],

      }
    },
    activated(){
      this.getData(true)
    },
    methods: {
      onTabClick(index){
        const that = this
        if(index===this.tabIndex){
          return
        }
        switch (index) {
          case 0:{
            this.query.app_flag = 'step10'
            break
          }
          case 1:{
            this.query.app_flag = 'step30'

            break
          }
          case 2:{
            this.query.app_flag = 'step0'
            break
          }
          case 3:{
            this.query.app_flag = 'step11'
            break
          }
        }

        if(index===4){
          that.showFilter = true
          return
        }
        that.tabIndex = index;
//        if(this.list[that.tabIndex].bom_list.length===0){
          this.getData(true)
//        }
      },
      prodTypeName(type){
        let str = ''
        switch (parseInt(type)){
          case 1:
            str = '注塑模'
            break
          case 2:
            str = '压铸模'
            break
        }
        return str
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
        this.$router.forward('/mats/bom/nodes/check/'+item.node_id+'/'+item.bom_id+'/'+this.query.app_flag)
      },
      getData(refresh){
        const that = this;
        const requestData={
          curr_page:refresh?1:1+that.pageModels[that.tabIndex].curr_page,
          page_size:that.pageModels[that.tabIndex].page_size,
          query:this.query
        }
        bomList(requestData).then(res=>{
          console.log(res)
            that.list[that.tabIndex].bom_list = refresh ? res.bom_list : that.list[that.tabIndex].bom_list.concat(res.bom_list);
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
            this.list[this.tabIndex].bom_list = this.list[this.tabIndex].bom_list.filter(o=>{
              return o.request_id !== item.request_id
            })
          }
        })

      }
    }
  }
</script>

<style lang="scss" scoped>
  .content{
    position: absolute;
    height: 100%;
    width: 100%;
  }

</style>

<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>排产列表</span>
      <i v-if="isSearch" class="right" @click="isSearch=false">取消</i>
      <i v-else class="right" @click="isSearch=true">搜索</i>

    </div>
    <div class="mw-page-content has-tab">
      <div style="height: 60px;">
        <tabs :tab-items="tabs" :tab-index="tabIndex" :on-tab-click="onTabClick"></tabs>

      </div>
      <div v-show="isSearch" class="search">
        <input type="text" placeholder="请输入物料编码">
      </div>
      <div style="flex: 1">
        <div class="content" v-show="tabIndex===0">
          <scroll class="page-content"  style="-webkit-overflow-scrolling: touch">
            <swipe-item v-for="(item,index) in prodList" class="req-item" @click.native="onItemClick(item)"  :obj="item" :canSwipe="true" swipeItemText="删除" v-on:SwipeItemClick="delItem(item,index)">
              <div class="">
                <p>物料名称:{{item.mat_name}}</p>
                <p >物料编号:{{ item.mat_no }}</p>
              </div>
            </swipe-item>
          </scroll>
        </div>
        <div class="content" v-show="tabIndex===1">
          <scroll class="page-content" style="-webkit-overflow-scrolling: touch">
            <swipe-item v-for="(item,index) in poList" class="req-item" @click.native="onItemClick(item)"  :obj="item" :canSwipe="true" swipeItemText="删除" v-on:SwipeItemClick="delItem(item,index)">
              <div >
                <p>物料名称:{{item.mat_name}}</p>
                <p >物料编号:{{ item.mat_no }}</p>
              </div>
            </swipe-item>
          </scroll>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
  import { prodOrderList,prodOrderDelete } from '@/api/production/processRoute'
  import SwipeItem from "../../../components/swipeItem";
  import CstItem from "../../../components/cstItem"
  export default{
    name:'schedulList',
    components: {SwipeItem,CstItem},
    data(){
      return {
        tabs:['待排场','已排场'],
        isSearch:false,
        pageData:[
          {
            curr_page:0,
            page_size:'10',
            total_page:''
          },
          {
            curr_page:0,
            page_size:'10',
            total_page:''
          }
        ],
        tabIndex:0,
        prodList:[],
        poList:[]
      }
    },
    computed:{

    },
    activated(){

    },
    mounted(){
      this.getData(this.tabIndex+1)
    },
    methods: {
      onTabClick(index) {
        if(index===this.tabIndex) {
          return
        }
        this.tabIndex = index
        this.getData(this.tabIndex+1)
      },
      //获取数据
      getData(state){
        let curr_page= this.pageData[this.tabIndex].curr_page
        let total_page=this.pageData[this.tabIndex].total_page
        if(curr_page===total_page){
          return
        }
        const  requestData={
          curr_page:curr_page+1,
          page_size:this.pageData[this.tabIndex].page_size,
          query:{
            mat_id:'',
            mat_name:'',
            mat_no:'',
            mat_type:'',
            mold_no:'',
            po_id:'',
            po_no:'',
            po_status:state
          }
        }
        prodOrderList(requestData).then(res => {
          if(this.tabIndex===0){
            this.prodList=res.prod_info
          }else{
            this.poList=res.prod_info
          }

          }).catch(error => {
            $toast.show(error.description)
          })
      },
      delItem(item,index){
        const  requestData={
          prod_info:{
            po_id:item.id
          }
        }
        $dialog.confirm({
          theme: 'ios',
          title: '确定要删除吗？',
          cancelText: '取消',
          okText: '确定'
        }).then(res=>{
          if(res){
            prodOrderDelete(requestData).then(res => {
              $toast.show('删除成功')
              if(this.tabIndex===0){
                this.prodList.splice(index,1)
              }else{
                this.poList.splice(index,1)
              }
            }).catch(error => {
              $toast.show(error.description)
            })
          }
        })
      },

      onItemClick(item){
          this.$router.forward({
            path:'/production/processRouteDetaile'
            ,query:{
              action:'action',
              prod_id:item.po_id
            }
          })
      }
    }
  }
</script>
<style lang="scss" scoped>
  .search{
    height: 50px;
    width: 100%;
    position: absolute;
    left: 0;
    top:60px;
    background: #fff;
    z-index: 10;
    padding-top: 10px;
    box-sizing: border-box;
    input{
      width: 90%;
      margin: 0 auto;
      text-align: center;
      height: 30px;
      line-height: 30px;
      background: #f0f0f0;
      border-radius: 5px;

    }

  }
</style>

<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>工艺路线</span>
      <i class="right" @click="showEditAction">创建</i>
    </div>
    <div class="mw-page-content has-tab">
      <div style="height: 60px">
        <tabs :tab-items="tabs" :tab-index="tabIndex" :on-tab-click="onTabClick"></tabs>
      </div>
      <div style="flex: 1">
        <div class="content" v-show="tabIndex===0">
          <scroll class="page-content"  style="-webkit-overflow-scrolling: touch" :on-infinite="onInfinite">
            <swipe-item v-for="(item,index) in prodList" class="req-item" @click.native="onItemClick(item)"  :obj="item" :canSwipe="true" swipeItemText="删除" v-on:SwipeItemClick="delItem(item,index)">
              <div >
                <p>物料名称:{{item.mat_name}}</p>
                <p >物料编号:{{ item.mat_no }}</p>
              </div>
            </swipe-item>
            <div v-if="pageData[tabIndex].curr_page===pageData[tabIndex].total_page" slot="infinite" class="text-center">没有更多数据</div>
          </scroll>
        </div>
        <div class="content" v-show="tabIndex===1">
          <scroll class="page-content" style="-webkit-overflow-scrolling: touch" :on-infinite="onInfinite">
            <swipe-item v-for="(item,index) in popList" class="req-item" @click.native="onItemClick(item)"  :obj="item" :canSwipe="true" swipeItemText="删除" v-on:SwipeItemClick="delItem(item,index)">
              <div >
                <p>物料名称:{{item.mat_name}}</p>
                <p >物料编号:{{ item.mat_no }}</p>
              </div>
            </swipe-item>
            <div v-if="pageData[tabIndex].curr_page===pageData[tabIndex].total_page" slot="infinite" class="text-center">没有更多数据</div>
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
    name:'processRoute',
    components: {SwipeItem,CstItem},
    data(){
      return {
        tabs:['定制中','已定制'],
        pageData:[
          {
            curr_page:0,
            page_size:10,
            total_page:''
          },
          {
            curr_page:0,
            page_size:10,
            total_page:''
          }
        ],
        tabIndex:0,
        prodList:[],
        popList:[]
      }
    },
    computed:{

    },
    activated(){
      this.getData(this.tabIndex,'refresh')
    },
    mounted(){
      // this.getData(this.tabIndex)
    },
    methods: {
      onTabClick(index) {
        if(index===this.tabIndex) {
          return
        }
        this.tabIndex = index
        this.getData(this.tabIndex)
      },
      //获取数据
      getData(state,refresh){
        if(refresh==='refresh'){
          this.pageData[this.tabIndex].curr_page=0
        }
        let curr_page= this.pageData[this.tabIndex].curr_page
        let total_page=this.pageData[this.tabIndex].total_page
        // alert(curr_page+":"+total_page)
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
            po_status:state+''
          }
        }
        prodOrderList(requestData).then(res => {
          this.pageData[this.tabIndex].curr_page=res.curr_page
          this.pageData[this.tabIndex].total_page=res.total_page
          if (state===0){
            if(refresh==='refresh'){
              this.prodList=[]
            }
              this.prodList=this.prodList.concat(res.prod_info)

          }else{
            if(refresh==='refresh') {
              this.popList = []
            }
            this.popList=this.popList.concat(res.prod_info)

          }
          }).catch(error => {
            $toast.show(error.description)
          })
      },
      delItem(item,index){
        const  requestData={
          prod_info:{
            po_id:item.po_id
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
                this.popList.splice(index,1)
              }

            }).catch(error => {
              $toast.show(error.description)
            })
          }
        })
      },
      onInfinite(done) {
        setTimeout(() => {
          this.getData(this.tabIndex)
          done()
        }, 500)
      },
      onItemClick(item){
          $router.forward({
            path:'/production/processRouteDetaile'
            ,query:{
              action:'check',
              prod_id:item.po_id
            }
          })
      },
      add(){
        if(this.tabIndex===0){
          $router.forward('/production/shift/new/new')
        }else{
          $router.forward('/production/calendar/action/new')
        }

      },
      showEditAction(){
        let that = this;
        let btns = new Object();
        btns['模号'] = ()=>{
          $router.forward({
            path:'searchList',
            query:{type:'bom'}
          })
        }
        btns['物料号'] = ()=>{
          $router.forward({
            path:'searchList',
            query:{type:'mat'}
          })
        }
        $actionSheet.show({
          buttons: btns,
          title:'请选择创建类型',
          cancelText:'取消'
        })
      },
    }
  }
</script>
<style lang="scss" scoped>
</style>

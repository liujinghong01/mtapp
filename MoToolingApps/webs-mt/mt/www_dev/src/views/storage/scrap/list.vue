<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>物料报废申请单列表</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content has-tab">
      <div style="height: 60px">
        <tabs :tab-items="tabs" :tab-index="tabIndex" :on-tab-click="onTabClick"></tabs>
      </div>
      <div style="flex: 1">
        <div class="content" v-show="tabIndex===0">
          <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
            <item   v-for="item in  list[0].inventoryList" @click.native="$router.forward('/storage/scrap/detail/check/'+item.scrap_id)">
              <h3>报废单号:{{ item.scrap_no }}</h3>
              <p>种类:{{ item.mat_count }} &nbsp;&nbsp;&nbsp;报废数量:{{ item.total_qty }}</p>
            </item>
            <div v-if="pageModels[0].curr_page >= pageModels[0].total_page" slot="infinite" class="text-center">没有更多数据</div>
          </scroll>
        </div>
        <div class="content" v-show="tabIndex===1">
          <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
            <item  v-for="item in  list[1].inventoryList" @click.native="$router.forward('/storage/scrap/detail/check/'+item.scrap_id)">
              <h3 >报废单号:{{ item.scrap_no }}</h3>
              <span v-show="item.is_scraped==0" @click="scrapSubmit(item)"
                    style="position:absolute;right:0;top:0;color:#4a90e2;width:70px;height:100%;line-height:67px">执行报废</span>
              <p>种类:{{ item.mat_count }} &nbsp;&nbsp;&nbsp;报废数量:{{ item.total_qty }}</p>
            </item>
            <div v-if="pageModels[1].curr_page >= pageModels[1].total_page" slot="infinite" class="text-center">没有更多数据</div>
          </scroll>
        </div>
      </div>
    </div>
    <div class="mw-page-footer">
      <div class="btns">
        <div class="btn" @click="$router.forward('/storage/scrap/detail/new/0')">
          新建物料报废单
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import { scrapList,scrap } from '@/api/storage/scrap'
  import SwipeItem from "../../../components/swipeItem";
  export default{
    name:'scrapList',
    components: {SwipeItem},
    data(){
      return {
        tabs:['待审批','已审批'],
        tabIndex:0,
        pageModels:[
          {curr_page:0,page_size:10},
          {curr_page:0,page_size:10},
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
      this.pageModels=[{curr_page:0,page_size:10},{curr_page:0,page_size:10}]
      this.list=[{inventoryList:[]},{inventoryList:[]}]
      if(this.tabIndex==0){
        this.scrapList('step20')
      }else{
        this.scrapList('step0')
      }

    },
    methods: {
      scrapSubmit(item){
        const requestData={
          scrap_id:item.scrap_id
        }
        $dialog.confirm({
          theme: 'ios',
          title: '确定要执行报废吗？',
          cancelText: '取消',
          okText: '确定'
        }).then(res=>{
          if(res){
            scrap(requestData).then(res => {
              if(res.scrap_info){
                $toast.show('执行报废成功')
              }
            }).catch(error => {
              $toast.show(error.description)
            })
          }
        })
        event.cancelBubble = true;

      },

      onTabClick(index) {

        if(index===this.tabIndex) {
          return
        }
        this.tabIndex = index
        if(this.list[this.tabIndex].length===undefined){
          if(this.tabIndex===1){
            this.scrapList('step0')
          }else{
            this.scrapList('step20')
          }

        }
      },
      //上拉加载 分页
      onInfinite(done){
        let _this=this
        setTimeout(function () {
          if(_this.tabIndex===0){
            _this.scrapList('step20')
          }else{
            _this.scrapList('step0')
          }
          done()
        },500)
      },
      //下拉刷新
      onRefresh(done){
        setTimeout(function () {
          done()
        },500)
      },
      scrapList(step){
        if(this.pageModels[this.tabIndex].curr_page>=this.pageModels[this.tabIndex].total_page){
          return
        }
        const requestData={
          curr_page:this.pageModels[this.tabIndex].curr_page+1,
          page_size:this.pageModels[this.tabIndex].page_size,
          query:{
            approve_step:step
          }
        }
        scrapList(requestData).then(res => {
          if(res.scrap_mat_list.length>0){
            this.list[this.tabIndex].inventoryList=this.list[this.tabIndex].inventoryList.concat( res.scrap_mat_list )
            this.pageModels[this.tabIndex].curr_page=res.curr_page
            this.pageModels[this.tabIndex].total_page=res.total_page
          }else{
            this.pageModels[this.tabIndex].total_page=0
          }
        }).catch(error => {
          $toast.show(error.description)
        })
      }
    },
    mounted(){
      // this.scrapList('step20')
    }
  }
</script>

<style lang="scss" scoped>

</style>

<template>
    <div class="mw-page page purchase" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>历史请购单列表</span>
            <i class="right"></i>
        </div>
        <div class="mw-page-content has-tab">
          <div style="flex: 1;">
            <div class="content" v-show="tabIndex===0">
              <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
                <div v-for="item in request_list">
                  <item class="req-item check" >
                    <div class="checkBox active" @click="check(item)"><i :class="['icon',item.check?'ion-android-radio-button-on active':'ion-android-radio-button-off']"></i> </div>
                    <div style="width: 88%;float:right;" @click="onItemClick(item)">
                      <div class="req-item-title">请购单号 ：<span class="vi-color">{{item.request_no}}</span><i class="icon ion-ios-arrow-right"></i></div>
                      <div class="req-item-content">
                        <span>部门 : {{item.dep_name}}</span>
                        <span>日期 : {{item.request_date}}</span>
                      </div>
                    </div>
                  </item>
                </div>
                <div v-if="pageModel.curr_page >= pageModel.total_page" slot="infinite" class="text-center">没有更多数据</div>
              </scroll>
            </div>
          </div>
        </div>
        <div class="mw-page-footer">
            <div class="btns">
                <div class="btn" @click="associate">
                    参考
                </div>
            </div>
        </div>
    </div>
</template>

<script>
 import { requisitionList,requisitionRefer } from '@/api/purchase/requisition'
    export default{
     name:'requisitionLineInfo',
        data(){
          return {
            tabIndex:0,
            tabs:[
              '制定中','审批中','已审批','退回','作废','筛选'
            ],
            pageModel: {curr_page:0,page_size:10,total_page:0},
            request_list:[

            ],
            checkedItem:{  //选中的对象

            },
            query:{
              approve_step:["step0", "step00"]
            }
          }
        },
      mounted(){
        this.getData(true)
      },
        methods: {
          onTabClick(index){
            if(index===this.tabs.length-1){
              return
            }
            this.tabIndex = index
          },
          onInfinite(done){
            const that = this;
            if(that.pageModel.total_page<=that.pageModel.curr_page){
              done()
              return
            }
            setTimeout(function () {
              if(that.pageModel.total_page<2){
                done()
              }else{
                that.getData();
                done()
              }
            },400)
          },
          onRefresh(done){
            setTimeout(function () {
              this.pageModel.curr_page = 0
              this.getData(true)
              done()
            },500)
          },
          onItemClick(item){
            this.$router.forward('/purchase/requisition/detail/associate/'+item.request_id)
          },
          check(item){
              //点击复选框
            if (item.checked) {
                //如果是选中
              this.$set(item,'check',false)
              this.checkedItem = {}
            }else{
                //未选中的，要把之前的check 置为false
              this.$set(this.checkedItem,'check',false)
              this.$set(item,'check',true)
              this.checkedItem = item
            }
          },
          getData(refresh){
            const reqData = {
              curr_page:refresh?1:1+this.pageModel.curr_page,
              page_size:this.pageModel.page_size,
              query:this.query
            }
            requisitionList(reqData).then(res=>{
              if(res.request_list.length>0){
                if(refresh){
                  this.request_list.length = 0
                }
                this.pageModel.curr_page = res.curr_page
                this.pageModel.total_page = res.total_page
                this.request_list = this.request_list.concat(res.request_list)
              }
            }).catch(error=>{
              $toast.show(error.description)
            })
          },
          associate(){
            //关联请购单
            const reqData = {
              request_id_his:this.checkedItem.request_id
            }
            requisitionRefer(reqData).then(res=>{
              console.log(res)
              this.$router.forward('/purchase/requisition/detail/check/'+res.request_id)
            }).catch(error=>{
              $toast.show(error.description)
            })
          }
        }
    }
</script>

<style lang="scss" scoped>

</style>

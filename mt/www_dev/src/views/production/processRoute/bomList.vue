<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>日历(模板维护)</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content has-tab">
      <div style="height: 60px">
        <tabs :tab-items="tabs" :tab-index="tabIndex" :on-tab-click="onTabClick"></tabs>
      </div>
      <div style="flex: 1">
        <div class="content" v-show="tabIndex===0">
          <scroll class="page-content"  style="-webkit-overflow-scrolling: touch">
            <swipe-item v-for="(item,index) in shiftList" class="req-item" @click.native="onItemClick(item)"  :obj="item" :canSwipe="true" swipeItemText="删除" v-on:SwipeItemClick="delShift(item,index)">
              <div class="calendarItem">
                <span class="color" :style="{ background:item.shift_color }"></span>
                <p style="padding-left: 50px;">{{ item.shift_name }}</p>
              </div>
            </swipe-item>
          </scroll>
        </div>
        <div class="content" v-show="tabIndex===1">
          <scroll class="page-content" style="-webkit-overflow-scrolling: touch">
            <swipe-item v-for="(item,index) in calendarList" class="req-item" @click.native="onItemClick(item)"  :obj="item" :canSwipe="true" swipeItemText="删除" v-on:SwipeItemClick="delCalendar(item,index)">
              <div class="calendarItem">
                <p>{{ item.calendar_name }}</p>
              </div>
            </swipe-item>
          </scroll>
        </div>
      </div>
    </div>
    <div class="mw-page-footer">
      <div class="btns">
        <div class="btn" @click="add">
          添加
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import { prod_req_mat_list } from '@/api/production/processRoute'
  import SwipeItem from "../../../components/swipeItem";
  import CstItem from "../../../components/cstItem"
  export default{
    name:'shopCalendar',
    components: {SwipeItem,CstItem},
    data(){
      return {
        tabs:['班次维护','工作日历'],
        tabIndex:0,
        shiftList:[],
        calendarList:[]
      }
    },
    computed:{

    },
    activated(){
      this.getData()
      this.getCalendarList()
    },
    mounted(){
    },
    methods: {
      onTabClick(index) {
        if(index===this.tabIndex) {
          return
        }
        this.tabIndex = index
      },
      //获取数据
      getData(refresh){
        const  requestData={
          curr_page:1,
          page_size:100,
          query:{
            shift_name:'',
            calendar_name:''
          }
        }
          shiftList(requestData).then(res => {
            this.shiftList=res.shift_list
          }).catch(error => {
            $toast.show(error.description)
          })
      },
      getCalendarList(){
        const  requestData={
          curr_page:1,
          page_size:100,
          query:{
            calendar_name:''
          }
        }
        calendarList(requestData).then(res => {
          this.calendarList=res.calendar_list
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      delShift(item,index){
        const  requestData={
          shift_id:item.shift_id
        }
        $dialog.confirm({
          theme: 'ios',
          title: '确定要删除吗？',
          cancelText: '取消',
          okText: '确定'
        }).then(res=>{
          if(res){
          	if(item.shift_id==1||item.shift_id==2){
          		$toast.show('不可删除系统默认班次')
          		return
          	}
            shiftDelete(requestData).then(res => {
              $toast.show('删除成功')
              this.shiftList.splice(index,1)
            }).catch(error => {
              $toast.show(error.description)
            })
          }
        })
      },
      delCalendar(item,index){
        const  requestData={
          calendar_id:item.calendar_id
        }
        $dialog.confirm({
          theme: 'ios',
          title: '确定要删除吗？',
          cancelText: '取消',
          okText: '确定'
        }).then(res=>{
          if(res){
            calendarDelete(requestData).then(res => {
              $toast.show('删除成功')
              this.calendarList.splice(index,1)
            }).catch(error => {
              $toast.show(error.description)
            })
          }
        })
      },
      onItemClick(item){
        if(this.tabIndex==0){
          $router.forward('/production/shift/check/'+item.shift_id)
        }else{
          $router.forward('/production/calendar/check/'+item.calendar_id)
        }
      },
      add(){
        if(this.tabIndex===0){
          $router.forward('/production/shift/new/new')
        }else{
          $router.forward('/production/calendar/action/new')
        }

      }
    }
  }
</script>

<style lang="scss" scoped>
.calendarItem{
  position: relative;
  .color{
    width: 30px;
    height: 17px;
    position: absolute;
    left: 0px;
    top: -2px;
    background: red;
    border-radius: 5px;
  }
}
</style>

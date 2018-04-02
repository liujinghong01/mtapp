<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>设备列表</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content has-tab">
      <div style="height: 60px">
        <tabs :tab-items="tabs" :tab-index="tabIndex" :on-tab-click="onTabClick"></tabs>
      </div>
      <div class="content" v-show="tabIndex===0">
        <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
          <swipe-item v-for="(item,index) in list" v-show="item.device_status!=2" @click.native="onItemClick(item)"  :obj="item" :canSwipe="true" swipeItemText="删除" v-on:SwipeItemClick="delItem(item,index)">
            <h3 ><span class="vi-color">{{ item.device_name }}</span></h3>
            <div >编号 ：<span >{{ item.device_no }}</span></div>
            <div >使用年限 ：<span >{{ item.work_life }}</span></div>
            <div class="req-item-content">
              <span>状态 : {{ item.device_status }}</span>
              <span>生产日期 : {{ item.prod_date }}</span>
            </div>
          </swipe-item>
          <div  slot="infinite" class="text-center">没有更多数据</div>
        </scroll>
      </div>
      <div class="content" v-show="tabIndex===1">
        <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
          <swipe-item v-for="(item,index) in list" v-show="item.device_status==2" @click.native="onItemClick(item)"  :obj="item" :canSwipe="true" swipeItemText="删除" v-on:SwipeItemClick="delItem(item,index)">
            <h3 ><span class="vi-color">{{ item.device_name }}</span></h3>
            <div >编号 ：<span >{{ item.device_no }}</span></div>
            <div >使用年限 ：<span >{{ item.work_life }}</span></div>
            <div class="req-item-content">
              <span>状态 : {{ item.device_status }}</span>
              <span>生产日期 : {{ item.prod_date }}</span>
            </div>
            <div class="btn" @click="overhaulForm">
              检修完毕
            </div>
          </swipe-item>
          <div  slot="infinite" class="text-center">没有更多数据</div>
        </scroll>
      </div>

    </div>
    <div class="mw-page-footer">
      <div class="btns">
        <div class="btn" @click="newEquipment">
          <!--pg_id	:'生产小组Id',
          pg_name	:'生产小组名称',-->
          添加设备
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import { deviceList,pgDeviceDelete } from '@/api/production/equipment'
  import SwipeItem from "../../../components/swipeItem"
  export default{
    name:'equipment',
    components: {SwipeItem},
    dep_id:'',
    dep_name:'',
    data(){
      return {
        tabs:['正常','检修中'],
        tabIndex:0,
        parent_id:'',
        list:[
          {
            device_addr:'设备所在地址',
            device_brand:'设备品牌',
            device_id:'设备Id'	,
            device_model:'设备型号'	,
            device_name:'设备名称(正常)',
            device_no:'设备编号',
            device_pic:'设备图标'	,
            device_status:1,
            is_checked	:'是否点检',
            pg_id:'生产小组Id'	,
            prod_date:'生产日期'	,
            work_life:'使用年限'
          },
          {
            device_addr:'设备所在地址',
            device_brand:'设备品牌',
            device_id:'设备Id'	,
            device_model:'设备型号'	,
            device_name:'设备名称(维修中)',
            device_no:'设备编号',
            device_pic:'设备图标'	,
            device_status:2,
            is_checked	:'是否点检',
            pg_id:'生产小组Id'	,
            prod_date:'生产日期'	,
            work_life:'使用年限'
          }


        ]
      }
    },
    computed:{

    },
    activated(){
      const param=this.$route.query

    },
    mounted(){
      const param = this.$route.query;
      this.dep_id=param.dep_id
      this.dep_name=param.dep_name
      this.deviceList()
    },
    methods: {
      onInfinite(){},
      delItem(item,index){
        const requestData = {
          device_info:{
            device_id:item.device_id
          }

        }
        pgDeviceDelete(requestData).then(res => {
          this.list.splice(index,1)
          $toast.show('设备删除成功')
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      onRefresh(){},
      deviceList(){
        const requestData = {
          curr_page:1,
          page_size:100,
          query	:{
            device_name:'',
            pg_id:this.dep_id
            // device_name:'',
            // pg_id:''

          }

        }
        deviceList(requestData).then(res => {
          this.list=res.pg_list
        }).catch(error => {
          $toast.show(error.description)
        })

      },
      onItemClick(item){
        this.$router.forward({
          path:'equipmentDetail',
          query:{
            parent_id:this.dep_id,
            action:'check',
            device_id:item.device_id
          }
        })
      },
      newEquipment(){
        this.$router.forward({
          path:'equipmentDetail',
          query:{
            parent_id:this.parent_id,
            pg_id:this.dep_id,
            pg_name:this.dep_name,
            action:'new'
          }
        })
      },
      overhaulForm(){
        this.$router.forward('overhaulForm')
        event.cancelBubble = true;
      },
      onTabClick(index) {
        if(index===this.tabIndex) {
          return
        }
        this.tabIndex = index
      },
    }
  }
</script>

<style lang="scss" scoped>

  .item-cont>div{
    line-height: 20px
  }
  .item-cont>div.btn{
    position: absolute;
    right: 0;
    top: 50%;
    margin-top: -40px;
    height: 80px;
    line-height: 80px;
    width: 58px;
    color: #4a90e2;
  }
</style>

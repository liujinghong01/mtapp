<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>设备点检表</span>
      <i class="right ion-calendar" style="font-size: 18px" @click="$router.forward({
          path:'checkRecord'
        })"></i>
    </div>
    <div class="mw-page-content has-tab">
      <div class="content" >
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


    </div>

  </div>
</template>

<script>
  import { deviceList,pgDeviceDelete,deviceCheck } from '@/api/production/equipment'
  import SwipeItem from "../../../components/swipeItem"
  export default{
    name:'check',
    components: {SwipeItem},
    dep_id:'',
    dep_name:'',
    data(){
      return {
        parent_id:'',
        list:[
          // {
          //   device_addr:'设备所在地址',
          //   device_brand:'设备品牌',
          //   device_id:'设备Id'	,
          //   device_model:'设备型号'	,
          //   device_name:'设备名称(正常)',
          //   device_no:'设备编号',
          //   device_pic:'设备图标'	,
          //   device_status:1,
          //   is_checked	:'是否点检',
          //   pg_id:'生产小组Id'	,
          //   prod_date:'生产日期'	,
          //   work_life:'使用年限'
          // }
        ]
      }
    },
    computed:{

    },
    activated(){
      this.deviceCheck()
    },
    mounted(){
      const param = this.$route.query;
      this.dep_id=param.dep_id
      this.dep_name=param.dep_name
      this.deviceCheck()
    },
    methods: {
      deviceCheck(){
        const requestData = {
          query	:{
            device_id:'',
          }
        }
        deviceCheck(requestData).then(res => {
          this.list=res.device_check_list
        }).catch(error => {
          $toast.show(error.description)
        })

      },
      onItemClick(item){
        this.$router.forward({
          path:'checkDetail',
          query:{
            device_id:item.device_id
          }
        })
      },
      onInfinite(){},
      onRefresh(){},
    }
  }
</script>

<style lang="scss" scoped>


</style>

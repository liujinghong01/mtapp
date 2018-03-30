<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>点检分配信息</span>
      <i class="right" v-show="action==='new'"></i>
      <i class="right" v-show="action==='check'" @click="action='action'">编辑</i>
      <i class="right" v-show="action==='action'" @click="cancel">取消</i>
    </div>
    <div class="mw-page-content ">
      <div class="content" >
        <scroll class="page-content"  style="-webkit-overflow-scrolling: touch">
          <div  class="item-divider" style="padding-left: 15px" v-show="staffList.length>0" >
            员工列表
            <span class="right" @click="Edit('staff')" v-show="action!=='check'">编辑</span>
          </div>
          <item v-for="(item,index) in staffList">{{item.check_uid_name}}</item>
          <div class="item-divider" style="padding-left: 15px" v-show="list.length>0" >
            设备列表
            <span class="right" @click="Edit('dev')" v-show="action!=='check'">编辑</span>
          </div>
          <item v-for="(item,index) in list" >
            <h3 ><span class="vi-color">{{ item.device_name }}</span></h3>
            <div >编号 ：<span >{{ item.device_no }}</span></div>
            <div >使用年限 ：<span >{{ item.work_life }}</span></div>
            <div class="req-item-content">
              <span>状态 : {{ item.device_status }}</span>
              <span>生产日期 : {{ item.prod_date }}</span>
            </div>
          </item>
        </scroll>
      </div>

    </div>
    <div class="mw-page-footer" v-show="action!=='check'">
      <div class="btns">
        <div class="btn"  v-show="action==='new'&&list.length<1"   @click="$router.forward({path:'distributionList',query:{dep_id:dep_id}})">
          选择设备
        </div>
        <div class="btn"  v-show="action==='new'&&list.length>0&&staffList.length<1"   @click="$router.forward({path:'personnelList',query:{dep_id:dep_id,type:'dep'}})">
          指派人员
        </div>
        <div class="btn"  v-show="action!=='check'&&list.length>0&&staffList.length>0"   @click="pgDeviceCheckAllocate()">
          提交
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import { pgDeviceCheckAllocate,pgDeviceCheckAllocateDetail } from '@/api/production/equipment'
  import SwipeItem from "../../../components/swipeItem"
  import sess from '@/utils/sess'
  export default{
    name:'distributionInfo',
    components: {SwipeItem},
    data(){
      return {
        action:'',
        dep_id:'',
        allocate_id:'',
        staffList:[],//'刘镜宏','向辉','黄杰'
        checkList:[],
        list:[],//[
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
          // },
          // {
          //   device_addr:'设备所在地址',
          //   device_brand:'设备品牌',
          //   device_id:'设备Id'	,
          //   device_model:'设备型号'	,
          //   device_name:'设备名称(维修中)',
          //   device_no:'设备编号',
          //   device_pic:'设备图标'	,
          //   device_status:2,
          //   is_checked	:'是否点检',
          //   pg_id:'生产小组Id'	,
          //   prod_date:'生产日期'	,
          //   work_life:'使用年限'
          // },
          // {
          //   device_addr:'设备所在地址',
          //   device_brand:'设备品牌',
          //   device_id:'设备Id'	,
          //   device_model:'设备型号'	,
          //   device_name:'设备名称(维修中)',
          //   device_no:'设备编号',
          //   device_pic:'设备图标'	,
          //   device_status:2,
          //   is_checked	:'是否点检',
          //   pg_id:'生产小组Id'	,
          //   prod_date:'生产日期'	,
          //   work_life:'使用年限'
          // }
          //]
      }
    },
    computed:{

    },
    activated(){
      if(this.action!=='check'){
        if(sess.get('checkList')!==null){
          this.list=JSON.parse(sess.get('checkList'))
          sess.remove('checkList')
        }
        if(sess.get('personnel')!==null){
          this.staffList=JSON.parse(sess.get('personnel'))
          sess.remove('personnel')
        }
      }else{
        this.pgDeviceCheckAllocateDetail()
      }
    },
    mounted(){
      const param=this.$route.query
      this.action=param.action
      this.dep_id=param.dep_id
      this.allocate_id=param.allocate_id
    },
    methods: {
      cancel(){
        this.action='check'
        this.pgDeviceCheckAllocateDetail()
      },
      Edit(item){
        if(item=='staff'){
          if(this.staffList.length>0){
            sess.set('staffList',JSON.stringify(this.staffList))
          }
          $router.forward({path:'personnelList',query:{dep_id:this.dep_id,type:'dep'}})
        }else if(item=='dev'){
          if(this.list.length>0){
            sess.set('devList',JSON.stringify(this.list))
          }
          $router.forward({path:'distributionList',query:{dep_id:this.dep_id}})
        }
      },
      //获取点检分配详情
      pgDeviceCheckAllocateDetail(){
        const  requestData={
          query:{
            allocate_id:this.allocate_id
          }
        }
        pgDeviceCheckAllocateDetail(requestData).then(res => {
          this.staffList=res.check_uid_list
          this.list=res.device_list
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      pgDeviceCheckAllocate(){
        const  requestData={
          check_allocate_info:{
            allocate_id:this.allocate_id,
            pg_id:this.dep_id,
            check_uid_list:this.staffList,
            device_list:this.list
          }
        }
        pgDeviceCheckAllocate(requestData).then(res => {
          $toast.show('添加点检分配成功')
          $router.go(-1)
        }).catch(error => {
          $toast.show(error.description)
        })
      }
    }
  }
</script>

<style lang="scss" scoped>

  .item>div{
    line-height: 20px
  }
  .addBtn{
    text-align: center;
    border:1px solid #416eb6;
    color:#416eb6;
  }
</style>

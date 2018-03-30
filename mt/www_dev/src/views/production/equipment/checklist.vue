<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>设备点检分配</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content ">
      <!--<cst-item v-for="(item,index) in checkList" :label="item.names"  :action="'check'" :hasArrow="true" v-model="'点检数量:'+item.num" @click.native="$router.forward('distributionInfo')"></cst-item>-->
      <swipe-item style="margin: 0px;border: none;border-bottom: 1px solid #ddd;" v-for="(item,index) in checkList"  @click.native="onItemClick(item)"  :obj="item" :canSwipe="true" swipeItemText="删除" v-on:SwipeItemClick="checkAllocateDelete(item,index)">
        <span class="names">{{ item.names }}</span>
        <span class="swipe-right">设备 <span style="color: #416eb6;">{{item.num}}</span></span>
      </swipe-item>
    </div>
    <div class="mw-page-footer" >
      <div class="btns">
        <div class="btn" @click="$router.forward({path:'distributionInfo',query:{action:'new',dep_id:dep_id}})">
          添加
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import { deviceCheckAllocate,checkAllocateDelete } from '@/api/production/equipment'
  import SwipeItem from "../../../components/swipeItem";
  import CstItem from "../../../components/cstItem"
  export default{
    name:'checklist',
    components: {CstItem,SwipeItem},
    data(){
      return {
        dep_id:'',
        checkList:[]
      }
    },
    computed:{

    },
    activated(){
      this.deviceCheckAllocate()
    },
    mounted(){
      const param = this.$route.query;
      this.dep_id=param.dep_id

    },
    methods: {
      onItemClick(item){
        $router.forward({path:'distributionInfo',query:{action:'check',allocate_id:item.id,dep_id:this.dep_id}})
      },
      //获取列表
      deviceCheckAllocate(){
        const  requestData={
          query:{
            check_uid:'',
            pg_id:this.dep_id
          }
        }
        deviceCheckAllocate(requestData).then(res => {
          this.checkList=[]
          res.check_allocate_info.forEach((e,i)=>{
            let obj={
                num:e.device_list.length,
                id:e.allocate_id,
                names:''
              }

              e.check_uid_list.forEach((item,index)=>{
                if(obj.names==''){
                  obj.names=item.check_uid_name
                }else{
                  obj.names+='、'+item.check_uid_name
                }
              })
            this.checkList.push(obj)
          })
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      checkAllocateDelete(item,index){
        const  requestData={
          check_allocate_info:{
            allocate_id:item.id
          }
        }
        checkAllocateDelete(requestData).then(res => {
            this.checkList.splice(index,1)
        }).catch(error => {
          $toast.show(error.description)
        })

      }
    }
  }
</script>

<style lang="scss" scoped>
.names{
  display: inline-block;
  width: 220px;
  overflow: hidden;
}
  .swipe-right{
    float: right;

  }

</style>

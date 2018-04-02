<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>任务分配</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content ">
      <div class="content" >
        <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
          <item  v-for="(item,index) in list" :class="item.check===true?'active':''"  @click.native="selectItem(item)">
            <h3 ><span class="vi-color">{{ item.device_name }}</span></h3>
            <div >编号 ：<span >{{ item.device_no }}</span></div>
            <div >使用年限 ：<span >{{ item.work_life }}</span></div>
            <div class="req-item-content">
              <!--<span>状态 : {{ item.device_status }}</span>-->
              <span>生产日期 : {{ item.prod_date }}</span>
            </div>
            <div class="checkbox">
              <i class="icon ion-android-checkbox-outline-blank"></i>
              <i class="icon ion-android-checkbox "></i>
            </div>
          </item>
          <div  slot="infinite" class="text-center">没有更多数据</div>
        </scroll>
      </div>
    </div>
    <div class="mw-page-footer">
      <div class="btns">
        <div class="btn" @click="popAllocate">
          确定
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import { pgDetail,popAllocate } from '@/api/production/work'
  import SwipeItem from "../../../components/swipeItem"
  import sess from '@/utils/sess'
  export default{
    name:'devList',
    components: {SwipeItem},
    data(){
      return {
        dep_id:'',
        pop_id:'',
        list:[]
      }
    },
    computed:{

    },
    activated(){
    },
    mounted(){
      const param=this.$route.query
      this.pg_id=param.pg_id
      this.pop_id=param.pop_id
      this.deviceList()

    },
    methods: {
      onInfinite(){},
      onRefresh(){},
      onItemClick(){

      },
      deviceList(){
        const requestData = {
          query	:{
            pg_id:this.pg_id
          }
        }
        pgDetail(requestData).then(res => {
          this.list=res.pg_info.device_list
          let allocated_list=[]
          if(sess.get('allocated_list')!==null){
            allocated_list=JSON.parse(sess.get('allocated_list'))
            sess.remove('allocated_list')
            this.list.forEach((e,i)=>{
              allocated_list.forEach((item,index)=>{
                if(e.device_id==item.resource_id){
                  this.$set(e,'check',true)
                }
              })
            })
          }

        }).catch(error => {
          $toast.show(error.description)
        })

      },
      selectItem(item){
        if(item.check==undefined){
          this.$set(item,'check',true)
        }else{
          item.check=!item.check
        }
      },
      popAllocate(){
        let checkList=[]
        this.list.forEach((e,i)=>{
          if(e.check){
            checkList.push({device_id:e.device_id})
          }
        })
        const requestData = {
          resource_info:{
            device_list:checkList,
            member_list:[],
            pop_id:this.pop_id
          }

        }
        popAllocate(requestData).then(res => {
          this.$router.go(-1)
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      determine(){
        let checkList=[]
        this.list.forEach((e,i)=>{
          if(e.check){
            checkList.push(e)
          }
        })
        sess.set('checkList',JSON.stringify(checkList))
        this.$router.go(-1)
      }
    }
  }
</script>

<style lang="scss" scoped>
.item{
  padding-left:40px ;
  &>div{
    line-height: 20px
  }
  &.active{
    background: #fff;
    &>div.checkbox>i{
      color: #416eb6;
      &.ion-android-checkbox{
        display: block;
      }
    }
  }
  .checkbox{
    width: 40px;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
    i{
      position: absolute;
      width: 14px;
      height: 20px;
      font-size: 18px;
      left: 50%;
      top: 50%;
      margin-top: -10px;
      margin-left: -5px;
      &.ion-android-checkbox{
        display: none;
      }
    }
  }
}


</style>

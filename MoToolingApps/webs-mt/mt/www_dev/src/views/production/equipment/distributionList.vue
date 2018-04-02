<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>设备点检分配</span>
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
              <span>状态 : {{ item.device_status }}</span>
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
        <div class="btn" @click="determine">
          确定
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import { deviceList } from '@/api/production/equipment'
  import SwipeItem from "../../../components/swipeItem"
  import sess from '@/utils/sess'
  export default{
    name:'distributionList',
    components: {SwipeItem},
    data(){
      return {
        dep_id:'',
        list:[
          // {
          //   check:'',
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
          //   check:true,
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


        ]
      }
    },
    computed:{

    },
    activated(){
    },
    mounted(){
      const param=this.$route.query
      this.dep_id=param.dep_id
      this.deviceList()

    },
    methods: {
      onInfinite(){},
      onRefresh(){},
      onItemClick(){

      },
      deviceList(){
        const requestData = {
          curr_page:1,
          page_size:100,
          query	:{
            device_name:'',
            pg_id:this.dep_id
          }
        }
        deviceList(requestData).then(res => {
          this.list=res.pg_list
          if(sess.get('devList')!==null){
            let devList=JSON.parse(sess.get('devList'))
            sess.remove('devList')
            this.list.forEach((e,i)=>{
              devList.forEach((item,index)=>{
                if(e.device_id==item.device_id){
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

<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>小组报工</span>
      <i class="right" >扫描</i>
    </div>
    <div class="mw-page-content has-tab">
      <div style="height: 60px">
        <tabs :tab-items="tabs" :tab-index="tabIndex" :on-tab-click="onTabClick"></tabs>
      </div>
      <div style="flex: 1">
        <div class="content" v-show="tabIndex===0">
          <scroll class="page-content"  style="-webkit-overflow-scrolling: touch">
            <div class="itemBox" v-show="e.pop_list.length>0" :style="{height:e.check?'40px':(e.pop_list.length*126+40)+'px' }" v-for="(e,i) in inceptList">
              <p class="itemTitle" @click="check(e)">生产小组：{{ e.pg_name }} <i  :class="e.check?'ion-arrow-down-b':'ion-arrow-up-b'"></i></p>
              <item  v-for="(item,index) in e.pop_list" style="padding-top: 46px;padding-bottom: 12px ">
                <div class="itemInfo" >
                  <div class="status"></div>
                  <div>
                    <p style="height: 18px">{{ item.mat_no }}</p>
                    <p>
                      <span style="margin-right: 5px;display: inline-block;max-width: 70%">物料名称 : {{item.mat_name}}</span>
                    </p>
                  </div>
                </div>
                <p>计划开始时间 : {{ item.plan_start_time }}</p>
                <p>计划结束时间 : {{ item.plan_end_time }}</p>
                <p>任务工时 : {{ item.plan_work_time }} &nbsp;&nbsp;&nbsp;&nbsp;待加工数量:{{ item.proc_qty }}</p>
                <p>生产订单号 : {{ item.po_no }}</p>
                <span v-if="item.pop_task_flag*1===1" class="right">我的任务</span>
                <span v-else class="right">部门任务</span>
              </item>
            </div>
          </scroll>
        </div>
        <div class="content" v-show="tabIndex===1">
          <scroll class="page-content" style="-webkit-overflow-scrolling: touch">
            <div class="itemBox"  :style="{height:e.check?'40px':((e.pg_type==='1'?e.resource_info.member_list.length:e.resource_info.device_list.length)*69+40)+'px' }" v-for="(e,i) in resourceList">
              <p class="itemTitle" @click="check(e)">生产小组：{{ e.pg_name }} <i  :class="e.check?'ion-arrow-down-b':'ion-arrow-up-b'"></i></p>
              <!--(e.pg_type==='1'?e.resource_info.member_list:e.resource_info.device_list)-->
                <item v-show="e.pg_type==='1'" v-for="(item,index) in e.resource_info.member_list" @click.native="memberClick(item,e.pg_id,e.pg_type)">
                  <p>
                      <span :style="{ color:current.member_flag*1===1?'#416eb6':'' }" v-for="(current,subscript) in item.member_sub_list">
                        {{ current.member_name }}
                        <i v-show="current.member_flag*1===1">(我自己)</i>
                      </span>
                  </p>
                  <span class="right" style="height: 52px;line-height: 52px;width: 58px;color:red" v-if="item.member_sub_list[0].member_status*1===0">待加工</span>
                  <span class="right" style="height: 52px;line-height: 52px;width: 58px" v-else @click="joinProc(item)">加工中</span>
                </item>
                <item v-show="e.pg_type==='0'&&item.device_status*1!==4" v-for="(item,index) in e.resource_info.device_list" @click.native="devClick(item,e.pg_id,e.pg_type)">
                  <p >设备：
                    <span :style="{ color:item.resource_flag*1===1?'#416eb6':'' }">
                      {{ item.device_name }} <i > {{ item.resource_flag*1===1? '(我负责的)':'' }}{{ item.resource_flag*1===2? '(我参与的)':'' }}</i>
                    </span>
                  </p>
                  <p>编号：{{ item.device_no }}</p>
                  <span class="right" style="height: 68px;line-height: 68px;width: 58px;color:red" v-if="item.device_status*1===0">待加工</span>
                  <span class="right" style="height: 68px;line-height: 68px;width: 58px" v-else-if="item.device_status*1===1" >加工中</span>
                  <span class="right" style="height: 68px;line-height: 68px;width: 58px" v-else-if="item.device_status*1===2">检修中</span>
                  <span class="right" style="height: 68px;line-height: 68px;width: 58px" v-else-if="item.device_status*1===3">维修中</span>
                  <span class="right" style="height: 68px;line-height: 68px;width: 58px" v-else-if="item.device_status*1===5">已报废</span>
                </item>
            </div>
          </scroll>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
  import { confirmList,popProcInfo,waitProcList } from '@/api/production/work'
  import SwipeItem from "../../../components/swipeItem";
  import CstItem from "../../../components/cstItem"
  export default{
    name:'work',
    components: {SwipeItem,CstItem},
    data(){
      return {
        tabs:['任务列表','资源列表'],//,'完工统计'

        tabIndex:0,
        inceptList:[],//任务列表
        resourceList:[],//资源列表
      }
    },
    computed:{

    },
    activated(){
      this.getData()
    },
    mounted(){
    },
    methods: {
      joinProc(item){
        alert(1)
        console.log(item)
        event.cancelBubble = true
      },
      onTabClick(index) {
        if(index===this.tabIndex) {
          return
        }
        this.tabIndex = index
        this.getData()
      },
      //获取数据
      getData(){
        const  requestData={
          query:{
            type:this.tabIndex+1
          }
        }
        confirmList(requestData).then(res => {
          if(this.tabIndex===0){
            this.inceptList=res.pg_list.pg_list
          }else if(this.tabIndex===1){
            this.resourceList=res.pg_list.pg_list
          }
          }).catch(error => {
            $toast.show(error.description)
          })
      },
      memberClick(item,pg_id,type){


          let member_flag=false
          for(let i=0;i<item.member_sub_list.length;i++){
            if(item.member_sub_list[i].member_flag*1===1){
              member_flag=true
              break
            }
          }
          if(member_flag){
            $router.forward({
              path:'/production/work/taskList'
              ,query:{
                pg_id:pg_id,
                resource_id:item.member_sub_list[0].member_id,
                type:type,
                pop_status:item.member_sub_list[0].member_status
              }
            })
          }else{
            if(item.member_sub_list[0].member_status*1===0){
              $toast.show('无法打开他人任务列表')
              // $dialog.alert({
              //   // 效果
              //   effect: 'default',
              //   // 标题
              //   title: '提示',
              //   // 内容
              //   content: '这是一个警告框',
              //   // 按钮文本
              //   okText: '确定',
              //   // 按钮主题
              //   okTheme: 'assertive'
              // })
            }else{
              // $dialog.confirm({
              //   theme: 'ios',
              //   title: '是否加入此工序中？',
              //   cancelText: '取消',
              //   okText: '确定'
              // }).then(res=>{
              //   if(res){
              //   }
              // })
              $router.forward({
                path:'/production/work/workInfo',
                query:{
                  pg_id:pg_id,
                  resource_id:item.member_sub_list[0].member_flag,
                  type:type
                }
              })
            }

          }


      },
      devClick(item,pg_id,type){
        if(item.device_status*1===3){
          $toast.show('设备维修中')
        }else if(item.device_status*1===2){
          $toast.show('设备检修中')
        }else if(item.device_status*1===1){
          //加工中
          if(item.resource_flag*1===1){//我负责的设备
            $router.forward({
              path:'/production/work/taskList'
              ,query:{
                pg_id:pg_id,
                resource_id:item.device_id,
                type:type,
                pop_status:item.device_status
              }
            })
          }else{
            $router.forward({
              path:'/production/work/workInfo'
              ,query:{
                pg_id:pg_id,
                resource_id:item.device_id,
                type:type
              }
            })

          }
        }else if(item.device_status*1===0){
          //待加工
          if(item.resource_flag*1===1){//我负责的设备
            $router.forward({
              path:'/production/work/taskList'
              ,query:{
                pg_id:pg_id,
                resource_id:item.device_id,
                type:type,
                pop_status:item.device_status
              }
            })

          }else{
            $router.forward({
              path:'/production/work/taskList'
              ,query:{
                pg_id:pg_id,
                resource_id:item.device_id,
                type:type,
                pop_status:item.device_status
              }
            })
          }

        }
      },
      onItemClick(item){
        if(this.tabIndex==0){

          $router.forward({
            path:'/production/processRouteDetaile'
            ,query:{
              prod_id:item.prod_id
            }
          })
        }else{
          $router.forward('/production/calendar/check/'+item.calendar_id)
        }
      },
      check(item){
        if(item.check===undefined){
          this.$set(item,'check',true)
        }else{
          this.$delete(item,'check')
        }
      },



    }
  }
</script>
<style lang="scss" scoped>
  .itemBox{
    overflow: hidden;
    transition: all .3s;
    .itemTitle{
      margin: 0;
      height: 40px;
      line-height: 40px;
      padding: 0 15px;
      position: relative;
      i{
        position: absolute;
        right: 15px;
        top: 0;
        width: 40px;
        height: 40px;
        line-height: 40px;
        text-align: center;
        color:#416eb6;
      }
    }
  }
  .item>p{
    font-size: 13px;
    margin-bottom: 3px;
    color: #999;
  }
  .item>span.right{
    width: 70px;
    height: 126px;
    line-height: 126px;
    position: absolute;
    right: 0;
    top: 0;
    color:#416eb6 ;
  }
  .itemInfo{
    width: 100%;
    height: 42px;
    position: absolute;
    right: 0;
    top: 0;
    padding: 12px 15px 0px 55px;
    line-height: 20px;
    &.hasCheck{
      width: 92%;
    }
    &>div>p{
      margin-bottom: 0;
      height: 14px;
      line-height: 14px;
    }
    span{
      font-size: 12px;
      color: #999;
      height: 12px;
      line-height: 12px;


    }
    .status{
      width: 30px;
      height: 24px;
      position: absolute;
      left: 15px;
      top:17px;
      background: red;
    }
  }
</style>

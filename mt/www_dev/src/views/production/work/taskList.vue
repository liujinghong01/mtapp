<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>任务列表</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content ">
      <div class="content" >
        <scroll class="page-content"  style="-webkit-overflow-scrolling: touch" >
          <item @click.native="itemClick(item)" :class="pop_status*1===0?'active':''" v-for="(item,index) in pop_list" style="padding-top: 46px;padding-bottom: 12px ">
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
            <p>任务工时 : {{ item.plan_work_time }}&nbsp;&nbsp;&nbsp;&nbsp;待加工数量:{{ item.proc_qty }}</p>
            <p>生产订单号 : {{ item.po_no }}</p>
            <div class="checkbox" >
              <i class="icon " :class="item.check?'ion-android-checkbox active':'ion-android-checkbox-outline-blank'"></i>
            </div>
            <div v-show="pop_status*1===1" class="right"> <span :class="item.pop_status==='4'?'active':''">{{ item.pop_status==='4'?'加工中':'待加工' }}</span></div>
          </item>

          <div  slot="infinite" class="text-center"></div>
        </scroll>
      </div>
    </div>
    <div class="mw-page-footer">
      <div class="btns" v-if="pop_status*1===0">
        <div class="btn" @click="popChangeStateus(1)">
          开始加工
        </div>
      </div>
      <div class="btns" v-else>
        <div class="btn" @click="jobBooking(2)">
          取消任务
        </div>|
        <div class="btn" @click="jobBooking(3)">
          部分完成
        </div>|
        <div class="btn" @click="jobBooking(4)">
          全部完成
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  import { getDeptInfo } from '@/api/company'
  import { waitProcList,popChangeStateus,popProcInfo } from '@/api/production/work'
  import SwipeItem from "../../../components/swipeItem"
  import sess from '@/utils/sess'
  export default{
    name:'taskList',
    components: {SwipeItem},
    data(){
      return {
        dep_id:'',
        resource_id:'',
        pop_status:'',//0待加工，1加工中
        type:'',
        pop_list:[
          // {name:'黄杰',id:1,check:true},
        ]
      }
    },
    computed:mapGetters([
      'chosenCompany',
      'userinfo'
    ]),
    activated(){
    },
    mounted(){
      const param=this.$route.query
      this.pg_id=param.pg_id
      this.resource_id=param.resource_id
      this.pop_status=param.pop_status
      this.type=param.type
      if(this.pop_status*1===0){
        this.waitProcList(this.pg_id,this.resource_id,this.type,this.pop_status)
      }else if(this.pop_status*1===1){
        this.popProcInfo(this.pg_id,this.resource_id,this.type)
      }

    },
    methods: {
      jobBooking(type){
        if(type===2){
          $dialog.confirm({
            theme: 'ios',
            title: '确定取消任务吗？',
            cancelText: '取消',
            okText: '确定'
          }).then(res=>{
            if(res){
              this.popChangeStateus(type)
            }
          })
        }else if(type===3){
          $dialog.confirm({
            theme: 'ios',
            title: '确定任务已 部分完成 吗？',
            cancelText: '取消',
            okText: '确定'
          }).then(res=>{
            if(res){
              this.popChangeStateus(type)
            }
          })
        }else if(type===4){
          $dialog.confirm({
            theme: 'ios',
            title: '确定任务已全部完成吗？',
            cancelText: '取消',
            okText: '确定'
          }).then(res=>{
            if(res){
              this.popChangeStateus(type)
            }
          })
        }

      },
      waitProcList(pg_id,resource_id,type,pop_status){
        const  requestData={
          query:{
            pg_id:pg_id,//	生产小组Id	number
            resource_id:resource_id,//	资源Id	string	@mock=
            type:type,//
            pop_status:pop_status//	加工任务状态	string	必填：3待加工、4加工中
          }
        }
        waitProcList(requestData).then(res => {
          if(res.pop_list.length>0){
            if(this.pop_status*1===0){
              this.pop_list=res.pop_list
            }else{
              res.pop_list.forEach((e,i)=>{
                this.pop_list.push(e)
              })
            }
          }

        }).catch(error => {
          $toast.show(error.description)
        })
      },
      popProcInfo(pg_id,resource_id,type){
        const  requestData={
          query:{
            pg_id:pg_id,//	生产小组Id	number
            resource_id:resource_id,//	资源Id	string	@mock=
            type:type,//
          }
        }
        popProcInfo(requestData).then(res => {
          this.waitProcList(this.pg_id,this.resource_id,this.type,this.pop_status)
          this.pop_list=res.pop_list
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      popChangeStateus(status){
        let device_id=''
        let pop_ids=[]
        if(this.type*1===0){
          device_id=this.resource_id
        }
        if(status*1===1){
          this.pop_list.forEach((e,i)=>{
            if(e.check){
              pop_ids.push(e.pop_id)
            }
          })
        }else{//.pop_status==='4'
          this.pop_list.forEach((e,i)=>{
            if(e.pop_status*1!==3){
              pop_ids.push(e.pop_id)
            }
          })
        }
        if(pop_ids.length<1){
          $toast.show('请选择工序')
          return
        }
        const  requestData={
          pop_info:{
            device_id:device_id,
            pg_id:this.pg_id,
            pop_ids:pop_ids,//
            set_status:status//	1开始加工，2取消加工，3部分完成，4全部完成；
          }
        }
        popChangeStateus(requestData).then(res => {
          alert(status)
          if(status===1){
            this.pop_status=1
            this.popProcInfo(this.pg_id,this.resource_id,this.type)
          }else{
            this.pop_status=0
            this.popProcInfo(this.pg_id,this.resource_id,this.type)
          }
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      itemClick(item){
        if(this.pop_status*1===0){
          if(item.check==undefined){
            this.$set(item,'check',true)
          }else{
            item.check=!item.check
          }
        }
      }

    }
  }
</script>

<style lang="scss" scoped>
  .itemInfo{
    width: 100%;
    height: 42px;
    position: absolute;
    right: 0;
    top: 0;
    padding: 12px 15px 0px 55px;
    line-height: 20px;
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
  div.right{
    width: 42px;
    height: 100%;
    position: absolute;
    right: 15px;
    top: 0;
    span{
      position: absolute;
      left: 0;
      top:50%;
      margin-top: -10px;
      &.active{
        color: #416eb6;
      }
    }

  }
  .checkbox{display: none}
  .active{
    background: #fff;
    &>.itemInfo{
      padding: 12px 15px 0px 90px;
      .status{
        left: 50px;
      }
    }
  &>p{
    padding-left: 35px;
  }
  &>.checkbox{
    display: block;
    width: 50px;
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
      &.active{
        color: #416eb6;
      }
    }

  }
}

</style>

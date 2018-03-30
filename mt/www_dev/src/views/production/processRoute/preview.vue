<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>排产预览</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content ">

      <item v-for="(item,index) in proc_list">
        <p style="color: #416eb6;font-size: 16px">{{ item.proc_name }}</p>
        <p>生产小组 <span class="right">{{ item.pg_name }}</span></p>
        <p v-show="item.proc_type==='0'">设备名称 <span class="right">{{ item.device_name }}</span></p>
        <p v-if="schedule_mode==='0'">计划开始时间 <span class="right">{{ item.plan_start_time }}</span></p>
        <div v-else style="width: 100%;position: relative;overflow: hidden;height: 22px;line-height: 22px;">
          计划开始时间 <span class="right" >{{ item.plan_start_time }}</span>
          <datepicker @click.native="currSelect=index" style="position: absolute;top:0;right: 55px;opacity: 1;padding: 0;min-height: 22px;height: 22px;width: 73px;border-bottom:1px solid #416eb6;" v-model="item.startDate"  date-format="yyyy-mm-dd"></datepicker>
          <span @click="timePicker(index,item)" style="width: 51px;height: 21px;position: absolute;right: 0;top: 0;border-bottom:1px solid #416eb6;"></span>
        </div>
        <p v-if="schedule_mode==='1'">计划结束时间 <span class="right">{{ item.plan_end_time }}</span></p>
        <div v-else style="width: 100%;position: relative;overflow: hidden;height: 22px;line-height: 22px;">
          计划结束时间 <span class="right">{{ item.plan_end_time }}</span>
          <datepicker @click.native="currSelect=index" style="position: absolute;top:0;right: 55px;opacity: 1;padding: 0;min-height: 22px;height: 22px;width: 73px;border-bottom:1px solid #416eb6;" v-model="item.endDate"  date-format="yyyy-mm-dd"></datepicker>
          <span @click="timePicker(index,item)" style="width: 51px;height: 21px;position: absolute;right: 0;top: 0;border-bottom:1px solid #416eb6;"></span>
        </div>
        <p>任务工时 <span class="right">{{ item.plan_work_time }}</span></p>
        <div style="position: relative;height: 22px;line-height: 22px" >
          区间负荷
          <div class="progressBar">
            <div style="width: 100%;height: 10px;border-radius: 5px;box-sizing: border-box;border:1px solid #416eb6;">
              <div style="background:#416eb6;width: 40%;height: 100%" :style="{width:item.plan_region_load*100+'%'}"></div>
            </div>
            <div class="value">{{ item.plan_region_load*100}}%</div>
          </div>
        </div>
      </item>
    </div>
    <div class="mw-page-footer">
      <div class="btns">
        <div class="btn" @click="submit()">
          生成生产订单
        </div>
      </div>
    </div>


    <time-picker v-on:chosen="chosen"
                 :showPicker="showPicker"
                 :pickerType="pickerType"
                 :pickerValue="pickerValue"
                 v-on:hideMask="showPicker=false">
    </time-picker>
  </div>
</template>

<script>

  import { todayDate } from '@/utils'
  import { scheduleStart,generated } from '@/api/production/processRoute'
  import timePicker from "../../../components/timePicker"
  import SwipeItem from "../../../components/swipeItem";
  import CstItem from "../../../components/cstItem"
  import sess from '@/utils/sess'

  export default{
    name:'schedulingPreview',
    components: {SwipeItem,CstItem,timePicker},
    data(){
      return {
        po_id:'',
        po_no:'',
        showPicker:false,
        currSelect:'',
        pickerType:'time',
        pickerValue:'',
        schedule_mode:'0',//排产方式 0-正序 1-倒序
        proc_list:[//工序列表	array<object>
          {
            device_name:'铣床01',//	设备名称	string
            pg_name	:'CNC',//生产小组名称	string

            plan_end_time:'2012-12-11 08:00:00',//	计划结束时间	string
            endDate:'2012-12-11',
            endTime:'08:00', //startData
            startDate:'2012-12-14',
            startTime:'09:00',
            plan_region_load:'80%',//	区间负荷	string
            plan_start_time:'2012-12-14 09:00:00',//	计划开始时间	string
            plan_work_time:'46',//	计划任务工时	string
            proc_id:'',//	工序id	string
            proc_name:'凹槽加工',//	工序名称	string
            proc_type:'0',//	工序类型	number 0-机器 1-人工
          },
          // {
          //   device_name:'铣床01',//	设备名称	string
          //   pg_name	:'CNC',//生产小组名称	string
          //   plan_end_time:'2012-12-11 08:00:00',//	计划结束时间	string
          //   plan_region_load:'80%',//	区间负荷	string
          //   plan_start_time:'2012-12-14 09:00:00',//	计划开始时间	string
          //   startDate:'2012-12-14',
          //   startTime:'09:00',
          //   plan_work_time:'46',//	计划任务工时	string
          //   proc_id:'',//	工序id	string
          //   proc_name:'凹槽加工',//	工序名称	string
          //   proc_type:'1',//	工序类型	number 0-机器 1-人工
          // },

        ]
      }
    },
    computed:{

    },
    activated(){


    },
    created(){
      let po_data=sess.get('po_data')
      sess.remove('po_data')
      this.po_id=po_data.po_id
      this.po_no=po_data.po_no
      this.schedule_mode=po_data.schedule_mode
      this.proc_list=po_data.proc_list
    },
    methods: {
      submit(){
        let proc_list=[]
        this.proc_list.forEach((e,i)=>{
          proc_list.push({
            plan_end_time:e.plan_end_time,
            plan_start_time:e.plan_start_time,
            plan_region_load:e.plan_region_load,
            plan_work_time:e.plan_work_time,
            proc_id:e.proc_id
          })
        })
        const requestData = {
          po_id:this.po_id,
          po_no:this.po_no,
          proc_list:proc_list
        }
        generated(requestData).then(res=>{
          $toast.show('生产订单生成成功')
          $router.go(-1)
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      timePicker(index,item){
        this.showPicker=true
        this.currSelect=index
        if(this.schedule_mode==='0'){
            this.pickerValue=item.endTime
        }else{
            this.pickerValue=item.startTime
        }
      },
      chosen(e){
        if(this.schedule_mode=='0'){
          this.proc_list[this.currSelect].endTime=e
          this.proc_list[this.currSelect].plan_end_time=this.proc_list[this.currSelect].endDate+' '+this.proc_list[this.currSelect].endTime+':00'
        }else{
          this.proc_list[this.currSelect].startTime=e
          this.proc_list[this.currSelect].plan_start_time=this.proc_list[this.currSelect].endDate+' '+this.proc_list[this.currSelect].endTime+':00'
        }
      },
    },
    watch:{

      proc_list:{
        handler:function(e){
          let item=e[this.currSelect]
          if(this.schedule_mode==='0'){
            this.proc_list[this.currSelect].plan_end_time=this.proc_list[this.currSelect].endDate+' '+this.proc_list[this.currSelect].endTime+':00'
          }else{
            this.proc_list[this.currSelect].plan_start_time=this.proc_list[this.currSelect].startDate+' '+this.proc_list[this.currSelect].startTime+':00'
          }
        },deep:true
      }
    }
  }
</script>

<style lang="scss" scoped>
  .right{
    float: right;
  }
  .item p{
    margin-bottom: 0px;
    height: 22px;
    line-height: 22px;
  }
  .progressBar{
    width: 100%;
    padding-left: 66px;
    padding-right: 35px;
    height: 22px;
    box-sizing: border-box;
    position: absolute;
    right: 0;
    top: 0;
    padding-top: 6px;
    .value{
      width: 35px;
      height: 14px;
      position: absolute;
      right: 0;
      top:0;
      text-align: right;
    }
  }
</style>

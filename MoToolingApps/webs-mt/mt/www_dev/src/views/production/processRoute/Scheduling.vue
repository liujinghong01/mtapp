<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>排产</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content ">
      <item style="margin-bottom: 0" >
        <span>排产方式：</span>
        <label><input name="isSchedule_mode"   v-model="schedule_mode" type="radio" value="0" /> 正序排产</label>
        <label><input name="isSchedule_mode"   v-model="schedule_mode" type="radio" value="1" /> 倒序排产</label>
      </item>
      <div  v-if="schedule_mode=='0'">
        <datepicker  v-model="startDate" label="计划开始日期" date-format="yyyy-mm-dd"></datepicker>
        <cst-item v-show="startDate!=='请选择'" label="计划开始时间"  v-model="startTime" action="check" @click.native="timePicker('start_time')" ></cst-item>
      </div>
      <div  v-else>
        <datepicker v-model="endDate" label="计划结束日期" date-format="yyyy-mm-dd"></datepicker>
        <cst-item v-show="endDate!=='请选择'" label="计划结束时间" @click.native="timePicker('end_time')" v-model="endTime" action="check" ></cst-item>
      </div>
    </div>
    <div class="mw-page-footer">
      <div class="btns">
        <div class="btn" @click="scheduleStart()">
          排产预览
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
  import { scheduleStart } from '@/api/production/processRoute'
  import timePicker from "../../../components/timePicker"
  import SwipeItem from "../../../components/swipeItem";
  import CstItem from "../../../components/cstItem"
  import sess from '@/utils/sess'

  export default{
    name:'scheduling',
    components: {SwipeItem,CstItem,timePicker},
    data(){
      return {
        startDate:todayDate(),
        endDate:todayDate(),
        showPicker:false,
        currSelect:'',
        pickerType:'time',
        pickerValue:'',
        schedule_mode:0,
        start_time:'',
        startTime:'请选择',
        end_time:'',
        endTime:'请选择',
        undeal_qty:'',
        po_id:''
      }
    },
    computed:{

    },
    activated(){
    },
    mounted(){
      const param=this.$route.query
      this.undeal_qty=param.undeal_qty
      this.po_id=param.po_id
      this.first_try_date=param.first_try_date
    },
    methods: {
      scheduleStart(){
        let  requestData={
          end_time:'',//	排序结束时间	string
          po_id	:this.po_id,//	工艺路线id	string
          schedule_mode:this.schedule_mode,//		排产方式	string
          start_time:'',//		排序开始时间
          undeal_qty:this.undeal_qty,//		当前排产数量
          first_try_date:this.first_try_date
        }
        if(this.schedule_mode==0){
          if(this.startTime==='请选择'){
            $toast.show('请选择开始时间')
            return
          }
          requestData.start_time=this.start_time
        }else{
          if(this.endTime.length==='请选择'){
            $toast.show('请选择开始时间')
            return
          }
          requestData.end_time=this.end_time
        }

        scheduleStart(requestData).then(res => {
          let po_data={
            po_id	:res.po_id,//生产定单id	string
            po_no	:res.po_no.bill_no,//生产定单编号	string
            schedule_mode:this.schedule_mode+'',
            proc_list:[]
          }
          res.proc_list.forEach((e,i)=>{
            let plan_end_time=e.planEndTime.year+'-'
              +(e.planEndTime.monthValue<10?('0'+e.planEndTime.monthValue):e.planEndTime.monthValue)+'-'
              +(e.planEndTime.dayOfMonth<10?('0'+e.planEndTime.dayOfMonth):e.planEndTime.dayOfMonth)+' '
              +(e.planEndTime.hour<10?('0'+e.planEndTime.hour):e.planEndTime.hour)+':'
              +(e.planEndTime.minute<10?('0'+e.planEndTime.minute):e.planEndTime.minute)
            let plan_start_time=e.planStartTime.year+'-'
              +(e.planStartTime.monthValue<10?('0'+e.planStartTime.monthValue):e.planStartTime.monthValue)+'-'
              +(e.planStartTime.dayOfMonth<10?('0'+e.planStartTime.dayOfMonth):e.planStartTime.dayOfMonth)+' '
              +(e.planStartTime.hour<10?('0'+e.planStartTime.hour):e.planStartTime.hour)+':'
              +(e.planStartTime.minute<10?('0'+e.planStartTime.minute):e.planStartTime.minute)

            let plan_region_load='0.5'
            po_data.proc_list.push({
                plan_end_time: plan_end_time+':00',
                plan_region_load: plan_region_load,
                plan_start_time:plan_start_time+':00',
                plan_work_time:e.taskHours,
                proc_id: e.id,
                proc_name:e.procName,
                proc_type:e.proc_type,
                device_name:e.device_name,
                endDate:plan_end_time.split(' ')[0],
                endTime:plan_end_time.split(' ')[1],
                startDate:plan_start_time.split(' ')[0],
                startTime:plan_start_time.split(' ')[1]
              })

          })
          sess.set('po_data',po_data)
          $router.forward({ path:'/production/schedulingPreview'})
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      timePicker(type){
        this.showPicker=true
        this.currSelect=type
        if(type=='start_time'){
            this.pickerValue=this.startTime
        }else{
            this.pickerValue=this.endTime
        }
      },
      chosen(e){
        if(this.currSelect=='start_time'){
          this.startTime=e
          this.start_time=this.startDate+' '+e
        }else{
          this.endTime=e
          this.end_time=this.endDate+' '+e
        }
      },
    },
    watch:{
      startDate:function(){
        this.start_time=this.startDate+' '+this.startTime
      },
      endDate:function(){
        this.end_time=this.endDate+' '+this.endTime
      }
    }
  }
</script>

<style lang="scss" scoped>

</style>

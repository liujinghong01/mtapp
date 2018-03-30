<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>新增班次</span>
      <i v-show="action==='check'" class="right" @click="action='action'">编辑</i>
      <i v-show="action==='action'" class="right" @click="action='check'">取消</i>
      <i v-show="action==='new'" class="right"></i>
    </div>
    <div class="mw-page-content ">
      <cst-item   label="班次名称" v-model="shift_info.shift_name" :placeholder="shift_info.shift_name===''?'请输入班次名称':''" :action="action" editType="text"></cst-item>
      <cst-item   label="选择颜色" :color="shift_info.shift_color"    editType="color" @click.native="selectColor()"></cst-item>
      <div v-for="(item,index) in shift_info.detail_list">
        <div class="item-divider"> <span class="left">时间段{{ index+1 }}</span>
          <span class="right" @click="deleteTime(index) " v-show="action!='check'">删除</span>
        </div>
        <cst-item   label="开始时间"  @click.native="timePicker(item,'start_time',index)" v-model="item.start_time"  :hasArrow="action==='action'?true:false" :action="action" :canEdit="false" editType="text"></cst-item>
        <cst-item   label="结束时间"  @click.native="timePicker(item,'end_time',index)" v-model="item.end_time"  :hasArrow="action==='action'?true:false" :action="action"  :canEdit="false" editType="text"></cst-item>
        <item   class="item-icon-right" >
          工作时长
          <span class="item-note" style="margin-right:0">{{   item.shift_mins | mins }}</span>
        </item>
      </div>
      <p v-show="action!=='check'" class="addBtn" @click="add()">+添加时间段</p>
    </div>
    <div class="mw-page-footer">
      <div class="btns">
        <div class="btn" @click="shiftModify">
          确定
        </div>
      </div>
    </div>
    <time-picker v-on:chosen="chosen" :showPicker="showPicker" :pickerType="pickerType" :pickerValue="pickerValue" v-on:hideMask="showPicker=false"></time-picker>
    <div class="selectColor" >
      <div v-show="showColorBox"  class="mask" @click="showColorBox=false"></div>
      <div class="colorBox" :class="showColorBox?'active':''">
        <p>颜色选择</p>
        <ul>
          <li @click="checkedColor(item)" v-for="(item ,index) in colorList" :style="{background:item}"></li>
        </ul>
      </div>
    </div>
  </div>

</template>

<script>
  import { shiftList,shiftModify,shiftDetail } from '@/api/production/shopCalendar'
  import CstItem from "../../../components/cstItem"
  import timePicker from "../../../components/timePicker"
  export default{
    name:'shift',
    components: {CstItem,timePicker},
    data(){
      return {
        action:'action',
        shift_id:'',
        currIndex:'',
        attribute:'',
        showPicker:false,
        showColorBox:false,
        pickerType:'time',
        pickerValue:'',
        colorList:['#81B1FF','#9FE3FF',"#FF6059","#FF66DD","#B566FF","#FFAC5B","#FFF66D","#8DFF72","#98FFC6"],
        value: [],
        shiftlist:[],
        shift_info:{//
          shift_name:'',
          shift_color:'#81B1FF',
          shift_id:'',
          day_mins:'',
          detail_list:[
            {
              detail_id:'',
              end_time:'请选择时间',
              shift_mins:'',
              start_time:'请选择时间',
              is_over_day:0
            }
          ]
        }
      }
    },
    computed:{

    },
    filters:{
      mins:function(e){
        let h=Math.round(e/60)
        let m=e%60
        if(h<10){
          h='0'+h
        }
        if(m<10){
          m='0'+m
        }
        return h+':'+m
      },
    },
    activated(){
    },
    mounted(){
      this.action=this.$route.params.action
      this.shift_id=this.$route.params.shift_id
      if(this.action==='check'){
        this.shiftDetail()
      }
      this.getShiftList()

    },
    methods: {
      getShiftList(){
        const  requestData={
          curr_page:1,
          page_size:100,
          query:{
            shift_name:'',
            calendar_name:''
          }
        }
        shiftList(requestData).then(res => {
          this.shiftlist=res.shift_list

        }).catch(error => {
          $toast.show(error.description)
        })
      },
      add(){
        let time={
          detail_id:'',
          end_time:'请选择时间',
          shift_mins:'',
          start_time:'请选择时间',
          is_over_day:0
        }
        this.shift_info.detail_list.push(time)
      },
      deleteTime(index){
        this.shift_info.detail_list.splice(index,1)
      },
      shiftDetail(){
        const  requestData={
          query:{
            shift_id:this.shift_id
          }
        }
        shiftDetail(requestData).then(res => {
          this.shift_info=res.shift_info
          // shift_name:'',
          //   shift_color:'#81B1FF',
          //   shift_id:'',
          //   day_mins:'',
          //   detail_list:[
        }).catch(error => {
          $toast.show(error.description)
        })

      },
      shiftModify(){
      	let detail_list=[]
      	this.shift_info.detail_list.forEach((e,i)=>{
      		detail_list.push({
      			detail_id:e.detail_id,
	          end_time:e.end_time,
	          shift_mins:e.shift_mins,
	          start_time:e.start_time,
	          is_over_day:e.is_over_day,
	          seq:i
      		})
      	})
      	this.shift_info.detail_list=detail_list
        const  requestData={
          shift_info:this.shift_info
        }
        if(this.shift_info.shift_name==''){
          $toast.show('请输入班次名称')
          return
        }
        let isShiftName=0
        this.shiftlist.forEach((e,i)=>{
          if(e.shift_name===this.shift_info.shift_name&&e.shift_id!=this.shift_id){
            isShiftName=1
            return
          }
        })
        if(isShiftName===1){
          $toast.show('班次名称重名，请重新设置')
          return
        }
        if(!this.shift_info.detail_list.length>0){
          $toast.show('至少添加一个工作时间段')
          return
        }
        let shift_mins=''
        this.shift_info.day_mins=0
        this.shift_info.detail_list.forEach((item,index)=>{
          this.shift_info.day_mins+=item.shift_mins
          if(item.shift_mins===''){
            shift_mins=index+1
          }
        })
        if(shift_mins!==''){
          $toast.show('时间段'+shift_mins+'设置有误')
          return
        }
        shiftModify(requestData).then(res => {
          $toast.show('班次编辑成功')
          $router.go(-1)
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      timePicker(e,a,i){
        if(this.action==='check'){
          return
        }
        this.showPicker=true
        this.pickerValue=e[a]
        this.currIndex=i
        this.attribute=a
      },
      chosen(item){
        let a=this.attribute
        if(a==='end_time'){//结束时间选择
          this.endTime(item)
        }else{
          this.startTime(item)
        }

      },
      endTime(item){
        if(this.shift_info.detail_list[this.currIndex].end_time==item){
          return
        }
        let arr=this.shift_info.detail_list[this.currIndex].start_time.split(":")
        let arr2=item.split(":")
        let start_time=Number(arr[0])*60+Number(arr[1])
        let end_time=Number(arr2[0])*60+Number(arr2[1])
        if(this.currIndex===0){
          if(end_time>start_time){
            this.shift_info.detail_list[this.currIndex].shift_mins=end_time-start_time
            this.shift_info.detail_list[this.currIndex].is_over_day=0
          }else{
            this.shift_info.detail_list[this.currIndex].shift_mins=1440+end_time-start_time
            this.shift_info.detail_list[this.currIndex].is_over_day=1
          }
          this.shift_info.detail_list[this.currIndex].end_time=item
          this.shift_info.detail_list.forEach((e,i)=>{
            if(i>this.currIndex){
              this.shift_info.detail_list[i]={
                detail_id:'',
                end_time:'请选择时间',
                shift_mins:'',
                start_time:'请选择时间',
                is_over_day:0
              }
            }
          })
        }else{
          let shift_mins=''
          if(end_time>start_time){
            if(this.shift_info.detail_list[this.currIndex-1].is_over_day==1){
              let shift_start_time=this.shift_info.detail_list[0].start_time.split(":")
              shift_start_time=Number(shift_start_time[0])*60+Number(shift_start_time[1])
              if(end_time<=shift_start_time){
                shift_mins=end_time-start_time
                this.shift_info.detail_list[this.currIndex].is_over_day=1
                this.shift_info.detail_list[this.currIndex].shift_mins=shift_mins
              }else{
                $toast.show('一个班次不能超出24h')
                return
              }
            }else{
              shift_mins=end_time-start_time
              this.shift_info.detail_list[this.currIndex].is_over_day=0
              this.shift_info.detail_list[this.currIndex].shift_mins=shift_mins
            }
            this.shift_info.detail_list[this.currIndex].end_time=item
            this.shift_info.detail_list.forEach((e,i)=>{
              if(i>this.currIndex){
                this.shift_info.detail_list[i]={
                  detail_id:'',
                  end_time:'请选择时间',
                  shift_mins:'',
                  start_time:'请选择时间',
                  is_over_day:0
                }
              }
            })
          }else{
            shift_mins=1440+end_time-start_time
            this.shift_info.detail_list[this.currIndex].shift_mins=shift_mins
            this.shift_info.detail_list[this.currIndex].is_over_day=1
            this.shift_info.detail_list[this.currIndex].end_time=item
          }
        }

      },
      startTime(item){
        if(this.shift_info.detail_list[this.currIndex].start_time===item){
          return
        }else{//开始时间
          if(this.currIndex===0){
            this.shift_info.detail_list[this.currIndex].start_time=item
            this.shift_info.detail_list.forEach((e,i)=>{
              if(i>this.currIndex){
                this.shift_info.detail_list[i]={
                  detail_id:'',
                  end_time:'请选择时间',
                  shift_mins:'',
                  start_time:'请选择时间',
                  is_over_day:0
                }
              }
            })
          }else{
            let arr=this.shift_info.detail_list[this.currIndex-1].end_time.split(":")
            let shift_start_time=this.shift_info.detail_list[0].start_time.split(":")
            shift_start_time=Number(shift_start_time[0])*60+Number(shift_start_time[1])
            let arr2=item.split(":")
            let end_time=Number(arr[0])*60+Number(arr[1])
            let start_time=Number(arr2[0])*60+Number(arr2[1])
            if(start_time>=end_time){
              if(this.shift_info.detail_list[this.currIndex-1].is_over_day==1){
                if(start_time<shift_start_time){
                  this.shift_info.detail_list[this.currIndex].start_time=item
                  this.shift_info.detail_list.forEach((e,i)=>{
                    if(i>this.currIndex){
                      this.shift_info.detail_list[i]={
                        detail_id:'',
                        end_time:'请选择时间',
                        shift_mins:'',
                        start_time:'请选择时间',
                        is_over_day:0
                      }
                    }
                  })
                  this.shift_info.detail_list[this.currIndex].is_over_day=1
                }else{
                  $toast.show('一个班次不能超出24h')
                }
              }else{
                this.shift_info.detail_list[this.currIndex].start_time=item
                this.shift_info.detail_list.forEach((e,i)=>{
                  if(i>this.currIndex){
                    this.shift_info.detail_list[i]={
                      detail_id:'',
                      end_time:'请选择时间',
                      shift_mins:'',
                      start_time:'请选择时间',
                      is_over_day:0
                    }
                  }
                })
                this.shift_info.detail_list[this.currIndex].is_over_day=0
              }
            }else{
              if(start_time<shift_start_time){
                if(this.shift_info.detail_list[this.currIndex-1].is_over_day==1){
                  $toast.show('开始时间必须大于上个时间段的结束时间')
                }else{
                  if(this.shift_info.detail_list[this.currIndex].start_time==item){
                    return
                  }
                  this.shift_info.detail_list[this.currIndex].start_time=item
                  this.shift_info.detail_list.forEach((e,i)=>{
                    if(i>this.currIndex){
                      this.shift_info.detail_list[i]={
                        detail_id:'',
                        end_time:'请选择时间',
                        shift_mins:'',
                        start_time:'请选择时间',
                        is_over_day:0
                      }
                    }
                  })
                  this.shift_info.detail_list[this.currIndex].is_over_day=1
                }
              }else{
                $toast.show('一个班次不能超出24h')
              }
            }
          }
          this.shift_info.detail_list[this.currIndex].end_time='请选择时间'
        }
      },
      selectColor(){//选择颜色
        if(this.action==='check'){
          return
        }
        this.showColorBox=true
      },
      checkedColor(item){//选择颜色
        this.shift_info.shift_color=item
        this.showColorBox=false
      }
    },
  }

</script>

<style lang="scss" scoped>
.compute-item{margin-top: -1px !important;}
.mask{
  position: absolute;
  width: 100%;
  height: 100%;
  top:0;
  left: 0;
  z-index: 16;
  background-color: #2e2e2d;
  opacity: .4;
}
.addBtn{
  text-align: center;
  margin: 20px 0;
  color: #416EB6;
  background: #fff;
  border-top: 1px solid #eee;
  border-bottom: 1px solid #eee;
  line-height: 40px;
}
.selectColor{
  .colorBox{
    width: 100%;
    height: 225px;
    position: absolute;
    bottom: -100%;
    left: 0;
    background: #fff;
    z-index: 20;
    border-top-left-radius:10px ;
    border-top-right-radius:10px ;
    transition: all .3s;
    &.active{
      bottom: 0;
    }
    p{
      text-align: center;
      line-height: 60px;
      margin: 0;
    }
    ul{
      width: 270px;
      height: 165px;
      margin: 0 auto;
      border-radius: 10px;
      overflow: hidden;
      li{
        width: 80px;height: 35px;
        float: left;margin: 5px;
        background: #007aff;

      }
    }
  }
}

</style>

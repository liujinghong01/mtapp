<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>{{ year+'/'+month }}</span>
      <i class="right" @click="action='action'" v-show="this.calendar_id==='new'" ></i>
      <i class="right" @click="action='action'" v-show="action!=='action'&&this.calendar_id!=='new'" style="flex: 0 0 21%;">编辑</i>
      <i class="right" @click="cancelEdit"  v-show="action==='action'&&this.calendar_id!=='new'" style="flex: 0 0 21%;">取消</i>
    </div>
    <div class="calendar">
      <div class="cd-header">
        <span>日</span>
        <span>一</span>
        <span>二</span>
        <span>三</span>
        <span>四</span>
        <span>五</span>
        <span>六</span>
      </div>
      <div class="cd-body">
        <ul id="calendar">
          <li @click='selectShift(item.shift_info.shift_name,"special",index)' v-for="(item,index) in curr_month" :style="{background:item.shift_info.shift_color}">
            <span v-show="item.date!==''">{{item.date.split('-')[2]}}</span>
            <p v-show="item.date!==''">{{item.shift_info.shift_name}}</p>
            <span v-show="item.date!==''">{{item.shift_info.day_mins | filterDate}}</span>
          </li>
        </ul>
        <div class="cd-btns" >
        	<div v-if="Multiselect===true" >
        		<span @click='cancelSelect' class="btn">取消</span>
          	<span @click="MultiselectShift" class="btn">确认</span>
        	</div>
        	<div v-else>
        		<span v-show="action==='action'" @click='Multiselect=true' class="btn">多选</span>
	          <span class="btn" @click="prevClick">《</span>{{ month | filterMonth }}<span class="btn" @click="nextClick">》</span>
        	</div>
        </div>
      </div>

    </div>
    <cst-item   label="日历名称" v-model="calendar_name" placeholder="请输入日历名称" :action="action" editType="text"></cst-item>
    <!--<cst-item @click.native="selectShift(calendar_info.shift_name,'default')" label="默认班次" v-model="calendar_name" :hasArrow="action==='action'?true:false"  :action="'check'" editType="text" ></cst-item>-->
    <div  class="mw-page-footer" v-show="action!=='check'">
      <div class="btns" >
        <div class="btn" @click="calendarModify()">
          保存
        </div>
      </div>
    </div>
		<time-picker
      v-on:chosen="chosen"
      :showPicker="showPicker"
      :pickerType="pickerType"
      :pickerValue="pickerValue"
      :pickerList='pickerList'
      v-on:hideMask="showPicker=false"></time-picker>
  </div>
</template>

<script>
  import { shiftList,calendarModify,calendarDetail } from '@/api/production/shopCalendar'
  import SwipeItem from "../../../components/swipeItem";
  import CstItem from "../../../components/cstItem"
  import timePicker from "../../../components/timePicker"
  import {currDate,todayDate,currDays} from "@/utils"
  export default{
    name:'calendar',
    components: {CstItem,timePicker},
    data(){
      return {
      	Multiselect:false,//多选
      	MultiselectList:[],//多选列表
      	selectType:'',//日历默认班次:default  特殊班次：special
      	showPicker:false,
      	pickerType:'',
      	pickerValue:'',
      	pickerList:[],
        calendar_name:'',
        calendar_id:'',
        date_list:[
          // {the_date:"2017-12-28",shift_info:{shift_type:"2",shift_color:"#9FE3FF",shift_id:302,shift_name:"加晚班",day_mins:480}},
          // {the_date:"2017-12-29",shift_info:{shift_type:"2",shift_color:"#9FE3FF",shift_id:302,shift_name:"加晚班",day_mins:480}},
          // {the_date:"2017-12-30",shift_info:{shift_type:"2",shift_color:"#9FE3FF",shift_id:302,shift_name:"加晚班",day_mins:480}},
          ],
        date_list_back:[],
        action:'action',
        year:'',
        month:'',
        monthLength:'',
        shiftList:[],
        curr_month:[],
      }
    },
    computed:{

    },
    activated(){
    },
    mounted(){
      this.action=this.$route.params.action
      this.calendar_id=this.$route.params.calendar_id
      this.getShiftList()//先获取班次列表，再生成当前月份的日历
      let date=todayDate().split('-')
      this.year=Number(date[0])
      this.month=Number(date[1])
      if(this.month<10){
        this.month='0'+this.month
      }
      this.day=Number(date[2])
      this.getMonthLength()


    },
    filters:{ // 过滤器
      filterDate:function ( value ) { // 局部过滤器
        let h=Math.floor(Number(value)/60)
        let m=Number(value)%60
        if(m<10){
          m='0'+m
        }
        return h+'.'+m
      },
      filterMonth:function (val) {
        if(Number(val)<10){
          return '0'+Number(val)
        }else{
          return val
        }
      }
    },
    methods: {
      cancelEdit(){
        this.action=''
        this.date_list=this.date_list_back
        this.cancelSelect()
        this.currMonth()

      },
      getMonthLength(){
        if(this.month==1||this.month==3||this.month==5||this.month==7||this.month==8||this.month==10||this.month==12){
          this.monthLength=31
        }else if(this.month==2){
          if(Number(this.year)%4===0){
            this.monthLength=29
          }else{
            this.monthLength=28
          }
        }else{
          this.monthLength=30
        }
      },
      nextClick(){
        if(this.month===12){
          this.year++
          this.month=1
        }else{
          this.month++
        }
        this.getCalendarDetail()
      },
      prevClick(){
        if(this.month===1){
          this.year--
          this.month=12
        }else{
          this.month--
        }
        // this.currMonth()
        this.getCalendarDetail()
      },
    	currMonth(){
        this.getMonthLength()
        let week=this.getWeekday(this.year+'-'+this.month+'-1')
        let monthLength=this.monthLength+week
        let defaul=''
        let rest={}
        this.shiftList.forEach((item,index)=>{
          	if(item.shift_id===1){
          		defaul=item
          	}else if(item.shift_id===2){
          		rest=item
          	}
        })
        let _this=this
        this.curr_month=[]
        for(var i=0;i<monthLength;i++){
          if(i<week){
            const dayInfo={date:'',shift_info:{shift_id:'',shift_name:'',day_mins:'',shift_color:''}}
            _this.curr_month.push(dayInfo)
          }else{
            let day=(i-week+1)
            let month=_this.month
            if(Number(day)<10){
              day='0'+Number(day)
            }
            if(Number(month)<10){
              month='0'+Number(month)
            }
            let curDate=_this.year+'-'+month+'-'+day

            if(this.date_list.length>0){
              this.date_list.forEach((e,i)=>{
                if(e.the_date===curDate){
                  const dayInfo={date:curDate,shift_info:e.shift_info}
                  _this.curr_month.push(dayInfo)
                  curDate=null
                }
              })
              if(curDate!==null){
                if(this.getWeekday(curDate)==0||this.getWeekday(curDate)==6){
                  const dayInfo={date:curDate,shift_info:rest}
                  _this.curr_month.push(dayInfo)
                  // console.log(curDate.shift_info.shift_name)
                }else{
                  const dayInfo={date:curDate,shift_info:defaul}
                  _this.curr_month.push(dayInfo)
                }
              }

            }else{
              if(this.getWeekday(curDate)==0||this.getWeekday(curDate)==6){
                const dayInfo={date:curDate,shift_info:rest}
                _this.curr_month.push(dayInfo)
              }else{
                const dayInfo={date:curDate,shift_info:defaul}
                _this.curr_month.push(dayInfo)
              }
            }
          }
        }
    	},
    	selectShift(pickerValue,selectType,index){

    	  if(this.action === 'check'){
    	    return
        }
        let currDate=todayDate()
        if(this.year<todayDate().split('-')[0]){
            return
        }
        if(this.year==todayDate().split('-')[0]){
    	    if(Number(this.month)<todayDate().split('-')[1]){
            return
          }
          if(this.month==todayDate().split('-')[1]){
    	      let day=this.curr_month[index].date.split('-')[2]
            if(Number(day)<todayDate().split('-')[2]){
              return
            }
          }

        }
    		if(this.Multiselect===true){
    			event.currentTarget.classList.add('active')
    			this.MultiselectList.push(index)
    			if(selectType==='default'){
    				$toast.show('先完成多选再设置默认班次')
    			}
    			return
    		}else{
    			this.MultiselectList=[index]
    		}
    		this.showPicker=true,
      	this.pickerType='shift',
      	this.pickerValue=pickerValue,
      	this.pickerList=this.shiftList
      	this.selectType=selectType
    	},
    	MultiselectShift(){
    		if(this.MultiselectList.length>0){
    			this.showPicker=true,
	      	this.pickerType='shift',
	      	this.pickerValue='',
	      	this.pickerList=this.shiftList
	      	this.selectType='Multiselect'
    		}else{
    			$toast.show('请选择日期')
    		}

    	},
    	cancelSelect(){
    		this.Multiselect=false
    		if(this.MultiselectList.length>0){
    			this.MultiselectList.forEach((item,index)=>{
	      		document.querySelectorAll("#calendar>li")[Number(item)].classList.remove("active")
	      	})
    		}
      	this.MultiselectList=[]
    	},
      getWeekday(sDate){
        var dt = new Date(sDate.replace(/-/g, '/'));
        return dt.getDay()
      },
      getShiftList() {
        const requestData = {
          curr_page: 1,
          page_size: 100,
          query: {
            shift_name: '',
//            calendar_name: ''
          }
        }
        shiftList(requestData).then(res => {
        	this.shiftList=res.shift_list
          if(this.calendar_id!=='new'){
            this.getCalendarDetail()
          }else{
            this.currMonth()
          }
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      getCalendarDetail(){
        const requestData = {
          query: {
            calendar_id: this.calendar_id,
            end_date:this.year+'-'+this.month+'-'+this.monthLength ,
            start_date: this.year+'-'+this.month+'-1'
          }
        }
        calendarDetail(requestData).then(res => {
          this.calendar_name=res.calendar_info.calendar_name
          this.date_list=[]
          this.date_list_back=[]
          res.calendar_info.detail_list.forEach((e,i)=>{
            let data={
              the_date:e.the_date,
              shift_info:e.shift_info
            }
            this.date_list.push(data)
            this.date_list_back.push(data)
          })
          this.currMonth()
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      calendarModify(){
        this.cancelSelect()
    	  if(this.calendar_name.trim()===''){
          $toast.show('日历名称有误')
          return
        }
        let dateList=[]
        if(this.date_list.length>0){
          this.date_list.forEach((e,i)=>{
            dateList.push({the_date:e.the_date,shift_id:e.shift_info.shift_id})
          })
        }
        if(this.date_list_back.length>0){
    	    if(dateList.length>0){
            this.date_list_back.forEach((e,i)=>{
              let the_date=e.the_date
              let isDate=false
              dateList.forEach((e,i)=>{
                console.log(e.the_date===the_date)
                if(e.the_date===the_date){
                  isDate=true
                  return
                }
              })
              if(isDate){
                return
              }else{
                dateList.push({the_date:e.the_date,shift_id:''})
              }

            })
          }else{
            this.date_list_back.forEach((e,i)=>{
              dateList.push({the_date:e.the_date,shift_id:''})
            })
          }
        }
        const requestData = {
          calendar_info: {
            calendar_id:this.calendar_id==='new'?'':this.calendar_id,
            calendar_name:this.calendar_name,
            date_list:dateList
          }
        }
        calendarModify(requestData).then(res => {
          this.calendar_id=res.calendar_info.calendar_id
          this.action='check'
          $toast.show('保存成功')
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      detailList(day,shift){
        if(this.getWeekday(day.date)===0||this.getWeekday(day.date)===6){
          if(day.shift_info.shift_id!==2){//添加或修改特殊班次
            if(this.date_list.length>0){
              let index=''
              this.date_list.forEach((e,i)=>{
                if(e.the_date===day.date){
                  index=i
                }
              })
              if(index!==''){
                this.date_list[index].shift_info=shift
              }else{
                this.date_list.push({
                  the_date:day.date,
                  shift_info:shift
                })
              }
            }else{
              this.date_list.push({
                the_date:day.date,
                shift_info:shift
              })
            }
          }else{//删除或修改特殊班次
            if(this.date_list.length>0){
              let index=''
              this.date_list.forEach((e,i)=>{
                if(e.the_date===day.date){
                  index=i
                }
              })
              if(index!==''){
                this.date_list.splice(index,1)
              }
            }
          }
        }else{
          if(day.shift_info.shift_id!==1){
            if(this.date_list.length>0){
              let index=''
              this.date_list.forEach((e,i)=>{
                if(e.the_date===day.date){
                  index=i
                }
              })
              if(index!==''){
                this.date_list[index].shift_info=shift
              }else{
                this.date_list.push({
                  the_date:day.date,
                  shift_info:shift
                })
              }
            }else{
              this.date_list.push({
                the_date:day.date,
                shift_info:shift
              })
            }
          }else{
            if(this.date_list.length>0){
              let index=''
              this.date_list.forEach((e,i)=>{
                if(e.the_date===day.date){
                  index=i
                }
              })
              if(index!==''){
                this.date_list.splice(index,1)
              }
            }
          }
        }
        console.log(this.date_list)
      },
      chosen(e){
      	if(this.selectType==='default'){//默认班次
      		// this.calendar_info.shift_name=e.split('(')[0]
	      	// this.shiftList.forEach((item,index)=>{
	      	// 	if(item.shift_name===this.calendar_info.shift_name){
	      	// 		this.calendar_info.shift_id=item.shift_id
	      	// 	}
	      	// })
	      	// this.currMonth()
	      	// this.MultiselectList=[]
      	}else if( this.selectType==='Multiselect'){//多选
      		let shiftName=e.split('(')[0]
      		let shift=''
      		this.shiftList.forEach((item,index)=>{
		      		if(item.shift_name===shiftName){
		      			shift=item
		      		}
		      })
      		this.MultiselectList.forEach((item,index)=>{
            let day=this.curr_month[Number(item)]
            day.shift_info=shift
            this.detailList(day,shift)
      			document.querySelectorAll("#calendar>li")[Number(item)].classList.remove("active")
      		})
      		this.MultiselectList=[]
      		this.Multiselect=false
      	}else{
      		if(this.Multiselect===false){//单选
      			let Index=this.MultiselectList
      			let shiftName=e.split('(')[0]
		      	this.shiftList.forEach((item,index)=>{
		      		if(item.shift_name===shiftName){
		      		  let day=this.curr_month[Index]
                day.shift_info=item
                this.detailList(day,item)
		      		}
		      	})
		      	this.MultiselectList=[]
	      	}
      	}

      },
      getLocalTime(nS) {
        let date= new Date(parseInt(nS) * 1000).toLocaleString().slice(0,10).split('/')
        return date[0]+'-'+date[1]+'-'+date[2];
      }

  }
  }
</script>

<style lang="scss" scoped>
.calendar{
  width: 100%;
  height: 360px;
  margin-top: -14px;
  .cd-header{
    height: 30px;
    line-height: 30px;
    background: #416eb6;color: #fff;
    &>span{
      /*font-size: 17px;*/
      width: 14.28%;
      float: left;
      text-align: center;
    }
  }
  .cd-body{
    width: 100%;
    position: relative;
    ul{
      width: 100%;
      height: 330px;
      border-bottom: 1px solid #416eb6;
      position:relative;
      background: #fff;
      &:after{
        content: " ";
        position: absolute;
        right: 0px;
        bottom: 0px;
        width: 1px;
        height: 330px;
        background:  #416eb6;
      }
      &:before{
        content: " ";
        position: absolute;
        left: 0px;
        bottom: 0px;
        width: 1px;
        height: 330px;
        background:  #416eb6;
      }
      li{
        width:  14.28%;
        height: 55px;
        float: left;
        text-align: center;
        line-height: 30px;
        box-sizing: border-box;
        border-bottom: 1px solid #416eb6;
        border-right: 1px solid #416eb6;
        line-height: 20px;
        position: relative;
        &.active:before{
        	position: absolute;
        	content: " ";
        	width: 6px;
        	height: 6px;
        	border-radius: 50%;
        	background: red;
        	top: 3px;right: 3px;
        }
        p{
          font-size: 10px;
          margin: 0;
          line-height: 16px;
          height: 16px;
          width: 100%;
          overflow: hidden;
        }
        &:nth-of-type(7n+1){
          border-left: 1px solid #416eb6;
        }

      }
    }
    .cd-btns{
      text-align: right;
      width: 230px;
      height: 43px;
      position: absolute;
      right: 0;
      bottom: 0;
      .btn{
        display: inline-block;
        width: 40px;
        text-align: center;
        height: 23px;
        line-height: 23px;
        margin: 10px 5px;
        background:#416eb6 ;
        color: #fff;
        border-radius: 5px;
        font-size: 12px;
      }
    }
  }
}
</style>

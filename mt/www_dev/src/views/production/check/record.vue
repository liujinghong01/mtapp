<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>{{ year+'/'+month }}</span>
      <i class="right" ></i>
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
          <li  v-for="(item,index) in curr_month" >
            <p>{{item }}</p>
          </li>
        </ul>
        <div class="cd-btns">
          <div class="btn">《</div>
          {{ month }}
          <div class="btn">》</div>
        </div>
      </div>
    </div>

    <div class="recordList">

    </div>
    </div>
</template>

<script>
  import SwipeItem from "../../../components/swipeItem";
  import CstItem from "../../../components/cstItem"
  import {todayDate} from "@/utils"
  export default{
    name:'checkRecord',
    components: {CstItem},
    data(){
      return {
        year:'',
        month:'',
        week:'',
        curr_month:[],
      }
    },
    computed:{

    },
    activated(){
    },
    mounted(){
      let days=todayDate()
      this.year=days.slice(0,4)
      this.month=days.slice(5,7)
      this.getCalendar()
    },
    methods: {
      getDateWeek(e){
        var   tmpdate   =   new   Date(e.split('-')[0],parseInt(e.split('-')[1])-1,e.split('-')[2]);
        return  tmpdate.getDay()
      },
      getCalendar(){
        let month=parseInt(this.month)
        let days=this.year+'-'+this.month+'-01'
        this.week=this.getDateWeek(days)
        if(month===2){
          if(parseInt(this.year)%4===0){
            this.add(29)
          }else{
            this.add(28)
          }
        }else if(month===4||month===6||month===9||month===11){
          this.add(30)
        }else{
          this.add(31)
        }
      },
      add(e){
        this.curr_month.length=0
        for (let i=0;i<this.week;i++){
          this.curr_month.push('')
        }
        for (let i=0;i<e;i++){
          this.curr_month.push(i+1)
        }
      }
  }
  }

</script>

<style lang="scss" scoped>
.calendar{
  width: 100%;
  height: 240px;
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
      height: 210px;
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
        height: 210px;
        background:  #416eb6;
      }
      li{
        width:  14.28%;
        height: 35px;
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
          line-height: 35px;
          height: 35px;
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
      width: 180px;
      height: 35px;
      position: absolute;
      right: 0;
      bottom: 0;
      .btn{
        display: inline-block;
        width: 40px;
        text-align: center;
        height: 23px;
        line-height: 23px;
        margin: 6px 5px;
        background:#416eb6 ;
        color: #fff;
        border-radius: 5px;
        font-size: 12px;
      }
    }
  }

}
.recordList{
  width: 100%;
  height: 100%;
}
</style>

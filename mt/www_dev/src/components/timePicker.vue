<template>
  <section >
    <div v-show="showPicker" class="mask" @click="$emit('hideMask')"></div>
    <div  class="pickerBox" :class="showPicker?'active':''">
      <div class="pkHeader">
        <span @click="cancel">取消</span>
        <span @click="ok" class="right">确认</span>
      </div>
      <div class="pkBody">
        <div class="slideList" :style="{width:width}" @touchend="touchend" @scroll="isscroll" v-for="(item,index) in selectList" >
          <div >
            <p> </p>
            <p> </p>
            <p class="active" v-for="(e,i) in item">{{ e }}</p>
            <p> </p>
            <p> </p>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
  // import MwRadio from "./mwRadio";
  // import {storageList} from '@/api/storage/baseInfo'

  export default{
    components: {},
    name:'timePicker',
    data(){
      return {
        currIndex:0,
        scrollTop:'',
        interval:'',
        width:'100%',
        result:[],
        selectList:[
          [],
          []
        ]
      }
    },
    computed:{

    },
    props:{
      showPicker:{
        type:Boolean,
        default:false,
        required:true
      },
      pickerType:{
        type:String,
        default:'time',//时间：time 日期:date 地区：region 班次：shift 单选radio
        required:true
      },
      pickerValue:{

      },
      pickerList:{
      }
    },
    methods: {
      ok(){
        switch (this.pickerType){
          case 'time':{
          	this.$emit('chosen',this.result[0]+':'+this.result[1])
          	break
          }
          case 'shift':{
            this.$emit('chosen',this.result[0])
            break
          }
          case 'radio':{
            this.$emit('chosen',this.result[0])
            break
          }
        }
        this.$emit('hideMask')
      },
      cancel(){
        this.$emit('hideMask')
      },
      touchend(){
        const _this=this
        _this.scrollTop=event.currentTarget.scrollTop
        let elem=event.currentTarget

        clearInterval(_this.interval)
        _this.interval = null
        _this.interval = setInterval(function () {
          _this.scroll(elem)
        }, 100);

      },
      scroll(e) {
        if(e.scrollTop == this.scrollTop) {
          clearInterval(this.interval)
          this.interval = null
          let index=Math.round(e.scrollTop/32)
          e.scrollTop=index*32
          let num=''
          if(this.pickerType==='radio'){
            num=this.pickerList[index]
          }else{
            num=e.firstChild.children[index+2].innerText
          }
          console.log("取值"+ index)
          let elemList=e.parentNode.children
          var _this=this
          for(var i=0;i<elemList.length;i++){
            if(e===elemList[i]){
                _this.result[i]=num
            }
          }
        } else {
          this.scrollTop = e.scrollTop
        }
      },
      isscroll(){
        let index=Math.round(event.currentTarget.scrollTop/32)
        let arr=event.currentTarget.firstChild.children
        for(var i=0;i<arr.length;i++ ){
          if(arr[i].classList.contains("active")){
            arr[i].classList.remove("active")
          }
          if(arr[i].classList.contains("activePrev")){
            arr[i].classList.remove("activePrev")
          }
        }
        arr[index+1].classList.add('activePrev')
        arr[index+2].classList.add('active')
      },
      refresh(){
        switch (this.pickerType){
          case 'time':{
            for(var i=0;i<24;i++){
              if(i<10){
                this.selectList[0][i]="0"+i
              }else{
                this.selectList[0][i]=i
              }

            }
            for(var i=0;i<60;i++){
              if(i<10){
                this.selectList[1][i]="0"+i
              }else{
                this.selectList[1][i]=i
              }
            }
            let elem=document.querySelectorAll('.slideList')
            if(!(this.pickerValue==='' || this.pickerValue==='请选择时间'|| this.pickerValue==='请选择')){

              let h=Number(this.pickerValue.split(':')[0])
              let m=Number(this.pickerValue.split(':')[1])
              if(h<10){
                h='0'+h
              }
              if(m<10){
                m='0'+m
              }
              this.result[0]=h
              this.result[1]=m
              setTimeout(function(){
                elem[0].scrollTop=h*32
                elem[1].scrollTop=m*32
              },100)
            }else{
              this.result[0]='12'
              this.result[1]='00'
              setTimeout(function(){
                elem[0].scrollTop=12*32
                elem[1].scrollTop=0
              },100)
            }

            break
          }
          case 'shift':{
          	this.selectList=[[]]
          	this.pickerList.forEach((item,index)=>{
          		this.selectList[0].push(item.shift_name+"(工作时长:"+Math.floor(item.day_mins/60)+':'+item.day_mins%60+')')
          	})
          	let elem=document.querySelectorAll('.slideList')
            if(!(this.pickerValue==='' || this.pickerValue==='请选择班次')){
							let shift=this.pickerValue
							this.result[0]=shift
							let shiftIndex=0
							this.pickerList.forEach((item,index)=>{
								if (item.shift_name==shift){
									shiftIndex=index
								}
							})
							setTimeout(function(){
                elem[0].scrollTop=shiftIndex*32
              },100)
            }else{
              this.result[0]=this.selectList[0][0]
              setTimeout(function(){
                elem[0].scrollTop=0
              },100)
            }

            break
          }
          case 'radio':{
            this.selectList=[[]]
            this.pickerList.forEach((item,index)=>{
              this.selectList[0].push(item.val)
            })
            let elem=document.querySelectorAll('.slideList')
            if(this.pickerValue.id===''||this.pickerValue.id===undefined||this.pickerValue.id===null){
              this.result[0]=this.pickerList[0]
              setTimeout(function(){
                elem[0].scrollTop=0
              },100)
            }else{
              let shift=this.pickerValue
              let shiftIndex=0
              this.pickerList.forEach((item,index)=>{
                if (item.id===shift.id){
                  shiftIndex=index
                }
              })
              let _this=this
              setTimeout(function(){
                elem[0].scrollTop=shiftIndex*32
                _this.result[0]=_this.pickerList[shiftIndex]
              },100)
            }
            break
          }
          case 'region':{

            break
          }
        }
        this.width=100/this.selectList.length+'%'
      },
    },
    mounted(){
    },
    watch:{
      'showPicker':function (newVal,oldVal) {
        clearInterval(this.interval)
        this.interval=null
        if(newVal){
          this.filterStr = ''
          this.refresh()
        }
      }
    }
  }
</script>

<style lang="scss" scoped>
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
  .pickerBox{
    width: 100%;
    height: 205px;
    background: #f5f5f5;
    z-index: 100;
    position: absolute;
    transform: translateY(0);
    left: 0;
    box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
    transition:transform .5s ;
    &.active{
      transform: translateY(-205px);
    }
    .pkHeader{
      width: 100%;
      height: 45px;
      background: #fff;
      box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
      span{
        width: 50px;height: 45px;
        text-align: center;
        float: left;
        line-height: 45px;
        &.right{
          float: right;
          color:#4c0 ;
        }
      }

    }
    .pkBody{
      width: 100%;height: 160px;
      .slideList{
        width: 100%;
        height: 100%;
        float: left;
        overflow-y:scroll ;
        -webkit-overflow-scrolling: touch;
        &::-webkit-scrollbar{
          width: 0;height: 0;
        }
        div{
          width: 100%;
          p{
            height: 32px;
            line-height: 32px;
            font-size: 17px;
            text-align: center;
            margin: 0;
            color: #000;
            opacity: 0.1;
            &.active{
              opacity: 1;
            }
            &.active{
              opacity: 1;
            }
            &.activePrev{
              opacity: .4;
            }
            &.active + p{
              opacity: 0.4;
            }
            &.active + p + p{
              opacity: 0.1;
            }
          }
        }
      }
    }
  }
</style>

<template>
  <div class="compute-item">

    <span class="label" :class="isMust?'isMust':''">
      {{label}}</span>
    <div class="value" v-show="editType!=='color'">
      <span v-if="!canEdit||action==='check' || action==='approve' || action==='checkOnly'">{{value}}</span>
      <input  v-else :type="editType" :placeholder="placeholder" ref="input"
              @input="input"
              :value="value"/>
    </div>
    <div class="value" v-show="editType==='color'">
      <span class="color" :style="{ background: color}"></span>
    </div>
    <div class="note" v-show="hasArrow">
      <span v-show="hasArrow&&arrowText" class="item-notes" @click="noteClick">{{arrowText}}</span>
      <i v-show="hasArrow" class="icon ion-ios-arrow-right arrow "></i>
    </div>
    <div class="hairline-top"></div>
  </div>
</template>
<script>
  let lock = false
  export default{
    name:'cstItem',
    data(){
      return {
        selectId: 'select-' + Math.random().toString(36).substring(3, 6),
      }
    },
    props:{
      label:{   //标签
        type:String,
        required:true
      },
      value:{    //值
        type:'',
        default:''
      },
      placeholder:{
        type:String,
        default:''
      },
      canEdit:{ //是否可以编辑
        type:Boolean,
        default:true
      },
      hasArrow:{
        type:Boolean,
        default:false
      },
      arrowText:{
        type:[String,Number],
        default:''
      },
      arrowClick:{
        type:Function
      },
      editType:{
        type:String,
        default:'text'
      },
      isSelect:{
        type:Boolean,
        default:false
      },
      options:{
        type:Array,
      },
      action:{
        type:String,
        default:'check'
      },
      isMust:{
        type:Boolean,
        default:false
      },
      color:''
    },
    methods:{
      noteClick(){
        if(this.arrowClick) this.arrowClick()
      },
      input(e){
        let value = e.target.value
        this.$emit('input', value)
      },
    }
  }
</script>
<style scoped lang="scss">
  .compute-item{
    min-height: 49px;
    position: relative;
    padding: 12px 15px;
    background-color: white;
    display: flex;
    &.noneBorder>.hairlineTop:before{background: red;}
    .arrow{
      color: #DDD; position: absolute;right: 15px;top: 14px;font-size: 20px;
      &.down{
        top: 13px;
      }
    }
    .label{
      flex: 0 0  auto;
      height: 100%;
      display: table;
      padding: 7px 10px 7px 0px;
      max-width: 52%;
      color: #484746;
      text-align: left;
      line-height: 12px;
    }
    .value{
      flex: 1 0 auto;
      text-align: right;
      input{
        margin: 0;
        padding: 0;
        border: none;
        color: #ababab;
        width: 100%;
        height: 24px;
        line-height: 24px;
        text-align: right;
        text-overflow: ellipsis;
        white-space: nowrap;
        overflow: hidden;
      }
      span{
        height: 24px;
        line-height: 24px;
        &.color{
          display: inline-block;width: 20px;height: 20px;
          border-radius: 50%;
        }
      }
    }


    .note{
      flex: 0 0 5%;
      .item-notes{
        float: right;
        color: #ababab;
        line-height: 24px;
        /*padding: 0 5px;*/
        padding-right: 15px;
      }
    }
  }
  .isMust{
    position: relative;
    margin-right: 0px;
    &:after{
      display: block;
      content: '*';
      color: #416eb6;
      position: absolute;
      right: -7px;
      top:50%;
      font-size: 20px;
      width: 10px;
      height: 10px;
      margin-top: -4px;

    }
  }
</style>

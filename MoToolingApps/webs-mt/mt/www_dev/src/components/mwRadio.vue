<template>
    <div class="item item-icon-right">
      <div class="label" @click="itemClick">
        <div class="check">
          <i :class="['icon radio-icon',obj.chosen?'ion-android-radio-button-on active':'ion-android-radio-button-off']"></i>
        </div>
        <input type="radio" style="position:absolute; left: -5000px;top: -5000px;"/>
        <div class="radio-content">
          <slot></slot>
        </div>
      </div>
      <i class="icon ion-ios-arrow-right arrow" @click="arrowClick" v-show="hasArrow"></i>
    </div>
</template>

<script>
  export default{
    name: 'mwRadio',
    data(){
      return {
        tmpChosen:{}
      }
    },
    props:{
      obj:{
        type:Object,
        required:true
      },
      chosenObj:{
        type:Object,
      },
      hasArrow:{
        type:Boolean,
        default:true
      },
      iClick:{
        type:Function,
      },
      aClick:{
        type:Function
      }
    },

    methods: {
      itemClick(){
        this.$set(this.obj,'chosen',true)
        if(this.iClick) this.iClick(this.obj)
      },
      arrowClick(){
        if(this.aClick) this.aClick(this.obj)
      }
    }
  }
</script>

<style type='stylesheet/scss' lang='scss' scoped>
  .item{
    .label{
      width: 90%;
      height: 100%;
      .check{
        width: 10%;float:left;height: 100%;padding: 10px 0;
        .radio-icon{
          left: 10px;
          color: #bbbbbb;
          &.active{
            color:#416eb6;
          }
        }
      }
      .radio-content{
        width: 90%;float:right;padding-left: 15px
      }
    }
    .arrow{
      color: #DDD;padding: 10px 0;height: 100% !important
    }
  }
</style>

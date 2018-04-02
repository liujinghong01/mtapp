<template>
    <div class="item item-icon-right">
      <div class="label" @click="itemClick">
        <div class="check">
          <i :class="['icon radio-icon',item.chosen?'ion-android-checkbox active':'ion-android-checkbox-outline-blank']"></i>
        </div>
        <input type="radio" style="position:absolute; left: -5000px;top: -5000px;"/>
        <div class="radio-content">
          <slot></slot>
        </div>
      </div>
      <i class="icon ion-ios-arrow-right arrow" @click="aClick" v-show="hasArrow"></i>
    </div>
</template>

<script>
  export default{
    name: 'mwCheckbox',
    data(){
      return {

      }
    },
    props:{
      obj:{
        type:Object,
        required:true
      },
      hasArrow:{
        type:Boolean,
        default:true
      },
      detailClick:{
        type:Function
      },
      arrowclick:{
        type:Function
      },
      chosenItems:{
        type:Array,
        required:true
      }
    },
    computed:{
      item:function (){
        return this.obj
      },
      items:function () {
        return this.chosenItems
      }
    },
    methods: {
      itemClick(){
        if(this.item.chosen){
          let index = this.items.indexOf(this.item)
          this.items.splice(index,1)
          this.$set(this.item,'chosen',false)
        }else{
          this.$set(this.item,'chosen',true)
          this.items.push(this.item)
        }
      },
      aClick(){
        if(this.arrowclick){
          this.arrowclick(this.item)
        }
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

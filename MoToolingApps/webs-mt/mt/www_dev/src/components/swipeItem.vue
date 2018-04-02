<template>
    <div class="item" :class="hasArrow?'item-icon-right':''"  @touchstart="touchStart" @touchend="touchEnd"  v-on:swipeleft="onSwipeLeft" v-on:itemclick="onItemClick">
      <div :class="['item-cont',obj.swipe?'active':'']">
        <slot></slot>
      </div>
      <div class="hairline-bottom"></div>
      <i class="icon ion-ios-arrow-right" style="color: #DDD;" v-show="hasArrow"></i>
      <div v-show="canSwipe" :class="obj.swipe?'action active':'action'">
        <div class="action-mask" @click.stop="closeMask()"></div>
        <div :class="['action-btns',obj.swipe?'active':'']" @click.stop="onSwipeItemClick">
          <p >{{swipeItemText}}</p>
        </div>
      </div>
    </div>
</template>

<script>
  export default{
    name: 'swipeItem',
    data(){
      return {
        touch:{
          x:0,y:0
        },
      }
    },
    props:{
      obj:{ //item
        type:Object,
        required:true
      },
      canSwipe:{ //是否可以滑动
        type:Boolean,
        default:true
      },
      swipeItemText:{ //滑动之后显示的内容
        type:String,
      },
      hasArrow:{    //是否显示箭头
        type:Boolean,
        default:false
      }
    },
    methods: {
      closeMask(){
        this.$set(this.obj,'swipe',false)
      },
      touchStart(event){
        this.touch.x = event.changedTouches[0].clientX;
        this.touch.y = event.changedTouches[0].clientY;
      },
      touchEnd(e){
        if(this.touch.x-e.changedTouches[0].clientX>50 && this.canSwipe){
          e.currentTarget.dispatchEvent(swipeLeft);
        }else if (this.touch.x - e.changedTouches[0].clientX <= 10 && this.touch.x - e.changedTouches[0].clientX >= 0 ){
          e.currentTarget.dispatchEvent(itemClick);
        }
      },
      onSwipeItemClick(){
        this.$set(this.obj,'swipe',false)
        this.$emit('SwipeItemClick',this.obj)
      },
      onItemClick(){
        if(this.obj.swipe){
          this.$set(this.obj,'swipe',false)
          return
        }
        this.$emit('ItemClick',this.obj)
      },
      onSwipeLeft(){
        this.$set(this.obj,'swipe',true)
        this.$emit('SwipeLeft',this.obj)
      }
    }
  }
</script>

<style type='stylesheet/scss' lang='scss' scoped>
  .item{
    .item-cont{
      -webkit-transition: -webkit-transform .15s ease-in-out;
      transition: transform .25s ease-in-out,-webkit-transform .25s ease-in-out;
      position: relative;
      &.active{
        -webkit-transform: translateX(-20%);
        transform: translateX(-20%);
      }
    }
    .action{
      width: 0;
      transition: all .25s;
      height: 100%;
      position: absolute;
      right: 0;
      top:0;
      .action-mask{
        width: 80%;
        height: 100%;
        background-color: #2e2e2d;
        opacity: 0;
        float: left;
      }
      .action-btns{
        width: 0;
        height: 100%;
        float: right;
        display:flex;
        align-items:center;/*所有子元素都垂直居中了*/
        background-color: #fe3b32;

        -webkit-transition: width .15s ease-in-out;
        transition: width .25s ease-in-out;
        p{
          width: 100%;
          padding: 30% 0;
          color: white;
          text-align: center;
        }
        &.active{
          width: 20%;
        }
      }
      &.active{
        width: 100%;
      }
    }
  }
</style>

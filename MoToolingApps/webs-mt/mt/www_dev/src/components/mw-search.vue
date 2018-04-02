<template>
  <div class="mw-search">
    <div class="mw-search-input">
      <input type="search" :placeholder="placeholder" ref="input"
             @focus="focus"
             @input="input"
             @blur="blur"
             :value="value"/>
      <div v-bind:class="['clear',showClear?'active':'']" @click="clear"><i class="icon ion-ios-close"></i></div>
    </div>
   <span @click="cancel" class="cancel" v-show="showClear||value">{{cancelText}}</span>
  </div>
</template>
<script>
  import _ from 'lodash'
  export default{
    name: 'mwSearch',
    props:{
      placeholder:{
        type:String,
        required:true
      },
      value:{
        type:String,
        required:true
      },
      search:{
        type:Function,
        required:true
      },
      cancelText:{
        type:String,
        required:true
      },
      cancelAction:{
        type:Function,
        required:true
      }
    },
    data(){
      return {
        type:this.inputType,
        showClear:false,
        showPassword:true
      }
    },
    methods: {
      input(e){
        let value = e.target.value
        this.$emit('input', value)
      },
      focus(){
        this.showClear = true
        this.$emit('focus', this.v)
      },
      blur(){
        this.showClear = false
        this.$emit('blur', this.v)
      },
      clear() {
        this.$refs.input.blur()
        this.$refs.input.value = ''
        this.v = ''
        this.$emit('input', '')
        this.showClear = false
        this.$refs.input.focus()
      },
      cancel(){
        if (this.cancelAction) {
          this.cancelAction()
        }
      },
      searchAction:_.debounce(function () {
        if (this.search) {
          this.search()
        }
      },800)
    },
    watch:{
      value:function (newVal) {
        this.showClear = !!newVal
        if (newVal) {
          this.searchAction()
        }
      },
    }
  }
</script>
<style type='stylesheet/scss' lang='scss' scoped>
  .mw-search{
    width: 100%;display: flex;justify-content: center;align-items: center;
    padding-left: 15px;
    .mw-search-input{
      height: 50px;position: relative;background-color: white;border-radius: 5px;width: 80%;display: inline-block;
      input{
        height: 100%;margin: 0 auto;background-color: transparent;width: 100%;
      }
      .clear{
        position: absolute;
        top: 14px;
        right: 9px;
        z-index: 3;
        display: none;
        color: #bbbbbb;
        &.active{
          display: block;
        }
        &.eye{
          right: 30px;
          font-size: 20px;
        }
      }
    }
    .cancel{
      flex: 1;text-align: center;color: #416eb6;transition: all .3s;
    }
  }


</style>

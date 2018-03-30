<template>
  <div class="mw-input">
    <!--<hairline-top></hairline-top>-->
    <input v-if="inputType==='text'" :type="type" :placeholder="placeholder" ref="input"
           @compositionstart="compositionStart"
           @compositionend="compositionEnd"
           @focus="focus"
           @input="input"
           @blur="blur"
           :autofocus="autofocus"
           :value="value"/>
    <input v-else  :type="type" :placeholder="placeholder" ref="input"
           @focus="focus"
           @input="input"
           @blur="blur"
           :autofocus="autofocus"
           :value="value"/>
    <div v-bind:class="['clear',showClear?'active':'']" @click="clear"><i class="icon ion-ios-close"></i></div>
    <div v-if="inputType==='password'">
      <div v-bind:class="['clear eye',showPassword?'':'active']" @click="showPWD"><i class="icon ion-eye-disabled"></i></div>
      <div v-bind:class="['clear eye',showPassword?'active':'']" @click="showPWD"><i class="icon ion-eye"></i></div>
    </div>
    <!--<hairline-bottom></hairline-bottom>-->
  </div>
</template>
<script>
  let lock = false
  export default{
    name: 'mwInput',
    props:{
      placeholder:{
        type:String,
        required:true
      },
      inputType:{
        type:String,
        default:'text',
      },
      autofocus: {
        type:Boolean,
        default:false
      },
      value:{
        type:String,
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
      compositionStart(){
        lock = true
      },
      compositionEnd(){
        lock = false
        this.$emit('input', this.$refs.input.value)
      },
      input(e){
        if (lock) {
          e.preventDefault()
          return
        }
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
      showPWD(){
        if (this.showPassword) {
          this.type = 'text'
        }else {
          this.type = 'password'
        }
        this.showPassword = !this.showPassword
        this.$refs.input.focus()
      }
    },
    watch:{
      value:function (newVal) {
        this.showClear = !!newVal
      },
      inputType:function (newVal) {
        console.log(newVal)
        this.type = newVal
      }
    }
  }
</script>

<style type='stylesheet/scss' lang='scss' scoped>
  .mw-input{
    height: 50px;position: relative;background-color: white;border-radius: 5px;
    input{
     height: 100%;margin: 0 auto;background-color: transparent;width: 100%;padding-left: 8px;
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

</style>

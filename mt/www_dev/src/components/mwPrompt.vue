<template>
  <section style="width: 100%;height: 100%;top: 0;position: absolute" v-show="show">
    <div class="mask" v-show="show" @click="hidePop"></div>
    <div class="pop" v-show="show">
      <i class="icon ion-close-circled close" @click.stop="hidePop"></i>
      <div class="prompt-title">{{title}}</div>
      <div class="prompt-input" v-if="promptType === 'input'">
        <mw-input style="width: 90%;margin: 0 auto" :placeholder="placeholder" v-model="v"></mw-input>
      </div>
      <div class="prompt-textarea" v-if="promptType === 'textarea'">
        <textarea :placeholder="placeholder"  v-model="v">

        </textarea>
      </div>
      <div class="prompt-footer">
        <div class="btn" @click="cancel">{{cancelText}}</div>
        <div class="btn" @click="ok">{{okText}}</div>
      </div>
    </div>
  </section>
</template>

<script>
  import MwInput from "./mw-input";
  export default{
    components: {MwInput},
    name: 'mwPrompt',
    props:{
      show:{
        type:Boolean,
        required:true,
        default:false
      },
      title:{
        type:String,
        default:'提示',
      },
      promptType:{
          //弹出类型：input or textarea
        type:String,
        default:'input',
      },
      okText:{
        type:String,
        default:'确认'
      },
      cancelText:{
        type:String,
        default:'取消'
      },
      placeholder:{
        type:String,
        default:'请输入',
      },
      okFunction:{
        type:Function,
        required:true
      },
      cancelFunction:{
        type:Function
      },
      value:{
        type:String,
        required:true
      }
    },
    data(){
      return {
        v:this.value
      }
    },
    activated(){

    },
    methods: {
      hidePop(){
        this.$emit('hidePop')
      },
      ok(){
        if (this.okFunction) {
          this.okFunction('ok')
        }
        // this.hidePop()
      },
      cancel(){
        if (this.cancelFunction) {
          this.cancelFunction('cancel')
        }
        this.hidePop()
      },
    },
    watch:{
      v:function () {
        this.$emit('input',this.v)

      },
      show:function () {
        if(this.show){
          this.v=''
        }
      }
    }
  }
</script>

<style type='stylesheet/scss' lang='scss' scoped>
  .hidden{
    overflow-y: hidden;
  }
  .mask{
    width: 100%;height: 100%;position:absolute;left: 0;right: 0;background-color: #282828;opacity:0.4;z-index: 11
  }
  .pop{
    position:absolute;top: 35%;width: 80%;min-height: 20%;z-index: 15;left: 0;
    right: 0;margin: auto;background-color: white;
    display: flex;
    flex-direction: column;
    border-radius: 8px;

    animation:promptPop .3s;
    /*animation-iteration-count:2;*/
    animation-fill-mode:forwards;

    /* Safari 和 Chrome */
    -webkit-animation:promptPop .3s;
    /*-webkit-animation-iteration-count:2;*/
    -webkit-animation-fill-mode:forwards;

    @keyframes promptPop
    {
      from {opacity: 0}
      to {opacity: 1}
    }

    @-webkit-keyframes promptPop /* Safari 和 Chrome */
    {
      from {opacity: 0}
      to {opacity: 1}
    }

    .close{
      position: absolute;
      right: 3px;
      top: 0px;
      color: #ababab;
      font-size: 16px;
    }
    .prompt-title{
      text-align: center;height: 40px;line-height: 40px;
    }
    .prompt-input{
      flex: 1;
      input{
        width: 100%;
        height: 100%;
        text-align: center;
      }
    }
    textarea{
      width: 90%;
      margin: 0 auto;
      height: 80px;
      resize: none;
    }
    .prompt-footer{
      width: 100%;
      height: 40px;
      display: flex;
      justify-content: center;
      align-items: center;
      .btn{
        flex: 1;
        text-align: center;
      }
    }
  }
</style>

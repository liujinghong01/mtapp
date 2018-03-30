<template>
    <section>
      <div class="mask" v-show="showFilter" @click="$emit('hideMask')"></div>
      <section style="display: flex;flex-direction: column" :class="['filter',showFilter?'active':'']">
        <div class="side-title" v-show="showFilter">
           {{title}}
        </div>
        <mw-search placeholder="输入物料名称" value="" search="" cancelText="" cancelAction=""></mw-search>
        <div class="side-footer"  v-show="showFilter">
          <div class="filter-btns">
            <div class="filter-btn" @click="ok">
              确定
            </div>
          </div>
        </div>
      </section>
    </section>
</template>
<script>
  import MwRadio from "./mwRadio"
  import MwSearch from "./mw-search"
    export default{
      components: {
        MwSearch,
        MwRadio},
      name:'sidebarSearch',
        data(){
          return {
            list:[            //查询过来的原始列表

            ],
            filterList:[],    //过滤之后的列表
            chosenItem:{},
            title:'',
            filterStr:''
          }
        },
      computed:{

      },
      props:{
        showFilter:{
          type:Boolean,
          default:false,
          required:true
        },
        dicType:{
          type:String,
          required:true
        },
        chosen:{
          type:Function,
          required:true
        },
        query:{
          type:Object,
        }
      },
      methods: {
        itemClick(item){
          if(item===this.chosenItem){
            return
          }
          this.chosenItem.chosen = false
          this.chosenItem = item
        },
        ok(){
          if(this.chosen) this.chosen(this.chosenItem)
          this.$emit('hideMask')
        },
        getList(){
          console.log('getList')
          let reqData = {

          }
        }
      },
      watch:{
        'showFilter':function (newVal,oldVal) {
          if(newVal){
            this.filterStr = ''
            this.getList()
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
  .filter{
    width: calc(100% - 80px);
    height: 100%;
    background-color: #F3F6FB;
    position: absolute;
    top:0;
    right: calc(80px - 100%);
    z-index: 17;
    transition:transform .2s;
    -moz-transition:transform .2s; /* Firefox 4 */
    -webkit-transition:transform .2s; /* Safari and Chrome */
    -o-transition:transform .2s; /* Opera */
  }
  .filter.active{
    transform: translateX(calc(80px - 100%));
  }
  .side-title{
    height: 40px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .side-content{
    flex: 1;
    overflow-x: hidden;
    overflow-y: auto;
  }
  .side-footer{
    height: 50px;
  }
  .filter-btns{
    display: flex;
    height: 100%;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);
    .filter-btn{
      flex: 1 0 auto;
      text-align: center;
      display: flex;
      justify-content: center;
      align-items: center;
    }
  }
</style>

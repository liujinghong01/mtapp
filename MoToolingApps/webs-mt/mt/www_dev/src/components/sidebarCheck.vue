<template>
    <section>
      <div class="mask" v-show="showFilter" @click="$emit('hideMask')"></div>
      <section style="display: flex;flex-direction: column" :class="['filter',showFilter?'active':'']">
        <div class="side-title" v-show="showFilter">
           {{title}}
        </div>
        <input type="search" style="background-color: #bbbbbb;width: 90%;margin: 0 auto;border-radius: 5px" v-model="filterStr" @input="filter">

        <div class="side-content" v-if="dicType==='processItem'">
          <div v-for="item in filterList">
            <mw-radio :obj="item" :hasArrow="false" :iClick="itemClick">{{item.process_name}}  <span style="float:right">{{item.process_ratio+'/'+item.process_unit_name}}</span></mw-radio>
          </div>
        </div>
        <div class="side-content" v-if="dicType==='material'">
          <div v-for="item in filterList">
            <mw-radio :obj="item" :hasArrow="false" :iClick="itemClick">{{item.raw_nature}}</mw-radio>
          </div>
        </div>
        <div class="side-content" v-if="dicType==='customer'">
          <div v-for="item in filterList">
            <mw-radio :obj="item" :hasArrow="false" :iClick="itemClick">{{item.cst_name+'/'+item.connect_man}}</mw-radio>
          </div>
        </div>
        <div class="side-content" v-if="dicType==='salesman'">
          <div v-for="item in filterList">
            <mw-radio :obj="item" :hasArrow="false" :iClick="itemClick">{{item.work_name}}</mw-radio>
          </div>
        </div>
        <div class="side-content" v-if="dicType==='part'">
          <div v-for="item in filterList">
            <mw-radio :obj="item" :hasArrow="false" :iClick="itemClick">{{item.part_name}}</mw-radio>
          </div>
        </div>
        <div class="side-content" v-if="dicType==='sub_part'">
          <div v-for="item in filterList">
            <mw-radio :obj="item" :hasArrow="false" :iClick="itemClick">{{item.sub_part_name}}</mw-radio>
          </div>
        </div>
        <div class="side-content" v-if="dicType==='waterType'">
          <div v-for="item in filterList">
            <mw-radio :obj="item" :hasArrow="false" :iClick="itemClick">{{item.name}}</mw-radio>
          </div>
        </div>
        <div class="side-content" v-if="dicType==='prodType'">
          <div v-for="item in filterList">
            <mw-radio :obj="item" :hasArrow="false" :iClick="itemClick">{{item.type_name}}</mw-radio>
          </div>
        </div>
        <div class="side-content" v-if="dicType==='runnerType'">
          <div v-for="item in filterList">
            <mw-radio :obj="item" :hasArrow="false" :iClick="itemClick">{{item.name}}</mw-radio>
          </div>
        </div>
        <div class="side-content" v-if="dicType==='coin'">
          <div v-for="item in filterList">
            <mw-radio :obj="item" :hasArrow="false" :iClick="itemClick">{{item.name}}</mw-radio>
          </div>
        </div>
        <div class="side-content" v-if="dicType==='outerWay'">
          <div v-for="item in filterList">
            <mw-radio :obj="item" :hasArrow="false" :iClick="itemClick">{{item.name}}</mw-radio>
          </div>
        </div>
        <div class="side-content" v-if="dicType==='supplier'">
          <div v-for="item in filterList">
            <mw-radio :obj="item" :hasArrow="false" :iClick="itemClick">{{item.sup_name+'/'+item.connect_man}}</mw-radio>
          </div>
        </div>
        <div class="side-content" v-if="dicType==='invoiceType'">
          <div v-for="item in filterList">
            <mw-radio :obj="item" :hasArrow="false" :iClick="itemClick">{{item.name}}</mw-radio>
          </div>
        </div>
        <div class="side-content" v-if="dicType==='storehouse'">
          <div v-for="item in filterList">
            <mw-radio :obj="item" :hasArrow="false" :iClick="itemClick">{{item.store_house_name}}</mw-radio>
          </div>
        </div>
        <div class="side-content" v-if="dicType==='depMember'">
          <div v-for="item in filterList">
            <mw-radio :obj="item" :hasArrow="false" :iClick="itemClick">{{item.work_name}}</mw-radio>
          </div>
        </div>
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
  import MwRadio from "./mwRadio";
  import{salesmanList,rawNatureList,processCostItemList,customerList,defaultSubPartList,
    defaultPartList,coinTypeList,outWayList,waterTypeList,runnerTypeList,cstCompanyList,
    invoiceType,querySup,getAllDeptMember
  } from '@/api/dic'
  import {storageList} from '@/api/storage/baseInfo'

    export default{
      components: {MwRadio},
      name:'sidebarCheck',
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
          switch (this.dicType){
            case 'processItem':{
              this.title = '请选择加工项目'
              processCostItemList(reqData).then(res=>{
                this.list = this.filterList = res.process_item_list
              }).catch(error=>{
                console.log(error.description)
              })
              break
            }
            case 'customer':{
              this.title = '请选择客户'
              cstCompanyList(reqData).then(res=>{
                this.list = this.filterList = res.cst_list
              }).catch(error=>{
                console.log(error.description)
              })
              break
            }
            case 'material':{
              this.title = '请选择材料'
              rawNatureList(reqData).then(res=>{
                this.list = this.filterList = res.raw_nature_list
              }).catch(error=>{
                console.log(error.description)
              })
              break
            }
            case 'part':{
              this.title = '请选择零部件'
              defaultSubPartList(reqData).then(res=>{
                this.list = this.filterList = res.default_part_list
              }).catch(error=>{
                console.log(error.description)
              })
              break
            }
            case 'sub_part':{
              this.title = '请选择子部件'
              break
            }
            case 'prodType':{
              this.title = '请选择产品类型'
              this.list = this.filterList = [{type:1,type_name:'注塑模'},{type:2,type_name:'压铸模'}/*,{type:3,type_name:'冲压模'}*/]
              break
            }
            case 'salesman':{
              this.title = '请选择业务员'
              salesmanList(reqData).then(res=>{
                this.list = this.filterList = res.list
              }).catch(error=>{
                console.log(error.description)
              })
              break
            }
            case 'waterType':{
              this.title = '请选择水口形式'
              waterTypeList(reqData).then(res=>{
                this.list = this.filterList = res.list
              }).catch(error=>{
                console.log(error.description)
              })
              break
            }
            case 'runnerType':{
              this.title = '请选择流道方式'
              runnerTypeList(reqData).then(res=>{
                this.list = this.filterList = res.list
              }).catch(error=>{
                console.log(error.description)
              })
              break
            }
            case 'coin':{
              this.title = '请选择货币类型'
              coinTypeList(reqData).then(res=>{
                this.list = this.filterList = res.list
              }).catch(error=>{
                console.log(error.description)
              })
              break
            }
            case 'outerWay':{
              this.title = '请选择顶出方式'
              outWayList(reqData).then(res=>{
                this.list = this.filterList = res.list
              }).catch(error=>{
                console.log(error.description)
              })
              break
            }
            case 'invoiceType':{
              this.title = '请选择发票类型'
              invoiceType(reqData).then(res=>{
                this.list = this.filterList = res.list
              }).catch(error=>{
                console.log(error.description)
              })
              break
            }
            case 'supplier':{
              this.title = '请选择供应商'
              reqData = {
                query:{
                  sup_name:''
                }
              }
              querySup(reqData).then(res=>{
                this.list = this.filterList = res.sup_list
              }).catch(error=>{
                console.log(error.description)
              })
              break
            }
            case 'storehouse':{
              this.title = '请选择仓库'
              reqData = {
                  is_paging:0
              }
              storageList(reqData).then(res=>{
                this.list = this.filterList = res.storehouse_list
              }).catch(error=>{
                console.log(error.description)
              })
              break
            }
            case 'depMember':{
              this.title = '请选择人员'
              reqData = {
                query:{
                  dep_code:'Storehouse_Dept'
                }
              }
              getAllDeptMember(reqData).then(res=>{
                this.list = this.filterList = res.member_list
              }).catch(error=>{
                console.log(error.description)
              })
              break
            }

            default: {
              this.title = '请选择加工项目'
            }
          }

        },
        filter(str){
          const that = this
          setTimeout(function () {
            switch (that.dicType) {
              case 'processItem': {
                that.filterList = that.list.filter(i=>{
                  return i.process_name.indexOf(that.filterStr.trim())>-1
                })
                break
              }
              case 'customer': {
                that.filterList = that.list.filter(i=>{
                  return i.cst_name.indexOf(that.filterStr.trim())>-1
                })
                break
              }
              case 'material': {
                that.filterList = that.list.filter(i=>{
                  return i.raw_nature.indexOf(that.filterStr.trim())>-1
                })
                break
              }
              case 'salesman': {
                that.filterList = that.list.filter(i=>{
                  return i.username.indexOf(that.filterStr.trim())>-1
                })
                break
              }
              case 'part': {
                that.filterList = that.list.filter(i=>{
                  return i.part_name.indexOf(that.filterStr.trim())>-1
                })
                break
              }
              case 'sub_part': {
                that.filterList = that.list.filter(i=>{
                  return i.sub_part_name.indexOf(that.filterStr.trim())>-1
                })
                break
              }
              case 'prodType': {
                that.filterList = that.list.filter(i=>{
                  return i.type_name.indexOf(that.filterStr.trim())>-1
                })
                break
              }
              case 'supplier': {
                that.filterList = that.list.filter(i=>{
                  return i.sup_name.indexOf(that.filterStr.trim())>-1
                })
                break
              }
              case 'storehouse': {
                that.filterList = that.list.filter(i=>{
                  return i.store_house_name.indexOf(that.filterStr.trim())>-1
                })
                break
              }
              case 'depMember': {
                that.filterList = that.list.filter(i=>{
                  return i.member_name.indexOf(that.filterStr.trim())>-1
                })
                break
              }
              default :{
                that.filterList = that.list.filter(i=>{
                  return i.name.indexOf(that.filterStr.trim())>-1
                })
                break
              }
            }
          },800)
        }
      },
      watch:{
        'showFilter':function (newVal,oldVal) {
          if(newVal){
            this.filterStr = ''
            this.list.length = 0
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
    width: 0;
    height: 0;
    transition:width .2s;
    -moz-transition:width .2s; /* Firefox 4 */
    -webkit-transition:width .2s; /* Safari and Chrome */
    -o-transition:width .2s; /* Opera */
  }
  .filter.active{
    width: 80%;
    height: 100%;
    background-color: #F3F6FB;
    position: absolute;
    top:0;
    right: 0;
    z-index: 17;

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

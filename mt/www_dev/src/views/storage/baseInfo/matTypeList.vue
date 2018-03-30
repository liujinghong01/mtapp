<template>
    <div class="mw-page page storage" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>物料类型列表</span>
            <i class="right" @click="addShow()">新增</i>
        </div>
        <div class="mw-page-content">
          <div>
            <swipe-item  :obj="item" swipeItemText="删除"
                       v-on:SwipeItemClick="itemDel(item)" v-for="item in mat_type_list">
              <span>{{item.mat_type_name}}</span>
            </swipe-item>
          </div>
        </div>
      <!--<mw-prompt :show="addType" :okFunction="add" v-model="newType" v-on:hidePop="addType = false" placeholder="请输入物料类型名称"></mw-prompt>-->
      <div v-if="addType" class="newBox" @click='hide()'>
          <div @click="prevent()">
            <i class="ion-ios-close" @click='hide()'></i>
            <p>
              <span :class="mat_type_info.type_name.trim()===''?'isMust':''">类型名称:</span>
              <input type="text" v-model="mat_type_info.type_name" placeholder="请输入物料类型名称">
            </p>
            <p>
              <span >是否质检:</span>
              <label><input name="isQc" type="radio" value="1" v-model="mat_type_info.is_qc">是</label>
              <label><input name="isQc" type="radio" value="0" v-model="mat_type_info.is_qc">否</label>
            </p>
            <p>仓库选择:<span class="select" @click="selsect('store')">{{mat_type_info.store_house_name}} <i class="ion-ios-arrow-right"></i></span></p>
            <p>库位选择:<span class="select" @click="selsect('house')">{{ mat_type_info.bin_name }} <i class="ion-ios-arrow-right"></i></span></p>
            <button  @click="add()" style="background: #416eb6;color: #fff;margin-top: 10px" class="button  button-block ">添加物料类型</button>

          </div>

      </div>
      <time-picker
        v-on:chosen="chosen"
        :showPicker="showPicker"
        :pickerType="pickerType"
        :pickerValue="pickerValue"
        :pickerList='pickerList'
        v-on:hideMask="showPicker=false"></time-picker>
    </div>
</template>

<script>
  import { storageMats,storageMatModify,storageMatDel,storList, houseList } from '@/api/storage/baseInfo'
  import SwipeItem from "../../../components/swipeItem";
  import timePicker from "@/components/timePicker"
  import MwInput from "../../../components/mw-input";
  import MwPrompt from "../../../components/mwPrompt";
    export default{
      name:'matTypes',
      components: {
        MwPrompt,
        MwInput,
        SwipeItem,
        timePicker
      },
      data(){
        return {
          selectType:'',
          showPicker:false,
          pickerType:'radio',
          pickerValue:'',
          pickerList:[],
          storList:[],
          houseList:[],
          mat_type_list:[],
          action:'check',
          addType:false,
          newType:'',
          mat_type_info: {//
            bin_name:'请选择',//	物料类型的缺省库位名称
            bin_no: '',//	物料类型的缺省库位	string	注意库位要与仓库匹配
            is_qc: '0',//	是否需要质检	string
            mat_no_prefix: '',//	物料前缀	string	@mock=测试内容x246
            mat_type: '',//	物料类型编码	string
            mat_type_id: '',//	物料类型Id	number	为空新增，否则修改
            procure_style: '',//	采购方式	number	1.直接采购 2.下料 3.库存供应
            store_house_id: '',//默认仓库Id	number
            store_house_name: '请选择',//	默认仓库名称	string
            type_name: '',//	物料类型名称	string	@mock=测试内容5yf8
            unit: '',//
          }
        }
      },
      activated(){
        this.getData()
      },
      methods: {
        chosen(e){
          if(this.selectType==='store'){
            this.mat_type_info.store_house_name=e.val
            this.mat_type_info.store_house_id=e.id
            this.gerHouseList()
          }else if(this.selectType==='house'){
            this.mat_type_info.bin_name=e.val
            this.mat_type_info.bin_no=e.id
          }
        },
        selsect(type){
          this.selectType=type
          if(type==='store'){
            if(this.storList.length>0){
              this.pickerList.length=0
              this.pickerValue={
                val:this.mat_type_info.store_house_name,
                id:this.mat_type_info.store_house_id
              }
              this.storList.forEach((e,i)=>{
                this.pickerList.push({
                  val:e.store_house_name,
                  id:e.store_house_id
                })
              })
              this.showPicker=true
            }else{
              this.getStorList()
            }

          }else if(type==='house'){
            if(this.mat_type_info.store_house_name!=='请选择'){
              if(this.houseList.length>0){
                this.pickerList.length=0
                this.pickerValue={
                  val:this.mat_type_info.bin_name,
                  id:this.mat_type_info.bin_no
                }
                this.houseList.forEach((e,i)=>{
                  this.pickerList.push({
                    val:e.store_room_name,
                    id:e.store_room_id
                  })
                })
                this.showPicker=true
              }else{
                this.gerHouseList()
              }

            }else{
              $toast.show('请先选择仓库',700)
            }
          }

        },
        getData(){
          const reqData = {
            is_paging:0
          }
          storageMats(reqData).then(res=>{
            this.mat_type_list = res.mat_type_list
          }).catch(error=>{
            $toast.show(error.description)
          })
        },
        getStorList(){
          const reqData = {
            is_paging:0
          }
          storList(reqData).then(res=>{
            this.storList.length=0
            if(res.storehouse_list.length>0){
              this.storList=res.storehouse_list
            }else{
              $toast.show('仓库数据不可用',600)
            }
          }).catch(error=>{
            $toast.show(error.description)
          })
        },
        gerHouseList(){
          const reqData = {
            query:{
              store_house_id:this.mat_type_info.store_house_id
            }
          }
          houseList(reqData).then(res=>{
            this.houseList.length=0
            if(res.store_house_info.store_room_list.length>0){
              this.houseList=res.store_house_info.store_room_list
            }else{
              $toast.show('该仓库下的库位数据不可用',600)
            }
          }).catch(error=>{
            $toast.show(error.description)
          })

        },
      //查看详情
        itemClick(item) {
          this.$router.forward('/storage/matTypeDetail/'+this.action+'/'+JSON.stringify(item))
        },
        addShow(){

          this.newType=''
          this.addType = true
          this.getStorList()
        },
        add(){
          if (!this.mat_type_info.type_name||!this.mat_type_info.type_name.trim()){
            $toast.show('类型名称不能为空')
            return
          }
          let isIndexOf=0
          for (let i=0;i<this.mat_type_list.length;i++){
            let e=this.mat_type_list[i]
            if(e.mat_type_name===this.mat_type_info.type_name){
              isIndexOf=i
              break
            }
          }
          if(isIndexOf!==0){
            $toast.show('新增类型名称重复')
            return
          }
          storageMatModify({mat_type_info:this.mat_type_info}).then(res=>{
            $toast.show('保存成功')
            this.addType = false
            this.newType = ''
            this.getData()
          }).catch(error=>{
            $toast.show(error.description)
          })
        },
        //删除仓库
        itemDel(item,index) {
          if (item.is_default=='1') {
            $toast.show('缺省物料类型无法删除')
            return
          }
          $dialog.confirm({
            theme:'ios',
            title:'提示',
            content:'确定要删除吗?',
            okText:'确定',
            cancelText:'取消'
          }).then(ok=>{
            if(ok) {
              storageMatDel({
                mat_type_id:item.mat_type_id
              }).then(res=>{
                this.mat_type_list = this.mat_type_list.filter(o=>{
                  return o.mat_type_id != item.mat_type_id
                })
              }).catch(error=>{
                $toast.show(error.description)
              })
            }
          }).catch(error=>{
            $toast.show(error.description)
          })
        },

        prevent(){
          event.cancelBubble = true
        },
        hide(){
          this.addType=false
        },
      }
    }
</script>

<style lang="scss" scoped>
  .newBox{
    position: absolute;
    width: 100%;
    height: 100%;
    top: 0px;
    left: 0;
    z-index: 10;
    background: rgba(0,0,0,.1);
    &>div{
      width: 240px;
      height: 254px;
      position: absolute;
      top: 50%;
      left: 50%;
      margin-top: -127px;
      margin-left: -120px;
      background: #fff;
      padding: 20px 15px;
      box-sizing: border-box;
      border-radius: 10px;
      &>.ion-ios-close{
        position: absolute;
        right: -10px;
        top: -10px;
        color: #9e9e9e;
        font-size: 24px;
      }
      &>p{
        display: block;
        height: 40px;
        line-height: 40px;
        border-bottom: 1px solid #ddd;
        margin: 0;
        box-sizing: border-box;
        &:last-of-type{
          border: none;
        }
        &>input[type="text"]{
          float: right;
          width: 125px;
          height: 37px;
        }
        &>.select{
          width: 125px;
          float: right;
          height: 39px;
          line-height: 39px;
          box-sizing: border-box;
          padding-right: 10px;
          /*text-align: right;*/
          .ion-ios-arrow-right{
              float: right;
              font-size: 18px;
              color: #ddd;
          }
        }
      }
    }
  }

</style>

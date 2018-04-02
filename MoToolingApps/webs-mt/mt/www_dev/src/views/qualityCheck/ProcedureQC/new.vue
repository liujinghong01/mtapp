<template>
    <div class="mw-page page " >
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>新增工序质检单</span>
            <i class="right"  >扫码</i>
        </div>
        <div class="mw-page-content"  >
          <div class="header">
            <cst-item @click.native="selectSup" label="工序：" v-model="proc_name"  editType="text"></cst-item>
            <cst-item   label="物料编码：" v-model="mat_no " placeholder="请输入采购单号" :action="action" editType="text"></cst-item>
            <cst-item   label="生产定单：" v-model="po_no " placeholder="请输入采购单号" :action="action" editType="text"></cst-item>
            <cst-item   label="确认号：" v-model="confirm_no " placeholder="请输入采购单号" :action="action" editType="text"></cst-item>
          </div>
          <div class="singleList" >
            <item v-for="(item,index) in prod_order_list" style="padding-left: 30px" class="single" @click.native="onItemClick(item)">
              <span >{{ item.purch_no }}</span>
              <p>物料编码：{{ item.mat_no }}</p>
              <p>工序：{{ item.proc_name }}</p>
              <p>工序状态：{{ item.pop_status }}</p>
              <p>待质检数:{{ item.pc_wait_qty }}</p>
              <i :class="item.qcStart?'ion-android-checkbox ':'ion-android-checkbox-outline-blank'"></i>
            </item>
          </div>
        </div>
      <div class="mw-page-footer" >
        <div class="btns">
          <div class="btn" @click="establish">生成质检单</div>
        </div>
      </div>


      <time-picker v-on:chosen="chosen"
                   :showPicker="showPicker"
                   :pickerType="pickerType"
                   :pickerValue="pickerValue"
                   :pickerList='pickerList'
                   v-on:hideMask="showPicker=false">
      </time-picker>
    </div>
</template>

<script>
  import CstItem from "../../../components/cstItem"
  import timePicker from "../../../components/timePicker"
  import { procedure_new_list,procList } from '@/api/MaterialQC'
  import sess from '@/utils/sess'
    export default{
      name:'newProcedureQC',
      components: {CstItem,timePicker},
      data(){
        return {
          confirm_no:'',//	确认号	string
          mat_no:'',//	物料编码	string
          po_no:'',//	生产订单编号	string
          proc_name:'请选择',//
          proc_id:'',
          action:'',
          showPicker:false,
          pickerType:'radio',
          pickerValue:'',
          pickerList:[],
          timer:'',
          prod_order_list:[]
        }
      },
      computed:{
        // name:function(){},
      },
      activated(){ },
      mounted(){
        this.getProcList()
        let param=this.$route.query

      },
      methods: {
        onItemClick(item){
          if(item.qcStart){
            this.$delete(item,'qcStart')
          }else{
            this.$set(item,'qcStart',true)
          }
        },
        establish(){
          let prod_order_list=[]
          this.prod_order_list.forEach((e,i)=>{
            if(e.qcStart){
              prod_order_list.push(e)
            }

          })
          sess.set('prod_order_list',JSON.stringify(prod_order_list))
          this.$router.forward({
            path:'ProcedureQCdetaile',
            query:{
              action:'new',
            }
          })
        },
        selectSup(){
          if(this.pickerList.length>0){
            this.pickerValue={
              val:this.proc_name,
              id:this.proc_id
            }
            this.showPicker=true

          }else{
            $toast.show('您的公司暂时没有工序可供选择',500)
          }

        },
        chosen(e){
          this.proc_name=e.val
          this.proc_id=e.id
          this.getList()
        },
        getList(){
          let reqData = {
            query:{
              confirm_no:this.confirm_no,
              mat_no:this.mat_no,
              po_no:this.po_no,
              proc_name:this.proc_name
            }

          }
          if(reqData.query.proc_name==='请选择'){
            $toast.show('请先选择工序',600)
            return
          }
          procedure_new_list(reqData).then(res=>{
            if(res.prod_qc_info.prod_order_info.length>0){
              this.prod_order_list=res.prod_qc_info.prod_order_info
            }else{
              this.prod_order_list=[]
            }

          }).catch(error=>{
            $toast.show(error.description)
          })
        },
        getProcList(refresh){
          const reqData = {
            query:{
              proc_name:'',
              proc_no:'',
              status:'1'
            }
          }
          procList(reqData).then(res=>{
            console.log(res)
            if(res.proc_info.length>0){
              res.proc_info.forEach((e,i)=>{
                this.pickerList.push({
                  id:e.id,
                  val:e.proc_name
                })
              })
            }
          }).catch(error=>{
            $toast.show(error.description)
          })
        },
      },
      watch:{
        purch_no:function(e,v){
          clearTimeout(this.timer)
          this.timer=setTimeout(()=>{
            this.getList()
          },500)
        },
        mat_no:function(e,v){
          clearTimeout(this.timer)
          this.timer=setTimeout(()=>{
            this.getList()
          },500)
        },
        po_no:function(e,v){
          clearTimeout(this.timer)
          this.timer=setTimeout(()=>{
            this.getList()
          },500)
        }
      }



    }
</script>

<style lang="scss" scoped>

.mw-page-content{
  position: relative;
  &>.header{
    width: 100%;
    position: absolute;
    top:0;
    left: 0;
    height: 204px;
    z-index: 10;
    background: #f5f5f5;
  }
  &>.singleList{
    width: 100%;
    height: 100%;
    overflow-y: scroll;
    -webkit-overflow-scrolling: touch;
    padding-top: 204px;
    box-sizing: border-box;
    position: absolute;
    top: 0;
    z-index: 0;
    &::-webkit-scrollbar{
      width: 0;height: 0;
    }
    .single{
      margin: 0;
      &>span{
        color: #416eb6;
      }
      &>p{
        margin-top: 5px;
      }
      i{
        position: absolute;
        width: 30px;
        height: 80px;
        left: 0;
        top: 50%;
        margin-top: -40px;
        line-height: 80px;
        text-align: center;
        font-size: 20px;
        &.ion-android-checkbox{
          color:#416eb6;
        }
      }
    }

  }
}
</style>

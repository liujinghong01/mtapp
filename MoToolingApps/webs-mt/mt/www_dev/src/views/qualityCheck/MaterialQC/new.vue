<template>
    <div class="mw-page page " >
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>新增来料质检单</span>
            <i class="right"  >扫码</i>
        </div>
        <div class="mw-page-content"  >
          <div class="header">
            <cst-item   label="采购单号：" v-model="purch_no " placeholder="请输入采购单号" :action="action" editType="text"></cst-item>
            <cst-item @click.native="selectSup" label="供应商：" v-model="sup_name" placeholder="请选择供应商"  editType="text"></cst-item>
            <item style="margin-bottom: 0" >
              <span>采购类型：</span>
              <label><input :disabled="action=='check'" name="purch_type"   v-model="purch_type" type="radio" value="1" /> 物料</label>
              <label><input :disabled="action=='check'" name="purch_type"   v-model="purch_type" type="radio" value="2" /> 外协</label>
            </item>

          </div>
          <div class="singleList" >
            <item v-for="(item,index) in purch_detail_list" class="single" @click.native="onItemClick(item)">
              <span >{{ item.purch_no }}</span>
              <p>供应商：{{ item.sup_name }}</p>
              <i class="ion-ios-arrow-right"></i>
            </item>
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
  import { purch_qc_stay,supplierList } from '@/api/MaterialQC'
  import sess from '@/utils/sess'
    export default{
      name:'newMaterialQC',
      components: {CstItem,timePicker},
      data(){
        return {
          purch_no:'',
          sup_name:'请选择',
          purch_type:1,
          action:'',
          showPicker:false,
          pickerType:'radio',
          pickerValue:'',
          pickerList:[],
          timer:'',
          purch_detail_list:[]
        }
      },
      computed:{
        // name:function(){},
      },
      activated(){ },
      mounted(){
        this.getSupplierList()
        let param=this.$route.query

      },
      methods: {
        onItemClick(item){
          sess.set('QC_info',JSON.stringify(item))
          this.$router.forward({
            path:'qcDetaile',
            query:{
              action:'new',
            }
          })
        },
        selectSup(){
          if(this.pickerList.length>0){
            this.showPicker=true
          }else{
            $toast.show('您的公司暂时没有供应商可供选择',500)
          }

        },
        chosen(e){
          this.sup_name=e.val
          this.getList()
        },
        getList(){
          let reqData = {
            query:{
              purch_no:this.purch_no,
              sup_name:this.sup_name,
              purch_type:this.purch_type
            }

          }
          if(reqData.query.sup_name==='请选择'){
            reqData.query.sup_name=''
          }
          purch_qc_stay(reqData).then(res=>{
            if(res.purch_list.length>0){
              this.purch_detail_list=res.purch_list
            }else{
              this.purch_detail_list=[]
            }
          }).catch(error=>{
            $toast.show(error.description)
          })
        },
        getSupplierList(refresh){
          const reqData = {
            query:{
              sup_name:''
            }
          }
          supplierList(reqData).then(res=>{
            if(res.sup_list.length>0){
              res.sup_list.forEach((e,i)=>{
                this.pickerList.push({
                  id:e.sup_company_id,
                  val:e.sup_name
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
    height: 154px;
    z-index: 10;
    background: #f5f5f5;
  }
  &>.singleList{
    width: 100%;
    height: 100%;
    overflow-y: scroll;
    -webkit-overflow-scrolling: touch;
    padding-top: 154px;
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
        width: 50px;
        height: 50px;
        right: 0;
        top: 50%;
        margin-top: -25px;
        line-height: 50px;
        text-align: center;
        font-size: 20px;
      }
    }

  }
}
</style>

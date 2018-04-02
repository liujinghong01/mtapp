<template>
    <div class="mw-page page storage" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>物料列表</span>
            <i class="right" @click="$router.forward({path:'/storage/newMat',query:{mat_id:mat_id,action:'edit'}})">编辑</i>
        </div>
        <div  class="mw-page-content has-tab">
          <div style="height:60px">
            <tabs :tab-items="tabs" :tab-index="tabIndex" :on-tab-click="onTabClick"></tabs>
          </div>
          <div class="page-content" v-show="tabIndex===0" style="flex: 1;position: relative;">
            <cst-item label="物料类型" v-model="mate_info.mat_type_name " action="check"   ></cst-item>
            <cst-item label="物料名称" v-model="mate_info.mat_name " action="check"  :hasArrow="false"  > </cst-item>
            <cst-item label="物料编号" v-model="mate_info.mat_no " action="check" :hasArrow="false"  ></cst-item>
            <cst-item label="规格型号" v-model="mate_info.spec " action="check"  :hasArrow="false"  ></cst-item>
            <cst-item label="计量单位" v-model="mate_info.unit_no " action="check"    > </cst-item>
            <item style="margin: 0">
              <span>形状：</span>
              <label><input disabled name="shape" type="radio" value="1" v-model="mate_info.shape"/> 立方体</label>
              <label><input disabled name="shape" type="radio" value="2" v-model="mate_info.shape"/> 圆柱体</label>
              <label><input disabled name="shape" type="radio" value="3" v-model="mate_info.shape"/> 管状体</label>
            </item>
            <cst-item class="noneBorder" v-show="mate_info.shape==='1'"  label="长(mm)" :placeholder="'0'" v-model="mate_info.length" action="check" editType="number"></cst-item>
            <cst-item class="noneBorder" v-show="mate_info.shape==='1'" label="宽(mm)" :placeholder="'0'" v-model="mate_info.width" action="check" editType="number"></cst-item>
            <cst-item class="noneBorder" v-show="mate_info.shape==='1'" label="高(mm)" :placeholder="'0'" v-model="mate_info.height" action="check" editType="number"></cst-item>
            <cst-item class="noneBorder" v-show="mate_info.shape==='2'" label="直径(mm)" :placeholder="'0'" v-model="mate_info.length" action="check" editType="number"></cst-item>
            <cst-item class="noneBorder" v-show="mate_info.shape==='2'" label="高(mm)" :placeholder="'0'" v-model="mate_info.height" action="check" editType="number"></cst-item>
            <cst-item class="noneBorder" v-show="mate_info.shape==='3'"  label="外圆直径(mm)" :placeholder="'0'" v-model="mate_info.length" action="check" editType="number"></cst-item>
            <cst-item class="noneBorder" v-show="mate_info.shape==='3'" label="内圆直径(mm)" :placeholder="'0'" v-model="mate_info.width" action="check" editType="number"></cst-item>
            <cst-item class="noneBorder" v-show="mate_info.shape==='3'" label="高(mm)" :placeholder="'0'" v-model="mate_info.height" action="check" editType="number"></cst-item>

            <cst-item label="材质" v-model="mate_info.rawnature_name " action="check" :canEdit="false"  ></cst-item>
            <cst-item   label="重量" v-model="mate_info.weight"  action="check" ></cst-item>
            <cst-item   label="物料单价" v-model="mate_info.plan_price" action="check" ></cst-item>
            <item >
              <span>入库是否质检：</span>
              <label><input name="isQc" disabled type="radio" value="1" v-model="mate_info.is_qc"/> 是</label>
              <label><input name="isQc" disabled type="radio" value="0"  v-model="mate_info.is_qc"/> 否</label>
            </item>
            <item  >
              备注 :
              <textarea disabled :placeholder="'该物料没有备注信息'"  class="remark" type="text" v-model="mate_info.mat_desc"></textarea>
            </item>
          </div>
          <div class="page-content" v-show="tabIndex===1" style="flex: 1;position: relative;">
           <p>库存</p>
          </div>
          <div class="page-content" v-show="tabIndex===2">
            出入库
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
  import CstItem from "@/components/cstItem";
  import { getMatDetaile } from '@/api/storage/baseInfo'
  import timePicker from "@/components/timePicker"
    export default{
      name:'matDetaile',
      components: {
        CstItem,
        timePicker
      },
      data(){
        return {
          tabIndex:0,
          tabs:['物料信息','库存信息','出入库明细'],
          selectType:'',
          showPicker:false,
          pickerType:'radio',
          pickerValue:'',
          pickerList:[],
          matTypeList:[],
          action:'',
          mat_id:'',
          mate_info:{//		object
            batch_qty:"",//	采购批量	number	@mock=
            bin_no:"",//	储位编号	string	@mock=
            handled_time:"",//	操作时间	string	@mock=
            handler_id:"",//	操作人	number	@mock=
            height:"",//	高	number	长方形或圆柱体时为高，管状体为管长
            is_qc:"",//	是否需要质检	string	@mock=
            is_unused:"",//	是否废弃	string	@mock=
            length:"",//	长	number	长方形时为长，圆柱体时为底面直径，管状体时为外圆直径
            mat_desc:"",//	物料描述	string	@mock=
            mat_id:"",//	物料ID	number	@mock=
            mat_no:"",//	物料编号	string	@mock=
            mat_type_id:"",//	物料类型ID	number	@mock=
            mat_type_name:"请选择",//	物料类型名称	string	@mock=
            min_stock_qty:"",//	安全库存	number	@mock=
            pic_path	:"",//附图路径	string	@mock=
            plan_price	:"",//计划单价	number	@mock=
            processes_type	:"",//加工类型	string	(粗R、中Z、精F)
            procure_style:"",//	供应类型	string	1.直接采购 2.下料 3.库存供应
            rawnature_id:"",//	材质ID	number	材质ID@mock=
            rawnature_name:"请选择",//	材质名称	string	@mock=
            shape	:"",//形状	string	1.长方体Cuboid,2.圆柱体Cylinder,3.管状体Pipe
            spec:"",//	规格型号	string	@mock=
            unit_no	:"请选择",//单位	string	@mock=
            version	:"",//版本	number	@mock=
            weight:"",//	重量	number	@mock=
            width:"",//
          }

        }
      },
      activated(){
        this.getMatDetaile()
      },
      mounted(){
        this.mat_id=this.$route.query.mat_id
      },
      methods: {
        getMatDetaile(){
          const reqData = {
            mat_id:this.mat_id
          }
          getMatDetaile(reqData).then(res=>{
            this.mate_info=res
          }).catch(error=>{
            $toast.show(error.description)
          })
        },
        newMat(){
          $router.forward({path:'/storage/newMat'})
        },
        onTabClick(index){
          if(index===this.tabIndex){
            return
          }
          this.tabIndex = index
        },
        selectMatType(){
          this.selectType='matType'
          if(this.matTypeList.length>0){
            this.pickerValue={
              val:this.mate_info.mat_type_name,
              id:this.mate_info.mat_type_id
            }
            this.pickerList=[]
            this.matTypeList.forEach((e,i)=>{
              this.pickerList.push({
                val:e.mat_type_name,
                id:e.mat_type_id
              })
            })
            this.showPicker=true
          }else{
            this.getMatTypeList()
          }
        },
        chosen(e){
          if(this.selectType==='matType'){
            this.mate_info.mat_type_name=e.val
            this.mate_info.mat_type_id=e.id
          }
        }


      }
    }
</script>

<style lang="scss" scoped>
  textarea{
    resize: none;
    border: 1px solid #ddd;
    width: 100%;height: 76px;
    padding: 3px;
  }
</style>

<template>
    <div class="mw-page page storage" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>新建物料</span>
            <i class="right" ></i>
        </div>
        <div  class="mw-page-content">
          <cst-item label="物料类型:" v-model="mate_info.mat_type_name" @click.native="selectMatType()" :isMust="true"
                    action="check"  :hasArrow="true"  >
          </cst-item>
          <!--<cst-item label="物料名称"   v-model="mate_info.mat_name " :action="this.action"  editType="number" :hasArrow="false"></cst-item>-->
          <cst-item label="物料名称" placeholder="请输入物料名称"  v-model="mate_info.mat_name"  action="action"  editType="text" :isMust="true"></cst-item>

          <!--<cst-item label="物料编号"   v-model="mate_info.mat_no " :action="this.action"  editType="text" :hasArrow="false"></cst-item>-->
          <cst-item label="物料编号" placeholder="请输入物料编号"  v-model="mate_info.mat_no"  action="action"  editType="text" :isMust="true"></cst-item>
          <cst-item label="规格型号" placeholder="请输入规格型号"  v-model="mate_info.spec"  action="action"  editType="text" :isMust="true"></cst-item>
          <cst-item label="计量单位" v-model="mate_info.unit_no " @click.native="selectUnit()" action="check"  :hasArrow="true" :isMust="true" ></cst-item>
          <item style="margin: 0">
            <span class="isMust">形状：</span>
            <label><input :disabled="action==='check'" name="shape" type="radio" value="1" v-model="mate_info.shape"/> 立方体</label>
            <label><input :disabled="action==='check'" name="shape" type="radio" value="2" v-model="mate_info.shape"/> 圆柱体</label>
            <label><input :disabled="action==='check'" name="shape" type="radio" value="3" v-model="mate_info.shape"/> 管状体</label>
          </item>
          <cst-item :isMust="true" class="noneBorder" v-show="mate_info.shape==='1'"  label="长(mm)" :placeholder="'0'" v-model="mate_info.length" action="action" editType="number"></cst-item>
          <cst-item :isMust="true" class="noneBorder" v-show="mate_info.shape==='1'" label="宽(mm)" :placeholder="'0'" v-model="mate_info.width" action="action" editType="number"></cst-item>
          <cst-item :isMust="true" class="noneBorder" v-show="mate_info.shape==='1'" label="高(mm)" :placeholder="'0'" v-model="mate_info.height" action="action" editType="number"></cst-item>
          <cst-item :isMust="true" class="noneBorder" v-show="mate_info.shape==='2'" label="直径(mm)" :placeholder="'0'" v-model="mate_info.length" action="action" editType="number"></cst-item>
          <cst-item :isMust="true" class="noneBorder" v-show="mate_info.shape==='2'" label="高(mm)" :placeholder="'0'" v-model="mate_info.height" action="action" editType="number"></cst-item>
          <cst-item :isMust="true" class="noneBorder" v-show="mate_info.shape==='3'"  label="外圆直径(mm)" :placeholder="'0'" v-model="mate_info.length" action="action" editType="number"></cst-item>
          <cst-item :isMust="true" class="noneBorder" v-show="mate_info.shape==='3'" label="内圆直径(mm)" :placeholder="'0'" v-model="mate_info.width" action="action" editType="number"></cst-item>
          <cst-item :isMust="true" class="noneBorder" v-show="mate_info.shape==='3'" label="高(mm)" :placeholder="'0'" v-model="mate_info.height" action="action" editType="number"></cst-item>

          <cst-item :isMust="true" label="材质" v-model="mate_info.rawnature_name " @click.native="selectRawnature()" action="action" :canEdit="false" :hasArrow="this.action!=='check'"  ></cst-item>
          <cst-item :isMust="true" label="重量(KG)" placeholder="请输入单个物料的重量"  v-model="mate_info.weight"  action="action"  editType="text" :hasArrow="false"></cst-item>
          <cst-item :isMust="true" label="物料单价(元)" placeholder="请输入物料单价"  v-model="mate_info.plan_price"  action="action"  editType="text" :hasArrow="false"></cst-item>
          <cst-item :isMust="true" label="安全库存" placeholder="请输入安全库存"  v-model="mate_info.min_stock_qty"  action="action"  editType="number" :hasArrow="false"></cst-item>
          <cst-item :isMust="true" label="批量采购基数" placeholder="请输入批量采购基数"  v-model="mate_info.batch_qty"  action="action"  editType="number" :hasArrow="false"></cst-item>

          <item >
            <span class="isMust">入库是否质检</span>
            <label ><input  name="isQc" :disabled="action ==='check'" type="radio" value="1" v-model="mate_info.is_qc"/> 是</label>
            <label ><input name="isQc" :disabled="action==='check'" type="radio" value="0"  v-model="mate_info.is_qc"/> 否</label>


          </item>
         <item  >
            备注 :
            <textarea :placeholder="action==='check'?'':'请输入备注'"  class="remark" type="text" v-model="mate_info.mat_desc"></textarea>
          </item>
      </div>
      <div class="mw-page-footer">
        <div  class="btns">
          <div class="btn" @click="save">
            保存
          </div>
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
  import { storageMats,UnitList,rawNatureList,matModify,getMatDetaile } from '@/api/storage/baseInfo'
  import timePicker from "@/components/timePicker"
    export default{
      name:'newMat',
      components: {
        CstItem,
        timePicker
      },
      data(){
        return {
          selectType:'',
          showPicker:false,
          pickerType:'radio',
          pickerValue:'',
          pickerList:[],
          matTypeList:[],
          unitList:[],
          rawNatureList:[],
          action:'',
          mat_id:'',
          mate_info:{//		object
            batch_qty:1,//	采购批量	number	@mock=
            bin_no:"",//	储位编号	string	@mock=
            handled_time:"",//	操作时间	string	@mock=
            handler_id:"",//	操作人	number	@mock=
            height:"",//	高	number	长方形或圆柱体时为高，管状体为管长
            is_qc:"",//	是否需要质检	string	@mock=
            is_unused:"",//	是否废弃	string	@mock=
            length:"",//	长	number	长方形时为长，圆柱体时为底面直径，管状体时为外圆直径
            mat_desc:"",//	物料描述	string	@mock=
            mat_name:'',
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
      mounted(){
        let param=this.$route.query
        this.action=param.action
        if(this.action==='edit'){
          this.mat_id=param.mat_id
          this.getMatDetaile()
        }
        this.getList()
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
        getList(){
          let reqData = {
            is_paging:0
          }
          storageMats(reqData).then(res=>{
            this.matTypeList = res.mat_type_list
          }).catch(error=>{
            $toast.show(error.description)
          })
          UnitList({}).then(res=>{
            this.unitList=res.unit_list
          }).catch(error=>{
            $toast.show(error.description)
          })
          rawNatureList({}).then(res=>{
            this.rawNatureList=res.raw_nature_list
          }).catch(error=>{
            $toast.show(error.description)
          })
        },
        newMat(){
          $router.forward({path:'/storage/newMat'})
        },
        save(){
          if(this.mate_info.mat_type_name==='请选择'){
            $toast.show('请选择物料类型',600)
            return
          }
          if(this.mate_info.mat_name.replace(/\s+/g,"")===''){
            $toast.show('请输入物料名称',600)
            return
          }
          if(this.mate_info.mat_no.replace(/\s+/g,"")===''){
            $toast.show('请输入物料编号',600)
            return
          }
          if(this.mate_info.spec.replace(/\s+/g,"")===''){
            $toast.show('请选输入规格型号',600)
            return
          }
          if(this.mate_info.unit_no==='请选择'){
            $toast.show('请选择计量单位',600)
            return
          }
          if(this.mate_info.shape===''){
            $toast.show('请选择物料形状',600)
            return
          }else{
            if(this.mate_info.shape==='2'){
              if(this.mate_info.length===''||this.mate_info.height===''){
                $toast.show('请完善物料尺寸',600)
                return
              }
            }else{
              if(this.mate_info.width===''||this.mate_info.length===''||this.mate_info.height===''){
                if(this.mate_info.length===''||this.mate_info.height===''){
                  $toast.show('请完善物料尺寸',600)
                  return
                }
              }
            }
          }
          if(this.mate_info.rawnature_name==='请选择'){
            $toast.show('请选择材质',600)
            return
          }
          if(this.mate_info.weight.replace(/\s+/g,"")===''){
            $toast.show('请输入重量',600)
            return
          }

          if(this.mate_info.plan_price.replace(/\s+/g,"")===''){
            $toast.show('请输入单价',600)
            return
          }
          if(this.mate_info.min_stock_qty.replace(/\s+/g,"")===''){
            $toast.show('请输入安全库存',600)
            return
          }
          if(this.mate_info.isQc===''){
            $toast.show('请选择入库前是否质检',600)
            return
          }
          const reqData = {
            mat_info:this.mate_info
          }
          matModify(reqData).then(res=>{
            this.matTypeList = res.mat_type_list
            $toast.show("新增成功",2000)
            history.back()
          }).catch(error=>{
            $toast.show(error.description)
          })
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

          }
        },
        selectUnit(){
          this.selectType='unit'
          if(this.unitList.length>0){
            this.pickerValue={
              val:this.mate_info.unit_no,
              id:this.mate_info.unit_no
            }
            this.pickerList=[]
            this.unitList.forEach((e,i)=>{
              this.pickerList.push({
                val:e.name,
                id:e.name
              })
            })
            this.showPicker=true
          }else{

          }
        },
        selectRawnature(){
          this.selectType='rawnature'
          if(this.rawNatureList.length>0){
            this.pickerValue={
              val:this.mate_info.rawnature_name,
              id:this.mate_info.rawnature_id
            }
            this.pickerList=[]
            this.rawNatureList.forEach((e,i)=>{
              this.pickerList.push({
                val:e.raw_nature,
                id:e.raw_nature_id
              })
            })
            this.showPicker=true
          }else{

          }
        },
        chosen(e){
          if(this.selectType==='matType'){
            this.mate_info.mat_type_name=e.val
            this.mate_info.mat_type_id=e.id
          }else if(this.selectType==='unit'){
            this.mate_info.unit_no=e.val
          }else if(this.selectType==='rawnature'){
            this.mate_info.rawnature_name=e.val
             this.mate_info.rawnature_id=e.id
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

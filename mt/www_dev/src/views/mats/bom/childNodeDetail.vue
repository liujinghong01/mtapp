<template>
  <div class="mw-page page mats nodeDetail" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span v-if="action==='new'">添加物料</span>
      <span v-else>物料详情</span>
      <i class="right"></i>
    </div>
    <div  class="mw-page-content">
      <cst-item v-show="mat_type==='materials'||mat_type==='electrode'"
                label="类别" v-model="mate_info.mat_type_name " :action="action" :canEdit="false" :hasArrow="action!=='check'&&mat_type!=='electrode'" @click.native="onSelect('mat_type')" >
      </cst-item>
      <cst-item label="物料名称" v-model="mate_info.mat_name "
                :action="this.mate_info.mat_type_name==='标准件'?'check':'action'" :canEdit="false" :hasArrow="this.mate_info.mat_type_name==='标准件'&&action!=='check'" @click.native="onSelect('mat_name')" >
      </cst-item>
      <div v-show="mate_info.mat_type_name==='请选择'||mate_info.mat_type_name==='毛坯'" class="mold"><!-- 模胚-->
        <item >
          <span>形状：</span>
          <label><input :disabled="action==='check'" name="shape" type="radio" value="1" v-model="mate_info.shape"/> 立方体</label>
          <label><input :disabled="action==='check'" name="shape" type="radio" value="2" v-model="mate_info.shape"/> 圆柱体</label>
        </item>
        <cst-item class="noneBorder" v-show="mate_info.shape==='1'"  label="长(mm)" :placeholder="'0'" v-model="mate_info.length" :action="this.action" editType="number"></cst-item>
        <cst-item class="noneBorder" v-show="mate_info.shape==='1'" label="宽(mm)" :placeholder="'0'" v-model="mate_info.width" :action="this.action" editType="number"></cst-item>
        <cst-item class="noneBorder" v-show="mate_info.shape==='1'" label="高(mm)" :placeholder="'0'" v-model="mate_info.height" :action="this.action" editType="number"></cst-item>
        <cst-item class="noneBorder" v-show="mate_info.shape==='2'" label="高(mm)" :placeholder="'0'" v-model="mate_info.height" :action="this.action" editType="number"></cst-item>
        <cst-item class="noneBorder" v-show="mate_info.shape==='2'" label="直径(mm)" :placeholder="'0'" v-model="mate_info.length" :action="this.action" editType="number"></cst-item>
        <cst-item label="材质" v-model="mate_info.rawnature_name "
                  :action="action" :canEdit="false" :hasArrow="this.action!=='check'" @click.native="onSelect('rawnature')" >
        </cst-item>
        <cst-item   label="物料单价" v-model="mate_info.plan_price" :placeholder="'0'" :action="this.action" editType="number">{{newPrice}}</cst-item>
        <cst-item   label="胚料重量" v-model="mate_info.weight" :placeholder="'0'" :action="this.action" editType="number"></cst-item>
      </div>
      <div v-show="mate_info.mat_type_name==='标准件'" class="standard">
        <cst-item label="规格型号" v-model="mate_info.spec "
                  :action="action" :canEdit="false" :hasArrow="this.action!=='check'" @click.native="onSelect('spec')" >
        </cst-item>
        <cst-item label="材质" v-model="mate_info.rawnature_name "
                  :action="'check'" :canEdit="false" :hasArrow="false"  >
        </cst-item>
        <cst-item   label="物料单价" v-model="mate_info.plan_price" :placeholder="'0'" :action="this.action" editType="number"></cst-item>
      </div>
      <item >
        <span>入库是否质检：</span>
        <label><input name="is_qc" :disabled="action==='check'" type="radio" value="1" v-model="mate_info.is_qc"/> 是</label>
        <label><input name="is_qc" :disabled="action==='check'" type="radio" value="0"  v-model="mate_info.is_qc"/> 否</label>
      </item>
      <cst-item  label="数量" v-model="node_info.total_qty" :action="action" editType="number"></cst-item>
      <item  style="height: 120px">
        备注 :
        <textarea :placeholder="action==='check'?'':'请输入备注'" :disabled="action==='check'" class="remark" type="text" v-model="node_info.remark"></textarea>
      </item>
    </div>
    <div class="select-box" :class="selectInfo.selectSwitch?'active':''">
      <div class="select-click" @click="selectInfo.selectSwitch=false"></div>
      <div class="select-main">
        <div class="main-header">
          <p>{{ selectInfo.title }}</p>
          <input type="text">
        </div>
        <div class="main-list">
          <p v-for=" (item,index) in selectInfo.list "  @click="checked(item,index)">{{ item }}</p>
        </div>
      </div>
    </div>
    <div v-show="app_flag!=='step30'&&app_flag!=='step0'"  class="mw-page-footer">
      <div v-show="this.action!=='check'" class="btns">
        <div class="btn" @click="saveBtn">
          保存
        </div>
        <div v-show="this.edit==='edit'" class="btn" @click="cancelEdit">
          取消
        </div>
      </div>
      <div v-show="this.action==='check'" class="btns">
        <div class="btn" @click="clickEdit">
          编辑
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import CstItem from "../../../components/cstItem";
  import { updateNodes,cascadeType,mateName,matNameSpec,cascadeMat,rawnatureList,rawnaturePrice,bomNodeInfo } from '@/api/mats'
  export default{
    name:'childNodeDetail',
    components: {CstItem},
    data(){
      return {
        action:'',
        edit:'',
        node_id:'',
        mat_type:'',
        app_flag:'',
        selectInfo:{
          title:'请选择类型',
          selectSwitch:false,
          selectType:'',
          list:[],
          CheckedList:{//已选选项selectInfo.CheckedList.TypeName
            mat_type:false,//物料类别
            mat_name:false,     //物料名称
            rawnature_name:'',//材料名称
            spec:false
          }
        },
        cascadeData:{//级联列表查询this.selectInfo.cascadeData.specList
          mat_name_list:[],
          specList:[],
          rawnature_list:[]
        },
        mate_info:{
          height:'',
          length:'',
          is_qc:0,
          mat_id:'',
          mat_name:'请选择',
          mat_name_id:'',
          mat_no:'',//毛坯必传
          mat_type_id:'1',
          mat_type_name:'请选择',
          pic_path:'',
          plan_price:'' ,
          procure_style:'',
          rawnature_id:'',
          rawnature_name:'请选择',
          shape:'1',
          spec:'请选择',
          weight:'',
          width:'',
        },
        node_info:{
          is_purchase:0,
          mat_id:'',
          mat_type:'',
          node_id:'',
          processes_f_count:'',
          processes_r_count:'',
          processes_z_count:'',
          remark:'',
          total_qty:1,
        }
      }
    },
    activated:function(){
    },
    computed:{
      newPrice:function(){
        if(this.mate_info.height!==0&&this.mate_info.length!==0&&this.mate_info.rawnature_id!==''){
          if(this.mate_info.shape==='1'&&this.mate_info.width!==0){
            this.rawnaturePrice()
          }else if(this.mate_info.shape==='2'){
            this.rawnaturePrice()
          }
        }
      }
    },
    methods: {
      cancelEdit(){
        this.action='check'
        this.edit=''
      },
      clickEdit(){
        this.action='action'
        this.edit='edit'
        if(this.mate_info.mat_type_name==='标准件'){
          this.mateName()
          this.matNameSpec()
        }else{
          this.cascadeMat()
        }


      },
      rawnaturePrice(){//计算价格
        if(this.action==='check'){
          return
        }
        let volume=''
        let rawnature=''
        if(this.mate_info.shape==='1'){
          this.mate_info.spec=this.mate_info.length+'x'+this.mate_info.width+'x'+this.mate_info.height
          volume=parseFloat(this.mate_info.length)*parseFloat(this.mate_info.width)*parseFloat(this.mate_info.height)
        }else if(this.mate_info.shape==='2'){
          this.mate_info.spec='Φ:'+this.mate_info.length+'  H:'+this.mate_info.height
          volume=Math.PI*(parseFloat(this.mate_info.length)/2)*(parseFloat(this.mate_info.length)/2)*parseFloat(this.mate_info.height)
        }
        const requestData={
          height:this.mate_info.height,
          length:this.mate_info.length,
          width:this.mate_info.width,
          rawnature_id:this.mate_info.rawnature_id,
          shape:this.mate_info.shape,
        }
        this.cascadeData.rawnature_list.forEach((e,i)=>{
          if(e.raw_nature_id===this.mate_info.rawnature_id){
            rawnature=e
            console.log(e)
          }
        })
        this.mate_info.weight=volume*rawnature.density/1000000
        this.mate_info.plan_price=volume*rawnature.density*rawnature.procure_price
      },
      rawnatureList(){//获取材质列表
        const requestData={
          query:{
            isUnused:'',
            rawnature_name:'',
            rawnatureType:''
          }
        }
        rawnatureList(requestData).then(res=>{
          this.cascadeData.rawnature_list=res.rawnature_list
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      cascadeMat(){//物料类型级联名称查询
        const requestData={
          query:{
            mat_type:this.mate_info.mat_type_name
          }
        }
        cascadeMat(requestData).then(res=>{
          this.cascadeData.mat_name_list =  res.listCascadeMat

        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      mateName(){//物料名称列表查询
        const requestData={ }
        mateName(requestData).then(res=>{
          this.cascadeData.mat_name_list=res.mat_name_list
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      matNameSpec(){//物料名称级联规格查询
        const requestData={
          query:{
            mat_name:this.mate_info.mat_name
          }
        }
        matNameSpec(requestData).then(res=>{
          this.cascadeData.specList=res.mat_info_list

        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      update(){//创建或修改BOM节点数据提交
        let p_node_id=''
        if(this.edit==='edit'){
          p_node_id=''
          this.node_info.node_id=this.node_id
          if(this.mat_type==='electrode'){
            this.node_info.mat_type='电极'
          }else{
            this.node_info.mat_type=this.mate_info.mat_type_name
          }


        }else{
          p_node_id=this.node_id
        }
        const requestData={
          mat_info:this.mate_info,
          node_info:this.node_info,
          p_node_id:p_node_id
        }
        updateNodes(requestData).then(res=>{
          if(res===true){
            if(this.edit==='edit'){
              $toast.show('物料编辑成功')
              this.action='check'
              this.edit=''
            }else{
              $toast.show('添加新物料成功')
              $router.go(-1)
            }

          }
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      saveBtn(){//保存
          if(this.mate_info.rawnature_id!==''){
            this.update()
          }else{
            $toast.show('物料信息不全或填写错误，请重新填写')
          }

      },
      onSelect(type){
        if(this.action==='check'){return}

        if(this.mat_type==='materials'||this.mat_type==='electrode'){
          //TypeName:物料类别   mat_name:物料名称 rawnature_name:材质
          switch (type){
            case 'mat_type':{
              this.selectInfo.title='请选择类型'
              this.selectInfo.list=['毛坯','标准件']
              break
            }
            case 'mat_name':{
              if(this.mate_info.mat_type_name==='毛坯'){
                return
                break
              }
              let _this=this
              if(this.mate_info.mat_type_name!=='请选择'){
                _this.selectInfo.title='请选择子物料名称'
                _this.selectInfo.list=[]
                if(this.mate_info.mat_type_name==='毛坯'){
                  _this.cascadeData.mat_name_list.forEach(function(e,i){
                    _this.selectInfo.list.push(e.matCascadeName)
                  })
                }else{
                  _this.selectInfo.list=_this.cascadeData.mat_name_list
                }
              }else{
                $toast.show('请先选择类型')
                return
              }
              break
            }
            case 'spec':{
              if(this.mate_info.mat_name!=='请选择'){
                this.selectInfo.list=[]
                this.cascadeData.specList.forEach((e,i)=>{
                  this.selectInfo.list.push(e.spec)
                })
              }else{
                $toast.show('请先选择物料名称')
                return
              }

              break
            }
            case 'rawnature':{
              if(this.mate_info.mat_type_name!=='请选择'){
                this.selectInfo.list=[]
                this.cascadeData.rawnature_list.forEach((e,i)=>{
                  this.selectInfo.list.push(e.raw_nature)
                })
              }else{
                $toast.show('请先选择物料类别')
                return
              }
              break
            }
          }
        }
        this.selectInfo.selectType=type
        this.selectInfo.selectSwitch=true
      },
      checked(item,index){
        console.log(item)
        switch (this.selectInfo.selectType){
          case 'mat_type':{
            this.mate_info.mat_type_name=item
            this.node_info.mat_type=item
            if(item==='毛坯'){
              this.mate_info.mat_name='A板材料'
              this.rawnatureList()
            }else{
              this.mate_info.mat_name='请选择'
              this.mateName()
            }
            this.mate_info.plan_price=''
            this.mate_info.weight=''
            this.mate_info.spec=''
            this.mate_info.rawnature_id=''
            // this.mate_info.mat_id=''
            this.mate_info.is_qc=0
            this.mate_info.rawnature_name='请选择'
            break
          }
          case 'mat_name':{
            this.mate_info.mat_name=item
            if(this.mate_info.mat_type_name==='标准件'){
              this.matNameSpec()
              this.mate_info.spec='请选择'
              this.mate_info.plan_price=0
              this.mate_info.rawnature_id=''
            }

            break
          }
          case 'spec':{
            this.mate_info.spec=item
            this.mate_info.rawnature_id=this.cascadeData.specList[index].rawnature_id
            this.mate_info.mat_id=this.cascadeData.specList[index].mat_id//选定物料ID
            this.mate_info.rawnature_name=this.cascadeData.specList[index].rawnature_name
            this.mate_info.plan_price=this.cascadeData.specList[index].plan_price
            if(this.cascadeData.specList[index].is_qc==='1'){
              this.mate_info.is_qc=1
            }else{
              this.mate_info.is_qc=0
            }
            break
          }
          case 'rawnature':{
            this.mate_info.rawnature_name=item
            this.mate_info.rawnature_id=this.cascadeData.rawnature_list[index].raw_nature_id
            break
          }
        }
        this.selectInfo.selectSwitch=false

      },
      bomNodeInfo(){//获取材质列表
        const requestData={
          node_id:this.node_id
        }
        bomNodeInfo(requestData).then(res=>{
          this.mate_info=res.bom_node_info.mat
          this.node_info.total_qty=res.bom_node_info.total_qty
          this.node_info.remark=res.bom_node_info.remark
          this.mate_info.is_qc=res.bom_node_info.mat.is_qc
          if(this.mate_info.is_qc!=='1'){
            this.mate_info.is_qc='0'
          }
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
    },
    mounted(){
      this.mat_type=this.$route.params.mat_type
      this.node_id=this.$route.params.node_id
      this.action=this.$route.params.action
      this.app_flag =this.$route.params.app_flag
      if(this.action==='check'){
        this.bomNodeInfo()
      }
      if(this.mat_type==='electrode'&&this.action==='new'){
        this.mate_info.mat_type_name='毛坯'
        this.mate_info.mat_type='毛坯'
        this.node_info.mat_type='毛坯'
        this.mate_info.mat_name='电极材料'
        this.rawnatureList()
      }
    }
  }
</script>
<style lang="scss" scoped>
  textarea{width: 100%;resize: none;height: 50px;background: #fff}
</style>

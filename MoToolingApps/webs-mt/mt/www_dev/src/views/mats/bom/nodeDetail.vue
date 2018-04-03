<template>
    <div class="mw-page page nodeDetail" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span v-if="action === 'new'" >添加物料</span>
            <span v-else>物料详情</span>
            <i class="right"></i>
        </div>
        <div class="mw-page-content ">
          <cst-item v-show="mat_type_name === '0'"
                    label="类别" v-model="selectInfo.mat_type_name " :action="action" :canEdit="false" :hasArrow="true" @click.native="selectList('TypeName')" >
          </cst-item>
          <cst-item v-show="mat_type_name!=='2'"
                    label="名称" v-model="mate_info.mat_name " :action="action" :canEdit="false" :hasArrow="true" @click.native="selectList('mat_name')" >
          </cst-item>
          <cst-item v-show="mat_type_name === '1'"
                    label="规格型号" v-model="mate_info.spec " :action="action" :canEdit="false" :hasArrow="true" @click.native="selectList('spec')" >
          </cst-item>
          <cst-item v-show="mat_type_name === '1'&&mate_info.spec!=='请选择'"
                    label="物料编码" v-model="mate_info.mat_no " :action="action" :canEdit="false"  >
          </cst-item>
          <item >
            <span>形状：</span>
            <label><input name="shape" type="radio" value="1" :disabled="action==='check'" v-model="mate_info.shape"/> 立方体</label>
            <label><input name="shape"  type="radio" value="2" :disabled="action==='check'" v-model="mate_info.shape"/> 圆柱体</label>
          </item>
          <cst-item class="noneBorder" v-show="mate_info.shape==='1'" :placeholder="'0'" label="长(mm)" v-model="mate_info.length"  :action="action" editType="number"></cst-item>
          <cst-item class="noneBorder" v-show="mate_info.shape==='1'" :placeholder="'0'" label="宽(mm)" v-model="mate_info.width" :action="action" editType="number"></cst-item>
          <cst-item class="noneBorder" v-show="mate_info.shape==='1'" :placeholder="'0'" label="高(mm)" v-model="mate_info.height" :action="action" editType="number"></cst-item>
          <cst-item class="noneBorder" v-show="mate_info.shape==='2'" :placeholder="'0'" label="高(mm)" v-model="mate_info.height" :action="action" editType="number"></cst-item>
          <cst-item class="noneBorder" v-show="mate_info.shape==='2'" :placeholder="'0'" label="直径(mm)" v-model="mate_info.length" :action="action" editType="number"></cst-item>
          <!--<item>-->
            <!--<span>材质：</span>-->
            <!--<span v-if="action==='check'||mat_type_name === '1' ">{{ mate_info.rawnature_name }}</span>-->
            <!--<span v-else class="item-note" @click="selectList('rawnature_name')">-->
              <!--{{ mate_info.rawnature_name }}-->
              <!--<i class="icon ion-ios-arrow-right" style="color: #DDD;"></i>-->
            <!--</span>-->
            <!--</item>-->
          <cst-item v-if="action==='check'||mat_type_name === '1' " label="材质" v-model="mate_info.rawnature_name" :action="action" :canEdit="false" editType="number"></cst-item>
          <cst-item v-else label="材质" v-model="mate_info.rawnature_name " :action="action" :canEdit="false" :hasArrow="true" @click.native="selectList('rawnature_name')" ></cst-item>

          <cst-item v-show="mate_info.shape==='2'" label="物料单价" :placeholder="'0'" v-model="mate_info.plan_price" :action="action" editType="number"></cst-item>
          <cst-item v-show="mat_type_name!=='1'" label="胚料重量" :placeholder="'0'" v-model="mate_info.weight" :action="action" editType="number"></cst-item>{{newPrice}}
          <cst-item v-show="mat_type_name !== '1'" label="计划单价"  :placeholder="'0'" v-model="mate_info.plan_price" :action="action" editType="number"></cst-item>
          <item  v-show=" mat_type_name === '0' ">
            <span>生成下层物料</span>
            <label v-if="mate_info.mat_name!=='外购模胚'"><input name="is_purchase"  :disabled="action==='check'" type="radio" value="1" v-model="node_info.is_purchase"/> 是</label>
            <label><input name="is_purchase"  :disabled="action==='check'" type="radio" value="0" v-model="node_info.is_purchase"/> 否</label>
          </item>
          <item  v-show=" mat_type_name === '1' ">
            <span>入库是否质检</span>
            <label><input :disabled="action==='check'" name="is_qc" type="radio" value="1" v-model="mate_info.is_qc"/> 是</label>
            <label><input :disabled="action==='check'" name="is_qc" type="radio" value="0" v-model="mate_info.is_qc"/> 否</label>
          </item>

          <item v-if="mat_type_name==='2'&&action==='new'" style="margin-top: 1px">
            数量 :
            <p >
              粗(个):<input :disabled="action==='check'" :placeholder="'0'" type="number" v-model="node_info.processes_r_count">
              中(个):<input :disabled="action==='check'" :placeholder="'0'" type="number" v-model="node_info.processes_z_count">
              精(个):<input :disabled="action==='check'" :placeholder="'0'" type="number"v-model="node_info.processes_f_count">
            </p>
          </item>
          <cst-item v-else label="数量" v-model="node_info.total_qty"  :action="action" editType="number"></cst-item>
          <item  v-show=" mat_type_name === '2' ">
            <span>供应类型</span>
            <label><input :disabled="action!=='new'" name="procure_style" type="radio" value="1" v-model="mate_info.procure_style"/> 采购</label>
            <label><input :disabled="action!=='new'" name="procure_style" type="radio" value="2" v-model="mate_info.procure_style"/> 下料</label>
            <label><input :disabled="action!=='new'" name="procure_style" type="radio" value="3" v-model="mate_info.procure_style"/> 库存</label>
          </item>
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
        <div v-show="this.action!=='check'&&$route.params.app_flag!=='step30'" class="mw-page-footer">
            <div class="btns">
                <div class="btn" @click="saveBtn">
                    保存
                </div>
                <div v-show="edit==='edit'" class="btn" @click="cancelEdit">
                  取消
                </div>
            </div>
        </div>
        <div v-show="this.action==='check'&&($route.params.app_flag!=='step30'&&$route.params.app_flag!=='step0')" class="mw-page-footer">
          <div class="btns">
            <div class="btn" @click="clickEdit">
              编辑
            </div>
          </div>
        </div>
    </div>
</template>
<script>
  import CstItem from "../../../components/cstItem";
  //{新建或保存Bom节点，查询物料类别，查询物料名称，物料名称级联查询，物料类型级联名称查询，材质列表，计划价格计算,获取BOM节点详情}
  import { updateNodes,cascadeType,mateName,matNameSpec,cascadeMat,rawnatureList,rawnaturePrice,bomNodeInfo } from '@/api/mats'
  export default{
    name:'bomNodeDetail',
    components: {CstItem},
    data(){
      return {
        action:'',
        node_id:'',
        mat_no:'',
        edit:'',
        mat_type_name:'',
        selectInfo:{
          title:'',
          selectSwitch:false,
          selectType:'',
          rawnatureIndex:-1,//被选中材质的下标
          mat_type_name:"",//根据该类型做级联
          CheckedList:{//已选选项CheckedList.TypeName
            TypeName:false,//物料类别
            mat_name:false,     //物料名称
            rawnature_name:'',//材料名称
            spec:false
          },
          list:[]
        },
        cascadeData:{//级联数据
          //物料类别类型列表cascadeData.mat_name_list
          cascade_type_list:[],
          //物料名称列表mat_name
          mat_name_list:[],
          //材质列表
          rawnature_list:[],
          //物料名称级联规格型号列表
          spec_list:[]
        },
        mate_info:{
          height:'',
          length:'',
          is_qc:'',
          mat_id:'',
          mat_name:'请选择',
          mat_nameId:'',
          mat_no:'',
          mat_cascade_type:'',
          mat_type_id:'1',
          mat_type_name:'',
          pic_path:'',
          plan_price:'' ,
          procure_style:'',
          rawnature_id:'',
          rawnature_name:'请选择',
          shape:'0',
          spec:'请选择',
          weight:'',
          width:'',
        },
        node_info:{
          is_purchase:1,
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
    computed:{
      newPrice:function(){
        if(this.mat_type_name==='2'){
          if(this.mate_info.height!==0&&this.mate_info.length!==0&&this.mate_info.rawnature_id!==''){
            if(this.mate_info.shape==='1'&&this.mate_info.width!==0){
              this.rawnaturePrice()
            }else if(this.mate_info.shape==='2'){
              this.rawnaturePrice()
            }
          }
        }
        let index=this.selectInfo.rawnatureIndex
        if(index!==-1&&this.mate_info.height!==0&&this.mate_info.height.toString().trim()!==''&&this.mate_info.length!==0&&this.mate_info.length.toString().trim()!==''){
          console.log(this.mate_info.height+':'+this.mate_info.length)
          if(this.mate_info.shape==='1'&&this.mate_info.width!==0&&this.mate_info.width.toString().trim()!==''){
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
        this.selectInfo.mat_type_name=this.mate_info.mat_type_name
        if(this.mat_type_name==='0'){
          this.cascadeType()
          this.rawnatureList()
        }else if(this.mat_type_name==='1'){
          this.mateName()
        }else if(this.mat_type_name==='2'){
          this.rawnatureList()
        }
      },
      //updateNodes,cascadeType,mateName,matNameSpec,cascadeMat,rawnatureList,rawnaturePrice
      cascadeType(){//物料类别查询
        const requestData={ }
        cascadeType(requestData).then(res=>{
          this.cascadeData.cascade_type_list=res.cascade_type_list
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
          this.cascadeData.spec_list=res.mat_info_list
          this.selectInfo.list=[]
          res.mat_info_list.forEach((e,i)=>{
            this.selectInfo.list.push(e.spec)
          })
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      cascadeMat(){//物料类型级联名称查询
        const requestData={
          query:{
            mat_type:this.selectInfo.mat_type_name
          }
        }
        cascadeMat(requestData).then(res=>{
          this.cascadeData.mat_name_list =  res.listCascadeMat

        }).catch(error=>{
          $toast.show(error.description)
        })
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
      bomNodeInfo(){//获取材质列表
        const requestData={
          node_id:this.node_id
        }
        bomNodeInfo(requestData).then(res=>{
          this.mate_info=res.bom_node_info.mat
          this.node_info.is_purchase=res.bom_node_info.is_purchase
          this.node_info.total_qty=res.bom_node_info.total_qty
          this.node_info.remark=res.bom_node_info.remark
          this.node_info.node_id=res.bom_node_info.node_id
          this.node_info.mat_type=res.bom_node_info.mat_type
          this.selectInfo.CheckedList.TypeName=true
          this.selectInfo.CheckedList.mat_name=true
          if(this.node_info.mat_type==='标准件'){
          }else{
            this.node_info.mat_id=''
            this.selectInfo.mat_type_name=this.mate_info.mat_type_name
          }
          if(this.mate_info.is_qc!=='1'){
            this.mate_info.is_qc='0'
          }
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      rawnaturePrice(){//计算价格
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
        //修改为前端计算
        this.cascadeData.rawnature_list.forEach((e,i)=>{
          if(e.raw_nature_id===this.mate_info.rawnature_id){
            rawnature=e
            console.log(e)
          }
        })
        this.mate_info.weight=volume*rawnature.density
        this.mate_info.plan_price=volume*rawnature.density*rawnature.procure_price

        // rawnaturePrice(requestData).then(res=>{
        //   this.mate_info.plan_price=res.plan_price
        //   this.mate_info.weight=res.weight
        // }).catch(error=>{
        //   $toast.show(error.description)
        //   this.mate_info.plan_price=0
        //   this.mate_info.weight=0
        // })
      },
      saveBtn(){//保存
        if(this.mat_type_name==='0'){
          let weight=this.mate_info.weight
          if(this.node_info.total_qty>0){
            if(weight>0){
              this.update()
            }else{
              $toast.show('物料信息不全或填写错误，请重新填写')
            }
          }else{
            $toast.show('物料数量有误，请重新填写')
          }
        }else if(this.mat_type_name==='1'){
          if(this.mate_info.rawnature_id!==''){
            this.update()
          }else{
            $toast.show('物料信息不全或填写错误，请重新填写')
          }
        }else if(this.mat_type_name==='2'){
          if(this.mate_info.rawnature_id!==''&&this.mate_info.procure_style!==''){
            if(this.node_info.processes_f_count!=='0'||this.node_info.processes_r_count!=='0'||this.node_info.processes_z_count!=='0'){
              this.update()
            }else{
              $toast.show('请填写新建电极数量')
            }

          }else{
            $toast.show('物料信息不全或填写错误，请重新填写')
          }
        }
      },
      update(){//创建或修改BOM节点数据提交
        var p_node_id=''
        if(this.edit==='edit'){
          p_node_id=''
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
            if(this.edit!=='edit'){
              $toast.show('添加新物料成功')
              $router.go(-1)
            }else{
              $toast.show('物料修改成功')
              $router.go(-1)
            }
          }
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      selectList(type){
        if(this.action==='check'){
          return
        }
        if(this.mat_type_name==='0'){
          //TypeName:物料类别   mat_name:物料名称 rawnature_name:材质
          switch (type){
            case 'TypeName':{
              this.selectInfo.title='请选择物料类别';
              this.selectInfo.list=this.cascadeData.cascade_type_list
              break
            }
            case 'mat_name':{
              if (this.selectInfo.CheckedList.TypeName){
                this.selectInfo.title='请选择物料'
                this.selectInfo.list=[];
                this.cascadeData.mat_name_list.forEach((e,i)=> {
                  this.selectInfo.list.push(e.mat_cascade_name)
                })
              }else{
                $toast.show('请先选择物料类别')
                return
              }
              break
            }
            case 'rawnature_name':{
              this.cascadeData.rawnature_list.forEach((e,i)=>{//侧滑选项框加载材质列表
                  this.selectInfo.list.push(e.raw_nature)
                })
              if (this.selectInfo.CheckedList.mat_name){
                this.selectInfo.title='请选择物料材质'
              }else{
                $toast.show('请先选择物料名称')
                return
              }
              break
            }
          }
        }else if(this.mat_type_name==='1'){
          switch (type){
            case 'mat_name':{
              this.selectInfo.list=this.cascadeData.mat_name_list
              this.selectInfo.title='请选择物料名称';
              break
            }
            case 'spec':{
              if (this.selectInfo.CheckedList.mat_name){
                this.selectInfo.title='请选择物料规格型号'
              }else{
                $toast.show('请先选择物料名称')
                return
              }
              break
            }
          }
        }else if(this.mat_type_name==='2'){
          switch (type){
              case 'rawnature_name':{
                this.selectInfo.title='请选择物料材质'
                this.cascadeData.rawnature_list.forEach((e,i)=>{
                  this.selectInfo.list.push(e.raw_nature)
                })
                break
              }
          }
        }
        this.selectInfo.selectType=type
        this.selectInfo.selectSwitch=true
      },
      checked(item,index){
        if(this.mat_type_name==='0'){
          switch (this.selectInfo.selectType){
            case 'TypeName':{
              this.selectInfo.mat_type_name=item//选定物料类别
              this.mate_info.mat_cascade_type=item
              this.cascadeMat();//物料类型级联名称查询，并清除侧滑列表数据，加载物料名称数据
              this.selectInfo.CheckedList.TypeName=true//标记物料类别已选，可选物料名称；并清空 已选物料名称，清空已选材质
              this.mate_info.mat_name='请选择'//清除已选物料名称
              this.mate_info.rawnature_name ='请选择'//清除已选物料材质
              this.selectInfo.CheckedList.mat_name=false//标记物料名称未选，禁止选物料材质
              break
            }
            case 'mat_name':{
              if(item==='外购模胚'){
                this.node_info.is_purchase='0'
              }

              this.mate_info.mat_name = item//选定物料名称
              this.node_info.mat_name_id=this.cascadeData.mat_name_list[index].mat_cascade_no
              this.node_info.mat_id=this.cascadeData.mat_name_list[index].id//选定物料ID

              if(this.edit==='edit'){
                this.mate_info.mat_id=this.node_info.mat_id
              }
              this.selectInfo.CheckedList.mat_name=true//标记物料名称已选
              this.mate_info.rawnature_name ='请选择'//清除已选物料材质
              this.mate_info.rawnature_id =''
              this.selectInfo.list=[];//清除侧滑列表数据
              break

            }
            case 'rawnature_name':{
              this.selectInfo.rawnatureIndex=index//选中材质，记住选中材质的下标值，便于读取材质信息
              this.mate_info.rawnature_id=this.cascadeData.rawnature_list[index].raw_nature_id//选定材质ID
              this.mate_info.rawnature_name = item//选定材质
              break
            }
          }
        }else if(this.mat_type_name==='1'){
          switch (this.selectInfo.selectType){
            case 'mat_name':{
              this.mate_info.mat_name = item//选定物料名称
              this.selectInfo.CheckedList.mat_name=true
              this.matNameSpec()
              this.mate_info.spec='请选择'
              this.mate_info.rawnature_name=''
              this.mate_info.plan_price=''
              break
            }
            case 'spec':{
              this.mate_info.spec=item
              let Item=this.cascadeData.spec_list[index]
              this.mate_info.rawnature_id=Item.rawnature_id
              this.mate_info.mat_id=Item.mat_id//选定物料ID
              this.mate_info.mat_no=Item.mat_no
              // console.log(Item.mat_no)
              this.node_info.mat_id=this.mate_info.mat_id
              this.mate_info.rawnature_name=Item.rawnature_name
              this.mate_info.plan_price=Item.plan_price
              if(Item.is_qc!=='1'){
                this.mate_info.is_qc=0
              }else{
                this.mate_info.is_qc=1
              }
            }
          }
        }else if(this.mat_type_name==='2'){
          switch (this.selectInfo.selectType){
            case 'rawnature_name':{
              this.mate_info.rawnature_id=this.cascadeData.rawnature_list[index].raw_nature_id//选定材质ID
              this.mate_info.rawnature_name = item//选定材质
              this.mate_info.mat_name='电极材料'
              break
            }
          }
        }

        this.selectInfo.selectSwitch=false
      }
    },
    mounted(){
      this.node_id = this.$route.params.node_id
      this.action = this.$route.params.action
      this.mat_type_name=this.$route.params.matTypeName
      if(this.mat_type_name==='0'){
        if(this.action==='new'){
          this.mate_info.mat_type_name = '半成品'
          this.node_info.mat_type='半成品'
          this.selectInfo.mat_type_name = '请选择类别'
          this.cascadeType();
          this.rawnatureList();
        }else if(this.action==='check'){
          this.bomNodeInfo()
        }
      }else if(this.mat_type_name==='1'){
        if(this.action==='new'){
          this.mate_info.mat_type_name = '标准件'
          this.node_info.mat_type='标准件'
          this.selectInfo.CheckedList.TypeName=true
          this.mateName()
        }else if(this.action==='check'){

          this.bomNodeInfo()
        }
      }else if(this.mat_type_name==='2'){
        this.node_info.mat_type='电极'
        this.mate_info.mat_type_name='电极'
        if(this.action=='new'){
          this.rawnatureList()
        }else{
          this.bomNodeInfo()
        }

      }

    }
  }
</script>

<style lang="scss" scoped>

  textarea{width: 100%;resize: none;height: 50px;background: #fff}
</style>

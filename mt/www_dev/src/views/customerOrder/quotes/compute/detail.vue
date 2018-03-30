<template>
  <div class="mw-page page" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>核算单详情</span>
      <!--审批状态码 step10(起草)：可编辑, step20(审批中),step30(审批中),step0(审批结束)不可编辑 step11(被退回)可编辑/可查看审批意见-->
      <i class="right" v-if="tabIndex===0"></i>
      <i class="right" @click="$router.forward({path:'/customerOrder/computemReferTo',query:{compute_id:compute.compute_id}})" v-else>参考</i>
    </div>
    <div class="mw-page-content has-tab">
      <div style="height:60px">
        <tabs :tab-items="tabs" :tab-index="tabIndex" :on-tab-click="onTabClick"></tabs>
      </div>
      <div style="flex: 1;position: relative">
        <div class="page-content" v-show="tabIndex===0">
          <cst-item label="核算单编号" v-show="compute.compute_no" v-model="compute.compute_no" editType="text" :action="action"
                    :canEdit="false"></cst-item>
          <cst-item label="核算日期" v-show="compute.compute_no" v-model="compute.created_at" editType="text" :action="action"
                    :canEdit="false"></cst-item>
          <cst-item label="模具类型" @click.native="onPicker('isType')" v-model="compute.type_name" editType="text" :action="action" :hasArrow="true"
                     :canEdit="false"></cst-item>
          <cst-item label="制品材质" @click.native="onPicker('isMat')" v-model="compute.material" editType="text" :action="action" :hasArrow="true"
                    :canEdit="false"></cst-item>
          <!--<cst-item label="制品材料"-->
                    <!--v-model="compute.material"-->
                    <!--editType="text"-->
                    <!--:action="action"-->
                    <!--:hasArrow="true"-->
                    <!--:canEdit="false"-->
                    <!--@click.native="changeType('material')">-->
          <!--</cst-item>-->
          <cst-item label="制品信息" v-model="compute.prod_info" placeholder="请输入制品信息" editType="text" :action="action"></cst-item>
          <item>
            备注：<br/>
            <textarea v-model="compute.prod_remark" placeholder="请输入制品备注"></textarea>
          </item>
          <!--<cst-item label="制品信息" editType="number" :hasArrow="true" :arrowText="compute.cst_products.length"-->
                    <!--:action="action"-->
                    <!--:canEdit="false"-->
                    <!--@click.native="toProdList"></cst-item>-->
        </div>
        <div class="page-content" v-show="tabIndex===1">
          <cst-item label="材料费" :hasArrow="true" v-model="compute.stuff_cost" @click.native="onArrowClick"
                    editType="number" :action="action" :canEdit="false"></cst-item>
          <cst-item label="加工费" :hasArrow="true" v-model="compute.process_cost" @click.native="onArrowClick1"
                    editType="number" :action="action" :canEdit="false"></cst-item>
          <cst-item label="设计费" v-model="compute.design_cost" editType="number" :action="action"></cst-item>
          <cst-item label="试模费" v-model="compute.try_mold_cost" editType="number" :action="action"></cst-item>
          <cst-item label="运输费" v-model="compute.delivery_cost" editType="number" :action="action"></cst-item>
          <cst-item label="其他费用" v-model="compute.post_box_cost" editType="number" :action="action"></cst-item>
          <item style="margin: 0" @click.native="onPicker('manage_ratio')">管理费:({{compute.manage_cost }}元) <span style="float: right">{{ compute.manage_ratio }}</span>  </item>
          <!--<cst-item label="管理费" @click.native="onPicker('manage_cost')" v-model="compute.manage_cost"  ></cst-item>-->
          <cst-item label="模具成本" v-model="compute.mold_cost" editType="number"></cst-item>
          <item style="margin: 0" @click.native="onPicker('profit_ratio')">利润:({{compute.profit_cost }}元) <span style="float: right">{{ compute.profit_ratio }}</span>  </item>

          <!--<cst-item label="利润" @click.native="onPicker('profit_cost')" v-model="compute.profit_cost" ></cst-item>-->
          <cst-item label="模具单价" v-model="compute.mold_up" editType="number" :action="action"></cst-item>
        </div>
      </div>
    </div>
    <mw-prompt :show="showApprove"
               :okFunction="approveProcess"
               v-on:hidePop="showApprove = false"
               title="核算单审批"
               placeholder="请输入审批意见"
               v-model="approve.suggestion"
               okText="同意"
               cancelText="不同意"
               :cancelFunction="approveProcess"
               promptType="textarea"
    ></mw-prompt>
    <sidebar-check :showFilter="showFilter" dicType="material" :chosen="choose" v-on:hideMask="showFilter = false"></sidebar-check>
    <!--如果action=check 且审批状态不等于step20和step30的时候-->
    <!--<div class="mw-page-footer"-->
         <!--v-if="action === 'check'&&(compute.approve_step!=='step20'&&compute.approve_step!=='step30')">-->
      <!--<div class="btns">-->
        <!--<div class="btn" v-if="(compute.approve_step==='step11'||compute.approve_step==='step10')" @click="submit">-->
          <!--提交审批-->
        <!--</div>-->
        <!--<div class="btn" v-show="compute.approve_step==='step11'||compute.approve_step==='step0'" @click="checkApprove">-->
          <!--查看审批意见-->
        <!--</div>-->
      <!--</div>-->
    <!--</div>-->
    <div class="mw-page-footer" v-if="action === 'new'||action === 'edit'||action === 'associate'">
      <div class="btns">
        <div class="btn" @click="saveData()">
          保存
        </div>
      </div>
    </div>
    <div class="mw-page-footer" v-else-if="action==='approve'">
      <div class="btns">
        <div class="btn" @click="submit">
          审批
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
  import{rawNatureList} from '@/api/dic'
  import  cstItem  from '@/components/cstItem'
  import {detail, save, submitApprove, associate} from '@/api/customerOrder/compute'
  import sess from '@/utils/sess'
  import timePicker from "@/components/timePicker"
  import SidebarCheck from "../../../../components/sidebarCheck";
  import MwPrompt from "../../../../components/mwPrompt";
  import { mapGetters } from 'vuex'
  export default{
    name: 'computeDetail',
    components: {
      MwPrompt,
      SidebarCheck,
      cstItem,
      timePicker
    },
    data(){
      return {
        selectType:'',
        showPicker:false,
        pickerType:'radio',
        pickerValue:'',
        pickerList:[],
        types: [//核算单类型
          {name: '冲压模', code: '1'},
          {name: '压铸模', code: '2'},
          {name: '注塑模', code: '3'},
        ],
        NatureList:[//材料种类

        ],
        action: '',
        tabs: ['头信息', '明细列表'],
        tabIndex: 0,
        showFilter: false,
        compute: {
          compute_id: '',
          compute_no: '',
          approval_flag: '',
          created_date: '',
          cst_company_name: '',
          cst_mold_id: '',
          cst_mold_type: '',
          type: '',
          type_name: '请选择',
          delivery_cost: '',
          design_cost: '',
          cst_products:'',
          manage_cost: '',
          manage_ratio:'0%',
          prod_info:'',
          material: '请选择',
          // material_id:'',
          mold_cost: '',
          mold_up: '',
          post_box_cost: '',
          process_cost: '',
          profit_cost: '',
          profit_ratio:'0%',
          stuff_cost: '',
          try_mold_cost: '',
          valid_date: ''
        },
        materialList: [],
        compute_id: '',
        showApprove:false,  //弹出审批框
        approve:{
          suggestion:'',
          approve_step:''
        }
      }
    },
    computed:{
      ...mapGetters(['userinfo'])
    },
    mounted(){
      this.rawNatureList()
      this.action = this.$route.params.action
      if (this.action === 'new') {
        //如果是新增，客户信息及模具类型会从上一个面页带过来
        const obj = this.$route.query
        console.log(obj)
        // this.compute.type_name = obj.type.name
        // this.compute.type = obj.type.code
      } else if (this.action === 'check' || this.action === 'approve') {
        this.compute_id = this.$route.params.compute_id
        this.compute.compute_id = this.$route.params.compute_id
        this.getData();
      } else if (this.action === 'associate') {
        const reqData = {
          compute_id: this.$route.params.compute_id
        }
        associate(reqData).then(res => {
          console.log(res)
          this.action = 'edit'
          this.compute_id = this.compute.compute_id = res.compute_info.compute_id
          this.compute = res.compute_info
        }).catch(error => {
          $toast.show(error.description)
        })
      }else if(this.action === 'action'){
        this.compute_id = this.$route.params.compute_id
        this.getData();
        this.action = 'edit'
      }
    },
    activated(){
      const prods = sess.get('prods');
      if (prods && prods != '') {
        this.compute.cst_products = JSON.parse(prods)
        sess.remove('prods')
        return
      }
      if(this.compute.compute_id){
        this.getData()
      }
    },
    methods: {

      rawNatureList(){
        rawNatureList({}).then(res=>{
          this.NatureList=res.raw_nature_list
        }).catch(error=>{
          console.log(error.description)
        })
      },
      onPicker(selectType){
        this.selectType=selectType
        this.pickerList=[]
        if(this.selectType==='isType'){
          this.pickerValue={
            id:this.compute.type,
            val:this.compute.type_name
          }
          this.types.forEach((e,i)=>{
            this.pickerList.push({val:e.name,id:e.code})
          })
        }else if(this.selectType==='isMat'){
          if(this.compute.material_id===undefined){
            this.$set(this.compute,'material_id','')
          }
          this.pickerValue={
            id:this.compute.material_id,
            val:this.compute.material
          }
          this.NatureList.forEach((e,i)=>{
            this.pickerList.push({val:e.raw_nature,id:e.raw_nature_id})
          })
        }else if(this.selectType==='manage_ratio'){
          var  id=this.compute.manage_ratio
          if((id+'').indexOf('%')!==-1){
            id=id.split('%')[0]
          }
          this.pickerValue={
            id:Number(id),
            val:this.compute.manage_ratio
          }
          for(let i=0;i<101;i++){
            this.pickerList.push({val:i+'%',id:i})
          }
        }else if(this.selectType==='profit_ratio'){
          var  id=this.compute.profit_ratio
          if((id+'').indexOf('%')!==-1){
            id=id.split('%')[0]
          }
          this.pickerValue={
            id:Number(id),
            val:this.compute.profit_ratio
          }
          for(let i=0;i<101;i++){
            this.pickerList.push({val:i+'%',id:i})
          }
        }

        this.showPicker=true
      },
      chosen(e){
        if(this.selectType==='isType'){
          this.compute.type_name =e.val
          this.compute.type = e.id
        }else if(this.selectType==='isMat'){
          this.compute.material =e.val
          this.compute.material_id = e.id
        }else if(this.selectType==='manage_ratio'){
          this.compute.manage_ratio =e.val
          this.calculate()

        }else if(this.selectType==='profit_ratio'){
          this.compute.profit_ratio =e.val
          this.calculate()
        }
      },
      onArrowClick(){
        $router.forward('/customerOrder/compute/cst_part_list/' + this.action + '/' + this.compute_id)
      },
      onArrowClick1(){
        $router.forward('/customerOrder/compute/cst_process_list/' + this.action + '/' + this.compute_id)
      },
      changeType(type){
        if (this.action === 'check'||this.action === 'approve') {
          return
        }
        this.showFilter = true
      },
      choose(item){
        this.compute.material = item.raw_nature
        console.log(item.raw_nature)
      },
      onMenuClick(){
        if (this.action === 'check') {
          this.action = 'edit'
        } else {
          this.action = 'check'
        }
      },
      onTabClick(index){
        if(index===this.tabIndex){
          return
        }
        if (!this.compute_id) {
          this.saveData(index)
          return
        }
        this.tabIndex = index
      },
      getData(){
        const that = this;
        const requestData = {
          compute_id: that.compute_id
        }
        detail(requestData).then(res => {
          that.compute = res
          if( sess.get('stuff_cost')!==null){
            that.compute.stuff_cost=sess.get('stuff_cost')
            sess.remove('stuff_cost')
            this.saveData()
          }
          if( sess.get('process_cost')!==null){
            that.compute.process_cost=sess.get('process_cost')
            sess.remove('process_cost')
            this.saveData()
          }
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      saveData(index){
        if (this.compute.type_name==='请选择') {
          $toast.show('请先选择模具类型')
          return
        }
        if (this.compute.material==='请选择') {
          $toast.show('请先选择制品材质')
          return
        }
        console.log(this.compute.prod_info)
        if (this.compute.prod_info==='') {
          $toast.show('请先输入制品信息')
          return
        }

        const requestData = {
          compute_id: this.compute_id,
          compute_info: this.compute
        }
        save(requestData).then(res => {
          // this.action = this.action === 'new' ? 'edit' : 'check'
          this.compute_id = this.compute.compute_id = res.compute_info.compute_id
          this.compute = res.compute_info
          if(index===1){
            this.tabIndex=index
            $toast.show('头信息已保存')
            // this.action='edit'
          }else if(index==='preview'){
            // if(this.action==='new'){
            //   this.$router.forward({
            //     path:'/customerOrder/computeDetailPreview',
            //     query:{
            //       compute_id:this.compute_id,
            //       isSubmit:'isSubmit',
            //       isNew:true
            //     }
            //   })
            // }else{
            //   $router.go(-1)
            // }
          }else{
            $toast.show('保存成功',600)
            $router.go(-1)
          }
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      submit(){
        if (this.action === 'edit') {
          $toast.show('请先保存头信息')
          return
        }
        if (this.action === 'approve') {
          this.showApprove = true
//          this.$router.forward('/approve/approve/' + this.compute_id + '/1/' + this.compute.approve_step)
        } else {
          const reqData = {
            bill_id: this.compute_id,
            bill_type: '1',
            approve_flag: '1',
            approve_step: this.compute.approve_step,
            approve_man_id:this.userinfo.uid,
            approve_man_name:this.userinfo.username,
          }
          submitApprove(reqData).then(res => {
            $toast.show('提交成功')
            sess.set('approve', true)
            this.$router.go(-1)
          }).catch(error => {
            $toast.show(error.description)
            console.log(error.description)
          })
        }
      },
      checkApprove(){
        //查看审批意见
        this.$router.forward('/approve/approveSuggests/' + this.compute.compute_id + '/1')
      },
      toProdList(){
        let m_action = this.action
        if(this.compute.approve_step==='step0'){
          m_action = 'readOnly'
        }
        $router.forward({
          path:'/customerOrder/compute/cst_prod_list/'+m_action,
          query:{
            cst_prods:JSON.stringify(this.compute.cst_products),
          }
        })
      },
      approveProcess(flag){
        console.log('approveProcess')
        flag = flag === 'ok' ? 1 : 2
        const reqData = {
          approve_sugg:this.approve.suggestion,
          bill_id: this.compute_id,
          bill_type: 1,
          approve_step: this.compute.approve_step,
          approve_man_id:this.userinfo.uid,
          approve_man_name:this.userinfo.username,
          approve_flag:flag
        }
        submitApprove(reqData).then(res=>{
//          $toast.show('提交成功')
          history.go(-1)
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      calculate(){
        // manage_ratio
        let cost=( Number(this.compute.stuff_cost)+
          Number(this.compute.process_cost)+
          Number(this.compute.design_cost)+
          Number(this.compute.try_mold_cost)+
          Number(this.compute.post_box_cost)+
          Number(this.compute.delivery_cost))
        if(this.compute.manage_ratio!==null){
          let ratio=Number(this.compute.manage_ratio.replace("%",""))
          this.compute.manage_cost=((cost/(100-ratio))*ratio).toFixed(2)
          this.compute.mold_cost=((cost/(100-ratio))*100).toFixed(2)
        }else{
          this.compute.mold_cost= ( Number(this.compute.manage_cost)+cost).toFixed(2)
        }



        if(this.compute.profit_ratio!==null||Number(this.compute.profit_ratio.split("%")[0])!==0){
          let ratio=Number(this.compute.profit_ratio.split("%")[0])
          this.compute.profit_cost=((this.compute.mold_cost/(100-ratio))*ratio).toFixed(2)
          this.compute.mold_up=((this.compute.mold_cost/(100-ratio))*100).toFixed(2)
        }else{
          this.compute.mold_up=(this.compute.mold_cost).toFixed(2)
        }
      }

    },
    watch:{
      'compute.try_mold_cost':{
        handler:function(e,v){
          this.calculate()
        },deep:true
      },
      'compute.post_box_cost':{
        handler:function(e,v){
          this.calculate()
        },deep:true
      },
      'compute.design_cost':{
        handler:function(e,v){
          this.calculate()
        },deep:true
      },
      'compute.delivery_cost':{
        handler:function(e,v){
          this.calculate()
        },deep:true
      },
      'compute.post_box_cost':{
        handler:function(e,v){
          this.calculate()
        },deep:true
      }
    }
  }
</script>
<style lang="scss" scoped>
  .page-content {
    height: 100%;
    width: 100%;
    position: absolute;
    textarea {
      width: 100%;
      resize: none;
      border: 1px solid #ddd;
      padding: 5px;
      line-height: 16px;
      height: 55px;
      margin-top: 5px;
      &:disabled {
        background: #fff;
      }
    }
  }
</style>

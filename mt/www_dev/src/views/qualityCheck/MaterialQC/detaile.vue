<template>
    <div class="mw-page page " >
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>来料质检单</span>
            <i class="right" v-show="action==='new'||step==='step'||step==='step0'" ></i>
            <i class="right" v-show="action==='action'" @click="getDetaile()">取消</i>
            <i class="right" v-show="action==='check'&&step!=='step'&&step!=='step0'" @click="action='action'">编辑</i>
        </div>
        <div class="mw-page-content"  >
          <div class="header">
            <cst-item v-show=" action!=='new' "  label="质检单号：" v-model="purch_qc_no " editType="text"></cst-item>
            <cst-item label="采购单号：" v-model="purch_qc_info.purch_no " editType="text"></cst-item>
            <cst-item   label="供应商：" v-model="purch_qc_info.sup_name"   editType="text"></cst-item>
            <item style="margin-bottom: 0px;" v-if="purch_qc_info.purch_type==1">
            采购类型：<span style="float: right">物料采购</span></item>
            <item style="margin-bottom: 0px;" v-else="purch_qc_info.purch_type==2">
            采购类型：<span style="float: right">外协采购</span></item>
            <cst-item   label="质检日期：" v-model="purch_qc_info.qc_date"   editType="text"></cst-item>
            <cst-item   label="质检员：" v-model="purch_qc_info.inspector"   editType="text"></cst-item>
          </div>
          <div class="matList" >
            <item v-for="(item,index) in purch_qc_info.purch_qc_detail_list" class="single">
              <div class="mat_info">
                <span >{{ item.mat_desc }}</span>
                <p>规格型号：{{ item.mat_model }}</p>
                <p>采购数量：{{ item.quantity }}</p>
                <p>待收数量：{{ item.no_qc_qty }}</p>
                <div v-show="action==='new'" class="toggle" :class="item.qcStart?'active':''"  @click="btnToggle(item)">
                  <div class="ok">
                    <div class="round"></div>
                  </div>
                  <div class="ng"></div>
                </div>
                <div v-show="action==='action'" class="ion-minus-circled" @click="deleteItem(index)"></div>
              </div>
              <div class="mat_detaile" :class="(item.qcStart||action!=='new')?'active':''">
                <div class="input" :class="action!=='check'?'active':''"><span>质检数量:</span><input :disabled="action==='check'" @keyup="qualityCheck(item)" v-model="item.qc_qty" placeholder="请输入质检数量" type="number"></div>
                <div class="input" :class="action!=='check'?'active':''"><span>合格数量:</span> <input :disabled="action==='check'" @keyup="qualified(item)" v-model="item.qualified_qty" placeholder="请输入合格数量" type="number"></div>
                <div class="input" :class="action!=='check'?'active':''"><span>特采数量:</span> <input :disabled="action==='check'" @keyup="special(item)" v-model="item.special_qty" placeholder="请输入特采数量" type="number"></div>
                <div><span>不合格数量:</span> <input disabled v-model="item.no_qualified_qty" placeholder="0" type="number"></div>
                <div>
                  <span>异常原因：</span>
                  <textarea v-model="item.unusual_reason" placeholder="请输入异常原因"></textarea>
                </div>
              </div>
            </item>
          </div>
        </div>
      <div class="mw-page-footer" v-show="step!=='step0'">
        <div class="btns">
          <div class="btn" @click="modify" v-show="action!=='check'&&step!=='step'">保存</div>
          <div class="btn" @click="Submit('1','step10')" v-show="step!=='step30'&&step!=='step'" >提交审批</div>
          <div class="btn" @click="Record" v-show="step!=='step'&&step!=='step10'" >查看审批流程</div>
          <div class="btn" @click="showApprove=true" v-show="step==='step'&&action!=='new'">审批</div>
        </div>
      </div>


      <mw-prompt :show="showApprove"
                 :okFunction="Process"
                 v-on:hidePop="showApprove = false"
                 title="核算单审批"
                 placeholder="请输入审批意见"
                 v-model="suggestion"
                 okText="同意"
                 cancelText="不同意"
                 :cancelFunction="cancelProcess"
                 promptType="textarea"
      ></mw-prompt>


    </div>
</template>

<script>
  import CstItem from "../../../components/cstItem"
  import MwPrompt from "../../../components/mwPrompt";
  import sess from '@/utils/sess'
  import {todayDate} from "@/utils"
  import { mapGetters } from 'vuex'
  import { mat_qc_modify,purch_qc_detaile,purch_qc_process } from '@/api/MaterialQC'
    export default{
      name:'qcDetaile',
      components: { CstItem,MwPrompt },
      data(){
        return {
          suggestion:'',
          showApprove:false,
          timer: null,
          purch_qc_no:'',
          sup_name:'请选择',//单号类型
          action:'',
          step:'',
          QC_info:'',
          purch_id:'',
          purch_qc_info:{		//purch_qc_info.purch_qc_no
            approve_step	:'',//		审批环节	string
            approve_sugg	:'',//审批意见	string
            inspector:'',//	质检员	string
            purch_id:'',//	采购单id	number
            purch_no:'',//	采购单号	string
            purch_qc_id:'',//	质检id	number
            purch_qc_no:'',//	质检单号	string
            purch_type:'1',//	采购类型	number	1.物料采购 2.外协采购
            qc_date	:todayDate(),//质检日期	string
            sup_company_id:'',//	供应商Id	number
            sup_name:'',//	供应商名称	string
            purch_qc_detail_list:[//		array<object>	@mock=1
              // {
              //   mat_desc: '',//	物料名称	string
              //   mat_id: '',//	物料id	number
              //   mat_model: '',//	规格型号	string
              //   mat_no: '',//	物料编码	string
              //   mat_type_id: '',//	物料类型id	number
              //   mat_type_name: '',//	物料类型名称	string
              //   no_qc_qty: '',//	剩余待检数量	number
              //   no_qualified_qty: '',//	不合格数	string
              //   purch_qc_detail_id: '',//	质检明细id	number	没有则新增，有则修改
              //   purch_sub_id: '',//	采购明细Id	number
              //   qc_bill_qty: '',//	已建质检数量	number
              //   qc_qty: '',//	质检数量	number
              //   qced_qty: '',//	已质检数量	number
              //   qualified_qty: '',//	合格数	number
              //   quantity: '',//	采购数量	number
              //   special_qty: '',//	特采数	number
              //   unusual_reason: ''//	异常原因	string
              // }
            ]
          }
      }
      },
      computed:mapGetters([
        'chosenCompany',
        'userinfo'
      ]),
      activated(){ },
      mounted(){
        let param=this.$route.query
        this.action=param.action
        this.step=param.step
        if(this.action==='new'){
          this.QC_info=sess.get('QC_info')
          this.purch_qc_info.purch_no=this.QC_info.purch_no
          sess.remove('QC_info')
          this.purch_qc_info.sup_name=this.QC_info.sup_name
          this.purch_qc_info.inspector=this.userinfo.fullname
          this.purch_qc_info.purch_type=this.QC_info.purch_type
          if(this.QC_info.purch_detail_list.length>0){
            this.QC_info.purch_detail_list.forEach((e,i)=>{
              if(e.is_qc==='1'){
                this.purch_qc_info.purch_qc_detail_list.push({
                  mat_desc: e.mat_desc,//	物料名称	string
                  mat_id: e.mat_id,//	物料id	number
                  mat_model: e.mat_model,//	规格型号	string
                  mat_no: e.mat_no,//	物料编码	string
                  mat_type_id: e.mat_type_id,//	物料类型id	number
                  mat_type_name: e.mat_type_name,//	物料类型名称	string
                  no_qc_qty: e.no_qc_qty,//	剩余待检数量	number
                  no_qualified_qty: '',//	不合格数	string
                  purch_qc_detail_id: '',//	质检明细id	number	没有则新增，有则修改
                  purch_sub_id: e.purch_sub_id,//	采购明细Id	number
                  qc_bill_qty: e.qc_bill_qty,//	已建质检数量	number
                  qc_qty: e.no_qc_qty,//	质检数量	number
                  qced_qty: e.qced_qty,//	已质检数量	number
                  qualified_qty: '',//	合格数	number
                  quantity: e.quantity,//	采购数量	number
                  special_qty: '',//	特采数	number
                  unusual_reason: ''//	异常原因	string
                })
              }

            })
          }
        }else if(this.action==='check'){
          this.purch_qc_no=param.purch_qc_no
          this.getDetaile()
        }

      },
      methods: {
        Process(){
          this.Submit('1',this.purch_qc_info.approve_step)
        },
        cancelProcess(){
          this.Submit('2',this.purch_qc_info.approve_step)
        },
        qualityCheck(item){
          if(item.qc_qty!==''){
            item.qc_qty=Number(item.qc_qty)
          }
          clearTimeout(this.timer)
          this.timer=setTimeout(()=>{
            if(item.qc_qty<0){
              item.qc_qty=-item.qc_qty
            }
            if(item.qc_qty>item.no_qc_qty){
              item.qc_qty=item.no_qc_qty
            }
            this.noQualified(item)
          },400)
        },
        qualified(item){
          if(item.qualified_qty!==''){
            item.qualified_qty=Number(item.qualified_qty)
          }
          clearTimeout(this.timer)
          this.timer=setTimeout(()=>{
            if(item.qualified_qty<0){
              item.qualified_qty=-item.qualified_qty
            }
            if(item.qualified_qty>item.qc_qty){
              item.qualified_qty=item.qc_qty
            }
            this.noQualified(item)
          },400)
        },
        special(item){
          if(item.special_qty!==''){
            item.special_qty=Number(item.special_qty)
          }
          clearTimeout(this.timer)
          this.timer=setTimeout(()=>{
            if(item.special_qty<0){
              item.special_qty=-item.special_qty
            }
            if(item.special_qty>item.no_qualified_qty){
              item.special_qty=item.no_qualified_qty
            }
          },400)
        },
        noQualified(item){
          item.no_qualified_qty=Number(item.qc_qty)-Number(item.qualified_qty)
        },
        modify(){
          let reqData=''
          if(this.action==='new'){
            let purch_qc_detail_list=[]
            let item=''
            for(let i=0;i<this.purch_qc_info.purch_qc_detail_list.length;i++ ){
              let e=this.purch_qc_info.purch_qc_detail_list[i]
              if(e.qcStart){
                purch_qc_detail_list.push(e)
                if(Number(e.no_qualified_qty)<1){
                  item=e
                  break
                }
              }
            }
            if(item!==''){
              $toast.show(item.mat_desc+'数量输入有误',700)
              return
            }
            console.log(purch_qc_detail_list)
            if(purch_qc_detail_list.length<1){
              $toast.show('请选择质检项',700)
              return
            }
            let purch_qc_info=this.purch_qc_info
            purch_qc_info.purch_qc_detail_list=purch_qc_detail_list
            reqData = {
              purch_qc_info:purch_qc_info
            }
          }else if(this.action==='action'){
            reqData = {
              purch_qc_info:this.purch_qc_info
            }
          }
          mat_qc_modify(reqData).then(res=>{

            if(this.step==='step30'){
              Submit('1','step10')
            }
            if(this.action==='new'){
              this.purch_qc_no=res.purch_qc_no
            }
              this.action='check'
              this.getDetaile()
              $toast.show('质检单保存成功')


          }).catch(error=>{
            $toast.show(error.description)
          })
        },
        btnToggle(item){
          if(item.qcStart){
            this.$delete(item,'qcStart')
          }else{
            this.$set(item,'qcStart',true)
          }
        },
        getDetaile(){
          const reqData = {
            query:{
              purch_qc_no:this.purch_qc_no
            }
          }
          purch_qc_detaile(reqData).then(res=>{
            this.purch_qc_info=res.purch_qc_info
            this.purch_qc_info.purch_qc_detail_list.forEach((e,i)=>{
              console.log(e.qualified_qty )
              if(e.qualified_qty ===null){
                this.purch_qc_info.purch_qc_detail_list[i].qualified_qty=0
              }
              if(e.special_qty===null){
                this.purch_qc_info.purch_qc_detail_list[i].special_qty=0
              }
            })
            this.action='check'
          }).catch(error=>{
            $toast.show(error.description)
          })
        },
        Record(){//查看审批流程
          this.$router.forward({
            path:'workflowOpinion',
            query:{
              bill_id:this.purch_qc_info.purch_qc_id,
              bill_type:'11'
            }
          })
        },
        deleteItem(index){
          if(this.purch_qc_info.purch_qc_detail_list.length>1){
            $dialog.confirm({
              theme: 'ios',
              title: '确定要删除吗？',
              cancelText: '取消',
              okText: '确定'
            }).then(res=>{
              if(res){
                this.purch_qc_info.purch_qc_detail_list.splice(index,1)
              }
            })
          }else{
            $dialog.alert({
              effect: 'ios',
              title: '提示',
              content: '该质检单至少保留一个质检项目',
              okText: '确定',
              okTheme: 'assertive'
            }).then((res)=>{

            })
          }


        },
        Submit(flag,step){
          const reqData = {
              approve_flag:flag,//	审批标志	string	审批结果:0待审,1同意,2不同意'
              approve_man_id	:this.userinfo.uid,//	审批人Id	number
              approve_man_name:this.userinfo.fullname,//		审批人名称	string
              approve_step	:step,//	当前所处环节	string	step10制订中，step11退回, step20预审中，step30审批中，step0审批结束，step00不需审批
              approve_sugg:this.suggestion,//		审批意见	string
              bill_id	:this.purch_qc_info.purch_qc_id,//	单据id	string
              bill_type:'11',//
          }
          purch_qc_process(reqData).then(res=>{
            $toast.show('提交成功',600)
            this.$router.go(-1)
          }).catch(error=>{
            $toast.show(error.description)
          })
        }
      },




    }
</script>

<style lang="scss" scoped>

.mw-page-content{
  position: relative;
  &>.matList{
    .mat_info{
      position: relative;
      span{
        color: #416eb6;margin-bottom: 5px
      }
      &>p{
        margin-top: 5px;
      }
      .ion-minus-circled{
        width: 30px;
        height: 30px;
        text-align: center;
        line-height: 30px;
        box-sizing: border-box;
        position: absolute;
        right: 0px;
        top: -7px;
        font-size:18px;
        color: red;
      }
      .toggle{
        width: 50px;
        height: 20px;
        border-radius: 10px;
        overflow: hidden;
        box-sizing: border-box;
        border: 1px solid #666;
        position: absolute;
        right: 10px;
        top: 50%;
        margin-top: -10px;
        &>.ok{
          box-sizing: border-box;
          position: absolute;
          border-radius: 10px;
          left: -1px;top: -1px;
          z-index: 10;
          width: 50px;
          height: 20px;
          background: #169BD5;
          color: #fff;
          transform: translateX(-30px);
          transition:all .4s;

          .round{
            width: 20px;
            height: 20px;
            border-radius: 10px;
            background: #fff;
            position: absolute;
            top: 0;
            right: 0;
            box-sizing: border-box;
            border: 1px solid #666;
          }
        }
        &.active>.ok{
          transform: translateX(-0px);
        }
        &>.ng{
          box-sizing: border-box;
          position: absolute;
          z-index: 1;
          width: 50px;
          height: 20px;
          left: -1px;top: -1px;
        }
      }

    }
    .mat_detaile{
      width: 100%;
      height:0px;
      transition: all 0.4s;
      overflow: hidden;
      &.active{
        height: 150px;
      }
      &>div{
        width: 100%;
        height: 22px;
        position:relative;
        &.input:after{
          display: block;
          content: ' ';
          width: 100%;
          height: 1px;
          position: absolute;
          left: 0;
          bottom: 0;
          background: #ddd;
        }
        &.active:after{
          background: #416eb6;
        }
        &:last-of-type:after{
          display: none;
        }
        span{
          line-height: 22px;
        }
        input{
          width: 70%;
          height: 22px;
          line-height: 22px;
          float: right;
          text-align: right;
          &:disabled{
            background: #fff;
          }
        }
        textarea{
          width: 100%;
          height: 40px;
          background: transparent;
          resize: none;
          padding: 0;
        }
      }
    }

  }
}
</style>

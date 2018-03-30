<template>
    <div class="mw-page page " >
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>工序质检单</span>
            <i class="right" v-show="action==='new'" ></i>
            <i class="right" v-show="action==='action'" @click="getDetaile()">取消</i>
            <i class="right" v-show="action==='check'" @click="action='action'">编辑</i>
        </div>
        <div class="mw-page-content"  >
          <div class="header">
            <cst-item v-show=" action!=='new' "  label="质检单号：" v-model="proc_qc_info.pop_qc_no " editType="text"></cst-item>
            <cst-item   label="质检日期：" v-model="proc_qc_info.qc_date"   editType="text"></cst-item>
            <cst-item   label="质检员：" v-model="proc_qc_info.inspector"   editType="text"></cst-item>
          </div>
          <div class="matList" >
            <item v-for="(item,index) in proc_qc_info.prod_order_list" class="single">
              <div class="mat_info">
                <span >{{ item.mat_no }}</span>
                <p>物料名称：{{ item.mat_name }}</p>
                <p>加工部门：{{ item.pg_name }}</p>
                <p>加工者：{{ item.worker_name }}</p>
                <p>生产订单：{{ item.po_no }}</p>
                <p>确认号：{{ item.confirm_no }}</p>
                <p>完工日期：{{ item.real_end_time }}</p>
                <p>待质检数:{{ item.pc_wait_qty }}</p>
                <div v-show="action!=='check'" class="ion-minus-circled" @click="deleteItem(index)"></div>
              </div>
              <div class="mat_detaile" :class="(item.qcStart||action!=='new')?'active':''">
                <div class="input" :class="action!=='check'?'active':''"><span>质量损失(KG):</span><input :disabled="action==='check'" @keyup="qualityCheck(item)" v-model="item.quality_loss" placeholder="请输入损失重量" type="number"></div>
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
      <div class="mw-page-footer" >
        <div class="btns">
          <div class="btn" @click="modify" v-show="action!=='check'">保存</div>
          <div class="btn" @click="process" v-show="action!=='new'">提交</div>
        </div>
      </div>


    </div>
</template>

<script>
  import CstItem from "../../../components/cstItem"
  import sess from '@/utils/sess'
  import {todayDate} from "@/utils"
  import { mapGetters } from 'vuex'
  import { proc_qc_modify,proc_qc_detail,purch_qc_process } from '@/api/MaterialQC'
    export default{
      name:'ProcedureQCdetaile',
      components: { CstItem },
      data(){
        return {
          timer: null,
          pop_qc_id:'',
          sup_name:'请选择',//单号类型
          action:'',
          QC_info:'',
          purch_id:'',
          proc_qc_info:{//		object proc_qc_info.prod_order_list
            inspector:'',//	质检员	string
            pop_qc_id:'',//	工序质检单id	number	如果存在就修改，不存在就新增
            pop_qc_no:'',//	质检单号	string
            qc_date:todayDate(),//	质检日期	string
            prod_order_list:[
              {//		array<object>
              confirm_no:'',//	确认号	string
              is_submit_fault:'',//	是否提交质量事故单	string
              mat_id:'',//	物料Id	number
              mat_name:'',//	物料名称	string
              mat_no:'',//	物料编码	string
              no_qualified_qty:'',//	不合格数	number
              pg_id	:'',//加工部门Id	number
              pg_name	:'',//加工部门	string
              po_id	:'',//生产订单id	number
              po_no	:'',//生产订单号	string
              pop_id:'',//	工序id	number
              pop_qc_sub_id:'',//	工序质检单子表id	number	如果存在就修改，不存在就新增
              pop_status:'',//	工序状态	string
              proc_name:'',//	工序	string
              pc_wait_qty:'',//待质检数
              qc_qty:'',//	质检数	number
              qualified_qty:'',//	合格数	number
              quality_loss:'',//	质量损失	string
              real_end_time:'',//	完工日期	string
              scrap_qty:'',//	报废数	number
              special_qty:'',//	特采数	number
              unusual_pic:'',//	异常图片	string
              unusual_reason:'',//	异常原因	string
              worker_name:'',
            }
          ],//	责任人	string
        },
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
        if(this.action==='new'){
          this.proc_qc_info.inspector=this.userinfo.fullname
          this.proc_qc_info.qc_date=todayDate()
          this.proc_qc_info.prod_order_list=JSON.parse(sess.get('prod_order_list'))
          this.proc_qc_info.prod_order_list.forEach((e,i)=>{
               e.qc_qty=e.pc_wait_qty
            })
        }else if(this.action==='check'){
          this.pop_qc_id=param.pop_qc_id
          this.getDetaile()
        }

      },
      methods: {
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
            this.noQualified(item)
          },400)
        },
        noQualified(item){
          item.no_qualified_qty=Number(item.qc_qty)-Number(item.qualified_qty)
        },
        modify(){
          let item=''
          for(let i=0;i<this.proc_qc_info.prod_order_list.length;i++ ){
            // qualified_qty special_qty
            let e=this.proc_qc_info.prod_order_list[i]
              if(Number(e.special_qty)<1){
                e.special_qty=0
              }
              console.log(e.qualified_qty)
              if(Number(e.qualified_qty)<1){
                item=e
                break
              }
          }
          if(item!==''){
            $toast.show(item.mat_name+'数据输入有误',700)
            return
          }
          let reqData=''
          if(this.action==='new'){
            reqData = {
              proc_qc_info:this.proc_qc_info
            }
          }else if(this.action==='action'){
            reqData = {
              proc_qc_info:this.proc_qc_info
            }
          }


          proc_qc_modify(reqData).then(res=>{
            this.$router.go(-1)
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
              pop_qc_id:this.pop_qc_id
            }
          }
          proc_qc_detail(reqData).then(res=>{
            this.proc_qc_info=res.proc_qc_info
            // this.proc_qc_info.prod_order_list.forEach((e,i)=>{
            //    e.qc_qty=e.pc_wait_qty
            //   if(e.qualified_qty ===null){
            //     this.purch_qc_info.purch_qc_detail_list[i].qualified_qty=0
            //   }
            //   if(e.special_qty===null){
            //     this.purch_qc_info.purch_qc_detail_list[i].special_qty=0
            //   }
            // })
            this.action='check'
          }).catch(error=>{
            $toast.show(error.description)
          })
        },
        deleteItem(index){
          if(this.proc_qc_info.prod_order_list.length>1){
            $dialog.confirm({
              theme: 'ios',
              title: '确定要删除吗？',
              cancelText: '取消',
              okText: '确定'
            }).then(res=>{
              if(res){
                this.proc_qc_info.prod_order_list.splice(index,1)
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
        process(){
          console.log(this.userinfo)
          const reqData = {
              approve_flag:'1',//	审批标志	string	审批结果:0待审,1同意,2不同意'
              approve_man_id	:this.userinfo.uid,//	审批人Id	number
              approve_man_name:this.userinfo.fullname,//		审批人名称	string
              approve_step	:'step10',//	当前所处环节	string	step10制订中，step11退回, step20预审中，step30审批中，step0审批结束，step00不需审批
              approve_sugg:'',//		审批意见	string
              bill_id	:this.pop_qc_id,//	单据id	string
              bill_type:'13',//
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

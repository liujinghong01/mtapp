<template>
  <div class="mw-page page"   v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>客户定单详情</span>
      <i class="right"  v-if="(approve_step==='step10'||approve_step==='step11')&&tabIndex===1" @click="$router.forward({path:'/customerOrder/order/orderReferTo',query:{project_id:project.project_id}})">参考</i>
      <i class="right" v-else></i>
    </div>
    <div class="mw-page-content compute-detail has-tab">
      <tabs :tab-items="tabs" :tab-index="tabIndex" :on-tab-click="onTabClick"></tabs>
      <div style="margin-bottom: 50px;margin-top: 60px" v-show="tabIndex===0">
        <cst-item label="定单号" v-show="action!='new'" v-model="project.project_no" editType="text" :action="action" :canEdit="false"></cst-item>
        <cst-item label="客户"
                  v-model="project.cst_name"
                  editType="text"
                  :action="action"
                  :hasArrow="true"
                  @click.native="changeType('customer')"
                  :canEdit="false" ></cst-item>
        <cst-item  v-if="action==='check'||action==='approve'" label="定单接受日期"  v-model="project.order_accept_date" editType="text" :action="action" :canEdit="false"> </cst-item>
        <datepicker style="padding-left: 15px" v-else v-model="project.order_accept_date" label="定单接受日期" date-format="yyyy-mm-dd"></datepicker>

        <!--<cst-item label="交货方式"  v-model="project.delivery_type" editType="text" :action="action" > </cst-item>-->
        <cst-item label="交货地点"  v-model="project.delivery_place" editType="text" :action="action" > </cst-item>
        <!--<cst-item label="是否含税"  v-model="project.is_include_tax" editType="text" :action="action" :canEdit="false" ></cst-item>-->
        <item style="padding-left: 15px">
          是否含税:
          <label>
            <input type="radio" name="is_include_tax" value="1" v-model="project.is_include_tax">是
          </label>
          <label>
            <input type="radio" name="is_include_tax" value="0" v-model="project.is_include_tax">否
          </label>
        </item>
        <cst-item label="税率(%)" v-show="project.is_include_tax==='1'"  v-model="project.tax_ratio" editType="number" :action="action" > </cst-item>
        <!--<cst-item label="发票类型"  v-model="project.invoice_type" editType="text" :action="action" > </cst-item>-->

        <cst-item label="发票类型"
                  v-model="project.invoice_type_name"
                  editType="text"
                  :action="action"
                  :hasArrow="true"
                  @click.native="changeType('invoiceType')"
                  :canEdit="false" ></cst-item>

        <cst-item label="业务员"
                  v-model="project.salesman_name"
                  editType="text"
                  :action="action"
                  :hasArrow="true"
                  @click.native="changeType('salesman')"
                  :canEdit="false" ></cst-item>
        <cst-item label="币种"  v-model="project.coin_name"
                  editType="text"
                  :action="action"
                  :hasArrow="true"
                  @click.native="changeType('coin')"
                  :canEdit="false"  ></cst-item>
      </div>
      <div style="margin-bottom: 50px;margin-top: 60px" v-show="tabIndex===1">
        <swipe-item
          v-show="orderLines.length>0"
          swipeItemText="删除"
          :canSwipe="true"
          v-on:SwipeItemClick="delItem"
          v-on:ItemClick="clickLine"
          v-on:SwipeLeft="onSwipeLeft"
          v-for="item in orderLines"
          :hasArrow="true"
          :obj="item">
          <div>
            <span class="label">产品名称:</span><span style="padding-left: 5px;color: #416eb6">{{item.cst_prod_name}}</span>
          </div>
        </swipe-item>
        <button v-show="approve_step==='step10'||approve_step==='step11'" @click="$router.forward('/customerOrder/order/lineInfo/new/'+project.project_id)" class="button button-calm button-block button-outline">添加产品</button>

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
    <sidebar-check :showFilter="showFilter" :dicType="dicType" :chosen="chosen" v-on:hideMask="showFilter=false"></sidebar-check>
    <div class="mw-pgae-footer">
      <div class="btns"  >
        <!--<div class="btn" @click="$router.forward('/customerOrder/quote/quoteHistory/associate/'+project.project_id)">-->
        <!--关联报价单-->
        <!--</div>-->
        <div class="btn"  @click="saveData(false)">保存</div>
      </div>
      <!--<div class="btns" v-show="action==='approve'" >-->
        <!--<div class="btn" @click="submit">审批</div>-->
      <!--</div>-->
      <!--<div class="btns" v-show="action==='check'&&(approve_step==='step10'||approve_step==='step11')" >-->
        <!--<div class="btn" @click="submit">提交审批</div>-->
      <!--</div>-->
      <!--<div class="btns" v-show="action==='check'&&(approve_step==='step0'||approve_step==='step11')" >-->
        <!--<div class="btn" @click="checkApprove">查看审批意见</div>-->
      <!--</div>-->
    </div>
  </div>
</template>
<script>
  import  cstItem  from '@/components/cstItem'
  import {  save ,headDetail,queryLineList,deleteLine} from '@/api/customerOrder/order'
  import {submitApprove} from '@/api/approve'
  import sess from '@/utils/sess'
  import {mapGetters} from 'vuex'
  import { todayDate } from '@/utils'
  import SwipeItem from "../../../components/swipeItem";
  import SidebarCheck from "../../../components/sidebarCheck";
  import MwPrompt from "../../../components/mwPrompt";
  export default{
    name:'orderDetail',
    components:{
      MwPrompt,
      SidebarCheck,
      SwipeItem,
      cstItem
    },
    data(){
      return{
        action:'edit',
        isSubmit:false,
        approve_step:'step10',
        tabs:['头信息','产品列表'],
        tabIndex:0,
        project:{
          coin:'',
          coin_name:'请选择',
          cst_company_id:'',
          cst_name:'请选择',
          delivery_place:'',
          delivery_type:'',
          invoice_type:'',
          invoice_type_name:'请选择',
          is_include_tax:'',
          order_accept_date:todayDate(),
          project_no:'',
          project_id:'',
          remark:'',
          salesman:'',
          salesman_name:'请选择',
          tax_ratio:'',
        },
        orderLines:[

        ],

        project_id:'',
        dicType:'',
        showFilter:false,
        showApprove:false,
        approve:{
          suggestion:''
        }
      }
    },
    computed:{
      ...mapGetters(['userinfo']),

    },
    created(){
      if(this.$route.params.action==='new'){
        this.action='new'
      }else{
        this.project.project_id = this.project_id = this.$route.params.project_id
        this.getData()
        this.getOrderLines()
      }
    },
    activated(){
      if(this.tabIndex == 1){
        this.getOrderLines()
      }
    },
    methods:{
      checkApprove(){
        //查看审批意见
        this.$router.forward('/approve/approveSuggests/' + this.project_id + '/3')
      },
      onSwipeLeft(){},
      changeType(type){
        if(this.action==='check'||this.action==='approve'){
          return
        }
        this.dicType = type
        this.showFilter = true
      },
      chosen(item){
        console.log(item)
        switch (this.dicType){
          case 'salesman':{
            this.project.salesman_name = item.work_name;
            this.project.salesman = item.uid;
            break
          }
          case 'customer':{
            this.project.cst_name = item.cst_name
            this.project.cst_company_id = item.cst_company_id
            this.project.delivery_place = item.cst_company_address
            break
          }
          case 'invoiceType':{
            this.project.invoice_type = item.code;
            this.project.invoice_type_name = item.name;
            break
          }
          case 'coin':{
            this.project.coin_name = item.name;
            this.project.coin = item.code;
            break
          }
        }
      },
      onTabClick(index){
        console.log('onTabClick')
        const that = this;
        if(index===this.tabIndex){
          return
        }
        if(!this.project_id){
          this.saveData(true)
          return
        }
//        switch (index){
//          case 1:{
//            that.getOrderLines();
//            break
//          }
//        }
        this.tabIndex = index
      },
      getData(){
        const that = this;
        const requestData = {
          project_id:that.project_id
        }
        headDetail(requestData).then(res=>{
          that.project = res.project_head
          this.approve_step=res.project_head.approve_step
        }).catch(error=>{
          $toast.show(error.description)
        })
      },

      //在不同的tab时 保存调用的接口 也不一样
      saveData(isBack){
        if(this.project.cst_name==='请选择'){
          $toast.show('请选择客户',600)
          return
        }
        if(this.project.delivery_place===''){
          $toast.show('请输入交货地点',600)
          return
        }
        if(this.project.is_include_tax===''){
          $toast.show('请选择是否含税',600)
          return
        }
        if(this.project.invoice_type_name==='请选择'){
          $toast.show('请选择发票类型',600)
          return
        }
        if(this.project.salesman_name==='请选择'){
          $toast.show('请选择业务员',600)
          return
        }
        if(this.project.coin_name==='请选择'){
          $toast.show('请选择币种',600)
          return
        }
        const requestData = {
          cst_order_head:this.project
        }
        save(requestData).then(res=>{
          console.log(res)
          this.project = res.project_head
          this.project_id = res.project_head.project_id
          this.action = 'check'

          if(!isBack){
            $toast.show('保存成功',600)
            $router.go(-1)

          }else{
            this.tabIndex=1
            $toast.show('头信息保存',600)
          }

        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      getOrderLines(){
        const reqData={
          project_id:this.project_id
        }
        queryLineList(reqData).then(res=>{
          this.orderLines = res.order_line_list;
          if(res.order_line_list.length>0){
            this.isSubmit=true
          }
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      submit(){
        if(this.action==='edit'){
          $toast.show('请先保存头信息')
          return
        }
        if(!this.isSubmit){
          $toast.show('请先添加产品信息')
          return
        }
        if(this.action==='approve'){
//          this.$router.forward('/approve/approve/'+this.project.project_id+'/3/'+this.project.approve_step)
          this.showApprove = true
        }else{
          const reqData={
            bill_id:this.project.project_id,
            bill_type:'3',
            approve_flag:'1',
            approve_step:this.project.approve_step,
            approve_man_id:this.userinfo.uid,
            approve_man_name:this.userinfo.username,
          }
          submitApprove(reqData).then(res=>{
            $toast.show('提交审批成功')
            this.$router.go(-1)
          }).catch(error=>{
            $toast.show(error.description)
            console.log(error.description)
          })
        }
      },
      clickLine(item){
        sess.set('projectNo', this.project.project_no)
        this.$router.forward('/customerOrder/order/lineInfo/'+this.action+'/'+item.order_line_id)
      },
      delItem(item){
        const that = this
        $dialog.confirm({
          theme:'ios',
          // 标题
          title: '确定要删除'+item.type_name+'吗?',
          // 取消按钮文本
          cancelText: '取消',
          // 确定按钮文本
          okText: '确定'
        }).then((res) => {
          console.log('confirm result: ', res)
          if(res){
            const reqData = {
              order_line_id:item.order_line_id
            }
            deleteLine(reqData).then(res=>{
              $toast.show('删除成功',200)
              this.getOrderLines()
            }).catch(error=>{
              $toast.show(error.description)
            })
          }
        })
      },
      approveProcess(flag){
        console.log('approveProcess')
        flag = flag === 'ok' ? 1 : 2
        const reqData = {
          approve_sugg:this.approve.suggestion,
          bill_id: this.project_id,
          bill_type: 3,
          approve_step: this.project.approve_step,
          approve_man_id:this.userinfo.uid,
          approve_man_name:this.userinfo.username,
          approve_flag:flag
        }
        submitApprove(reqData).then(res=>{
         $toast.show('审批成功')
          history.go(-1)
        }).catch(error=>{
          $toast.show(error.description)
        })
      }
    },
  }
</script>
<style lang="scss" scoped>

</style>

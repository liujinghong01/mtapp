<template>
    <div class="mw-page page purchase" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>采购单详情</span>
            <i class="right" @click="action='edit'" v-show="action==='check'&&purchase.approve_price_step!=='step0'">编辑</i>
            <i class="right" @click="action='check'" v-show="action==='edit'">取消</i>
            <i class="right" v-show="action==='new' || action === 'approve'||purchase.approve_price_step==='step0'"></i>
        </div>
        <div class="mw-page-content">
          <cst-item label="采购单编号" v-model="purchase.purch_no" v-show="purchase.purch_no"></cst-item>
          <div class="item-divider" style="padding-left: 15px">供应商信息</div>
          <cst-item label="供应商" v-model="purchase.sup_name" :hasArrow="true" :action="action" :canEdit="false" @click.native="chooseDic('supplier')"></cst-item>
          <cst-item label="联系人" v-model="purchase.sup_linkman_name" :hasArrow="false" :action="action" :canEdit="false"></cst-item>
          <cst-item label="电话" v-model="purchase.sup_linkman_phone" :hasArrow="false" :action="action" :canEdit="false"></cst-item>
          <cst-item label="Email" v-model="purchase.sup_linkman_email" :hasArrow="false" :action="action" :canEdit="false"></cst-item>
          <div class="item-divider" style="padding-left: 15px">采购员信息</div>
          <cst-item label="采购员" v-model="purchase.purch_man_name" :action="action" :canEdit="false"></cst-item>
          <cst-item label="电话" v-model="purchase.purch_man_phone" :hasArrow="false" :action="action" :canEdit="false"></cst-item>
          <cst-item label="Email" v-model="purchase.purch_man_email" :hasArrow="false" :action="action" :canEdit="false"></cst-item>
          <div class="item-divider" style="padding-left: 15px">定单信息</div>
          <cst-item label="下单日期" v-model="purchase.order_date" :hasArrow="false" action="check" :canEdit="false"></cst-item>
          <!--<datepicker v-else v-model="purchase.order_date" label="请购时间" date-format="yyyy-mm-dd"></datepicker>-->
          <cst-item v-if="action==='check'" label="期望交货日期" v-model="purchase.delivery_date" :hasArrow="false" :action="action" :canEdit="false"></cst-item>
          <datepicker v-else  v-model="purchase.delivery_date" label="期望交货日期" date-format="yyyy-mm-dd"></datepicker>
          <cst-item label="交货地点" v-model="purchase.delivery_place" :action="action"></cst-item>
          <cst-item label="币种" v-model="purchase.coin_name" :hasArrow="true" :action="action" :canEdit="false" @click.native="chooseDic('coin')"></cst-item>
          <cst-item label="发票种类" v-model="purchase.invoice_type_name" :hasArrow="true" :action="action" :canEdit="false" @click.native="chooseDic('invoiceType')"></cst-item>
          <cst-item label="税率" v-model="purchase.tax_ratio" :action="action" :canEdit="true" editType="number"></cst-item>
          <cst-item label="折扣" v-model="purchase.rebate" :action="action" :canEdit="true" editType="number"></cst-item>
          <div v-for="item in purchase.purch_detail_list" style="margin-top: 8px">
            <swipe-item swipeItemText="删除" :obj="item" v-on:ItemClick="onItemClick" v-on:SwipeItemClick="delItem(item)">
              物料描述
              <span class="item-note normal">{{item.mat_desc}} <i :class="['left icon',!item.col? 'ion-ios-arrow-down':'ion-ios-arrow-up']"></i></span>
            </swipe-item>
            <div :class="['col-content',!item.col?'coll':'']">
              <cst-item label="物料编号" v-model="item.mat_no" action="check" style="overflow-x: scroll"></cst-item>
              <cst-item label="单位" v-model="item.unit" action="check"></cst-item>
              <cst-item label="单价" v-model="item.plan_price" action="edit"></cst-item>
              <cst-item label="数量" v-model="item.plan_qty" action="edit"></cst-item>
              <cst-item label="加工费/件" v-model="item.process_up" action="edit"></cst-item>
              <cst-item label="加工费金额" v-model="item.process_cost" action="edit"></cst-item>
              <cst-item label="金额" v-model="item.total_price" action="edit">{{ pirce }}</cst-item>
            </div>
          </div>
          <button class="button button-calm button-block button-outline" @click="queryMats" v-show="action ==='edit' || action === 'new'">添加采购物料</button>

          <item style="margin-top: 8px">
            总金额
            <span class="item-note">{{totalPrice}}</span>
          </item>
          <textarea placeholder="请填写说明" v-model="purchase.remark">

          </textarea>
        </div>
      <div class="mask" v-show="showMats" @click="showMats = false"></div>
      <section style="display: flex;flex-direction: column" :class="['filter',showMats?'active':'']">
        <div class="side-title" v-show="showMats">
          请选择物料
        </div>
        <input type="search" style="background-color: #bbbbbb;width: 90%;margin: 0 auto;border-radius: 5px" v-model="matSearch" @input="filter">
        <div style="flex: 1">
          <mw-checkbox v-for="item in filterMatSearchList" :obj="item" :chosenItems="purchase.purch_detail_list" :hasArrow="false">{{item.mat_desc}}</mw-checkbox>
        </div>
        <div class="side-footer"  v-show="showMats">
          <div class="filter-btns">
            <div class="filter-btn" @click="showMats=false">
              确定
            </div>
          </div>
        </div>
      </section>
      <sidebar-check :showFilter="showFilter" :dicType="dicType" :chosen="chosen" v-on:hideMask="showFilter=false"></sidebar-check>
      <div class="mw-page-footer" v-if="action==='approve'">
        <div class="btns" >
          <div class="btn" @click="submitApprovePurchase(false)">
            审批
          </div>
        </div>
      </div>
      <div class="mw-page-footer" v-show="action==='new'||action==='edit'">
        <div class="btns" >
          <div class="btn" @click="createPurchase">
            提交审批
          </div>
        </div>
      </div>
      <!--<div class="mw-page-footer" v-show="action==='check'">-->
        <!--<div class="btns" >-->
          <!--<div class="btn" @click="createPurchase">-->
            <!--提交审批-->
          <!--</div>-->
        <!--</div>-->
      <!--</div>-->
      <time-picker
        v-on:chosen="chosenPicker"
        :showPicker="showPicker"
        :pickerType="pickerType"
        :pickerValue="pickerValue"
        :pickerList='pickerList'
        v-on:hideMask="showPicker=false"></time-picker>
    </div>
</template>

<script>
  import CstItem from "../../../components/cstItem";
  import SwipeItem from "../../../components/swipeItem";
  import timePicker from "../../../components/timePicker"
  import sess from '@/utils/sess'
  import SidebarCheck from "../../../components/sidebarCheck";
  import {todayDate} from '@/utils/'
  import { mapGetters } from 'vuex'
  import { purchaseModify,purchaseSubmitApprove,purchaseDetail } from '@/api/purchase/purchase'
  import { submitApprove } from '@/api/approve'
  import { queryMatBySupplier } from '@/api/dic'
  import MwCheckbox from "../../../components/mwCheckbox";
    export default{
      name:'purchaseDetail',
      components: {
        MwCheckbox,
        SidebarCheck,
        SwipeItem,
        CstItem,
        timePicker
      },
      data(){
        return {
          showPicker:false,
          pickerType:'radio',
          pickerValue:'',
          pickerList:[],
          deps:[],
          matSearch:'',
          matSearchList:[],
          filterMatSearchList:[],
          chosenMats:[],
          showMats:false,
          purchase: {
            coin: "1",
            coin_name: "请选择",
            company_id:'', //制单人公司id
            company_name:'',// 制单人公司名称
            create_type:'', //创建类型
            creator_id:'', //制单人id
            delivery_date:'', //期望交货日期
            delivery_place:'',//交货地点
            handler_id:'', //'修改人id
            invoice_type:'', //发票类型
            invoice_type_name:'请选择',//
            order_date:'',  //下单日期
            order_flag:'',//订单标记·
            purch_detail_list: [

            ],
            purch_id:'',   //采购单id
            purch_no:'',  //采购单号
            purch_man: '', //采购员
            purch_man_email: "",
            purch_man_name: "请选择",
            purch_man_phone: "",
            rebate:'',    //折扣
            remark: "",  //备注
            send_date:'', //订单发送日期
            sup_company_id: '',
            sup_name: "请选择",
            sup_linkman:'', //供应商联系人
            sup_linkman_email:'',
            sup_linkman_name:'',
            sup_linkman_phone:'',
            tax_ratio: 0.17,
            total_price: '',
            total_process_cost:'',
            total_qty:'',
            total_weight:''
          },
          action:'check',
          showFilter:false,
          dicType:'customer',
          purchase_id:''
        }
      },
      computed:{
        ...mapGetters(['userinfo','chosenCompany']),
        pirce(){
          this.purchase.purch_detail_list.forEach((e,i)=>{
            let plan_price=Number(e.plan_price)
            let process_up=Number(e.process_up)
            let plan_qty=Number(e.plan_qty)
            if(isNaN(plan_price)){
              plan_price=0
              e.plan_price=0
            }
            if(isNaN(process_up)){
              process_up=0
              e.process_up=0
            }
            if(isNaN(plan_qty)){
              plan_qty=0
              e.plan_qty=0
            }
            e.total_price=(plan_price+process_up)*plan_qty
            e.process_cost=process_up*plan_qty
          })
        },
        totalPrice(){
          let total = 0.00
          if(this.purchase.purch_detail_list.length>0){
            this.purchase.purch_detail_list.forEach(i=>{
              total+= Number(i.total_price)
            })
          }
          total = this.purchase.total_price = total.toFixed(2)
          return total
        }
      },
      created(){
        this.action = this.$route.params.action
        if(this.action==='new'){
          if(this.chosenCompany.deps.length<1){
            this.purchase.purch_man_name = this.userinfo.username
            this.purchase.purch_man_email = this.userinfo.email
            this.purchase.purch_man_phone = this.userinfo.mobile
          } else{
            //直接默认取第一个
            this.purchase.purch_man_name = this.chosenCompany.deps[0].work_name
            this.purchase.purch_man_email = this.chosenCompany.deps[0].work_email
            this.purchase.purch_man_phone = this.chosenCompany.deps[0].work_phone
          }


          this.purchase.purch_man = this.userinfo.uid
          this.purchase.company_id = this.chosenCompany.company_id
          this.purchase.company_name = this.chosenCompany.company_name
          this.purchase.order_date = todayDate()
          this.purchase.delivery_date = todayDate()
          const mats = sess.get('mats')
          this.purchase.purch_detail_list = mats?JSON.parse(mats):[]
          sess.remove('mats')
        }else{
          this.purchase_id = this.$route.params.purchase_id
          this.getDetail()
        }
      },
        methods: {
          onItemClick(item){
            this.$set(item,'col',!item.col)
          },
          chosenPicker(e){
            this.purchase.purch_man_name = this.deps[e.id].work_name
            this.purchase.purch_man_email = this.deps[e.id].work_email
            this.purchase.purch_man_phone = this.deps[e.id].work_phone
          },
          selectPurch(){
            if(this.action==='check'){
              return
            }
            this.showPicker=true
            this.pickerValue={id:'',val:''}
          },
          chosen(item){
           switch (this.dicType){
             case 'supplier':{
               this.purchase.sup_name = item.sup_name
               this.purchase.sup_company_id = item.sup_company_id
               this.purchase.sup_linkman_email = item.connect_email
               this.purchase.sup_linkman_phone = item.connect_phone
               this.purchase.sup_linkman_name = item.connect_man
               break;
             }
             case 'coin':{
               this.purchase.coin = item.code
               this.purchase.coin_name = item.name
               break;
             }
             case 'invoiceType':{
               this.purchase.invoice_type = item.code
               this.purchase.invoice_type_name = item.name
               break;
             }
           }
          },
          chooseDic(dicType){
            if (this.action==='check' || this.action === 'approve') {
              return
            }
            switch (dicType){
              case 'supplier':{
                this.dicType = 'supplier'
                this.showFilter = true
                break;
              }
              case 'coin':{
                this.dicType = 'coin'
                this.showFilter = true
                break;
              }
              case 'invoiceType':{
                this.dicType = 'invoiceType'
                this.showFilter = true
                break;
              }
            }
          },
          delItem(item){
            const that = this
            $dialog.confirm({
              theme: 'ios',
              title: '确定要删除'+item.mat_desc+'吗？',
              cancelText: '取消',
              okText: '确定'
            }).then((res) => {
              if(res){
                that.purchase.purch_detail_list = that.purchase.purch_detail_list.filter(o=>{
                    return o.mat_desc !== item.mat_desc
                })
              }
            })
          },
          getDetail(){
            const reqData = {
              query:{
                purch_id:this.purchase_id
              }
            }
            purchaseDetail(reqData).then(res=>{
              console.log(res)
              this.purchase = res.purch_info

            }).catch(error=>{
              $toast.show(error.description)
            })
          },
          queryMats(){
            if(this.purchase.sup_company_id===''){
              $toast.show('请选择供应商',600)
              return
            }
            this.showMats = true
            const reqData = {
              query:{
                sup_company_ids:[this.purchase.sup_company_id]
              }
            }
            queryMatBySupplier(reqData).then(res=>{
              console.log(res)
              this.filterMatSearchList = this.matSearchList = res.mat_list
            }).catch(error=>{
              $toast.show(error.description)
            })
          },
          createPurchase(){
            if(this.purchase.sup_name==='请选择'){
              $toast.show('请选择供应商',600)
              return
            }
            if(this.purchase.purch_man_name==="请选择"){
              $toast.show('请选择采购员信息',600)
              return
            }
            if(this.purchase.delivery_place===''){
              $toast.show('请输入交货地点',600)
              return
            }
            if(this.purchase.invoice_type_name==='请选择'){
              $toast.show('请选择币种',600)
              return
            }
            if(this.purchase.coin_name==='请选择'){
              $toast.show('请选发票类型',600)
              return
            }
            if(this.purchase.purch_detail_list.length<1){
              $toast.show('请添加采购物料',600)
              return
            }
            const reqData = {
              purch_info:this.purchase
            }
            purchaseModify(reqData).then(res=>{
              console.log(res)
              this.purchase.purch_no = res.purch_no
              this.purchase.purch_id = res.purch_id
              this.purchase_id = res.purch_id
              //保存完之后直接提交审批
              this.submitApprovePurchase(true)
              this.action = 'check'
            }).catch(error=>{
              $toast.show(error.description)
            })
          },
          submitApprovePurchase(isFirst){

            let arr = [this.purchase.approve_price_step,this.purchase.approve_grade1_step,this.purchase.approve_grade2_step]
            let type = 5
            let step = 'step10'
            for (let i= 0;i<arr.length;i++) {
              if (arr[i] !== 'step00' && arr[i]  !== 'step0') {
                step = arr[i]
                break
              }
              type ++;
            }

            const reqData = {
              approve_step:isFirst?'step10':step,
              bill_type:isFirst?5:type,
              bill_id:this.purchase_id,
              approve_flag:1,
            }
            submitApprove(reqData).then(res=>{
              $toast.show('提交成功')
              history.go(-1)
            }).catch(error=>{
              console.log(error)
              $toast.show(error.description)
            })
          },
          filter(){   //过滤供应商
            const that = this
            setTimeout(function () {
              that.filterMatSearchList = that.matSearchList.filter(i=>{
                return i.mat_desc.indexOf(that.matSearch)>-1
              })
            },500)
          },
        }
    }
</script>

<style lang="scss" scoped>

.col-content{
  height: 350px;
}
</style>

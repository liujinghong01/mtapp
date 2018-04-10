<template>

  <div class="mw-page page purchase" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span v-if="action==='new'">新建询价单</span>
      <span v-else>询价反馈单</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content">
      <div class="item-divider" style="padding-left: 15px" v-show="action==='new'">供应商信息</div>
      <item v-show="action==='new'" class="supList" style="overflow-x: scroll">
        <span v-for="(item,index) in inquiry_info.sup_list" @click="deleteSup(index)">{{ item.sup_name }} <i class="ion-minus"></i></span>
        <span @click="chooseDic('supplier')" style="color:#416eb6">添加供应商+</span>
      </item>

      <div class="item-divider" v-show="action==='new'" style="padding-left: 15px">采购员信息</div>
      <cst-item label="采购员" v-show="action==='new'" v-model="inquiry_info.purch_man_name"  :action="action" :canEdit="false"></cst-item>
      <cst-item label="电话" v-show="action==='new'" v-model="inquiry_info.purch_man_phone" :hasArrow="false" :action="action" :canEdit="false"></cst-item>
      <cst-item label="Email" v-show="action==='new'" v-model="inquiry_info.purch_man_email" :hasArrow="false" :action="action" :canEdit="false"></cst-item>
      <div class="item-divider" style="padding-left: 15px">定单信息</div>
      <cst-item v-if="action==='check'" label="报价截止日期" v-model="inquiry_info.quote_end_date" :hasArrow="false" :action="action" :canEdit="false"></cst-item>
      <datepicker  v-else v-model="inquiry_info.quote_end_date"  label="报价截止日期" date-format="yyyy-mm-dd"></datepicker>

      <cst-item v-if="action==='check'" label="期望交货日期" v-model="inquiry_info.delivery_date" :hasArrow="false" :action="action" :canEdit="false"></cst-item>
      <datepicker  v-else  v-model="inquiry_info.delivery_date" label="期望交货日期" date-format="yyyy-mm-dd"></datepicker>
      <!--<cst-item label="交货地点"  v-model="inquiry_info.delivery_place" :action="action=='new'?'action':'check'"></cst-item>-->
      <item>
        交货地址：
        <textarea :disabled="action=='new'?'action':'check'" style="width: 100%;height: 56px;padding: 3px 0;border: 1px solid #ddd;margin: 5px 0 0 0"  placeholder="请填写交货地址"     v-model="inquiry_info.delivery_place" ></textarea>
      </item>
      <!--<cst-item label="币种" v-model="inquiry_info.coin_name" :hasArrow="true" :action="action==='new'?'action':'check'" :canEdit="false" @click.native="chooseDic('coin')"></cst-item>-->
      <!--<cst-item label="发票种类" v-model="inquiry_info.invoice_type_name" :hasArrow="true" :action="action==='new'?'action':'check'" :canEdit="false" @click.native="chooseDic('invoiceType')"></cst-item>-->
      <!--<cst-item label="税率" v-model="inquiry_info.tax_ratio" :action="action==='new'?'action':'check'" :canEdit="true" editType="number"></cst-item>-->
      <!--<cst-item label="折扣" v-model="inquiry_info.rebate" :action="action" :canEdit="true" editType="number"></cst-item>-->
      <div v-for="item in inquiry_info.mat_list" style="margin-top: 8px">
        <swipe-item swipeItemText="删除" :obj="item"  v-on:ItemClick="onItemClick">
          物料名称
          <span class="item-note normal">{{item.mat_desc}} <i :class="['left icon',item.col? 'ion-ios-arrow-down':'ion-ios-arrow-up']"></i></span>

        </swipe-item>
        <div style="overflow: hidden" :class="['col-content',item.col?'coll':'']">
          <item>
            单价
            <span v-if="action==='check'" class="item-note">{{item.up}}</span>
            <input v-else placeholder="请输入单价" class="modelInput" type="number" v-model="item.up" >
          </item>
          <item>
            数量
            <input v-if="action==='new'" placeholder="请输入数量" class="modelInput" type="number" @change="quantity(item)" v-model="item.quantity" >
            <span v-else class="item-note">{{item.quantity}}</span>
          </item>

          <item>
            加工费/件
            <span v-if="action==='check'" class="item-note">{{item.process_up}}</span>
            <input v-else placeholder="请输入加工费" class="modelInput" type="number" v-model="item.process_up" >
          </item>
          <item>
            加工费金额
            <span class="item-note">{{item.process_cost}}</span>
          </item>
          <item>
            金额
            <span  class="item-note">{{item.total_price}}</span>
          </item>
        </div>
      </div>
      <item style="margin-top: 8px">
        总金额{{ totalPrice }}
        <span class="item-note">{{ inquiry_info.total_price }}</span>
      </item>
      <textarea  :disabled="action!=='new'" v-show="this.action!=='action'"  >备注：{{ inquiry_info.remark }}</textarea>
      <textarea  placeholder="请填写说明"   v-show="this.action==='action'"  v-model="inquiry_info.remark" ></textarea>
    </div>
    <sidebar-check :showFilter="showFilter" :dicType="dicType" :chosen="chosen" v-on:hideMask="showFilter=false"></sidebar-check>
    <div class="mw-page-footer" v-show="action=='action'">
      <div class="btns">
        <div class="btn" @click="submitFeedback()">
          提交反馈
        </div>
      </div>
    </div>
    <div class="mw-page-footer" v-show="action=='new'">
      <div class="btns">
        <div class="btn" @click="newInquiry">
          提交询价单
        </div>
      </div>
    </div>

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
  import  sess from '@/utils/sess'
  import {todayDate} from '@/utils'
  import {mapGetters} from 'vuex'
  import SidebarCheck from "../../../components/sidebarCheck";
  import { supInquiryFeedbackDetail,submitFeddback,sendInquiry,getCompany } from '@/api/purchase/inquiry';
  export default{
    name:'inquiryFeedback',
    components: {
      timePicker,
      SidebarCheck,
      SwipeItem,
      CstItem},
    data(){
      return {
        action:'check',
        dicType:'coin',
        showFilter:false,
        feedback_id:1,
        deps:[],
        showPicker:false,
        pickerType:'radio',
        pickerValue:'',
        pickerList:[],
        inquiry_info: {//inquiry_info.mat_list
          //接口未返回‘折扣’字段
          "coin": "1",
          "coin_name": "请选择",
          "deal_flag": 0,
          "delivery_date": todayDate(),
          "delivery_place": "",
          "inquiry_date":'',
          "inquiry_id": '',
          "invoice_type": 1,
          "invoice_type_name": '请选择',
          "rebate":1,
          "mat_list": [
          ],
          "purch_man": 47441,
          "purch_man_email": "",
          "purch_man_name": "请选择",
          "purch_man_phone": "",
          "quote_date": todayDate(),
          "quote_end_date": todayDate(),
          "remark": "",
          "sup_list": [

          ],
          "tax_ratio": 0,
          "total_price": 0
        }

      }

    },
    mounted() {
      this.action = this.$route.params.action
      if(this.action==='new'){
        this.getCompanyInfo()
        if(this.chosenCompany.deps.length<1){
          this.inquiry_info.purch_man_name = this.userinfo.username
          this.inquiry_info.purch_man_email = this.userinfo.email
          this.inquiry_info.purch_man_phone = this.userinfo.mobile
        } else{
          //直接默认取第一个
          this.inquiry_info.purch_man_name = this.chosenCompany.deps[0].work_name
          this.inquiry_info.purch_man_email = this.chosenCompany.deps[0].work_email
          this.inquiry_info.purch_man_phone = this.chosenCompany.deps[0].work_phone
        }
        this.inquiry_info.inquiry_date = todayDate()
        const inquiryMats=JSON.parse(sess.get('mats'))
        sess.remove('mats')
        this.inquiry_info.mat_list=[]
        if (inquiryMats) {
          inquiryMats.forEach((e,i)=>{
            let mats={
              mat_desc: e.mat_desc,
              mat_id:e.mat_id,
              mat_model: e.mat_model,
              mat_no:e.mat_no,
              mat_type_id:e.mat_type_id,
              mat_type_name: e.mat_type_name,
              process_cost: '',
              process_up:'',
              require_id:e.require_id,
              plan_qty:e.plan_qty,
              quantity: e.plan_qty,
              tax_cost: '',
              total_price:e.total_price,
              up: ''
            }
            this.inquiry_info.mat_list.push(mats)
          })
        }

      }else{
        this.supplierFeedbackDetail()
      }

    },
    methods: {
      chosenPicker(e){
        this.inquiry_info.purch_man_name = this.deps[e.id].work_name
        this.inquiry_info.purch_man_email = this.deps[e.id].work_mail
        this.inquiry_info.purch_man_phone = this.deps[e.id].work_phone
      },
      selectPurch(){
        if(this.action!=='new'){
          return
        }
        this.showPicker=true
        this.pickerValue={id:'',val:''}
      },
      onMenuClick(){

      },
      quantity(item){
        if(item.quantity>item.plan_qty){
          item.quantity=item.plan_qty
        }else if(item.quantity<1){
          item.quantity=1
        }
      },
      deleteSup(i){
        this.inquiry_info.sup_list.splice(i,1)
      },
      newInquiry(){

        if(!this.inquiry_info.sup_list.length>0){
          $toast.show('请添加供应商');
          return
        }
        if(this.inquiry_info.quote_end_date==='请选择'){
          $toast.show('请选择截至日期');
          return
        }
        if(this.inquiry_info.delivery_place==''){
          $toast.show('请输入交货地点');
          return
        }
//        if(this.inquiry_info.coin_name==='请选择'){
//          $toast.show('请选择币种');
//          return
//        }
//        if(this.inquiry_info.invoice_type_name==='请选择'){
//          $toast.show('请选择发票类型');
//          return
//        }

        let isPrice=false
        this.inquiry_info.mat_list.forEach((e,i)=>{
          if(!(e.total_price>0)){
            isPrice=true
            return
          }
        })
        const requestData={
          inquiry_info:this.inquiry_info
        };
        sendInquiry(requestData).then(res=>{
          this.$router.go(-1)
        }).catch(error=>{
          $toast.show(error.description);
        })
      },
      onItemClick(item){
        this.$set(item,'col',!item.col)
      },
      chosen(item){
        switch (this.dicType){
          case 'coin':{
            this.inquiry_info.coin = item.code
            this.inquiry_info.coin_name = item.name
            break;
          }
          case 'invoiceType':{
            this.inquiry_info.invoice_type = item.code
            this.inquiry_info.invoice_type_name = item.name
            break;
          }//
          case 'supplier':{
            let isIndexOf=false
            for(let i=0;i<this.inquiry_info.sup_list.length;i++){
              if(this.inquiry_info.sup_list[i].sup_company_id==item.sup_company_id){
                isIndexOf=true
                break
              }
            }
            if(isIndexOf){
              return
            }
            this.inquiry_info.sup_list.push({
              sup_company_id:item.sup_company_id,
              sup_name:item.sup_name
            })
            break;
          }
        }
      },
      chooseDic(dicType){
        if(this.action!=="check") {
          switch (dicType) {
            case 'coin': {
              this.dicType = 'coin'
              this.showFilter = true
              break;
            }
            case 'invoiceType': {
              this.dicType = 'invoiceType'
              this.showFilter = true
              break;
            }
            //customer
            case 'supplier': {
              this.dicType = 'supplier'
              this.showFilter = true
              break;
            }
          }
        }
      },
      //查询公司详情
      getCompanyInfo(){

        getCompany({}).then(res=>{
          this.inquiry_info.delivery_place=res.address
        }).catch(error=>{
          $toast.show(error.description);
        })
      },
      //查询供货商反馈单详情
      supplierFeedbackDetail(){
        const requestData={
          query:{inquiry_id:this.$route.params.inquiry_id},
//          inquiry_id:this.$route.params.inquiry_id,
          inquiry_id:8
        };
        supInquiryFeedbackDetail(requestData).then(res=>{
          this.inquiry_info= res.inquiry_feedback;
        }).catch(error=>{
          $toast.show(error.description);
        })
      },
      //提交反馈
      submitFeedback(){
        const requestData={
          inquiry_feedback:this.inquiry_info
        };
        submitFeddback(requestData).then(res=>{
          $toast.show("提交反馈成功");
          this.action="check"
          $router.go(-1)
        }).catch(error=>{
          $toast.show(error.description);
        })
      }

    },
    computed:{
      ...mapGetters(['userinfo','chosenCompany']),
      totalPrice:function(){
        if(this.$route.params.action!=='checck'){
          let totalPrice=0
          this.inquiry_info.mat_list.forEach((item,i)=>{
            // item.process_up=Number(item.process_up).toFixed(2)//加工费
            // item.up=Number(item.up).toFixed(2)//单价
            item.quantity=Number(item.quantity)//数量
            if(this.inquiry_info.rebate){
              this.inquiry_info.rebate=Number(this.inquiry_info.rebate)//折扣
            }else{
              this.inquiry_info.rebate=1
            }
            item.process_cost=(item.quantity*item.process_up*this.inquiry_info.rebate).toFixed(2);
            item.total_price=(item.quantity*(Number(item.process_up)+Number(item.up))*this.inquiry_info.rebate).toFixed(2);
            totalPrice+=Number(item.total_price);
            this.inquiry_info.total_price=totalPrice.toFixed(2);
          })
        }


      }

    },
    // created(){
    //   this.action = this.$route.params.action
    //   if(this.$route.params.inquiry_id==='null'&&this.action==='new'){
    //     this.inquiry_info.purch_man_name = this.userinfo.username
    //     this.inquiry_info.purch_man_email = this.userinfo.email
    //     this.inquiry_info.purch_man_phone = this.userinfo.phone
    //     this.inquiry_info.inquiry_date = todayDate()
    //     const mats = sess.get('mats')
    //     this.inquiry_info.mat_list = JSON.parse(mats)
    //     sess.remove('mats')
    //   }
    // },
  }
</script>

<style lang="scss" scoped>

  .col-content{
    height: 250px;
  }
  .modelInput{float: right;height: 16px;line-height: 14px;text-align: right;color: #ababab;padding: 0}
  .item-note{color: #666}
  .supList{
    overflow-y: visible;
    span{
      display: inline-block;
      border: 1px solid #416eb6;
      padding: 5px 10px;
      border-radius: 5px;
      margin-right: 10px;
      margin-bottom: 5px;
      position: relative;
      overflow: visible;
      i{
        position: absolute;
        width: 16px;height: 16px;
        text-align: center;
        line-height: 16px;
        background:red ;
        color: #fff;
        border-radius: 16px;
        top: -4px;right: -4px;
        font-size: 10px;
      }
    }
  }
</style>

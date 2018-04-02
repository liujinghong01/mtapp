<template>
  <div class="mw-page page" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>报价单详情</span>
      <!--审批状态码 step10(起草)：可编辑, step20(审批中),step30(审批中),step0(审批结束)不可编辑 step11(被退回)可编辑/可查看审批意见-->
      <!--<i class="right" v-if="action==='check'&&(quote.approve_step==='step10'||quote.approve_step==='step11')" @click="action='edit'">编辑</i>-->
      <!--<i class="right"  v-else-if="action ==='edit'" @click="action='check'">取消</i>-->
      <!--<i class="right"  v-else></i>-->

      <i class="right" v-if="tabIndex===1" @click="$router.forward({path:'/customerOrder/quoteReferTo',query:{quote_id:quote_id}})">参考</i>
      <i class="right" v-else></i>
    </div>
    <div class="mw-page-content has-tab">
      <div style="height: 48px">
        <tabs :tab-items="tabs" :tab-index="tabIndex" :on-tab-click="onTabClick"></tabs>
      </div>
      <div style="flex: 1;position: relative">
        <div class="page-content" v-if="tabIndex===0">
          <cst-item label="报价单编号" v-show="action!='new'" v-model="quote.quote_no" editType="text" :action="action" :canEdit="false"></cst-item>
          <cst-item label="报价日期"  v-show="action!='new'"  v-model="quote.quote_date" editType="text" :action="action" :canEdit="false"></cst-item>
          <cst-item label="报价员"  v-model="quote.handler_name" editType="text" :action="action" :canEdit="false" > </cst-item>
          <cst-item label="业务员"
                    v-model="quote.salesman_name"
                    editType="text"
                    :action="action"
                    :hasArrow="true"
                    @click.native="changeType('salesman')"
                    :canEdit="false" ></cst-item>
          <cst-item label="客户"
                    v-model="quote.cst_name"
                    editType="text"
                    :action="action"
                    :hasArrow="true"
                    @click.native="changeType('customer')"
                    :canEdit="false" ></cst-item>
          <cst-item label="致"  v-model="quote.connect_man" editType="text" action="check"></cst-item>
          <cst-item label="Email"  v-model="quote.email" editType="text" action="check" ></cst-item>
          <cst-item label="电话"  v-model="quote.phone" editType="text" action="check" ></cst-item>
          <cst-item label="交货地址"  v-model="quote.delivery_place" editType="text" :action="action" ></cst-item>
          <cst-item label="产品名称"  v-model="quote.prod_name" editType="text" :action="action" ></cst-item>
          <cst-item label="币种"  v-model="quote.coin_name"
                    editType="text"
                    :action="action"
                    :hasArrow="true"
                    @click.native="changeType('coin')"
                    :canEdit="false"  ></cst-item>
          <cst-item label="报价有效期(天)"  v-model="quote.avail_days" editType="number" :action="action"></cst-item>
        </div>
        <div  class="page-content" v-if="tabIndex===1">
          <swipe-item
            v-show="quoteLines.length>0"
            swipeItemText="删除"
            :canSwipe="true"
            v-on:SwipeItemClick="delItem"
            v-on:ItemClick="clickLine"
            v-for="item in quoteLines"
            :hasArrow="true"
            :obj="item">
            <p>
              <span class="prodItem" v-for="(e,i) in item.prod_list">{{ e.cst_prod_name }}</span>
            </p>
          </swipe-item>
          <!--<div class="btn"  v-show="tabIndex===1" >-->
            <!---->
          <!--</div>-->
          <button @click="$router.forward('/customerOrder/quote/line_info/new/'+quote_id)" class="button button-calm button-block button-outline">新增产品</button>

        </div>
        <div  class="page-content" v-if="tabIndex===2">
          <item>总计:{{ total.toFixed(2) }}</item>
          <list>
            <div v-for="(item,index) in paylist" class="payItem">
              <div class="item-divider prd-header">
                <span style="padding-left:10px;float:left;">第{{index+1}}次付款</span>
                <span style="padding-right:10px;float:right;" @click="delPayItem(index)" v-show="action==='edit'||action==='new'">删除</span>
              </div>
              <!--<cst-item label="比例(%)" v-model="item.pay_ratio" :action="action" placeholder="0" editType="number"></cst-item>-->
              <item style="margin: 0">比例(%):
                <span class="val">
                  <input @blur="pay_money(item)" v-model="item.pay_ratio" placeholder="0" type="number">
                </span>
              </item>
              <cst-item label="金额" v-model="item.pay_money"  placeholder="0" editType="number"></cst-item>
              <item style="padding-left: 15px">
                条款
              </item>
              <textarea placeholder="请输入条款信息" v-model="item.pay_term"  v-if="action==='new'||action==='edit'"></textarea>
              <textarea v-model="item.pay_term" v-else disabled></textarea>
            </div>
            <!--<div  @click="addOnePayItem"  class="item-divider" style="text-align: center;color: #416eb6">添加付款信息</div>-->
            <button v-show="action==='new'||action==='edit'" @click="addOnePayItem" class="button button-calm button-block button-outline">添加付款信息</button>

          </list>
        </div>
        <div  class="page-content remark" v-if="tabIndex===3">
          <div style="padding: 5px 15px;min-height: 200px;background-color: white">
            <div class="remarkItem" v-for="(item,index) in remarkList"><span>{{item.seq_no}}</span> <input v-model="item.remark"/><span v-show="action!=='check'" style="float:right;color: #416eb6;" @click="delRemark(index)">删除</span></div>
            <div class="remarkItem" v-for="item in chosenRemarkTemplate"><span>{{item.seq_no}}</span> <input v-model="item.remark"/></div>
            <textarea placeholder="手动添加备注" v-model="remark" v-if="action==='new'||action==='edit'"></textarea>
            <textarea v-model="remark" v-else disabled></textarea>
          </div>

          <div class="item-divider" v-show="remarkTemplates.length>0" >
            <span style="padding-left: 15px">备注模板列表</span>
          </div>
          <von-checkbox v-show="action!=='check'" :options="remarkTemplates" v-model="chosenRemarkTemplateIndexs" theme="positive"></von-checkbox>
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
    <sidebar-check :showFilter="showFilter" :dicType="dicType" :chosen="chosen" v-on:hideMask="showFilter=false"></sidebar-check>
    <div class="mw-page-footer"  v-if="action === 'check'&&(quote.approve_step!=='step20'&&quote.approve_step!=='step30')">
      <div class="btns">
        <div class="btn" v-if="(quote.approve_step==='step11'||quote.approve_step==='step10')" @click="submit">
          提交审批
        </div>
        <div class="btn" v-show="quote.approve_step==='step11'||quote.approve_step==='step0'" @click="checkApprove">
          查看审批意见
        </div>
      </div>
    </div>
    <div class="mw-page-footer" v-else-if="action === 'new'||action === 'edit'||action === 'associate'">
      <div class="btns" >
        <div class="btn" @click="saveData('')">
          保存
        </div>
      </div>
    </div>
    <div class="mw-page-footer" v-else-if="action==='approve'">
      <div class="btns" >
        <div class="btn" @click="submit">
          审批
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import  cstItem  from '@/components/cstItem'
  import {  modify ,headDetail,quoteLines,quoteLineDel,payList,payListModify, remarkList , remarkListModify,remarkTemplate,relateCompute} from '@/api/customerOrder/quote'
  import sess from '@/utils/sess'
  import {mapGetters} from 'vuex'
  import { submitApprove } from '@/api/customerOrder/compute'
  import SwipeItem from "../../../../components/swipeItem";
  import SidebarCheck from "../../../../components/sidebarCheck";
  import MwPrompt from "../../../../components/mwPrompt";
  export default{
    name:'quoteDetail',
    components:{
      MwPrompt,
      SidebarCheck,
      SwipeItem,
      cstItem
    },
    data(){
      return{
        total:'',
        action:'edit',
        tabs:['头信息','明细信息','付款明细','备注'],
        tabIndex:0,
        quote:{
          quote_id:'',
          quote_no:'',
          quote_date:'',
          cst_company_id:'',
          cst_name:'请选择',
          salesman:'',
          salesman_name:'请选择',
          prod_name:'',
          phone:'',
          handler:'',
          handler_name:'请选择',
          email:'',
          connect_man:'',
          coin_name:'请选择',
          coin:'',
          avail_days:'',
          approve_step:''
        },
        quoteLines:[

        ],
        paylist:[
        ],
        remarkList:[

        ],
        remarkTemplates:[],
        chosenRemarkTemplateIndexs:[],
        chosenRemarkTemplate:[],
        quote_id:'',
        dicType:'',
        showFilter:false,
        remark:'',  //手动添加备注
        approve:{
          suggestion:''
        },
        showApprove:false
      }
    },
    computed:{
      ...mapGetters(['userinfo']),
    },
    created(){
      this.action = this.$route.params.action
      if(this.action ==='new'){
        //如果是新增，客户信息及模具类型会从上一个面页带过来

      }else{
        this.action ='edit'
        this.quote.quote_id = this.quote_id = this.$route.params.quote_id
        this.getData();
        this.getQuoteLines()
      }
      this.quote.handler_name = this.userinfo.username
      this.quote.handler = this.userinfo.uid
    },
    activated(){
      if(this.tabIndex===1){
        this.getQuoteLines();
      }
    },

    methods:{
      chosen(item){
        if(this.dicType === 'coin'){
          this.quote.coin_name = item.name
          this.quote.coin = item.code
        }else if(this.dicType === 'customer'){
          this.quote.cst_name = item.cst_name
          this.quote.cst_company_id = item.cst_company_id
          this.quote.connect_man = item.connect_man
          this.quote.phone = item.phone
          this.quote.email = item.email
          this.quote.delivery_place = item.cst_company_address
        }else if(this.dicType === 'salesman'){
          this.quote.salesman_name = item.work_name
          this.quote.salesman = item.uid
        }
      },
      pay_money(item){
        if(item.pay_ratio>0&&item.pay_ratio<=100){
          item.pay_money=item.pay_ratio*this.total/100
        }else{
          $toast.show('比例输入有误',600)
        }
      },
      //修改类型-字典数据
      changeType(type){
        if(this.action==='check'||this.action==='approve'){
          return
        }
        this.dicType = type
        this.showFilter = true
      },
      onMenuClick(){
        if(this.action==='check'){
          this.action = 'edit'
        }else{
          this.action = 'check'
        }
      },
      onTabClick(index){
        const that = this;
        if(index===this.tabIndex){
          return
        }
        if(index===2&&this.quoteLines.length<1){
          $toast.show('请先添加行信息')
          return
        }
        if(!this.quote_id){
          this.saveData(index)
          return
        }
        console.log(index)
        switch (index){
          case 0:{
            break
          }
          case 1:{
            that.getQuoteLines();
            break
          }
          case 2:{
            if(this.paylist.length===0){
              //付款明细
              this.getPayList()
            }
            break;
          }
          case 3:{
            if(this.remarkList.length===0){
              //备注信息
              this.getRemarkList()
            }
            if(this.remarkTemplates.length===0&&(this.action==='new'||this.action==='edit')){
              //备注信息模板
              this.getRemarkTemplates()
            }
            break;
          }
        }
        this.tabIndex = index
      },
      getData(){
        const that = this;
        const requestData = {
          quote_id:that.quote_id
        }
        headDetail(requestData).then(res=>{
          that.quote = res
        }).catch(error=>{
          $toast.show(error.description)
        })
      },

      //在不同的tab时 保存调用的接口 也不一样
      saveData(index){
        //验证数据完整性
        if(this.quote.salesman==='') {
          $toast.show('请选择业务员')
          return
        }
        if(this.quote.cst_company_id==='') {
          $toast.show('请选择客户')
          return
        }
        if(this.quote.prod_name==='') {
          $toast.show('请填写产品名称')
          return
        }
        if(this.quote.coin==='') {
          $toast.show('请选择币种')
          return
        }
        if(this.quote.avail_days==='') {
          $toast.show('请填写报价有效期')
          return
        }

        switch (this.tabIndex){
          case 2:{
            let rate = 0
            let money = 0
            this.paylist.forEach(o=>{
              rate = rate + parseInt(o.pay_ratio)
              money = money + parseFloat(o.pay_money)
            })
            if(rate!==100) {
              $toast.show('付款比例有误')
              return
            }
            if(money===0) {
              $toast.show('金额不能为0')
              return
            }
            const requestData = {
              quote_id:this.quote_id,
              pay_list:this.paylist
            }
            payListModify(requestData).then(res=>{
              // this.paylist = res.pay_list?res.pay_list:[]
              $toast.show('付款信息保存成功',700)
              $router.go(-1)
            }).catch(error=>{
              $toast.show(error.description)
            })
            break
          }
          case 3:{
            const requestData = {
              quote_id:this.quote_id,
              remark_list:this.remarkList.concat(this.chosenRemarkTemplate)
            }
            remarkListModify(requestData).then(res=>{
              // this.remarkList = res.remark_list?res.remark_list:[]
              $toast.show('备注信息保存成功',700)
              $router.go(-1)
            }).catch(error=>{
              $toast.show(error.description)
            })
            break
          }
          default:{
            const requestData = {
              quote_head:this.quote
            }
            modify(requestData).then(res=>{
              this.quote = res.quote_head
              this.quote_id = res.quote_head.quote_id
              if(index===''){
                $toast.show('保存成功')
                $router.go(-1)
              }else{
                $toast.show('头信息已保存')
                this.tabIndex=index
              }
            }).catch(error=>{
              $toast.show(error.description)
            })
            break
          }

        }
      },
      //查询行信息列表
      getQuoteLines(){
        const reqData={
          quote_id:this.quote_id
        }
        quoteLines(reqData).then(res=>{
          this.quoteLines = res.quote_line_list;
          this.total=0
          if(this.quoteLines.length>0){
            this.quoteLines.forEach((e,i)=>{
              this.total+=Number(e.price)*Number(e.count)
            })
          }
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      clickLine(item){
        this.$router.forward('/customerOrder/quote/line_info/'+this.action+'/'+item.quote_line_id)
      },
      delItem(item){
        const that = this
        $dialog.confirm({
          theme:'ios',
          // 标题
          title: '确定要删除'+item.cst_mold_no+'吗?',
          // 取消按钮文本
          cancelText: '取消',
          // 确定按钮文本
          okText: '确定'
        }).then((res) => {
          console.log('confirm result: ', res)
          if(res){
            const reqData = {
              quote_line_id:item.quote_line_id
            }
            quoteLineDel(reqData).then(res=>{
              $toast.show('删除成功',200)
              this.getQuoteLines()
            }).catch(error=>{
              $toast.show(error.description)
            })
          }
        })
      },
      getPayList(){
        const that = this
        const reqData = {
          quote_id:this.quote_id
        }
        payList(reqData).then(res=>{
          that.paylist = res.pay_list?res.pay_list:[]
          console.log(res)
        }).catch(error=>{
          console.log(error)
          $toast.show(error.description)
        })
      },
      //付款清单列表
      addOnePayItem(){
        let obj = {
          pay_id:'',
          pay_money:'',
          pay_ratio:'',
          pay_term:''
        }
        this.paylist.push(obj)
      },

      delPayItem(i){
        this.paylist.splice(i,1)
      },

      getRemarkList(){
        const reqData = {
          quote_id:this.quote_id
        }
        remarkList(reqData).then(res=>{
          this.remarkList = res.remark_list?res.remark_list:[]
          this.chosenRemarkTemplateIndexs.length = 0;
          console.log(res)
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      delRemark(index){      //删除已经保存的备注信息
        for(let i in this.remarkList){
          if(index==i){
            this.remarkList.splice(index,1)
          }
        }
      },
      getRemarkTemplates(){
        const reqData = {

        }
        remarkTemplate(reqData).then(res=>{
          const tempList = res.list?res.list:[];
          tempList.forEach((item,index)=>{
            this.remarkTemplates.push(item.name)
          })
          console.log(res)
        }).catch(error=>{

        })
      },
      submit(){
        if(this.action==='edit'){
          $toast.show('请先保存头信息')
          return
        }
        if(this.action==='approve'){
          this.showApprove = true
//          this.$router.forward('/approve/approve/'+this.quote_id+'/2/'+this.quote.approve_step)

        }else{
          const reqData={
            bill_id:this.quote_id,
            bill_type:'2',
            approve_flag:'1',
            approve_step:this.quote.approve_step,
            approve_man_id:this.userinfo.uid,
            approve_man_name:this.userinfo.username,
          }
          submitApprove(reqData).then(res=>{
            $toast.show('提交成功')
            sess.set('approve',true)
            this.$router.go(-1)
          }).catch(error=>{
            $toast.show(error.description)
            console.log(error.description)
          })
        }
      },
      checkApprove(){
        //查看审批意见
        this.$router.forward('/approve/approveSuggests/'+this.quote.quote_id +'/2')
      },
      approveProcess(flag){
        flag = flag === 'ok' ? 1 : 2
        const reqData = {
          approve_sugg:this.approve.suggestion,
          bill_id: this.quote_id,
          bill_type: 2,
          approve_step: this.quote.approve_step,
          approve_man_id:this.userinfo.uid,
          approve_man_name:this.userinfo.username,
          approve_flag:flag
        }
        submitApprove(reqData).then(res=>{
//          $toast.show('审批成功')
          history.go(-1)
        }).catch(error=>{
          $toast.show(error.description)
        })
      }
    },
    watch:{
      'chosenRemarkTemplateIndexs':function () {
        this.chosenRemarkTemplate.length=0;
        this.chosenRemarkTemplateIndexs.forEach((item,index)=>{
          this.chosenRemarkTemplate.push({
            seq_no:index+this.remarkList.length+1,remark:this.remarkTemplates[item]
          })
        })
      }
    }
  }
</script>
<style lang="scss" scoped>



  .item-divider span{
    color: #416eb6;
    transform: scale(0.8,08);
    -webkit-transform: scale(0.8,0.8);
  }
  .remark textarea{
    width: 100%;
    margin: auto;
    margin-top: 20px;
    padding-left: 15px;
    height: 80px;
    resize: none;
    border: 1px solid blueviolet;
  }
  .payItem textarea{
    width: 100%;
    resize: none;
    height: 50px;
    padding: 5px 15px;
  }

  .remarkItem{
    height: 30px;
    line-height: 30px;
    input{
      width: 80%;
      border: none;
    }
  }
  .prodItem{
    &:last-of-type:after{
      display: none;
    }
    &:after{
      display: inline;
      content: '、';
    }
  }
  .val{
    position: absolute;
    width: 80%;
    height: 16px;
    right: 0;
    box-sizing: border-box;
    padding-right: 15px;
    &>input{
      width: 100%;
      height: 100%;
      text-align: right;
    }
  }
</style>

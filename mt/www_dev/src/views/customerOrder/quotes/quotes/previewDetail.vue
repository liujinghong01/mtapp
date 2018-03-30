<template>
  <div class="mw-page page" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>报价单</span>
      <i class="right" v-if="btnShow==='approve'||btnShow===''||btnShow==='isApproval'"></i>
      <i class="right" v-else @click="$router.forward('/customerOrder/quote/detail/check/'+quote_id)">编辑</i>
    </div>

    <div class="mw-page-content">
      <div class="previewBox">
        <div class="preview">
          <p>报价单编号号：{{ quote.quote_no }}</p>
          <p>产品名称：{{ quote.prod_name }}</p>
          <p v-if="btnShow==='approve'">审批状态：
            <span style="color:#416eb6 " v-if="quote.approve_step==='step20'">初审</span>
            <span v-else  style="color:#416eb6 ">复审</span>
          </p>
          <p>币种：{{ quote.coin_name }}</p>
          <p>报价日期：{{ quote.quote_date }}</p>
          <p>报价有效日期（天）：{{ quote.avail_days }}</p>
          <p>报价员：{{ quote.handler_name }}</p>
          <p>业务员：{{ quote.salesman_name }}</p>
          <p>客户：{{ quote.cst_name }}</p>
          <p>致：{{ quote.connect_man }}</p>
          <p>Email：{{ quote.email }}</p>
          <p>电话：{{ quote.phone }}</p>
          <p>交货地点：{{ quote.delivery_place }}</p>
          <div class="LineInfo">
            <p class="title">行信息</p>
            <p v-if="quoteLines.length<1">暂未配置行信息</p>
            <div class="lineItem" v-for="(item,index) in quoteLines" @click="clickLine(item)">
              <p>制品信息：
                <span v-for="(e,i) in item.prod_list">{{ e.cst_prod_name }}</span>
              </p>
              <p>产品类型：{{ item.type_name }} <span class="right">数量：{{ item.count }}</span></p>
              <p>单价:{{ Number(item.price).toFixed(2) }}<span class="right">总计:{{(Number(item.price)*Number(item.count)).toFixed(2) }}</span></p>
              <i class="ion-ios-arrow-right"></i>
            </div>
          </div>
          <div class="info">
            <p class="title">付款信息</p>
            <p v-if="paylist.length<1">暂未配置付款信息</p>
            <p v-for="(item,index) in paylist">
              第{{ index+1 }}次：{{ item.pay_money.toFixed(2) }}元（{{ item.pay_ratio }}%）
              <br/>
              <span>{{ item.pay_term }}</span>
            </p>
          </div>
          <div class="info">
            <p class="title">备注信息</p>
            <p v-if="remarkList.length<1">暂无备注信息</p>
            <p v-for="(item,index) in remarkList">{{ item.seq_no }}.{{ item.remark }}</p>
          </div>
        </div>
      </div>
    </div>
    <div class="mw-page-footer" >
      <div v-if="btnShow!==''&&btnShow!=='return'" class="btns">
        <div class="btn" v-show="btnShow==='isSubmit'" @click="submit">
          提交审批
        </div>
        <div class="btn" v-show="btnShow==='isApproval'" @click="checkApprove">
          查看审批意见
        </div>
        <div class="btn" v-show="btnShow==='approve'" @click="approve">
          审批
        </div>
      </div>
      <div v-if="btnShow==='return'" class="btns">
        <div class="btn"  @click="submit">
          提交审批
        </div>|
        <div class="btn"  @click="checkApprove">
          查看审批意见
        </div>
      </div>
    </div>

    <mw-prompt :show="showApprove"
               :okFunction="approveProcess"
               v-on:hidePop="showApprove = false"
               title="核算单审批"
               placeholder="请输入审批意见"
               v-model="approve_sugg"
               okText="同意"
               cancelText="不同意"
               :cancelFunction="noApproveProcess"
               promptType="textarea"
    ></mw-prompt>

  </div>
</template>
<script>
  import MwPrompt from "@/components/mwPrompt";
  import {  modify ,headDetail,quoteLines,quoteLineDel,payList,payListModify, remarkList , remarkListModify,remarkTemplate,relateCompute} from '@/api/customerOrder/quote'
  import { submitApprove } from '@/api/customerOrder/compute'
  import { mapGetters } from 'vuex'
  export default{
    name: 'quotePreviewDetail',
    components: {
      MwPrompt
    },
    data(){
      return {
        approve_sugg:'',
        showApprove:false,
        btnShow:'',

        quote_id:'',
        quote:'',
        quoteLines:[],
        paylist:[],
        remarkList:[]
      }
    },
    computed:{
      ...mapGetters(['userinfo'])
    },
    mounted(){
      // this.quote.quote_id =
      this.quote_id = this.$route.query.quote_id
      this.btnShow=this.$route.query.btnShow
    },

    activated(){
      this.getData()
    },
    methods: {
      goMatList(){
        if(Number(compute.stuff_cost)>0){
          $router.forward({path:'/customerOrder/computemMaterialListPreview',query:{quote_id:quote_id,cost:compute.stuff_cost}})
        }

      },
      getData(){//报价单头信息
        const that = this;
        const requestData = {
          quote_id:that.quote_id
        }
        headDetail(requestData).then(res=>{
          that.quote = res
          that.getQuoteLines()
          that.getPayList()
          that.getRemarkList()
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      getQuoteLines(){//报价单行信息
        const reqData={
          quote_id:this.quote_id
        }
        quoteLines(reqData).then(res=>{
          this.quoteLines = res.quote_line_list;
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      getPayList(){//报价单付款信息
        const that = this
        const reqData = {
          quote_id:this.quote_id
        }
        payList(reqData).then(res=>{
          if(res.pay_list!==null){
            this.paylist = res.pay_list
          }
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      getRemarkList(){//报价单备注信息
        const reqData = {
          quote_id:this.quote_id
        }
        remarkList(reqData).then(res=>{
          if(res.remark_list!==null){
            this.remarkList = res.remark_list
          }
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      submit(){//提交审批
        if(this.quoteLines.length<1){
          $toast.show('请先配置行信息')
          return
        }
        $dialog.confirm({
          theme: 'ios',
          title: '确定要提交审批吗？',
          cancelText: '取消',
          okText: '确定'
        }).then(res=>{
          if(!res){
            return
          }
          const reqData = {
            bill_id: this.quote_id,
            bill_type: '2',
            approve_flag: '1',
            approve_step: this.quote.approve_step
          }
          submitApprove(reqData).then(res => {
            $toast.show('提交成功')
            this.btnShow='isApproval'
            $router.go(-1)
          }).catch(error => {
            $toast.show(error.description)
            console.log(error.description)
          })
        })

      },
      checkApprove(){
        //查看审批意见
        this.$router.forward('/approve/approveSuggests/' + this.quote_id + '/2')
      },
      approve(){
        this.showApprove=true
      },
      approveProcess(){
        this.process('1')
      },
      noApproveProcess(){
        if(this.approve_sugg===''){
          $toast.show('请输入审批意见',600)
          return
        }
        this.process('2')
      },
      process(approve_flag){
        const reqData = {
          bill_id: this.quote_id,
          bill_type: '2',
          approve_flag: approve_flag,
          approve_step: this.quote.approve_step,
          approve_sugg:this.approve_sugg
        }
        submitApprove(reqData).then(res => {
          $toast.show('审批成功')
          $router.go(-1)
        }).catch(error => {
          $toast.show(error.description)
          console.log(error.description)
        })
      },
      clickLine(item){
        this.$router.forward('/customerOrder/quote/line_info/check/'+item.quote_line_id)
      },
    },

  }
</script>
<style lang="scss" scoped>

.LineInfo{
  width: 100%;
  padding:0 5px;
  &>.lineItem{
    border-bottom: 1px solid #ddd;
    padding: 10px 0;
    position: relative;
    &:first-of-type{
      border-top: 1px solid #ddd;
    }
    &>i.ion-ios-arrow-right{
      position: absolute;
      right: 0;
      top: 50%;
      height: 40px;
      width: 20px;
      text-align: center;
      line-height: 40px;
      margin-top: -20px;
      font-size: 24px;
      color: #416eb6;
    }
    &>p{
      margin: 0;
      font-size: 13px;
      line-height: 15px;
      padding: 1px 0;
      width: 90%;
      text-overflow: ellipsis;
      overflow: hidden;
      white-space: nowrap;
      &>span{
        &:after{
          content: '/';
          display: inline-block;
        }
        &:last-of-type:after{
          display: none;
        }
        &.right{
          float: right;width: 50%;
          &:after{
            display: none;
          }
        }

      }
    }
  }
}
  .info{
    width: 100%;
    padding:0 5px;
    p{
      margin: 0;
      font-size: 13px;
    }
  }
p.title{
  color: #416eb6;margin-top: 10px ;margin-bottom: 0px ;
}

</style>

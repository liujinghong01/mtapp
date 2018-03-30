<template>
  <div class="mw-page page" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>客户订单</span>
      <i class="right" v-if="btnShow==='approve'||btnShow===''||btnShow==='isApproval'"></i>
      <i class="right" v-else @click="$router.forward('/customerOrder/order/detail/check/'+project_id)">编辑</i>
    </div>

    <div class="mw-page-content">
      <div class="previewBox">
        <div class="preview">
          <p>客户订单号：{{ project.project_no }}</p>
          <p>客户：{{ project.cst_name }}</p>
          <p v-if="btnShow==='approve'">审批状态：
            <span style="color:#416eb6 " v-if="project.approve_step==='step20'">初审</span>
            <span v-else  style="color:#416eb6 ">复审</span>
          </p>
          <p>订单接受日期：{{ project.order_accept_date }}</p>
          <p>交货地点：{{ project.delivery_place }}</p>
          <p>发票类型：{{ project.invoice_type_name }}</p>
          <p>是否含税：
            <span v-if="project.is_include_tax==1">是</span>
            <span v-else>否</span>
          </p>
          <p v-if="project.is_include_tax==1">税率(%)：{{ project.tax_ratio }}</p>
          <p>业务员：{{ project.salesman_name }}</p>
          <p>币种：{{ project.coin_name }}</p>
          <div class="LineInfo">
            <p class="title">行信息</p>
            <p v-if="orderLines.length<1">暂未配置行信息</p>
            <div class="lineItem" v-for="(item,index) in orderLines" @click="clickLine(item)">
              <p>产品名称：{{ item.cst_prod_name }} </p>

              <i class="ion-ios-arrow-right"></i>
            </div>
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
               title="客户订单审批"
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
  import { headDetail,queryLineList } from '@/api/customerOrder/order'
  import sess from '@/utils/sess'
  import { submitApprove } from '@/api/customerOrder/compute'
  import { mapGetters } from 'vuex'
  export default{
    name: 'orderPreviewDetail',
    components: {
      MwPrompt
    },
    data(){
      return {
        approve_sugg:'',
        showApprove:false,
        btnShow:'',

        project_id:'',
        project:'',
        approve_step:'',
        orderLines:''
      }
    },
    computed:{
      ...mapGetters(['userinfo'])
    },
    mounted(){
      this.project_id = this.$route.query.project_id
      this.btnShow=this.$route.query.btnShow
    },

    activated(){
      this.getData()
      this.getOrderLines()
    },
    methods: {
      goMatList(){
        if(Number(compute.stuff_cost)>0){
          $router.forward({path:'/customerOrder/computemMaterialListPreview',query:{quote_id:quote_id,cost:compute.stuff_cost}})
        }

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
      submit(){//提交审批
        // if(this.orderLines.length<1){
        //   $toast.show('请先配置行信息')
        //   return
        // }
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
            bill_id: this.project_id,
            bill_type: '3',
            approve_flag: '1',
            approve_step: this.project.approve_step
          }
          submitApprove(reqData).then(res => {
            $toast.show('提交成功')
            $router.go(-1)
          }).catch(error => {
            $toast.show(error.description)
            console.log(error.description)
          })
        })

      },
      checkApprove(){
        //查看审批意见
        this.$router.forward('/approve/approveSuggests/' + this.project_id + '/3')
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
          bill_id: this.project.project_id,
          bill_type: '3',
          approve_flag: approve_flag,
          approve_step: this.project.approve_step,
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
        // this.$router.forward('/customerOrder/quote/line_info/check/'+item.quote_line_id)
        sess.set('projectNo', this.project.project_no)
        this.$router.forward('/customerOrder/order/lineInfo/checkOnly/'+item.order_line_id)
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

p.title{
  color: #416eb6;margin-top: 10px ;margin-bottom: 0px ;
}

</style>

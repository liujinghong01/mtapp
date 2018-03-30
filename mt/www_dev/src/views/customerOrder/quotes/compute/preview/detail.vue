<template>
  <div class="mw-page page" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>核算单</span>
      <i class="right" v-if="compute.approve_step!=='step0'&&btnShow!=='approve'" @click="edit()">编辑</i>
      <i class="right" v-else></i>
    </div>

    <div class="mw-page-content">
      <div class="previewBox">
        <div class="preview">
          <p>核算单单号：{{ compute.compute_no }}</p>
          <p>核算日期：{{ compute.compute_date }}</p>
          <p>模具类型：
            <span v-show="compute.type+''==='1'">冲压模</span>
            <span v-show="compute.type+''==='2'">压铸模</span>
            <span v-show="compute.type+''==='3'">注塑模</span>
          </p>
          <p>制品材料：{{ compute.material }}</p>
          <p>制品信息：{{ compute.prod_info }}</p>
          <p v-if="btnShow==='approve'">审批状态:<span style="color: #416eb6" v-if="compute.approve_step==='step20'" >初审</span> <span style="color: #416eb6" v-else>复审</span></p>
          <p>制单人：{{ compute.handler_name }}</p>
          <p>备注：{{ compute.prod_remark }}</p>
          <div class="cascade">
            <div class="left">
              <p>费用占比</p>
            </div>
            <div class="right">
              <p @click="goMatList()" class="hasArrow">材料费：{{ compute.stuff_cost }}元({{ Number(compute.stuff_cost )===0?'0':(Number(compute.stuff_cost )*100/Number(compute.mold_up)).toFixed(2) }} %)  <i v-show="Number(compute.stuff_cost)>0" class="ion-ios-arrow-right"></i> </p>
              <p>加工费：{{ compute.process_cost }}元({{ Number(compute.process_cost )===0?'0':(Number(compute.process_cost )*100/Number(compute.mold_up)).toFixed(2) }} %) </p>
              <p>设计费：{{ compute.design_cost }}元({{ Number(compute.design_cost )===0?'0':(Number(compute.design_cost )*100/Number(compute.mold_up)).toFixed(2) }} %) </p>
              <p>试模费：{{ compute.try_mold_cost }}元({{ Number(compute.try_mold_cost )===0?'0':(Number(compute.try_mold_cost )*100/Number(compute.mold_up)).toFixed(2) }} %) </p>
              <p>其他费用：{{ compute.post_box_cost }}元({{ Number(compute.post_box_cost )===0?'0':(Number(compute.post_box_cost )*100/Number(compute.mold_up)).toFixed(2) }} %) </p>
              <p>管理费：{{ compute.manage_cost }}元({{ compute.manage_ratio }})  </p>
              <!--<p>其他费用：{{ compute.stuff_cost }}元</p>-->
            </div>
          </div>
          <p>成本：{{ compute.mold_cost }}元( {{ Number(compute.mold_cost )===0?'0':(Number(compute.mold_cost )*100/Number(compute.mold_up)).toFixed(0) }}%) </p>
          <p>利润：{{ compute.profit_cost }}元({{ compute.profit_ratio }} ) </p>
          <!--<p>税率：{{ compute.mold_cost }}元</p>-->
          <p>模具单价：{{ compute.mold_up }}元</p>
        </div>
      </div>
    </div>
    <div class="mw-page-footer" v-show="btnShow!==''&&btnShow!=='return'">
      <div class="btns">
        <div class="btn" v-if="btnShow==='approve'" @click="approve">
          审批
        </div>
        <div class="btn" v-else-if="btnShow==='isSubmit'&&compute.approve_step!='step20'&&compute.approve_step!='step30'" @click="submit">
          提交审批
        </div>
        <div class="btn" v-else @click="checkApprove">
          <!--<div class="btn" v-else-if="btnShow==='isApproval'" @click="checkApprove">-->
          查看审批意见
        </div>

      </div>
    </div>
    <div class="mw-page-footer" v-show="btnShow==='return'">
      <div class="btns">
        <div class="btn" @click="submit">
          提交审批
        </div>|
        <div class="btn"  @click="checkApprove">
          <!--<div class="btn" v-else-if="btnShow==='isApproval'" @click="checkApprove">-->
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
  import {detail, save, submitApprove, associate} from '@/api/customerOrder/compute'
  import { mapGetters } from 'vuex'
  export default{
    name: 'computeDetailPreview',
    components: {
      MwPrompt
    },
    data(){
      return {
        isNew:false,
        approve_sugg:'',
        showApprove:false,
        compute:'',
        compute_id:'',
        btnShow:'',
      }
    },
    computed:{
      ...mapGetters(['userinfo'])
    },
    mounted(){
      let param=this.$route.query
      this.compute_id = param.compute_id
      this.btnShow=param.isSubmit
      if(param.isNew){
        this.isNew=param.isNew
      }

      this.getData()
    },
    activated(){
      this.getData()
    },
    methods: {
      goMatList(){

        if(Number(this.compute.stuff_cost)>0){
          this.$router.forward({path:'/customerOrder/computemMaterialListPreview',query:{compute_id:this.compute_id,cost:this.compute.stuff_cost}})
        }

      },
      edit(){

        if(this.isNew){
          this.$router.go(-1)
        }else{
          this.$router.forward('/customerOrder/compute/detail/action/'+this.compute_id)
        }
      },
      getData(){
        const that = this;
        const requestData = {
          compute_id: that.compute_id
        }
        detail(requestData).then(res => {
          that.compute = res
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      submit(){//提交审批
        if(Number(this.compute.mold_up)<=0){
          $toast.show('请先配置核算单各项费用数据',700)
          return
        }

        $dialog.confirm({
            theme: 'ios',
            title: '确定要提交审批吗？',
            cancelText: '取消',
            okText: '确定'
          }).then(res=>{
          if(res){
            const reqData = {
              bill_id: this.compute_id,
              bill_type: '1',
              approve_flag: '1',
              approve_step: this.compute.approve_step
            }
            submitApprove(reqData).then(res => {
              $toast.show('提交成功')
              this.$router.go(-1)
            }).catch(error => {
              $toast.show(error.description)
              console.log(error.description)
            })
          }
        })
      },
      checkApprove(){
        //查看审批意见
        this.$router.forward('/approve/approveSuggests/' + this.compute.compute_id + '/1')
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
          bill_id: this.compute_id,
          bill_type: '1',
          approve_flag: approve_flag,
          approve_step: this.compute.approve_step,
          approve_sugg:this.approve_sugg
        }
        submitApprove(reqData).then(res => {
          $toast.show('审批成功')
          this.$router.go(-1)
        }).catch(error => {
          $toast.show(error.description)
          console.log(error.description)
        })
      }
    },
  }
</script>
<style lang="scss" scoped>
  .cascade{
    width: 100%;
    border-bottom: 1px solid #ddd;
    &:after{
      display: block;
      content: " ";
      clear: both;
    }
    &>.left{
      width: 20%;
      float: left;
      &>p{
        line-height: 17px;
        border-bottom: 1px solid #ddd;
        margin: 0;
        font-size: 12px;
        padding:  5px;
        &:last-of-type{
          border-bottom: 0px;
        }
      }
    }
    &>.right{
      width: 80%;
      height: 100%;
      float: right;
      border-left: 1px solid #ddd;
      &>p{
        line-height: 20px;
        border-bottom: 1px solid #ddd;
        margin: 0;
        font-size: 12px;
        padding:  5px;
        &:last-of-type{
          border-bottom: 0px;
        }
      }

    }

  }

</style>

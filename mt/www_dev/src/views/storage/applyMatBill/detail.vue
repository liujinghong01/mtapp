<template>
    <div class="mw-page page storage" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>领料申请单</span>
            <i class="right" v-show="action==='check'" @click="action='edit'">编辑</i>
            <i class="right" v-show="action==='edit'" @click="action='check'">取消</i>
            <i class="right" v-show="action==='new'"></i>
        </div>
        <div class="mw-page-content">
        <div id="box">
          <cst-item label="领料类型" v-show="isShow" v-model="apply_mat_info.aplly_type" :action="action" placeholder="0"></cst-item>
        </div>
          <cst-item label="申请人" v-model="userinfo.fullname" :canEdit="false"></cst-item>
          <cst-item label="申请日期" v-model="apply_mat_info.request_date" :canEdit="false"></cst-item>
          <cst-item label="申请部门" v-model="chosenCompany.deps[0].dep_name" :canEdit="false"></cst-item>
          <cst-item label="领料总数量" v-model="apply_mat_info.apply_detail_list.length" :canEdit="false"></cst-item>
          <div class="item-divider"> <span class="left">领用物料列表</span>
            <span class="right" @click="$router.forward('/storage/applyMatBill/matList')" v-show="action!='check'">添加</span></div>
          <item v-for="item in apply_mat_info.apply_detail_list">
            <div style="display: flex">
              <div class="matBill-content">
                <p class="title">物料名称: {{item.mat_desc}}</p>
                <p class="title">物料编码: {{item.mat_no}}</p>
                <p class="title">规格型号: {{item.mat_model}}</p>
              </div>
              <div class="edit-zone">
                <span @click="minCount(item)">-</span>&nbsp;&nbsp;
                <input type="number" v-model="item.quantity" disabled v-if="action==='check'"/>
                <input type="number" v-model="item.quantity" v-else @input="countChange(item)"/>
                &nbsp;&nbsp;<span @click="addCount(item)">+</span>
              </div>
            </div>
          </item>
        </div>
      <div class="mw-page-footer">
        <div class="btns" v-show="action!=='check'">
          <div class="btn" @click="saveData">保存</div>
        </div>
        <div class="btns" v-show="action==='check'">
          <div class="btn" @click="submitApprove">提交审批</div>
        </div>
      </div>
    </div>
</template>
<script>
  import CstItem from "../../../components/cstItem";
  import { mapGetters } from 'vuex'
  import { todayDate } from '@/utils'
  import { billModify,billDetail } from '@/api/storage/applyMatBill'
  import {submitApprove} from '@/api/customerOrder/compute'
  import sess from '@/utils/sess'
  window.onload = function(){
     var vm = new Vue({
         el:'#box',
         data:{
           isShow:false,
         },
     });
  }
  export default{
    name:'applyMatBillDetail',
    components: {CstItem},
    data(){
      return {
        apply_mat_info:{
          apply_detail_list:[],
          apply_id:'',
          apply_no:'',
          approve_step: "",
          approve_sugg: "",
          dep_id: 1,
          dep_name: "",
          aplly_type:'0',
          request_date:todayDate()
        },
        action:'',
        bill_id:''
      }
    },
    computed:{
      ...mapGetters(['userinfo','chosenCompany'])
    },
    created(){
      this.action = this.$route.params.action
      if(this.action!=='new') {
        this.bill_id = this.$route.params.bill_id
        this.getData()
      }
    },
    activated(){
      const mats = sess.get('mats')
      console.log(mats)
      if (mats) {
        this.apply_mat_info.apply_detail_list = this.apply_mat_info.apply_detail_list.concat(JSON.parse(mats))
        sess.remove('mats')
      }
    },
    methods: {
      //获取领料单详情
      getData(){
        const reqData = {
          query:{
            apply_id:this.bill_id
          }
        }
        billDetail(reqData).then(res=>{
          console.log(res)
          //TODO 查询详情，少了当前库存量
          this.apply_mat_info = res.apply_mat_info
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      //保存信息
      saveData(isApprove){
        const reqData = {
          apply_mat_info:this.apply_mat_info
        }
        this.apply_mat_info.dep_id = this.chosenCompany.deps[0].dep_id
        this.apply_mat_info.dep_name = this.chosenCompany.deps[0].dep_name
        if(!this.apply_mat_info.dep_id||!this.apply_mat_info.dep_name) {
          $toast.show('部门信息不能为空')
          return
        }
        if(!this.apply_mat_info.apply_detail_list||this.apply_mat_info.apply_detail_list.length===0) {
          $toast.show('请先填写物料信息')
          return
        }
        billModify(reqData).then(res=>{
          console.log(res)
          this.action = 'check'
          $toast.show('保存成功')
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      //提交审批
      submitApprove(){
        const reqData = {
          bill_id:this.apply_mat_info.apply_id,
          bill_type:8,
          approve_flag:1,
          approve_step:this.action==='new'?'step10':this.apply_mat_info.approve_step
        }
        submitApprove(reqData).then(res=>{
          console.log(res)
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      addCount(item){   //点击+号
        if(this.action==='check') {
          return
        }
        if(item.quantity>=item.curr_stock_qty){
          $toast.show('不能再加了')
          return
        }
        this.$set(item,'quantity',++item.quantity)
      },
      minCount(item){//点击-号
        if(this.action==='check') {
          return
        }
        if(item.quantity===1){
          $dialog.confirm({
            theme:'ios',
            title:'要删除物料吗？',
            okText:'确定',
            cancelText:'取消'
          }).then(res=>{
            if(res) {
              this.apply_mat_info.apply_detail_list = this.apply_mat_info.apply_detail_list.filter(o=>{
                  return o.mat_id !== item.mat_id
              })
            }
          })
          return
        }
        this.$set(item,'quantity',--item.quantity)
      },
      countChange(item){
        console.log(item)
        if((item.quantity+'').indexOf('.')>-1){
          this.$set(item,'quantity',(item.quantity+'').substring(0,(item.quantity+'').indexOf('.')))
        }
      }
    }
  }
</script>

<style lang="scss" scoped>

</style>

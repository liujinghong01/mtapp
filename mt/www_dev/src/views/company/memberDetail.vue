<template>
  <div class="mw-page page company" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>员工信息</span>
      <i class="right" v-if="action === 'check'" @click="action = 'edit'">管理</i>
      <i class="right" v-if="action === 'edit'" @click="action = 'check'">取消</i>
      <i class="right" v-if="action === 'action'" ></i>
    </div>
    <div class="mw-page-content">
      <list>
        <cst-item label="姓名" placeholder="请输入员工姓名" v-model="member_info.work_name" :action="action"></cst-item>
        <cst-item label="电话" placeholder="请输入联系电话" v-model="member_info.work_phone" :action="action"></cst-item>
        <!--<cst-item label="职位" placeholder="请输入员工职位" v-model="member_info.dep_position" :action="action"></cst-item>-->
        <!--<cst-item label="工作状态" placeholder="请输入工作状态" v-model="member_info.work_state" :action="action"></cst-item>-->
        <cst-item label="工作邮箱" placeholder="请输入员工作邮箱号码" v-model="member_info.work_email" :action="action"></cst-item>
      </list>
    </div>
    <div class="mw-page-footer" v-show="action !== 'check'">
      <div class="btns">
        <div class="btn" @click="saveDate">保存</div>
      </div>
    </div>
  </div>
</template>
<script>
  import {getMemberInfo,updateMemberInfo} from '@/api/company'
  import { validatePhone, validateEmail } from '@/utils/validate'
  import {mapGetters} from 'vuex'
  import CstItem from "../../components/cstItem";
  export default{
    components: {CstItem},
    name:'memberDetail',
    data(){
      return {
        member_id:'',
        member_info:{
          furlough_end_date:'',//	休假开始日期
          furlough_start_date	:'',//	休假结束日期
          is_leader:'',//		是否为生产小组组长
          member_id:'',//		成员Id
          pg_id:'',//		生产小组Id
          position_code:'',//		职位编码
          position_name:'',//		职位Id
          work_mail:'',//		工作邮箱
          work_name:'',//		工作名字
          work_phone:'',//		工作电话
          work_state:'',//
        },
        action:'check'
      }
    },
    computed:mapGetters([
      'chosenCompany'
    ]),
    mounted(){
      this.member_id = this.$route.params.member_id;
      if(this.member_id==='new'){
        this.action = 'action'
      }else{
        this.getMemberDetail()
      }
    },
    methods:{
      getMemberDetail(){
        const requestData = {
          "company_id": this.chosenCompany.company_id,
          "member_id":this.member_id
        }
        getMemberInfo(requestData).then(res=>{
          console.log(res);
          this.member_info = res;
        }).catch(error=>{
          console.log(error)
        })
      },
      //修改成员基本信息
      saveDate(){

        if(this.member_info.work_email && !validateEmail(this.member_info.work_email)){
          $toast.show('请填写正确的邮箱号',1500)
          return
        }
        updateMemberInfo(this.member_info).then(res=>{
          $toast.show('修改成功')
          this.action = 'check'
          this.member_info = res;
        }).catch(error=>{
          $toast.show(error.description)
        })
      }
    }
  }
</script>
<style scoped lang="scss">
  .header-content{
    width: 100%;
    height:120px;
    background-color: #416eb6;
    .item-avatar{
      width: 100%;height: 80px;padding: 10px 16px;
      .avatar{
        width: 60px;height: 60px;border-radius: 60px;margin: 0 auto;
      }
    }
  }
</style>

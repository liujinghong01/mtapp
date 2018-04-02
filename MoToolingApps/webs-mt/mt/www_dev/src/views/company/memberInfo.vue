<template>
  <div class="mw-page page company" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>会员信息</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content">
      <list>
        <cst-item label="姓名" v-model="member_info.work_name" :action="action"></cst-item>
        <cst-item label="电话" v-model="member_info.work_phone" :action="action"></cst-item>
        <cst-item label="职位" v-model="member_info.dep_position" :action="action"></cst-item>
        <cst-item label="工作状态" v-model="member_info.work_state" :action="action"></cst-item>
        <cst-item label="工作邮箱" v-model="member_info.work_email" :action="action"></cst-item>
      </list>
    </div>
    <invite v-on:hideInvite="showPopup=false" :type="2"  v-show="showPopup"> </invite>
    <div class="mw-page-footer">
      <div class="btns">
        <div class="btn" @click="showPopup = true">邀请加入MoTooling</div>
      </div>
    </div>
  </div>
</template>
<script>
  import {getMemberInfo,updateMemberInfo} from '@/api/company'
  import {mapGetters} from 'vuex'
  import CstItem from "../../components/cstItem";
  import Invite from "../../components/invite";
  export default{
    components: {
      Invite,
      CstItem},
    name:'memberInfo',
    data(){
      return {
        member_id:'',
        member_info:{},
        action:'check',
        showPopup:false
      }
    },
    computed:mapGetters([
      'chosenCompany'
    ]),
    mounted(){
      this.member_id = this.$route.params.member_id;
      this.getMemberDetail()
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

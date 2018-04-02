<template>
  <div class="mw-page page company" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>我的公司</span>
      <i class="right" @click="action='edit'"  v-if="chosenCompany&&action==='check'">管理</i>
      <i class="right" @click="turnCheck"  v-else-if="chosenCompany&&action==='edit'">取消</i>
      <i class="right" v-else></i>
    </div>
    <div class="mw-page-content" v-if="chosenCompany">
      <list class="list-ios">
        <item class="item-icon-right" @click.native="$router.forward('/company/companyDetail/'+chosenCompany.company_id)">
          <img src="../../../static/images/company/logo_company.png" class="item-img">
          <span style="line-height: 30px;margin-left: 15px">{{chosenCompany.company_name}}</span>
          <i class="icon ion-ios-arrow-right" style="color: #DDD;padding: 34px 0"></i>
        </item>
      </list>
      <list>
        <div class="item item-divider-ios">
          部门信息
        </div>
        <item class="item-icon-right" v-for="item in depList" key="item.dep_id"  @click.native="goDepDetail(item)">
          {{item.dep_name}}
          <span class="item-note">{{item.member_num}}</span>
          <i class="icon ion-ios-arrow-right" style="color: #DDD;"></i>
        </item>
      </list>
      <!--<list v-show="members.length>0">-->
        <!--<div class="item item-divider-ios">-->
          <!--成员-->
        <!--</div>-->
        <!--<item class="item-icon-right item-contact" v-for="item in members" key="item.members" @click.native="$router.forward('/company/memberDetail/'+item.member_id)">-->
          <!--<img :src="item.icon?item.icon:'static/images/company/contact2.png'" class="item-img">-->
          <!--<span style="line-height: 30px;margin-left: 15px">{{item.work_name}}</span><span class="label" v-if="item.member_id===userinfo.uid">我自己</span>-->
        <!--</item>-->
        <!--<item class="item-icon-right" @click.native="showPopup=true">-->
          <!--<img src="../../../static/images/company/logo_company.png" class="item-img">-->
          <!--<span style="line-height: 30px;margin-left: 15px">邀请同事加入</span>-->
        <!--</item>-->
      <!--</list>-->
      <!--<div v-show="showPopup">-->
        <!--<invite v-on:hideInvite="showPopup=false" type="2"></invite>-->
      <!--</div>-->
    </div>
    <div class="mw-page-content"  v-if="!chosenCompany">
        <div style="height: 30%;width: 80%;margin: 30px auto">
          <img src="../../../static/images/company/default.png" width="100%">
        </div>
        <div style="width: 80%;margin: 0 auto">
          <div class="text-center">
            <p>您还未关联企业，您可以自己申请试用企业版，或者将您的公司注册为企业用户。</p>
            <md-button class="button button-calm block" @click.native="$router.forward('/company/trial')">试用企业版</md-button>
            <!--<br>-->
            <!--<md-button class="button button-assertive block">购买企业版</md-button>-->
          </div>
        </div>
      </div>
    <mw-prompt :show="addDepFlag" :okFunction="addNewDep" v-model="newDepName" v-on:hidePop="addDepFlag = false" placeholder="请输入新部门名称"></mw-prompt>
    <div class="mw-page-footer" v-show="action==='edit'">
      <div class="btns">
        <!--<div class="btn" @click="showPopup=true">添加员工</div>-->
        <!--<div class="divider"></div>-->
        <div class="btn" @click="addDepFlag = true">添加部门</div>
        <!--<div class="divider"></div>-->
        <!--<div class="btn" @click="showEditAction">批量操作</div>-->
      </div>
    </div>
  </div>
</template>
<script>
  import { mapGetters } from 'vuex'
  import { getDeptInfo,createDepartment } from '@/api/company'
  import invite from '@/components/invite.vue'
  import WX from '@/utils/WX'
  import Contact from '@/utils/Contact'
  import MwPrompt from "../../components/mwPrompt";
  export default {
    name:'myCompany',
    data(){
      return {
        showPopup:false,
        addDepFlag:false,
        depList:[],
        members:[],
        newDepName:'',
        action:'check',
      }
    },
    components:{
      MwPrompt,
      invite
    },
    computed:mapGetters([
      'hideNavbar',
      'chosenCompany',
      'userinfo'
    ]),

    activated(){
      if (this.chosenCompany){
        this.getDepts()
      }
    },
    methods:{
      //获取公司部门以及成员
      getDepts(){
        const that = this;
        if(!that.chosenCompany.company_id){
          return
        }
        const requestData = {
          company_id:that.chosenCompany.company_id,
          dep_id:'all'
        }
        getDeptInfo(requestData).then(res=>{
          console.log(res);
          that.depList = res.deps;
          that.members = res.memberInfos;
        }).catch(error=>{
          console.log(error);
          $toast.show(error.description)
        })
      },
      goDepDetail(item){
//        this.$router.forward('/company/deptDetail/'+item.dep_id+'/'+item.dep_name);
        this.$router.forward({
          path:'/company/deptDetail',
          query:{
            dep_id:item.dep_id,
            dep_name:item.dep_name,
            dep_code:item.dep_code
          }
        });
      },
      turnCheck(){
         //action 从edit 变成check
        this.showPopup = false
        this.addDepFlag = false
        this.action = 'check'
      },
      showEditAction(){
        let that = this;
        let btns = new Object();
        btns['批量移动员工'] = ()=>{
          if(that.members.length===0){
            $toast.show('当前部门无可移动员工')
            return;
          }
          that.$router.forward({path:'/company/batchSelect',query:{members:JSON.stringify(that.members),type:'move',dep_id:null}})
        }
        btns['批量删除员工'] = ()=>{
          if(that.members.length===0){
            $toast.show('当前部门无可删除员工')
            return;
          }
          that.$router.forward({path:'/company/batchSelect',query:{members:JSON.stringify(that.members),type:'del',dep_id:null}})
        }
        $actionSheet.show({
          buttons: btns,
          title:'批量操作',
          cancelText:that.$t('cancel')
        })
      },
      //添加新部门
      addNewDep(){
        const that = this;
        if(this.newDepName.trim()===''){
          $toast.show('部门名称不能为空');
          return;
        }
        let requestData = {
          company_id:that.chosenCompany.company_id,
          dep_name:that.newDepName
        }
        createDepartment(requestData).then(res=>{
          this.addDepFlag = false
          that.getDepts();
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
    }
  }
</script>
<style scoped lang="scss">
  .item-img{
    width: 30px;height: 30px;float:left;
  }
  .text-btn{
    border: 1px solid #4a90e2;
    padding: 3px;
    border-radius: 2px;
    margin-right: 10px;
  }
  .label{
    font-size: 10px;margin-left: 10px;border: 1px solid;padding: 2px;border-radius: 5px;background-color: #416eb6;color: white
  }
</style>

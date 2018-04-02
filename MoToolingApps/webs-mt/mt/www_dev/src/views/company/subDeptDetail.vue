<template>
  <div class="mw-page page company" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>{{dep_name}}</span>
      <i class="right" v-if="action === 'check'" @click="action = 'edit'">管理</i>
      <i class="right" v-if="action === 'edit'" @click="turnCheck">取消</i>
    </div>
    <div class="mw-page-content">
      <list>
        <item>
          <span style="opacity: .6">{{chosenCompany.company_name}}</span>
          <i class="icon ion-ios-arrow-right"></i>
          <span style="opacity: .6">{{upperDepName}}</span>
          <i class="icon ion-ios-arrow-right"></i> {{dep_name}}
        </item>
      </list>
      <list>
        <item class="item-icon-right" v-for="item in depList" key="item.dep_id">
          {{item.dep_name}}
          <span class="item-note">{{item.member_num}}</span>
          <i class="icon ion-ios-arrow-right" style="color: #DDD;"></i>
        </item>
      </list>
      <list>
        <div class="item item-divider-ios">
          成员
        </div>
        <item class="item-icon-right item-contact" v-for="item in members" key="item.members"  @click.native="$router.forward('/company/memberDetail/'+item.member_id)">
          <img :src="item.icon?item.icon:'static/images/company/contact2.png'" class="item-img">
          <span style="line-height: 30px;margin-left: 15px">{{item.work_name}}</span><span class="label" v-if="item.member_id===userinfo.uid">我自己</span>
        </item>
        <item class="item-icon-right" @click.native="showPopup=true">
          <img src="../../../static/images/company/logo_company.png" class="item-img">
          <span style="line-height: 30px;margin-left: 15px">邀请同事加入</span>
        </item>
      </list>
      <div v-show="showPopup">
        <invite v-on:hideInvite="showPopup=false" :dep_id="dep_id" :dep_name="dep_name" type="2"></invite>
      </div>
    </div>
    <mw-prompt :show="addDepFlag" :okFunction="addNewDep" v-model="newDepName" placeholder="请输入部门名称" v-on:hidePop="addDepFlag=false"></mw-prompt>
    <div class="mw-page-footer" v-show="action==='edit'">
      <div class="btns">
        <!--<div class="btn" @click="addDepFlag = true">添加子部门</div>-->
        <!--<div class="divider"></div>-->
        <div class="btn" @click="$router.forward('/company/deptSetting/'+dep_id)">部门设置</div>
        <div class="divider"></div>
        <div class="btn" @click="showEditAction">批量操作</div>
      </div>
    </div>
  </div>
</template>
<script>
  import { mapGetters } from 'vuex'
  import { getDeptInfo,createDepartment } from '@/api/company'
  import invite from '@/components/invite.vue'
  import MwPrompt from "../../components/mwPrompt";
  export default{
    name:'subDeptDetail',
    data(){
      return{
        dep_id:'',
        dep_name:'',
        upperDepName:'',
        showPopup:false,
        addDepFlag:false,
        depList:[],
        members:[],
        newDepName:'',
        action:'check'
      }
    },
    components:{
      MwPrompt,
      invite
    },
    computed:mapGetters([
      'chosenCompany',
      'userinfo'
    ]),
    activated(){
      const param = this.$route.query;
      console.log(param)
      this.dep_id = param.dep_id;
      this.dep_name = param.dep_name;
      this.upperDepName = param.upperDepName
      this.getDepts();
    },
    methods:{
      turnCheck(){
        //action 从edit 变成check
        this.showPopup = false
        this.addDepFlag = false
        this.action = 'check'
      },
      //获取公司部门以及成员
      getDepts(){
        const that = this;
        if(!that.chosenCompany.company_id){
          return
        }
        const requestData = {
          company_id:that.chosenCompany.company_id,
          dep_id:that.dep_id
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
      showEditAction(){
        let that = this;
        let btns = new Object();
        btns['批量移动员工'] = ()=>{
          if(that.members.length===0){
            $toast.show('当前部门无可移动员工')
            return;
          }
          that.$router.forward({path:'/company/batchSelect',query:{members:JSON.stringify(that.members),type:'move',dep_id: this.dep_id}})
        }
        btns['批量删除员工'] = ()=>{
          if(that.members.length===0){
            $toast.show('当前部门无可删除员工')
            return;
          }
          that.$router.forward({path:'/company/batchSelect',query:{members:JSON.stringify(that.members),type:'del',dep_id:this.dep_id}})
        }
        $actionSheet.show({
          buttons: btns,
          title:'批量操作',
          cancelText:that.$t('cancel')
        })
      },
      addNewDep(){
        const that = this;
        if(this.newDepName.trim()===''){
          $toast.show('部门名称不能为空');
          return;
        }
        let requestData = {
          company_id:that.chosenCompany.company_id,
          dep_name:that.newDepName,
          upper_id:that.dep_id?that.dep_id:'undefined'
        }
        createDepartment(requestData).then(res=>{
          that.getDepts();
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
    },
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
</style>

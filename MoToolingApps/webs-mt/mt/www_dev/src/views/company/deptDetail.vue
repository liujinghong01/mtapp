<template>
  <div class="mw-page page" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>{{dep_name}}</span>
      <i class="right" @click="action = 'edit'" v-if="action==='check'">管理</i>
      <i class="right" @click="action = 'check'" v-if="action==='edit'">取消</i>
    </div>
    <div class="mw-page-content">
          <list>
              <item>
                <span style="opacity: .6">{{chosenCompany.company_name}}</span> <i class="icon ion-ios-arrow-right"></i> {{dep_name}}
              </item>
          </list>
          <!--<list v-if="dep_code==='Production_Dept'">-->
          <list v-if="dep_name==='生产部'">
            <swipe-item style="padding: 0px;margin:0px" v-for="(item,index) in depList"  @click.native="goDepDetail(item)"  :obj="item" :canSwipe="true" swipeItemText="删除" v-on:SwipeItemClick="delDept(item,index)">
             <item class="item-icon-right"   style="margin:0px">
                {{item.pg_name}}
                <i class="icon ion-ios-arrow-right" style="color: #DDD;"></i>
              </item>
            </swipe-item>
          </list>
          <list v-else>
            <item class="item-icon-right" v-for="item in depList" key="item.dep_id"  @click.native="goDepDetail(item)">
              {{item.dep_name}}
              <!--<span class="item-note">{{item.member_num}}</span>-->
              <i class="icon ion-ios-arrow-right" style="color: #DDD;"></i>
            </item>
          </list>
          <list>
            <div class="item item-divider-ios">
              成员
            </div>
            <item class="item-icon-right item-contact" v-for="item in members" key="item.members" @click.native="$router.forward('/company/memberDetail/'+item.member_id)">
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
        <div class="btn" @click="addSubDep">添加子部门</div>
        <div class="divider"></div>
        <div class="btn" @click="$router.forward('/company/deptSetting/'+dep_id)">部门设置</div>
        <!--<div class="divider"></div>-->
        <!--<div class="btn" @click="showEditAction">批量操作</div>-->
      </div>
    </div>
  </div>
</template>
<script>
  import { mapGetters } from 'vuex'
  import SwipeItem from "@/components/swipeItem";
  import { getDeptInfo,createDepartment } from '@/api/company'
  import { getAllDeptMember,pgList,pgDelete } from '@/api/production/equipment'
  import invite from '@/components/invite.vue'
  import MwPrompt from "../../components/mwPrompt";
    export default{
      name:'deptDetail',
      data(){
        return{
          dep_id:'',
          dep_name:'',
          dep_code:'',
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
        invite,
        SwipeItem
      },
      computed:mapGetters([
        'chosenCompany',
        'userinfo'
      ]),
      created(){
        // const param = this.$route.query;
        // this.dep_id = param.dep_id;
        // this.dep_name = param.dep_name;
        // this.dep_code = param.dep_code;
      },
      activated(){
        const param = this.$route.query;
        this.dep_id = param.dep_id;
        this.dep_name = param.dep_name;
        this.dep_code = param.dep_code;
        if(this.dep_code=='Production_Dept'){
          this.getPgList()
        }
        this.getDepts();
      },
      methods:{
        delDept(item,index){
          const requestData = {
            pg_info:{
              pg_id:item.pg_id
            }
          }
          pgDelete(requestData).then(res=>{
            $toast.show('删除成功')
            this.depList.splice(index,1)
          }).catch(error=>{
            $toast.show(error.description)
          })
        },
        addSubDep(){
          if(this.dep_code==='Production_Dept'){
            this.$router.forward({path:'/company/productionDeptSub',query:{parent_id:this.dep_id,action:'new',dep_name:'新建子部门',upperDepName:this.dep_name,dep_code:this.dep_code,parentId:this.dep_id}});
          }else{
            this.addDepFlag = true
          }
        },
        turnCheck(){
          this.action = 'check'
          this.addDepFlag = false
          this.showPopup = false
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
            if(this.dep_code!=='Production_Dept'){
              that.depList = res.deps;
            }
            that.members = res.memberInfos;
          }).catch(error=>{
            $toast.show(error.description)
          })
        },
        getPgList(){
          const  requestData={
            page_size:100,
            query:{
              dep_id:this.dep_id
            }
          }
          pgList(requestData).then(res => {
            this.depList = res.pg_list
            // this.getAllDeptMember(){//获取员工列表
          }).catch(error => {
            $toast.show(error.description)
          })
        },
        getAllDeptMember(){//获取员工列表
          const  requestData={
            query:{
              dep_code:this.dep_code
            }
          }
          getAllDeptMember(requestData).then(res => {

          }).catch(error => {
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
            that.$router.forward({path:'/company/batchSelect',query:{members:JSON.stringify(that.members),type:'move',dep_id:this.dep_id}})
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
        showInput(){
          if(this.addDepFlag){
            return
          }else{
            this.addDepFlag = !this.addDepFlag
          }
        },
        hideInput(){
          this.addDepFlag =  false;
          this.newDepName = '';
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
            that.hideInput()
            that.getDepts();
          }).catch(error=>{
            $toast.show(error.description)
          })
        },
        goDepDetail(item){
          if(this.dep_code==='Production_Dept'){
            this.$router.forward({path:'/company/' +
              'productionDeptSub',query:{dep_id:item.pg_id,dep_name:item.pg_name,parent_id:this.dep_id,upperDepName:this.dep_name,action:'check'}});
          }else{
            this.$router.forward({path:'/company/subDeptDetail',query:{dep_id:item.dep_id,dep_name:item.dep_name,upperDepName:this.dep_name}});
          }
        },
      },
    }
</script>
<style scoped lang="scss">
  .label{
    transform: scale(0.8,0.8);
    -webkit-transform:scale(0.8,0.8); /* Chrome, Safari, Opera */
    -moz-transform: scale(0.8,0.8);
    color: #416eb6;
    font-size: 12px;
    padding-left: 5px;
  }
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

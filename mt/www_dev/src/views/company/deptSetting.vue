<template>
  <div class="mw-page page company" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>部门信息</span>
      <!--<i class="right" v-if="action === 'check'" @click="action = 'edit'">管理</i>-->
      <i class="right"></i>
    </div>
    <div class="mw-page-content">
      <list>
        <cst-item label="部门名称" v-model="departInfo.dep_name" :action="action" ></cst-item>
        <cst-item label="部门主管" v-model="departInfo.dep_admin_name"
                  action="check" :canEdit="false" :hasArrow="action==='edit'" @click.native="selectAdmin"
        ></cst-item>
        <!--<cst-item label="所属部门" v-model="departInfo.parent_dep_name"   @click.native="changeUpDept"-->
                  <!--:action="action" :canEdit="false"></cst-item>-->
      </list>
    </div>
    <div class="mask" v-show="showFilter" @click="showFilter = false"></div>
    <section style="display: flex;flex-direction: column" :class="['filter',showFilter?'active':'']">
      <div class="side-title" v-show="showFilter">
        选择
      </div>
      <div class="side-content">
        <div v-for="item in depNames">
          <mw-radio :obj="item" :hasArrow="false" :iClick="itemClick"><span style="float:left">{{item.name}}</span></mw-radio>
        </div>
      </div>
      <div class="side-footer"  v-show="showFilter">
        <div class="filter-btns">
          <div class="filter-btn" @click="ok">
            确定
          </div>
        </div>
      </div>
    </section>
    <div class="mw-page-footer" v-show="action === 'edit'">
      <div class="btns">
        <div class="btn" @click="updateInfo">保存</div>
      </div>
    </div>

    <time-picker
      v-on:chosen="chosen"
      :showPicker="showPicker"
      :pickerType="pickerType"
      :pickerValue="pickerValue"
      :pickerList='pickerList'
      v-on:hideMask="showPicker=false"></time-picker>
  </div>
</template>
<script>
  import {getDeptDetail,updateDeptInfo,getSubDeps,getAllDeptMember} from '@/api/company'
  import {mapGetters} from 'vuex'
  import timePicker from "@/components/timePicker"
  import CstItem from "../../components/cstItem";
  import MwRadio from "../../components/mwRadio";

  export default{
    components: {
      MwRadio,
      CstItem,
      timePicker
    },
    name:'deptSetting',
    data(){
      return {
        selectType:'',
        showPicker:false,
        pickerType:'radio',
        pickerValue:'',
        pickerList:[],
        member_list:[],
        dep_id:'',
        departInfo:{
          dep_name:''
        },
        action:'edit',
        showFilter:false,
        filterList:[],
        depList : [],
        depNames : [],
        chosenItem:{}
      }
    },
    computed:mapGetters([
      'chosenCompany'
    ]),
    mounted(){
      this.dep_id = this.$route.params.dep_id;
      this.getDetail()
      // this.getSubDeps()
      this.getAllDeptMember()
    },
    methods:{
      getAllDeptMember(){
        const requestData = {
          query:{
            "dep_code": '',
            "dep_id":this.dep_id
          }

        }
        getAllDeptMember(requestData).then(res=>{
          console.log(res)
          this.member_list=res.memberInfos
        }).catch(error=>{
          $toast.show(error.description,2000)
          console.log(error)
        })
      },
      selectAdmin(){
        if (this.action==='check') {
          return
        }else{
          if(this.member_list.length>0){
            this.selectType='admin'
            this.pickerValue={
              val:this.departInfo.dep_admin_name,
              id:this.departInfo.dep_admin_id
            }
            this.pickerList=[]
            this.member_list.forEach((e,i)=>{
              this.pickerList.push({
                val:e.work_name,
                id:e.uid
              })
            })
            this.showPicker=true
          }else{
//            $toast.show('请先给该部门添加人员',600)
          }
        }
      },
      chosen(e){
        if(this.selectType==='admin'){
          this.departInfo.dep_admin_name=e.val
          this.departInfo.dep_admin_id=e.id
        }
      },
      getDetail(){
        const requestData = {
          "company_id": this.chosenCompany.company_id,
          "dep_id":this.dep_id
        }
        getDeptDetail(requestData).then(res=>{
          console.log(res);
          this.departInfo = res.dep_info;
        }).catch(error=>{
          console.log(error)
        })
      },
      //更换admin
      changeAdmin(){
        if (this.action==='check') {
          return
        }

      },
      //更换上级部门
      changeUpDept(){
        if (this.action==='check') {
          return
        }
        this.showFilter = true
      },
      updateInfo(){
        console.log(this.departInfo)
        if (!this.departInfo.dep_name.trim()) {
          $toast.show('部门名称不能为空')
          return
        }
        const requestData = {
          dep_info:this.departInfo
        }
        updateDeptInfo(requestData).then(res=>{
          $toast.show('修改成功')
          this.action = 'check'
          this.departInfo = res.dep_info
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      getSubDeps(){
        const that = this;
        if(!that.chosenCompany.company_id){
          return
        }
        const requestData = {
          company_id:that.chosenCompany.company_id,
        }
        getSubDeps(requestData).then(res=>{
          let tmpDeps = [];
          let tmpNames = [];
          for(let i in res.deps){
            tmpNames.push({id:res.deps[i].dep_id,name:res.deps[i].dep_name,real_name:res.deps[i].dep_name})
            tmpDeps.push(res.deps[i]);
            if(res.deps[i].deps && res.deps[i].deps.length > 0){
              for(let j in res.deps[i].deps){
                tmpNames.push({id:res.deps[i].deps[j].dep_id,name:res.deps[i].dep_name+'>'+res.deps[i].deps[j].dep_name,real_name:res.deps[i].deps[j].dep_name});
                tmpDeps.push(res.deps[i].deps[j])
              }
            }
          }
          that.depList = tmpDeps;
          that.depNames = tmpNames;
        }).catch(error=>{
          console.log(error);
          $toast.show(error.description)
        })
      },
      itemClick(item){
        if(item===this.chosenItem){
          return
        }
        this.chosenItem.chosen = false
        this.chosenItem = item
      },
      ok(){
        if(parseInt(this.dep_id) === parseInt(this.chosenItem.id)){
          this.showFilter = false
          $toast.show('所属部门不能为自身!')
          return
        }
        this.showFilter = false
        this.departInfo.parent_dep_name = this.chosenItem.real_name
        this.departInfo.parent_dep_id = this.chosenItem.id
      }
    }
  }
</script>
<style scoped lang="scss">
  .mask{
    position: absolute;
    width: 100%;
    height: 100%;
    top:0;
    left: 0;
    z-index: 16;
    background-color: #2e2e2d;
    opacity: .4;
  }
  .filter{
    width: 0;
    height: 0;
    transition:width .2s;
    -moz-transition:width .2s; /* Firefox 4 */
    -webkit-transition:width .2s; /* Safari and Chrome */
    -o-transition:width .2s; /* Opera */
  }
  .filter.active{
    width: 80%;
    height: 100%;
    background-color: #F3F6FB;
    position: absolute;
    top:0;
    right: 0;
    z-index: 17;

  }
  .side-title{
    height: 40px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .side-content{
    flex: 1;
    overflow-x: hidden;
    overflow-y: auto;
  }
  .side-footer{
    height: 50px;
  }
  .filter-btns{
    display: flex;
    height: 100%;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);
    .filter-btn{
      flex: 1 0 auto;
      text-align: center;
      display: flex;
      justify-content: center;
      align-items: center;
    }
  }
</style>

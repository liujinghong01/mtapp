<template>
  <div class="mw-page page company" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>批量选择</span>
      <i class="right" @click="$router.go(-1)">取消</i>
    </div>
    <div class="mw-page-content">
      <von-checkbox :options="filterName()" v-model="chosenTopics" theme="positive"></von-checkbox>
    </div>
    <div class="mask" v-show="showFilter" @click="showFilter = false"></div>
    <section style="display: flex;flex-direction: column" :class="['filter',showFilter?'active':'']">
      <div class="side-title" v-show="showFilter">
        选择
      </div>
      <div class="side-content">
        <div v-for="item in depNames">
          <mw-radio :obj="item" :hasArrow="false" :chosenObj="chosenItem" :iClick="itemClick"><span style="float:left">{{item.name}}</span></mw-radio>
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
    <div class="mw-page-footer">
      <div class="btns">
        <div style="width: 80%;height: 100%;overflow: scroll">
          <div class="selected-label" v-for="item in chosenTopics">{{members[item].work_name}}</div>
        </div>
        <div class="btn" @click="moveMember"  style="background-color: #416eb6;color: white;padding: 15px 0"  v-if="type==='move'">确认({{chosenTopics.length}})</div>
        <div class="btn" @click="delMember"   style="background-color: #416eb6;color: white;padding: 15px 0" v-if="type==='del'">删除({{chosenTopics.length}})</div>
      </div>
    </div>
    </div>
</template>
<script>
  import {deleteDepMembers,getSubDeps,batchMoveMember}  from '@/api/company'
  import { mapGetters } from 'vuex'
  import MwRadio from "../../components/mwRadio";
  export default {
    components: {MwRadio},
    name:'batchSelect',
    data(){
      return {
        chosenTopics: [],
        type:'move',
        members:[],
        dep_id:'',
        showFilter:false,
        filterList:[],
        depList : [],
        depNames : [],
        chosenItem:{}

      }
    },
    mounted(){
      this.members = JSON.parse(this.$route.query.members)
      this.dep_id = this.$route.query.dep_id
      this.type = this.$route.query.type
    },
    computed:{
      chooseMemberIds(){
        let a = [],that = this;
        for(let i in that.chosenTopics) {
          a.push(that.members[that.chosenTopics[i]].member_id)
        }
        return a;
      },
      ...mapGetters(['chosenCompany'])
    },
    methods:{
      menuClick(){
        this.$router.go(-1);
      },
      filterName(){
        let names = [];
        for(let i in this.members){
          names.push(this.members[i].work_name)
        }
        return names;
      },
      delMember(){
        if(this.chooseMemberIds.length===0){
            $toast.show('请选择成员')
            return
        }
        const requestData = {
          "dep_id":'',
          "company_id":this.chosenCompany.company_id,
          "member_ids":this.chooseMemberIds
        }
        deleteDepMembers(requestData).then(res=>{
          history.back();
        }).catch(error=>{
          console.log(error.description)
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
            if(res.deps[i].deps.length>0){
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
        const that = this;
        const requestData = {
          company_id:that.chosenCompany.company_id,  //公司id
          dep_ids:[this.chosenItem.id],          //  将要转入的部门id
          member_ids:that.chooseMemberIds,       //  转移部门的人员id
          member_dep_id:that.dep_id             //   需调整部门人员的原部门id
        }
        batchMoveMember(requestData).then(res=>{
          history.go(-1);
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      moveMember(){
        if(this.chooseMemberIds.length===0){
          $toast.show('请选择成员')
          return
        }
        this.getSubDeps()
        this.showFilter = true

//        this.$router.forward({path:'/company/chooseDep/'+this.dep_id+'/batch',query:{members:this.chooseMemberIds,type:'del',dep_id:'null'}})
      }
    }
  }
</script>
<style scoped lang="scss">
  .div-bottom{
    position: fixed;
    bottom: 0;
    height: 50px;
    width: 100%;
    display: flex;
  }
  .selected-label{
    float: left;
    width: 20%;
    height: 60%;
    border-radius: 40px;
    border:1px solid #416eb6;
    color: #416eb6;
    line-height: 30px;
    text-align: center;
    margin: 10px 2px;
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
  }
  .div-bottom .btn-right{
    width: 80px;
    position: absolute;
    right: 5px;
  }
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

<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>人员列表</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content ">
      <div class="content" >
        <scroll class="page-content"  style="-webkit-overflow-scrolling: touch" >
          <item v-for="(item,index) in list" :class="item.check?'active':''"  @click.native="checked(item,index)">
            <div class="checkbox">
              <i class="icon ion-android-checkbox-outline-blank"></i>
              <i class="icon ion-android-checkbox"></i>
            </div>
            <span>{{ item.work_name }}</span>
          </item>
          <div  slot="infinite" class="text-center"></div>
        </scroll>
      </div>
    </div>
    <div class="mw-page-footer">
      <div class="btns">
        <div class="btn" @click="ascertain">
          确定
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  import { getDeptInfo } from '@/api/company'
  import { pgDetail,pgMemberModify,pgMemberDelete,getAllMembers } from '@/api/production/equipment'
  import SwipeItem from "../../../components/swipeItem"
  import sess from '@/utils/sess'
  export default{
    name:'personnelList',
    components: {SwipeItem},
    data(){
      return {
        dep_id:'',
        parent_id:'',
        type:'',
        list:[
          // {name:'黄杰',id:1,check:true},
        ]
      }
    },
    computed:mapGetters([
      'chosenCompany',
      'userinfo'
    ]),
    activated(){
    },
    mounted(){
      const param = this.$route.query;
      this.type=param.type
      this.dep_id = param.dep_id
      if(this.type=='dep'){
        this.pgDetail()
      }else if(this.type=='parent'){
        this.parent_id=param.parent_id
        this.getDepts()
      }



    },
    methods: {
      onInfinite(){},
      onRefresh(){},
      ascertain(){
        let personnel=[]
        this.list.forEach((e,i)=>{
          if(e.check){
            personnel.push({
              check_uid_name:e.work_name,
              check_uid:e.member_id
            })
          }
        })
        if(personnel.length>0){
          sess.set('personnel',JSON.stringify(personnel))
          this.$router.go(-1)
        }else{
          $toast.show('请选择人员')
          return
        }
      },
      //获取公司部门以及成员
      pgDetail(){
        const  requestData={
          query:{
            pg_id:this.dep_id
          }
        }
        pgDetail(requestData).then(res => {
          let staffList=[]
          if(sess.get('staffList')!==null){
            staffList=JSON.parse(sess.get('staffList'))
            sess.remove('staffList')
          }
          this.list=res.pg_info.memeber_list
          if(staffList.length>0){
            this.list.forEach((e,i)=>{
              staffList.forEach((item,index)=>{
                if(e.member_id==item.check_uid&&e.work_name==item.check_uid_name){
                  this.$set(e,'check',true)
                }
              })
            })
          }
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      //获取公司部门以及成员
      getDepts(){
        const that = this;
        if(!that.chosenCompany.company_id){
          return
        }
        const requestData = {
          company_id:that.chosenCompany.company_id,
          // dep_id:that.parent_id
          dep_id:''
        }
        getAllMembers(requestData).then(res=>{
          that.list = res.memberInfos;
          if(sess.get('memeber_list')!==null){
            let memeber_list=JSON.parse(sess.get('memeber_list'))
            that.list.forEach((e,i)=>{
              memeber_list.forEach((item,index)=>{
                if(e.member_id==item.member_id){
                  this.$set(e,'check',true)
                }
              })
            })
          }
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      //生产小组员工新增
      pgMemberModify(item,index){
        const requestData = {
          member_info:{
            furlough_end_date:null,//	休假开始日期
            furlough_start_date:null,//		休假结束日期
            is_leader:null,//		是否为生产小组组长	0否，1是；
            member_id:item.member_id,//		成员Id
            pg_id:this.dep_id,//		生产小组Id
            position_code:null,//		职位编码
            position_name:'',//		职位Id
            work_mail:item.work_mail,//		工作邮箱
            work_name:item.work_name,//		工作名字
            work_phone:item.work_phone,//		工作电话
            work_state:item.work_state,//		工作状态
          }
        }
        pgMemberModify(requestData).then(res=>{
          $toast.show('添加人员成功')
          this.$set(this.list[index],'check',true)
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      //生产小组删除员工
      pgMemberDelete(item,index){
        const requestData = {
          member_info:{
            member_id:item.member_id,//		成员Id
            pg_id:this.dep_id,//		生产小组Id
          }
        }
        pgMemberDelete(requestData).then(res=>{
          $toast.show('删除人员成功')
          this.$set(this.list[index],'check',false)
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      checked(item,index){
        if(this.type=='dep'){
          if(item.check==undefined){
            this.$set(item,'check',true)
          }else{
            item.check=!item.check
          }
        }else if(this.type=='parent'){
          if(item.check==undefined){
            this.pgMemberModify(item,index)
          }else {
            if(item.check){
              this.pgMemberDelete(item,index)
            }else{
              this.pgMemberModify(item,index)
            }

          }
        }

      }

    }
  }
</script>

<style lang="scss" scoped>
.item{
  padding-left:40px ;
  &>div{
    line-height: 20px
  }
  &.active{
    background: #fff;
    &>div.checkbox>i{
      color: #416eb6;
      &.ion-android-checkbox{
        display: block;
      }
    }
  }
  .checkbox{
    width: 40px;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
    i{
      position: absolute;
      width: 14px;
      height: 20px;
      font-size: 18px;
      left: 50%;
      top: 50%;
      margin-top: -10px;
      margin-left: -5px;
      &.ion-android-checkbox{
        display: none;
      }
    }
  }
}


</style>

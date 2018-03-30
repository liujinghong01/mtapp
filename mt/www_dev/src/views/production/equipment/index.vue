<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span v-if="action==='new'">新建子部门</span>
      <span v-else>{{ pg_info.pg_name }}</span>
      <i v-if="action==='check'" class="right" @click="Edit">编辑</i>
      <i v-if="action==='action'" class="right" @click="cancelEdit">取消</i>
      <i v-if="action==='new'" class="right"></i>
    </div>
    <div class="mw-page-content ">
      <cst-item label="部门名称" placeholder="请输入部门名称" v-model="pg_info.pg_name" :action="action" ></cst-item>
      <cst-item label="部门主管" v-model="pg_info.dep_admin" action="check" :canEdit="false" :hasArrow="action==='action'" @click.native="selectAdmin"></cst-item>
      <cst-item label="上级部门" v-model="pg_info.parent_dep_name" action="check" ></cst-item>
      <item  @click.native="selectDepType">部门类型 <span :class="action==='check'?'active':''">{{ pg_info.pg_type| pgType }} <i v-show="action!=='check'" class="icon ion-ios-arrow-right arrow " ></i> </span></item>

      <cst-item label="设备列表" v-show="action==='check'&&pg_info.pg_type==0"  :action="'check'" :hasArrow="true" @click.native="$router.forward({path:'productionDeptSub/equipmentList',query:{dep_id:dep_id,dep_name:dep_name}})"></cst-item>
      <cst-item label="设备点检分配" v-show="action==='check'&&pg_info.pg_type==0"  :action="'check'" :hasArrow="true" v-model="num" @click.native="$router.forward({path:'productionDeptSub/checklist',query:{parent_id:parent_id,dep_id:dep_id}})"></cst-item>
      <cst-item label="部门日历设置"  v-model="pg_info.calendar_name" action="check" :canEdit="false" :hasArrow="action==='action'" @click.native="selectCalendar"></cst-item>

      <div v-show="action=='check'">
        <div class="item-divider"  style="padding-left: 15px">人员列表</div>

        <swipe-item style="margin: 0px;border: none;border-bottom: 1px solid #ddd;" v-for="(item,index) in pg_info.memeber_list" class="req-item" @click.native=""  :obj="item" :canSwipe="true" swipeItemText="删除" v-on:SwipeItemClick="tmplDelete(item,index)">
          <!--<item class="item-icon-right item-contact"    @click.native="$router.forward('/company/memberDetail/'+item.member_id)">-->
            <img src="static/images/company/contact2.png" class="item-img">
            <span style="line-height: 30px;margin-left: 15px">{{ item.work_name }}</span>
            <!--<span class="label" v-if="item.member_id===userinfo.uid">我自己</span>-->
        </swipe-item>
        <item style="border-top: 1px solid #ddd;" class="item-icon-right" @click.native="config">
          <img src="static/images/company/logo_company.png" class="item-img">
          <span style="line-height: 30px;margin-left: 15px">配置人员</span>
        </item>
      </div>


      <div class="mw-page-footer" v-show="action!=='check'">
        <div class="btns">
          <div class="btn" @click="pgModify">
            提交
          </div>
        </div>
      </div>

    </div>
    <time-picker v-on:chosen="chosen"
                 :showPicker="showPicker"
                 :pickerType="pickerType"
                 :pickerValue="pickerValue"
                 :pickerList='pickerList'
                 v-on:hideMask="showPicker=false">
    </time-picker>
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  import { getDeptInfo } from '@/api/company'
  import { pgModify,getAllDeptMember,pgDetail,pgMemberDelete } from '@/api/production/equipment'
  import { calendarList} from '@/api/production/shopCalendar'
  import timePicker from "../../../components/timePicker"
  import CstItem from "../../../components/cstItem"
  import SwipeItem from "../../../components/swipeItem";
  import sess from '@/utils/sess'
  export default{
    name:'productionDeptSub',
    components: {timePicker,CstItem,SwipeItem},
    computed:mapGetters([
      'chosenCompany',
      'userinfo'
    ]),
    data(){
      return {
        action:'new',
        dep_code:'',
        currSelect:'',//当前选择项
        showPicker:false,
        pickerType:'',
        pickerValue:'',
        pickerList:'',
        num:10,
        //页面传参
        dep_id:'',
        dep_name:'',
        parent_id:'',
        upperDepName:'',
        calendarArr:[],
        depList:[],
        depType:[
          {val:'机器',id:'0'},
          {val:'人工',id:'1'}
        ],
        pg_info:{//
          assign_to_device:'',//
          avail_status:'',//	有效状态
          calendar_id	:'',//生产小组日历Id
          calendar_name:'',//	生产小组日历名称
          capa_ratio:'',//	能力利用率
          day_hours:'',//	单人每日工时
          dep_admin:'',//	部门主管名字
          dep_admin_id:'',//	部门主管Id
          labour_ratio:'',//	人工工时系数
          overload_ratio:'',//	允许过载率
          parent_dep_id:'',//	上级部门Id
          parent_dep_name:'',//	上级部门名称
          pg_desc:'',//	描述
          pg_id:'',//	生产小组Id
          pg_name	:'',//生产小组名称
          pg_pic:'',//	生产小组图标
          pg_type:'0',//	类型
          pg_type_name:'机器',
          queue_length:'',//	排队时间
          std_unit:'',//	标准时间单位
          trans_length	:'',//搬运时间
        }

      }
    },
    activated(){
      // this.getPersonnel()
      if(this.action!=='new'){
        this.pgDetail()
      }
    },
    mounted(){
      const param = this.$route.query;
      this.parent_id=param.parent_id
      this.action=param.action
      this.dep_name=param.dep_name
      this.dep_id = param.dep_id
      this.upperDepName=param.upperDepName
      this.pg_info.parent_dep_name=this.upperDepName
      this.pg_info.dep_name=this.dep_name
      if(this.action==='new'){
        this.dep_code=param.dep_code
        this.pg_info.parent_dep_id=param.parentId
        this.pg_info.dep_name=''
        // this.getAllDeptMember()//获取部门人员列表
        this.getCalendarList()//获取日历列表
        this.getDepts()
      }else{
        this.pgDetail()
      }

    },
    filters:{ // 过滤器
      pgType:function ( value ) { // 局部过滤器
        if(value==0){
          return '机器'
        }else if(value==1){
          return '人工'
        }
      }
    },
    methods: {
      config(){
        let memeber_list=this.pg_info.memeber_list
        if(memeber_list.length>0){
          sess.set('memeber_list',memeber_list)
        }
        this.$router.forward({path:'productionDeptSub/personnelList',query:{dep_id:this.dep_id,parent_id:this.parent_id,type:'parent'}})
      },
      getPersonnel(){
        let personnel=JSON.parse(sess.get('personnel'))//depList
      },
      //获取公司部门以及成员
      getDepts(){
        const that = this;
        if(!that.chosenCompany.company_id){
          return
        }
        const requestData = {
          company_id:that.chosenCompany.company_id,
          dep_id:that.parent_id
        }
        getDeptInfo(requestData).then(res=>{
          if(res.memberInfos.length>0){
            res.memberInfos.forEach((e,i)=>{
              this.depList.push({
                val:e.work_name,
                id:e.member_id
              })
            })
          }
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      Edit(){
        this.pg_info.memeber_list.forEach((e,i)=>{
          this.depList.push({
            val:e.work_name,
            id:e.member_id
          })
        })
        this.action='action'
        if(!(this.calendarArr.length>0)){//点击编辑按钮获取日历列表
          this.getCalendarList()
        }
      },
      cancelEdit(){
        this.action='check'
        this.pgDetail()
      },
      selectCalendar(){
        if(this.action=='check'){
          return
        }
        this.currSelect='Calendar'//记住当前选择项
        this.showPicker=true
        this.pickerType='radio'
        this.pickerValue={
          val:this.pg_info.calendar_name,
          id:this.pg_info.calendar_id
        }
        this.pickerList=this.calendarArr
      },
      selectAdmin(){
        if(this.action=='check'){
          return
        }
        this.currSelect='Admin'//记住当前选择项
        this.showPicker=true
        this.pickerType='radio'
        this.pickerValue={
          val:this.pg_info.dep_admin,
          id:this.pg_info.dep_admin_id
        }
        this.pickerList=this.depList
      },
      selectDepType(){
        if(this.action=='check'){
          return
        }
        this.currSelect='DepType'
        this.showPicker=true
        this.pickerType='radio'
        this.pickerValue={
          val:this.pg_info.pg_type_name,
          id:this.pg_info.pg_type
        }
        this.pickerList=this.depType
      },
      chosen(e){
        if(this.currSelect==='Admin'){
          this.pg_info.dep_admin=e.val
          this.pg_info.dep_admin_id=e.id
          this.currSelect=''
        }else if(this.currSelect==='DepType'){
          this.pg_info.pg_type_name=e.val
          this.pg_info.pg_type=e.id
          this.currSelect=''
        }else if(this.currSelect==='Calendar'){
          this.pg_info.calendar_name=e.val
          this.pg_info.calendar_id=e.id
          this.currSelect=''
        }

      },
      //生产小组新增，修改
      pgModify(){
        if(this.pg_info.pg_name===''||this.pg_info.pg_name==undefined){
          $toast.show('请输入生产小组名称')
          return
        }
        if(this.pg_info.dep_admin_id===''||this.pg_info.dep_admin_id==undefined){
          $toast.show('请选择部门主管')
          return
        }
        if(this.pg_info.calendar_id===''||this.pg_info.calendar_id==undefined){
          $toast.show('请为部门设置工作日历')
          return
        }
        const  requestData={
          pg_info:this.pg_info
        }
        pgModify(requestData).then(res => {
          $toast.show('生产小组新建成功')
          this.$router.go(-1)
          }).catch(error => {
            $toast.show(error.description)
          })
      },
      //生产小组人员删除
      pgMemberDelete(item,index){
        const  requestData={
          query:{
            pg_id:this.dep_id
          }
        }
        pgMemberDelete(requestData).then(res => {
          this.pg_info.memeber_list.splice(index,1)
          $toast.show('人员删除成功')
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      //查询生产小组详情
      pgDetail(){
        const  requestData={
          query:{
            pg_id:this.dep_id
          }
        }
        pgDetail(requestData).then(res => {
          this.pg_info=res.pg_info
          console.log(this.pg_info)
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      getAllDeptMember(){
        const  requestData={
          query:{
            dep_code:this.parent_id
          }
        }
        getAllDeptMember(requestData).then(res => {


        }).catch(error => {
          $toast.show(error.description)
        })
      },
      getCalendarList(){
        const  requestData={
          curr_page:1,
          page_size:100,
          query:{
            calendar_name:''
          }
        }
        calendarList(requestData).then(res => {
            res.calendar_list.forEach((e,i)=>{
              this.calendarArr.push({
                id:e.calendar_id,
                val:e.calendar_name
              })
          })
        }).catch(error => {
          $toast.show(error.description)
        })
      },
    }
  }
</script>

<style lang="scss" scoped>
  .item-img{
    width: 30px;height: 30px;float:left;
  }
  .item{
       &>span{
         float: right;
         padding-right: 15px;
         &.active{
           padding-right: 0px;
         }
         .arrow{
           color: #DDD;
           position: absolute;
           right: 15px;
           top: 14px;
           font-size: 20px;}
       }
     }
</style>

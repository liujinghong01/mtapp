<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>设备详情</span>

      <i v-if="action==='check'" class="right" @click="Edit">编辑</i>
      <i v-if="action==='action'" class="right" @click="cancelEdit">取消</i>
      <i v-if="action==='new'" class="right"></i>
    </div>
    <div class="mw-page-content has-tab">
      <div style="height: 60px" v-show="action==='check'">
        <tabs :tab-items="tabs" :tab-index="tabIndex" :on-tab-click="onTabClick"></tabs>
      </div>
      <div class="content" v-show="tabIndex===0">
        <scroll class="page-content"  style="-webkit-overflow-scrolling: touch">
          <cst-item label="设备名称" placeholder="请输入设备名称" v-model="device_info.device_name"  :action="action==='check'?'check':'action'" ></cst-item>
          <cst-item label="设备型号" placeholder="请输入设备名称" v-model="device_info.device_model"  :action="action==='check'?'check':'action'" ></cst-item>
          <cst-item label="设备编码" placeholder="请输入设备编码" v-model="device_info.device_no"  :action="action==='check'?'check':'action'" ></cst-item>
          <cst-item label="设备品牌" placeholder="请输入设备品牌" v-model="device_info.device_brand"  :action="action==='check'?'check':'action'" ></cst-item>
          <cst-item label="设备所在地址" placeholder="请输入设备所在地址" v-model="device_info.device_addr"  :action="action==='check'?'check':'action'" ></cst-item>

<!--device_info.prod_date//生产日期    device_info.device_addr//请输入设备所在地址  device_info.device_brand//请输入设备编码  device_info.device_model//请输入设备名称-->

          <cst-item v-if="action==='check'" label="生产日期" v-model="device_info.prod_date"  action="check" ></cst-item>
          <datepicker  v-else  v-model="device_info.prod_date" label="生产日期" date-format="yyyy-mm-dd"></datepicker>
          <item  >
            <span>设备是否共享</span>
            <label><input name="isShare"  :disabled="action==='check'" type="radio" value="1" v-model="device_info.is_share"/> 是</label>
            <label><input name="isShare"  :disabled="action==='check'" type="radio" value="0" v-model="device_info.is_share"/> 否</label>
          </item>
          <cst-item style="margin-top: 1px" label="使用年限(年）" v-model="device_info.work_life" placeholder="请输入使用年限" :action="action==='check'?'check':'action'" ></cst-item>
          <cst-item label="状态" v-show="action === 'check' " v-model="device_info.device_status"  action="check" ></cst-item>
          <cst-item label="设备负责人" v-model="device_info.manager_name" action="check" :canEdit="false" :hasArrow="action!=='check'" @click.native="selectAdmin"></cst-item>
        </scroll>
      </div>
      <div class="content" v-show="tabIndex===1">
        <scroll class="page-content"  style="-webkit-overflow-scrolling: touch">
          <item  v-for="(item,index) in  maintain_list" @click.native="goDetail(item.voucher_no)">
            <div >本次检修开始时间:<span >{{ item.start_date }}</span></div>
            <div >本次检修结束时间:<span >{{ item.end_date }}</span></div>
            <div >检修时长:<span >{{ item.length }}</span></div>
            <div >检修周期:<span >{{ item.cycle }}</span></div>
            <div >下次计划检修开始时间:<span >{{ item.next_start_date }}</span></div>
            <div >下次计划检修结束时间:<span >{{ item.next_end_date }}</span></div>

          </item>
        </scroll>
      </div>
    </div>
    <div class="mw-page-footer">
      <div class="btns">
        <div class="btn" v-show="action==='check'&&device_info.device_status!=2" @click="deviceMaintainStart">
          开始检修
        </div>
        <div class="btn" v-show="action==='check'&&device_info.device_status==2" @click="$router.forward({path:'overhaulForm',query:{action:'new',device_id:device_id}})">
          完成检修
        </div>
        <div class="btn" v-show="action!=='check'" @click="DeviceModify">
          提交
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
  import { pgDeviceModify,deviceDetail,maintenanceRecord,deviceMaintainStart } from '@/api/production/equipment'
  import CstItem from "../../../components/cstItem"
  import timePicker from "../../../components/timePicker"
  export default{
    name:'equipment',
    components: { CstItem,timePicker },
    data(){
      return {
        currSelect:'',//当前选择项
        showPicker:false,
        pickerType:'',
        pickerValue:'',
        pickerList:'',
        action:'action',
        tabs:['基本信息','维修记录'],
        tabIndex:0,
        parent_id:'',
        device_id:'',
        device_info:{//device_info.device_status
          device_addr:'',
          device_brand:''	,
          device_id:''	,
          device_model:''	,
          device_name:''	,
          device_no:'',
          device_pic:'',
          device_status:0,
          device_status_name:'',
          is_auto_pause:'',
          is_auto_prod	:'',
          is_share:1,
          labour_ratio:null,
          last_maintain_reason:'',
          pg_id	:'',
          pg_name	:'',
          manager_name:'请选择',
          manager_id:'',
          prod_date:'',
          work_life:'',
          check_allocate_list:[
            {check_uid:'',check_uid_name:'11'}
          ]

        },
        maintain_list:[
          // {
          //   cycle	:'1年',
          //   device_id:'	设备ID',
          //   device_name:'	设备名称',
          //   start_date:'	2017-12-20',
          //   end_date:'	2017-12-25',
          //   is_maintained:'	是否检修完毕',
          //   length:'	5(天）',
          //   maintain_no:'	检修单号',
          //   next_end_date:'	2018-12-25',
          //   next_start_date:'	2018-12-20',
          //   prev_end_date:'	20186-12-25',
          //   reason:'	检修原因',
          // }
        ],
        depList:[
          // {val:'',id:''},
        ],
      }
    },
    computed:mapGetters([
      'chosenCompany',
      'userinfo'
    ]),
    activated(){
      if(this.action!=='new'){
        this.deviceDetail()
      }
    },
    mounted(){
      const param=this.$route.query
      this.device_info.pg_id=param.pg_id
      this.device_info.pg_name=param.pg_name
      this.parent_id=param.parent_id
      this.device_info.pg_name=param.pg_name
      this.action=param.action
      if(this.action==='new'){
        this.getDepts()
      }else{
        this.device_id=param.device_id
        this.deviceDetail()
      }

    },
    methods: {
      cancelEdit(){
        this.action='check'
        this.deviceDetail()
      },
      Edit(){
        this.action='action'
        this.tabIndex=0
        this.getDepts()
      },
      deviceDetail(){//获取设备详情
        const requestData = {
          query:{
            device_id:this.device_id
          }
        }
        deviceDetail(requestData).then(res=>{
          this.device_info=res.device_info
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      goDetail(){
        this.$router.forward({path:'overhaulForm',query:{action:'new',device_id:this.device_id}})
      },
      maintenanceRecord(){//设备维修记录
        const requestData = {
          query:{
            device_id:this.device_id
          }
        }
        maintenanceRecord(requestData).then(res=>{
          this.maintain_list=res.check_detail_list
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      deviceMaintainStart(){//开始检修
        const requestData = {
          device_maintain_info:{
            device_id:this.device_id
          }
        }
        deviceMaintainStart(requestData).then(res=>{
          this.device_info.device_status=2
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      onTabClick(index) {
        if(index===this.tabIndex) {
          return
        }
        this.tabIndex = index
        if(this.tabIndex==1&&this.maintain_list.length<1){
          this.maintenanceRecord()
        }
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
              that.depList.push({
                id:e.member_id,
                val:e.work_name
              })
            })
          }
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      DeviceModify(){//device_info.device_name
        if(this.device_info.device_name===''){
          $toast.show('请输入设备名称')
          return
        }
        // if(this.device_info.device_model===''){
        //   $toast.show('请输入设备型号')
        //   return
        // }
        if(this.device_info.device_brand===''){
          $toast.show('请输入设备编码')
          return
        }
        if(this.device_info.device_addr===''){
          $toast.show('请输入设备所在地址')
          return
        }
        if(this.device_info.prod_date===''){
          $toast.show('请选择生产日期')
          return
        }
        if(this.device_info.work_life===''){
          $toast.show('请选择使用年限')
          return
        }
        // if(this.device_info.check_allocate_list.check_uid_name===undefined){
        //   $toast.show('请选择设备负责人')
        //   return
        // }
        const requestData = {
          device_info:this.device_info
        }
        pgDeviceModify(requestData).then(res => {
          if(this.action==='new'){
            $toast.show('添加设备成功')
            this.$router.go(-1)
          }else{
            $toast.show('设备修改成功')
            this.action='check'
          }

        }).catch(error => {
          $toast.show(error.description)
        })
      },
      selectAdmin(){
        if(this.action=='check'){
          return
        }
        this.currSelect='Admin'//记住当前选择项
        this.showPicker=true
        this.pickerType='radio'
        this.pickerValue={
          val:this.device_info.manager_name,
          id:this.device_info.manager_id
        }
        this.pickerList=this.depList
      },
      chosen(e){
        if(this.currSelect==='Admin'){
          console.log(e)
          this.device_info.manager_name=e.val
          this.device_info.manager_id=e.id
          // alert(this.device_info.check_allocate_list.check_uid)
          this.currSelect=''
        }

      },

    }
  }
</script>

<style lang="scss" scoped>

  .item>div{line-height: 22px}
</style>

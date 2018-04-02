<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span v-if="action==='new'">添加工序</span>
      <span v-else>工序详情</span>
      <i  class="right"></i>
    </div>
    <div class="mw-page-content ">
      <cst-item label="工序名称" @click.native="select('name')" placeholder="请选择工序"  v-model="procInfo.proc_name"  action="check" :hasArrow="true" ></cst-item>
      <item style="margin-bottom: 0"  v-show="procInfo.proc_name!=='请选择工序'">
        <span>工序类型：</span>
        <label v-show="procInfo.proc_type==1"><input name="isType" disabled  v-model="procInfo.proc_type" type="radio" value="1" /> 生产</label>
        <label v-show="procInfo.proc_type==2"><input name="isType" disabled  v-model="procInfo.proc_type" type="radio" value="2" /> 外协</label>
      </item>
      <cst-item v-show="procInfo.proc_type==2" label="外协工期(小时)"  v-model="procInfo.ass_time" action="check" ></cst-item>
      <cst-item v-show="procInfo.proc_type==2" label="计价单位"  v-model="procInfo.ass_unit" action="check" ></cst-item>
      <cst-item v-show="procInfo.proc_type==2" label="单位数量" placeholder="请输入单位数量"  v-model="procInfo.ass_calc_qty" :action="action" ></cst-item>
      <cst-item v-show="procInfo.proc_type==2" label="生产数量" placeholder="请输入生产数量"  v-model="procInfo.proc_qty" :action="action" ></cst-item>
      <cst-item v-show="procInfo.proc_type==2" label="计价单价" placeholder="请输入外协单价"  v-model="procInfo.ass_up" :action="action" ></cst-item>
      <cst-item v-show="procInfo.proc_type==2" label="外协金额"  v-model="procInfo.ass_price" action="check" ></cst-item>

      <cst-item v-show="procInfo.proc_type==1" label="生产小组" @click.native="select('dep')"   v-model="procInfo.pg_name"  action="check" :hasArrow="true" ></cst-item>
      <item style="margin-bottom: 0"  v-show="procInfo.proc_type==1">
        <span>部门类型：{{procInfo.pg_type}}</span>
        <label v-show="procInfo.pg_type==0"><input name="isPgType" disabled  v-model="procInfo.pg_type" type="radio" value="0" /> 机器</label>
        <label v-show="procInfo.pg_type==1"><input name="isPgType" disabled  v-model="procInfo.pg_type" type="radio" value="1" /> 人工</label>
      </item>
      <cst-item v-show="procInfo.proc_type==1&&procInfo.pg_type==0" label="设备" @click.native="select('dev')"   v-model="procInfo.device_name"  action="check" :hasArrow="true" ></cst-item>
      <cst-item v-show="procInfo.proc_type==1&&procInfo.pg_type==0" label="机器工时(小时)" placeholder="请输入机器工时"  v-model="procInfo.machine_time" :action="action" ></cst-item>
      <cst-item v-show="procInfo.proc_type==1&&procInfo.pg_type!=0" label="人工工时(小时)" placeholder="请输入人工工时"  v-model="procInfo.labor_time" :action="action" ></cst-item>
      <cst-item v-show="procInfo.proc_type==1&&procInfo.pg_type==0" label="辅助时间(小时)"  v-model="procInfo.other_time" action="check" ></cst-item>


      <item style="margin-bottom: 0" >
        <span>是否质检：</span>
        <label><input name="isQc"   v-model="procInfo.is_need_qc" type="radio" value="1" /> 是</label>
        <label><input name="isQc"   v-model="procInfo.is_need_qc" type="radio" value="2" /> 否</label>
      </item>
      <item  >
        <span>加工内容:</span>
        <textarea style="resize: none;width: 100%;height:68px;line-height: 16px;font-size: 12px"  v-model="procInfo.proc_desc" placeholder="加工内容"></textarea>
      </item>


      <!--<item style="padding-right: 25px" v-show="proc_info.proc_type==1" @click.native="goDepList">关联部门-->
        <!--<span class="stopIcon" v-for="item in proc_info.proc_pg_list">{{ item.pg_name }} <i v-show="item.is_default">(默认)</i></span>-->
        <!--<i v-show="action!=='check'" class="icon ion-ios-arrow-right arrow " ></i>-->
      <!--</item>-->




      <div class="mw-page-footer" v-show="action!=='check'">
        <div class="btns">
          <div class="btn" @click="storage()">
            确定
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
  </div>
</template>

<script>
  import { getDeptInfo } from '@/api/company'
  import { processCostItemUnitList,processDetail,processList,pgDetail  } from '@/api/production/procedure'
  import timePicker from "../../../components/timePicker"
  import CstItem from "../../../components/cstItem"
  import sess from '@/utils/sess'
  export default{
    name:'templateAddProc',
    components: {timePicker,CstItem},
    computed:{},
    data(){
      return {
        showPicker:false,
        currSelect:'',
        pickerType:'radio',
        pickerValue:'',
        pickerList:[],
        action:'new',
        proc_id:'',
        proc_name_list:[
          {val:'name',id:'id'}
        ],
        pgList:[],
        devList:[],
        proc_info:{//proc_info.proc_pg_list
          ass_add_time:'',//	外协附加工时
          id:'',//	工序Id	number	为空则新增，非空则修改
          is_can_ass:1,//	是否可外协
          proc_desc:'',//	加工内容
          proc_name:'',//	工序名称
          proc_no:'',//	工序编号
          proc_type:1,//工序类型
          company:'请选择',//计量单位
          company_id:'',
          proc_pg_list	:[
            {//关联部门	array<object>
              // is_default:'',//	是否默认部门	number
              // pg_id	:'',//生产小组Id	number
              // proc_id:'',//	工序Id	number
            }
          ],
        },
        procInfo:{
          ass_calc_qty:'',//	计价数量(单位数量)	number	外协
          ass_price:'',//	外协金额	number	外协
          ass_time:'',//	外协工期	number	外协
          ass_unit:'',//	外协单位(计价单位)	string	外协
          ass_up:'',//	外协单价(计价单价)	number	外协
          device_id:'',//	设备	number
          device_name:'请选择',
          id:'',//	模板子Id	number
          is_need_qc:'1',//	是否要质检	string
          labor_time:'',//	人工时间	number
          machine_time:'',//	机器时间	number
          other_time:'',//	其它辅助时间	number
          pg_id:'',//	生产小组ID	number
          pg_name:'请选择',
          pg_type:0,
          pic_path:'',//	附图路径	string
          proc_desc:'',//	加工内容	string
          proc_id:'',//	工序Id	number
          proc_name:'请选择工序',//	工序名称	string
          proc_qty:'',//	生产数量	number	外协
          proc_seq:'',//	工序序号	number
          proc_type:'1',//	工序类型	string	(1：生产，2:外协）
        }
      }
    },
    activated(){
      if(this.action==='new'){
        // this.getPgList()

      }
    },
    mounted(){
      const param = this.$route.query;
      this.action=param.action
      if(this.action==='new'){
        this.processList()
      }else if(this.action==='action'){
        this.getProcInfo()
        this.processList()
        this.processDetail()
        this.pgDetail()
      }


    },
    watch:{
      procInfo:{
        handler:function(e){
           e.ass_price=Number(e.ass_up)*Number(e.ass_calc_qty)*Number(e.proc_qty)
        },deep:true
      }
    },
    methods: {
      getProcInfo(){
        this.procInfo=sess.get('procInfo')
        sess.remove('procInfo')
      },
      //保存
      storage(){
        if(this.procInfo.proc_type==1){//生产
          if(this.procInfo.pg_type==0){//机器类型
            if(this.procInfo.device_id==''||this.procInfo.device_id==null){
              $toast.show('请选择设备')
              return
            }
            if(this.procInfo.machine_time===''||this.procInfo.machine_time===null){
              $toast.show('请输入机器工时')
              return
            }
            if(this.procInfo.proc_desc===''||this.procInfo.proc_desc===null){
              $toast.show('请输入加工内容')
              return
            }
            this.procInfo.labor_time=''
            sess.set('procInfo',JSON.stringify(this.procInfo))
            this.$router.go(-1)

          }else{//人工类型
            if(this.procInfo.labor_time==''||this.procInfo.labor_time==null){
              $toast.show('请输入人工工时')
              return
            }
            if(this.procInfo.proc_desc===''||this.procInfo.proc_desc===null){
              $toast.show('请输入加工内容')
              return
            }
            this.procInfo.device_id=''
            this.procInfo.machine_time=''
            sess.set('procInfo',JSON.stringify(this.procInfo))
            this.$router.go(-1)
          }
        }else if(this.procInfo.proc_type==2){//外协
          if(this.procInfo.ass_calc_qty==''||this.procInfo.ass_calc_qty==null){
            $toast.show('请输入单位数量')
            return
          }
          if(this.procInfo.proc_qty==''||this.procInfo.proc_qty==null){
            $toast.show('请输入生产数量')
            return
          }
          if(this.procInfo.ass_up==''||this.procInfo.ass_up==null){
            $toast.show('请输入单位价格')
            return
          }
          if(isNaN(this.procInfo.ass_price)){
            $toast.show('参数错误')
            return
          }
          if(this.procInfo.proc_desc===''||this.procInfo.proc_desc===null){
            $toast.show('请输入加工内容')
            return
          }
          sess.set('procInfo',JSON.stringify(this.procInfo))
          this.$router.go(-1)
        }

      },
      //工序详情
      processDetail(){
        const  requestData={
          query:{
            proc_id:this.procInfo.proc_id
          }
        }
        processDetail(requestData).then(res => {
          if(res.proc_info!==null){
            this.procInfo.proc_type=res.proc_info.proc_type
            this.procInfo.ass_time=res.proc_info.ass_time
            this.procInfo.other_time=res.proc_info.other_time
            this.procInfo.ass_unit=res.proc_info.ass_unit
            this.pgList=[]
            res.proc_info.pg_list.forEach((e,i)=>{
              if(e.is_default=='1'&&this.action==='new'){
                this.procInfo.pg_id=e.pg_id,
                  this.procInfo.pg_name=e.pg_name
                this.pgDetail()
              }
              this.pgList.push({
                val:e.pg_name,id:e.pg_id
              })
            })
          }else{
            $toast.show('工序已失效，请重新选择')
          }

        }).catch(error => {
          $toast.show(error.description)
        })
      },

      //选择工序
      select(item){
        if(item==='name'){
          this.showPicker=true
          this.currSelect=item
          this.pickerValue={val:this.procInfo.proc_name ,id:this.procInfo.proc_id}
          this.pickerList=this.proc_name_list
        }else if(item==='dep'){
          this.showPicker=true
          this.currSelect=item
          this.pickerValue={val:this.procInfo.pg_name ,id:this.procInfo.pg_id}
          this.pickerList=this.pgList
          console.log(this.pickerList)
        }else if(item==='dev'){
          if(this.devList.length>0){
            this.showPicker=true
            this.currSelect=item
            this.pickerValue={val:this.procInfo.device_name ,id:this.procInfo.device_id}
            this.pickerList=this.devList
          }else{
            $toast.show('该生产小组暂未配置设备')
          }

        }
      },
      //获取工序列表
      processList(){
        this.proc_name_list=[]
        const  requestData={
          query:{
            proc_nameL:'',
            proc_no:'',
            status:''
          }
        }
        processList(requestData).then(res => {
          res.proc_info.forEach((e,i)=>{
            this.proc_name_list.push({
              val:e.proc_name,id:e.id
            })
          })

        }).catch(error => {
          $toast.show(error.description)
        })
      },
      //获取单位列表
      processCostItemUnitList(){
        const  requestData={}
        processCostItemUnitList(requestData).then(res => {
          res.unit_list.forEach((e,i)=>{
            this.pickerList.push({
              val:e.name,
              id:e.code
            })
          })
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      //获取生产小组详情
      pgDetail(){
        const  requestData={
          query:{
            pg_id:this.procInfo.pg_id
          }
        }
        pgDetail(requestData).then(res => {
            this.procInfo.pg_type=res.pg_info.pg_type
            if(this.procInfo.pg_type==0){
              if(res.pg_info.device_list.length>0){
                this.devList=[]
                res.pg_info.device_list.forEach((e,i)=>{
                  this.devList.push({
                    val:e.device_name,
                    id:e.device_id
                  })
                })
              }else{
                this.devList=[]
              }

            }
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      chosen(e){
        if(this.currSelect=='name'){
          this.procInfo.proc_name=e.val
          this.procInfo.proc_id=e.id
          this.processDetail()
        }else if(this.currSelect==='dep'){
          this.procInfo.pg_id=e.id,
          this.procInfo.pg_name=e.val
          this.pgDetail()
        }else if(this.currSelect==='dev'){
          this.procInfo.device_name=e.val
          this.procInfo.device_id=e.id
        }

      }
    }

  }
</script>

<style lang="scss" scoped>
  .item-img{
    width: 30px;height: 30px;float:left;
  }
  .item{
    .rigth{float: right;padding-right: 15px}
    .arrow{
      color: #DDD;
      position: absolute;
      right: 15px;
      top: 14px;
      font-size: 20px;
    }
    &>span.stopIcon:before{
     content: "、";
    }
    &>span.stopIcon:first-of-type:before{
      content: "：";
    }
  }

</style>

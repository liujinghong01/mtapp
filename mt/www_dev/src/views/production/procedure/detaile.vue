<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span v-if="action==='new'">新建工序</span>
      <span v-else>工序详情</span>
      <i v-if="action==='check'" class="right" @click="Edit">编辑</i>
      <i v-if="action==='action'" class="right" @click="cancelEdit">取消</i>
      <i v-if="action==='new'" class="right"></i>
    </div>
    <div class="mw-page-content ">
      <cst-item label="工序编号" v-if="action!=='new'" placeholder="请输入工序编号" v-model="proc_info.proc_no" action="check" ></cst-item>
      <cst-item label="工序名称" placeholder="请输入工序名称" v-model="proc_info.proc_name" :action="action" ></cst-item>
      <cst-item label="加工内容" placeholder="请输入加工内容" v-model="proc_info.proc_desc" :action="action" ></cst-item>
      <item  >
        <span>工序类型</span>
        <label><input name="isShare"  :disabled="action==='check'" v-model="proc_info.proc_type" type="radio" value="1" /> 生产</label>
        <label><input name="isShare"  :disabled="action==='check'" v-model="proc_info.proc_type" type="radio" value="2" /> 外协</label>
      </item>
      <cst-item v-show="proc_info.proc_type==1" label="辅助工时(小时)" placeholder="请输入辅助工时" v-model="proc_info.other_time" :action="action" ></cst-item>
      <item style="padding-right: 25px" v-show="proc_info.proc_type==1" @click.native="goDepList">关联部门
        <span class="stopIcon" v-for="item in proc_info.pg_list">{{ item.pg_name }} <i v-show="item.is_default">(默认)</i></span>
        <i v-show="action!=='check'" class="icon ion-ios-arrow-right arrow " ></i>
      </item>
      <cst-item v-show="proc_info.proc_type==2" label="外协工期(小时)" placeholder="请输入外协工期" v-model="proc_info.ass_time" :action="action" ></cst-item>
      <item v-show="proc_info.proc_type==2"  @click.native="selectCompany">计量单位 <span class="rigth" :class="action==='check'?'active':''">{{ proc_info.ass_unit}} <i v-show="action!=='check'" class="icon ion-ios-arrow-right arrow " ></i> </span></item>




      <div class="mw-page-footer" v-show="action!=='check'">
        <div class="btns">
          <div class="btn" @click="processModify">
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
  </div>
</template>

<script>
  import { getDeptInfo } from '@/api/company'
  import { processCostItemUnitList,processModify,processDetail  } from '@/api/production/procedure'
  import timePicker from "../../../components/timePicker"
  import CstItem from "../../../components/cstItem"
  import sess from '@/utils/sess'
  export default{
    name:'procedureDetaile',
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
        proc_info:{//proc_info.proc_pg_list
          other_time:'',
          ass_time:'',//	外协附加工时
          id:'',//	工序Id	number	为空则新增，非空则修改
          is_can_ass:1,//	是否可外协
          proc_desc:'',//	加工内容
          proc_name:'',//	工序名称
          proc_no:'',//	工序编号
          proc_type:1,//工序类型
          ass_unit:'请选择',//计量单位
          ass_unit_id:'',
          pg_list	:[
            {//关联部门	array<object>
              // is_default:'',//	是否默认部门	number
              // pg_id	:'',//生产小组Id	number
              // proc_id:'',//	工序Id	number
            }
          ],
        },
      }
    },
    activated(){
      if(this.action!=='check'){
        this.getPgList()
      }
    },
    mounted(){
      const param = this.$route.query;
      this.action=param.action
      if(this.action==='new'){
        this.processCostItemUnitList()
      }else if(this.action==='check'){
        this.proc_id=param.proc_id
        this.processDetail()
      }


    },
    methods: {
      Edit(){
        this.action='action'
      },
      cancelEdit(){
        this.action='check'
        this.processDetail()
      },
      //工序详情
      processDetail(){
        const  requestData={
          query:{
            proc_id:this.proc_id
          }
        }
        processDetail(requestData).then(res => {
          this.proc_info=res.proc_info
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      //工序新增修改
      processModify(){
        this.proc_info.pg_list.forEach((e,i)=>{
          this.$set(e,'proc_id',this.proc_info.id)
        })
        const  requestData={
          proc_info:this.proc_info
        }
        processModify(requestData).then(res => {
          if(this.action==='new'){
            $toast.show('新建基础工序成功')
          }else{
            $toast.show('编辑成功')
          }

          $router.go(-1)
        }).catch(error => {
          $toast.show(error.description)
        })

      },
      //设置关联部门
      goDepList(){
        if(this.action=='check'){
          return
        }
        if(this.proc_info.pg_list.length>0){
          sess.set('pg_list',JSON.stringify(this.proc_info.pg_list))
        }
        $router.forward({path:'departmentList'})
      },
      //获取关联部门列表
      getPgList(){
        this.proc_info.pg_list=[]
        if(sess.get('pg_list')===null){
          return
        }
        let List=JSON.parse(sess.get('pg_list'))
        sess.remove('pg_list')
        List.forEach((e,i)=>{
          let obj={
                is_default:e.is_default,
                pg_id	:e.pg_id,
                pg_name:e.pg_name
              }
          if(e.is_default==1){
            this.proc_info.pg_list.unshift(obj)
          }else{
            this.proc_info.pg_list.push(obj)
          }

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
      selectCompany(){
        this.currSelect='ass_unit'//记住当前选择项
        this.showPicker=true
        this.pickerValue={
          val:this.proc_info.ass_unit,
          id:this.proc_info.ass_unit_id
        }
      },
      chosen(e){
        console.log(e)
        this.proc_info.ass_unit=e.val
        this.proc_info.ass_unit_id=e.id
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

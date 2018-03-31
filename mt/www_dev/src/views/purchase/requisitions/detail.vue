<template>
    <div class="mw-page page purchase" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>请购单详情</span>
          <i class="right" @click="action='edit'" v-if="action==='check'">编辑</i>
          <i class="right" @click="action='check'" v-else-if="action==='edit'">取消</i>
          <i class="right" v-else></i>
        </div>
      <div class="mw-page-content has-tab">
        <div>
          <div class="mw-tab">
            <ul class="tab-content">
              <li :class="{'active':tabIndex===0}" @click="onTabClick(0)">头信息</li>
              <li :class="{'active':tabIndex===1}" @click="onTabClick(1)">行信息</li>
            </ul>
          </div>
        </div>
        <div style="flex: 1;position: relative">
          <div class="page-content" v-show="tabIndex===0">
            <cst-item label="请购单号" :action="action" v-model="head_info.request_no" v-show="head_info.request_no"  :canEdit="false"></cst-item>

            <div v-if="action === 'new'">

              <cst-item label="经办人" v-if="chosenCompany.deps&&chosenCompany.deps.length > 0" :action="action" v-model="chosenCompany.deps[0].work_name" :canEdit="false"></cst-item>
              <cst-item :hasArrow="action!=='check'?true:false" @click.native="selectDep()" v-if="chosenCompany.deps&&chosenCompany.deps.length > 0"   label="请购部门" :action="action" v-model="head_info.dep_name" :canEdit="false"></cst-item>

            </div>
            <div v-else>
              <cst-item label="经办人" :action="action" v-model="head_info.request_man_name" :canEdit="false"></cst-item>
              <cst-item @click.native="selectDep()" label="请购部门" :hasArrow="action!=='check'?true:false" :action="action" v-model="head_info.dep_name" :canEdit="false"></cst-item>
            </div>
            <cst-item v-if="action==='check'" label="请购时间" :action="action" v-model="head_info.request_date"></cst-item>
            <datepicker v-else v-model="head_info.request_date" label="请购时间" date-format="yyyy-mm-dd"></datepicker>
            <div class="item-divider" style="padding-left: 15px">备注</div>
            <textarea style="margin: 0" v-model="head_info.reason" :disabled="action==='check'" placeholder="请输入备注">
            </textarea>
          </div>
          <div class="page-content" v-show="tabIndex===1">
            <div v-for="item in head_info.line_list">
              <swipe-item swipeItemText="删除" :obj="item" v-on:SwipeItemClick="delItem(item)" v-on:ItemClick="onItemClick(item)">
                <div class="req-item">
                  <div class="req-item-title">物料编码 ：<span class="vi-color">{{item.mat_no}}</span></div>
                  <div class="req-item-content">
                    <span>类型 : {{item.mat_type_name}}</span>
                    <span>数量 : {{item.quantity}}</span>
                    <span>单价 : {{item.up}}</span>
                    <span>金额 : {{item.price}}</span>
                  </div>
                </div>
              </swipe-item>
            </div>
          </div>
        </div>
      </div>

      <div class="mw-page-footer" v-if="action==='approve'">
        <div class="btns" >
          <div class="btn" @click="submit">
            审批
          </div>
        </div>
      </div>
      <div class="mw-page-footer" v-show="action==='new'||action==='edit'">
        <div class="btns" >
          <div class="btn" @click="saveHead('')">
            保存
          </div>
          <div class="divider" v-show="tabIndex===1"></div>
          <div class="btn"  @click="$router.forward({path:'/purchase/requisition/lineInfo/new',query:{line_info:request_id}})" v-show="tabIndex===1">
            新增
          </div>
        </div>
      </div>
      <div class="mw-page-footer" v-show="action==='check' && (head_info.approve_step==='step10' || head_info.approve_step==='step11')">
        <div class="btns" >
          <div class="btn" @click="submit">
            提交审批
          </div>
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
  import CstItem from "../../../components/cstItem";
  import { requisitionDetail,requisitionLineDel,requisitionModify,requisitionHeadModify } from '@/api/purchase/requisition'
  import SwipeItem from "../../../components/swipeItem"
  import timePicker from "../../../components/timePicker"
  import { todayDate } from "@/utils"
  import { submitApprove } from '@/api/approve'
  import { mapGetters } from 'vuex'
  import sess from '@/utils/sess'
    export default{
      name:'requisitionDetail',
      components: {timePicker,SwipeItem,CstItem},
      data(){
          return {
            showPicker:false,
            pickerValue:{},
            pickerList:[
            ],
            tabIndex:0,
            request_id:'',
            action:'check',
            showPicker:false,
            pickerType:'radio',
            pickerValue:'',
            pickerList:'',
            dep_list:[],
            head_info:{
              "dep_id": '',
              "dep_name": "请选择",
              "reason": "",
              "request_date": todayDate(),
              "request_id": '',
              "request_man": '',
              "request_man_name": "",
              "request_no": "",
              line_list:[
              ],
            },

          }
        },
      mounted(){
        this.action = this.$route.params.action
        if(this.chosenCompany.deps.length>0){
          this.chosenCompany.deps.forEach((e,i)=>{
            this.dep_list.push({
              id:e.dep_id,
              val:e.dep_name
            })
          })
        }else{
          $dialog.alert({
            // 效果
            effect: 'ios',
            // 标题
            title: '提示',
            // 内容
            content: '您暂不属于任何部门',
            // 按钮文本
            okText: '确定',
            // 按钮主题
            okTheme: 'assertive'
          }).then((res)=>{

          })
        }

        if (this.action !== 'new') {
          this.request_id = this.$route.params.request_id
        }
        this.pickerValue = this.pickerList[0]

        if(this.chosenCompany.deps && this.chosenCompany.deps.length === 1){
          this.head_info.dep_name = this.chosenCompany.deps[0].dep_name
          this.head_info.dep_id = this.chosenCompany.deps[0].dep_id
        }
        if(!this.chosenCompany.deps || this.chosenCompany.deps.length < 1){
          $dialog.alert({
            effect: 'default',
            // 标题
            title: '提示',
            content: '您尚未添加部门,不能操作请购单',
            okText: '确定',
            okTheme: 'assertive'
          }).then(e => {
            this.$router.go(-1)
          })
        }
      },
      computed:{
        ...mapGetters(['userinfo','chosenCompany']),

      },
      activated(){
        if (this.request_id && this.action != 'new') {
          this.getDetail()
        }
      },
      methods: {

        selectDep(){
          if(this.action==='check'){
            return
          }
          this.showPicker=true
          this.pickerList=this.dep_list
          this.pickerValue={
            id:this.head_info.dep_id,
            val:this.head_info.dep_name
          }
          console.log(this.pickerValue)
        },
        chosen(e){
          this.head_info.dep_name=e.val
          this.head_info.dep_id=e.id
        },
        getDetail(){
          const reqData = {
            query:{
              request_id:this.request_id
            }
          }
          requisitionDetail(reqData).then(res=>{
            this.head_info = res.head_info
            this.request_id = res.head_info.request_id
            console.log(res)
          }).catch(error=>{
            $toast.show(error.description)
          })
        },
        onTabClick(index){
          if(!this.request_id) {
            this.saveHead(index)
            return
          }
          this.tabIndex = index
        },
        submit(){
          if(this.action==='edit'){
            $toast.show('请先保存头信息',600)
            return
          }
          if(this.head_info.line_list.length<1){
            $toast.show('请添加行信息',600)
            return
          }
          if(this.action==='approve'){
            this.$router.forward('/approve/approve/'+this.head_info.request_id+'/4/'+this.head_info.approve_step)
          }else{
            const reqData={
              bill_id:this.request_id,
              bill_type:'4',
              approve_flag:'1',
              approve_step:this.head_info.approve_step?this.head_info.approve_step:'step10'
            }
            submitApprove(reqData).then(res=>{
              $toast.show('提交成功')
              this.$router.go(-1)
            }).catch(error=>{
              $toast.show(error.description)
            })
          }
        },
        checkApprove(){
          //查看审批意见
          this.$router.forward('/approve/approveSuggests/'+this.head_info.request_id+'/4')
        },
        onItemClick(item){
          this.$router.forward({path:'/purchase/requisition/lineInfo/'+this.action,query:{line_info:JSON.stringify(item)}})
        },
        delItem(item){
          $dialog.confirm({
            theme: 'ios',
            title: '确定要删除'+item.mat_desc+'吗？',
            cancelText: '取消',
            okText: '确定'
          }).then((res) => {
            if(res){
              const reqData = {
                line_ids:[item.line_id]
              }
              requisitionLineDel(reqData).then(res=>{
                this.head_info.line_list = this.head_info.line_list.filter(o=>{
                  return o.line_id !==item.line_id
                })
              }).catch(error=>{
                $toast.show(error.description)
              })
            }
          })

        },
        saveHead(index){
          if(this.head_info.dep_id ===''){
            $toast.show('请选择请购部门',600)
            return
          }
          this.head_info.request_man = this.userinfo.uid
          this.head_info.request_man_name = this.chosenCompany.deps[0].work_name

          const reqData = {
            head_info:this.head_info
          }
          requisitionModify(reqData).then(res=>{
            this.request_id = res.request_id
            this.head_info.request_no = res.request_no

            if(index===1){
              $toast.show('头信息已保存')
              this.tabIndex=1
            }else{
              $toast.show('保存成功')
              this.action = 'check'
            }

            console.log(res)
          }).catch(error=>{
            $toast.show(error.description)
          })
        },
        showSelectBox(){
          this.showPicker = true
        }
      }
    }
</script>

<style lang="scss" scoped>

</style>

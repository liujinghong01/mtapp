<template>
    <div class="mw-page page storage" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>模具领料单</span>
            <i class="right" v-if="action=='check'"  @click="action='action'">编辑</i>
            <i class="right" v-if="action=='action'" >取消</i>
          <i class="right" v-if="action=='new'" ></i>
        </div>
        <div class="mw-page-content singleDetail" :class="action!=='new'?'active':''" >
          <div class="header">
            <cst-item v-show="action!=='new'"   label="领料单号" v-model="apply_mat_info.apply_no"  action='check' editType="text"></cst-item>
            <item style="margin-bottom: 0" >
              <span>领料类型：</span>
              <label><input :disabled="action=='check'" name="aplly_type"   v-model="apply_mat_info.aplly_type" type="radio" value="1" /> 需求</label>
              <label><input :disabled="action=='check'" name="aplly_type"   v-model="apply_mat_info.aplly_type" type="radio" value="2" /> 超领</label>
            </item>
            <item style="margin-bottom: 0" v-show="apply_mat_info.aplly_type*1===1" >
              <span>BOM类型：</span>
              <label><input :disabled="action=='check'" name="bom_type"   v-model="apply_mat_info.bom_type" type="radio" value="1" /> 主BOM</label>
              <label><input :disabled="action=='check'" name="bom_type"   v-model="apply_mat_info.bom_type" type="radio" value="2" /> 电极BOM</label>
            </item>
            <cst-item   label="模号：" v-model="apply_mat_info.mold_no" placeholder="请输入BOM编码" :action="action" editType="text"></cst-item>

          </div>
          <div class="mateList" :class="apply_mat_info.aplly_type*1===1?'active':''" >
            <div class="mateListTop" >
              <span>物料列表</span>
              <span class="right" v-show="action!=='check'" @click="addMate()">添加</span>
            </div>
            <div class="list" style="padding-top: 29px">
              <div class="item"  v-for="(item,index) in apply_mat_info.apply_detail_list">
                <div class="itemInfo"  >
                  <div class="numberBox" style="top: 17px;height: 70px;width: 100px">
                    <span @click="onChange(item,'1')" >+</span>
                    <input  :disabled="action==='check'" type="number" @change="onChange(item)"  v-model="item.quantity" >
                    <span @click="onChange(item,'-1')" >-</span>

                  </div>
                  <h3>物料编码: <span>{{ item.mat_no }}</span></h3>
                  <p>物料名称: <span>{{ item.mat_desc }}</span></p>
                  <p>规格型号: <span>{{ item.mat_model }}</span></p>
                  <p>物料类型: <span>{{ item.mat_type_name }}</span></p>
                </div>
              </div>
            </div>
          </div>
        </div>

      <div class="mw-page-footer" >
        <div class="btns" v-if="action!=='check'">
          <div  style="" class="total" style="flex: 0 0 80%;">
            <div style="display: flex">
              <span style="width: 50%;text-align: center">物料种类:{{apply_mat_info.apply_detail_list.length}}</span>
              <span style="width: 50%;text-align: center">{{totalCount}}</span>
            </div>
          </div>
          <div  class="btn" style="background-color: #416eb6;color: white;padding: 15px 0" @click="submit">
            保存
          </div>
        </div>
        <div  v-else  class="btns">
          <div class="btn"  @click="approve">
            提交审批
          </div>
        </div>

      </div>
    </div>
</template>

<script>
  import { storehouseList,toragesManage } from '@/api/storage/management'
  import { billModify,billDetail,approve } from '@/api/storage/applyMatBill'
  import CstItem from "../../../components/cstItem"
  import timePicker from "../../../components/timePicker"
  import sess from '@/utils/sess'
    export default{
      name:'MaterialRequisitionDetaile',
      components: {CstItem,timePicker},
      data(){
        return {
          type:'请选择',//单号类型
          action:'',
          apply_mat_info	:{//
            aplly_type:'',//	领料类型	string	（0部门领料，1模具需求领料，2模具超领领料）
            apply_id:'',//	领料单Id	number	@mock=68432
            apply_no:'',//	领料单号	string	@mock=
            approve_step:'',//	审批环节	string	@mock=
            approve_sugg:'',//	审批意见	string	@mock=
            bom_type:'',//	Bom类型	string	模具领料需要：(1主BOM,2 电极BOM）
            dep_id:'',//	申请部门Id	number	@mock=1
            dep_name:'',//	申请部门名称	string	@mock=
            mold_no:'',//	模号	string	模具领料需要
            request_date:'',//	申请日期	string	@mock=
            total_qty:'',//	总数量	number
            apply_detail_list:[//		array<object>
              // {
              //   curr_stock_qty:'',//	当前库存量	number	@mock=1
              //   detail_id:'',//	明细Id	number	@mock=1
              //   mat_desc:'',//	物料名称	string	@mock=
              //   mat_id:'',//	物料Id	number	@mock=1
              //   mat_model:'',//	规格型号	string	@mock=
              //   mat_no:'',//	物料编码	string	@mock=
              //   mat_type_id:'',//	物料类型Id	number	@mock=1
              //   mat_type_name:'',//	物料类型名称	string	@mock=
              //   node_id	:'',//生产需求Id	number	模具领料需要
              //   quantity:'',//	申请数量	number	@mock=1
              //   remark:'',//	备注	string	@mock=
              //   unit:'',//	单位	string	@mock=
              //   weight:'',//	重量
              // }
            ],
        }
      }
      },
      computed:{
        numberList:function(){
        },
        totalCount(){
          let sumCount = 0
          let total_price=0
          this.apply_mat_info.apply_detail_list.filter(o=>{
            total_price+=Number(o.quantity)*Number(o.total_price)
            sumCount += Number(o.quantity)
          })
          return '总数量:'+sumCount
        },
      },
      activated(){
        const mats = JSON.parse(sess.get('mats'))
        if(mats){
          // this.requestData.change_detail_list=mats
          console.log(mats[0].quantity)
          this.apply_mat_info.apply_detail_list=mats
          console.log(this.apply_mat_info.apply_detail_list)
          sess.remove('mats')
        }else{
          console.log("本地储存为空")
        }
      },
      mounted(){
        let param=this.$route.query
        this.action=param.action
        this.apply_id=param.apply_id
        if(this.action==='check'){
          this.billDetail()
        }
      },
      methods: {
        submit(){
          if(this.apply_mat_info.aplly_type===''){
            $dialog.alert({
              effect: 'ios',
              title: '提示',
              content: '请选择领料类型',
              okText: '确定',
              okTheme: 'positive'
            })
            return
          }
          if(this.apply_mat_info.aplly_type==='1'&&this.apply_mat_info.bom_type===''){
              $dialog.alert({
                effect: 'ios',
                title: '提示',
                content: '请选择BOM类型',
                okText: '确定',
                okTheme: 'positive'
              })
            return
          }
          if(this.apply_mat_info.mold_no===''){
            $dialog.alert({
              effect: 'ios',
              title: '提示',
              content: '请输入模号',
              okText: '确定',
              okTheme: 'positive'
            })
            return
          }
          if(this.apply_mat_info.apply_detail_list.length<1){
            $dialog.alert({
              effect: 'ios',
              title: '提示',
              content: '请添加物料',
              okText: '确定',
              okTheme: 'positive'
            })
            return
          }
          const  requestData={
            apply_mat_info:this.apply_mat_info
          }
          billModify(requestData).then(res => {
            $toast.show('新建模具领料单成功')
            $router.go(-1)
          }).catch(error => {
            $toast.show(error.description)
          })
        },
        approve(){
          const  requestData={
            bill_type:'8',
            bill_id:this.apply_mat_info.apply_id,
            approve_step:"step20",
            approve_flag:'0'
          }
          approve(requestData).then(res => {
            $toast.show('新建模具领料单成功')
            $router.go(-1)
          }).catch(error => {
            $toast.show(error.description)
          })
        },
        onChange(e,type,index){
          if(this.action=='check'){
            return
          }
          if(type==='-1'){
            e.quantity=Number(e.quantity)-1
          }else if(type==='1'){
            console.log(e )
            if(this.apply_mat_info.aplly_type*1===1){
              if(e.quantity<e.undeal_qty){
                e.quantity=Number(e.quantity)+1
              }
            }else{
              if(e.quantity<e.curr_stock_qty){
                e.quantity=Number(e.quantity)+1
              }
            }
          }
          let quantity=Number(e.quantity)
          if(quantity%1!==0){
            e.quantity=parseInt(quantity)
          }
          if(e.quantity<=0){
            $dialog.confirm({
              theme: 'ios',
              title: '确定要删除吗？',
              cancelText: '取消',
              okText: '确定'
            }).then(res=>{
              if(res){
                this.apply_mat_info.apply_detail_list.splice(index,1)
              }else{
                e.quantity=1
              }
            })
          }
          if(this.apply_mat_info.aplly_type*1===1){
            if(e.quantity>e.undeal_qty){
              e.quantity=e.undeal_qty
            }
          }else{
            if(e.quantity>e.curr_stock_qty){
              e.quantity=e.curr_stock_qty
            }
          }
        },
        addMate(){
          if(this.apply_mat_info.aplly_type===''){
            $dialog.alert({
              effect: 'ios',
              title: '提示',
              content: '请选择领料类型',
              okText: '确定',
              okTheme: 'positive'
            })
            return
          }
          if(this.apply_mat_info.aplly_type==='1'&&this.apply_mat_info.bom_type===''){
            $dialog.alert({
              effect: 'ios',
              title: '提示',
              content: '请选择BOM类型',
              okText: '确定',
              okTheme: 'positive'
            })
            return
          }
          if(this.apply_mat_info.mold_no===''){
            $dialog.alert({
              effect: 'ios',
              title: '提示',
              content: '请输入BOM编码',
              okText: '确定',
              okTheme: 'positive'
            })
            return
          }
          if(this.apply_mat_info.apply_detail_list.length>0){
            let list=JSON.stringify(this.apply_mat_info.apply_detail_list)
            sess.set('matList',list)
            console.log('本地储存成功'+list)
          }else{
            console.log('本地储存为空')
          }
          this.$router.forward({
            path:'MaterialList',
            query:{
              type:this.apply_mat_info.aplly_type,
              bom_type:this.apply_mat_info.bom_type,
              mold_no:this.apply_mat_info.mold_no
            }
          })

        },
        billDetail(){
          const  requestData={
            query:{
              apply_id:this.apply_id
            }
          }
          billDetail(requestData).then(res => {
            this.apply_mat_info=res.apply_mat_info
          }).catch(error => {
            $toast.show(error.description)
          })
        }
      },
      watch:{
        'apply_mat_info.aplly_type':{
          handler:function(val,oldVal){
            if( val!==oldVal&&this.action!=='check'){
              this.apply_mat_info.bom_type=''
            }
          },deep:true
        },
        'apply_mat_info.bom_type':{
          handler:function(val,oldVal){
            if( val!==oldVal&&this.action!=='check'){
              this.apply_mat_info.apply_detail_list=[]
            }
          },deep:true
        }
      }


    }
</script>

<style lang="scss" scoped>
  .singleDetail {
    .mateList {
      padding-top: 102px;
      .mateListTop {
        top: 102px;
      }
      &.active {
        padding-top: 156px;
        .mateListTop {
          top: 156px;
        }
      }
    }
    &.active {
      .mateList {
        padding-top: 152px;
        .mateListTop {
          top: 152px;
        }
        &.active {
          padding-top: 206px;
          .mateListTop {
            top: 206px;
          }
        }
      }
    }
  }
</style>

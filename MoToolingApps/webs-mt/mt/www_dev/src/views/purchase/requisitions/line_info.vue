<template>
    <div class="mw-page page purchase" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
          <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
          <span>请购单详情</span>
          <i class="right" @click="onMenuClick" v-if="action==='check'">编辑</i>
          <i class="right" @click="action='check'" v-else-if="action==='edit'">取消</i>
          <i class="right" v-else></i>
        </div>
        <div class="mw-page-content">
          <cst-item :isMust="true" label="物料描述" :action="action" v-model="line_info.mat_desc" :canEdit="false" :hasArrow="true" @click.native="chooseMat"></cst-item>
          <!--<cst-item label="规格型号" :action="action" v-model="line_info.mat_model" :canEdit="false" ></cst-item>-->

          <cst-item   label="物料编码" :action="action" v-model="line_info.mat_no" :canEdit="false"></cst-item>
          <cst-item   label="单位" :action="action" v-model="line_info.unit" :canEdit="false" ></cst-item>
          <cst-item :isMust="true" label="数量" :action="action" v-model="line_info.quantity" editType="number"></cst-item>
          <cst-item :isMust="true" label="单价" :action="action" v-model="line_info.price" editType="number" ></cst-item>
          <cst-item label="重量" :action="action" v-model="totalWeight" editType="number" ></cst-item>
          <cst-item :isMust="true" label="金额" :action="action" v-model="line_info.up" editType="number"></cst-item>
          <cst-item v-if="action==='check'" label="交货日期" :action="action" v-model="line_info.delivery_date"></cst-item>
          <datepicker v-else v-model="line_info.delivery_date" label="交货日期" date-format="yyyy-mm-dd"></datepicker>
          <div class="item-divider" style="padding-left: 15px">备注</div>
          <textarea style="margin: 0" v-model="line_info.remark" :disabled="action==='check'">

          </textarea>
        </div>
      <div class="mask" v-show="showMats" @click="showMats = false"></div>
      <section  :class="['filter',showMats?'active':'']" style="display: flex;flex-direction: column">
        <div class="side-title" v-show="showMats">
          请选择物料
        </div>
        <div class="side-content" style="display: flex;flex-direction: column">
          <div style="height: 50px">
            <mw-search placeholder="输入物料关键字" v-model="matSearch" :search="getMats" cancelText="取消" :cancelAction="searchCancel"></mw-search>
          </div>
          <div style="flex: 1">
            <mw-radio v-for="item in matSearchList" :obj="item" :chosenObj="chosenMat" :iClick="itemClick" :hasArrow="false">{{item.mat_desc}}</mw-radio>
          </div>
        </div>
        <div class="side-footer"  v-show="showMats">
          <div class="filter-btns">
            <div class="filter-btn" @click="chooseMatItem">
              确定
            </div>
          </div>
        </div>
      </section>
        <div class="mw-page-footer" v-show="action === 'edit' || action === 'new'">
            <div class="btns">
                <div class="btn" @click="saveData">
                    保存
                </div>
            </div>
        </div>
    </div>
</template>
<script>
  import CstItem from "../../../components/cstItem";
  import { todayDate } from '@/utils'
  import { requisitionLineModify } from '@/api/purchase/requisition'
  import MwRadio from "../../../components/mwRadio";
  import { queryMatByName } from '@/api/dic'
  import MwSearch from "../../../components/mw-search";
    export default{
      components: {
        MwSearch,
        MwRadio,
        CstItem},
      name:'requisitionLineInfo',
        data(){
          return {
            line_id:'',
            action:'check',
            showMats:false,
            matSearchList:[],
            filterMatSearchList:[],
            chosenMat:{},
            matSearch:'',
            request_id:'',
            line_info: {
              line_ids: '',
              mat_desc: "请选择",
              mat_id: '',
              mat_model: "",
              mat_no: "",
              mat_type_id: '',
              mat_type_name: '',
              price: "",
              quantity: '',
              remark: "",
              delivery_date: todayDate(),
              unit: "",
              up: '',
              weight: '',
              unit_weight:'',
              unit_price:''
            },
          }
        },
      mounted(){
        this.action = this.$route.params.action
        if(this.action !== 'new'){
          this.line_info = JSON.parse(this.$route.query.line_info)
        }else if(this.action==='new'){
          this.request_id = this.$route.query.line_info
        }
      },
      computed:{
        totalWeight(){
          let total = this.line_info.unit_weight? this.line_info.quantity * this.line_info.unit_weight : this.line_info.weight
          this.line_info.weight = total
          this.line_info.up = this.line_info.quantity * this.line_info.price
          return total
        },
      },
      methods: {
        onMenuClick(){
          this.action = 'edit'
        },
        searchCancel(){
          this.matSearch = ''
          this.matSearchList = []
        },

        getMats(){
          //查询物料
          const reqData = {
            query:{
              mat_desc:this.matSearch,
              is_include_process_type:0
            }
          }
          queryMatByName(reqData).then(res=>{
            if(res.mat_list.length===0){
              this.matSearchList = []
              $toast.show('搜索结果为空')
            }else{
              this.matSearchList = res.mat_list
            }
          }).catch(error=>{
            $toast.show(error.description)
          })
        },
        //点击某个单选
        itemClick(item){
          if(item===this.chosenMat){
            return
          }
          this.chosenMat.chosen = false
          this.chosenMat = item
        },
        chooseMatItem(){
          this.showMats = false
          this.line_info.mat_desc = this.chosenMat.mat_desc
          this.line_info.mat_id = this.chosenMat.mat_id
          this.line_info.mat_no = this.chosenMat.mat_no
          this.line_info.unit = this.chosenMat.unit
          this.line_info.unit_weight = this.chosenMat.weight
          this.line_info.price = this.chosenMat.price
          this.line_info.mat_type_name = this.chosenMat.mat_type_name
          this.line_info.mat_type_id = this.chosenMat.mat_type_id
          console.log(this.chosenMat)
        },
        saveData(){
          if(this.line_info.mat_no==='请选择'){
            $toast.show('请选择物料描述',600)
            return
          }
          if(this.line_info.quantity==='' || this.line_info.quantity === null){
            $toast.show('请输入数量',600)
            return
          }
          if(this.line_info.price==='' || this.line_info.price === null){
            $toast.show('请输入单价',600)
            return
          }
          const reqData = {
            line_list:[this.line_info],
            request_id:this.request_id
          }
          requisitionLineModify(reqData).then(res=>{
            $toast.show('保存成功')
            history.go(-1)
          }).catch(error=>{
            $toast.show(error.description)
          })
        },
        chooseMat(){
          if(this.action !== 'new' && this.action !== 'edit'){
            return
          }else{
            this.showMats = true
            this.getMats()
          }
        }
      },
    }
</script>

<style lang="scss" scoped>

</style>

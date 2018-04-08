<template>
  <div class="mw-page page" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>定单产品明细</span>
      <!--<i class="right " v-if="action==='check'&&action!=='checkOnly'" @click="onMenuClick">编辑</i>-->
      <!--<i class="right " v-else-if="action ==='edit'" @click="action='check'">取消</i>-->
      <!--<i  class="right " v-else-if="action ==='new'" @click="associate">-->
              <!--&lt;!&ndash;关联报价单&ndash;&gt;</i>-->
      <i ></i>
    </div>
    <div class="mw-page-content compute-detail">
      <cst-item label="产品名称:"  v-model="cst_order_line.cst_prod_name" editType="text" placeholder="输入产品名称"  :action="action" :canEdit="true"></cst-item>
      <cst-item
        label="产品类型"
        v-model="cst_order_line.type_name"
        editType="text"
        placeholder="输入产品类型"
        :action="action"
        :canEdit="true"
        :hasArrow="true"
        @click.native="chooseType('prodType')"></cst-item>
      <!--<cst-item label="客户模号"  v-model="cst_order_line.cst_mold_no" placeholder="输入客户模号"  :action="action" :canEdit="true"></cst-item>-->
      <!--<cst-item @click.native="prodsClick" label="客户制品信息"-->
                <!--editType="text"-->
                <!--:action="action"-->
                <!--:canEdit="false"-->
                <!--:hasArrow="true"-->
                <!--:arrowText="cst_order_line.prod_list.length+''">-->
      <!--</cst-item>-->

      <item style="margin: 0" @click.native="prodsClick">
        <span>制品信息：</span>
        <span class="prodItem"
              v-for="(item,index) in cst_order_line.prod_list">
              {{ item.cst_prod_name }}
        </span>
        <i class=" ion-ios-arrow-right  "></i>
      </item>

      <cst-item label="制品材料" v-model="cst_order_line.material"
                editType="text"
                :action="action"
                :hasArrow="true"
                :canEdit="false"
                @click.native="chooseType('material')">
      </cst-item>
      <cst-item label="请输入腔/芯材料" v-model="cst_order_line.cavity_material"
                editType="text"
                :action="action"
                :hasArrow="true"
                :canEdit="false"
                @click.native="chooseType('rawNature')">
      </cst-item>
      <!--<cst-item label="腔/芯材料"  placeholder="请输入腔/芯材料" v-model="cst_order_line.cavity_material" editType="text" :action="action" :canEdit="true"></cst-item>-->

      <item style="margin: 0">
        <p> <span :class="(action==='new'||action==='edit')?'isMust':''">正式开模日期</span>  <span style="float: right">{{ cst_order_line.start_date }}</span>    </p>
        <datepicker v-if="action==='new'||action==='edit'"  style="opacity:0;position: absolute;width: 100%;height:100%;top:0;left:0;" v-model="cst_order_line.start_date" label="正式开模日期" date-format="yyyy-mm-dd"></datepicker>
      </item>
      <item style="margin: 0">
        <p> <span :class="(action==='new'||action==='edit')?'isMust':''">首次试模日期</span>  <span style="float: right">{{ cst_order_line.first_try_date }}</span>    </p>
        <datepicker v-if="action==='new'||action==='edit'" style="opacity:0;position: absolute;width: 100%;height:100%;top:0;left:0;" v-model="cst_order_line.first_try_date" label="首次试模日期" date-format="yyyy-mm-dd"></datepicker>
      </item>
      <item style="margin: 0" >
        <p ><span :class="(action==='new'||action==='edit')?'isMust':''">交货日期</span>  <span style="float: right">{{ cst_order_line.delivery_date }}</span>    </p>
        <datepicker v-if="action==='new'||action==='edit'" style="opacity:0;position: absolute;width: 100%;height:100%;top:0;left:0;"  v-model="cst_order_line.delivery_date" label="交货日期" date-format="yyyy-mm-dd"></datepicker>
      </item>

      <!--<cst-item  label="交货日期"  v-model="cst_order_line.delivery_date"  :action="action" :canEdit="false"></cst-item>-->
      <cst-item label="单价"  v-model="cst_order_line.up" editType="number" placeholder="0"  :action="action" :canEdit="action==='new'||action==='edit'"></cst-item>
      <cst-item label="数量"  v-model="cst_order_line.count" editType="number" placeholder="0"   :action="action" :canEdit="action==='new'||action==='edit'"></cst-item>
      <cst-item label="金额"  v-model="newPrice" editType="number" :action="action" :canEdit="false"></cst-item>

      <cst-item label="备注"  placeholder="请输入备注"  v-model="cst_order_line.remark" editType="text" :action="action" :canEdit="action==='new'||action==='edit'"></cst-item>
    </div>
    <sidebar-check :showFilter="showFilter" :dicType="dicType" :chosen="chosen" v-on:hideMask="showFilter = false"></sidebar-check>
    <div class="mw-page-footer" v-show="action!='check'&&action!=='checkOnly'&&action!=='approve'">
      <div class="btns" >
        <div class="btn" @click="saveData">
          保存
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
  import  cstItem  from '@/components/cstItem'
  import timePicker from "@/components/timePicker"
  import { todayDate } from '@/utils'
  import { queryLineDetail ,modifyLine,associateQuote,rawNatureList } from '@/api/customerOrder/order'
  import sess from '@/utils/sess'
  import {mapGetters} from 'vuex'
  import SwipeItem from "../../../components/swipeItem";
  import SidebarCheck from "../../../components/sidebarCheck";
  export default{
    name:'orderLineInfo',
    components:{
      SidebarCheck,
      SwipeItem,
      cstItem,
      timePicker
    },
    data(){
      return{
        selectType:'',
        showPicker:false,
        pickerType:'radio',
        pickerValue:'',
        pickerList:[],
        rawNatureList:[],
        action:'',
        tabs:['头信息','明细信息','付款明细','备注'],
        tabIndex:0,
        cst_order_line:{//this.cst_order_line.start_date=todayDate()
          cavity_material:'',
          count:'',
          cst_mold_no:'',
          delivery_date:'',
          first_try_date:'',
          start_date:'',
          material:'请选择',
          order_line_id:'',
          price:'',
          remark:'',
          prod_list:[],
          type:0,
          type_name:'请选择',
          rawNature:'请选择',
          up:'0',
          project_no:'',
          cst_prod_name:''
        },
        order_line_id:'',
        project_id:'',
        showFilter:false,
        dicType:''
      }
    },
    computed:{
      ...mapGetters(['userinfo']),
      newPrice(){
        let Price=Number(this.cst_order_line.count)*Number(this.cst_order_line.up)
        this.cst_order_line.price=Price
        return Price
      }
    },
    created(){
      this.action = this.$route.params.action
      if(this.action ==='new'){
        //如果是新增，则将报价单id带过来
        this.project_id = this.$route.params.line_id
        this.cst_order_line.start_date=todayDate()
        this.cst_order_line.delivery_date=todayDate()
        this.cst_order_line.first_try_date=todayDate()
      }else{
        this.order_line_id = this.$route.params.line_id
        this.getData();
      }
      if(this.action!=='checkOnly'){
        this.getrawNature()
      }
    },
    activated(){
      //从制品列表返回
      const prods = sess.get('prods');
      sess.remove('prods')
      if(prods&&prods!=''){
        this.cst_order_line.prod_list = JSON.parse(prods)
        return
      }
      //从历史报价单列表返回
      const historyQuote = sess.get('historyQuote');
      sess.remove('prods')
      if(historyQuote&&historyQuote!=''){
        this.project_id = historyQuote.project_id

        return
      }
    },
    destoryed(){
      console.log('destoryed')
    },
    methods:{
      getrawNature(){
        rawNatureList({}).then(res=>{
          this.rawNatureList=res.raw_nature_list
        }).catch(error=>{
          $toast.show(error.description)
        })
      },

      //制品信息  列表点击
      prodsClick(){
//        if(this.action==='checkOnly'){
//          this.$router.forward('/customerOrder/order/prdList/checkOnly/undefined')
//          return
//        }
        if(this.cst_order_line.prod_list.length===0){
          this.$router.forward('/customerOrder/order/prdList/'+this.action+'/undefined')
        }else{
          window.sessionStorage.setItem('prdList', JSON.stringify(this.cst_order_line.prod_list))
          //this.$router.forward('/customerOrder/order/prdList/'+this.action+'/'+JSON.stringify(this.cst_order_line.prod_list))
          this.$router.forward('/customerOrder/order/prdList/'+this.action+'/'+ (this.cst_order_line.order_line_id ? this.cst_order_line.order_line_id :'undefined') )
        }
      },
      chooseType(type){
        if(this.action==='check'||this.action==='checkOnly'||this.action==='approve'){
          return
        }
        if(type==='rawNature'){
          this.selectType='rawNature'
            // showPicker:false,
            // pickerType:'radio',
            // pickerValue:'',
            // pickerList:[],
          this.pickerList.length=0
          this.rawNatureList.forEach((e,i)=>{
            this.pickerList.push({
              val:e.raw_nature,
              id:e.raw_nature
            })
          })
          this.pickerValue={
            val:this.cst_order_line.cavity_material,
            id:this.cst_order_line.cavity_material
          }
          this.showPicker=true

          return
        }
        this.dicType = type
        this.showFilter = true
      },
      chosen(item){
        if(this.selectType==='rawNature'){
          this.cst_order_line.cavity_material=item.val
          this.showPicker=false
          return
        }
        switch (this.dicType){
          case 'material':{
            this.cst_order_line.material = item.raw_nature
            break
          }
          case 'rawNature':{
            this.cst_order_line.rawNature = item.rawNature;
            this.cst_order_line.rawNature = item.rawNature;
            break
          }
          case 'cavity_material':{
            this.cst_order_line.cavity_material = item.name;
            break
          }
          case 'type':{
            this.cst_order_line.type = item.type
            this.cst_order_line.type_name = item.type_name
            break
          }
          case 'prodType':{
            this.cst_order_line.type = item.type
            this.cst_order_line.type_name = item.type_name
            break
          }
        }
      },
      associate(){
        //关联报价单/核算单
        this.$router.forward('/customerOrder/quote/quoteHistory/associate/'+this.project_id)
      },
      onMenuClick(){
        if(this.action==='check'){
          this.action = 'edit'
          this.getrawNature()
        }else{
          this.action = 'check'
        }
      },
      getData(){
        const that = this;
        const requestData = {
          order_line_id:that.order_line_id
        }
        queryLineDetail(requestData).then(res=>{
          let first_try_date=res.first_try_date
          let delivery_date=res.delivery_date
          that.cst_order_line = res
          setTimeout(()=>{
            that.cst_order_line.first_try_date=first_try_date
            setTimeout(()=>{
              that.cst_order_line.delivery_date=delivery_date
            },100)
          },100)

          this.cst_order_line.project_no = sess.get('projectNo')
          sess.remove('projectNo')
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      saveData(){
        // console.log(this.cst_order_line.cst_prod_name.trim()==='')
        if(!this.cst_order_line.cst_prod_name||(this.cst_order_line.cst_prod_name).trim()===''){
          $toast.show('请输入产品名称')
          return
        }
        if(this.cst_order_line.type_name==='请选择'){
          $toast.show('请选择产品类型')
          return
        }

        if(this.cst_order_line.prod_list.length<1){
          $toast.show('请添加客户制品信息')
          return
        }
        if(this.cst_order_line.material==='请选择'||this.cst_order_line.material===undefined){
          $toast.show('请选择制品材料')
          return
        }
        if(!this.cst_order_line.cavity_material||this.cst_order_line.cavity_material.trim()===''){
          $toast.show('请输入腔/芯材料')
          return
        }
        let delivery_date=this.cst_order_line.delivery_date
        let first_try_date=this.cst_order_line.first_try_date
        let start_date=this.cst_order_line.start_date
        delivery_date=Number(delivery_date.replace(/-/g,""))
        first_try_date=Number(first_try_date.replace(/-/g,""))
        start_date=Number(start_date.replace(/-/g,""))
        if(first_try_date<start_date){
          $toast.show('首次试模日期必须大于开模日期',700)
          return
        }
        if(delivery_date<first_try_date){
          $toast.show('交货日期必须大于首次试模日期',700)
          return
        }

        if(this.cst_order_line.up=='0'){
          $toast.show('请输入单价及数量')
          return
        }


        const requestData = {
          project_id:this.project_id,
          cst_order_line:this.cst_order_line
        }
        modifyLine(requestData).then(res=>{
          console.log(res)
          this.action = 'check'
          history.go(-1)
          $toast.show('保存成功')
        }).catch(error=>{
          $toast.show(error.description)
        })
      }
    },
    //cst_order_line.start_date
    watch:{
      'cst_order_line.start_date':{
        handler:function(e,v){
          this.cst_order_line.first_try_date=e
          this.cst_order_line.delivery_date=e
        },deep:true
      },
      'cst_order_line.first_try_date':{
        handler:function(e,v){
          this.cst_order_line.delivery_date=e

        },deep:true
      },
  }
  }
</script>
<style lang="scss" scoped>
  .compute-item .note{width: 100px}
  .ion-ios-arrow-right{
    color: #DDD;
    position: absolute;
    right: 15px;
    top: 14px;
    font-size: 20px;
  }
  .prodItem{
    &:last-of-type:after{
      display: none;
    }
    &:after{
      display: inline;
      content: '、';
    }
  }
</style>

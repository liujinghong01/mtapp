<template>
   <div class="page has-navbar" v-nav="{hideNavbar:true}">
      <div class="navbar top" style="visibility: visible">
        <div style="position: absolute;width: 100%;height: 100%;visibility: visible;">
          <von-header>
            <button class="button button-icon ion-ios-arrow-back" slot="left" @click="$router.go(-1)"></button>
            <span slot="title">报价单行信息</span>
            <!--<span slot="right" class="right-menu" v-show="action==='check'" @click="onMenuClick">编辑</span>-->
            <!--<span slot="right" class="right-menu" v-show="action ==='edit'" @click="action='check'">取消</span>-->
            <span slot="right" class="right-menu"></span>
          </von-header>
        </div>

      </div>
        <div class="page-content compute-detail" style="padding-bottom: 50px">
            <!--<cst-item label="客户模号"  v-model="quote_line_info.cst_mold_no" editType="text" :action="action" :canEdit="true"></cst-item>-->
            <!--<cst-item label="客户制品信息"-->
                      <!--editType="text"-->
                      <!--:action="action"-->
                      <!--:canEdit="false"-->
                      <!--:hasArrow="true"-->
                      <!--:arrowText="quote_line_info.prod_list.length+''"-->
                      <!--:arrowClick="prodsClick">-->
            <!--</cst-item>-->
          <item style="margin: 0" @click.native="prodsClick">
            制品信息：
            <span class="prodItem" v-for="(item,index) in quote_line_info.prod_list">{{ item.cst_prod_name }}</span>
            <i class=" ion-ios-arrow-right  "></i>
          </item>

          <cst-item label="制品材料"  v-model="quote_line_info.material"
                    editType="text"
                    :action="action"
                    :hasArrow="true"
                    :canEdit="false"
                    @click.native="chooseType('material')">
          </cst-item>
            <cst-item label="产品类型"
                      v-model="quote_line_info.type_name"
                      editType="text"
                      :action="action"
                      :hasArrow="true"
                      :canEdit="false"
                      @click.native="chooseType('prodType')"
                      >
            </cst-item>
          <cst-item label="流道形式"
                    v-model="quote_line_info.runner_name"
                    editType="text"
                    :action="action"
                    :hasArrow="true"
                    :canEdit="false"
                    @click.native="chooseType('runnerType')">
          </cst-item>
          <cst-item label="顶出方式"
                    v-model="quote_line_info.out_way_name"
                    editType="text"
                    :action="action"
                    :hasArrow="true"
                    :canEdit="false"
                    @click.native="chooseType('outerWay')">
          </cst-item>
          <cst-item label="水口形式"
                    v-model="quote_line_info.water_type_name"
                    editType="text"
                    :action="action"
                    :hasArrow="true"
                    :canEdit="false"
                    @click.native="chooseType('waterType')">
          </cst-item>
          <!--<item style="padding-left: 15px">-->
            <!--<span>截面形状</span>-->
            <!--<label><input name="stuff_shape" type="radio" value="方形" v-model="quote_line_info.stuff_shape"/> 方形</label>-->
            <!--<label>-->
              <!--<input name="stuff_shape" type="radio" value="圆形" v-model="quote_line_info.stuff_shape"/> 圆形-->
            <!--</label>-->
          <!--</item>-->
          <item class="size">
            <span class="label">尺寸(mm)</span>
            <ul class="lines">
              <li>
                <span>长</span>
                <input type="number" v-model="quote_line_info.length" v-if="action==='check'" disabled/>
                <input type="number" v-model="quote_line_info.length" v-else />
                x
              </li>
              <li>
                <span>宽</span>
                <input type="number" v-model="quote_line_info.width"  v-if="action==='check'" disabled/>
                <input type="number" v-model="quote_line_info.width" v-else />
                x
              </li>
              <li>
                <span>高</span>
                <input type="number" v-model="quote_line_info.height"  v-if="action==='check'" disabled/>
                <input type="number" v-model="quote_line_info.height" v-else />
              </li>
            </ul>
          </item>
          <!--<cst-item v-show="quote_line_info.stuff_shape==='圆形'" label="高(mm)" v-model="quote_line_info.height" :action="action" editType="number"></cst-item>-->
          <!--<cst-item v-show="quote_line_info.stuff_shape==='圆形'" label="直径(mm)" v-model="quote_line_info.diameter" :action="action" editType="number"></cst-item>-->
          <cst-item label="重量(kg)"  v-model="quote_line_info.weight" editType="number" :action="action" ></cst-item>
          <cst-item label="单价"  v-model="quote_line_info.price" editType="number" :action="action" ></cst-item>
          <cst-item label="数量"  v-model="quote_line_info.count" editType="number" :action="action" ></cst-item>
          <cst-item label="总计"  v-model="totalPrice" editType="number" :action="action" ></cst-item>
          <cst-item label="折扣"  v-model="quote_line_info.rebate" editType="number" :action="action" ></cst-item>
          <cst-item label="折后金额"  v-model="quote_line_info.rebate_price" editType="number" action="check" ></cst-item>
          <cst-item label="工期"  v-model="quote_line_info.task_time" editType="number" :action="action"></cst-item>
          <!--<cst-item label="内模材料"  v-model="quote_line_info.mold_mat" editType="text" :action="action" ></cst-item>-->
            <cst-item label="备注"  v-model="quote_line_info.remark" editType="text" :action="action"></cst-item>
          </div>
     <sidebar-check :showFilter="showFilter" :dicType="dicType" :chosen="chosen" v-on:hideMask="showFilter = false"></sidebar-check>
      <div class="btns" v-show="action!='check'">
        <div class="btn" @click="saveData">
          保存
        </div>
      </div>
    </div>
</template>
<script>
  import  cstItem  from '@/components/cstItem'
  import { quoteLineDetail, save ,quoteLineModify } from '@/api/customerOrder/quote'
  import sess from '@/utils/sess'
  import {mapGetters} from 'vuex'
  import SwipeItem from "../../../../components/swipeItem";
  import SidebarCheck from "../../../../components/sidebarCheck";
  export default{
    name:'quote_line_info',
    components:{
      SidebarCheck,
      SwipeItem,
      cstItem
    },
    data(){
     return{
       action:'',
       tabs:['头信息','明细信息','付款明细','备注'],
       tabIndex:0,
       quote_line_info:{
         count:1,
         quote_line_id:'',
         cst_mold_no:'',
         height:'',
         length:'',
         material:'请选择',
         mold_mat:'',
         out_way:'',
         out_way_name:'请选择',
         prod_list:[

         ],
         up:'',
         rebate:'',
         rebate_price:'',
         stuff_shape:'方形',//形状
         diameter:'', //直径
         remark:'',
         runner:'',
         runner_name:'请选择',
         task_time:'',
         price:'',
         type:'',
         type_name:'请选择',
         water_type:'',
         water_type_name:'请选择',
         weight:'',
         width:'',
         density:'', //比重
         raw_price:'',//物料单价
       },
       quote_line_id:'',
       quote_id:'',
       dicType:'',
       showFilter:false
     }
    },
    computed:{
      ...mapGetters(['userinfo']),
//      stuffPrice(){
//        let result = 0.00
//        if(this.action ==='check'){
//          result = this.quote_line_info.price
//        }else{
//          let v = 0.00
//        //  if(this.quote_line_info.stuff_shape==='方形'){   //方形的时候计算单价
//            v = parseFloat(this.quote_line_info.length)*parseFloat(this.quote_line_info.height)*parseFloat(this.quote_line_info.width)
//          console.log(v)
//        //  }else{
//          //  v =  Number(Math.PI * this.quote_line_info.diameter * this.quote_line_info.diameter * this.quote_line_info.height).toFixed(2)
//         // }
//          if(v){
//            this.quote_line_info.weight = Number(v/1000000*this.quote_line_info.density).toFixed(2)
//            this.quote_line_info.price = result = Number( this.quote_line_info.weight*parseFloat(this.quote_line_info.raw_price)).toFixed(2)
//          }
//        }
//        return result
//      },
      totalPrice(){
        let result = 0.00
        let v = this.quote_line_info.price * this.quote_line_info.count
        if(v){
          this.quote_line_info.total_price = result = Number(v).toFixed(2)
        }
        return result
      }
    },
    created(){
      console.log('mounted')
      this.action = this.$route.params.action
      if(this.action ==='new'){
          //如果是新增，则将报价单id带过来
        this.quote_id = this.$route.params.quote_line_id

      }else{
        this.quote_line_id = this.$route.params.quote_line_id
        this.getData();
      }
      this.quote_line_info.handler_name = this.userinfo.username
      this.quote_line_info.handler = this.userinfo.uid
    },
    activated(){
      console.log('activated')
      //从制品列表返回
      const prods = sess.get('prods');
      if(prods&&prods!='') {
        this.quote_line_info.prod_list = JSON.parse(prods)
        sess.remove('prods')
        return
      }
    },
    destoryed(){
      console.log('destoryed')
    },
    watch:{
      'quote_line_info.rebate':function(newVal,oldVal){
         if(newVal !== oldVal){
            this.quote_line_info.rebate = Number(newVal).toFixed(2)
            this.quote_line_info.rebate_price = Number(this.totalPrice * Number(newVal).toFixed(2)).toFixed(2)
         }
      },
      //'quote_line_info.rebate_price':function(newVal,oldVal){
       // if(newVal !== oldVal){
      //             this.quote_line_info.rebate = Number(newVal / this.totalPrice).toFixed(2)
      //           }
     // }
    },
    methods:{
      //制品信息  列表点击
      prodsClick(){
        if(this.action==='check'){
          this.$router.forward('/customerOrder/quote/line_prods/check/'+JSON.stringify(this.quote_line_info.prod_list))
          return
        }
        if(this.quote_line_info.prod_list.length===0){
          this.$router.forward('/customerOrder/quote/line_prods/new/undefined')
        }else{
          this.$router.forward('/customerOrder/quote/line_prods/edit/'+JSON.stringify(this.quote_line_info.prod_list))
        }
      },
      chooseType(type){
        if(this.action==='check'){
          return
        }
        this.dicType = type
        this.showFilter = true
      },
      chosen(item){
        switch (this.dicType){
          case 'material':{
            this.quote_line_info.material = item.raw_nature
            this.quote_line_info.density = item.density
            this.quote_line_info.raw_price = item.procure_price
            break
          }
          case 'prodType':{
            this.quote_line_info.type = item.type
            this.quote_line_info.type_name = item.type_name
            break
          }
          case 'runnerType':{
            this.quote_line_info.runner = item.code
            this.quote_line_info.runner_name = item.name
            break
          }
          case 'outerWay':{
            this.quote_line_info.out_way = item.code
            this.quote_line_info.out_way_name = item.name
            break
          }
          case 'waterType':{
            this.quote_line_info.water_type = item.code
            this.quote_line_info.water_type_name = item.name
            break
          }

        }
      },
      onMenuClick(){
        if(this.action==='check'){
          this.action = 'edit'
        }else{
          this.action = 'check'
        }
      },
      onTabClick(index){
        console.log('onTabClick')
        if(index===this.tabIndex){
            return
        }
        this.tabIndex = index
      },
      getData(){
        const that = this;
        const requestData = {
          quote_line_id:that.quote_line_id
        }
        quoteLineDetail(requestData).then(res=>{
          that.quote_line_info = res
        }).catch(error=>{
            $toast.show(error.description)
        })
      },
      saveData(){
        // if(!this.quote_line_info.cst_mold_no) {
        //   $toast.show('请填写客户模号')
        //   return
        // }
        if(this.quote_line_info.material==='请选择') {
          $toast.show('请选择制品材料')
          return
        }
        if(!this.quote_line_info.type) {
          $toast.show('请选择产品类型')
          return
        }
        if(!this.quote_line_info.runner) {
          $toast.show('请选择流道形式')
          return
        }
        if(!this.quote_line_info.out_way) {
          $toast.show('请选择顶出方式')
          return
        }
        if(!this.quote_line_info.task_time) {
          $toast.show('请填写工期')
          return
        }
        if(!this.quote_line_info.count) {
          $toast.show('请填写数量')
          return
        }
        if(!this.quote_line_info.price) {
          $toast.show('请填写单价')
          return
        }
        const requestData = {
          quote_id:this.quote_id,
          quote_line:this.quote_line_info
        }
        quoteLineModify(requestData).then(res=>{
            console.log(res)
          this.action = 'check'
            $toast.show('保存成功',600)
            $router.go(-1)
        }).catch(error=>{
            $toast.show(error.description)
        })
      }
    },
  }
</script>
<style lang="scss" scoped>
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

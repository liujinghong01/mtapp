<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>盘点单详情</span>
      <i class="right iconfont icon-saoyisao" @click="scan"></i>
    </div>
    <div class="mw-page-content has-tab">
      <div>
        <cst-item label="库存地点" :hasArrow="false" v-model="check_info.store_house_name"></cst-item>
        <cst-item label="经办人" :hasArrow="false" v-model="check_info.operator_name"></cst-item>
        <item class="matTypes">
          <div style="height:auto;overflow-wrap: normal;">
            <p  style="display: inline;padding-right: 10px"><label><input type="checkbox" v-model="checkAll"/>全部类型</label></p>
            <p  v-for="(item,index) in check_info.mat_type_list" style="display: inline;padding-right: 10px">
              <label>
                <input type="checkbox" name="mat_type" :value="item" v-model="filter_mat_types"/>
                {{item.mat_type_name}}
              </label>
              <br v-show="(index+1)%3===0">
            </p>
          </div>
        </item>
      </div>
      <div style="height: 60px;margin-top: 10px">
        <div class="mw-tab">
          <ul class="tab-content">
            <li :class="{'active':tabIndex===0}" @click="onTabClick(0)">待盘物料</li>
            <li :class="{'active':tabIndex===1}" @click="onTabClick(1)">已盘物料</li>
          </ul>
        </div>
      </div>
      <div style="flex: 1">
        <div style="height: 100%;position: relative" v-show="tabIndex===0">
          <scroll :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch;top: 0;">
            <item  v-for="item in  list[0].mats">
              <div style="display: flex">
                <div class="matBill-content" style="width: 80%">
                  <p class="title">物料名称: {{item.mat_desc}}</p>
                  <p class="title">物料编码: {{item.mat_no}}</p>
                  <p class="title">规格型号: {{item.mat_model}}</p>
                  <span>所在仓库: {{item.store_house_name}}</span>
                  <span>账面数: {{item.check_bill_qty}}</span> &nbsp;&nbsp;
                </div>
                <div class="edit-zone" style="width: 25%;flex-direction: column">
                  <div style="display: flex">
                    <span @click="minCount(item)">-</span>&nbsp;&nbsp;
                    <input type="number" v-model="item.check_real_qty" @input="countChange(item)"/>
                    &nbsp;&nbsp;<span @click="addCount(item)">+</span>
                  </div>
                  <br>
                  <p @click="confirm(item)">
                    确认
                  </p>
                </div>
              </div>
            </item>
            <div v-if="pageModels[0].curr_page >= pageModels[0].total_page" slot="infinite" class="text-center">没有更多数据</div>
          </scroll>
        </div>
        <div style="height: 100%;position: relative" v-show="tabIndex===1">
          <scroll :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch;top: 0">
            <item  v-for="item in  list[1].mats">
              <div style="display: flex">
                <div class="matBill-content" style="width: 80%">
                  <p class="title">物料名称: {{item.mat_desc}}</p>
                  <p class="title">物料编码: {{item.mat_no}}</p>
                  <p class="title">规格型号: {{item.mat_model}}</p>
                  <span>所在仓库: {{item.store_house_name}}</span>
                  <span>账面数: {{item.check_bill_qty}}</span> &nbsp;&nbsp;
                </div>
                <div class="edit-zone" style="width: 25%;flex-direction: column">
                  <div style="display: flex">
                    <span @click="minCount(item)">-</span>&nbsp;&nbsp;
                    <input type="tel" v-model="item.check_real_qty" @input="countChange(item)"/>
                    &nbsp;&nbsp;<span @click="addCount(item)">+</span>
                  </div>
                  <br>
                  <p @click="confirm(item)">
                    确认
                  </p>
                </div>
              </div>
            </item>
            <div v-if="pageModels[1].curr_page >= pageModels[1].total_page" slot="infinite" class="text-center">没有更多数据</div>
          </scroll>
        </div>
      </div>
    </div>
    <!--<mw-prompt :show="showPop" :okFunction="confirm" v-on:hidePop="showPop = false" v-on:input="promptVal"></mw-prompt>-->
    <div class="mw-page-footer">
      <div class="btns">
        <div class="btn">
          保存
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import { inventoryAdd,inventoryDetail,inventoryMat } from '@/api/storage/inventory'
  import CstItem from "../../../components/cstItem"
  import Scanner from '@/utils/Scanner'
  import { queryMats } from '@/api/storage/baseInfo'
  import SwipeItem from "../../../components/swipeItem";
  import MwPrompt from "../../../components/mwPrompt";
  export default{
      name:'inventoryDetail',
    components: {
      MwPrompt,
      SwipeItem,
      CstItem},
    data(){
      return {
        tabIndex:0,
        check_info:{
          store_house_name:'',
          store_house_id:'',
          check_bill_no:'',
          check_bill_id:'',
          operator_name:'',
          operator_id:'',
          mat_type_list:[], //默认待盘物料类型
        },
        reason:'',  //盈亏原因
        showPop:false,    //输入盈亏原因弹窗
        currItem:{},
        pageModels:[
          {curr_page:0,page_size:10,total_page:0},
          {curr_page:0,page_size:10,total_page:0},
        ],
        list:[
          {mats:[]},
          {mats:[]},
        ],
        query:{
          check_bill_id:'',
          liquidate_flag:'2',
          mat_desc:'',
          mat_no:'',
          mat_type_ids:[]
        },
        filter_mat_types:[],
        checkAll:false
      }
    },
    mounted(){
      let inventory = this.$route.query.inventory
      if (inventory) {
        this.check_info = JSON.parse(inventory)
        this.query.check_bill_id = this.check_info.check_bill_id
        this.getData(true)
      }
    },
    methods: {
      onMenuClick(){

      },
      onTabClick(index){
        if (index===this.tabIndex) {
          return
        }
        this.tabIndex = index
        this.query.liquidate_flag = index?'1':'2'
        if (this.list[index].mats.length === 0) {
          this.getData(true)
        }
      },
      onRefresh(done){
        const that = this
        setTimeout(function () {
          that.getData(true);
          done()
        },400)
      },
      //上拉加载 分页
      onInfinite(done){
        const that = this;
        if(that.pageModels[that.tabIndex].total_page<=that.pageModels[that.tabIndex].curr_page){
          done()
          return
        }
        setTimeout(function () {
          if(that.pageModels[that.tabIndex].total_page<2){
            done()
          }else{
            that.getData(true);
            done()
          }
        },500)
      },

      getData(refresh){
        const reqData = {
          curr_page:refresh?1:1+this.pageModels[this.tabIndex].curr_page,
          page_size:this.pageModels[this.tabIndex].page_size,
          query:this.query
        }
        inventoryDetail(reqData).then(res=>{
          if(res.scrap_mat_list.length>0){
            if(refresh){
              this.list[this.tabIndex].mats.length = 0
            }
            this.pageModels[this.tabIndex].curr_page = res.curr_page
            this.pageModels[this.tabIndex].total_page = res.total_page
            this.list[this.tabIndex].mats = this.list[this.tabIndex].mats.concat(res.scrap_mat_list)
          }
        }).catch(error=>{
          console.log(error)
          $toast.show(error.description)
        })

      },
      //扫描
      scan(){
        const that = this
        Scanner.scanbar(function (success) {
          console.log(success)
          that.queryMatByQR(success.text)
        },function (error) {
          console.log(error)
        })
      },
      //用扫描结果查询物料信息
      queryMatByQR(matNo){
        this.query.mat_no = matNo
        const reqData = {
          curr_page:1,
          page_size:10,
          query:this.query
        }
        inventoryDetail(reqData).then(res=>{
          console.log(res)
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      addCount(item){ //点击+号
        if(!item.check_real_qty) {
          this.$set(item,'check_real_qty',1)
        }else{
          this.$set(item,'check_real_qty',++item.check_real_qty)
        }
      },
      minCount(item){//点击-号
        if(item.check_real_qty===0){
         $toast.show('不能再减了')
          return
        }
        this.$set(item,'check_real_qty',--item.check_real_qty)
      },
      countChange(item){
        if(item.check_real_qty<0) {
          this.$set(item,'check_real_qty',0)
        }
      },
      promptVal(val){
        this.reason = val
      },
      //盘点单个物料
      confirm(item){

        if (item && item.check_real_qty !== item.check_bill_qty && !this.showPop){
          this.currItem = item
          this.showPop = true
          return
        }else {
          if (!this.reason) {
            $toast.show('请填写盈亏原因')
            return
          }
        }
        const reqData = {
          check_info:{
            check_bill_id:this.currItem.check_bill_id,
            check_bill_no:this.currItem.check_bill_no,
            mat_id:this.currItem.mat_id,
            check_real_qty:this.currItem.check_real_qty,
            check_profit_loss_reason:this.reason
          }
        }
        inventoryMat(reqData).then(res=>{
          this.showPop = false
          this.list[this.tabIndex].mats = this.list[this.tabIndex].mats.filter(o=>{
            return o.mat_id != this.currItem.mat_id
          })
        }).catch(error=>{
          $toast.show(error.description)
        })
      }
    },
    watch:{
      'checkAll':function (newVal) {
        if(newVal){
          this.filter_mat_types = this.check_info.mat_type_list
        }else{
          this.filter_mat_types = []
        }
      },
      filter_mat_types:function (newVal,oldVal) {
        if (newVal!==oldVal) {
          console.log(newVal)
          let arr = []
          newVal.forEach(o=>{
            arr.push(o.mat_type_id)
          })
          this.query.mat_type_ids = arr
          this.getData(true)
        }
      }
    }
  }
</script>

<style lang="scss" scoped>
  .matTypes{
    padding-left: 15px;
  }
</style>

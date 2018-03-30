<template>
    <div class="mw-page page storage" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>物料清单</span>
            <i class="right"></i>
        </div>
        <div class="mw-page-content has-tab">
          <div style="height: 50px;">
            <div style="display: flex;width: 100%;margin: 10px 15px;justify-content: center;align-items: center">
              <input type="search" style="flex:0 0 80%;border-radius: 5px" v-model="matSearch" placeholder="请输入物料编码"/>
              <i style="flex: 1;text-align: center" class="iconfont icon-saoyisao"></i>
            </div>
          </div>
          <div style="flex: 1;position: relative">
            <div class="hairline-top"></div>
            <item v-for="item in matList">
              <div style="display: flex">
                <div class="matBill-content" style="width: 80%">
                  <p class="title">物料名称: {{item.mat_desc}}</p>
                  <p class="title">物料编码: {{item.mat_no}}</p>
                  <p class="title">规格型号: {{item.mat_model}}</p>
                  <span>所在仓库: {{item.store_house_name}}</span>
                  <span>库存量: {{item.stock_qty}}</span> &nbsp;&nbsp;
                </div>
                <div class="edit-zone" style="width: 25%" v-if="item.quantity>0">
                    <span @click="minCount(item)">-</span>&nbsp;&nbsp;
                    <input type="number" v-model="item.quantity" disabled v-if="!item.quantity"/>
                    <input type="number" v-model.numbers="item.quantity" v-else @input="countChange(item)" disabled/>
                    &nbsp;&nbsp;<span @click="addCount(item)">+</span>
                </div>
                <div v-else class="edit-zone">
                  <span @click="addCount(item)">+</span>
                </div>
              </div>
            </item>
          </div>
        </div>
        <div class="mw-page-footer">
          <div class="btns">
            <div style="" class="total">
              <div style="display: flex">
                <span >总数量 :{{totalCount}}</span>
              </div>
            </div>
            <div class="btn" style="background-color: #416eb6;color: white;padding: 15px 0" @click="ok">
                确定
            </div>
          </div>
        </div>
    </div>
</template>

<script>
  import { storeSearchMat } from '@/api/storage/baseInfo'
  import _ from 'lodash'
  import sess from '@/utils/sess'
    export default{
      name:'matList',
        data(){
          return {
            matSearch: '',  //搜索关键字
            matList: [

            ],
            chosenMats:[],    //已选择的产品
//            totalPrice:0.00,  //总金额
            action:'check',
            pageModel:{
              curr_page:0,
              page_size:10
            }
          }
        },
        methods: {
          searchMats:_.debounce(
            function(){
              storeSearchMat({
                query:{
                  is_include_process_type:1,
                  mat_no:this.matSearch,
                  is_paging:1,
                  curr_page:++this.pageModel.curr_page,
                  page_size:this.pageModel.page_size
                }
              }).then(res=>{
                if(res.store_mat_list.length>0){
                  this.matList = res.store_mat_list
                }else{
                  $toast.show('结果查询为空')
                  this.matList = []
                }
              }).catch(error=>{
                $toast.show(error.description)
              })
            },1000),
          addCount(item){
            if(!item.quantity) {
              this.$set(item,'quantity',1)
              this.chosenMats.push(item)
            }else{
              if (item.quantity === item.stock_qty){
                $toast.show('不能超过物料库存',2000)
                return
              }
              this.$set(item,'quantity',++item.quantity)
            }
          },
          minCount(item){//点击-号

            if(item.quantity===1){
              $dialog.confirm({
                theme:'ios',
                title:'要删除物料吗？',
                okText:'确定',
                cancelText:'取消'
              }).then(res=>{
                if(res) {
                  item.quantity = 0
                  this.chosenMats = this.chosenMats.filter(o=>{
                    return o.mat_id !== item.mat_id
                  })
                }
              })
              return
            }
            this.$set(item,'quantity',--item.quantity)
          },
          countChange(item){
            if (item.quantity === item.stock_qty){
              $toast.show('不能超过物料库存',2000)
              return
            }
          },
          ok(){
            console.log('ok')
            if(this.chosenMats.length === 0){
              $toast.show('请选择物料')
              return
            }
            sess.set('mats',JSON.stringify(this.chosenMats))
            history.go(-1)
          }
        },
      computed:{
        totalCount(){
          let sumCount = 0
//          let sumPrice = 0.00
          this.chosenMats.filter(o=>{
            sumCount += o.quantity
//            sumPrice += (o.quantity*o.price)
          })
//          this.totalPrice = sumPrice
          return sumCount
        },
      },
      watch:{
        'matSearch':function () {
          this.searchMats()
        }
      }
    }
</script>

<style lang="scss" scoped>
  .total{
    flex: 0 0 80%;
    span{
      flex: 1 0 auto;text-align: center
    }
  }
</style>

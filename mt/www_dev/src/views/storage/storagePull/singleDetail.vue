<template>
    <div class="mw-page page storage" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>出库单</span>
            <i class="right" >
            </i>
        </div>
        <div class="mw-page-content singleDetail">
          <div class="header">
            <item v-if="$route.params.action==='check'">
              出库单单号:
              <span class="item-note">12345678</span>
            </item>
            <item>
              出库类型:
              <span  class="item-note" style="overflow: visible" >
                {{ $route.params.type }}
              </span>
            </item>
            <item>
              单号:
              <span class="item-note">
                {{ $route.params.bill_no }}
              </span>
            </item>
          </div>
          <div class="mateList">
            <div class="list">
              <div class="item" v-for="(item,index) in out_store_detail_list">
                <div class="itemInfo"  @click="onDetail">
                  <div class="numberBox" @click="stopPropagation">
                    <span @click="quantity(item,1)">+</span>
                    <input type="number"  @change="onChange(item)" v-model="item.quantity">
                    <span @click="quantity(item,-1)">-</span>
                  </div>
                  <h3>物料名称: <span>{{ item.mat_desc }}</span></h3>
                  <p>规格型号: <span>{{ item.mat_model }}</span></p>
                  <p>需出库数量: <span>{{ item.total }}</span></p>
                  <p>库存: <span>{{ item.stock_qty }}</span></p>
                </div>
                <div class="itemDetail">
                  <item>物料编码:<span class="item-note" >{{ item.mat_no }}</span></item>
                  <item>仓库名称:<span class="item-note" >{{ item.store_house_name }}</span></item>
                  <p>库位: <span>{{ item.store_room_name }}</span></p>
                  <item>收货单价:<span class="item-note" >{{ item.unit }}</span></item>
                  <item>入库重量:<span class="item-note" >{{ item.weight }}</span></item>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="mw-page-footer">
          <div class="btns">
            <div class="btn" @click="submit()">
                确认出库
            </div>
          </div>
        </div>
    </div>
</template>

<script>
  import { initDataOut,submitStorageOut } from '@/api/storage/storagePull'
  import { todayDate } from '@/utils'
  import SwipeItem from "../../../components/swipeItem";
    export default{
      name:'singleDetail',
      components: {SwipeItem},
      data(){
        return {
          verification:true,
          out_store_detail_list:[]
        }
      },
      computed:{
      },
      activated(){
      },
      methods: {
        onChange(e) {
          let quantity = Number(e.quantity)
          if (quantity % 1 !== 0) {
            e.quantity = parseInt(quantity)
          }
          if (quantity < 0) {
            e.quantity = -quantity
          }
          if(quantity>e.stock_qty){
            e.quantity = e.stock_qty
          }
          if (quantity > e.total) {
            e.quantity = e.total
          }
        },
        stopPropagation(){
          event.cancelBubble = true;
        },
        submit(){
          if(this.out_store_detail_list.length>0){
            const  requestData={
              out_store_info:{
                bill_no:this.$route.params.bill_no,
                dep_id:'',
                dep_name:'',
                out_store_detail_list:this.out_store_detail_list
              }
            }
            submitStorageOut(requestData).then(res => {
              $toast.show('出库单提交成功')
              $router.go(-1)
            }).catch(error => {
              $toast.show(error.description)
            })
          }else{
            $toast.show('该单号下没有可出库物料')
          }

        },
        onDetail(){
          let next=event.currentTarget.nextElementSibling||event.currentTarget.nextSibling
          if(next.classList.contains("active")){
            next.classList.remove('active')
          }else{
            next.classList.add('active')
          }
        },
        ckeck(item){
          if(typeof item.checked === 'undefined'){
            this.$set(item,'checked',true);
          }else {
            item.checked = !item.checked;
          }
          event.cancelBubble = true;
        },
        quantity(item,type){
          if(type===1){
            if(item.quantity<item.total){
              item.quantity=Number(item.quantity)+1
            }
          }else{
            if(Number(item.quantity)>0){
              item.quantity=Number(item.quantity)-1
            }else{
              item.quantity=0
            }
          }
        },
        initDataOut(){
          const  requestData={
            query:{
              bill_no:this.$route.params.bill_no
            }
          }
          initDataOut(requestData).then(res => {
              this.out_store_detail_list=res.out_store_info.out_store_detail_list
              if(this.out_store_detail_list.length>0){
                this.out_store_detail_list.forEach((e,i)=>{
                  e.total=e.quantity
                  e.chalkup_date=todayDate()
                })
              }
          }).catch(error => {
            $toast.show(error.description)
          })
        }
      },
      mounted(){
        this.initDataOut()
      }
    }
</script>

<style lang="scss" scoped>

</style>

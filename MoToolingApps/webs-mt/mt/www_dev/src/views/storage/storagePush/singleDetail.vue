<template>
    <div class="mw-page page storage" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>入库单</span>
            <i class="right"   @click="checkAll">
              全部入库
            </i>
        </div>
        <div class="mw-page-content singleDetail">
          <div class="header">
            <item v-if="$route.params.action==='check'">
              入库单单号:
              <span class="item-note">12345678</span>
            </item>
            <item>
              入库类型:
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
              <div class="item" v-for="(item,index) in in_store_detail_list" >
                <div class="itemInfo" style="padding-left: 36px" @click="onDetail">
                  <div class="checkBox" @click="ckeck(item)">
                    <i v-if="item.checked" class="icon ion-android-checkbox "></i>
                    <i v-else class="icon ion-android-checkbox-outline-blank "></i>
                  </div>
                  <div class="numberBox" @click="stopPropagation">
                    <span @click="quantity(item,1)">+</span>
                    <input type="number" v-model="item.quantity" @change="onChange(item)">
                    <span @click="quantity(item,-1)">-</span>
                  </div>
                  <h3>物料名称: <span>{{ item.mat_desc }}</span></h3>
                  <p>规格型号: <span>{{ item.mat_model }}</span></p>
                  <p>可入库数量: <span>{{ item.total }}</span></p>
                  <p>库位: <span>{{ item.store_room_name }}</span></p>
                </div>
                <div class="itemDetail">
                  <item>物料编码:<span class="item-note" >{{ item.mat_no }}</span></item>
                  <item>仓库名称:<span class="item-note" >{{ item.store_house_name }}</span></item>
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
                提交
            </div>
          </div>
        </div>
    </div>
</template>

<script>
  import { initDatain,submitStorage } from '@/api/storage/storagePush'
  import { todayDate } from '@/utils'
  import SwipeItem from "../../../components/swipeItem";
    export default{
      name:'scrapDetail',
      components: {SwipeItem},
      data(){
        return {
          in_store_detail_list:[]
        }
      },
      computed:{
      },
      activated(){
      },
      methods: {
        checkAll(){
          this.in_store_detail_list.forEach((e,i)=>{
            if(!e.checked){
              this.$set(e,'checked',true);
            }else{
              e.checked=true
            }
          })
        },
        onChange(e){
          let quantity=Number(e.quantity)
          if(quantity%1!==0){
            e.quantity=parseInt(quantity)
          }
          if(quantity<0){
            e.quantity=-quantity
          }
          if(quantity>e.total){
            e.quantity=e.total
          }
        },
        stopPropagation(){
          event.cancelBubble = true;
        },
        submit(){
          const  requestData={
            in_store_info:{
              bill_no:this.$route.params.bill_no,
              dep_id:'',
              dep_name:'',
              in_store_detail_list:[]
            }
          }
          this.in_store_detail_list.forEach((e,i)=>{
            if(e.checked){
              requestData.in_store_info.in_store_detail_list.push(e)
            }
          })
          if( requestData.in_store_info.in_store_detail_list.length>0){
            submitStorage(requestData).then(res => {
              $toast.show('入库单提交成功')
              $router.go(-1)
            }).catch(error => {
              $toast.show(error.description)
            })
          }else{
            $toast.show('请选择入库物料')
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
        initDatain(){

          const  requestData={
            query:{
              bill_no:this.$route.params.bill_no
            }
          }
          initDatain(requestData).then(res => {
            this.in_store_detail_list=res.in_store_info.in_store_detail_list
            this.in_store_detail_list.forEach(function(e,i){
              e.total=e.quantity
              e.chalkup_date=todayDate()
            })
          }).catch(error => {
            $toast.show(error.description)
          })
        }
      },
      mounted(){
        this.initDatain()
      }
    }
</script>

<style lang="scss" scoped>

</style>

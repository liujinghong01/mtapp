<template>
    <div class="mw-page page storage" v-nav="{hideNavbar:true}">
      <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span >{{ voucher_no }}</span>
            <i   class="right" ></i>
        </div>
        <div class="mw-page-content singleDetail" >
          <cst-item   label="入库类型" v-model="voucher_info.trans_type"  :action="'check'" editType="text"></cst-item>
          <cst-item   label="关联凭证" v-model="voucher_info.indent_no"  :action="'check'" editType="text"></cst-item>
          <cst-item   label="凭证日期" v-model="voucher_info.trans_date"  :action="'check'" editType="text"></cst-item>
          <cst-item   label="记账日期" v-model="voucher_info.chalkup_date"  :action="'check'" editType="text"></cst-item>
          <cst-item   label="操作人" v-model="voucher_info.handler_name"  :action="'check'" editType="text"></cst-item>
          <!--<cst-item   label="关联对象" v-model="voucher_info.relate_object"  :action="'check'" editType="text"></cst-item>-->
          <div  class="item-divider" style="padding-left: 15px;">物料列表</div>
          <item v-for="(item,index) in voucher_info.detailList"  class="mate-item" :class="active?'active':''"  @click.native="active=!active">
            <h3 class="vi-color" style="font-size: 16px">{{ item.mat_desc }}</h3>
            <div >规格型号 ：<span >{{ item.mat_model }}</span></div>
            <div >库存 ：<span >{{ item.stock_qty }}</span></div>
            <div >金额 ：<span >{{ item.total_price }}</span></div>
            <div class="mat-info">
              <!--<div >收讫状态：<span >{{ e. }}</span></div>-->
              <div >库存地点：<span >{{ item.store_house_name }}</span></div>
              <div >库位：<span >{{ item.store_room_name }}</span></div>
              <div >收货数量：<span >{{ item.voucher_qty }}</span></div>
              <!--<div >收货重量：<span >500kg</span></div>-->
              <div >收货金额：<span >{{ item.voucher_price }}元</span></div>
              <!--<div >入库金额：<span >1800.00元</span></div>-->
              <!--<div >加工费收货金额：<span >300.00元</span></div>-->
            </div>
          </item>


        </div>
    </div>
</template>

<script>
  import { voucherDetail } from '@/api/storage/voucher'
  import CstItem from "../../../components/cstItem"
    export default{
      name:'voucherDetail',
      components: {CstItem},
      data(){
        return {
          voucher_no:'',
          type:'采购入库',
          active:false,
          voucher_info:{},
          detail_list:{}

        }
      },
      activated(){
      },
      methods: {
        voucherDetail(){
          const requestData={
            query:{
              voucher_no:'PZ17120114'
            }
          }
          voucherDetail(requestData).then(res => {
              this.voucher_info=res.voucher_info
            this.detail_list=res.voucher_info.detail_list
          }).catch(error => {
            $toast.show(error.description)
            // this.pageModels[this.tabIndex].total_page=''
          })
        }

      },
      mounted(){
        this.voucher_no=this.$route.params.voucher_no
        this.voucherDetail()
      }
    }
</script>

<style lang="scss" scoped>
  .mate-item{
    line-height: 24px;
    .mat-info{
      height: 0px;
      overflow: hidden;
      transition: all .3s;
    }
    &.active{
      background: #fff;
      .mat-info{
        height: 190px;
      }

    }
  }
</style>

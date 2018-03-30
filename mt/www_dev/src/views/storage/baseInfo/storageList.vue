<template>
    <div class="mw-page page storage" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>仓库列表</span>
            <i class="right"></i>
        </div>
        <div class="mw-page-content">
          <div>
            <swipe-item class="req-item"  :obj="item" swipeItemText="删除" v-for="item in storehouse_list"
                        v-on:ItemClick="itemClick(item)" v-on:SwipeItemClick="delItem">
              <div class="req-item-title"><span class="vi-color">{{item.store_house_name}}</span><i class="icon ion-ios-arrow-right"></i></div>
              <div class="req-item-content">
                <span>总资产 : {{item.total_price}}</span>
                <span>总数量 : {{item.stock_qty}}</span>
              </div>
            </swipe-item>
          </div>
        </div>
        <div class="mw-page-footer">
            <div class="btns">
                <div class="btn" @click="$router.forward('/storage/storageDetail/new/null')">
                    新增
                </div>
            </div>
        </div>
    </div>
</template>

<script>
  import { storageList,storageDel } from '@/api/storage/baseInfo'
  import SwipeItem from "../../../components/swipeItem";
    export default{
        name:'storageList',
      components: {SwipeItem},
      data(){
        return {
          storehouse_list:[]
        }
      },
      created(){

      },
      activated(){
        this.getData()
      },
      methods: {
        getData(){
          const reqData = {
            is_paging:0
          }
          storageList(reqData).then(res=>{
            console.log(res)
            this.storehouse_list = res.storehouse_list
          }).catch(error=>{
            $toast.show(error.description)
          })
        },
        //点击查看详情
        itemClick(item){
          console.log(item)
          this.$router.forward('/storage/storageDetail/check/'+item.store_house_id)
        },
        //删除仓库
        delItem(item) {
          $dialog.confirm({
            theme:'ios',
            title:'提示',
            content:'确定要删除吗?',
            okText:'确定',
            cancelText:'取消'
          }).then(ok=>{
            if(ok) {
              storageDel({
                store_house_id:item.store_house_id
              }).then(res=>{
                this.storehouse_list = this.storehouse_list.filter(o=>{
                  return o.store_house_id != item.store_house_id
                })
              }).catch(error=>{
                $toast.show(error.description)
              })
            }
          }).catch(error=>{
            $toast.show(error.description)
          })
        }

      }
    }
</script>

<style lang="scss" scoped>

</style>

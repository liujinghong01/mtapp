<template>
    <div class="mw-page page storage" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>物料列表</span>
            <i class="right" @click=" $router.forward({path:'/storage/newMat'})">新增</i>
        </div>
        <div class="mw-page-content">
          <div class="content">
            <scroll class="page-content" :on-infinite="onInfinite" style="-webkit-overflow-scrolling: touch">
              <swipe-item  :obj="item" swipeItemText="删除" @click.native="itemClick(item)"
                         v-on:SwipeItemClick="itemDel(item)" v-for="item in list">

                <p >编号：<span style="color: #416eb6">{{ item.mat_no }}</span></p>
                <p>物料名称：{{ item.mat_name }}</p>
                <p>规格型号：{{ item.spec }}</p>
                <p>库存：{{ item.batch_qty }}</p>
              </swipe-item>
              <div v-if="pageModels.curr_page >= pageModels.total_page" slot="infinite" class="text-center">没有更多数据</div>
            </scroll>
          </div>
        </div>

    </div>
</template>

<script>
  import { storageMats,storageMatModify,storageMatDel,matNameList } from '@/api/storage/baseInfo'
  import SwipeItem from "../../../components/swipeItem";
    export default{
      name:'BasicDataList',
      components: {
        SwipeItem},
      data(){
        return {
          pageModels:{curr_page:0,page_size:10,total_page:0 },
          list:[],
          action:'check',
          addType:false,
          newType:''
        }
      },
      activated(){
        this.getData()
      },
      methods: {
        onInfinite(done) {
          const that = this;
          if(that.pageModels.total_page<=that.pageModels.curr_page){
            done()
            return
          }
          setTimeout(function () {
            if(that.pageModels.total_page<2){
              done()
            }else{
              that.getData()
              done()
            }
          },400)
        },
        getData(){
          const reqData = {
            curr_page:this.pageModels.curr_page+=1,
            page_size:this.pageModels.page_size
          }
          matNameList(reqData).then(res=>{
            this.list = this.list.concat(res.list)
            this.pageModels.total_page=res.total_page
          }).catch(error=>{
            $toast.show(error.description)
          })
        },
        //查看详情
        itemClick(item) {
          this.$router.forward({path:'/storage/matDetaile',query:{
              mat_id:item.mat_id
            }})
        },
        add(){
          if (!this.newType||!this.newType.trim()){
            $toast.show('类型名称不能为空')
            return
          }
          storageMatModify({mat_type_info:{mat_type_name:this.newType}}).then(res=>{
            $toast.show('保存成功')
            this.addType = false
            this.newType =''
          }).catch(error=>{
            $toast.show(error.description)
          })
        },
        //删除仓库
        itemDel(item) {
          if (item.is_default=='1') {
            $toast.show('缺省物料类型无法删除')
            return
          }
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

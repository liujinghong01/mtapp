<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>工艺模板列表</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content">
      <div style="flex: 1">
        <div class="content" >
            <swipe-item v-for="(item,index) in tmpl_list" class="req-item" @click.native="onItemClick(item)"  :obj="item" :canSwipe="true" swipeItemText="删除" v-on:SwipeItemClick="tmplDelete(item,index)">

              <p>{{item.tmpl_name}}</p>

            </swipe-item>
        </div>
      </div>
    </div>
    <div class="mw-page-footer">
      <div class="btns">
        <div class="btn" @click="add">
          添加
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import { tmplList,tmplDelete } from '@/api/production/procTemplate'
  import SwipeItem from "../../../components/swipeItem";
  import CstItem from "../../../components/cstItem"
  export default{
    name:'procTemplate',
    components: {SwipeItem,CstItem},
    data(){
      return {
        tmpl_list:[]
      }
    },
    computed:{

    },
    activated(){
      this.getData()
    },
    mounted(){
    },
    methods: {
      tmplDelete(item,index){
        const  requestData={
          proc_info:{
            tmpl_id:item.id,
          }
        }
        tmplDelete(requestData).then(res => {
          $toast.show('删除成功')
          this.tmpl_list.splice(index,1)
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      //获取数据
      getData(refresh){
        const  requestData={
          query:{
            status:'',
            tmpl_name:'',
            tmpl_type_id:'',
            tmpl_type_name:''
          }
        }
        tmplList(requestData).then(res => {
          this.tmpl_list=res.routing_tmpl_info
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      onItemClick(item){
        $router.forward({path:'templateDetaile',query:{action:'check',tmpl_id:item.id}})
      },
      add(){
          $router.forward({path:'templateDetaile',query:{action:'new'}})
      }
    }
  }
</script>

<style lang="scss" scoped>
.calendarItem{
  position: relative;
  .color{
    width: 30px;
    height: 17px;
    position: absolute;
    left: 0px;
    top: -2px;
    background: red;
    border-radius: 5px;
  }
}
</style>

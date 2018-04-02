<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>基础工序列表</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content">
      <div style="flex: 1">
        <div class="content" >
            <swipe-item v-for="(item,index) in procList" class="req-item" @click.native="onItemClick(item)"  :obj="item" :canSwipe="true" swipeItemText="删除" v-on:SwipeItemClick="processDelete(item,index)">
              <div class="calendarItem">
                <p>{{item.proc_no+":"+ item.proc_name }}</p>
              </div>
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
  import { processList,processDelete } from '@/api/production/procedure'
  import SwipeItem from "../../../components/swipeItem";
  import CstItem from "../../../components/cstItem"
  export default{
    name:'procedure',
    components: {SwipeItem,CstItem},
    data(){
      return {
        procList:[]
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
      processDelete(item,index){
        const  requestData={
          proc_info:{
            proc_id:item.id,
          }
        }
        processDelete(requestData).then(res => {
          this.procList.splice(index,1)
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      //获取数据
      getData(refresh){
        const  requestData={
          query:{
            proc_name:'',
            proc_no:'',
            status:1
          }
        }
        processList(requestData).then(res => {
          this.procList=res.proc_info
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      onItemClick(item){
        $router.forward({path:'procedureDetaile',query:{action:'check',proc_id:item.id}})
      },
      add(){
          $router.forward({path:'procedureDetaile',query:{action:'new'}})

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

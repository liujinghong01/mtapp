<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>任务领取</span>
      <i class="right" >扫描</i>
    </div>
    <div class="mw-page-content has-tab">
      <div style="height: 60px">
        <tabs :tab-items="tabs" :tab-index="tabIndex" :on-tab-click="onTabClick"></tabs>
      </div>
      <div style="flex: 1">
        <div class="content" v-show="tabIndex===0">
          <scroll class="page-content"  style="-webkit-overflow-scrolling: touch">
            <div class="itemBox" :style="{height:e.check?'40px':(e.pop_list.length*64+40)+'px' }" v-for="(e,i) in pg_list">
              <p class="itemTitle" @click="check(e)">生产小组：{{ e.pg_name }} <i  :class="e.check?'ion-arrow-down-b':'ion-arrow-up-b'"></i></p>
              <item style="height: 64px" v-for="(item,index) in e.pop_list"   @click.native="check(item)">
                <div class="checkBox "  :class="item.check?'active':''" >
                  <i class="ion-android-radio-button-off " :class="item.check?'ion-android-radio-button-on ':'ion-android-radio-button-off'" ></i>
                </div>
                <div class="itemInfo hasCheck" >
                  <div>
                    <p >{{ item.mat_no }}</p>
                    <p style="height: 14px;line-height: 14px">
                      <span style="margin-right: 5px;display: inline-block;max-width: 70%">物料名称:{{item.mat_name}}</span>
                    </p>
                    <p><span style="margin-right: 5px;display: inline-block;max-width: 70%">工序:{{item.proc_name}}</span></p>
                  </div>
                  <div class="status" :class="'active'+item.pri_flag"></div>
                </div>
              </item>
            </div>

          </scroll>
        </div>
        <div class="content" v-show="tabIndex===1">
          <scroll class="page-content" style="-webkit-overflow-scrolling: touch">
            <div class="itemBox" :style="{height:e.check?'40px':(e.pop_list.length*64+40)+'px' }" v-for="(e,i) in pop_list">
              <p class="itemTitle" @click="check(e)">生产小组：{{ e.pg_name }} <i  :class="e.check?'ion-arrow-down-b':'ion-arrow-up-b'"></i></p>
              <item style="height: 64px" v-for="(item,index) in e.pop_list"  @click.native="onItemClick(item)">
                <div class="itemInfo" >
                  <div>
                    <p >{{ item.mat_no }}</p>
                    <p style="height: 14px;line-height: 14px">
                      <span style="margin-right: 5px;display: inline-block;max-width: 70%">物料名称:{{item.mat_name}}</span>
                    </p>
                    <p><span style="margin-right: 5px;display: inline-block;max-width: 70%">工序:{{item.proc_name}}</span></p>
                    <span v-if="item.allocated_list.length<1" class="right">分配任务</span>
                    <!--<span v-if="item.allocated_list.length<1" class="right">指派任务</span>-->
                  </div>
                  <div class="status" :class="'active'+item.pri_flag"></div>
                </div>
              </item>
            </div>
          </scroll>
        </div>
      </div>
    </div>
    <div class="mw-page-footer" v-show="tabIndex===0">
      <div class="btns">
        <div class="btn" @click="incept">
          领取
        </div>
      </div>
    </div>

  </div>
</template>

<script>
  import { inceptList,incept } from '@/api/production/work'
  import SwipeItem from "../../../components/swipeItem";
  import CstItem from "../../../components/cstItem"
  import sess from '@/utils/sess'
  export default{
    name:'getTask',
    components: {SwipeItem,CstItem},
    data(){
      return {
        tabs:['待领取任务','已领取任务'],
        tabIndex:0,
        pg_list:[],
        pop_list:[],
        pop_ids:[]
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
      onTabClick(index) {
        if(index===this.tabIndex) {
          return
        }
        this.tabIndex = index
        this.getData()
      },
      //获取数据
      getData(){
        const  requestData={
          curr_page:1,
          page_size:100,
          query:{
            pop_status:this.tabIndex+2
          }
        }
        inceptList(requestData).then(res => {
          if(this.tabIndex===0){
            this.pg_list=res.pg_list
          }else{
            this.pop_list=res.pg_list
          }

        }).catch(error => {
          $toast.show(error.description)
        })
      },
      onItemClick(item){
        if(item.allocated_list.length>0){
            sess.set('allocated_list',item.allocated_list)
        }
        if(item.pg_type+''==='0'){
          $router.forward({
            path:'/production/getTask/devList'
            ,query:{
              pg_id:item.pg_id,
              pop_id:item.pop_id
            }
          })
        }else{
          $router.forward({
            path:'/production/getTask/persList'
            ,query:{
              pg_id:item.pg_id,
              pop_id:item.pop_id
            }
          })
        }
      },
      check(item){
        if(item.check===undefined){
          this.$set(item,'check',true)
          if(item.pop_id!==undefined){
            this.pop_ids.push(item.pop_id)
          }
        }else{
          this.$delete(item,'check')
          if(item.pop_id!==undefined){
            let index=this.pop_ids.indexOf(item.pop_id)
            this.pop_ids.splice(index,1)
          }
        }
      },
      incept(){
        if(this.pop_ids.length<1){
          $toast.show('请先选择任务')
          return
        }
        const  requestData={
          incept_info:{
            incept_flag:1,
            pop_ids:this.pop_ids
          }
        }
        incept(requestData).then(res => {
          $toast.show('领取成功')
          this.getData()
        }).catch(error => {
          $toast.show(error.description)
        })
      }

    }
  }
</script>
<style lang="scss" scoped>
  .itemBox{
    overflow: hidden;
    transition: all .3s;
    .itemTitle{
      margin: 0;
      height: 40px;
      line-height: 40px;
      padding: 0 15px;
      position: relative;
      i{
        position: absolute;
        right: 15px;
        top: 0;
        width: 40px;
        height: 40px;
        line-height: 40px;
        text-align: center;
        color:#416eb6;
      }
    }
  }

  .checkBox{
    width: 8%;
    text-align: right;
    height: 64px;
    line-height: 64px;
    position: absolute;
    left: 0;
    top: 0;
    font-size: 20px;
    color: #9e9e9e;

    &.active{
      color: #416eb6;
    }
  }
  .itemInfo{
    width: 100%;
    height: 64px;
    position: absolute;
    right: 0;
    top: 0;
    padding: 7px 15px 7px 55px;
    line-height: 20px;
    &.hasCheck{
      width: 92%;
    }
    p{
      margin-bottom: 0;
      span{
        font-size: 12px;
        color: #999;
      }
    }
    span.right{
      width: 60px;
      height: 65px;
      line-height: 65px;
      position: absolute;
      right: 0;
      top: 0;
      color:#416eb6 ;
    }
    .status{
      width: 30px;
      height: 22px;
      position: absolute;
      left: 15px;
      top:21px;

      &.active1{
        background: #33FF33;
      }
      &.active2{
        background:#FF00FF;
      }
      &.active3{
        background: red;
      }
      &.active4{
        background: #666666;
      }
    }
  }
</style>

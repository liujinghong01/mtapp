<template>
  <div class="mw-page page  purchase" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>询价单详情</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content" style="display: flex;flex-direction: column;-webkit-box-orient:vertical">
      <div>
        <div class="mw-tab">
          <ul style="min-width: 100%" :style="{'width': width+'px'}" class="tab-content" v-if="action==='action'">
            <li v-for="(item, index) in inquiry_feedback_list" :class="tabIndex===index&&action==='action'?'active':''"  @click="tabClick(index)">{{item.sup_name}}</li>
          </ul>
          <ul style="min-width: 100%" :style="{'width': width+'px'}" class="tab-content" v-if="action!=='action'">
            <li v-for="(item, index) in inquiry_feedback_list[0].sup_list" :class="tabIndex===index&&action==='action'?'active':''"  @click="tabClick(index)">{{item.sup_name}}</li>
          </ul>
        </div>
      </div>
      <div class="divider"></div>
      <div class="scrollbar_hide" style="flex: 1;overflow-y: scroll;overflow-x: hidden">
        <div :class="['inquiry_detail',tabIndex===index?'active':'']" v-for="(item,index) in inquiry_feedback_list">
          <item >
            报价截止日期
            <span class="item-note">{{item.quote_end_date}}</span>
          </item>
          <item>
            期望交货日期
            <span class="item-note">{{item.delivery_date}}</span>
          </item>
          <item>
            交货地点
            <span class="item-note">{{item.delivery_place}}</span>
          </item>
          <item>
            货币类型
            <span class="item-note">{{item.coin_name}}</span>
          </item>
          <div v-for="mat in item.mat_list" style="margin-top: 8px">
            <swipe-item swipeItemText="删除" :obj="mat" v-on:ItemClick="onItemClick">
              <div :class="['swipe-content',item.swipe?'active':'']">
                物料描述
                <span class="item-note normal">{{mat.mat_desc}} <i :class="['left icon',mat.col? 'ion-ios-arrow-down':'ion-ios-arrow-up']"></i></span>
              </div>
            </swipe-item>
            <div :class="['col-content',mat.col?'coll':'']">
              <item>
                单价
                <span class="item-note">{{mat.up}}</span>
              </item>
              <item>
                数量
                <span class="item-note">{{mat.quantity}}</span>
              </item>
              <item>
                加工费/件
                <span class="item-note">{{mat.process_up}}</span>
              </item>
              <item>
                加工费金额
                <span class="item-note">{{mat.process_cost}}</span>
              </item>
              <item>
                金额
                <span class="item-note">{{mat.total_price}}</span>
              </item>
            </div>
          </div>
          <item style="margin-top: 8px">
            总金额
            <span class="item-note">{{item.total_price}}</span>
          </item>
          <textarea v-show="inquiry_feedback_list[tabIndex].remark" v-model="inquiry_feedback_list[tabIndex].remark" disabled>
          </textarea>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import SwipeItem from "../../../components/swipeItem";
  import  sess from '@/utils/sess'
  import {todayDate} from '@/utils'
  import {mapGetters} from 'vuex'
  import SidebarCheck from "../../../components/sidebarCheck";
  import { sendInquiry } from '@/api/purchase/inquiry'
  import { querySupByMat } from '@/api/dic'
  import MwCheckbox from "../../../components/mwCheckbox";
  //{供应商反馈单详情，查询询价反馈单详情}
  import { inquiryDetail,inquiryFeedbackDetail } from '@/api/purchase/inquiry';
  export default{
    name:'inquiryDetail',
    components: {SwipeItem},
    data(){
      return {
        tabIndex :0,
        width:0,
        companys:[],
        action:'check',
        inquiry_feedback_list:[{sup_list:''}],
        showSups:false, //是否显示选择供应商
        supSearch:'',  //搜索供应商
        inquiry_id:'',
        inquiry_info:{
          quote_end_date:'2018-1-2'
        }
      }
    },

    created:function(){
      this.action = this.$route.params.action
      if(this.action!=='new'){
        this.inquiry_id = this.$route.params.inquiry_id
        if(this.action === 'action'){
          //询价反馈单详情
          this.FeedbackDetail()
        }else{
          //询价单详情
          this.setInquiryDetail()
        }
      }

    },
    methods: {
      //获取询价单详情
      setInquiryDetail(){
        const requestData={
          query:{inquiry_id:this.inquiry_id}
        };
        inquiryDetail(requestData).then(res=>{
          this.inquiry_feedback_list= [res.inquiry_info];
          if(res.inquiry_info.sup_list.length){
            this.width=res.inquiry_info.sup_list.length*100;
          }

        }).catch(error=>{
          $toast.show(error.description);
        })
      },
      //询价反馈单详情
      FeedbackDetail(){
        const requestData={
          query:{
            inquiry_id:this.inquiry_id,
            feedback_id:""
          }
        };
        inquiryFeedbackDetail(requestData).then(res=>{
          this.inquiry_feedback_list=res.inquiry_feedback_list;
          this.width=this.inquiry_feedback_list.length*100;
        }).catch(error=>{
          $toast.show(error.description);
        })
      },
      onMenuClick(){

      },
      tabClick(index){
        if(this.$route.params.action!='action'){
          return
        }else{
          this.tabIndex = index
          const ulW = document.querySelector('.tab-content').scrollWidth
          const liSL = document.querySelector('.tab-content').children[index].offsetLeft
          const winWidth = window.innerWidth
        }

//            let po
//            if(ulW>winWidth){
//              po = -100*(index-2)<(winWidth-ulW)?(winWidth-ulW):-100*(index-2)
//            }
//            if(liSL>=300){
//              document.querySelector('.tab-content').style.transform = 'translateX('+po+'px)'
//            }else{
//              document.querySelector('.tab-content').style.transform = 'translateX('+0+'px)'
//            }
      },
      onItemClick(item){
        console.log(item)
        this.$set(item,'col',!item.col)
      }
    }
  }
</script>

<style lang="scss" scoped>
  .material .col-content{overflow: hidden}
</style>

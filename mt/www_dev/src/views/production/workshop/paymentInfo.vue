<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span style="font-weight: 400">应付供应商账款表</span>
      <i class="right" ></i>
    </div>
    <div class="mw-page-content" style="padding-top: 15px">
        <div class="pieBox">
          <i></i>
          <img src="../../../assets/img/workshop/pie.png" alt="">
          <div class="pie-list">
            <div class="pie-item" v-for="(item,index) in data">
              <i>
                <s v-bind:style="{background:item.color}"></s>
                <b v-bind:style="{background:item.color}"></b>
              </i>
              <p>{{ item.name }}</p>
              <span>{{ item.value }}</span>
            </div>
          </div>
          <p>
            合计金额
            <span>2,298,083</span>
          </p>
          <p>
            增值税
            <span>372,833</span>
          </p>
        </div>
        <div class="detailed">
          清单明细
          <i  class="ion-chevron-down"></i>
        </div>
    </div>

  </div>
</template>

<script>
  // import { confirmList,popProcInfo,waitProcList } from '@/api/production/work'
  // import SwipeItem from "../../../components/swipeItem";
  // import CstItem from "../../../components/cstItem"
  export default{
    name:'paymentInfo',
    components: {},
    data(){
      return {
        data:[
          {
            name:'模架',
            value:'15%',
            color:'#74A9FF'
          },
          {
            name:'胚料',
            value:'17%',
            color:'#EBB1FF'
          },
          {
            name:'标准件',
            value:'22%',
            color:'#9391FF'
          },
          {
            name:'办公用品',
            value:'4%',
            color:'#B0E9AC'
          },
          {
            name:'外协加工',
            value:'30%',
            color:'#FACE49'
          },
          {
            name:'其他',
            value:'12%',
            color:'#FF8A8D'
          }
        ]
      }
    },
    computed:{
    },
    activated(){
    },
    mounted(){
    },
    methods: {}
  }
</script>
<style lang="scss" scoped>
  .pieBox{
    width: 290px;
    height: 270px;
    margin: 0 auto;
    background: #fff;
    box-shadow: 0 5px 10px 0 rgba(84,149,255,0.10);
    border-radius: 20px;
    padding: 15px;
    position: relative;
    margin-bottom: 20px;
    img{
      width: 140px;
      height: 140px;
    }
    &>i{
      position: absolute;
      width: 20px;
      height: 18px;
      right: -3px;
      top: -3px;
      background: url("../../../assets/img/workshop/icon.png") no-repeat center center;
      background-size: 100% 100%;
    }
    .pie-list{
      width: 120px;
      position: absolute;
      top: 15px;
      right: 15px;
      .pie-item{
        width: 100%;
        height: 25px;
        line-height: 25px;
        font-size: 12px;
        position: relative;
        i{
          position: absolute;
          width: 12px;
          height: 12px;
          left: 6px;
          top: 6px;
          s{
            width: 6px;
            height: 6px;
            position: absolute;
            top: 3px;
            left: 3px;
            border-radius: 50%;
            background: #5495FF;
          }
          b{
            width: 12px;
            height: 12px;
            position: absolute;
            top: 0px;
            left: 0px;
            border-radius: 50%;
            background: #5495FF;
            opacity: .3;
          }
        }
        p{
          width: 85px;
          margin: 0;
          padding-left: 24px;
        }
        span{
          position: absolute;
          width: 30px;
          height: 40px;
          top: 0;
          right: 0;
          text-align: right;
        }
      }
    }
    &>p{
      width: 100%;
      height: 40px;
      line-height: 40px;
      margin: 0;
      font-size: 16px;
      border-top: 1px #E0E0E0 solid;
      &:first-of-type{
        border-top:none;
        margin-top: 15px;
      }
      span{
        float: right;
      }
    }
  }
  .detailed{
    width: 290px;
    height: 50px;
    line-height: 50px;
    background: #FFFFFF;
    box-shadow: 0 5px 10px 0 rgba(84,149,255,0.10);
    border-radius: 10px;
    margin: 0 auto;
    font-size: 16px;
    padding-left: 15px;
    position: relative;
    .ion-chevron-down{
      width: 50px;
      height: 50px;
      text-align:center;
      line-height: 50px;
      position: absolute;
      right: 0;
      top: 0;
    }
  }
</style>

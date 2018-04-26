<template>
    <div class="mw-page page" v-nav="{hideNavbar:true}">
      <div class="mw-page-header">
        <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
        <span>报价单</span>
        <i class="right icon ion-funnel" style="font-size: 16px" @click="showSidebar"></i>
      </div>
      <div class="mw-page-content">
        <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
          <div class="order-item">
            <div>
              <div class="order-item-title">
                <p>产品名称：CNC02</p>
                <span>参考</span>
              </div>
              <p>客户：天水创业有限公司</p>
              <p>报价日期：2018-04-20</p>
            </div>

          </div>

          <div  slot="infinite" class="text-center">没有更多数据</div>
        </scroll>
      </div>
      <div v-show="maskShow" class="mask" @click="maskHide"></div>
      <div v-show="sidebarShow" class="sidebar" v-bind:style="{transform:translate}">
        <h3>删选条件</h3>
        <div>
          产品名称
          <input type="text" placeholder="请输入产品名称">
        </div>
        <div>
          客户名称
          <input type="text" placeholder="请输入客户名称">
        </div>
      </div>
      <div  class="product-details" v-show="productDetaileShow"  v-bind:style="{marginTop:productDetaileTop,transform:scale}">
        <h3>制品详情 <i class="ion-close-round"  @click="maskHide"></i></h3>
        <div class="scrollBox">

          <ul>
            <li>
              <span>编号</span>
              <input placeholder="" type="text">
            </li>
            <li>
              <span>客户</span>
            </li>
            <li>
              <span>联系人</span>
            </li>
            <li>
              <span>电话</span>
            </li>
            <li>
              <span>邮箱</span>
            </li><li>
            <span>编号</span>
            <input placeholder="" type="text">
          </li>
            <li>
              <span>客户</span>
            </li>
            <li>
              <span>联系人</span>
            </li>
            <li>
              <span>电话</span>
            </li>
            <li>
              <span>邮箱</span>
            </li><li>
            <span>编号</span>
            <input placeholder="" type="text">
          </li>
            <li>
              <span>客户</span>
            </li>
            <li>
              <span>联系人</span>
            </li>
            <li>
              <span>电话</span>
            </li>
            <li>
              <span>邮箱</span>
            </li><li>
            <span>编号</span>
            <input placeholder="" type="text">
          </li>
            <li>
              <span>客户</span>
            </li>
            <li>
              <span>联系人</span>
            </li>
            <li>
              <span>电话</span>
            </li>
            <li>
              <span>邮箱</span>
            </li>
          </ul>
        </div>
      </div>

  </div>
</template>
<script>
  import { mapGetters } from 'vuex'
    export default{
      name:'quotes_list',
      components:{
      },
      data(){
        return{
          sidebarShow:true,
          maskShow:false,
          productDetaileShow:false,
          productDetaileTop:'0',
          scale:'scale(0,0)',
          translate:'translate(100%)'
        }
      },
      mounted() {

      },
      activated(){

      },
      computed:{
      },
      methods:{
        onInfinite(){},
        onRefresh(){},
        deleteItem(){
          event.cancelBubble = true;
          $dialog.confirm({
            theme: 'ios',
            title: '确定要删除吗？',
            cancelText: '取消',
            okText: '确定'
          }).then(res=>{
            if(res){
              alert(1)
            }
          })
        },
        cancelBubble(){
          event.cancelBubble = true;
        },
        showSidebar(){
          this.maskShow=true
          this.sidebarShow=true
          setTimeout(()=>{
            this.translate='translate(0%)'
          },100)
        },
        showDetaile(){
          this.productDetaileShow=true
          this.maskShow=true
          setTimeout(()=>{
            this.scale='scale(1,1)'
            this.productDetaileTop='-'+document.querySelector('.product-details').clientHeight/2+'px'
          },100)
        },
        maskHide(){
          this.maskShow=false
            this.moreShow=false
            this.productDetaileShow=false
            this.productDetaileTop='0'
          this.translate='translate(100%)'
            this.scale='scale(0,0)'
        }

      }
    }
</script>
<style lang="scss" scoped>
  input:disabled{
    background: transparent;
  }
  textarea:disabled{
    background: transparent;
    border: none !important;
  }
  .mask{
    width: 100%;
    height: 100vh;
    position: absolute;
    top: 0;
    left: 0;
    background: rgba(0,0,0,.2);
    z-index: 15;
  }
  .sidebar{
    width: 70%;
    height: 100vh;
    position: absolute;
    top: 0;
    right: 0;
    background:#fff;
    padding-left: 15px;
    transform: translate(100%);
    transition: all .3s ;
    z-index: 20;
    &>h3{
      width: 100%;
      line-height: 60px;
      font-size: 18px;
      color:#416eb6;
    }
    &>div{
      width: 100%;
      height: 30px;
      line-height: 29px;
      border-bottom: 1px solid #d9d9d9;
      input{
        float: right;
        height: 29px;
        line-height: 29px;
        text-align: right;
        padding-right: 10px;
        width: 70%;
      }
    }

  }
  .product-details{
    width: 90%;
    max-height: 80vh;
    background: #fff;
    border-radius: 15px;
    position: absolute;
    left: 5%;
    top:50%;
    z-index: 20;
    transition: all .3s;

    overflow: hidden;
    &>h3{
      display: block;
      line-height: 40px ;
      font-size: 18px ;
      color: #416eb6;
      margin: 0;
      width: 100%;
      height: 40px;
      padding-left: 15px;
      display: flex;
      box-shadow: 0 5px 10px 0 rgba(84, 149, 255, 0.1);
      &>i{
        color: #282828 ;
        width: 40px;
        text-align: center;
        position: absolute;
        top: 0;
        right: 0;
      }
    }
    &>.scrollBox{
      width: 100%;
      -webkit-box-flex: 1;
      -webkit-flex: 1;
      -ms-flex: 1;
      flex: 1;
      max-height: 90vh;
      overflow: hidden;
      overflow-y: scroll;
      -webkit-overflow-scrolling: touch;
      &::-webkit-scrollbar{
        width: 0;
        height: 0;
      }
      &>ul{
        width: 100%;
        padding:0 15px;
        box-sizing: border-box;
        padding-bottom:100px ;
        &>li{
          width: 100%;
          height: 30px;
          border-bottom: 1px solid #D9D9D9;
          position: relative;
          &:last-of-type{
            border-bottom: none;
          }
          &>input{
            float: right;
            height: 29px;
            line-height: 29px;
            width: 70%;
            text-align: right;
            background: transparent;

          }
          &>span{
            line-height: 29px;
            font-size: 14px;
          }
        }
      }
    }


  }


  .order-item{
    width: 100%;
    box-sizing: border-box;
    padding: 15px 15px 0 15px;
    &>div{
      width: 100%;
      background: url("../../../../assets/img/home-beakground.png") no-repeat center center #fff;
      background-size: 100% 100%;
      padding:0px  10px 10px 10px;
      border-radius: 10px;
      &>p{
        font-size: 14px;
        color: #D9D9D9 ;
        margin: 0;
        line-height: 30px;
      }
      &>.order-item-title{
        width: 100%;
        height: 40px ;
        margin: 0;
        line-height: 50px;
        position: relative;
        font-size: 15px;
        p{
          margin: 0;
        }
        span{
          position: absolute;
          right: 10px;
          top: 15px;
          height: 20px;
          line-height: 20px;
          font-size: 14px;
          text-align: center;
          width: 50px;
          border: 1px solid #416eb6;
          color: #416eb6;
          border-radius: 5px;
        }
      }
    }
  }
</style>

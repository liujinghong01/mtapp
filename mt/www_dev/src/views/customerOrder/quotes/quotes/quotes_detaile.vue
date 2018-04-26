<template>
    <div class="mw-page page" v-nav="{hideNavbar:true}">
      <div class="mw-page-header">
        <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
        <span>报价单</span>
        <i class="right icon ion-navicon-round" @click="showMore"></i>
      </div>
      <div class="mw-page-content">
        <div class="card-box">
          <div class="card">
            <h3>基础信息</h3>
            <div class="basic-info">
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
                </li>
              </ul>
            </div>
          </div>
        </div>
        <div class="card-box">
          <div class="card">
            <h3>制品信息 <i class="ion-plus-circled" @click="golist"></i></h3>
            <div class="line-info">
              <div class="title">
                <span class="row-40">
                  产品名称
                </span>
                <span class="row-25">
                  单价
                </span>
                <span class="row-25">
                  数量
                </span>
              </div>
              <ul>
                <li @click="showDetaile">
                  <div class="row-40">工装车床</div>
                  <div class="row-25">3000</div>
                  <div class="row-25" @click="cancelBubble"><input value="15" type="number"></div>
                  <i class="ion-minus-circled" @click="deleteItem"></i>
                </li><li>
                <div class="row-40">工装车床</div>
                <div class="row-25">3000</div>
                <div class="row-25"><input value="15" type="number"></div>
                <i class="ion-minus-circled"></i>
              </li><li>
                <div class="row-40">工装车床</div>
                <div class="row-25">3000</div>
                <div class="row-25"><input value="15" type="number"></div>
                <i class="ion-minus-circled"></i>
              </li>

              </ul>

            </div>


          </div>

        </div>

        <div class="card-box">
          <div class="card">
            <h3>付款信息 <i class="ion-plus-circled"></i></h3>
            <div class="line-info">
              <div class="title">
                <span class="row-25">
                  批次
                </span>
                <span class="row-25">
                  金额
                </span>
                <span class="row-25">
                  比例
                </span>
              </div>
              <ul>
                <li>
                  <div class="row-25">第1次</div>
                  <div class="row-25">3000</div>
                  <div class="row-25">30%</div>
                  <i class="ion-minus-circled"></i>
                </li>
                <li>
                  <div class="row-25">第2次</div>
                  <div class="row-25">3000</div>
                  <div class="row-25">30%</div>
                  <i class="ion-minus-circled"></i>
                </li>
                <li>
                  <div class="row-25">第3次</div>
                  <div class="row-25">4000</div>
                  <div class="row-25">40%</div>
                  <i class="ion-minus-circled"></i>
                </li>

              </ul>

            </div>
          </div>
        </div>
        <div class="card-box">
          <div class="card">
            <h3>说明</h3>
            <div class="remarks">
              <textarea placeholder="请输入备注说明"></textarea>

            </div>
          </div>
        </div>

      </div>
      <div class="mw-page-footer" >
        <div class="btns"  style="color: #416eb6;">
          <div class="btn" @click="saveData('')">
            保存
          </div>
          <span>|</span>
          <div class="btn" @click="saveData('')">
            提交
          </div>
        </div>
      </div>

      <div v-show="maskShow" class="mask" @click="maskHide"></div>
      <div v-show="moreShow" class="more" v-bind:style="{transform:scale}">
        <ul>
          <li>我的报价单</li>
          <li @click="golist">历史报价单</li>
          <li>打印</li>
          <li>导入</li>
          <li>审批设置</li>
        </ul>
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
      name:'quotes_detaile',
      components:{
      },
      data(){
        return{
          maskShow:false,
          moreShow:false,
          productDetaileShow:false,
          productDetaileTop:'0',
          scale:'scale(0,0)'
        }
      },
      mounted() {

      },
      activated(){

      },
      computed:{
      },
      methods:{
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
        showMore(){
          this.maskShow=true
          this.moreShow=true
          setTimeout(()=>{
            this.scale='scale(1,1)'
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
            this.scale='scale(0,0)'
        },
        golist(){
          this.$router.forward({
            path:'quotes_list',
            query:{
            }
          })
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
.card-box{
  width: 100%;
  padding: 15px 15px 0 15px ;
  &:last-of-type{
    padding-bottom: 15px;
  }
  &>.card{

    background: #fff;
    margin: 0;
    border-radius: 10px;
    width: 100%;
    padding: 0 10px;
    &>h3{
      line-height: 40px ;
      font-size: 18px ;
      color: #416eb6;
      margin: 0;
      &>.ion-plus-circled{
        float: right;
        font-size: 22px;
      }

    }
    &>.basic-info{
      width: 100%;
      ul{
        width: 100%;
        li{
          width: 100%;
          height: 30px;
          border-bottom: 1px solid #D9D9D9;
          &:last-of-type{
            border-bottom: none;
          }
          &>input{
            float: right;
            height: 29px;
            line-height: 29px;
            width: 70%;
            text-align: right;
          }
          &>span{
            line-height: 29px;
            font-size: 14px;
          }
        }
      }
    }
    &>.line-info{
      width: 100%;
      &>.title{
        color: #5A5A5A;
        height: 20px;
        width: 100%;
        span{
          float: left;
        }
      }
      ul{
        width: 100%;
        li{
          width: 100%;
          height: 30px;
          border-bottom: 1px solid #D9D9D9;
          &:last-of-type{
            border-bottom: none;
          }
          &>div{
            float: left;
            line-height: 30px;
            font-size: 14px;
            input{
              width: 100%;
              border:none;
              height: 29px ;
              line-height: 29px;
              font-size: 14px;
              text-align: center;
              padding: 0;

            }
          }
          &>.ion-minus-circled{
            float: right;
            font-size: 20px;
            line-height: 29px;
            color: red;
          }
        }
      }
      .row-40{
        width: 40%;
      }
      .row-25{
        width: 25%;
        text-align: center;
      }
    }
    &>.remarks{
      width: 100%;
      padding-bottom: 15px;
      textarea{
        width: 100%;
        height: 80px;
        resize: none;
        font-size: 15px;
        line-height: 20px;
        border: 1px solid #d9d9d9;
      }

    }
  }
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
  .more{
    width: 100px;
    background: #fff;
    position: absolute;
    right: 15px;
    top: 45px;
    z-index: 20;
    border-radius: 10px;
    transition: all .3s ;
    ul{
      width: 100%;
      padding: 10px ;

      li{
        width: 100%;
        height: 30px;
        line-height: 30px;
        text-align: center;
        border-bottom: 1px solid #dbdbdb;
        font-size: 14px;
        &:last-of-type{
          border-bottom:none;
        }
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
</style>

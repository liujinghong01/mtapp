<template>
  <section>
    <div class="navbar top" style="position: fixed;visibility: visible">
      <div class="search" @click="$router.forward('/search')">
        <input type="search">
        <div class="inputMask"><i class="icon ion-ios-search-strong" style="margin-right: 10px"></i>搜索</div>
      </div>
    </div>
    <div class="page" v-nav="{hideNavbar:true}">
      <div class="page-content" style="z-index: 1" >

        <div id="banner" style="display: none" @scroll="scroll()" @touchstart="touchstart()" @touchend="touchend()" @touchmove="touchmove()"> <!--@touchend="touchend()"-->
          <div id="bannerBox" :style="{width:5*width+'px'}" >
            <div class="bannerItem"  :style="{width:width+'px'}">
              <div class="itemContent" :style="{transform:'scale('+position[0].x+','+position[0].y+')'}">
                1
              </div>
            </div>myApps
            <div class="bannerItem"  :style="{width:width+'px'}">
              <div class="itemContent" :style="{transform:'scale('+position[1].x+','+position[1].y+')'}">
                2
              </div>myApps
            </div>
            <div class="bannerItem"  :style="{width:width+'px'}">
              <div class="itemContent" :style="{transform:'scale('+position[2].x+','+position[2].y+')'}">
                3
              </div>
            </div><div class="bannerItem"  :style="{width:width+'px'}">
            <div class="itemContent" :style="{transform:'scale('+position[3].x+','+position[3].y+')'}">
              4
            </div>
          </div><div class="bannerItem"  :style="{width:width+'px'}">
            <div class="itemContent" :style="{transform:'scale('+position[4].x+','+position[4].y+')'}">
              5
            </div>
          </div>
          </div>
        </div>
        <div class="home-content">
          <div class="home-top">
            <div class="background"></div>
            <div class="tools-content">
              <!--<cells :items="entrances" :on-cell-click="onCellClick1" row="1" col="4"></cells>-->
              <div v-bind:style="{width:(1+apps.length)/4*100+'%'}">
                <div @click="onCellClick(index)" v-bind:style="{width:1/(1+apps.length)*100+'%'}" v-for="(item,index) in apps">
                  <div> <i class="icon iconfont" :class="item.icon"></i> </div>
                  <span>{{item.name}}</span>
                </div>
                <div @click="onCellClick()" v-bind:style="{width:1/(1+apps.length)*100+'%'}" >
                  <div> <i class="icon  ion-more" ></i> </div>
                  <span>更多</span>
                </div>
              </div>
            </div>
            <div class="card-box">
              <div class="card">
                <img src="../../assets/img/home/tongji.png"  style="width: 100%;height: 100%" alt="">

              </div>
              <!--<swiper ref="swiper"-->
                      <!--direction="horizontal"-->
                      <!--width="100%"-->
                      <!--height="100%"-->
                      <!--pager-color="#416eb6"-->
                      <!--pager-bg-color="#e5e4e3"-->
                      <!--hide-pager="false"-->
              <!--&gt;-->
                <!--<swiper-item>-->
                  <!--<div class="card">{{ apps.length }}</div>-->
                <!--</swiper-item>-->
                <!--<swiper-item>-->
                  <!--<div class="card">2</div>-->
                <!--</swiper-item>-->
                <!--<swiper-item>-->
                  <!--<div class="card">3</div>-->
                <!--</swiper-item>-->
              <!--</swiper>-->
            </div>
          </div>

          <div class="count">
            <div class="block-title">
              <p>设备稼动率（4月）</p>
            </div>
            <div class="count-swipe">
              <div v-bind:style="{width:UtilizationRate.length/3*100+'%'}">
                <div v-for="(item,index) in UtilizationRate" v-bind:style="{ width:1/UtilizationRate.length*100+'%' }">
                  <div>
                    <img v-if="item.rate==60" src="../../assets/img/home/60.png" alt="">
                    <img v-else-if="item.rate==80" src="../../assets/img/home/80.png" alt="">
                    <img v-else='item.rate==25' src="../../assets/img/home/25.png" alt="">
                    <p v-bind:style="{color :item.color}">{{ item.rate }}%</p>
                  </div>
                  <p v-bind:style="{color :item.color}">{{ item.name }}</p>
                </div>
              </div>
            </div>
          </div>
          <!--<div class="modules">-->
          <!--<cells :items="modules" :on-cell-click="onCellClick" row="2" col="4"></cells>-->
          <!--</div>-->
        </div>

        <div class="item-divider-ios" style="padding-top: 15px"></div>

        <div class="image-text">
          <div class="block-title">
            <p>公司动态</p>
          </div>
          <div class="image-text-list">
            <div class="news-list">
              <div class="news">
                <img src="../../../static/images/index/news/article_1.png" height="100%">
                <div class="news-content">
                  <div class="title">MES与APS有啥区别？智能工厂布局必知！</div>
                  <div class="detail">随着制造精益化、制造数字化的不断发展，形成了EMS...</div>
                </div>
              </div>
              <div class="news">
                <img src="../../../static/images/index/news/article_1.png" height="100%">
                <div class="news-content">
                  <div class="title">MES与APS有啥区别？智能工厂布局必知！</div>
                  <div class="detail">随着制造精益化、制造数字化的不断发展，形成了...</div>
                </div>
              </div>
              <div class="news">
                <img src="../../../static/images/index/news/article_1.png" height="100%">
                <div class="news-content">
                  <div class="title">MES与APS有啥区别？智能工厂布局必知！</div>
                  <div class="detail">随着制造精益化、制造数字化的不断发展，形成了...</div>
                </div>
              </div>
              <div class="news">
                <img src="../../../static/images/index/news/article_1.png" height="100%">
                <div class="news-content">
                  <p class="title">MES与APS有啥区别？智能工厂布局必知！</p>
                  <p class="detail">随着制造精益化、制造数字化的不断发展，形成了...</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>
<script>
  import { mapGetters } from 'vuex'
  import storage from '@/utils/storage'
  export default {
    name:'home',
    data(){
      return {
        keywords:'',
        times:'',
        endTimes:'',
        width:screen.width*0.84,

        option1:{
          series: [{
            name:'',
            type: 'pie',
            radius: ['0%', '80%'],
            label: {
              normal: {
                formatter: function(params) {
                  var res = params.seriesName;
                  res += params.name + ' : ' + params.value;
                  return res;
                }
              }
            },
            data: [
              {
                value: 11,
                name: '拖期',
                itemStyle:{color:'#f82327'}
              },
              {
                value: 11,
                name: '未开始',
                itemStyle:{color:'#fb8627'}
              },
              {
                value: 33,
                name: '进行中',
                itemStyle:{color: '#51d447'}
              },
            ]
          }]
        },
        entrances:[
          '<div class="tool"><i class="icon iconfont icon-saoyisao"></i><br><span>扫一扫</span></div>',
          '<div class="tool"><i class="icon iconfont icon-shenpi"></i><span class="badge tip"><p></p></span><br><span>审批</span></div>',
          '<div class="tool"><i class="icon iconfont icon-tongji"></i><br><span>统计</span></div>',
          '<div class="tool"><i class="icon iconfont icon-baogao"></i><span class="badge tip"><p></p></span><br><span>报告</span></div>',
        ],
        modules:[
          /*'<div class="tool"><i class="icon iconfont icon-hesuandan"></i><br><span>报价核算单</span></div>',
          '<div class="tool"><i class="icon iconfont icon-baojiadan"></i><br><span>报价单</span></div>',
          '<div class="tool"><i class="icon iconfont icon-kehudingdan"></i><br><span>客户定单</span></div>',
          '<div class="tool"><i class="icon iconfont icon-shengchanguanli"></i><br><span>生产</span></div>',
          '<div class="tool"><i class="icon iconfont icon-chejianzuoye"></i><br><span>工作日历</span></div>',
          '<div class="tool"><i class="icon iconfont icon-zhijian"></i><br><span>来料质检</span></div>',
          '<div class="tool"><i class="icon iconfont icon-kucunguanli"></i><br><span>库存数据</span></div>',
          '<div class="tool"><i class="icon iconfont icon-tianjia"></i><br><span>更多</span></div>',*/
        ],
        apps:[
          {name:'报价核算单','icon':'icon-hesuandan','path':'/customerOrder/compute'},
          {name:'报价单','icon':'icon-baojiadan','path':'/customerOrder/quote/list',},
          {name:'客户定单','icon':'icon-kehudingdan','path':'/customerOrder/order/list',},
          // {name:'生产管理','icon':'icon-shengchanguanli','path':'/production/schedulList',},
          // {name:'工作日历','icon':'icon-chejianzuoye','path':'production/shopCalendar',},
          // {name:'来料质检','icon':'icon-zhijian','path':'/qualityCheck/MaterialQC',},
          // {name:'库存数据','icon':'icon-kucunguanli','path':'storage/baseInfo',},
          // {name:'统计','icon':'icon-kucunguanli','path':'storage/baseInfo',}
        ],
        UtilizationRate:[
          {
            name:'CNC',
            rate:60,
            color:'#FE9732'
          },
          {
            name:'EDM',
            rate:80,
            color:'#5FD859'
          },
          {
            name:'铣床',
            rate:25,
            color:'#FE3B32'
          }
        ],
        position:[
          {x:1,y:1},
          {x:.9,y:.65},
          {x:.9,y:.65},
          {x:.9,y:.65},
          {x:.9,y:.65}

        ],
        startX:0,
        endX:0,
        endX:0,
        scrollLeft:0,
        currLeft:'',
        banner:'',

      }
    },
    mounted(){
      const myApps = JSON.parse(storage.get('myApps'))
      if(myApps&&myApps!=''){
        this.apps = myApps
        this.modules.length = 0;
      }
      this.apps.forEach(item=>{
        this.modules.push('<div class="tool"><i class="icon iconfont '+item.icon+'"></i><br><span>'+item.name+'</span></div>')
    })
      this.modules.push('<div class="tool"><i class="icon iconfont icon-tianjia"></i><br><span>更多</span></div>')
    },
    computed:mapGetters([
      'userinfo',
      'hideNavbar',
      'chosenCompany'
    ]),
    methods: {
      onCellClick(item){
        if (!this.chosenCompany) {
          $dialog.confirm({
            theme: 'ios',
            title: '提示',
            content: '您还未加入任何企业，是否创建属于自己的企业？',
            cancelText: '取消',
            okText: '确定'
          }).then(res=>{
            if (res) {
              this.$router.forward('/company/trial')
            }
          })
        }else {
          try {
            this.$router.forward(this.apps[item].path);
          }catch (e){
            this.$router.forward('/apps');
          }
        }

      },
      onCellClick1(item){
        const that = this;
        switch (item){
          case 0: that.scan();break;
          case 1: that.$router.forward('/approve');break;
          case 2: console.log('统计');break;
          case 3: console.log('报告');break;
        }
      },
      scan(){
        window.cordova.plugins.barcodeScanner.scan(function (res) {
          alert(JSON.stringify(res))
        },function (resp) {

        })
      },
      touchstart(){
        clearInterval(this.endTimes)
        this.banner=document.getElementById('banner')
        this.startX=event.changedTouches[0].clientX
        // let _this=this
        // this.times=setInterval(function(){
        //   _this.calculatingPosition()
        // },100)

      },
      touchmove(){

      },
      scroll(){
        this.calculatingPosition()
      },
      touchend(){
        this.endX=event.changedTouches[0].clientX
        let _this=this
        _this.currLeft=_this.banner.scrollLeft
        _this.endTimes=setInterval(function(){
          if(_this.banner.scrollLeft==_this.currLeft){
            clearInterval(_this.endTimes)
            let num=Math.floor((_this.currLeft+_this.width/2)/_this.width)*_this.width
            let process= (num-_this.scrollLeft)/8
            _this.endTimes=setInterval(function(){
              if(process<0&&_this.banner.scrollLeft<num){
                _this.banner.scrollLeft=num
                clearInterval(_this.endTimes)
                clearInterval(_this.times)
              }else if(process>0&&_this.banner.scrollLeft>num){
                _this.banner.scrollLeft=num
                clearInterval(_this.endTimes)
                clearInterval(_this.times)
              }else{
                _this.banner.scrollLeft=_this.banner.scrollLeft+process
              }
            },20)
          }else{
            _this.currLeft=_this.banner.scrollLeft
          }
        },20)
      },
      calculatingPosition(){
        this.scrollLeft=this.banner.scrollLeft
        let items=document.getElementById('bannerBox').children
        let screenWidth=screen.width
        let minLeft=-screenWidth*0.76
        let centerLeft=screenWidth*0.08
        let maxLeft=screenWidth*0.92
        for(let i=0;i<items.length;i++){
          let left=items[i].offsetLeft-this.scrollLeft
          if(left<minLeft){
            this.position[i].x=0.9
            this.position[i].y=0.64
          }else if(left>=minLeft&&left<centerLeft){
            this.position[i].x=1-((centerLeft-left)/this.width*0.1)
            this.position[i].y=1-((centerLeft-left)/this.width*0.36)

          }else if(left>=centerLeft&&left<=maxLeft){
            this.position[i].x=1-((left-centerLeft)/this.width*0.1)
            this.position[i].y=1-((left-centerLeft)/this.width*0.36)
          }else if(left>maxLeft){
            this.position[i].x=0.9
            this.position[i].y=0.64
          }

        }
      },

    },
  }
</script>
<style type="text/css" lang="scss">

  .block-title{
    width: 100%;
    height: 28px;
    position: relative;
    padding-left:10px ;
    &:before{
      display: block;
      content: '';
      position: absolute;
      left: 0;
      top: 8px;
      height: 12px ;
      width: 2px;
      font-size: 12px;
      background: #416eb6;

    }
    p{
      font-size: 12px;
      line-height: 28px;
      color: #416eb6;
      margin: 0;
      border-bottom: 1px solid #ddd ;

    }

  }

  .top{
    height: 60px;
    background-color: #416eb6;
    .search{
      width: 80%;
      margin: 0 auto;
      padding-top: 13px;
      position: relative;
      height: 100%;
      input{
        height: 30px;
        border-radius: 5px;
        width: 100%;
        opacity: .1;
      }
      .inputMask{
        position: absolute;
        top: 0;
        height: 30px;
        border-radius: 5px;
        width: 100%;
        text-align: center;
        color: white;
      }
    }
  }
  .home-content{
    min-height: 250px;
    background: #fff;

    .cells{
      margin: 0;
      .row{
        border: none;
        .hairline-left:before,.hairline-top:before{
          width: 0;
          height: 0;
        }
        .tool{
          .icon{
            height: 30px;
            font-size: 30px;
            line-height: 50px;
          }
          span{
            font-size: 12px;
          }
        }
      }
    }
    .home-top{
      width: 100%;
      height: 280px;
      position: relative;
      .background{
        width: 100%;
        height:150px;
        background: url("../../assets/img/download.png") no-repeat center center;
        background-size: 100% 100%;
        position: absolute;
        left: 0;
        top: 50px;
        z-index: 10;
      }

    }
    .tools-content{
      height: 80px;
      width: 100%;
      position: absolute;
      left: 0;
      top: 0;
      z-index: 100;
      background-color: #416eb6;
      overflow: hidden;
      overflow-x: scroll;
      -webkit-overflow-scrolling: touch;
      .cells{
        height: 100%;
        background-color: #416eb6;
        .row{
          height: 100px;
          color: white;
          .col{
            height: 100px;
          }
        }
      }
      &::-webkit-scrollbar{
        width: 0;height: 0;
      }
      &>div{
        min-width: 100%;
        height: 100%;
        &>div{
          float: left;
          height: 100%;
          color: #fff;
          text-align: center;
          div{
            width: 40px;
            height: 40px;
            line-height: 40px;
            margin:5px auto;
            background: #fff;
            border-radius: 50%;
            color: #416eb6;
            i{
              font-size: 22px;
            }
          }
          span{
            font-size: 12px;
          }
        }
      }
    }
    .card-box{
      width: 100%;
      height: 200px;

      position: absolute;
      top: 90px;
      left: 0;
      z-index: 100;
      margin: 0;
      padding: 0;
      .card{
        width: 288px;
        height:197px ;
        position: absolute;
        top: 0;
        left: 50%;
        margin: 0;
        padding: 0;
        margin-left: -144px;
        background: #fff;
        border-radius: 6px;
        box-shadow: 1px 2px 6px 2px rgba(54, 150, 255, 0.1);

      }
    }
    .count{
      width: 100%;
      height: 200px;
      margin: 0;
      margin-top: 20px;
      padding: 0;
      box-shadow: 0 -2px 4px 0 rgba(200, 200, 200, 0.5);


      &>.count-swipe{
        overflow: hidden;
        overflow-x: auto;
        width: 100%;
        height: 170px;
        margin: 0;
        padding: 0;
        background: url("../../assets/img/home-beakground.png") no-repeat center center ;
        background-size: 100% 100%;
        &::-webkit-scrollbar{
          width: 0;
          height: 0;
        }
        &>div{
          min-width: 100%;
          height: 170px;
          margin: 0;
          padding: 0;
          &>div{
            height: 170px;
            float: left;
            margin: 0;
            padding: 0;
            text-align: center;
            position: relative;
            &>div{
              img{
                width: 70px;
                height: 70px;
                margin-top: 40px;
              }
              &>p{
                position: absolute;
                width: 100%;
                height: 20px;
                line-height: 20px;
                left: 0;
                top: 66px;
              }
            }

          }


        }
      }
    }
    .modules{
      height: 150px;
      .row {
        height: 75px;
        color: #416eb6;
        .col {
          height: 75px;
          i{
            font-size: 24px!important;
          }
        }
      }
    }
  }
  .image-text{
    width: 100%;
    background: #fff;
    .news-list{
      width: 100%;
      margin-bottom: 50px;
      .news{
        height: 120px;
        padding:10px 15px;
        display: flex;
        position: relative;
        &:after{
          display: block;
          content: '';
          position: absolute;
          width: 100%;
          height: 1px;
          background: #ddd;
          bottom: 0;
          left: 15px;
        }
        img{
          height: 100%;
          float: left;
        }
        .news-content{
          height: 100%;
          height: 100px;
          margin-left: 15px;
          width: 60%;

          /*overflow: hidden;*/

          .title{
            width: 100%;
            height: 50px;
            line-height: 25px;
            font-weight: bold;
            overflow: hidden;
          }
          .detail{
            margin-top: 10px;
            height: 40px;
            font-size: 12px;
            line-height: 20px;
            opacity: .5;
            overflow: hidden;
          }
        }

      }
    }

  }
  #banner{
    width: 100%;height: 200px;
    overflow-x: scroll;
    &::-webkit-scrollbar{width: 0;height: 0}
    #bannerBox{
      height: 100%;
      box-sizing: content-box;
      padding:0 8%;
      font-size:0;
      :after{
        display: block;
        content: " ";
        clear: both;
      }
      .bannerItem{
        height: 100%;
        display: inline-block;
        .itemContent{
          width: 100%;
          height: 100%;
          background: #416eb6;
          font-size: 20px;
          transform: scale(1,1);
          transition: all .1s;
        }
      }
    }

  }

</style>

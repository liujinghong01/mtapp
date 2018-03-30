<template>
  <section>
  <div class="navbar top" style="position: fixed;visibility: visible">
      <div class="search" @click="$router.forward('/search')">
        <input type="search">
        <div class="inputMask"><i class="icon ion-ios-search-strong" style="margin-right: 10px"></i>搜索</div>
      </div>
  </div>
  <div class="page" v-nav="{hideNavbar:true}">
    <div class="page-content">
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
        <div class="tools-content">
          <cells :items="entrances" :on-cell-click="onCellClick1" row="1" col="4"></cells>
        </div>
        <div class="modules">
          <cells :items="modules" :on-cell-click="onCellClick" row="2" col="4"></cells>
        </div>
      </div>
      <div class="item-divider-ios" style="padding-top: 15px"></div>
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
            <div class="title">MES与APS有啥区别？智能工厂布局必知！</div>
            <div class="detail">随着制造精益化、制造数字化的不断发展，形成了...</div>
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
        entrances:[
          '<div class="tool"><i class="icon iconfont icon-saoyisao"></i><br><span>扫一扫</span></div>',
          '<div class="tool"><i class="icon iconfont icon-shenpi"></i><span class="badge tip"><p></p></span><br><span>审批</span></div>',
          '<div class="tool"><i class="icon iconfont icon-tongji"></i><br><span>统计</span></div>',
          '<div class="tool"><i class="icon iconfont icon-baogao"></i><span class="badge tip"><p></p></span><br><span>报告</span></div>',
        ],
        modules:[
          '<div class="tool"><i class="icon iconfont icon-hesuandan"></i><br><span>报价核算单</span></div>',
          '<div class="tool"><i class="icon iconfont icon-baojiadan"></i><br><span>报价单</span></div>',
          '<div class="tool"><i class="icon iconfont icon-kehudingdan"></i><br><span>客户定单</span></div>',
          '<div class="tool"><i class="icon iconfont icon-shengchanguanli"></i><br><span>生产</span></div>',
          '<div class="tool"><i class="icon iconfont icon-chejianzuoye"></i><br><span>工作日历</span></div>',
          '<div class="tool"><i class="icon iconfont icon-zhijian"></i><br><span>来料质检</span></div>',
          '<div class="tool"><i class="icon iconfont icon-kucunguanli"></i><br><span>库存数据</span></div>',
          '<div class="tool"><i class="icon iconfont icon-tianjia"></i><br><span>更多</span></div>',
        ],
        apps:[
          {name:'报价核算单','icon':'icon-hesuandan','path':'/customerOrder/compute'},
          {name:'报价单','icon':'icon-baojiadan','path':'/customerOrder/quote/list',},
          {name:'客户定单','icon':'icon-kehudingdan','path':'/customerOrder/order/list',},
          {name:'生产管理','icon':'icon-shengchanguanli','path':'/production/schedulList',},
          {name:'工作日历','icon':'icon-chejianzuoye','path':'production/shopCalendar',},
          {name:'来料质检','icon':'icon-zhijian','path':'/qualityCheck/MaterialQC',},
          {name:'库存数据','icon':'icon-kucunguanli','path':'storage/baseInfo',}
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
      console.log(myApps)
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
    .tools-content{
      height: 100px;
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
  .news-list{
    width: 100%;
    margin-bottom: 50px;
    .news{
      height: 120px;
      padding: 15px;
      display: flex;
      img{
        float: left;
      }
      .news-content{
        height: 100%;
        margin-left: 15px;
        width: 60%;
        .title{
          width: 100%;
          font-weight: bold;
        }
        .detail{
          margin-top: 10px;
          font-size: 12px;
          opacity: .5;
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

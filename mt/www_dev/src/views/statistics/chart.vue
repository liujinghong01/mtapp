<template>
    <div class="mw-page page" v-nav="{hideNavbar:true}">
      <div class="mw-page-header">
        <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
        <span>CNC</span>
        <i class="right">4月</i>
      </div>
        <div class="mw-page-content has-tab">
          <div class="chart">
            <!-- 图表展示 -->
            <div id="chart" >

            </div>
          </div>
          <!-- 高度=屏幕高度-图表高度-导航高度 -->
            <div class="list" :style="{height:listHeight+'px'}">
              <div class="list-date">
                <ul>
                  <li class="list-item-day">
                    <span class="icon icon-circle-outline"></span>
                    4月1日
                  </li>
                  <li class="list-item-inner">
                    <div class="list-item--title">
                      设备号
                    </div>
                    <div class="list-item--content">
                      CNC01
                    </div>
                      <div class="list-item--content">
                      cnc02
                    </div>
                  </li>
                  <li class="list-item-inner">
                    <div class="list-item--title">
                      开机时间(H)
                    </div>
                    <div class="list-item--content">
                      12
                    </div>
                  <div class="list-item--content">
                      13bb
                    </div>
                  </li>
                </ul>
              </div>
              <div class="list-date">
                <ul>
                  <li class="list-item-day">
                    <span class="icon icon-circle-outline"></span>
                    4月2日
                  </li>
                  <li class="list-item-inner">
                    <div class="list-item--title">
                      设备号
                    </div>
                    <div class="list-item--content">
                      CNC01
                    </div>
                      <div class="list-item--content">
                      cnc02
                    </div>
                  </li>
                  <li class="list-item-inner">
                    <div class="list-item--title">
                      开机时间(H)
                    </div>
                    <div class="list-item--content">
                      12
                    </div>
                  <div class="list-item--content">
                      13bb
                    </div>
                  </li>
                </ul>
              </div>
              <div class="list-date">
                <ul>
                  <li class="list-item-day">
                    <span class="icon icon-circle-outline"></span>
                    4月3日
                  </li>
                  <li class="list-item-inner">
                    <div class="list-item--title">
                      设备号
                    </div>
                    <div class="list-item--content">
                      CNC01
                    </div>
                      <div class="list-item--content">
                      cnc02
                    </div>
                  </li>
                  <li class="list-item-inner">
                    <div class="list-item--title">
                      开机时间(H)
                    </div>
                    <div class="list-item--content">
                      12
                    </div>
                  <div class="list-item--content">
                      13bb
                    </div>
                  </li>
                </ul>
              </div>
              <div class="list-date">
                <ul>
                  <li class="list-item-day">
                    <span class="icon icon-circle-outline"></span>
                    4月4日
                  </li>
                  <li class="list-item-inner">
                    <div class="list-item--title">
                      设备号
                    </div>
                    <div class="list-item--content">
                      CNC01
                    </div>
                      <div class="list-item--content">
                      cnc02
                    </div>
                  </li>
                  <li class="list-item-inner">
                    <div class="list-item--title">
                      开机时间(H)
                    </div>
                    <div class="list-item--content">
                      12
                    </div>
                  <div class="list-item--content">
                      13bb
                    </div>
                  </li>
                </ul>
              </div>

          </div>
        </div>
    </div>
</template>

<script>
  import storage from "@/utils/storage";
  export default {
    name: "statisticsChart",
    data() {
      return {
        listHeight:300,
      }
    },
    mounted(){
      this.drawLine('chart')
    },
    created(){
      console.log('created')
      this.listHeight = window.innerHeight-360;
    },
    activated(){
      console.log('activated')
    },
    methods: {
      drawLine(el,data){
        let option={
          xAxis: {
            type: 'category',
            boundaryGap: false,
            data: ['', '5', '10', '15', '20', '25', ''],
            itemStyle:{color:'#639EFF'},
            axisLine: {
              lineStyle: {
                color: '#fff'
              }
            },
          },
          yAxis: {
            type: 'value',
            axisLine: {
              lineStyle: {
                color: '#fff'
              }
            },
            axisTick: {
              inside: true
            },
            splitLine: {
              show: true
            },
            axisLabel: {
              inside: true,
              formatter: '{value}\n'
            },
          },
          series: [{
            data: [60, 80, 180, 100, 60, 80, 70],
            type: 'line',
            smooth: true,
            itemStyle:{color:'#639EFF',fontSize:'60'},
            areaStyle: {color:'#639EFF'}
          },
            {
              data: [[0, 80], [40, 80]],
              type: 'line'
            }
          ]
        }
        // 基于准备好的dom，初始化echarts实例
        let myChart = this.$echarts.init(document.getElementById(el))
        // 绘制图表
        myChart.setOption(option)
      }
    }

  };

</script>

<style lang="scss" scoped>
  .page {
    background: #fff;
  }

  .chart {
    height: 190px;
    width: 100%;
    /*background: url('../../assets/img/chart-demo.png') no-repeat center center;*/
    background-size: cover;
    position: relative;
    background: #416eb6;
    #chart{
      width: 124%;
      height: 140%;
      position: absolute;
      left: -12%;
      top:-25%;
    }
  }

  .list {
    padding-top: 20px;
    overflow-y: auto;
    -webkit-overflow-scrolling:touch;
    &::-webkit-scrollbar {
      width: 0;
      height: 0;
    }
  }

  .list-date {
    font-size: 16px;
    line-height: 32px;

    ul {
      display: flex;
      flex-wrap: wrap;
      li {
        text-align: center;
        flex: 1 0 auto;
      };

    }
    .list-item-day{
      margin-left: 24px;
      border-left: dashed 1px #416eb6;
      position: relative;
      text-align: left;
      font-size: 16px;
      padding-left: 20px;
    }
    // .list-item-day .ion-ios-circle-outline{
    //   position: absolute;
    //   left: -6px;
    //   top: 5px;
    //   color: #416eb6;
    //   background: #f5f5f5;
    //   line-height: 20px;
    // }

    .list-item--title{
      color:#1c1c1c;
      opacity: 0.5;
    }

    .list-item--content{
      font-size: 16px;
      line-height: 32px;
    }
  }
  .icon-circle-outline{
    position: absolute;
    left: -7px;
    top: 8px;
    color: #416eb6;
    background: #ffffff;

    display: inline-block;
    height: 14px;
    width: 14px;
    border: solid 1px #416eb6;
    border-radius: 50%;

  }
</style>

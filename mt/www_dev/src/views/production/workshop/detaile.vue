<template>
  <div id="work-body" >
    <div class="work-box" v-bind:style="{ width:width,height:height,left:'-'+position,top:position }" :class="!screen?'active':''">
      <div class="work-left">
        <div >
          <p @click="$router.go(-1)"><i class="left icon ion-ios-arrow-back"></i></p>
          <div class="floor" :class="floor_active?'active':''" @click="floor_active=!floor_active">
            <span>1楼</span><i class="ion-chevron-down"></i>
          </div>
          <div class="info">
            <div class="state">
              <p>正在加工 <span class="state-1"></span></p>
              <p>待加工<span class="state-2"></span></p>
              <p>故障维修<span class="state-3"></span></p>
            </div>
            <div class="floor-list" :class="floor_active?'active':''" @click="floor_active=!floor_active">

              <div class="list">
                <div >1楼</div>
                <div >2楼</div>
                <div >3楼</div>
              </div>

            </div>
          </div>
        </div>
      </div>
      <div class="work-rigth">
        <img  src="../../../assets/img/workshop/workshop.jpg" alt="">
        <div class="plane">
          <div v-bind:style="{top:item.top+'%',left:item.left+'%'}" v-for="(item,index) in equipment_list" @click="molded_show=true">
            <span :class="item.state"></span>
            <p>{{ item.name }}</p>
          </div>
        </div>
      </div>
      <div class="mask" v-if="molded_show">
        <div class="molded">
          <img  src="../../../assets/img/workshop/cnc01.png" alt="">
          <h3>设备名称：CNC01</h3>
          <p>开机时间：2108-04-13</p>
          <p>上机物料：WL-0123456789</p>
          <p>操作员：吴杰</p>
          <i class="ion-close-circled" @click="molded_show=false"></i>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import sess from '@/utils/sess'
  export default{
    data(){
      return{
        width:'100%',
        height:'100%',
        screen:false,
        position:0,
        floor_active:false,
        molded_show:false,
        equipment_list:[
          {
            name:'CNC01',
            state:'state-1',
            left :6,
            top:23
          },
          {
            name:'CNC02',
            state:'state-2',
            left :18,
            top:23
          },
          {
            name:'CNC03',
            state:'state-3',
            left :30,
            top:23
          },
          {
            name:'CNC04',
            state:'state-1',
            left :42,
            top:23
          },
          {
            name:'铣床01',
            state:'state-1',
            left :60,
            top:23
          },
          {
            name:'铣床02',
            state:'state-2',
            left :72,
            top:23
          },
          {
            name:'折弯机01',
            state:'state-1',
            left :6,
            top:50
          },
          {
            name:'折弯机02',
            state:'state-2',
            left :18,
            top:50
          },
          {
            name:'折弯机03',
            state:'state-3',
            left :30,
            top:50
          },
          {
            name:'折弯机04',
            state:'state-1',
            left :42,
            top:50
          },
          {
            name:'磨床01',
            state:'state-1',
            left :60,
            top:50
          },
          {
            name:'磨床02',
            state:'state-2',
            left :72,
            top:50
          },

        ]
      }
    },
    computed:{

    },
    watch:{

    },
    mounted(){
      this.layout()
    },
    methods:{
      layout(){
        let width=document.body.clientHeight
        let height=document.body.clientWidth
        if(width<height){
          this.width=height+'px'
          this.height=width+'px'
          this.screen=true
          this.position=0
        }else{
          this.width=width+'px'
          this.height=height+'px'
          this.screen=false
          this.position=(width-height)/2+'px'
        }
      }
    }

  }
</script>
<style lang="scss" scoped>
  .state-1{
    display: block;
    box-sizing: border-box;
    width:16px ;
    height: 16px;
    border-radius: 50%;
    border: 5px solid #EEFAEE;
    background: #5CD156;

  }
  .state-2{
    display: block;
    box-sizing: border-box;
    width:16px ;
    height: 16px;
    border-radius: 50%;
    border: 5px solid #FEF4EA;
    background: #FE9732;
  }
  .state-3{
    display: block;
    box-sizing: border-box;
    width:16px ;
    height: 16px;
    border-radius: 50%;
    border: 5px solid #FEEBEA;
    background: #FE3B32;
  }
  #work-body{
    width: 100%;
    height: 100vh;
    position: relative;
    .work-box{
      position: absolute;
      left: 0;
      top: 0;
      background: #fff;
      &.active{
        transform:rotate(90deg);
      }
      &>.work-left{
        width:24% ;
        height: 100%;
        float: left;
        box-sizing: border-box;
        padding: 15px;
        &>div{
          width: 100%;
          height: 100%;
          border-right: 1px dashed #ccc;
          &>p{
            width: 100%;
            height: 30px;
            line-height: 18px;
            margin:0;

            i{
              width: 18px;
              height: 18px;
              display: block;
              font-size: 24px;
            }

          }
          &>.floor{
            width: 100%;
            height: 30px;
            margin-top: 30px;
            line-height: 30px;
            position: relative;
            font-size: 17px;
            padding-left: 5px;
            i{
              width: 30px;
              height: 30px;
              line-height: 34px;
              position: absolute;right: 0;
              top: 0;
              font-size: 16px;
              transform:rotate(0deg);
              text-align: center;
              transition: all .3s;
            }
            &.active>i{
              transform:rotate(-180deg);
            }

          }
          &>.info{
            width: 100%;
            height: 200px;
            padding: 10px 5px;
            position: relative;
            overflow: hidden;
            .state{
              width: 100%;
              p{
                width: 100%;
                height: 40px;
                line-height: 40px;
                font-size: 15px;
                margin: 0;
                position: relative;
                span{
                  position: absolute;
                  right:0px;
                  top: 12px;


                }
              }
            }
            .floor-list{
              width: 100%;
              position: absolute;
              z-index: 10;
              top: 0;
              left: 0;
              padding-right:5px;
              transform:translate(0,-120%);
              transition: all .3s;
              &.active{
                transform:translate(0,0);
              }
              &>.list{
                width: 100%;
                background: #fff;
                box-shadow: 0 0 15px 0 rgba(84,149,255,0.10);
                border-radius: 20px;
                border: 1px solid #EDF4FF;
                div{
                  width: 88%;
                  margin: 0 auto;
                  height: 40px;
                  border-bottom: 1px solid #EDF4FF;
                  font-size: 15px;
                  text-align: center;
                  line-height: 40px;
                  &:last-of-type{
                    border-bottom: none;
                  }
                }
              }

            }
          }

        }
      }
      &>.work-rigth{
        width:76% ;
        height: 100%;
        float: left;
        position: relative;
        &>img{
          width: 100%;
          height: 100%;
        }
        .plane{
          width: 100%;
          height: 100%;
          position: absolute;
          left: 0;
          top: 0;
          &>div{
            width: 50px;
            height: 50px;
            background: #fff;
            position: absolute;
            left: 10%;
            top: 0;
            text-align: center;
            padding-top: 5px;
            span{
              margin: 0 auto;

            }
            p{
              font-size: 12px;
              line-height: 14px;
              padding-top: 3px;
              margin: 0;
              height: 28px;
              /*display: -webkit-box;*/
              -webkit-box-orient: vertical;
              -webkit-line-clamp: 2;
              overflow: hidden;
              text-align: center;
            }
          }
        }
      }
      &>.mask{
        width: 100%;
        height: 100%;
        background: rgba(0,0,0,.3);
        position: absolute;left: 0;
        top: 0;
        .molded{
          width: 200px;
          height: 250px;
          position: absolute;
          left: 50%;
          top: 50%;
          margin-left: -100px;
          margin-top: -125px;
          background: #fff;
          border-radius: 10px;
          padding-top: 10px;
          text-align: center;
          img{
            width: 130px;
            height: 110px;
          }
          h3{
            margin: 0;
            font-size: 16px;
            margin-bottom: 10px;
          }
          p{
            margin: 0;
            font-size: 12px;
            line-height: 16px;
            color: #5A5A5A;
          }
          i{
            font-size: 23px;
            color: #DDEAFF;
            line-height: 46px;
          }
        }
      }
    }

  }
</style>

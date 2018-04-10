<template>
  <div class="mw-page page moment" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left"></i>
      <span>厂圈</span>
      <i class="right ion-edit" style="font-size: 18px;"></i>
    </div>
    <div class="mw-page-content">
      <scroll style="bottom:50px;top:0;"
              :on-refresh="onRefresh"
              :on-infinite="onInfinite">
        <section class="moment-items" v-for="item in data.info">
          <div class="moment-items-item" >
            <div class="left">
              <img :src="item.avatar" width="100%">
            </div>
            <div class="right">
              <div class="nick-name">{{item.trigger}}
                <!-- -{{item.title}}-->
              </div>
              <div class="cont">{{item.content}}</div>
              <div class="link" v-if="item.type === 'link'">
                <!--<img src="../../../static/images/icon/icon-40.png" width="100%">-->
                <p>
                  <span>{{item.content}}</span>
                  <br>
                  <span class="actionDesc">{{item.content.actionDesc}}</span>
                </p>
              </div>
              <div class="images"  v-if="item.type === 'images'">
                <div v-if="">

                </div>
              </div>
              <div class="tag">
                <span class="time-tag">{{item.time}}</span>
                <span class="project-tag">项目-{{item.proj_name}}</span>
                <i class="icon ion-android-chat chat-tag" @click.stop="showAction(item)"></i>
                <div class="action-tag"  :class="{'active':item.showAction}">
                  <div class="action" @click.stop="zanAction(item)" v-if="!isZan(item)">
                    <i class="icon ion-ios-heart-outline"></i>
                    赞
                  </div>
                  <div class="action" @click.stop="cancelZan(item)" v-else>
                    <i class="icon ion-ios-heart-outline"></i>
                    取消
                  </div>
                  <div class="action-divider"> </div>
                  <div class="action" @click.stop="showComment(item)">
                    <i class="icon ion-ios-chatbubble-outline"></i>
                    评论
                  </div>
                </div>
              </div>
              <div class="actions">
                <div class="arrow-top"></div>
                <div class="zans" v-if="item.praise.length">
                  <i class="icon ion-ios-heart-outline" style="color: #5496ff"></i>
                  <span class="zans-people" v-for="o in item.praise" :key="o.id">{{o.from_name}}  </span>
                </div>
                <div class="comments">
                  <p v-for="o in item.comment"><span class="name">{{o.from_name}}：</span>{{o.text}}</p>
                </div>
              </div>
            </div>
          </div>
          <hr class="moment-comment">
        </section>
        <div slot="infinite" class="text-center">
          没有更多数据
        </div>
      </scroll>
    </div>
  </div>
</template>
<script>
  import { mapGetters } from 'vuex'
  export default {
    name:'notice',
    computed:mapGetters([
      'hideNavbar'
    ]),
    data(){
      return{
        moments:{},
        data: {
          "info": [{
            "id": 12,
            "trigger_uid": "1",
            "iid": "0",
            "content": "3213213",
            "text": "",
            "img": "",
            "source": "1",
            "type": "3",
            "seeing": "177",
            "updated_at": "2018-04-09 19:39:48",
            "proj_name": "133528752872872",
            "trigger": "Bridly Goodman",
            "avatar": "http:\/\/wx.qlogo.cn\/mmopen\/vi_32\/BqCPu9GVaq1YdaAsnEj3kbfeS3TYMY5vAiaJibz9TDJCuOLuib0JUtvyNWlicCZCOhKTcicNZZJqZEtDtsPnIuKEz8Q\/0",
            "praise": [],
            "comment": []
          }, {
            "id": 11,
            "trigger_uid": "1",
            "iid": "0",
            "content": "nice shoot",
            "text": "",
            "img": "",
            "source": "1",
            "type": "3",
            "seeing": "177",
            "updated_at": "2018-03-15 15:00:39",
            "proj_name": "133528752872872",
            "trigger": "Bridly Goodman",
            "avatar": "http:\/\/wx.qlogo.cn\/mmopen\/vi_32\/BqCPu9GVaq1YdaAsnEj3kbfeS3TYMY5vAiaJibz9TDJCuOLuib0JUtvyNWlicCZCOhKTcicNZZJqZEtDtsPnIuKEz8Q\/0",
            "praise": [],
            "comment": []
          }, {
            "id": 8,
            "trigger_uid": "1",
            "iid": "0",
            "content": "afdsafdsaf",
            "text": "",
            "img": "",
            "source": "1",
            "type": "3",
            "seeing": "177",
            "updated_at": "2018-03-15 09:42:02",
            "proj_name": "133528752872872",
            "trigger": "Bridly Goodman",
            "avatar": "http:\/\/wx.qlogo.cn\/mmopen\/vi_32\/BqCPu9GVaq1YdaAsnEj3kbfeS3TYMY5vAiaJibz9TDJCuOLuib0JUtvyNWlicCZCOhKTcicNZZJqZEtDtsPnIuKEz8Q\/0",
            "praise": [],
            "comment": [{
              "id": 8,
              "from_uid": "1",
              "from_name": "Bridly Goodman",
              "text": "你好"
            }, {
              "id": 9,
              "from_uid": "1",
              "from_name": "Bridly Goodman",
              "text": "2333333"
            }, {
              "id": 10,
              "from_uid": "1",
              "from_name": "Bridly Goodman",
              "text": "3434343333"
            }]
          }, {
            "id": 7,
            "trigger_uid": "1",
            "iid": "0",
            "content": "fdsafdsadsa",
            "text": "",
            "img": "",
            "source": "1",
            "type": "3",
            "seeing": "177",
            "updated_at": "2018-03-15 09:40:16",
            "proj_name": "133528752872872",
            "trigger": "Bridly Goodman",
            "avatar": "http:\/\/wx.qlogo.cn\/mmopen\/vi_32\/BqCPu9GVaq1YdaAsnEj3kbfeS3TYMY5vAiaJibz9TDJCuOLuib0JUtvyNWlicCZCOhKTcicNZZJqZEtDtsPnIuKEz8Q\/0",
            "praise": [],
            "comment": [{
              "id": 15,
              "from_uid": "1",
              "from_name": "Bridly Goodman",
              "text": "1212121"
            }]
          }, {
            "id": 6,
            "trigger_uid": "1",
            "iid": "0",
            "content": "fewfwefwq",
            "text": "",
            "img": "",
            "source": "1",
            "type": "3",
            "seeing": "177",
            "updated_at": "2018-03-15 09:38:40",
            "proj_name": "133528752872872",
            "trigger": "Bridly Goodman",
            "avatar": "http:\/\/wx.qlogo.cn\/mmopen\/vi_32\/BqCPu9GVaq1YdaAsnEj3kbfeS3TYMY5vAiaJibz9TDJCuOLuib0JUtvyNWlicCZCOhKTcicNZZJqZEtDtsPnIuKEz8Q\/0",
            "praise": [{
              "from_uid": "1",
              "from_name": "Bridly Goodman"
            }],
            "comment": [{
              "id": 14,
              "from_uid": "1",
              "from_name": "Bridly Goodman",
              "text": "nice to meet you too!"
            }]
          }, {
            "id": 5,
            "trigger_uid": "1",
            "iid": "0",
            "content": "111",
            "text": "",
            "img": "",
            "source": "1",
            "type": "3",
            "seeing": "177",
            "updated_at": "2018-03-15 09:31:13",
            "proj_name": "133528752872872",
            "trigger": "Bridly Goodman",
            "avatar": "http:\/\/wx.qlogo.cn\/mmopen\/vi_32\/BqCPu9GVaq1YdaAsnEj3kbfeS3TYMY5vAiaJibz9TDJCuOLuib0JUtvyNWlicCZCOhKTcicNZZJqZEtDtsPnIuKEz8Q\/0",
            "praise": [{
              "from_uid": "1",
              "from_name": "Bridly Goodman"
            }],
            "comment": []
          }, {
            "id": 10,
            "trigger_uid": "1",
            "iid": "0",
            "content": "有",
            "text": "",
            "img": "",
            "source": "1",
            "type": "3",
            "seeing": "177",
            "updated_at": "2018-03-15 05:01:28",
            "proj_name": "133528752872872",
            "trigger": "Bridly Goodman",
            "avatar": "http:\/\/wx.qlogo.cn\/mmopen\/vi_32\/BqCPu9GVaq1YdaAsnEj3kbfeS3TYMY5vAiaJibz9TDJCuOLuib0JUtvyNWlicCZCOhKTcicNZZJqZEtDtsPnIuKEz8Q\/0",
            "praise": [],
            "comment": []
          }, {
            "id": 9,
            "trigger_uid": "1",
            "iid": "0",
            "content": "项目圈开通测试",
            "text": "",
            "img": "",
            "source": "1",
            "type": "3",
            "seeing": "177",
            "updated_at": "2018-03-15 03:29:48",
            "proj_name": "133528752872872",
            "trigger": "Bridly Goodman",
            "avatar": "http:\/\/wx.qlogo.cn\/mmopen\/vi_32\/BqCPu9GVaq1YdaAsnEj3kbfeS3TYMY5vAiaJibz9TDJCuOLuib0JUtvyNWlicCZCOhKTcicNZZJqZEtDtsPnIuKEz8Q\/0",
            "praise": [],
            "comment": []
          }, {
            "id": 4,
            "trigger_uid": "1",
            "iid": "0",
            "content": "nice to meet you",
            "text": "",
            "img": "",
            "source": "1",
            "type": "3",
            "seeing": "177",
            "updated_at": "2018-03-14 16:51:21",
            "proj_name": "133528752872872",
            "trigger": "Bridly Goodman",
            "avatar": "http:\/\/wx.qlogo.cn\/mmopen\/vi_32\/BqCPu9GVaq1YdaAsnEj3kbfeS3TYMY5vAiaJibz9TDJCuOLuib0JUtvyNWlicCZCOhKTcicNZZJqZEtDtsPnIuKEz8Q\/0",
            "praise": [{
              "from_uid": "1",
              "from_name": "Bridly Goodman"
            }],
            "comment": [{
              "id": 4,
              "from_uid": "1",
              "from_name": "Bridly Goodman",
              "text": "nice"
            }, {
              "id": 5,
              "from_uid": "1",
              "from_name": "Bridly Goodman",
              "text": "good"
            }, {
              "id": 6,
              "from_uid": "1",
              "from_name": "Bridly Goodman",
              "text": "你好棒"
            }]
          }],
          "total": 1,
          "page": 1,
          "num": 10
        },
        showAction:false,
      }
    },
    mounted(){},
    methods:{
      onRefresh(done){
        let that = this
        setTimeout(()=>{
          done()
        },600)
      },
      onInfinite(){},
      zanAction(item){

      },
      isZan(item){

      },
      showAction(item){
        this.$set(item,'showAction',!item.showAction)
      },
    }

  }
</script>

<style scoped lang="scss">
  .moment{
    background: #fff;
    .showMask{
      overflow-y: hidden;
      .mask{
        position: absolute;
        z-index: 1;
        top: 0;
        left: 0;
        height: 100vh;
        width: 100%;
        background-color: rgba(0,0,0,.2);
      }
    }
    .moment-items{
      padding: 12px 12px 0 12px;
      .moment-comment{
        transform: scaleY(.5);
        margin-top: 20px;
      }
      .moment-items-item{
        display: flex;
        .left{
          flex: 0 0 32px;
        }
        .right{
          flex: 1;
          padding-left: 8px;
          .nick-name{
            line-height: 24px;
            color: #5496ff;
            font-weight: bold;
          }
          .cont{
            word-break: keep-all;
            word-wrap: break-word;
            color: #282828;
          }
          .link{
            height: 40px;
            width: 100%;
            background-color: #F3F6FB;
            margin-top: 6px;
            padding: 2px;
            img{
              float: left;
              width: 36px;
              height: 100%;
            }
            p{
              /*width: 80%;*/
              height: 100%;
              float: left;
              padding:4px;
              word-wrap: break-word;
              overflow: hidden;
              text-overflow: ellipsis;
              font-size: 11px;
              line-height: 16px;
              .actionDesc{
                color: green;
                font-size: 10px;
              }
            }
          }
          .tag{
            padding-top: 4px;
            position: relative;
            .time-tag{
              font-size: 8px;
              opacity: .5;
              line-height: 24px;
            }
            .project-tag{
              font-size: 8px;
              line-height: 24px;
              transform: scale(.9);
              color: #5496ff;
              padding-left: 8px;
            }
            .chat-tag {
              float: right;
              font-size: 16px;
              color: #5496ff;
              opacity: .5;
              line-height: 24px;
            }
            .action-tag{
              width: 0;
              height: 24px;
              background-color: #5496ff;
              border-radius: 4px;
              overflow-y: hidden;
              transition: width .1s;
              position: absolute;
              right: 16px;
              top: 3px;
              z-index: 100;
              &.active{
                width: 224px;
              }
              .action{
                width: 48%;
                float: left;
                height: 24px;
                line-height: 24px;
                text-align: center;
                color: #FFFFFF;
                font-size: 10px;
              }
            }
          }
          .actions{
            margin-top: .075rem;
            width: 100%;
            position: relative;
            .arrow-top{
              border: 4px solid rgba(255,255,255,0);
              border-bottom-color: #F3F6FB;
              width: 0;
              height: 0;
              position: absolute;
              top: -.175rem;
              left: 12px;
            }
            .zans{
              line-height: 20px;
              padding:0 8px;
              border-bottom: 1px solid rgb(255,255,255);
              background-color: #F3F6FB;
              .zans-people{
                color: #5496ff;
                font-size: 11px;
                &:after{
                  display: inline-block;
                  content: "、";
                }
                &:first-of-type:after{
                  display: none;
                }
              }
            }
            .comments{
              padding: 0 8px;
              width: 100%;
              height: auto;
              background-color: #F3F6FB;
              p{
                font-size: 10px;
                line-height: 16px;
                padding: 1px;
              }
              .name{
                color: #5496ff;
              }
            }
          }
        }
      }


    }
    .comment-input{
      background-color: #cccccc;
      position: fixed;
      bottom: 0;
      border: none;
      width: 100%;
      height: 44px;
      line-height: 44px;
      z-index: 100;
      padding: 0 12px;
      input{
        width: 82%;
        border: none;
      }
      a{
        margin-left: 5%;
        border-radius: 4px;
        width: 10%;
        padding: 4px;
        color: white;
        background-color: #5496ff;
        font-size: 10px;
      }
    }
  }
</style>

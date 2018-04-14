<template>
    <div class="mw-page page" v-nav="{hideNavbar:true}">
      <div class="mw-page-header">
        <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
        <span>订阅管理</span>
        <i v-if="action==='check'" slot="right" @click="action='edit'">编辑</i>
        <i v-if="action==='edit'" slot="right" @click="saveEdit()">完成</i>
        <!-- <button v-if="action==='check'"  class="button button-icon" slot="left" @click="action='edit'">编辑</button>
        <button v-if="action==='edit'" class="button button-icon" slot="right" @click="saveEdit()">完成</button> -->
      </div>
        <!-- <div class="mw-page-content has-tab"> -->
        <div class="mw-page-content" style="display: flex;flex-direction: column">
          <div id="scrollBox" style="flex: 1;overflow-y: auto;overflow-x: hidden">
            <div class="modules" v-for="(item,parentIndex) in appCategory" :key="item.name">
              <p>{{item.name}}</p>
              <ul>
                <li v-for="(app, index) in item.apps" @click.prevent="changeSubStatus(index,parentIndex)" :key='app.id' :class="{'edit':action==='edit', 'active': app.status}">
                <!-- <li  v-for="app in item.apps" @click.prevent="navigateTo(app)" :class="{'edit':action==='edit', 'active': isActive(item)}"> -->
                  <!-- <i :class="['icon','iconfont',app.icon]"></i> -->
                  <p>{{app.name}}</p>

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
    name: "notificationList",
    data() {
      return {
        action:'check',
        appCategory:[
          {
            name:'业务事件',
            apps:[
              {
                name: '项目启动12对的',
                status: true,
                id:13
              },
              {
                name: '项目启动2安抚方',
                status: false,
                id:46
              },
              {
                name: '项目启动3',
                id:17
              },
              {
                name: '项目启动4',
              },
              {
                name: '项目启动5'
              },
              {
                name: '项目启动6'
              }
            ],
          },{
            name: '财务事件',
            apps:[
              {
                name: '应付确认'
              },{
                name:'应收确认'
              }
            ]
          },{
            name:'行政事件',
            apps:[
              {
                name: '高管到离'
              },
              {
                name:'高管到离2'
              }
            ]
          },{
            name:'工具事件',
            apps:[
              {
                name: '报价确认'
              },
              {
                name:'8D确认'
              }
            ]
          },

        ]

      };
    },
    methods: {
      onMenuClick(){
        this.$router.forward('./')
      },
      saveEdit(){
        this.action = 'check'
        console.log('12333')
      },
      changeSubStatus(index,parentIndex){
        // this.appCategory[parentIndex][index].status=!this.appCategory[parentIndex][index].status
      }
    },

    created(){
      console.log('created')

    },
    activated(){
      console.log('activated')
    }
  };
</script>

<style lang="scss" scoped>
.modules{
  background-color: white;
  margin-top: 10px;
  padding: 10px;
  &>p{
    margin: 5px;
    color: #D9D9D9;
  }
  ul{
    display: flex;
    flex-wrap: wrap;
    li{
      text-align: center;
      flex: 0 0 33.33%;
      // height: 40px;
      // padding-top: 20px;
      padding: 6px;
      position: relative;
      border: 4px solid white;
      // opacity: .4;
      background-color: #F9F9F9;
      color: #282828;
      &.edit{
        background-color: #f3f7fb;
      }
      &.active{
        background-color: #416eb6;
        opacity: 1;
      }
      p{
        width: 100%;
        font-size: 12px;
        padding-top: 5px;
        // position: absolute;
        // top: 40px;
        text-align: center;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }

    }
  }
}
</style>

<template>
  <div class="mw-page page" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>事件通知</span>
      <i class="right icon ion-navicon-round" @click="onMenuClick"></i>
    </div>
    <div class="mw-page-content has-tab">
      <scroll
      :class="['page-content']"
      :on-infinite="onInfinite"
      :on-refresh="onRefresh"
      >
        <div v-for="(item, index) in items[0].list" :key="index">
          <item class="item-icon-right" @click.native="this.$router.forward('/production/paymentInfo');">
            <div :class="['item-cont',item.swipe?'active':'']" >
              <p>{{item.title}}</p>
              <p>{{item.date}}</p>
              <i class="icon ion-ios-arrow-right arrow"></i>
            </div>
          </item>
        </div>
        <div v-if="pageModels.curr_page >= pageModels.total_page" slot="infinite" class="text-center">
          没有更多数据
        </div>
      </scroll>
    </div>
  </div>
</template>
<script>
  import storage from "@/utils/storage";
  export default {
    name: "notificationList",
    data() {
      return {
        pageModels:{
          curr_page:0,
          page_size:10,
          total_page:0
          },
        items:[{
          list:[
            {
              id:1,
              title:'3月份应付供应商账款已经确认',
              date:'4月16日'
            },
            {
              id:2,
              title:'金联 投标项目中标',
              date:'4月14日'
            },
            {
              id:3,
              title:'海枫 投诉8D报告 客户满意',
              date:'4月13日'
            },
            {
              id:4,
              title:'亚林宇集团 项目成功签收 ',
              date:'4月12日'
            },
            {
              id:5,
              title:'超能集团 项目首期款已付',
              date:'4月11日'
            },
            {
              id:6,
              title:'海枫 投诉8D报告 客户满意2222',
              date:'4月10日'
            },
          ]},
        ]

      };
    },

    methods: {
      onMenuClick(){
        this.$router.forward('./subscribe')
      },
      onRefresh(){
        console.log('refresh')
      },
      onInfinite(done) {
        const that = this;
        if(that.pageModels.total_page<=that.pageModels.curr_page){
          done()
          return
        }
        setTimeout(function () {
          if(that.pageModels.total_page<=that.pageModels.curr_page){
            done()
          }else{
            that.setList();
            done()
          }
        },400)
      },
    },

    activated(){
      console.log('activated')
    },

    created(){
      console.log('created')
    },

  };
</script>

<style lang="scss" scoped>

</style>

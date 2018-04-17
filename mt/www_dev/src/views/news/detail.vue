<template>
  <div class="mw-page page" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>新闻咨询</span>
      <i class="right icon ion-share" @click="onShareBtnClick"></i>
    </div>
    <div class="mw-page-content">
      <div class="new-title-wrap">
        <div class="title">
          {{news_info.title}}
        </div>
        <div class="meta">
          <div>
            {{news_info.from}}
          </div>
          <div class="date">
            {{news_info.date}}
          </div>
        </div>
      </div>
      <div class="content rich-text" v-html="news_info.content">
      </div>
    </div>
    <popup-share v-show="PopupShareStatus" v-on:hideShare="PopupShareStatus=false">
    </popup-share>
  </div>
</template>
<script>
  import storage from "@/utils/storage";
  import popupShare from "@/components/popupShare.vue";
  export default {
    name: "notificationList",
    data() {
      return {
        PopupShareStatus:false,
        news_info:{
          title:'十三届全国人大一次会议在京闭幕 习近平发表重要讲话',
          from: '网易新闻',
          date: '2018.03.21',
          content:'习近平发表重要讲话会议表决通过关于政府工作报告的决议等通过监察法 习近平签署主席令予以公布栗战书主持并讲话李克强汪洋王沪宁赵乐际韩正王岐山等出席会议。<br><img src="http://placeimg.com/280/140/tech"><br>3月20日，第十三届全国人民代表大会第一次会议在北京人民大会堂闭幕。中共中央总书记、国家主席、中央军委主席习近平发表重要讲话。 新华社记者 姚大伟 摄'
        }
      };
    },

    methods: {
      // 点击分享按钮
      onShareBtnClick(){
        this.PopupShareStatus = true
      },
    },

    activated(){
      console.log('activated')
    },

    created(){
      console.log('created')
    },
    components:{
      popupShare
    }


  };
</script>

<style lang="scss" scoped>
.mw-page-content{
  padding: 15px;
}

.new-title-wrap{
  border-bottom: solid 1px #D8D8D8;
  .title{
    font-size: 22px;
    line-height: 1.3;
  }
  .meta{
    display: flex;
    justify-content: space-between;
    padding: 18px 0;
    .date{
      color:#ABABAB;
    }
  }
}

.content{
  padding-top: 20px;
}

.rich-text{
  font-size: 16px;
  line-height: 1.6;
  img{
    max-width: 100%;
  }
}
</style>

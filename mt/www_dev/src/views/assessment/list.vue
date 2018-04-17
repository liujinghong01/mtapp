<template>
  <div class="mw-page page" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>员工完工达成情况</span>
      <i class="right" @click="onSelectAll">全选</i>
    </div>
    <div class="mw-page-content">
      <tabs :tab-items="tabs" :tab-index="tabIndex" :on-tab-click="onTabClick"></tabs>
      <div class="list-wrap">
          <ul class="list">
            <li class="list-item list-title">
              <div>名次</div>
              <div>姓名</div>
              <div>积分</div>
              <div>完成工时<br>(h)</div>
              <div></div>
            </li>
          </ul>
          <!-- 顶部排名 -->
          <ul class="list list-top">
            <template v-for="(item,index) in itemsTop">
              <li class="list-item"  v-bind:key="item.id">
                <div class="rank"><span v-if="item.id!==1&&item.id!==2&&item.id!==3">{{item.id}}</span></div>
                <div>{{item.name}}</div>
                <div>{{item.points}}</div>
                <div>{{item.time}}</div>
                <div @click="select(itemsTop,index,'top')"><i :class="['icon', item.checked?'ion-android-checkbox-outline':'ion-android-checkbox-outline-blank']"></i></div>
              </li>
            </template>
          </ul>
          <div class="list list-more">
            点击查看更多人员
          </div>
          <!-- 尾部排名 -->
          <ul class="list list-bottom">
            <template v-for="(item,index) in itemsBottom">
              <li class="list-item"  v-bind:key="item.id">
                <div class="rank"><span v-if="item.id!==1&&item.id!==2&&item.id!==3">{{item.id}}</span></div>
                <div>{{item.name}}</div>
                <div>{{item.points}}</div>
                <div>{{item.time}}</div>
                <div @click="select(itemsBottom,index,'bottom')"><i :class="['icon', item.checked?'ion-android-checkbox-outline':'ion-android-checkbox-outline-blank']"></i></div>
              </li>
            </template>
          </ul>
      </div>
    </div>
    <div class="mw-page-footer">
      <button class="button button-calm button-block"><i class="icon iconfont">&#xe6ec;</i> 奖励</button>
      <button class="button button-light button-block" style="color:#11c1f3"><i class="icon iconfont">&#xe63d;</i> 警告</button>
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
        tabs: ['昨天','上周','上个月','其他月份'],
        tabIndex: 0,
        itemsTop:[
          {
            id:1,
            name:'张山',
            points: 20,
            time:30,
            checked:true
          },
          {
            id:2,
            name:'李四',
            points: 123,
            time: 20,
            checked:true
          },
          {
            id:3,
            name:'李四',
            points: 456,
            time: 19,
            checked:false
          },
        ],
        itemsBottom:[{
            id:10,
            name:'李四',
            points: 123,
            time: 19,
            checked:false
          },
          {
            id:11,
            name:'李四',
            points: 123,
            time: 19,
            checked:true
          },
          {
            id:12,
            name:'李四',
            points: 123,
            time: 19,
            checked:true
        }]
      };
    },

    methods: {
      // 选项切换
      select(item,index,type){
        let checked = item[index].checked
        // this.itemsTop[index].checked = checked === true ? false : true;
        switch (type) {
          case 'top':
              // let checked = this.itemsTop[index].checked
              this.itemsTop[index].checked = checked === true ? false : true
            break;
            case 'bottom':
              // let checked = this.itemsTop[index].checked
              this.itemsBottom[index].checked = checked === true ? false : true
              break;
          default:
            break;
        }
      },
      onSelectAll(){
        console.log('SelectAll')
      },
      onTabClick(index){
        console.log(index)
        this.tabIndex = index
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
.list-wrap{
  padding-bottom: 50px;
  padding-top: 60px;
  height: 100%;
}
.list{
  background: #fff;
}
.list-title{
  color: #5A5A5A;
}
.list-more{
  text-align: center;
  height: 40px;
  line-height: 40px;
  color: #5495FF ;
}

.list-item{
  display: flex;
  justify-content: space-around;
  text-align: center;
  align-items: center;
  min-height: 36px;
  margin:10px 10px 0;
  border-radius: 4px;
  font-size: 12px;
  div{
    // height: 20px;
    flex: 1 0 20%;
    position: relative;
  }

  .rank{
    height: 20px;
  }

  @at-root .list-top &:nth-child(1){
    background: #FF7980;
    .rank{
      height: 20px;
      background: url(../../assets/img/assessment/1.png) no-repeat center;
      background-size: contain;
    }
  }
  @at-root .list-top &:nth-child(2){
    background: #FFD370;
    .rank{
      background: url(../../assets/img/assessment/2.png) no-repeat center;
      background-size: contain;
    }
  }
  @at-root .list-top &:nth-child(3){
    background: #7CC2FE;
    .rank{
      background: url(../../assets/img/assessment/3.png) no-repeat center;
      background-size: contain;
    }
  }
}
.mw-page-footer{
  position: fixed;
  width: 100%;
  bottom: 0;
  display: flex;
  .button{
    flex: 1;
    margin: 0;
    align-items: center;
  }
}

</style>

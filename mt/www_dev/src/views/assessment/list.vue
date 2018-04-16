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
            <li class="list-item">
              <div>名次</div>
              <div>姓名</div>
              <div>积分</div>
              <div>完成工时<br>(h)</div>
              <div></div>
            </li>
          </ul>

          <ul class="list">
            <template v-for="(item,index) in items">
              <li class="list-item list-item-content"  v-bind:key="item.id">
                <div>{{item.id}}</div>
                <div>{{item.name}}</div>
                <div>{{item.points}}</div>
                <div>{{item.time}}</div>
                <div @click="select(items,index)"><i :class="['icon', item.checked?'ion-android-checkbox-outline':'ion-android-checkbox-outline-blank']"></i></div>
              </li>
            </template>
          </ul>
      </div>
    </div>
    <div class="mw-page-footer">
      <md-button class="button button-calm block">奖励</md-button>
      <md-button class="button button-light block">警告</md-button>
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
        items:[
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
          {
            id:4,
            name:'李四',
            points: 123,
            time: 19,
             checked:false
          },
          {
            id:5,
            name:'李四',
            points: 123,
            time: 19,
            checked:true
          },
          {
            id:6,
            name:'李四',
            points: 123,
            time: 19,
            checked:true
          },
        ]
      };
    },

    methods: {
      // 选项切换
      select(item,index){
        let checked = this.items[index].checked
        this.items[index].checked = checked === true ? false : true;
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

.list-item{
  display: flex;
  justify-content: space-around;
  text-align: center;
  align-items: center;
  min-height: 40px;
  margin:10px 10px 0;
  border-radius: 4px;
  div{
    height: 100%;
    flex: 1 0 20%;
  }
  &.list-item-content:nth-child(1){
    background: #FF7980;
  }
  &.list-item-content:nth-child(2){
    background: #FFD370;
  }
  &.list-item-content:nth-child(3){
    background: #7CC2FE;
  }
}
.mw-page-footer{
  position: fixed;
  width: 100%;
  bottom: 0;
  display: flex;
  .button{
    flex: 1
  }
}

</style>

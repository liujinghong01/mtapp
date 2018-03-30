<template>
  <div class="page has-navbar" v-nav="{hideNavbar:true}"
       v-tabbar="{'menus': menus, menuColor: '#999', activeMenuColor: '#416eb6', onMenuClick: menuClicked}" v-tabbar-menu-index="activeIndex">
  <router-view></router-view>
  </div>
</template>
<script>
  import { mapGetters } from 'vuex'
  import { platform } from '@/utils'
  export default {
    name:'index',
    computed:mapGetters([
      'activeIndex',
      'userinfo',
      'hideNavbar'
    ]),
    data() {
      return {
        showMenuButton:this.activeIndex===2?true:false,
        menus: [
          {
            iconOn: 'iconfont icon-shouyexuanzhong',
            iconOff: 'iconfont icon-shouye',
            text: '首页',
            path: '/home'
          },
          {
            iconOn: 'iconfont icon-xiaoxixuanzhong',
            iconOff: 'iconfont icon-xiaoxi',
            text: '通知',
            badge: '15',
            path: '/notice'
          },
          {
            iconOn: 'iconfont icon-renmaixuanzhong',
            iconOff: 'iconfont icon-renmai',
            text: '人脉',
            path: '/relationship',
          },
          {
            iconOn: 'iconfont icon-wodexuanzhong',
            iconOff: 'iconfont icon-wode',
            text: '我的',
            path: '/my'
          }
        ]
      }
    },

    methods: {
      menuClicked(menuIndex) {
        console.log(menuIndex);
        if(menuIndex===0){
          this.$store.dispatch('hideNavbar',true)
        }else{
          this.$store.dispatch('hideNavbar',false)
        }
        if(menuIndex===2){
          this.showMenuButton=true
        }else{
          this.showMenuButton=false
        }
        this.$store.dispatch('changIndex',menuIndex);
        $app.setTitle(this.menus[menuIndex].text)
      },
      handleBackButton(e){
        console.log(this.$route)

        if(this.$route.meta.home){
          navigator.app.exitApp();
        }
      }
    },

    mounted(){
      const that = this;
      if('Android'===platform()){
        document.addEventListener("backbutton",that.handleBackButton,false)
      }

    },
    beforeDestroy() {
      const that = this;
      $tabbar.$emit('hideTabbar')
      if('Android'===platform()){
        document.removeEventListener("backbutton",that.handleBackButton,false)
      }
    }
  }
</script>
<style lang="scss" scoped>
  .badge{

  }

</style>

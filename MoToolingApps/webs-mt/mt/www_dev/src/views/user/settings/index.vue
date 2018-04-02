<template>
    <div class="page has-navbar" v-nav="{title: $t('settings'), showBackButton: true}">
        <div class="page-content">
          <div class="item-divider-ios"></div>
          <item class="item-icon-right">
            {{$t('notify-setting')}}
            <i class="icon ion-ios-arrow-right" style="color: #DDD;"></i>
          </item>
          <item class="item-icon-right">
            {{$t('changelog')}}
            <i class="icon ion-ios-arrow-right" style="color: #DDD;"></i>
          </item>
          <item class="item-icon-right" >
            {{$t('share')}}
            <i class="icon ion-ios-arrow-right" style="color: #DDD;"></i>
          </item>
          <div class="item-divider-ios"></div>
          <item class="item-icon-right">
            {{$t('proxy')}}
            <i class="icon ion-ios-arrow-right" style="color: #DDD;"></i>
          </item>
          <item class="item-icon-right">
            {{$t('feedback')}}
            <i class="icon ion-ios-arrow-right" style="color: #DDD;"></i>
          </item>
          <item class="item-icon-right" @click.native="scan">
            {{$t('about-us')}}
            <i class="icon ion-ios-arrow-right" style="color: #DDD;"></i>
          </item>
          <item class="item-icon-right" @click.native="showAction">
            读取名片
            <i class="icon ion-ios-arrow-right" style="color: #DDD;"></i>
          </item>
          <div class="item-divider-ios"></div>
          <item style="text-align: center" @click.native="logout">
            {{$t('exit')}}
          </item>
        </div>
    </div>
</template>
<script>
  import sess from '@/utils/sess'
  import Scanner from '@/utils/Scanner'
  import cameraPlugin from '@/utils/Camera'


  export default{
    name:'settings',
    data(){
      return{

      }
    },
    methods:{
      logout(){
        const that = this;
        $dialog.confirm({
          // 设置为ios样式
          theme: 'ios',
          // 标题
          title: '确定要退出吗？',
          // 取消按钮文本
          cancelText: '取消',
          // 确定按钮文本
          okText: '退出'
        }).then((res) => {
          if(res){
            that.$store.dispatch('FedLogOut').then(()=>{
              sess.clear();
              that.$store.dispatch('changIndex',0);
              that.$store.dispatch('setCompany','')
              that.$router.forward('/')
            })
          }
        })

      },
      scan(){
        Scanner.scanbar(function (response) {
          console.log('success'+response.text)
        },function (error) {
          console.log('error'+error.text)
        })
      },
      showAction(){
        let that = this;
        let btns = new Object();
        btns['拍摄照片'] = ()=>{
          that.capture('take')
        }
        btns['从相册选取'] = ()=>{
          that.capture('pic')
        }
        $actionSheet.show({
          buttons: btns,
          title:'',
          cancelText:'取消'
        })
      },
      capture(type){
        cameraPlugin.getPicture(type,function () {

        },function () {

        })
      }
    }
  }
</script>

<style scoped>

</style>

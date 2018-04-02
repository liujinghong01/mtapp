<template>
  <div class="mw-page page crm" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>我的</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content">
          <div class="item-avatar" @click="showAction">
            <p style="height: 60px;line-height: 60px;float:left;">头像</p>
            <div class="avatar"><img :src="userinfo.avatar? userinfo.avatar : '../../../../static/images/logo.png'" width="100%"></div>
          </div>
          <div class="item item-divider">
            基本信息
          </div>
          <item class="item-icon-right" @click.native="changeType('username')">
            昵称
            <span class="item-note">{{userinfo.username }}</span>
            <i class="icon ion-ios-arrow-right" style="color: #DDD;"></i>
          </item>
          <item class="item-icon-right" @click.native="changeType('gender')">
            性别
            <span class="item-note">{{setGender}}</span>
            <i class="icon ion-ios-arrow-right" style="color: #DDD;"></i>
          </item>
          <!--<item class="item-icon-right" @click.native="$router.forward('/modifyUserinfo/zone/'+userinfo.country+userinfo.city)">-->
            <!--地区-->
            <!--<span class="item-note">{{userinfo.country}} {{ userinfo.city}}</span>-->
            <!--<i class="icon ion-ios-arrow-right" style="color: #DDD;"></i>-->
          <!--</item>-->
          <item  class="item-icon-right" @click.native="changeType('mobile')">
            手机号
            <span class="item-note">{{userinfo.mobile?userinfo.mobile:'未设置'}}</span>
            <i class="icon ion-ios-arrow-right" style="color: #DDD;"></i>
          </item>
          <item  class="item-icon-right" @click.native="changeType('email')">
            邮箱
            <span class="item-note">{{userinfo.email?userinfo.email:'未设置'}}</span>
            <i class="icon ion-ios-arrow-right" style="color: #DDD;"></i>
          </item>
          <!--<div class="item item-divider">-->
            <!--第三方账号-->
          <!--</div>-->
          <!--<item class="item-icon-right" @click.native="$router.forward('/modifyUserinfo/wechat')">-->
            <!--微信-->
            <!--<span class="item-note">小小的大雄</span>-->
            <!--<i class="icon ion-ios-arrow-right" style="color: #DDD;"></i>-->
          <!--</item>-->
          <!--<item class="item-icon-right" @click.native="$router.forward('/myCompany')">-->
            <!--QQ-->
            <!--<span class="item-note">{{$t('no-attach')}}</span>-->
            <!--<i class="icon ion-ios-arrow-right" style="color: #DDD;"></i>-->
          <!--</item>-->
        </div>
    <item style="text-align: center" @click.native="logout">
      {{$t('exit')}}
    </item>
    </div>
</template>
<script>
  import cameraPlugin from '@/utils/Camera'
  import { mapGetters } from 'vuex'
  import { profilePicture } from '@/api/login'
  export default{
      name:'userinfo',
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
                that.$router.go(-1)
                sess.clear();
                that.$store.dispatch('changIndex',0);
                that.$store.dispatch('setCompany','')

              })
            }
          })

        },
        changeType(type){
          let obj = {
            type: type,
            val :''
          }
          switch (type){
            case 'username' :{
              obj.val = this.userinfo.username || ''
              break
            }
            case 'gender' :{
              obj.val = this.userinfo.gender
              break
            }
            case 'mobile' :{
              obj.val = this.userinfo.mobile || ''
              break
            }
            case 'email' :{
              obj.val = this.userinfo.email || ''
              break
            }
          }
          this.$router.forward({path:'/modifyUserinfo',query:obj})
        },
        showAction(){
          var that = this;
          var btns = new Object();
          btns['本地上传'] = ()=>{
            cameraPlugin.getPicture('get',function (res) {
//              console.log(res)
              const reqData = {
                image: res   //拿到的数据 要加上image的前缀
              }
              profilePicture(reqData).then(res =>{
                this.userinfo.avatar = res.avatar_url
              }).catch(err => {
                $toast.show(err.description)
              })
            },function (err) {
              $toast.show(err)
            })
          }
          btns['拍照'] = ()=>{
            cameraPlugin.getPicture('take',function (res) {
              const reqData = {
                image:"data:image/jpeg;base64," + res
              }
              profilePicture(reqData).then(res =>{
                this.userinfo.avatar = res.avatar_url
              }).catch(err => {
                $toast.show(err.description)
              })
            },function (err) {
              $toast.show(err)
            })
          }
          $actionSheet.show({
            buttons: btns,
            title:'修改头像',
            cancelText:that.$t('cancel')
          })
        }
      },
      computed:{
        ...mapGetters(['userinfo']),
        setGender(){
          let gender = ''
          switch (this.userinfo.gender){
            case 0: gender ='未知';break;
            case 1: gender ='男';break;
            case 2: gender = '女';break;
            default: gender = '未知';break;
          }
          return gender;
        }
      }
    }
</script>
<style lang="scss" scoped>
  .item-avatar{
    width: 100%;height: 80px;padding: 10px 16px
  }
  .avatar{
    width: 60px;height: 60px;border-radius: 60px;float:right;overflow: hidden;
  }
</style>

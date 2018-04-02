<template>
  <div class="page has-navbar" v-nav="{title: '选择国家或者地区', showBackButton: true}">
    <div class="page-content">

      <list class="list-ios">
        <item v-for="item in zoneCode" key="item.code" @click.native="chooseCode(item)">
          {{local==='zh'?item.zh:item.en}}
          <span class="item-note">
            {{item.code}}
          </span>
        </item>
      </list>
    </div>
  </div>
</template>

<script>
  import axios from 'axios'
  import sess from '@/utils/sess'

  export default{
    name: 'zoneCode',
    data(){
      return {
        zoneCode:[],
        local:'zh'
      }
    },
    created(){
      const that = this;
      if(navigator){
        switch (navigator.language){
          case 'zh-CN':
            that.local = 'zh',
            axios.get('static/zone_code-zh.json').then(function (resopnse) {
              that.zoneCode = resopnse.data
            }).catch(function (error) {
              console.log(error)
            })
            break;
          case 'en-US':
            that.local = 'en',
            axios.get('static/zone_code.json').then(function (resopnse) {
              that.zoneCode = resopnse.data
            }).catch(function (error) {
              console.log(error)
            })
            break;
          default:
            axios.get('static/zone_code-zh.json').then(function (resopnse) {
              that.zoneCode = resopnse.data
            }).catch(function (error) {
              console.log(error)
            })
            break;
        }
      }

    },
    methods: {
      chooseCode(item){
        let root = document.querySelector('[von-app]')
        if (root) root.setAttribute('transition-direction', 'back');
        sess.set('zoneCode',JSON.stringify(item))
        history.go(-1)
      }
    },
    destroyed(){
      this.zoneCode.length = 0;
      console.log('destroyed')
    }
  }
</script>

<style type='stylesheet/scss' lang='scss' scoped>

</style>

<template>
    <div class="mw-page page storage" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>仓库基础信息</span>
            <i class="right"></i>
        </div>
        <div class="mw-page-content">
          <cst-item label="物料基础数据" :hasArrow="true" @click.native="$router.forward('/storage/BasicDataList')"></cst-item>
          <cst-item label="物料类型" :hasArrow="true" @click.native="$router.forward('/storage/matTypeList')"></cst-item>
          <cst-item label="库存信息" :hasArrow="true" @click.native="$router.forward('/storage/storageList')"></cst-item>

        </div>
    </div>
</template>

<script>
  import CstItem from "../../../components/cstItem";
  export default{
      name:'storageBaseInfo',
    components: {CstItem},
    data(){
        return {}
    },
    methods: {
    }
  }
</script>

<style lang="scss" scoped>

</style>

<template>
    <div class="mw-page page storage" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>物料类型</span>
          <i class="right" @click="edit" v-show="action === 'check'">编辑</i>
          <i class="right" @click="action = 'check'" v-show="action === 'edit'">取消</i>
          <i class="right" v-show="action === 'new'"></i>
        </div>
        <div class="mw-page-content">
          <div>
            <cst-item label="类型名称" v-model="matTypeDetail.mat_type_name" :action="action"></cst-item>
            <cst-item label="物料编码前缀" v-model="matTypeDetail.mat_no_prefix" :action="action"></cst-item>
            <cst-item label="默认单位" v-model="matTypeDetail.unit" :action="action"></cst-item>
            <cst-item label="采购方式" v-model="setPurchaseType" :action="action" v-if="action ==='check'"></cst-item>
            <item v-else>
              采购方式
              <label><input name="procure_style" type="radio" value="1" v-model="matTypeDetail.procure_style"/> 直接采购</label>
              <label><input name="procure_style" type="radio" value="2" v-model="matTypeDetail.procure_style"/> 下料</label>
              <label><input name="procure_style" type="radio" value="3" v-model="matTypeDetail.procure_style"/> 库存供应</label>
            </item>
          </div>
        </div>
        <div class="mw-page-footer" v-show="action !== 'check'">
            <div class="btns">
                <div class="btn" @click="saveData">
                    保存
                </div>
            </div>
        </div>
    </div>
</template>

<script>
  import { storageMats,storageMatModify } from '@/api/storage/baseInfo'
  import SwipeItem from "../../../components/swipeItem";
  import CstItem from "../../../components/cstItem";
    export default{
        name:'matTypeDetail',
      components: {
        CstItem,
        SwipeItem},
      data(){
        return {
          action:'check',
          matTypeDetail:{
            mat_type_name:'',
            mat_no_prefix:'',
            unit:'',
            procure_style:''
          }
        }
      },
      created(){
        this.action = this.$route.params.action
        if(this.action !== 'new') {
          this.matTypeDetail = JSON.parse(this.$route.params.matTypeDetail)
        }
      },
      computed:{
        setPurchaseType(){
          let type = ''
          if (this.matTypeDetail.procure_style ==1) {
            type = '直接采购'
          }else if (this.matTypeDetail.procure_style == 2) {
            type = '下料'
          }else if (this.matTypeDetail.procure_style == 3) {
            type = '库存供应'
          }
          return type
        }
      },
      methods: {
          //保存数据
        saveData(){
          if (!this.matTypeDetail.mat_type_name) {
            $toast.show('类型名称不能为空')
            return
          }
          if (!this.matTypeDetail.mat_no_prefix) {
            $toast.show('编码前缀不能为空')
            return
          }
          if (!this.matTypeDetail.unit) {
            $toast.show('默认单位不能为空')
            return
          }
          if (!this.matTypeDetail.procure_style) {
            $toast.show('采购方式不能为空')
            return
          }
          const reqData = {
            mat_type_info:this.matTypeDetail
          }

          storageMatModify(reqData).then(res=>{
            console.log(res)
            $toast.show('保存成功')
            this.action = 'check'
          }).catch(error=>{
            $toast.show(error.description)
          })
        },
        edit() {
          if (this.matTypeDetail.is_default === '1') {
            $toast.show('缺省物料类型无法编辑')
            return
          }
          this.action = 'edit'
        }
      }
    }
</script>

<style lang="scss" scoped>

</style>

<template>
    <div class="mw-page page" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>盘点单详情</span>
            <i class="right"></i>
        </div>
        <div class="mw-page-content">
          <cst-item label="库存地点" :hasArrow="true" v-model="check_info.store_house_name" @click.native="chooseDic('storehouse')"></cst-item>
          <cst-item label="经办人" :hasArrow="true" v-model="check_info.operator_name" @click.native="chooseDic('depMember')"></cst-item>
          <item class="matTypes">
              <div style="width: 20%">物料类型</div>
            <br>
              <div style="width: 100%;height:auto;overflow-wrap: normal;">
                <p  style="display: inline;padding-right: 10px"><label><input type="checkbox" v-model="checkAll"/>全部类型</label></p>
                <p  v-for="(item,index) in curr_store_mat_types" style="display: inline;padding-right: 10px">
                  <label>
                    <input type="checkbox" name="mat_type" :value="item" v-model="check_info.mat_type_list"/>
                    {{item.mat_type_name}}
                  </label>
                  <br v-show="(index+1)%3===0">
                </p>
              </div>
          </item>
        </div>
      <sidebar-check :showFilter="showSide" :dicType="dicType" :chosen="chosenStore" v-on:hideMask="showSide=false"></sidebar-check>
        <div class="mw-page-footer">
          <div class="btns">
            <div class="btn" @click="saveData">
                保存
            </div>
          </div>
        </div>
    </div>
</template>

<script>
  import {storageList,storageMats} from '@/api/storage/baseInfo'
  import { inventoryAdd } from '@/api/storage/inventory'
  import CstItem from "../../../components/cstItem"
  import SidebarCheck from "../../../components/sidebarCheck";
    export default{
        name:'addInventory',
      components: {
        SidebarCheck,
        CstItem},
      data(){
        return {
          check_info:{
            store_house_name:'请选择',
            store_house_id:'',
            operator_id:'',
            operator_name:'请选择',
            mat_type_list:[], //已选择的物料类型
          },
          curr_store_mat_types:[], //所选库存地点的物料类型
          storehouses:[], //所有库存地点列表
          showSide:false,  //是否显示侧边栏
          dicType:'',
          checkAll:false
        }
      },
      mounted(){
        this.getMatTypes()
      },
      methods: {
        onMenuClick(){

        },
        chosenStore(item){
          console.log(item)
          switch (this.dicType) {
            case 'storehouse': {
              this.check_info.store_house_id = item.store_house_id
              this.check_info.store_house_name = item.store_house_name
            }
            case 'depMember': {
              this.check_info.operator_name = item.work_name
              this.check_info.operator_id = item.member_id
            }
            default:{

            }
          }
        },
        chooseDic(type){
          this.dicType = type
          this.showSide = true
        },
        getMatTypes(){    //根据仓库地点获取物料类型

          storageMats({
            is_paging:0
          }).then(res=>{
            this.curr_store_mat_types = res.mat_type_list
          }).catch(error=>{
          })
        },
        //保存信息
        saveData(){
          const reqData = {
            check_info:this.check_info
          }
          inventoryAdd(reqData).then(res=>{
            $toast.show('保存成功')
            history.go(-1)
            console.log(res)
          }).catch(error=>{
            $toast.show(error.description)
          })
        }
      },
      watch:{
        'checkAll':function (newVal) {

          if(newVal){
            this.check_info.mat_type_list = this.curr_store_mat_types
          }else{
            this.check_info.mat_type_list = []
          }
        }
      }
    }
</script>

<style lang="scss" scoped>
 .matTypes{
   padding-left: 15px;
 }
</style>

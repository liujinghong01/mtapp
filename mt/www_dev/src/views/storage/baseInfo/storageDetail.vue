<template>
    <div class="mw-page page storage" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>仓库详情</span>
            <i class="right" @click="action = 'edit'" v-show="action === 'check'">编辑</i>
            <i class="right" @click="cancel" v-show="action === 'edit'">取消</i>
            <i class="right" v-show="action === 'new'"></i>
        </div>
        <div class="mw-page-content">
          <div>
            <cst-item label="名称" v-model="store_house_info.store_house_name" :action="action"></cst-item>
            <!--<cst-item label="编码" v-model="store_house_info.store_house_no" :action="action" v-show="action !=='new'" :canEdit="false"></cst-item>-->
            <cst-item label="地址" v-model="store_house_info.store_house_addr" :action="action"></cst-item>
            <cst-item label="负责人" v-model="store_house_info.manager_name" action="check" @click.native="selectManager()" :hasArrow="action!=='check'"></cst-item>
            <cst-item label="备注" v-model="store_house_info.remark" :action="action"></cst-item>
            <div v-for="(item,index) in store_house_info.store_room_list">
              <div class="divider"><span class="left">库位{{index+1}}</span><span class="right" @click="roomDel(item)" v-show="action!=='check'">删除</span></div>
              <cst-item label="库位名称" v-model="item.store_room_name" :action="action"></cst-item>
              <cst-item label="库位描述" v-model="item.store_room_desc" :action="action"></cst-item>
            </div>
            <button v-show="action!='check'" @click="addRoom" class="button button-calm button-block button-outline">添加库位信息</button>
          </div>
        </div>
        <div class="mw-page-footer" v-show="action !== 'check'">
            <div class="btns">
                <div class="btn" @click="saveData">
                    保存
                </div>
            </div>
        </div>
      <time-picker
        v-on:chosen="chosen"
        :showPicker="showPicker"
        :pickerType="pickerType"
        :pickerValue="pickerValue"
        :pickerList='pickerList'
        v-on:hideMask="showPicker=false"></time-picker>
    </div>
</template>

<script>
  import { storageDetail,storageNew,storageRoomDel,Allname } from '@/api/storage/baseInfo'
  import CstItem from "../../../components/cstItem";
  import timePicker from "@/components/timePicker"
    export default{
      name:'storageDetail',
      components: { CstItem,timePicker },
      data(){
        return {
          selectType:'',
          showPicker:false,
          pickerType:'radio',
          pickerValue:'',
          pickerList:[],
          store_house_info:{
            "store_room_list": [

            ],
            "manager_name": '请选择',
            "store_house_name": "",
            "manager_id": '',
            "store_house_no": "",
            "remark":'' ,
            "store_house_addr": "",
          },
          list:[
            {val:'向辉',id:1},
            {val:'吴杰',id:2},
            {val:'玲珑',id:3},
            {val:'镜宏',id:4},
          ],
          store_house_id:'',
          action:'check'
        }
      },
      mounted(){

      },
      created(){
        this.action = this.$route.params.action
        if (this.action !== 'new') {
          this.store_house_id = this.$route.params.store_house_id
          this.getData()
        }else{
          this.Allname()
        }

      },

      methods: {
        cancel(){
          this.action='check'
          this.getData()
        },
        selectManager(){
          if(this.action==='check'){
            return
          }
          if(this.list.length>0){
            this.pickerValue={
              val:this.store_house_info.manager_name,
              id:this.store_house_info.manager_id,
            }
            this.pickerList=[]
            this.list.forEach((e,i)=>{
              this.pickerList.push({
                val:e.val,
                id:e.id
              })
            })
            this.showPicker=true
          }
        },
        chosen(e){
         this.store_house_info.manager_name=e.val
         this.store_house_info.manager_id=e.id
        },
        //Allname 获取部门人员
        Allname(){
          const reqData = {
            query:{
              dep_code:'Storehouse_Dept',
              dep_id:''
            }

          }
          Allname(reqData).then(res=>{
            if(res.member_list.length>0){
              this.list.length=0
              res.member_list.forEach((e,i)=>{
                this.list.push({
                  val:e.work_name,
                  id:e.member_id
                })
              })
            }
          }).catch(error=>{
            $toast.show(error.description)
          })
        },
        //获取详情信息
        getData(){
          const reqData = {
            query:{
              store_house_id:this.store_house_id
            }
          }
          storageDetail(reqData).then(res=>{
            console.log(res)
            this.store_house_info = res.store_house_info
          }).catch(error=>{
            $toast.show(error.description)
          })
        },
        //删除库位
        roomDel(item){
          $dialog.confirm({
            theme:'ios',
            title:'提示',
            content:'确定要删除吗?',
            okText:'确定',
            cancelText:'取消'
          }).then(ok=>{
            if(ok){
              storageRoomDel({
                store_room_id:item.store_room_id
              }).then(res=>{
                this.store_house_info.store_room_list = this.store_house_info.store_room_list.filter(o=>{
                  return o.store_room_id != item.store_room_id
                })
              }).catch(error=>{
                $toast.show(error.description)
              })
            }
          }).catch(error=>{
            $toast.show(error.description)
          })
        },
        //增加库位信息
        addRoom(){
          this.store_house_info.store_room_list.push({
            "store_room_name": "",
            "store_room_desc": ""
          })
        },
        //保存信息
        saveData(){
          if (!this.store_house_info.store_house_name) {
            $toast.show('仓库名称不能为空')
            return
          }
          if (!this.store_house_info.store_house_addr) {
            $toast.show('仓库地址不能为空')
            return
          }
          if (!this.store_house_info.manager_name) {
            $toast.show('负责人不能为空')
            return
          }
          const reqData = {
            store_house_info:this.store_house_info
          }
          storageNew(reqData).then(res=>{
             console.log(res)
            $toast.show('保存成功')
            history.back()
            this.action = 'check'
          }).catch(error=>{
            $toast.show(error.description)
          })
        }

      }
    }
</script>

<style lang="scss" scoped>

</style>

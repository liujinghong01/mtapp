<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>工艺模板类型列表</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content ">
      <div class="content" >
        <scroll class="page-content"  style="-webkit-overflow-scrolling: touch" >
            <swipe-item @click.native="ascertain(item)" style="margin-left: 0px;margin-right: 0px;" v-for="(item,index) in temp_list" class="req-item"   :obj="item" :canSwipe="true" swipeItemText="删除" v-on:SwipeItemClick="tmplTypeDelete(item,index)">
              <div >
                <p>{{item.tmpl_type_name}}</p>
              </div>
            </swipe-item>
          <div  slot="infinite" class="text-center"></div>
        </scroll>
      </div>
    </div>
    <div class="mw-page-footer">
      <div class="btns">
        <div class="btn" @click="addDepFlag=true">
          添加类型
        </div>
      </div>
    </div>
    <mw-prompt :show="addDepFlag" :okFunction="addNewDep" v-model="typeName" placeholder="请输工艺模板类型名称" v-on:hidePop="addDepFlag=false"></mw-prompt>
  </div>
</template>

<script>
  import { getDeptInfo } from '@/api/company'
  import { tmplTypeList,tmplTypeModify,tmplTypeDelete } from '@/api/production/procTemplate'
  import SwipeItem from "../../../components/swipeItem"
  import sess from '@/utils/sess'
  import MwPrompt from "@/components/mwPrompt";
  export default{
    name:'departmentList',
    components: {SwipeItem,MwPrompt},
    data(){
      return {
        addDepFlag:false,
        typeName:'',
        dep_id:'',
        parent_id:'',
        list:[
          // {name:'黄杰',id:1,check:true},
        ],
        temp_list:[]
      }
    },
    computed:{},
    activated(){
      if(sess.get('pg_list')!=null){
        this.list=JSON.parse(sess.get('pg_list'))
        sess.remove('pg_list')
      }
    },
    mounted(){
      const param = this.$route.query;
      this.dep_id = param.dep_id
      this.parent_id=param.parent_id
      this.tmplTypeList()
    },
    methods: {
      addNewDep(){
        if(this.type==''){
          $toast.show('请输入内容')
          return
        }else{
          this.tmplTypeModify()
        }
      },
      tmplTypeModify(){
        const  requestData={
          routing_tmpl_type_info:{
            tmpl_type_name:this.typeName
          }
        }
        tmplTypeModify(requestData).then(res => {
          this.addDepFlag=false
          $toast.show('添加类型成功')
          this.tmplTypeList()
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      tmplTypeDelete(item,index){
        const  requestData={
          type_id:item.id
        }
        tmplTypeDelete(requestData).then(res => {
          if(res){
            this.temp_list.splice(index,1)
            $toast.show('删除成功')
          }else{
            $toast.show('工艺模板种类已被引用，不能删除！')
          }
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      ascertain(item){
        let tempType={
          tmpl_type_id:item.id,
          tmpl_type_name:item.tmpl_type_name
        }
        sess.set('tempType',JSON.stringify(tempType))
        this.$router.go(-1)

      },
      //获取工艺模板类型列表
      tmplTypeList(){
        const  requestData={
          query:{
            status:'',
            tmpl_type_name:''
          }
        }
        tmplTypeList(requestData).then(res => {
          this.temp_list=res.routing_tmpl_type_list
        }).catch(error => {
          $toast.show(error.description)
        })
      },



    }
  }
</script>

<style lang="scss" scoped>
</style>

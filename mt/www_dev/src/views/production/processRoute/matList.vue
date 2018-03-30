<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>{{ mold_no }}</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content has-tab">
      <div style="height: 60px">
        <tabs :tab-items="tabs" :tab-index="tabIndex" :on-tab-click="onTabClick"></tabs>
      </div>
      <div style="flex: 1">
        <div class="content" v-if="tabIndex===0">
          <scroll class="page-content"  style="-webkit-overflow-scrolling: touch">
            <item v-for="(item,index) in node_list" @click.native="goDetaile(item)">
              <p>{{ item.mat_no+'('+item.mat_name+')' }} <i style="float: right" class="ion-chevron-right"></i></p>
              <!--<span>规格:{{ item.mat_model }}</span>-->
              <!--<span class="itemBtn">创建</span>-->
            </item>
          </scroll>
        </div>
        <div class="content" v-show="tabIndex===1">
          <scroll class="page-content" style="-webkit-overflow-scrolling: touch">
            <item  v-for="(item,index) in electrode_list" @click.native="goDetaile(item)">
              <p>{{ item.mat_no+'('+item.mat_name+')' }} </p>
              <span>规格:{{ item.mat_model }}</span>
              <span class="itemBtn">创建</span>
            </item>
          </scroll>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import { req_bom_node_list } from '@/api/production/processRoute'
  import SwipeItem from "../../../components/swipeItem";
  import CstItem from "../../../components/cstItem"
  export default{
    name:'mateList',
    components: {SwipeItem,CstItem},
    data(){
      return {
        tabs:['主BOM','电极BOM'],
        tabIndex:0,
        mold_no:'',
        node_id:'',
        node_list:'',
        electrode_list:[
          {
              mat_id:'',//	物料id	number
              mat_model:'物料规格尺寸',//	物料规格尺寸	string
              mat_name:'物料名称',//	物料名称	string
              mat_no:'13213213',//	物料编码	string
              mat_type_id:'',//	物料类型id	number
              mat_type_name:'',//	物料类型	string
              mold_no	:'',//模号	string
              node_id:'',//	节点id	number
              node_pos:'',//	bom中绝对中位置	number
              rawnature_name:'',//	物料材质	string
              req_id:'',//	需求id	number
              undeal_qty:'',//	未处理量	number	数据>0时，为可创建
            }


        ],
      }
    },
    computed:{

    },

    mounted(){
      const param=this.$route.query
      this.bom_id=param.bom_id
      this.node_id=param.node_id
      this.mold_no=param.mold_no
      this.getData('成品')
    },
    methods: {
      onTabClick(index) {
        if(index===this.tabIndex) {
          return
        }
        this.tabIndex = index
        if(this.tabIndex==1){
          this.getData('电极')
        }
      },
      //获取数据
      getData(type){
        const  requestData={
          query:{
            bom_type:type,
            bom_id:this.bom_id,
            node_id:this.node_id
          }
        }
        req_bom_node_list(requestData).then(res => {
          if(type==='成品'){
            this.node_list=res.bom_node_list
          }else if(type==='电极'){
            this.electrode_list=res.bom_node_list
          }

        }).catch(error => {
          $toast.show(error.description)
        })
      },
      goDetaile(item){
        if(this.tabIndex===0){
          $router.forward({

            path:'nodesList',query:{
              action:'new',
              node_id:this.node_id,
              bom_id:this.bom_id
            }
          })
        }else{
          $router.forward({
            path:'processRouteDetaile',query:{
              action:'new',
              mat_id:item.mat_id,
              mat_no:item.mat_no,
              req_id:item.req_id
            }
          })
        }

      },


    }
  }
</script>

<style lang="scss" scoped>
  .itemBtn{
    position: absolute;
    right: 0px;
    top:0px;
    height: 65px;
    line-height: 65px;
    width: 70px;
    text-align: center;
    color: #416eb6;
  }
</style>

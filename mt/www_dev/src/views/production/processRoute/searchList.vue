<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>创建工艺路线</span>
      <i class="right" ></i>
    </div>
    <div class="mw-page-content ">

        <div class="content">
          <cst-item v-show="type==='mat'" label="物料编码" placeholder="请输入物料编码" v-model="mat_no"  action="action" ></cst-item>
          <cst-item v-show="type==='bom'" label="模号" placeholder="请输入模号" v-model="mold_no"  action="action" ></cst-item>
          <item v-show="type==='mat'" v-for="(item,index) in mat_req_info" @click.native="goDetaile(item)">
            <p>{{ item.mat_no+'('+item.mat_name+')' }} </p>
            <span>模号:{{ item.mold_no }}</span>
            <span class="itemBtn">创建</span>
          </item>
          <item v-show="type==='bom'" v-for="(item,index) in bom_req_info" @click.native="goMatList(item)">
            <p style="color:#416eb6">{{ item.mold_no }} </p>
            <p style="margin: 5px 0;">产品名称:{{ item.prod_name }}</p>
            <p>客户名称:{{item.cst_name}}</p>
            <i class="ion-ios-arrow-right"></i>
          </item>
        </div>

    </div>

  </div>
</template>

<script>
  import { prod_req_mat_list,prod_req_bom_list } from '@/api/production/processRoute'
  import SwipeItem from "../../../components/swipeItem"
  import CstItem from "../../../components/cstItem"
  export default{
    name:'searchList',
    components: {SwipeItem,CstItem},
    data(){
      return {
        type:'',
        mat_no:'',
        mold_no:'',
        time:'',
        mat_req_info:[
          // {
          //   mat_id:'1',//	物料id	number
          //   mat_name:'物料名称',//	物料名称	string
          //   mat_no:'3213132123',//	物料编码	string
          //   mat_type_id:'1',//	物料类型id	number
          //   mat_type_name:'物料类型',//	物料类型	string
          //   mold_no:'456654654',//	模号	string
          //   node_pos:'',//	bom中绝对中位置	number
          //   prod_name:'产品名称',//	产品名称	string
          //   req_id:'00014',//	需求id	string
          //   undeal_qty:'',//	未处理量
          // }
        ],
        bom_req_info:[
          // {
          //   cst_name:'客户名称',//	客户名称	string
          //   mold_no:'132165565',//	模号	string
          //   node_id	:'1111',//节点id
          //   prod_name:'产品名称',//	产品名称
          // }
        ]
      }
    },
    computed:{

    },
    activated(){
      if(this.type==='mat'&&this.mat_no!==''){
        this.getMatList()
      }else if(this.type==='bom'&&this.mold_no!==''){
        this.getBomList()
      }
    },
    mounted(){
      const param=this.$route.query
      this.type=param.type
    },
    methods: {
      goDetaile(item){
        alert(1)
        $router.forward({
          path:'processRouteDetaile',query:{
            action:'new',
            mat_id:item.mat_id,
            mat_no:item.mat_no,
            req_id:item.req_id
          }
        })
      },
      goMatList(item){
        $router.forward({
          path:'processRoute/matList',query:{
            mold_no:item.mold_no,
            bom_id:item.bom_id,
            node_id:item.node_id
          }
        })
      },
      getBomList(){
        const  requestData={
          query:{
            mold_no:this.mold_no
          }
        }
        prod_req_bom_list(requestData).then(res => {
          this.bom_req_info=res.prod_info
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      getMatList(){
        const  requestData={
          query:{
            mat_no:this.mat_no,
            mold_no:''
          }
        }
        prod_req_mat_list(requestData).then(res => {
          this.mat_req_info=res.prod_info
        }).catch(error => {
          $toast.show(error.description)
        })
      }

    },
    watch:{
      mat_no:function(){
          clearTimeout(this.time)
          this.time=setTimeout(this.getMatList,400)
      },
      mold_no:function(){
        clearTimeout(this.time)
        this.time=setTimeout(this.getBomList,400)
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
  .ion-ios-arrow-right{
    position: absolute;
    right: 0px;
    top:0px;
    height: 89px;
    line-height: 89px;
    width: 70px;
    text-align: center;
    font-size: 30px;
    color: #ababab;
  }
</style>

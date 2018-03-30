<template>
  <div class="mw-page page" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>参考核算单列表</span>
      <i class="right "></i>
    </div>
    <div class="mw-page-content " style="position: relative" >
      <div class="content">
        <scroll class="page-content"
                :on-infinite="onInfinite" style="-webkit-overflow-scrolling: touch">
          <div v-for="(item, index) in items" :key="index"  >
            <item  @click.native="referToInfo(item)">
              <p >类型:{{item.type_name}}</p>
              <p>编号:{{item.compute_no}}</p>
              <p>制品信息:{{item.prod_info}}</p>
              <p>核算日期:{{item.created_at}}</p>
              <span class="referTo" @click="referTo(item)">参考</span>
            </item>
          </div>
          <div v-if="pageModels.curr_page >= pageModels.total_page" slot="infinite" class="text-center">没有更多数据</div>
        </scroll>

        <div class="referToInfo" v-show="isShow" @click="isShow=false">
            <div class="infoBox">
              <p>编号：{{ compute.compute_no }} </p>
              <p>材料费：{{ compute.stuff_cost }}元({{ (Number(compute.stuff_cost )*100/Number(compute.mold_cost)).toFixed(2) }} %) </p>
              <p>加工费：{{ compute.process_cost }}元({{ (Number(compute.process_cost )*100/Number(compute.mold_cost)).toFixed(2) }} %) </p>
              <p>设计费：{{ compute.design_cost }}元({{ (Number(compute.design_cost )*100/Number(compute.mold_cost)).toFixed(2) }} %) </p>
              <p>试模费：{{ compute.try_mold_cost }}元({{ (Number(compute.try_mold_cost )*100/Number(compute.mold_cost)).toFixed(2) }} %) </p>
              <p>其他费用：{{ compute.post_box_cost }}元({{ (Number(compute.post_box_cost )*100/Number(compute.mold_cost)).toFixed(2) }} %) </p>
              <p>管理费：{{ compute.manage_cost }}元({{ compute.manage_ratio }} ) </p>
              <p>成本：{{ compute.mold_cost }}元(100 %) </p>
              <p>利润：{{ compute.profit_cost }}元({{ compute.profit_ratio }} ) </p>
              <p>模具单价：{{ compute.mold_up }}元</p>
            </div>
        </div>
      </div>
    </div>


  </div>
</template>
<script>
  import { mapGetters } from 'vuex'
  import { cstSupSearch } from '@/api/supply'
  import swipeItem from '@/components/swipeItem'
  import _ from 'lodash'
  import {list,detail,associate} from '@/api/customerOrder/compute'
  import sess from '@/utils/sess'
  export default{
    name:'computemReferTo',
    components:{
      swipeItem
    },
    data(){
      return{
        pageModels:{
          curr_page:0,
          page_size:10,
          total_page:0
        },
        query:{       //查询条件
          compute_no:"",    //核算单编号
          approve_step:[], //审批步骤
          cst_company_id:[], //选择的公司id数组
          cst_prod_name:'',   //产品名称
          type:[]             //核算单类型
        },
        items:[],
        compute:{},
        isShow:false

      }
    },

    mounted() {
      this.query.approve_step.push('step0')
    },
    activated(){
      this.setList(true)
    },
    computed:{
      ...mapGetters(['chosenCompany']),
    },
    methods:{
      onMenuClick(){
        this.$router.forward('/customerOrder/compute/detail/new/null')
      },
      onInfinite(done) {
        const that = this;
        if(that.pageModels.total_page<=that.pageModels.curr_page){
          done()
          return
        }
        setTimeout(function () {
          if(that.pageModels.total_page<2){
            done()
          }else{
            that.setList();
            done()
          }
        },400)
      },
      setList(refresh){
        const that = this;
        const requestData={
          curr_page:refresh?1:++that.pageModels.curr_page,
          page_size:that.pageModels.page_size,
          query:this.query
        }
        list(requestData).then(res=>{
          console.log(res)
          if (res.list.length>0) {
            if(!refresh){
              that.items= that.items.concat(res.list);
            }else{
              that.items = res.list;
            }
            that.pageModels.total_page = res.total_page;
            that.pageModels.curr_page = res.curr_page;
            that.pageModels.total_count = res.total_count;
          }

        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      onItemClick(item,isSubmit){
        // this.$router.forward('/customerOrder/compute/detail/check/'+item.compute_id)
        this.$router.forward({
          path:'/customerOrder/computeDetailPreview/',
          query:{
            compute_id:item.compute_id,
            isSubmit:isSubmit
          }
        })
      },
      referTo(item){
        event.cancelBubble = true;
        const reqData = {
          compute_id: item.compute_id,
          current_compute_id:this.$route.query.compute_id
        }
        associate(reqData).then(res => {
          $toast.show('参考成功',600)
          $router.go(-1)
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      referToInfo(item){
        this.isShow=true
        const requestData = {
          compute_id: item.compute_id
        }
        detail(requestData).then(res => {
          this.compute = res
          $toast.hide()

        }).catch(error => {
          this.isShow=false
          $toast.show(error.description,600)
        })
      },

    },
    watch:{

    }
  }
</script>
<style lang="scss" scoped>
  .content {
    position: relative;
    height: 100%;
  }
  p{
    line-height: 16px;padding:1px 0;margin: 0;
    font-size: 12px;
    text-overflow:ellipsis;
  }
.referTo{
  background: #416eb6;
  position: absolute;
  width: 50px;
  text-align: center;
  height: 20px;
  right: 10px;
  top: 50%;
  margin-top: -10px;
  line-height: 20px;
  border-radius: 3px;
  color: #fff;
}
  .referToInfo{
    width: 100%;height: 100%;
    position: absolute;
    left: 0;
    bottom: 0;
    z-index: 50;
    background: rgba(16,8,6,.3);

    &>.infoBox{
      width: 200px;
      height: 321px;
      background: #ffffff;
      position: absolute;
      left: 50%;
      top: 50%;
      margin-left: -100px;
      margin-top: -170px;
      border-radius:6px ;
      padding: 8px 5px;
      &>p{
        line-height: 20px;
        border-bottom: 1px solid #ddd;
        margin: 0;
        font-size: 12px;
        padding:  5px;
        &:last-of-type{
          border-bottom: 0px;
        }
      }
    }
  }
</style>

<template>
    <div class="mw-page page purchase" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>物料需求计划列表</span>
            <i class="right"  v-if="!check" @click="check=true">操作</i>
            <i class="right" v-if="check" @click="check=false">取消</i>
        </div>
        <div class="mw-page-content" style="position: relative;padding-top: 35px;">
          <div class="item-divider" >
            <span style="padding-left: 15px;float:left;color: #416eb6;" v-show="check" @click="checkAll">全选</span>
            <span style="float:right;padding-right: 15px;color: #416eb6;" @click="showFilter=true" v-if="!check">筛选</span>
            <span style="float:right;padding-right: 15px;color: #416eb6;" @click="delItems" v-if="check">删除({{checkedMats.length}})</span>
          </div>
          <div class="content">
            <scroll class="page-content" :on-infinite="onInfinite" :on-refresh="onRefresh" style="-webkit-overflow-scrolling: touch">
              <div v-for="item in require_list">
                <item :class="['req-item',check?'check':'']" >
                  <div class="checkBox" @click="$set(item,'check',!item.check)"><i :class="['icon',item.check?'ion-android-checkbox active':'ion-android-checkbox-outline-blank']"></i> </div>
                  <div class="req-item-detail" @click="onItemClick(item)">
                    <div class="req-item-title">物料描述 ：<span class="vi-color">{{item.mat_desc}}</span><i class="icon ion-ios-arrow-right"></i></div>
                    <div class="req-item-title">物料编码 ：<span class="vi-color">{{item.mat_no}}</span></div>
                    <div class="req-item-content">
                      <span>需求来源 : {{item.create_type_name}}</span><span>当前需求量 : {{item.plan_qty-item.buy_qty}}</span>
                      <span>物料类型 : {{item.mat_type_name}}</span><span>金额 : {{item.total_price}}</span>
                    </div>
                  </div>
                </item>
              </div>
              <div v-if="pageModel.curr_page >= pageModel.total_page" slot="infinite" class="text-center">没有更多数据</div>
            </scroll>
          </div>
        </div>
      <div class="mask" v-show="showFilter" @click="showFilter=false"></div>
      <div :class="showFilter?'filter active':'filter'">
        <div class="item " style="text-align: center" v-show="showFilter">
          筛选
        </div>
        <list v-show="showFilter" style="position: relative">
          <br>
          <span style="padding: 10px">物料来源</span>
          <ul class="types">
            <li v-for="item in types" :key="item.code" @click="checkType(item)" :class="item.checked?'active':''">{{item.value}}</li>
          </ul>
        </list>
        <div class="btns" v-show="showFilter">
          <div class="btn reset" @click="reset">重置</div>
          <div class="btn" style="background-color: #416eb6" @click="getData(true)">确定</div>
        </div>
      </div>
      <div class="mw-page-footer"  v-if="check">
        <!--<div class="btns" v-if="!check">-->
          <!--<div class="btn" @click="check=true">-->
            <!--操作-->
          <!--</div>-->
        <!--</div>-->
        <div class="btns" v-if="check">
          <div class="btn" @click="createPurchase">
            采购
          </div>
          <div class="divider"></div>
          <div class="btn" @click="inquiry">
            询价
          </div>
          <!--<div class="divider"></div>-->
          <!--<div class="btn" @click="check=true">-->
            <!--下料-->
          <!--</div>-->
        </div>
      </div>
    </div>
</template>

<script>
  import { requirementList,requirementDel,querySupplier } from '@/api/purchase/requirement'
  import sess from '@/utils/sess'
    export default{
      name:'requirements',
        data(){
          return {
            check:false,
            showFilter:false,
            isCheckAll:true,
            pageModel:{
              curr_page:0,
              total_page:0,
              page_size:10
            },
            require_list:[

            ],
            query:{
              create_type:[],
              mat_desc:''
            },
            types:[
              {code:1,value:'Bom'},
              {code:2,value:'外协'},
              {code:3,value:'请购单'},
              {code:4,value:'仓库报警'},
              {code:5,value:'库存下料'},
              {code:6,value:'工序转外协'},
            ],
          }
        },
      computed:{
        checkedMats(){
          let mats = []
          mats = this.require_list.filter(o=>{
            return o.check===true
          })
          return mats
        }
      },
        methods: {
          onInfinite(done){
            const that = this
            if(that.pageModel.total_page<=that.pageModel.curr_page){
              done()
              return
            }
            setTimeout(function () {
              if(that.pageModel.total_page<2){
                done()
              }else{
                that.getData();
                done()
              }
            },400)
          },
          onRefresh(done){
            const that = this
            setTimeout(function () {
              that.getData(true)
              done()
            },500)
          },
          onItemClick(item){
            this.$router.forward('/purchase/requirements/detail/'+item.require_id)
          },
          //生成采购单
          createPurchase(){
            if(this.checkedMats.length===0){
              $toast.show('请先选择物料')
              return
            }
            let mat_ids=[]
            this.checkedMats.forEach((e,i)=>{
              mat_ids.push(e.mat_id)
            })
            const reqData = {
              query:{
                mat_ids:mat_ids
              }
            }
            querySupplier(reqData).then(res=>{
              if(res.sup_list.length<1){
                if(mat_ids.length>1){
                  $toast.show('所选物料不属于同一家供应商',600)
                }else{
                  $toast.show('暂无供应商可供采购该物料',600)
                }

              }else{
                sess.set('mats',JSON.stringify(this.checkedMats))
                this.$router.forward('/purchase/purchase/detail/new/null')
              }
            }).catch(error=>{
              $toast.show(error.description)
            })

          },

          //询价单
          inquiry(){
            if(this.checkedMats.length===0){
              $toast.show('请先选择物料')
              return
            }
            sess.set('mats',JSON.stringify(this.checkedMats))
            console.log(this.checkedMats)
            this.$router.forward('/purchase/inquiry/feedback/new/null')
          },
          getData(refresh){
            const reqData = {
              curr_page :refresh? 1:1+this.pageModel.curr_page,
              page_size : this.pageModel.page_size,
              query:this.query
            }
            requirementList(reqData).then(res=>{
              if(refresh){
                this.require_list.length = 0
                this.showFilter=false
              }
              this.pageModel.curr_page = res.curr_page
              this.pageModel.total_page = res.total_page
              this.require_list = this.require_list.concat(res.require_list)

            }).catch(error=>{
              $toast.show(error.description)
            })
          },
          checkAll(){
            this.isCheckAll = !this.isCheckAll
            this.require_list.forEach(item=>{
              if(this.isCheckAll){
                this.$set(item,'check',false)
              }else{
                this.$set(item,'check',true)
              }
            })
          },
          checkType(item){
            this.$set(item,'checked',!item.checked)
            if(item.checked){
              this.query.create_type.push(item.code)
            }else{
              let index=this.query.create_type.indexOf(item.code)
              this.query.create_type.splice(index,1)
            }

          },
          delItems(){
            if(this.checkedMats.length===0){
              return
            }
            $dialog.confirm({
              theme:'ios',
              title:'确定要删除这'+this.checkedMats.length+'项吗？',
              cancelText:'取消',
              confirmText:'确定'
            }).then(res=>{
              if(res){

              }
            })
          },
          reset(){
            this.types.forEach(i=>{
              i.checked  = false
            })
            this.query.create_type=[]
          }
        },
      mounted(){
        this.getData(true)
      },
      activated(){
        this.getData(true)
      }
    }
</script>

<style lang="scss" scoped>
  .item-divider{
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
  }

</style>

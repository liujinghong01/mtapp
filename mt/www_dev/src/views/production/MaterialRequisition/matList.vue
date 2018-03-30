<template>
    <div class="mw-page page storage" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>物料清单</span>
            <i class="right"></i>
        </div>
        <div class="mw-page-content has-tab">
          <div v-show="type*1!==1" style="height: 50px;">
            <div style="width: 100%;height: 100%;padding: 10px">
              <input type="search" placeholder="请输入物料编码" style="float:left;width: 85%;border-radius: 5px;padding: 0 5px;box-sizing: border-box" v-model="matSearch"/>
              <i  class="iconfont icon-saoyisao"></i>
            </div>
          </div>
          <div style="flex: 1;position: relative">
            <div class="hairline-top"></div>
            <item v-for="item in matList">
              <div style="display: flex">
                <div class="matBill-content" style="width: 80%">
                  <p class="title">物料名称: {{item.mat_desc}}</p>
                  <p class="title">物料编码: {{item.mat_no}}</p>
                  <p class="title">规格型号: {{item.mat_model}}</p>
                  <span>所在仓库: {{item.store_house_name}}</span>&nbsp;&nbsp;
                  <span v-if="type*1===1">库存量: {{item.total_stock_qty}}</span>
                  <span v-else>库存量: {{item.stock_qty}}</span>
                  <p v-if="type*1===1" class="title">需求量: {{item.undeal_qty}}</p>
                </div>
                <div class="edit-zone" style="width: 25%" v-if="item.quantity>0">
                    <span @click="minCount(item)">-</span>&nbsp;&nbsp;
                    <input type="number" v-model="item.quantity"  @change="countChange(item)"/>
                    &nbsp;&nbsp;<span @click="addCount(item)">+</span>
                </div>
                <div v-else class="edit-zone">
                  <span @click="addCount(item)">+</span>
                </div>
              </div>
            </item>
          </div>
        </div>
        <div class="mw-page-footer">
          <div class="btns">
            <div style="" class="total">
              <div style="display: flex">
                <span >物料种类:{{this.chosenMats.length}}</span>
                <span style="padding: 0 15px">{{totalCount}}</span>
              </div>
            </div>
            <div class="btn" style="background-color: #416eb6;color: white;padding: 15px 0" @click="ok">
                确定
            </div>
          </div>
        </div>
    </div>
</template>

<script>
  import { storeSearchMat,moldMatReq } from '@/api/storage/baseInfo'
  import _ from 'lodash'
  import sess from '@/utils/sess'
    export default{
      name:'MaterialList',
        data(){
          return {
            matSearch: '',  //搜索关键字
            matList: [],
            list:[],
            house_id:'',
            chosenMats:[],    //已选择的产品
//            totalPrice:0.00,  //总金额
            action:'check',
            type:'',
            bom_type:'',
            mold_no:''
          }
        },
      mounted() {
        let param=this.$route.query
        this.type=param.type
        this.bom_type=param.bom_type
        this.mold_no=param.mold_no
        let list=[]
        if(sess.get('matList')!==null){
          list=JSON.parse(sess.get('matList'))
        }
        if(list.length>0){
          this.chosenMats=list
          sess.remove('matList')
          console.log('获取本地储存成功2'+list)
        }else{
          console.log('获取本地储存为空2'+list)
        }
        if(this.type*1===1){
          this.moldMatReq()
        }
      },
        methods: {
          cancel(){
            this.action='action'
          },
          searchMats:_.debounce(
            function(){
                this.storeSearchMat()
            },1000),
          storeSearchMat(){
            storeSearchMat({
              query:{
                mat_desc:'',
                mat_id:'',
                mat_model:'',
                mat_no:this.matSearch
              }
            }).then(res=>{
              if(res.store_mat_list.length>0){
                this.matList = res.store_mat_list
                if(this.chosenMats.length>0){
                  this.matList.forEach((item,index)=>{
                    this.chosenMats.forEach((e,i)=>{
                      if(item.mat_no===e.mat_no){
                        this.$set(item,'quantity',e.quantity)
                      }
                    })
                  })
                }

              }else{
                $toast.show('结果查询为空')
                this.matList = []
              }
            }).catch(error=>{
              $toast.show(error.description)
            })
          },
          moldMatReq(){
            moldMatReq({
              query:{
                apply_type:this.type,
                bom_type:this.bom_type,
                mold_no:this.mold_no,
              }
            }).then(res=>{
              if(res.req_list.length>0){
                this.matList = res.req_list
                if(this.chosenMats.length>0){
                  this.matList.forEach((item,index)=>{
                    this.chosenMats.forEach((e,i)=>{
                      if(item.mat_no===e.mat_no){
                        this.$set(item,'quantity',e.quantity)
                      }
                    })
                  })
                }

              }else{
                $toast.show('结果查询为空')
                this.matList = []
              }
            }).catch(error=>{
              $toast.show(error.description)
            })
          },

          addCount(item){
            if(!item.quantity) {
              this.$set(item,'quantity',1)
              this.chosenMats.push(item)
            }else{
              if(this.type==='1'){
                if(item.quantity<item.undeal_qty){
                  item.quantity++
                }else{
                  item.quantity=item.undeal_qty
                }
              }else{
                if(item.quantity<item.stock_qty){
                  item.quantity++
                }else{
                  item.quantity=item.stock_qty
                }
              }
              this.chosenMats.forEach((e,i)=>{
                if(e.mat_id==item.mat_id){
                  e.quantity=item.quantity
                }
              })

            }
          },
          countChange(item){
            if(item.quantity<=0){
              item.quantity=1
            }
            if(this.type==='1'){
              if(item.quantity>item.undeal_qty) {
                item.quantity = item.undeal_qty
              }
            }else{
              if(item.quantity>item.stock_qty){
                item.quantity=item.stock_qty
              }
            }
            this.chosenMats.forEach((e,i)=>{
              if(e.mat_id==item.mat_id){
                e.quantity=item.quantity
              }
            })
          },
          minCount(item){//点击-号
            if(item.quantity===1){
              $dialog.confirm({
                theme:'ios',
                title:'要删除物料吗？',
                okText:'确定',
                cancelText:'取消'
              }).then(res=>{
                if(res) {
                  item.quantity=0
                  this.chosenMats.forEach((e,i)=>{
                    if(e.mat_id==item.mat_id){
                      item.checked=false
                      this.chosenMats.splice(i,1)
                    }
                  })
                }
              })
              return
            }
            this.$set(item,'quantity',--item.quantity)
            this.chosenMats.forEach((e,i)=>{
              if(e.mat_id==item.mat_id){
                e.quantity=item.quantity
              }
            })
          },
          ok(){
            if(this.chosenMats.length === 0){
              $toast.show('请选择物料')
              return
            }
            if(this.chosenMats.length>0){
              let list=JSON.stringify(this.chosenMats)
              sess.set('mats',list)
              console.log("本地储存成功2"+list)
            }else{
              console.log("本地储存为空2")
            }
            $router.go(-1)
          }
        },
        computed:{
        totalCount(){
          let sumCount = 0
          this.chosenMats.filter(o=>{
            sumCount += o.quantity
          })
          return '总数量'+sumCount
        },
      },
        watch:{
        'matSearch':function () {
          this.searchMats()
        }
      },

    }
</script>

<style lang="scss" scoped>
  .total{
    flex: 0 0 80%;
    span{
      flex: 1 0 auto;text-align: center
    }
  }
  .icon-saoyisao{
    float: left;
    display: block;width: 15%;
    text-align: center;
    line-height: 34px;
  }
</style>

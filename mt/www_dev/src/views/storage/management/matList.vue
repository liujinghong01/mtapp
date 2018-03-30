<template>
    <div class="mw-page page storage" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>物料清单</span>
            <i class="right"></i>
        </div>
        <div class="mw-page-content has-tab">
          <div style="height: 50px;">
            <div style="display: flex;width: 100%;margin: 10px 15px;justify-content: center;align-items: center">
              <input type="search" style="flex:0 0 80%;border-radius: 5px" v-model="matSearch"/>
              <i style="flex: 1;text-align: center" class="iconfont icon-saoyisao"></i>
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
                  <span>所在仓库: {{item.store_house_name}}</span>
                  <span>库存量: {{item.stock_qty}}</span> &nbsp;&nbsp;
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
  import { queryMats } from '@/api/storage/baseInfo'
  import _ from 'lodash'
  import sess from '@/utils/sess'
    export default{
      name:'matList',
        data(){
          return {
            matSearch: '',  //搜索关键字
            matList: [],
            list:[],
            house_id:'',
            chosenMats:[],    //已选择的产品
//            totalPrice:0.00,  //总金额
            action:'check'
          }
        },
        methods: {
          searchMats:_.debounce(
            function(){
              queryMats({
                query:{
                  is_include_process_type:1,
                  mat_desc:this.matSearch,
                  store_house_id:this.house_id
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
            },1000),
          addCount(item){
            if(!item.quantity) {
              this.$set(item,'quantity',1)
              this.chosenMats.push(item)
            }else{
              if(this.house_id===''){
                this.$set(item,'quantity',++item.quantity)
                this.chosenMats.forEach((e,i)=>{
                  if(e.mat_no==item.mat_no){
                    e.quantity=item.quantity
                  }
                })
              }else{
                if(item.quantity<item.stock_qty){
                  this.$set(item,'quantity',++item.quantity)
                  this.chosenMats.forEach((e,i)=>{
                    if(e.mat_no==item.mat_no){
                      e.quantity=item.quantity
                    }
                  })
                }
              }

            }
          },
          countChange(item){
            if(item.quantity<=0){
              item.quantity=1
            }
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
                    if(e.mat_no==item.mat_no){
                      item.checked=false
                      this.chosenMats.splice(i,1)
                    }
                  })
                }
              })
              return
            }
            this.$set(item,'quantity',--item.quantity)
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
        mounted() {
          this.house_id=this.$route.query.house_id
          console.log(sess.get('matList'))
          let list=JSON.parse(sess.get('matList'))
          if(list.length>0){
            this.chosenMats=list
            sess.remove('matList')
            console.log('获取本地储存成功2'+list)
          }else{
            console.log('获取本地储存为空2'+list)
          }
        }
    }
</script>

<style lang="scss" scoped>
  .total{
    flex: 0 0 80%;
    span{
      flex: 1 0 auto;text-align: center
    }
  }
</style>

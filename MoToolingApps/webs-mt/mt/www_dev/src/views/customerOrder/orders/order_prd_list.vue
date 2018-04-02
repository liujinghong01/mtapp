<template>
  <div class="mw-page page" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>制品列表</span>
      <i class="right" v-if="action==='check'" @click="action='edit'">编辑</i>
      <i class="right" v-else-if="action==='edit'" @click="action='check'">取消</i>
      <i class="right" v-else></i>
    </div>
    <div class="mw-page-content">
      <list>
        <div v-for="(item,index) in cst_prods">
          <div class="item-divider prd-header">
            <span style="padding-left:15px;float:left;">制品{{index+1}}</span>
            <span style="padding-right:15px;float:right;" @click="delItem(item,index)" v-if="action === 'edit' || action === 'new'">删除</span>
          </div>
          <!--<cst-item label="名称" v-model="item.cst_prod_name" :action="action"></cst-item>-->
          <item style="margin: 0">
            <span>名称</span>
            <input @blur="duplicate(item,index)" style="padding:0;float: right;width:70%;height: 15px;text-align: right" :disabled="action==='check'" type="text" v-model="item.cst_prod_name">
          </item>
          <cst-item label="腔数" v-model="item.cavity_total" :action="action" placeholder="0" editType="number"></cst-item>
          <!--<cst-item label="附件信息" :val="item.cavity_total" :action="edit"></cst-item>-->
        </div>
        <button v-show="action!='check'&&action!='checkOnly'&&action!='approve'" @click="addOneProd" class="button button-calm button-block button-outline">添加制品信息</button>
      </list>
    </div>
    <div class="btns" v-show="action!='check'&&action!='checkOnly'">
      <div class="btn" @click="saveData">
        确定
      </div>
    </div>
  </div>
</template>
<script>
  import cstItem from '@/components/cstItem'
  import sess from '@/utils/sess'
  import {prdDel } from '@/api/customerOrder/compute'
  export default{
    name:'orderPrdList',
    components:{
      cstItem,
    },
    data(){
      return {
        cst_prods:[],
        action:''
      }
    },
    mounted(){
      this.action = this.$route.params.action
      let prdList = JSON.parse(window.sessionStorage.getItem('prdList'))
      window.sessionStorage.removeItem('prdList')
      this.cst_prods = prdList ? prdList :[]
//      if(this.action!=='new'){
//        this.cst_prods = JSON.parse(window.sessionStorage.getItem('prdList'))
//        sess.remove('prdList')
//        //this.cst_prods = JSON.parse(this.$route.params.line_id)
//      }
    },
    methods:{
      duplicate(item,index){
          if (this.cst_prods.length <= 1){
            return
          }
          console.log(item)
          this.cst_prods.forEach((e,i)=>{
            if(e.cst_prod_name === item.cst_prod_name && i!==index){
              item.cst_prod_name=''
              $toast.show('您填写的名称又重复，请重新填写',600)
            }
          })
      },
      closeMask(){
        this.currItem.swipe = false;
        this.currItem = {}
      },
      delItem(item,index){
        const that = this;
        $dialog.confirm({
          theme:'ios',
          // 标题
          title: '确定要删除'+item.cst_prod_name+'吗?',
          // 取消按钮文本
          cancelText: '取消',
          // 确定按钮文本
          okText: '确定'
        }).then((res) => {
          console.log('confirm result: ', res)
          if(res){
            that.delProd(index)
          }
        })
      },
      addOneProd(){
        let obj = {
          cst_prod_name:'',
          cavity_total:'',
          prod_id:null
        }
        this.cst_prods.push(obj)
      },
      delProd(i){
        for(let index in this.cst_prods){
          if(index==i){
            this.cst_prods.splice(i,1)
          }
        }
      },
      saveData(){ //这里并没有保存制品信息,只是将数组保存在了session里,保存是在产品明细(行信息)里一起保存

        let a = true
        this.cst_prods.forEach(o=>{
          if (!o.cst_prod_name.trim()) {
            $toast.show('制品名称不能为空')
            a = false
            return
          }
          if (!o.cavity_total) {
            $toast.show('腔数不能为0')
            a = false
            return
          }
        })

        if (!a){
            return
        }

        sess.set('prods',JSON.stringify(this.cst_prods))

        history.go(-1)
      }
    }
  }
</script>
<style scoped lang="scss">
  .prd-header{
    span{
      color: #416eb6;
      transform: scale(0.8,08);
      -webkit-transform: scale(0.8,0.8);
    }

  }
</style>

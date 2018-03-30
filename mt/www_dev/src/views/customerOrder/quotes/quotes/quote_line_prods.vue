<template>
    <div class="page has-navbar" v-nav="{hideNavbar:true}">
      <div class="navbar top">
        <von-header style="position: fixed;visibility: visible">
          <button class="button button-icon ion-ios-arrow-back" slot="left" @click="$router.go(-1)"></button>
          <span slot="title">制品列表</span>
          <!--<span slot="right" class="right-menu" v-show="action==='check'" @click="onMenuClick">编辑</span>-->
          <!--<span slot="right" class="right-menu" v-show="action==='edit'" @click="onMenuClick">取消</span>-->
        </von-header>
      </div>
        <div class="page-content">
          <list>
            <div v-for="(item,index) in cst_prods">
              <div class="item-divider prd-header">
                <span style="padding-left:30px;float:left;">制品{{index+1}}</span>
                <span v-if="action!=='check'" style="padding-right:30px;float:right;" @click="delItem(item,index)">删除</span>
              </div>
              <item style="margin: 0">
                <span>名称</span>
                <input @blur="duplicate(item,index)" style="padding:0;float: right;width:70%;height: 15px;text-align: right" :disabled="action==='check'" type="text" v-model="item.cst_prod_name">
              </item>
              <cst-item label="腔数" v-model="item.cavity_total" :action="action" placeholder="0"></cst-item>
              <!--<cst-item label="附件信息" :val="item.cavity_total" :action="edit"></cst-item>-->
            </div>
            <button v-show="action!='check'" @click="addOneProd" class="button button-calm button-block button-outline">添加制品信息</button>
          </list>
        </div>
      <div class="btns" v-show="action!='check'">
        <div class="btn" @click="saveData">
          保存
        </div>
      </div>
    </div>
</template>
<script>
  import cstItem from '@/components/cstItem'
  import sess from '@/utils/sess'
  import {prdDel } from '@/api/customerOrder/compute'
    export default{
      name:'quote_line_prods',
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
        if(this.action!=='new'){
          this.cst_prods = JSON.parse(this.$route.params.cst_prod_list)
        }
      },
      methods:{

        duplicate(item,index){
          if (this.cst_prods.length <= 1){
            return
          }
          this.cst_prods.forEach((e,i)=>{
            if(e.cst_prod_name === item.cst_prod_name && i!==index){
              item.cst_prod_name=''
              $toast.show('您填写的名称重复，请重新填写',2000)
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
        onItemClick(item){
          if(this.currItem.swipe){
            this.currItem.swipe = false;
            return;
          }
          $router.forward('/customerOrder/compute/cst_prod_detail/'+this.action+'/'+JSON.stringify(item))
        },
        onSwipeLeft(item){
          this.$set(item,'swipe',true);
          this.currItem = item
        },
        addOneProd(){
          let obj = {
            cst_prod_name:'',
            cavity_total:'',
          }
          this.cst_prods.push(obj)
        },
        delProd(i){
          console.log(i)
          for(let index in this.cst_prods){
              console.log(index)
            if(index==i){
              this.cst_prods.splice(i,1)
            }
          }
        },
        saveData(){
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

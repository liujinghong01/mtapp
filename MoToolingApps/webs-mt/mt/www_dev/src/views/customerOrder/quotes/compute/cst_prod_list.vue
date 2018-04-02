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
                <span style="padding-right:15px;float:right;" @click="delProd(index)">删除</span>
              </div>
              <cst-item label="名称" v-model="item.cst_prod_name"  placeholder="制品名称" :action="action"></cst-item>
              <cst-item label="腔数" v-model="item.cavity_total" editType="number" placeholder="0" :action="action"></cst-item>
              <!--<cst-item label="附件信息" :val="item.cavity_total" :action="edit"></cst-item>-->
            </div>
            <button v-show="action !== 'check' && action !== 'readOnly'" @click="addOneProd" class="button button-calm button-block button-outline">添加制品信息</button>
          </list>
        </div>
      <div class="btns" v-show="action !== 'check' && action !== 'readOnly'">
        <div class="btn" @click="saveData">
          保存
        </div>
      </div>
    </div>
</template>
<script>
  import swipeItem from '@/components/swipeItem'
  import cstItem from '@/components/cstItem'
  import sess from '@/utils/sess'
  import {prdDel } from '@/api/customerOrder/compute'
    export default{
      name:'cst_prod_list',
      components:{
        cstItem,
        swipeItem
      },
      data(){
        return {
          cst_prods:[],
          action:''
        }
      },
      mounted(){
          this.cst_prods = JSON.parse(this.$route.query.cst_prods)
          this.action = this.$route.params.action
      },
      methods:{
        onMenuClick(){
          if(this.action==='check'){
            this.action = 'edit'
          }else{
            this.action = 'check'
          }
        },
        closeMask(){
          this.currItem.swipe = false;
          this.currItem = {}
        },
        delItem(item){
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
          //如果是查看

        },
        saveData(){
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

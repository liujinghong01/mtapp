
          <template>
            <div class="mw-page page" v-nav="{hideNavbar:true}">
              <div class="mw-page-header">
                <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
                <span>加工项目</span>
                <!--<i class="right" @click="onMenuClick" v-else-if="action==='check'">编辑</i>-->
                <i class="right"></i>
              </div>
              <div class="mw-page-content">
          <list>
            <cst-item label="项目" v-model="process_detail.process_name" :action="action" :hasArrow="true" @click.native="changeType"></cst-item>
            <cst-item :label="'工时/数量('+(process_detail.process_unit)+')'" v-model="process_detail.process_hours" placeholder="0" :action="action" editType="number"></cst-item>
            <cst-item label="单位" v-model="process_detail.process_unit" :action="action" :canEdit="false"></cst-item>
            <cst-item label="费率" v-model="process_detail.cost_ratio"  editType="number"></cst-item>
            <cst-item label="费用" v-model="process_detail.process_cost" :action="action"></cst-item>
          </list>
        </div>
      <sidebar-check :showFilter="showFilter" dicType="processItem" :chosen="chosen" v-on:hideMask="showFilter = false"></sidebar-check>
      <div class="btns" v-show="action!='check'">
        <div class="btn" @click="saveData">
          保存
        </div>
      </div>
    </div>
</template>
<script>
  import CstItem from "../../../../components/cstItem";
  import sess from '@/utils/sess'
  import SidebarCheck from "../../../../components/sidebarCheck";
    export default{
      components: {
        SidebarCheck,
        CstItem},
      name:'cst_process_detail',
      data(){
        return{
          process_detail:{
            id:'',
            process_cost:'',
            process_hours:'',
            cost_ratio:'',
            process_name:'',
            process_unit:'',
          },
          action:'',
          showFilter:false
        }
      },
      computed:{

      },
      mounted(){
        this.action = this.$route.query.action
        if(this.action==='new'){
          this.compute_id = this.$route.query.process_detail
        }else{
          this.process_detail = JSON.parse(decodeURIComponent(this.$route.query.process_detail))
        }
      },
      methods:{
        itemCost(){
            if (this.process_detail.process_unit!==''){
              this.process_detail.process_cost =Number(parseFloat(this.process_detail.cost_ratio)* parseFloat(this.process_detail.process_hours)).toFixed(2)
            }
            if(isNaN(this.process_detail.process_cost)){
              this.process_detail.process_cost=0
            }
        },
        saveData(){
          if(!this.process_detail.process_name){
            $toast.show('请选择加工项目')
            return
          }
          if(this.process_detail.process_hours==0){
            $toast.show('请填写工时数')
            return
          }
          sess.set('process_detail',JSON.stringify(this.process_detail))
          history.go(-1)
        },
        onMenuClick(){
          this.action = 'edit'
        },
        changeType(){
          if(this.action !== 'check'){
            this.showFilter = true
          }
        },
        chosen(item){
          console.log(item)
          this.process_detail.cost_ratio = item.process_ratio
          this.process_detail.process_name = item.process_name
          this.process_detail.process_unit = item.process_unit_name
        }
      },
      watch:{
        'process_detail.process_hours':{
          handler:function(e,v){
            this.itemCost()
          },deep:true
        },
        'process_detail.process_name':{
          handler:function(e,v){
            this.itemCost()
          },deep:true
        },

      }
    }
</script>
<style scoped lang="scss">
  .item{
    padding-left: 40px;
    .label{
      width: 30%;
      float: left;
      height: 100%;
    }
    .value{
      width: 70%;
      margin-left: 30px;
      text-align: right;
    }
  }
</style>

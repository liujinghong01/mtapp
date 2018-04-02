
          <template>
            <div class="mw-page page" v-nav="{hideNavbar:true}">
              <div class="mw-page-header">
                <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
                <span>子部件详情</span>
                <!--<i class="right" v-if="action==='check'"  @click="action='edit'">编辑</i>-->
                <!--<i class="right" v-else-if="action ==='edit'||action ==='new'" @click="action='check'">取消</i>-->
                <i class="right"></i>
              </div>
              <div class="mw-page-content">
          <list>
            <cst-item label="部件名称" v-model="part_detail.sub_part_name" placeholder="请输入部件名称" :action="action"></cst-item>
            <cst-item label="材质"  v-model="part_detail.raw_nature" placeholder="请选择材质"
                      editType="text"
                      :action="action"
                      :hasArrow="true"
                      :canEdit="false"
                      @click.native="showFilter = (action ==='edit' || action ==='new')">
            </cst-item>
            <cst-item label="截面形状"  v-if="action === 'check' || action === 'approve'"  v-model="part_detail.stuff_shape"></cst-item>
            <item style="min-height: 49px;position: relative;padding: 17px 15px;" v-else>
              <span>截面形状</span>
              <label><input name="stuff_shape" type="radio" value="方形" v-model="part_detail.stuff_shape"/> 方形</label>
              <label>
                <input name="stuff_shape" type="radio" value="圆形" v-model="part_detail.stuff_shape"/> 圆形
              </label>
            </item>
            <item class="size" style="padding-left: 15px" v-show="part_detail.stuff_shape==='方形'" >
              <span class="label">尺寸(mm)</span>
              <ul class="lines">
                <li>
                  <span >长</span>
                  <input type="number"  v-if="action === 'check' || action === 'approve'"   v-model="part_detail.stuff_length" disabled/>
                  <input type="number" v-else v-model="part_detail.stuff_length"/>
                  x
                </li>
                <li>
                  <span>宽</span>
                  <input type="number"  v-if="action === 'check' || action === 'approve'"   v-model="part_detail.stuff_width" disabled/>
                  <input type="number" v-else v-model="part_detail.stuff_width"/>
                  x
                </li>
                <li>
                  <span>高</span>
                  <input type="number"  v-if="action === 'check' || action === 'approve'"   v-model="part_detail.stuff_height" disabled/>
                  <input type="number" v-else v-model="part_detail.stuff_height"/>
                </li>
              </ul>
            </item>
            <cst-item v-show="part_detail.stuff_shape==='圆形'" label="高(mm)" v-model="part_detail.stuff_height" placeholder="0" :action="action" editType="number"></cst-item>
            <cst-item v-show="part_detail.stuff_shape==='圆形'" label="直径(mm)" v-model="part_detail.stuff_diameter"  placeholder="0"  :action="action" editType="number"></cst-item>
            <cst-item :label="'重量('+part_detail.stuff_density_unit+')'" v-model="part_detail.stuff_weight" :action="action" editType="number"></cst-item>
            <cst-item label="单价" v-model="part_detail.stuff_price"  :action="action" editType="number"></cst-item>
            <cst-item label="件数" v-model="part_detail.stuff_piece" :action="action"  placeholder="0"  editType="number"></cst-item>
            <cst-item label="总价" v-model="part_detail.stuff_up"  :action="action" editType="number"></cst-item>
          </list>
        </div>
      <sidebar-check :showFilter="showFilter" dicType="material" :chosen="chosen" v-on:hideMask="showFilter = false"></sidebar-check>
      <div class="btns" v-show="action!='check'">
        <div class="btn" @click="saveData">
          确定
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
      name:'cst_part_sub_detail',
      data(){
        return{
          part_detail:{
            is_calc_weight:'',
            raw_nature:'',
            raw_nature_id:'',
            stuff_density:0,   //比重
            stuff_density_unit:'kg',
            stuff_diameter:'',
            stuff_height:'',
            stuff_length:'',
            stuff_price:0,   //子部件单价 =  1、长*宽*高/1000000*比重*材料采购单价(procure_price)  2、pi*直径(stuff_diameter)*直径(stuff_diameter)*高*比重*材料采购单价(procure_price)
            stuff_piece:'',   //数量
            stuff_shape:'圆形',
            stuff_up:0.00,    //总价
            stuff_weight:0,
            stuff_width:'',
            sub_part_id:'',
            sub_part_name:'',
            raw_price:0
          },
          action:'',
          showFilter:false
        }
      },
      methods:{
        chosen(item){
          this.part_detail.raw_nature = item.raw_nature
          this.part_detail.stuff_density = item.density
          this.part_detail.raw_price = item.procure_price
        },
        saveData(){
          if(!this.part_detail.sub_part_name){
            $toast.show('请填写部件名称')
            return
          }
//          if(!this.part_detail.stuff_up){
//            $toast.show('总价不能为0')
//            return
//          }
          sess.set('part_detail',JSON.stringify(this.part_detail))
          history.go(-1)
        },
        //重量计算
        stuffPrice(){
          if(this.action ==='new' || this.action ==='edit'){
            let v = 0.00
            if(this.part_detail.stuff_shape === '方形'){   //方形的时候计算单价
              v = parseFloat(this.part_detail.stuff_length)*parseFloat(this.part_detail.stuff_height)*parseFloat(this.part_detail.stuff_width)
            }else{
              v =  Number(Math.PI * (this.part_detail.stuff_diameter * this.part_detail.stuff_diameter) / 4 * this.part_detail.stuff_height).toFixed(2)
            }
            if(v){
              // console.log(v)
              this.part_detail.stuff_weight = Number(v/1000000*this.part_detail.stuff_density).toFixed(3)
              if(parseFloat(this.part_detail.raw_price)>0){
                this.part_detail.stuff_price = Number( this.part_detail.stuff_weight*parseFloat(this.part_detail.raw_price)).toFixed(3)
              }


            }
          }
        },
        //计算总价
        totalPrice(){
          let result = 0.00
          let v = this.part_detail.stuff_price * this.part_detail.stuff_piece

          if(v){
            console.log(this.part_detail.stuff_weight)
            if(Number(this.part_detail.stuff_weight)===0){
              this.part_detail.stuff_up = result = Number(v).toFixed(2)
            }else{
              this.part_detail.stuff_up = result = (Number(v)*Number(this.part_detail.stuff_weight)).toFixed(2)
            }
          }else{
            this.part_detail.stuff_up = result = 0.00
          }
          return result
        }
      },
      mounted(){
        this.action = this.$route.params.action
        if(this.action==='new'){
        }else{
          this.part_detail = JSON.parse(this.$route.query.cst_part_sub_detail)
        }
      },
      watch:{
        'part_detail.raw_nature':function () {
          this.stuffPrice()
          this.totalPrice()
        },
        'part_detail.stuff_shape':function () {
          this.stuffPrice()
          this.totalPrice()
        },
        'part_detail.stuff_piece':function () {
          this.totalPrice()
        },
        'part_detail.stuff_price':function () {
          this.totalPrice()
        },
        'part_detail.stuff_length':function () {
          this.stuffPrice()
          this.totalPrice()
        },
        'part_detail.stuff_width':function () {
          this.stuffPrice()
          this.totalPrice()
        },
        'part_detail.stuff_weight':function (){
          this.totalPrice()
        },
        'part_detail.stuff_height':function () {
          this.stuffPrice()
          this.totalPrice()
        },
        'part_detail.stuff_diameter':function () {
          this.stuffPrice()
          this.totalPrice()
        }
      }
    }
</script>
<style scoped lang="scss">
  .item{
    padding-left: 20px;
    label{
      width: 30%;
      float: right;
    }
  }
</style>

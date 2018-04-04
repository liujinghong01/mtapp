<template>
  <div class="mw-page page" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>定单下达</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content">
      <item>
        <span>供货类型</span>
        <label>
          <input name="sup_type" type="radio" value="1" v-model="order_startup.sup_type"/> 自制
        </label>
        <label>
          <input name="sup_type" type="radio" value="2" v-model="order_startup.sup_type"/> 外购
        </label>
      </item>
      <cst-item label="数量" editType="number" v-model="order_startup.count" :canEdit="false"></cst-item>
      <cst-item label="未税单价" editType="number" v-model="order_startup.up" :canEdit="false"></cst-item>
      <cst-item label="未税总价" editType="number" v-model="order_startup.price" :canEdit="false"></cst-item>
      <item>
        <span>是否制定BOM</span>


        <label >
          <input name="is_crt_bom" type="radio" value="1" v-model="order_startup.is_crt_bom" /> 是
        </label>
        <label v-if="order_startup.sup_type==2">
          <input name="is_crt_bom" type="radio" :value="0" v-model="order_startup.is_crt_bom"/> 否
        </label>
      </item>
      <item>
        <span>是否批量生产</span>

        <label><input name="is_batch_process" type="radio" value="1" v-model="order_startup.is_batch_process"/>是</label>
        <label>
          <input name="is_batch_process" type="radio" value="0" v-model="order_startup.is_batch_process"/> 否
        </label>
      </item>
      <div class="item-divider">
        <span class="divider-title">目标成本</span>
      </div>
      <item>
        <input class="input-percent" @input="checkPercent('design_ratio')" placeholder="0" type="number" v-model="order_startup.design_ratio_percent" maxlength="2"/>% 设计费
        <input class="input-value" @input="checkValue('design_cost')" placeholder="0"  v-model="order_startup.design_cost" type="number"/>
      </item>
      <item>
        <input class="input-percent" @input="checkPercent('stuff_ratio')" placeholder="0"   v-model="order_startup.stuff_ratio_percent" type="number"/>% 材料费
        <input class="input-value" @input="checkValue('stuff_cost')"  placeholder="0"  v-model="order_startup.stuff_cost" type="number"/>
      </item>
      <item>
        <input class="input-percent" @input="checkPercent('process_ratio')" placeholder="0"   v-model="order_startup.process_ratio_percent" type="number"/>% 加工费
        <input class="input-value" @input="checkValue('process_cost')"  placeholder="0"  v-model="order_startup.process_cost" type="number"/>
      </item>

    </div>
    <div class="mw-page-footer">
      <div class="btns">
        <div class="btn" @click.prevent="startup">
          确定下达
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {startup } from '@/api/customerOrder/manage'
  import {queryLineDetail} from '@/api/customerOrder/order'
  import CstItem from "../../../components/cstItem";
  export default{
    name:'manageStart',
    components: {
      CstItem,
    },
    data(){
      return {
        order_line_id:'',
        order_startup:{
          sup_type:1,
          up:'',
          order_line_id:'',
          process_cost:'',
          process_ratio:'',
          process_ratio_percent:'',
          design_cost:'',
          design_ratio:'',
          design_ratio_percent:'',
          is_batch_process:'1',
          is_crt_bom:'1',
          stuff_cost:'',
          stuff_ratio:'',
          stuff_ratio_percent:'',
          count:'',
          cst_mold_no:'',
          price:0.00
        },
      }
    },
    computed:{
      up:function () {
        if(this.order_startup.is_batch_process==1){
          this.order_startup.price = this.order_startup.count * this.order_startup.up
        }else{
          this.order_startup.price = this.order_startup.up
        }
//        this.resetPrice('process_ratio')
        return this.order_startup.price
      }
    },
    methods: {
      startup(){
//        if(this.order_startup.design_cost==0){
//          $toast.show('设计费不能为0')
//          return
//        }
//        if(this.order_startup.stuff_cost==0){
//          $toast.show('材料费不能为0')
//          return
//        }
//        if(this.order_startup.process_cost==0){
//          $toast.show('加工费不能为0')
//          return
//        }
        let total = Number(this.order_startup.design_ratio_percent) +
          Number(this.order_startup.stuff_ratio_percent) +
          Number(this.order_startup.process_ratio_percent)
        if(total > 100){
          $dialog.confirm({
            theme: 'ios',
            title: '比例已超出100%,确认下达?',
            cancelText: '取消',
            okText: '确定'
          }).then((res) => {
            console.log('confirm result: ', res)
            if(res){
              const reqData={
                order_startup:this.order_startup
              }
              startup(reqData).then(res=>{
                this.$router.go(-1)
              }).catch(error=>{
                $toast.show(error.description)
              })
            }else{
              return
            }
          })
        }else{
          const reqData={
            order_startup:this.order_startup
          }
          startup(reqData).then(res=>{
            $toast.show('下达成功',600)
            this.$router.go(-1)
          }).catch(error=>{
            $toast.show(error.description)
          })
        }
      },
      checkPercent(type){
        switch (type){
          case 'design_ratio':
//            if(this.order_startup.design_ratio_percent>99){
//              this.order_startup.design_ratio_percent = Math.floor(this.order_startup.design_ratio_percent/10)
//            }else if(this.order_startup.design_ratio_percent.length>=5){
//              this.order_startup.design_ratio_percent = Number(this.order_startup.design_ratio_percent).toFixed(2)
//            }else if(this.order_startup.design_ratio_percent<0){
//              this.order_startup.design_ratio_percent = 1
//            }
            this.order_startup.design_ratio = this.order_startup.design_ratio_percent/100
            this.order_startup.design_cost = Number(this.order_startup.price * (this.order_startup.design_ratio)).toFixed(2)
            break;
          case 'process_ratio':
//            if(this.order_startup.process_ratio_percent>99){
//              this.order_startup.process_ratio_percent = Math.floor(this.order_startup.process_ratio_percent/10)
//            }else if(this.order_startup.process_ratio_percent.length>=5){
//              this.order_startup.process_ratio_percent = Number(this.order_startup.process_ratio_percent).toFixed(2)
//            }else if(this.order_startup.process_ratio_percent<0){
//              this.order_startup.process_ratio_percent = 1
//            }
            this.order_startup.process_ratio = this.order_startup.process_ratio_percent/100
            this.order_startup.process_cost = Number(this.order_startup.price * (this.order_startup.process_ratio)).toFixed(2)
            break;
          case 'stuff_ratio':
//            if(this.order_startup.stuff_ratio_percent>99){
//              this.order_startup.stuff_ratio_percent = Math.floor(this.order_startup.stuff_ratio_percent/10)
//            }else if(this.order_startup.stuff_ratio_percent.length>=5){
//              this.order_startup.stuff_ratio_percent = Number(this.order_startup.stuff_ratio_percent).toFixed(2)
//            }else if(this.order_startup.stuff_ratio_percent<0){
//              this.order_startup.stuff_ratio_percent = 1
//            }
            this.order_startup.stuff_ratio = this.order_startup.stuff_ratio_percent/100
            this.order_startup.stuff_cost = Number(this.order_startup.price * (this.order_startup.stuff_ratio)).toFixed(2)
            break
        }
//        this.resetPrice(type)
      },
      checkValue(type){
        switch (type){
          case 'design_cost':
//            if(this.order_startup.design_cost<0){
//              this.order_startup.design_cost = this.order_startup.up * 0.1
//            }else if(this.order_startup.design_cost>this.order_startup.up){
//              this.order_startup.design_cost = this.order_startup.up * 0.9
//            }
            this.order_startup.design_ratio = this.order_startup.design_cost/this.order_startup.price
            console.log(this.order_startup.design_ratio)
            this.order_startup.design_ratio_percent = Number(this.order_startup.design_ratio * 100).toFixed(2);
//            this.resetPrice('design_ratio')
            break;
          case 'process_cost':
//            if(this.order_startup.process_cost<0){
//              this.order_startup.process_cost = this.order_startup.up * 0.1
//            }else if(this.order_startup.process_cost>this.order_startup.price){
//              this.order_startup.process_cost = this.order_startup.price * 0.9
//            }
            this.order_startup.process_ratio = this.order_startup.process_cost/this.order_startup.price
            console.log(this.order_startup.design_ratio)
            this.order_startup.process_ratio_percent = Number(this.order_startup.process_ratio * 100).toFixed(2)
//            this.resetPrice('process_ratio')
            break;
          case 'stuff_cost':
//            if(this.order_startup.stuff_cost<0){
//              this.order_startup.stuff_cost = this.order_startup.price * 0.1
//            }else if(this.order_startup.stuff_cost>this.order_startup.price){
//              this.order_startup.stuff_cost = this.order_startup.price * 0.9
//            }
            this.order_startup.stuff_ratio = this.order_startup.stuff_cost/this.order_startup.price
            console.log(this.order_startup.stuff_ratio)
            this.order_startup.stuff_ratio_percent = Number(this.order_startup.stuff_ratio * 100).toFixed(2)
//            this.resetPrice('stuff_ratio')
            break
        }
      },
      resetPrice(type){
        let x = parseFloat(this.order_startup.design_ratio_percent),z = parseFloat(this.order_startup.process_ratio_percent) , y = parseFloat(this.order_startup.stuff_ratio_percent)
        console.log(x+'-'+y+'-'+z)
        switch (type){
          case 'design_ratio':{
            if(x+y+z>100){
              y = Math.floor((100-x)/2)
            }
            z = 100-x - y
            break
          }
          case 'stuff_ratio':{
            console.log(y)
            if(x+y+z>100){
              z = Math.floor((100-y)/2)
            }
            x = 100-y - z
            break
          }
          case 'process_ratio':{
            if(x+y+z>100){
              x = Math.floor((100-z)/2)
            }
            y = 100-x - z
            break
          }
        }
        console.log(x+'-'+y+'-'+z)
        this.order_startup.design_ratio_percent = Number(x).toFixed(2)
        this.order_startup.stuff_ratio_percent = Number(y).toFixed(2)
        this.order_startup.process_ratio_percent = Number(z).toFixed(2)
        this.order_startup.design_ratio = x/100
        this.order_startup.design_cost = Number(this.order_startup.price * (this.order_startup.design_ratio)).toFixed(2)
        this.order_startup.stuff_ratio = y/100
        this.order_startup.stuff_cost = Number(this.order_startup.price * (this.order_startup.stuff_ratio)).toFixed(2)
        this.order_startup.process_ratio = z/100
        this.order_startup.process_cost = Number(this.order_startup.price * (this.order_startup.process_ratio)).toFixed(2)

      },

    },
    mounted(){
      this.order_line_id = this.$route.params.order_line_id
      this.order_startup.order_line_id = this.$route.params.order_line_id
      queryLineDetail({order_line_id:this.order_line_id}).then(res=>{
        this.order_startup.up = res.up
        this.order_startup.count = res.count
        this.order_startup.price = res.up * res.count
      }).catch(error=>{

      })
    },
    watch:{
      'order_startup.sup_type':function (oldValue,newVale) {
        console.log(newVale)
        if(newVale==1){
          this.order_startup.is_crt_bom = '0'
        }else {
          this.order_startup.is_crt_bom = '1'
        }
      }
    }
  }
</script>

<style lang="scss" scoped>
  .divider-title{
    padding: 15px;
  }
  .input-percent,.input-value{
    width: 50px;
    display: inline;
    border-bottom: 1px solid;
    text-align: right;
  }

  .input-value{
    width: 40%;
    text-align: left;
  }
</style>

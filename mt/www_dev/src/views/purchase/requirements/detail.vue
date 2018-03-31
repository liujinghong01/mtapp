<template>
  <div class="mw-page page  purchase" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>物料详情</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content">
      <item>
        物料描述
        <span class="item-note">{{material.mat_desc}}</span>
      </item>
      <item>
        物料类型
        <span class="item-note">{{material.mat_type_name}}</span>
      </item>
      <item>
        规格型号
        <span class="item-note">{{material.mat_model}}</span>
      </item>
      <item>
        物料需求来源
        <span class="item-note">{{material.create_type_name}}</span>
      </item>
      <item v-if="!material.request_id">
        请购单号
        <span class="item-note">{{material.request_id}}</span>
      </item>
      <item v-if="!material.mat_no">
        模号
        <span class="item-note">{{material.mat_no}}</span>
      </item>
      <item>
        申请数量
        <span class="item-note">{{material.plan_qty}}</span>
      </item>
      <item>
        申请重量
        <span class="item-note">{{material.plan_weight}}</span>
      </item>
      <item>
        总需求量
        <span class="item-note">{{material.total_require_qty}}</span>
      </item>
      <item>
        在途量
        <span class="item-note">{{material.on_passage_qty}}</span>
      </item>
      <item>
        当前库存量
        <span class="item-note">{{material.curr_stock_qty}}</span>
      </item>
      <item>
        期望交货日期
        <span class="item-note">{{material.delivery_date}}</span>
      </item>
      <item>
        采购数量
        <span class="item-note">{{material.buy_qty}}</span>
      </item>
      <div class="item-divider" style="padding-left: 15px">备注</div>
      <textarea style="margin: 0" v-model="material.remark" disabled="disabled">
        </textarea>
    </div>
    <div class="mw-page-footer">
      <div class="btns">
        <div class="btn" @click="createPurchase">
          采购
        </div>
        <div class="divider"></div>
        <div class="btn" @click="inquiry">
          询价
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import { requirementDetail } from '@/api/purchase/requirement'
  import sess from '@/utils/sess'
  export default{
    name: 'requirementDetail',
    data(){
      return {
        require_id:'',
        material: {
          "material_id": '',
          "buy_qty": '',
          "coin": "",
          "create_type": '',
          "create_type_name": "",
          "deal_flag": '',
          "mat_desc": "",
          "mat_id": '',
          "mat_model": "",
          "mat_no": '',
          "mat_type_id": '',
          "mat_type_name": "",
          "node_pos": '',
          "plan_price": '',
          "plan_qty": '',
          "plan_weight": '',
          "project_prod_id": '',
          "remark": "",
          "request_id": '',
          "require_date": "",
          "require_id": '',
          "supply_type": '',
          "supply_type_name": "",
          "total_price": '',
          "total_require_qty": '',
          "unit": "",
          "on_passage_qty":'',
          "curr_stock_qty":''
      }

      }
    },
    methods: {
      //询价单
      inquiry(){
        sess.set('mats',JSON.stringify([this.material]))
        this.$router.forward('/purchase/inquiry/feedback/new/null')
      },

      //生成采购单
      createPurchase(){
        sess.set('mats',JSON.stringify([this.material]))
        this.$router.forward('/purchase/purchase/detail/new/null')
      },

    },
    mounted(){
      this.require_id = this.$route.params.require_id
      requirementDetail({
        query:{
          require_id:this.require_id
        }
      }).then(res=>{
        console.log(res)
        this.material = res.require_info
      }).catch(error=>{
        $toast.show(error.description)
      })
    }
  }
</script>

<style lang="scss" scoped>

</style>

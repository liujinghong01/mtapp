<template>
  <div class="mw-page page" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>零部件明细</span>
      <i class="right" ></i>
    </div>

    <div class="mw-page-content">
      <div class="previewBox">
        <div class="preview">
          <p>总计：{{cost}}元</p>
          <div class="previewItem" v-for="(item,index) in this.part_detail.sub_part_list">
            <p>零部件：{{ item.sub_part_name }}</p>
            <!--<p>材质：铜</p>-->
            <p>截面形状:{{ item.stuff_shape }}</p>
            <!--<p>数量：10</p>-->
            <!--<p>尺寸(mm)：159*256*186</p>-->
            <!--<p>单位：kg/L</p>-->
            <!--<p>单价：20元</p>-->
            <!--<p>重量:12KG</p>-->
            <p>价格：{{ item.stuff_up }}元</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import { partDetail } from '@/api/customerOrder/compute'
  export default{
    name: 'computemChilderMaterialPreview',
    components: {
    },
    data(){
      return {
        compute_id:'',
        part_detail:'',
        cost:''
      }
    },
    computed:{
    },
    mounted(){
      this.compute_id=this.$route.query.compute_id
      this.cost=this.$route.query.cost
      this.getData()
    },
    activated(){
    },
    methods: {
      getData(){
        const reqData={
          part_id:this.compute_id,
        }
        partDetail(reqData).then(res=>{
          this.part_detail = res
        }).catch(error=>{
          console.log(error.description)
          $toast.show(error.description)
        })
      },
    },
  }
</script>
<style lang="scss" scoped>
  .previewItem{
    width: 100%;
    box-sizing: border-box;
    border-bottom: 1px solid #ddd;
    padding: 8px 0;
    &:last-of-type{border-bottom: 0}
    &>p{
      line-height: 20px;
      margin: 0;
      font-size: 13px;
    }
  }
</style>

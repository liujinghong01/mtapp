<template>
  <div class="mw-page page" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>零部件材料</span>
      <i class="right" ></i>
    </div>

    <div class="mw-page-content">
      <div class="previewBox">
        <div class="preview">
          <p>总计：{{ cost }}元</p>
          <div class="previewItem hasArrow" v-for="(item,index) in part_list" @click="itemClick(item.part_id,item.part_cost)">
            <p>零部件：{{ item.part_name }}</p>
            <!--<p>材质：铜</p>-->
            <!--<p>重量:12KG</p>-->
            <!--<p>尺寸(mm)：159*256*186</p>-->
            <p>费用：{{ item.part_cost }}元</p>
            <i class="ion-ios-arrow-right"></i>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import { getPartList } from '@/api/customerOrder/compute'
  export default{
    name: 'computemMaterialListPreview',
    components: {
    },
    data(){
      return {
        compute_id:'',
        part_list:'',
        cost:''
      }
    },
    computed:{
    },
    mounted(){
      this.compute_id = this.$route.query.compute_id
      this.cost=this.$route.query.cost
      this.getPartList()
    },
    activated(){

    },
    methods: {
      getPartList(){
        const reqData={
          compute_id : this.compute_id
        }
        getPartList(reqData).then(res=>{
          this.part_list = res.part_list
        }).catch(error=>{
          console.log(error.description)
        })
      },
      itemClick(id,cost){
        this.$router.forward({path:'/customerOrder/computemChilderMaterialPreview',query:{compute_id:id,cost:cost}})
      }
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

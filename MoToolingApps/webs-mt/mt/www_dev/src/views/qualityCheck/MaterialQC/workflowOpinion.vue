<template>
    <div class="mw-page page " >
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>审批意见查看</span>
            <i class="right"  ></i>
        </div>
        <div class="mw-page-content"  >

        </div>



    </div>
</template>

<script>
  import CstItem from "../../../components/cstItem"
  import { workflowOpinion } from '@/api/MaterialQC'
    export default{
      name:'workflowOpinion',
      components: {CstItem},
      data(){
        return {
          bill_id:'',
          bill_type:'',
          process_list:[]
        }
      },
      mounted(){
        let param=this.$route.query
        this.bill_id=param.bill_id
        this.bill_type=param.bill_type
        this.getData()
      },
      methods: {
        getData(){
          let reqData = {
            bill_id:this.bill_id,
            bill_type:this.bill_type
          }
          workflowOpinion(reqData).then(res=>{

          }).catch(error=>{
            $toast.show(error.description)
          })
        },
      },
    }
</script>

<style lang="scss" scoped>

</style>

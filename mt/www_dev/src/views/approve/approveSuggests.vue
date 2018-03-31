<template>
    <div class="mw-page page approve" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>审批意见</span>
            <i class="right"></i>
        </div>
        <div class="mw-page-content">
          <item v-for="item in suggests">
            <p>审批状态 ： {{item.approve_status}}</p>
            <p>审批人 ： {{item.approve_man_name}}</p>
            <p>审批时间 ： {{item.approve_end_date}}</p>
            <p>审批意见 ： {{item.approve_sugg ? item.approve_sugg : '无'}}</p>
          </item>
        </div>
    </div>
</template>

<script>
  import { submitApprove,approveSuggests } from '@/api/approve'
    export default{
      name:'approveSuggests',
        data(){
          return {
            bill_id:'',
            bill_type:'',
            suggests:[

            ]
          }
        },
      mounted(){
        this.bill_id = this.$route.params.bill_id
        this.bill_type = this.$route.params.bill_type

        approveSuggests({
          bill_id:this.bill_id,
          bill_type:this.bill_type
        }).then(res=>{
          console.log(res)
          this.suggests = res.process_list
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      filters:{
        approveFlag(value){
          let flag = '待审'
          if (value == 1) {
            flag = '审批通过'
          }else if (value == 2){
            flag = '审批不通过'
          }
          return flag
        }
      }
    }
</script>

<style lang="scss" scoped>
  textarea{
    width: 100%;
    height: 80%;
    resize: none;
    padding: 15px;
  }
</style>

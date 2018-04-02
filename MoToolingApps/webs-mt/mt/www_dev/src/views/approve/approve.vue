<template>
    <div class="mw-page page approve" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
            <span>审批</span>
            <i class="right"></i>
        </div>
        <div class="mw-page-content">
          <textarea v-model="approve_sugg" placeholder="输入审批意见">

          </textarea>
        </div>
        <div class="mw-page-footer">
          <div class="btns">
            <div class="btn" @click="submit(2)">
                不同意
            </div>
            <div class="divider"></div>
            <div class="btn" @click="submit(1)">
               同意
            </div>
          </div>
        </div>
    </div>
</template>

<script>
  import { submitApprove } from '@/api/customerOrder/compute'
  import { mapGetters } from 'vuex'
    export default{
      name:'approveSuggestion',
        data(){
          return {
            approve_sugg:'',
            bill_id:'',
            bill_type:'',
            approve_step:'',
            approve_man_id:'',
            approve_man_name:'',
            approve_flag:''
          }
        },
      mounted(){
        this.bill_id = this.$route.params.bill_id
        this.bill_type = this.$route.params.bill_type
        this.approve_step = this.$route.params.approve_step
      },
      computed:{
        ...mapGetters(['userinfo'])
      },
        methods: {
          submit(flag){
            const reqData = {
              approve_sugg:this.approve_sugg,
              bill_id: this.bill_id,
              bill_type: this.bill_type,
              approve_step: this.approve_step,
              approve_man_id:this.userinfo.uid,
              approve_man_name:this.userinfo.fullname,
              approve_flag:flag
            }
            submitApprove(reqData).then(res=>{
              $toast.show('提交成功')
              history.go(-1)
            }).catch(error=>{
              $toast.show(error.description)
            })
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

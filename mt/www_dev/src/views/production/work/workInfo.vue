<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>加工信息</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content ">
      <div class="content" >
        <scroll class="page-content"  style="-webkit-overflow-scrolling: touch" >
          <item @click.native="itemClick(item)" :class="pop_status*1===0?'active':''" v-for="(item,index) in pop_list" style="padding-top: 46px;padding-bottom: 12px ">
            <div class="itemInfo" >
              <div>
                <p style="height: 18px;color: #416eb6">{{ item.mat_no }}</p>
                <p>
                  物料名称 : {{item.mat_name}}
                </p>
              </div>
            </div>
            <p>生产订单号 : {{ item.po_no }}</p>
            <p>计划开始时间 : {{ item.plan_start_time }}</p>
            <p>计划结束时间 : {{ item.plan_end_time }}</p>
            <p>实际开始时间 : {{ item.real_start_time }}</p>
            <p>任务工时 : {{ item.plan_work_time }}&nbsp;&nbsp;&nbsp;&nbsp;待加工数量:{{ item.proc_qty }}</p>
            <p>工序内容 : {{ item.proc_desc }}</p>
            <p>加工者 :
              <span style="margin-right: 5px" v-for="(e,i) in item.member_list">{{ e.member_name }}</span>
            </p>
          </item>

          <div  slot="infinite" class="text-center"></div>
        </scroll>
      </div>
    </div>

  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  import { popProcInfo} from '@/api/production/work'
  import SwipeItem from "../../../components/swipeItem"
  import sess from '@/utils/sess'
  export default{
    name:'taskList',
    components: {SwipeItem},
    data(){
      return {
        dep_id:'',
        resource_id:'',
        type:'',
        pop_list:[ ]
      }
    },
    computed:mapGetters([
      'chosenCompany',
      'userinfo'
    ]),
    activated(){
    },
    mounted(){
      const param=this.$route.query
      this.pg_id=param.pg_id
      this.resource_id=param.resource_id
      this.type=param.type
      this.popProcInfo()
      // this.waitProcList(this.pg_id,this.resource_id,this.type,this.pop_status)
    },
    methods: {
      popProcInfo(){
        const  requestData={
          query:{
            pg_id:this.pg_id,//	生产小组Id	number
            resource_id:this.resource_id,//	资源Id	string	@mock=
            type:this.type,//
          }
        }
        popProcInfo(requestData).then(res => {
            this.pop_list=res.pop_list
        }).catch(error => {
          $toast.show(error.description)
        })
      },
      itemClick(item){
        if(this.pop_status*1===0){
          if(item.check==undefined){
            this.$set(item,'check',true)
          }else{
            item.check=!item.check
          }
        }
      }

    }
  }
</script>

<style lang="scss" scoped>
  .itemInfo{
    width: 100%;
    height: 42px;
    position: absolute;
    right: 0;
    top: 0;
    padding: 12px 15px 0px 15px;
    line-height: 20px;
    &>div>p{
      margin-bottom: 0;
      height: 14px;
      line-height: 14px;
    }
    span{
      font-size: 12px;
      color: #999;
      height: 12px;
      line-height: 12px;


    }
  }


</style>

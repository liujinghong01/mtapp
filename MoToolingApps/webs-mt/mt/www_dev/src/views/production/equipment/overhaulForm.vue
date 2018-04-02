<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>检修单</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content ">
      <div class="content" >
          <cst-item label="本次检修开始时间"  v-model="device_maintain_info.start_date.substring(0,10)"  action="action" ></cst-item>
          <cst-item label="检修周期(年)" v-model="device_maintain_info.cycle"   action="action" ></cst-item>

          <cst-item label="下次计划检修开始时间" v-model="device_maintain_info.next_start_date.substring(0,10)"   action="action" ></cst-item>
          <cst-item label="下次计划检修结束时间" v-model="device_maintain_info.next_end_date.substring(0,10)"   action="action" ></cst-item>
          <div class="reason">
            <p>检修原因</p>
            <textarea placeholder="请输入维修原因" v-model="reason"></textarea>
          </div>
      </div>
    </div>
    <div class="mw-page-footer">
      <div class="btns">
        <div class="btn" @click="deviceMaintainBill()" >
          保存
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import { deviceMaintainInfo,deviceMaintainBill } from '@/api/production/equipment'
  import CstItem from "../../../components/cstItem"
  export default{
    name:'overhaulForm',
    components: { CstItem },
    data(){
      return {
        action:'',
        device_id:'',
        reason:'',
        device_maintain_info:{//device_maintain_info.start_date
          cycle:'1',
          device_id:'设备ID',
          device_name:'	设备名称',
          end_date:'	实际检修结束时间',
          is_maintained:'是否检修完毕',
          length:'检修时长(天)',
          maintain_id:'	检修Id',
          maintain_no:'	检修单号',
          next_end_date:'下次计划检修结束时间',
          next_start_date:'下次计划检修开始时间',
          prev_end_date:'	上次检修完毕时间',
          reason:'	检修原因',
          start_date:'实际检修开始时间',
        }
      }
    },
    computed:{

    },
    activated(){
    },
    mounted(){
      const param=this.$route.query
      this.action=param.action
      if(this.action=='new'){
        this.device_id=param.device_id
        this.deviceMaintainInfo()
      }

    },

    methods: {
      onTabClick(index) {
        if(index===this.tabIndex) {
          return
        }
        this.tabIndex = index
      },
      deviceMaintainInfo(){
        const requestData = {
          query:{
            device_id:this.device_id
          }
        }
        deviceMaintainInfo(requestData).then(res=>{
          this.device_maintain_info=res.device_maintain_info
        }).catch(error=>{
          $toast.show(error.description)
        })
      },
      deviceMaintainBill(){
        this.device_maintain_info.reason=this.reason
        alert(this.device_maintain_info.reason)
        const requestData = {
          device_maintain_info:this.device_maintain_info
        }
        deviceMaintainBill(requestData).then(res=>{
          $toast.show('设备检修完成')
          $router.go(-1)
        }).catch(error=>{
          $toast.show(error.description)
        })
      }

    }
  }
</script>

<style lang="scss" scoped>

  .item>div{line-height: 26px}
  .reason{
    background: #fff;
    padding: 15px;
    border-top: 1px solid #ddd;
    textarea{
      width: 100%;
      resize: none;
      height: 50px;
    }
  }
</style>

<template>
  <div class="mw-page page storage" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>设备点检</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content has-tab">
      <div class="content" >
        <scroll class="page-content"  style="-webkit-overflow-scrolling: touch">
          <cst-item label="设备名称"  v-model="device_info.device_name"   ></cst-item>
          <cst-item label="设备型号" v-model="device_info.device_model"   ></cst-item>
          <cst-item label="设备编码" v-model="device_info.device_no"   ></cst-item>
          <cst-item label="设备品牌"  v-model="device_info.device_brand"   ></cst-item>
          <cst-item label="设备所在地址"  v-model="device_info.device_addr"  ></cst-item>
          <cst-item  label="生产日期" v-model="device_info.prod_date"  action="check" ></cst-item>
          <item  >
            <span>设备是否共享</span>
            <label><input name="isShare"  disabled type="radio" value="1" v-model="device_info.is_share"/> 是</label>
            <label><input name="isShare"  disabled type="radio" value="0" v-model="device_info.is_share"/> 否</label>
          </item>
          <cst-item style="margin-top: 1px" label="使用年限(年）" v-model="device_info.work_life"  ></cst-item>
          <cst-item label="状态" v-show="action === 'check' " v-model="device_info.device_status"  ></cst-item>
          <item>
            备注：
            <textarea v-model="remark" style="resize: none;width: 100%;line-height: 20px;height: 65px" placeholder="请输入点检备注"></textarea>
          </item>
        </scroll>
      </div>

    </div>
    <div class="mw-page-footer">
      <div class="btns">
        <div class="btn" @click="pgDeviceCheck">
          点检
        </div>
      </div>
    </div>
  </div>

</template>

<script>
  import { deviceDetail,pgDeviceCheck } from '@/api/production/equipment'
  import CstItem from "../../../components/cstItem"
  export default{
    name:'checkDetail',
    components: { CstItem },
    data(){
      return {
        remark:'',
        parent_id:'',
        device_id:'',
        device_info:{//device_info.device_status
          device_addr:'',
          device_brand:''	,
          device_id:''	,
          device_model:''	,
          device_name:''	,
          device_no:'',
          device_pic:'',
          device_status:0,
          device_status_name:'',
          is_auto_pause:'',
          is_auto_prod	:'',
          is_share:1,
          labour_ratio:null,
          last_maintain_reason:'',
          pg_id	:'',
          pg_name	:'',
          prod_date:'',
          work_life:'',
          check_allocate_list:[
            {check_uid:'',check_uid_name:'11'}
          ]

        },
      }
    },
    computed:{

    },
    activated(){
    },
    mounted(){
      const param=this.$route.query
      this.device_id=param.device_id
      this.deviceDetail()

    },
    methods: {
      deviceDetail(){//获取设备详情
        const requestData = {
          query:{
            device_id:this.device_id
          }
        }
        deviceDetail(requestData).then(res=>{
          this.device_info=res.device_info
        }).catch(error=>{
          $toast.show(error.description)
        })
      },//点检
      pgDeviceCheck(){
        const requestData = {
          device_check_info:{
            device_id:this.device_id,
            remark:this.remark
          }
        }
        pgDeviceCheck(requestData).then(res=>{
          $router.go(-1)
          $toast.show('点检完成')
        }).catch(error=>{
          $toast.show(error.description)
        })
      }

    }
  }
</script>

<style lang="scss" scoped>

  .item>div{line-height: 22px}
</style>

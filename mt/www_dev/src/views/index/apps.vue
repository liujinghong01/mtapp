<template>
    <div class="mw-page page" v-nav="{hideNavbar:true}">
        <div class="mw-page-header" >
            <von-header>
                <button class="button button-icon ion-ios-arrow-back" slot="left" @click="$router.go(-1)"></button>
                <span slot="title">应用列表</span>
                <button v-if="action==='check'" class="button button-icon" slot="right" @click="action='edit'">编辑</button>
                <button v-if="action==='edit'" class="button button-icon" slot="right" @click="ok()">完成</button>
            </von-header>
        </div>
        <div class="mw-page-content" style="display: flex;flex-direction: column">
          <div class="my-apps" v-if="action==='check'" >
            <span >我的应用</span>
            <div class="my-apps-icons">
              <i v-for="item in myApps" :class="['icon', 'iconfont',item.icon ]"></i>
              <i class="icon ion-android-add"></i>
            </div>
          </div>
          <div class="modules" v-show="action==='edit'">
            <p style="margin: 5px">我的应用 <span style="font-size: 12px;opacity: .5">(首页最多放7个应用)</span></p>
            <ul>
              <li v-for="item in myApps">
                <i :class="['icon' ,'iconfont', item.icon]"></i>
                <p>{{item.name}}</p>
                <span class="badge" @click.prevent="removeMyApps(item)"><i class="icon ion-android-remove-circle"></i></span>
              </li>
            </ul>
          </div>
          <div id="scrollBox" style="flex: 1;overflow-y: auto;overflow-x: hidden">
            <div class="modules" v-for="item in apps">
              <p style="margin: 5px">{{item.name}}</p>
              <ul>
                <li  v-for="app in item.apps" @click.prevent="navigateTo(app)" :class="{'edit':action==='edit', 'active': isActive(item)}">
                  <i :class="['icon','iconfont',app.icon]"></i>
                  <p>{{app.name}}</p>
                  <span class="badge" @click.prevent="addApp(app)" v-show="action==='edit'&&!isChecked(app)"><i class="icon ion-android-add-circle add"></i></span>
                  <span class="badge"  v-show="action==='edit'&&isChecked(app)"><i class="icon ion-checkmark-circled checked"></i></span>
                </li>
              </ul>
            </div>
          </div>
        </div>
    </div>
</template>

<script>
  import storage from '@/utils/storage'
  import { getPermission } from '@/api/company'
  import sess from '@/utils/sess'
    export default{
      name:'apps',
      data(){
        return {
          action:'check',
          scroll_top:'',
          isAdmin: false,
          myApps:[
            // name： 显示名称  icon： 显示图标   path：跳转路由   identity：该功能对应的后台资源      hasPermission：当前用户是否有权限
            {name:'报价核算单','icon':'icon-hesuandan','path':'/customerOrder/compute', identity:'compute',hasPermission:false},
            {name:'报价单','icon':'icon-baojiadan','path':'/customerOrder/quote/list', identity:'quote',hasPermission:false},
            {name:'客户定单','icon':'icon-kehudingdan','path':'/customerOrder/order/list',  identity:'cst_order',hasPermission:false},
          ],
          apps:[
            {
              name:'客户定单',
              apps:[
                {name:'报价核算单','icon':'icon-hesuandan','path':'/customerOrder/compute',  identity:'compute',hasPermission:false},
                {name:'报价单','icon':'icon-baojiadan','path':'/customerOrder/quote/list',  identity:'quote',hasPermission:false},
                {name:'客户定单','icon':'icon-kehudingdan','path':'/customerOrder/order/list', identity:'cst_order',hasPermission:false},
                {name:'定单管理','icon':'icon-hesuandan','path':'/customerOrder/manage/list', identity:'order_operate',hasPermission:false},
              ]
            },
            {
              name:'物料管理',
              apps:[
                {name:'BOM列表','icon':'ion-ios-list-outline','path':'/mats/bom/list', identity: 'bm',hasPermission:false},
              ]
            },
            {
              name:'采购管理',
              apps:[
                {name:'请购单','icon':'icon-hesuandan','path':'/purchase/requisitions', identity: 'request_order' ,hasPermission:false},
                {name:'询价单','icon':'icon-hesuandan','path':'/purchase/inquiry', identity: 'inquiry_deal',hasPermission:false},
                {name:'采购单','icon':'icon-hesuandan','path':'/purchase/purchase', identity: 'purch_order',hasPermission:false},
                {name:'物料需求计划','icon':'icon-hesuandan','path':'/purchase/requirements', identity: 'mat_require_plan',hasPermission:false},
//                {name:'销售定单','icon':'icon-hesuandan','path':'/purchase/sales'},
              ]
            },
            {
              name:'仓库管理',
              apps:[
                {name:'基础数据','icon':'icon-hesuandan','path':'/storage/baseInfo', identity: 'iv_base_data',hasPermission:false},
                {name:'领料单','icon':'icon-hesuandan','path':'/storage/applyMatBill/list', identity: 'apply_mat_bill',hasPermission:false},
                {name:'仓库盘点','icon':'icon-hesuandan','path':'/storage/inventory/list', identity: 'store_check_bill',hasPermission:false},
                {name:'入库单','icon':'icon-hesuandan','path':'/storage/storagePush/list', identity: 'in_store',hasPermission:false},
                {name:'出库单','icon':'icon-hesuandan','path':'/storage/storagePull/list', identity: 'out_store',hasPermission:false},
                {name:'物料报废','icon':'icon-hesuandan','path':'/storage/scrap/list', identity: 'mat_require_plan',hasPermission:false},
                {name:'库存物料管理','icon':'icon-hesuandan','path':'/storage/management/new/new', identity: 'mat_info',hasPermission:false},
                {name:'凭证管理','icon':'icon-hesuandan','path':'/storage/voucher/list', identity: 'voucher_list',hasPermission:false},
              ]
            },
	    {
              name:'生产管理',
              apps:[
                {name:'工作日历','icon':'ion-calendar','path':'/production/shopCalendar', identity: 'set_time_calendar',hasPermission:false},
                {name:'设备点检','icon':'icon-hesuandan','path':'/production/check', identity: 'device',hasPermission:false},
                {name:'基础工序','icon':'icon-hesuandan','path':'/production/procedure', identity: 'working_procedure',hasPermission:false},
                {name:'工艺模板','icon':'ion-ios-paper-outline','path':'/production/procTemplate', identity: 'technology_template',hasPermission:false},
                {name:'工艺路线','icon':'ion-map','path':'/production/processRoute', identity: 'routings',hasPermission:false},
                {name:'排产','icon':'ion-link','path':'/production/schedulList', identity: 'aps',hasPermission:false},
                {name:'任务领取','icon':'ion-clipboard','path':'/production/getTask', identity: 'task_manager',hasPermission:false},
                {name:'小组报工','icon':'icon-hesuandan','path':'/production/work', identity: 'job_booking',hasPermission:false},
                {name:'模具领料','icon':'icon-hesuandan','path':'/production/MaterialRequisition', identity: 'mould_apply_mat_bill',hasPermission:false},
              ]
            },
            {
              name:'质检管理',
              apps:[
                {name:'来料质检','icon':'ion-calendar','path':'/qualityCheck/MaterialQC', identity: 'qm',hasPermission:false},
                {name:'工序质检','icon':'icon-hesuandan','path':'/qualityCheck/ProcedureQC', identity: 'pop_qc',hasPermission:false},
              ]
            },
//            {
//              name:'采购管理',equipment
//              apps:[
//                {name:'采购管理','icon':'icon-hesuandan','path':'/customerOrder/compute'},
//                {name:'采购管理1','icon':'icon-hesuandan','path':'/customerOrder/compute'},
//                {name:'采购管理2','icon':'icon-hesuandan','path':'/customerOrder/compute'},
//                {name:'采购管理3','icon':'icon-hesuandan','path':'/customerOrder/compute'},
//                {name:'采购管理4','icon':'icon-hesuandan','path':'/customerOrder/compute'},
//              ]
//            },

          ]
        }
      },
      methods: {
        isChecked(item){
         for(let i in this.myApps){
           if(this.myApps[i].name===item.name){
               return true
           }
         }
          return false
        },
        removeMyApps(item){
          if(this.myApps.length>1){
            this.myApps.splice(this.myApps.indexOf(item),1)
          }else{
            $toast.show('最少保留一个应用',600)
          }

        },
        addApp(item){
          if(this.myApps.length===7){
            $toast.show('首页最多能放7个应用')
            return
          }
          this.myApps.push(item)
        },
        navigateTo(item){
          if(this.action==='edit'){
              return
          }
          if (!this.isActive(item)){
            $toast.show('权限不足，请联系管理员',2000)
            return
          }
          let scroll_top=document.getElementById('scrollBox').scrollTop
          sess.set('scroll_top',scroll_top)
          this.$router.forward(item.path)
        },
        ok(){
          this.action = 'check'
          storage.remove('myApps')
          storage.set('myApps',JSON.stringify(this.myApps))
        },
        //获取用户权限
        getUserPermission(){
          getPermission({}).then(res => {
            console.log(res)
            console.log(this.apps)
            //如果不是企业admin
            if (!this.isEnterpriseAdmin(res.role)){
              if (res.permission && res.permission.length){
                res.permission.forEach(permission => {
                  this.filterPermission(permission,this.apps)
                })
              }
            }
          }).catch( err => {

          })
        },

        //设置用户权限
        filterPermission(permission,apps){
          apps.forEach( app => {
            app.apps.forEach( ap => {
              if (permission.identity === ap.identity){
                ap.hasPermission = true
                if (permission.child_module && permission.child_module.length){
                  permission.child_module.forEach( child =>{
                    this.filterPermission(child,apps)
                  })
                }
              }
            })
          })
        },
        //是否是企业管理员
        isEnterpriseAdmin(roles){
          if (roles && roles.length){
            roles.forEach(o => {
              if (o.code === 'enterprise_admin'){
                this.isAdmin = true
                return true
              }
            })
          }
          return false
        },
        isActive(item){
          console.log(item.hasPermission || this.isAdmin)
          return item.hasPermission || this.isAdmin
        }
      },
      beforeMount(){

      },
      created(){
        this.getUserPermission()
        setTimeout(function(){
          console.log(sess.get('scroll_top'))
          if(sess.get('scroll_top')!==null){
            document.getElementById('scrollBox').scrollTop=sess.get('scroll_top')
            sess.remove('scroll_top')
          }
        },100)
        if(storage.get('myApps')){
          this.myApps = JSON.parse(storage.get('myApps'))
        }
      },

    }
</script>

<style lang="scss" scoped>



.my-apps{
  height: 50px;background-color: white;padding: 10px;display: flex;
  span{
    line-height: 30px;
  }
  .my-apps-icons{
    line-height: 30px;flex: 1;padding-left: 20px;
    i{
      margin-right: 10px;color: #416eb6;font-size: 16px;
    }
  }
}
.modules{
  background-color: white;
  margin-top: 10px;
  padding: 10px;
  ul{
    display: flex;
    flex-wrap: wrap;
    li{
      text-align: center;
      flex: 0 0 25%;
      height: 80px;
      padding-top: 20px;
      position: relative;
      border: 4px solid white;
      opacity: .4;
      &.edit{
        background-color: #f3f7fb;
      }
      &.active{
        opacity: 1;
      }

      i{
        color: #416eb6;
        font-size: 2em;
        width: 80px;
        height: 55px;
        position: absolute;
        top: 0;
        left: 50%;
        margin-left: -40px;
        line-height: 55px;
      }
      p{
        width: 100%;
        font-size: 12px;
        padding-top: 5px;
        position: absolute;
        top: 40px;
        text-align: center;
      }
      .badge{
        position: absolute;
        right: 0;
        top: 0;
        .icon{
          font-size: 16px;
          color: red;
        }
        .add{
          color: #416eb6;
        }
        .checked{
          color: #ababab;
        }
      }
    }
  }
}


</style>

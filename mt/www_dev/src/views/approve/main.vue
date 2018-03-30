<template>
    <div class="mw-page page" v-nav="{hideNavbar:true}">
        <div class="mw-page-header">
            <von-header>
                <button class="button button-icon ion-ios-arrow-back" slot="left" @click="$router.go(-1)"></button>
                <span slot="title">审批</span>
            </von-header>
        </div>
        <div class="mw-page-content has-tab">
          <div style="flex: 1;overflow-y: auto">
            <div class="modules" v-for="item in apps">
              <p style="margin: 5px">{{item.name}}</p>
              <ul>
                <li :class="{edit:action==='edit'}" v-for="app in item.apps" @click.prevent="navigateTo(app)">
                  <i :class="['icon','iconfont',app.icon]"></i>
                  <p>{{app.name}}</p>
                  <span class="badge tip"></span>
                </li>
              </ul>
            </div>
          </div>
        </div>
    </div>
</template>

<script>
  import storage from '@/utils/storage'
    export default{
      name:'approveMain',
      data(){
        return {
          action:'check',
          apps:[
            {
              name:'客户定单',
              apps:[
                {name:'报价核算单','icon':'icon-hesuandan','path':'/approve/computes'},
                {name:'报价单','icon':'icon-baojiadan','path':'/approve/quotes'},
                {name:'客户定单','icon':'icon-kehudingdan','path':'/approve/orders'},
//                {name:'定单管理','icon':'icon-hesuandan','path':'/customerOrder/manage/list'},
              ]
            },
            {
              name:'BOM',
              apps:[
                {name:'BOM','icon':'icon-hesuandan','path':'/approve/boms'},
              ]
            },
            {
              name:'采购管理',
              apps:[
                {name:'请购单','icon':'icon-hesuandan','path':'/approve/requisitions'},
                {name:'采购单','icon':'icon-hesuandan','path':'/approve/purchases'},
              ]
            },
            {
              name:'质检单审批',
              apps:[
                {name:'来料质检','icon':'icon-hesuandan','path':'/approve/approveMaterialQC'},
                {name:'工序质检','icon':'icon-hesuandan','path':'/approve/approveProcedureQC'},
              ]
            },
          ]
        }
      },
      methods: {
        navigateTo(item){
          if(this.action==='edit'){
              return
          }
          this.$router.forward(item.path)
        },

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
      &.edit{
        background-color: #f3f7fb;
      }

      i{
        color: #416eb6;
        font-size: 2em;
      }
      p{
        font-size: 12px;
        padding-top: 5px;
      }
      .badge{
        display: block;
        right: 22px;
        top: 8px;
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

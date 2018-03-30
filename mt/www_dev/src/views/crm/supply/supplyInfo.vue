<template>
  <div class="mw-page page crm" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>供应商信息</span>
      <i class="right" @click="action='edit'"  v-if="action==='check'">管理</i>
      <i class="right" @click="cancel"  v-else-if="action==='edit'">取消</i>
      <i class="right" v-else></i>
    </div>
    <div class="mw-page-content">
      <list>
        <item class="item-icon-right">
          <img src="../../../../static/images/company/logo_company.png" class="item-img">
          <span class="item-text item-text-top">{{info.sup_company_name}}</span><br>
          <!--<span class="item-text item-text-bottom">认证图片</span>-->
        </item>
      </list>

      <list>
        <cst-item label="企业全称" v-if="action === 'check'" v-model="info.sup_company_name"></cst-item>
        <cst-item label="企业全称" v-else v-model="info_edit.sup_company_name" :action="action" ></cst-item>
        <cst-item label="所属行业" v-if="action === 'check'" v-model="info.industry" ></cst-item>
        <cst-item label="所属行业" v-else v-model="info_edit.industry" :action="action" ></cst-item>
        <cst-item label="办公地址" v-if="action === 'check'" v-model="info.sup_company_address" ></cst-item>
        <cst-item label="办公地址" v-else v-model="info_edit.sup_company_address" :action="action" ></cst-item>
        <cst-item label="接口联系人" v-if="action === 'check'" v-model="info.contact_man" ></cst-item>
        <cst-item label="接口联系人" v-else v-model="info_edit.contact_man" :action="action" ></cst-item>
        <cst-item label="联系邮箱" v-if="action === 'check'" v-model="info.email" ></cst-item>
        <cst-item label="联系邮箱" v-else v-model="info_edit.email" :action="action" ></cst-item>
        <!--<item class="item-icon-right">-->
          <!--信誉等级-->
          <!--<span class="item-note" style="color: #416eb6;">-->
              <!--<i class="ion-ios-star" v-for="i in info.grade"></i>-->
          <!--</span>-->
        <!--</item>-->
      </list>
    </div>
    <div class="mw-page-footer" v-show="action === 'edit'">
      <div class="btns">
        <div class="btn" @click="saveData">
          保存
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import { supInfoView,supInfoUpdate } from '@/api/supply';
  import { mapGetters } from 'vuex'
  import CstItem from "../../../components/cstItem";
  export default {
    components: {CstItem},
    name:'supplyInfo',
    data(){
      return {
        info:{},
        action:'check',
        info_edit:{}
      }
    },
    computed:mapGetters([
      'token',
      'uid'
    ]),
    mounted(){
      this.getSupInfo()
    },
    methods:{
      getSupInfo(){
        let data = {
          sup_company_id:this.$route.query.id
        }
        supInfoView(data)
          .then(res => {
            this.info = res;
            this.info_edit = JSON.parse(JSON.stringify(this.info))
            console.log(res);
          })
          .catch(err => {
            console.log(err);
          })
      },
      cancel(){
        //取消按钮
        if (_.isEqual(this.info,this.info_edit)) {
          this.action = 'check'
        }else {
          $dialog.confirm({
            theme:'ios',
            okText:'确定',
            cancelText:'取消',
            title:'提示',
            content:'有未保存的内容，确定要取消吗？'
          }).then(res=>{
            if (res) {
              this.info_edit = JSON.parse(JSON.stringify(this.info))
              this.action = 'check'
            }
          })
        }
      },
      //保存用户信息
      saveData(){
        if (_.isEqual(this.info,this.info_edit)){
          this.action = 'check'
          return
        }
        const reqData = this.info_edit
        supInfoUpdate(reqData).then(res=>{
          console.log(res)
          this.action = 'check'
          this.getSupInfo()
        }).catch(error=>{
          $toast.show(error.description)
        })
      }
    }
  }
</script>
<style scoped lang="scss">
  .item-img{
    width: 50px;
    height: 50px;
    float:left;
  }
  .item-text{
    line-height: 25px;
    margin-left: 8px
  }
  .item-text-top{
    font-size: 16px;
  }
  .item-text-bottom{
    font-size: 12px;
    color: #416eb6;
  }
</style>

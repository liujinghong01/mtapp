<template>
  <div class="mw-page page" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
        <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
        <span>我的企业</span>
        <i class="right" @click="action = 'edit'" v-show="action === 'check'">编辑</i>
        <i class="right" @click="cancel" v-show="action === 'edit'">取消</i>
    </div>
    <div class="mw-page-content">
      <div style="height: 100px;background-color: white">
        <div style="padding: 20px 15px;display: flex">
          <div style="width: 60px;height: 60px;background-color: #416eb6;border-radius: 50%;"></div>
          <div style="flex: 1;padding: 8px 18px">
            <div class="company-name">{{chosenCompany.company_name}}</div>
            <div class="licence">未认证    <span style="opacity: .6;zoom: .3;margin-left: 10px" @click.stop="">立即认证</span></div>
          </div>
        </div>
      </div>
      <div class="item item-divider">基本信息</div>
      <cst-item label="公司全称" v-if="action === 'check'" v-model="companyInfo.company_name"></cst-item>
      <cst-item label="公司全称" v-else v-model="companyInfo_edit.company_name" :canEdit="true" :action="action"></cst-item>
      <!-- <cst-item label="所属行业" v-model="companyInfo_edit.industry" :canEdit="true" :action="action"></cst-item> -->
      <cst-item label="所属行业" v-model="companyInfo_edit.biz_des" :canEdit="true" :action="action"></cst-item>
      <cst-item label="联系邮箱" v-model="companyInfo_edit.email" :canEdit="true" :action="action"></cst-item>
      <cst-item label="企业认证" v-model="companyInfo.licence" ></cst-item>
      <cst-item label="管理员设置"></cst-item>
      <cst-item label="所在地区" v-model="companyInfo_edit.country"  :canEdit="true" :action="action"></cst-item>
    </div>
    <div class="mw-page-footer" v-show="action === 'edit'">
        <div class="btns" @click="saveData">
          <div class="btn">
              保存
          </div>
        </div>
    </div>
  </div>
</template>

<script>
  import {mapGetters} from 'vuex'
  import { getCompanyProfile } from '@/api/company'
  import { companyProfile } from '@/api/company'
  import CstItem from "../../components/cstItem"
  import _ from 'lodash'
    export default{
      components: {CstItem},
      name:'companyDetail',
      data(){
        return {
          action:'check',
          companyInfo:{},
          companyInfo_edit:{}
        }
      },
    computed:{
      ...mapGetters(['chosenCompany'])
    },
      mounted(){

      },
      activated(){
        this.getCompanyInfo()
      },
      methods: {
        onMenuClick(){

        },
        getCompanyInfo(){
          //获取公司基本信息
          getCompanyProfile({}).then(res=>{
            console.log(res)
            this.companyInfo = res
            this.companyInfo_edit = JSON.parse(JSON.stringify(this.companyInfo))
          }).catch(error=>{
            $toast.show(error.description)
          })
        },
        //保存数据
        saveData(){
        //alert("OK");
          if (_.isEqual(this.companyInfo,this.companyInfo_edit)) {
            this.action = 'check'
            $toast.show('没有修改，不用保存！', 600)
          } else {
            companyProfile(this.companyInfo_edit).then(res => {
              console.log(res)
              this.action = 'check'
              $toast.show('保存成功', 600)
            }).catch(error => {
              $toast.show(error.description)
            })
          }
        },
        cancel(){
          //取消按钮
          if (_.isEqual(this.companyInfo,this.companyInfo_edit)) {
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
                this.companyInfo_edit = JSON.parse(JSON.stringify(this.companyInfo))
                this.action = 'check'
              }
            })
          }
        }
      }
    }
</script>

<style lang="scss" scoped>
  .company-name{
    font-size: 20px;
  }
  .licence{
    margin-top: 5px;
  }
</style>

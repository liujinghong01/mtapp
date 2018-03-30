<template>
  <div class="page has-navbar"
       v-nav="{
            title:'请选择客户',
            showMenuButton:true,
            menuButtonText:'取消',
            onMenuButtonClick:menuClick}">
    <div class="page-content">
      <von-checkbox :options="customList" v-model="thisList" theme="positive"></von-checkbox>
      <div class="div-bottom">
        <div class="selected-label" v-for="item in thisList" :key="item.company_id">{{customList[item].substr(0,3)}}...</div>
        <md-button
          class="md-button button button-positive btn-right"
          @click.native="confirmDel">
          确认
        </md-button>
      </div>
    </div>
  </div>
</template>
<script>
  import { cstSupDelete } from '@/api/supply';
  import { mapGetters } from 'vuex'
  export default {
    data(){
      return {
        thisList: [],
        customList: [],
        listIds:[]
      }
    },
    computed:mapGetters([
      'token',
      'uid',
      'chosenCompany'
    ]),
    created(){
      let customList = JSON.parse(window.sessionStorage.getItem('customList'));
      for(var i in customList){
        this.customList[i] = customList[i].cst_company_name;
        this.listIds[i] = customList[i].cst_company_id;
      }
      //this.supplyList;
    },
    methods:{
      menuClick(){
        this.$router.go(-1);
      },
      confirmDel(){
        let reqIds = [];
        for(var i in this.thisList){
          reqIds[i] = parseInt(this.listIds[i]);
        }
        let data = {};
        data.client_type = 2;
        data.company_id = this.chosenCompany.company_id;
        data.uid = this.uid;
        data.token = this.token;
        data.cst_sup_typ = 1;
        data.cst_sup_id = reqIds;
        console.log(data);
        cstSupDelete(data)
          .then(res => {
            $toast.show('删除成功!', 1000)
            this.$router.go(-1);
          })
          .catch(err => {
            console.log(err);
          })
      }
    }
  }
</script>
<style scoped lang="scss">
  .div-bottom{
    position: fixed;
    bottom: 0;
    height: 10%;
    width: 100%;
    padding: 10px 5px;
    display: flex;
  }
  .selected-label{
    width: 80px;
    height: 30px;
    border-radius: 40px;
    border:1px solid #416eb6;
    color: #416eb6;
    margin-right: 3px;
    line-height: 30px;
    text-align: center;
  }
  .div-bottom .btn-right{
    width: 80px;
    position: absolute;
    right: 5px;
  }
</style>

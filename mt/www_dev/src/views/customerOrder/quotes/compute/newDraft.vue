<template>
  <div class="mw-page page" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>新增核算单</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content has-tab">
        <div style="position:relative;">
          <von-input type="text" v-model="currCustomer.cst_company_name" placeholder="请输入客户名称" label="客户名称"></von-input>
          <ul class="searchList" v-show="searchList.length>0">
            <li v-for="item in searchList" :key="item.cst_company_id" @click="setCustomer(item)">{{item.cst_company_name}}</li>

          </ul>
        </div>
        <div class="prod-type">
          <p class="title">产品类型</p>
          <ul class="types">
            <li :class="['type',{active:item.chosen}]" v-for="item in types" :key="item.code" @click="chooseType(item)">
              {{item.name}}
            </li>
          </ul>
        </div>
        <div class="compute-history">
          <p class="title">历史核算单</p>
          <list class="history">
            <item v-for="item in compute_history">
              <span>客户:{{item.cst_company_name}}</span> <span>类型:{{item.type_name}}</span>
              <span>编号:{{item.compute_no}}</span> <span>核算日期:{{item.created_at}}</span>
              <a class="action" @click.stop.prevent="associateCompute(item)">参考</a>
            </item>
          </list>
        </div>

      </div>
    <div class="mw-page-footer">
      <div class="btns">
        <div class="btn" @click="ok">确定</div>
      </div>
    </div>
  </div>
</template>
<script>
  import {mapGetters} from 'vuex'
  import _ from 'lodash'
  import {cstSupSearch} from '@/api/supply'
  import {list, associate} from '@/api/customerOrder/compute'
  export default{
    name: 'computeDraft',
    data(){
      return {
        searchList: [],
        currCustomer: {
          cst_company_name: ''
        },
        compute_history: [],
        types: [
          {name: '冲压模', code: '1'},
          {name: '压铸模', code: '2'},
          {name: '注塑模', code: '3'},
        ],
        chosenType: {}
      }
    },
    computed: {
      ...mapGetters(['chosenCompany']),

    },
    methods: {
      setCustomer(item){
        this.currCustomer = item
        this.searchList.length = 0;
        this.getHistory()
      },
      chooseType(item){
        if (item.chosen) {
          this.$set(item, 'chosen', false)
          return
        }
        if (this.chosenType.name) {
          this.chosenType.chosen = false
        }
        this.chosenType = item;
        this.$set(item, 'chosen', true)
        this.getHistory();
      },
      matchCustomer: _.debounce(
        function () {
          let data = {};
          data.cst_sup_name = this.currCustomer.cst_company_name;
          data.cst_sup_type = 1;
          data.curr_page = 1;
          data.page_size = 10;

          cstSupSearch(data)
            .then(res => {
              $loading.hide();
              console.log(res);
              this.searchList = res.cst_sup;
            })
            .catch(err => {
              $toast.show('搜索出错了!');
              console.log(err);
            })
        }, 300
      ),
      getHistory(){
        let requestData = {
          curr_page: 1,
          page_size: 15,
          query: {
            cst_company_id: this.currCustomer.cst_company_id ? [this.currCustomer.cst_company_id] : [],
            type: this.chosenType.code ? [this.chosenType.code] : [],
            cst_prod_name: '',
            compute_no: '',
            approve_step: []
          },
        }
        list(requestData).then(res => {
          this.compute_history = res.list

        }).catch(error => {
          $toast.show(error.description)
        })
      },
      ok(){
        // if (!this.currCustomer.cst_company_id) {
        //   $toast.show('请填写客户信息')
        //   return
        // }
        // if (!this.chosenType.code) {
        //   $toast.show('请选模具类型')
        //   return
        // }
        this.$router.forward({
          path: '/customerOrder/compute/detail/new/null',
          query: {cst_company: this.currCustomer, type: this.chosenType}
        })
      },
      associateCompute(item){

        this.$router.forward('/customerOrder/compute/detail/associate/' + item.compute_id)

      }
    },
    watch: {
      'currCustomer.cst_company_name': function () {
        if (this.currCustomer.cst_company_name.trim().length === 0) {
          this.currCustomer.cst_company_id = ''
          this.searchList.length = 0;
          return
        }
        if (this.currCustomer.cst_company_id) {
          return;
        }
        this.matchCustomer();
      }
    }
  }
</script>
<style scoped lang="scss">
  .searchList {
    background-color: whitesmoke;
    width: 100%;
    height: auto;
    position: absolute;
    z-index: 10;
    padding-left: 10px;
    li {
      padding: 8px;
      height: 40px;
      transition: height .2s;
    }
  }

  .prod-type {
    width: 100%;
    height: auto;
    margin-top: 10px;
    background-color: white;
    .title {
      padding: 10px 15px;
      width: 100%;
      margin: 0;
    }
    .types {
      width: 90%;
      margin: 0 auto;
      position: relative;
      padding-bottom: 5px;
      li {
        padding: 5px 20px;
        text-align: center;
        border-radius: 15px;
        background-color: #FFFFFF;
        transform: scale(0.8, 0.8);
        -webkit-transform: scale(0.8, 0.8); /* Chrome, Safari, Opera */
        -moz-transform: scale(0.8, 0.8);
        border: thin solid #ababab;
        color: #ababab;
        width: 30%;
        text-overflow: ellipsis;
        white-space: nowrap;
        overflow: hidden;
        display: inline-block;
        &.active {
          color: #416eb6;
          border: thin solid #416eb6;
        }
      }

    }
  }

  .compute-history {
    width: 100%;
    margin-top: 10px;
    background-color: white;
    position: relative;

    .title {
      padding: 10px 15px;
      width: 100%;
      margin: 0;
    }
    .history {
      width: 100%;
      position: absolute;
      top: 40px;
      height: auto;
      span {
        width: 45%;
        text-overflow: ellipsis;
        white-space: nowrap;
        float: left;
        padding: 5px;
        overflow: hidden;
      }
    }
  }

  .btns {
    height: 50px;
    width: 100%;
    display: flex;
    position: fixed;
    background-color: white;
    bottom: 0;
    align-items: center;
    z-index: 10;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);
    .btn {
      flex: 1;
      text-align: center;
    }
  }
</style>

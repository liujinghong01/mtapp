<template>
    <div class="mw-page page" v-nav="{hideNavbar:true}">
      <div class="mw-page-header">
        <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
        <span>报价单列表</span>
        <i class="right icon ion-android-add" @click="onMenuClick"></i>
    </div>
    <div class="mw-page-content has-tab">
      <div style="height: 60px">
        <tabs :tab-items="tabs" :tab-index="tabIndex" :on-tab-click="onTabClick"></tabs>
      </div>
      <div style="flex: 1;position:relative;">
        <div :class="['content',tabIndex===0 ? 'active' : '']">
          <scroll class="page-content"
                  :on-infinite="onInfinite">
            <div v-for="(item, index) in items[0].list" :key="index"  >
              <swipe-item :item="item" swipeItemText="删除" :canSwipe="true" v-on:SwipeItemClick="delItem" v-on:ItemClick="onItemClick(item,'isSubmit')"  :obj="item">
                <p>报价单编号:{{item.quote_no}}</p>
                <p>客户:{{item.cst_name}}</p>
                <p>业务员:{{item.salesman_name}}</p>
                <p>报价日期:{{item.quote_date}}</p>
              </swipe-item>
            </div>
            <div v-if="pageModels[0].curr_page >= pageModels[0].total_page" slot="infinite" class="text-center">没有更多数据</div>
          </scroll>
        </div>
        <div :class="['content',tabIndex===1 ? 'active' : '']">
          <scroll class="page-content"
                  :on-infinite="onInfinite">
            <div v-for="(item, index) in items[1].list" :key="index"  >
              <swipe-item :item="item" swipeItemText="删除" :canSwipe="true" v-on:SwipeItemClick="delItem" v-on:ItemClick="onItemClick(item,'isApproval')"  :obj="item">

                <p>报价单编号:{{item.quote_no}}</p>
                <p>客户:{{item.cst_name}}</p>
                <p>业务员:{{item.salesman_name}}</p>
                <p>报价日期:{{item.quote_date}}</p>
              </swipe-item>
            </div>

            <div v-if="pageModels[1].curr_page >= pageModels[1].total_page" slot="infinite" class="text-center">没有更多数据</div>
          </scroll>
        </div>
        <div :class="['content',tabIndex===2 ? 'active' : '']">
          <scroll class="page-content"
                  :on-infinite="onInfinite">
            <div v-for="(item, index) in items[2].list" :key="index"  >
              <swipe-item
                :item="item"
                swipeItemText="删除"
                :canSwipe="false"
                v-on:SwipeItemClick="delItem"
                v-on:ItemClick="onItemClick(item,'isApproval')"
                :obj="item">
                <p>报价单编号:{{item.quote_no}}</p>
                <p>客户:{{item.cst_name}}</p>
                <p>业务员:{{item.salesman_name}}</p>
                <p>报价日期:{{item.quote_date}}</p>
              </swipe-item>
            </div>
            <div v-if="pageModels[2].curr_page >= pageModels[2].total_page" slot="infinite" class="text-center">没有更多数据</div>
          </scroll>
        </div>
        <div :class="['content',tabIndex===3 ? 'active' : '']">
          <scroll class="page-content"
                  :on-infinite="onInfinite">
            <div v-for="(item, index) in items[3].list" :key="index"  >
              <swipe-item :item="item" swipeItemText="删除" :canSwipe="true" v-on:SwipeItemClick="delItem" v-on:ItemClick="onItemClick(item,'return')"  :obj="item">
                <p>报价单编号:{{item.quote_no}}</p>
                <p>客户:{{item.cst_name}}</p>
                <p>业务员:{{item.salesman_name}}</p>
                <p>报价日期:{{item.quote_date}}</p>
              </swipe-item>
            </div>
            <div v-if="pageModels[3].curr_page >= pageModels[3].total_page" slot="infinite" class="text-center">没有更多数据</div>
          </scroll>
        </div>
      </div>
    </div>
    <div class="mask" v-show="showFilter" @click="showFilter=false"></div>
    <div :class="showFilter?'filter active':'filter'">
      <div class="item item-divider" v-show="showFilter">
        筛选
      </div>
      <list class="list-ios" v-show="showFilter" style="position: relative">
        <von-input type="text" v-model="query.quote_no" placeholder="输入编号" label="报价单编号"></von-input>
        <von-input type="text" v-model="searchCompanyName" placeholder="输入客户名称" label="客户"></von-input>
        <ul class="searchCsts" v-show="cstList.length>0&&showCstList">
          <li @click="chooseCst(item)" v-for="item in cstList" :key="item.cst_company_id">{{item.cst_company_name}}
          </li>
          <li @click="showCstList=false" style="text-align: center">收起
          </li>
        </ul>

        <von-input type="text" v-model="query.prod_name" placeholder="产品名称" label="产品名称"></von-input>
        <p style="margin: 8px 15px">产品类型</p>
        <ul class="companys" v-show="types.length>0">
          <li v-for="item in types" :key="item.name" @click="chooseType(item)" :class="item.chosen?'active':''">{{item.name}}</li>
        </ul>
      </list>
      <div class="btns" v-show="showFilter">
        <div class="btn reset">重置</div><div class="btn">确定</div>
      </div>
    </div>
  </div>
</template>
<script>
  import { mapGetters } from 'vuex'
  import { cstSupSearch } from '@/api/supply'
  import swipeItem from '@/components/swipeItem'
  import _ from 'lodash'
  import { list,quoteDelete } from '@/api/customerOrder/quote'
  import sess from '@/utils/sess'
    export default{
      name:'quoteList',
      components:{
        swipeItem
      },
      data(){
        return{
          tabs: [   //tabs 标签
            "制定中", "审批中", "已审批", "退回"
          ],
          tabIndex:0,  //当前选中的tab
          pageModels:[
            {curr_page:0,page_size:20,total_page:0},
            {curr_page:0,page_size:20,total_page:0},
            {curr_page:0,page_size:20,total_page:0},
            {curr_page:0,page_size:20,total_page:0},
            {curr_page:0,page_size:20,total_page:0},
          ],
          query:{       //查询条件
            start_date:'',    //报价开始日期
            end_date:'',      //报价日期
            prod_name:'',     //产品名称
            quote_no:'',      //报价单编号
            approve_step:[], //审批步骤
            cst_company_id:[], //选择的公司id数组
            salesman:[],        //业务员
            type:[]             //报价单类型
          },
          searchCompanyName:'', //用户搜索客户关键字
          cstList:[],//搜索出来的客户列表
          showCstList:true,// 是否显示搜索客户列表
          chosenCst:[],    //用户所选择的公司列表
          types:[//核算单类型列表
            {name:'冲压模',code:'1'},
            {name:'压铸模',code:'2'},
            {name:'注塑模',code:'3'},
          ],
          items:[ //核算单列表,每个list代表某一状态的list
            { list:[] },
            { list:[] },
            { list:[] },
            { list:[] },
          ],
          showFilter:false,   //是否显示筛选框
        }
      },
      mounted() {
        this.query.approve_step.push('step10')

      },
      activated(){
        this.setList(true)

      },
      computed:{
        ...mapGetters(['chosenCompany']),
        chosenTypes(){
          return this.types.filter(item=>{
            return item.chosen
          })
        },
      },
      methods:{
        onMenuClick(){
          this.$router.forward('/customerOrder/quote/detail/new/null')
          // $actionSheet.show({
          //   // 支持三种主题样式 ios/android/weixin
          //   theme: 'ios',
          //   title: '请选择',
          //   buttons: {
          //     // 操作列表选项文字及回调函数
          //     '新建报价单': () => {
          //       this.$router.forward('/customerOrder/quote/detail/new/null')
          //     },
          //     // '参考历史报价单': () => {
          //     //   this.$router.forward('/customerOrder/quote/quoteHistory')
          //     // }
          //   },
          //   cancelText:'取消'
          // })

        },
        onTabClick(index){  //切换审批状态
          console.log('onTabClick');
          const that = this;

          if(index===this.tabIndex){
            return
          }
          switch (index) {
            case 0:{
              that.query.approve_step.length = 0
              that.query.approve_step.push('step10')
              break
            }
            case 1:{
              that.query.approve_step.length = 0
              that.query.approve_step.push('step20')
              that.query.approve_step.push('step30')
              break
            }
            case 2:{
              that.query.approve_step.length = 0
              that.query.approve_step.push('step0')
              break
            }
            case 3:{
              that.query.approve_step.length = 0
              that.query.approve_step.push('step11')
              break
            }
          }

          if(index===4){
            that.showFilter = true
              return
          }
          that.tabIndex = index;
          this.setList(true)
        },
        onInfinite(done) {
          const that = this;
          if(that.pageModels[that.tabIndex].total_page<=that.pageModels[that.tabIndex].curr_page){
            done()
            return
          }
          setTimeout(function () {
            if(that.pageModels[that.tabIndex].total_page<2){
              done()
            }else{
              that.setList();
              done()
            }
          },400)
        },
        setList(refresh){
          let number=0
          for(var i=0;i<=100;i++){
            number+=i
            console.log(number)
          }
          const that = this;
          const requestData={
            curr_page:refresh?1:++that.pageModels[that.tabIndex].curr_page,
            page_size:that.pageModels[that.tabIndex].page_size,
            query:this.query
          }
          list(requestData).then(res=>{
            console.log(res)
            if (res.quote_list.length>0) {
              if(!refresh) {
                that.items[that.tabIndex].list = that.items[that.tabIndex].list.concat(res.quote_list);
              }else {
                that.items[that.tabIndex].list = res.quote_list
              }
              that.pageModels[that.tabIndex].total_page = res.total_page
              that.pageModels[that.tabIndex].curr_page = res.curr_page
              that.pageModels[that.tabIndex].total_count = res.total_count
            }
          }).catch(error=>{
              $toast.show(error.description)
          })
        },
        delItem(item){
          $dialog.confirm({
            theme:'ios',
            title:'确定要删除吗？',
            okText:'确定',
            cancelText:'取消',
          }).then(res=>{
            if(res) {
              quoteDelete({
                quote_id:item.quote_id
              }).then(res=>{
                this.items[this.tabIndex].list = this.items[this.tabIndex].list.filter(o=>{
                  return o.quote_id!==item.quote_id
                })
              }).catch(error=>{
                $toast.show(error.description)
              })
            }
          })

        },
        onItemClick(item,btnShow){
          // this.$router.forward('/customerOrder/quote/detail/check/'+item.quote_id)
          this.$router.forward({path:'/customerOrder/quotePreviewDetail',query:{quote_id:item.quote_id,btnShow:btnShow}})

        },
        //筛选相关
        chooseType(item){
          if(item.chosen){
            this.$set(item,'chosen',false)
            return
          }
          this.$set(item,'chosen',true)
          this.query.type.push(item);
        },
        //选择客户
        chooseCst(item){
          let tmpList = this.chosenCst.filter(cst=>{
              return cst.cst_company_name === item.cst_company_name
          })
          if(tmpList.length>0){
             return
          }
          this.chosenCst.push(item)
        },

        searchCompany:_.debounce(
            function(){
              let data = {};
              data.client_type = 2;
              data.cst_sup_name = this.searchCompanyName;
              data.cst_sup_type = 1;
              data.curr_page = 1;
              data.page_size = 10;
              cstSupSearch(data)
                .then(res => {
                  console.log(res);
                  this.showCstList = true
                  this.cstList = res.cst_sup;
                })
                .catch(err => {
                  $toast.show('搜索出错了!');
                  console.log(err);
                })
            },800
          )
        },
      watch:{
        'searchCompanyName':function () {
          if(this.searchCompanyName.trim().length===0){
            this.searchCompanyName=''
            this.cstList.length=0;
            return
          }
          this.searchCompany()
        }
      }
    }
</script>
<style lang="scss" scoped>
  .mask{
    position: absolute;
    width: 100%;
    height: 100%;
    top:0;
    left: 0;
    z-index: 11;
    background-color: #2e2e2d;
    opacity: .4;
  }
  .filter{
    width: 0;
  }
  .filter.active{
    width: 80%;
    height: 100%;
    background-color: #F3F6FB;
    position: absolute;
    top:0;
    right: 0;
    z-index: 12;
    transition:width .5s;
    -moz-transition:width .2s; /* Firefox 4 */
    -webkit-transition:width .2s; /* Safari and Chrome */
    -o-transition:width .2s; /* Opera */
  }
  .stopSwipe{
    overflow: hidden;
  }
  .content{
    position: absolute;
    top:0;
    height: 100%;
    width: 100%;
    display: none;
  }
  .content.active{
    display: block;
  }
  .item{
    p{
      margin-bottom: 5px;
    }

  }

  .companys{
    width: 100%;
    min-height: 30px;
    padding: 5px;
    li{
      display: inline-block;
      width: 30%;
      text-align: center;
      border-radius: 15px;
      text-overflow: ellipsis;
      white-space: nowrap;
      overflow: hidden;
      padding: 5px 0;
      transform: scale(0.8,0.8);
      -webkit-transform:scale(0.8,0.8); /* Chrome, Safari, Opera */
      -moz-transform: scale(0.8,0.8);
      color: #ababab;
      border:thin solid #ababab;
      &.active{
        color: #416eb6;
        border:thin solid #416eb6;
      }
    }
  }

  .btns{
    display: flex;
    position: absolute;
    bottom: 0;
    width: 100%;
    height: 40px;

    .btn{
      flex: 1;
      text-align: center;
      line-height: 40px;
      background-color: #416eb6 ;
      color: white;
      &.reset{
        background-color:#FFFFFF ;
        color:  #416eb6;
      }
    }
  }
  .searchCsts{
    position: absolute;
    width: 65%;
    max-height: 100px;
    top: 100px;
    right: 0;
    z-index: 12;
    background-color: #FFFFFF;
    overflow-y: auto;

    li{
      padding: 10px;
      :after {
        content: '';
        position: absolute;
        background-color: #ddd;
        display: block;
        left: 0;
        top: 0;
        bottom: 0;
        right: auto;
        height: 1px;
        width: 100%;
        transform-origin: 0% 100%;
        -webkit-transform-origin: 0% 100%;
        transform: scaleY(0.5);
        -webkit-transform: scaleY(0.5);
      }
    }

  }

</style>

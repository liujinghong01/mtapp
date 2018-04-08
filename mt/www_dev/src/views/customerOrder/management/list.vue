<template>
  <div class="mw-page page" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>客户定单管理</span>
      <i class="right"></i>
    </div>
    <div class="mw-page-content has-tab">
      <div style="height: 60px">
        <tabs :tab-items="tabs" :tab-index="tabIndex" :on-tab-click="onTabClick"></tabs>
      </div>
      <div style="flex: 1;position: relative">
        <div :class="['content',tabIndex===0 ? 'active' : '']">
          <scroll :class="['page-content', showPop?'hidden':' ']"
                  :on-infinite="onInfinite" :on-refresh="onRefresh">
            <div v-for="(item, index) in items[0].list" :key="index"  >
              <item class="item-icon-right" @click.native="$router.forward('/customerOrder/order/lineInfo/checkOnly/'+item.order_line_id)">
                <div :class="['item-cont',item.swipe?'active':'']" >
                  <p>客户：<a class="cst-name">{{item.cst_name}}</a></p>
                  <p>定单编号：<a class="cst-name">{{item.project_no}}</a></p>
                  <p>产品名称：<a class="cst-name">{{item.prod_name}}</a></p>
                  <span class="no">产品类型: {{item.type_name}}</span>
                  <span class="no">数量:{{item.count}}</span>
                  <p class="start" @click.stop="startup(item)">下达</p>
                </div>
              </item>
              <div>
              </div>
            </div>
            <div v-if="pageModels[0].curr_page >= pageModels[0].total_page" slot="infinite" class="text-center">没有更多数据</div>
          </scroll>
        </div>
        <div :class="['content',tabIndex===1 ? 'active' : '']">
          <scroll :class="['page-content',  showPop?'hidden':' ']"
                  :on-infinite="onInfinite" :on-refresh="onRefresh">
            <div v-for="(item, index) in items[1].list" :key="index"  >
              <item class="item-icon-right" @click.native="$router.forward('/customerOrder/order/lineInfo/checkOnly/'+item.order_line_id)">
                <div :class="['item-cont',item.swipe?'active':'']">
                  <!--<p>客户产品名称：<a class="prd-name">{{item.cst_name}}</a></p>-->
                  <p>客户：<a class="cst-name">{{item.cst_name}}</a></p>
                  <p>定单编号：<a class="cst-name">{{item.project_no}}</a></p>
                  <p>产品名称：<a class="cst-name">{{item.prod_name}}</a></p>
                  <span class="no">产品类型: {{item.type_name}}</span>
                  <span class="no">数量:{{item.count}}</span>
                  <p v-show="item.reason!==null">再制原因: <span style="float: none;color: #416eb6;">{{ item.reason }}</span></p>
                  <i class="icon ion-ios-arrow-down arrow" v-show="!item.check" @click.stop="arrowClick(item)"></i>
                  <i class="icon ion-ios-arrow-up arrow" v-show="item.check" @click.stop="arrowClick(item)"></i>
                </div>
              </item>
              <ul :class="['actions',item.check?'active':'']">
                <li :class="['action',item.check?'active':'']" @click="showPoppue('stop')">
                  <i class="icon ion-ios-pause"></i>
                  <p>暂停</p>
                </li>
                <li :class="['action',item.check?'active':'']" @click="showPoppue('end')">
                  <i class="icon ion-checkmark"></i>
                  <p>完结</p>
                </li>
              </ul>
            </div>
            <div v-if="pageModels[1].curr_page >= pageModels[1].total_page" slot="infinite" class="text-center">没有更多数据</div>
          </scroll>
        </div>
        <div :class="['content',tabIndex===2 ? 'active' : '']">
          <scroll :class="['page-content', showPop?'hidden':' ']"
                  :on-infinite="onInfinite" :on-refresh="onRefresh">
            <div v-for="(item, index) in items[2].list" :key="index"  >
              <item class="item-icon-right" @click.native="$router.forward('/customerOrder/order/lineInfo/checkOnly/'+item.order_line_id)">
                <div :class="['item-cont',item.swipe?'active':'']">
                  <!--<p>客户名称：<a class="prd-name">{{item.cst_name}}</a></p>-->
                  <p>客户：<a class="cst-name">{{item.cst_name}}</a></p>
                  <p>定单编号：<a class="cst-name">{{item.project_no}}</a></p>
                  <p>产品名称：<a class="cst-name">{{item.prod_name}}</a></p>
                  <span class="no">产品类型: {{item.type_name}}</span>
                  <span class="no">数量:{{item.count}}</span>
                  <p v-show="item.reason!==null">暂停原因: <span style="float: none;color: #416eb6;">{{ item.reason }}</span></p>
                  <i class="icon ion-ios-arrow-down arrow" v-show="!item.check" @click.stop="arrowClick(item)"></i>
                  <i class="icon ion-ios-arrow-up arrow" v-show="item.check" @click.stop="arrowClick(item)"></i>
                </div>
              </item>
              <ul :class="['actions',item.check?'active':'']">
                <li :class="['action',item.check?'active':'']" @click="showPoppue('start')">
                  <i class="icon ion-ios-pause"></i>
                  <p>开始</p>
                </li>
              </ul>
            </div>
            <div v-if="pageModels[2].curr_page >= pageModels[2].total_page" slot="infinite" class="text-center">没有更多数据</div>
          </scroll>
        </div>
        <div :class="['content',tabIndex===3 ? 'active' : '']">
          <scroll :class="['page-content', showPop?'hidden':' ']"
                  :on-infinite="onInfinite" :on-refresh="onRefresh">
            <div v-for="(item, index) in items[3].list" :key="index"  >
              <item class="item-icon-right" @click.native="$router.forward('/customerOrder/order/lineInfo/checkOnly/'+item.order_line_id)">
                <div :class="['item-cont',item.swipe?'active':'']">
                  <!--<p>客户产品名称：<a class="prd-name">{{item.cst_name}}</a></p>-->
                  <p>客户：<a class="cst-name">{{item.cst_name}}</a></p>
                  <p>定单编号：<a class="cst-name">{{item.project_no}}</a></p>
                  <p>产品名称：<a class="cst-name">{{item.prod_name}}</a></p>
                  <span class="no">产品类型: {{item.type_name}}</span>
                  <span class="no">数量:{{item.count}}</span>
                  <i class="icon ion-ios-arrow-down arrow" v-show="!item.check" @click.stop="arrowClick(item)"></i>
                  <i class="icon ion-ios-arrow-up arrow" v-show="item.check" @click.stop="arrowClick(item)"></i>
                </div>
              </item>
              <ul :class="['actions',item.check?'active':'']">
                <li :class="['action',item.check?'active':'']" @click="showPoppue('restart')">
                  <i class="icon ion-ios-pause"></i>
                  <p>反完结</p>
                </li>
              </ul>
            </div>
            <div v-if="pageModels[3].curr_page >= pageModels[3].total_page" slot="infinite" class="text-center">没有更多数据</div>
          </scroll>
        </div>
      </div>
    </div>
    <div class="mask" v-show="showPop" @click="showPop = false"></div>
    <div class="pop" v-show="showPop">
      <textarea  placeholder="原因" v-model="reason"></textarea>
      <md-button class="button button-assertive" style="width: 45%" @click.native="showPop = false">取消</md-button>
      <md-button class="button button-positive" style="width: 45%;float: right;" v-show="popAction==='stop'" @click.native="stop">暂停</md-button>
      <md-button class="button button-positive" style="width: 45%;float: right;" v-show="popAction==='end'" @click.native="end">完结</md-button>
      <md-button class="button button-positive" style="width: 45%;float: right;" v-show="popAction==='start'" @click.native="start">重新开始</md-button>
      <md-button class="button button-positive" style="width: 45%;float: right;" v-show="popAction==='restart'" @click.native="restart">反完结</md-button>
    </div>
  </div>
</template>
<script>
  import { mapGetters } from 'vuex'
  import { cstSupSearch } from '@/api/supply'
  import swipeItem from '@/components/swipeItem'
  import _ from 'lodash'
  import { list,manage,start,restart} from '@/api/customerOrder/manage'
    export default{
      name:'manageList',
      components:{
        swipeItem
      },
      data(){
        return{
          tabs: [   //tabs 标签
            "待下达", "在制中", "已暂停", "已完结"
          ],
          tabIndex:0,
          pageModels:[
            {curr_page:0,page_size:10,total_page:0},
            {curr_page:0,page_size:10,total_page:0},
            {curr_page:0,page_size:10,total_page:0},
            {curr_page:0,page_size:10,total_page:0},
          ],
          query:{       //查询条件
            start_date:'',    //报价开始日期
            end_date:'',      //报价日期
            prod_name:'',     //产品名称
            project_no:'',      //报价单编号
            startup_step:'', //审批步骤
            cst_company_id:[], //选择的公司id数组
          },
          searchCompanyName:'', //用户搜索客户关键字
          cstList:[],//搜索出来的客户列表
          showCstList:true,// 是否显示搜索客户列表
          chosenCst:[],    //用户所选择的公司列表
          items:[ //核算单列表,每个list代表某一状态的list
            { list:[] },
            { list:[] },
            { list:[] },
            { list:[] },
            { list:[] }
          ],
          showFilter:false,   //是否显示筛选框
          currItem:{},        //当前操作的item
          touch:{             //手指位置对象
            x:0,
            y:0
          },
          showPop:false,
          popAction:'stop',
          reason:''
        }
      },
      mounted() {
        this.query.startup_step = 'step100'
        this.setList(true)
      },
      activated(){
        this.setList(true)
      },
      computed:{
        ...mapGetters(['chosenCompany']),
      },
      methods:{
        onTabClick(index){  //切换审批状态
          console.log('onTabClick');
          const that = this;
          if(index===this.tabIndex){
            return
          }
          switch (index) {
            case 0:{
              that.query.startup_step = 'step100'
              break
            }
            case 1:{
              that.query.startup_step = 'step200'
              break
            }
            case 2:{
              that.query.startup_step = 'step300'
              break
            }
            case 3:{
              that.query.startup_step = 'step00'
              break
            }
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
            if(that.pageModels[that.tabIndex].total_page<=that.pageModels[that.tabIndex].curr_page){
              done()
            }else{
              that.setList();
              done()
            }
          },400)
        },
        setList(refresh){
          const that = this;
          const requestData={
            curr_page:refresh?1:1+that.pageModels[that.tabIndex].curr_page,
            page_size:that.pageModels[that.tabIndex].page_size,
            query:this.query
          }
          list(requestData).then(res=>{
            that.items[that.tabIndex].list = refresh ? res.cst_order_list : that.items[that.tabIndex].list.concat(res.cst_order_list);
            that.pageModels[that.tabIndex].total_page = res.total_page;
            that.pageModels[that.tabIndex].curr_page = res.curr_page;
            that.pageModels[that.tabIndex].total_count = res.total_count;

          }).catch(error=>{
              $toast.show(error.description)
          })
        },
        onRefresh(done){
          this.pageModels[this.tabIndex].total_page = 0;
          this.pageModels[this.tabIndex].curr_page = 0;
          this.pageModels[this.tabIndex].total_count = 0;
          this.setList(true)
          setTimeout(function () {
            done()
          },500)
        },
        startup(item){
          this.$router.forward('/customerOrder/manage/start/'+item.order_line_id)
        },
        arrowClick(item){
          if(item.check){
            this.$set(item,'check',false)
            return
          }else {
            this.$set(this.currItem,'check',false)
            this.$set(item,'check',true)
            this.currItem = item
          }
        },
        showPoppue(action){
          this.reason = ''
          this.showPop = true
          this.popAction = action
        },
        stop(){
          if(!this.reason.trim()){
            $toast.show('请填写原因')
            return
          }
          const reqData = {
            flag:'1',
            order_line_id:this.currItem.order_line_id,
            reason:this.reason
          }
          manage(reqData).then(res=>{
            $toast.show('暂停成功')
            this.showPop = false
            this.removeItem()
          }).catch(error=>{
            $toast.show(error.description)
          })
        },
        end(){
          if(!this.reason.trim()){
            $toast.show('请填写原因')
            return
          }
          const reqData = {
            flag:'2',
            order_line_id:this.currItem.order_line_id,
            reason:this.reason
          }
          manage(reqData).then(res=>{
            $toast.show('操作成功')
            this.showPop = false
            this.removeItem()
          }).catch(error=>{
            $toast.show(error.description)
          })
        },
        start(){
          if(!this.reason.trim()){
            $toast.show('请填写原因')
            return
          }
          const reqData = {
            order_line_id:this.currItem.order_line_id,
            reason:this.reason
          }
          start(reqData).then(res=>{
            $toast.show('操作成功')
            this.showPop = false
            this.removeItem()
          }).catch(error=>{
            $toast.show(error.description)
          })
        },
        restart(){
          if(!this.reason.trim()){
            $toast.show('请填写原因')
            return
          }
          const reqData = {
            order_line_id:this.currItem.order_line_id,
            reason:this.reason
          }
          restart(reqData).then(res=>{
            $toast.show('操作成功')
            this.showPop = false
            this.removeItem()
          }).catch(error=>{
            $toast.show(error.description)
          })
        },
        removeItem(){
          const that = this
          let tmpList = this.items[this.tabIndex].list
          for(let i in tmpList) {
            if (tmpList[i].order_line_id === that.currItem.order_line_id) {
              that.items[that.tabIndex].list.splice(i, 1)
            }
          }
        }
      }
    }
</script>
<style lang="scss" scoped>
  $vi-color:#416eb6;

  .hidden{
    overflow-y: hidden;
  }
  .mask{
    width: 100%;height: 100%;position:absolute;left: 0;right: 0;background-color: #282828;opacity:0.2;z-index: 14
  }
  .pop{
    position:absolute;top: 30%;width: 80%;height: 50%;z-index: 15;left: 0;right: 0;margin: auto;
    textarea{
      width: 100%;height: 80%;resize: none;margin-bottom: 10px;padding: 10px;
    }
  }
  .content{
    position: absolute;
    height: 100%;
    width: 100%;
    display: none;
  }
  .content.active{
    display: block;
  }

  .actions{
    width: 100%;
    margin: 0 auto;
    height: 0;
    display: flex;
    background-color: white;
    overflow-y: hidden;
    transition:height .5s;
    -moz-transition:height .5s; /* Firefox 4 */
    -webkit-transition:height .5s; /* Safari and Chrome */
    -o-transition:height .5s; /* Opera */
    .action{
      flex: 1 0 50%;
      text-align: center;
      color: #416eb6;
      /*opacity: 0;*/
      /*background-color: white;*/
      /*transition:opacity .5s;*/
      /*-moz-transition:opacity .5s; !* Firefox 4 *!*/
      /*-webkit-transition:opacity .5s; !* Safari and Chrome *!*/
      /*-o-transition:opacity .5s; !* Opera *!*/
      /*&.active{*/
        /*opacity: 1;*/
      /*}*/
    }
    &.active{
      height: 40px;
    }
  }
  .item{
    .item-cont{
      span{
        float: left;
        text-overflow: ellipsis;
        white-space: nowrap;
        padding: 5px;
        overflow: hidden;
        font-size: 12px;
        color: #ababab;
      }
      p{
        height: 24px;
        line-height: 24px;
        padding: 0 5px;
        .cst-name{
          color: #282828;
        }
        .prd-name{
          color: #416eb6;
        }
      }
      .no{
        width: 50%;
      }
      .name{
        width: 25%;
      }
      .count{
        width: 15%;
      }
    }
  }

  .start{
    position: absolute;right: 10px;top: 45%; color:$vi-color;
  }

</style>

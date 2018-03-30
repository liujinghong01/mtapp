<template>
  <div class="mw-page page purchase" v-nav="{hideNavbar:true}">
    <div class="mw-page-header">
      <i class="left icon ion-ios-arrow-back" @click="$router.go(-1)"></i>
      <span>询价单列表</span>
      <i class="right" @click="onMenuClick">筛选</i>
    </div>
    <div class="mw-page-content" style="display: flex;flex-direction: column">
      <div>
        <div class="mw-tab">
          <ul class="tab-content">
            <li :class="{'active':tabIndex===0}" @click="onTabClick(0)">询价单</li>
            <li :class="{'active':tabIndex===1}" @click="onTabClick(1)">询价反馈单</li>
          </ul>
        </div>
      </div>
      <div class="divider"></div>
      <div style="flex: 1;position: relative;">
        <div class="content"  v-show="tabIndex===0">
          <scroll class="page-content" :on-infinite="onInfinite"  style="-webkit-overflow-scrolling: touch">
            <div v-for="(item, index) in List[0]" :key="index">
              <item class="item-icon-right"  @click.native="onItemClick(item)">
                编号 : {{item.inquiry_no}}
                <br/>
                公司名称 : {{item.company_name}}
                <span class="item-note normal" v-show="item.deal_flag>0" >已反馈</span>
                <i class="icon ion-ios-arrow-right" style="color: #DDD;"></i>
              </item>
            </div>
            <div v-if="PageModel[0].curr_page===PageModel[0].total_page" slot="infinite" class="text-center">没有更多数据</div>
          </scroll>
        </div>
        <div class="content"  v-show="tabIndex===1">
          <scroll class="page-content" :on-infinite="onInfinite"  >
            <div v-for="(item, index) in List[1]" :key="index">
              <item class="item-icon-right"  @click.native="onItemClick(item)">
                编号 : {{item.inquiry_no}}
                公司名称 : {{item.company_name}}
                <span class="item-note normal" v-show="item.deal_flag>0" >查看反馈</span>
                <i class="icon ion-ios-arrow-right" style="color: #DDD;"></i>
              </item>
            </div>
            <div v-if="PageModel[1].curr_page ===PageModel[1].total_page" slot="infinite" class="text-center">没有更多数据</div>
          </scroll>
        </div>
      </div>
    </div>
  </div>
  <!--<sidebar-check :showFilter="showFilter" :dicType="dicType" :chosen="chosen" v-on:hideMask="showFilter=false"></sidebar-check>-->
</template>

<script>
  import CstItem from "../../../components/cstItem";
  import SidebarCheck from "../../../components/sidebarCheck";
  import { inquiryList,inquiryFeedbackList } from '@/api/purchase/inquiry';
  export default {
    components: {
      CstItem,
      SidebarCheck
    },
    name: 'inquiryList',
    data() {
      return {
        action: 'check',
        dicType: 'coin',
        showFilter: false,
        tabIndex: 0,
        List: [[],[]],
        PageModel: [ {
          curr_page: 0,
          page_size: 30,
          total_page:''
        },{
          curr_page: 0,
          page_size: 10,
          total_page:''
        }],
      }
    },
    mounted: function () {
      this.inquiryList();
    },
    methods: {
      //获取列表
      //获取询价单列表
      inquiryList(){
        const requestData = {
          curr_page: this.PageModel[0].curr_page+1,
          page_size: this.PageModel[0].page_size,
          // query: {
          //   deal_flag: "",
          //   mat_desc: ""
          // }
        };
        inquiryList(requestData).then(res => {
          let index=this.tabIndex
          if(this.PageModel[index].curr_page===this.PageModel[index].total_page){
            return
          }
          if(res.inquiry_list.length>0){
            console.log(res.inquiry_list)
            this.List[index] = this.List[index].concat(res.inquiry_list);
            this.PageModel[index].curr_page = res.curr_page;
            this.PageModel[index].total_page = res.total_page;
          }else{
            this.PageModel[index].curr_page=this.PageModel[index].total_page
          }
        }).catch(error => {
          $toast.show(error.description);
          this.PageModel[index].curr_page=this.PageModel[index].total_page
        })
      },
      feedbackList(){
        const requestData = {
          curr_page: this.PageModel[1].curr_page+1,
          page_size: this.PageModel[1].page_size,
          query: {
            deal_flag: "",
            mat_desc: ""
          }
        };
        inquiryFeedbackList(requestData).then(res => {
          let index=this.tabIndex
          if(this.PageModel[index].curr_page===this.PageModel[index].total_page){
            return
          }
          if(res.inquiry_list.length>0){
            this.List[index] = this.List[index].concat(res.inquiry_list);
            this.PageModel[index].curr_page = res.curr_page;
            this.PageModel[index].total_page = res.total_page;
          }else{
            this.PageModel[index].curr_page=this.PageModel[index].total_page
          }
        }).catch(error => {
          $toast.show(error.description);
          this.PageModel[index].curr_page=this.PageModel[index].total_page
        })
      },
      onMenuClick() {

      },
      onInfinite(done) {
        setTimeout(() => {
          if(this.tabIndex==0&&this.PageModel[0].curr_page<this.PageModel[0].total_page){
            this.inquiryList();
            done();
          }else if(this.tabIndex==1&&this.PageModel[1].curr_page<this.PageModel[1].total_page){
            this.feedbackList()
            done()
          }else{
            done()
          }
        }, 200)

      },
      onTabClick(index) {
        this.tabIndex = index;
        if(this.tabIndex==1&&this.PageModel[1].curr_page!==this.PageModel[0].total_page){
          this.feedbackList();
        }
      },
      onItemClick(item) {
        if (this.tabIndex == 0 && item.deal_flag > 0) {
          //询价反馈单详情(已反馈)
          this.$router.forward('/purchase/inquiry/detail/action/' + item.inquiry_id)
        } else if (this.tabIndex == 0 && item.deal_flag <= 0) {
          //询价详情（未反馈）
          this.$router.forward('/purchase/inquiry/detail/check/' + item.inquiry_id)
        } else if (this.tabIndex == 1 && item.deal_flag > 0) {
          //供应商询价反馈单详情（可修改，提交）
          this.$router.forward('/purchase/inquiry/feedback/action/' + item.inquiry_id)
        } else if(this.tabIndex == 1 && item.deal_flag <= 0)  {
          //供应商询价反馈单详情（不可修改）
          this.$router.forward('/purchase/inquiry/feedback/check/' + item.inquiry_id)
        }
      }
    }

  }
</script>
<style lang="scss" scoped>

</style>

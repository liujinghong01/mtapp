webpackJsonp([90],{353:function(t,e,a){a(780);var i=a(0)(a(552),a(927),"data-v-56da53a8",null);t.exports=i.exports},416:function(t,e,a){"use strict";function i(t){return a.i(c.a)({url:"/api/iv/store_room/search_mat",method:"post",data:t})}function s(t){return a.i(c.a)({url:"/api/iv/scrap_mat_bill/modify",method:"post",data:t})}function n(t){return a.i(c.a)({url:"/api/iv/scrap_mat_bill/list",method:"post",data:t})}function o(t){return a.i(c.a)({url:"/api/iv/scrap_mat_bill/detail",method:"post",data:t})}function r(t){return a.i(c.a)({url:"/api/iv/scrap_mat_bill/scrap",method:"post",data:t})}e.d=i,e.c=s,e.e=n,e.b=o,e.a=r;var c=a(10)},552:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var i=a(416),s=a(20),n=a.n(s);e.default={name:"scrapList",components:{SwipeItem:n.a},data:function(){return{tabs:["待审批","已审批"],tabIndex:0,pageModels:[{curr_page:0,page_size:10},{curr_page:0,page_size:10}],list:[{inventoryList:[]},{inventoryList:[]}]}},computed:{},activated:function(){this.pageModels=[{curr_page:0,page_size:10},{curr_page:0,page_size:10}],this.list=[{inventoryList:[]},{inventoryList:[]}],0==this.tabIndex?this.scrapList("step20"):this.scrapList("step0")},methods:{scrapSubmit:function(t){var e={scrap_id:t.scrap_id};$dialog.confirm({theme:"ios",title:"确定要执行报废吗？",cancelText:"取消",okText:"确定"}).then(function(t){t&&a.i(i.a)(e).then(function(t){t.scrap_info&&$toast.show("执行报废成功")}).catch(function(t){$toast.show(t.description)})}),event.cancelBubble=!0},onTabClick:function(t){t!==this.tabIndex&&(this.tabIndex=t,void 0===this.list[this.tabIndex].length&&(1===this.tabIndex?this.scrapList("step0"):this.scrapList("step20")))},onInfinite:function(t){var e=this;setTimeout(function(){0===e.tabIndex?e.scrapList("step20"):e.scrapList("step0"),t()},500)},onRefresh:function(t){setTimeout(function(){t()},500)},scrapList:function(t){var e=this;if(!(this.pageModels[this.tabIndex].curr_page>=this.pageModels[this.tabIndex].total_page)){var s={curr_page:this.pageModels[this.tabIndex].curr_page+1,page_size:this.pageModels[this.tabIndex].page_size,query:{approve_step:t}};a.i(i.e)(s).then(function(t){t.scrap_mat_list.length>0?(e.list[e.tabIndex].inventoryList=e.list[e.tabIndex].inventoryList.concat(t.scrap_mat_list),e.pageModels[e.tabIndex].curr_page=t.curr_page,e.pageModels[e.tabIndex].total_page=t.total_page):e.pageModels[e.tabIndex].total_page=0}).catch(function(t){$toast.show(t.description)})}}},mounted:function(){}}},641:function(t,e,a){e=t.exports=a(225)(),e.push([t.i,"","",{version:3,sources:[],names:[],mappings:"",file:"list.vue",sourceRoot:""}])},780:function(t,e,a){var i=a(641);"string"==typeof i&&(i=[[t.i,i,""]]),i.locals&&(t.exports=i.locals);a(226)("799e6ebc",i,!0)},927:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{directives:[{name:"nav",rawName:"v-nav",value:{hideNavbar:!0},expression:"{hideNavbar:true}"}],staticClass:"mw-page page storage"},[a("div",{staticClass:"mw-page-header"},[a("i",{staticClass:"left icon ion-ios-arrow-back",on:{click:function(e){t.$router.go(-1)}}}),t._v(" "),a("span",[t._v("物料报废申请单列表")]),t._v(" "),a("i",{staticClass:"right"})]),t._v(" "),a("div",{staticClass:"mw-page-content has-tab"},[a("div",{staticStyle:{height:"60px"}},[a("tabs",{attrs:{"tab-items":t.tabs,"tab-index":t.tabIndex,"on-tab-click":t.onTabClick}})],1),t._v(" "),a("div",{staticStyle:{flex:"1"}},[a("div",{directives:[{name:"show",rawName:"v-show",value:0===t.tabIndex,expression:"tabIndex===0"}],staticClass:"content"},[a("scroll",{staticClass:"page-content",staticStyle:{"-webkit-overflow-scrolling":"touch"},attrs:{"on-infinite":t.onInfinite,"on-refresh":t.onRefresh}},[t._l(t.list[0].inventoryList,function(e){return a("item",{nativeOn:{click:function(a){t.$router.forward("/storage/scrap/detail/check/"+e.scrap_id)}}},[a("h3",[t._v("报废单号:"+t._s(e.scrap_no))]),t._v(" "),a("p",[t._v("种类:"+t._s(e.mat_count)+"    报废数量:"+t._s(e.total_qty))])])}),t._v(" "),t.pageModels[0].curr_page>=t.pageModels[0].total_page?a("div",{staticClass:"text-center",attrs:{slot:"infinite"},slot:"infinite"},[t._v("没有更多数据")]):t._e()],2)],1),t._v(" "),a("div",{directives:[{name:"show",rawName:"v-show",value:1===t.tabIndex,expression:"tabIndex===1"}],staticClass:"content"},[a("scroll",{staticClass:"page-content",staticStyle:{"-webkit-overflow-scrolling":"touch"},attrs:{"on-infinite":t.onInfinite,"on-refresh":t.onRefresh}},[t._l(t.list[1].inventoryList,function(e){return a("item",{nativeOn:{click:function(a){t.$router.forward("/storage/scrap/detail/check/"+e.scrap_id)}}},[a("h3",[t._v("报废单号:"+t._s(e.scrap_no))]),t._v(" "),a("span",{directives:[{name:"show",rawName:"v-show",value:0==e.is_scraped,expression:"item.is_scraped==0"}],staticStyle:{position:"absolute",right:"0",top:"0",color:"#4a90e2",width:"70px",height:"100%","line-height":"67px"},on:{click:function(a){t.scrapSubmit(e)}}},[t._v("执行报废")]),t._v(" "),a("p",[t._v("种类:"+t._s(e.mat_count)+"    报废数量:"+t._s(e.total_qty))])])}),t._v(" "),t.pageModels[1].curr_page>=t.pageModels[1].total_page?a("div",{staticClass:"text-center",attrs:{slot:"infinite"},slot:"infinite"},[t._v("没有更多数据")]):t._e()],2)],1)])]),t._v(" "),a("div",{staticClass:"mw-page-footer"},[a("div",{staticClass:"btns"},[a("div",{staticClass:"btn",on:{click:function(e){t.$router.forward("/storage/scrap/detail/new/0")}}},[t._v("\n        新建物料报废单\n      ")])])])])},staticRenderFns:[]}}});
//# sourceMappingURL=90.e1929d3c482550600e87.js.map
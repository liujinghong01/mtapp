webpackJsonp([121],{227:function(t,e,n){n(768);var a=n(0)(n(425),n(915),"data-v-4d404b40",null);t.exports=a.exports},395:function(t,e,n){"use strict";function a(t){return n.i(m.a)({url:"/api/bom/list",method:"post",data:t})}function i(t){return n.i(m.a)({url:"api/bom_node/list",method:"post",data:t})}function o(t){return n.i(m.a)({url:"/api/bom_node/update",method:"post",data:t})}function s(t){return n.i(m.a)({url:"/api/cascade_type/list",method:"post",data:t})}function r(t){return n.i(m.a)({url:"/api/mat_name/list",method:"post",data:t})}function p(t){return n.i(m.a)({url:"/api/mat_name_spec/list",method:"post",data:t})}function d(t){return n.i(m.a)({url:"/api/cascade_mat/list",method:"post",data:t})}function c(t){return n.i(m.a)({url:"/api/rawnature/list",method:"post",data:t})}function l(t){return n.i(m.a)({url:"/api/rawnature/price",method:"post",data:t})}function u(t){return n.i(m.a)({url:"/api/bom_node/info",method:"post",data:t})}function _(t){return n.i(m.a)({url:"/api/bom_node/delete",method:"post",data:t})}e.k=a,e.i=i,e.h=o,e.a=s,e.b=r,e.c=p,e.d=d,e.e=c,e.g=l,e.f=u,e.j=_;var m=n(10)},425:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var a=n(14),i=n.n(a),o=n(5),s=(n(22),n(395));e.default={name:"approveBoms",data:function(){return{tabIndex:0,pageModels:[{curr_page:0,page_size:10},{curr_page:0,page_size:10}],query:{start_date:"",end_date:"",prod_name:"",quote_no:"",app_flag:[],cst_company_id:[],salesman:[],type:[]},items:[{list:[]},{list:[]}],showFilter:!1}},mounted:function(){this.query.app_flag="step30"},activated:function(){this.setList(!0)},computed:i()({},n.i(o.a)(["chosenCompany"]),{chosenTypes:function(){return this.types.filter(function(t){return t.chosen})}}),methods:{onInfinite:function(t){var e=this;if(e.pageModels[e.tabIndex].total_page<=e.pageModels[e.tabIndex].curr_page)return void t();setTimeout(function(){e.pageModels[e.tabIndex].total_page<2?t():(e.setList(),t())},400)},onRefresh:function(t){var e=this;setTimeout(function(){e.setList(!0),t()},400)},setList:function(t){var e=this,a={curr_page:t?1:e.pageModels[e.tabIndex].curr_page+1,page_size:e.pageModels[e.tabIndex].page_size,query:this.query};n.i(s.k)(a).then(function(n){console.log(n),e.items[e.tabIndex].list=t?n.bom_list:e.items[e.tabIndex].list.concat(n.bom_list),e.pageModels[e.tabIndex].total_page=n.total_page,e.pageModels[e.tabIndex].curr_page=n.curr_page,e.pageModels[e.tabIndex].total_count=n.total_count}).catch(function(t){$toast.show(t.description)})},onItemClick:function(t){this.$router.forward("/mats/bom/nodes/approve/"+t.node_id+"/"+t.bom_id+"/step30")}}}},629:function(t,e,n){e=t.exports=n(225)(),e.push([t.i,"\n.content[data-v-4d404b40] {\n  position: relative;\n  height: 100%;\n  width: 100%;\n  display: none;\n}\n.content.active[data-v-4d404b40] {\n  display: block;\n}\n.item span[data-v-4d404b40] {\n  width: 50%;\n  float: left;\n  text-overflow: ellipsis;\n  white-space: nowrap;\n  padding: 5px;\n  overflow: hidden;\n}\n","",{version:3,sources:["/Users/zxf/code/motooling/mt-app/webs-mtapp/mtApp/mt/www_dev/src/views/approve/Bom/boms.vue"],names:[],mappings:";AACA;EACE,mBAAmB;EACnB,aAAa;EACb,YAAY;EACZ,cAAc;CACf;AACD;EACE,eAAe;CAChB;AACD;EACE,WAAW;EACX,YAAY;EACZ,wBAAwB;EACxB,oBAAoB;EACpB,aAAa;EACb,iBAAiB;CAClB",file:"boms.vue",sourcesContent:["\n.content[data-v-4d404b40] {\n  position: relative;\n  height: 100%;\n  width: 100%;\n  display: none;\n}\n.content.active[data-v-4d404b40] {\n  display: block;\n}\n.item span[data-v-4d404b40] {\n  width: 50%;\n  float: left;\n  text-overflow: ellipsis;\n  white-space: nowrap;\n  padding: 5px;\n  overflow: hidden;\n}\n"],sourceRoot:""}])},768:function(t,e,n){var a=n(629);"string"==typeof a&&(a=[[t.i,a,""]]),a.locals&&(t.exports=a.locals);n(226)("9d589eac",a,!0)},915:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{directives:[{name:"nav",rawName:"v-nav",value:{hideNavbar:!0},expression:"{hideNavbar:true}"}],staticClass:"mw-page page"},[n("div",{staticClass:"mw-page-header"},[n("i",{staticClass:"left icon ion-ios-arrow-back",on:{click:function(e){t.$router.go(-1)}}}),t._v(" "),n("span",[t._v("待审批BOM列表")]),t._v(" "),n("i",{staticClass:"right"})]),t._v(" "),n("div",{staticClass:"mw-page-content has-tab"},[n("div",{class:["content",0===t.tabIndex?"active":""]},[n("scroll",{staticClass:"page-content",attrs:{"on-infinite":t.onInfinite,"on-refresh":t.onRefresh}},[t._l(t.items[0].list,function(e,a){return n("div",{key:a},[n("item",{nativeOn:{click:function(n){t.onItemClick(e)}}},[n("p",[n("span",{staticStyle:{width:"100%"}},[t._v("产品名称:"+t._s(e.cst_prod_name))])]),t._v(" "),n("p",[n("span",{staticStyle:{width:"100%"}},[t._v("项目模号:"+t._s(e.mold_no))])]),t._v(" "),n("span",[t._v("客户名称:"+t._s(e.cst_name))]),t._v(" "),n("span",[t._v("产品类型:"+t._s(e.prod_type))])])],1)}),t._v(" "),t.pageModels[0].curr_page>=t.pageModels[0].total_page?n("div",{staticClass:"text-center",attrs:{slot:"infinite"},slot:"infinite"},[t._v("没有更多数据")]):t._e()],2)],1)])])},staticRenderFns:[]}}});
//# sourceMappingURL=121.9c3c98cab8e55c3b609d.js.map
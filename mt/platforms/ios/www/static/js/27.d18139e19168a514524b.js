webpackJsonp([27],{298:function(t,e,a){a(690);var i=a(0)(a(455),a(800),"data-v-ff4a1258",null);t.exports=i.exports},328:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});e.default={name:"cstItem",data:function(){return{selectId:"select-"+Math.random().toString(36).substring(3,6)}},props:{label:{type:String,required:!0},value:{type:"",default:""},placeholder:{type:String,default:""},canEdit:{type:Boolean,default:!0},hasArrow:{type:Boolean,default:!1},arrowText:{type:[String,Number],default:""},arrowClick:{type:Function},editType:{type:String,default:"text"},isSelect:{type:Boolean,default:!1},options:{type:Array},action:{type:String,default:"check"},color:""},methods:{noteClick:function(){this.arrowClick&&this.arrowClick()},input:function(t){var e=t.target.value;this.$emit("input",e)}}}},329:function(t,e,a){e=t.exports=a(220)(),e.push([t.i,"\n.compute-item[data-v-19336c8e] {\n  min-height: 49px;\n  position: relative;\n  padding: 12px 15px;\n  background-color: white;\n  display: -ms-flexbox;\n  display: flex;\n}\n.compute-item.noneBorder > .hairlineTop[data-v-19336c8e]:before {\n    background: red;\n}\n.compute-item .arrow[data-v-19336c8e] {\n    color: #DDD;\n    position: absolute;\n    right: 15px;\n    top: 14px;\n    font-size: 20px;\n}\n.compute-item .arrow.down[data-v-19336c8e] {\n      top: 13px;\n}\n.compute-item .label[data-v-19336c8e] {\n    -ms-flex: 0 0  auto;\n        flex: 0 0  auto;\n    height: 100%;\n    display: table;\n    padding: 7px 10px 7px 0px;\n    max-width: 52%;\n    color: #484746;\n    text-align: left;\n    line-height: 12px;\n}\n.compute-item .value[data-v-19336c8e] {\n    -ms-flex: 1 0 auto;\n        flex: 1 0 auto;\n    text-align: right;\n}\n.compute-item .value input[data-v-19336c8e] {\n      margin: 0;\n      padding: 0;\n      border: none;\n      color: #ababab;\n      width: 100%;\n      height: 24px;\n      line-height: 24px;\n      text-align: right;\n      text-overflow: ellipsis;\n      white-space: nowrap;\n      overflow: hidden;\n}\n.compute-item .value span[data-v-19336c8e] {\n      height: 24px;\n      line-height: 24px;\n}\n.compute-item .value span.color[data-v-19336c8e] {\n        display: inline-block;\n        width: 20px;\n        height: 20px;\n        border-radius: 50%;\n}\n.compute-item .note[data-v-19336c8e] {\n    -ms-flex: 0 0 5%;\n        flex: 0 0 5%;\n}\n.compute-item .note .item-notes[data-v-19336c8e] {\n      float: right;\n      color: #ababab;\n      line-height: 24px;\n      /*padding: 0 5px;*/\n      padding-right: 15px;\n}\n","",{version:3,sources:["E:/weibosi/webs-mt/mt/www_dev/src/components/cstItem.vue"],names:[],mappings:";AACA;EACE,iBAAiB;EACjB,mBAAmB;EACnB,mBAAmB;EACnB,wBAAwB;EACxB,qBAAqB;EACrB,cAAc;CACf;AACD;IACI,gBAAgB;CACnB;AACD;IACI,YAAY;IACZ,mBAAmB;IACnB,YAAY;IACZ,UAAU;IACV,gBAAgB;CACnB;AACD;MACM,UAAU;CACf;AACD;IACI,oBAAoB;QAChB,gBAAgB;IACpB,aAAa;IACb,eAAe;IACf,0BAA0B;IAC1B,eAAe;IACf,eAAe;IACf,iBAAiB;IACjB,kBAAkB;CACrB;AACD;IACI,mBAAmB;QACf,eAAe;IACnB,kBAAkB;CACrB;AACD;MACM,UAAU;MACV,WAAW;MACX,aAAa;MACb,eAAe;MACf,YAAY;MACZ,aAAa;MACb,kBAAkB;MAClB,kBAAkB;MAClB,wBAAwB;MACxB,oBAAoB;MACpB,iBAAiB;CACtB;AACD;MACM,aAAa;MACb,kBAAkB;CACvB;AACD;QACQ,sBAAsB;QACtB,YAAY;QACZ,aAAa;QACb,mBAAmB;CAC1B;AACD;IACI,iBAAiB;QACb,aAAa;CACpB;AACD;MACM,aAAa;MACb,eAAe;MACf,kBAAkB;MAClB,mBAAmB;MACnB,oBAAoB;CACzB",file:"cstItem.vue",sourcesContent:["\n.compute-item[data-v-19336c8e] {\n  min-height: 49px;\n  position: relative;\n  padding: 12px 15px;\n  background-color: white;\n  display: -ms-flexbox;\n  display: flex;\n}\n.compute-item.noneBorder > .hairlineTop[data-v-19336c8e]:before {\n    background: red;\n}\n.compute-item .arrow[data-v-19336c8e] {\n    color: #DDD;\n    position: absolute;\n    right: 15px;\n    top: 14px;\n    font-size: 20px;\n}\n.compute-item .arrow.down[data-v-19336c8e] {\n      top: 13px;\n}\n.compute-item .label[data-v-19336c8e] {\n    -ms-flex: 0 0  auto;\n        flex: 0 0  auto;\n    height: 100%;\n    display: table;\n    padding: 7px 10px 7px 0px;\n    max-width: 52%;\n    color: #484746;\n    text-align: left;\n    line-height: 12px;\n}\n.compute-item .value[data-v-19336c8e] {\n    -ms-flex: 1 0 auto;\n        flex: 1 0 auto;\n    text-align: right;\n}\n.compute-item .value input[data-v-19336c8e] {\n      margin: 0;\n      padding: 0;\n      border: none;\n      color: #ababab;\n      width: 100%;\n      height: 24px;\n      line-height: 24px;\n      text-align: right;\n      text-overflow: ellipsis;\n      white-space: nowrap;\n      overflow: hidden;\n}\n.compute-item .value span[data-v-19336c8e] {\n      height: 24px;\n      line-height: 24px;\n}\n.compute-item .value span.color[data-v-19336c8e] {\n        display: inline-block;\n        width: 20px;\n        height: 20px;\n        border-radius: 50%;\n}\n.compute-item .note[data-v-19336c8e] {\n    -ms-flex: 0 0 5%;\n        flex: 0 0 5%;\n}\n.compute-item .note .item-notes[data-v-19336c8e] {\n      float: right;\n      color: #ababab;\n      line-height: 24px;\n      /*padding: 0 5px;*/\n      padding-right: 15px;\n}\n"],sourceRoot:""}])},330:function(t,e,a){var i=a(329);"string"==typeof i&&(i=[[t.i,i,""]]),i.locals&&(t.exports=i.locals);a(221)("6ea14c28",i,!0)},331:function(t,e,a){a(330);var i=a(0)(a(328),a(332),"data-v-19336c8e",null);t.exports=i.exports},332:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"compute-item"},[a("span",{staticClass:"label"},[t._v(t._s(t.label))]),t._v(" "),a("div",{directives:[{name:"show",rawName:"v-show",value:"color"!==t.editType,expression:"editType!=='color'"}],staticClass:"value"},[t.canEdit&&"check"!==t.action&&"approve"!==t.action?"check"!==t.action||"approve"!==t.action?a("input",{ref:"input",attrs:{type:t.editType,placeholder:t.placeholder},domProps:{value:t.value},on:{input:t.input}}):t._e():a("span",[t._v(t._s(t.value))])]),t._v(" "),a("div",{directives:[{name:"show",rawName:"v-show",value:"color"===t.editType,expression:"editType==='color'"}],staticClass:"value"},[a("span",{staticClass:"color",style:{background:t.color}})]),t._v(" "),a("div",{directives:[{name:"show",rawName:"v-show",value:t.hasArrow,expression:"hasArrow"}],staticClass:"note"},[a("span",{directives:[{name:"show",rawName:"v-show",value:t.hasArrow&&t.arrowText,expression:"hasArrow&&arrowText"}],staticClass:"item-notes",on:{click:t.noteClick}},[t._v(t._s(t.arrowText))]),t._v(" "),a("i",{directives:[{name:"show",rawName:"v-show",value:t.hasArrow,expression:"hasArrow"}],staticClass:"icon ion-ios-arrow-right arrow "})]),t._v(" "),a("div",{staticClass:"hairline-top"})])},staticRenderFns:[]}},367:function(t,e,a){"use strict";function i(t){return a.i(o.a)({url:"/api/iv/apply_mat_bill/list",method:"post",data:t})}function n(t){return a.i(o.a)({url:"/api/iv/apply_mat_bill/detail",method:"post",data:t})}function s(t){return a.i(o.a)({url:"/api/iv/apply_mat_bill/modify",method:"post",data:t})}e.c=i,e.a=n,e.b=s;var o=a(7)},455:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var i=a(331),n=a.n(i),s=a(367),o=a(20),l=a.n(o);e.default={name:"applyMatBills",components:{SwipeItem:l.a,CstItem:n.a},data:function(){return{tabs:["制定中","审批中","已审批","退回"],tabIndex:0,pageModels:[{curr_page:0,page_size:10,total_page:0},{curr_page:0,page_size:10,total_page:0},{curr_page:0,page_size:10,total_page:0},{curr_page:0,page_size:10,total_page:0}],query:{approve_step:[]},list:[{billList:[]},{billList:[]},{billList:[]},{billList:[]}]}},mounted:function(){this.query.approve_step.push("step10")},methods:{onTabClick:function(t){if(console.log(t),4!==t){switch(this.tabIndex=t,t){case 0:this.query.approve_step.length=0,this.query.approve_step.push("step10");break;case 1:this.query.approve_step.length=0,this.query.approve_step.push("step20"),this.query.approve_step.push("step30");break;case 2:this.query.approve_step.length=0,this.query.approve_step.push("step0");break;case 3:this.query.approve_step.length=0,this.query.approve_step.push("step11")}0===this.list[this.tabIndex].billList.length&&this.getData(!0)}},onInfinite:function(t){var e=this;if(e.pageModels[this.tabIndex].total_page<=e.pageModels[this.tabIndex].curr_page)return void t();setTimeout(function(){e.pageModels[e.tabIndex].total_page<2?t():(e.getData(),t())},500)},onRefresh:function(t){var e=this;setTimeout(function(){e.pageModels[e.tabIndex].curr_page=0,e.getData(!0),t()},500)},onItemClick:function(t){this.$router.forward("/storage/applyMatBill/detail/check/"+t.apply_id)},delItem:function(t){},getData:function(t){var e=this,i={curr_page:t?1:this.pageModels[this.tabIndex].curr_page+1,page_size:this.pageModels[this.tabIndex].page_size,query:this.query};a.i(s.c)(i).then(function(a){a.apply_mat_list.length>0&&(t&&(e.list[e.tabIndex].billList.length=0),e.pageModels[e.tabIndex].curr_page=a.curr_page,e.pageModels[e.tabIndex].total_page=a.total_page,e.list[e.tabIndex].billList=e.list[e.tabIndex].billList.concat(a.apply_mat_list))}).catch(function(t){$toast.show(t.description)})}},activated:function(){console.log("activated"),this.getData(!0)}}},589:function(t,e,a){e=t.exports=a(220)(),e.push([t.i,"","",{version:3,sources:[],names:[],mappings:"",file:"list.vue",sourceRoot:""}])},690:function(t,e,a){var i=a(589);"string"==typeof i&&(i=[[t.i,i,""]]),i.locals&&(t.exports=i.locals);a(221)("634f26e4",i,!0)},800:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{directives:[{name:"nav",rawName:"v-nav",value:{hideNavbar:!0},expression:"{hideNavbar:true}"}],staticClass:"mw-page page storage"},[a("div",{staticClass:"mw-page-header"},[a("i",{staticClass:"left icon ion-ios-arrow-back",on:{click:function(e){t.$router.go(-1)}}}),t._v(" "),a("span",[t._v("领料单列表")]),t._v(" "),a("i",{staticClass:"right"})]),t._v(" "),a("div",{staticClass:"mw-page-content has-tab"},[a("div",{staticStyle:{height:"60px"}},[a("tabs",{attrs:{"tab-items":t.tabs,"tab-index":t.tabIndex,"on-tab-click":t.onTabClick}})],1),t._v(" "),a("div",{staticStyle:{flex:"1"}},[a("div",{directives:[{name:"show",rawName:"v-show",value:0===t.tabIndex,expression:"tabIndex===0"}],staticClass:"content"},[a("scroll",{staticClass:"page-content",staticStyle:{"-webkit-overflow-scrolling":"touch"},attrs:{"on-infinite":t.onInfinite,"on-refresh":t.onRefresh}},[t._l(t.list[0].billList,function(e){return a("div",[a("swipe-item",{staticClass:"req-item",attrs:{obj:e,swipeItemText:"删除"},on:{ItemClick:function(a){t.onItemClick(e)},SwipeItemClick:function(a){t.delItem(e)}}},[a("div",{staticClass:"req-item-title"},[t._v("领料单号 ："),a("span",{staticClass:"vi-color"},[t._v(t._s(e.apply_no))]),a("i",{staticClass:"icon ion-ios-arrow-right"})]),t._v(" "),a("div",{staticClass:"req-item-content"},[a("span",[t._v("部门 : "+t._s(e.dep_name))]),t._v(" "),a("span",[t._v("日期 : "+t._s(e.request_date))])])])],1)}),t._v(" "),t.pageModels[0].curr_page>=t.pageModels[0].total_page?a("div",{staticClass:"text-center",attrs:{slot:"infinite"},slot:"infinite"},[t._v("没有更多数据")]):t._e()],2)],1),t._v(" "),a("div",{directives:[{name:"show",rawName:"v-show",value:1===t.tabIndex,expression:"tabIndex===1"}],staticClass:"content"},[a("scroll",{staticClass:"page-content",staticStyle:{"-webkit-overflow-scrolling":"touch"},attrs:{"on-infinite":t.onInfinite,"on-refresh":t.onRefresh}},[t._l(t.list[1].billList,function(e){return a("div",[a("swipe-item",{staticClass:"req-item",attrs:{obj:e,swipeItemText:"删除"},on:{ItemClick:function(a){t.onItemClick(e)},SwipeItemClick:function(a){t.delItem(e)}}},[a("div",{staticClass:"req-item-title"},[t._v("领料单号 ："),a("span",{staticClass:"vi-color"},[t._v(t._s(e.apply_no))]),a("i",{staticClass:"icon ion-ios-arrow-right"})]),t._v(" "),a("div",{staticClass:"req-item-content"},[a("span",[t._v("部门 : "+t._s(e.dep_name))]),t._v(" "),a("span",[t._v("日期 : "+t._s(e.request_date))])])])],1)}),t._v(" "),t.pageModels[1].curr_page>=t.pageModels[1].total_page?a("div",{staticClass:"text-center",attrs:{slot:"infinite"},slot:"infinite"},[t._v("没有更多数据")]):t._e()],2)],1),t._v(" "),a("div",{directives:[{name:"show",rawName:"v-show",value:2===t.tabIndex,expression:"tabIndex===2"}],staticClass:"content"},[a("scroll",{staticClass:"page-content",staticStyle:{"-webkit-overflow-scrolling":"touch"},attrs:{"on-infinite":t.onInfinite,"on-refresh":t.onRefresh}},[t._l(t.list[2].billList,function(e){return a("div",[a("swipe-item",{staticClass:"req-item",attrs:{canSwipe:!1,obj:e,swipeItemText:"删除"},on:{ItemClick:function(a){t.onItemClick(e)},SwipeItemClick:function(a){t.delItem(e)}},nativeOn:{click:function(a){t.onItemClick(e)}}},[a("div",{staticClass:"req-item-title"},[t._v("领料单号 ："),a("span",{staticClass:"vi-color"},[t._v(t._s(e.apply_no))]),a("i",{staticClass:"icon ion-ios-arrow-right"})]),t._v(" "),a("div",{staticClass:"req-item-content"},[a("span",[t._v("部门 : "+t._s(e.dep_name))]),t._v(" "),a("span",[t._v("日期 : "+t._s(e.request_date))])])])],1)}),t._v(" "),t.pageModels[2].curr_page>=t.pageModels[2].total_page?a("div",{staticClass:"text-center",attrs:{slot:"infinite"},slot:"infinite"},[t._v("没有更多数据")]):t._e()],2)],1),t._v(" "),a("div",{directives:[{name:"show",rawName:"v-show",value:3===t.tabIndex,expression:"tabIndex===3"}],staticClass:"content"},[a("scroll",{staticClass:"page-content",staticStyle:{"-webkit-overflow-scrolling":"touch"},attrs:{"on-infinite":t.onInfinite,"on-refresh":t.onRefresh}},[t._l(t.list[3].billList,function(e){return a("div",[a("swipe-item",{staticClass:"req-item",attrs:{obj:e,swipeItemText:"删除"},on:{ItemClick:function(a){t.onItemClick(e)},SwipeItemClick:function(a){t.delItem(e)}}},[a("div",{staticClass:"req-item-title"},[t._v("领料单号 ："),a("span",{staticClass:"vi-color"},[t._v(t._s(e.apply_no))]),a("i",{staticClass:"icon ion-ios-arrow-right"})]),t._v(" "),a("div",{staticClass:"req-item-content"},[a("span",[t._v("部门 : "+t._s(e.dep_name))]),t._v(" "),a("span",[t._v("日期 : "+t._s(e.request_date))])])])],1)}),t._v(" "),t.pageModels[3].curr_page>=t.pageModels[3].total_page?a("div",{staticClass:"text-center",attrs:{slot:"infinite"},slot:"infinite"},[t._v("没有更多数据")]):t._e()],2)],1)])]),t._v(" "),a("div",{staticClass:"mw-page-footer"},[a("div",{staticClass:"btns"},[a("div",{staticClass:"btn",on:{click:function(e){t.$router.forward("/storage/applyMatBill/detail/new/null")}}},[t._v("\n        新增\n      ")])])])])},staticRenderFns:[]}}});
//# sourceMappingURL=27.d18139e19168a514524b.js.map
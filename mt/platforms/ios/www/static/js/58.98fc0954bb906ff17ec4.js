webpackJsonp([58],{283:function(t,e,i){i(799);var a=i(0)(i(481),i(947),"data-v-6fb0a93e",null);t.exports=a.exports},370:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});e.default={name:"cstItem",data:function(){return{selectId:"select-"+Math.random().toString(36).substring(3,6)}},props:{label:{type:String,required:!0},value:{type:"",default:""},placeholder:{type:String,default:""},canEdit:{type:Boolean,default:!0},hasArrow:{type:Boolean,default:!1},arrowText:{type:[String,Number],default:""},arrowClick:{type:Function},editType:{type:String,default:"text"},isSelect:{type:Boolean,default:!1},options:{type:Array},action:{type:String,default:"check"},color:""},methods:{noteClick:function(){this.arrowClick&&this.arrowClick()},input:function(t){var e=t.target.value;this.$emit("input",e)}}}},371:function(t,e,i){e=t.exports=i(225)(),e.push([t.i,"\n.compute-item[data-v-19336c8e] {\n  min-height: 49px;\n  position: relative;\n  padding: 12px 15px;\n  background-color: white;\n  display: -ms-flexbox;\n  display: flex;\n}\n.compute-item.noneBorder > .hairlineTop[data-v-19336c8e]:before {\n    background: red;\n}\n.compute-item .arrow[data-v-19336c8e] {\n    color: #DDD;\n    position: absolute;\n    right: 15px;\n    top: 14px;\n    font-size: 20px;\n}\n.compute-item .arrow.down[data-v-19336c8e] {\n      top: 13px;\n}\n.compute-item .label[data-v-19336c8e] {\n    -ms-flex: 0 0  auto;\n        flex: 0 0  auto;\n    height: 100%;\n    display: table;\n    padding: 7px 10px 7px 0px;\n    max-width: 52%;\n    color: #484746;\n    text-align: left;\n    line-height: 12px;\n}\n.compute-item .value[data-v-19336c8e] {\n    -ms-flex: 1 0 auto;\n        flex: 1 0 auto;\n    text-align: right;\n}\n.compute-item .value input[data-v-19336c8e] {\n      margin: 0;\n      padding: 0;\n      border: none;\n      color: #ababab;\n      width: 100%;\n      height: 24px;\n      line-height: 24px;\n      text-align: right;\n      text-overflow: ellipsis;\n      white-space: nowrap;\n      overflow: hidden;\n}\n.compute-item .value span[data-v-19336c8e] {\n      height: 24px;\n      line-height: 24px;\n}\n.compute-item .value span.color[data-v-19336c8e] {\n        display: inline-block;\n        width: 20px;\n        height: 20px;\n        border-radius: 50%;\n}\n.compute-item .note[data-v-19336c8e] {\n    -ms-flex: 0 0 5%;\n        flex: 0 0 5%;\n}\n.compute-item .note .item-notes[data-v-19336c8e] {\n      float: right;\n      color: #ababab;\n      line-height: 24px;\n      /*padding: 0 5px;*/\n      padding-right: 15px;\n}\n","",{version:3,sources:["/Users/zxf/code/motooling/mt-app/webs-mtapp/mtApp/mt/www_dev/src/components/cstItem.vue"],names:[],mappings:";AACA;EACE,iBAAiB;EACjB,mBAAmB;EACnB,mBAAmB;EACnB,wBAAwB;EACxB,qBAAqB;EACrB,cAAc;CACf;AACD;IACI,gBAAgB;CACnB;AACD;IACI,YAAY;IACZ,mBAAmB;IACnB,YAAY;IACZ,UAAU;IACV,gBAAgB;CACnB;AACD;MACM,UAAU;CACf;AACD;IACI,oBAAoB;QAChB,gBAAgB;IACpB,aAAa;IACb,eAAe;IACf,0BAA0B;IAC1B,eAAe;IACf,eAAe;IACf,iBAAiB;IACjB,kBAAkB;CACrB;AACD;IACI,mBAAmB;QACf,eAAe;IACnB,kBAAkB;CACrB;AACD;MACM,UAAU;MACV,WAAW;MACX,aAAa;MACb,eAAe;MACf,YAAY;MACZ,aAAa;MACb,kBAAkB;MAClB,kBAAkB;MAClB,wBAAwB;MACxB,oBAAoB;MACpB,iBAAiB;CACtB;AACD;MACM,aAAa;MACb,kBAAkB;CACvB;AACD;QACQ,sBAAsB;QACtB,YAAY;QACZ,aAAa;QACb,mBAAmB;CAC1B;AACD;IACI,iBAAiB;QACb,aAAa;CACpB;AACD;MACM,aAAa;MACb,eAAe;MACf,kBAAkB;MAClB,mBAAmB;MACnB,oBAAoB;CACzB",file:"cstItem.vue",sourcesContent:["\n.compute-item[data-v-19336c8e] {\n  min-height: 49px;\n  position: relative;\n  padding: 12px 15px;\n  background-color: white;\n  display: -ms-flexbox;\n  display: flex;\n}\n.compute-item.noneBorder > .hairlineTop[data-v-19336c8e]:before {\n    background: red;\n}\n.compute-item .arrow[data-v-19336c8e] {\n    color: #DDD;\n    position: absolute;\n    right: 15px;\n    top: 14px;\n    font-size: 20px;\n}\n.compute-item .arrow.down[data-v-19336c8e] {\n      top: 13px;\n}\n.compute-item .label[data-v-19336c8e] {\n    -ms-flex: 0 0  auto;\n        flex: 0 0  auto;\n    height: 100%;\n    display: table;\n    padding: 7px 10px 7px 0px;\n    max-width: 52%;\n    color: #484746;\n    text-align: left;\n    line-height: 12px;\n}\n.compute-item .value[data-v-19336c8e] {\n    -ms-flex: 1 0 auto;\n        flex: 1 0 auto;\n    text-align: right;\n}\n.compute-item .value input[data-v-19336c8e] {\n      margin: 0;\n      padding: 0;\n      border: none;\n      color: #ababab;\n      width: 100%;\n      height: 24px;\n      line-height: 24px;\n      text-align: right;\n      text-overflow: ellipsis;\n      white-space: nowrap;\n      overflow: hidden;\n}\n.compute-item .value span[data-v-19336c8e] {\n      height: 24px;\n      line-height: 24px;\n}\n.compute-item .value span.color[data-v-19336c8e] {\n        display: inline-block;\n        width: 20px;\n        height: 20px;\n        border-radius: 50%;\n}\n.compute-item .note[data-v-19336c8e] {\n    -ms-flex: 0 0 5%;\n        flex: 0 0 5%;\n}\n.compute-item .note .item-notes[data-v-19336c8e] {\n      float: right;\n      color: #ababab;\n      line-height: 24px;\n      /*padding: 0 5px;*/\n      padding-right: 15px;\n}\n"],sourceRoot:""}])},372:function(t,e,i){var a=i(371);"string"==typeof a&&(a=[[t.i,a,""]]),a.locals&&(t.exports=a.locals);i(226)("6ea14c28",a,!0)},373:function(t,e,i){i(372);var a=i(0)(i(370),i(374),"data-v-19336c8e",null);t.exports=a.exports},374:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"compute-item"},[i("span",{staticClass:"label"},[t._v(t._s(t.label))]),t._v(" "),i("div",{directives:[{name:"show",rawName:"v-show",value:"color"!==t.editType,expression:"editType!=='color'"}],staticClass:"value"},[t.canEdit&&"check"!==t.action&&"approve"!==t.action&&"checkOnly"!==t.action?i("input",{ref:"input",attrs:{type:t.editType,placeholder:t.placeholder},domProps:{value:t.value},on:{input:t.input}}):i("span",[t._v(t._s(t.value))])]),t._v(" "),i("div",{directives:[{name:"show",rawName:"v-show",value:"color"===t.editType,expression:"editType==='color'"}],staticClass:"value"},[i("span",{staticClass:"color",style:{background:t.color}})]),t._v(" "),i("div",{directives:[{name:"show",rawName:"v-show",value:t.hasArrow,expression:"hasArrow"}],staticClass:"note"},[i("span",{directives:[{name:"show",rawName:"v-show",value:t.hasArrow&&t.arrowText,expression:"hasArrow&&arrowText"}],staticClass:"item-notes",on:{click:t.noteClick}},[t._v(t._s(t.arrowText))]),t._v(" "),i("i",{directives:[{name:"show",rawName:"v-show",value:t.hasArrow,expression:"hasArrow"}],staticClass:"icon ion-ios-arrow-right arrow "})]),t._v(" "),i("div",{staticClass:"hairline-top"})])},staticRenderFns:[]}},402:function(t,e,i){"use strict";function a(t){return i.i(l.a)({url:"/api/iv/apply_mat_bill/list",method:"post",data:t})}function n(t){return i.i(l.a)({url:"/api/iv/apply_mat_bill/detail",method:"post",data:t})}function s(t){return i.i(l.a)({url:"/api/iv/apply_mat_bill/modify",method:"post",data:t})}function o(t){return i.i(l.a)({url:"/api/workflow/process",method:"post",data:t})}e.d=a,e.c=n,e.a=s,e.b=o;var l=i(10)},481:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var a=i(373),n=i.n(a),s=i(402),o=i(20),l=i.n(o);e.default={name:"MaterialRequisition",components:{SwipeItem:l.a,CstItem:n.a},data:function(){return{tabs:["制定中","审批中","已审批","退回"],tabIndex:0,pageModels:[{curr_page:0,page_size:10,total_page:0},{curr_page:0,page_size:10,total_page:0},{curr_page:0,page_size:10,total_page:0},{curr_page:0,page_size:10,total_page:0}],query:{aplly_type:[1,2],approve_step:[]},list:[{billList:[]},{billList:[]},{billList:[]},{billList:[]}]}},mounted:function(){this.query.approve_step.push("step10")},methods:{onTabClick:function(t){if(console.log(t),4!==t){switch(this.tabIndex=t,t){case 0:this.query.approve_step.length=0,this.query.approve_step.push("step10");break;case 1:this.query.approve_step.length=0,this.query.approve_step.push("step20"),this.query.approve_step.push("step30");break;case 2:this.query.approve_step.length=0,this.query.approve_step.push("step0");break;case 3:this.query.approve_step.length=0,this.query.approve_step.push("step11")}0===this.list[this.tabIndex].billList.length&&this.getData(!0)}},onInfinite:function(t){var e=this;if(e.pageModels[this.tabIndex].total_page<=e.pageModels[this.tabIndex].curr_page)return void t();setTimeout(function(){e.pageModels[e.tabIndex].total_page<2?t():(e.getData(),t())},500)},onRefresh:function(t){var e=this;setTimeout(function(){e.pageModels[e.tabIndex].curr_page=0,e.getData(!0),t()},500)},onItemClick:function(t){this.$router.forward({path:"MaterialRequisition/detaile",query:{action:"check",apply_id:t.apply_id}})},delItem:function(t){},getData:function(t){var e=this,a={curr_page:t?1:this.pageModels[this.tabIndex].curr_page+1,page_size:this.pageModels[this.tabIndex].page_size,query:this.query};i.i(s.d)(a).then(function(i){i.apply_mat_list.length>0&&(t&&(e.list[e.tabIndex].billList.length=0),e.pageModels[e.tabIndex].curr_page=i.curr_page,e.pageModels[e.tabIndex].total_page=i.total_page,e.list[e.tabIndex].billList=e.list[e.tabIndex].billList.concat(i.apply_mat_list))}).catch(function(t){$toast.show(t.description)})}},activated:function(){console.log("activated"),this.getData(!0)}}},660:function(t,e,i){e=t.exports=i(225)(),e.push([t.i,"","",{version:3,sources:[],names:[],mappings:"",file:"index.vue",sourceRoot:""}])},799:function(t,e,i){var a=i(660);"string"==typeof a&&(a=[[t.i,a,""]]),a.locals&&(t.exports=a.locals);i(226)("1fd72ad2",a,!0)},947:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{directives:[{name:"nav",rawName:"v-nav",value:{hideNavbar:!0},expression:"{hideNavbar:true}"}],staticClass:"mw-page page storage"},[i("div",{staticClass:"mw-page-header"},[i("i",{staticClass:"left icon ion-ios-arrow-back",on:{click:function(e){t.$router.go(-1)}}}),t._v(" "),i("span",[t._v("领料单列表")]),t._v(" "),i("i",{staticClass:"right"})]),t._v(" "),i("div",{staticClass:"mw-page-content has-tab"},[i("div",{staticStyle:{height:"60px"}},[i("tabs",{attrs:{"tab-items":t.tabs,"tab-index":t.tabIndex,"on-tab-click":t.onTabClick}})],1),t._v(" "),i("div",{staticStyle:{flex:"1"}},[i("div",{directives:[{name:"show",rawName:"v-show",value:0===t.tabIndex,expression:"tabIndex===0"}],staticClass:"content"},[i("scroll",{staticClass:"page-content",staticStyle:{"-webkit-overflow-scrolling":"touch"},attrs:{"on-infinite":t.onInfinite,"on-refresh":t.onRefresh}},[t._l(t.list[0].billList,function(e){return i("div",[i("swipe-item",{staticClass:"req-item",attrs:{obj:e,swipeItemText:"删除"},on:{ItemClick:function(i){t.onItemClick(e)},SwipeItemClick:function(i){t.delItem(e)}}},[i("div",{staticClass:"req-item-title"},[t._v("领料单号 ："),i("span",{staticClass:"vi-color"},[t._v(t._s(e.apply_no))]),i("i",{staticClass:"icon ion-ios-arrow-right"})]),t._v(" "),i("div",{staticClass:"req-item-content"},[i("span",[t._v("部门 : "+t._s(e.dep_name))]),t._v(" "),i("span",[t._v("日期 : "+t._s(e.request_date))])])])],1)}),t._v(" "),t.pageModels[0].curr_page>=t.pageModels[0].total_page?i("div",{staticClass:"text-center",attrs:{slot:"infinite"},slot:"infinite"},[t._v("没有更多数据")]):t._e()],2)],1),t._v(" "),i("div",{directives:[{name:"show",rawName:"v-show",value:1===t.tabIndex,expression:"tabIndex===1"}],staticClass:"content"},[i("scroll",{staticClass:"page-content",staticStyle:{"-webkit-overflow-scrolling":"touch"},attrs:{"on-infinite":t.onInfinite,"on-refresh":t.onRefresh}},[t._l(t.list[1].billList,function(e){return i("div",[i("swipe-item",{staticClass:"req-item",attrs:{obj:e,swipeItemText:"删除"},on:{ItemClick:function(i){t.onItemClick(e)},SwipeItemClick:function(i){t.delItem(e)}}},[i("div",{staticClass:"req-item-title"},[t._v("领料单号 ："),i("span",{staticClass:"vi-color"},[t._v(t._s(e.apply_no))]),i("i",{staticClass:"icon ion-ios-arrow-right"})]),t._v(" "),i("div",{staticClass:"req-item-content"},[i("span",[t._v("部门 : "+t._s(e.dep_name))]),t._v(" "),i("span",[t._v("日期 : "+t._s(e.request_date))])])])],1)}),t._v(" "),t.pageModels[1].curr_page>=t.pageModels[1].total_page?i("div",{staticClass:"text-center",attrs:{slot:"infinite"},slot:"infinite"},[t._v("没有更多数据")]):t._e()],2)],1),t._v(" "),i("div",{directives:[{name:"show",rawName:"v-show",value:2===t.tabIndex,expression:"tabIndex===2"}],staticClass:"content"},[i("scroll",{staticClass:"page-content",staticStyle:{"-webkit-overflow-scrolling":"touch"},attrs:{"on-infinite":t.onInfinite,"on-refresh":t.onRefresh}},[t._l(t.list[2].billList,function(e){return i("div",[i("swipe-item",{staticClass:"req-item",attrs:{canSwipe:!1,obj:e,swipeItemText:"删除"},on:{ItemClick:function(i){t.onItemClick(e)},SwipeItemClick:function(i){t.delItem(e)}},nativeOn:{click:function(i){t.onItemClick(e)}}},[i("div",{staticClass:"req-item-title"},[t._v("领料单号 ："),i("span",{staticClass:"vi-color"},[t._v(t._s(e.apply_no))]),i("i",{staticClass:"icon ion-ios-arrow-right"})]),t._v(" "),i("div",{staticClass:"req-item-content"},[i("span",[t._v("部门 : "+t._s(e.dep_name))]),t._v(" "),i("span",[t._v("日期 : "+t._s(e.request_date))])])])],1)}),t._v(" "),t.pageModels[2].curr_page>=t.pageModels[2].total_page?i("div",{staticClass:"text-center",attrs:{slot:"infinite"},slot:"infinite"},[t._v("没有更多数据")]):t._e()],2)],1),t._v(" "),i("div",{directives:[{name:"show",rawName:"v-show",value:3===t.tabIndex,expression:"tabIndex===3"}],staticClass:"content"},[i("scroll",{staticClass:"page-content",staticStyle:{"-webkit-overflow-scrolling":"touch"},attrs:{"on-infinite":t.onInfinite,"on-refresh":t.onRefresh}},[t._l(t.list[3].billList,function(e){return i("div",[i("swipe-item",{staticClass:"req-item",attrs:{obj:e,swipeItemText:"删除"},on:{ItemClick:function(i){t.onItemClick(e)},SwipeItemClick:function(i){t.delItem(e)}}},[i("div",{staticClass:"req-item-title"},[t._v("领料单号 ："),i("span",{staticClass:"vi-color"},[t._v(t._s(e.apply_no))]),i("i",{staticClass:"icon ion-ios-arrow-right"})]),t._v(" "),i("div",{staticClass:"req-item-content"},[i("span",[t._v("部门 : "+t._s(e.dep_name))]),t._v(" "),i("span",[t._v("日期 : "+t._s(e.request_date))])])])],1)}),t._v(" "),t.pageModels[3].curr_page>=t.pageModels[3].total_page?i("div",{staticClass:"text-center",attrs:{slot:"infinite"},slot:"infinite"},[t._v("没有更多数据")]):t._e()],2)],1)])]),t._v(" "),i("div",{staticClass:"mw-page-footer"},[i("div",{staticClass:"btns"},[i("div",{staticClass:"btn",on:{click:function(e){t.$router.forward({path:"MaterialRequisition/detaile",query:{action:"new"}})}}},[t._v("\n        新增\n      ")])])])])},staticRenderFns:[]}}});
//# sourceMappingURL=58.98fc0954bb906ff17ec4.js.map
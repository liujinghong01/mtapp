webpackJsonp([21],{306:function(t,e,a){a(617);var n=a(0)(a(463),a(726),"data-v-36a1c18c",null);t.exports=n.exports},328:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});e.default={name:"cstItem",data:function(){return{selectId:"select-"+Math.random().toString(36).substring(3,6)}},props:{label:{type:String,required:!0},value:{type:"",default:""},placeholder:{type:String,default:""},canEdit:{type:Boolean,default:!0},hasArrow:{type:Boolean,default:!1},arrowText:{type:[String,Number],default:""},arrowClick:{type:Function},editType:{type:String,default:"text"},isSelect:{type:Boolean,default:!1},options:{type:Array},action:{type:String,default:"check"},color:""},methods:{noteClick:function(){this.arrowClick&&this.arrowClick()},input:function(t){var e=t.target.value;this.$emit("input",e)}}}},329:function(t,e,a){e=t.exports=a(220)(),e.push([t.i,"\n.compute-item[data-v-19336c8e] {\n  min-height: 49px;\n  position: relative;\n  padding: 12px 15px;\n  background-color: white;\n  display: -ms-flexbox;\n  display: flex;\n}\n.compute-item.noneBorder > .hairlineTop[data-v-19336c8e]:before {\n    background: red;\n}\n.compute-item .arrow[data-v-19336c8e] {\n    color: #DDD;\n    position: absolute;\n    right: 15px;\n    top: 14px;\n    font-size: 20px;\n}\n.compute-item .arrow.down[data-v-19336c8e] {\n      top: 13px;\n}\n.compute-item .label[data-v-19336c8e] {\n    -ms-flex: 0 0  auto;\n        flex: 0 0  auto;\n    height: 100%;\n    display: table;\n    padding: 7px 10px 7px 0px;\n    max-width: 52%;\n    color: #484746;\n    text-align: left;\n    line-height: 12px;\n}\n.compute-item .value[data-v-19336c8e] {\n    -ms-flex: 1 0 auto;\n        flex: 1 0 auto;\n    text-align: right;\n}\n.compute-item .value input[data-v-19336c8e] {\n      margin: 0;\n      padding: 0;\n      border: none;\n      color: #ababab;\n      width: 100%;\n      height: 24px;\n      line-height: 24px;\n      text-align: right;\n      text-overflow: ellipsis;\n      white-space: nowrap;\n      overflow: hidden;\n}\n.compute-item .value span[data-v-19336c8e] {\n      height: 24px;\n      line-height: 24px;\n}\n.compute-item .value span.color[data-v-19336c8e] {\n        display: inline-block;\n        width: 20px;\n        height: 20px;\n        border-radius: 50%;\n}\n.compute-item .note[data-v-19336c8e] {\n    -ms-flex: 0 0 5%;\n        flex: 0 0 5%;\n}\n.compute-item .note .item-notes[data-v-19336c8e] {\n      float: right;\n      color: #ababab;\n      line-height: 24px;\n      /*padding: 0 5px;*/\n      padding-right: 15px;\n}\n","",{version:3,sources:["E:/weibosi/webs-mt/mt/www_dev/src/components/cstItem.vue"],names:[],mappings:";AACA;EACE,iBAAiB;EACjB,mBAAmB;EACnB,mBAAmB;EACnB,wBAAwB;EACxB,qBAAqB;EACrB,cAAc;CACf;AACD;IACI,gBAAgB;CACnB;AACD;IACI,YAAY;IACZ,mBAAmB;IACnB,YAAY;IACZ,UAAU;IACV,gBAAgB;CACnB;AACD;MACM,UAAU;CACf;AACD;IACI,oBAAoB;QAChB,gBAAgB;IACpB,aAAa;IACb,eAAe;IACf,0BAA0B;IAC1B,eAAe;IACf,eAAe;IACf,iBAAiB;IACjB,kBAAkB;CACrB;AACD;IACI,mBAAmB;QACf,eAAe;IACnB,kBAAkB;CACrB;AACD;MACM,UAAU;MACV,WAAW;MACX,aAAa;MACb,eAAe;MACf,YAAY;MACZ,aAAa;MACb,kBAAkB;MAClB,kBAAkB;MAClB,wBAAwB;MACxB,oBAAoB;MACpB,iBAAiB;CACtB;AACD;MACM,aAAa;MACb,kBAAkB;CACvB;AACD;QACQ,sBAAsB;QACtB,YAAY;QACZ,aAAa;QACb,mBAAmB;CAC1B;AACD;IACI,iBAAiB;QACb,aAAa;CACpB;AACD;MACM,aAAa;MACb,eAAe;MACf,kBAAkB;MAClB,mBAAmB;MACnB,oBAAoB;CACzB",file:"cstItem.vue",sourcesContent:["\n.compute-item[data-v-19336c8e] {\n  min-height: 49px;\n  position: relative;\n  padding: 12px 15px;\n  background-color: white;\n  display: -ms-flexbox;\n  display: flex;\n}\n.compute-item.noneBorder > .hairlineTop[data-v-19336c8e]:before {\n    background: red;\n}\n.compute-item .arrow[data-v-19336c8e] {\n    color: #DDD;\n    position: absolute;\n    right: 15px;\n    top: 14px;\n    font-size: 20px;\n}\n.compute-item .arrow.down[data-v-19336c8e] {\n      top: 13px;\n}\n.compute-item .label[data-v-19336c8e] {\n    -ms-flex: 0 0  auto;\n        flex: 0 0  auto;\n    height: 100%;\n    display: table;\n    padding: 7px 10px 7px 0px;\n    max-width: 52%;\n    color: #484746;\n    text-align: left;\n    line-height: 12px;\n}\n.compute-item .value[data-v-19336c8e] {\n    -ms-flex: 1 0 auto;\n        flex: 1 0 auto;\n    text-align: right;\n}\n.compute-item .value input[data-v-19336c8e] {\n      margin: 0;\n      padding: 0;\n      border: none;\n      color: #ababab;\n      width: 100%;\n      height: 24px;\n      line-height: 24px;\n      text-align: right;\n      text-overflow: ellipsis;\n      white-space: nowrap;\n      overflow: hidden;\n}\n.compute-item .value span[data-v-19336c8e] {\n      height: 24px;\n      line-height: 24px;\n}\n.compute-item .value span.color[data-v-19336c8e] {\n        display: inline-block;\n        width: 20px;\n        height: 20px;\n        border-radius: 50%;\n}\n.compute-item .note[data-v-19336c8e] {\n    -ms-flex: 0 0 5%;\n        flex: 0 0 5%;\n}\n.compute-item .note .item-notes[data-v-19336c8e] {\n      float: right;\n      color: #ababab;\n      line-height: 24px;\n      /*padding: 0 5px;*/\n      padding-right: 15px;\n}\n"],sourceRoot:""}])},330:function(t,e,a){var n=a(329);"string"==typeof n&&(n=[[t.i,n,""]]),n.locals&&(t.exports=n.locals);a(221)("6ea14c28",n,!0)},331:function(t,e,a){a(330);var n=a(0)(a(328),a(332),"data-v-19336c8e",null);t.exports=n.exports},332:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"compute-item"},[a("span",{staticClass:"label"},[t._v(t._s(t.label))]),t._v(" "),a("div",{directives:[{name:"show",rawName:"v-show",value:"color"!==t.editType,expression:"editType!=='color'"}],staticClass:"value"},[t.canEdit&&"check"!==t.action&&"approve"!==t.action?"check"!==t.action||"approve"!==t.action?a("input",{ref:"input",attrs:{type:t.editType,placeholder:t.placeholder},domProps:{value:t.value},on:{input:t.input}}):t._e():a("span",[t._v(t._s(t.value))])]),t._v(" "),a("div",{directives:[{name:"show",rawName:"v-show",value:"color"===t.editType,expression:"editType==='color'"}],staticClass:"value"},[a("span",{staticClass:"color",style:{background:t.color}})]),t._v(" "),a("div",{directives:[{name:"show",rawName:"v-show",value:t.hasArrow,expression:"hasArrow"}],staticClass:"note"},[a("span",{directives:[{name:"show",rawName:"v-show",value:t.hasArrow&&t.arrowText,expression:"hasArrow&&arrowText"}],staticClass:"item-notes",on:{click:t.noteClick}},[t._v(t._s(t.arrowText))]),t._v(" "),a("i",{directives:[{name:"show",rawName:"v-show",value:t.hasArrow,expression:"hasArrow"}],staticClass:"icon ion-ios-arrow-right arrow "})]),t._v(" "),a("div",{staticClass:"hairline-top"})])},staticRenderFns:[]}},333:function(t,e,a){"use strict";function n(t){return a.i(d.a)({url:"/api/iv/storehouse/modify",method:"post",data:t})}function i(t){return a.i(d.a)({url:"/api/iv/storehouse/list",method:"post",data:t})}function o(t){return a.i(d.a)({url:"/api/iv/storehouse/detail",method:"post",data:t})}function s(t){return a.i(d.a)({url:"/api/iv/storehouse/delete",method:"post",data:t})}function c(t){return a.i(d.a)({url:"/api/iv/storeroom/delete",method:"post",data:t})}function r(t){return a.i(d.a)({url:"/api/iv/mat_type/list",method:"post",data:t})}function l(t){return a.i(d.a)({url:"/api/iv/mat_type/modify",method:"post",data:t})}function p(t){return a.i(d.a)({url:"/api/iv/store_room/search_mat",method:"post",data:t})}e.g=n,e.c=i,e.e=o,e.h=s,e.f=c,e.b=r,e.d=l,e.a=p;var d=a(7)},349:function(t,e,a){"use strict";function n(t){return a.i(c.a)({url:"/api/iv/store_check_bill/list",method:"post",data:t})}function i(t){return a.i(c.a)({url:"/api/iv/store_check_bill/detail",method:"post",data:t})}function o(t){return a.i(c.a)({url:"/api/iv/store_check_bill/add",method:"post",data:t})}function s(t){return a.i(c.a)({url:"/api/iv/store_check_bill/modify",method:"post",data:t})}e.d=n,e.a=i,e.c=o,e.b=s;var c=a(7)},359:function(t,e,a){"use strict";var n=a(8),i=a.n(n),o={};console.log(window.cordova);var s={};try{s=window.cordova?window.cordova.plugins.barcodeScanner:""}catch(t){console.log(window.cordova.plugins)}o.scanbar=function(t,e){s.scan(function(a){if(a.cancelled)return void e({text:"取消操作"});t(a)},function(t){console.log(i()(t)),e(t)})},e.a=o},463:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n=a(349),i=a(331),o=a.n(i),s=a(359),c=(a(333),a(20)),r=a.n(c),l=a(86),p=a.n(l);e.default={name:"inventoryDetail",components:{MwPrompt:p.a,SwipeItem:r.a,CstItem:o.a},data:function(){return{tabIndex:0,check_info:{store_house_name:"",store_house_id:"",check_bill_no:"",check_bill_id:"",operator_name:"",operator_id:"",mat_type_list:[]},reason:"",showPop:!1,currItem:{},pageModels:[{curr_page:0,page_size:10,total_page:0},{curr_page:0,page_size:10,total_page:0}],list:[{mats:[]},{mats:[]}],query:{check_bill_id:"",liquidate_flag:"2",mat_desc:"",mat_no:"",mat_type_ids:[]},filter_mat_types:[],checkAll:!1}},mounted:function(){var t=this.$route.params.inventory;t&&(this.check_info=JSON.parse(t),this.query.check_bill_id=this.check_info.check_bill_id,this.getData(!0))},methods:{onMenuClick:function(){},onTabClick:function(t){t!==this.tabIndex&&(this.tabIndex=t,this.query.liquidate_flag=t?"1":"2",0===this.list[t].mats.length&&this.getData(!0))},onRefresh:function(t){var e=this;setTimeout(function(){e.getData(!0),t()},400)},onInfinite:function(t){var e=this;if(e.pageModels[e.tabIndex].total_page<=e.pageModels[e.tabIndex].curr_page)return void t();setTimeout(function(){e.pageModels[e.tabIndex].total_page<2?t():(e.getData(!0),t())},500)},getData:function(t){var e=this,i={curr_page:t?1:1+this.pageModels[this.tabIndex].curr_page,page_size:this.pageModels[this.tabIndex].page_size,query:this.query};a.i(n.a)(i).then(function(a){a.check_detail_list.length>0&&(t&&(e.list[e.tabIndex].mats.length=0),e.pageModels[e.tabIndex].curr_page=a.curr_page,e.pageModels[e.tabIndex].total_page=a.total_page,e.list[e.tabIndex].mats=e.list[e.tabIndex].mats.concat(a.check_detail_list))}).catch(function(t){$toast.show(t.description)})},scan:function(){var t=this;s.a.scanbar(function(e){console.log(e),t.queryMatByQR(e.text)},function(t){console.log(t)})},queryMatByQR:function(t){this.query.mat_no=t;var e={curr_page:1,page_size:10,query:this.query};a.i(n.a)(e).then(function(t){console.log(t)}).catch(function(t){$toast.show(t.description)})},addCount:function(t){t.check_real_qty?this.$set(t,"check_real_qty",++t.check_real_qty):this.$set(t,"check_real_qty",1)},minCount:function(t){if(0===t.check_real_qty)return void $toast.show("不能再减了");this.$set(t,"check_real_qty",--t.check_real_qty)},countChange:function(t){t.check_real_qty<0&&this.$set(t,"check_real_qty",0)},promptVal:function(t){this.reason=t},confirm:function(t){var e=this;if(t&&t.check_real_qty!==t.check_bill_qty&&!this.showPop)return this.currItem=t,void(this.showPop=!0);if(!this.reason)return void $toast.show("请填写盈亏原因");var i={check_info:{check_bill_id:this.currItem.check_bill_id,check_bill_no:this.currItem.check_bill_no,mat_id:this.currItem.mat_id,check_real_qty:this.currItem.check_real_qty,check_profit_loss_reason:this.reason}};a.i(n.b)(i).then(function(t){e.showPop=!1,e.list[e.tabIndex].mats=e.list[e.tabIndex].mats.filter(function(t){return t.mat_id!=e.currItem.mat_id})}).catch(function(t){$toast.show(t.description)})}},watch:{checkAll:function(t){this.filter_mat_types=t?this.check_info.mat_type_list:[]},filter_mat_types:function(t,e){if(t!==e){console.log(t);var a=[];t.forEach(function(t){a.push(t.mat_type_id)}),this.query.mat_type_ids=a,this.getData(!0)}}}}},516:function(t,e,a){e=t.exports=a(220)(),e.push([t.i,"\n.matTypes[data-v-36a1c18c] {\n  padding-left: 15px;\n}\n","",{version:3,sources:["E:/weibosi/webs-mt/mt/www_dev/src/views/storage/inventory/inventoryDetail.vue"],names:[],mappings:";AACA;EACE,mBAAmB;CACpB",file:"inventoryDetail.vue",sourcesContent:["\n.matTypes[data-v-36a1c18c] {\n  padding-left: 15px;\n}\n"],sourceRoot:""}])},617:function(t,e,a){var n=a(516);"string"==typeof n&&(n=[[t.i,n,""]]),n.locals&&(t.exports=n.locals);a(221)("726e2fc0",n,!0)},726:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{directives:[{name:"nav",rawName:"v-nav",value:{hideNavbar:!0},expression:"{hideNavbar:true}"}],staticClass:"mw-page page storage"},[a("div",{staticClass:"mw-page-header"},[a("i",{staticClass:"left icon ion-ios-arrow-back",on:{click:function(e){t.$router.go(-1)}}}),t._v(" "),a("span",[t._v("盘点单详情")]),t._v(" "),a("i",{staticClass:"right iconfont icon-saoyisao",on:{click:t.scan}})]),t._v(" "),a("div",{staticClass:"mw-page-content has-tab"},[a("div",[a("cst-item",{attrs:{label:"库存地点",hasArrow:!1},model:{value:t.check_info.store_house_name,callback:function(e){t.$set(t.check_info,"store_house_name",e)},expression:"check_info.store_house_name"}}),t._v(" "),a("cst-item",{attrs:{label:"经办人",hasArrow:!1},model:{value:t.check_info.operator_name,callback:function(e){t.$set(t.check_info,"operator_name",e)},expression:"check_info.operator_name"}}),t._v(" "),a("item",{staticClass:"matTypes"},[a("div",{staticStyle:{height:"auto","overflow-wrap":"normal"}},[a("p",{staticStyle:{display:"inline","padding-right":"10px"}},[a("label",[a("input",{directives:[{name:"model",rawName:"v-model",value:t.checkAll,expression:"checkAll"}],attrs:{type:"checkbox"},domProps:{checked:Array.isArray(t.checkAll)?t._i(t.checkAll,null)>-1:t.checkAll},on:{change:function(e){var a=t.checkAll,n=e.target,i=!!n.checked;if(Array.isArray(a)){var o=t._i(a,null);n.checked?o<0&&(t.checkAll=a.concat([null])):o>-1&&(t.checkAll=a.slice(0,o).concat(a.slice(o+1)))}else t.checkAll=i}}}),t._v("全部类型")])]),t._v(" "),t._l(t.check_info.mat_type_list,function(e,n){return a("p",{staticStyle:{display:"inline","padding-right":"10px"}},[a("label",[a("input",{directives:[{name:"model",rawName:"v-model",value:t.filter_mat_types,expression:"filter_mat_types"}],attrs:{type:"checkbox",name:"mat_type"},domProps:{value:e,checked:Array.isArray(t.filter_mat_types)?t._i(t.filter_mat_types,e)>-1:t.filter_mat_types},on:{change:function(a){var n=t.filter_mat_types,i=a.target,o=!!i.checked;if(Array.isArray(n)){var s=e,c=t._i(n,s);i.checked?c<0&&(t.filter_mat_types=n.concat([s])):c>-1&&(t.filter_mat_types=n.slice(0,c).concat(n.slice(c+1)))}else t.filter_mat_types=o}}}),t._v("\n              "+t._s(e.mat_type_name)+"\n            ")]),t._v(" "),a("br",{directives:[{name:"show",rawName:"v-show",value:(n+1)%3==0,expression:"(index+1)%3===0"}]})])})],2)])],1),t._v(" "),a("div",{staticStyle:{height:"60px","margin-top":"10px"}},[a("div",{staticClass:"mw-tab"},[a("ul",{staticClass:"tab-content"},[a("li",{class:{active:0===t.tabIndex},on:{click:function(e){t.onTabClick(0)}}},[t._v("待盘物料")]),t._v(" "),a("li",{class:{active:1===t.tabIndex},on:{click:function(e){t.onTabClick(1)}}},[t._v("已盘物料")])])])]),t._v(" "),a("div",{staticStyle:{flex:"1"}},[a("div",{directives:[{name:"show",rawName:"v-show",value:0===t.tabIndex,expression:"tabIndex===0"}],staticStyle:{height:"100%",position:"relative"}},[a("scroll",{staticStyle:{"-webkit-overflow-scrolling":"touch",top:"0"},attrs:{"on-infinite":t.onInfinite,"on-refresh":t.onRefresh}},[t._l(t.list[0].mats,function(e){return a("item",[a("div",{staticStyle:{display:"flex"}},[a("div",{staticClass:"matBill-content",staticStyle:{width:"80%"}},[a("p",{staticClass:"title"},[t._v("物料名称: "+t._s(e.mat_desc))]),t._v(" "),a("p",{staticClass:"title"},[t._v("物料编码: "+t._s(e.mat_no))]),t._v(" "),a("p",{staticClass:"title"},[t._v("规格型号: "+t._s(e.mat_model))]),t._v(" "),a("span",[t._v("所在仓库: "+t._s(e.store_house_name))]),t._v(" "),a("span",[t._v("账面数: "+t._s(e.check_bill_qty))]),t._v("   \n              ")]),t._v(" "),a("div",{staticClass:"edit-zone",staticStyle:{width:"25%","flex-direction":"column"}},[a("div",{staticStyle:{display:"flex"}},[a("span",{on:{click:function(a){t.minCount(e)}}},[t._v("-")]),t._v("  \n                  "),a("input",{directives:[{name:"model",rawName:"v-model",value:e.check_real_qty,expression:"item.check_real_qty"}],attrs:{type:"number"},domProps:{value:e.check_real_qty},on:{input:[function(a){a.target.composing||t.$set(e,"check_real_qty",a.target.value)},function(a){t.countChange(e)}]}}),t._v("\n                    "),a("span",{on:{click:function(a){t.addCount(e)}}},[t._v("+")])]),t._v(" "),a("br"),t._v(" "),a("p",{on:{click:function(a){t.confirm(e)}}},[t._v("\n                  确认\n                ")])])])])}),t._v(" "),t.pageModels[0].curr_page>=t.pageModels[0].total_page?a("div",{staticClass:"text-center",attrs:{slot:"infinite"},slot:"infinite"},[t._v("没有更多数据")]):t._e()],2)],1),t._v(" "),a("div",{directives:[{name:"show",rawName:"v-show",value:1===t.tabIndex,expression:"tabIndex===1"}],staticStyle:{height:"100%",position:"relative"}},[a("scroll",{staticStyle:{"-webkit-overflow-scrolling":"touch",top:"0"},attrs:{"on-infinite":t.onInfinite,"on-refresh":t.onRefresh}},[t._l(t.list[1].mats,function(e){return a("item",[a("div",{staticStyle:{display:"flex"}},[a("div",{staticClass:"matBill-content",staticStyle:{width:"80%"}},[a("p",{staticClass:"title"},[t._v("物料名称: "+t._s(e.mat_desc))]),t._v(" "),a("p",{staticClass:"title"},[t._v("物料编码: "+t._s(e.mat_no))]),t._v(" "),a("p",{staticClass:"title"},[t._v("规格型号: "+t._s(e.mat_model))]),t._v(" "),a("span",[t._v("所在仓库: "+t._s(e.store_house_name))]),t._v(" "),a("span",[t._v("账面数: "+t._s(e.check_bill_qty))]),t._v("   \n              ")]),t._v(" "),a("div",{staticClass:"edit-zone",staticStyle:{width:"25%","flex-direction":"column"}},[a("div",{staticStyle:{display:"flex"}},[a("span",{on:{click:function(a){t.minCount(e)}}},[t._v("-")]),t._v("  \n                  "),a("input",{directives:[{name:"model",rawName:"v-model",value:e.check_real_qty,expression:"item.check_real_qty"}],attrs:{type:"tel"},domProps:{value:e.check_real_qty},on:{input:[function(a){a.target.composing||t.$set(e,"check_real_qty",a.target.value)},function(a){t.countChange(e)}]}}),t._v("\n                    "),a("span",{on:{click:function(a){t.addCount(e)}}},[t._v("+")])]),t._v(" "),a("br"),t._v(" "),a("p",{on:{click:function(a){t.confirm(e)}}},[t._v("\n                  确认\n                ")])])])])}),t._v(" "),t.pageModels[1].curr_page>=t.pageModels[1].total_page?a("div",{staticClass:"text-center",attrs:{slot:"infinite"},slot:"infinite"},[t._v("没有更多数据")]):t._e()],2)],1)])]),t._v(" "),a("mw-prompt",{attrs:{show:t.showPop,okFunction:t.confirm},on:{hidePop:function(e){t.showPop=!1},input:t.promptVal}}),t._v(" "),t._m(0,!1,!1)],1)},staticRenderFns:[function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"mw-page-footer"},[a("div",{staticClass:"btns"},[a("div",{staticClass:"btn"},[t._v("\n        保存\n      ")])])])}]}}});
//# sourceMappingURL=21.128bfb24738978f4ca81.js.map
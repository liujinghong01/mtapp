webpackJsonp([8],{259:function(t,e,i){i(644);var n=i(0)(i(416),i(754),"data-v-5e8044b6",null);t.exports=n.exports},328:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});e.default={name:"cstItem",data:function(){return{selectId:"select-"+Math.random().toString(36).substring(3,6)}},props:{label:{type:String,required:!0},value:{type:"",default:""},placeholder:{type:String,default:""},canEdit:{type:Boolean,default:!0},hasArrow:{type:Boolean,default:!1},arrowText:{type:[String,Number],default:""},arrowClick:{type:Function},editType:{type:String,default:"text"},isSelect:{type:Boolean,default:!1},options:{type:Array},action:{type:String,default:"check"},color:""},methods:{noteClick:function(){this.arrowClick&&this.arrowClick()},input:function(t){var e=t.target.value;this.$emit("input",e)}}}},329:function(t,e,i){e=t.exports=i(220)(),e.push([t.i,"\n.compute-item[data-v-19336c8e] {\n  min-height: 49px;\n  position: relative;\n  padding: 12px 15px;\n  background-color: white;\n  display: -ms-flexbox;\n  display: flex;\n}\n.compute-item.noneBorder > .hairlineTop[data-v-19336c8e]:before {\n    background: red;\n}\n.compute-item .arrow[data-v-19336c8e] {\n    color: #DDD;\n    position: absolute;\n    right: 15px;\n    top: 14px;\n    font-size: 20px;\n}\n.compute-item .arrow.down[data-v-19336c8e] {\n      top: 13px;\n}\n.compute-item .label[data-v-19336c8e] {\n    -ms-flex: 0 0  auto;\n        flex: 0 0  auto;\n    height: 100%;\n    display: table;\n    padding: 7px 10px 7px 0px;\n    max-width: 52%;\n    color: #484746;\n    text-align: left;\n    line-height: 12px;\n}\n.compute-item .value[data-v-19336c8e] {\n    -ms-flex: 1 0 auto;\n        flex: 1 0 auto;\n    text-align: right;\n}\n.compute-item .value input[data-v-19336c8e] {\n      margin: 0;\n      padding: 0;\n      border: none;\n      color: #ababab;\n      width: 100%;\n      height: 24px;\n      line-height: 24px;\n      text-align: right;\n      text-overflow: ellipsis;\n      white-space: nowrap;\n      overflow: hidden;\n}\n.compute-item .value span[data-v-19336c8e] {\n      height: 24px;\n      line-height: 24px;\n}\n.compute-item .value span.color[data-v-19336c8e] {\n        display: inline-block;\n        width: 20px;\n        height: 20px;\n        border-radius: 50%;\n}\n.compute-item .note[data-v-19336c8e] {\n    -ms-flex: 0 0 5%;\n        flex: 0 0 5%;\n}\n.compute-item .note .item-notes[data-v-19336c8e] {\n      float: right;\n      color: #ababab;\n      line-height: 24px;\n      /*padding: 0 5px;*/\n      padding-right: 15px;\n}\n","",{version:3,sources:["E:/weibosi/webs-mt/mt/www_dev/src/components/cstItem.vue"],names:[],mappings:";AACA;EACE,iBAAiB;EACjB,mBAAmB;EACnB,mBAAmB;EACnB,wBAAwB;EACxB,qBAAqB;EACrB,cAAc;CACf;AACD;IACI,gBAAgB;CACnB;AACD;IACI,YAAY;IACZ,mBAAmB;IACnB,YAAY;IACZ,UAAU;IACV,gBAAgB;CACnB;AACD;MACM,UAAU;CACf;AACD;IACI,oBAAoB;QAChB,gBAAgB;IACpB,aAAa;IACb,eAAe;IACf,0BAA0B;IAC1B,eAAe;IACf,eAAe;IACf,iBAAiB;IACjB,kBAAkB;CACrB;AACD;IACI,mBAAmB;QACf,eAAe;IACnB,kBAAkB;CACrB;AACD;MACM,UAAU;MACV,WAAW;MACX,aAAa;MACb,eAAe;MACf,YAAY;MACZ,aAAa;MACb,kBAAkB;MAClB,kBAAkB;MAClB,wBAAwB;MACxB,oBAAoB;MACpB,iBAAiB;CACtB;AACD;MACM,aAAa;MACb,kBAAkB;CACvB;AACD;QACQ,sBAAsB;QACtB,YAAY;QACZ,aAAa;QACb,mBAAmB;CAC1B;AACD;IACI,iBAAiB;QACb,aAAa;CACpB;AACD;MACM,aAAa;MACb,eAAe;MACf,kBAAkB;MAClB,mBAAmB;MACnB,oBAAoB;CACzB",file:"cstItem.vue",sourcesContent:["\n.compute-item[data-v-19336c8e] {\n  min-height: 49px;\n  position: relative;\n  padding: 12px 15px;\n  background-color: white;\n  display: -ms-flexbox;\n  display: flex;\n}\n.compute-item.noneBorder > .hairlineTop[data-v-19336c8e]:before {\n    background: red;\n}\n.compute-item .arrow[data-v-19336c8e] {\n    color: #DDD;\n    position: absolute;\n    right: 15px;\n    top: 14px;\n    font-size: 20px;\n}\n.compute-item .arrow.down[data-v-19336c8e] {\n      top: 13px;\n}\n.compute-item .label[data-v-19336c8e] {\n    -ms-flex: 0 0  auto;\n        flex: 0 0  auto;\n    height: 100%;\n    display: table;\n    padding: 7px 10px 7px 0px;\n    max-width: 52%;\n    color: #484746;\n    text-align: left;\n    line-height: 12px;\n}\n.compute-item .value[data-v-19336c8e] {\n    -ms-flex: 1 0 auto;\n        flex: 1 0 auto;\n    text-align: right;\n}\n.compute-item .value input[data-v-19336c8e] {\n      margin: 0;\n      padding: 0;\n      border: none;\n      color: #ababab;\n      width: 100%;\n      height: 24px;\n      line-height: 24px;\n      text-align: right;\n      text-overflow: ellipsis;\n      white-space: nowrap;\n      overflow: hidden;\n}\n.compute-item .value span[data-v-19336c8e] {\n      height: 24px;\n      line-height: 24px;\n}\n.compute-item .value span.color[data-v-19336c8e] {\n        display: inline-block;\n        width: 20px;\n        height: 20px;\n        border-radius: 50%;\n}\n.compute-item .note[data-v-19336c8e] {\n    -ms-flex: 0 0 5%;\n        flex: 0 0 5%;\n}\n.compute-item .note .item-notes[data-v-19336c8e] {\n      float: right;\n      color: #ababab;\n      line-height: 24px;\n      /*padding: 0 5px;*/\n      padding-right: 15px;\n}\n"],sourceRoot:""}])},330:function(t,e,i){var n=i(329);"string"==typeof n&&(n=[[t.i,n,""]]),n.locals&&(t.exports=n.locals);i(221)("6ea14c28",n,!0)},331:function(t,e,i){i(330);var n=i(0)(i(328),i(332),"data-v-19336c8e",null);t.exports=n.exports},332:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"compute-item"},[i("span",{staticClass:"label"},[t._v(t._s(t.label))]),t._v(" "),i("div",{directives:[{name:"show",rawName:"v-show",value:"color"!==t.editType,expression:"editType!=='color'"}],staticClass:"value"},[t.canEdit&&"check"!==t.action&&"approve"!==t.action?"check"!==t.action||"approve"!==t.action?i("input",{ref:"input",attrs:{type:t.editType,placeholder:t.placeholder},domProps:{value:t.value},on:{input:t.input}}):t._e():i("span",[t._v(t._s(t.value))])]),t._v(" "),i("div",{directives:[{name:"show",rawName:"v-show",value:"color"===t.editType,expression:"editType==='color'"}],staticClass:"value"},[i("span",{staticClass:"color",style:{background:t.color}})]),t._v(" "),i("div",{directives:[{name:"show",rawName:"v-show",value:t.hasArrow,expression:"hasArrow"}],staticClass:"note"},[i("span",{directives:[{name:"show",rawName:"v-show",value:t.hasArrow&&t.arrowText,expression:"hasArrow&&arrowText"}],staticClass:"item-notes",on:{click:t.noteClick}},[t._v(t._s(t.arrowText))]),t._v(" "),i("i",{directives:[{name:"show",rawName:"v-show",value:t.hasArrow,expression:"hasArrow"}],staticClass:"icon ion-ios-arrow-right arrow "})]),t._v(" "),i("div",{staticClass:"hairline-top"})])},staticRenderFns:[]}},333:function(t,e,i){"use strict";function n(t){return i.i(d.a)({url:"/api/iv/storehouse/modify",method:"post",data:t})}function o(t){return i.i(d.a)({url:"/api/iv/storehouse/list",method:"post",data:t})}function a(t){return i.i(d.a)({url:"/api/iv/storehouse/detail",method:"post",data:t})}function s(t){return i.i(d.a)({url:"/api/iv/storehouse/delete",method:"post",data:t})}function c(t){return i.i(d.a)({url:"/api/iv/storeroom/delete",method:"post",data:t})}function r(t){return i.i(d.a)({url:"/api/iv/mat_type/list",method:"post",data:t})}function l(t){return i.i(d.a)({url:"/api/iv/mat_type/modify",method:"post",data:t})}function p(t){return i.i(d.a)({url:"/api/iv/store_room/search_mat",method:"post",data:t})}e.g=n,e.c=o,e.e=a,e.h=s,e.f=c,e.b=r,e.d=l,e.a=p;var d=i(7)},334:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0}),e.default={name:"mwRadio",data:function(){return{tmpChosen:{}}},props:{obj:{type:Object,required:!0},chosenObj:{type:Object},hasArrow:{type:Boolean,default:!0},iClick:{type:Function},aClick:{type:Function}},methods:{itemClick:function(){this.$set(this.obj,"chosen",!0),this.iClick&&this.iClick(this.obj)},arrowClick:function(){this.aClick&&this.aClick(this.obj)}}}},335:function(t,e,i){e=t.exports=i(220)(),e.push([t.i,"\n.item .label[data-v-5a0c5df3] {\n  width: 90%;\n  height: 100%;\n}\n.item .label .check[data-v-5a0c5df3] {\n    width: 10%;\n    float: left;\n    height: 100%;\n    padding: 10px 0;\n}\n.item .label .check .radio-icon[data-v-5a0c5df3] {\n      left: 10px;\n      color: #bbbbbb;\n}\n.item .label .check .radio-icon.active[data-v-5a0c5df3] {\n        color: #416eb6;\n}\n.item .label .radio-content[data-v-5a0c5df3] {\n    width: 90%;\n    float: right;\n    padding-left: 15px;\n}\n.item .arrow[data-v-5a0c5df3] {\n  color: #DDD;\n  padding: 10px 0;\n  height: 100% !important;\n}\n","",{version:3,sources:["E:/weibosi/webs-mt/mt/www_dev/src/components/mwRadio.vue"],names:[],mappings:";AACA;EACE,WAAW;EACX,aAAa;CACd;AACD;IACI,WAAW;IACX,YAAY;IACZ,aAAa;IACb,gBAAgB;CACnB;AACD;MACM,WAAW;MACX,eAAe;CACpB;AACD;QACQ,eAAe;CACtB;AACD;IACI,WAAW;IACX,aAAa;IACb,mBAAmB;CACtB;AACD;EACE,YAAY;EACZ,gBAAgB;EAChB,wBAAwB;CACzB",file:"mwRadio.vue",sourcesContent:["\n.item .label[data-v-5a0c5df3] {\n  width: 90%;\n  height: 100%;\n}\n.item .label .check[data-v-5a0c5df3] {\n    width: 10%;\n    float: left;\n    height: 100%;\n    padding: 10px 0;\n}\n.item .label .check .radio-icon[data-v-5a0c5df3] {\n      left: 10px;\n      color: #bbbbbb;\n}\n.item .label .check .radio-icon.active[data-v-5a0c5df3] {\n        color: #416eb6;\n}\n.item .label .radio-content[data-v-5a0c5df3] {\n    width: 90%;\n    float: right;\n    padding-left: 15px;\n}\n.item .arrow[data-v-5a0c5df3] {\n  color: #DDD;\n  padding: 10px 0;\n  height: 100% !important;\n}\n"],sourceRoot:""}])},336:function(t,e,i){var n=i(335);"string"==typeof n&&(n=[[t.i,n,""]]),n.locals&&(t.exports=n.locals);i(221)("e60fe1aa",n,!0)},337:function(t,e,i){i(336);var n=i(0)(i(334),i(338),"data-v-5a0c5df3",null);t.exports=n.exports},338:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"item item-icon-right"},[i("div",{staticClass:"label",on:{click:t.itemClick}},[i("div",{staticClass:"check"},[i("i",{class:["icon radio-icon",t.obj.chosen?"ion-android-radio-button-on active":"ion-android-radio-button-off"]})]),t._v(" "),i("input",{staticStyle:{position:"absolute",left:"-5000px",top:"-5000px"},attrs:{type:"radio"}}),t._v(" "),i("div",{staticClass:"radio-content"},[t._t("default")],2)]),t._v(" "),i("i",{directives:[{name:"show",rawName:"v-show",value:t.hasArrow,expression:"hasArrow"}],staticClass:"icon ion-ios-arrow-right arrow",on:{click:t.arrowClick}})])},staticRenderFns:[]}},339:function(t,e,i){"use strict";function n(t){return i.i(v.a)({url:"api/customerOrder/dict/salesmanList",method:"post",data:t})}function o(t){return i.i(v.a)({url:"api/customerOrder/dict/rawNatureList",method:"post",data:t})}function a(t){return i.i(v.a)({url:"api/customerOrder/dict/processCostItemList",method:"post",data:t})}function s(t){return i.i(v.a)({url:"api/customerOrder/dict/defaultSubPartList",method:"post",data:t})}function c(t){return i.i(v.a)({url:"api/customerOrder/dict/coinTypeList",method:"post",data:t})}function r(t){return i.i(v.a)({url:"api/customerOrder/dict/outWayList",method:"post",data:t})}function l(t){return i.i(v.a)({url:"api/customerOrder/dict/waterTypeList",method:"post",data:t})}function p(t){return i.i(v.a)({url:"api/customerOrder/dict/runnerTypeList",method:"post",data:t})}function d(t){return i.i(v.a)({url:"api/customerOrder/dict/cstCompanyList",method:"post",data:t})}function u(t){return i.i(v.a)({url:"/api/dict/invoice_type",method:"post",data:t})}function m(t){return i.i(v.a)({url:"/api/pc/supplier/list",method:"post",data:t})}function h(t){return i.i(v.a)({url:"/api/dict/query_mat_by_supplier",method:"post",data:t})}function A(t){return i.i(v.a)({url:"/api/dict/search/matName",method:"post",data:t})}function f(t){return i.i(v.a)({url:"api/company/getAllDeptMember",method:"post",data:t})}e.e=n,e.c=o,e.a=a,e.d=s,e.h=c,e.i=r,e.f=l,e.g=p,e.b=d,e.j=u,e.k=m,e.n=h,e.m=A,e.l=f;var v=i(7)},340:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n=i(337),o=i.n(n),a=i(339),s=i(333);e.default={components:{MwRadio:o.a},name:"sidebarCheck",data:function(){return{list:[],filterList:[],chosenItem:{},title:"",filterStr:""}},computed:{},props:{showFilter:{type:Boolean,default:!1,required:!0},dicType:{type:String,required:!0},chosen:{type:Function,required:!0},query:{type:Object}},methods:{itemClick:function(t){t!==this.chosenItem&&(this.chosenItem.chosen=!1,this.chosenItem=t)},ok:function(){this.chosen&&this.chosen(this.chosenItem),this.$emit("hideMask")},getList:function(){var t=this;console.log("getList");var e={};switch(this.dicType){case"processItem":this.title="请选择加工项目",i.i(a.a)(e).then(function(e){t.list=t.filterList=e.process_item_list}).catch(function(t){console.log(t.description)});break;case"customer":this.title="请选择客户",i.i(a.b)(e).then(function(e){t.list=t.filterList=e.cst_list}).catch(function(t){console.log(t.description)});break;case"material":this.title="请选择材料",i.i(a.c)(e).then(function(e){t.list=t.filterList=e.raw_nature_list}).catch(function(t){console.log(t.description)});break;case"part":this.title="请选择零部件",i.i(a.d)(e).then(function(e){t.list=t.filterList=e.default_part_list}).catch(function(t){console.log(t.description)});break;case"sub_part":this.title="请选择子部件";break;case"prodType":this.title="请选择产品类型",this.list=this.filterList=[{type:1,type_name:"注塑模"},{type:2,type_name:"压铸模"}];break;case"salesman":this.title="请选择业务员",i.i(a.e)(e).then(function(e){t.list=t.filterList=e.list}).catch(function(t){console.log(t.description)});break;case"waterType":this.title="请选择水口形式",i.i(a.f)(e).then(function(e){t.list=t.filterList=e.list}).catch(function(t){console.log(t.description)});break;case"runnerType":this.title="请选择流道方式",i.i(a.g)(e).then(function(e){t.list=t.filterList=e.list}).catch(function(t){console.log(t.description)});break;case"coin":this.title="请选择货币类型",i.i(a.h)(e).then(function(e){t.list=t.filterList=e.list}).catch(function(t){console.log(t.description)});break;case"outerWay":this.title="请选择顶出方式",i.i(a.i)(e).then(function(e){t.list=t.filterList=e.list}).catch(function(t){console.log(t.description)});break;case"invoiceType":this.title="请选择发票类型",i.i(a.j)(e).then(function(e){t.list=t.filterList=e.list}).catch(function(t){console.log(t.description)});break;case"supplier":this.title="请选择供应商",e={query:{sup_name:""}},i.i(a.k)(e).then(function(e){t.list=t.filterList=e.sup_list}).catch(function(t){console.log(t.description)});break;case"storehouse":this.title="请选择仓库",e={is_paging:0},i.i(s.c)(e).then(function(e){t.list=t.filterList=e.storehouse_list}).catch(function(t){console.log(t.description)});break;case"depMember":this.title="请选择人员",e={query:{dep_code:"Storehouse_Dept"}},i.i(a.l)(e).then(function(e){t.list=t.filterList=e.member_list}).catch(function(t){console.log(t.description)});break;default:this.title="请选择加工项目"}},filter:function(t){var e=this;setTimeout(function(){switch(e.dicType){case"processItem":e.filterList=e.list.filter(function(t){return t.process_name.indexOf(e.filterStr.trim())>-1});break;case"customer":e.filterList=e.list.filter(function(t){return t.cst_name.indexOf(e.filterStr.trim())>-1});break;case"material":e.filterList=e.list.filter(function(t){return t.raw_nature.indexOf(e.filterStr.trim())>-1});break;case"salesman":e.filterList=e.list.filter(function(t){return t.username.indexOf(e.filterStr.trim())>-1});break;case"part":e.filterList=e.list.filter(function(t){return t.part_name.indexOf(e.filterStr.trim())>-1});break;case"sub_part":e.filterList=e.list.filter(function(t){return t.sub_part_name.indexOf(e.filterStr.trim())>-1});break;case"prodType":e.filterList=e.list.filter(function(t){return t.type_name.indexOf(e.filterStr.trim())>-1});break;case"supplier":e.filterList=e.list.filter(function(t){return t.sup_name.indexOf(e.filterStr.trim())>-1});break;case"storehouse":e.filterList=e.list.filter(function(t){return t.store_house_name.indexOf(e.filterStr.trim())>-1});break;case"depMember":e.filterList=e.list.filter(function(t){return t.member_name.indexOf(e.filterStr.trim())>-1});break;default:e.filterList=e.list.filter(function(t){return t.name.indexOf(e.filterStr.trim())>-1})}},800)}},watch:{showFilter:function(t,e){t&&(this.filterStr="",this.getList())}}}},341:function(t,e,i){e=t.exports=i(220)(),e.push([t.i,"\n.mask[data-v-a0a7730c] {\n  position: absolute;\n  width: 100%;\n  height: 100%;\n  top: 0;\n  left: 0;\n  z-index: 16;\n  background-color: #2e2e2d;\n  opacity: .4;\n}\n.filter[data-v-a0a7730c] {\n  width: 0;\n  height: 0;\n  transition: width .2s;\n  -moz-transition: width .2s;\n  /* Firefox 4 */\n  -webkit-transition: width .2s;\n  /* Safari and Chrome */\n  -o-transition: width .2s;\n  /* Opera */\n}\n.filter.active[data-v-a0a7730c] {\n  width: 80%;\n  height: 100%;\n  background-color: #F3F6FB;\n  position: absolute;\n  top: 0;\n  right: 0;\n  z-index: 17;\n}\n.side-title[data-v-a0a7730c] {\n  height: 40px;\n  display: -ms-flexbox;\n  display: flex;\n  -ms-flex-pack: center;\n      justify-content: center;\n  -ms-flex-align: center;\n      align-items: center;\n}\n.side-content[data-v-a0a7730c] {\n  -ms-flex: 1;\n      flex: 1;\n  overflow-x: hidden;\n  overflow-y: auto;\n}\n.side-footer[data-v-a0a7730c] {\n  height: 50px;\n}\n.filter-btns[data-v-a0a7730c] {\n  display: -ms-flexbox;\n  display: flex;\n  height: 100%;\n  box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);\n}\n.filter-btns .filter-btn[data-v-a0a7730c] {\n    -ms-flex: 1 0 auto;\n        flex: 1 0 auto;\n    text-align: center;\n    display: -ms-flexbox;\n    display: flex;\n    -ms-flex-pack: center;\n        justify-content: center;\n    -ms-flex-align: center;\n        align-items: center;\n}\n","",{version:3,sources:["E:/weibosi/webs-mt/mt/www_dev/src/components/sidebarCheck.vue"],names:[],mappings:";AACA;EACE,mBAAmB;EACnB,YAAY;EACZ,aAAa;EACb,OAAO;EACP,QAAQ;EACR,YAAY;EACZ,0BAA0B;EAC1B,YAAY;CACb;AACD;EACE,SAAS;EACT,UAAU;EACV,sBAAsB;EACtB,2BAA2B;EAC3B,eAAe;EACf,8BAA8B;EAC9B,uBAAuB;EACvB,yBAAyB;EACzB,WAAW;CACZ;AACD;EACE,WAAW;EACX,aAAa;EACb,0BAA0B;EAC1B,mBAAmB;EACnB,OAAO;EACP,SAAS;EACT,YAAY;CACb;AACD;EACE,aAAa;EACb,qBAAqB;EACrB,cAAc;EACd,sBAAsB;MAClB,wBAAwB;EAC5B,uBAAuB;MACnB,oBAAoB;CACzB;AACD;EACE,YAAY;MACR,QAAQ;EACZ,mBAAmB;EACnB,iBAAiB;CAClB;AACD;EACE,aAAa;CACd;AACD;EACE,qBAAqB;EACrB,cAAc;EACd,aAAa;EACb,yCAAyC;CAC1C;AACD;IACI,mBAAmB;QACf,eAAe;IACnB,mBAAmB;IACnB,qBAAqB;IACrB,cAAc;IACd,sBAAsB;QAClB,wBAAwB;IAC5B,uBAAuB;QACnB,oBAAoB;CAC3B",file:"sidebarCheck.vue",sourcesContent:["\n.mask[data-v-a0a7730c] {\n  position: absolute;\n  width: 100%;\n  height: 100%;\n  top: 0;\n  left: 0;\n  z-index: 16;\n  background-color: #2e2e2d;\n  opacity: .4;\n}\n.filter[data-v-a0a7730c] {\n  width: 0;\n  height: 0;\n  transition: width .2s;\n  -moz-transition: width .2s;\n  /* Firefox 4 */\n  -webkit-transition: width .2s;\n  /* Safari and Chrome */\n  -o-transition: width .2s;\n  /* Opera */\n}\n.filter.active[data-v-a0a7730c] {\n  width: 80%;\n  height: 100%;\n  background-color: #F3F6FB;\n  position: absolute;\n  top: 0;\n  right: 0;\n  z-index: 17;\n}\n.side-title[data-v-a0a7730c] {\n  height: 40px;\n  display: -ms-flexbox;\n  display: flex;\n  -ms-flex-pack: center;\n      justify-content: center;\n  -ms-flex-align: center;\n      align-items: center;\n}\n.side-content[data-v-a0a7730c] {\n  -ms-flex: 1;\n      flex: 1;\n  overflow-x: hidden;\n  overflow-y: auto;\n}\n.side-footer[data-v-a0a7730c] {\n  height: 50px;\n}\n.filter-btns[data-v-a0a7730c] {\n  display: -ms-flexbox;\n  display: flex;\n  height: 100%;\n  box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);\n}\n.filter-btns .filter-btn[data-v-a0a7730c] {\n    -ms-flex: 1 0 auto;\n        flex: 1 0 auto;\n    text-align: center;\n    display: -ms-flexbox;\n    display: flex;\n    -ms-flex-pack: center;\n        justify-content: center;\n    -ms-flex-align: center;\n        align-items: center;\n}\n"],sourceRoot:""}])},342:function(t,e,i){var n=i(341);"string"==typeof n&&(n=[[t.i,n,""]]),n.locals&&(t.exports=n.locals);i(221)("2c018453",n,!0)},343:function(t,e,i){i(342);var n=i(0)(i(340),i(344),"data-v-a0a7730c",null);t.exports=n.exports},344:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("section",[i("div",{directives:[{name:"show",rawName:"v-show",value:t.showFilter,expression:"showFilter"}],staticClass:"mask",on:{click:function(e){t.$emit("hideMask")}}}),t._v(" "),i("section",{class:["filter",t.showFilter?"active":""],staticStyle:{display:"flex","flex-direction":"column"}},[i("div",{directives:[{name:"show",rawName:"v-show",value:t.showFilter,expression:"showFilter"}],staticClass:"side-title"},[t._v("\n       "+t._s(t.title)+"\n    ")]),t._v(" "),i("input",{directives:[{name:"model",rawName:"v-model",value:t.filterStr,expression:"filterStr"}],staticStyle:{"background-color":"#bbbbbb",width:"90%",margin:"0 auto","border-radius":"5px"},attrs:{type:"search"},domProps:{value:t.filterStr},on:{input:[function(e){e.target.composing||(t.filterStr=e.target.value)},t.filter]}}),t._v(" "),"processItem"===t.dicType?i("div",{staticClass:"side-content"},t._l(t.filterList,function(e){return i("div",[i("mw-radio",{attrs:{obj:e,hasArrow:!1,iClick:t.itemClick}},[t._v(t._s(e.process_name)+"  "),i("span",{staticStyle:{float:"right"}},[t._v(t._s(e.process_ratio+"/"+e.process_unit_name))])])],1)})):t._e(),t._v(" "),"material"===t.dicType?i("div",{staticClass:"side-content"},t._l(t.filterList,function(e){return i("div",[i("mw-radio",{attrs:{obj:e,hasArrow:!1,iClick:t.itemClick}},[t._v(t._s(e.raw_nature))])],1)})):t._e(),t._v(" "),"customer"===t.dicType?i("div",{staticClass:"side-content"},t._l(t.filterList,function(e){return i("div",[i("mw-radio",{attrs:{obj:e,hasArrow:!1,iClick:t.itemClick}},[t._v(t._s(e.cst_name+"/"+e.connect_man))])],1)})):t._e(),t._v(" "),"salesman"===t.dicType?i("div",{staticClass:"side-content"},t._l(t.filterList,function(e){return i("div",[i("mw-radio",{attrs:{obj:e,hasArrow:!1,iClick:t.itemClick}},[t._v(t._s(e.username))])],1)})):t._e(),t._v(" "),"part"===t.dicType?i("div",{staticClass:"side-content"},t._l(t.filterList,function(e){return i("div",[i("mw-radio",{attrs:{obj:e,hasArrow:!1,iClick:t.itemClick}},[t._v(t._s(e.part_name))])],1)})):t._e(),t._v(" "),"sub_part"===t.dicType?i("div",{staticClass:"side-content"},t._l(t.filterList,function(e){return i("div",[i("mw-radio",{attrs:{obj:e,hasArrow:!1,iClick:t.itemClick}},[t._v(t._s(e.sub_part_name))])],1)})):t._e(),t._v(" "),"waterType"===t.dicType?i("div",{staticClass:"side-content"},t._l(t.filterList,function(e){return i("div",[i("mw-radio",{attrs:{obj:e,hasArrow:!1,iClick:t.itemClick}},[t._v(t._s(e.name))])],1)})):t._e(),t._v(" "),"prodType"===t.dicType?i("div",{staticClass:"side-content"},t._l(t.filterList,function(e){return i("div",[i("mw-radio",{attrs:{obj:e,hasArrow:!1,iClick:t.itemClick}},[t._v(t._s(e.type_name))])],1)})):t._e(),t._v(" "),"runnerType"===t.dicType?i("div",{staticClass:"side-content"},t._l(t.filterList,function(e){return i("div",[i("mw-radio",{attrs:{obj:e,hasArrow:!1,iClick:t.itemClick}},[t._v(t._s(e.name))])],1)})):t._e(),t._v(" "),"coin"===t.dicType?i("div",{staticClass:"side-content"},t._l(t.filterList,function(e){return i("div",[i("mw-radio",{attrs:{obj:e,hasArrow:!1,iClick:t.itemClick}},[t._v(t._s(e.name))])],1)})):t._e(),t._v(" "),"outerWay"===t.dicType?i("div",{staticClass:"side-content"},t._l(t.filterList,function(e){return i("div",[i("mw-radio",{attrs:{obj:e,hasArrow:!1,iClick:t.itemClick}},[t._v(t._s(e.name))])],1)})):t._e(),t._v(" "),"supplier"===t.dicType?i("div",{staticClass:"side-content"},t._l(t.filterList,function(e){return i("div",[i("mw-radio",{attrs:{obj:e,hasArrow:!1,iClick:t.itemClick}},[t._v(t._s(e.sup_name))])],1)})):t._e(),t._v(" "),"invoiceType"===t.dicType?i("div",{staticClass:"side-content"},t._l(t.filterList,function(e){return i("div",[i("mw-radio",{attrs:{obj:e,hasArrow:!1,iClick:t.itemClick}},[t._v(t._s(e.name))])],1)})):t._e(),t._v(" "),"storehouse"===t.dicType?i("div",{staticClass:"side-content"},t._l(t.filterList,function(e){return i("div",[i("mw-radio",{attrs:{obj:e,hasArrow:!1,iClick:t.itemClick}},[t._v(t._s(e.store_house_name))])],1)})):t._e(),t._v(" "),"depMember"===t.dicType?i("div",{staticClass:"side-content"},t._l(t.filterList,function(e){return i("div",[i("mw-radio",{attrs:{obj:e,hasArrow:!1,iClick:t.itemClick}},[t._v(t._s(e.member_name))])],1)})):t._e(),t._v(" "),i("div",{directives:[{name:"show",rawName:"v-show",value:t.showFilter,expression:"showFilter"}],staticClass:"side-footer"},[i("div",{staticClass:"filter-btns"},[i("div",{staticClass:"filter-btn",on:{click:t.ok}},[t._v("\n          确定\n        ")])])])])])},staticRenderFns:[]}},416:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n=i(8),o=i.n(n),a=i(331),s=i.n(a),c=i(79),r=i(1),l=i(343),p=i.n(l);e.default={name:"computeDetail",components:{SidebarCheck:p.a,cstItem:s.a},data:function(){return{action:"",tabs:["头信息","明细列表"],tabIndex:0,showFilter:!1,compute:{compute_id:"",compute_no:"",approval_flag:"",created_date:"",cst_company_id:"",cst_company_name:"",cst_mold_id:"",cst_mold_type:"",type:"",type_name:"",delivery_cost:"",design_cost:"",cst_products:[],manage_cost:"",material:"请选择",mold_cost:"",mold_up:"",post_box_cost:"",process_cost:"",profit_cost:"",stuff_cost:"",try_mold_cost:"",valid_date:""},materialList:[],compute_id:""}},mounted:function(){var t=this;if(console.log("mounted"),this.action=this.$route.params.action,"new"===this.action){var e=this.$route.query;console.log(e),this.compute.cst_company_id=e.cst_company.cst_company_id,this.compute.cst_company_name=e.cst_company.cst_company_name,this.compute.type_name=e.type.name,this.compute.type=e.type.code}else if("check"===this.action||"approve"===this.action)this.compute_id=this.$route.params.compute_id,this.compute.compute_id=this.$route.params.compute_id,this.getData();else if("associate"===this.action){var n={compute_id:this.$route.params.compute_id};i.i(c.j)(n).then(function(e){console.log(e),t.action="edit",t.compute_id=t.compute.compute_id=e.compute_info.compute_id,t.compute=e.compute_info}).catch(function(t){$toast.show(t.description)})}},activated:function(){console.log("activated");var t=r.a.get("prods");if(t&&""!=t)return this.compute.cst_products=JSON.parse(t),void r.a.remove("prods");this.compute.compute_id&&this.getData()},methods:{onArrowClick:function(){$router.forward("/customerOrder/compute/cst_part_list/"+this.action+"/"+this.compute_id)},onArrowClick1:function(){$router.forward("/customerOrder/compute/cst_process_list/"+this.action+"/"+this.compute_id)},changeType:function(t){"check"!==this.action&&"approve"!==this.action&&(this.showFilter=!0)},choose:function(t){this.compute.material=t.raw_nature},onMenuClick:function(){"check"===this.action?this.action="edit":this.action="check"},onTabClick:function(t){if(console.log("onTabClick"),!this.compute_id)return void $toast.show("请先保存头信息");this.tabIndex=t},getData:function(){var t=this,e={compute_id:t.compute_id};i.i(c.k)(e).then(function(e){t.compute=e}).catch(function(t){$toast.show(t.description)})},saveData:function(){var t=this;if(0===this.compute.cst_products.length)return void $toast.show("请先添加制品信息");var e={compute_id:this.compute_id,compute_info:this.compute};i.i(c.l)(e).then(function(e){t.action="new"===t.action?"edit":"check",t.compute_id=t.compute.compute_id=e.compute_info.compute_id,t.compute=e.compute_info,$toast.show("保存成功")}).catch(function(t){$toast.show(t.description)})},submit:function(){var t=this;if("edit"===this.action)return void $toast.show("请先保存头信息");if("approve"===this.action)this.$router.forward("/approve/approve/"+this.compute_id+"/1/"+this.compute.approve_step);else{var e={bill_id:this.compute_id,bill_type:"1",approve_flag:"1",approve_step:this.compute.approve_step};i.i(c.a)(e).then(function(e){$toast.show("提交成功"),r.a.set("approve",!0),t.$router.go(-1)}).catch(function(t){$toast.show(t.description),console.log(t.description)})}},checkApprove:function(){this.$router.forward("/approve/approveSuggests/"+this.compute.compute_id+"/1")},toProdList:function(){var t=this.action;"step0"===this.compute.approve_step&&(t="readOnly"),$router.forward({path:"/customerOrder/compute/cst_prod_list/"+t,query:{cst_prods:o()(this.compute.cst_products)}})}}}},543:function(t,e,i){e=t.exports=i(220)(),e.push([t.i,"","",{version:3,sources:[],names:[],mappings:"",file:"detail.vue",sourceRoot:""}])},644:function(t,e,i){var n=i(543);"string"==typeof n&&(n=[[t.i,n,""]]),n.locals&&(t.exports=n.locals);i(221)("209c7bf2",n,!0)},754:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{directives:[{name:"nav",rawName:"v-nav",value:{hideNavbar:!0},expression:"{hideNavbar:true}"}],staticClass:"mw-page page"},[i("div",{staticClass:"mw-page-header"},[i("i",{staticClass:"left icon ion-ios-arrow-back",on:{click:function(e){t.$router.go(-1)}}}),t._v(" "),i("span",[t._v("核算单详情")]),t._v(" "),"check"!==t.action||"step10"!==t.compute.approve_step&&"step11"!==t.compute.approve_step?"edit"===t.action?i("i",{staticClass:"right",on:{click:function(e){t.action="check"}}},[t._v("取消")]):i("i",{staticClass:"right"}):i("i",{staticClass:"right",on:{click:function(e){t.action="edit"}}},[t._v("编辑")])]),t._v(" "),i("div",{staticClass:"mw-page-content has-tab"},[i("div",{staticStyle:{height:"60px"}},[i("tabs",{attrs:{"tab-items":t.tabs,"tab-index":t.tabIndex,"on-tab-click":t.onTabClick}})],1),t._v(" "),i("div",{staticStyle:{flex:"1",position:"relative"}},[i("div",{directives:[{name:"show",rawName:"v-show",value:0===t.tabIndex,expression:"tabIndex===0"}],staticClass:"page-content",staticStyle:{height:"100%"}},[i("cst-item",{directives:[{name:"show",rawName:"v-show",value:"new"!=t.action,expression:"action!='new'"}],attrs:{label:"核算单编号",editType:"text",action:t.action,canEdit:!1},model:{value:t.compute.compute_no,callback:function(e){t.$set(t.compute,"compute_no",e)},expression:"compute.compute_no"}}),t._v(" "),i("cst-item",{directives:[{name:"show",rawName:"v-show",value:"new"!=t.action,expression:"action!='new'"}],attrs:{label:"核算日期",editType:"text",action:t.action,canEdit:!1},model:{value:t.compute.created_at,callback:function(e){t.$set(t.compute,"created_at",e)},expression:"compute.created_at"}}),t._v(" "),i("cst-item",{attrs:{label:"模具类型",editType:"text",action:t.action,canEdit:!1},model:{value:t.compute.type_name,callback:function(e){t.$set(t.compute,"type_name",e)},expression:"compute.type_name"}}),t._v(" "),i("cst-item",{attrs:{label:"客户",editType:"text",action:t.action,canEdit:!1},model:{value:t.compute.cst_company_name,callback:function(e){t.$set(t.compute,"cst_company_name",e)},expression:"compute.cst_company_name"}}),t._v(" "),i("cst-item",{attrs:{label:"制品材料",editType:"text",action:t.action,hasArrow:!0,canEdit:!1},nativeOn:{click:function(e){t.changeType("material")}},model:{value:t.compute.material,callback:function(e){t.$set(t.compute,"material",e)},expression:"compute.material"}}),t._v(" "),i("cst-item",{attrs:{label:"制品信息",editType:"number",hasArrow:!0,arrowText:t.compute.cst_products.length,action:t.action,canEdit:!1},nativeOn:{click:function(e){t.toProdList(e)}}})],1),t._v(" "),i("div",{directives:[{name:"show",rawName:"v-show",value:1===t.tabIndex,expression:"tabIndex===1"}],staticClass:"page-content"},[i("cst-item",{attrs:{label:"材料费",hasArrow:!0,editType:"number",action:t.action,canEdit:!1},nativeOn:{click:function(e){t.onArrowClick(e)}},model:{value:t.compute.stuff_cost,callback:function(e){t.$set(t.compute,"stuff_cost",e)},expression:"compute.stuff_cost"}}),t._v(" "),i("cst-item",{attrs:{label:"加工费",hasArrow:!0,editType:"number",action:t.action,canEdit:!1},nativeOn:{click:function(e){t.onArrowClick1(e)}},model:{value:t.compute.process_cost,callback:function(e){t.$set(t.compute,"process_cost",e)},expression:"compute.process_cost"}}),t._v(" "),i("cst-item",{attrs:{label:"设计费",editType:"number",action:t.action},model:{value:t.compute.design_cost,callback:function(e){t.$set(t.compute,"design_cost",e)},expression:"compute.design_cost"}}),t._v(" "),i("cst-item",{attrs:{label:"试模费",editType:"number",action:t.action},model:{value:t.compute.try_mold_cost,callback:function(e){t.$set(t.compute,"try_mold_cost",e)},expression:"compute.try_mold_cost"}}),t._v(" "),i("cst-item",{attrs:{label:"寄模费",editType:"number",action:t.action},model:{value:t.compute.post_box_cost,callback:function(e){t.$set(t.compute,"post_box_cost",e)},expression:"compute.post_box_cost"}}),t._v(" "),i("cst-item",{attrs:{label:"运输费",editType:"number",action:t.action},model:{value:t.compute.delivery_cost,callback:function(e){t.$set(t.compute,"delivery_cost",e)},expression:"compute.delivery_cost"}}),t._v(" "),i("cst-item",{attrs:{label:"管理费",editType:"number",action:t.action},model:{value:t.compute.manage_cost,callback:function(e){t.$set(t.compute,"manage_cost",e)},expression:"compute.manage_cost"}}),t._v(" "),i("cst-item",{attrs:{label:"模具成本",editType:"number",action:t.action},model:{value:t.compute.mold_cost,callback:function(e){t.$set(t.compute,"mold_cost",e)},expression:"compute.mold_cost"}}),t._v(" "),i("cst-item",{attrs:{label:"利润",editType:"number",action:t.action},model:{value:t.compute.profit_cost,callback:function(e){t.$set(t.compute,"profit_cost",e)},expression:"compute.profit_cost"}}),t._v(" "),i("cst-item",{attrs:{label:"模具单价",editType:"number",action:t.action},model:{value:t.compute.mold_up,callback:function(e){t.$set(t.compute,"mold_up",e)},expression:"compute.mold_up"}})],1)])]),t._v(" "),i("sidebar-check",{attrs:{showFilter:t.showFilter,dicType:"material",chosen:t.choose},on:{hideMask:function(e){t.showFilter=!1}}}),t._v(" "),"check"===t.action&&"step20"!==t.compute.approve_step&&"step30"!==t.compute.approve_step?i("div",{staticClass:"mw-page-footer"},[i("div",{staticClass:"btns"},["step11"===t.compute.approve_step||"step10"===t.compute.approve_step?i("div",{staticClass:"btn",on:{click:t.submit}},[t._v("\n        提交审批\n      ")]):t._e(),t._v(" "),i("div",{directives:[{name:"show",rawName:"v-show",value:"step11"===t.compute.approve_step||"step0"===t.compute.approve_step,expression:"compute.approve_step==='step11'||compute.approve_step==='step0'"}],staticClass:"btn",on:{click:t.checkApprove}},[t._v("\n        查看审批意见\n      ")])])]):"new"===t.action||"edit"===t.action||"associate"===t.action?i("div",{staticClass:"mw-page-footer"},[i("div",{staticClass:"btns"},[i("div",{staticClass:"btn",on:{click:t.saveData}},[t._v("\n        保存\n      ")])])]):"approve"===t.action?i("div",{staticClass:"mw-page-footer"},[i("div",{staticClass:"btns"},[i("div",{staticClass:"btn",on:{click:t.submit}},[t._v("\n        审批\n      ")])])]):t._e()],1)},staticRenderFns:[]}}});
//# sourceMappingURL=8.0880ec13bb072d0163ad.js.map
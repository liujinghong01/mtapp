webpackJsonp([13],{329:function(t,e,n){n(764);var i=n(0)(n(528),n(911),"data-v-4b05073a",null);t.exports=i.exports},370:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});e.default={name:"cstItem",data:function(){return{selectId:"select-"+Math.random().toString(36).substring(3,6)}},props:{label:{type:String,required:!0},value:{type:"",default:""},placeholder:{type:String,default:""},canEdit:{type:Boolean,default:!0},hasArrow:{type:Boolean,default:!1},arrowText:{type:[String,Number],default:""},arrowClick:{type:Function},editType:{type:String,default:"text"},isSelect:{type:Boolean,default:!1},options:{type:Array},action:{type:String,default:"check"},color:""},methods:{noteClick:function(){this.arrowClick&&this.arrowClick()},input:function(t){var e=t.target.value;this.$emit("input",e)}}}},371:function(t,e,n){e=t.exports=n(225)(),e.push([t.i,"\n.compute-item[data-v-19336c8e] {\n  min-height: 49px;\n  position: relative;\n  padding: 12px 15px;\n  background-color: white;\n  display: -ms-flexbox;\n  display: flex;\n}\n.compute-item.noneBorder > .hairlineTop[data-v-19336c8e]:before {\n    background: red;\n}\n.compute-item .arrow[data-v-19336c8e] {\n    color: #DDD;\n    position: absolute;\n    right: 15px;\n    top: 14px;\n    font-size: 20px;\n}\n.compute-item .arrow.down[data-v-19336c8e] {\n      top: 13px;\n}\n.compute-item .label[data-v-19336c8e] {\n    -ms-flex: 0 0  auto;\n        flex: 0 0  auto;\n    height: 100%;\n    display: table;\n    padding: 7px 10px 7px 0px;\n    max-width: 52%;\n    color: #484746;\n    text-align: left;\n    line-height: 12px;\n}\n.compute-item .value[data-v-19336c8e] {\n    -ms-flex: 1 0 auto;\n        flex: 1 0 auto;\n    text-align: right;\n}\n.compute-item .value input[data-v-19336c8e] {\n      margin: 0;\n      padding: 0;\n      border: none;\n      color: #ababab;\n      width: 100%;\n      height: 24px;\n      line-height: 24px;\n      text-align: right;\n      text-overflow: ellipsis;\n      white-space: nowrap;\n      overflow: hidden;\n}\n.compute-item .value span[data-v-19336c8e] {\n      height: 24px;\n      line-height: 24px;\n}\n.compute-item .value span.color[data-v-19336c8e] {\n        display: inline-block;\n        width: 20px;\n        height: 20px;\n        border-radius: 50%;\n}\n.compute-item .note[data-v-19336c8e] {\n    -ms-flex: 0 0 5%;\n        flex: 0 0 5%;\n}\n.compute-item .note .item-notes[data-v-19336c8e] {\n      float: right;\n      color: #ababab;\n      line-height: 24px;\n      /*padding: 0 5px;*/\n      padding-right: 15px;\n}\n","",{version:3,sources:["/Users/zxf/code/motooling/mt-app/webs-mtapp/mtApp/mt/www_dev/src/components/cstItem.vue"],names:[],mappings:";AACA;EACE,iBAAiB;EACjB,mBAAmB;EACnB,mBAAmB;EACnB,wBAAwB;EACxB,qBAAqB;EACrB,cAAc;CACf;AACD;IACI,gBAAgB;CACnB;AACD;IACI,YAAY;IACZ,mBAAmB;IACnB,YAAY;IACZ,UAAU;IACV,gBAAgB;CACnB;AACD;MACM,UAAU;CACf;AACD;IACI,oBAAoB;QAChB,gBAAgB;IACpB,aAAa;IACb,eAAe;IACf,0BAA0B;IAC1B,eAAe;IACf,eAAe;IACf,iBAAiB;IACjB,kBAAkB;CACrB;AACD;IACI,mBAAmB;QACf,eAAe;IACnB,kBAAkB;CACrB;AACD;MACM,UAAU;MACV,WAAW;MACX,aAAa;MACb,eAAe;MACf,YAAY;MACZ,aAAa;MACb,kBAAkB;MAClB,kBAAkB;MAClB,wBAAwB;MACxB,oBAAoB;MACpB,iBAAiB;CACtB;AACD;MACM,aAAa;MACb,kBAAkB;CACvB;AACD;QACQ,sBAAsB;QACtB,YAAY;QACZ,aAAa;QACb,mBAAmB;CAC1B;AACD;IACI,iBAAiB;QACb,aAAa;CACpB;AACD;MACM,aAAa;MACb,eAAe;MACf,kBAAkB;MAClB,mBAAmB;MACnB,oBAAoB;CACzB",file:"cstItem.vue",sourcesContent:["\n.compute-item[data-v-19336c8e] {\n  min-height: 49px;\n  position: relative;\n  padding: 12px 15px;\n  background-color: white;\n  display: -ms-flexbox;\n  display: flex;\n}\n.compute-item.noneBorder > .hairlineTop[data-v-19336c8e]:before {\n    background: red;\n}\n.compute-item .arrow[data-v-19336c8e] {\n    color: #DDD;\n    position: absolute;\n    right: 15px;\n    top: 14px;\n    font-size: 20px;\n}\n.compute-item .arrow.down[data-v-19336c8e] {\n      top: 13px;\n}\n.compute-item .label[data-v-19336c8e] {\n    -ms-flex: 0 0  auto;\n        flex: 0 0  auto;\n    height: 100%;\n    display: table;\n    padding: 7px 10px 7px 0px;\n    max-width: 52%;\n    color: #484746;\n    text-align: left;\n    line-height: 12px;\n}\n.compute-item .value[data-v-19336c8e] {\n    -ms-flex: 1 0 auto;\n        flex: 1 0 auto;\n    text-align: right;\n}\n.compute-item .value input[data-v-19336c8e] {\n      margin: 0;\n      padding: 0;\n      border: none;\n      color: #ababab;\n      width: 100%;\n      height: 24px;\n      line-height: 24px;\n      text-align: right;\n      text-overflow: ellipsis;\n      white-space: nowrap;\n      overflow: hidden;\n}\n.compute-item .value span[data-v-19336c8e] {\n      height: 24px;\n      line-height: 24px;\n}\n.compute-item .value span.color[data-v-19336c8e] {\n        display: inline-block;\n        width: 20px;\n        height: 20px;\n        border-radius: 50%;\n}\n.compute-item .note[data-v-19336c8e] {\n    -ms-flex: 0 0 5%;\n        flex: 0 0 5%;\n}\n.compute-item .note .item-notes[data-v-19336c8e] {\n      float: right;\n      color: #ababab;\n      line-height: 24px;\n      /*padding: 0 5px;*/\n      padding-right: 15px;\n}\n"],sourceRoot:""}])},372:function(t,e,n){var i=n(371);"string"==typeof i&&(i=[[t.i,i,""]]),i.locals&&(t.exports=i.locals);n(226)("6ea14c28",i,!0)},373:function(t,e,n){n(372);var i=n(0)(n(370),n(374),"data-v-19336c8e",null);t.exports=i.exports},374:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"compute-item"},[n("span",{staticClass:"label"},[t._v(t._s(t.label))]),t._v(" "),n("div",{directives:[{name:"show",rawName:"v-show",value:"color"!==t.editType,expression:"editType!=='color'"}],staticClass:"value"},[t.canEdit&&"check"!==t.action&&"approve"!==t.action&&"checkOnly"!==t.action?n("input",{ref:"input",attrs:{type:t.editType,placeholder:t.placeholder},domProps:{value:t.value},on:{input:t.input}}):n("span",[t._v(t._s(t.value))])]),t._v(" "),n("div",{directives:[{name:"show",rawName:"v-show",value:"color"===t.editType,expression:"editType==='color'"}],staticClass:"value"},[n("span",{staticClass:"color",style:{background:t.color}})]),t._v(" "),n("div",{directives:[{name:"show",rawName:"v-show",value:t.hasArrow,expression:"hasArrow"}],staticClass:"note"},[n("span",{directives:[{name:"show",rawName:"v-show",value:t.hasArrow&&t.arrowText,expression:"hasArrow&&arrowText"}],staticClass:"item-notes",on:{click:t.noteClick}},[t._v(t._s(t.arrowText))]),t._v(" "),n("i",{directives:[{name:"show",rawName:"v-show",value:t.hasArrow,expression:"hasArrow"}],staticClass:"icon ion-ios-arrow-right arrow "})]),t._v(" "),n("div",{staticClass:"hairline-top"})])},staticRenderFns:[]}},376:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0}),e.default={name:"mwRadio",data:function(){return{tmpChosen:{}}},props:{obj:{type:Object,required:!0},chosenObj:{type:Object},hasArrow:{type:Boolean,default:!0},iClick:{type:Function},aClick:{type:Function}},methods:{itemClick:function(){this.$set(this.obj,"chosen",!0),this.iClick&&this.iClick(this.obj)},arrowClick:function(){this.aClick&&this.aClick(this.obj)}}}},377:function(t,e,n){e=t.exports=n(225)(),e.push([t.i,"\n.item .label[data-v-5a0c5df3] {\n  width: 90%;\n  height: 100%;\n}\n.item .label .check[data-v-5a0c5df3] {\n    width: 10%;\n    float: left;\n    height: 100%;\n    padding: 10px 0;\n}\n.item .label .check .radio-icon[data-v-5a0c5df3] {\n      left: 10px;\n      color: #bbbbbb;\n}\n.item .label .check .radio-icon.active[data-v-5a0c5df3] {\n        color: #416eb6;\n}\n.item .label .radio-content[data-v-5a0c5df3] {\n    width: 90%;\n    float: right;\n    padding-left: 15px;\n}\n.item .arrow[data-v-5a0c5df3] {\n  color: #DDD;\n  padding: 10px 0;\n  height: 100% !important;\n}\n","",{version:3,sources:["/Users/zxf/code/motooling/mt-app/webs-mtapp/mtApp/mt/www_dev/src/components/mwRadio.vue"],names:[],mappings:";AACA;EACE,WAAW;EACX,aAAa;CACd;AACD;IACI,WAAW;IACX,YAAY;IACZ,aAAa;IACb,gBAAgB;CACnB;AACD;MACM,WAAW;MACX,eAAe;CACpB;AACD;QACQ,eAAe;CACtB;AACD;IACI,WAAW;IACX,aAAa;IACb,mBAAmB;CACtB;AACD;EACE,YAAY;EACZ,gBAAgB;EAChB,wBAAwB;CACzB",file:"mwRadio.vue",sourcesContent:["\n.item .label[data-v-5a0c5df3] {\n  width: 90%;\n  height: 100%;\n}\n.item .label .check[data-v-5a0c5df3] {\n    width: 10%;\n    float: left;\n    height: 100%;\n    padding: 10px 0;\n}\n.item .label .check .radio-icon[data-v-5a0c5df3] {\n      left: 10px;\n      color: #bbbbbb;\n}\n.item .label .check .radio-icon.active[data-v-5a0c5df3] {\n        color: #416eb6;\n}\n.item .label .radio-content[data-v-5a0c5df3] {\n    width: 90%;\n    float: right;\n    padding-left: 15px;\n}\n.item .arrow[data-v-5a0c5df3] {\n  color: #DDD;\n  padding: 10px 0;\n  height: 100% !important;\n}\n"],sourceRoot:""}])},378:function(t,e,n){var i=n(377);"string"==typeof i&&(i=[[t.i,i,""]]),i.locals&&(t.exports=i.locals);n(226)("e60fe1aa",i,!0)},379:function(t,e,n){n(378);var i=n(0)(n(376),n(380),"data-v-5a0c5df3",null);t.exports=i.exports},380:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"item item-icon-right"},[n("div",{staticClass:"label",on:{click:t.itemClick}},[n("div",{staticClass:"check"},[n("i",{class:["icon radio-icon",t.obj.chosen?"ion-android-radio-button-on active":"ion-android-radio-button-off"]})]),t._v(" "),n("input",{staticStyle:{position:"absolute",left:"-5000px",top:"-5000px"},attrs:{type:"radio"}}),t._v(" "),n("div",{staticClass:"radio-content"},[t._t("default")],2)]),t._v(" "),n("i",{directives:[{name:"show",rawName:"v-show",value:t.hasArrow,expression:"hasArrow"}],staticClass:"icon ion-ios-arrow-right arrow",on:{click:t.arrowClick}})])},staticRenderFns:[]}},382:function(t,e,n){"use strict";function i(t){return n.i(v.a)({url:"api/customerOrder/dict/salesmanList",method:"post",data:t})}function a(t){return n.i(v.a)({url:"api/customerOrder/dict/rawNatureList",method:"post",data:t})}function o(t){return n.i(v.a)({url:"api/customerOrder/dict/processCostItemList",method:"post",data:t})}function s(t){return n.i(v.a)({url:"api/customerOrder/dict/defaultSubPartList",method:"post",data:t})}function c(t){return n.i(v.a)({url:"api/customerOrder/dict/coinTypeList",method:"post",data:t})}function r(t){return n.i(v.a)({url:"api/customerOrder/dict/outWayList",method:"post",data:t})}function l(t){return n.i(v.a)({url:"api/customerOrder/dict/waterTypeList",method:"post",data:t})}function d(t){return n.i(v.a)({url:"api/customerOrder/dict/runnerTypeList",method:"post",data:t})}function p(t){return n.i(v.a)({url:"api/customerOrder/dict/cstCompanyList",method:"post",data:t})}function u(t){return n.i(v.a)({url:"/api/dict/invoice_type",method:"post",data:t})}function h(t){return n.i(v.a)({url:"/api/pc/supplier/list",method:"post",data:t})}function A(t){return n.i(v.a)({url:"/api/dict/query_mat_by_supplier",method:"post",data:t})}function m(t){return n.i(v.a)({url:"/api/dict/search/matName",method:"post",data:t})}function f(t){return n.i(v.a)({url:"api/company/getAllDeptMember",method:"post",data:t})}e.e=i,e.c=a,e.a=o,e.d=s,e.h=c,e.i=r,e.f=l,e.g=d,e.b=p,e.j=u,e.k=h,e.n=A,e.m=m,e.l=f;var v=n(10)},398:function(t,e,n){"use strict";function i(t){return n.i(d.a)({url:"/api/pc/query/requestList",method:"post",data:t})}function a(t){return n.i(d.a)({url:"/api/pc/query/request",method:"post",data:t})}function o(t){return n.i(d.a)({url:"/api/pc/request/delete",method:"post",data:t})}function s(t){return n.i(d.a)({url:"/api/pc/request/deleteLine",method:"post",data:t})}function c(t){return n.i(d.a)({url:"/api/pc/request/referHis",method:"post",data:t})}function r(t){return n.i(d.a)({url:"/api/pc/request/modify",method:"post",data:t})}function l(t){return n.i(d.a)({url:"/api/pc/request/modifyLine",method:"post",data:t})}e.a=i,e.d=a,e.g=o,e.e=s,e.b=c,e.f=r,e.c=l;var d=n(10)},400:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var i=n(21),a=n.n(i);e.default={name:"mwSearch",props:{placeholder:{type:String,required:!0},value:{type:String,required:!0},search:{type:Function,required:!0},cancelText:{type:String,required:!0},cancelAction:{type:Function,required:!0}},data:function(){return{type:this.inputType,showClear:!1,showPassword:!0}},methods:{input:function(t){var e=t.target.value;this.$emit("input",e)},focus:function(){this.showClear=!0,this.$emit("focus",this.v)},blur:function(){this.showClear=!1,this.$emit("blur",this.v)},clear:function(){this.$refs.input.blur(),this.$refs.input.value="",this.v="",this.$emit("input",""),this.showClear=!1,this.$refs.input.focus()},cancel:function(){this.cancelAction&&this.cancelAction()},searchAction:a.a.debounce(function(){this.search&&this.search()},800)},watch:{value:function(t){this.showClear=!!t,t&&this.searchAction()}}}},403:function(t,e,n){e=t.exports=n(225)(),e.push([t.i,"\n.mw-search[data-v-03fbc5a6] {\n  width: 100%;\n  display: -ms-flexbox;\n  display: flex;\n  -ms-flex-pack: center;\n      justify-content: center;\n  -ms-flex-align: center;\n      align-items: center;\n  padding-left: 15px;\n}\n.mw-search .mw-search-input[data-v-03fbc5a6] {\n    height: 50px;\n    position: relative;\n    background-color: white;\n    border-radius: 5px;\n    width: 80%;\n    display: inline-block;\n}\n.mw-search .mw-search-input input[data-v-03fbc5a6] {\n      height: 100%;\n      margin: 0 auto;\n      background-color: transparent;\n      width: 100%;\n}\n.mw-search .mw-search-input .clear[data-v-03fbc5a6] {\n      position: absolute;\n      top: 14px;\n      right: 9px;\n      z-index: 3;\n      display: none;\n      color: #bbbbbb;\n}\n.mw-search .mw-search-input .clear.active[data-v-03fbc5a6] {\n        display: block;\n}\n.mw-search .mw-search-input .clear.eye[data-v-03fbc5a6] {\n        right: 30px;\n        font-size: 20px;\n}\n.mw-search .cancel[data-v-03fbc5a6] {\n    -ms-flex: 1;\n        flex: 1;\n    text-align: center;\n    color: #416eb6;\n    transition: all .3s;\n}\n","",{version:3,sources:["/Users/zxf/code/motooling/mt-app/webs-mtapp/mtApp/mt/www_dev/src/components/mw-search.vue"],names:[],mappings:";AACA;EACE,YAAY;EACZ,qBAAqB;EACrB,cAAc;EACd,sBAAsB;MAClB,wBAAwB;EAC5B,uBAAuB;MACnB,oBAAoB;EACxB,mBAAmB;CACpB;AACD;IACI,aAAa;IACb,mBAAmB;IACnB,wBAAwB;IACxB,mBAAmB;IACnB,WAAW;IACX,sBAAsB;CACzB;AACD;MACM,aAAa;MACb,eAAe;MACf,8BAA8B;MAC9B,YAAY;CACjB;AACD;MACM,mBAAmB;MACnB,UAAU;MACV,WAAW;MACX,WAAW;MACX,cAAc;MACd,eAAe;CACpB;AACD;QACQ,eAAe;CACtB;AACD;QACQ,YAAY;QACZ,gBAAgB;CACvB;AACD;IACI,YAAY;QACR,QAAQ;IACZ,mBAAmB;IACnB,eAAe;IACf,oBAAoB;CACvB",file:"mw-search.vue",sourcesContent:["\n.mw-search[data-v-03fbc5a6] {\n  width: 100%;\n  display: -ms-flexbox;\n  display: flex;\n  -ms-flex-pack: center;\n      justify-content: center;\n  -ms-flex-align: center;\n      align-items: center;\n  padding-left: 15px;\n}\n.mw-search .mw-search-input[data-v-03fbc5a6] {\n    height: 50px;\n    position: relative;\n    background-color: white;\n    border-radius: 5px;\n    width: 80%;\n    display: inline-block;\n}\n.mw-search .mw-search-input input[data-v-03fbc5a6] {\n      height: 100%;\n      margin: 0 auto;\n      background-color: transparent;\n      width: 100%;\n}\n.mw-search .mw-search-input .clear[data-v-03fbc5a6] {\n      position: absolute;\n      top: 14px;\n      right: 9px;\n      z-index: 3;\n      display: none;\n      color: #bbbbbb;\n}\n.mw-search .mw-search-input .clear.active[data-v-03fbc5a6] {\n        display: block;\n}\n.mw-search .mw-search-input .clear.eye[data-v-03fbc5a6] {\n        right: 30px;\n        font-size: 20px;\n}\n.mw-search .cancel[data-v-03fbc5a6] {\n    -ms-flex: 1;\n        flex: 1;\n    text-align: center;\n    color: #416eb6;\n    transition: all .3s;\n}\n"],sourceRoot:""}])},404:function(t,e,n){var i=n(403);"string"==typeof i&&(i=[[t.i,i,""]]),i.locals&&(t.exports=i.locals);n(226)("0b6931fc",i,!0)},405:function(t,e,n){n(404);var i=n(0)(n(400),n(406),"data-v-03fbc5a6",null);t.exports=i.exports},406:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"mw-search"},[n("div",{staticClass:"mw-search-input"},[n("input",{ref:"input",attrs:{type:"search",placeholder:t.placeholder},domProps:{value:t.value},on:{focus:t.focus,input:t.input,blur:t.blur}}),t._v(" "),n("div",{class:["clear",t.showClear?"active":""],on:{click:t.clear}},[n("i",{staticClass:"icon ion-ios-close"})])]),t._v(" "),n("span",{directives:[{name:"show",rawName:"v-show",value:t.showClear||t.value,expression:"showClear||value"}],staticClass:"cancel",on:{click:t.cancel}},[t._v(t._s(t.cancelText))])])},staticRenderFns:[]}},528:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var i=n(373),a=n.n(i),o=n(48),s=n(398),c=n(379),r=n.n(c),l=n(382),d=n(405),p=n.n(d);e.default={components:{MwSearch:p.a,MwRadio:r.a,CstItem:a.a},name:"requisitionLineInfo",data:function(){return{line_id:"",action:"check",showMats:!1,matSearchList:[],filterMatSearchList:[],chosenMat:{},matSearch:"",request_id:"",line_info:{line_ids:"",mat_desc:"请选择",mat_id:"",mat_model:"",mat_no:"",mat_type_id:"",mat_type_name:"",price:"",quantity:"",remark:"",delivery_date:n.i(o.a)(),unit:"",up:"",weight:"",unit_weight:"",unit_price:""}}},mounted:function(){this.action=this.$route.params.action,"new"!==this.action?this.line_info=JSON.parse(this.$route.query.line_info):"new"===this.action&&(this.request_id=this.$route.query.line_info)},computed:{totalWeight:function(){var t=this.line_info.unit_weight?this.line_info.quantity*this.line_info.unit_weight:this.line_info.weight;return this.line_info.weight=t,this.line_info.up=this.line_info.quantity*this.line_info.price,t}},methods:{onMenuClick:function(){this.action="edit"},searchCancel:function(){this.matSearch="",this.matSearchList=[]},getMats:function(){var t=this,e={query:{mat_desc:this.matSearch,is_include_process_type:0}};n.i(l.m)(e).then(function(e){0===e.mat_list.length?(t.matSearchList=[],$toast.show("搜索结果为空")):t.matSearchList=e.mat_list}).catch(function(t){$toast.show(t.description)})},itemClick:function(t){t!==this.chosenMat&&(this.chosenMat.chosen=!1,this.chosenMat=t)},chooseMatItem:function(){this.showMats=!1,this.line_info.mat_desc=this.chosenMat.mat_desc,this.line_info.mat_id=this.chosenMat.mat_id,this.line_info.mat_no=this.chosenMat.mat_no,this.line_info.unit=this.chosenMat.unit,this.line_info.unit_weight=this.chosenMat.weight,this.line_info.price=this.chosenMat.price,this.line_info.mat_type_name=this.chosenMat.mat_type_name,this.line_info.mat_type_id=this.chosenMat.mat_type_id,console.log(this.chosenMat)},saveData:function(){var t={line_list:[this.line_info],request_id:this.request_id};n.i(s.c)(t).then(function(t){$toast.show("保存成功"),history.go(-1)}).catch(function(t){$toast.show(t.description)})},chooseMat:function(){"new"!==this.action&&"edit"!==this.action||(this.showMats=!0)}}}},625:function(t,e,n){e=t.exports=n(225)(),e.push([t.i,"","",{version:3,sources:[],names:[],mappings:"",file:"line_info.vue",sourceRoot:""}])},764:function(t,e,n){var i=n(625);"string"==typeof i&&(i=[[t.i,i,""]]),i.locals&&(t.exports=i.locals);n(226)("6d48a7a0",i,!0)},911:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{directives:[{name:"nav",rawName:"v-nav",value:{hideNavbar:!0},expression:"{hideNavbar:true}"}],staticClass:"mw-page page purchase"},[n("div",{staticClass:"mw-page-header"},[n("i",{staticClass:"left icon ion-ios-arrow-back",on:{click:function(e){t.$router.go(-1)}}}),t._v(" "),n("span",[t._v("请购单详情")]),t._v(" "),"check"===t.action?n("i",{staticClass:"right",on:{click:t.onMenuClick}},[t._v("编辑")]):"edit"===t.action?n("i",{staticClass:"right",on:{click:function(e){t.action="check"}}},[t._v("取消")]):n("i",{staticClass:"right"})]),t._v(" "),n("div",{staticClass:"mw-page-content"},[n("cst-item",{attrs:{label:"物料描述",action:t.action,canEdit:!1,hasArrow:!0},nativeOn:{click:function(e){t.chooseMat(e)}},model:{value:t.line_info.mat_desc,callback:function(e){t.$set(t.line_info,"mat_desc",e)},expression:"line_info.mat_desc"}}),t._v(" "),n("cst-item",{attrs:{label:"物料编码",action:t.action,canEdit:!1},model:{value:t.line_info.mat_no,callback:function(e){t.$set(t.line_info,"mat_no",e)},expression:"line_info.mat_no"}}),t._v(" "),n("cst-item",{attrs:{label:"单位",action:t.action,canEdit:!1},model:{value:t.line_info.unit,callback:function(e){t.$set(t.line_info,"unit",e)},expression:"line_info.unit"}}),t._v(" "),n("cst-item",{attrs:{label:"数量",action:t.action,editType:"number"},model:{value:t.line_info.quantity,callback:function(e){t.$set(t.line_info,"quantity",e)},expression:"line_info.quantity"}}),t._v(" "),n("cst-item",{attrs:{label:"单价",action:t.action,editType:"number"},model:{value:t.line_info.price,callback:function(e){t.$set(t.line_info,"price",e)},expression:"line_info.price"}}),t._v(" "),n("cst-item",{attrs:{label:"重量",action:t.action,editType:"number"},model:{value:t.totalWeight,callback:function(e){t.totalWeight=e},expression:"totalWeight"}}),t._v(" "),n("cst-item",{attrs:{label:"金额",action:t.action,editType:"number"},model:{value:t.line_info.up,callback:function(e){t.$set(t.line_info,"up",e)},expression:"line_info.up"}}),t._v(" "),"check"===t.action?n("cst-item",{attrs:{label:"交货日期",action:t.action},model:{value:t.line_info.delivery_date,callback:function(e){t.$set(t.line_info,"delivery_date",e)},expression:"line_info.delivery_date"}}):n("datepicker",{attrs:{label:"交货日期","date-format":"yyyy-mm-dd"},model:{value:t.line_info.delivery_date,callback:function(e){t.$set(t.line_info,"delivery_date",e)},expression:"line_info.delivery_date"}}),t._v(" "),n("div",{staticClass:"item-divider",staticStyle:{"padding-left":"15px"}},[t._v("备注")]),t._v(" "),n("textarea",{directives:[{name:"model",rawName:"v-model",value:t.line_info.remark,expression:"line_info.remark"}],staticStyle:{margin:"0"},attrs:{disabled:"check"===t.action},domProps:{value:t.line_info.remark},on:{input:function(e){e.target.composing||t.$set(t.line_info,"remark",e.target.value)}}})],1),t._v(" "),n("div",{directives:[{name:"show",rawName:"v-show",value:t.showMats,expression:"showMats"}],staticClass:"mask",on:{click:function(e){t.showMats=!1}}}),t._v(" "),n("section",{class:["filter",t.showMats?"active":""],staticStyle:{display:"flex","flex-direction":"column"}},[n("div",{directives:[{name:"show",rawName:"v-show",value:t.showMats,expression:"showMats"}],staticClass:"side-title"},[t._v("\n      请选择物料\n    ")]),t._v(" "),n("div",{staticClass:"side-content",staticStyle:{display:"flex","flex-direction":"column"}},[n("div",{staticStyle:{height:"50px"}},[n("mw-search",{attrs:{placeholder:"输入物料关键字",search:t.getMats,cancelText:"取消",cancelAction:t.searchCancel},model:{value:t.matSearch,callback:function(e){t.matSearch=e},expression:"matSearch"}})],1),t._v(" "),n("div",{staticStyle:{flex:"1"}},t._l(t.matSearchList,function(e){return n("mw-radio",{attrs:{obj:e,chosenObj:t.chosenMat,iClick:t.itemClick,hasArrow:!1}},[t._v(t._s(e.mat_desc))])}))]),t._v(" "),n("div",{directives:[{name:"show",rawName:"v-show",value:t.showMats,expression:"showMats"}],staticClass:"side-footer"},[n("div",{staticClass:"filter-btns"},[n("div",{staticClass:"filter-btn",on:{click:t.chooseMatItem}},[t._v("\n          确定\n        ")])])])]),t._v(" "),n("div",{directives:[{name:"show",rawName:"v-show",value:"edit"===t.action||"new"===t.action,expression:"action === 'edit' || action === 'new'"}],staticClass:"mw-page-footer"},[n("div",{staticClass:"btns"},[n("div",{staticClass:"btn",on:{click:t.saveData}},[t._v("\n                保存\n            ")])])])])},staticRenderFns:[]}}});
//# sourceMappingURL=13.f022564388ae2dd69e8c.js.map
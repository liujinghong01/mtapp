webpackJsonp([53],{297:function(t,e,n){n(779);var a=n(0)(n(496),n(926),"data-v-56be813d",null);t.exports=a.exports},370:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});e.default={name:"cstItem",data:function(){return{selectId:"select-"+Math.random().toString(36).substring(3,6)}},props:{label:{type:String,required:!0},value:{type:"",default:""},placeholder:{type:String,default:""},canEdit:{type:Boolean,default:!0},hasArrow:{type:Boolean,default:!1},arrowText:{type:[String,Number],default:""},arrowClick:{type:Function},editType:{type:String,default:"text"},isSelect:{type:Boolean,default:!1},options:{type:Array},action:{type:String,default:"check"},color:""},methods:{noteClick:function(){this.arrowClick&&this.arrowClick()},input:function(t){var e=t.target.value;this.$emit("input",e)}}}},371:function(t,e,n){e=t.exports=n(225)(),e.push([t.i,"\n.compute-item[data-v-19336c8e] {\n  min-height: 49px;\n  position: relative;\n  padding: 12px 15px;\n  background-color: white;\n  display: -ms-flexbox;\n  display: flex;\n}\n.compute-item.noneBorder > .hairlineTop[data-v-19336c8e]:before {\n    background: red;\n}\n.compute-item .arrow[data-v-19336c8e] {\n    color: #DDD;\n    position: absolute;\n    right: 15px;\n    top: 14px;\n    font-size: 20px;\n}\n.compute-item .arrow.down[data-v-19336c8e] {\n      top: 13px;\n}\n.compute-item .label[data-v-19336c8e] {\n    -ms-flex: 0 0  auto;\n        flex: 0 0  auto;\n    height: 100%;\n    display: table;\n    padding: 7px 10px 7px 0px;\n    max-width: 52%;\n    color: #484746;\n    text-align: left;\n    line-height: 12px;\n}\n.compute-item .value[data-v-19336c8e] {\n    -ms-flex: 1 0 auto;\n        flex: 1 0 auto;\n    text-align: right;\n}\n.compute-item .value input[data-v-19336c8e] {\n      margin: 0;\n      padding: 0;\n      border: none;\n      color: #ababab;\n      width: 100%;\n      height: 24px;\n      line-height: 24px;\n      text-align: right;\n      text-overflow: ellipsis;\n      white-space: nowrap;\n      overflow: hidden;\n}\n.compute-item .value span[data-v-19336c8e] {\n      height: 24px;\n      line-height: 24px;\n}\n.compute-item .value span.color[data-v-19336c8e] {\n        display: inline-block;\n        width: 20px;\n        height: 20px;\n        border-radius: 50%;\n}\n.compute-item .note[data-v-19336c8e] {\n    -ms-flex: 0 0 5%;\n        flex: 0 0 5%;\n}\n.compute-item .note .item-notes[data-v-19336c8e] {\n      float: right;\n      color: #ababab;\n      line-height: 24px;\n      /*padding: 0 5px;*/\n      padding-right: 15px;\n}\n","",{version:3,sources:["/Users/zxf/code/motooling/mt-app/webs-mtapp/mtApp/mt/www_dev/src/components/cstItem.vue"],names:[],mappings:";AACA;EACE,iBAAiB;EACjB,mBAAmB;EACnB,mBAAmB;EACnB,wBAAwB;EACxB,qBAAqB;EACrB,cAAc;CACf;AACD;IACI,gBAAgB;CACnB;AACD;IACI,YAAY;IACZ,mBAAmB;IACnB,YAAY;IACZ,UAAU;IACV,gBAAgB;CACnB;AACD;MACM,UAAU;CACf;AACD;IACI,oBAAoB;QAChB,gBAAgB;IACpB,aAAa;IACb,eAAe;IACf,0BAA0B;IAC1B,eAAe;IACf,eAAe;IACf,iBAAiB;IACjB,kBAAkB;CACrB;AACD;IACI,mBAAmB;QACf,eAAe;IACnB,kBAAkB;CACrB;AACD;MACM,UAAU;MACV,WAAW;MACX,aAAa;MACb,eAAe;MACf,YAAY;MACZ,aAAa;MACb,kBAAkB;MAClB,kBAAkB;MAClB,wBAAwB;MACxB,oBAAoB;MACpB,iBAAiB;CACtB;AACD;MACM,aAAa;MACb,kBAAkB;CACvB;AACD;QACQ,sBAAsB;QACtB,YAAY;QACZ,aAAa;QACb,mBAAmB;CAC1B;AACD;IACI,iBAAiB;QACb,aAAa;CACpB;AACD;MACM,aAAa;MACb,eAAe;MACf,kBAAkB;MAClB,mBAAmB;MACnB,oBAAoB;CACzB",file:"cstItem.vue",sourcesContent:["\n.compute-item[data-v-19336c8e] {\n  min-height: 49px;\n  position: relative;\n  padding: 12px 15px;\n  background-color: white;\n  display: -ms-flexbox;\n  display: flex;\n}\n.compute-item.noneBorder > .hairlineTop[data-v-19336c8e]:before {\n    background: red;\n}\n.compute-item .arrow[data-v-19336c8e] {\n    color: #DDD;\n    position: absolute;\n    right: 15px;\n    top: 14px;\n    font-size: 20px;\n}\n.compute-item .arrow.down[data-v-19336c8e] {\n      top: 13px;\n}\n.compute-item .label[data-v-19336c8e] {\n    -ms-flex: 0 0  auto;\n        flex: 0 0  auto;\n    height: 100%;\n    display: table;\n    padding: 7px 10px 7px 0px;\n    max-width: 52%;\n    color: #484746;\n    text-align: left;\n    line-height: 12px;\n}\n.compute-item .value[data-v-19336c8e] {\n    -ms-flex: 1 0 auto;\n        flex: 1 0 auto;\n    text-align: right;\n}\n.compute-item .value input[data-v-19336c8e] {\n      margin: 0;\n      padding: 0;\n      border: none;\n      color: #ababab;\n      width: 100%;\n      height: 24px;\n      line-height: 24px;\n      text-align: right;\n      text-overflow: ellipsis;\n      white-space: nowrap;\n      overflow: hidden;\n}\n.compute-item .value span[data-v-19336c8e] {\n      height: 24px;\n      line-height: 24px;\n}\n.compute-item .value span.color[data-v-19336c8e] {\n        display: inline-block;\n        width: 20px;\n        height: 20px;\n        border-radius: 50%;\n}\n.compute-item .note[data-v-19336c8e] {\n    -ms-flex: 0 0 5%;\n        flex: 0 0 5%;\n}\n.compute-item .note .item-notes[data-v-19336c8e] {\n      float: right;\n      color: #ababab;\n      line-height: 24px;\n      /*padding: 0 5px;*/\n      padding-right: 15px;\n}\n"],sourceRoot:""}])},372:function(t,e,n){var a=n(371);"string"==typeof a&&(a=[[t.i,a,""]]),a.locals&&(t.exports=a.locals);n(226)("6ea14c28",a,!0)},373:function(t,e,n){n(372);var a=n(0)(n(370),n(374),"data-v-19336c8e",null);t.exports=a.exports},374:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"compute-item"},[n("span",{staticClass:"label"},[t._v(t._s(t.label))]),t._v(" "),n("div",{directives:[{name:"show",rawName:"v-show",value:"color"!==t.editType,expression:"editType!=='color'"}],staticClass:"value"},[t.canEdit&&"check"!==t.action&&"approve"!==t.action&&"checkOnly"!==t.action?n("input",{ref:"input",attrs:{type:t.editType,placeholder:t.placeholder},domProps:{value:t.value},on:{input:t.input}}):n("span",[t._v(t._s(t.value))])]),t._v(" "),n("div",{directives:[{name:"show",rawName:"v-show",value:"color"===t.editType,expression:"editType==='color'"}],staticClass:"value"},[n("span",{staticClass:"color",style:{background:t.color}})]),t._v(" "),n("div",{directives:[{name:"show",rawName:"v-show",value:t.hasArrow,expression:"hasArrow"}],staticClass:"note"},[n("span",{directives:[{name:"show",rawName:"v-show",value:t.hasArrow&&t.arrowText,expression:"hasArrow&&arrowText"}],staticClass:"item-notes",on:{click:t.noteClick}},[t._v(t._s(t.arrowText))]),t._v(" "),n("i",{directives:[{name:"show",rawName:"v-show",value:t.hasArrow,expression:"hasArrow"}],staticClass:"icon ion-ios-arrow-right arrow "})]),t._v(" "),n("div",{staticClass:"hairline-top"})])},staticRenderFns:[]}},408:function(t,e,n){"use strict";function a(t){return n.i(c.a)({url:"/api/pm/routing_tmpl_type/list",method:"post",data:t})}function i(t){return n.i(c.a)({url:"/api/pm/routing_tmpl_type/delete",method:"post",data:t})}function o(t){return n.i(c.a)({url:"/api/pm/routing_tmpl_type/edit",method:"post",data:t})}function r(t){return n.i(c.a)({url:"/api/pm/routing_tmpl/modify",method:"post",data:t})}function p(t){return n.i(c.a)({url:"/api/pm/routing_tmpl/list",method:"post",data:t})}function s(t){return n.i(c.a)({url:"/api/pm/routing_tmpl/delete",method:"post",data:t})}function l(t){return n.i(c.a)({url:"/api/pm/routing_tmpl/detail",method:"post",data:t})}e.c=a,e.b=i,e.a=o,e.d=r,e.g=p,e.f=s,e.e=l;var c=n(10)},496:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var a=n(408),i=n(20),o=n.n(i),r=n(373),p=n.n(r);e.default={name:"procTemplate",components:{SwipeItem:o.a,CstItem:p.a},data:function(){return{tmpl_list:[]}},computed:{},activated:function(){this.getData()},mounted:function(){},methods:{tmplDelete:function(t,e){var i=this,o={proc_info:{tmpl_id:t.id}};n.i(a.f)(o).then(function(t){$toast.show("删除成功"),i.tmpl_list.splice(e,1)}).catch(function(t){$toast.show(t.description)})},getData:function(t){var e=this,i={query:{status:"",tmpl_name:"",tmpl_type_id:"",tmpl_type_name:""}};n.i(a.g)(i).then(function(t){e.tmpl_list=t.routing_tmpl_info}).catch(function(t){$toast.show(t.description)})},onItemClick:function(t){$router.forward({path:"templateDetaile",query:{action:"check",tmpl_id:t.id}})},add:function(){$router.forward({path:"templateDetaile",query:{action:"new"}})}}}},640:function(t,e,n){e=t.exports=n(225)(),e.push([t.i,"\n.calendarItem[data-v-56be813d] {\n  position: relative;\n}\n.calendarItem .color[data-v-56be813d] {\n    width: 30px;\n    height: 17px;\n    position: absolute;\n    left: 0px;\n    top: -2px;\n    background: red;\n    border-radius: 5px;\n}\n","",{version:3,sources:["/Users/zxf/code/motooling/mt-app/webs-mtapp/mtApp/mt/www_dev/src/views/production/procTemplate/index.vue"],names:[],mappings:";AACA;EACE,mBAAmB;CACpB;AACD;IACI,YAAY;IACZ,aAAa;IACb,mBAAmB;IACnB,UAAU;IACV,UAAU;IACV,gBAAgB;IAChB,mBAAmB;CACtB",file:"index.vue",sourcesContent:["\n.calendarItem[data-v-56be813d] {\n  position: relative;\n}\n.calendarItem .color[data-v-56be813d] {\n    width: 30px;\n    height: 17px;\n    position: absolute;\n    left: 0px;\n    top: -2px;\n    background: red;\n    border-radius: 5px;\n}\n"],sourceRoot:""}])},779:function(t,e,n){var a=n(640);"string"==typeof a&&(a=[[t.i,a,""]]),a.locals&&(t.exports=a.locals);n(226)("1838677d",a,!0)},926:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{directives:[{name:"nav",rawName:"v-nav",value:{hideNavbar:!0},expression:"{hideNavbar:true}"}],staticClass:"mw-page page storage"},[n("div",{staticClass:"mw-page-header"},[n("i",{staticClass:"left icon ion-ios-arrow-back",on:{click:function(e){t.$router.go(-1)}}}),t._v(" "),n("span",[t._v("工艺模板列表")]),t._v(" "),n("i",{staticClass:"right"})]),t._v(" "),n("div",{staticClass:"mw-page-content"},[n("div",{staticStyle:{flex:"1"}},[n("div",{staticClass:"content"},t._l(t.tmpl_list,function(e,a){return n("swipe-item",{staticClass:"req-item",attrs:{obj:e,canSwipe:!0,swipeItemText:"删除"},on:{SwipeItemClick:function(n){t.tmplDelete(e,a)}},nativeOn:{click:function(n){t.onItemClick(e)}}},[n("p",[t._v(t._s(e.tmpl_name))])])}))])]),t._v(" "),n("div",{staticClass:"mw-page-footer"},[n("div",{staticClass:"btns"},[n("div",{staticClass:"btn",on:{click:t.add}},[t._v("\n        添加\n      ")])])])])},staticRenderFns:[]}}});
//# sourceMappingURL=53.75a23bc51b86c5f5e0e3.js.map
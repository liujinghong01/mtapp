webpackJsonp([44],{314:function(t,n,e){e(737);var a=e(0)(e(513),e(882),"data-v-284da2c7",null);t.exports=a.exports},370:function(t,n,e){"use strict";Object.defineProperty(n,"__esModule",{value:!0});n.default={name:"cstItem",data:function(){return{selectId:"select-"+Math.random().toString(36).substring(3,6)}},props:{label:{type:String,required:!0},value:{type:"",default:""},placeholder:{type:String,default:""},canEdit:{type:Boolean,default:!0},hasArrow:{type:Boolean,default:!1},arrowText:{type:[String,Number],default:""},arrowClick:{type:Function},editType:{type:String,default:"text"},isSelect:{type:Boolean,default:!1},options:{type:Array},action:{type:String,default:"check"},color:""},methods:{noteClick:function(){this.arrowClick&&this.arrowClick()},input:function(t){var n=t.target.value;this.$emit("input",n)}}}},371:function(t,n,e){n=t.exports=e(225)(),n.push([t.i,"\n.compute-item[data-v-19336c8e] {\n  min-height: 49px;\n  position: relative;\n  padding: 12px 15px;\n  background-color: white;\n  display: -ms-flexbox;\n  display: flex;\n}\n.compute-item.noneBorder > .hairlineTop[data-v-19336c8e]:before {\n    background: red;\n}\n.compute-item .arrow[data-v-19336c8e] {\n    color: #DDD;\n    position: absolute;\n    right: 15px;\n    top: 14px;\n    font-size: 20px;\n}\n.compute-item .arrow.down[data-v-19336c8e] {\n      top: 13px;\n}\n.compute-item .label[data-v-19336c8e] {\n    -ms-flex: 0 0  auto;\n        flex: 0 0  auto;\n    height: 100%;\n    display: table;\n    padding: 7px 10px 7px 0px;\n    max-width: 52%;\n    color: #484746;\n    text-align: left;\n    line-height: 12px;\n}\n.compute-item .value[data-v-19336c8e] {\n    -ms-flex: 1 0 auto;\n        flex: 1 0 auto;\n    text-align: right;\n}\n.compute-item .value input[data-v-19336c8e] {\n      margin: 0;\n      padding: 0;\n      border: none;\n      color: #ababab;\n      width: 100%;\n      height: 24px;\n      line-height: 24px;\n      text-align: right;\n      text-overflow: ellipsis;\n      white-space: nowrap;\n      overflow: hidden;\n}\n.compute-item .value span[data-v-19336c8e] {\n      height: 24px;\n      line-height: 24px;\n}\n.compute-item .value span.color[data-v-19336c8e] {\n        display: inline-block;\n        width: 20px;\n        height: 20px;\n        border-radius: 50%;\n}\n.compute-item .note[data-v-19336c8e] {\n    -ms-flex: 0 0 5%;\n        flex: 0 0 5%;\n}\n.compute-item .note .item-notes[data-v-19336c8e] {\n      float: right;\n      color: #ababab;\n      line-height: 24px;\n      /*padding: 0 5px;*/\n      padding-right: 15px;\n}\n","",{version:3,sources:["/Users/zxf/code/motooling/mt-app/webs-mtapp/mtApp/mt/www_dev/src/components/cstItem.vue"],names:[],mappings:";AACA;EACE,iBAAiB;EACjB,mBAAmB;EACnB,mBAAmB;EACnB,wBAAwB;EACxB,qBAAqB;EACrB,cAAc;CACf;AACD;IACI,gBAAgB;CACnB;AACD;IACI,YAAY;IACZ,mBAAmB;IACnB,YAAY;IACZ,UAAU;IACV,gBAAgB;CACnB;AACD;MACM,UAAU;CACf;AACD;IACI,oBAAoB;QAChB,gBAAgB;IACpB,aAAa;IACb,eAAe;IACf,0BAA0B;IAC1B,eAAe;IACf,eAAe;IACf,iBAAiB;IACjB,kBAAkB;CACrB;AACD;IACI,mBAAmB;QACf,eAAe;IACnB,kBAAkB;CACrB;AACD;MACM,UAAU;MACV,WAAW;MACX,aAAa;MACb,eAAe;MACf,YAAY;MACZ,aAAa;MACb,kBAAkB;MAClB,kBAAkB;MAClB,wBAAwB;MACxB,oBAAoB;MACpB,iBAAiB;CACtB;AACD;MACM,aAAa;MACb,kBAAkB;CACvB;AACD;QACQ,sBAAsB;QACtB,YAAY;QACZ,aAAa;QACb,mBAAmB;CAC1B;AACD;IACI,iBAAiB;QACb,aAAa;CACpB;AACD;MACM,aAAa;MACb,eAAe;MACf,kBAAkB;MAClB,mBAAmB;MACnB,oBAAoB;CACzB",file:"cstItem.vue",sourcesContent:["\n.compute-item[data-v-19336c8e] {\n  min-height: 49px;\n  position: relative;\n  padding: 12px 15px;\n  background-color: white;\n  display: -ms-flexbox;\n  display: flex;\n}\n.compute-item.noneBorder > .hairlineTop[data-v-19336c8e]:before {\n    background: red;\n}\n.compute-item .arrow[data-v-19336c8e] {\n    color: #DDD;\n    position: absolute;\n    right: 15px;\n    top: 14px;\n    font-size: 20px;\n}\n.compute-item .arrow.down[data-v-19336c8e] {\n      top: 13px;\n}\n.compute-item .label[data-v-19336c8e] {\n    -ms-flex: 0 0  auto;\n        flex: 0 0  auto;\n    height: 100%;\n    display: table;\n    padding: 7px 10px 7px 0px;\n    max-width: 52%;\n    color: #484746;\n    text-align: left;\n    line-height: 12px;\n}\n.compute-item .value[data-v-19336c8e] {\n    -ms-flex: 1 0 auto;\n        flex: 1 0 auto;\n    text-align: right;\n}\n.compute-item .value input[data-v-19336c8e] {\n      margin: 0;\n      padding: 0;\n      border: none;\n      color: #ababab;\n      width: 100%;\n      height: 24px;\n      line-height: 24px;\n      text-align: right;\n      text-overflow: ellipsis;\n      white-space: nowrap;\n      overflow: hidden;\n}\n.compute-item .value span[data-v-19336c8e] {\n      height: 24px;\n      line-height: 24px;\n}\n.compute-item .value span.color[data-v-19336c8e] {\n        display: inline-block;\n        width: 20px;\n        height: 20px;\n        border-radius: 50%;\n}\n.compute-item .note[data-v-19336c8e] {\n    -ms-flex: 0 0 5%;\n        flex: 0 0 5%;\n}\n.compute-item .note .item-notes[data-v-19336c8e] {\n      float: right;\n      color: #ababab;\n      line-height: 24px;\n      /*padding: 0 5px;*/\n      padding-right: 15px;\n}\n"],sourceRoot:""}])},372:function(t,n,e){var a=e(371);"string"==typeof a&&(a=[[t.i,a,""]]),a.locals&&(t.exports=a.locals);e(226)("6ea14c28",a,!0)},373:function(t,n,e){e(372);var a=e(0)(e(370),e(374),"data-v-19336c8e",null);t.exports=a.exports},374:function(t,n){t.exports={render:function(){var t=this,n=t.$createElement,e=t._self._c||n;return e("div",{staticClass:"compute-item"},[e("span",{staticClass:"label"},[t._v(t._s(t.label))]),t._v(" "),e("div",{directives:[{name:"show",rawName:"v-show",value:"color"!==t.editType,expression:"editType!=='color'"}],staticClass:"value"},[t.canEdit&&"check"!==t.action&&"approve"!==t.action&&"checkOnly"!==t.action?e("input",{ref:"input",attrs:{type:t.editType,placeholder:t.placeholder},domProps:{value:t.value},on:{input:t.input}}):e("span",[t._v(t._s(t.value))])]),t._v(" "),e("div",{directives:[{name:"show",rawName:"v-show",value:"color"===t.editType,expression:"editType==='color'"}],staticClass:"value"},[e("span",{staticClass:"color",style:{background:t.color}})]),t._v(" "),e("div",{directives:[{name:"show",rawName:"v-show",value:t.hasArrow,expression:"hasArrow"}],staticClass:"note"},[e("span",{directives:[{name:"show",rawName:"v-show",value:t.hasArrow&&t.arrowText,expression:"hasArrow&&arrowText"}],staticClass:"item-notes",on:{click:t.noteClick}},[t._v(t._s(t.arrowText))]),t._v(" "),e("i",{directives:[{name:"show",rawName:"v-show",value:t.hasArrow,expression:"hasArrow"}],staticClass:"icon ion-ios-arrow-right arrow "})]),t._v(" "),e("div",{staticClass:"hairline-top"})])},staticRenderFns:[]}},397:function(t,n,e){"use strict";function a(t){return e.i(r.a)({url:"/api/pm/pop/incept_list",method:"post",data:t})}function i(t){return e.i(r.a)({url:"/api/pm/pop/incept",method:"post",data:t})}function o(t){return e.i(r.a)({url:"/api/pm/pop/confirm_list",method:"post",data:t})}function s(t){return e.i(r.a)({url:"/api/pm/pop/wait_proc_list",method:"post",data:t})}function p(t){return e.i(r.a)({url:"/api/pm/pg/detail",method:"post",data:t})}function c(t){return e.i(r.a)({url:"/api/pm/pop/allocate",method:"post",data:t})}function A(t){return e.i(r.a)({url:"/api/pm/pop/proc_info",method:"post",data:t})}function l(t){return e.i(r.a)({url:"/api/pm/pop/change_status",method:"post",data:t})}n.c=a,n.d=i,n.h=o,n.f=s,n.a=p,n.b=c,n.e=A,n.g=l;var r=e(10)},513:function(t,n,e){"use strict";Object.defineProperty(n,"__esModule",{value:!0});var a=e(397),i=e(20),o=e.n(i),s=e(373),p=e.n(s),c=e(1);n.default={name:"getTask",components:{SwipeItem:o.a,CstItem:p.a},data:function(){return{tabs:["待领取任务","已领取任务"],tabIndex:0,pg_list:[],pop_list:[],pop_ids:[]}},computed:{},activated:function(){this.getData()},mounted:function(){},methods:{onTabClick:function(t){t!==this.tabIndex&&(this.tabIndex=t,this.getData())},getData:function(){var t=this,n={curr_page:1,page_size:100,query:{pop_status:this.tabIndex+2}};e.i(a.c)(n).then(function(n){0===t.tabIndex?t.pg_list=n.pg_list:t.pop_list=n.pg_list}).catch(function(t){$toast.show(t.description)})},onItemClick:function(t){t.allocated_list.length>0&&c.a.set("allocated_list",t.allocated_list),t.pg_type+""=="0"?$router.forward({path:"/production/getTask/devList",query:{pg_id:t.pg_id,pop_id:t.pop_id}}):$router.forward({path:"/production/getTask/persList",query:{pg_id:t.pg_id,pop_id:t.pop_id}})},check:function(t){if(void 0===t.check)this.$set(t,"check",!0),void 0!==t.pop_id&&this.pop_ids.push(t.pop_id);else if(this.$delete(t,"check"),void 0!==t.pop_id){var n=this.pop_ids.indexOf(t.pop_id);this.pop_ids.splice(n,1)}},incept:function(){var t=this;if(this.pop_ids.length<1)return void $toast.show("请先选择任务");var n={incept_info:{incept_flag:1,pop_ids:this.pop_ids}};e.i(a.d)(n).then(function(n){$toast.show("领取成功"),t.getData()}).catch(function(t){$toast.show(t.description)})}}}},598:function(t,n,e){n=t.exports=e(225)(),n.push([t.i,"\n.itemBox[data-v-284da2c7] {\n  overflow: hidden;\n  transition: all .3s;\n}\n.itemBox .itemTitle[data-v-284da2c7] {\n    margin: 0;\n    height: 40px;\n    line-height: 40px;\n    padding: 0 15px;\n    position: relative;\n}\n.itemBox .itemTitle i[data-v-284da2c7] {\n      position: absolute;\n      right: 15px;\n      top: 0;\n      width: 40px;\n      height: 40px;\n      line-height: 40px;\n      text-align: center;\n      color: #416eb6;\n}\n.checkBox[data-v-284da2c7] {\n  width: 8%;\n  text-align: right;\n  height: 64px;\n  line-height: 64px;\n  position: absolute;\n  left: 0;\n  top: 0;\n  font-size: 20px;\n  color: #9e9e9e;\n}\n.checkBox.active[data-v-284da2c7] {\n    color: #416eb6;\n}\n.itemInfo[data-v-284da2c7] {\n  width: 100%;\n  height: 64px;\n  position: absolute;\n  right: 0;\n  top: 0;\n  padding: 7px 15px 7px 55px;\n  line-height: 20px;\n}\n.itemInfo.hasCheck[data-v-284da2c7] {\n    width: 92%;\n}\n.itemInfo p[data-v-284da2c7] {\n    margin-bottom: 0;\n}\n.itemInfo p span[data-v-284da2c7] {\n      font-size: 12px;\n      color: #999;\n}\n.itemInfo span.right[data-v-284da2c7] {\n    width: 60px;\n    height: 65px;\n    line-height: 65px;\n    position: absolute;\n    right: 0;\n    top: 0;\n    color: #416eb6;\n}\n.itemInfo .status[data-v-284da2c7] {\n    width: 30px;\n    height: 22px;\n    position: absolute;\n    left: 15px;\n    top: 21px;\n}\n.itemInfo .status.active1[data-v-284da2c7] {\n      background: #33FF33;\n}\n.itemInfo .status.active2[data-v-284da2c7] {\n      background: #FF00FF;\n}\n.itemInfo .status.active3[data-v-284da2c7] {\n      background: red;\n}\n.itemInfo .status.active4[data-v-284da2c7] {\n      background: #666666;\n}\n","",{version:3,sources:["/Users/zxf/code/motooling/mt-app/webs-mtapp/mtApp/mt/www_dev/src/views/production/work/getTask.vue"],names:[],mappings:";AACA;EACE,iBAAiB;EACjB,oBAAoB;CACrB;AACD;IACI,UAAU;IACV,aAAa;IACb,kBAAkB;IAClB,gBAAgB;IAChB,mBAAmB;CACtB;AACD;MACM,mBAAmB;MACnB,YAAY;MACZ,OAAO;MACP,YAAY;MACZ,aAAa;MACb,kBAAkB;MAClB,mBAAmB;MACnB,eAAe;CACpB;AACD;EACE,UAAU;EACV,kBAAkB;EAClB,aAAa;EACb,kBAAkB;EAClB,mBAAmB;EACnB,QAAQ;EACR,OAAO;EACP,gBAAgB;EAChB,eAAe;CAChB;AACD;IACI,eAAe;CAClB;AACD;EACE,YAAY;EACZ,aAAa;EACb,mBAAmB;EACnB,SAAS;EACT,OAAO;EACP,2BAA2B;EAC3B,kBAAkB;CACnB;AACD;IACI,WAAW;CACd;AACD;IACI,iBAAiB;CACpB;AACD;MACM,gBAAgB;MAChB,YAAY;CACjB;AACD;IACI,YAAY;IACZ,aAAa;IACb,kBAAkB;IAClB,mBAAmB;IACnB,SAAS;IACT,OAAO;IACP,eAAe;CAClB;AACD;IACI,YAAY;IACZ,aAAa;IACb,mBAAmB;IACnB,WAAW;IACX,UAAU;CACb;AACD;MACM,oBAAoB;CACzB;AACD;MACM,oBAAoB;CACzB;AACD;MACM,gBAAgB;CACrB;AACD;MACM,oBAAoB;CACzB",file:"getTask.vue",sourcesContent:["\n.itemBox[data-v-284da2c7] {\n  overflow: hidden;\n  transition: all .3s;\n}\n.itemBox .itemTitle[data-v-284da2c7] {\n    margin: 0;\n    height: 40px;\n    line-height: 40px;\n    padding: 0 15px;\n    position: relative;\n}\n.itemBox .itemTitle i[data-v-284da2c7] {\n      position: absolute;\n      right: 15px;\n      top: 0;\n      width: 40px;\n      height: 40px;\n      line-height: 40px;\n      text-align: center;\n      color: #416eb6;\n}\n.checkBox[data-v-284da2c7] {\n  width: 8%;\n  text-align: right;\n  height: 64px;\n  line-height: 64px;\n  position: absolute;\n  left: 0;\n  top: 0;\n  font-size: 20px;\n  color: #9e9e9e;\n}\n.checkBox.active[data-v-284da2c7] {\n    color: #416eb6;\n}\n.itemInfo[data-v-284da2c7] {\n  width: 100%;\n  height: 64px;\n  position: absolute;\n  right: 0;\n  top: 0;\n  padding: 7px 15px 7px 55px;\n  line-height: 20px;\n}\n.itemInfo.hasCheck[data-v-284da2c7] {\n    width: 92%;\n}\n.itemInfo p[data-v-284da2c7] {\n    margin-bottom: 0;\n}\n.itemInfo p span[data-v-284da2c7] {\n      font-size: 12px;\n      color: #999;\n}\n.itemInfo span.right[data-v-284da2c7] {\n    width: 60px;\n    height: 65px;\n    line-height: 65px;\n    position: absolute;\n    right: 0;\n    top: 0;\n    color: #416eb6;\n}\n.itemInfo .status[data-v-284da2c7] {\n    width: 30px;\n    height: 22px;\n    position: absolute;\n    left: 15px;\n    top: 21px;\n}\n.itemInfo .status.active1[data-v-284da2c7] {\n      background: #33FF33;\n}\n.itemInfo .status.active2[data-v-284da2c7] {\n      background: #FF00FF;\n}\n.itemInfo .status.active3[data-v-284da2c7] {\n      background: red;\n}\n.itemInfo .status.active4[data-v-284da2c7] {\n      background: #666666;\n}\n"],sourceRoot:""}])},737:function(t,n,e){var a=e(598);"string"==typeof a&&(a=[[t.i,a,""]]),a.locals&&(t.exports=a.locals);e(226)("1682e9be",a,!0)},882:function(t,n){t.exports={render:function(){var t=this,n=t.$createElement,e=t._self._c||n;return e("div",{directives:[{name:"nav",rawName:"v-nav",value:{hideNavbar:!0},expression:"{hideNavbar:true}"}],staticClass:"mw-page page storage"},[e("div",{staticClass:"mw-page-header"},[e("i",{staticClass:"left icon ion-ios-arrow-back",on:{click:function(n){t.$router.go(-1)}}}),t._v(" "),e("span",[t._v("任务领取")]),t._v(" "),e("i",{staticClass:"right"},[t._v("扫描")])]),t._v(" "),e("div",{staticClass:"mw-page-content has-tab"},[e("div",{staticStyle:{height:"60px"}},[e("tabs",{attrs:{"tab-items":t.tabs,"tab-index":t.tabIndex,"on-tab-click":t.onTabClick}})],1),t._v(" "),e("div",{staticStyle:{flex:"1"}},[e("div",{directives:[{name:"show",rawName:"v-show",value:0===t.tabIndex,expression:"tabIndex===0"}],staticClass:"content"},[e("scroll",{staticClass:"page-content",staticStyle:{"-webkit-overflow-scrolling":"touch"}},t._l(t.pg_list,function(n,a){return e("div",{staticClass:"itemBox",style:{height:n.check?"40px":64*n.pop_list.length+40+"px"}},[e("p",{staticClass:"itemTitle",on:{click:function(e){t.check(n)}}},[t._v("生产小组："+t._s(n.pg_name)+" "),e("i",{class:n.check?"ion-arrow-down-b":"ion-arrow-up-b"})]),t._v(" "),t._l(n.pop_list,function(n,a){return e("item",{staticStyle:{height:"64px"},nativeOn:{click:function(e){t.check(n)}}},[e("div",{staticClass:"checkBox ",class:n.check?"active":""},[e("i",{staticClass:"ion-android-radio-button-off ",class:n.check?"ion-android-radio-button-on ":"ion-android-radio-button-off"})]),t._v(" "),e("div",{staticClass:"itemInfo hasCheck"},[e("div",[e("p",[t._v(t._s(n.mat_no))]),t._v(" "),e("p",{staticStyle:{height:"14px","line-height":"14px"}},[e("span",{staticStyle:{"margin-right":"5px",display:"inline-block","max-width":"70%"}},[t._v("物料名称:"+t._s(n.mat_name))])]),t._v(" "),e("p",[e("span",{staticStyle:{"margin-right":"5px",display:"inline-block","max-width":"70%"}},[t._v("工序:"+t._s(n.proc_name))])])]),t._v(" "),e("div",{staticClass:"status",class:"active"+n.pri_flag})])])})],2)}))],1),t._v(" "),e("div",{directives:[{name:"show",rawName:"v-show",value:1===t.tabIndex,expression:"tabIndex===1"}],staticClass:"content"},[e("scroll",{staticClass:"page-content",staticStyle:{"-webkit-overflow-scrolling":"touch"}},t._l(t.pop_list,function(n,a){return e("div",{staticClass:"itemBox",style:{height:n.check?"40px":64*n.pop_list.length+40+"px"}},[e("p",{staticClass:"itemTitle",on:{click:function(e){t.check(n)}}},[t._v("生产小组："+t._s(n.pg_name)+" "),e("i",{class:n.check?"ion-arrow-down-b":"ion-arrow-up-b"})]),t._v(" "),t._l(n.pop_list,function(n,a){return e("item",{staticStyle:{height:"64px"},nativeOn:{click:function(e){t.onItemClick(n)}}},[e("div",{staticClass:"itemInfo"},[e("div",[e("p",[t._v(t._s(n.mat_no))]),t._v(" "),e("p",{staticStyle:{height:"14px","line-height":"14px"}},[e("span",{staticStyle:{"margin-right":"5px",display:"inline-block","max-width":"70%"}},[t._v("物料名称:"+t._s(n.mat_name))])]),t._v(" "),e("p",[e("span",{staticStyle:{"margin-right":"5px",display:"inline-block","max-width":"70%"}},[t._v("工序:"+t._s(n.proc_name))])]),t._v(" "),n.allocated_list.length<1?e("span",{staticClass:"right"},[t._v("分配任务")]):t._e()]),t._v(" "),e("div",{staticClass:"status",class:"active"+n.pri_flag})])])})],2)}))],1)])]),t._v(" "),e("div",{directives:[{name:"show",rawName:"v-show",value:0===t.tabIndex,expression:"tabIndex===0"}],staticClass:"mw-page-footer"},[e("div",{staticClass:"btns"},[e("div",{staticClass:"btn",on:{click:t.incept}},[t._v("\n        领取\n      ")])])])])},staticRenderFns:[]}}});
//# sourceMappingURL=44.7ac65f488f42f880a954.js.map
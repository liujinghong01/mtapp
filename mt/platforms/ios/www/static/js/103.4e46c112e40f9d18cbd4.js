webpackJsonp([103],{317:function(t,n,i){i(796);var a=i(0)(i(516),i(944),"data-v-6ac150b2",null);t.exports=a.exports},397:function(t,n,i){"use strict";function a(t){return i.i(d.a)({url:"/api/pm/pop/incept_list",method:"post",data:t})}function o(t){return i.i(d.a)({url:"/api/pm/pop/incept",method:"post",data:t})}function e(t){return i.i(d.a)({url:"/api/pm/pop/confirm_list",method:"post",data:t})}function p(t){return i.i(d.a)({url:"/api/pm/pop/wait_proc_list",method:"post",data:t})}function s(t){return i.i(d.a)({url:"/api/pm/pg/detail",method:"post",data:t})}function c(t){return i.i(d.a)({url:"/api/pm/pop/allocate",method:"post",data:t})}function A(t){return i.i(d.a)({url:"/api/pm/pop/proc_info",method:"post",data:t})}function r(t){return i.i(d.a)({url:"/api/pm/pop/change_status",method:"post",data:t})}n.c=a,n.d=o,n.h=e,n.f=p,n.a=s,n.b=c,n.e=A,n.g=r;var d=i(10)},516:function(t,n,i){"use strict";Object.defineProperty(n,"__esModule",{value:!0});var a=i(5),o=(i(79),i(397)),e=i(20),p=i.n(e);i(1);n.default={name:"taskList",components:{SwipeItem:p.a},data:function(){return{dep_id:"",resource_id:"",pop_status:"",type:"",pop_list:[]}},computed:i.i(a.a)(["chosenCompany","userinfo"]),activated:function(){},mounted:function(){var t=this.$route.query;this.pg_id=t.pg_id,this.resource_id=t.resource_id,this.pop_status=t.pop_status,this.type=t.type,1*this.pop_status==0?this.waitProcList(this.pg_id,this.resource_id,this.type,this.pop_status):1*this.pop_status==1&&this.popProcInfo(this.pg_id,this.resource_id,this.type)},methods:{jobBooking:function(t){var n=this;2===t?$dialog.confirm({theme:"ios",title:"确定取消任务吗？",cancelText:"取消",okText:"确定"}).then(function(i){i&&n.popChangeStateus(t)}):3===t?$dialog.confirm({theme:"ios",title:"确定任务已 部分完成 吗？",cancelText:"取消",okText:"确定"}).then(function(i){i&&n.popChangeStateus(t)}):4===t&&$dialog.confirm({theme:"ios",title:"确定任务已全部完成吗？",cancelText:"取消",okText:"确定"}).then(function(i){i&&n.popChangeStateus(t)})},waitProcList:function(t,n,a,e){var p=this,s={query:{pg_id:t,resource_id:n,type:a,pop_status:e}};i.i(o.f)(s).then(function(t){t.pop_list.length>0&&(1*p.pop_status==0?p.pop_list=t.pop_list:t.pop_list.forEach(function(t,n){p.pop_list.push(t)}))}).catch(function(t){$toast.show(t.description)})},popProcInfo:function(t,n,a){var e=this,p={query:{pg_id:t,resource_id:n,type:a}};i.i(o.e)(p).then(function(t){e.waitProcList(e.pg_id,e.resource_id,e.type,e.pop_status),e.pop_list=t.pop_list}).catch(function(t){$toast.show(t.description)})},popChangeStateus:function(t){var n=this,a="",e=[];if(1*this.type==0&&(a=this.resource_id),1*t==1?this.pop_list.forEach(function(t,n){t.check&&e.push(t.pop_id)}):this.pop_list.forEach(function(t,n){1*t.pop_status!=3&&e.push(t.pop_id)}),e.length<1)return void $toast.show("请选择工序");var p={pop_info:{device_id:a,pg_id:this.pg_id,pop_ids:e,set_status:t}};i.i(o.g)(p).then(function(i){alert(t),1===t?(n.pop_status=1,n.popProcInfo(n.pg_id,n.resource_id,n.type)):(n.pop_status=0,n.popProcInfo(n.pg_id,n.resource_id,n.type))}).catch(function(t){$toast.show(t.description)})},itemClick:function(t){1*this.pop_status==0&&(void 0==t.check?this.$set(t,"check",!0):t.check=!t.check)}}}},657:function(t,n,i){n=t.exports=i(225)(),n.push([t.i,"\n.itemInfo[data-v-6ac150b2] {\n  width: 100%;\n  height: 42px;\n  position: absolute;\n  right: 0;\n  top: 0;\n  padding: 12px 15px 0px 55px;\n  line-height: 20px;\n}\n.itemInfo > div > p[data-v-6ac150b2] {\n    margin-bottom: 0;\n    height: 14px;\n    line-height: 14px;\n}\n.itemInfo span[data-v-6ac150b2] {\n    font-size: 12px;\n    color: #999;\n    height: 12px;\n    line-height: 12px;\n}\n.itemInfo .status[data-v-6ac150b2] {\n    width: 30px;\n    height: 24px;\n    position: absolute;\n    left: 15px;\n    top: 17px;\n    background: red;\n}\ndiv.right[data-v-6ac150b2] {\n  width: 42px;\n  height: 100%;\n  position: absolute;\n  right: 15px;\n  top: 0;\n}\ndiv.right span[data-v-6ac150b2] {\n    position: absolute;\n    left: 0;\n    top: 50%;\n    margin-top: -10px;\n}\ndiv.right span.active[data-v-6ac150b2] {\n      color: #416eb6;\n}\n.checkbox[data-v-6ac150b2] {\n  display: none;\n}\n.active[data-v-6ac150b2] {\n  background: #fff;\n}\n.active > .itemInfo[data-v-6ac150b2] {\n    padding: 12px 15px 0px 90px;\n}\n.active > .itemInfo .status[data-v-6ac150b2] {\n      left: 50px;\n}\n.active > p[data-v-6ac150b2] {\n    padding-left: 35px;\n}\n.active > .checkbox[data-v-6ac150b2] {\n    display: block;\n    width: 50px;\n    height: 100%;\n    position: absolute;\n    top: 0;\n    left: 0;\n}\n.active > .checkbox i[data-v-6ac150b2] {\n      position: absolute;\n      width: 14px;\n      height: 20px;\n      font-size: 18px;\n      left: 50%;\n      top: 50%;\n      margin-top: -10px;\n      margin-left: -5px;\n}\n.active > .checkbox i.active[data-v-6ac150b2] {\n        color: #416eb6;\n}\n","",{version:3,sources:["/Users/zxf/code/motooling/mt-app/webs-mtapp/mtApp/mt/www_dev/src/views/production/work/taskList.vue"],names:[],mappings:";AACA;EACE,YAAY;EACZ,aAAa;EACb,mBAAmB;EACnB,SAAS;EACT,OAAO;EACP,4BAA4B;EAC5B,kBAAkB;CACnB;AACD;IACI,iBAAiB;IACjB,aAAa;IACb,kBAAkB;CACrB;AACD;IACI,gBAAgB;IAChB,YAAY;IACZ,aAAa;IACb,kBAAkB;CACrB;AACD;IACI,YAAY;IACZ,aAAa;IACb,mBAAmB;IACnB,WAAW;IACX,UAAU;IACV,gBAAgB;CACnB;AACD;EACE,YAAY;EACZ,aAAa;EACb,mBAAmB;EACnB,YAAY;EACZ,OAAO;CACR;AACD;IACI,mBAAmB;IACnB,QAAQ;IACR,SAAS;IACT,kBAAkB;CACrB;AACD;MACM,eAAe;CACpB;AACD;EACE,cAAc;CACf;AACD;EACE,iBAAiB;CAClB;AACD;IACI,4BAA4B;CAC/B;AACD;MACM,WAAW;CAChB;AACD;IACI,mBAAmB;CACtB;AACD;IACI,eAAe;IACf,YAAY;IACZ,aAAa;IACb,mBAAmB;IACnB,OAAO;IACP,QAAQ;CACX;AACD;MACM,mBAAmB;MACnB,YAAY;MACZ,aAAa;MACb,gBAAgB;MAChB,UAAU;MACV,SAAS;MACT,kBAAkB;MAClB,kBAAkB;CACvB;AACD;QACQ,eAAe;CACtB",file:"taskList.vue",sourcesContent:["\n.itemInfo[data-v-6ac150b2] {\n  width: 100%;\n  height: 42px;\n  position: absolute;\n  right: 0;\n  top: 0;\n  padding: 12px 15px 0px 55px;\n  line-height: 20px;\n}\n.itemInfo > div > p[data-v-6ac150b2] {\n    margin-bottom: 0;\n    height: 14px;\n    line-height: 14px;\n}\n.itemInfo span[data-v-6ac150b2] {\n    font-size: 12px;\n    color: #999;\n    height: 12px;\n    line-height: 12px;\n}\n.itemInfo .status[data-v-6ac150b2] {\n    width: 30px;\n    height: 24px;\n    position: absolute;\n    left: 15px;\n    top: 17px;\n    background: red;\n}\ndiv.right[data-v-6ac150b2] {\n  width: 42px;\n  height: 100%;\n  position: absolute;\n  right: 15px;\n  top: 0;\n}\ndiv.right span[data-v-6ac150b2] {\n    position: absolute;\n    left: 0;\n    top: 50%;\n    margin-top: -10px;\n}\ndiv.right span.active[data-v-6ac150b2] {\n      color: #416eb6;\n}\n.checkbox[data-v-6ac150b2] {\n  display: none;\n}\n.active[data-v-6ac150b2] {\n  background: #fff;\n}\n.active > .itemInfo[data-v-6ac150b2] {\n    padding: 12px 15px 0px 90px;\n}\n.active > .itemInfo .status[data-v-6ac150b2] {\n      left: 50px;\n}\n.active > p[data-v-6ac150b2] {\n    padding-left: 35px;\n}\n.active > .checkbox[data-v-6ac150b2] {\n    display: block;\n    width: 50px;\n    height: 100%;\n    position: absolute;\n    top: 0;\n    left: 0;\n}\n.active > .checkbox i[data-v-6ac150b2] {\n      position: absolute;\n      width: 14px;\n      height: 20px;\n      font-size: 18px;\n      left: 50%;\n      top: 50%;\n      margin-top: -10px;\n      margin-left: -5px;\n}\n.active > .checkbox i.active[data-v-6ac150b2] {\n        color: #416eb6;\n}\n"],sourceRoot:""}])},796:function(t,n,i){var a=i(657);"string"==typeof a&&(a=[[t.i,a,""]]),a.locals&&(t.exports=a.locals);i(226)("76dbf228",a,!0)},944:function(t,n){t.exports={render:function(){var t=this,n=t.$createElement,i=t._self._c||n;return i("div",{directives:[{name:"nav",rawName:"v-nav",value:{hideNavbar:!0},expression:"{hideNavbar:true}"}],staticClass:"mw-page page storage"},[i("div",{staticClass:"mw-page-header"},[i("i",{staticClass:"left icon ion-ios-arrow-back",on:{click:function(n){t.$router.go(-1)}}}),t._v(" "),i("span",[t._v("任务列表")]),t._v(" "),i("i",{staticClass:"right"})]),t._v(" "),i("div",{staticClass:"mw-page-content "},[i("div",{staticClass:"content"},[i("scroll",{staticClass:"page-content",staticStyle:{"-webkit-overflow-scrolling":"touch"}},[t._l(t.pop_list,function(n,a){return i("item",{class:1*t.pop_status==0?"active":"",staticStyle:{"padding-top":"46px","padding-bottom":"12px"},nativeOn:{click:function(i){t.itemClick(n)}}},[i("div",{staticClass:"itemInfo"},[i("div",{staticClass:"status"}),t._v(" "),i("div",[i("p",{staticStyle:{height:"18px"}},[t._v(t._s(n.mat_no))]),t._v(" "),i("p",[i("span",{staticStyle:{"margin-right":"5px",display:"inline-block","max-width":"70%"}},[t._v("物料名称 : "+t._s(n.mat_name))])])])]),t._v(" "),i("p",[t._v("计划开始时间 : "+t._s(n.plan_start_time))]),t._v(" "),i("p",[t._v("计划结束时间 : "+t._s(n.plan_end_time))]),t._v(" "),i("p",[t._v("任务工时 : "+t._s(n.plan_work_time)+"    待加工数量:"+t._s(n.proc_qty))]),t._v(" "),i("p",[t._v("生产订单号 : "+t._s(n.po_no))]),t._v(" "),i("div",{staticClass:"checkbox"},[i("i",{staticClass:"icon ",class:n.check?"ion-android-checkbox active":"ion-android-checkbox-outline-blank"})]),t._v(" "),i("div",{directives:[{name:"show",rawName:"v-show",value:1*t.pop_status==1,expression:"pop_status*1===1"}],staticClass:"right"},[i("span",{class:"4"===n.pop_status?"active":""},[t._v(t._s("4"===n.pop_status?"加工中":"待加工"))])])])}),t._v(" "),i("div",{staticClass:"text-center",attrs:{slot:"infinite"},slot:"infinite"})],2)],1)]),t._v(" "),i("div",{staticClass:"mw-page-footer"},[1*t.pop_status==0?i("div",{staticClass:"btns"},[i("div",{staticClass:"btn",on:{click:function(n){t.popChangeStateus(1)}}},[t._v("\n        开始加工\n      ")])]):i("div",{staticClass:"btns"},[i("div",{staticClass:"btn",on:{click:function(n){t.jobBooking(2)}}},[t._v("\n        取消任务\n      ")]),t._v("|\n      "),i("div",{staticClass:"btn",on:{click:function(n){t.jobBooking(3)}}},[t._v("\n        部分完成\n      ")]),t._v("|\n      "),i("div",{staticClass:"btn",on:{click:function(n){t.jobBooking(4)}}},[t._v("\n        全部完成\n      ")])])])])},staticRenderFns:[]}}});
//# sourceMappingURL=103.4e46c112e40f9d18cbd4.js.map
webpackJsonp([76],{276:function(e,t,i){i(684);var n=i(0)(i(433),i(794),"data-v-de450a26",null);e.exports=n.exports},345:function(e,t,i){"use strict";function n(e){return i.i(p.a)({url:"/api/pm/shift/list",method:"post",data:e})}function a(e){return i.i(p.a)({url:"/api/pm/calendar/list",method:"post",data:e})}function o(e){return i.i(p.a)({url:"/api/pm/calendar/delete",method:"post",data:e})}function d(e){return i.i(p.a)({url:"/api/pm/calendar/modify",method:"post",data:e})}function c(e){return i.i(p.a)({url:"/api/pm/shift/delete",method:"post",data:e})}function s(e){return i.i(p.a)({url:"/api/pm/shift/modify",method:"post",data:e})}function r(e){return i.i(p.a)({url:"/api/pm/shift/detail",method:"post",data:e})}function v(e){return i.i(p.a)({url:"/api/pm/calendar/detail",method:"post",data:e})}t.a=n,t.f=a,t.h=o,t.c=d,t.g=c,t.e=s,t.d=r,t.b=v;var p=i(7)},433:function(e,t,i){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n=(i(345),i(20)),a=i.n(n);t.default={name:"equipment",components:{SwipeItem:a.a},data:function(){return{list:[{check:"",device_addr:"设备所在地址",device_brand:"设备品牌",device_id:"设备Id",device_model:"设备型号",device_name:"设备名称(正常)",device_no:"设备编号",device_pic:"设备图标",device_status:1,is_checked:"是否点检",pg_id:"生产小组Id",prod_date:"生产日期",work_life:"使用年限"},{check:!0,device_addr:"设备所在地址",device_brand:"设备品牌",device_id:"设备Id",device_model:"设备型号",device_name:"设备名称(维修中)",device_no:"设备编号",device_pic:"设备图标",device_status:2,is_checked:"是否点检",pg_id:"生产小组Id",prod_date:"生产日期",work_life:"使用年限"}]}},computed:{},activated:function(){},mounted:function(){},methods:{onInfinite:function(){},onRefresh:function(){},onItemClick:function(){}}}},583:function(e,t,i){t=e.exports=i(220)(),t.push([e.i,"\n.item[data-v-de450a26] {\n  padding-left: 40px;\n}\n.item > div[data-v-de450a26] {\n    line-height: 20px;\n}\n.item.active[data-v-de450a26] {\n    background: #fff;\n}\n.item.active > div.checkbox > i[data-v-de450a26] {\n      color: #416eb6;\n}\n.item.active > div.checkbox > i.ion-android-checkbox[data-v-de450a26] {\n        display: block;\n}\n.item .checkbox[data-v-de450a26] {\n    width: 40px;\n    height: 100%;\n    position: absolute;\n    top: 0;\n    left: 0;\n}\n.item .checkbox i[data-v-de450a26] {\n      position: absolute;\n      width: 14px;\n      height: 20px;\n      font-size: 18px;\n      left: 50%;\n      top: 50%;\n      margin-top: -10px;\n      margin-left: -5px;\n}\n.item .checkbox i.ion-android-checkbox[data-v-de450a26] {\n        display: none;\n}\n","",{version:3,sources:["E:/weibosi/webs-mt/mt/www_dev/src/views/production/equipment/distributionList.vue"],names:[],mappings:";AACA;EACE,mBAAmB;CACpB;AACD;IACI,kBAAkB;CACrB;AACD;IACI,iBAAiB;CACpB;AACD;MACM,eAAe;CACpB;AACD;QACQ,eAAe;CACtB;AACD;IACI,YAAY;IACZ,aAAa;IACb,mBAAmB;IACnB,OAAO;IACP,QAAQ;CACX;AACD;MACM,mBAAmB;MACnB,YAAY;MACZ,aAAa;MACb,gBAAgB;MAChB,UAAU;MACV,SAAS;MACT,kBAAkB;MAClB,kBAAkB;CACvB;AACD;QACQ,cAAc;CACrB",file:"distributionList.vue",sourcesContent:["\n.item[data-v-de450a26] {\n  padding-left: 40px;\n}\n.item > div[data-v-de450a26] {\n    line-height: 20px;\n}\n.item.active[data-v-de450a26] {\n    background: #fff;\n}\n.item.active > div.checkbox > i[data-v-de450a26] {\n      color: #416eb6;\n}\n.item.active > div.checkbox > i.ion-android-checkbox[data-v-de450a26] {\n        display: block;\n}\n.item .checkbox[data-v-de450a26] {\n    width: 40px;\n    height: 100%;\n    position: absolute;\n    top: 0;\n    left: 0;\n}\n.item .checkbox i[data-v-de450a26] {\n      position: absolute;\n      width: 14px;\n      height: 20px;\n      font-size: 18px;\n      left: 50%;\n      top: 50%;\n      margin-top: -10px;\n      margin-left: -5px;\n}\n.item .checkbox i.ion-android-checkbox[data-v-de450a26] {\n        display: none;\n}\n"],sourceRoot:""}])},684:function(e,t,i){var n=i(583);"string"==typeof n&&(n=[[e.i,n,""]]),n.locals&&(e.exports=n.locals);i(221)("237aea0c",n,!0)},794:function(e,t){e.exports={render:function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("div",{directives:[{name:"nav",rawName:"v-nav",value:{hideNavbar:!0},expression:"{hideNavbar:true}"}],staticClass:"mw-page page storage"},[i("div",{staticClass:"mw-page-header"},[i("i",{staticClass:"left icon ion-ios-arrow-back",on:{click:function(t){e.$router.go(-1)}}}),e._v(" "),i("span",[e._v("设备点检分配")]),e._v(" "),i("i",{staticClass:"right"})]),e._v(" "),i("div",{staticClass:"mw-page-content "},[i("div",{staticClass:"content"},[i("scroll",{staticClass:"page-content",staticStyle:{"-webkit-overflow-scrolling":"touch"},attrs:{"on-infinite":e.onInfinite,"on-refresh":e.onRefresh}},[e._l(e.list,function(t,n){return i("item",{class:!0===t.check?"active":""},[i("h3",[i("span",{staticClass:"vi-color"},[e._v(e._s(t.device_name))])]),e._v(" "),i("div",[e._v("编号 ："),i("span",[e._v(e._s(t.device_no))])]),e._v(" "),i("div",[e._v("使用年限 ："),i("span",[e._v(e._s(t.work_life))])]),e._v(" "),i("div",{staticClass:"req-item-content"},[i("span",[e._v("状态 : "+e._s(t.device_status))]),e._v(" "),i("span",[e._v("生产日期 : "+e._s(t.prod_date))])]),e._v(" "),i("div",{staticClass:"checkbox"},[i("i",{staticClass:"icon ion-android-checkbox-outline-blank"}),e._v(" "),i("i",{staticClass:"icon ion-android-checkbox "})])])}),e._v(" "),i("div",{staticClass:"text-center",attrs:{slot:"infinite"},slot:"infinite"},[e._v("没有更多数据")])],2)],1)]),e._v(" "),i("div",{staticClass:"mw-page-footer"},[i("div",{staticClass:"btns"},[i("div",{staticClass:"btn",on:{click:function(e){}}},[e._v("\n        指派人员\n      ")])])])])},staticRenderFns:[]}}});
//# sourceMappingURL=76.4bf89d14a51c10cd6cd7.js.map
webpackJsonp([82],{274:function(n,t,a){a(811);var i=a(0)(a(472),a(959),"data-v-7f94c285",null);n.exports=i.exports},411:function(n,t,a){"use strict";var i=a(6),o=a.n(i),e={};console.log(window.cordova);var s={};try{s=window.cordova?window.cordova.plugins.barcodeScanner:""}catch(n){console.log(window.cordova.plugins)}e.scanbar=function(n,t){s.scan(function(a){if(a.cancelled)return void t({text:"取消操作"});n(a)},function(n){console.log(o()(n)),t(n)})},t.a=e},472:function(n,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=a(411),o=a(5);t.default={name:"my",computed:a.i(o.a)(["userinfo","hideNavbar","chosenCompany"]),data:function(){return{}},methods:{scan:function(){i.a.scanbar(function(){},function(){})}},mounted:function(){console.log(this.$router)}}},672:function(n,t,a){t=n.exports=a(225)(),t.push([n.i,"\n.user-info[data-v-7f94c285] {\n  width: 100%;\n  height: 160px;\n  position: relative;\n  background: url("+a(848)+");\n  background-size: cover;\n}\n.user-info .avatar[data-v-7f94c285] {\n    width: 70px;\n    height: 70px;\n    /*margin: 20px auto 10px auto;*/\n    padding-top: 10px;\n    margin: 0 auto;\n}\n.user-info .avatar img[data-v-7f94c285] {\n      border-radius: 40px;\n}\n.user-info .content[data-v-7f94c285] {\n    position: relative;\n    width: 80%;\n    margin: 20px auto 0 auto;\n    color: #ffffff;\n    height: 60px;\n}\n.user-info .content p[data-v-7f94c285] {\n      /*margin-top: 3px;*/\n      text-align: center;\n}\n.user-info .content .company[data-v-7f94c285] {\n      margin-top: -10px;\n      opacity: .6;\n}\n.user-info .content .name[data-v-7f94c285] {\n      font-size: 16px;\n}\n.user-info .qrcode[data-v-7f94c285] {\n    position: relative;\n    float: right;\n    width: 20px;\n    height: 20px;\n    background-color: blue;\n    margin: 30px 15px;\n}\n.user-info .pl-login[data-v-7f94c285] {\n    position: relative;\n    float: right;\n    width: 60px;\n    height: 20px;\n    margin: 30px 0;\n}\n","",{version:3,sources:["/Users/zxf/code/motooling/mt-app/webs-mtapp/mtApp/mt/www_dev/src/views/index/my.vue"],names:[],mappings:";AACA;EACE,YAAY;EACZ,cAAc;EACd,mBAAmB;EACnB,0CAA0D;EAC1D,uBAAuB;CACxB;AACD;IACI,YAAY;IACZ,aAAa;IACb,gCAAgC;IAChC,kBAAkB;IAClB,eAAe;CAClB;AACD;MACM,oBAAoB;CACzB;AACD;IACI,mBAAmB;IACnB,WAAW;IACX,yBAAyB;IACzB,eAAe;IACf,aAAa;CAChB;AACD;MACM,oBAAoB;MACpB,mBAAmB;CACxB;AACD;MACM,kBAAkB;MAClB,YAAY;CACjB;AACD;MACM,gBAAgB;CACrB;AACD;IACI,mBAAmB;IACnB,aAAa;IACb,YAAY;IACZ,aAAa;IACb,uBAAuB;IACvB,kBAAkB;CACrB;AACD;IACI,mBAAmB;IACnB,aAAa;IACb,YAAY;IACZ,aAAa;IACb,eAAe;CAClB",file:"my.vue",sourcesContent:['\n.user-info[data-v-7f94c285] {\n  width: 100%;\n  height: 160px;\n  position: relative;\n  background: url("../../assets/img/userinfo/userinfo.png");\n  background-size: cover;\n}\n.user-info .avatar[data-v-7f94c285] {\n    width: 70px;\n    height: 70px;\n    /*margin: 20px auto 10px auto;*/\n    padding-top: 10px;\n    margin: 0 auto;\n}\n.user-info .avatar img[data-v-7f94c285] {\n      border-radius: 40px;\n}\n.user-info .content[data-v-7f94c285] {\n    position: relative;\n    width: 80%;\n    margin: 20px auto 0 auto;\n    color: #ffffff;\n    height: 60px;\n}\n.user-info .content p[data-v-7f94c285] {\n      /*margin-top: 3px;*/\n      text-align: center;\n}\n.user-info .content .company[data-v-7f94c285] {\n      margin-top: -10px;\n      opacity: .6;\n}\n.user-info .content .name[data-v-7f94c285] {\n      font-size: 16px;\n}\n.user-info .qrcode[data-v-7f94c285] {\n    position: relative;\n    float: right;\n    width: 20px;\n    height: 20px;\n    background-color: blue;\n    margin: 30px 15px;\n}\n.user-info .pl-login[data-v-7f94c285] {\n    position: relative;\n    float: right;\n    width: 60px;\n    height: 20px;\n    margin: 30px 0;\n}\n'],sourceRoot:""}])},811:function(n,t,a){var i=a(672);"string"==typeof i&&(i=[[n.i,i,""]]),i.locals&&(n.exports=i.locals);a(226)("422dcb86",i,!0)},848:function(n,t,a){n.exports=a.p+"static/img/userinfo.05bbc56.png"},959:function(n,t){n.exports={render:function(){var n=this,t=n.$createElement,a=n._self._c||t;return a("div",{directives:[{name:"nav",rawName:"v-nav",value:{hideNavbar:!0},expression:"{hideNavbar:true}"}],staticClass:"mw-page page approve"},[n._m(0,!1,!1),n._v(" "),a("div",{staticClass:"mw-page-content"},[a("div",[a("div",{staticClass:"user-info",on:{click:function(t){n.$router.forward("/userinfo")}}},[a("div",{staticClass:"avatar"},[a("img",{attrs:{src:n.userinfo.avatar,width:"100%"}})]),n._v(" "),a("div",{staticClass:"content"},[a("p",{staticClass:"name"},[n._v(n._s(n.userinfo.username))]),n._v(" "),a("p",{staticClass:"company"},[n._v(n._s(n.chosenCompany?n.chosenCompany.company_name:"未关联公司"))])])]),n._v(" "),a("div",{staticClass:"item-divider-ios"}),n._v(" "),a("item",{staticClass:"item-icon-left",nativeOn:{click:function(t){n.scan(t)}}},[n._v("\n            "+n._s(n.$t("scan"))+"\n            "),a("i",{staticClass:"icon ion-qr-scanner"})]),n._v(" "),a("item",{staticClass:"item-icon-left item-icon-right"},[n._v("\n            "+n._s(n.$t("card"))+"\n            "),a("i",{staticClass:"icon ion-card"}),n._v(" "),a("i",{staticClass:"icon ion-ios-arrow-right",staticStyle:{color:"#DDD"}})]),n._v(" "),a("div",{staticClass:"item-divider-ios"}),n._v(" "),a("item",{staticClass:"item-icon-left item-icon-right",nativeOn:{click:function(t){n.$router.forward("/settings")}}},[n._v("\n            "+n._s(n.$t("settings"))+"\n            "),a("i",{staticClass:"icon ion-gear-a"}),n._v(" "),a("i",{staticClass:"icon ion-ios-arrow-right",staticStyle:{color:"#DDD"}})])],1)])])},staticRenderFns:[function(){var n=this,t=n.$createElement,a=n._self._c||t;return a("div",{staticClass:"mw-page-header"},[a("i",{staticClass:"left"}),n._v(" "),a("span",[n._v("我的")]),n._v(" "),a("i",{staticClass:"right"})])}]}}});
//# sourceMappingURL=82.34f521be32e8a9adf28c.js.map
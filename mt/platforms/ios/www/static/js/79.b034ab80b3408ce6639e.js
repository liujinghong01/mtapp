webpackJsonp([79],{366:function(n,t,i){i(728);var o=i(0)(i(565),i(873),"data-v-18daa15e",null);n.exports=o.exports},411:function(n,t,i){"use strict";var o=i(6),e=i.n(o),s={};console.log(window.cordova);var c={};try{c=window.cordova?window.cordova.plugins.barcodeScanner:""}catch(n){console.log(window.cordova.plugins)}s.scanbar=function(n,t){c.scan(function(i){if(i.cancelled)return void t({text:"取消操作"});n(i)},function(n){console.log(e()(n)),t(n)})},t.a=s},420:function(n,t,i){"use strict";var o={};o.getPicture=function(n,t,i){navigator.camera.getPicture(function(n){console.log("success"+n),t(n)},function(n){console.log("fail "+n),i("error "+n)},{allowEdit:!1,encodingType:0,targetWidth:100,targetHeight:100,saveToPhotoAlbum:!0,correctOrientation:!0,quality:50,sourceType:"take"===n?1:2,destinationType:Camera.DestinationType.FILE_URI})},t.a=o},565:function(n,t,i){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var o=i(1),e=i(411),s=i(420);t.default={name:"settings",data:function(){return{}},methods:{logout:function(){var n=this;$dialog.confirm({theme:"ios",title:"确定要退出吗？",cancelText:"取消",okText:"退出"}).then(function(t){t&&n.$store.dispatch("FedLogOut").then(function(){o.a.clear(),n.$store.dispatch("changIndex",0),n.$store.dispatch("setCompany",""),n.$router.forward("/")})})},scan:function(){e.a.scanbar(function(n){console.log("success"+n.text)},function(n){console.log("error"+n.text)})},showAction:function(){var n=this,t=new Object;t["拍摄照片"]=function(){n.capture("take")},t["从相册选取"]=function(){n.capture("pic")},$actionSheet.show({buttons:t,title:"",cancelText:"取消"})},capture:function(n){s.a.getPicture(n,function(){},function(){})}}}},589:function(n,t,i){t=n.exports=i(225)(),t.push([n.i,"\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n","",{version:3,sources:[],names:[],mappings:"",file:"index.vue",sourceRoot:""}])},728:function(n,t,i){var o=i(589);"string"==typeof o&&(o=[[n.i,o,""]]),o.locals&&(n.exports=o.locals);i(226)("3af1bca2",o,!0)},873:function(n,t){n.exports={render:function(){var n=this,t=n.$createElement,i=n._self._c||t;return i("div",{directives:[{name:"nav",rawName:"v-nav",value:{title:n.$t("settings"),showBackButton:!0},expression:"{title: $t('settings'), showBackButton: true}"}],staticClass:"page has-navbar"},[i("div",{staticClass:"page-content"},[i("div",{staticClass:"item-divider-ios"}),n._v(" "),i("item",{staticClass:"item-icon-right"},[n._v("\n        "+n._s(n.$t("notify-setting"))+"\n        "),i("i",{staticClass:"icon ion-ios-arrow-right",staticStyle:{color:"#DDD"}})]),n._v(" "),i("item",{staticClass:"item-icon-right"},[n._v("\n        "+n._s(n.$t("changelog"))+"\n        "),i("i",{staticClass:"icon ion-ios-arrow-right",staticStyle:{color:"#DDD"}})]),n._v(" "),i("item",{staticClass:"item-icon-right"},[n._v("\n        "+n._s(n.$t("share"))+"\n        "),i("i",{staticClass:"icon ion-ios-arrow-right",staticStyle:{color:"#DDD"}})]),n._v(" "),i("div",{staticClass:"item-divider-ios"}),n._v(" "),i("item",{staticClass:"item-icon-right"},[n._v("\n        "+n._s(n.$t("proxy"))+"\n        "),i("i",{staticClass:"icon ion-ios-arrow-right",staticStyle:{color:"#DDD"}})]),n._v(" "),i("item",{staticClass:"item-icon-right"},[n._v("\n        "+n._s(n.$t("feedback"))+"\n        "),i("i",{staticClass:"icon ion-ios-arrow-right",staticStyle:{color:"#DDD"}})]),n._v(" "),i("item",{staticClass:"item-icon-right",nativeOn:{click:function(t){n.scan(t)}}},[n._v("\n        "+n._s(n.$t("about-us"))+"\n        "),i("i",{staticClass:"icon ion-ios-arrow-right",staticStyle:{color:"#DDD"}})]),n._v(" "),i("item",{staticClass:"item-icon-right",nativeOn:{click:function(t){n.showAction(t)}}},[n._v("\n        读取名片\n        "),i("i",{staticClass:"icon ion-ios-arrow-right",staticStyle:{color:"#DDD"}})]),n._v(" "),i("div",{staticClass:"item-divider-ios"}),n._v(" "),i("item",{staticStyle:{"text-align":"center"},nativeOn:{click:function(t){n.logout(t)}}},[n._v("\n        "+n._s(n.$t("exit"))+"\n      ")])],1)])},staticRenderFns:[]}}});
//# sourceMappingURL=79.b034ab80b3408ce6639e.js.map
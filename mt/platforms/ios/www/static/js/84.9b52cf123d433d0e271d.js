webpackJsonp([84],{327:function(e,t,o){o(595);var n=o(0)(o(484),o(700),"data-v-068fff47",null);e.exports=n.exports},484:function(e,t,o){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n=o(8),a=o.n(n),s=o(32),c=o.n(s),i=o(1);t.default={name:"zoneCode",data:function(){return{zoneCode:[],local:"zh"}},created:function(){var e=this;if(navigator)switch(navigator.language){case"zh-CN":e.local="zh",c.a.get("static/zone_code-zh.json").then(function(t){e.zoneCode=t.data}).catch(function(e){console.log(e)});break;case"en-US":e.local="en",c.a.get("static/zone_code.json").then(function(t){e.zoneCode=t.data}).catch(function(e){console.log(e)});break;default:c.a.get("static/zone_code-zh.json").then(function(t){e.zoneCode=t.data}).catch(function(e){console.log(e)})}},methods:{chooseCode:function(e){var t=document.querySelector("[von-app]");t&&t.setAttribute("transition-direction","back"),i.a.set("zoneCode",a()(e)),history.go(-1)}},destroyed:function(){this.zoneCode.length=0,console.log("destroyed")}}},494:function(e,t,o){t=e.exports=o(220)(),t.push([e.i,"","",{version:3,sources:[],names:[],mappings:"",file:"zoneCode.vue",sourceRoot:""}])},595:function(e,t,o){var n=o(494);"string"==typeof n&&(n=[[e.i,n,""]]),n.locals&&(e.exports=n.locals);o(221)("d003243a",n,!0)},700:function(e,t){e.exports={render:function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("div",{directives:[{name:"nav",rawName:"v-nav",value:{title:"选择国家或者地区",showBackButton:!0},expression:"{title: '选择国家或者地区', showBackButton: true}"}],staticClass:"page has-navbar"},[o("div",{staticClass:"page-content"},[o("list",{staticClass:"list-ios"},e._l(e.zoneCode,function(t){return o("item",{key:"item.code",nativeOn:{click:function(o){e.chooseCode(t)}}},[e._v("\n        "+e._s("zh"===e.local?t.zh:t.en)+"\n        "),o("span",{staticClass:"item-note"},[e._v("\n          "+e._s(t.code)+"\n        ")])])}))],1)])},staticRenderFns:[]}}});
//# sourceMappingURL=84.9b52cf123d433d0e271d.js.map
webpackJsonp([120],{229:function(t,e,i){i(833);var s=i(0)(i(427),i(981),"data-v-b6ba3c64",null);t.exports=s.exports},396:function(t,e,i){"use strict";function s(t){return i.i(n.a)({url:"/api/workflow/process",method:"post",data:t})}function a(t){return i.i(n.a)({url:"/api/workflow/show_opinion",method:"post",data:t})}e.a=s,e.b=a;var n=i(10)},427:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var s=i(396);e.default={name:"approveSuggests",data:function(){return{bill_id:"",bill_type:"",suggests:[]}},mounted:function(){var t=this;this.bill_id=this.$route.params.bill_id,this.bill_type=this.$route.params.bill_type,i.i(s.b)({bill_id:this.bill_id,bill_type:this.bill_type}).then(function(e){console.log(e),t.suggests=e.process_list}).catch(function(t){$toast.show(t.description)})}}},694:function(t,e,i){e=t.exports=i(225)(),e.push([t.i,"\ntextarea[data-v-b6ba3c64] {\n  width: 100%;\n  height: 80%;\n  resize: none;\n  padding: 15px;\n}\n","",{version:3,sources:["/Users/zxf/code/motooling/mt-app/webs-mtapp/mtApp/mt/www_dev/src/views/approve/approveSuggests.vue"],names:[],mappings:";AACA;EACE,YAAY;EACZ,YAAY;EACZ,aAAa;EACb,cAAc;CACf",file:"approveSuggests.vue",sourcesContent:["\ntextarea[data-v-b6ba3c64] {\n  width: 100%;\n  height: 80%;\n  resize: none;\n  padding: 15px;\n}\n"],sourceRoot:""}])},833:function(t,e,i){var s=i(694);"string"==typeof s&&(s=[[t.i,s,""]]),s.locals&&(t.exports=s.locals);i(226)("b7f1af8c",s,!0)},981:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{directives:[{name:"nav",rawName:"v-nav",value:{hideNavbar:!0},expression:"{hideNavbar:true}"}],staticClass:"mw-page page approve"},[i("div",{staticClass:"mw-page-header"},[i("i",{staticClass:"left icon ion-ios-arrow-back",on:{click:function(e){t.$router.go(-1)}}}),t._v(" "),i("span",[t._v("审批意见")]),t._v(" "),i("i",{staticClass:"right"})]),t._v(" "),i("div",{staticClass:"mw-page-content"},[i("item",[i("span",[t._v("状态 ： {{}}")])])],1)])},staticRenderFns:[]}}});
//# sourceMappingURL=120.b2bd01d6506909e0550c.js.map
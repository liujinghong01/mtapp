webpackJsonp([138],{228:function(e,t,a){a(792);var i=a(0)(a(426),a(940),"data-v-643872bf",null);e.exports=i.exports},426:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=a(14),s=a.n(i),n=a(80),o=a(5);t.default={name:"approveSuggestion",data:function(){return{approve_sugg:"",bill_id:"",bill_type:"",approve_step:"",approve_man_id:"",approve_man_name:"",approve_flag:""}},mounted:function(){this.bill_id=this.$route.params.bill_id,this.bill_type=this.$route.params.bill_type,this.approve_step=this.$route.params.approve_step},computed:s()({},a.i(o.a)(["userinfo"])),methods:{submit:function(e){var t={approve_sugg:this.approve_sugg,bill_id:this.bill_id,bill_type:this.bill_type,approve_step:this.approve_step,approve_man_id:this.userinfo.uid,approve_man_name:this.userinfo.username,approve_flag:e};a.i(n.a)(t).then(function(e){$toast.show("提交成功"),history.go(-1)}).catch(function(e){$toast.show(e.description)})}}}},653:function(e,t,a){t=e.exports=a(225)(),t.push([e.i,"\ntextarea[data-v-643872bf] {\n  width: 100%;\n  height: 80%;\n  resize: none;\n  padding: 15px;\n}\n","",{version:3,sources:["/Users/zxf/code/motooling/mt-app/webs-mtapp/mtApp/mt/www_dev/src/views/approve/approve.vue"],names:[],mappings:";AACA;EACE,YAAY;EACZ,YAAY;EACZ,aAAa;EACb,cAAc;CACf",file:"approve.vue",sourcesContent:["\ntextarea[data-v-643872bf] {\n  width: 100%;\n  height: 80%;\n  resize: none;\n  padding: 15px;\n}\n"],sourceRoot:""}])},792:function(e,t,a){var i=a(653);"string"==typeof i&&(i=[[e.i,i,""]]),i.locals&&(e.exports=i.locals);a(226)("05e0a4ec",i,!0)},940:function(e,t){e.exports={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{directives:[{name:"nav",rawName:"v-nav",value:{hideNavbar:!0},expression:"{hideNavbar:true}"}],staticClass:"mw-page page approve"},[a("div",{staticClass:"mw-page-header"},[a("i",{staticClass:"left icon ion-ios-arrow-back",on:{click:function(t){e.$router.go(-1)}}}),e._v(" "),a("span",[e._v("审批")]),e._v(" "),a("i",{staticClass:"right"})]),e._v(" "),a("div",{staticClass:"mw-page-content"},[a("textarea",{directives:[{name:"model",rawName:"v-model",value:e.approve_sugg,expression:"approve_sugg"}],attrs:{placeholder:"输入审批意见"},domProps:{value:e.approve_sugg},on:{input:function(t){t.target.composing||(e.approve_sugg=t.target.value)}}})]),e._v(" "),a("div",{staticClass:"mw-page-footer"},[a("div",{staticClass:"btns"},[a("div",{staticClass:"btn",on:{click:function(t){e.submit(2)}}},[e._v("\n            不同意\n        ")]),e._v(" "),a("div",{staticClass:"divider"}),e._v(" "),a("div",{staticClass:"btn",on:{click:function(t){e.submit(1)}}},[e._v("\n           同意\n        ")])])])])},staticRenderFns:[]}}});
//# sourceMappingURL=138.21af17dd65573964491c.js.map
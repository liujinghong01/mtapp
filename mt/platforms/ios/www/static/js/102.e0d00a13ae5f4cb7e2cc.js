webpackJsonp([102],{326:function(e,a,t){var i=t(0)(t(483),t(701),null,null);e.exports=i.exports},483:function(e,a,t){"use strict";Object.defineProperty(a,"__esModule",{value:!0});var i=t(8),r=t.n(i),s=(t(4),t(50)),n=t(85),o=(t(34),t(21)),c=t.n(o),l=t(49),p=t.n(l);a.default={components:{MwInput:p.a},name:"modifyUserinfo",data:function(){return{param:{},param_edit:{},tempUserinfo:{},tempUserinfo_edit:{},genderOptions:["未知","男","女"],title:""}},created:function(){this.param=this.$route.query,this.param_edit=JSON.parse(r()(this.param))},computed:{setTitle:function(){var e="";switch(this.param.type){case"gender":e="性别";break;case"username":e="昵称";break;case"mobile":e="手机号";break;case"email":e="邮箱";break;case"zone":e="选择地区";break;case"wechat":e="微信";break;default:e="昵称"}return this.title=e,e}},methods:{menuClick:function(){var e=this;if(console.log("menuClick"),"gender"!==this.param_edit.type){if(!this.param_edit.val)return void $toast.show(this.title+"不能为空");if(!t.i(n.a)(parseInt(this.param_edit.val)))return void $toast.show(this.title+"不正确")}if(c.a.isEqual(this.param,this.param_edit))return void history.go(-1);var a={};switch(this.param.type){case"gender":a.gender=this.param_edit.val;break;case"username":a.username=this.param_edit.val;break;case"mobile":a.mobile=this.param_edit.val;break;case"email":a.email=this.param_edit.val}t.i(s.a)(a).then(function(a){e.$store.dispatch("GetInfo"),history.back()}).catch(function(e){$toast.show(e.description)})}}}},701:function(e,a){e.exports={render:function(){var e=this,a=e.$createElement,t=e._self._c||a;return t("div",{directives:[{name:"nav",rawName:"v-nav",value:{hideNavbar:!0},expression:"{hideNavbar:true}"}],staticClass:"mw-page page crm"},[t("div",{staticClass:"mw-page-header"},[t("i",{staticClass:"left icon ion-ios-arrow-back",on:{click:function(a){e.$router.go(-1)}}}),e._v(" "),t("span",[e._v("修改"+e._s(e.setTitle))]),e._v(" "),t("i",{staticClass:"right",on:{click:e.menuClick}},[e._v("保存")])]),e._v(" "),t("div",{staticClass:"mw-page-content"},["gender"===e.param_edit.type?t("div",[t("von-radio",{attrs:{options:e.genderOptions},model:{value:e.param_edit.val,callback:function(a){e.$set(e.param_edit,"val",a)},expression:"param_edit.val"}})],1):t("div",[t("mw-input",{attrs:{placeholder:e.setTitle,autofocus:!0},model:{value:e.param_edit.val,callback:function(a){e.$set(e.param_edit,"val",a)},expression:"param_edit.val"}})],1)])])},staticRenderFns:[]}}});
//# sourceMappingURL=102.e0d00a13ae5f4cb7e2cc.js.map